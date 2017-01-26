package com.optimussoftware.api.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.optimussoftware.api.response.CheckResponse;
import com.optimussoftware.api.response.GenericResponse;
import com.optimussoftware.api.response.advertising.AdvertisingByCampaign.AdvertisingListByCampaign;
import com.optimussoftware.api.response.devices.DevicesList;
import com.optimussoftware.api.response.favorites.FavoritesList;
import com.optimussoftware.api.response.favorites.advertisingFavorite.AdvertisingFavoriteList;
import com.optimussoftware.api.response.interest.InterestList;
import com.optimussoftware.api.response.location.Item;
import com.optimussoftware.api.response.newAdvertising.AdverstingsList;
import com.optimussoftware.api.response.review.NewReview.ReviewResponse;
import com.optimussoftware.api.response.review.ReviewCheck.ReviewCheck;
import com.optimussoftware.api.response.user.WishUpdate;
import com.optimussoftware.api.response.userinterest.UserInterestList;
import com.optimussoftware.boohos.App;
import com.optimussoftware.db.Advertising;
import com.optimussoftware.db.Campaign;
import com.optimussoftware.db.Devices;
import com.optimussoftware.db.Dislikes;
import com.optimussoftware.db.Favorites;
import com.optimussoftware.db.Likes;
import com.optimussoftware.db.Location;
import com.optimussoftware.db.Review;
import com.optimussoftware.db.User;
import com.optimussoftware.db.UserInterest;
import com.optimussoftware.boohos.account.BoohosToken;
import com.optimussoftware.boohos.data.Constants;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by william.castillo@optimus-software.com on 01/07/16.
 * Request hermeapp
 */
public final class BaseAPI {

    public interface AuthInterface {
        @POST("login")
        Call<BoohosToken> login(@Body User user);

        @Multipart
        @POST("signup")
        Call<GenericResponse> signup(@Part MultipartBody.Part file,
                                     @Part("email") String email,
                                     @Part("password") String password,
                                     @Part("first_name") String first_name,
                                     @Part("last_name") String last_name);
    }

    public interface AdvertisingInterface {
        @GET("advertising/{uid}")
        Call<Advertising> get(@Path("uid") String uid);

        /*@GET("advertising")
        Call<AdvertisingListByCampaign> getAdvertisingByCampaign(@Query("q") String id_campaign,
                                                                 @Query("df") String campaign,
                                                                 @Query("embedded") String embeddedImage);*/
    }

    public interface InterestInterface {
        @GET("interest")
        Call<InterestList> get(@Query("page") int page);

    }

    public interface CampaignInterface {
        @GET("campaign/{uid}")
        Call<Campaign> get(@Path("uid") String uid);

        @GET("campaign/{uid}")
        Call<com.optimussoftware.api.response.campaign.Item> getCampaign(@Path("uid") String uid);

    }

    public interface LocationInterface {
        @GET("location/{uid}")
        Call<Location> get(@Path("uid") String uid);

        @GET("location/{uid}")
        Call<com.optimussoftware.api.response.location.Location> getLocation(@Path("uid") String uid);

        @GET("location/{uid}")
        Call<Item> list(@Path("uid") String uid);
    }

    public interface LastOffertsInterface {
        @GET("lasted_deals")
        Call<AdverstingsList> offerts(
                @Query("uuid") String device_uid,
                @Query("debug") String debug);
    }

    public interface GrowHackingInterface {

    }

    /*public interface ImageInterface {
        @GET("image/{uid}")
        Call<com.optimussoftware.api.response.advertising.Image> get(@Path("uid") String uid);

        @GET("image")
        Call<com.optimussoftware.api.response.advertising.Image> sync(
                @Query("q") String condition);

    }*/

    public interface DevicesInterface {
        @GET("device")
        Call<DevicesList> list(@Query("page") int page,
                               @Query("where") String location_id);

        @POST("device")
        Call<GenericResponse> device(@Body Devices device);

        @DELETE("device/{uid}")
        Call<Void> remove(@Path("uid") String uid);
    }


    public interface UserInterestInterface {
        @GET("user/wish")
        Call<UserInterestList> list(
                @Query("page") int page,
                @Query("where") String condition);

        @POST("user/wish")
        Call<GenericResponse>
        create(@Body UserInterest userInterest);

        @DELETE("user/wish/{uid}")
        Call<Void> remove(@Path("uid") String uid);

    }

    public interface UserResourceInterface {
        @GET("user/{uid}")
        Call<com.optimussoftware.api.response.user.User> get(@Path("uid") String uid,
                                                             @Query("embedded") String mac);

        //        @PATCH("user/{id}")
//        @Multipart
//        Call<GenericResponse> update(@Part MultipartBody.Part file,
//                                     @Path("id") String id,
//                                     @Part("full_name") String full_name,
//                                     @Part("gender") String gender,
//                                     @Part("birthday") Date birthday,
//                                     @Part("phone") String phone);
        @Multipart
        @PATCH("profile_update")
        Call<GenericResponse> update(@Part MultipartBody.Part file,
                                     @Part("user_id") String user_id,
                                     @Part("email") String email,
                                     @Part("full_name") String full_name,
                                     @Part("gender") String gender,
                                     @Part("birthday") Date birthday,
                                     @Part("phone") String phone);

        @PATCH("user/{uid}")
        Call<GenericResponse> updateBirthdayGender(@Path("uid") String user_id,
                                                   @Body User user);

        @PATCH("user/{uid}")
        Call<GenericResponse> updateWish(@Path("uid") String user_id,
                                         @Body WishUpdate update);


    }

    public interface LikeResourceInterface {
        @POST("user/like")
        Call<GenericResponse> like(@Body Likes likes);

        @POST("user/like/check")
        Call<CheckResponse> check(@Body Likes likes);

        @DELETE("user/like/{uid}")
        Call<Void> remove(@Path("uid") String uid);

    }

    public interface DislikeResourceInterface {
        @POST("user/dislike")
        Call<GenericResponse> dislike(@Body Dislikes dislikes);

        @POST("user/dislike/check")
        Call<CheckResponse> check(@Body Dislikes dislikes);

        @DELETE("user/dislike/{uid}")
        Call<Void> remove(@Path("uid") String uid);
    }

    public interface FavoriteResourceInterface {

        @GET("user/favorite")
        Call<AdvertisingFavoriteList> getAdvertisingFavorite(@Query("where") String advertising_id,
                                                             @Query("embedded") String mac,
                                                             @Query("page") int page);
        /*@GET("user/favorite")
        Call<AdvertisingFavoriteList> getAdvertisingFavorite(@Query("q") String advertising_id,
                                                             @Query("df") String advertising,
                                                             @Query("embedded") String mac,
                                                             @Query("page") int page);*/

        @POST("user/favorite")
        Call<GenericResponse> favorite(@Body Favorites favorites);


        @POST("user/favorite/check")
        Call<CheckResponse> check(@Body Favorites favorites);

        @DELETE("user/favorite/{uid}")
        Call<Void> remove(@Path("uid") String uid);

        @GET("user/favorite")
        Call<FavoritesList> list(@Query("where") String favorite_id,
                                 @Query("embedded") String embedded,
                                 @Query("page") int page);
    }

    public interface RecoveryInterface {
        @POST("recovery")
        Call<GenericResponse> recovery(@Body User recovery);
    }

    public interface ReviewResourceInterface {
        @POST("advertising/review")
        Call<GenericResponse> create(@Body Review review);

        @PATCH("advertising/review/{id}")
        Call<GenericResponse> edit(@Path("id") String id,
                                   @Body Review review);

        @DELETE("advertising/review/{id}")
        Call<Void> remove(@Path("id") String uid);

        @POST("advertising/review/check")
        Call<ReviewCheck> checkReview(@Body Review review);

        @GET("advertising/review/")
        Call<ReviewResponse> getReviewByAdvertising(@Query("where") String advertising_id,
                                                    @Query("embedded") String mac,
                                                    @Query("page") int page);

        /*@GET("advertising/review/")
        Call<ReviewResponse> getReviewByAdvertising(@Query("q") String advertising_id,
                                                    @Query("df") String advertising,
                                                    @Query("embedded") String mac,
                                                    @Query("page") int page);*/
    }


    public static OkHttpClient.Builder getDefaultOkHttpClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        //.addHeader("authorization", Constants.BOOHOS_TOKEN).build();
                        .addHeader("authorization", App.getTokenBoohos()).build();
                return chain.proceed(request);
            }
        });
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);
        return httpClient;
    }

    public static OkHttpClient.Builder getOkHttpClientWithETAG(final String ifMatch) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .addHeader("If-Match", ifMatch)
                        .build();
                return chain.proceed(request);
            }
        });
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);
        return httpClient;
    }

    public static Gson dateFormatGson() {
        return new GsonBuilder().setDateFormat(Constants.DATE_FORMAT).create();
    }

    public static Gson dateFormatGsonWrite() {
        return new GsonBuilder().setDateFormat(Constants.DATE_FORMAT_WRITE).create();
    }

}
