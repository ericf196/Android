package com.optimussoftware.boohos.account;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.androidadvance.topsnackbar.TSnackbar;
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.multi.CompositeMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.multi.SnackbarOnAnyDeniedMultiplePermissionsListener;
import com.kbeanie.imagechooser.api.ChooserType;
import com.kbeanie.imagechooser.api.ChosenImage;
import com.kbeanie.imagechooser.api.ChosenImages;
import com.kbeanie.imagechooser.api.ImageChooserListener;
import com.kbeanie.imagechooser.api.ImageChooserManager;
import com.kbeanie.imagechooser.api.IntentUtils;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.context.IconicsContextWrapper;
import com.mikepenz.iconics.view.IconicsButton;
import com.mikepenz.iconics.view.IconicsImageView;
import com.optimussoftware.api.Auth;
import com.optimussoftware.api.UserResource;
import com.optimussoftware.api.response.GenericResponse;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.data.Constants;
import com.optimussoftware.boohos.data.DBController;
import com.optimussoftware.boohos.security.UserRegisterNoSocialPermissionsListener;
import com.optimussoftware.boohos.util.Commons;
import com.optimussoftware.boohos.util.OptimusShowCase;
import com.optimussoftware.boohos.widget.BaseActivity;
import com.optimussoftware.boohos.widget.FontCache;
import com.optimussoftware.boohos.widget.OptimusSnackBar;
import com.optimussoftware.db.User;
import com.optimussoftware.image.Factory;
import com.optimussoftware.image.SizeImage;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.File;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRegisterActivity extends BaseActivity
        implements CalendarDatePickerDialogFragment.OnDateSetListener,
        ImageChooserListener {

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.input_register_email)
    MaterialEditText emailText;
    @BindView(R.id.input_register_password)
    MaterialEditText passwordText;
    @BindView(R.id.icon_show_password)
    IconicsImageView icon_show_password;
    @BindView(R.id.input_register_first_name)
    MaterialEditText firstNameText;
    @BindView(R.id.input_register_last_name)
    MaterialEditText lastNameText;
    @BindView(R.id.input_register_phone)
    MaterialEditText phoneText;
    @BindView(R.id.input_register_birthday)
    MaterialEditText birthdayText;
    @BindView(R.id.input_register_gender)
    MaterialBetterSpinner genderSpinner;
    @BindView(R.id.profile_image)
    CircleImageView profileImageView;
    @BindView(R.id.button_register_submit)
    IconicsButton mainButton;
    @BindView(R.id.floating_photo)
    FloatingActionMenu floatingPhoto;
    @BindView(R.id.option_camera)
    FloatingActionButton floatingCamera;
    @BindView(R.id.option_galery)
    FloatingActionButton floatingGalery;
    @BindView(android.R.id.content)
    ViewGroup rootView;

    private MultiplePermissionsListener allPermissionsListener;

    private static final String TAG = "RegisterNoSocial";
    private static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";
    public static final int MODE_REGISTER = 1;
    public static final int MODE_UPDATE = 2;

    private DateFormat dateFormat;
    private User user;
    private ImageChooserManager imageChooserManager;
    private String filePath;
    private int chooserType;
    private List<String> dataset = new LinkedList<>(Arrays.asList("Male", "Female"));
    private Activity activity;
    private boolean cameraAuthorized;
    private boolean galleryAuthorized;
    private String user_id;
    private String oldFile = null;
    boolean passVisible = false;
    private MaterialDialog dialogLoading;
    private boolean validateNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        activity = this;
        dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        user = new User();
        checkForSharedImage(getIntent());
        updateUI();
        setPermissions();
        Commons.setTypeFace(mainButton);
        dialogLoading = dialogLoaging();
    }

    private void updateUI() {
        setActionFloating();
        showPassword();
        String email = Commons.getEmail(this);
        if (email != null)
            emailText.setText(email);

        birthdayText.setFocusable(false);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, dataset);
        genderSpinner.setAdapter(adapter);

        if (getIntent().getIntExtra("MODE", MODE_REGISTER) == MODE_UPDATE) {
            populateForm();
            showTutorialEdit();
        } else {
            phoneText.setVisibility(View.GONE);
            genderSpinner.setVisibility(View.GONE);
            birthdayText.setVisibility(View.GONE);
        }
    }

    private String phone(String number) {
        try {
            PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
            Phonenumber.PhoneNumber pn1 = phoneUtil.parse(number,
                    Locale.getDefault().getCountry());
            String num = phoneUtil.format(pn1, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
            validateNumber = phoneUtil.isValidNumber(pn1);
            return num;
        } catch (NumberParseException e) {
            Log.e("NumberParseException", "NumberParseException : " + e.toString());
            validateNumber = false;
        }
        return null;
    }

    private void setActionFloating() {
        floatingPhoto.hideMenuButton(true);
        floatingPhoto.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingPhoto.hideMenuButton(true);
            }
        });

        int color = ContextCompat.getColor(this, R.color.colorPrimary);
        Drawable drawGalery = Factory.getIcon(this, CommunityMaterial.Icon.cmd_folder_image, 16);
        drawGalery.mutate().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        floatingGalery.setImageDrawable(drawGalery);

        Drawable drawCamera = Factory.getIcon(this, CommunityMaterial.Icon.cmd_camera, 16);
        drawCamera.mutate().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        floatingCamera.setImageDrawable(drawCamera);

        floatingGalery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingPhoto.close(true);
                floatingPhoto.hideMenuButton(true);
                chooseImage();
            }
        });
        floatingCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingPhoto.close(true);
                floatingPhoto.hideMenuButton(true);
                takePicture();
            }
        });
    }

    public void showPassword() {
        icon_show_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cursor = passwordText.getSelectionEnd();
                if (passVisible) {
                    passVisible = false;
                    icon_show_password.setIcon(Factory.getIcon(UserRegisterActivity.this, CommunityMaterial.Icon.cmd_eye, 30));
                    passwordText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordText.setTypeface(FontCache.getTypeface(UserRegisterActivity.this, getString(R.string.font_path_regular)));
                } else {
                    passVisible = true;
                    icon_show_password.setIcon(Factory.getIcon(UserRegisterActivity.this, CommunityMaterial.Icon.cmd_eye_off, 30));
                    passwordText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordText.setTypeface(FontCache.getTypeface(UserRegisterActivity.this, getString(R.string.font_path_regular)));
                }
                passwordText.setSelection(cursor);
            }
        });
    }

    private void populateForm() {
        DBController controller = DBController.getControler();
        mainButton.setText(getString(R.string.edit_profile_button_text));
        String uid = getIntent().getStringExtra("user_id");
        user = controller.getUser(this, uid);
        oldFile = user.getProfile_photo();

        firstNameText.setText(user.getFirst_name());
        lastNameText.setText(user.getLast_name());
        emailText.setText(user.getEmail());
        emailText.setEnabled(false);
        phoneText.setText(phone(user.getPhone()));
        try {
            birthdayText.setText(dateFormat.format(user.getBirthday()));
        } catch (Exception e) {
            Log.e(TAG, "Error user.getBirthday()--> " + e.getMessage());
        }
        SizeImage sizeImage = new SizeImage(UserRegisterActivity.this, 120, 120);
        Factory.setImage(profileImageView, this, oldFile, sizeImage);
        phoneText.setVisibility(View.VISIBLE);
        genderSpinner.setVisibility(View.VISIBLE);
        birthdayText.setVisibility(View.VISIBLE);
        passwordText.setVisibility(View.GONE);
        icon_show_password.setVisibility(View.GONE);
        genderSpinner.setText(user.getGender());
    }

    public void showTutorialEdit() {
        OptimusShowCase optimusShowCase = new OptimusShowCase(this);
        boolean showed = optimusShowCase.getInfoShow().isShowedScreenEditButton();
        Log.d(TAG, "isShowedScreenFloatingButton() " + showed);
        if (!showed) {
            Log.d(TAG, "Mostro");
            final ShowcaseView showcaseView = optimusShowCase.getShowCaseView(R.id.button_register_submit,
                    getString(R.string.app_name), getString(R.string.app_name), "ok");
            showcaseView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showcaseView.hide();
                }
            });
            showcaseView.show();
            optimusShowCase.getInfoShow().setShowedScreenEditButton(true);
        }
    }

    private void setPermissions() {
        MultiplePermissionsListener feedbackViewMultiplePermissionListener =
                new UserRegisterNoSocialPermissionsListener(this);
        allPermissionsListener =
                new CompositeMultiplePermissionsListener(feedbackViewMultiplePermissionListener,
                        SnackbarOnAnyDeniedMultiplePermissionsListener.Builder
                                .with(rootView, R.string.all_permissions_denied_init_activity)
                                .build());

        Dexter.continuePendingRequestsIfPossible(allPermissionsListener);
        if (!Dexter.isRequestOngoing()) {
            Dexter.checkPermissions(allPermissionsListener,
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void showPermissionRationale(final PermissionToken token) {
        new MaterialDialog.Builder(this)
                .title(R.string.permissions_title)
                .content(R.string.permissions_request)
                .negativeText(android.R.string.cancel)
                .positiveText(android.R.string.ok)
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
        if (permission.compareTo(Manifest.permission.READ_EXTERNAL_STORAGE) == 0)
            galleryAuthorized = true;
        if (permission.compareTo(Manifest.permission.CAMERA) == 0)
            cameraAuthorized = true;
        if (permission.compareTo(Manifest.permission.WRITE_EXTERNAL_STORAGE) == 0)
            cameraAuthorized = true;
    }

    public void permissionDenied(String permission, boolean isPermanentlyDenied) {

    }

    private Callback<GenericResponse> callbackSignup = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            Log.d(TAG, "Crear onResponse");
            setLoading(false);
            setResult(Constants.SIGN_UP_OK);
            finish();
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            setLoading(false);
            Log.d(TAG, "Crear onFailure : " + t.getMessage());
            errorSnackBar(getString(R.string.user_register_fails));
        }
    };

    private Callback<GenericResponse> callbackEditInfo = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            if (response.isSuccessful()) {
                Log.d(TAG, "Editar onResponse");
                getNewDateUser();
            } else {
                Log.e(TAG, "Editar onResponse error");
                setLoading(false);
                errorSnackBar(getString(R.string.user_register_fails));
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            Log.d(TAG, "Editar onFailure : " + t.getMessage());
            setLoading(false);
            errorSnackBar(getString(R.string.user_register_fails));
        }
    };

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(IconicsContextWrapper.wrap(context));
    }

    private void getNewDateUser() {
        UserResource userResource = new UserResource();
        Callback<com.optimussoftware.api.response.user.User> callbackUser =
                new Callback<com.optimussoftware.api.response.user.User>() {
                    @Override
                    public void onResponse(Call<com.optimussoftware.api.response.user.User> call,
                                           Response<com.optimussoftware.api.response.user.User> response) {
                        setLoading(false);
                        Log.d(TAG, "Get User onResponse");
                        User user = com.optimussoftware.api.core.Factory.getUserFromResponse(response.body());
                        DBController.getControler().createUser(activity, user);
                        setResult(Constants.EDIT_PROFILE_OK);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<com.optimussoftware.api.response.user.User> call, Throwable t) {
                        Log.d(TAG, "Get User onFailure " + t.toString());
                    }
                };
        userResource.get(user.get_id(), callbackUser);
    }

    private void register() {
        String fullName = firstNameText.getText().toString().trim()
                + " " + lastNameText.getText().toString().trim();
        user.setFull_name(fullName);
        user.setFirst_name(firstNameText.getText().toString().trim());
        user.setLast_name(lastNameText.getText().toString().trim());
        user.setEmail(emailText.getText().toString().trim());
        user.setPassword(passwordText.getText().toString());
        user.setPhone(phoneText.getText().toString().trim());//optional
        user.setGender(genderSpinner.getText().toString().toLowerCase());

        if (validateFirstName()) {
            if (validateLastName()) {
                if (validateEmail()) {
                    if (validatePassword()) {
                        if (validatePhoto()) {
                            setLoading(true);
                            Auth auth = new Auth();
                            auth.signUp(user, callbackSignup);
                        }
                    }
                }
            }
        }
    }

    private boolean validateFirstName() {
        if (TextUtils.isEmpty(user.getFirst_name())) {
            firstNameText.requestFocus();
            //firstNameText.setError(getString(R.string.enter_name));
            firstNameText.setError("");
            errorSnackBar(getString(R.string.enter_name));
            return false;
        }
        return true;
    }

    private boolean validateLastName() {
        if (TextUtils.isEmpty(user.getLast_name())) {
            lastNameText.requestFocus();
            //fullNameText.setError(getString(R.string.enter_name));
            lastNameText.setError("");
            errorSnackBar(getString(R.string.enter_name));
            return false;
        }
        return true;
    }

    private boolean validateEmail() {
        if (TextUtils.isEmpty(user.getEmail())) {
            emailText.requestFocus();
            //emailText.setError(getString(R.string.enter_email));
            emailText.setError("");
            errorSnackBar(getString(R.string.enter_email));
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(
                user.getEmail()).matches()) {
            emailText.requestFocus();
            //emailText.setError(getString(R.string.enter_email_valid));
            emailText.setError("");
            errorSnackBar(getString(R.string.enter_email_valid));
            return false;
        }
        return true;
    }

    private boolean validatePassword() {
        Pattern pattern = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,})");
        Matcher matcher = pattern.matcher(user.getPassword());
        if (!matcher.matches()) {
            passwordText.requestFocus();
            //passwordText.setError(getString(R.string.enter_password_valid));
            passwordText.setError("");
            errorSnackBar(getString(R.string.enter_password_valid));
            return false;
        }
        return true;
    }

    private boolean validatePhone() {
        if (TextUtils.isEmpty(user.getPhone())) {
            phoneText.requestFocus();
            //phoneText.setError(getString(R.string.enter_phone));
            phoneText.setError("");
            errorSnackBar(getString(R.string.enter_phone));
            return false;
        } else if (!validateNumber) {
            phoneText.requestFocus();
            //phoneText.setError(getString(R.string.enter_phone_valid));
            phoneText.setError("");
            errorSnackBar(getString(R.string.enter_phone_valid));
            return false;
        }
        return true;
    }

    private boolean validateGender() {
        if (TextUtils.isEmpty(user.getGender())) {
            genderSpinner.requestFocus();
            //genderSpinner.setError(getString(R.string.enter_gender));
            genderSpinner.setError("");
            errorSnackBar(getString(R.string.enter_gender));
            return false;
        }
        return true;
    }

    private boolean validateBirthday() {
        if (user.getBirthday() == null) {
            birthdayText.requestFocus();
            //birthdayText.setError(getString(R.string.enter_birthday));
            birthdayText.setError("");
            errorSnackBar(getString(R.string.enter_birthday));
            return false;
        }
        return true;
    }

    private boolean validatePhoto() {
        if (TextUtils.isEmpty(user.getProfile_photo())) {
            TSnackbar snackbar = OptimusSnackBar.snackBarTop(UserRegisterActivity.this,
                    coordinatorLayout, getString(R.string.enter_photo), OptimusSnackBar.LENGTH_LONG, true)
                    .setAction(getString(R.string.select), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            selectPhoto();
                        }
                    });
            snackbar.show();
            return false;
        }
        return true;
    }

    private void errorSnackBar(String mjs) {
        OptimusSnackBar.snackBarTop(UserRegisterActivity.this, coordinatorLayout,
                mjs, OptimusSnackBar.LENGTH_LONG, true).show();
    }

    private void update() {
        String fullName = firstNameText.getText().toString().trim()
                + " " + lastNameText.getText().toString().trim();
        user.setFull_name(fullName);
        user.setFirst_name(firstNameText.getText().toString().trim());
        user.setLast_name(lastNameText.getText().toString().trim());
        user.setEmail(emailText.getText().toString().trim());
        user.setPhone(phone(phoneText.getText().toString()));
        user.setGender(genderSpinner.getText().toString().toLowerCase());

        Log.d(TAG, "user getFirst_name " + user.getFirst_name());
        Log.d(TAG, "user getEmail " + user.getEmail());
        Log.d(TAG, "user getPhone " + user.getPhone());
        Log.d(TAG, "user getGender " + user.getGender());

        if (validateFirstName()) {
            if (validateLastName()) {
                if (validatePhone()) {
                    if (validateGender()) {
                        if (validateBirthday()) {
                            if (validatePhoto()) {
                                setLoading(true);
                                UserResource auth = new UserResource();
                                auth.updateInfo(user, oldFile, callbackEditInfo);
                            }
                        }
                    }
                }
            }
        }
    }

    private MaterialDialog dialogLoaging() {
        return new MaterialDialog.Builder(this)
                .theme(Theme.LIGHT)
                .content(R.string.please_wait)
                .autoDismiss(false)
                .progress(true, 0)
                .build();
    }

    private void setLoading(boolean loading) {
        if (loading) {
            dialogLoading.show();
            mainButton.setEnabled(false);
        } else {
            dialogLoading.dismiss();
            mainButton.setEnabled(true);
        }
    }

    @OnClick(R.id.button_register_submit)
    public void registerUser(View view) {
        if (getIntent().getIntExtra("MODE", MODE_REGISTER) == MODE_REGISTER)
            register();
        else
            update();
    }

    @OnClick(R.id.profile_image)
    public void selectPicture(View view) {
        selectPhoto();
    }

    private void selectPhoto() {
        floatingPhoto.setVisibility(View.VISIBLE);
        floatingPhoto.showMenuButton(true);
        floatingPhoto.open(true);
    }

    @OnClick(R.id.input_register_birthday)
    public void setBirthday(View view) {
        CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                .setOnDateSetListener(UserRegisterActivity.this);
        cdp.show(getSupportFragmentManager(), FRAG_TAG_DATE_PICKER);
    }

    @Override
    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(year, monthOfYear, dayOfMonth);
        Date date = calendar.getTime();
        user.setBirthday(date);
        birthdayText.setText(dateFormat.format(date));
    }

    @Override
    public void onImagesChosen(final ChosenImages images) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "On Images Chosen: " + images.size());
                onImageChosen(images.getImage(0));
            }
        });
    }

    @Override
    public void onImageChosen(final ChosenImage image) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (image != null) {
                    user.setProfile_photo(image.getFilePathOriginal());
                    File file = new File(image.getFilePathOriginal());
                    Uri uri = Uri.fromFile(file);
                    SizeImage sizeImage = new SizeImage(UserRegisterActivity.this, 120, 120);
                    Factory.setImage(profileImageView, activity, uri, sizeImage);
                }
            }
        });
    }

    @Override
    public void onError(final String reason) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Log.i(TAG, "OnError: " + reason);
                errorSnackBar(reason);
                //showToast(reason);
            }
        });
    }

    private void chooseImage() {
        chooserType = ChooserType.REQUEST_PICK_PICTURE;
        imageChooserManager = new ImageChooserManager(this,
                ChooserType.REQUEST_PICK_PICTURE, true);
        Bundle bundle = new Bundle();
        bundle.putBoolean(Intent.EXTRA_ALLOW_MULTIPLE, false);
        imageChooserManager.setExtras(bundle);
        imageChooserManager.setImageChooserListener(this);
        imageChooserManager.clearOldFiles();
        try {
            filePath = imageChooserManager.choose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void takePicture() {
        chooserType = ChooserType.REQUEST_CAPTURE_PICTURE;
        imageChooserManager = new ImageChooserManager(this,
                ChooserType.REQUEST_CAPTURE_PICTURE, true);
        imageChooserManager.setImageChooserListener(this);
        try {
            filePath = imageChooserManager.choose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkForSharedImage(Intent intent) {
        if (intent != null) {
            if (intent.getAction() != null && intent.getType() != null && intent.getExtras() != null) {
                ImageChooserManager m = new ImageChooserManager(this, ChooserType.REQUEST_PICK_PICTURE);
                m.setImageChooserListener(this);
                m.submit(ChooserType.REQUEST_PICK_PICTURE, IntentUtils.getIntentForMultipleSelection(intent));
            }
        }
    }

    private void reinitializeImageChooser() {
        imageChooserManager = new ImageChooserManager(this, chooserType, true);
        Bundle bundle = new Bundle();
        bundle.putBoolean(Intent.EXTRA_ALLOW_MULTIPLE, false);
        imageChooserManager.setExtras(bundle);
        imageChooserManager.setImageChooserListener(this);
        imageChooserManager.reinitialize(filePath);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "OnActivityResult");
        Log.i(TAG, "File Path : " + filePath);
        Log.i(TAG, "Chooser Type: " + chooserType);
        if (resultCode == RESULT_OK
                && (requestCode == ChooserType.REQUEST_PICK_PICTURE || requestCode == ChooserType.REQUEST_CAPTURE_PICTURE)) {
            if (imageChooserManager == null) {
                reinitializeImageChooser();
            }
            imageChooserManager.submit(requestCode, data);
        }
    }

    @Override
    public void onBackPressed() {
        setResult(Constants.SIGN_UP_BACK);
        finish();
    }
}
