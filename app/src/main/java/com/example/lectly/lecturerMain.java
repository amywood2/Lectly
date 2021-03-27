package com.example.lectly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.pusher.client.Pusher;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionStateChange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class lecturerMain extends AppCompatActivity {

    private static final String TAG = null;
    Button files;
    FloatingActionButton createButton;
    Button menu;
    EditText textInputTitle;
    EditText textInputDemonstration;
    EditText textInputDescription;
    EditText postStudentWorkInput;
    Switch postAllowCommentsDecision;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_main);
        setupUI();

    }

    private void setupUI() {
        files = (Button) findViewById(R.id.files);
        menu = (Button) findViewById(R.id.menu);
        createButton = (FloatingActionButton) findViewById(R.id.createButton);




        createButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(lecturerMain.this);
                    ViewGroup viewGroup = findViewById(android.R.id.content);
                    View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.createpostdialog, viewGroup, false);
                    textInputTitle = (EditText) viewGroup.findViewById(R.id.postTitleInput);
                    textInputDescription = (EditText) viewGroup.findViewById(R.id.postDescriptionInput);
                    textInputDemonstration = (EditText) viewGroup.findViewById(R.id.postDemoInput);

                    builder.setView(dialogView)
                        .setPositiveButton("Post", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                            //post
                            //take user to post page
                            String title, description, demonstration;

                            title = String.valueOf(textInputTitle.getText());
                            description = String.valueOf(textInputDescription.getText());
                            demonstration = String.valueOf(textInputDemonstration.getText());


                            if(!title.equals("") && !description.equals("") && !demonstration.equals("")){

                                Handler handler = new Handler();
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        //Starting Write and Read data with URL
                                        //Creating array for parameters
                                        String[] field = new String[3];
                                        field[0] = "title";
                                        field[1] = "description";
                                        field[2] = "demonstration";

                                        //Creating array for data
                                        String[] data = new String[3];
                                        data[0] = title;
                                        data[1] = description;
                                        data[2] = demonstration;

                                        PutData putData = new PutData("http://192.168.1.87:8888/Lectly/savePost.php", "POST", field, data);
                                        if (putData.startPut()) {
                                            if (putData.onComplete()) {
                                                String result = putData.getResult();
                                                if (result.equals("Your post has been successfully posted")) {
                                                    Toast.makeText(getApplicationContext(), "Your post has been successfully posted", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(getApplicationContext(), lecturerMain.class);
                                                    startActivity(intent);
                                                    finish();
                                                } else {
                                                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }
                                    }
                                });
                            }else {
                                Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                            }
                        }

                        })
                        .setNegativeButton("Save as draft", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //save post as draft
                            }
                        });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });
        }



    }







