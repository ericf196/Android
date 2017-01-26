package com.optimussoftware.boohos.share;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.optimussoftware.boohos.util.Commons;


/**
 * Created by guerra on 18/06/16.
 * ShareInstagram and copy text to clipboard
 */
public class ShareInstagram extends ShareNative {

    private Context context;

    public ShareInstagram(Context context) {
        super(context);
        this.context = context;
    }

    /**
     * Sharing an image
     * REQUIREMENT	VALUE
     * Photo Format	jpeg, gif, png
     */

    public void sharePhoto(String text, Uri uri) {
        /*image/jpeg
        * image/png
        * image/gif*/
        if (getInstagramInstalled()) {
            shareBinaryContent(TYPE_IMAGE, uri, APP_INSTAGRAM);
            if (text != null) {
                Commons.copyClipboard(context, "ShareInstagramPhoto", text);
            }
        } else {
            toastAppNoInstalled(context);
            Log.e("Share", "ShareInstagram app no installed");
        }
    }

    /**
     * Sharing a video
     * REQUIREMENT	VALUE
     * Minimum Duration	3 seconds
     * Maximum Duration	10 minutes
     * Video Format	mkv, mp4
     * Minimum Dimensions	640x640 pixels
     */

    public void shareVideo(String text, Uri uri) {
        /*video/mp4*/
        /*video/mkv*/
        if (getInstagramInstalled()) {
            shareBinaryContent(TYPE_VIDEO, uri, APP_INSTAGRAM);
            if (text != null) {
                Commons.copyClipboard(context, "ShareInstagramVideo", text);
            }
        } else {
            toastAppNoInstalled(context);
            Log.e("Share", "ShareInstagram app no installed");
        }
    }

    private Boolean getInstagramInstalled() {
        return appInstalled(context, APP_INSTAGRAM);
    }

}
