package com.optimussoftware.boohos.review;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.optimussoftware.api.ReviewResource;
import com.optimussoftware.api.response.review.NewReview.Item;
import com.optimussoftware.api.response.review.NewReview.ReviewResponse;
import com.optimussoftware.api.response.review.ReviewCheck.ReviewCheck;
import com.optimussoftware.db.Advertising;
import com.optimussoftware.db.Review;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.data.PersonalInfo;
import com.optimussoftware.boohos.listview.adapters.ReviewAdapter;
import com.optimussoftware.boohos.widget.BaseActivity;
import com.optimussoftware.boohos.widget.EndlessRecyclerViewScrollListener;
import com.optimussoftware.boohos.widget.OptimusTextView;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by guerra on 18/07/16.
 * List review y advertising
 */
public class ReviewActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView(R.id.linear_my_comment)
    LinearLayout linear_my_comment;
    @BindView(R.id.rating_indicator)
    RatingBar rating_indicator;
    @BindView(R.id.text_cant_score)
    OptimusTextView text_cant_score;
    @BindView(R.id.text_cant_vote)
    OptimusTextView text_cant_vote;
    @BindView(R.id.recycler_review)
    RecyclerView recycler_review;

    private PersonalInfo personalInfo;
    private Advertising advertising;

    private ReviewAdapter reviewAdapter;
    private ReviewResource reviewResource;
    private Callback<ReviewResponse> cb;

    private int page = 1;
    private float total = 0.0f;
    private float maxResults = 0.0f;
    private boolean canGetNext = true;
    private ItemReview itemReview = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_activity);
        ButterKnife.bind(this);
        startBase(toolbar);
        initToolbar(CommunityMaterial.Icon.cmd_arrow_left);
        if (getIntent().getSerializableExtra("advertising") != null) {
            advertising = (Advertising) getIntent().getSerializableExtra("advertising");
            Log.i("ReviewActivity", advertising.get_id() + " " + advertising.getName());
        }
        init();
        initReview();
    }

    private void init() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAndBackData();
            }
        });
        personalInfo = new PersonalInfo(this);
        reviewResource = new ReviewResource();
    }

    private void initReview() {
        //check if has review
        setMyComment();
        //set grafics
        setDateRating();
        //recycler
        setRecyclerReview();
    }

    private void setMyComment() {
        Review reviewCheck = new Review();
        reviewCheck.setUser_id(personalInfo.getUuid());
        reviewCheck.setAdvertising_id(advertising.get_id());
        Callback<ReviewCheck> checkCallback = new Callback<ReviewCheck>() {
            @Override
            public void onResponse(Call<ReviewCheck> call, Response<ReviewCheck> response) {
                Log.d("ReviewActivity", "onResponse checkReviewCallback --> " + response.body().getUserId());
                if (response.body().getUserId() != null) {
                    itemReview = new ItemReview(ReviewActivity.this);
                    itemReview.updateUi(com.optimussoftware.api.core.Factory.getReviewFromCheck(
                            personalInfo.getFirst_name(), personalInfo.getLast_name(),
                            personalInfo.getPicture(), response.body()));
                    linear_my_comment.addView(itemReview.getViewReview());
                    linear_my_comment.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ReviewCheck> call, Throwable t) {
                Log.e("ReviewActivity", "onFailure checkReviewCallback --> " + t.getMessage());
            }
        };
        reviewResource.checkReview(reviewCheck, checkCallback);
    }

    private void setDateRating() {
        rating_indicator.setRating(advertising.getAcum_votes());
        text_cant_score.setText(String.valueOf(advertising.getCount_votes()));
        text_cant_vote.setText(String.valueOf(advertising.getAcum_votes()));
    }

    private void setRecyclerReview() {
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(ReviewActivity.this,
                LinearLayoutManager.VERTICAL, false);

        recycler_review.setLayoutManager(mLayoutManager);
        reviewAdapter = new ReviewAdapter(new ReviewResponse().getItems());
        recycler_review.setAdapter(reviewAdapter);
        recycler_review.addOnScrollListener(new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                if (page <= Math.ceil((total / maxResults))) {
                    if (canGetNext) {
                        canGetNext = false;
                        Log.d("ReviewActivity", "next " + page);
                        reviewResource.getReviewByAdvertising(advertising.get_id(), page, cb);
                        //reviewResource.list(page, advertising_id, cb1);
                    }
                    reviewAdapter.setLoading(true);
                } else {
                    reviewAdapter.setLoading(false);
                }
                Log.d("ReviewActivity", "onLoadMore next " + page);
            }
        });

        cb = new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                Log.d("ReviewActivity", "onResponse callbackByAdvertising --> " + response.body().getItems().size());
                canGetNext = true;
                List<Item> itemList = response.body().getItems();
                for (int i = 0; i < response.body().getItems().size(); i++) {
                    if (response.body().getItems().get(i).getUserId().getId().equals(personalInfo.getUuid())) {
                        itemList.remove(i);
                    }
                }
                reviewAdapter.add(itemList);
                page = response.body().getMeta().getPage() + 1;
                total = response.body().getMeta().getTotal();
                maxResults = response.body().getMeta().getMaxResults();
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                Log.e("ReviewActivity", "onFailure callbackByAdvertising --> " + t.getMessage());
                canGetNext = true;
            }
        };
        reviewResource.getReviewByAdvertising(advertising.get_id(), page, cb);
    }


    @Override
    public void onBackPressed() {
        finishAndBackData();
    }

    private void finishAndBackData() {
        if (itemReview != null) {
            Intent intent = new Intent();
            intent.putExtra("mReview", itemReview.getItemReview());
            setResult(Activity.RESULT_OK, intent);
            finish();
        } else {
            finish();
        }
    }
}


