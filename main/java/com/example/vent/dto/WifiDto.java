package com.example.vent.dto;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WifiDto {

    @SerializedName("ssid")
    private String ssid;

    @SerializedName("password")
    private String password;
}
