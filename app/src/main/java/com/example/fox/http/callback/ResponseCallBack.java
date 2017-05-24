package com.example.fox.http.callback;

import android.support.annotation.NonNull;

import com.example.fox.model.DataResult;


/**
 *
 */
public interface ResponseCallBack<T> {

    /**
     * 开始请求回调
     */
    void onStart();

    /**
     * 请求成功回调
     * @param result
     */
    void onSuccess(DataResult result);

    /**
     * 请求失败回调
     * @param errorMsg
     */
    void onFailure(String errorMsg);

    /**
     * 泛型解析的类型
     * @return
     */
    @NonNull
    Class<T> getType();

    java.lang.reflect.Type getType2();

}
