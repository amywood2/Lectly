package com.example.lectly;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

public class createPost extends AppCompatActivity {

    Button savePostButton;
    FloatingActionButton infoButton;
    TextInputEditText textInputTitle;
    TextInputEditText textInputDescription;
    TextInputEditText textInputDemonstration;
    TextInputEditText textInputStudentWork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        savePostButton =  findViewById(R.id.savePostButton);
        infoButton = findViewById(R.id.infoButton);
        textInputTitle = (TextInputEditText) findViewById(R.id.titleValue);
        textInputDescription = (TextInputEditText) findViewById(R.id.descriptionValue);
        textInputDemonstration = (TextInputEditText) findViewById(R.id.demonstrationValue);
        textInputStudentWork = (TextInputEditText) findViewById(R.id.studentWorkValue);

        infoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(createPost.this);
                alertDialog.setTitle("Post Info");
                alertDialog.setMessage("This app makes use of the constructivist learning theory. This allows students " +
                                "to learn from a demonstration (presented by a lecturer). Students can then use the demonstration to further " +
                        "there knowledge independently and complete any work set for them" + "\n" + "\n" +
                        "It is important to clearly explain the material through the use of several formats of resources such as notes, recordings, activities. This means that all types of learnings" +
                        "are catered for and student will allow student access to as many resources as possible" + "\n" + "\n" +
                        "Please use these extra resources to aid making posts: "+ "\n" +
                        "");
                        //add extra resources

                alertDialog.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                alertDialog.show();
            }
        });

        savePostButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String title, description, demonstration, studentWork;

                title = String.valueOf(textInputTitle.getText());
                description = String.valueOf(textInputDescription.getText());
                demonstration = String.valueOf(textInputDemonstration.getText());
                studentWork = String.valueOf(textInputStudentWork.getText());

                if (!title.equals("") && !description.equals("") && !demonstration.equals("") && !studentWork.equals("") ) {
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[4];
                            field[0] = "title";
                            field[1] = "description";
                            field[2] = "demonstration";
                            field[3] = "studentWork";

                            //Creating array for data
                            String[] data = new String[4];
                            data[0] = title;
                            data[1] = description;
                            data[2] = demonstration;
                            data[3] = studentWork;

                            PutData putData = new PutData("http://192.168.1.87:8888/Lectly/savePost.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if (result.equals("Your post has been successfully posted")) {
                                        Toast.makeText(createPost.this, "Your post has been successfully posted", Toast.LENGTH_LONG).show();
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
                } else {
                    Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}


