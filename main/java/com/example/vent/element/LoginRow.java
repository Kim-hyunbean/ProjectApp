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
public class LoginRow {
    @Attribute(name = "user_id")
    private String user_id;

    @Attribute(name = "user_name")
    private String user_name;

    @Attribute(name = "user_grade")
    private String user_grade;
}
