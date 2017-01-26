package com.optimussoftware.api;

import com.optimussoftware.api.core.BaseAPI;
import com.optimussoftware.api.response.newAdvertising.AdverstingsList;
import com.optimussoftware.boohos.data.Constants;


import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 * Created by Created by william.castillo@optimus-software.com on 13/06/16.
 */
public class LastOfferts {
    final String TAG = LastOfferts.class.getSimpleName();
    Retrofit retrofit;

    public LastOfferts(){
    }

    public void getOfferts(String device_uid, Callback<AdverstingsList> callback) {
        OkHttpClient.Builder httpClient = BaseAPI.getDefaultOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URI)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        BaseAPI.LastOffertsInterface service = retrofit.create(BaseAPI.LastOffertsInterface.class);
        Call<AdverstingsList> adverstingFullCall = service.offerts(device_uid, "1");
        adverstingFullCall.enqueue(callback);
    }
}
