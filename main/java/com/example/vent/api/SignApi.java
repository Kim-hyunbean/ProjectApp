package com.example.vent.api;

import com.example.vent.dto.LoginDto;
import com.example.vent.dto.SignDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface SignApi {

        //   @FormUrlEncoded
        //  @Headers("Content-Type: charset=EUC-KR;")
        //@POST("memb_post.aspx")
        @FormUrlEncoded
        @POST
        Call<SignDto> userSign(@Url String url,
                               @Field(value = "s0", encoded = true) String s0,
                               @Field(value = "s1", encoded = true) String s1,
                               @Field(value = "s2", encoded = true) String s2, @Field("s3") String s3,
                               @Field("s4") String s4, @Field(value = "s5",encoded = true) String s5,
                               @Field("s6") String s6, @Field(value = "s7",encoded = true) String s7,
                               @Field("u1") String u1);
//    @GET
//    Call<SignDto> userSign(@Url String url,
//                           @Query(value = "s0", encoded = true) String s0, @Query(value = "s1", encoded = true) String s1,
//                           @Query(value = "s2", encoded = true) String s2, @Query("s3") String s3,
//                           @Query("s4") String s4, @Query(value = "s5",encoded = true) String s5,
//                           @Query("s6") String s6, @Query(value = "s7",encoded = true) String s7,
//                           @Query("u1") String u1);
// @GET("memb_post.aspx")
//    @POST("memb_post.aspx")
//    Call<SignDto> userSign(@Body SignDto signDto);

}
