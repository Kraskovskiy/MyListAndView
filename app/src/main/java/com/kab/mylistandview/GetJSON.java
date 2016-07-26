package com.kab.mylistandview;

import android.content.Context;
import android.content.ServiceConnection;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Kraskovskiy on 26.07.2016.
 */
public class GetJSON {

    private MyJSONService myJSONService;
    private Context mContext;

    public GetJSON(Context context) {
        myJSONService = ServiceConnector.createService();
        mContext = context;
    }

    public void getJSONList() {
        Call<List<Images>> call = myJSONService.listImages();
        call.enqueue(new Callback<List<Images>>() {
            @Override
            public void onResponse(Call<List<Images>> call, Response<List<Images>> response) {
                Log.e("getJSONList","getJSONList = " + response.headers()+"\n" + response.message());
                Log.e("getJSONList","JSONList = " + response.body().get(0).getDescription());
              }

            @Override
            public void onFailure(Call<List< Images>> call, Throwable t) {
                Log.e("getJSONList","t = " + t.getMessage());

            }
        });
    }



  /*  public void getJSON(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("GetImagesonFailure", e.toString());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {

                    Log.e("TAG", "onResponse: "+response.body().string() );

                } catch (OutOfMemoryError e) {

                }
                Log.e("GetImagesonResponse",response.message());
            }
        });
    }
*/
}
