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

public class createDemoFile extends AppCompatActivity {

    EditText demoInput;
    Button saveDemoFile;
    public static String demoToSave;
    public static String demofileNameToSave;
    FloatingActionButton demoInfoButton;
    EditText demoFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_demo_file);

        demoInput = findViewById(R.id.demoInput);
        demoInput.setText("");
        saveDemoFile = findViewById(R.id.saveDemoFile);
        demoInfoButton = findViewById(R.id.demoInfoButton);
        demoFileName = findViewById(R.id.demoFileName);

        saveDemoFile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                demoToSave = demoInput.getText().toString();
                demofileNameToSave = demoFileName.getText().toString();
                Intent i = new Intent(getApplicationContext(), saveDemoToDrive.class);
                startActivity(i);
            }
        });

        demoInfoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(createDemoFile.this);
                alertDialog.setTitle("Demostratration File Info");
                alertDialog.setMessage("A demonstration file should contain content for students to learn. " +
                        "Make sure the content provides is clear and concise. This is beneficial to the effectiveness of how each student will " +
                        "learn the content. When the file is save, it will be uploaded to the modules shared google drive. " );

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