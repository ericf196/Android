package com.optimussoftware.api;

import com.optimussoftware.api.core.BaseAPI;
import com.optimussoftware.api.response.GenericResponse;
import com.optimussoftware.api.response.review.NewReview.ReviewResponse;
import com.optimussoftware.api.response.review.ReviewCheck.ReviewCheck;
import com.optimussoftware.db.Review;
import com.optimussoftware.boohos.data.Constants;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by william.castillo@optimus-software.com on 15/07/16.
 */
public class ReviewResource {

    public void create(Review review, Callback<GenericResponse> callback) {
        OkHttpClient.Builder httpClient = BaseAPI.getDefaultOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URI)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        BaseAPI.ReviewResourceInterface service = retrofit.create(BaseAPI.ReviewResourceInterface.class);
        Call<GenericResponse> reviewCall = service.create(review);
        reviewCall.enqueue(callback);
    }

    public void edit(String id, String etag, Review review, Callback<GenericResponse> callback) {
        OkHttpClient.Builder httpClient = BaseAPI.getOkHttpClientWithETAG(etag);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URI)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        BaseAPI.ReviewResourceInterface service = retrofit.create(BaseAPI.ReviewResourceInterface.class);
        Call<GenericResponse> reviewCall = service.edit(id, review);
        reviewCall.enqueue(callback);
    }

    public void remove(Review review, Callback<Void> callback) {
        OkHttpClient.Builder httpClient = BaseAPI.getOkHttpClientWithETAG(review.get_etag());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URI)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        BaseAPI.ReviewResourceInterface service = retrofit.create(BaseAPI.ReviewResourceInterface.class);
        Call<Void> reviewCall = service.remove(review.get_id());
        reviewCall.enqueue(callback);
    }

    public void checkReview(Review review, Callback<ReviewCheck> callback) {
        OkHttpClient.Builder httpClient = BaseAPI.getDefaultOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URI)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        BaseAPI.ReviewResourceInterface service = retrofit.create(BaseAPI.ReviewResourceInterface.class);
        Call<ReviewCheck> reviewCall = service.checkReview(review);
        reviewCall.enqueue(callback);
    }

    /*public void getReviewByAdvertising(String advertising_id, int page, Callback<ReviewResponse> callback) {
        OkHttpClient.Builder httpClient = BaseAPI.getDefaultOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URI)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        String embedded = "{\"user_id\":1}";
        String id = "\"" + advertising_id + "\"";

        BaseAPI.ReviewResourceInterface service = retrofit.create(BaseAPI.ReviewResourceInterface.class);
        Call<ReviewResponse> reviewCall = service.getReviewByAdvertising(id, "advertising_id", embedded, page);
        reviewCall.enqueue(callback);
    }*/

    public void getReviewByAdvertising(String advertising_id, int page, Callback<ReviewResponse> callback) {
        OkHttpClient.Builder httpClient = BaseAPI.getDefaultOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URI)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        String embedded = "{\"user_id\":1}";
        String id = "{\"advertising_id\":\"" + advertising_id + "\"}";

        //?where={%22advertising_id%22:%225875b6d9f0ee2b2ede2aad52%22}&embedded={%22user_id%22:1}&page=1

        //?q=%225875b6d9f0ee2b2ede2aad52%22&df=advertising_id&embedded={%22user_id%22:1}&page=1
        BaseAPI.ReviewResourceInterface service = retrofit.create(BaseAPI.ReviewResourceInterface.class);
        Call<ReviewResponse> reviewCall = service.getReviewByAdvertising(id, embedded, page);
        reviewCall.enqueue(callback);
    }

}
