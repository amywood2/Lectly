package com.example.lectly;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;


public class studentMain extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private static final String TAG = null;
    FloatingActionButton menu;
    FloatingActionButton filter;
    FloatingActionButton notes;
    TextView dataView;
    private ArrayList<String> postTitles;
    private ArrayList<String> postDescriptions;
    private ArrayList<String> postDemonstrations;
    private ArrayList<String> postStudentWorks;
    private JSONArray result;

    public static String idClicked;

    Boolean isSaved = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);
        setupUI();
        getPosts();

    }

    private void setupUI() {
        //files = (Button) findViewById(R.id.files);
        menu =  (FloatingActionButton) findViewById(R.id.sMenuButton);
        filter =  (FloatingActionButton) findViewById(R.id.sFilterButton);
        notes =  (FloatingActionButton) findViewById(R.id.sNotesButton);
        //dataView = findViewById(R.id.textViewTitle);

        postTitles = new ArrayList<String>();
        postDescriptions = new ArrayList<String>();
        postDemonstrations = new ArrayList<String>();
        postStudentWorks = new ArrayList<String>();

        menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), studentMenu.class);
                startActivity(i);
            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  filter dialog
            }
        });

        notes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), notesActivity.class);
                startActivity(i);
            }
        });
    }

    private void getPosts() {
        final FrameLayout layout = findViewById(R.id.frameLayout);
        StringRequest stringRequest = new StringRequest("http://192.168.1.87:8888/Lectly/getPosts.php",
                new Response.Listener<String>() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onResponse(String response) {
                        JSONObject allPosts = null;
                        try {
                            allPosts = new JSONObject(response.toString());
                            result = allPosts.getJSONArray(PostDetails.JSON_ARRAY);

                            for (int i = 0; i < result.length(); i++) {

                                ConstraintLayout postLayout = new ConstraintLayout(studentMain.this);
                                CardView card = new CardView(studentMain.this);
                                JSONObject jsonObject = result.getJSONObject(i);

                                String id = jsonObject.getString("id");
                                String title = jsonObject.getString("title");
                                //String timeAndDate = jsonObject.getString("description");
                                String description = jsonObject.getString("description");
                                TextView titleView = new TextView(studentMain.this);
                                titleView.setTextSize(28);

                                TextView timeDate = new TextView(studentMain.this);
                                TextView descriptionV = new TextView(studentMain.this);
                                TextView lecturerName = new TextView(studentMain.this);
                                descriptionV.setTextSize(18);

                                titleView.setText(title);
                                titleView.setTextColor(R.color.black);
                                timeDate.setText(" TIME DATE");
                                descriptionV.setText(description);
                                descriptionV.setTextColor(R.color.black);


                           /*     StringRequest modStringRequest = new StringRequest("http://192.168.1.87:8888/Lectly/getIndividualModule.php?id=" + id,
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
                                                            modParams.topMargin= i * 450;
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
                               requestQueue.add(modStringRequest);*/

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

                                CardView.LayoutParams imageParams = new CardView.LayoutParams
                                        ((int) CardView.LayoutParams.MATCH_PARENT, (int) CardView.LayoutParams.WRAP_CONTENT);

                                ImageView save = new ImageView(studentMain.this);
                                save.setImageResource(R.drawable.saveicon);
                                layoutParams.width = 1150;
                                layoutParams.leftMargin = -50;
                                // layoutParams.rightMargin = 100;
                                layoutParams.topMargin = i * 570;

                                card.setPadding(100, 10, 10, 10);
                                card.setCardElevation(10);
                                card.setRadius(15);
                                card.setClickable(true);

                                imageParams.width= 80;
                                imageParams.height= 80;
                                save.setLayoutParams(imageParams);
                                imageParams.setMargins(900,380,10,0);

                                postLayout.setLayoutParams(layoutParams);
                                card.setLayoutParams(cardParams);
                                card.setContentPadding(50, 10,50,40);
                                card.setBackgroundResource(R.drawable.card_boarder);

                                save.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (isSaved == false) {
                                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(studentMain.this);
                                            alertDialog.setTitle("Save this post?");
                                            alertDialog.setMessage("You can find this post in your saved posts");
                                            //add extra resources
                                            alertDialog.setPositiveButton("Save Post", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    //add post to savepost database
                                                    //change icon to filled in
                                                    save.setImageResource(R.drawable.filledsaveicon);
                                                    dialog.cancel();
                                                    isSaved = true;
                                                }
                                            });
                                            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.cancel();
                                                }
                                            });
                                            alertDialog.show();
                                        } else {
                                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(studentMain.this);
                                            alertDialog.setTitle("Unsave this post?");
                                            alertDialog.setMessage("This post will be removed from your saved posts");
                                            //add extra resources
                                            alertDialog.setPositiveButton("Unsave Post", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    //remove post from saved posts
                                                    //change icon to unfilled in
                                                    save.setImageResource(R.drawable.saveicon);
                                                    dialog.cancel();
                                                    isSaved = false;
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

                                titleView.setPadding(10, 10, 10, 50);
                                timeDate.setPadding(10, 100, 10, 10);
                                descriptionV.setPadding(10, 200, 10, 10);
                                lecturerName.setPadding(10, 400, 10, 0);;
                                //eye.setPadding(10,50,10,10);



                                card.addView(titleView);
                                card.addView(timeDate);
                                card.addView(descriptionV);
                                card.addView(save);
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
                            getTitles(result);
                            getDescriptions(result);
                            getDemonstrations(result);
                            getStudentWorks(result);
                            //creatingNewsfeed(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            TextView noposts = new TextView(studentMain.this);
                            noposts.setText("No posts to view");
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