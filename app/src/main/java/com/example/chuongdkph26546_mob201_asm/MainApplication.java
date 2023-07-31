package com.example.chuongdkph26546_mob201_asm;

import android.app.Application;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PrefUtil.get(getApplicationContext());
    }
}
