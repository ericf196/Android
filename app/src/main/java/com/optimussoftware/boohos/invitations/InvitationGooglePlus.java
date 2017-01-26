package com.optimussoftware.boohos.invitations;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.appinvite.AppInvite;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.android.gms.appinvite.AppInviteInvitationResult;
import com.google.android.gms.appinvite.AppInviteReferral;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.widget.BaseActivity;

/**
 * Created by leonardojpr on 16/01/17.
 */

public class InvitationGooglePlus extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = InvitationGooglePlus.class.getSimpleName();
    private static final int REQUEST_INVITE = 0;

    private GoogleApiClient mGoogleApiClient;

    private Button invite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invite_app);

        invite = (Button) findViewById(R.id.invite);

        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new AppInviteInvitation.IntentBuilder("Send app Invite Quicstart invitation")
                        .setMessage(getString(R.string.invitation_message))
                        .setDeepLink(Uri.parse(getString(R.string.invitation_deep_link)))
                        .setEmailHtmlContent("<html><body>" +
                                "<h1>App Invites </h1>"+
                                "<a href=\"%%APPINVITE_LINK_PLACEHOLDER%%\">Install Now!</a>" +
                                "<body></html>")
                        .setEmailSubject("Invite boohos ")
                        .build();
                startActivityForResult(intent, REQUEST_INVITE);
            }
        });

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(AppInvite.API)
                .enableAutoManage(this, this)
                .build();

        boolean autoLaunchDeepLink = true;
        AppInvite.AppInviteApi.getInvitation(mGoogleApiClient, this, autoLaunchDeepLink).setResultCallback(new ResultCallback<AppInviteInvitationResult>() {
            @Override
            public void onResult(@NonNull AppInviteInvitationResult result) {
                Log.d(TAG, "getInvitation onResult: " + result.getStatus());
                if (result.getStatus().isSuccess()) {
                    Intent intent = result.getInvitationIntent();
                    String deepLink = AppInviteReferral.getDeepLink(intent);
                    String invitationId = AppInviteReferral.getInvitationId(intent);
                }
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: requestCode = " + requestCode + ", resultCode = " + resultCode);

        if (requestCode == REQUEST_INVITE) {
            if (resultCode == RESULT_OK) {
                String[] ids = AppInviteInvitation.getInvitationIds(resultCode, data);
                for (String id : ids) {
                    Log.d(TAG, "onActivityResult: sent invitation " + id);
                }
            } else {
                Log.d(TAG, "onActivityResult: sendind invitation failed");
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed : " + connectionResult);
    }
}
