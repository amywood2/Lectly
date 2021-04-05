package com.example.lectly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class lecturerMenu extends AppCompatActivity {

    Button allPosts;
    Button dashboard;
    Button modules;
    Button settings;
    Button connect;
    Button students;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_menu);

        allPosts = findViewById(R.id.allPosts);
        dashboard = findViewById(R.id.learningQuiz);
        modules = findViewById(R.id.modules);
        settings = findViewById(R.id.settings);
        connect = findViewById(R.id.connect);
        students = findViewById(R.id.congntiveSkills);


        allPosts.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), allPosts.class);
                startActivity(i);
            }
        });

        dashboard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), dashboard.class);
                startActivity(i);
            }
        });

        modules.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), modules.class);
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

        students.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), students.class);
                startActivity(i);
            }
        });
    }

}