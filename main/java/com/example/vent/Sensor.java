package com.example.vent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Sensor {

    private String mode;
    private String indoorTemp;
    private String outdoorTemp;
    private String voc;
    private String co2;
    private String dust;
    private String humidity;
    private String airVolume;
    private String damper;

}
