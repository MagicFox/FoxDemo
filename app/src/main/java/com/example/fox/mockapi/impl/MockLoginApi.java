package com.example.fox.mockapi.impl;

import com.example.fox.http.callback.ResponseCallBack;
import com.example.fox.mockapi.BaseMockApi;
import com.example.fox.mockapi.IMockApiStrategy;
import com.example.fox.model.login.ADItem;
import com.example.fox.model.login.LoginResult;
import com.example.fox.present.user.ILogin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.functions.Func0;

/**
 * Created by magicfox on 2017/5/19.
 */

public class MockLoginApi extends BaseMockApi implements IResponse{

    public void login(String url,Map<String, Object> map, final ResponseCallBack callback) {
        final IMockApiStrategy.Response response = onResponse();
        if (response.state == IMockApiStrategy.Response.STATE_SUCCESS) {
            Func0<LoginResult> mockTasks = new Func0<LoginResult>() {
                @Override
                public LoginResult call() {
                    LoginResult result = new LoginResult();
                    switch (mCallCount){
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 6:
                            result.adList = getList(mCallCount);
                            break;
                    }
                    return result;
                }
            };
            giveSuccessResult(mockTasks, callback, response);
        } else {
            giveErrorResult(callback, response);
        }
    }

    List<ADItem> list = new ArrayList<>();
    ADItem item;
    private List<ADItem> getList(int size){
        list.clear();
        for(int i =0;i<size;i++){
            item = new ADItem();
            item.url = "http://baidu.com";
            item.name = "测试链接"+i;
            list.add(item);
        }
        return list;
    }

}
