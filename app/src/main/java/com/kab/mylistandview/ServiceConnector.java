package com.kab.mylistandview;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Kraskovskiy on 26.07.2016.
 */
public class ServiceConnector {
    private static String API_BASE_URL = "http://testokpo.net23.net";
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build();

    public static MyJsonService createService() {
        MyJsonService service = retrofit.create(MyJsonService.class);
        return service;
    }
}
