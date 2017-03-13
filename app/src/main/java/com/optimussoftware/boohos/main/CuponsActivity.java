package com.optimussoftware.boohos.main;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.optimussoftware.boohos.R;

public class CuponsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cupons);

        FragmentManager fragmentManager= getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        CuponsFiveFragment cuponsThreeFragment = new CuponsFiveFragment();
        //CuponsFourFragment cuponsThreeFragment = new CuponsFourFragment();
        //CuponsThreeFragment cuponsThreeFragment = new CuponsThreeFragment();
        //CuponsTwoFragment cuponsThreeFragment = new CuponsTwoFragment();
        //CuponsOneFragment cuponsThreeFragment = new CuponsOneFragment();

        fragmentTransaction.add(R.id.fragment_cupon_container, cuponsThreeFragment);
        fragmentTransaction.commit();

    }
}
