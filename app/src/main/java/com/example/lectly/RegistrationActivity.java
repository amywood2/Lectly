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
        private TextInputLayout name,regemail,regphone,regpassword,regconfpassword,orgbuild,orgarea,orgdistrict,orgstate,orgcountry,orgpin;

        private TextInputEditText namevalue,regemailvalue,regphonevalue,regpasswordvalue,regconfpasswordvalue,orgbuildval,orgareaval,orgpinval;
        private AutoCompleteTextView orgcountryval,orgdisval,orgstateval;


        private String teachername,teacheremail,teacherphone,teacherpassword,teacherconfpassword,teacherclasses,orgbuildstring,orgareastring,orgdisstring,orgstatestring,orgcountrystring,orgpinstring;
        private ProgressBar progressBar;
        private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";
        private String namepattern = "[a-zA-Z ]+";
        private String phonereg = "^[0-9]{10}$";
        private String pincodereg = "^[0-9]{6}$";


        private SharedPreferences sharedPreferences;
        private SharedPreferences.Editor editor;


        private ArrayList<String> countrylist,statelist,districtlist;
        private ArrayList<ClassItem> fullccountrylist,fullstatelist,fulldislist;
        private ArrayAdapter<String> countryadapter,stateadapter,districtadapter;
        private Button regbutt;
        private String countryid,stateid,districtid;




        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_registration);


            name = findViewById(R.id.name);
            regemail =findViewById(R.id.regemail);
            regphone =findViewById(R.id.regphone);
            regpassword = findViewById(R.id.regpassword);
            regconfpassword = findViewById(R.id.regconfpassword);

            orgbuild = findViewById(R.id.orgbuild);
            orgarea = findViewById(R.id.orgarea);
            orgdistrict = findViewById(R.id.orgdistrict);
            orgstate = findViewById(R.id.orgstate);
            orgcountry = findViewById(R.id.orgcountry);
            orgpin = findViewById(R.id.orgpincode);


            namevalue = findViewById(R.id.namevalue);
            regemailvalue =findViewById(R.id.regemailvalue);
            regphonevalue = findViewById(R.id.regphoneval);
            regpasswordvalue  = findViewById(R.id.regpasswordvalue);
            regconfpasswordvalue = findViewById(R.id.regconfpasswordvalue);
            orgbuildval =findViewById(R.id.orgbuildno);
            orgareaval = findViewById(R.id.orgareaval);
            orgdisval = findViewById(R.id.orgdisvalue);
            orgstateval = findViewById(R.id.orgstateval);
            orgcountryval = findViewById(R.id.orgcountryval);
            orgpinval = findViewById(R.id.orgpincodeval);


            regbutt = findViewById(R.id.register);


           // sharedPreferences = getApplicationContext().getSharedPreferences(Constants.PREFERENCEFILE,Context.MODE_PRIVATE);


            fullccountrylist = new ArrayList<>();
            fulldislist = new ArrayList<>();
            fullstatelist = new ArrayList<>();


            countrylist = new ArrayList<>();
            statelist = new ArrayList<>();
            districtlist = new ArrayList<>();


            progressBar = findViewById(R.id.registerbar);
            countryadapter = new ArrayAdapter<>(RegistrationActivity.this,android.R.layout.simple_list_item_1,countrylist);


            orgcountryval.setAdapter(countryadapter);


            orgcountryval.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    orgcountrystring = countrylist.get(countrylist.indexOf(orgcountryval.getText().toString().trim()));
                    countryid = fullccountrylist.get(countrylist.indexOf(orgcountryval.getText().toString().trim())).getParentid();

                }
            });


            orgstateval.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    orgstatestring = statelist.get(statelist.indexOf(orgstateval.getText().toString().trim()));
                    stateid = String.valueOf(fullstatelist.get(statelist.indexOf(orgstateval.getText().toString().trim())).getId());

                }
            });


            orgdisval.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    orgdisstring = districtlist.get(districtlist.indexOf(orgdisval.getText().toString().trim()));
                    districtid = String.valueOf(fulldislist.get(districtlist.indexOf(orgdisval.getText().toString().trim())).getId());
                }
            });


            orgcountryval.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    orgcountryval.showDropDown();
                    Toast.makeText(RegistrationActivity.this,"clicked",Toast.LENGTH_SHORT).show();
                }
            });


            hidedialog();






            namevalue.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus)
                    {
                        teachername = namevalue.getText().toString().trim();
                        //Toast.makeText(RegisterActivity.this,teachername,Toast.LENGTH_LONG).show();
                        if(!teachername.matches(namepattern) || TextUtils.isEmpty(teachername))
                        {
                            name.setErrorEnabled(true);
                            name.setError("Enter a valid name");


                        }
                        else
                        {
                            name.setError("");
                            name.setErrorEnabled(false);
                        }
                    }
                }
            });
            regemailvalue.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus)
                    {
                        teacheremail = regemailvalue.getText().toString().trim();
                        if(!teacheremail.matches(emailPattern)|| TextUtils.isEmpty(teacheremail))
                        {
                            regemail.setErrorEnabled(true);
                            regemail.setError("Enter a valid email");


                        }
                        else
                        {
                            regemail.setError("");
                            regemail.setErrorEnabled(false);
                        }
                    }
                }
            });
            regphonevalue.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus)
                    {
                        teacherphone = regphonevalue.getText().toString().trim();
                        if(!teacherphone.matches(phonereg) || TextUtils.isEmpty(teacherphone))
                        {
                            regphone.setErrorEnabled(true);
                            regphone.setError("enter a valid phone number");


                        }
                        else
                        {
                            regphone.setError("");
                            regphone.setErrorEnabled(false);
                        }
                    }
                }
            });
            orgbuildval.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus)
                    {
                        orgbuildstring = orgbuildval.getText().toString().trim();
                        if(TextUtils.isEmpty(orgbuildstring))
                        {
                            orgbuild.setErrorEnabled(true);
                            orgbuild.setError("Enter a valid Building number");
                        }
                        else
                        {
                            orgbuild.setError("");
                            orgbuild.setErrorEnabled(false);
                        }
                    }
                }
            });
            orgareaval.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus)
                    {
                        orgareastring = orgareaval.getText().toString().trim();
                        if(TextUtils.isEmpty(orgareastring))
                        {
                            orgarea.setErrorEnabled(true);
                            orgarea.setError("Enter a valid Area");
                        }
                        else
                        {
                            orgarea.setError("");
                            orgarea.setErrorEnabled(false);
                        }
                    }
                }
            });
            orgdisval.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus)
                    {
                        orgdisstring = orgdisval.getText().toString().trim();
                        if(TextUtils.isEmpty(orgdisstring) || !districtlist.contains(orgdisstring))
                        {
                            orgdistrict.setErrorEnabled(true);
                            orgdistrict.setError("Select a valid District");
                        }
                        else
                        {
                            orgdistrict.setError("");
                            orgdistrict.setErrorEnabled(false);
                        }
                    }
                }
            });
            orgstateval.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus)
                    {
                        orgstatestring = orgstateval.getText().toString().trim();
                        if(TextUtils.isEmpty(orgstatestring) || !statelist.contains(orgstatestring))
                        {
                            orgstate.setErrorEnabled(true);
                            orgstate.setError("Select a valid State");
                        }
                        else
                        {
                            orgstate.setError("");
                            orgstate.setErrorEnabled(false);
                        }
                    }
                }
            });
            orgcountryval.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus)
                    {
                        orgcountrystring = orgcountryval.getText().toString().trim();
                        if(TextUtils.isEmpty(orgcountrystring) || !countrylist.contains(orgcountrystring))
                        {
                            orgcountry.setErrorEnabled(true);
                            orgcountry.setError("Select a valid Country");
                        }
                        else
                        {
                            orgcountry.setError("");
                            orgcountry.setErrorEnabled(false);
                        }
                    }
                }
            });
            orgpinval.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus)
                    {
                        orgpinstring = orgpinval.getText().toString().trim();
                        if(!orgpinstring.matches(pincodereg) || TextUtils.isEmpty(orgpinstring))
                        {
                            orgpin.setErrorEnabled(true);
                            orgpin.setError("Select a valid Pincode");
                        }
                        else
                        {
                            orgpin.setError("");
                            orgpin.setErrorEnabled(false);
                        }
                    }
                }
            });




        }
        public void register(View view)
        {
            showdialog();
            teacherpassword = regpasswordvalue.getText().toString().trim();
            teacherconfpassword = regconfpasswordvalue.getText().toString().trim();
            teacherphone = regphonevalue.getText().toString().trim();
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
        public void openLogin(View view)
        {
            Intent intent = new Intent(RegistrationActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
        public void showdialog()
        {
            progressBar.setVisibility(View.VISIBLE);
            regbutt.setVisibility(View.GONE);
        }
        public void hidedialog()
        {
            progressBar.setVisibility(View.GONE);
            regbutt.setVisibility(View.VISIBLE);
        }
        public void showalert(String message)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);
            builder.setMessage(message)
                    .setNegativeButton("Retry", null)
                    .create()
                    .show();
        }
        public boolean ispassnotequal(String pass,String confpass)
        {
            if(pass.equals(confpass))
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        public void fetchSpinnerValues(String url, final String type,final String dat)
        {

            showdialog();
            final ArrayList<String> spinnerdata = new ArrayList<>();
            Map<String, String> parameters = new HashMap<String, String>();
            parameters.put("data", dat);
            JSONObject regrequest = new JSONObject(parameters);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, regrequest, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    hidedialog();
                    try {
                        if(response.getString("message").equals("success"))
                        {


                            JSONArray jsonArray = response.getJSONArray("data");
                            //Toast.makeText(RegisterActivity.this,jsonArray.toString(),Toast.LENGTH_LONG).show();
                            if(type.equals("country"))
                            {
                                for(int i=0;i<jsonArray.length();i++)
                                {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    countrylist.add(jsonObject.getString("cname"));
                                    //Toast.makeText(RegisterActivity.this,String.valueOf(spinnerdata.size()),Toast.LENGTH_LONG).show();
                                    fullccountrylist.add(new ClassItem(jsonObject.getInt("id"),jsonObject.getString("cname"),jsonObject.getString("code")));
                                }
                                countryadapter.notifyDataSetChanged();
                                //Toast.makeText(RegisterActivity.this,String.valueOf(countrylist.size()),Toast.LENGTH_LONG).show();
                            }
                            else if(type.equals("state"))
                            {
                                for(int i=0;i<jsonArray.length();i++)
                                {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    statelist.add(jsonObject.getString("state"));
                                    fullstatelist.add(new ClassItem(jsonObject.getInt("id"),jsonObject.getString("state"),jsonObject.getString("cid")));
                                }
                                stateadapter.notifyDataSetChanged();
                            }
                            else if(type.equals("district"))
                            {
                                for(int i=0;i<jsonArray.length();i++)
                                {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    districtlist.add(jsonObject.getString("districts"));
                                    fulldislist.add(new ClassItem(jsonObject.getInt("id"),jsonObject.getString("districts"),jsonObject.getString("state_id")));
                                }
                                districtadapter.notifyDataSetChanged();
                            }
                        }
                        else
                        {
                            showalert(response.getString("message"));
                            regbutt.setEnabled(false);
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
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(0,-1,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
           // MySingleton.getInstance(RegistrationActivity.this).addToRequestQue(jsonObjectRequest);

        }
    }