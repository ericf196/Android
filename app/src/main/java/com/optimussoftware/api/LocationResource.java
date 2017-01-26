package com.optimussoftware.api;

import com.optimussoftware.api.core.BaseAPI;
import com.optimussoftware.api.response.location.Item;
import com.optimussoftware.db.Location;
import com.optimussoftware.boohos.data.Constants;

import java.io.IOException;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by william.castillo@optimus-software.com on 15/07/16.
 */
public class LocationResource {
    /**
     * @param uid
     * @param callback
     */
    public void get(String uid, Callback<Location> callback){
        OkHttpClient.Builder httpClient = BaseAPI.getDefaultOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URI)
                .addConverterFactory(GsonConverterFactory.create(BaseAPI.dateFormatGson()))
                .client(httpClient.build())
                .build();
        BaseAPI.LocationInterface service = retrofit.create(BaseAPI.LocationInterface.class);
        Call<Location> locationCall = service.get(uid);
        locationCall.enqueue(callback);
    }

    public Response<Location> get(String location_id) throws IOException {
        OkHttpClient.Builder httpClient = BaseAPI.getDefaultOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URI)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        BaseAPI.LocationInterface service = retrofit.create(BaseAPI.LocationInterface.class);
        Call<Location> interestCall = service.get(location_id);
        return interestCall.execute();
    }

    /**
     * @param uid
     * @param callback
     */
    public void getLocation(String uid, Callback<com.optimussoftware.api.response.location.Location> callback){
        OkHttpClient.Builder httpClient = BaseAPI.getDefaultOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URI)
                .addConverterFactory(GsonConverterFactory.create(BaseAPI.dateFormatGson()))
                .client(httpClient.build())
                .build();
        BaseAPI.LocationInterface service = retrofit.create(BaseAPI.LocationInterface.class);
        Call<com.optimussoftware.api.response.location.Location> locationCall = service.getLocation(uid);
        locationCall.enqueue(callback);
    }

    public Response<Item> listSync(String location_id) throws IOException {
        OkHttpClient.Builder httpClient = BaseAPI.getDefaultOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URI)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        BaseAPI.LocationInterface service = retrofit.create(BaseAPI.LocationInterface.class);
        Call<Item> locationCall = service.list(location_id);
        return locationCall.execute();

    }
}
