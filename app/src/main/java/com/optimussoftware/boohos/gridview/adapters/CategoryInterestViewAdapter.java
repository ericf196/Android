package com.optimussoftware.boohos.gridview.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;

import com.optimussoftware.api.core.Factory;
import com.optimussoftware.api.response.commons.Logo;
import com.optimussoftware.api.response.user.User;
import com.optimussoftware.api.response.user.Wish;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.UserInterestActivity;
import com.optimussoftware.boohos.async.UserInterestAsyncTask;
import com.optimussoftware.boohos.data.DBController;
import com.optimussoftware.boohos.data.PersonalInfo;
import com.optimussoftware.boohos.gridview.holders.CategoryInterestViewHolders;
import com.optimussoftware.db.Interest;
import com.optimussoftware.db.UserInterest;

import java.util.ArrayList;
import java.util.List;

public class CategoryInterestViewAdapter extends RecyclerView.Adapter<CategoryInterestViewHolders> {

    private static final String TAG = CategoryInterestViewAdapter.class.getSimpleName();

    private List<Interest> itemList;
    private Context context;
    private UserInterestActivity activity;
    private UserInterestAsyncTask userInterestAsyncTask;
    private final static int FADE_DURATION = 1000;// in milliseconds
    PersonalInfo personalInfo;
    User user;
    List<Wish> wishes;

    public CategoryInterestViewAdapter(Context context, List<Interest> itemList, UserInterestActivity activity, User user) {
        this.context = context;
        this.itemList = itemList;
        this.activity = activity;
        this.user = user;
        personalInfo = new PersonalInfo(activity);
        if (user.getWishes() != null) {
            wishes = user.getWishes();
        } else {
            wishes = new ArrayList<>();
        }

    }

    @Override
    public CategoryInterestViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_interest_item, parent, false);
        return new CategoryInterestViewHolders(layoutView, activity);
    }

    @Override
    public void onBindViewHolder(final CategoryInterestViewHolders holder, final int position) {
        setFadeAnimation(holder.itemView);
        holder.setView(itemList.get(position));
        final boolean[] isSelect = {false};
        if (checkUserInterest(itemList.get(position))) {
            holder.getIconVisible(true);
            isSelect[0] = true;

        } else {
            holder.getIconVisible(false);
            isSelect[0] = false;
        }


        holder.photoCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSelect[0]) {
                    createUserInterest(itemList.get(position), false);
                    holder.getIconVisible(false);
                    isSelect[0] = false;
                } else {
                    createUserInterest(itemList.get(position), true);
                    holder.getIconVisible(true);
                    isSelect[0] = true;
                }
            }
        });
    }

    private void createUserInterest(Interest interest, boolean action) {
        sendInterest(interest, action);
        userInterestAsyncTask = new UserInterestAsyncTask(user);
        userInterestAsyncTask.execute(((UserInterestActivity) context));
    }

    public void addUserInterest(Interest uno) {

            UserInterest dsUserInterest = new UserInterest();
            dsUserInterest.setInterest_id(uno.get_id());
            dsUserInterest.setUser_id(user.getId());
            DBController.getControler().createUserInterest(context, dsUserInterest);
    }

    private void removeUserInterest(Interest interest_id) {
        List<UserInterest> userInterestList = DBController.getControler().getUserInterest(context, ((UserInterestActivity) context).getUser_id(), interest_id.get_id());
        UserInterest userInterest = new UserInterest();
        for (UserInterest interest : userInterestList) {
            userInterest = interest;
        }
        userInterestAsyncTask = new UserInterestAsyncTask(null, userInterest);
        userInterestAsyncTask.execute(((UserInterestActivity) context));
    }

    private boolean checkUserInterest(Interest interest_id) {
        List<UserInterest> userInterestList = DBController.getControler().getUserInterest(context, ((UserInterestActivity) context).getUser_id(), interest_id.get_id());
        if (!userInterestList.isEmpty()) {
            return true;
        }
        return false;
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

    private void sendInterest(Interest interest, boolean action) {

        if (action) {
            wishes.add(addWish(interest));
            user.setWishes(wishes);
            addUserInterest(interest);
        } else {
            removeWish(interest);
        }
    }

    private Wish addWish(Interest uno) {
        Wish wish = new Wish();
        wish.setId(uno.get_id());
        wish.setEtag(uno.get_etag());
        wish.setActive(uno.getActive());
        wish.setDelete(uno.getDeleted());
        wish.setDescription(uno.getDescription());
        Logo logo = new Logo();
        logo.setFilename(uno.getImage());
        wish.setLogo(logo);
        return wish;
    }

    private void removeWish(Interest interest) {
        Log.d(TAG," probando " + DBController.getControler().getUserInterest(context, user.getId(), interest.get_id()).get(0).getId());
        DBController.getControler().removeUserInterest(context, DBController.getControler().getUserInterest(context, user.getId(), interest.get_id()).get(0));

        for (int i = 0; i < wishes.size(); i++) {
            if(wishes.get(i).getId().equals(interest.get_id())) {
                 wishes.remove(i);
                user.setWishes(wishes);
            }
        }
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
