package com.example.vent.api;

import com.example.vent.dto.WifiDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface WifiApi {

    @POST("/wifi_info")
    Call<String> sendWifiInfo(@Body WifiDto wifiDto);
}
