package com.example.lectly;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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


    private static final String TAG = null;
    Button savePostButton;

    FloatingActionButton infoButton;
    TextInputEditText textInputTitle;
    TextInputEditText textInputDescription;
    String chosenModuleName;
    private ArrayList<String> allModules;
    private ArrayList<String> moduleNames;
    private JSONArray result;
    private Spinner spinner;
    Button workFile;
    Button demoFile;
    TextView textViewTitle;
    int module_id;
    public String name;
    TextView notification;
    public String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        savePostButton = findViewById(R.id.savePostButton);
        infoButton = findViewById(R.id.infoButton);
        textInputTitle = (TextInputEditText) findViewById(R.id.titleValue);
        textInputDescription = (TextInputEditText) findViewById(R.id.descriptionValue);
        allModules = new ArrayList<String>();
        moduleNames = new ArrayList<String>();
        spinner = (Spinner) findViewById(R.id.moduleSpinner);
        workFile = findViewById(R.id.createWorkFile);
        demoFile = findViewById(R.id.createDemoFile);

        getModules();

        infoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(createPost.this);
                alertDialog.setTitle("Post Info");
                alertDialog.setMessage("This app makes use of the constructivist learning theory. This allows students " +
                        "to learn from a demonstration (presented by a lecturer). Students can then use the demonstration to further " +
                        "there knowledge independently and complete any work set for them" + "\n" + "\n" +
                        "It is important to clearly explain the material through the use of several formats of resources such as notes, recordings, activities. This means that all types of learnings" +
                        "are catered for and student will allow student access to as many resources as possible" + "\n" + "\n");

                alertDialog.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                alertDialog.show();
            }
        });


        demoFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), createDemoFile.class);
                startActivity(i);
            }
        });

        workFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), createWorkFile.class);
                startActivity(i);
            }
        });


        savePostButton.setOnClickListener(new View.OnClickListener() {
            // @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            public void onClick(View view) {
                String description, demonstrationName,  studentWork;
                String stringModuleId = String.valueOf(module_id);

                title = String.valueOf(textInputTitle.getText());
                description = String.valueOf(textInputDescription.getText());
                demonstrationName = String.valueOf(createDemoFile.demofileNameToSave);
                studentWork = String.valueOf(createWorkFile.workfileNameToSave);
                //studentWork = String.valueOf(textInputStudentWork.getText());

                if (!title.equals("") && !description.equals("") && !studentWork.equals("")) {
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            // uploadMultipart();
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[5];
                            field[0] = "title";
                            field[1] = "description";
                            field[2] = "demonstration";
                            field[3] = "studentWork";
                            field[4] = "module_id";


                            //Creating array for data
                            String[] data = new String[5];
                            data[0] = title;
                            data[1] = description;
                            data[2] = demonstrationName;
                            data[3] = studentWork;
                            data[4] = stringModuleId;

                            PutData putData = new PutData("http://192.168.1.87:8888/Lectly/savePost.php", "POST", field, data);
                            if (putData.startPut()) {
                                //uploadMultipart();
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if (result.equals("Your post has been successfully posted")) {
                                        Toast.makeText(createPost.this, "Your post has been successfully posted", Toast.LENGTH_LONG).show();
                                        getPostId();
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

    private void getPostId(){
        StringRequest stringRequest = new StringRequest("http://192.168.1.87:8888/Lectly/getNewPost.php?title=" + title,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject newPost = null;
                        try {
                            newPost = new JSONObject(response);
                            JSONArray postresult = newPost.getJSONArray(PostDetails.JSON_ARRAY);
                            for (int i = 0; i < postresult.length(); i++) {
                                JSONObject jsonObject = postresult.getJSONObject(i);
                                String newPostId = jsonObject.getString("id");

                                addNewPostToDashboard(newPostId);
                                //Toast.makeText(createPost.this, "post id is " + newPostId, Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void addNewPostToDashboard(String newPostId) {
        String no_of_saves = "0";
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                //Starting Write and Read data with URL
                //Creating array for parameters
                String[] field = new String[2];
                field[0] = "post_id";
                field[1] = "no_of_saves";

                //Creating array for data
                String[] data = new String[2];
                data[0] = newPostId;
                data[1] = no_of_saves;

                PutData putData = new PutData("http://192.168.1.87:8888/Lectly/addPostToDashboard.php", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                         Toast.makeText(createPost.this, result, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void getModules() {
        StringRequest stringRequest = new StringRequest("http://192.168.1.87:8888/Lectly/getModules.php",
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

                //getting the module id
                for (int i = 0; i < moduleNames.size(); i++) {
                    if (moduleNames.get(i) == chosenModuleName) {
                        module_id = i + 1;
                    }
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "Please select the module you wish to post in", Toast.LENGTH_SHORT).show();
            }
        });
    }
}




