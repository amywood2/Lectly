package com.example.lectly;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;


public class allPosts extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private static final String TAG = null;
    FloatingActionButton files;
    FloatingActionButton menu;
    FloatingActionButton create;
    FloatingActionButton filter;
    TextView dataView;
    private ArrayList<String> postTitles;
    private ArrayList<String> postDescriptions;
    private ArrayList<String> postDemonstrations;
    private ArrayList<String> postStudentWorks;
    private JSONArray result;
    private TextView textViewTitle;
    private TextView textViewDescription;
    private TextView textViewDemonstration;
    private TextView textViewStudentWork;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_main);
        setupUI();
        getPosts();

    }

    private void setupUI() {
        files = (FloatingActionButton) findViewById(R.id.lNotesButton);
        //menu = (Button) findViewById(R.id.menu);
        create = (FloatingActionButton) findViewById(R.id.createButton);
        menu =  (FloatingActionButton) findViewById(R.id.lMenuButton);
        filter =  (FloatingActionButton) findViewById(R.id.lFilterButton);
        //dataView = findViewById(R.id.textViewTitle);

        postTitles = new ArrayList<String>();
        postDescriptions = new ArrayList<String>();
        postDemonstrations = new ArrayList<String>();
        postStudentWorks = new ArrayList<String>();
        // textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        // textViewDescription = (TextView) findViewById(R.id.textViewDescription);
        // textViewDemonstration = (TextView) findViewById(R.id.textViewDemonstration);
        //  textViewStudentWork = (TextView) findViewById(R.id.textViewStudentWork);




        create.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), createPost.class);
                startActivity(i);
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), lecturerMenu.class);
                startActivity(i);
            }
        });

        files.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), notesActivity.class);
                startActivity(i);
            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  filter dialog
            }
        });
    }

    private void getPosts() {
        StringRequest stringRequest = new StringRequest("http://192.168.5.31:8888/Lectly/getPosts.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject allPosts = null;
                        try {
                            allPosts = new JSONObject(response);
                            result = allPosts.getJSONArray(PostDetails.JSON_ARRAY);
                            getTitles(result);
                            getDescriptions(result);
                            getDemonstrations(result);
                            getStudentWorks(result);
                            creatingNewsfeed(result);
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

    private void getTitles(JSONArray allPosts) {
        for (int i = 0; i < allPosts.length(); i++) {
            try {
                JSONObject json = allPosts.getJSONObject(i);
                postTitles.add(json.getString(PostDetails.TITLE));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //textViewTitle.setText(postTitles.get(0));
    }

    private void getDescriptions(JSONArray allPosts) {
        for (int i = 0; i < allPosts.length(); i++) {
            try {
                JSONObject json = allPosts.getJSONObject(i);
                postDescriptions.add(json.getString(PostDetails.DESCRIPTION));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //textViewDescription.setText(postDescriptions.get(0));
    }

    private void getDemonstrations(JSONArray allPosts) {
        for (int i = 0; i < allPosts.length(); i++) {
            try {
                JSONObject json = allPosts.getJSONObject(i);
                postDemonstrations.add(json.getString(PostDetails.DEMONSTRATION));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //textViewDemonstration.setText(postDemonstrations.get(0));
    }

    private void getStudentWorks(JSONArray allPosts) {
        for (int i = 0; i < allPosts.length(); i++) {
            try {
                JSONObject json = allPosts.getJSONObject(i);
                postStudentWorks.add(json.getString(PostDetails.STUDENTWORK));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // textViewStudentWork.setText(postStudentWorks.get(0));
    }

    private void creatingNewsfeed(JSONArray allPosts){
        final ConstraintLayout layout =  findViewById(R.id.constraintLayout);
        final Button[] b_titles = new Button[postTitles.size()];
        final TextView[] tv_descriptions = new TextView[postDescriptions.size()];
        final TextView[] tv_demonstrations = new TextView[postDemonstrations.size()];
        final TextView[] tv_studentWork = new TextView[postStudentWorks.size()];

        for (int i = 0; i < postTitles.size(); i++) {
            b_titles[i] = new Button(this);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams
                    ((int) LinearLayout.LayoutParams.WRAP_CONTENT,(int) LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 50;
            params.topMargin  = i*50;
            b_titles[i].setText(postTitles.get(i));
            b_titles[i].setTextSize((float) 20);
            if(i == 0){
                b_titles[i].setPadding(100, 50, 20, 50);
            }else{
                b_titles[i].setPadding(100, 450*i, 20, 50);
            }
            b_titles[i].setBackgroundColor(000000);
            b_titles[i].setLayoutParams(params);
            layout.addView(b_titles[i]);
        }

        for (int i = 0; i < postDescriptions.size(); i++) {
            tv_descriptions[i] = new TextView(this);
            ConstraintLayout.LayoutParams params=new ConstraintLayout.LayoutParams
                    ((int) ConstraintLayout.LayoutParams.WRAP_CONTENT,(int) ConstraintLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 50;
            params.topMargin  = i*50;
            tv_descriptions[i].setText(postDescriptions.get(i));
            tv_descriptions[i].setTextSize((float) 20);
            if(i == 0){
                tv_descriptions[i].setPadding(100, 100, 20, 50);
            }else{
                tv_descriptions[i].setPadding(100, 500*i, 20, 50);
            }
            tv_descriptions[i].setLayoutParams(params);
            layout.addView(tv_descriptions[i]);
        }

        for (int i = 0; i < postDemonstrations.size(); i++) {
            tv_demonstrations[i] = new TextView(this);
            ConstraintLayout.LayoutParams params=new ConstraintLayout.LayoutParams
                    ((int) ConstraintLayout.LayoutParams.WRAP_CONTENT,(int) ConstraintLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 50;
            params.topMargin  = i*50;
            tv_demonstrations[i].setText(postDemonstrations.get(i));
            tv_demonstrations[i].setTextSize((float) 20);
            if(i == 0){
                tv_demonstrations[i].setPadding(100, 150, 20, 50);
            }else{
                tv_demonstrations[i].setPadding(100, 550*i, 20, 50);
            }
            tv_demonstrations[i].setLayoutParams(params);
            layout.addView(tv_demonstrations[i]);
        }

        for (int i = 0; i < postStudentWorks.size(); i++) {
            tv_studentWork[i] = new TextView(this);
            ConstraintLayout.LayoutParams params=new ConstraintLayout.LayoutParams
                    ((int) ConstraintLayout.LayoutParams.WRAP_CONTENT,(int) ConstraintLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 50;
            params.topMargin  = i*50;

            tv_studentWork[i].setText(postStudentWorks.get(i));
            tv_studentWork[i].setTextSize((float) 20);
            if(i == 0){
                tv_studentWork[i].setPadding(100, 200, 20, 50);
            }else{
                tv_studentWork[i].setPadding(100, 600*i, 20, 50);
            }
            tv_studentWork[i].setLayoutParams(params);
            layout.addView(tv_studentWork[i]);
        }

    }
}