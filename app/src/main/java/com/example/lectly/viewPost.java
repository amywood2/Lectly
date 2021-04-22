package com.example.lectly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
    FloatingActionButton backButton;
    TextView postModule;
    ArrayList<Comment> comments;
    FloatingActionButton saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);

        getLecturerIdClicked = lecturerMain.idClicked;
        getStudentIdClicked = studentMain.idClicked;
        postName = findViewById(R.id.postName);
        postDescription = findViewById(R.id.postDescription);
        postModule = findViewById(R.id.postModule);
        backButton = findViewById(R.id.backButton);
        saveButton = findViewById(R.id.saveThisPost);

        if (typeOfUser.typeofuser == "student"){
            saveButton.setVisibility(View.VISIBLE);
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i;
                if (typeOfUser.typeofuser == "lecturer") {
                    i = new Intent(getApplicationContext(), lecturerMain.class);
                } else{
                    i = new Intent(getApplicationContext(), studentMain.class);
                }
                startActivity(i);
            }
        });

        getPost();

        // Lookup the recyclerview in activity layout
        RecyclerView viewComments = (RecyclerView) findViewById(R.id.viewComments);

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
                                    String description = jsonObject.getString("description");
                                    postName.setText(title);
                                    postDescription.setText("description is " + description);
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
                                    String description = jsonObject.getString("description");
                                    postName.setText(title);
                                    postDescription.setText("description is " + description);
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

/*
      StringRequest moduleStringRequest = new StringRequest("http://192.168.1.87:8888/Lectly/getIndividualModule.php?id=" + getidClicked ,
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
                                postModule.setText(module_name);

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
        modrequestQueue.add(moduleStringRequest);*/
    }
}