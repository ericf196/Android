package com.optimussoftware.api;

import com.optimussoftware.api.core.BaseAPI;
import com.optimussoftware.api.response.CheckResponse;
import com.optimussoftware.api.response.GenericResponse;
import com.optimussoftware.db.Likes;
import com.optimussoftware.boohos.data.Constants;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by william.castillo@optimus-software.com on 14/07/16.
 */
public class LikeResource {
    public void like(Likes likes, Callback<GenericResponse> callback) {
        OkHttpClient.Builder httpClient = BaseAPI.getDefaultOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URI)
                .addConverterFactory(GsonConverterFactory.create(BaseAPI.dateFormatGson()))
                .client(httpClient.build())
                .build();
        BaseAPI.LikeResourceInterface service = retrofit.create(BaseAPI.LikeResourceInterface.class);
        Call<GenericResponse> likeCall = service.like(likes);
        likeCall.enqueue(callback);
    }

    public void remove(Likes likes, Callback<Void> callback) {
        OkHttpClient.Builder httpClient = BaseAPI.getOkHttpClientWithETAG(likes.get_etag());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URI)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        BaseAPI.LikeResourceInterface service = retrofit.create(BaseAPI.LikeResourceInterface.class);
        Call<Void> likesCall = service.remove(likes.get_id());
        likesCall.enqueue(callback);
    }


    public void checkIfExist(Likes likes, Callback<CheckResponse> callback) {
        OkHttpClient.Builder httpClient = BaseAPI.getDefaultOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URI)
                .addConverterFactory(GsonConverterFactory.create(BaseAPI.dateFormatGson()))
                .client(httpClient.build())
                .build();
        BaseAPI.LikeResourceInterface service = retrofit.create(BaseAPI.LikeResourceInterface.class);
        Call<CheckResponse> likeCall = service.check(likes);
        likeCall.enqueue(callback);
    }
}
