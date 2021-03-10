package com.example.lectly;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
    private TextInputLayout name, regemail, regphone, regpassword, regconfpassword, orgbuild, orgarea, orgdistrict, orgstate, orgcountry, orgpin;

    private TextInputEditText namevalue, regemailvalue, regphonevalue, regpasswordvalue, regconfpasswordvalue, orgbuildval, orgareaval, orgpinval;
    private AutoCompleteTextView orgcountryval, orgdisval, orgstateval;


    private String teachername, teacheremail, teacherphone, teacherpassword, teacherconfpassword, teacherclasses, orgbuildstring, orgareastring, orgdisstring, orgstatestring, orgcountrystring, orgpinstring;
    private ProgressBar progressBar;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";
    private String namepattern = "[a-zA-Z ]+";



    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        name = findViewById(R.id.name);
        regemail = findViewById(R.id.regemail);

        regpassword = findViewById(R.id.regpassword);
        regconfpassword = findViewById(R.id.regconfpassword);


        namevalue = findViewById(R.id.namevalue);
        regemailvalue = findViewById(R.id.regemailvalue);

        regpasswordvalue = findViewById(R.id.regpasswordvalue);
        regconfpasswordvalue = findViewById(R.id.regconfpasswordvalue);


        // sharedPreferences = getApplicationContext().getSharedPreferences(Constants.PREFERENCEFILE,Context.MODE_PRIVATE);


        progressBar = findViewById(R.id.registerbar);


        hidedialog();


        namevalue.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    teachername = namevalue.getText().toString().trim();
                    //Toast.makeText(RegisterActivity.this,teachername,Toast.LENGTH_LONG).show();
                    if (!teachername.matches(namepattern) || TextUtils.isEmpty(teachername)) {
                        name.setErrorEnabled(true);
                        name.setError("Enter a valid name");


                    } else {
                        name.setError("");
                        name.setErrorEnabled(false);
                    }
                }
            }
        });
        regemailvalue.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    teacheremail = regemailvalue.getText().toString().trim();
                    if (!teacheremail.matches(emailPattern) || TextUtils.isEmpty(teacheremail)) {
                        regemail.setErrorEnabled(true);
                        regemail.setError("Enter a valid email");


                    } else {
                        regemail.setError("");
                        regemail.setErrorEnabled(false);
                    }
                }
            }
        });


    }

    public void register(View view) {
        showdialog();
        teacherpassword = regpasswordvalue.getText().toString().trim();
        teacherconfpassword = regconfpasswordvalue.getText().toString().trim();
        teachername = namevalue.getText().toString().trim();
        teacheremail = regemailvalue.getText().toString().trim();


         /*   if(new CheckInter().checkInternet(RegistrationActivity.this)) {


                if (!teacherpassword.equals(teacherconfpassword)) {
                    showalert("password dont match");
                    hidedialog();
                } else if (name.isErrorEnabled() || regemail.isErrorEnabled() || regphone.isErrorEnabled() ||  orgbuild.isErrorEnabled() || orgarea.isErrorEnabled() || orgpin.isErrorEnabled() || orgcountry.isErrorEnabled() || orgstate.isErrorEnabled() || orgdistrict.isErrorEnabled()) {


                    showalert("enter valid details");
                    hidedialog();
                } else {

                    Map<String, String> parameters = new HashMap<String, String>();
                    parameters.put("name", teachername);
                    parameters.put("email", teacheremail);
                    parameters.put("phone", teacherphone);


                    parameters.put("password", teacherpassword);
                    parameters.put("buildno",orgbuildstring);
                    parameters.put("area",orgareastring);
                    parameters.put("country",countryid);
                    parameters.put("state",stateid);
                    parameters.put("district",districtid);
                    parameters.put("pincode",orgpinstring);
                    JSONObject regrequest = new JSONObject(parameters);
                    JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, Constants.REGISTER_URL,regrequest, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            hidedialog();
                            try {
                                //JSONObject jsonObject = new JSONObject(response);
                                if (response.getString("message").equals("success")) {
                                    editor = sharedPreferences.edit();
                                    editor.putBoolean("authenticated", true);
                                    editor.putString("token", response.getString("data"));
                                    editor.apply();
                                    Intent intent = new Intent(RegistrationActivity.this, UploadActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    showalert(response.getString("message"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            hidedialog();
                            if(error.toString().contains("NoConnectionError"))
                            {
                                showalert("server is down try again later");
                                error.printStackTrace();
                            }
                            else {
                                String errorres = new String(error.networkResponse.data);
                                try {
                                    JSONObject jsonObject = new JSONObject(errorres);
                                    showalert(jsonObject.getString("message"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Log.i("error data", new String(error.networkResponse.data));
                                Log.e("register error res", error.toString());
                            }


                        }
                    });
                    MySingleton.getInstance(RegistrationActivity.this).addToRequestQue(stringRequest);


                }
            }else


          */
        {
            hidedialog();
        }
    }

    public void openLogin(View view) {
        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void showdialog() {
        progressBar.setVisibility(View.VISIBLE);
        //regbutt.setVisibility(View.GONE);
    }

    public void hidedialog() {
        progressBar.setVisibility(View.GONE);
        //regbutt.setVisibility(View.VISIBLE);
    }

    public void showalert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);
        builder.setMessage(message)
                .setNegativeButton("Retry", null)
                .create()
                .show();
    }

    public boolean ispassnotequal(String pass, String confpass) {
        if (pass.equals(confpass)) {
            return false;
        } else {
            return true;
        }
    }
}