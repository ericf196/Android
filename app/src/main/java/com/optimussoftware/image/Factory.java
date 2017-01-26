package com.optimussoftware.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.data.Constants;
import com.optimussoftware.image.transform.BlurTransform;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

/**
 * Created by william.castillo@optimus-software.com on 06/07/16.
 */
public class Factory {

    /*Get Icon from community material font*/
    public static IconicsDrawable getIcon(Context context, CommunityMaterial.Icon icon, int size) {
        return new IconicsDrawable(context, icon).sizeDp(size).color(0xFFFFFFFF);
    }

    public static IconicsDrawable getIconWB(Context context, CommunityMaterial.Icon icon, int size) {
        return new IconicsDrawable(context, icon).sizeDp(size).color(0xFFFFFFFF)
                .backgroundColorRes(R.color.colorPrimary);
    }

    private static Drawable getDefaultImage(Context context, int mesuare) {
        return getIcon(context, CommunityMaterial.Icon.cmd_image_broken, mesuare);
    }

    private static Drawable getDefaultImage(Context context) {
        return getIcon(context, CommunityMaterial.Icon.cmd_image_broken, 90);
    }

    public static void setImageWithBlur(final ImageView img, final Context context, final String url, final SizeImage sizeImage) {
        if (url != null) {
            Callback diskfailCallback = new Callback() {
                @Override
                public void onSuccess() {
                }

                @Override
                public void onError() {
                    Picasso.with(context)
                            .load(url)
                            .error(getDefaultImage(context, Math.max(sizeImage.width, sizeImage.height)))
                            .transform(new BlurTransform(context))
                            .into(img, new Callback() {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onError() {
                                    Log.d("Picasso", "Could not fetch image");
                                }
                            });
                }
            };
            Picasso.with(context)
                    .load(url)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .error(getDefaultImage(context, Math.max(sizeImage.width, sizeImage.height)))
                    .transform(new BlurTransform(context))
                    .into(img, diskfailCallback);
        }
    }

    public static void setImageWithBlur(final ImageView img, final Context context, final String url) {
        if (url != null) {
            Callback diskfailCallback = new Callback() {
                @Override
                public void onSuccess() {
                }

                @Override
                public void onError() {
                    Picasso.with(context)
                            .load(url)
                            .error(getDefaultImage(context))
                            .transform(new BlurTransform(context))
                            .into(img, new Callback() {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onError() {
                                    Log.d("Picasso", "Could not fetch image");
                                }
                            });
                }
            };
            Picasso.with(context)
                    .load(url)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .error(getDefaultImage(context))
                    .transform(new BlurTransform(context))
                    .into(img, diskfailCallback);
        }
    }



    public static void setImage(final ImageView img, final Context context, final String url, final SizeImage sizeImage) {
        if (url != null) {
            Callback diskfailCallback = new Callback() {
                @Override
                public void onSuccess() {
                }

                @Override
                public void onError() {
                    Picasso.with(context)
                            .load(url)
                            .error(getDefaultImage(context, Math.max(sizeImage.width, sizeImage.height)))
                            .into(img, new Callback() {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onError() {
                                    Log.d("Picasso", "Could not fetch image");
                                }
                            });
                }
            };
            if (url != null && url.compareTo("") != 0)
                Picasso.with(context)
                        .load(url)
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .error(getDefaultImage(context, Math.max(sizeImage.width, sizeImage.height)))
                        .resize(sizeImage.width, sizeImage.height)
                        .centerCrop()
                        .into(img, diskfailCallback);
        }
    }

    public static void setImageSelfHost(ImageView img, Context context, String url, SizeImage sizeImage) {
        if (url != null) {
            setImage(img, context, Constants.URL_WEB + url, sizeImage);
        }
    }

    public static void setImage(ImageView img, Context context, Uri uri, SizeImage sizeImage) {
        Picasso.with(context)
                .load(uri)
                .error(getDefaultImage(context, Math.max(sizeImage.width, sizeImage.height)))
                .resize(sizeImage.width, sizeImage.height)
                .centerCrop()
                .into(img);
    }

    public static Drawable getRotateDrawable(Context context, final Bitmap b, final float angle) {
        return new BitmapDrawable(context.getResources(), b) {
            @Override
            public void draw(final Canvas canvas) {
                canvas.save();
                canvas.rotate(angle, b.getWidth() / 2, b.getHeight() / 2);
                super.draw(canvas);
                canvas.restore();
            }
        };
    }
}