package com.optimussoftware.api;

/**
 * Created by william.castillo@optimus-software.com on 10/06/16.
 */

import com.optimussoftware.api.response.GenericResponse;
import com.optimussoftware.api.core.BaseAPI;
import com.optimussoftware.db.User;
import com.optimussoftware.boohos.account.BoohosToken;
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

public class Auth {
    final String TAG = "Herme:Auth";
    Retrofit retrofit;

    public Auth() {
    }

    public void login(User user, Callback<BoohosToken> callback) {
        OkHttpClient.Builder httpClient = BaseAPI.getDefaultOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URI)
                .addConverterFactory(GsonConverterFactory.create(BaseAPI.dateFormatGson()))
                .client(httpClient.build())
                .build();
        BaseAPI.AuthInterface service = retrofit.create(BaseAPI.AuthInterface.class);
        Call<BoohosToken> loginCall = service.login(user);
        loginCall.enqueue(callback);
    }

    public BoohosToken loginSync(User user) {
        try {
            OkHttpClient.Builder httpClient = BaseAPI.getDefaultOkHttpClient();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.API_URI)
                    .addConverterFactory(GsonConverterFactory.create(BaseAPI.dateFormatGson()))
                    .client(httpClient.build())
                    .build();
            BaseAPI.AuthInterface service = retrofit.create(BaseAPI.AuthInterface.class);
            Call<BoohosToken> loginCall = service.login(user);
            BoohosToken boohosToken = loginCall.execute().body();
            if (boohosToken != null)
                return boohosToken;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void signUp(User user, Callback<GenericResponse> callback) {
        OkHttpClient.Builder httpClient = BaseAPI.getDefaultOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URI)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        File file = new File(user.getProfile_photo());
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part picture =
                MultipartBody.Part.createFormData("profile_photo", file.getName(), requestFile);

        BaseAPI.AuthInterface service = retrofit.create(BaseAPI.AuthInterface.class);
        Call<GenericResponse> signUpCall = service.signup(picture,
                user.getEmail(), user.getPassword(),
                user.getFirst_name(), user.getLast_name());
        signUpCall.enqueue(callback);
    }

}
