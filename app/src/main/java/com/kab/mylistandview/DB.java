package com.kab.mylistandview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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

    public Images readItem(int id) {
        Cursor cursor = mDB.query(DBHelper.TABLE_NAME, null, DBHelper.COLUMN_ID + " = " + id, null, null, null, null);
        if (cursor.moveToFirst()) {
            int itemMylike_index = cursor.getColumnIndexOrThrow(DBHelper.COLUMN_MYLIKE);
            int itemDescription_index = cursor.getColumnIndexOrThrow(DBHelper.COLUMN_DESCRIPTION);
            int itemID_index = cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ID);
            int itemAuthor_index = cursor.getColumnIndexOrThrow(DBHelper.COLUMN_AUTHOR);
            int itemDate_index = cursor.getColumnIndexOrThrow(DBHelper.COLUMN_DATE);
            int itemName_index = cursor.getColumnIndexOrThrow(DBHelper.COLUMN_NAME);
            int itemNumberLikes = cursor.getColumnIndexOrThrow(DBHelper.COLUMN_NUMBER_OF_LIKES);
            int itemUrl_index = cursor.getColumnIndexOrThrow(DBHelper.COLUMN_URL);

            Images image = new Images(cursor.getInt(itemID_index), cursor.getString(itemName_index),
                    cursor.getString(itemUrl_index), cursor.getInt(itemNumberLikes),
                    cursor.getString(itemAuthor_index), cursor.getString(itemDate_index),
                    cursor.getString(itemDescription_index), cursor.getInt(itemMylike_index));

            cursor.close();

            return image;
        } else {
            cursor.close();
            return null;
        }

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

    public int update(int values, int whereClause) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COLUMN_MYLIKE, values);
        mContext.getContentResolver().notifyChange(DBHelper.URI_TABLE_NAME, null);
        return mDB.update(DBHelper.TABLE_NAME, cv, DBHelper.COLUMN_ID+" = "+whereClause, null);
    }

    public void delete(long id) {
        mDB.delete(DBHelper.TABLE_NAME, DBHelper.COLUMN_ID + " = " + id, null);
        mContext.getContentResolver().notifyChange(DBHelper.URI_TABLE_NAME, null);
    }

    public int getCount() {
        String countQuery = "SELECT  * FROM " + DBHelper.TABLE_NAME;
        Cursor cursor = mDB.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

    public void dbTrunc()
    {
        mDB.execSQL("DROP TABLE " + DBHelper.TABLE_NAME);
        mDB.execSQL(DBHelper.DB_CREATE_STRING);
        mContext.getContentResolver().notifyChange(DBHelper.URI_TABLE_NAME, null);
    }

}
