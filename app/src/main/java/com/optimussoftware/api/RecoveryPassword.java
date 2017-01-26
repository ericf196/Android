package com.optimussoftware.api;

import com.optimussoftware.api.core.BaseAPI;
import com.optimussoftware.api.response.GenericResponse;
import com.optimussoftware.db.User;
import com.optimussoftware.boohos.data.Constants;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by guerra on 26/09/16.
 */
public class RecoveryPassword {

    public void recovery(User user, Callback<GenericResponse> callback) {
        OkHttpClient.Builder httpClient = BaseAPI.getDefaultOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URI)
                .addConverterFactory(GsonConverterFactory.create(BaseAPI.dateFormatGson()))
                .client(httpClient.build())
                .build();
        BaseAPI.RecoveryInterface service = retrofit.create(BaseAPI.RecoveryInterface.class);
        Call<GenericResponse> recoveryCall = service.recovery(user);
        recoveryCall.enqueue(callback);
    }


}
