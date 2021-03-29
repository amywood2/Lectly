package com.example.lectly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;



public class lecturerMain extends AppCompatActivity {

    private static final String TAG = null;
    Button files;
    FloatingActionButton createButton;
    Button menu;
    TextInputEditText textInputTitle;
    TextInputEditText textInputDemonstration;
    TextInputEditText textInputDescription;
    TextInputEditText postStudentWorkInput;
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
            public void onClick(View v){
                Intent i = new Intent (getApplicationContext(), createPost.class);
                startActivity(i);
            }
        });


        /*createButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(lecturerMain.this);
                    ViewGroup viewGroup = findViewById(android.R.id.content);
                    View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.createpostdialog, viewGroup, false);
                    textInputTitle = (TextInputEditText) viewGroup.findViewById(R.id.titleValue);
                    textInputDescription = (TextInputEditText) viewGroup.findViewById(R.id.descriptionValue);
                    textInputDemonstration = (TextInputEditText) viewGroup.findViewById(R.id.demonstrationValue);

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

                                        PutData putData = new PutData("http://192.168.5.31:8888/Lectly/savePost.php", "POST", field, data);
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
        }*/
    }
    }







