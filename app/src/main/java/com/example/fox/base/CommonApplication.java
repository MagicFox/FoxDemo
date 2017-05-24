package com.example.fox.base;

import android.app.Application;

/**
 * Created by magicfox on 2017/5/24.
 */

public class CommonApplication extends Application {
    static CommonApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }


    public static CommonApplication getInstance(){
        return instance;
    }
}
