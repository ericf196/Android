package com.optimussoftware.boohos;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.view.IconicsImageView;
import com.optimussoftware.api.InterestResource;
import com.optimussoftware.api.asynctasks.GetInterestTask;
import com.optimussoftware.api.response.commons.Logo;
import com.optimussoftware.api.response.user.User;
import com.optimussoftware.api.response.user.Wish;
import com.optimussoftware.boohos.data.DBController;
import com.optimussoftware.boohos.gridview.adapters.CategoryInterestViewAdapter;
import com.optimussoftware.boohos.widget.BaseActivity;
import com.optimussoftware.db.Interest;
import com.optimussoftware.db.UserInterest;
import com.optimussoftware.image.Factory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Callback;

public class UserInterestActivity extends BaseActivity {

    public static int CREATE = 1;
    public static int REMOVE = 2;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbarProgress)
    ProgressBar progressBar;
    @BindView(R.id.interest_grid)
    RecyclerView grid;
    @BindView(R.id.interest_tag_view)
    CardView interestTagView;
    @BindView(R.id.icon_interest)
    IconicsImageView iconInterest;
    @BindView(R.id.title_tags)
    TextView titleTags;
    @BindView(R.id.close_tags)
    IconicsImageView closeTags;
    @BindView(R.id.photo_tags)
    ImageView photoTags;

    private String TAG = UserInterestActivity.class.getSimpleName();
    private List<Interest> interests;
    private List<UserInterest> userInterests;
    private String user_id, _etag;
    private UserInterest dsUserInterest;
    private UserInterestActivity activity;
    private Callback<Interest> interestCallback;
    private InterestResource interestResource;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_interest);
        ButterKnife.bind(this);
        startBase(toolbar);
        initToolbar(CommunityMaterial.Icon.cmd_arrow_left, R.string.app_name, R.string.sub_title_interest);
        init();
        Log.d(TAG, "size userInterest " + DBController.getControler().getUserInterest(this, user_id).size());
    }

    private void init() {
        activity = this;

        user_id = getIntent().getStringExtra("user_id");
        _etag = DBController.getControler().getUser(getBaseContext(), user_id).get_etag();
        Log.d(TAG, user_id + " " + DBController.getControler().getUser(getBaseContext(), user_id).get_etag());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        progressBar.getIndeterminateDrawable().setColorFilter(Color.WHITE, Mode.SRC_ATOP);
        new GetInterestTask().execute(this);
        closeTags.setImageDrawable(Factory.getIcon(this, CommunityMaterial.Icon.cmd_close_box_outline, 30));
    }

    public void setVisibleProgressBar(int value) {
        progressBar.setVisibility(value);
    }

    public List<Interest> getInterests() {
        return interests;
    }

    public void setInterests(List<Interest> interests) {
        this.interests = interests;
    }

    public List<UserInterest> getUserInterests() {
        return userInterests;
    }

    public void setUserInterests(List<UserInterest> userInterests) {
        this.userInterests = userInterests;
    }

    public String getUserID() {
        return user_id;
    }


    public void populateGrid() {
        GridLayoutManager gridLayoutManager;

        gridLayoutManager = new GridLayoutManager(this, 2);
        grid.setHasFixedSize(true);
        grid.setLayoutManager(gridLayoutManager);
        List<Interest> interestList = new ArrayList<>();
        user = new User();
        user.setId(user_id);
        user.setEtag(_etag);
        if (!DBController.getControler().getUserInterest(this, user_id).isEmpty()) {
            for (UserInterest uno : DBController.getControler().getUserInterest(this, user_id)) {
                interestList.add(DBController.getControler().getInterest(getBaseContext(), uno.getInterest_id()));
            }
            List<Wish> wishes = new ArrayList<>();
            for (Interest uno : interestList) {
                Wish wish = new Wish();
                wish.setId(uno.get_id());
                wish.setEtag(uno.get_etag());
                wish.setActive(uno.getActive());
                wish.setDelete(uno.getDeleted());
                wish.setDescription(uno.getDescription());
                Logo logo = new Logo();
                logo.setFilename(uno.getImage());
                wish.setLogo(logo);
                wishes.add(wish);
            }

            user.setWishes(wishes);
        }
        CategoryInterestViewAdapter rcAdapter =
                new CategoryInterestViewAdapter(UserInterestActivity.this, interests, this, user);
        grid.setAdapter(rcAdapter);

    }

    private void updateInterestAsign(List<InterestAssing> assings) {
        userInterests = DBController.getControler().getInterestByUser(this, user_id);
        for (UserInterest userInterest : userInterests) {
            boolean sw = false;
            int index = 0;
            while (index < assings.size() && !sw) {
                if (assings.get(index).interest.get_id().compareTo(userInterest.getInterest_id()) == 0) {
                    assings.get(index).userInterest = userInterest;
                    sw = true;
                }
                index++;
            }
        }
    }

    /*private HashtagView.DataTransform buildDataTags(List<InterestAssing> assings) {
        return new HashtagView.DataStateTransform<InterestAssing>() {
            @Override
            public CharSequence prepare(InterestAssing item) {
                SpannableString spannableString = new SpannableString(item.interest.getName());
                spannableString.setSpan(new StrikethroughSpan(), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                return spannableString;
            }

            @Override
            public CharSequence prepareSelected(InterestAssing item) {
                return new SpannableString(item.interest.getName());
            }
        };
    }

    private HashtagView.DataSelector<InterestAssing> preselectedData(List<InterestAssing> assings) {
        return new HashtagView.DataSelector<InterestAssing>() {
            @Override
            public boolean preselect(InterestAssing item) {
                return item.userInterest != null;
            }
        };
    } */

    @OnClick(R.id.close_tags)
    public void actionCloseTags(View view) {
        interestTagView.animate()
                .alpha(0.0f)
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        interestTagView.setVisibility(View.GONE);
                        grid.animate()
                                .alpha(1.0f)
                                .translationY(0)
                                .setDuration(300)
                                .setListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animator) {
                                        grid.setVisibility(View.VISIBLE);
                                        grid.setTop(-1 * grid.getHeight());
                                        grid.setAlpha(0.0f);
                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animator) {
                                        //interestTags.clearAnimation();
                                        grid.clearAnimation();
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animator) {
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animator) {
                                    }
                                });
                    }
                });
    }

    @Override
    public String getUser_id() {
        return user_id;
    }

    public class InterestAssing {
        Interest interest;
        UserInterest userInterest;

        InterestAssing(Interest interest) {
            this.interest = interest;
        }

        public Interest getInterest() {
            return interest;
        }

        public UserInterest getUserInterest() {
            return userInterest;
        }
    }

    public class DataAsync {
        UserInterestActivity activity;
        InterestAssing elem;
        int action;

        DataAsync(UserInterestActivity activity, InterestAssing elem) {
            this.activity = activity;
            this.elem = elem;
        }

        public InterestAssing getElem() {
            return elem;
        }

        public UserInterestActivity getActivity() {
            return activity;
        }

        public int getAction() {
            return action;
        }

        public void setAction(int action) {
            this.action = action;
        }
    }
}
