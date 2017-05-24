package com.example.fox.http;

import com.example.fox.common.Constant;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;

/**
 * Created by magicfox on 2017/4/27.
 */

public class OkHttpManager {

    private static OkHttpClient okHttpClient,downloadOkHttpClient;
    private static final int TIME_OUT = 15;//超时 秒作为单位 SECONDS
    private static final int READ_TIME_OUT = 15;//超时秒作为单位 SECONDS
    private static final long SIZE_OF_CACHE = 50 * 1024 * 1024;//缓存大小 50MB
    private static final String API_KEY = "";
    private static final String API_Secret = "";

    static class OkHttpManagerHolder{
        static OkHttpManager instance = new OkHttpManager();
    }


    public static OkHttpManager getInstance(){
        return OkHttpManagerHolder.instance;
    }

    private OkHttpManager(){
        //Mark:oauth目前还没加
//        OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(API_KEY, API_Secret);

        Map<String, String> headerParamsMap = new HashMap<>();
        headerParamsMap.put("DeviceID", Constant.DEVICE_ID);
        headerParamsMap.put("Mac", Constant.MAC);
        headerParamsMap.put("Content-Type", "application/json");
        okHttpClient = new OkHttpClient().newBuilder()
                .addNetworkInterceptor(new StethoInterceptor())
//                .addInterceptor(new BasicParamsInterceptor.Builder()
//                        .addHeaderParamsMap(headerParamsMap)
//                        .build())
//                .addInterceptor(new SigningInterceptor(consumer))
//                .hostnameVerifier(new HostnameVerifier() {
//                    @Override
//                    public boolean verify(String hostname, SSLSession session) {
//                        return true;
//                    }
//                })
                .addInterceptor(new LoggerInterceptor())
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .followRedirects(true)
                .followSslRedirects(true)
                //设置缓存.cache(new Cache(new File(getContext().getCacheDir(), "responses"), SIZE_OF_CACHE))
                .build();

        downloadOkHttpClient = new OkHttpClient().newBuilder()
                .addNetworkInterceptor(new StethoInterceptor())
                .addInterceptor(new BasicParamsInterceptor.Builder()
                        .addHeaderParam("DeviceID", Constant.DEVICE_ID)
                        .addParam("Mac", Constant.MAC)
                        .build())
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .followRedirects(true)
                .followSslRedirects(true)
                .build();
    }

    public OkHttpClient getDefaultClient() {
        return okHttpClient;
    }

    /**
     * 下载文件的okHttp实例，不需要OAuth认证
     * @return OkHttpClient
     */
    public OkHttpClient getDownloadClient(){
        return downloadOkHttpClient;
    }

}
