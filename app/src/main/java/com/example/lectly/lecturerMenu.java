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
    Button logout;
    Button connect;
    Button students;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_menu);

        allPosts = findViewById(R.id.lAllPosts);
        dashboard = findViewById(R.id.lDashboard);
        modules = findViewById(R.id.lModules);
        logout = findViewById(R.id.lLogOut);
        connect = findViewById(R.id.lConnectButton);
        students = findViewById(R.id.lStudents);


        allPosts.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), lecturerMain.class);
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

       students.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), students.class);
                startActivity(i);
            }
        });
    }

}