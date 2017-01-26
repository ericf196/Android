package com.optimussoftware.api;

import com.optimussoftware.api.core.BaseAPI;
import com.optimussoftware.api.response.GenericResponse;
import com.optimussoftware.api.response.devices.DevicesList;
import com.optimussoftware.db.Devices;
import com.optimussoftware.boohos.data.Constants;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by leonardojpr on 23/08/16.
 */
public class DevicesResource {

    public void device(Devices devices, Callback<GenericResponse> callback) {
        OkHttpClient.Builder httpClient = BaseAPI.getDefaultOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URI)
                .addConverterFactory(GsonConverterFactory.create(BaseAPI.dateFormatGson()))
                .client(httpClient.build())
                .build();
        BaseAPI.DevicesInterface service = retrofit.create(BaseAPI.DevicesInterface.class);
        Call<GenericResponse> dislikeCall = service.device(devices);
        dislikeCall.enqueue(callback);
    }

    public void remove(Devices devices, Callback<Void> callback) {
        OkHttpClient.Builder httpClient = BaseAPI.getOkHttpClientWithETAG(devices.get_etag());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URI)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        BaseAPI.DevicesInterface service = retrofit.create(BaseAPI.DevicesInterface.class);
        Call<Void> devicesCall = service.remove(devices.get_id());
        devicesCall.enqueue(callback);
    }

    public void list(int page, String location_id, Callback<DevicesList> callback) {
        String where = "\"location_id\":\"" + location_id + "\"";
        where = "{" + where + "}";
        OkHttpClient.Builder httpClient = BaseAPI.getDefaultOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URI)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        BaseAPI.DevicesInterface service = retrofit.create(BaseAPI.DevicesInterface.class);
        Call<DevicesList> interestCall = service.list(page, where);
        interestCall.enqueue(callback);
    }
}
