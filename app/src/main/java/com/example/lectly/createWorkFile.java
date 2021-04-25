package com.example.lectly;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class createWorkFile extends AppCompatActivity {

    EditText workInput;
    Button saveWorkFile;
    public static String workToSave;
    public static String workfileNameToSave;
    FloatingActionButton workInfoButton;
    EditText workFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_work_file);

        workInput = findViewById(R.id.workInput);
        workInput.setText("");
        saveWorkFile = findViewById(R.id.saveWorkFile);
        workInfoButton = findViewById(R.id.workInfoButton);
        workFileName = findViewById(R.id.workFileName);

        saveWorkFile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                workToSave = workInput.getText().toString();
                workfileNameToSave = workFileName.getText().toString();
                Intent i = new Intent(getApplicationContext(), saveWorkToDrive.class);
                startActivity(i);
            }
        });

        workInfoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(createWorkFile.this);
                alertDialog.setTitle("Work File Info");
                alertDialog.setMessage("This app makes use of the constructivist learning theory. This allows students " +
                        "to learn from a demonstration (presented by a lecturer). Students can then use the demonstration to further " +
                        "there knowledge independently and complete any work set for them" + "\n" + "\n" +
                        "It is important to clearly explain the material through the use of several formats of resources such as notes, recordings, activities. This means that all types of learnings" +
                        "are catered for and student will allow student access to as many resources as possible" + "\n" + "\n" +
                        "Please use these extra resources to aid making posts: " + "\n" +
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
    }
}
