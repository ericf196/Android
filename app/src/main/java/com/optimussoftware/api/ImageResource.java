package com.optimussoftware.api;

/**
 * Created by leonardojpr on 10/10/16.
 */

public class ImageResource {

    /*public void get(String uid, Callback<com.optimussoftware.api.response.advertising.Image> callback){
        OkHttpClient.Builder httpClient = BaseAPI.getDefaultOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URI)
                .addConverterFactory(GsonConverterFactory.create(BaseAPI.dateFormatGson()))
                .client(httpClient.build())
                .build();
        BaseAPI.ImageInterface service = retrofit.create(BaseAPI.ImageInterface.class);
        Call<com.optimussoftware.api.response.advertising.Image> imageCall = service.get(uid);
        imageCall.enqueue(callback);
    }

    public Response<com.optimussoftware.api.response.advertising.Image> sync(String uid) throws IOException{
        String where = uid + "=user_id" ;
        OkHttpClient.Builder httpClient = BaseAPI.getDefaultOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URI)
                .addConverterFactory(GsonConverterFactory.create(BaseAPI.dateFormatGson()))
                .client(httpClient.build())
                .build();
        BaseAPI.ImageInterface service = retrofit.create(BaseAPI.ImageInterface.class);
        Call<com.optimussoftware.api.response.advertising.Image> advertisingCall = service.sync(uid);
       return advertisingCall.execute();
    }*/
}
