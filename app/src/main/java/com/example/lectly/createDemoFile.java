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