package com.example.jainam_vacation.helper;

import android.app.Application;

import com.example.jainam_vacation.network.RetrofitClient;

public class CurrentApplication extends Application {
    static CurrentApplication applicationInstance;

    public CurrentApplication() {
        applicationInstance = this;
    }

    public static CurrentApplication getInstance() {
        if (applicationInstance == null){
            applicationInstance = new CurrentApplication();
        }
        return applicationInstance;
    }
}
