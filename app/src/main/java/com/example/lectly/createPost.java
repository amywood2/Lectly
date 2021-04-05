package com.example.lectly;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class createPost extends AppCompatActivity {

    Button savePostButton;
    FloatingActionButton infoButton;
    TextInputEditText textInputTitle;
    TextInputEditText textInputDescription;
    TextInputEditText textInputDemonstration;
    TextInputEditText textInputStudentWork;
    String chosenModuleName;
    private ArrayList<String> allModules;
    private ArrayList<String> moduleNames;
    private JSONArray result;
    private Spinner spinner;
    private TextView textViewTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        savePostButton = findViewById(R.id.savePostButton);
        infoButton = findViewById(R.id.infoButton);
        textInputTitle = (TextInputEditText) findViewById(R.id.titleValue);
        textInputDescription = (TextInputEditText) findViewById(R.id.descriptionValue);
        textInputDemonstration = (TextInputEditText) findViewById(R.id.demonstrationValue);
        textInputStudentWork = (TextInputEditText) findViewById(R.id.studentWorkValue);
        allModules = new ArrayList<String>();
        moduleNames = new ArrayList<String>();
        spinner = (Spinner) findViewById(R.id.spinner);

        textViewTitle = findViewById(R.id.textViewTitle);


        getModules();
        //getModuleId();

        infoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(createPost.this);
                alertDialog.setTitle("Post Info");
                alertDialog.setMessage("This app makes use of the constructivist learning theory. This allows students " +
                        "to learn from a demonstration (presented by a lecturer). Students can then use the demonstration to further " +
                        "there knowledge independently and complete any work set for them" + "\n" + "\n" +
                        "It is important to clearly explain the material through the use of several formats of resources such as notes, recordings, activities. This means that all types of learnings" +
                        "are catered for and student will allow student access to as many resources as possible" + "\n" + "\n" +
                        "Please use these extra resources to aid making posts: " + "\n" +
                        "");
                //add extra resources

                alertDialog.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                alertDialog.show();
            }
        });

        savePostButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String title, description, demonstration, studentWork;

                title = String.valueOf(textInputTitle.getText());
                description = String.valueOf(textInputDescription.getText());
                demonstration = String.valueOf(textInputDemonstration.getText());
                studentWork = String.valueOf(textInputStudentWork.getText());


                if (!title.equals("") && !description.equals("") && !demonstration.equals("") && !studentWork.equals("") ) {
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[4];
                            field[0] = "title";
                            field[1] = "description";
                            field[2] = "demonstration";
                            field[3] = "studentWork";


                            //Creating array for data
                            String[] data = new String[4];
                            data[0] = title;
                            data[1] = description;
                            data[2] = demonstration;
                            data[3] = studentWork;


                            PutData putData = new PutData("http://192.168.5.31:8888/Lectly/savePost.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if (result.equals("Your post has been successfully posted")) {
                                        Toast.makeText(createPost.this, "Your post has been successfully posted", Toast.LENGTH_LONG).show();
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
                } else {
                    Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


        private void getModules() {
            StringRequest stringRequest = new StringRequest("http://192.168.5.31:8888/Lectly/getModules.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject allModules = null;
                            try {
                                allModules = new JSONObject(response);
                                result = allModules.getJSONArray(ModuleDetails.JSON_ARRAY);
                                getModuleName(result);

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

        private void getModuleName(JSONArray allModules) {
            for (int i = 0; i < allModules.length(); i++) {
                try {
                    JSONObject json = allModules.getJSONObject(i);
                    moduleNames.add(json.getString(ModuleDetails.MODULENAME));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            spinner.setAdapter(new ArrayAdapter<String>(createPost.this, android.R.layout.simple_spinner_dropdown_item, moduleNames));

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    Object item = parent.getItemAtPosition(pos);
                    chosenModuleName = item.toString();
                    //textViewTitle.setText(chosenModuleName);

                }
                public void onNothingSelected(AdapterView<?> parent) {
                    Toast.makeText(getApplicationContext(), "Please select the module you wish to post in", Toast.LENGTH_SHORT).show();

                }
            });
        }

    private void getModuleId() {
        for (int i = 0; i < allModules.size(); i++) {
            if (allModules.get(i) == chosenModuleName) {
                int module_id = i;
                textViewTitle.setText("The module id is    " + module_id);

            }
        }
    }



}


