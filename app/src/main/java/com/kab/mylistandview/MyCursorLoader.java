package com.kab.mylistandview;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;

/**
 * Created by Kraskovskiy on 26.07.2016.
 */
public class MyCursorLoader extends CursorLoader {
    private DB mDb;

    public MyCursorLoader(Context context, DB db) {
        super(context);
        this.mDb = db;
    }

    @Override
    public Cursor loadInBackground() {
        return mDb.getAllData();
    }
}
