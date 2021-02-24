package com.example.lectly.picker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.lectly.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class lecturerMain extends AppCompatActivity {

    Button files;
    FloatingActionButton createButton;
    Button postButton;
    Button menu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_main);
        setupUI();
        setupListeners();
    }

    private void setupUI() {
        files = findViewById(R.id.files);
        postButton = findViewById(R.id.postButton);
        menu = findViewById(R.id.menu);
        createButton = findViewById(R.id.createButton);
    }

    private void setupListeners() {
        files.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IPicker mPicker = Picker.createPicker(Picker.ONEDRIVE_APP_ID);
                mPicker.startPicking(lecturerMain.this, LinkType.DownloadLink);
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                AlertDialog.Builder build = new AlertDialog.Builder(lecturerMain.this);

                build.setTitle("Create a Post");

                build.setView(R.layout.createpostdialog)
                        .setPositiveButton("Post Now", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //post and add to db

                            }
                        })
                        .setNegativeButton("Schedule Post", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //schedule post and add to db
                            }
                        });


                //title of post


                //description

                //demonstration

                //student work

                //allow comments

                //post in modules - drop down box to select modules

                //schedule post button

                //post now button

                //help button - explain concept of contructivist learning theory
                AlertDialog dialog = build.create();
                dialog.show();
            }
        });
    }


}


