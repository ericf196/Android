package com.optimussoftware.api;

import com.optimussoftware.api.core.BaseAPI;
import com.optimussoftware.api.response.CheckResponse;
import com.optimussoftware.api.response.GenericResponse;
import com.optimussoftware.api.response.favorites.FavoritesList;
import com.optimussoftware.api.response.favorites.advertisingFavorite.AdvertisingFavoriteList;
import com.optimussoftware.db.Favorites;
import com.optimussoftware.boohos.data.Constants;

import java.io.IOException;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by william.castillo@optimus-software.com on 14/07/16.
 */
public class FavoriteResource {

    public void favorite(Favorites favorites, Callback<GenericResponse> callback) {
        OkHttpClient.Builder httpClient = BaseAPI.getDefaultOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URI)
                .addConverterFactory(GsonConverterFactory.create(BaseAPI.dateFormatGson()))
                .client(httpClient.build())
                .build();
        BaseAPI.FavoriteResourceInterface service = retrofit.create(BaseAPI.FavoriteResourceInterface.class);
        Call<GenericResponse> likeCall = service.favorite(favorites);
        likeCall.enqueue(callback);
    }

    public void getAdvertisingFavorite(int page, String user_id, Callback<AdvertisingFavoriteList> callback) {
        OkHttpClient.Builder httpClient = BaseAPI.getDefaultOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URI)
                .addConverterFactory(GsonConverterFactory.create(BaseAPI.dateFormatGson()))
                .client(httpClient.build())
                .build();
        String embedded = "{\"advertising_id\":1}";
        String id = "{\"user_id\":\"" + user_id + "\"}";
        BaseAPI.FavoriteResourceInterface service = retrofit.create(BaseAPI.FavoriteResourceInterface.class);
        Call<AdvertisingFavoriteList> likeCall = service.getAdvertisingFavorite(id, embedded, page);
        likeCall.enqueue(callback);
    }

    /*public void getAdvertisingFavorite(int page, String user_id, Callback<AdvertisingFavoriteList> callback){
        OkHttpClient.Builder httpClient = BaseAPI.getDefaultOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URI)
                .addConverterFactory(GsonConverterFactory.create(BaseAPI.dateFormatGson()))
                .client(httpClient.build())
                .build();
        String embedded = "{\"advertising_id\":1}";
        String id = "\"" + user_id + "\"";
        BaseAPI.FavoriteResourceInterface service = retrofit.create(BaseAPI.FavoriteResourceInterface.class);
        Call<AdvertisingFavoriteList> likeCall = service.getAdvertisingFavorite(id, "user_id", embedded, page);
        likeCall.enqueue(callback);
    }*/

    public void remove(Favorites favorites, Callback<Void> callback) {
        OkHttpClient.Builder httpClient = BaseAPI.getOkHttpClientWithETAG(favorites.get_etag());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URI)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        BaseAPI.FavoriteResourceInterface service = retrofit.create(BaseAPI.FavoriteResourceInterface.class);
        Call<Void> favoritesCall = service.remove(favorites.get_id());
        favoritesCall.enqueue(callback);
    }

    public void checkIfExist(Favorites favorite, Callback<CheckResponse> callback) {
        OkHttpClient.Builder httpClient = BaseAPI.getDefaultOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URI)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        BaseAPI.FavoriteResourceInterface service = retrofit.create(BaseAPI.FavoriteResourceInterface.class);
        Call<CheckResponse> favoritesCall = service.check(favorite);
        favoritesCall.enqueue(callback);
    }

    public Response<FavoritesList> listByUser(int page, String user_id) throws IOException {
        String id = "{\"user_id\":\"" + user_id + "\"}";
        String embedded = "{\"favorite_id\":1}";
        OkHttpClient.Builder httpClient = BaseAPI.getDefaultOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URI)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        BaseAPI.FavoriteResourceInterface service = retrofit.create(BaseAPI.FavoriteResourceInterface.class);
        Call<FavoritesList> interestCall = service.list(id, embedded, page);
        return interestCall.execute();
    }
}
