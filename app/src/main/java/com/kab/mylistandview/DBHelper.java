package com.kab.mylistandview;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

/**
 * Created by Kraskovskiy on 26.07.2016.
 */
public class DBHelper  extends SQLiteOpenHelper {

    private static final int NUMBER_OF_VERSION_DB = 1;

    public static final String DATABASE_NAME = "listImages";
    public static final String TABLE_NAME = "dataTable";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_URL = "myLike";
    public static final String COLUMN_NUMBER_OF_LIKES = "nLikes";
    public static final String COLUMN_AUTHOR = "author";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_MYLIKE = "myLike";





    public static final Uri URI_TABLE_NAME = Uri.parse("sqlite://com.kab.mylistandview/table/" + TABLE_NAME);

    public static final String DB_CREATE_STRING = "create table "+ TABLE_NAME+ " ("
            + COLUMN_ID+" integer primary key autoincrement,"
            + COLUMN_NAME+" text,"
            + COLUMN_URL +" text,"
            + COLUMN_NUMBER_OF_LIKES +" text,"
            + COLUMN_AUTHOR +" text,"
            + COLUMN_DATE +" text,"
            + COLUMN_DESCRIPTION +" text,"
            + COLUMN_MYLIKE+" text"
            +");";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, NUMBER_OF_VERSION_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE_STRING);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}

