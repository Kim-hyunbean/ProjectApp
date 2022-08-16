package com.example.vent.api;

import com.example.vent.dto.SensorDto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SensorApi {

    @GET("applist.aspx")
    Call<SensorDto> getSensor(@Query("u3") String uid);

}
