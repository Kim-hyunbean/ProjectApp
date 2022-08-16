package com.example.vent.api;

import com.example.vent.dto.LoginDto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CommandApi {

    @GET("cmdupdate.aspx")
    Call<String> command(@Query("s1") String s1, @Query("s2") String s2);
}
