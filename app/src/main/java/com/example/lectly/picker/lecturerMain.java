package com.example.lectly.picker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lectly.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class lecturerMain extends AppCompatActivity {

    Button files;
    FloatingActionButton createButton;
    Button postButton;
    Button menu;
    EditText postTitleInput;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_main);
        setupUI();
        setupListeners();
        FirebaseApp.initializeApp(this);
    }



    private void setupUI() {
        files = findViewById(R.id.files);
        postButton = findViewById(R.id.postButton);
        menu = findViewById(R.id.menu);
        createButton = findViewById(R.id.createButton);
        postTitleInput = findViewById(R.id.postTitleInput);

    }

    private void setupListeners() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

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


                Map<String, Object> newPost = new HashMap<>();

                //title of post
                String titleUserInput = postTitleInput.toString();
                newPost.put("Title", titleUserInput);

                build.setView(R.layout.createpostdialog)
                        .setPositiveButton("Post Now", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //post and add to db
                                db.collection("posts")
                                        .add(newPost)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Toast.makeText(lecturerMain.this, "Your post has been posted", Toast.LENGTH_LONG)
                                                        .show();

                                            }
                                        });
                            }
                        })

                        .setNegativeButton("Schedule Post", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //schedule post and add to db
                            }
                        });



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


