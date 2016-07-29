package com.kab.mylistandview;

import android.content.Context;
import android.util.Log;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Kraskovskiy on 26.07.2016.
 */
public class JsonDownloader {
    private MyJsonService mJsonService;
    private Context mContext;

    public JsonDownloader(Context context) {
        mJsonService = ServiceConnector.createService();
        mContext = context;
    }

    public void getJSONList() {
        Call<List<Images>> call = mJsonService.listImages();
        call.enqueue(new Callback<List<Images>>() {
            @Override
            public void onResponse(Call<List<Images>> call, Response<List<Images>> response) {
                Log.e("getJSONList","getJSONList = " + response.headers()+"\n" + response.message());
                saveResponseToDB(response.body());
              }

            @Override
            public void onFailure(Call<List< Images>> call, Throwable t) {
                Log.e("getJSONList","t = " + t.getMessage());

            }
        });
    }

    private void saveResponseToDB(List<Images> images) {
        DB db = new DB(mContext);
        db.open();
        db.dbTrunc();

        for (Images item : images) {
            db.append(item);
        }

        db.close();
    }
}
