package com.optimussoftware.boohos.share;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;

import com.optimussoftware.boohos.R;

/**
 * Created by guerra on 13/06/16.
 */
public class ShareNative {

    public final String TYPE_TEXT_PLAIN = "text/plain";
    public final String TYPE_TEXT_HTML = "text/html";
    public final String TYPE_IMAGE = "image/*";
    public final String TYPE_VIDEO = "video/*";
    //public static  final String APP_FACEBOOK = "com.facebook.katana";
    public static  final String APP_GOOGLE_PLUS = "com.google.android.apps.plus";
    public static  final String APP_INSTAGRAM = "com.instagram.android";
    //public static  final String APP_TWITTER = "com.twitter.android";
    public static  final String APP_WHATSAPP = "com.whatsapp";

    private Context context;

    public ShareNative(Context context) {
        this.context = context;
    }

    public void shareTextContent(String text, String app) {
        Intent share = new Intent();
        share.setAction(Intent.ACTION_SEND);
        share.putExtra(Intent.EXTRA_TEXT, text);
        share.setType(TYPE_TEXT_PLAIN);
        if (app != null) {
            share.setPackage(app);
        }
        context.startActivity(Intent.createChooser(share, context.getString(R.string.share_with)));
    }

    public void shareBinaryContent(String type, Uri uri, String app) {
        Intent share = new Intent();
        share.setAction(Intent.ACTION_SEND);
        share.putExtra(Intent.EXTRA_STREAM, uri);
        share.setType(type);
        if (app != null) {
            share.setPackage(app);
        }
        context.startActivity(Intent.createChooser(share, context.getString(R.string.share_with)));
    }

    public void shareContent(String subject, String content, String type) {
        shareContent(subject, content, type, null, null);
    }

    public void shareContent(String subject, String content, String type, Uri uri, String app) {
        Intent share = new Intent();
        share.setAction(Intent.ACTION_SEND);
        share.putExtra(Intent.EXTRA_SUBJECT, subject);
        share.putExtra(Intent.EXTRA_TEXT, content);
        share.setType(type);
        if (uri != null) {
            share.putExtra(Intent.EXTRA_STREAM, uri);
        }
        if (app != null) {
            share.setPackage(app);
        }
        context.startActivity(Intent.createChooser(share, context.getString(R.string.share_with)));
    }

    public static boolean appInstalled(Context context, String appName) {
        try {
            PackageManager pm = context.getPackageManager();
            pm.getPackageInfo(appName, PackageManager.GET_META_DATA);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("share", "app " + appName + " not Installed");
            return false;
        }
    }

    public static void toastAppNoInstalled(Context context) {
        Toast.makeText(context, context.getString(R.string.app_no_installed), Toast.LENGTH_SHORT).show();
    }


}
