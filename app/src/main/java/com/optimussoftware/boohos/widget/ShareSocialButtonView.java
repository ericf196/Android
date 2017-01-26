package com.optimussoftware.boohos.widget;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.androidadvance.topsnackbar.TSnackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.optimussoftware.db.Advertising;
import com.optimussoftware.db.Location;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.data.Constants;
import com.optimussoftware.boohos.data.DBController;
import com.optimussoftware.boohos.listview.model.AdvertisingContent;
import com.optimussoftware.boohos.share.Save;
import com.optimussoftware.boohos.share.Share;
import com.optimussoftware.boohos.util.AnimLayoutParams;
import com.optimussoftware.boohos.util.Commons;
import com.optimussoftware.image.Factory;
import com.optimussoftware.image.SizeImage;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by leonardojpr on 9/14/16.
 * ShareSocialButtonView
 */
public class ShareSocialButtonView extends RelativeLayout {

    private final String TAG = ShareSocialButtonView.class.getSimpleName();
    private LinearLayout linear_image_share;
    private ImageView image_advertising_share;
    private CircleImageView image_location;
    private OptimusTextView text_name_share;
    private LinearLayout linear_share;
    private ImageView share_facebook;
    private ImageView share_instagram;
    private ImageView share_twitter;
    private ImageView share_google;
    private ImageView share_whatsapp;
    private RelativeLayout relative_share;
    private boolean shareOpen = false;

    public ShareSocialButtonView(Context context) {
        super(context);
        init();
    }

    public ShareSocialButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ShareSocialButtonView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.share_social_button, this);
        relative_share = (RelativeLayout) findViewById(R.id.share_social_button_relative);
        linear_image_share = (LinearLayout) findViewById(R.id.linear_image_share);
        image_advertising_share = (ImageView) findViewById(R.id.image_advertising_share);
        image_location = (CircleImageView) findViewById(R.id.image_location);
        text_name_share = (OptimusTextView) findViewById(R.id.text_name_share);
        linear_share = (LinearLayout) findViewById(R.id.linear_share);
        linear_share = (LinearLayout) findViewById(R.id.linear_share);
        share_facebook = (ImageView) findViewById(R.id.share_facebook);
        share_instagram = (ImageView) findViewById(R.id.share_instagram);
        share_twitter = (ImageView) findViewById(R.id.share_twitter);
        share_google = (ImageView) findViewById(R.id.share_google);
        share_whatsapp = (ImageView) findViewById(R.id.share_whatsapp);
        relative_share.setClickable(false);
        relative_share.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                animarLayoutDown(getContext());
            }
        });
    }

    private void setData(final Context context, Advertising advertising) {

        Location location = DBController.getControler().getLocation(context, advertising.getLocation_id());
        if (location != null) {
            Factory.setImageWithBlur(image_location, context, Constants.URL_WEB + location.getImage());
        }

        SizeImage sizeImage = new SizeImage((Commons.getWidthDisplay(context) - Commons.dpToPx(context, 40)), (Commons.getWidthDisplay(context) - Commons.dpToPx(context, 40)));
        Factory.setImage(image_advertising_share, context, AdvertisingContent.pathViewDefault(context, advertising), sizeImage);
        text_name_share.setText(advertising.getName());
    }

    public final void getActionShareButton(final Context context, final Share share, final Advertising advertising) {
        setData(context, advertising);
        Log.i(TAG, "getActionShareButton");
        animarLayoutUp(context);
        share_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareSocialButton(context, share, share.FACEBOOK, advertising);
            }
        });

        share_instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareSocialButton(context, share, share.INSTAGRAM, advertising);
            }
        });

        share_twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareSocialButton(context, share, share.TWITTER, advertising);
            }
        });

        share_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareSocialButton(context, share, share.GOOGLE_PLUS, advertising);
            }
        });

        share_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareSocialButton(context, share, share.WHATSAPP, advertising);
            }
        });

    }

    private void shareSocialButton(final Context context, final Share share,
                                   final int social, final Advertising advertising) {
        if (Dexter.isRequestOngoing()) {
            return;
        }
        Dexter.checkPermission(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                Log.d(TAG, "onPermissionGranted");
                if (response.getPermissionName().equals("android.permission.WRITE_EXTERNAL_STORAGE")) {
                    try {
                        if (social == share.GOOGLE_PLUS ||
                                social == share.INSTAGRAM ||
                                social == share.WHATSAPP) {
                            if (!share.appInstalled(social)) {
                                share.toastAppNoInstalled();
                                return;
                            }
                        }

                        Save save = new Save(context);
                        save.setOnSaveMedia(new Save.OnSaveMedia() {
                            @Override
                            public void onSaveStart() {

                            }

                            @Override
                            public void onSaveEnd(File file) {
                                share.setContent(social, advertising.getDescription(),
                                        Uri.fromFile(file), null);
                            }

                            @Override
                            public void onSaveError() {

                            }
                        });
                        save.saveImageFromView(linear_image_share);
                    } catch (Exception e) {
                        Log.e(TAG, "Exception shareSocialButton: " + e);
                    }
                }
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                Log.d(TAG, "onPermissionDenied");
                TSnackbar snackbar = OptimusSnackBar.snackBarTop(context,
                        linear_image_share, context.getString(R.string.permissions_request),
                        OptimusSnackBar.LENGTH_LONG, true);
                snackbar.setAction(R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent myAppSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.parse("package:" + context.getPackageName()));
                        myAppSettings.addCategory(Intent.CATEGORY_DEFAULT);
                        myAppSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(myAppSettings);
                    }
                });
                snackbar.show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, final PermissionToken token) {
                Log.d(TAG, "onPermissionRationaleShouldBeShown");
                new MaterialDialog.Builder(context)
                        .theme(Theme.LIGHT)
                        .title(R.string.permissions_title)
                        .content(R.string.permissions_request)
                        .negativeText(android.R.string.cancel)
                        .positiveText(android.R.string.ok)
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                                token.cancelPermissionRequest();
                            }
                        })
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                                token.continuePermissionRequest();
                            }
                        })
                        .onNeutral(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE);


    }

    private void animarLayoutUp(Context context) {
        relative_share.setVisibility(View.VISIBLE);
        AnimLayoutParams animLayoutParams = new AnimLayoutParams();
        AnimatorSet animatorSet = new AnimatorSet();
        //ValueAnimator value = animLayoutParams.animarLayoutHeight(linear_share, Commons.getHeightDisplay(context) / 4, 300);
        ValueAnimator valueLinear = animLayoutParams.animarLayoutHeight(linear_share, Commons.getHeightDisplay(context), 300);
        ValueAnimator valueHeightImage = animLayoutParams.animarLayoutHeight(linear_image_share, (Commons.getWidthDisplay(context) - Commons.dpToPx(context, 40)), 300);
        ValueAnimator valueWidthImage = animLayoutParams.animarLayoutWidth(linear_image_share, (Commons.getWidthDisplay(context) - Commons.dpToPx(context, 40)), 300);
        relative_share.setClickable(true);
        relative_share.setBackground(context.getResources().getDrawable(R.drawable.background_shadow));
        animatorSet.playTogether(valueLinear, valueHeightImage, valueWidthImage);
        animatorSet.start();
        setShareOpen(true);
        Log.i(TAG, "animarLayoutUp");
    }

    public void animarLayoutDown(Context context) {
        AnimLayoutParams animLayoutParams = new AnimLayoutParams();
        AnimatorSet animatorSet = new AnimatorSet();
        ValueAnimator value = animLayoutParams.animarLayoutHeight(linear_share, Commons.dpToPx(context, 0), 300);
        relative_share.setClickable(false);
        relative_share.setBackground(null);
        animatorSet.playTogether(value);
        animatorSet.start();
        setShareOpen(false);
    }

    public boolean isShareOpen() {
        return shareOpen;
    }

    public void setShareOpen(boolean shareOpen) {
        this.shareOpen = shareOpen;
    }

}

