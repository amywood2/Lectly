package com.example.lectly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_main);
        FirebaseApp.initializeApp(this);
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
                                //post
                                String titleUserInput = (String) postTitleInput.getText().toString();
                                newPost.put("Title", titleUserInput);
                                String descriptionUserInput = (String) postDescriptionInput.getText().toString();
                                String demoUserInput = (String) postDemoInput.getText().toString();
                                String studentWorkUserInput = (String) postStudentWorkInput.getText().toString();
                                Boolean decision;
                                //show comment section on post
                                //dont show comment section on post
                                decision = postAllowCommentsDecision.isChecked();

                                //post and add to db
                                db.collection("Posts")
                                        .add(newPost)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Log.d(TAG,"DocumentSnapshot added with ID: " + documentReference.getId());
                                                Toast.makeText(lecturerMain.this, "Your new post has been posted", Toast.LENGTH_LONG).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w(TAG, "Error adding document", e);
                                                Toast.makeText(lecturerMain.this, "Your new post has not been posted", Toast.LENGTH_LONG).show();

                                            }
                                        });

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





/*

        createButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                AlertDialog.Builder build = new AlertDialog.Builder(lecturerMain.this);

                Context context = build.getContext();
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.createpostdialog, null, false);

                build.setTitle("Create a Post");

                postTitleInput = (EditText) view.findViewById(R.id.postTitleInput);
                postDescriptionInput = (EditText) view.findViewById(R.id.postDescriptionInput);
                postDemoInput = (EditText) view.findViewById(R.id.postDemoInput);
                postStudentWorkInput = (EditText) view.findViewById(R.id.postStudentWorkInput);
                postAllowCommentsDecision = (Switch) view.findViewById(R.id.allowComments);

                View.OnClickListener listener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String titleUserInput = (String) postTitleInput.getText().toString();
                        String descriptionUserInput = (String) postDescriptionInput.getText().toString();
                        String demoUserInput = (String) postDemoInput.getText().toString();
                        String studentWorkUserInput = (String) postStudentWorkInput.getText().toString();
                        Boolean decision;
                        //show comment section on post
                        //dont show comment section on post
                        decision = postAllowCommentsDecision.isChecked();

                    }
                  };



                    build.setView(view)
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
                                                        Toast.makeText(lecturerMain.this, "Your new post has been posted", Toast.LENGTH_LONG).show();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(lecturerMain.this, "Your new post has not been posted", Toast.LENGTH_LONG).show();

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

                        build.create();
                        //dialog.show();

                    }


                };
*/


    }
}







