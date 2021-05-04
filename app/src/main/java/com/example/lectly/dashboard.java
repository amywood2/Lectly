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
import android.widget.Button;
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

public class dashboard extends AppCompatActivity {

    public JSONArray result;
    Button postSaves;
    Button studyTimes;
    public FrameLayout layout;

    ProgressDialog loadingDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        postSaves = findViewById(R.id.postSavesButton);
        studyTimes = findViewById(R.id.studyTimesButton);

        layout = findViewById(R.id.dashboardFrameLayout);
        TextView choose = new TextView(dashboard.this);
        choose.setText("Please choose an interaction...");
        choose.setTextSize(20);
        choose.setPadding(20, 10, 10,10);
        layout.addView(choose);

        postSaves.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveDashboard();
            }
        });

        studyTimes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                studyTimesDashboard();
            }
        });
    }

    public void saveDashboard(){
        loadingDialog = new ProgressDialog(this);
        loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loadingDialog.setTitle("Loading");
        loadingDialog.setMessage("Loading dashboard for post saves");
        loadingDialog.setIndeterminate(true);
        loadingDialog.setCanceledOnTouchOutside(false);

        layout.removeAllViews();
        loadingDialog.show();
        StringRequest stringRequest = new StringRequest("http://<ip-address>:3306/Lectly/getPosts.php",
                new Response.Listener<String>() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onResponse(String response) {
                        JSONObject allPosts = null;
                        try {
                            allPosts = new JSONObject(response.toString());
                            result = allPosts.getJSONArray(PostDetails.JSON_ARRAY);

                            for (int i = 0; i < result.length(); i++) {

                                ConstraintLayout postLayout = new ConstraintLayout(dashboard.this);
                                CardView card = new CardView(dashboard.this);
                                JSONObject jsonObject = result.getJSONObject(i);

                                String id = jsonObject.getString(PostDetails.ID);
                                String postTitle = jsonObject.getString(PostDetails.TITLE);

                                TextView titleView = new TextView(dashboard.this);
                                titleView.setTextSize(28);

                                TextView totalSavesV = new TextView(dashboard.this);
                                totalSavesV.setTextSize(18);

                                StringRequest dashStringRequest = new StringRequest("http://<ip-address>:3306/Lectly/getTotalSaves.php?post_id=" + id,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                JSONObject totalsaves = null;
                                                try {
                                                    totalsaves = new JSONObject(response.toString());
                                                    JSONArray saveresult = totalsaves.getJSONArray(ModuleDetails.JSON_ARRAY);

                                                    for (int k = 0; k < saveresult.length(); k++) {
                                                        JSONObject jsonObject = saveresult.getJSONObject(k);
                                                        String saves = jsonObject.getString("no_of_saves");

                                                        if (saves == null){
                                                            totalSavesV.setText("Total saves: 0");
                                                        } else {
                                                            totalSavesV.setText("Total saves: " + saves);
                                                        }
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
                                RequestQueue dashRequestQueue = Volley.newRequestQueue(dashboard.this);
                                dashRequestQueue.add(dashStringRequest);


                                titleView.setText(postTitle);
                                titleView.setTextColor(R.color.black);
                                totalSavesV.setTextColor(R.color.black);

                                titleView.setPadding(10, 10, 10, 40);
                                totalSavesV.setPadding(10, 100, 10, 10);

                                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams
                                        ((int) FrameLayout.LayoutParams.MATCH_PARENT, (int) FrameLayout.LayoutParams.WRAP_CONTENT);
                                CardView.LayoutParams cardParams = new CardView.LayoutParams
                                        ((int) CardView.LayoutParams.MATCH_PARENT, (int) CardView.LayoutParams.WRAP_CONTENT);

                                layoutParams.width = 1150;
                                layoutParams.leftMargin = -50;
                                //layoutParams.rightMargin = 100;

                                layoutParams.topMargin = i * 250;

                                //card.setPadding(100, 10, 10, 10);
                                card.setCardElevation(10);
                                card.setRadius(15);
                                card.setClickable(true);

                                postLayout.setLayoutParams(layoutParams);
                                card.setLayoutParams(cardParams);
                                card.setContentPadding(50, 10,50,40);
                                card.setBackgroundResource(R.drawable.card_boarder);

                                card.addView(titleView);
                                card.addView(totalSavesV);

                                postLayout.addView(card);
                                layout.addView(postLayout);
                                loadingDialog.dismiss();
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

    public void studyTimesDashboard(){
        loadingDialog = new ProgressDialog(this);
        loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loadingDialog.setTitle("Loading");
        loadingDialog.setMessage("Loading dashboard for student study times");
        loadingDialog.setIndeterminate(true);
        loadingDialog.setCanceledOnTouchOutside(true);

        layout.removeAllViews();
        loadingDialog.show();
        StringRequest stringRequest = new StringRequest("http://<ip-address>:3306/Lectly/getStudyTimes.php",
                new Response.Listener<String>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onResponse(String response) {
                JSONObject allTimes = null;
                try {
                    allTimes = new JSONObject(response.toString());
                    JSONArray result = allTimes.getJSONArray(ModuleDetails.JSON_ARRAY);

                    for (int i = 0; i < result.length(); i++) {
                        JSONObject jsonObject = result.getJSONObject(i);
                        String student_id  = jsonObject.getString("student_id");
                        String study_type = jsonObject.getString("type");
                        String study_timestamp = jsonObject.getString("timestamp");

                        ConstraintLayout postLayout = new ConstraintLayout(dashboard.this);
                        CardView card = new CardView(dashboard.this);

                        TextView nameView = new TextView(dashboard.this);
                        nameView.setTextSize(28);

                        StringRequest nameStringRequest = new StringRequest("http://<ip-address>:3306/Lectly/getIndividualStudent.php?id=" + student_id,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        JSONObject student = null;
                                        try {
                                            student = new JSONObject(response.toString());
                                            JSONArray aStudent = student.getJSONArray(ModuleDetails.JSON_ARRAY);

                                            for (int k = 0; k < aStudent.length(); k++) {
                                                JSONObject jsonObject = aStudent.getJSONObject(k);
                                                String student_name = jsonObject.getString(PersonDetails.FULLNAME);
                                                nameView.setText(student_name);
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
                        RequestQueue nameRequestQueue = Volley.newRequestQueue(dashboard.this);
                        nameRequestQueue.add(nameStringRequest);

                        TextView timeView = new TextView(dashboard.this);
                        timeView.setText(study_timestamp);
                        timeView.setTextSize(16);

                        TextView typeView = new TextView(dashboard.this);
                        typeView.setText(study_type);
                        typeView.setTextSize(18);

                        nameView.setTextColor(R.color.black);
                        typeView.setTextColor(R.color.black);
                        timeView.setTextColor(R.color.black);

                        nameView.setPadding(10, 10, 10, 50);
                        typeView.setPadding(10, 100, 10, 10);
                        timeView.setPadding(800, 50, 10, 10);

                        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams
                                ((int) FrameLayout.LayoutParams.MATCH_PARENT, (int) FrameLayout.LayoutParams.WRAP_CONTENT);
                        CardView.LayoutParams cardParams = new CardView.LayoutParams
                                ((int) CardView.LayoutParams.MATCH_PARENT, (int) CardView.LayoutParams.WRAP_CONTENT);

                        layoutParams.width = 1150;
                        layoutParams.leftMargin = -50;
                        //layoutParams.rightMargin = 100;

                        layoutParams.topMargin = i * 250;

                        //card.setPadding(100, 10, 10, 10);
                        card.setCardElevation(10);
                        card.setRadius(15);

                        postLayout.setLayoutParams(layoutParams);
                        card.setLayoutParams(cardParams);
                        card.setContentPadding(50, 20,50,40);
                        card.setBackgroundResource(R.drawable.card_boarder);

                        card.addView(nameView);
                        card.addView(typeView);
                        card.addView(timeView);

                        postLayout.addView(card);
                        layout.addView(postLayout);
                        loadingDialog.dismiss();
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


}


