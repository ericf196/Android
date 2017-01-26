package com.optimussoftware.boohos.account;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.calendardatepicker.MonthAdapter;
import com.mikepenz.iconics.view.IconicsButton;
import com.mikepenz.iconics.view.IconicsImageView;
import com.optimussoftware.api.UserResource;
import com.optimussoftware.api.response.GenericResponse;
import com.optimussoftware.db.User;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.data.DBController;
import com.optimussoftware.boohos.util.Commons;
import com.optimussoftware.boohos.widget.BaseActivity;
import com.optimussoftware.boohos.widget.OptimusSnackBar;
import com.optimussoftware.boohos.widget.OptimusTextView;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by leonardojpr on 10/18/16.
 */

public class BirthdayAndGenderActivity extends BaseActivity implements CalendarDatePickerDialogFragment.OnDateSetListener {

    private static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.birthday_gender)
    LinearLayout birthday_gender;
    @BindView(R.id.select_female)
    LinearLayout select_female;
    @BindView(R.id.select_male)
    LinearLayout select_male;
    @BindView(R.id.select_birthday)
    MaterialEditText select_birthday;
    @BindView(R.id.button_edit_submit)
    IconicsButton mainButton;
    @BindView(R.id.input_female)
    OptimusTextView input_female;
    @BindView(R.id.input_male)
    OptimusTextView input_male;
    @BindView(R.id.text_birthday)
    OptimusTextView text_birthday;
    @BindView(R.id.iconics_male)
    IconicsImageView iconics_male;
    @BindView(R.id.iconics_female)
    IconicsImageView iconics_female;

    private DateFormat dateFormat;
    private User user;
    private MaterialDialog dialogLoading;

    String user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.birthday_and_gender);
        ButterKnife.bind(this);
        user_id = getIntent().getStringExtra("user_id");
        Commons.setTypeFace(mainButton);
        mainButton.setText(getString(R.string.edit_profile_button_text));
        dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        select_birthday.setFocusable(false);
        checkBirthdayAndGender();
    }

    @Override
    public void onBackPressed() {

    }

    private void checkBirthdayAndGender() {
        user = new User();
        dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        dialogLoading = dialogLoaging();
        Commons.setTypeFace(mainButton);
        mainButton.setText(getBaseContext().getString(R.string.continue_));
    }

    private void update() {

        if (validateGender()) {
            if (validateBirthday()) {
                UserResource auth = new UserResource();
                auth.updateBirthdayAndGender(user, user_id, DBController.getControler().getUser(getBaseContext(), user_id).get_etag(), callbackEditInfo);
                setLoading(true);
            }
        }

    }

    private Callback<GenericResponse> callbackEditInfo = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            if (response.isSuccessful()) {
                Log.d("lary", "Editar onResponse");
                getNewDateUser();
            } else {
                Log.e("lary", "Editar onResponse error -->" + response.errorBody().contentType());
                setLoading(false);
                // errorSnackBar(getString(R.string.user_register_fails));
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            Log.d("lary", "Editar onFailure : " + t.getMessage());
            setLoading(false);
            // errorSnackBar(getString(R.string.user_register_fails));
        }
    };


    private void getNewDateUser() {
        UserResource userResource = new UserResource();
        Callback<com.optimussoftware.api.response.user.User> callbackUser =
                new Callback<com.optimussoftware.api.response.user.User>() {
                    @Override
                    public void onResponse(Call<com.optimussoftware.api.response.user.User> call,
                                           Response<com.optimussoftware.api.response.user.User> response) {
                        setLoading(false);
                        User user = com.optimussoftware.api.core.Factory.getUserFromResponse(response.body());
                        DBController.getControler().createUser(getBaseContext(), user);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<com.optimussoftware.api.response.user.User> call, Throwable t) {
                        Log.d("Get User Error", t.toString());
                    }
                };
        userResource.get(user_id, callbackUser);
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

    private boolean validateGender() {
        if (TextUtils.isEmpty(user.getGender())) {
            select_male.requestFocus();
            //genderSpinner.setError(getString(R.string.enter_gender));
            //  genderSpinner.setError("");
            errorSnackBar(getString(R.string.enter_gender));
            return false;
        }
        return true;
    }

    private boolean validateBirthday() {
        if (user.getBirthday() == null) {
            //  select_birthdayec.requestFocus();
            //birthdayText.setError(getString(R.string.enter_birthday));
            select_birthday.setError("");
            errorSnackBar(getString(R.string.enter_birthday));
            return false;
        }
        return true;
    }

    @OnClick(R.id.select_birthday)
    public void setBirthday(View view) {
        DateTime now = DateTime.now();
        MonthAdapter.CalendarDay minDate = new MonthAdapter.CalendarDay(now.getYear() - 100, now.getMonthOfYear(), now.getDayOfMonth());
        MonthAdapter.CalendarDay maxDate = new MonthAdapter.CalendarDay(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth());
        CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                .setOnDateSetListener(BirthdayAndGenderActivity.this);
        cdp.setDateRange(minDate, maxDate);
        cdp.show(getSupportFragmentManager(), FRAG_TAG_DATE_PICKER);
    }

    @OnClick(R.id.select_female)
    public void setGenderFemale(View view) {
        user.setGender(getString(R.string.input_user_gender_female));
        input_male.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorEnabled));
        input_female.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        iconics_female.setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        iconics_male.setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorEnabled));
    }

    @OnClick(R.id.select_male)
    public void setGenderMale(View view) {
        user.setGender(getString(R.string.input_user_gender_male));
        input_female.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorEnabled));
        input_male.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        iconics_male.setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        iconics_female.setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorEnabled));
    }

    @OnClick(R.id.button_edit_submit)
    public void registerUser(View view) {
        Log.i("", user.get_id() + " " + user.getGender() + " " + user.getBirthday());
        update();
    }


    private MaterialDialog dialogLoaging() {
        return new MaterialDialog.Builder(this)
                .theme(Theme.LIGHT)
                .content(R.string.please_wait)
                .autoDismiss(false)
                .progress(true, 0)
                .build();
    }

    @Override
    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(year, monthOfYear, dayOfMonth);
        Date date = calendar.getTime();
        user.setBirthday(date);
        select_birthday.setText(dateFormat.format(date));
        select_birthday.setHint("");
        text_birthday.setVisibility(View.VISIBLE);
    }

    private void errorSnackBar(String mjs) {
        OptimusSnackBar.snackBarTop(BirthdayAndGenderActivity.this, coordinatorLayout,
                mjs, OptimusSnackBar.LENGTH_LONG, true).show();
    }
}