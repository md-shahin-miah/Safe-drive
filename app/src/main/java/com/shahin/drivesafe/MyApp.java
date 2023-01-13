package com.shahin.drivesafe;

import android.app.Application;
import android.content.Context;

public class MyApp extends Application {

    public static int ck = 1;

    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();

        context =  getApplicationContext();

    }


    public static Context getContext(){
        return context;
    }




}

