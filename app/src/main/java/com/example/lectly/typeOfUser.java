package com.example.lectly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class typeOfUser extends AppCompatActivity {

    public static String typeofuser;
    Button isLecturer;
    Button isStudent;
    ImageView logo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_of_user);

        isLecturer = findViewById(R.id.isLecturerbutton);
        isStudent = findViewById(R.id.isStudentbutton);
        logo = findViewById(R.id.logo);

        isLecturer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                typeofuser = "Lecturer";
                Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        });

        isStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                typeofuser = "Student";
                Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        });

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}