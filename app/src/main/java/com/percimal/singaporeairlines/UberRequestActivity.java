package com.percimal.singaporeairlines;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.uber.sdk.android.core.UberSdk;
import com.uber.sdk.android.core.auth.AccessTokenManager;
import com.uber.sdk.android.core.auth.AuthenticationError;
import com.uber.sdk.android.core.auth.LoginCallback;
import com.uber.sdk.android.core.auth.LoginManager;
import com.uber.sdk.core.auth.AccessToken;
import com.uber.sdk.core.auth.Scope;
import com.uber.sdk.rides.client.SessionConfiguration;

import java.util.Arrays;

public class UberRequestActivity extends AppCompatActivity implements UberRequestFragment.OnFragmentInteractionListener, UberAuthFragment.OnFragmentInteractionListener {

    AccessTokenManager accessTokenManager;
    LoginManager loginManager;
    Flight flight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uber_request);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        flight = (Flight) getIntent().getSerializableExtra("flight");

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

            }

            @Override
            public void onLoginError(@NonNull AuthenticationError error) {

            }

            @Override
            public void onLoginSuccess(@NonNull AccessToken accessToken) {
                UberRequestFragment uberRequestFragment = new UberRequestFragment();
                uberRequestFragment.setArguments(getIntent().getExtras());
                getFragmentManager().beginTransaction().replace(R.id.frame_layout, uberRequestFragment).commit();
            }

            @Override
            public void onAuthorizationCodeReceived(@NonNull String authorizationCode) {

            }
        };

        accessTokenManager = new AccessTokenManager(getApplicationContext());
        loginManager = new LoginManager(accessTokenManager, loginCallback);

        if(loginManager.isAuthenticated()) {
            UberRequestFragment uberRequestFragment = new UberRequestFragment();
            uberRequestFragment.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction().add(R.id.frame_layout, uberRequestFragment).commit();
        } else {
            UberAuthFragment uberAuthFragment = new UberAuthFragment();
            getFragmentManager().beginTransaction().add(R.id.frame_layout, uberAuthFragment).commit();
        }

    }

    @Override
    public void authenticate() {
        loginManager.login(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        loginManager.onActivityResult(this, requestCode, resultCode, data);
    }
}
