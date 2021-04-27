package com.example.lectly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class studentMenu extends AppCompatActivity {

    Button modules;
    Button logout;
    Button study;
    Button connect;
    Button savedPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_menu);

        modules = findViewById(R.id.modulesButton);
        logout = findViewById(R.id.logoutButton);
        study = findViewById(R.id.studyButton);
        connect = findViewById(R.id.sConnectButton);
        savedPosts = findViewById(R.id.sSavedPostsButton);

        modules.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), studentModules.class);
                startActivity(i);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });

        connect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), connect.class);
                startActivity(i);
            }
        });

        study.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), studySection.class);
                startActivity(i);
            }
        });

        savedPosts.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), savedPosts.class);
                startActivity(i);
            }
        });
    }


}