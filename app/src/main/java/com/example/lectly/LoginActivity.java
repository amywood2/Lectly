package com.example.lectly;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    EditText textInputUsernameLogin, textInputPasswordLogin;
    Button loginButton;
    Button register;
    Button forgotPassword;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        textInputUsernameLogin = findViewById(R.id.loginUsernameValue);
        textInputPasswordLogin = findViewById(R.id.loginPasswordValue);
        loginButton = findViewById(R.id.loginButton);
        register = findViewById(R.id.registerButton);
        forgotPassword = findViewById(R.id.ForgotPassword);
        setupListeners();
    }

    private void setupListeners() {

        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent i = new Intent (getApplicationContext(), typeOfUser.class);
                startActivity(i);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username, password;

                username = String.valueOf(textInputUsernameLogin.getText());
                password = String.valueOf(textInputPasswordLogin.getText());

                if(!username.equals("") && !password.equals("")) {

                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[2];
                            field[0] = "username";
                            field[1] = "password";

                            //Creating array for data
                            String[] data = new String[2];
                            data[0] = username;
                            data[1] = password;

                            PutData putData = new PutData("http://192.168.5.31:8888/Lectly/login.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if (result.equals("Student Logging in")) {
                                        Toast.makeText(getApplicationContext(), "Welcome " + username, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), studentMain.class);
                                        startActivity(intent);
                                        finish();
                                    } else if (result.equals("Lecturer Logging in")) {
                                        Toast.makeText(getApplicationContext(), "Welcome " + username, Toast.LENGTH_SHORT).show();
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
                }else {
                    Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}





