package com.optimussoftware.boohos.invitations;

import android.content.Context;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.widget.AppInviteDialog;
import com.optimussoftware.boohos.widget.BaseActivity;

/**
 * Created by guerra on 22/11/16.
 * InvitationFacebook
 */

public class InvitationFacebook {

    private static String TAG = "InvitationFacebook";

    public static void invite(CallbackManager callbackManager, Context context) {
        String appLinkUrl, previewImageUrl;
        appLinkUrl = "https://fb.me/1798905770418256";
        previewImageUrl = "https://www.google.co.ve/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png";

        if (AppInviteDialog.canShow()) {
            AppInviteContent content = new AppInviteContent.Builder()
                    .setApplinkUrl(appLinkUrl)
                    .setPreviewImageUrl(previewImageUrl)
                    .build();
            //AppInviteDialog.show((BaseActivity) context, content);
            AppInviteDialog appInviteDialog = new AppInviteDialog((BaseActivity) context);
            appInviteDialog.registerCallback(callbackManager, new FacebookCallback<AppInviteDialog.Result>() {
                @Override
                public void onSuccess(AppInviteDialog.Result result) {
                    Log.e(TAG, "invite onSuccess");
                }

                @Override
                public void onCancel() {
                    Log.e(TAG, "invite onCancel");
                }

                @Override
                public void onError(FacebookException e) {
                    Log.e(TAG, "invite onError " + e.getMessage());
                }
            });

            appInviteDialog.show(content);
        }
    }
}
