package com.optimussoftware.boohos.widget;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Toast;

import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.Iconics;
import com.mikepenz.iconics.context.IconicsLayoutInflater;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.util.PreferenceManager;
import com.optimussoftware.image.Factory;

import java.util.List;


/**
 * Created by william.castillo@optimus-software.com on 29/06/16.
 * BaseActivity
 */

public class BaseActivity extends AppCompatActivity {

    private PreferenceManager preferenceManager = null;
    private String authToken = null;
    private String user_id = null;
    private ActionBar actionBar;
    private String titleToolbar;
    private String subTitleToolbar;
    private CommunityMaterial.Icon iconToolbar;
    public Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory(getLayoutInflater(), new IconicsLayoutInflater(getDelegate()));
        super.onCreate(savedInstanceState);
        Iconics.init(getApplicationContext());
        Iconics.registerFont(new CommunityMaterial());
        //Iconics.registerFont(new CustomFont());
    }

    public void startBase(Toolbar toolbar) {
        this.toolbar = toolbar;
        titleToolbar = getString(R.string.app_name);
        subTitleToolbar = null;
        iconToolbar = CommunityMaterial.Icon.cmd_menu;
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            actionBar = getSupportActionBar();
        }
    }

    public Spannable setColorTextToolbar(String str) {
        Spannable text = new SpannableString(str);
        text.setSpan(new ForegroundColorSpan(Color.WHITE), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return text;
    }


    public void initToolbar() {
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle(setColorTextToolbar(titleToolbar));
            if (subTitleToolbar != null) {
                actionBar.setSubtitle(setColorTextToolbar(subTitleToolbar));
            }
            toolbar.setNavigationIcon(Factory.getIcon(this, iconToolbar, 20));
        }
    }

    public void iniTabs(TabLayout tabLayout, List<String> datos) {
        tabLayout.setVisibility(View.VISIBLE);
        for (String s : datos) {
            tabLayout.addTab(tabLayout.newTab().setText(s));
        }
    }

    public void initToolbar(int title_id) {
        titleToolbar = getString(title_id);
        initToolbar();
    }

    public void initToolbar(int title_id, int subtitle_id) {
        titleToolbar = getString(title_id);
        subTitleToolbar = getString(subtitle_id);
        initToolbar();
    }

    public void initToolbar(CommunityMaterial.Icon icon) {
        iconToolbar = icon;
        initToolbar();
    }

    public void initToolbar(CommunityMaterial.Icon icon, int title_id) {
        iconToolbar = icon;
        titleToolbar = getString(title_id);
        initToolbar();
    }

    public void initToolbar(CommunityMaterial.Icon icon, int title_id, int subtitle_id) {
        iconToolbar = icon;
        titleToolbar = getString(title_id);
        subTitleToolbar = getString(subtitle_id);
        initToolbar();
    }

    public PreferenceManager getPreferenceManager() {
        if (preferenceManager == null) {
            preferenceManager = new PreferenceManager(this);
        }
        return preferenceManager;
    }

    public void showToast(String msj) {
        Toast.makeText(this, msj, Toast.LENGTH_LONG).show();
    }

    public void showToast(int msj) {
        Toast.makeText(this, msj, Toast.LENGTH_LONG).show();
    }

    public String getAuthToken() {
        if (authToken == null)
            authToken = getIntent().getStringExtra("authToken");
        return authToken;
    }

    public String getUser_id() {
        if (user_id == null)
            user_id = getIntent().getStringExtra("user_id");
        return user_id;
    }

    public void propage(Intent intent) {
        if (intent != null) {
            intent.putExtra("authToken", getAuthToken());
            intent.putExtra("user_id", getUser_id());
        }
    }

}
