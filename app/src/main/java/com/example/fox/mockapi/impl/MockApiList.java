package com.example.fox.mockapi.impl;

import com.example.fox.http.Api;
import com.example.fox.mockapi.BaseMockApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by magicfox on 2017/5/19.
 */

public class MockApiList {
    public Map<String,? extends BaseMockApi> list = new HashMap<>();
    public void init(){
//        list.put(Api.LOGIN,MockLoginApi.class);
    }
}
