package com.optimussoftware.boohos.widget;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.AttributeSet;
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
import com.optimussoftware.boohos.data.Constants;
import com.optimussoftware.db.User;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.data.DBController;
import com.optimussoftware.boohos.data.PersonalInfo;
import com.optimussoftware.boohos.util.Commons;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by leonardojpr on 11/10/16.
 * GenderAndBirthdayView
 */

public class GenderAndBirthdayView extends CoordinatorLayout {
    private static final String TAG = GenderAndBirthdayView.class.getSimpleName();
    private static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";

    private CoordinatorLayout coordinatorLayout;
    private LinearLayout birthday_gender;
    private LinearLayout select_female;
    private LinearLayout select_male;
    private MaterialEditText select_birthday;
    private IconicsButton editButton;
    private OptimusTextView input_female;
    private OptimusTextView input_male;
    private OptimusTextView text_birthday;
    private IconicsImageView iconics_male;
    private IconicsImageView iconics_female;

    private PersonalInfo personalInfo;
    private User user;

    private DateFormat dateFormat;

    private MaterialDialog dialogLoading;

    public GenderAndBirthdayView(Context context) {
        super(context);
        init();
    }

    public GenderAndBirthdayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GenderAndBirthdayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.birthday_and_gender, this);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        birthday_gender = (LinearLayout) findViewById(R.id.birthday_gender);
        select_female = (LinearLayout) findViewById(R.id.select_female);
        select_male = (LinearLayout) findViewById(R.id.select_male);
        select_birthday = (MaterialEditText) findViewById(R.id.select_birthday);
        editButton = (IconicsButton) findViewById(R.id.button_edit_submit);

        input_male = (OptimusTextView) findViewById(R.id.input_male);
        input_female = (OptimusTextView) findViewById(R.id.input_female);
        text_birthday = (OptimusTextView) findViewById(R.id.text_birthday);
        iconics_male = (IconicsImageView) findViewById(R.id.iconics_male);
        iconics_female = (IconicsImageView) findViewById(R.id.iconics_female);
        dialogLoading = dialogLoaging();

        select_birthday.setFocusable(false);
        Commons.setTypeFace(editButton);
        editButton.setText(getContext().getString(R.string.continue_));

        dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());

        setBirthdayListener();
        setGenderMaleListener();
        setGenderFemaleListener();
        registerUserListener();

    }

    public void setUser(User user, PersonalInfo personalInfo) {
        this.user = user;
        this.personalInfo = personalInfo;
    }

    private void updateBirthdayAndGender() {
        if (validateGender()) {
            if (validateBirthday()) {
                UserResource auth = new UserResource();
                Log.d("dateSend", "user.getBirthday() " + user.getBirthday());
                Log.d("dateSend", "user.getGender() " + user.getGender());
                auth.updateBirthdayAndGender(user, personalInfo.getUuid(), DBController.getControler().getUser(getContext(), personalInfo.getUuid()).get_etag(), callbackEditInfo);
                setLoading(true);
            }
        }
    }

    private Callback<GenericResponse> callbackEditInfo = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            if (response.isSuccessful()) {
                Log.d(TAG, "Editar onResponse");
                getNewDateUser();
            } else {
                Log.e(TAG, "Editar onResponse error -->" + response.errorBody().contentType());
                setLoading(false);
                // errorSnackBar(getString(R.string.user_register_fails));
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            Log.d(TAG, "Editar onFailure : " + t.getMessage());
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
                        DBController.getControler().createUser(getContext(), user);
                        coordinatorLayout.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<com.optimussoftware.api.response.user.User> call, Throwable t) {
                        Log.d("Get User Error", t.toString());
                    }
                };
        userResource.get(personalInfo.getUuid(), callbackUser);
    }

    private void setLoading(boolean loading) {
        if (loading) {
            dialogLoading.show();
            editButton.setEnabled(false);
        } else {
            dialogLoading.dismiss();
            editButton.setEnabled(true);
        }
    }

    private boolean validateGender() {
        if (TextUtils.isEmpty(user.getGender())) {
            select_male.requestFocus();
            //genderSpinner.setError(getString(R.string.enter_gender));
            //  genderSpinner.setError("");
            errorSnackBar(getContext().getString(R.string.enter_gender));
            return false;
        }
        return true;
    }

    private boolean validateBirthday() {
        if (user.getBirthday() == null) {
            //  select_birthdayec.requestFocus();
            //birthdayText.setError(getString(R.string.enter_birthday));
            select_birthday.setError("");
            errorSnackBar(getContext().getString(R.string.enter_birthday));
            return false;
        }
        return true;
    }

    public void setBirthdayListener() {
        select_birthday.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTime now = DateTime.now();
                final MonthAdapter.CalendarDay minDate = new MonthAdapter.CalendarDay(now.getYear() - 100, now.getMonthOfYear(), now.getDayOfMonth());
                final MonthAdapter.CalendarDay maxDate = new MonthAdapter.CalendarDay(now.getYear() - 10, now.getMonthOfYear(), now.getDayOfMonth());
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment();
                cdp.setDateRange(minDate, maxDate);
                cdp.setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                    @Override
                    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                        dialog.setDateRange(minDate, minDate);
                        Calendar calendar = Calendar.getInstance();
                        calendar.clear();
                        calendar.set(year, monthOfYear, dayOfMonth);

                        Date date = calendar.getTime(); //Lo que el user nos da;

                        SimpleDateFormat dt1 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.ENGLISH);
                        String dateToStr = dt1.format(date);
                        Log.d("date", "dateToStr " + dateToStr);//<- esto es lo que mandas;
                        //Wed, 20 Jun 1990 00:00:00 GMT
                        Date strToDate = null;
                        try {
                            strToDate = dt1.parse(dateToStr);
                            Log.d("date", "strToDate " + strToDate);
                        } catch (ParseException e) {
                            Log.e("date", "ParseException date str " + dateToStr);
                            Log.e("date", "ParseException date Exc " + e.getMessage());
                            e.printStackTrace();
                        }

                        Date date1 = Commons.parseDateGMT(dateToStr);
                        Log.d("date", "date1 " + date1);

                        select_birthday.setText(dateFormat.format(date));
                        select_birthday.setHint("");
                        user.setBirthday(strToDate);
                        text_birthday.setVisibility(View.VISIBLE);
                    }
                });
                cdp.show(((AppCompatActivity) getContext()).getSupportFragmentManager(), FRAG_TAG_DATE_PICKER);

            }
        });

    }

    public void setGenderFemaleListener() {
        select_female.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setGender("female");
                input_male.setTextColor(ContextCompat.getColor(getContext(), R.color.colorEnabled));
                input_female.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                iconics_female.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                iconics_male.setColor(ContextCompat.getColor(getContext(), R.color.colorEnabled));
            }
        });
    }

    public void setGenderMaleListener() {
        select_male.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setGender("male");
                input_female.setTextColor(ContextCompat.getColor(getContext(), R.color.colorEnabled));
                input_male.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                iconics_male.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                iconics_female.setColor(ContextCompat.getColor(getContext(), R.color.colorEnabled));
            }
        });
    }

    public void registerUserListener() {
        editButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "User id: " + user.get_id() + " gender: " + user.getGender() + " birthday: " + user.getBirthday());
                updateBirthdayAndGender();
            }
        });
    }

    private MaterialDialog dialogLoaging() {
        return new MaterialDialog.Builder(getContext())
                .theme(Theme.LIGHT)
                .content(R.string.please_wait)
                .autoDismiss(false)
                .progress(true, 0)
                .build();
    }

    private void errorSnackBar(String mjs) {
        OptimusSnackBar.snackBarTop(getContext(), coordinatorLayout,
                mjs, OptimusSnackBar.LENGTH_LONG, true).show();
    }
}
