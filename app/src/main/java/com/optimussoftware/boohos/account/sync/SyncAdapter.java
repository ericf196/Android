package com.optimussoftware.boohos.account.sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import com.optimussoftware.api.FavoriteResource;
import com.optimussoftware.api.LocationResource;
import com.optimussoftware.api.response.commons.Meta;
import com.optimussoftware.api.response.favorites.FavoritesList;
import com.optimussoftware.db.Location;
import com.optimussoftware.boohos.account.auth.AccountUtils;
import com.optimussoftware.boohos.data.Constants;
import com.optimussoftware.boohos.data.DBController;
import com.optimussoftware.boohos.data.PersonalInfo;

import java.io.IOException;

import retrofit2.Response;


public class SyncAdapter extends AbstractThreadedSyncAdapter {

    private final static String TAG = SyncAdapter.class.getSimpleName();

    ContentResolver resolver;

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        resolver = context.getContentResolver();
    }

    public SyncAdapter(
            Context context,
            boolean autoInitialize,
            boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        resolver = context.getContentResolver();
    }

    @Override
    public void onPerformSync(Account account,
                              Bundle extras,
                              String authority,
                              ContentProviderClient provider,
                              final SyncResult syncResult) {

        Log.i("Leonardo", "onPerformSync()...");

        boolean soloSubida = extras.getBoolean(ContentResolver.SYNC_EXTRAS_UPLOAD, false);

        if (!soloSubida) {
            performSyncLocal(extras.getString("user_id"));
            getContext().sendBroadcast(new Intent(Constants.ACTION_FINISHED_SYNC));
        }
    }

    private void performSyncLocal(String user_id) {
        try {
            FavoriteResource favoriteResource = new FavoriteResource();
            Response<FavoritesList> response;
            Meta meta = null;
            int page = 1;
            do {
                response = favoriteResource.listByUser(page, user_id);
                if (response.isSuccessful()) {
                    Log.i(TAG, "Favorite response.isSuccessful()");
                    meta = response.body().getMeta();
                    for (com.optimussoftware.api.response.favorites.Item item : response.body().getItems()) {
                        DBController.getControler().createFavorite(getContext(), com.optimussoftware.api.core.Factory.getFavoriteFromResponse(item));
                        Log.i(TAG, "Create favorite DB");
                        getLocation(item.getLocationId());
                    }
                }
                page++;
            } while (!meta.isLast());
        } catch (Exception e) {
            Log.i(TAG, "Err --> " + e.toString());
        }
    }

    private boolean getLocation(String location_id) throws IOException {
        boolean valor = false;
        LocationResource locationResource = new LocationResource();
        Response<Location> response;
        response = locationResource.get(location_id);
        if (response.isSuccessful()) {
            Log.i(TAG, "Location response.isSuccessful()");
            Location location = response.body();
            if (DBController.getControler().getLocation(getContext(), location_id) == null) {
                DBController.getControler().createLocation(getContext(), location);
                Log.i(TAG, "Create Location DB");
                valor = true;
            }
        }

        return valor;
    }

    public static void sincronizarAhora(Context context, PersonalInfo personalInfo, boolean onlyUpload) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        bundle.putString("user_id", personalInfo.getUuid());
        if (onlyUpload)
            bundle.putBoolean(ContentResolver.SYNC_EXTRAS_UPLOAD, true);

        ContentResolver.requestSync(AccountUtils.getAccount(context, personalInfo.getEmail()),
                AccountUtils.AUTH_TOKEN_TYPE, bundle);
    }
}