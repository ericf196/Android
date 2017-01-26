package com.optimussoftware.boohos.listview.holders;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.mikepenz.iconics.view.IconicsImageView;
import com.optimussoftware.db.Review;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.data.PersonalInfo;
import com.optimussoftware.boohos.widget.OptimusDate;
import com.optimussoftware.boohos.widget.OptimusTextView;
import com.optimussoftware.image.Factory;
import com.optimussoftware.image.SizeImage;

import org.joda.time.DateTime;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by guerra on 16/07/16.
 * Item_ list reviews
 */
public class ReviewViewHolder extends RecyclerView.ViewHolder {

    //controls
    @BindView(R.id.image_review)
    ImageView image_review;
    @BindView(R.id.text_name)
    OptimusTextView text_name;
    @BindView(R.id.icon)
    public IconicsImageView icon;
    @BindView(R.id.rating_bar)
    RatingBar rating_bar;
    @BindView(R.id.text_date)
    OptimusTextView text_date;
    @BindView(R.id.text_comment)
    OptimusTextView text_comment;

    private Context context;
    PersonalInfo personalInfo;

    public ReviewViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = itemView.getContext();
        personalInfo = new PersonalInfo((Activity) context);
    }

    public void setReview(Review review) {
        setImage(review.getProfile_photo());
        setName(review.getFirst_name()+" "+review.getLast_name());
        setVote(review.getVote());
        setDate(review.getCreated());
        setComment(review.getComment());
        setEdit(review.getUser_id());
    }

    private void setImage(String url) {
        Factory.setImage(image_review, context, url, new SizeImage(context, 40, 40));
    }

    private void setName(String name) {
        text_name.setText(name);
    }

    private void setDate(Date date) {
        Log.d("OptimusDate", "Review Date -> " + date);
        long dateReview = date.getTime();
        long dateNow = new DateTime().getMillis();
        text_date.setText(OptimusDate.timeAgo(context, dateReview, dateNow));
    }

    private void setVote(int vote) {
        rating_bar.setRating(vote);
    }

    private void setComment(String comment) {
        text_comment.setText(comment);
    }

    public void setEdit(String uid) {

        if (uid.equals(personalInfo.getUuid())) {
            icon.setVisibility(View.VISIBLE);
        } else {
            icon.setVisibility(View.INVISIBLE);
        }
        //todo
        //icon.setVisibility(View.GONE);
    }

}
