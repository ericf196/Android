package com.optimussoftware.boohos.account;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.multi.CompositeMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.multi.SnackbarOnAnyDeniedMultiplePermissionsListener;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.view.IconicsButton;
import com.mikepenz.iconics.view.IconicsImageView;
import com.optimussoftware.api.Auth;
import com.optimussoftware.api.UserResource;
import com.optimussoftware.api.response.user.Wish;
import com.optimussoftware.db.User;
import com.optimussoftware.boohos.App;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.account.auth.AccountUtils;
import com.optimussoftware.boohos.data.Constants;
import com.optimussoftware.boohos.data.DBController;
import com.optimussoftware.boohos.data.PersonalInfo;
import com.optimussoftware.boohos.security.LoginActivityPermissionsListener;
import com.optimussoftware.boohos.util.Commons;
import com.optimussoftware.boohos.util.PreferenceManager;
import com.optimussoftware.boohos.widget.FontCache;
import com.optimussoftware.boohos.widget.OptimusSnackBar;
import com.optimussoftware.boohos.widget.OptimusTextView;
import com.optimussoftware.image.Factory;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthActivity extends AccountAuthenticatorActivity
        implements View.OnClickListener {

    @BindView(R.id.main_layout)
    View mLayout;

    @BindView(R.id.login_google_plus)
    com.mikepenz.iconics.view.IconicsButton buttonGoogle;
    @BindView(R.id.login_facebook)
    com.mikepenz.iconics.view.IconicsButton buttonFacebook;

    @BindView(R.id.input_login_email)
    MaterialEditText userEmailText;
    @BindView(R.id.input_login_password)
    MaterialEditText userPasswordText;
    @BindView(R.id.icon_show_pass)
    IconicsImageView icon_show_pass;
    @BindView(R.id.forgot_pass)
    OptimusTextView forgotPass;
    @BindView(R.id.login_button)
    IconicsButton buttonLogin;

    @BindView(android.R.id.content)
    ViewGroup rootView;

    public static final String ARG_ACCOUNT_TYPE = "Herme";
    public static final String ARG_AUTH_TOKEN_TYPE = "authTokenType";
    public static final String ARG_IS_ADDING_NEW_ACCOUNT = "isAddingNewAccount";
    public static final String PARAM_USER_PASSWORD = "password";
    public static final String PARAM_SOURCE = "source";
    public static final String PARAM_USER_ID = "user_id";

    /*Account Manager*/
    private AccountManager accountManager;

    // Values for email and password at the time of the login attempt.
    private String email;
    private String password;
    private String TAG = null;
    private String source = null;
    private PreferenceManager preferenceManager = null;
    private PersonalInfo personalInfo = null;
    private String phoneNumber = "";
    //private String phoneNumber = null;
    private boolean googleactive = false;
    private Auth auth = null;
    private BoohosToken boohosToken;
    private boolean passVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_fragment);
        ButterKnife.bind(this);
        preferenceManager = new PreferenceManager(getApplicationContext());
        TAG = Commons.getTag(this.getApplicationContext(), AuthActivity.class);
        init();
    }

    private void init() {
        accountManager = AccountManager.get(this);
        Activity activity = this;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        DBController.getControler().createDB(getBaseContext());
        preferenceManager.putInt(Constants.BEACONS_TIME_DELAY, 10);
        buttonGoogle.setOnClickListener(this);
        buttonFacebook.setOnClickListener(this);
        buttonLogin.setOnClickListener(this);
        Commons.setTypeFace(buttonGoogle);
        Commons.setTypeFace(buttonFacebook);
        Commons.setTypeFace(buttonLogin);
        setPermissions();
        showPassword();
        forgotPassword();
    }

    public void showPassword() {
        icon_show_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cursor = userPasswordText.getSelectionEnd();
                if (passVisible) {
                    passVisible = false;
                    icon_show_pass.setIcon(Factory.getIcon(AuthActivity.this, CommunityMaterial.Icon.cmd_eye, 30));
                    userPasswordText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    userPasswordText.setTypeface(FontCache.getTypeface(AuthActivity.this, getString(R.string.font_path_regular)));
                } else {
                    passVisible = true;
                    icon_show_pass.setIcon(Factory.getIcon(AuthActivity.this, CommunityMaterial.Icon.cmd_eye_off, 30));
                    userPasswordText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    userPasswordText.setTypeface(FontCache.getTypeface(AuthActivity.this, getString(R.string.font_path_regular)));
                }
                userPasswordText.setSelection(cursor);
            }
        });
    }

    public void forgotPassword() {
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AuthActivity.this, ForgotPassActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setPermissions() {
        MultiplePermissionsListener feedbackViewMultiplePermissionListener =
                new LoginActivityPermissionsListener(this);
        MultiplePermissionsListener allPermissionsListener =
                new CompositeMultiplePermissionsListener(feedbackViewMultiplePermissionListener,
                        SnackbarOnAnyDeniedMultiplePermissionsListener.Builder
                                .with(rootView, R.string.all_permissions_denied_init_activity)
                                .build());
        Dexter.continuePendingRequestsIfPossible(allPermissionsListener);
        if (!Dexter.isRequestOngoing()) {
            Dexter.checkPermissions(allPermissionsListener,
                    Manifest.permission.GET_ACCOUNTS);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void showPermissionRationale(final PermissionToken token) {
        new MaterialDialog.Builder(this)
                .theme(Theme.LIGHT)
                .title(R.string.permissions_title)
                .content(R.string.permissions_request)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        token.cancelPermissionRequest();
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        token.continuePermissionRequest();
                    }
                })
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    public void permissionGranted(String permission) {
        if ((permission.compareTo(Manifest.permission.GET_ACCOUNTS)) == 0)
            googleactive = true;
        if ((permission.compareTo(Manifest.permission.READ_PHONE_STATE)) == 0)
            phoneNumber = Commons.getNumberPhone(this);
    }

    public void permissionDenied(String permission, boolean isPermanentlyDenied) {
        if ((permission.compareTo(Manifest.permission.GET_ACCOUNTS)) == 0)
            googleactive = false;
        if ((permission.compareTo(Manifest.permission.READ_PHONE_STATE)) == 0)
            phoneNumber = "";
    }

    public void signUp(View view) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(AuthActivity.this, UserRegisterActivity.class);
                intent.putExtra("phoneNumber", phoneNumber);
                startActivityForResult(intent, Constants.SIGN_UP);
            }
        }, 1000);
    }

    /**
     * @param user
     */
    private void saveInfoPerson(User user) {
        DBController.getControler().createUser(getBaseContext(), user);
        personalInfo = new PersonalInfo(preferenceManager);
        personalInfo.setUuid(user.get_id());
        personalInfo.writeInfo();
    }

    /**
     * @param user
     */
    private void registerAndStart(User user) {
        saveInfoPerson(user);

        final Account account = new Account(user.getEmail(), AuthActivity.ARG_ACCOUNT_TYPE);

        if (getIntent().getBooleanExtra(ARG_IS_ADDING_NEW_ACCOUNT, false)) {
            accountManager.addAccountExplicitly(account, getIntent().getStringExtra("password"), null);
        } else {
            accountManager.setPassword(account, getIntent().getStringExtra("password"));
        }

        accountManager.setAuthToken(account, AccountUtils.AUTH_TOKEN_TYPE, boohosToken.getToken());
        accountManager.setUserData(account, PARAM_SOURCE, user.getSource());
        accountManager.setUserData(account, PARAM_USER_ID, user.get_id());

        Intent intent = new Intent();
        intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, user.getEmail());
        intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, AccountUtils.ACCOUNT_TYPE);
        intent.putExtra(AccountManager.KEY_AUTHTOKEN, boohosToken.getToken());
        intent.putExtra(PARAM_USER_PASSWORD, password);
        intent.putExtra(PARAM_USER_ID, user.get_id());
        intent.putExtra(PARAM_SOURCE, user.getSource());

        setAccountAuthenticatorResult(intent.getExtras());
        setResult(AccountAuthenticatorActivity.RESULT_OK, intent);
        finish();
    }

    private Callback<com.optimussoftware.api.response.user.User> callbackUser =
            new Callback<com.optimussoftware.api.response.user.User>() {
                @Override
                public void onResponse(Call<com.optimussoftware.api.response.user.User> call,
                                       Response<com.optimussoftware.api.response.user.User> response) {
//                    Log.d(TAG, "getUserFromResponse: " + response.body().getWishes().size());
                    try {
                        if (!response.body().getWishes().isEmpty()) {
                            for (Wish one : response.body().getWishes()) {
                                Log.d(TAG, "getUserFromResponse: " + one.getName());
                                DBController.getControler().createUserInterest(getBaseContext(), com.optimussoftware.api.core.Factory.getUserInterestFromWish(one, response.body().getId()));
                            }
                        }
                    }catch (Exception e) {

                    }
                    registerAndStart(com.optimussoftware.api.core.Factory.getUserFromResponse(response.body()));
                }

                @Override
                public void onFailure(Call<com.optimussoftware.api.response.user.User> call, Throwable t) {
                    Log.d("Get User Error", t.toString());
                }
            };

    private void initLoginActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(AuthActivity.this, LoginActivity.class);
                intent.putExtra("source", source);
                intent.putExtra("phoneNumber", phoneNumber);
                if (source.compareTo(Constants.SOURCE_SELF) == 0) {
                    intent.putExtra("email", email);
                    intent.putExtra("password", password);
                    startActivityForResult(intent, Constants.CODE_ACCESS_REQUEST);
                } else
                    startActivityForResult(intent, Constants.CODE_ACCESS_REQUEST_SOCIAL);
            }
        }, 1000);
    }

    public void loginUser() {
        //hide pass
        passVisible = false;
        icon_show_pass.setIcon(Factory.getIcon(AuthActivity.this, CommunityMaterial.Icon.cmd_eye, 30));
        userPasswordText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        userPasswordText.setTypeface(FontCache.getTypeface(AuthActivity.this, getString(R.string.font_path_regular)));

        email = userEmailText.getText().toString().toLowerCase().trim();
        password = userPasswordText.getText().toString();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            showToast(R.string.toast_missing_field);
            return;
        }
        source = Constants.SOURCE_SELF;
        initLoginActivity();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                loginUser();
                break;
            case R.id.login_google_plus:
                if (googleactive) {
                    source = Constants.SOURCE_GOOGLE_PLUS;
                    initLoginActivity();
                } else
                    alertNoGoogle();
                break;
            case R.id.login_facebook:
                source = Constants.SOURCE_FACEBOOK;
                initLoginActivity();
                break;
        }
    }

    private void alertNoGoogle() {
        int itemsColor, titleColor;
        Drawable icon;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            icon = getDrawable(R.drawable.ic_notification);
            titleColor = getColor(R.color.colorAccent);
            itemsColor = getColor(R.color.colorPrimary);

        } else {
            icon = getResources().getDrawable(R.drawable.ic_notification);
            titleColor = getResources().getColor(R.color.colorAccent);
            itemsColor = getResources().getColor(R.color.colorPrimary);
        }
        new MaterialDialog.Builder(this)
                .theme(Theme.LIGHT)
                .title(R.string.app_name)
                .titleColor(titleColor)
                .icon(icon)
                .content(R.string.google_plus_permission_error)
                .typeface(App.optimusFontDefault, App.optimusFontDefault)
                .positiveColor(itemsColor)
                .positiveText(android.R.string.ok)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constants.SIGN_UP:
                signupSelfResult(resultCode);
                break;
            case Constants.CODE_ACCESS_REQUEST:
                loginResult(resultCode, data);
                break;
            case Constants.CODE_ACCESS_REQUEST_SOCIAL:
                socialLoginResult(resultCode, data);
        }
    }

    private BoohosToken buildToken(Intent data) {
        App.setTokenBoohos(data.getStringExtra("token"));
        return new BoohosToken(data.getStringExtra("user_id"), data.getStringExtra("token"));
    }

    private void accessGranted() {
        UserResource userResource = new UserResource();
        userResource.get(boohosToken.get_id(), callbackUser);
    }

    private void loginResult(int resultCode, Intent data) {
        Log.d("resultado", "esto- " + resultCode + " erro_msj- " + data.getStringExtra("erro_msj"));
        switch (resultCode) {
            case Constants.CODE_ACCESS_GRANTED:
                boohosToken = buildToken(data);
                accessGranted();
                break;
            case Constants.CODE_ACCESS_DENIED:
                OptimusSnackBar.snackBarTop(AuthActivity.this,
                        mLayout, data.getStringExtra("erro_msj"),
                        OptimusSnackBar.LENGTH_LONG, true).show();
        }
    }

    private void socialLoginResult(int resultCode, Intent data) {
        switch (resultCode) {
            case Constants.CODE_ACCESS_GRANTED:
                boohosToken = buildToken(data);
                accessGranted();
                break;
            case Constants.CODE_ACCESS_DENIED:
                showToast(data.getStringExtra("error_msj"));
        }
    }

    private void signupSelfResult(int resultCode) {
        if (Constants.SIGN_UP_OK == resultCode)
            showToast(R.string.user_register_successful);
        else if (Constants.SIGN_UP_BACK != resultCode)
            showToast(R.string.user_register_fails);
    }

    @Override
    public void onBackPressed() {

    }

    public void showToast(String msj) {
        Toast.makeText(this, msj, Toast.LENGTH_LONG).show();
    }

    public void showToast(int msj) {
        Toast.makeText(this, msj, Toast.LENGTH_LONG).show();
    }
}
