package com.percimal.singaporeairlines;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.uber.sdk.android.core.UberSdk;
import com.uber.sdk.android.core.auth.AccessTokenManager;
import com.uber.sdk.android.core.auth.AuthenticationError;
import com.uber.sdk.android.core.auth.LoginCallback;
import com.uber.sdk.android.core.auth.LoginManager;
import com.uber.sdk.core.auth.AccessToken;
import com.uber.sdk.core.auth.Scope;
import com.uber.sdk.rides.client.SessionConfiguration;
import com.uber.sdk.rides.client.UberRidesApi;
import com.uber.sdk.rides.client.model.Ride;
import com.uber.sdk.rides.client.services.RidesService;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UberRequestActivity extends AppCompatActivity implements
        UberAuthFragment.OnFragmentInteractionListener,
        UberRequestFragment.OnFragmentInteractionListener,
        PorterRequestDialogFragment.OnFragmentInteractionListener {

    private static final int PERMISSIONS_REQUEST_FINE_LOCATION = 1;
    AccessTokenManager accessTokenManager;
    LoginManager loginManager;
    Flight flight;
    UberAuthFragment uberAuthFragment;
    UberRequestFragment uberRequestFragment;
    PorterRequestDialogFragment porterRequestDialog;
    RidesService ridesService;
    PorterService porterService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uber_request);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        flight = (Flight) getIntent().getSerializableExtra("flight");

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            initializeActivity();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_FINE_LOCATION);
        }

        porterService = new Retrofit.Builder()
                .baseUrl("http://api.sia.jon.sg/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PorterService.class);
    }

    private void initializeActivity() {
        SessionConfiguration config = new SessionConfiguration.Builder()
                .setClientId(getString(R.string.uber_client_id))
                .setClientSecret(getString(R.string.uber_client_secret))
                .setEnvironment(SessionConfiguration.Environment.SANDBOX)
                .setScopes(Arrays.asList(Scope.PROFILE, Scope.ALL_TRIPS, Scope.REQUEST, Scope.RIDE_WIDGETS))
                .build();
        UberSdk.initialize(config);

        LoginCallback loginCallback = new LoginCallback() {
            @Override
            public void onLoginCancel() {
                uberAuthFragment.loginFailCallback();
            }

            @Override
            public void onLoginError(@NonNull AuthenticationError error) {
                uberAuthFragment.loginFailCallback();
            }

            @Override
            public void onLoginSuccess(@NonNull AccessToken accessToken) {
                uberTokenInit();
            }

            @Override
            public void onAuthorizationCodeReceived(@NonNull String authorizationCode) {

            }
        };

        accessTokenManager = new AccessTokenManager(getApplicationContext());
        loginManager = new LoginManager(accessTokenManager, loginCallback);

        if(loginManager.isAuthenticated()) {
            uberTokenInit();
        } else {
            uberAuthFragment = new UberAuthFragment();
            getFragmentManager().beginTransaction().add(R.id.frame_layout, uberAuthFragment).commit();
        }

        porterRequestDialog = new PorterRequestDialogFragment();
    }

    private void uberTokenInit() {
        uberRequestFragment = new UberRequestFragment();
        uberRequestFragment.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction().replace(R.id.frame_layout, uberRequestFragment).commit();
        ridesService = UberRidesApi.with(loginManager.getSession()).build().createService();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        loginManager.onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initializeActivity();
                } else {
                    Toast.makeText(this, "This feature requires location permission", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            }
        }
    }

    @Override
    public void uberLogin() {
        loginManager.login(this);
    }

    @Override
    public void showPorterRequestDialog() {
        porterRequestDialog.show(getFragmentManager(), "porter");
        ridesService.getCurrentRide().enqueue(new Callback<Ride>() {
            @Override
            public void onResponse(Call<Ride> call, Response<Ride> response) {
                if (response.isSuccessful()) {
                    Ride ride = response.body();
                    // Query backend to check if ride already requested for porter service.
                    porterService.getPorterRequest(ride.getRideId()).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                porterRequestDialog.initialize(PorterRequestDialogFragment.PorterState.REQUESTED);
                            } else {
                                porterRequestDialog.initialize(PorterRequestDialogFragment.PorterState.AVAILABLE);
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            porterRequestDialog.initialize(PorterRequestDialogFragment.PorterState.UNAVAILABLE);
                        }
                    });
                } else {
                    porterRequestDialog.initialize(PorterRequestDialogFragment.PorterState.UNAVAILABLE);
                }
            }

            @Override
            public void onFailure(Call<Ride> call, Throwable t) {
                porterRequestDialog.initialize(PorterRequestDialogFragment.PorterState.UNAVAILABLE);
            }
        });
    }

    @Override
    public void requestPorter() {
        ridesService.getCurrentRide().enqueue(new Callback<Ride>() {
            @Override
            public void onResponse(Call<Ride> call, Response<Ride> response) {
                if (response.isSuccessful()) {
                    Ride ride = response.body();
                    porterService.createPorterRequest(new CreatePortalRequestBody(
                            accessTokenManager.getAccessToken().getToken(),
                            flight.flightNumber,
                            flight.originAirport,
                            flight.originTerminal,
                            flight.travelClass)).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                porterRequestDialog.requestCallback(true);
                            } else {
                                porterRequestDialog.requestCallback(false);
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            porterRequestDialog.requestCallback(false);
                        }
                    });
                } else {
                    porterRequestDialog.requestCallback(false);
                }
            }

            @Override
            public void onFailure(Call<Ride> call, Throwable t) {
                porterRequestDialog.requestCallback(false);
            }
        });
    }
}
