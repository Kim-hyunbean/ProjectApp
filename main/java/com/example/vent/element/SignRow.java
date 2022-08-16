package com.example.vent.element;

import com.tickaroo.tikxml.annotation.Attribute;
import com.tickaroo.tikxml.annotation.Xml;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Xml(name = "z:row")
public class SignRow {

    @Attribute(name = "uID")
    private String ID;

    @Attribute(name = "uPW")
    private String PW;

    @Attribute(name = "uName")
    private String Name;

    @Attribute(name = "uGrade")
    private String uGrade;

    @Attribute(name = "uOnline")
    private String uOnline;

    @Attribute(name = "uAddress")
    private String uAddress;

    @Attribute(name = "uCallNum")
    private String uCallNum;

    @Attribute(name = "uCompany")
    private String uCompany;

    @Attribute(name = "uCode")
    private String uCode;
}
