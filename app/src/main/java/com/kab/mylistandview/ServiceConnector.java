package com.kab.mylistandview;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Kraskovskiy on 26.07.2016.
 */
public class ServiceConnector {

    private static String API_BASE_URL = "http://testokpo.net23.net";

   // private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build();


    public static MyJSONService createService() {
        MyJSONService service = retrofit.create(MyJSONService.class);
        return  service;
    }


    /*public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass);
    }*/

}
