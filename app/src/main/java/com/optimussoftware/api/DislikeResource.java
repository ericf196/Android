package com.optimussoftware.api;

import com.optimussoftware.api.core.BaseAPI;
import com.optimussoftware.api.response.CheckResponse;
import com.optimussoftware.api.response.GenericResponse;
import com.optimussoftware.db.Dislikes;
import com.optimussoftware.boohos.data.Constants;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by william.castillo@optimus-software.com on 14/07/16.
 */
public class DislikeResource {
    public void dislike(Dislikes dislikes, Callback<GenericResponse> callback){
        OkHttpClient.Builder httpClient = BaseAPI.getDefaultOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URI)
                .addConverterFactory(GsonConverterFactory.create(BaseAPI.dateFormatGson()))
                .client(httpClient.build())
                .build();
        BaseAPI.DislikeResourceInterface service = retrofit.create(BaseAPI.DislikeResourceInterface.class);
        Call<GenericResponse> dislikeCall = service.dislike(dislikes);
        dislikeCall.enqueue(callback);
    }

    public void remove(Dislikes dislikes, Callback<Void> callback) {
        OkHttpClient.Builder httpClient = BaseAPI.getOkHttpClientWithETAG(dislikes.get_etag());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URI)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        BaseAPI.DislikeResourceInterface service = retrofit.create(BaseAPI.DislikeResourceInterface.class);
        Call<Void> dislikesCall = service.remove(dislikes.get_id());
        dislikesCall.enqueue(callback);
    }

    public void checkIfExist(Dislikes dislikes, Callback<CheckResponse> callback){
        OkHttpClient.Builder httpClient = BaseAPI.getDefaultOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URI)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        BaseAPI.DislikeResourceInterface service = retrofit.create(BaseAPI.DislikeResourceInterface.class);
        Call<CheckResponse> dislikesCall = service.check(dislikes);
        dislikesCall.enqueue(callback);
    }
}
