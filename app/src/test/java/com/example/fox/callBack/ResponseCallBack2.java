package com.example.fox.callBack;

import android.support.annotation.NonNull;


/**
 * Created by magicfox on 2017/5/23.
 */

public interface ResponseCallBack2<T> {
    /**
     * 开始请求回调
     */
    void onStart();

    /**
     * 请求成功回调
     * @param result
     */
    void onSuccess(T result);

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
