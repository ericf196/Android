package com.optimussoftware.boohos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appinvite.AppInvite;
import com.google.android.gms.appinvite.AppInviteInvitationResult;
import com.google.android.gms.appinvite.AppInviteReferral;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.mikepenz.iconics.view.IconicsButton;
import com.optimussoftware.boohos.widget.BaseActivity;
import com.optimussoftware.boohos.widget.OptimusTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by guerra on 26/11/16.
 * WelcomeActivity
 */

public class WelcomeActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener {


    @BindView(R.id.deep_link_text)
    OptimusTextView textDeepLink;
    @BindView(R.id.invitation_id_text)
    OptimusTextView textIdDeepLink;
    @BindView(R.id.btn_deep_link)
    IconicsButton btnDeepLink;

    private static final String TAG = "WelcomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);
        ButterKnife.bind(this);
        btnDeepLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(AppInvite.API)
                .build();

        boolean autoLaunchDeepLink = false;
        AppInvite.AppInviteApi.getInvitation(mGoogleApiClient, this, autoLaunchDeepLink)
                .setResultCallback(
                        new ResultCallback<AppInviteInvitationResult>() {
                            @Override
                            public void onResult(@NonNull AppInviteInvitationResult result) {
                                if (result.getStatus().isSuccess()) {
                                    Intent intent = result.getInvitationIntent();
                                    String deepLink = AppInviteReferral.getDeepLink(intent);
                                    String invitationId = AppInviteReferral.getInvitationId(intent);

                                    Log.d(TAG, "Found Referral 1: " + invitationId + ":" + deepLink);
                                    textDeepLink.setText(getString(R.string.deep_link_fmt, deepLink));
                                    textDeepLink.setText(deepLink);
                                } else {
                                    Log.d(TAG, "getInvitation: no deep link found.");
                                }
                            }
                        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        if (AppInviteReferral.hasReferral(intent)) {
            processReferralIntent(intent);
        }
    }

    private void processReferralIntent(Intent intent) {
        String invitationId = AppInviteReferral.getInvitationId(intent);
        String deepLink = AppInviteReferral.getDeepLink(intent);

        Log.d(TAG, "Found Referral2: " + invitationId + ":" + deepLink);
        textDeepLink.setText(getString(R.string.deep_link_fmt, deepLink));
        textIdDeepLink.setText(getString(R.string.invitation_id_fmt, invitationId));
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services Error: " + connectionResult.getErrorCode(),
                Toast.LENGTH_SHORT).show();
    }
}
