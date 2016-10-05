package com.percimal.singaporeairlines;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.uber.sdk.android.core.UberSdk;
import com.uber.sdk.android.core.auth.AccessTokenManager;
import com.uber.sdk.android.core.auth.AuthenticationError;
import com.uber.sdk.android.core.auth.LoginCallback;
import com.uber.sdk.android.core.auth.LoginManager;
import com.uber.sdk.core.auth.AccessToken;
import com.uber.sdk.core.auth.Scope;
import com.uber.sdk.rides.client.Session;
import com.uber.sdk.rides.client.SessionConfiguration;
import com.uber.sdk.rides.client.UberRidesApi;
import com.uber.sdk.rides.client.services.RidesService;

import java.util.Arrays;

public class FlightDetailActivity extends AppCompatActivity {

    private AccessTokenManager accessTokenManager;
    private LoginManager loginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Flight flight = (Flight) getIntent().getSerializableExtra("flight");
        ((CollapsingToolbarLayout)findViewById(R.id.toolbar_layout)).setTitle(flight.originAirport + " - " + flight.destinationAirport);
        ((ImageView) findViewById(R.id.coverImage)).setImageResource(getResources().getIdentifier(flight.destinationAirport.toLowerCase(), "drawable", getPackageName()));

        SessionConfiguration config = new SessionConfiguration.Builder()
                .setClientId(getString(R.string.uber_client_id))
                .setClientSecret(getString(R.string.uber_client_secret))
                .setServerToken(getString(R.string.uber_server_token))
                .setEnvironment(SessionConfiguration.Environment.SANDBOX)
                .setScopes(Arrays.asList(Scope.PROFILE, Scope.ALL_TRIPS, Scope.REQUEST, Scope.RIDE_WIDGETS))
                .build();
        UberSdk.initialize(config);

        accessTokenManager = new AccessTokenManager(getApplicationContext());
        loginManager = new LoginManager(accessTokenManager, new LoginCallback() {
            @Override
            public void onLoginCancel() {
                Toast.makeText(getApplicationContext(), "cancel", Toast.LENGTH_SHORT).show();
                Log.v("ZZZ", "cancel");
            }

            @Override
            public void onLoginError(@NonNull AuthenticationError error) {
                Toast.makeText(getApplicationContext(), "login err", Toast.LENGTH_SHORT).show();
                Log.v("ZZZ", "login err");
            }

            @Override
            public void onLoginSuccess(@NonNull AccessToken accessToken) {
                Toast.makeText(getApplicationContext(), "login succ", Toast.LENGTH_SHORT).show();
                Log.v("ZZZ", "login succ");
            }

            @Override
            public void onAuthorizationCodeReceived(@NonNull String authorizationCode) {
                Toast.makeText(getApplicationContext(), "auth code rcv", Toast.LENGTH_SHORT).show();
                Log.v("ZZZ", "auth code rcv");
            }
        });
        loginManager.login(this);

        Session session = loginManager.getSession();
        RidesService service = UberRidesApi.with(session).build().createService();

//        final RideRequestButton rideRequestButton = new RideRequestButton(getApplicationContext());
//        ((LinearLayout) findViewById(R.id.content)).addView(rideRequestButton);
//        int requestCode = 1234;
//        rideRequestButton.setRequestBehavior(new RideRequestActivityBehavior(this, requestCode));
//        RideParameters rideParams = new RideParameters.Builder()
//                .setDropoffLocation(1.3550422, 103.9870393, "Terminal 3 Departure", "Airport Boulevard")
//                .build();
//        rideRequestButton.setRideParameters(rideParams);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        loginManager.onActivityResult(this, requestCode, resultCode, data);
    }

}
