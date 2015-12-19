package com.example.harsh.mytube;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.google.api.services.youtube.YouTubeScopes;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

    private static final int RC_SIGN_IN = 0;

    // Google client to communicate with Google
    private GoogleApiClient mGoogleApiClient;

    public static Activity mActivity = null;

    private boolean mIntentInProgress;
    private boolean signedInUser;
    private ConnectionResult mConnectionResult;
    private SignInButton signinButton;
    private ImageView image;
    private TextView username, emailLabel;
    private LinearLayout profileFrame, signinFrame;

    public static String account_name = "";
    public static String oauthToken ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;
        signinButton = (SignInButton) findViewById(R.id.signin);
        signinButton.setOnClickListener(this);

        image = (ImageView) findViewById(R.id.image);
        username = (TextView) findViewById(R.id.username);
        emailLabel = (TextView) findViewById(R.id.email);

        profileFrame = (LinearLayout) findViewById(R.id.profileFrame);
        signinFrame = (LinearLayout) findViewById(R.id.signinFrame);

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API,Plus.PlusOptions.builder().build())
                .addScope(new Scope(Scopes.PROFILE))
                .addScope(new Scope(Scopes.EMAIL))
                .addScope(new Scope(YouTubeScopes.YOUTUBE))
                .build();

        findViewById(R.id.signin).setOnClickListener(this);

    }

    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();

    }

    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            logout();
            mGoogleApiClient.disconnect();
        }
    }

    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this, 0).show();
            return;
        }

        if (!mIntentInProgress) {
            // store mConnectionResult
            mConnectionResult = result;

            if (signedInUser) {
                resolveSignInError();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        switch (requestCode) {
            case RC_SIGN_IN:
                if (responseCode == RESULT_OK) {
                    signedInUser = false;

                }
                mIntentInProgress = false;
                if (!mGoogleApiClient.isConnecting()) {
                    mGoogleApiClient.connect();
                }
                break;
        }
    }

    @Override
    public void onConnected(Bundle arg0) {
        signedInUser = false;
        Toast.makeText(this, "Connected", Toast.LENGTH_LONG).show();
        //getProfileInformation();
        account_name = Plus.AccountApi.getAccountName(mGoogleApiClient);

        new Thread(){
           public void run() {
                String scope_string = "oauth2:" + Scopes.PLUS_LOGIN + " " + Scopes.PLUS_ME +" "+ Scopes.EMAIL+" "+Scopes.PROFILE +" "+ YouTubeScopes.YOUTUBE;
                GetGoogleToken getGoogleToken = new GetGoogleToken(mActivity,account_name , scope_string);
                try {
                    String token = getGoogleToken.fetchToken();
                    oauthToken = token;
                } catch (IOException e) {
                    e.printStackTrace();
                }
           }}.start();

        Intent searchActivity = new Intent(this, TabActivity.class);
        startActivity(searchActivity);

    }

    private void setOauthToken(String token) {
        oauthToken = token;
    }
    public String getOauthToken() {
        return oauthToken;
    }

    private void updateProfile(boolean isSignedIn) {
        if (isSignedIn) {
            signinFrame.setVisibility(View.GONE);
            profileFrame.setVisibility(View.VISIBLE);

        } else {
            signinFrame.setVisibility(View.VISIBLE);
            profileFrame.setVisibility(View.GONE);
        }
    }



    @Override
    public void onConnectionSuspended(int cause) {
        mGoogleApiClient.connect();
        updateProfile(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signin:
                googlePlusLogin();
                break;
        }

    }

    public void signIn(View v) {
        googlePlusLogin();

    }

    public void logout() {
        googlePlusLogout();
    }

    private void googlePlusLogin() {
        if (!mGoogleApiClient.isConnecting()) {
            signedInUser = true;
            resolveSignInError();
        }

    }

    private void googlePlusLogout() {
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
            updateProfile(false);
        }
    }



}
