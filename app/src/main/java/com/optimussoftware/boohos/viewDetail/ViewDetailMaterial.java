package com.optimussoftware.boohos.viewDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Toast;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.facebook.CallbackManager;
import com.facebook.share.widget.ShareDialog;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.view.IconicsButton;
import com.optimussoftware.api.CampaignResource;
import com.optimussoftware.api.LocationResource;
import com.optimussoftware.api.ReviewResource;
import com.optimussoftware.api.response.location.Location;
import com.optimussoftware.api.response.review.NewReview.ReviewResponse;
import com.optimussoftware.api.response.review.ReviewCheck.ReviewCheck;
import com.optimussoftware.boohos.util.OptimusShowCase;
import com.optimussoftware.db.Advertising;
import com.optimussoftware.db.AdvertisingImage;
import com.optimussoftware.db.Image;
import com.optimussoftware.db.NotificationAdvertising;
import com.optimussoftware.db.Review;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.data.Constants;
import com.optimussoftware.boohos.data.DBController;
import com.optimussoftware.boohos.data.PersonalInfo;
import com.optimussoftware.boohos.listview.adapters.AdvertisingViewAdapter;
import com.optimussoftware.boohos.rating.RatingControl;
import com.optimussoftware.boohos.review.ItemReview;
import com.optimussoftware.boohos.review.ReviewActivity;
import com.optimussoftware.boohos.share.Share;
import com.optimussoftware.boohos.util.Commons;
import com.optimussoftware.boohos.widget.AdvertisingActionButtonView;
import com.optimussoftware.boohos.widget.BaseActivity;
import com.optimussoftware.boohos.widget.OptimusDate;
import com.optimussoftware.boohos.widget.OptimusTextView;
import com.optimussoftware.boohos.widget.ShareSocialButtonView;
import com.optimussoftware.image.Factory;
import com.optimussoftware.image.SizeImage;

import org.joda.time.DateTime;
import org.joda.time.Period;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by guerra on 05/07/16.
 * Advertising ViewGeneral
 */
public class ViewDetailMaterial extends BaseActivity implements
        BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.slider)
    SliderLayout slider;
    @BindView(R.id.text_name)
    OptimusTextView text_name;
    @BindView(R.id.text_name_location_short)
    OptimusTextView text_name_location_short;
    @BindView(R.id.text_name_campaign)
    OptimusTextView text_name_campaign;
    @BindView(R.id.text_description)
    OptimusTextView text_description;
    @BindView(R.id.linear_campaing)
    LinearLayout linear_campaing;
    @BindView(R.id.recycler_campaign)
    RecyclerView recycler_campaign;
    @BindView(R.id.linear_campaing1)
    LinearLayout linear_campaing1;
    @BindView(R.id.recycler_campaign1)
    RecyclerView recycler_campaign1;
    @BindView(R.id.image_main)
    CircleImageView image_main;
    @BindView(R.id.rating_indicator)
    RatingBar rating_indicator;
    @BindView(R.id.text_cant_score)
    OptimusTextView text_cant_score;
    @BindView(R.id.text_cant_vote)
    OptimusTextView text_cant_vote;
    @BindView(R.id.image_profile)
    CircleImageView image_profile;
    @BindView(R.id.rating_review)
    RatingBar rating_review;
    @BindView(R.id.text_comment_this)
    OptimusTextView text_comment_this;
    @BindView(R.id.linear_comment_this)
    LinearLayout linear_comment_this;
    @BindView(R.id.linear_my_comment)
    LinearLayout linear_my_comment;
    @BindView(R.id.linear_list)
    LinearLayout linear_list;
    @BindView(R.id.text_reviews)
    OptimusTextView text_reviews;
    @BindView(R.id.button_show_more)
    IconicsButton button_show_more;
    @BindView(R.id.image_location)
    CircleImageView image_location;
    @BindView(R.id.text_name_location)
    OptimusTextView text_name_location;
    @BindView(R.id.text_description_location)
    OptimusTextView text_description_location;
    @BindView(R.id.text_schedule_location)
    OptimusTextView text_schedule_location;
    @BindView(R.id.text_phone_location)
    OptimusTextView text_phone_location;
    @BindView(R.id.text_day_exp)
    OptimusTextView text_day;
    @BindView(R.id.text_hour_exp)
    OptimusTextView text_hour;
    @BindView(R.id.text_minute_exp)
    OptimusTextView text_minute;
    @BindView(R.id.share_button_social)
    ShareSocialButtonView share_social_button;
    @BindView(R.id.advertising_action_button)
    AdvertisingActionButtonView advertisingActionButton;

    private static final String TAG = ViewDetailMaterial.class.getSimpleName();
    private Share share;
    private CallbackManager callbackManager;
    private Advertising advertising;
    private PersonalInfo personalInfo;
    private ItemReview mItemReview;
    private String notification_id = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.REVIEW_RESULT) {
                setReviewOnActivityResult(data);
            } else {
                Log.d(TAG, "NO REVIEW_RESULT");
            }
        } else {
            Log.d(TAG, "NO RESULT_OK");
        }
    }

    private void setReviewOnActivityResult(Intent data) {
        Log.d(TAG, "REVIEW_RESULT");
        if (data != null) {
            if (data.getSerializableExtra("mReview") != null) {
                Review newReview = (Review) data.getSerializableExtra("mReview");
                Log.d(TAG, "mReview updateUi " + newReview.getComment());
                if (mItemReview != null) {
                    linear_my_comment.removeAllViews();
                    mItemReview.updateUi(newReview);
                    linear_my_comment.addView(mItemReview.getViewReview());
                } else {
                    Log.d(TAG, "mItemReview NULL");
                }
            } else {
                Log.d(TAG, "DATA NULL");
            }
        } else {
            Log.d(TAG, "DATA NULL");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail_material);
        ButterKnife.bind(this);
        startBase(toolbar);
        initToolbar(CommunityMaterial.Icon.cmd_arrow_left);
        init();
        callbackManager = CallbackManager.Factory.create();
        personalInfo = new PersonalInfo(ViewDetailMaterial.this);
        share = new Share(this, callbackManager, new ShareDialog(this));


        if (getIntent().getSerializableExtra("notification") != null) {
            NotificationAdvertising notificationAdvertising = (NotificationAdvertising) getIntent().getSerializableExtra("notification");
            notificationAdvertising.setViewed(true);
            Log.i(TAG, "notification advertising --> " + notificationAdvertising.getViewed());
            notification_id = notificationAdvertising.get_id();
            DBController.getControler().createAdvertisingNotification(getBaseContext(), notificationAdvertising);
            List<NotificationAdvertising> notificationAdvertisingList = DBController.getControler().getNotificationAdvertisingList(getBaseContext(), notificationAdvertising.getAdvertising_id());
            for (int i = 0; i < notificationAdvertisingList.size(); i++) {
                if (notificationAdvertisingList.get(i).getDate().compareTo(new Date()) < 0) {
                    notificationAdvertisingList.get(i).setViewed(true);
                    DBController.getControler().createAdvertisingNotification(getBaseContext(), notificationAdvertisingList.get(i));
                }
            }
        }

        if (getIntent().getSerializableExtra("item") != null) {
            advertising = (Advertising) getIntent().getSerializableExtra("item");
            // advertising_id = "AVc_Mt7VbEyhSJa54OIe";
            Log.i(TAG, advertising.get_id() + " " + advertising.getName());
            setView();
        }
    }

    @Override
    public void onBackPressed() {
        if (share_social_button.isShareOpen()) {
            share_social_button.animarLayoutDown(this);
        } else {
            super.onBackPressed();
            Intent intent = new Intent();
            intent.putExtra("id", notification_id);
            setResult(Constants.NOTIFICATION_VIEWED, intent);
        }
    }

    private void setView() {
        text_name.setText(advertising.getName());
        text_description.setText(advertising.getDescription());
        rating_indicator.setRating(advertising.getAcum_votes());
        text_cant_score.setText(String.valueOf(advertising.getCount_votes()));
        text_cant_vote.setText(String.valueOf(advertising.getAcum_votes()));
        Factory.setImage(image_profile, ViewDetailMaterial.this,
                personalInfo.getPicture(), new SizeImage(40, 40));
        setActionAdvertising();
        setImages();
        setComment();
        setCampaign();
        setSimilarOfferts();
    }

    private void setActionAdvertising() {
        advertisingActionButton.getActionListener(advertising, personalInfo, true);
        advertisingActionButton.initIconButton(advertising, personalInfo);
        advertisingActionButton.getActionShare(share, advertising, share_social_button);
        advertisingActionButton.setPaddingIcon(5);
    }

    private void init() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("id", notification_id);
                setResult(Constants.NOTIFICATION_VIEWED, intent);
                finish();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        slider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    private void setImages() {
        slider.setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        Commons.getWidthDisplay(ViewDetailMaterial.this)));

        List<AdvertisingImage> advertisingImage = DBController.getControler().getAdvertisingImageList(getBaseContext(), advertising.get_id());
        //Log.d("lary", "insert size " + advertisingImage.size());

        for (int i = 0; i < advertisingImage.size(); i++) {
            Image image = DBController.getControler().getImage(getBaseContext(), advertisingImage.get(i).getImage_id());
            //Log.d("lary", "------------ -- " + i + " -- ----------");
            //Log.d("lary", "insert imagen " + image.getName());
            //Log.d("lary", "insert imagen " + Constants.URL_WEB + image.getFile());
            DefaultSliderView sliderView = new DefaultSliderView(this);
            sliderView.image(Constants.URL_WEB + image.getFile())
                    .setScaleType(BaseSliderView.ScaleType.CenterInside)
                    .setOnSliderClickListener(this);
            sliderView.bundle(new Bundle());
            sliderView.getBundle().putString("extra", Constants.URL_WEB + image.getFile());
            slider.addSlider(sliderView);
        }
        slider.stopAutoCycle();
        slider.addOnPageChangeListener(this);
        //slider.setPresetTransformer(SliderLayout.Transformer.Background2Foreground);
        slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        slider.setCustomIndicator((PagerIndicator) findViewById(R.id.custom_indicator));
        try {
            slider.setCurrentPosition(0);
        } catch (Exception e) {
            Log.e(TAG, "Exception setCurrentPosition " + e);
        }
        //slider.setCustomAnimation(new DescriptionAnimation());
        //slider.setDuration(6000);
    }

    private void setComment() {
        //todo
//        linear_my_comment.setVisibility(View.GONE);
//        linear_comment_this.setVisibility(View.VISIBLE);
        //todo
        ReviewResource reviewResource = new ReviewResource();
        mItemReview = new ItemReview(ViewDetailMaterial.this);
        //check if has review
        Review reviewCheck = new Review();
        reviewCheck.setUser_id(personalInfo.getUuid());
        reviewCheck.setAdvertising_id(advertising.get_id());
        Callback<ReviewCheck> checkCallback = new Callback<ReviewCheck>() {
            @Override
            public void onResponse(Call<ReviewCheck> call, Response<ReviewCheck> response) {
                Log.d(TAG, "onResponse checkReviewCallback --> " + response.body().getUserId());
                if (response.body().getUserId() != null) {
                    mItemReview.updateUi(com.optimussoftware.api.core.Factory.getReviewFromCheck(
                            personalInfo.getFirst_name(), personalInfo.getLast_name(),
                            personalInfo.getPicture(), response.body()));
                    linear_my_comment.addView(mItemReview.getViewReview());

                    linear_my_comment.setVisibility(View.VISIBLE);
                    linear_comment_this.setVisibility(View.GONE);
                } else {
                    linear_my_comment.setVisibility(View.GONE);
                    linear_comment_this.setVisibility(View.VISIBLE);
                    showTutorialReview();
                }
            }

            @Override
            public void onFailure(Call<ReviewCheck> call, Throwable t) {
                Log.e(TAG, "onFailure checkReviewCallback --> " + t.getMessage());
                t.printStackTrace();
            }
        };
        //todo
        reviewResource.checkReview(reviewCheck, checkCallback);
        //todo

        //rating
        final RatingControl ratingControl = new RatingControl(ViewDetailMaterial.this);
        rating_review.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                ratingControl.show(true, null, null, advertising.get_id(), v, null);
            }
        });
        text_comment_this.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ratingControl.show(true, null, null, advertising.get_id(), rating_review.getRating(), null);
            }
        });

        ratingControl.setOnCreateReview(new RatingControl.OnCreateReview() {
            @Override
            public void onCreateReviewSucess(Review review) {
                /*Review mReview = new Review();
                mReview.set_id(personalInfo.getUuid());
                mReview.setProfile_photo(personalInfo.getPicture());
                mReview.setFirst_name(personalInfo.getFirst_name());
                mReview.setLast_name(personalInfo.getLast_name());
                mReview.setVote(review.getVote());
                mReview.setComment(review.getComment());
                mReview.setCreated(review.getCreated());
                mReview.setUpdated(review.getUpdated());*/
                final ItemReview itemReview = new ItemReview(ViewDetailMaterial.this);
                itemReview.updateUi(review);
                linear_my_comment.addView(itemReview.getViewReview());
                linear_my_comment.setVisibility(View.VISIBLE);
                linear_comment_this.setVisibility(View.GONE);
            }

            @Override
            public void onCreateReviewError() {
                Log.e(TAG, "onCreateReviewError");
                //Toast.makeText(ViewDetailMaterial.this, getString(R.string.has_review), Toast.LENGTH_SHORT).show();
                Toast.makeText(ViewDetailMaterial.this, getString(R.string.toast_error), Toast.LENGTH_SHORT).show();
            }
        });

        //getlista
        Callback<ReviewResponse> callbackByAdvertising = new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                Log.d(TAG, "onResponse callbackByAdvertising --> " + response.body().getItems().size());
                int i = 0;
                while (i < 3 && i < response.body().getItems().size()) {
                    if (!personalInfo.getUuid().equals(response.body().getItems().get(i).getUserId().getId())) {
                        final ItemReview itemReview = new ItemReview(ViewDetailMaterial.this);
                        itemReview.updateUi(com.optimussoftware.api.core.Factory.getReviewFromResponse(response.body().getItems().get(i)));
                        linear_list.addView(itemReview.getViewReview());
                    }
                    i++;
                }
                if (linear_list.getChildCount() == 0) {
                    text_reviews.setVisibility(View.GONE);
                } else {
                    text_reviews.setVisibility(View.VISIBLE);
                }
                if (linear_list.getChildCount() > 3) {
                    button_show_more.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                Log.e(TAG, "onFailure callbackByAdvertising --> " + t.getMessage());
                t.printStackTrace();
            }
        };
        //todo
        reviewResource.getReviewByAdvertising(advertising.get_id(), 1, callbackByAdvertising);
        //todo
        linear_my_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startReviewActivity();
            }
        });
        linear_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startReviewActivity();
            }
        });
        button_show_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startReviewActivity();
            }
        });
    }


    public void startReviewActivity() {
        Intent intent = new Intent(this, ReviewActivity.class);
        intent.putExtra("advertising", advertising);
        startActivityForResult(intent, Constants.REVIEW_RESULT);
    }

    public void showTutorialReview() {
        OptimusShowCase optimusShowCase = new OptimusShowCase(this);
        boolean showed = optimusShowCase.getInfoShow().isShowedScreenReviewButton();
        Log.d(TAG, "isShowedScreenFloatingButton() " + showed);
        if (!showed) {
            Log.d(TAG, "Mostro");
            final ShowcaseView showcaseView = optimusShowCase.getShowCaseView(R.id.rating_review,
                    getString(R.string.app_name), getString(R.string.app_name), "ok");
            showcaseView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showcaseView.hide();
                }
            });
            showcaseView.show();
            optimusShowCase.getInfoShow().setShowedScreenReviewButton(true);
        }
    }

    private void setCampaign() {
        CampaignResource campaignResource = new CampaignResource();
        Callback<com.optimussoftware.api.response.campaign.Item> cb = new Callback<com.optimussoftware.api.response.campaign.Item>() {
            @Override
            public void onResponse(Call<com.optimussoftware.api.response.campaign.Item> call, Response<com.optimussoftware.api.response.campaign.Item> response) {
                if (response.body().getLocations().size() > 0) {
                    setLocation(response.body().getLocations().get(0));
                }
                text_name_campaign.setText(response.body().getName());
                setTime(response.body().getFinishDate());
                DBController.getControler().createCampaign(getBaseContext(), com.optimussoftware.api.core.Factory.getCampaignFromResponse(response.body()));
            }

            @Override
            public void onFailure(Call<com.optimussoftware.api.response.campaign.Item> call, Throwable t) {

            }
        };
        campaignResource.getCampaign(advertising.getCampaign_id(), cb);
    }

    private void setTime(String dateEnd) {
        final DateTime dateTimeEnd = OptimusDate.convertStringToDateTime(dateEnd);
        Period period = OptimusDate.diferenceBetewnDateInDay(new DateTime(), dateTimeEnd);
        text_day.setText(String.valueOf(period.getDays()));
        text_hour.setText(String.valueOf(period.getHours()));
        text_minute.setText(String.valueOf(period.getMinutes()));
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Period period = OptimusDate.diferenceBetewnDateInDay(new DateTime(), dateTimeEnd);
                        text_day.setText(String.valueOf(period.getDays()));
                        text_hour.setText(String.valueOf(period.getHours()));
                        text_minute.setText(String.valueOf(period.getMinutes()));
                    }
                });
            }
        }, 0, 60000);
    }

    private void setSimilarOfferts() {
        recycler_campaign.setLayoutManager(new LinearLayoutManager(ViewDetailMaterial.this,
                LinearLayoutManager.HORIZONTAL, false));
        recycler_campaign1.setLayoutManager(new LinearLayoutManager(ViewDetailMaterial.this,
                LinearLayoutManager.HORIZONTAL, false));
        final AdvertisingViewAdapter adapter = new AdvertisingViewAdapter(ViewDetailMaterial.this);
        recycler_campaign.setAdapter(adapter);
        recycler_campaign1.setAdapter(adapter);

        List<Advertising> adverstingsLists = DBController.getControler().getAdvertisingListByCampaign(ViewDetailMaterial.this, advertising.getCampaign_id());
        if (adverstingsLists.size() > 0) {
            for (int i = 0; i < adverstingsLists.size(); i++) {
                if (adverstingsLists.get(i).get_id().equals(advertising.get_id())) {
                    adverstingsLists.remove(i);
                }
            }
            adapter.addAvertingList(adverstingsLists);
            linear_campaing.setVisibility(View.VISIBLE);
            linear_campaing1.setVisibility(View.VISIBLE);
        } /*else {
            AdvertisingResource advertisingResource = new AdvertisingResource();
            Callback<AdvertisingListByCampaign> adverstingsListCallback = new Callback<AdvertisingListByCampaign>() {
                @Override
                public void onResponse(Call<AdvertisingListByCampaign> call, Response<AdvertisingListByCampaign> response) {
                    for (int i = 0; i < response.body().getItems().size(); i++) {
                        DBController.getControler().moreAdvertising(getBaseContext(), response.body().getItems().get(i));
                    }
                    if (response.body().getItems().size() > 0) {
                        adapter.clearAvertingList();
                    }
                    List<Advertising> adverstingsLists = DBController.getControler().getAdvertisingListByCampaign(ViewDetailMaterial.this, advertising.getCampaign_id());
                    if (adverstingsLists.size() > 0) {
                        adapter.addAvertingList(adverstingsLists);
                        linear_campaing.setVisibility(View.VISIBLE);
                        linear_campaing1.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<AdvertisingListByCampaign> call, Throwable t) {

                }
            };
            Log.d(TAG , "AQUI------>");
            advertisingResource.getListAdvertisingByCampaign(advertising.getCampaign_id(), adverstingsListCallback);
        }*/
    }

    private void setLocation(String id_location) {
        LocationResource locationResource = new LocationResource();
        Callback<com.optimussoftware.api.response.location.Location> locationCallback = new Callback<com.optimussoftware.api.response.location.Location>() {
            @Override
            public void onResponse(Call<Location> call, Response<Location> response) {
                setData(response.body());
            }

            @Override
            public void onFailure(Call<Location> call, Throwable t) {

            }
        };
        locationResource.getLocation(id_location, locationCallback);
    }

    private void setData(com.optimussoftware.api.response.location.Location location) {
        Factory.setImage(image_main, ViewDetailMaterial.this,
                Constants.URL_WEB + location.getLogo().getFilename(), new SizeImage(50, 50));
        Factory.setImage(image_location, ViewDetailMaterial.this,
                Constants.URL_WEB + location.getLogo().getFilename(), new SizeImage(40, 40));
        text_name_location_short.setText(location.getName());
        text_name_location.setText(location.getName());
        text_description_location.setText(location.getHome_page());
        String address = location.getCountry() + ", " +
                location.getState() + " " + location.getCity() + ".";
        text_schedule_location.setText(address);
        text_phone_location.setText(location.getTelephone());
    }
}