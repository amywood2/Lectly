package com.example.lectly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class viewPost extends AppCompatActivity {

    private String getLecturerIdClicked;
    private String getStudentIdClicked;
    private JSONArray postresult;
    private JSONArray modresult;
    TextView postName;
    TextView postDescription;
    TextView postDemo;
    TextView postWork;
    FloatingActionButton backButton;
    TextView postModule;
    ArrayList<Comment> comments;
    FloatingActionButton saveButton;
    public String module_id;
    public static EditText commentsInput;
    FloatingActionButton sendButton;

    RecyclerView viewComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);

        getLecturerIdClicked = lecturerMain.idClicked;
        getStudentIdClicked = studentMain.idClicked;
        postName = findViewById(R.id.postName);
        postDescription = findViewById(R.id.postDescription);
        postDemo = findViewById(R.id.postDemo);
        postWork = findViewById(R.id.postWork);
        postModule = findViewById(R.id.postModule);

        commentsInput = findViewById(R.id.commentsInput);
        sendButton = findViewById(R.id.sendButton);

        postModule.setText(lecturerMain.moduleNameClicked);
        saveButton = findViewById(R.id.saveThisPost);


        if (typeOfUser.typeofuser == "student") {
            saveButton.setVisibility(View.VISIBLE);
            postModule.setText(studentMain.moduleNameClicked);
        } else {
            postModule.setText(lecturerMain.moduleNameClicked);
        }


        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(viewPost.this);
                alertDialog.setTitle("Save this post?");
                alertDialog.setMessage("You can find this post in your saved posts");

                if (typeOfUser.typeofuser == "lecturer") {
                    alertDialog.setPositiveButton("Save Post", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            //add post to savepost database
                            Handler handler = new Handler();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    //Starting Write and Read data with URL
                                    //Creating array for parameters
                                    String[] field = new String[2];
                                    field[0] = "student_id";
                                    field[1] = "post_id";

                                    //Creating array for data
                                    String[] data = new String[2];
                                    data[0] = LoginActivity.user_id;
                                    data[1] = lecturerMain.idClicked;

                                    PutData putData = new PutData("http://<ip-address>:3306/Lectly/savedSection.php", "POST", field, data);
                                    if (putData.startPut()) {
                                        if (putData.onComplete()) {
                                            String result = putData.getResult();
                                            if (result.equals("Your post has been successfully saved")) {
                                                Toast.makeText(viewPost.this, "Your post has been successfully saved", Toast.LENGTH_LONG).show();

                                            } else {
                                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                }
                            });
                            //change icon to filled in
                            saveButton.setImageResource(R.drawable.filledsaveicon);

                            StringRequest updatestringRequest = new StringRequest("http://<ip-address>:3306/Lectly/updateTotalSaves.php?post_id=" + lecturerMain.idClicked,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            JSONObject updatedSaves;
                                            try {
                                                updatedSaves = new JSONObject(response);
                                                JSONArray updateresult = updatedSaves.getJSONArray(SavedPostsDetails.JSON_ARRAY);

                                                for (int i = 0; i < updateresult.length(); i++) {
                                                    JSONObject jsonObject = updateresult.getJSONObject(i);
                                                    String total_saves = jsonObject.getString("no_of_saves");
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

                            RequestQueue updaterequestQueue = Volley.newRequestQueue(viewPost.this);
                            updaterequestQueue.add(updatestringRequest);

                            dialog.cancel();
                        }
                    });
                    alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alertDialog.show();
                }else {

                    alertDialog.setPositiveButton("Save Post", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            //add post to savepost database
                            Handler handler = new Handler();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    //Starting Write and Read data with URL
                                    //Creating array for parameters
                                    String[] field = new String[2];
                                    field[0] = "student_id";
                                    field[1] = "post_id";

                                    //Creating array for data
                                    String[] data = new String[2];
                                    data[0] = LoginActivity.user_id;
                                    data[1] = studentMain.idClicked;

                                    PutData putData = new PutData("http://<ip-address>:3306/Lectly/savedSection.php", "POST", field, data);
                                    if (putData.startPut()) {
                                        if (putData.onComplete()) {
                                            String result = putData.getResult();
                                            if (result.equals("Your post has been successfully saved")) {
                                                Toast.makeText(viewPost.this, "Your post has been successfully saved", Toast.LENGTH_LONG).show();

                                            } else {
                                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                }
                            });
                            //change icon to filled in
                            saveButton.setImageResource(R.drawable.filledsaveicon);

                            StringRequest updatestringRequest = new StringRequest("http://<ip-address>:3306/Lectly/updateTotalSaves.php?post_id=" + studentMain.idClicked,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            JSONObject updatedSaves;
                                            try {
                                                updatedSaves = new JSONObject(response);
                                                JSONArray updateresult = updatedSaves.getJSONArray(SavedPostsDetails.JSON_ARRAY);

                                                for (int i = 0; i < updateresult.length(); i++) {
                                                    JSONObject jsonObject = updateresult.getJSONObject(i);
                                                    String total_saves = jsonObject.getString("no_of_saves");
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

                            RequestQueue updaterequestQueue = Volley.newRequestQueue(viewPost.this);
                            updaterequestQueue.add(updatestringRequest);

                            dialog.cancel();
                        }
                    });
                    alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alertDialog.show();
                }
            }
        });

        getPost();


        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Lookup the recyclerview in activity layout
                viewComments = (RecyclerView) findViewById(R.id.viewComments);

                // Initialize contacts
                comments = Comment.createCommentList(1);
                // Create adapter passing in the sample user data
                CommentsAdapter adapter = new CommentsAdapter(comments);
                // Attach the adapter to the recyclerview to populate items
                viewComments.setAdapter(adapter);
                // Set layout manager to position the items
                viewComments.setLayoutManager(new LinearLayoutManager(viewPost.this));

                commentsInput.setHint("Add a comment");
            }
        });

    }


    private void getPost() {
        StringRequest stringRequest;
        if (typeOfUser.typeofuser == "lecturer") {
            stringRequest = new StringRequest("http://<ip-address>:3306/Lectly/getIndividualPost.php?id=" + getLecturerIdClicked,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject thePost = null;
                            try {
                                thePost = new JSONObject(response.toString());
                                postresult = thePost.getJSONArray(PostDetails.JSON_ARRAY);

                                for (int i = 0; i < postresult.length(); i++) {
                                    JSONObject jsonObject = postresult.getJSONObject(i);
                                    String title = jsonObject.getString(PostDetails.TITLE);
                                    String description = jsonObject.getString(PostDetails.DESCRIPTION);
                                    String demo = jsonObject.getString(PostDetails.DEMONSTRATION);
                                    String work = jsonObject.getString(PostDetails.STUDENTWORK);
                                    module_id = jsonObject.getString("module_id");
                                    postName.setText(title);
                                    postDescription.setText(description);
                                    postDemo.setText("Lecture file: " + demo+ ".pdf");
                                    postWork.setText("Work to be completed: " + work+ ".pdf");
                                 //   postModule.setText("here");
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
        } else {
            stringRequest = new StringRequest("http://<ip-address>:3306/Lectly/getIndividualPost.php?id=" + getStudentIdClicked,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject thePost = null;
                            try {
                                thePost = new JSONObject(response.toString());
                                postresult = thePost.getJSONArray(PostDetails.JSON_ARRAY);

                                for (int i = 0; i < postresult.length(); i++) {
                                    JSONObject jsonObject = postresult.getJSONObject(i);
                                    String title = jsonObject.getString(PostDetails.TITLE);
                                    String description = jsonObject.getString(PostDetails.DESCRIPTION);
                                    String demo = jsonObject.getString(PostDetails.DEMONSTRATION);
                                    String work = jsonObject.getString(PostDetails.STUDENTWORK);
                                    module_id = jsonObject.getString("module_id");
                                    postName.setText(title);
                                    postDescription.setText(description);
                                    postDemo.setText("Lecture file: " + demo + ".pdf");
                                    postWork.setText("Work to be completed: " + work + ".pdf");

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
        }
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public void getModuleName() {
        StringRequest moduleStringRequest = new StringRequest("http://<ip-address>:3306/Lectly/getIndividualModule.php?id=" ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject modules = null;
                        try {
                            modules = new JSONObject(response.toString());
                            modresult = modules.getJSONArray(PostDetails.JSON_ARRAY);

                            for (int i = 0; i < modresult.length(); i++) {
                                JSONObject jsonObject = modresult.getJSONObject(i);
                                String module_name = jsonObject.getString("module_name");
                                postModule.setText("module name is " + module_name);

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
        RequestQueue modrequestQueue = Volley.newRequestQueue(this);
        modrequestQueue.add(moduleStringRequest);
    }
}
