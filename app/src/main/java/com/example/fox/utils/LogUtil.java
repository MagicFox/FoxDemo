package com.example.fox.utils;

import android.util.Log;

import com.example.fox.BuildConfig;


public class LogUtil {
    private LogUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    // 是否需要打印bug，在App的debug版本里会打印日志，而在release则不会，使用Gradle自动管理
    public static final boolean isDebug = BuildConfig.DEBUG;

    private static final String TAG = "Log_________";

    // 下面四个是默认tag的函数
    public static void i(Object msg) {
        if (isDebug)
            Log.i(TAG, JsonUtil.toJson(msg));
    }

    public static void d(Object msg) {
        if (isDebug)
            Log.d(TAG, JsonUtil.toJson(msg));
    }

    public static void e(Object msg) {
        if (isDebug)
            Log.e(TAG, JsonUtil.toJson(msg));
    }

    public static void v(Object msg) {
        if (isDebug)
            Log.v(TAG, JsonUtil.toJson(msg));
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, Object msg) {
        if (isDebug)
            Log.i(tag, JsonUtil.toJson(msg));
    }

    public static void d(String tag, Object msg) {
        if (isDebug)
            Log.d(tag, JsonUtil.toJson(msg));
    }

    public static void e(String tag, Object msg) {
        if (isDebug)
            Log.e(tag, JsonUtil.toJson(msg));
    }

    public static void v(String tag, Object msg) {
        if (isDebug)
            Log.v(tag, JsonUtil.toJson(msg));
    }
}
