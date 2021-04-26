package com.example.lectly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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

import java.util.ArrayList;

public class savedPosts extends AppCompatActivity {

    TextView userId;
    private JSONArray result;
    private JSONArray savedResult;
    private JSONArray modresult;
    public static String idClicked;
    boolean isPostClicked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_posts);

        getSavedPosts();
    }

    private void getSavedPosts() {
        final FrameLayout layout = findViewById(R.id.frameLayoutSaved);
        StringRequest idStringRequest = new StringRequest("http://192.168.1.87:8888/Lectly/lookUpSavedPosts.php?student_id=" + LoginActivity.loggedInUserId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject allPosts;
                        try {
                            allPosts = new JSONObject(response.toString());
                            result = allPosts.getJSONArray(PostDetails.JSON_ARRAY);

                            for (int i = 0; i < result.length(); i++) {
                                JSONObject jsonObject = result.getJSONObject(i);

                                String post_id = jsonObject.getString("post_id");

                                StringRequest savedStringRequest = new StringRequest("http://192.168.1.87:8888/Lectly/getAllSavedPosts.php?id=" + post_id,
                                        new Response.Listener<String>() {
                                            @SuppressLint("ResourceAsColor")
                                            @Override
                                            public void onResponse(String response) {
                                                JSONObject saveIds = null;
                                                try {
                                                    saveIds = new JSONObject(response.toString());
                                                    savedResult = saveIds.getJSONArray(PostDetails.JSON_ARRAY);

                                                    for (int i = 0; i < savedResult.length(); i++) {

                                                        ConstraintLayout postLayout = new ConstraintLayout(savedPosts.this);
                                                        CardView card = new CardView(savedPosts.this);
                                                        JSONObject jsonObject = savedResult.getJSONObject(i);

                                                        String id = jsonObject.getString("id");
                                                        String title = jsonObject.getString("title");
                                                        //String timeAndDate = jsonObject.getString("description");
                                                        String description = jsonObject.getString("description");
                                                        //module_id = jsonObject.getString("module_id");

                                                        TextView titleView = new TextView(savedPosts.this);
                                                        titleView.setTextSize(28);
                                                        TextView timeDate = new TextView(savedPosts.this);
                                                        TextView descriptionV = new TextView(savedPosts.this);
                                                        TextView lecturerName = new TextView(savedPosts.this);
                                                        TextView moduleNameV = new TextView(savedPosts.this);

                                                        descriptionV.setTextSize(18);

                                                        titleView.setText(post_id);
                                                        titleView.setTextColor(R.color.black);
                                                        timeDate.setText(" TIME DATE");

                                                        descriptionV.setText(description);
                                                        descriptionV.setTextColor(R.color.black);
/*
                                                        StringRequest modStringRequest = new StringRequest("http://192.168.1.87:8888/Lectly/getIndividualModule.php?id=",
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
                                                                                //module_lecturer = jsonObject.getString("module_lecturer_id");
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
                                                        modrequestQueue.add(modStringRequest);*/

                                                        if (i == 1) {
                                                            timeDate.setText("15/02/21");
                                                            lecturerName.setText("Posted by Keith Vermont");
                                                        } else if (i == 2) {
                                                            timeDate.setText("22/02/21");
                                                            lecturerName.setText("Posted by Sarah Findlay");
                                                        } else {
                                                            timeDate.setText("1/03/21");
                                                            lecturerName.setText("Posted by Thomas Douglas");
                                                        }

                                                        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams
                                                                ((int) FrameLayout.LayoutParams.MATCH_PARENT, (int) FrameLayout.LayoutParams.WRAP_CONTENT);
                                                        CardView.LayoutParams cardParams = new CardView.LayoutParams
                                                                ((int) CardView.LayoutParams.MATCH_PARENT, (int) CardView.LayoutParams.WRAP_CONTENT);


                                                        layoutParams.width = 1150;
                                                        layoutParams.leftMargin = -50;
                                                        layoutParams.topMargin = i * 570;

                                                        card.setPadding(100, 10, 10, 10);
                                                        card.setCardElevation(10);
                                                        card.setRadius(15);
                                                        card.setClickable(true);

                                                        postLayout.setLayoutParams(layoutParams);
                                                        card.setLayoutParams(cardParams);
                                                        card.setContentPadding(50, 10, 50, 40);
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
                                                        layout.addView(postLayout);

                                                        card.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View view) {
                                                                idClicked = id;
                                                                // moduleNameClicked = moduleNameV.getText().toString();
                                                                Intent j = new Intent(getApplicationContext(), viewPost.class);
                                                                startActivity(j);
                                                                //textViewTitle.setText("get from button is  " + postNameClicked);
                                                            }
                                                        });
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
                                RequestQueue saverequestQueue = Volley.newRequestQueue(savedPosts.this);
                                saverequestQueue.add(savedStringRequest);

//---------------------------------------------------------------------

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


