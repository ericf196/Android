package com.optimussoftware.boohos.share;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideo;
import com.facebook.share.model.ShareVideoContent;
import com.facebook.share.widget.ShareDialog;


/**
 * Created by guerra on 14/06/16.
 */
public class ShareFacebook {

    private ShareDialog shareDialog;

    public ShareFacebook(Context context, CallbackManager callbackManager, ShareDialog shareDialog) {
        this.shareDialog = shareDialog;
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Log.d("lary", "FacebookCallback onSuccess");
            }

            @Override
            public void onCancel() {
                Log.d("lary", "FacebookCallback onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("lary", "FacebookCallback onError " + error.getMessage());
            }
        });
    }

    public ShareLinkContent shareLink(String title, String description, String urlLink) {
        return new ShareLinkContent.Builder()
                .setContentTitle(title)
                .setContentDescription(description)
                .setContentUrl(Uri.parse(urlLink))
                .build();
    }

    /**
     * El tamaño de las fotos debe ser inferior a 12 MB.
     */
    public SharePhotoContent sharePhoto(Bitmap image) {
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(image)
                .build();
        return new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();
    }

    public SharePhotoContent sharePhoto(Uri uriImagen) {
        SharePhoto photo = new SharePhoto.Builder()
                .setImageUrl(uriImagen)
                .build();
        return new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();
    }

    /**
     * El tamaño de los videos debe ser inferior a 12 MB.
     */
    public ShareVideoContent shareVideo(Uri uriVideo) {
        ShareVideo video = new ShareVideo.Builder()
                .setLocalUrl(uriVideo)
                .build();
        return new ShareVideoContent.Builder()
                .setVideo(video)
                .build();
    }

    /* para compartir en modo backgroung se deben pedir permisos extas
    private void sharePhotoToFacebook() {
        List<String> permissionNeeds = Arrays.asList("publish_actions");
        LoginManager manager = LoginManager.getInstance();
        manager.logInWithPublishPermissions(this, permissionNeeds);
        manager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("share", "onSuccess");
            }

            @Override
            public void onCancel() {
                Log.d("share", "onCancel");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d("share", "onCancel");
            }
        });
    }*/

    /*public void shareContent(final ShareContent content) {
        Log.d("share", "shareContent");
        ShareApi.share(content, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Log.d("share", "onSuccess getPostId = " + result.getPostId());
                Toast.makeText(activity, activity.getString(R.string.share_post_success), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel() {
                Log.d("share", "onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(activity, error.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("share", "onError " + error.getMessage());
                Log.e("share", "onError " + error.toString());
                //Toast.makeText(activity, "onError getMessage" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }*/

    public void showShareDialogLink(ShareContent shareContent) {
        Log.d("larysa", "showShareDialogLink");
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            shareDialog.show(shareContent);
        }
    }

    public void showShareDialogPhoto(ShareContent shareContent) {
        Log.d("larysa", "showShareDialogPhoto");
        if (ShareDialog.canShow(SharePhotoContent.class)) {
            Log.d("larysa", "true");
            shareDialog.show(shareContent);
        } else {
            Log.d("larysa", "false");
        }
    }

    public void showShareDialogVideo(ShareContent shareContent) {
        Log.d("larysa", "showShareDialogVideo");
        if (ShareDialog.canShow(ShareVideoContent.class)) {
            shareDialog.show(shareContent);
        }
    }

// test open graph
//    public void shareOpenExample1() {
//        ShareOpenGraphObject object = new ShareOpenGraphObject.Builder()
//                .putString("og:type", "article")
//                .putString("og:title", "Hola mi titulo de mi historia")
//                .putString("og:description", "Descripcion de mi historia.")
//                .putString("og:url", " http://integration.optimus-software.com/herme/images/img_b0fc6aba-8af7-4381-9196-6eed192d0d24.jpg")
//                .putString("og:image", " http://integration.optimus-software.com/herme/images/img_b0fc6aba-8af7-4381-9196-6eed192d0d24.jpg")
//                .build();
//        ShareOpenGraphAction action = new ShareOpenGraphAction.Builder()
//                .setActionType("hermeapp:taste")
//                .putObject("article", object)
//                .build();
//        ShareOpenGraphContent content = new ShareOpenGraphContent.Builder()
//                .setPreviewPropertyName("article")
//                .setAction(action)
//                .build();
//        shareDialog.show(content);
//    }

//    public ShareOpenGraphContent shareOpenExample() {
//        ShareOpenGraphObject object = new ShareOpenGraphObject.Builder()
//                .putString("og:type", "fitness.course")//fitness.course
//                .putString("og:title", "Sample Course")
//                .putString("og:description", "This is a sample course.")
//                .putInt("fitness:duration:value", 100)
//                .putString("fitness:duration:units", "s")
//                .putInt("fitness:distance:value", 12)
//                .putString("fitness:distance:units", "km")
//                .putInt("fitness:speed:value", 5)
//                .putString("fitness:speed:units", "m/s")
//                .build();
//        ShareOpenGraphAction action = new ShareOpenGraphAction.Builder()
//                .setActionType("fitness.runs")//fitness.runs
//                .putObject("fitness:course", object)
//                .build();
//        ShareOpenGraphContent content = new ShareOpenGraphContent.Builder()
//                .setPreviewPropertyName("fitness:course")
//                .setAction(action)
//                .build();
//        return content;
//    }
//
//    public ShareOpenGraphContent shareOpen() {
//        ShareOpenGraphObject object = null;
//        ShareOpenGraphAction action = null;
//        ShareOpenGraphContent content = null;
//        try {
//            object = new ShareOpenGraphObject.Builder()
//                    .putString("og:type", "product")
//                    .putString("og:title", "Nombre producto")
//                    .build();
//        } catch (Exception e) {
//            Log.e("lary", "ShareOpenGraphObject " + e.toString());
//        }
//        try {
//            action = new ShareOpenGraphAction.Builder()
//                    .setActionType("og:product")
//                    .putObject("product", object)
//                    .build();
//        } catch (Exception e) {
//            Log.e("lary", "ShareOpenGraphAction " + e.toString());
//        }
//        try {
//            content = new ShareOpenGraphContent.Builder()
//                    .setPreviewPropertyName("product")
//                    .setAction(action)
//                    .build();
//        } catch (Exception e) {
//            Log.e("lary", "Object " + e.toString());
//        }
//
//        return content;
//    }
//
//    public ShareOpenGraphContent shareOpenBook() {// Create an object
//        ShareOpenGraphObject object = new ShareOpenGraphObject.Builder()
//                .putString("og:type", "books.book")
//                .putString("og:title", "A Game of Thrones")
//                .putString("og:description", "In the frozen wastes to the north of Winterfell, sinister and supernatural forces are mustering.")
//                .putString("books:isbn", "0-553-57340-3")
//                .build();
//
//        // Create an action
//        ShareOpenGraphAction action = new ShareOpenGraphAction.Builder()
//                .setActionType("books.reads")
//                .putObject("book", object)
//                .build();
//
//        // Create the content
//        ShareOpenGraphContent content = new ShareOpenGraphContent.Builder()
//                .setPreviewPropertyName("book")
//                .setAction(action)
//                .build();
//
//        return content;
//    }

}
