package com.kab.mylistandview;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Kraskovskiy on 26.07.2016.
 */
public class FullViewFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_full_view, null);

        Images image = ListFragment.sImage;

        TextView text_Title = (TextView) view.findViewById(R.id.text_Title);
        TextView text_Name_Author = (TextView) view.findViewById(R.id.text_Name_Author);
        TextView text_Date = (TextView) view.findViewById(R.id.text_Date);
        TextView text_Number_Likes = (TextView) view.findViewById(R.id.text_Number_Likes);
        TextView text_Full_Description = (TextView) view.findViewById(R.id.text_Full_Description);
        ImageView imageBackground = (ImageView) view.findViewById(R.id.imageBackground);
        ImageView imageLike = (ImageView) view.findViewById(R.id.imageLike);

        if (image!=null) {
            text_Title.setText(image.getName());
            text_Name_Author.setText(image.getAuthor());
            text_Date.setText(image.getDate());
            text_Number_Likes.setText(String.valueOf(image.getNumberLike()));
            text_Full_Description.setText(image.getDescription());
        }

        return view;
    }
}


