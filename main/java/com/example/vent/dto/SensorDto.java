package com.example.vent.dto;

import com.example.vent.element.SensorRow;
import com.tickaroo.tikxml.annotation.Element;
import com.tickaroo.tikxml.annotation.Path;
import com.tickaroo.tikxml.annotation.Xml;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Xml
@Getter
@Setter
public class SensorDto {

    @Path("rs:data")
    @Element
    private List<SensorRow> rows;

}
