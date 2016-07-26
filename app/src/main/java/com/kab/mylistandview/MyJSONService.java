package com.kab.mylistandview;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
/**
 * Created by Kraskovskiy on 26.07.2016.
 */
public interface MyJSONService {
    @GET("/images.json")
    Call<List<Images>> listImages();
}
