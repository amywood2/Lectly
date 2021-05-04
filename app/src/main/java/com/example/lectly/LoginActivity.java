package com.example.lectly;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    EditText textInputUsernameLogin, textInputPasswordLogin;
    Button loginButton;
    Button register;
    Button forgotPassword;
    public static Boolean isLecturerLoggedIn;
    public static Boolean isStudentLoggedIn;
    public static String username;
    public static String password;
    private JSONArray idResult;
    public static String loggedInUserId;
    public static String user_id;




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

                            PutData putData = new PutData("http://<ip-address>:3306/Lectly/login.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if (result.equals("Student Logging in")) {
                                        //isStudentLoggedIn = true;
                                        typeOfUser.typeofuser = "student";
                                        Toast.makeText(getApplicationContext(), "Welcome " + username, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), studentMain.class);
                                        startActivity(intent);
                                        finish();
                                    } else if (result.equals("Lecturer Logging in")) {
                                        //isLecturerLoggedIn = true;
                                        typeOfUser.typeofuser = "lecturer";
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
                getUserID();
            }
        });
    }


    public void getUserID(){
        if (typeOfUser.typeofuser == "Lecturer") {
            StringRequest modStringRequest = new StringRequest("http://<ip-address>:3306:8888/Lectly/getLecturerId.php?username=" + username,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject theIds = null;
                            try {
                                theIds = new JSONObject(response.toString());
                                idResult = theIds.getJSONArray(PersonDetails.JSON_ARRAY);
                                for (int i = 0; i < idResult.length(); i++) {
                                    JSONObject jsonObject = idResult.getJSONObject(i);
                                    String id = jsonObject.getString("id");
                                    loggedInUserId = id;
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    });
            RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
            requestQueue.add(modStringRequest);
        }else {
            StringRequest modStringRequest = new StringRequest("http://<ip-address>:3306/Lectly/getStudentId.php?username=" + username,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject ids;
                            try {
                                ids = new JSONObject(response);
                                idResult = ids.getJSONArray(PersonDetails.JSON_ARRAY);
                                for (int i = 0; i < idResult.length(); i++) {
                                    JSONObject jsonObject = idResult.getJSONObject(i);
                                    user_id = jsonObject.getString(PersonDetails.ID);
                                    loggedInUserId = user_id;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    });
            RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
            requestQueue.add(modStringRequest);
        }

    }
}





