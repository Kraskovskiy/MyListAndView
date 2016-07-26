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
    private DB mDB;
    Images mImage;
    ImageView mImageLike;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_full_view, null);

        mImage = ListFragment.sImage;
        mDB = new DB(getActivity().getApplicationContext());
        mDB.open();

        TextView text_Title = (TextView) view.findViewById(R.id.text_Title);
        TextView text_Name_Author = (TextView) view.findViewById(R.id.text_Name_Author);
        TextView text_Date = (TextView) view.findViewById(R.id.text_Date);
        TextView text_Number_Likes = (TextView) view.findViewById(R.id.text_Number_Likes);
        TextView text_Full_Description = (TextView) view.findViewById(R.id.text_Full_Description);
        mImageBackground = (ImageView) view.findViewById(R.id.imageBackground);
        mImageLike = (ImageView) view.findViewById(R.id.imageLike);
        mImageLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mImage.getMyLike()==0){
                    mImage.setMyLike(1);
                    mDB.update(mImage.getMyLike(),mImage.getId());
                    setLikeImage();
                } else {
                    mImage.setMyLike(0);
                    mDB.update(mImage.getMyLike(),mImage.getId());
                    setLikeImage();
                }

            }
        });

        if (mImage !=null) {
            text_Title.setText(String.format(getResources().getString(R.string.title), mImage.getName()));
            text_Name_Author.setText(String.format(getResources().getString(R.string.name_author), mImage.getAuthor()));
            text_Date.setText(String.format(getResources().getString(R.string.date), mImage.getDate()));
            text_Number_Likes.setText(String.format(getResources().getString(R.string.number_of_likes), mImage.getNumberLike()));
            text_Full_Description.setText(String.format(getResources().getString(R.string.full_description), mImage.getDescription()));
        }

        setLikeImage();

        GetImage getImage = new GetImage();
        getImage.getImages(mImage.getUrl(),this);



        return view;
    }

    public void setLikeImage(){
        if (mImage.getMyLike() == 0) {
            mImageLike.setImageResource(R.drawable.like_grey);
        } else {
            mImageLike.setImageResource(R.drawable.like_orange);
        }
    }


    @Override
    public void callbackBitmapIsLoad(final Bitmap bitmap) {
        Log.e("TAG", "callbackBitmapIsLoad: " + "DONE!" );
        if (getActivity()!=null) {
            Handler myHandler = new Handler(getActivity().getApplicationContext().getMainLooper());

            myHandler.post(new Runnable() {

                @Override
                public void run() {
                    mImageBackground.setImageBitmap(bitmap);
                }
            });
        }

    }
}


