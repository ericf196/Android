package com.optimussoftware.boohos.share;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.optimussoftware.boohos.util.Commons;

/**
 * Created by guerra on 20/06/16.
 * ShareWhatsapp and copy text to clipboard
 */
public class ShareWhatsapp extends ShareNative {

    private Context context;

    public ShareWhatsapp(Context context) {
        super(context);
        this.context = context;
    }

    public void shareText(String text) {
        this.shareText(text, null);
    }

    public void shareText(String text, String link) {
        if (getWhatsappInstalled()) {
            if (link == null) {
                shareTextContent(text, APP_WHATSAPP);
            } else {
                shareTextContent(text + " " + link, APP_WHATSAPP);
            }
        } else {
            toastAppNoInstalled(context);
            Log.e("Share", "ShareWhatsapp app no installed");
        }
    }

    public void sharePhoto(String text, Uri uri) {
        if (getWhatsappInstalled()) {
            shareBinaryContent(TYPE_IMAGE, uri, APP_WHATSAPP);
            if (text != null) {
                Commons.copyClipboard(context, "ShareWhatsappPhoto", text);
            }
        } else {
            toastAppNoInstalled(context);
            Log.e("Share", "ShareWhatsapp app no installed");
        }
    }

    public void shareVideo(String text, Uri uri) {
        if (getWhatsappInstalled()) {
            shareBinaryContent(TYPE_VIDEO, uri, APP_WHATSAPP);
            if (text != null) {
                Commons.copyClipboard(context, "ShareWhatsappVideo", text);
            }
        } else {
            toastAppNoInstalled(context);
            Log.e("Share", "ShareWhatsapp app no installed");
        }
    }

    public Boolean getWhatsappInstalled() {
        return appInstalled(context, APP_WHATSAPP);
    }

}
