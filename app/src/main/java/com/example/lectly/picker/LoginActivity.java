package com.example.lectly.picker;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lectly.R;

public class LoginActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    Button register;
    Button login;
    Button forgotPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupUI();
        setupListeners();
    }

    private void setupUI(){
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        forgotPassword = findViewById(R.id.ForgotPassword);
    }

    private void setupListeners(){
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUsername();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(LoginActivity.this, RegistrationActivity.class);
                //startActivity(i);
            }
        });
    }

    void checkUsername(){
        boolean isValid = true;

        if (isEmpty(username)){
            username.setError("Please enter your username to login");
            isValid = false;
        } else {
            if (!isEmail(username)){
                username.setError("Please enter a valid email");
                isValid = false;
            }
        }

        if (isValid){
            String usernameValue = username.getText().toString();
            String passwordValue = password.getText().toString();
            if (usernameValue.equals("lecturer@gmail.com") && passwordValue.equals("testing123")){
                //checks are ok so open lecturer main page
                Intent i = new Intent( LoginActivity.this, lecturerMain.class);
                startActivity(i);
                this.finish();
            } else {
                Toast t = Toast.makeText(this, "Wrong email or password", Toast.LENGTH_SHORT );
                t.show();
            }
        }
    }

    boolean isEmail(EditText text){
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isEmpty(EditText text){
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

}





