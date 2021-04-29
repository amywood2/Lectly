package com.example.lectly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class savedPosts extends AppCompatActivity {

    TextView userId;
    private JSONArray result;
    private JSONArray savedResult;
    private JSONArray modresult;
    private JSONArray lecturerResult;
    public static String idClicked;
    boolean isPostClicked;
    public String id;
    public String title;
    public String description;
    public String module_id;
    public String module_lecturer_id;
    public static String moduleNameClicked;
    ProgressDialog loadingDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_posts);

        getSavedPosts();
    }

    private void getSavedPosts() {

        loadingDialog = new ProgressDialog(this); // this = YourActivity
        loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loadingDialog.setTitle("Loading");
        loadingDialog.setMessage("Loading all saved posts...");
        loadingDialog.setIndeterminate(true);
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.show();

        final FrameLayout savedLayout = findViewById(R.id.frameLayoutSaved);
        StringRequest idStringRequest = new StringRequest("http://192.168.1.87:8888/Lectly/lookUpSavedPosts.php?student_id=" + LoginActivity.loggedInUserId,
                new Response.Listener<String>() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onResponse(String response) {
                        JSONObject allPosts;
                        try {
                            allPosts = new JSONObject(response.toString());
                            result = allPosts.getJSONArray(PostDetails.JSON_ARRAY);

                            for (int i  = 0; i < result.length(); i++) {
                                JSONObject jsonObject = result.getJSONObject(i);

                                String post_id = jsonObject.getString("post_id");

                                TextView timeDate = new TextView(savedPosts.this);
                                TextView descriptionV = new TextView(savedPosts.this);
                                TextView lecturerName = new TextView(savedPosts.this);
                                TextView moduleNameV = new TextView(savedPosts.this);
                                TextView titleView = new TextView(savedPosts.this);
                                titleView.setTextSize(28);

                                descriptionV.setTextSize(18);

                                StringRequest stringRequest = new StringRequest("http://192.168.1.87:8888/Lectly/getAllSavedPosts.php?id=" + post_id,
                                        new Response.Listener<String>() {
                                            @SuppressLint("ResourceAsColor")
                                            @Override
                                            public void onResponse(String response) {
                                                JSONObject allPosts = null;
                                                try {
                                                    allPosts = new JSONObject(response.toString());
                                                    savedResult = allPosts.getJSONArray(PostDetails.JSON_ARRAY);

                                                    for (int i = 0; i < savedResult.length(); i++) {


                                                        JSONObject jsonObject = savedResult.getJSONObject(i);

                                                        String id = jsonObject.getString("id");
                                                        String title = jsonObject.getString("title");
                                                        //String timeAndDate = jsonObject.getString("description");
                                                        String description = jsonObject.getString("description");
                                                        module_id = jsonObject.getString("module_id");

                                                        titleView.setText(title);
                                                        descriptionV.setText(description);


                                                        StringRequest modStringRequest = new StringRequest("http://192.168.1.87:8888/Lectly/getIndividualModule.php?id=" + module_id,
                                                                new Response.Listener<String>() {
                                                                    @Override
                                                                    public void onResponse(String response) {
                                                                        JSONObject modules = null;
                                                                        try {
                                                                            modules = new JSONObject(response.toString());
                                                                            modresult = modules.getJSONArray(ModuleDetails.JSON_ARRAY);
                                                                            for (int i = 0; i < modresult.length(); i++) {
                                                                                JSONObject jsonObject = modresult.getJSONObject(i);
                                                                                //String moduleid = jsonObject.getString("id");
                                                                                String moduleName = jsonObject.getString(ModuleDetails.MODULENAME);
                                                                                module_lecturer_id = jsonObject.getString("module_lecturer_id");
                                                                                //module_lecturer = "this worked";
                                                                                moduleNameV.setText(moduleName);
                                                                                moduleNameV.setTextSize(16);
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
                                                        RequestQueue modrequestQueue = Volley.newRequestQueue(savedPosts.this);
                                                        modrequestQueue.add(modStringRequest);

                                                        StringRequest lecturerstringRequest = new StringRequest("http://192.168.1.87:8888/Lectly/getLecturerName.php?id=" + module_lecturer_id,
                                                                new Response.Listener<String>() {
                                                                    @Override
                                                                    public void onResponse(String response) {
                                                                        JSONObject lecturer;
                                                                        try {
                                                                            lecturer = new JSONObject(response);
                                                                            lecturerResult = lecturer.getJSONArray(PersonDetails.JSON_ARRAY);


                                                                            for (int i = 0; i < lecturerResult.length(); i++) {
                                                                                JSONObject jsonObject = lecturerResult.getJSONObject(i);
                                                                                String lecturer_name = jsonObject.getString("fullname");
                                                                                lecturerName.setText("Posted by " + lecturer_name);
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

                                                        RequestQueue lecturerrequestQueue = Volley.newRequestQueue(savedPosts.this);
                                                        lecturerrequestQueue.add(lecturerstringRequest);

                                                    }

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                    TextView noposts = new TextView(savedPosts.this);
                                                    noposts.setText("No posts to view");
                                                }
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {

                                            }
                                        });

                                RequestQueue requestQueue = Volley.newRequestQueue(savedPosts.this);
                                requestQueue.add(stringRequest);

                                titleView.setTextColor(R.color.black);

                                descriptionV.setTextSize(18);
                                descriptionV.setText(description);
                                descriptionV.setTextColor(R.color.black);

                                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams
                                        ((int) FrameLayout.LayoutParams.MATCH_PARENT, (int) FrameLayout.LayoutParams.WRAP_CONTENT);
                                CardView.LayoutParams cardParams = new CardView.LayoutParams
                                        ((int) CardView.LayoutParams.MATCH_PARENT, (int) CardView.LayoutParams.WRAP_CONTENT);
                                layoutParams.width = 1150;
                                layoutParams.leftMargin = -50;
                                //layoutParams.rightMargin = 100;
                                layoutParams.topMargin = i * 570;

                                ConstraintLayout postLayout = new ConstraintLayout(savedPosts.this);
                                CardView card = new CardView(savedPosts.this);

                                card.setPadding(100, 10, 10, 10);
                                card.setCardElevation(10);
                                card.setRadius(15);

                                postLayout.setLayoutParams(layoutParams);
                                card.setLayoutParams(cardParams);
                                card.setContentPadding(50, 10,50,40);
                                card.setBackgroundResource(R.drawable.card_boarder);

                                titleView.setPadding(10, 10, 10, 50);
                                moduleNameV.setPadding(10, 100, 10, 0);
                                timeDate.setPadding(10, 150, 10, 10);
                                descriptionV.setPadding(10, 230, 10, 10);
                                lecturerName.setPadding(10, 400, 10, 0);

                                card.addView(titleView);
                                card.addView(timeDate);
                                card.addView(descriptionV);
                                card.addView(lecturerName);
                                card.addView(moduleNameV);

                                postLayout.addView(card);
                                savedLayout.addView(postLayout);
                                loadingDialog.dismiss();

                                card.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        idClicked = post_id;
                                        moduleNameClicked = moduleNameV.getText().toString();
                                        Intent j = new Intent(getApplicationContext(), viewPost.class);
                                        startActivity(j);
                                    }
                                });
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
        RequestQueue requestQueue = Volley.newRequestQueue(savedPosts.this);
        requestQueue.add(idStringRequest);

    }
}

