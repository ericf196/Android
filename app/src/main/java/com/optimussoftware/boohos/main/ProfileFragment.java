package com.optimussoftware.boohos.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.optimussoftware.boohos.account.AboutActivity;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.data.PersonalInfo;
import com.optimussoftware.boohos.util.Commons;
import com.optimussoftware.boohos.widget.OptimusTextView;

/**
 * Created by guerra on 20/10/16.
 * Fragment to profile used in menu (MainActivity)
 */

public class ProfileFragment extends Fragment {

    private View rootView;
    private OptimusTextView user_email;
    private OptimusTextView user_phone;
    private OptimusTextView user_gender;
    private OptimusTextView user_birthday;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.profile_fragment, container, false);
        init();
        return rootView;
    }

    private void init() {
        findViews();
        populateInfoProfile();
        setAbout();
    }

    private void findViews() {
        user_email = (OptimusTextView) rootView.findViewById(R.id.user_email);
        user_phone = (OptimusTextView) rootView.findViewById(R.id.user_phone);
        user_gender = (OptimusTextView) rootView.findViewById(R.id.user_gender);
        user_birthday = (OptimusTextView) rootView.findViewById(R.id.user_birthday);
    }

    private void populateInfoProfile() {
        PersonalInfo personalInfo = new PersonalInfo(getActivity());

        Commons.setTextToTextView(user_email, "");
        Commons.setTextToTextView(user_phone, "");
        Commons.setTextToTextView(user_gender, "");
        Commons.setTextToTextView(user_birthday, "");

        Commons.setTextToTextView(user_email, setDateNoNull(personalInfo.getEmail()));
        Commons.setTextToTextView(user_phone, setDateNoNull(personalInfo.getPhone()));
        Commons.setTextToTextView(user_gender, setDateNoNull(personalInfo.getGender()));
        Commons.setTextToTextView(user_birthday, setDateNoNull(personalInfo.getBirthday()));
    }

    private String setDateNoNull(String s) {
        if (s == null || s.isEmpty()) {
            s = getString(R.string.complete_your_profile);
        }
        return s;
    }

    private void setAbout() {
        OptimusTextView text_about = (OptimusTextView) rootView.findViewById(R.id.text_about);
        text_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent);
            }
        });
    }


    public void reinit() {
        populateInfoProfile();
    }

}
