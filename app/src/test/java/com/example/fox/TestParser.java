package com.example.fox;

import android.support.annotation.NonNull;

import com.example.fox.http.callback.ResponseCallBack;
import com.example.fox.mode.TestDetail;
import com.example.fox.model.DataResult;
import com.example.fox.utils.JsonUtil;
import com.example.fox.utils.LogUtil;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.lang.reflect.Type;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

import static org.junit.Assert.assertEquals;

/**
 * Created by magicfox on 2017/5/23.
 */

public class TestParser {

    @Test
    public void testBack() {

        ResponseCallBack<TestDetail> callBack = new ResponseCallBack<TestDetail>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataResult result) {
                LogUtil.d("__________success=" + (result == null));
//                assertEquals(result,null);
                if (result != null) {
//                    assertEquals(result.detail,null);
                    if (result.detail != null) {
                        TestDetail detail = (TestDetail) result.detail;
                        if (detail != null) {
                            assertEquals(detail.code, "test");
                        } else {

                            throw new NullPointerException("null code");
                        }
                    } else {

                        throw new NullPointerException("null detail");
                    }
                } else {
                    throw new NullPointerException("null");
                }
            }

            @Override
            public void onFailure(String errorMsg) {

            }

            @NonNull
            @Override
            public Class getType() {
                return TestDetail.class;
            }

            @Override
            public Type getType2() {
                return new TypeToken<DataResult<TestDetail>>(){}.getType();
            }
        };

        testHandlerBack(TestDetail.class, callBack);

    }

    public <T> void testHandlerBack(T t, final ResponseCallBack callBack) {

        String result = "{code:\"123\",message:\"hello\",detail:{code:\"test\"}}";
        Observable.just(result).map(new Func1<String, DataResult<T>>() {

            @Override
            public DataResult<T> call(String s) {
                return JsonUtil.fromJson(s, new DataResult().getClass().getGenericSuperclass());
            }
        }).doOnNext(new Action1<DataResult<T>>() {
            @Override
            public void call(DataResult<T> tDataResult) {
                callBack.onSuccess(tDataResult);
            }
        });


    }

}
