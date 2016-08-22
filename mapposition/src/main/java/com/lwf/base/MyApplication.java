package com.lwf.base;

import android.app.Application;

/**
 * Created by liwenfei on 16/8/18.
 */
public class MyApplication extends Application {

    private static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override

    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
