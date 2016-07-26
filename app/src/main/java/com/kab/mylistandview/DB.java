package com.kab.mylistandview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Kraskovskiy on 26.07.2016.
 */
public class DB {
    private final Context mContext;
    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;

    public DB(Context context) {
        mContext = context;
    }

    public void open() {
        mDBHelper = new DBHelper(mContext);
        mDB = mDBHelper.getWritableDatabase();
    }

    public void close() {
        if (mDBHelper!=null) mDBHelper.close();
    }

    public Cursor getAllData() {
        return mDB.query(DBHelper.TABLE_NAME, null, null, null, null, null, null);
    }

    public void append(Images images) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COLUMN_AUTHOR, images.getAuthor());
        cv.put(DBHelper.COLUMN_DESCRIPTION, images.getDescription());
        cv.put(DBHelper.COLUMN_NUMBER_OF_LIKES, images.getNumberLike());
        cv.put(DBHelper.COLUMN_NAME, images.getName());
        cv.put(DBHelper.COLUMN_DATE, images.getDate());
        cv.put(DBHelper.COLUMN_URL, images.getUrl());
        cv.put(DBHelper.COLUMN_ID, images.getId());
        cv.put(DBHelper.COLUMN_MYLIKE, "0");
        mDB.insert(DBHelper.TABLE_NAME, null, cv);
        mContext.getContentResolver().notifyChange(DBHelper.URI_TABLE_NAME, null);
    }

    public void delete(long id) {
        mDB.delete(DBHelper.TABLE_NAME, DBHelper.COLUMN_ID + " = " + id, null);
        mContext.getContentResolver().notifyChange(DBHelper.URI_TABLE_NAME, null);
    }

    public void dbTrunc()
    {
        mDB.execSQL("DROP TABLE " + DBHelper.TABLE_NAME);
        mDB.execSQL(DBHelper.DB_CREATE_STRING);
        mContext.getContentResolver().notifyChange(DBHelper.URI_TABLE_NAME, null);
    }

    public void clearAll()
    {
        mDB.delete(DBHelper.TABLE_NAME, null, null);
        mContext.getContentResolver().notifyChange(DBHelper.URI_TABLE_NAME, null);
    }
}
