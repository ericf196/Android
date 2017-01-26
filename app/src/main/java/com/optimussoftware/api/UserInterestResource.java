package com.optimussoftware.api;

import com.optimussoftware.api.response.GenericResponse;
import com.optimussoftware.api.response.userinterest.UserInterestList;
import com.optimussoftware.api.core.BaseAPI;
import com.optimussoftware.db.UserInterest;
import com.optimussoftware.boohos.data.Constants;

import java.io.IOException;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by william on 16/06/16.
 */
public class UserInterestResource {

    public void list(int page, String user_id, Callback<UserInterestList> callback) {
        String where = "\"user_id\":\"" + user_id + "\"";
        where = "{" + where + "}";
        OkHttpClient.Builder httpClient = BaseAPI.getDefaultOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URI)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        BaseAPI.UserInterestInterface service = retrofit.create(BaseAPI.UserInterestInterface.class);
        Call<UserInterestList> interestCall = service.list(page, where);
        interestCall.enqueue(callback);
    }

    public Response<UserInterestList> listSync(int page, String user_id) throws IOException {
        String where = user_id + "=user_id" ;
        OkHttpClient.Builder httpClient = BaseAPI.getDefaultOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URI)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        BaseAPI.UserInterestInterface service = retrofit.create(BaseAPI.UserInterestInterface.class);
        Call<UserInterestList> interestCall = service.list(page, where);
        return interestCall.execute();

    }

    public void add(UserInterest userInterest, Callback<GenericResponse> callback){
        OkHttpClient.Builder httpClient = BaseAPI.getDefaultOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URI)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        BaseAPI.UserInterestInterface service = retrofit.create(BaseAPI.UserInterestInterface.class);
        Call<GenericResponse> interestCall = service.create(userInterest);
        interestCall.enqueue(callback);
    }

}
