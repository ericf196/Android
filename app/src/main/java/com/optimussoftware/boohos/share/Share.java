package com.optimussoftware.boohos.share;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.share.widget.ShareDialog;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by guerra on 29/06/16.
 * Functions and dialogs to share
 */

public class Share {

    //class
    private String TAG = "Share";
    private Context context;
    private ShareFacebook shareFacebook;
    private ShareGoogle shareGoogle;
    private ShareInstagram shareInstagram;
    private ShareTwitter shareTwitter;
    private ShareWhatsapp shareWhatsapp;

    //variables
    public final int FACEBOOK = 0;
    public final int GOOGLE_PLUS = 1;
    public final int INSTAGRAM = 2;
    public final int TWITTER = 3;
    public final int WHATSAPP = 4;

    public Share(Context context, CallbackManager callbackManager, ShareDialog shareDialog) {
        this.context = context;
        initShareFunctions(callbackManager, shareDialog);
    }

    private void initShareFunctions(CallbackManager callbackManager, ShareDialog shareDialog) {
        //Save save = new Save(context);
        shareFacebook = new ShareFacebook(context, callbackManager, shareDialog);
        shareGoogle = new ShareGoogle(context);
        shareInstagram = new ShareInstagram(context);
        shareTwitter = new ShareTwitter(context);
        shareWhatsapp = new ShareWhatsapp(context);
    }

    public void setContent(int app, final String text, final Uri uri, final String link) {
        //app obligarorio
        //text sino tiene imagen o video obligarotio
        //uri obligatorio solo para instagram
        //link opcional
        if (app == FACEBOOK) {
            if (uri != null) {
                if (isImage(uri)) {
                    shareFacebook.showShareDialogPhoto(shareFacebook.sharePhoto(uri));
                    //todo verificar permisos si se usa en background
                    //shareFacebook.shareContent(shareFacebook.sharePhoto(uri));
                } else if (isVideo(uri)) {
                    shareFacebook.showShareDialogVideo(shareFacebook.shareVideo(uri));
                    //shareFacebook.shareContent(shareFacebook.shareVideo(uri));
                }
            } else {
                if (link != null) {
                    if (text != null) {
                        shareFacebook.showShareDialogLink(shareFacebook.shareLink(text, "", link));
                    } else {
                        shareFacebook.showShareDialogLink(shareFacebook.shareLink("", "", link));
                    }
                } else {
                    Log.e("share", "setContent FACEBOOK with uri null and link null");
                }
            }
        } else if (app == GOOGLE_PLUS) {
            if (uri != null) {
                if (isImage(uri)) {
                    shareGoogle.sharePhoto(text, uri, link);
                } else if (isVideo(uri)) {
                    shareGoogle.shareVideo(text, uri, link);
                }
            } else {
                if (text != null) {
                    shareGoogle.shareText(text, link);
                }
            }
        } else if (app == INSTAGRAM) {
            if (uri != null) {
                if (isImage(uri)) {
                    shareInstagram.sharePhoto(text, uri);
                } else if (isVideo(uri)) {
                    shareInstagram.shareVideo(text, uri);
                } else {
                    Log.e("Share", "setContent INSTAGRAM with uri null");
                }
            }
        } else if (app == TWITTER) {
            if (uri != null) {
                if (isImage(uri)) {
                    if (link == null) {
                        shareTwitter.sharePhoto(text, uri);
                    } else {
                        try {
                            shareTwitter.shareTwiFull(text, uri, new URL(link));
                        } catch (MalformedURLException e) {
                            Log.e("Share", "setContent TWITTER link -> " + e.toString());
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                if (link == null) {
                    shareTwitter.shareText(text);
                } else {
                    try {
                        shareTwitter.shareLink(text, new URL(link));
                    } catch (MalformedURLException e) {
                        Log.e("Share", "setContent TWITTER link -> " + e.toString());
                        e.printStackTrace();
                    }
                }
            }
        } else if (app == WHATSAPP) {
            if (uri != null) {
                if (isImage(uri)) {
                    shareWhatsapp.sharePhoto(text, uri);
                } else if (isVideo(uri)) {
                    shareWhatsapp.shareVideo(text, uri);
                }
            } else {
                if (link == null) {
                    shareWhatsapp.shareText(text);
                } else {
                    shareWhatsapp.shareText(text, link);
                }
            }
        }
    }

    /*public Uri uriFromUrl(String urlImage) {
        Log.d(TAG, "uriFromUrl: " + urlImage);
        return save.uriImageFromUrl(urlImage);
    }*/

    private boolean isImage(Uri uri) {
        boolean image = false;
        String fullPath = uri.getPath();
        int dot = fullPath.lastIndexOf(".");
        String ext = fullPath.substring(dot + 1);
        if (ext.equals("jpg") || ext.equals("png") || ext.equals("gif")) {
            image = true;
        }
        Log.d(TAG, "isImage " + image);
        return image;
    }

    private boolean isVideo(Uri uri) {
        boolean video = false;
        String fullPath = uri.getPath();
        //MimeTypeMap.getFileExtensionFromUrl(uri.getPath()));//devuelve la extension del archivo
        int dot = fullPath.lastIndexOf(".");
        String ext = fullPath.substring(dot + 1);
        if (ext.equals("mkv") || ext.equals("mp4")) {
            video = true;
        }
        Log.d(TAG, "isVideo " + video);
        return video;
    }

    public boolean appInstalled(int social) {
        String appName = "";
        if (social == GOOGLE_PLUS) {
            appName = ShareNative.APP_GOOGLE_PLUS;
        } else if (social == INSTAGRAM) {
            appName = ShareNative.APP_INSTAGRAM;
        } else if (social == WHATSAPP) {
            appName = ShareNative.APP_WHATSAPP;
        }
        return ShareNative.appInstalled(context, appName);
    }

    public void toastAppNoInstalled() {
        ShareNative.toastAppNoInstalled(context);
    }

}
