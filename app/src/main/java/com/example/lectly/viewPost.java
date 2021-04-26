package com.example.lectly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
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


        postModule.setText(lecturerMain.moduleNameClicked);
        backButton = findViewById(R.id.backButton);
        saveButton = findViewById(R.id.saveThisPost);


        if (typeOfUser.typeofuser == "student") {
            saveButton.setVisibility(View.VISIBLE);
            postModule.setText(studentMain.moduleNameClicked);
        }else {
            postModule.setText(lecturerMain.moduleNameClicked);
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i;
                if (typeOfUser.typeofuser == "lecturer") {
                    i = new Intent(getApplicationContext(), lecturerMain.class);
                } else {
                    i = new Intent(getApplicationContext(), studentMain.class);
                }
                startActivity(i);
            }
        });

        /*saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(viewPost.this);
                    alertDialog.setTitle("Save this post?");
                    alertDialog.setMessage("You can find this post in your saved posts");
                    //add extra resources
                    alertDialog.setPositiveButton("Save Post", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //add post to savepost database



                            //change icon to filled in
                            saveButton.setImageResource(R.drawable.filledsaveicon);
                            dialog.cancel();
                            studentMain.isSaved = true;
                        }
                    });
                    alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alertDialog.show();
                } else {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(viewPost.this);
                    alertDialog.setTitle("Unsave this post?");
                    alertDialog.setMessage("This post will be removed from your saved posts");
                    //add extra resources
                    alertDialog.setPositiveButton("Unsave Post", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //remove post from saved posts
                            //change icon to unfilled in
                            saveButton.setImageResource(R.drawable.saveicon);
                            dialog.cancel();
                            //studentMain.isSaved = false;
                        }
                    });
                    alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alertDialog.show();
                }

        });*/

        getPost();
        //getModuleName();

        // Lookup the recyclerview in activity layout
        viewComments = (RecyclerView) findViewById(R.id.viewComments);

        // Initialize contacts
        comments = Comment.createCommentList(10);
        // Create adapter passing in the sample user data
        CommentsAdapter adapter = new CommentsAdapter(comments);
        // Attach the adapter to the recyclerview to populate items
        viewComments.setAdapter(adapter);
        // Set layout manager to position the items
        viewComments.setLayoutManager(new LinearLayoutManager(this));
    }


    private void getPost() {
        StringRequest stringRequest;
        if (typeOfUser.typeofuser == "lecturer") {
            stringRequest = new StringRequest("http://192.168.1.87:8888/Lectly/getIndividualPost.php?id=" + getLecturerIdClicked,
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
            stringRequest = new StringRequest("http://192.168.1.87:8888/Lectly/getIndividualPost.php?id=" + getStudentIdClicked,
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
        StringRequest moduleStringRequest = new StringRequest("http://192.168.1.87:8888/Lectly/getIndividualModule.php?id=" ,
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
