package com.optimussoftware.boohos.async;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.optimussoftware.api.FavoriteResource;
import com.optimussoftware.api.response.CheckResponse;
import com.optimussoftware.api.response.GenericResponse;
import com.optimussoftware.boohos.account.NotificationUtil;
import com.optimussoftware.db.Advertising;
import com.optimussoftware.db.Favorites;
import com.optimussoftware.db.NotificationAdvertising;
import com.optimussoftware.boohos.data.DBController;
import com.optimussoftware.boohos.data.PersonalInfo;
import com.optimussoftware.boohos.service.receiver.AlarmEfectiveDays;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Perez on 25/7/2016.
 */
@SuppressWarnings("DefaultFileTemplate")
public class FavoritesAsyncTask extends AsyncTask<Void, Void, Boolean> {

    private static final String TAG = FavoritesAsyncTask.class.getSimpleName();

    private Context context;
    private PersonalInfo personalInfo;
    private Favorites favorites;
    private Advertising advertising;
    private DBController dbController;

    public FavoritesAsyncTask(Context context, PersonalInfo personalInfo, Favorites favorites, Advertising advertising) {
        this.context = context;
        this.personalInfo = personalInfo;
        this.favorites = favorites;
        this.advertising = advertising;
        dbController = DBController.getControler();
    }

    private void addFavorite(final String uid) {
        Callback<GenericResponse> createLikesCallback = new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

                try {

                    GenericResponse r = response.body();
                    Favorites favorites = new Favorites();
                    favorites.set_id(r.get_id());
                    favorites.set_etag(r.get_etag());
                    favorites.setActive(true);
                    favorites.setDeleted(false);
                    favorites.setAdvertising_id(uid);
                    favorites.setUser_id(personalInfo.getUuid());
                    favorites.setLocation_id(advertising.getLocation_id());
                    dbController.createFavorite(context, favorites);
                    NotificationUtil notificationUtil = new NotificationUtil(context);
                    notificationUtil.showDialogCheckActiveNotification(advertising);

                } catch (Exception e) {
                    checkIfExistFavorite(uid);
                }
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {

            }
        };

        FavoriteResource favoriteResource = new FavoriteResource();
        Favorites favorites = new Favorites();
        favorites.setAdvertising_id(uid);
        favorites.setUser_id(personalInfo.getUuid());
        favorites.setLocation_id(advertising.getLocation_id());
        favoriteResource.favorite(favorites, createLikesCallback);
    }

    private void checkIfExistFavorite(final String advertising_id) {

        Callback<CheckResponse> checkIfExistFavoriteCallback = new Callback<CheckResponse>() {
            @Override
            public void onResponse(Call<CheckResponse> call, Response<CheckResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        Log.i(TAG, "Agregar favoriteBD" + response.body().getId());
                        CheckResponse r = response.body();
                        Favorites favorites = new Favorites();
                        favorites.set_id(r.getId());
                        favorites.set_etag(r.getSource().getEtag());
                        favorites.setActive(true);
                        favorites.setDeleted(false);
                        favorites.setAdvertising_id(advertising_id);
                        favorites.setUser_id(personalInfo.getUuid());
                        favorites.setLocation_id(advertising.getLocation_id());
                        dbController.createFavorite(context, favorites);
                        NotificationUtil notificationUtil = new NotificationUtil(context);
                        notificationUtil.showDialogCheckActiveNotification(advertising);
                    }

                } catch (Exception e) {
                    Log.e(TAG, "Err check favorite--> " + e + " valor " + true + "Response Body ");
                }
            }

            @Override
            public void onFailure(Call<CheckResponse> call, Throwable t) {
                Log.i(TAG, "Failure " + t);
            }
        };

        FavoriteResource favoriteResource = new FavoriteResource();
        Favorites favorites = new Favorites();
        favorites.setUser_id(personalInfo.getUuid());
        favorites.setAdvertising_id(advertising_id);
        favorites.setLocation_id(advertising.getLocation_id());
        favoriteResource.checkIfExist(favorites, checkIfExistFavoriteCallback);


    }

    public void removeFavorite(String uid, String e_tag, final String advertising_id) {
        Callback<Void> removeDislikeCallback = new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                cancelNotify(advertising_id);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        };

        FavoriteResource favoriteResource = new FavoriteResource();
        Favorites favorites = new Favorites();
        favorites.set_id(uid);
        favorites.set_etag(e_tag);
        favoriteResource.remove(favorites, removeDislikeCallback);
    }



    public void cancelNotify(String advertising) {

        List<NotificationAdvertising> notificationAdvertisingListDB = DBController.getControler().getNotificationAdvertisingList(context, advertising);
        Log.i(TAG, notificationAdvertisingListDB.size() + "");
        for (int i = 0; i < notificationAdvertisingListDB.size(); i++) {
            if (notificationAdvertisingListDB.get(i).getDelete()) {
                Intent alarmIntent = new Intent(context, AlarmEfectiveDays.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, Integer.parseInt(notificationAdvertisingListDB.get(i).get_id()), alarmIntent, PendingIntent.FLAG_ONE_SHOT);

                AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

                manager.cancel(pendingIntent);
                DBController.getControler().removeNotificationAdvertising(context, notificationAdvertisingListDB.get(i));
            }
        }

    }

    @Override
    protected Boolean doInBackground(Void... voids) {

        if (favorites != null) {
            removeFavorite(favorites.get_id(), favorites.get_etag(), favorites.getAdvertising_id());
            dbController.removeFavorite(context, favorites);
        } else {
            addFavorite(advertising.get_id());
        }
        return true;
    }
}
