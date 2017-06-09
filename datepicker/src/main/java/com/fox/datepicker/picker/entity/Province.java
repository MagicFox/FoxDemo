package com.fox.datepicker.picker.entity;


import java.util.List;

/**
 * Created by magicfox on 2017/6/8.
 */

public class Province implements LinkageFirst<City> {

    @Override
    public List<City> getSeconds() {
        return city;
    }

    public List<City> city;

    @Override
    public Object getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getKeyCode() {
        return id;
    }

    public String id;       //"id": "100001",当前地名对应的编号
    public String name;     //"name": "中国",
    public String type;     //"type": 1=国家；2=省份；3=市区；4=区域(县城)
    public String parentId; //"parentId": "0"  当前地名对应其所属地的Id

}
