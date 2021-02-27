package com.example.lectly;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
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


    Button files;
    FloatingActionButton createButton;
    Button postButton;
    Button menu;
    EditText postTitleInput;
    EditText postDescriptionInput;
    EditText postDemoInput;
    EditText postStudentWorkInput;
    Switch postAllowCommentsDecision;



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_main);
        FirebaseApp.initializeApp(this);
        setupUI();
        setupListeners();
    }


    private void setupUI() {
        files = findViewById(R.id.files);
        postButton = findViewById(R.id.postButton);
        menu = findViewById(R.id.menu);
        createButton = findViewById(R.id.createButton);
        postTitleInput = findViewById(R.id.postTitleInput);
        postDescriptionInput = findViewById(R.id.postDescriptionInput);
        postDemoInput = findViewById(R.id.postDemoInput);
        postStudentWorkInput = findViewById(R.id.postStudentWorkInput);
        postAllowCommentsDecision = findViewById(R.id.allowComments);
    }

    private void setupListeners() {

        //access firebase database

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //create place to store posts
        Map<String, Object> newPost = new HashMap<>();

        files.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // IPicker mPicker = Picker.createPicker(Picker.ONEDRIVE_APP_ID);
               // mPicker.startPicking(lecturerMain.this, LinkType.DownloadLink);
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                AlertDialog.Builder build = new AlertDialog.Builder(lecturerMain.this);

                build.setTitle("Create a Post");



                //post in
                //show radio button for every module the lecturer can post in


                build.setView(R.layout.createpostdialog)
                        .setPositiveButton("Post Now", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //getting title of post
                                //String titleUserInput = postTitleInput.getText().toString();
                                String text = (String) postTitleInput.getText().toString();
                                newPost.put("Title", text);

                                //getting description of post
                                //String descriptionUserInput = postDescriptionInput.toString();
                                //newPost.put("Description", descriptionUserInput);

                                //getting demonstration text
                                //String demoUserInput = postDemoInput.toString();
                               // newPost.put("Demonstration", demoUserInput);

                                //getting student work text
                               // String studentWorkUserInput = postStudentWorkInput.toString();
                               // newPost.put("Student work", studentWorkUserInput);

                                //getting allow comments boolean answer
                              //  Boolean decision;
                             //   if (postAllowCommentsDecision.isChecked()){
                               //     decision = true;
                                    //show comment section on post
                              //  }else{
                              //      decision = false;
                                    //dont show comment section on post
                             //   }
                              //  newPost.put("Comment Decision", decision);

                                //post and add to db
                                db.collection("Posts")
                                        .add(newPost)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                               Toast.makeText(lecturerMain.this,"Your new post has been posted", Toast.LENGTH_LONG).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(lecturerMain.this,"Your new post has not been posted", Toast.LENGTH_LONG).show();

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


