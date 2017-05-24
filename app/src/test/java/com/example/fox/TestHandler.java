package com.example.fox;

import android.support.annotation.NonNull;

import com.example.fox.callBack.ResponseCallBack2;
import com.example.fox.http.HttpUtils;
import com.example.fox.http.callback.ResponseCallBack;
import com.example.fox.mode.TestDetail;
import com.example.fox.model.DataResult;
import com.example.fox.utils.JsonUtil;
import com.example.fox.utils.LogUtil;
import com.google.gson.reflect.TypeToken;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

import static org.junit.Assert.assertNotNull;


/**
 * Created by magicfox on 2017/5/23.
 */

public class TestHandler extends BasePrint{

    @Before
    public void init(){
        println("------method init called------");
    }

    @Test
    public void testBack2(){

        ResponseCallBack2<DataResult<List<TestDetail>>> callBack = new ResponseCallBack2<DataResult<List<TestDetail>>>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataResult<List<TestDetail>> result) {
                LogUtil.d("__________success="+(result==null));
                println(String.valueOf(result==null));
                Assert.assertNotNull(result);
            }

            @Override
            public void onFailure(String errorMsg) {
                throw new NullPointerException("not null code");

            }

            @NonNull
            @Override
            public Class getType() {
                return TestDetail.class;
            }

            @Override
            public Type getType2() {
                return new TypeToken<DataResult<List<TestDetail>>>(){}.getType();
            }
        };
        testHandlerBack2(callBack);
    }


    public <T> void testHandlerBack2(final ResponseCallBack2<T> callBack){
        final String result = "{code:\"123\",message:\"hello\",detail:[{code:\"test\"},{code:\"test1\"}]}";
        if(callBack == null)return;

        HttpUtils.getInstance().handleResponse(Observable.just(result).asObservable(), new ResponseCallBack<DataResult<List<TestDetail>>>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataResult result) {
                assertNotNull(result);
                println("message="+result.message);
            }

            @Override
            public void onFailure(String errorMsg) {
                println("err message="+errorMsg);

            }

            @NonNull
            @Override
            public Class getType() {
                return TestDetail.class;
            }

            @Override
            public Type getType2() {
                return new TypeToken<DataResult<List<TestDetail>>>(){}.getType();
            }
        });
//        Observable.just(result).map(new Func1<String,T>() {
//
//            @Override
//            public T call(String s) {
//                println(s);
//                return JsonUtil.fromJson(s,callBack.getType2());
//            }
//        }).subscribe(new Action1<T>() {
//            @Override
//            public void call(T result1) {
//                callBack.onSuccess(result1);
//            }
//        });

    }


}
