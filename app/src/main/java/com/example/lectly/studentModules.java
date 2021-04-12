package com.example.lectly;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class studentModules extends AppCompatActivity {

    private ArrayList students;
    private ArrayList moduleNames;
    private JSONArray result;
    private Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_modules);

        students = new ArrayList<String>();
        moduleNames = new ArrayList<String>();
        spinner = (Spinner) findViewById(R.id.moduleSpinner);
       // spinner.setOnItemSelectedListener(this);
       // textViewName = (TextView) findViewById(R.id.textViewName);
       // textViewCourse = (TextView) findViewById(R.id.textViewCourse);
       // textViewSession = (TextView) findViewById(R.id.textViewSession);
        getData();
    }

    private void getData(){
        StringRequest stringRequest = new StringRequest("http://192.168.5.31:8888/Lectly/getModules.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            result = j.getJSONArray(ModuleDetails.JSON_ARRAY);
                            getModules(result);
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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getModules(JSONArray j){
        for(int i=0;i<j.length();i++){
            try {
                JSONObject json = j.getJSONObject(i);
                moduleNames.add(json.getString(ModuleDetails.MODULENAME));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        spinner.setAdapter(new ArrayAdapter<String>(studentModules.this, android.R.layout.simple_spinner_dropdown_item, moduleNames));
    }
}