package com.example.lectly;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class Data {

    //private Data(){}

    //table for posts
    public static class Posts implements BaseColumns{
        public static final String TABLE_NAME = "posts";
        public static final String COLUMN_NAME_TITLE = "post_title";
        public static final String COLUMN_NAME_DESCRIPTION = "post_description";
        public static final String COLUMN_NAME_DEMONSTRATION = "post_demonstration";
        public static final String COLUMN_NAME_STUDENT = "post_student";


        private static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + Posts.TABLE_NAME + " (" +
                        Posts._ID + " INTEGER PRIMARY KEY," +
                        Posts.COLUMN_NAME_TITLE + " TEXT," +
                        Posts.COLUMN_NAME_DESCRIPTION + " TEXT," +
                        Posts.COLUMN_NAME_DEMONSTRATION + " TEXT," +
                        Posts.COLUMN_NAME_STUDENT + " TEXT)";

        private static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + Posts.TABLE_NAME;
    }

    public static class DbHelper extends SQLiteOpenHelper {
        //must increment database version if changing database schema
        public static final int DATABASE_VERSION = 2;
        public static final String DATABASE_NAME = "posts.db";

        public DbHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(Data.Posts.SQL_CREATE_ENTRIES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //only for online data so when offline discard data and start over
            db.execSQL(Posts.SQL_DELETE_ENTRIES);
            onCreate(db);
        }

        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }





}
