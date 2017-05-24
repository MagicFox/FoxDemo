package com.example.fox.http;

import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by magicfox on 2017/4/27.
 */
public class RetrofitManager {

    private static Retrofit singleton;

    private static class RetrofitManagerHolder{
        static RetrofitManager instance = new RetrofitManager();
    }
    private RetrofitManager(){}

    public static RetrofitManager getInstance() {
        return RetrofitManagerHolder.instance;
    }
    /**
     * create api,default GsonConverterFactory
     */
    public <T> T createApi(Class<T> clazz) {
        return createApi(clazz, GsonConverterFactory.create());
    }
    /**
     * create api,custom converter factory
     */
    public <T> T createApi(Class<T> clazz, Converter.Factory converterFactory) {
        if (singleton == null) {
            synchronized (RetrofitManager.class) {
                singleton = new Retrofit.Builder()
                        .baseUrl(Api.BASE_URL)
                        .addConverterFactory(converterFactory)
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .client(OkHttpManager.getInstance().getDefaultClient())
                        .build();
            }
        }
        return singleton.create(clazz);
    }
}
