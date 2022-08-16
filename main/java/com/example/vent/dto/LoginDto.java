package com.example.vent.dto;

import com.example.vent.element.LoginRow;
import com.tickaroo.tikxml.annotation.Element;
import com.tickaroo.tikxml.annotation.Path;
import com.tickaroo.tikxml.annotation.Xml;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Xml
@Getter
@Setter
public class LoginDto {

    @Path("rs:data")
    @Element
    private List<LoginRow> rows;

}
