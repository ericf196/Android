package com.optimussoftware.api;

import com.optimussoftware.api.core.BaseAPI;
import com.optimussoftware.api.response.advertising.AdvertisingByCampaign.AdvertisingListByCampaign;
import com.optimussoftware.db.Advertising;
import com.optimussoftware.boohos.data.Constants;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by william.castillo@optimus-software.com on 19/07/16.
 */
public class AdvertisingResource {

    public void get(String uid, Callback<Advertising> callback) {
        OkHttpClient.Builder httpClient = BaseAPI.getDefaultOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URI)
                .addConverterFactory(GsonConverterFactory.create(BaseAPI.dateFormatGson()))
                .client(httpClient.build())
                .build();
        BaseAPI.AdvertisingInterface service = retrofit.create(BaseAPI.AdvertisingInterface.class);
        Call<Advertising> advertisingCall = service.get(uid);
        advertisingCall.enqueue(callback);
    }

    /*public void getListAdvertisingByCampaign(String id_campaign, Callback<AdvertisingListByCampaign> callback) {
        OkHttpClient.Builder httpClient = BaseAPI.getDefaultOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URI)
                .addConverterFactory(GsonConverterFactory.create(BaseAPI.dateFormatGson()))
                .client(httpClient.build())
                .build();
        BaseAPI.AdvertisingInterface service = retrofit.create(BaseAPI.AdvertisingInterface.class);
        Call<AdvertisingListByCampaign> advertisingCall = service.getAdvertisingByCampaign(id_campaign, "id_campaign", "{\"images\":1}");
        advertisingCall.enqueue(callback);
    }*/
}
