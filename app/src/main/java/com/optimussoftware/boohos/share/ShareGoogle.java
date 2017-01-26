package com.optimussoftware.boohos.share;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.google.android.gms.plus.PlusShare;

/**
 * Created by guerra on 20/06/16.
 */
public class ShareGoogle extends ShareNative {

    private Context context;

    public ShareGoogle(Context context) {
        super(context);
        this.context = context;
    }

    public void shareText(String text) {
        this.shareText(text, null);
    }

    public void shareText(String text, String link) {
        PlusShare.Builder share = new PlusShare.Builder(context);
        share.setType("text/plain");
        share.setText(text);//no null
        if (link != null) {
            share.setContentUrl(Uri.parse(link));
        }
        context.startActivity(share.getIntent());
    }

    public void sharePhoto(String text, Uri uri) {
        this.sharePhoto(text, uri, null);
    }

    public void sharePhoto(String text, Uri uri, String link) {
        if (getWhatsappInstalled()) {
            PlusShare.Builder share = new PlusShare.Builder(context);
            share.setType("image/*");
            if (text != null) {
                share.setText(text);
            }
            share.addStream(uri);//no null
            if (link != null) {
                share.setContentUrl(Uri.parse(link));
            }
            context.startActivity(share.getIntent());
        } else {
            toastAppNoInstalled(context);
            Log.e("ShareGoogle", "ShareGoogle app no installed");
        }
    }

    public void shareVideo(String text, Uri uri) {
        this.shareVideo(text, uri, null);
    }

    public void shareVideo(String text, Uri uri, String link) {
        if (getWhatsappInstalled()) {
            PlusShare.Builder share = new PlusShare.Builder(context);
            share.setType("video/*");
            if (text != null) {
                share.setText(text);
            }
            share.addStream(uri);//no null
            if (link != null) {
                share.setContentUrl(Uri.parse(link));
            }
            context.startActivity(share.getIntent());
        } else {
            toastAppNoInstalled(context);
            Log.e("ShareGoogle", "ShareGoogle app no installed");
        }
    }

    public Boolean getWhatsappInstalled() {
        return appInstalled(context, APP_GOOGLE_PLUS);
    }
}
