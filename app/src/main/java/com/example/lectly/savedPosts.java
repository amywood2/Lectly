package com.example.lectly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class savedPosts extends AppCompatActivity {

    TextView userId;
    private JSONArray result;
    public static String idClicked;
    boolean isPostClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_posts);

        userId = findViewById(R.id.userIdTextView);

        userId.setText(LoginActivity.loggedInUserId);

        getSavedPosts();
    }

    private void getSavedPosts() {
        final FrameLayout layout = findViewById(R.id.frameLayout);
        StringRequest stringRequest = new StringRequest("http://192.168.1.87:8888/Lectly/getSavedPosts.php",
                new Response.Listener<String>() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onResponse(String response) {
                        JSONObject allPosts = null;
                        try {
                            allPosts = new JSONObject(response.toString());
                            result = allPosts.getJSONArray(PostDetails.JSON_ARRAY);

                            for (int i = 0; i < result.length(); i++) {

                                ConstraintLayout postLayout = new ConstraintLayout(savedPosts.this);
                                CardView card = new CardView(savedPosts.this);
                                JSONObject jsonObject = result.getJSONObject(i);

                                String id = jsonObject.getString("id");
                                String title = jsonObject.getString("title");
                                //String timeAndDate = jsonObject.getString("description");
                                String description = jsonObject.getString("description");
                                TextView titleView = new TextView(savedPosts.this);
                                titleView.setTextSize(28);

                                TextView timeDate = new TextView(savedPosts.this);
                                TextView descriptionV = new TextView(savedPosts.this);
                                TextView lecturerName = new TextView(savedPosts.this);
                                descriptionV.setTextSize(18);

                                titleView.setText(title);
                                titleView.setTextColor(R.color.black);
                                timeDate.setText(" TIME DATE");
                                descriptionV.setText(description);
                                descriptionV.setTextColor(R.color.black);

                                //getModuleName();


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

                                CardView.LayoutParams imageParams = new CardView.LayoutParams
                                        ((int) CardView.LayoutParams.MATCH_PARENT, (int) CardView.LayoutParams.WRAP_CONTENT);

                                ImageView deleteFromSaved = new ImageView(savedPosts.this);
                                deleteFromSaved.setImageResource(R.drawable.deleteicon);
                                layoutParams.width = 1150;
                                layoutParams.leftMargin = -50;
                                // layoutParams.rightMargin = 100;
                                layoutParams.topMargin = i * 570;

                                card.setPadding(100, 10, 10, 10);
                                card.setCardElevation(10);
                                card.setRadius(15);
                                card.setClickable(true);

                                imageParams.width = 100;
                                imageParams.height = 100;

                                imageParams.setMargins(900, 380, 10, 0);

                                postLayout.setLayoutParams(layoutParams);
                                card.setLayoutParams(cardParams);
                                card.setContentPadding(50, 10, 50, 40);
                                card.setBackgroundResource(R.drawable.card_boarder);

                                deleteFromSaved.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(savedPosts.this);
                                        alertDialog.setTitle("Remove from saved post?");
                                        alertDialog.setMessage("This post will no longer be in your saved posts.");
                                        //add extra resources
                                        alertDialog.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                //REMOVE FROM SAVED POSTS
                                            }
                                        });
                                        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });
                                        alertDialog.show();
                                    }
                                });


                                titleView.setPadding(10, 10, 10, 50);
                                timeDate.setPadding(10, 100, 10, 10);
                                descriptionV.setPadding(10, 200, 10, 10);
                                lecturerName.setPadding(10, 400, 10, 0);
                                ;


                                card.addView(titleView);
                                card.addView(timeDate);
                                card.addView(descriptionV);
                                card.addView(deleteFromSaved);
                                card.addView(lecturerName);


                                //card.addView(relativeLayout);
                                postLayout.addView(card);
                                layout.addView(postLayout);


                                card.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        idClicked = id;
                                        Intent j = new Intent(getApplicationContext(), viewPost.class);
                                        startActivity(j);
                                        //textViewTitle.setText("get from button is  " + postNameClicked);

                                    }
                                });
                            }

                            //textViewTitle.setText(allPosts.get(PostDetails.TITLE));
                            // getTitles(result);
                            // getDescriptions(result);
                            // getDemonstrations(result);
                            // getStudentWorks(result);
                            //creatingNewsfeed(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            TextView noposts = new TextView(savedPosts.this);
                            noposts.setText("You have no saved posts. You can save posts to review later by clicking on the save icon in your newsfeed.");
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

/*    public void getModuleName() {
        StringRequest modStringRequest = new StringRequest("http://192.168.1.87:8888/Lectly/getIndividualModule.php?id=" + id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject modules;
                        try {
                            modules = new JSONObject(response);
                            modresult = modules.getJSONArray(PostDetails.JSON_ARRAY);
                            for (int i = 0; i < modresult.length(); i++) {
                                module = new TextView(lecturerMain.this);
                                JSONObject jsonObject = modresult.getJSONObject(i);
                                String module_name = jsonObject.getString("module_name");
                                module.setText(module_name);
                                FrameLayout.LayoutParams modParams = new FrameLayout.LayoutParams
                                        ((int) FrameLayout.LayoutParams.MATCH_PARENT, (int) FrameLayout.LayoutParams.WRAP_CONTENT);
                                module.setPadding(10, 10, 10, 50);
                                modParams.topMargin = i * 450;
                                module.setLayoutParams(modParams);

                                card.addView(module);

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
        RequestQueue requestQueue = Volley.newRequestQueue(lecturerMain.this);
        requestQueue.add(modStringRequest);
    }*/
}