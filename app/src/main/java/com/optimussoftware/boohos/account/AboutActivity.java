package com.optimussoftware.boohos.account;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;

import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.view.IconicsImageView;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.widget.BaseActivity;
import com.optimussoftware.boohos.widget.FontCache;
import com.optimussoftware.boohos.widget.OptimusTextView;
import com.optimussoftware.image.Factory;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by guerra on 27/07/16.
 * Information about app
 */
public class AboutActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @BindView(R.id.linear_support)
    public LinearLayout linear_support;
    @BindView(R.id.text_support)
    public OptimusTextView text_support;
    @BindView(R.id.icon_support)
    public IconicsImageView icon_support;
    @BindView(R.id.text_cont_support)
    public OptimusTextView text_cont_support;

    @BindView(R.id.linear_terms)
    public LinearLayout linear_terms;
    @BindView(R.id.text_terms)
    public OptimusTextView text_terms;
    @BindView(R.id.icon_terms)
    public IconicsImageView icon_terms;
    @BindView(R.id.text_cont_terms)
    public OptimusTextView text_cont_terms;

    @BindView(R.id.linear_privacy)
    public LinearLayout linear_privacy;
    @BindView(R.id.text_privacy)
    public OptimusTextView text_privacy;
    @BindView(R.id.icon_privacy)
    public IconicsImageView icon_privacy;
    @BindView(R.id.text_cont_privacy)
    public OptimusTextView text_cont_privacy;

    @BindView(R.id.linear_cookies)
    public LinearLayout linear_cookies;
    @BindView(R.id.text_cookies)
    public OptimusTextView text_cookies;
    @BindView(R.id.icon_cookies)
    public IconicsImageView icon_cookies;
    @BindView(R.id.text_cont_cookies)
    public OptimusTextView text_cont_cookies;

    int heightDefault = 0;
    int heightSupport = 0;
    int heightTerms = 0;
    int heightPrivacy = 0;
    int heightCookies = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);
        ButterKnife.bind(this);
        startBase(toolbar);
        initToolbar(CommunityMaterial.Icon.cmd_arrow_left, R.string.about);
        init();
        setClick();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        heightSupport = text_cont_support.getMeasuredHeight();
        heightTerms = text_cont_terms.getMeasuredHeight();
        heightPrivacy = text_cont_privacy.getMeasuredHeight();
        heightCookies = text_cont_cookies.getMeasuredHeight();
        text_cont_support.setHeight(0);
        text_cont_terms.setHeight(0);
        text_cont_privacy.setHeight(0);
        text_cont_cookies.setHeight(0);
    }

    private void init() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setClick() {
        linear_support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnimation(text_cont_support, heightSupport, text_support, icon_support);
            }
        });
        linear_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnimation(text_cont_terms, heightTerms, text_terms, icon_terms);
            }
        });
        linear_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnimation(text_cont_privacy, heightPrivacy, text_privacy, icon_privacy);
            }
        });
        linear_cookies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnimation(text_cont_cookies, heightCookies, text_cookies, icon_cookies);
            }
        });
    }

    private void startAnimation(View view, int height, OptimusTextView textTitle, IconicsImageView icon) {
        if (view.getHeight() == heightDefault) {
            textTitle.setTypeface(FontCache.getTypeface(AboutActivity.this, getString(R.string.font_path_bold)));
            animation(view, heightDefault, height).start();
            icon.setIcon(Factory.getIcon(AboutActivity.this,
                    CommunityMaterial.Icon.cmd_arrow_up_drop_circle_outline,
                    (int) getResources().getDimension(R.dimen.icon_size_xsmall)));
        } else {
            textTitle.setTypeface(FontCache.getTypeface(AboutActivity.this, getString(R.string.font_path_medium)));
            icon.setIcon(Factory.getIcon(AboutActivity.this,
                    CommunityMaterial.Icon.cmd_arrow_down_drop_circle_outline,
                    (int) getResources().getDimension(R.dimen.icon_size_xsmall)));
            animation(view, height, heightDefault).start();
        }
    }

    private AnimatorSet animation(final View view, int heightIni, int heightNew) {
        ValueAnimator slideAnimator = ValueAnimator
                .ofInt(heightIni, heightNew)
                .setDuration(600);
        slideAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                view.getLayoutParams().height = value.intValue();
                view.requestLayout();
            }
        });
        AnimatorSet set = new AnimatorSet();
        set.play(slideAnimator);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        return set;
    }

}
