package com.optimussoftware.google;

import android.app.Activity;
import android.os.AsyncTask;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;

/**
 *
 * Created by Created by william.castillo@optimus-software.com on 07/05/16.
 */
public abstract class FullRequestPerson extends AsyncTask<Activity, Void, String>
        implements IAsyncTask {
    private GoogleApiClient mGoogleApiClient = null;
    private Activity activity = null;

    public FullRequestPerson(Activity activity, GoogleApiClient mGoogleApiClient){
        this.activity = activity;
        this.mGoogleApiClient = mGoogleApiClient;
    }

    public abstract void onResponseReceived(Object result);
    @Override
    protected String doInBackground(Activity... params) {
        String scope = "oauth2:profile email";
        String account = Plus.AccountApi.getAccountName(mGoogleApiClient);
        String token = null;
        try {
            token = GoogleAuthUtil.getToken(activity, account, scope);
        } catch (UserRecoverableAuthException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            Exception mException = e;
        }
        return token;
    }

    @Override
    protected void onPostExecute(String result) {
        onResponseReceived(result);
    }
}
