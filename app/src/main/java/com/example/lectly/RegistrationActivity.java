package com.example.lectly;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.common.internal.Constants;
import com.google.android.material.animation.ArgbEvaluatorCompat;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    TextInputEditText textInputName, textInputUsername, textInputEmail, textInputPassword;
    Button registerButton;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        textInputName = findViewById(R.id.nameValue);
        textInputEmail = findViewById(R.id.emailValue);
        textInputPassword = findViewById(R.id.passwordValue);
        textInputUsername = findViewById(R.id.usernameValue);
        registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fullname, username, password, email;
                fullname = String.valueOf(textInputName.getText());
                username = String.valueOf(textInputUsername.getText());
                password = String.valueOf(textInputPassword.getText());
                email = String.valueOf(textInputEmail.getText());

                if(!fullname.equals("") && !email.equals("") && !username.equals("") && !password.equals("")) {

                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[4];
                            field[0] = "fullname";
                            field[1] = "email";
                            field[2] = "username";
                            field[3] = "password";

                            //Creating array for data
                            String[] data = new String[4];
                            data[0] = fullname;
                            data[1] = email;
                            data[2] = username;
                            data[3] = password;

                            PutData putData = new PutData("http://192.168.1.87:8888/RegisterSystem/signup.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if (result.equals("Sign Up Success")){
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        //if student take to student main
                                        //if lecturer take to lecturer main
                                        Intent intent = new Intent(getApplicationContext(), lecturerMain.class);
                                        startActivity(intent);
                                        finish();

                                    }else {
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


