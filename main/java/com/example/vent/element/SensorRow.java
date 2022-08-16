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
public class SensorRow {

    @Attribute(name = "pid")
    private String pid;

    @Attribute(name = "pserial")
    private String pserial;

    @Attribute(name = "pposi")
    private String pposi;

    @Attribute(name = "pdata")
    private String pdata;

    @Attribute(name = "pdate")
    private String pdate;

    @Attribute(name = "pcommand")
    private String pcommand;

    @Attribute(name = "pinstall")
    private String pinstall;

    @Attribute(name = "user_id")
    private String user_id;

}

//<rs:data>
//<z:row pid="000001" pserial="1234" pposi="원룸 123호" ptitle="원룸 123호" pdata="ALFA509C10E07200000AEA9751000D"
//        pdate="2022-02-01 13:39:12" pcommand="" pinstall="2021-12-30" user_id="FAN001" />
//<z:row pid="000002" pserial="1234" pposi="원룸 123호" ptitle="원룸 123호" pdata="0" pdate="0" pcommand="0"
//        pinstall="2021-12-30" user_id="FAN001" />
//<z:row pid="000003" pserial="1234" pposi="원룸 123호" ptitle="원룸 123호" pdata="0" pdate="123" pcommand="0"
//        pinstall="2021-12-30" user_id="FAN001" />
//<z:row pid="000006" pserial="1234" pposi="원룸 123호" ptitle="원룸 123호" pdata="ALFA50FD0EC0E80000270F2161001D"
//        pdate="123" pcommand="0" pinstall="2021-12-30" user_id="FAN001" />
//<z:row pid="000009" pserial="1234" pposi="원룸 123호" ptitle="원룸 123호" pdata="0" pdate="123" pcommand="0"
//        pinstall="2021-12-30" user_id="FAN001" />
//</rs:data>