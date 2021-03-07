package com.example.lectly;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class studentMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);
    }
/*
    TextView text;
    Data.DbHelper helper = new Data.DbHelper(studentMain.this);

    SQLiteDatabase db = helper.getReadableDatabase();

    // Define a projection that specifies which columns from the database
    // you will actually use after this query.
    String[] projection = {
            BaseColumns._ID,
            Data.Posts.COLUMN_NAME_TITLE,
            Data.Posts.COLUMN_NAME_DESCRIPTION
    };

    // Filter results WHERE "title" = 'My Title'
    String selection = Data.Posts.COLUMN_NAME_TITLE + " = ?";
    String[] selectionArgs = { "My Title" };

    // How you want the results sorted in the resulting Cursor
    String sortOrder =
            Data.Posts.COLUMN_NAME_DESCRIPTION + " DESC";

    Cursor cursor = db.query(
            Data.Posts.TABLE_NAME,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            selection,              // The columns for the WHERE clause
            selectionArgs,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            sortOrder               // The sort order
    );
*/


    //List itemIds = new ArrayList<>();
   // while(cursor.moveToNext()) {
        //long itemId = cursor.getLong(
          //      cursor.getColumnIndexOrThrow(Data.Posts._ID));
        //itemIds.add(itemId);
   // }
    //cursor.close();






}