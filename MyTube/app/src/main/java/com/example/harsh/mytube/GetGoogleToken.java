package com.example.harsh.mytube;

import android.app.Activity;
import android.os.AsyncTask;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;

import java.io.IOException;

/**
 * Created by harsh on 10/18/2015.
 */
public class GetGoogleToken extends AsyncTask {

    Activity mActivity;
    String mScope;
    String mEmail;

    GetGoogleToken(Activity activity, String name, String scope) {
        this.mActivity = activity;
        this.mScope = scope;
        this.mEmail = name;
    }

    /**
     * Executes the asynchronous job. This runs when you call execute()
     * on the AsyncTask instance.
     */

    @Override
    protected String doInBackground(Object[] params) {
        String token = null;
        try {
            token = fetchToken();
            if (token != null) {

                // Use the token to access the user's Google data.
                return token;
            }
        } catch (IOException e) {
            // The fetchToken() method handles Google-specific exceptions,
            // so this indicates something went wrong at a higher level.
            // TIP: Check for network connectivity before starting the AsyncTask.
            return "Not Found";
        }
        return token;
    }

    /**
     * Gets an authentication token from Google and handles any
     * GoogleAuthException that may occur.
     */
    protected String fetchToken() throws IOException {
        try {
            return GoogleAuthUtil.getToken(mActivity, mEmail, mScope);
        } catch (UserRecoverableAuthException userRecoverableException) {
            // GooglePlayServices.apk is either old, disabled, or not present
            // so we need to show the user some UI in the activity to recover.
            // mActivity.handleException(userRecoverableException);
            return null;
        } catch (GoogleAuthException fatalException) {
            // Some other type of unrecoverable exception has occurred.
            // Report and log the error as appropriate for your app.
            return null;
        }
    }
}
