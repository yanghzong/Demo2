package com.example.demo2.application;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

public class MyApplication  extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
