package com.optimussoftware.api;

import com.optimussoftware.api.response.interest.InterestList;
import com.optimussoftware.api.core.BaseAPI;
import com.optimussoftware.boohos.data.Constants;

import java.io.IOException;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 * Created by Created by william.castillo@optimus-software.com on 16/06/16.
 */
public class InterestResource {



    /**
     * @param page
     * @param callback
     */
    public void list(int page, Callback<InterestList> callback) {
        OkHttpClient.Builder httpClient = BaseAPI.getDefaultOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URI)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        BaseAPI.InterestInterface service = retrofit.create(BaseAPI.InterestInterface.class);
        Call<InterestList> interestCall = service.get(page);
        interestCall.enqueue(callback);
    }

    /**
     * @param page
     * @throws IOException
     */
    public Response<InterestList> listSync(int page) throws IOException {
        OkHttpClient.Builder httpClient = BaseAPI.getDefaultOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URI)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        BaseAPI.InterestInterface service = retrofit.create(BaseAPI.InterestInterface.class);
        Call<InterestList> interestCall = service.get(page);
        return interestCall.execute();
    }
}
