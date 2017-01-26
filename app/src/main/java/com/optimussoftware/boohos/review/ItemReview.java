package com.optimussoftware.boohos.review;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.mikepenz.iconics.view.IconicsImageView;
import com.optimussoftware.db.Review;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.data.PersonalInfo;
import com.optimussoftware.boohos.rating.RatingControl;
import com.optimussoftware.boohos.widget.OptimusDate;
import com.optimussoftware.boohos.widget.OptimusTextView;
import com.optimussoftware.image.Factory;
import com.optimussoftware.image.SizeImage;

import org.joda.time.DateTime;

/**
 * Created by guerra on 18/07/16.
 * Inflate Items to Review
 */
public class ItemReview {

    private Context context;
    private RatingControl ratingControl;
    private PersonalInfo personalInfo;
    private Review itemReview;

    private View viewReview;
    private ImageView image_review;
    private OptimusTextView text_name;
    private IconicsImageView icon;
    private RatingBar rating_bar;
    private OptimusTextView text_date;
    private OptimusTextView text_comment;

    public ItemReview(final Context context) {
        this.context = context;
        itemReview = new Review();
        personalInfo = new PersonalInfo((Activity) context);
        ratingControl = new RatingControl(context);
        ratingControl.setOnCreateReview(new RatingControl.OnCreateReview() {
            @Override
            public void onCreateReviewSucess(Review review) {
                updateUi(review);
            }

            @Override
            public void onCreateReviewError() {
                Toast.makeText(context, context.getString(R.string.toast_error), Toast.LENGTH_SHORT).show();
            }
        });
        findView();
    }

    private void findView() {
        viewReview = View.inflate(context, R.layout.review_item, null);
        viewReview.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        image_review = (ImageView) viewReview.findViewById(R.id.image_review);
        text_name = (OptimusTextView) viewReview.findViewById(R.id.text_name);
        icon = (IconicsImageView) viewReview.findViewById(R.id.icon);
        rating_bar = (RatingBar) viewReview.findViewById(R.id.rating_bar);
        text_date = (OptimusTextView) viewReview.findViewById(R.id.text_date);
        text_comment = (OptimusTextView) viewReview.findViewById(R.id.text_comment);
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ratingControl.show(false, itemReview.get_id(),
                        itemReview.get_etag(), itemReview.getAdvertising_id(),
                        itemReview.getVote(), itemReview.getComment());
            }
        });
    }

    public void updateUi(Review review) {
        setItemReview(review);
        Log.e("ItemReview", "setItemReview updateUi: " + review.getComment());
        Factory.setImage(image_review, context, review.getProfile_photo(), new SizeImage(context, 40, 40));
        String full_name = review.getFirst_name() + " " + review.getLast_name();
        text_name.setText(full_name);
        rating_bar.setRating(review.getVote());
        text_date.setText(OptimusDate.timeAgo(context, review.getUpdated().getTime(), new DateTime().getMillis()));
        text_comment.setText(review.getComment());
        try {
            if (review.getUser_id().equals(personalInfo.getUuid())) {
                icon.setVisibility(View.VISIBLE);
            } else {
                icon.setVisibility(View.INVISIBLE);
            }
        } catch (Exception e) {
            Log.e("ItemReview", "Exception updateUi: " + e.toString());
        }
    }

    public void setItemReview(Review itemReview) {
        this.itemReview = itemReview;
    }

    public Review getItemReview() {
        return itemReview;
    }

    public View getViewReview() {
        return viewReview;
    }
}
