package com.optimussoftware.boohos.invitations.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mikepenz.iconics.Iconics;
import com.mikepenz.iconics.view.IconicsButton;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.invitations.GrowHackingActivity;

/**
 * Created by leonardojpr on 12/6/16.
 */

public class IntroGrowHackingFragment extends Fragment{

    private static final String TAG = IntroGrowHackingFragment.class.getSimpleName();
    private static final String INDEX = "index";
    private int index = 0;

    IconicsButton iconicsButton;

    public static IntroGrowHackingFragment newInstance(int index) {

        // Instantiate a new fragment
        IntroGrowHackingFragment fragment = new IntroGrowHackingFragment();

        // Save the parameters
        Bundle bundle = new Bundle();
        bundle.putInt(INDEX, index);
        fragment.setArguments(bundle);
        fragment.setRetainInstance(true);

        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.intro_grow_hacking, container, false);
        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.index = (getArguments() != null) ? getArguments().getInt(INDEX)
                : -1;

        iconicsButton = (IconicsButton) getView().findViewById(R.id.button_continue);
        iconicsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((GrowHackingActivity)getActivity()).pager.setCurrentItem(1);
            }
        });
    }

}
