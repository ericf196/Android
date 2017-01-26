package com.optimussoftware.boohos.account;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.odoo.widgets.slider.SliderView;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.config.IntroSliderItems;
import com.optimussoftware.boohos.data.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AppIntro extends AppCompatActivity {

    @BindView(R.id.sliderView)
    SliderView sliderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_intro);
        ButterKnife.bind(this);
        trySlider();
    }

    private void trySlider(){
        IntroSliderItems sliderItems = new IntroSliderItems(this);
        if (sliderView != null && !sliderItems.getItems().isEmpty()) {
            sliderView.setItems(getSupportFragmentManager(), sliderItems.getItems());
        } else {
            setResult(Constants.CODE_START_LOGIN);
            finish();
        }
    }
}
