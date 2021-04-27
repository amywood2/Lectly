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
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class savedPosts extends AppCompatActivity {

    TextView userId;
    private JSONArray result;
    private JSONArray savedResult;
    private JSONArray modresult;
    public static String idClicked;
    boolean isPostClicked;
    public int i;
    public String id;
    public int j;
    public String title;

    public String description;
    public String module_id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_posts);

        getSavedPosts();
    }

    private void getSavedPosts() {

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

                            int number = result.length();

                            for (j  = 0; j < result.length(); j++) {
                                JSONObject jsonObject = result.getJSONObject(j);

                                String post_id = jsonObject.getString("post_id");

                                StringRequest stringRequest = new StringRequest("http://192.168.1.87:8888/Lectly/getAllSavedPosts.php?id=" + post_id,
                                        new Response.Listener<String>() {
                                            @SuppressLint("ResourceAsColor")
                                            @Override
                                            public void onResponse(String response) {
                                                JSONObject allPosts = null;
                                                try {
                                                    allPosts = new JSONObject(response.toString());
                                                    result = allPosts.getJSONArray(PostDetails.JSON_ARRAY);

                                                    for (int i = 0; i < result.length(); i++) {


                                                        JSONObject jsonObject = result.getJSONObject(i);

                                                        String id = jsonObject.getString("id");
                                                        String title = jsonObject.getString("title");
                                                        //String timeAndDate = jsonObject.getString("description");
                                                        String description = jsonObject.getString("description");
                                                        module_id = jsonObject.getString("module_id");

                                                        TextView titleView = new TextView(savedPosts.this);
                                                        titleView.setTextSize(28);

                                                        TextView timeDate = new TextView(savedPosts.this);
                                                        TextView descriptionV = new TextView(savedPosts.this);
                                                        TextView lecturerName = new TextView(savedPosts.this);
                                                        TextView moduleNameV = new TextView(savedPosts.this);

                                                        descriptionV.setTextSize(18);

                                                        titleView.setText(title);
                                                        titleView.setTextColor(R.color.black);
                                                        timeDate.setText(" TIME DATE");

                                                        descriptionV.setText(description);
                                                        descriptionV.setTextColor(R.color.black);

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
                                                        modrequestQueue.add(modStringRequest);

                                                        if (i == 1){
                                                            timeDate.setText("15/02/21");
                                                            lecturerName.setText("Posted by Keith Vermont");
                                                        } else if (i == 2) {
                                                            timeDate.setText("22/02/21");
                                                            lecturerName.setText("Posted by Sarah Findlay");
                                                        }else{
                                                            timeDate.setText("1/03/21");
                                                            lecturerName.setText("Posted by Thomas Douglas");
                                                        }

                                                        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams
                                                                ((int) FrameLayout.LayoutParams.MATCH_PARENT, (int) FrameLayout.LayoutParams.WRAP_CONTENT);
                                                        CardView.LayoutParams cardParams = new CardView.LayoutParams
                                                                ((int) CardView.LayoutParams.MATCH_PARENT, (int) CardView.LayoutParams.WRAP_CONTENT);


                                                        layoutParams.width = 1150;
                                                        layoutParams.leftMargin = -50;
                                                        //layoutParams.rightMargin = 100;
                                                        layoutParams.topMargin = j * 570;

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

