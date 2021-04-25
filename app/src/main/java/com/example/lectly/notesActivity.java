package com.example.lectly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class notesActivity extends AppCompatActivity {

    EditText notesInput;
    Button saveNotes;
    public static String notesToSave;
    public static String notesFileNameToSave;
    EditText notesFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        notesInput = findViewById(R.id.noteInput);
        notesInput.setText("");
        saveNotes = findViewById(R.id.saveNotes);
        notesFileName =findViewById(R.id.notesFileName);


        saveNotes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                notesToSave = saveNotes.getText().toString();
                notesFileNameToSave = notesFileName.getText().toString();
                Intent i = new Intent(getApplicationContext(), DriveActivity.class);
                startActivity(i);
            }
        });
    }
}