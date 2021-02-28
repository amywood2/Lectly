package com.example.lectly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class lecturerMain extends AppCompatActivity {

    private static final String TAG = null;
    Button files;
    FloatingActionButton createButton;
    Button postButton;
    Button menu;
    EditText postTitleInput;
    EditText postDescriptionInput;
    EditText postDemoInput;
    EditText postStudentWorkInput;
    Switch postAllowCommentsDecision;

    //access firebase database






    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_main);
        setupUI();
        setupListeners();
    }


    private void setupUI() {
        files = (Button) findViewById(R.id.files);
        postButton = (Button) findViewById(R.id.postButton);
        menu = (Button) findViewById(R.id.menu);
        createButton = (FloatingActionButton) findViewById(R.id.createButton);
    }


    private void setupListeners() {
        Data.DbHelper helper = new Data.DbHelper(lecturerMain.this);

        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();

        files.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // IPicker mPicker = Picker.createPicker(Picker.ONEDRIVE_APP_ID);
                // mPicker.startPicking(lecturerMain.this, LinkType.DownloadLink);
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder build = new AlertDialog.Builder(lecturerMain.this);

                Context context = build.getContext();
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.createpostdialog, null, false);

                postTitleInput = (EditText) view.findViewById(R.id.postTitleInput);
                postDescriptionInput = (EditText) view.findViewById(R.id.postDescriptionInput);
                postDemoInput = (EditText) view.findViewById(R.id.postDemoInput);
                postStudentWorkInput = (EditText) view.findViewById(R.id.postStudentWorkInput);
                postAllowCommentsDecision = (Switch) view.findViewById(R.id.allowComments);

                build.setView(view)
                        .setPositiveButton("Post Now", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //post and save to db

                                String titleUserInput = (String) postTitleInput.getText().toString();
                                values.put(Data.Posts.COLUMN_NAME_TITLE, titleUserInput);
                                String descriptionUserInput = (String) postDescriptionInput.getText().toString();
                                values.put(Data.Posts.COLUMN_NAME_DESCRIPTION, titleUserInput);
                                String demoUserInput = (String) postDemoInput.getText().toString();
                                String studentWorkUserInput = (String) postStudentWorkInput.getText().toString();
                                Boolean decision;
                                //show comment section on post
                                //dont show comment section on post
                                decision = postAllowCommentsDecision.isChecked();
                                long newRowId = db.insert(Data.Posts.TABLE_NAME, null, values);
                            }
                        })
                        .setNegativeButton("Schedule Post", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //schedule post
                            }
                        });
                build.show();

            }

        });

    }

}







