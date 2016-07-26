package com.kab.mylistandview;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by Kraskovskiy on 26.07.2016.
 */
public class CustomListAdapter extends SimpleCursorAdapter {
    private Context mContext;
    private int mLayout;
    private Cursor mCursor;
    private final LayoutInflater mInflater;

    public CustomListAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        mLayout =layout;
        mContext = context;
        mInflater =LayoutInflater.from(context);
        mCursor =c;
    }

    @Override
    public View newView (Context context, Cursor cursor, ViewGroup parent) {
        return mInflater.inflate(mLayout, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);

    }
}

