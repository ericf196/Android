package com.optimussoftware.boohos.rating;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.optimussoftware.api.ReviewResource;
import com.optimussoftware.api.response.GenericResponse;
import com.optimussoftware.db.Review;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.data.PersonalInfo;
import com.optimussoftware.boohos.util.Commons;
import com.optimussoftware.boohos.widget.OptimusTextView;
import com.optimussoftware.image.Factory;
import com.optimussoftware.image.SizeImage;
import com.rengwuxian.materialedittext.MaterialEditText;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by guerra on 13/07/16.
 * Dialog custom for comment
 */
public class RatingControl {

    //Interface
    private OnCreateReview onCreateReview;

    public void setOnCreateReview(OnCreateReview onCreateReview) {
        this.onCreateReview = onCreateReview;
    }

    private RatingBar rating_bar_dialog;
    private LinearLayout linear_content_rating;
    private ProgressBar progress_rating;
    private MaterialEditText edit_mensaje_dialog;
    private View buttonPositive;
    private View buttonNegative;

    //Strings
    private String actionCreate;
    private String actionEdit;
    private String actionCancel;
    private String positiveText;
    private String negativeText;

    //Class
    private Context context;
    private String TAG = "RatingControl";
    private MaterialDialog dialog;

    //petitions
    private ReviewResource reviewResource;
    private PersonalInfo personalInfo;

    public RatingControl(Context context) {
        this.context = context;
        reviewResource = new ReviewResource();
        actionCreate = context.getString(R.string.button_send);
        actionEdit = context.getString(R.string.button_send);
        actionCancel = context.getString(android.R.string.cancel);
        personalInfo = new PersonalInfo((Activity) context);
    }

    private void initDialog(final boolean create, final String idReview, final String eTag, final String idAdvertising) {
        //set configurations dialog
        dialog = new MaterialDialog.Builder(context)
                .theme(Theme.LIGHT)
                .customView(R.layout.rating_dialog, true)
                .autoDismiss(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (create) {
                            actionPositiveCreate(idAdvertising);
                        } else {
                            actionPositiveEdit(idReview, eTag, idAdvertising);
                        }
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        actionNegative();
                    }
                })
                .negativeText(getNegativeText())
                .positiveText(getPositiveText()).build();
        initControlsView();
    }

    private void initControlsView() {
        //controls dialog
        CircleImageView image_dialog = (CircleImageView) dialog.getCustomView().findViewById(R.id.image_dialog);
        OptimusTextView text_name_rating = (OptimusTextView) dialog.getCustomView().findViewById(R.id.text_name_rating);
        OptimusTextView text_location_rating = (OptimusTextView) dialog.getCustomView().findViewById(R.id.text_location_rating);
        linear_content_rating = (LinearLayout) dialog.getCustomView().findViewById(R.id.linear_content_rating);
        progress_rating = (ProgressBar) dialog.getCustomView().findViewById(R.id.progress_rating);
        edit_mensaje_dialog = (MaterialEditText) dialog.getCustomView().findViewById(R.id.edit_mensaje_dialog);
        rating_bar_dialog = (RatingBar) dialog.getCustomView().findViewById(R.id.rating_bar_dialog);
        buttonNegative = dialog.getActionButton(DialogAction.NEGATIVE);
        buttonPositive = dialog.getActionButton(DialogAction.POSITIVE);

        Factory.setImage(image_dialog, context, personalInfo.getPicture(), new SizeImage(context, 80, 80));
        text_name_rating.setText(personalInfo.getFull_name());
        text_location_rating.setText(personalInfo.getLocation());

        //validations
        buttonPositive.setEnabled(false);//disable when don't have comment
        rating_bar_dialog.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                buttonPositive.setEnabled(v > 0 &&
                        edit_mensaje_dialog.getText().toString().trim().length() > 0);//enable when has comment
            }
        });
        edit_mensaje_dialog.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                buttonPositive.setEnabled(s.toString().trim().length() > 0 &&
                        rating_bar_dialog.getRating() > 0);//enable when has comment
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void actionPositiveCreate(String advertising_id) {
        final String user_id = personalInfo.getUuid();
        final Integer vote = (int) rating_bar_dialog.getRating();
        final String comment = edit_mensaje_dialog.getText().toString();
        final String lang = Commons.getLang();

        final Review review = new Review();
        review.setUser_id(user_id);
        review.setAdvertising_id(advertising_id);
        review.setVote(vote);
        review.setComment(comment);
        review.setLang(lang);

        Callback<GenericResponse> cbReview = new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                Log.d(TAG, "cbReviewCreate onResponse --->");
                if (response.message().equals("CREATED")) {
                    review.setFirst_name(personalInfo.getFirst_name());
                    review.setLast_name(personalInfo.getLast_name());
                    review.setProfile_photo(personalInfo.getPicture());
                    review.set_id(response.body().get_id());
                    review.set_etag(response.body().get_etag());
                    review.setCreated(Commons.parseDateGMT(response.body().get_created()));
                    review.setUpdated(Commons.parseDateGMT(response.body().get_updated()));
                    Log.d("lary", "Review creado getUser_id " + review.getUser_id());
                    Log.d("lary", "Review creado es " + review.getVote() + " voto " +
                            review.getComment() + " <- Comentario " + response.body().get_created());
                    if (onCreateReview != null) {
                        onCreateReview.onCreateReviewSucess(review);
                    }
                } else {
                    Log.e(TAG, "cbReviewCreate onResponse ---> Code: " + response.code());
                    if (onCreateReview != null) {
                        onCreateReview.onCreateReviewError();
                    }
                }
                setProgressLoad(false);
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {
                Log.e(TAG, "cbReviewCreate onFailure ---> " + t.toString());
                if (onCreateReview != null) {
                    onCreateReview.onCreateReviewError();
                }
                setProgressLoad(false);
                dialog.dismiss();
            }
        };
        //post create review
        setProgressLoad(true);
        reviewResource.create(review, cbReview);
    }

    private void actionPositiveEdit(String idReview, String eTag, String advertising_id) {
        final String user_id = personalInfo.getUuid();
        final Integer vote = (int) rating_bar_dialog.getRating();
        final String comment = edit_mensaje_dialog.getText().toString();
        final String lang = Commons.getLang();

        final Review review = new Review();
        review.setUser_id(user_id);
        review.setAdvertising_id(advertising_id);
        review.setVote(vote);
        review.setComment(comment);
        review.setLang(lang);

        Callback<GenericResponse> cbReview = new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                Log.d(TAG, "cbReviewEdit onResponse --->");
                if (response.code() == 200) {
                    review.setFirst_name(personalInfo.getFirst_name());
                    review.setLast_name(personalInfo.getLast_name());
                    review.setProfile_photo(personalInfo.getPicture());
                    review.set_id(response.body().get_id());
                    review.set_etag(response.body().get_etag());
                    review.setCreated(Commons.parseDateGMT(response.body().get_created()));
                    review.setUpdated(Commons.parseDateGMT(response.body().get_updated()));
                    Log.d("lary", "Review Editado getUser_id " + review.getUser_id());
                    Log.d(TAG, "Review editado es " + review.getVote() + " voto " +
                            review.getComment() + " <- Comentario " + response.body().get_created());
                    if (onCreateReview != null) {
                        onCreateReview.onCreateReviewSucess(review);
                    }
                } else {
                    Log.e(TAG, "cbReviewEdit onResponse ---> Code: " + response.code());
                    if (onCreateReview != null) {
                        onCreateReview.onCreateReviewError();
                    }
                }
                setProgressLoad(false);
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {
                Log.e(TAG, "cbReviewEdit onFailure ---> " + t.toString());
                if (onCreateReview != null) {
                    onCreateReview.onCreateReviewError();
                }
                setProgressLoad(false);
                dialog.dismiss();
            }
        };
        //post create review
        setProgressLoad(true);
        reviewResource.edit(idReview, eTag, review, cbReview);
    }

    private void setProgressLoad(boolean show){
        linear_content_rating.setEnabled(!show);
        linear_content_rating.setClickable(!show);
        buttonPositive.setEnabled(!show);
        buttonPositive.setClickable(!show);
        buttonNegative.setEnabled(!show);
        buttonNegative.setClickable(!show);

        if(show){
            progress_rating.setVisibility(View.VISIBLE);
        }else {
            progress_rating.setVisibility(View.GONE);
        }
    }

    private void actionNegative() {
        rating_bar_dialog.setRating(0);
        edit_mensaje_dialog.setText("");
        dialog.dismiss();
    }

    public void show(boolean create, String idReview, String eTag, String idAdvertising,
                     float rating, String comment) {
        //Comment null if create is true
        if (create) {
            setPositiveText(actionCreate);
            setNegativeText(actionCancel);
            initDialog(true, null, null, idAdvertising);
            rating_bar_dialog.setRating(rating);
            edit_mensaje_dialog.setText("");
            dialog.show();
        } else {
            //this part to edit
            setPositiveText(actionEdit);
            setNegativeText(actionCancel);
            initDialog(false, idReview, eTag, idAdvertising);
            rating_bar_dialog.setRating(rating);
            edit_mensaje_dialog.setText(comment);
            dialog.show();
        }
    }

    private String getPositiveText() {
        return positiveText;
    }

    private void setPositiveText(String positiveText) {
        this.positiveText = positiveText;
    }

    private String getNegativeText() {
        return negativeText;
    }

    private void setNegativeText(String negativeText) {
        this.negativeText = negativeText;
    }

    public interface OnCreateReview {
        void onCreateReviewSucess(Review review);

        void onCreateReviewError();
    }

}
