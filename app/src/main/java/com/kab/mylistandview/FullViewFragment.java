package com.kab.mylistandview;

import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Kraskovskiy on 26.07.2016.
 */
public class FullViewFragment extends Fragment implements MyBitmapCallback {
    private ImageView mImageBackground;
    private static final int MAX_LINE_COUNT = 5;
    private ImageView mImageLike;
    private TextView mTextFullDescription;
    private ProgressBar mProgressBar;
    public static Bitmap sBitmap;
    public static Images sImage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_full_view, null);

        sImage = ListFragment.sImage;
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        Button btnExpand = (Button) view.findViewById(R.id.btnExpand);
        btnExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collapseExpandTextView(mTextFullDescription);
            }
        });

        TextView textTitle = (TextView) view.findViewById(R.id.text_Title);
        TextView textNameAuthor = (TextView) view.findViewById(R.id.text_Name_Author);
        TextView textDate = (TextView) view.findViewById(R.id.text_Date);
        TextView textNumberLikes = (TextView) view.findViewById(R.id.text_Number_Likes);

        mTextFullDescription = (TextView) view.findViewById(R.id.text_Full_Description);
        mTextFullDescription.setMaxLines(MAX_LINE_COUNT);
        mTextFullDescription.setEllipsize(TextUtils.TruncateAt.END);

        mImageBackground = (ImageView) view.findViewById(R.id.imageBackground);
        mImageLike = (ImageView) view.findViewById(R.id.imageLike);
        mImageLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateLikeInImage();
                setLikeImage();
            }
        });

        if (sImage !=null) {
            textTitle.setText(String.format(getResources().getString(R.string.title), sImage.getName()));
            textNameAuthor.setText(String.format(getResources().getString(R.string.name_author), sImage.getAuthor()));
            textDate.setText(String.format(getResources().getString(R.string.date), sImage.getDate()));
            textNumberLikes.setText(String.format(getResources().getString(R.string.number_of_likes), sImage.getNumberLike()));
            mTextFullDescription.setText(String.format(getResources().getString(R.string.full_description), sImage.getDescription()));
        }

        if (sBitmap == null) {
            getImagesFromUrl();
        } else {
            mImageBackground.setImageBitmap(sBitmap);
            mProgressBar.setVisibility(View.INVISIBLE);
            mImageLike.setVisibility(View.VISIBLE);
        }

        setLikeImage();

        setRetainInstance(true);
        return view;
    }


    public void getImagesFromUrl(){
        GetImage getImage = new GetImage();
        getImage.getImages(sImage.getUrl(), this);
    }


        public void updateLikeInImage(){
            DB db = new DB(getActivity().getApplicationContext());
            db.open();
            if (sImage.getMyLike()==0){
                sImage.setMyLike(1);
                db.update(sImage.getMyLike(), sImage.getId());
            } else {
                sImage.setMyLike(0);
                db.update(sImage.getMyLike(), sImage.getId());
            }
            db.close();
        }



    private void collapseExpandTextView(TextView tv) {

        if (tv.getMaxLines() == MAX_LINE_COUNT) {
            // collapsed - expand it
            tv.setEllipsize(null);
            tv.setMaxLines(Integer.MAX_VALUE);
        } else {
            // expanded - collapse it
            tv.setEllipsize(TextUtils.TruncateAt.END);
            tv.setMaxLines(MAX_LINE_COUNT);
        }

        ObjectAnimator animation = ObjectAnimator.ofInt(tv, "maxLines", tv.getMaxLines());
        animation.setDuration(200).start();
    }

    private void setLikeImage(){
        if (sImage.getMyLike() == 0) {
            mImageLike.setImageResource(R.drawable.like_grey);
        } else {
            mImageLike.setImageResource(R.drawable.like_orange);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void callbackBitmapIsLoad(final Bitmap bitmap) {
        Log.e("TAG", "callbackBitmapIsLoad: " + "DONE!");
        sBitmap = bitmap;
        if (getActivity() != null) {

            Handler myHandler = new Handler(getActivity().getApplicationContext().getMainLooper());
            myHandler.post(new Runnable() {

                @Override
                public void run() {
                    mImageBackground.setImageBitmap(bitmap);
                    mProgressBar.setVisibility(View.INVISIBLE);
                    mImageLike.setVisibility(View.VISIBLE);
                    setLikeImage();
                }
            });
        }

    }
}


