package com.kab.mylistandview;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Kraskovskiy on 26.07.2016.
 */
public class FullViewFragment extends Fragment implements MyBitmapCallback {
    ImageView mImageBackground;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_full_view, null);

        Images image = ListFragment.sImage;

        TextView text_Title = (TextView) view.findViewById(R.id.text_Title);
        TextView text_Name_Author = (TextView) view.findViewById(R.id.text_Name_Author);
        TextView text_Date = (TextView) view.findViewById(R.id.text_Date);
        TextView text_Number_Likes = (TextView) view.findViewById(R.id.text_Number_Likes);
        TextView text_Full_Description = (TextView) view.findViewById(R.id.text_Full_Description);
        mImageBackground = (ImageView) view.findViewById(R.id.imageBackground);
        ImageView imageLike = (ImageView) view.findViewById(R.id.imageLike);

        if (image!=null) {
            text_Title.setText(image.getName());
            text_Name_Author.setText(image.getAuthor());
            text_Date.setText(image.getDate());
            text_Number_Likes.setText(String.valueOf(image.getNumberLike()));
            text_Full_Description.setText(image.getDescription());
        }

        if (image.getMyLike().equals("0")) {
            imageLike.setImageResource(R.drawable.like_grey);
        } else {
            imageLike.setImageResource(R.drawable.like_orange);
        }

        GetImage getImage = new GetImage();
        getImage.getImages(image.getUrl(),this);



        return view;
    }

    @Override
    public void callbackBitmapIsLoad(final Bitmap bitmap) {
        Log.e("TAG", "callbackBitmapIsLoad: " + "DONE!" );
        Handler myHandler = new Handler(getActivity().getApplicationContext().getMainLooper());

        myHandler.post(new Runnable() {

            @Override
            public void run() {
                mImageBackground.setImageBitmap(bitmap);
            }
        });

    }
}


