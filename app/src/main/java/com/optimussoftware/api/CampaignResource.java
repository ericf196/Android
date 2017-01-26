package com.optimussoftware.api;

import com.optimussoftware.api.core.BaseAPI;
import com.optimussoftware.db.Campaign;
import com.optimussoftware.boohos.data.Constants;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by william.castillo@optimus-software.com on 15/07/16.
 */
public class CampaignResource {
    /**
     * @param uid
     * @param callback
     */
    public void get(String uid, Callback<Campaign> callback) {
        OkHttpClient.Builder httpClient = BaseAPI.getDefaultOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URI)
                .addConverterFactory(GsonConverterFactory.create(BaseAPI.dateFormatGson()))
                .client(httpClient.build())
                .build();
        BaseAPI.CampaignInterface service = retrofit.create(BaseAPI.CampaignInterface.class);
        Call<Campaign> campaignCall = service.get(uid);
        campaignCall.enqueue(callback);
    }


    /**
     * @param uid
     * @param callback
     */
    public void getCampaign(String uid, Callback<com.optimussoftware.api.response.campaign.Item> callback) {
        OkHttpClient.Builder httpClient = BaseAPI.getDefaultOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URI)
                .addConverterFactory(GsonConverterFactory.create(BaseAPI.dateFormatGson()))
                .client(httpClient.build())
                .build();
        BaseAPI.CampaignInterface service = retrofit.create(BaseAPI.CampaignInterface.class);
        Call<com.optimussoftware.api.response.campaign.Item> campaignCall = service.getCampaign(uid);
        campaignCall.enqueue(callback);
    }
}
