package com.example.lectly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class studentMenu extends AppCompatActivity {

    Button modules;
    Button settings;
    Button study;
    Button connect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_menu);

        modules = findViewById(R.id.modulesButton);
        settings = findViewById(R.id.settingsButton);
        study = findViewById(R.id.studyButton);
        connect = findViewById(R.id.sConnectButton);

        modules.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), studentModules.class);
                startActivity(i);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), settings.class);
                startActivity(i);
            }
        });

        connect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), connect.class);
                startActivity(i);
            }
        });
    }


}