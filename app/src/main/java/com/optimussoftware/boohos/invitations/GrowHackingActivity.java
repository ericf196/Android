package com.optimussoftware.boohos.invitations;

import android.Manifest;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.multi.CompositeMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.multi.SnackbarOnAnyDeniedMultiplePermissionsListener;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.invitations.adapters.PagerAdapterGrowHacking;
import com.optimussoftware.boohos.invitations.fragments.IntroGrowHackingFragment;
import com.optimussoftware.boohos.invitations.fragments.ListContactGrowHackingFragment;
import com.optimussoftware.boohos.security.GrowHackingPermissionListener;
import com.optimussoftware.boohos.widget.BaseActivity;
import com.optimussoftware.boohos.widget.OptimusTextView;
import com.optimussoftware.boohos.widget.OptimusViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by leonardojpr on 12/6/16.
 */

public class GrowHackingActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView(R.id.view_pager_main)
    public OptimusViewPager pager;
    @BindView(R.id.text_invite_friend)
    public OptimusTextView txt_invite;
    @BindView(R.id.text_skip)
    public OptimusTextView txt_skip;
    @BindView(R.id.controllers_grow)
    LinearLayout controllers;
    @BindView(android.R.id.content)
    ViewGroup rootView;

    public static final int CONNECT_FRIEND = 1;
    public static final int INVITE_APP = 2;
    public static final int SELECT = 3;


    public int pos = 0;

    private MultiplePermissionsListener allPermissionsListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grow_hackign);
        ButterKnife.bind(this);
        setPermissions();
        startBase(toolbar);
        initToolbar(CommunityMaterial.Icon.cmd_arrow_left);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    private void initViewPager() {
        final PagerAdapterGrowHacking adapter = new PagerAdapterGrowHacking(getSupportFragmentManager());
        adapter.addFragment(IntroGrowHackingFragment.newInstance(0));
        adapter.addFragment(ListContactGrowHackingFragment.newInstance(CONNECT_FRIEND));
        adapter.addFragment(ListContactGrowHackingFragment.newInstance(INVITE_APP));
        this.pager.setPagingEnabled(false);
        this.pager.setAdapter(adapter);

        this.pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pos = position;
                if (position == 0) {
                    controllers.setVisibility(View.GONE);
                } else if (position == CONNECT_FRIEND){
                    controllers.setVisibility(View.VISIBLE);
                    txt_invite.setText(getBaseContext().getString(R.string.add_all));
                } else if(position == INVITE_APP) {
                    txt_invite.setText(getBaseContext().getString(R.string.send_all));
                    controllers.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        txt_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pos < adapter.getCount())
                    pager.setCurrentItem(pos + 1);
            }
        });
    }

    private void setPermissions() {
        MultiplePermissionsListener feedbackViewMultiplePermissionListener =
                new GrowHackingPermissionListener(this);
        allPermissionsListener =
                new CompositeMultiplePermissionsListener(feedbackViewMultiplePermissionListener,
                        SnackbarOnAnyDeniedMultiplePermissionsListener.Builder
                                .with(rootView, R.string.all_permissions_denied_init_activity)
                                .build());

        Dexter.continuePendingRequestsIfPossible(allPermissionsListener);
        if (!Dexter.isRequestOngoing()) {
            Dexter.checkPermissions(allPermissionsListener,
                    Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS);
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
        initViewPager();
    }


    public void permissionDenied(String permission, boolean isPermanentlyDenied) {
        setPermissions();
    }
}
