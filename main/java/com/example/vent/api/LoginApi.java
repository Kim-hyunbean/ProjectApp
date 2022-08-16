package com.example.vent.api;

import com.example.vent.dto.LoginDto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LoginApi {

    @GET("applogin.aspx")
    Call<LoginDto> login(@Query("uid") String uid, @Query("upwd") String upwd);
}
