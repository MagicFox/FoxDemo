package com.example.fox.mode;

import java.io.Serializable;

/**
 * Created by magicfox on 2017/5/23.
 */

public class TestDetail implements Serializable{
    public String code,name;
    public String toString(){
        return String.format("code=%s,name=%s",code,name);
    }
}
