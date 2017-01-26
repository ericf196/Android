package com.optimussoftware.api;

import com.optimussoftware.api.core.BaseAPI;
import com.optimussoftware.api.response.GenericResponse;
import com.optimussoftware.api.response.user.User;
import com.optimussoftware.api.response.user.Wish;
import com.optimussoftware.api.response.user.WishUpdate;
import com.optimussoftware.boohos.data.Constants;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by william on 15/06/16.
 */
public class UserResource {

    /**
     * @param uid
     * @param callback
     */
    public void get(String uid, Callback<User> callback) {
        OkHttpClient.Builder httpClient = BaseAPI.getDefaultOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URI)
                .addConverterFactory(GsonConverterFactory.create(BaseAPI.dateFormatGson()))
                .client(httpClient.build())
                .build();

        String embedded = "{\"wishes\":1}";
        String id = "\"" + uid + "\"";
        BaseAPI.UserResourceInterface service = retrofit.create(BaseAPI.UserResourceInterface.class);
        Call<User> userCall = service.get(uid, embedded);
        userCall.enqueue(callback);
    }

    public void updateInfo(com.optimussoftware.db.User user, String oldFile, Callback<GenericResponse> callback) {
        MultipartBody.Part picture = null;
        OkHttpClient.Builder httpClient = BaseAPI.getOkHttpClientWithETAG(user.get_etag());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URI)
                .addConverterFactory(GsonConverterFactory.create(BaseAPI.dateFormatGsonWrite()))
                .client(httpClient.build())
                .build();

        if (oldFile != null && oldFile.compareTo(user.getProfile_photo()) != 0) {
            File file = new File(user.getProfile_photo());
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);
            picture = MultipartBody.Part.createFormData("profile_photo", file.getName(), requestFile);
        }

        BaseAPI.UserResourceInterface service = retrofit.create(BaseAPI.UserResourceInterface.class);
        Call<GenericResponse> editCall = service.update(picture, user.get_id(), user.getEmail(),
                user.getFull_name(), user.getGender(),
                user.getBirthday(), user.getPhone());
        editCall.enqueue(callback);
    }

    public void updateBirthdayAndGender(com.optimussoftware.db.User user,String user_id,String _etag, Callback<GenericResponse> callback) {

        OkHttpClient.Builder httpClient = BaseAPI.getOkHttpClientWithETAG(_etag);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URI)
                .addConverterFactory(GsonConverterFactory.create(BaseAPI.dateFormatGson()))
                .client(httpClient.build())
                .build();

        BaseAPI.UserResourceInterface service = retrofit.create(BaseAPI.UserResourceInterface.class);
        Call<GenericResponse> editCall = service.updateBirthdayGender(user_id, user);
        editCall.enqueue(callback);
    }

    public void updateWishUser(WishUpdate wishUpdate, String user_id, String etag, Callback<GenericResponse> callback)  {
        OkHttpClient.Builder httpClient = BaseAPI.getOkHttpClientWithETAG(etag);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URI)
                .addConverterFactory(GsonConverterFactory.create(BaseAPI.dateFormatGson()))
                .client(httpClient.build())
                .build();

        BaseAPI.UserResourceInterface service = retrofit.create(BaseAPI.UserResourceInterface.class);
        Call<GenericResponse> editCall = service.updateWish(user_id, wishUpdate);
        editCall.enqueue(callback);
    }



}
