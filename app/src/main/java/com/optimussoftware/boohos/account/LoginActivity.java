package com.optimussoftware.boohos.account;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.PersonBuffer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.optimussoftware.api.Auth;
import com.optimussoftware.api.core.Factory;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.data.Constants;
import com.optimussoftware.boohos.widget.BaseActivity;
import com.optimussoftware.db.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks, ResultCallback<People.LoadPeopleResult> {

    private GoogleApiClient mGoogleApiClient = null;
    private CallbackManager callbackManagerFacebook;
    private String phoneNumber = null;
    private String source = null;
    private String __token = null;
    private Auth auth = null;
    private BoohosToken boohosToken;
    private final String TAG = "Login";
    private GoogleSignInAccount acct;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_social);
        firebaseAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
        new SocialLoginAsyncTask().execute();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        // client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private Callback<BoohosToken> callbackLogin = new Callback<BoohosToken>() {
        @Override
        public void onResponse(Call<BoohosToken> call, Response<BoohosToken> response) {
            boohosToken = response.body();
            Intent intent = new Intent();
            if (boohosToken != null) {
                intent.putExtra("token", boohosToken.getToken());
                intent.putExtra("user_id", boohosToken.get_id());
                setResult(Constants.CODE_ACCESS_GRANTED, intent);
            } else {
                if (source.compareTo(Constants.SOURCE_SELF) == 0) {
                    intent.putExtra("erro_msj", getString(R.string.toast_login_error));
                } else {
                    intent.putExtra("erro_msj", getString(R.string.toast_login_social_error));
                }
                setResult(Constants.CODE_ACCESS_DENIED, intent);
            }
            finish();
        }

        @Override
        public void onFailure(Call<BoohosToken> call, Throwable t) {
            Log.d(TAG, "Auth Error" + t.toString());
            Intent intent = new Intent();
            intent.putExtra("erro_msj", t.toString());
            setResult(Constants.CODE_ACCESS_DENIED, intent);
            finish();
        }
    };

    private void apiAuthCall(User user) {
        try {
            auth = new Auth();
            auth.login(user, callbackLogin);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private GraphRequest.GraphJSONObjectCallback facebookCallball = new GraphRequest.GraphJSONObjectCallback() {
        @Override
        public void onCompleted(JSONObject object, GraphResponse response) {
            try {
                apiAuthCall(Factory.getUserFromFacebookData(response, phoneNumber));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void requestSuccessfullFacebook(final LoginResult loginResult) {
        __token = loginResult.getAccessToken().getToken();
        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), facebookCallball);
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,first_name,middle_name," +
                "last_name,gender,birthday,hometown");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void handleSignInResultGoogle(Intent data) {
        GoogleSignInResult result = com.google.android.gms.auth.api.Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        if (result != null && result.isSuccess()) {
            try {
                acct = result.getSignInAccount();
                Log.d(TAG, acct.getDisplayName() + " tokens " + acct.getIdToken());
                Plus.PeopleApi.load(mGoogleApiClient, acct.getId()).setResultCallback(this);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.d(TAG, result.getStatus().getStatusMessage() + " " + result.getStatus());
        }
    }

    @Override
    public void onResult(@NonNull People.LoadPeopleResult peopleData) {
        if (peopleData.getStatus().getStatusCode() == CommonStatusCodes.SUCCESS) {
            PersonBuffer personBuffer = peopleData.getPersonBuffer();
            try {
                int count = personBuffer.getCount();
                Log.d(TAG, "onResult: " + count);
                if (count > 0)
                    apiAuthCall(Factory.getUserFromGoogleData(acct, personBuffer.get(0), phoneNumber));
            } finally {
                personBuffer.release();
            }
        } else {
            Log.e(TAG, "Error requesting people data: " + peopleData.getStatus());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constants.SIGN_UP_GOOGLE:
                handleSignInResultGoogle(data);
                break;
            default:
                callbackManagerFacebook.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult result) {
        Log.d(TAG, "Error " + result.toString());
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Login Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        // client.connect();
        //AppIndex.AppIndexApi.start(client, getIndexApiAction());
        firebaseAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        // AppIndex.AppIndexApi.end(client, getIndexApiAction());
        // client.disconnect();
        firebaseAuth.removeAuthStateListener(mAuthListener);
    }

    class SocialLoginAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            phoneNumber = getIntent().getStringExtra("phoneNumber");
            source = getIntent().getStringExtra("source");
            if (source.compareTo(Constants.SOURCE_FACEBOOK) == 0)
                facebookLogin();
            if (source.compareTo(Constants.SOURCE_GOOGLE_PLUS) == 0)
                googleLogin();
            if (source.compareTo(Constants.SOURCE_SELF) == 0)
                selfLogin();
            return null;
        }

        void selfLogin() {
            User user = new User();
            user.setEmail(getIntent().getStringExtra("email"));
            user.setPassword(getIntent().getStringExtra("password"));
            user.setPhone(phoneNumber);
            user.setSource(source);
            apiAuthCall(user);
        }

        void facebookLogin() {
            callbackManagerFacebook = CallbackManager.Factory.create();
            LoginManager.getInstance().
                    logInWithReadPermissions(LoginActivity.this,
                            Arrays.asList("public_profile", "user_friends", "email", "user_birthday",
                                    "user_about_me", "user_hometown"));
            LoginManager.getInstance().registerCallback(callbackManagerFacebook,
                    new FacebookCallback<LoginResult>() {
                        @Override
                        public void onSuccess(LoginResult loginResult) {
                            handleFacebookAccessToken(loginResult.getAccessToken());
                            requestSuccessfullFacebook(loginResult);
                        }

                        @Override
                        public void onCancel() {
                            showToast("Auth Cancel");
                        }

                        @Override
                        public void onError(FacebookException exception) {
                            showToast(exception.getMessage());
                        }
                    });
        }

        void googleLogin() {

            Plus.PlusOptions options = new Plus.PlusOptions.Builder()
                    .addActivityTypes("http://schemas.google.com/AddActivity",
                            "http://schemas.google.com/ReviewActivity")
                    .build();

            GoogleSignInOptions gso = new GoogleSignInOptions
                    .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestScopes(new Scope(Scopes.PLUS_LOGIN))
                    .requestScopes(new Scope(Scopes.PLUS_ME))
                    .requestEmail()
                    .build();

            mGoogleApiClient = new GoogleApiClient.Builder(LoginActivity.this)
                    .enableAutoManage(LoginActivity.this, LoginActivity.this)
                    .addApi(com.google.android.gms.auth.api.Auth.GOOGLE_SIGN_IN_API, gso)
                    .addApi(Plus.API, options)
                    .addScope(Plus.SCOPE_PLUS_PROFILE)
                    .addScope(Plus.SCOPE_PLUS_LOGIN)
                    .build();


            Intent intent = com.google.android.gms.auth.api.Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            startActivityForResult(intent, Constants.SIGN_UP_GOOGLE);
        }
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    public void showToast(String msj) {
        Toast.makeText(this, msj, Toast.LENGTH_LONG).show();
    }

    public void showToast(int msj) {
        Toast.makeText(this, msj, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }
}
