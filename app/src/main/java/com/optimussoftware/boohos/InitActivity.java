package com.optimussoftware.boohos;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.OperationCanceledException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.optimussoftware.boohos.account.AppIntro;
import com.optimussoftware.boohos.account.AuthActivity;
import com.optimussoftware.boohos.account.auth.AccountUtils;
import com.optimussoftware.boohos.data.Constants;
import com.optimussoftware.boohos.main.MainActivity;
import com.optimussoftware.boohos.widget.BaseActivity;


public class InitActivity extends BaseActivity {

    private AccountManager accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        FacebookSdk.sdkInitialize(getApplicationContext());
        init();
    }

    private void init() {
        if (!getPreferenceManager().getBoolean(Constants.KEY_FRESH_LOGIN, false)) {
            getPreferenceManager().setBoolean(Constants.KEY_FRESH_LOGIN, true);
            Log.i(InitActivity.class.getSimpleName(), "startIntro");
            starIntro();
        } else {
            Log.i(InitActivity.class.getSimpleName(), "startAuth");
            startAuth();
        }
    }

    private void starIntro() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(InitActivity.this, AppIntro.class);
                startActivityForResult(intent, Constants.CODE_SLIDER_INTRO);
            }
        }, 100);
    }

    private void startMain(final String authToken, final String user_id, final String account_name) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(InitActivity.this, MainActivity.class);
                intent.putExtra("authToken", authToken);
                intent.putExtra("user_id", user_id);
                intent.putExtra("account_name", account_name);
                startActivityForResult(intent, Constants.CODE_ACCESS_GRANTED);
                finish();
            }
        }, 100);
    }

    private void startAuth() {
        accountManager = AccountManager.get(InitActivity.this);
        accountManager.getAuthTokenByFeatures(AccountUtils.ACCOUNT_TYPE, AccountUtils.AUTH_TOKEN_TYPE,
                null, this, null, null, new GetAuthTokenCallback(), null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("resultado", requestCode + " d");
        switch (resultCode) {
            case Constants.CODE_START_LOGIN:
                startAuth();
                break;
            case Constants.CODE_ACCESS_DENIED:
                break;
        }
    }

    private class GetAuthTokenCallback implements AccountManagerCallback<Bundle> {

        @Override
        public void run(AccountManagerFuture<Bundle> result) {
            Bundle bundle;
            try {
                bundle = result.getResult();

                final Intent intent = (Intent) bundle.get(AccountManager.KEY_INTENT);
                if (null != intent) {
                    startActivityForResult(intent, Constants.CODE_START_APP);
                } else {
                    String authToken = bundle.getString(AccountManager.KEY_AUTHTOKEN);
                    final String accountName = bundle.getString(AccountManager.KEY_ACCOUNT_NAME);
                    final String user_id = bundle.getString(AuthActivity.PARAM_USER_ID);
                    final String source = bundle.getString(AuthActivity.PARAM_SOURCE);

                    // Save session username & auth token
                    getPreferenceManager().putString(Constants.TOKEN_API, authToken);
                    getPreferenceManager().putString(Constants.ACCOUNT_NAME, accountName);
                    getPreferenceManager().putString(Constants.SOURCE, source);
                    getPreferenceManager().putString(Constants.USER_ID_FIELD, user_id);

                    // If the logged account didn't exist, we need to create it on the device
                    Account account = AccountUtils.getAccount(InitActivity.this, accountName);
                    if (null == account) {
                        account = new Account(accountName, AccountUtils.ACCOUNT_TYPE);
                        accountManager.addAccountExplicitly(account, bundle.getString(AuthActivity.PARAM_USER_PASSWORD), null);
                        accountManager.setAuthToken(account, AccountUtils.AUTH_TOKEN_TYPE, authToken);
                    }
                    Log.i(InitActivity.class.getSimpleName(), "startMain");
                    startMain(authToken, user_id, accountName);
                }
            } catch (OperationCanceledException e) {
                // If signup was cancelled, force activity termination
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}