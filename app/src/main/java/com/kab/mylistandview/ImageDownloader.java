package com.kab.mylistandview;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Kraskovskiy on 26.07.2016.
 */
public class ImageDownloader {
   private MyBitmapCallback mBitmapCallback;

    public void getImages(String url, MyBitmapCallback callback) {
        mBitmapCallback = callback;
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
                    Bitmap bitmap = decodeBitmapFromStream(response.body().byteStream());
                    mBitmapCallback.callbackBitmapIsLoad(bitmap);

                } catch (OutOfMemoryError e) {
                    Log.e("GetImagesonResponse", e.toString());
                }
                Log.i("GetImagesonResponse", response.message());
            }
        });
    }

    public Bitmap decodeBitmapFromStream(InputStream is) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        return BitmapFactory.decodeStream(is, null, options);
    }

}
