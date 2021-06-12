package com.example.jainam_vacation.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient instance = null;
    private API api;

    public static synchronized RetrofitClient getInstance() {
        if (instance == null){
            instance = new RetrofitClient();
        }
        return instance;
    }

    public API getApi() {
        return api;
    }

    private RetrofitClient(){
        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        api = retrofit.create(API.class);
    }
}