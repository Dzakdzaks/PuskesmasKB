package com.developer.kb.kb.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InitLibrary {
//    public static Retrofit setInit(){
//        return new Retrofit.Builder().baseUrl("http://192.168.0.108/newkb/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//    }

    public static Retrofit setInit(){
        return new Retrofit.Builder().baseUrl("http://192.168.43.17/newkb/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ApiService getInstance(){
        return setInit().create(ApiService.class);
    }
}
