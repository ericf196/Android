package com.optimussoftware.boohos.async;

import android.os.AsyncTask;
import android.util.Log;

import com.optimussoftware.api.UserInterestResource;
import com.optimussoftware.api.UserResource;
import com.optimussoftware.api.response.GenericResponse;
import com.optimussoftware.api.response.user.User;
import com.optimussoftware.api.response.user.Wish;
import com.optimussoftware.api.response.user.WishUpdate;
import com.optimussoftware.db.Interest;
import com.optimussoftware.db.UserInterest;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.UserInterestActivity;
import com.optimussoftware.boohos.data.DBController;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by william.castillo@optimus-software.com on 10/07/16.
 */

public class UserInterestAsyncTask extends AsyncTask<UserInterestActivity, Void, Void> {

    private static final String TAG = UserInterestAsyncTask.class.getSimpleName();

    private UserInterestActivity scope;
    private UserInterest dsUserInterest;
    private Interest interest;
    private Callback<GenericResponse> createUserInterestCallback;
    private Callback<Void> removeUserInterestCallback;
    private Callback<GenericResponse> callbackAddInterest;

    private User user;

    public UserInterestAsyncTask(User user) {
        this.user = user;
    }

    public UserInterestAsyncTask(Interest interest, UserInterest dsUserInterest) {
        this.interest = interest;
        this.dsUserInterest = dsUserInterest;
    }

    private void addInterest() {
        Log.d(TAG, "init addInterest: "+ user.getWishes().size() + " " + user.getEtag());
        callbackAddInterest = new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "isSuccessFul --> " + response.isSuccessful() + " _Etag " + response.body().get_etag());
                    com.optimussoftware.db.User userDB = DBController.getControler().getUser(scope, user.getId());
                    userDB.set_etag(response.body().get_etag());
                    DBController.getControler().createUser(scope, userDB);
                    Log.d(TAG, "isSuccessFul --> " + response.isSuccessful() + " _Etag " +  DBController.getControler().getUser(scope, user.getId()).get_etag());
                } else {
                    Log.d(TAG, "isSuccessFul --> " + response.isSuccessful());
                }
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {

            }
        };

        List<String> listWish = new ArrayList<>();
        for (Wish uno : user.getWishes()) {
            listWish.add(uno.getId());
        }


        WishUpdate wishUpdate = new WishUpdate();
        wishUpdate.setUpdated(listWish);
        UserResource userResource = new UserResource();
        userResource.updateWishUser(wishUpdate, user.getId(), DBController.getControler().getUser(scope, user.getId()).get_etag(), callbackAddInterest);
    }

    private void addInterest(Interest item){
        createUserInterestCallback = new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                GenericResponse r = response.body();
                DBController.getControler().createUserInterest(scope.getApplicationContext(), dsUserInterest);
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {
                scope.showToast(R.string.toast_connection_error);
            }
        };

        Interest interest = item;
        UserInterestResource userInterestResource = new UserInterestResource();
        dsUserInterest = new UserInterest();
        dsUserInterest.setInterest_id(interest.get_id());
        dsUserInterest.setUser_id(scope.getUserID());
        userInterestResource.add(dsUserInterest, createUserInterestCallback);
    }

    private void removeInterest(UserInterest item){
        removeUserInterestCallback = new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                DBController.getControler().removeUserInterest(scope.getApplicationContext(), dsUserInterest);
                dsUserInterest = null;
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                scope.showToast(R.string.toast_connection_error);
            }
        };

        UserInterest userInterest = item;
        dsUserInterest = userInterest;
        UserInterestResource userInterestResource = new UserInterestResource();
    }

    @Override
    protected Void doInBackground(UserInterestActivity... data) {
        scope = data[0];
        addInterest();
        return null;
    }

}
