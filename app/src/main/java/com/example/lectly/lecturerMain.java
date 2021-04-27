package com.example.lectly;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;


public class lecturerMain extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private static final String TAG = null;
    FloatingActionButton notes;
    FloatingActionButton menu;
    FloatingActionButton create;
    FloatingActionButton filter;
    TextView dataView;
    private ArrayList<String> postTitles;
    private ArrayList<String> postDescriptions;
    private ArrayList<String> postDemonstrations;
    private ArrayList<String> postStudentWorks;
    private JSONArray result;
    private JSONArray modresult;
    public static String idClicked = "0";
    public static String moduleNameClicked;
    public static String post_id;
    public static String module_id;
    public String postTotalSaves;
    //public String moduleName;
    public String module_lecturer;
    //public static String moduleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_main);
        setupUI();
        getPosts();
    }

    private void setupUI() {
        notes = (FloatingActionButton) findViewById(R.id.lNotesButton);
        create = (FloatingActionButton) findViewById(R.id.createButton);
        menu = (FloatingActionButton) findViewById(R.id.lMenuButton);
        filter = (FloatingActionButton) findViewById(R.id.lFilterButton);


        postTitles = new ArrayList<String>();
        postDescriptions = new ArrayList<String>();
        postDemonstrations = new ArrayList<String>();
        postStudentWorks = new ArrayList<String>();


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

        notes.setOnClickListener(new View.OnClickListener() {
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
        final FrameLayout layout = findViewById(R.id.modulesFrameLayout);
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

                                ConstraintLayout postLayout = new ConstraintLayout(lecturerMain.this);
                                CardView card = new CardView(lecturerMain.this);
                                JSONObject jsonObject = result.getJSONObject(i);

                                String id = jsonObject.getString("id");
                                String title = jsonObject.getString("title");
                                //String timeAndDate = jsonObject.getString("description");
                                String description = jsonObject.getString("description");
                                module_id = jsonObject.getString("module_id");

                                TextView titleView = new TextView(lecturerMain.this);
                                titleView.setTextSize(28);

                                TextView timeDate = new TextView(lecturerMain.this);
                                TextView descriptionV = new TextView(lecturerMain.this);
                                TextView lecturerName = new TextView(lecturerMain.this);
                                TextView moduleNameV = new TextView(lecturerMain.this);

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
                                RequestQueue modrequestQueue = Volley.newRequestQueue(lecturerMain.this);
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

                                CardView.LayoutParams imageParams = new CardView.LayoutParams
                                        ((int) CardView.LayoutParams.MATCH_PARENT, (int) CardView.LayoutParams.WRAP_CONTENT);

                                ImageView eye = new ImageView(lecturerMain.this);
                                eye.setImageResource(R.drawable.eyeoutline);
                                layoutParams.width = 1150;
                                layoutParams.leftMargin = -50;
                                //layoutParams.rightMargin = 100;
                                layoutParams.topMargin = i * 570;

                                card.setPadding(100, 10, 10, 10);
                                card.setCardElevation(10);
                                card.setRadius(15);
                                card.setClickable(true);

                                imageParams.width= 100;
                                imageParams.height= 100;
                                eye.setLayoutParams(imageParams);
                                imageParams.setMargins(900,380,10,0);

                                postLayout.setLayoutParams(layoutParams);
                                card.setLayoutParams(cardParams);
                                card.setContentPadding(50, 10,50,40);
                                card.setBackgroundResource(R.drawable.card_boarder);

                                eye.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View view) {
                                        StringRequest modStringRequest = new StringRequest("http://192.168.1.87:8888/Lectly/getTotalSaves.php?post_id=" + id,
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        JSONObject totalsaves = null;
                                                        try {
                                                            totalsaves = new JSONObject(response.toString());
                                                            JSONArray saveresult = totalsaves.getJSONArray(ModuleDetails.JSON_ARRAY);
                                                            for (int i = 0; i < saveresult.length(); i++) {
                                                                JSONObject jsonObject = saveresult.getJSONObject(i);
                                                                String saves = jsonObject.getString("no_of_saves");
                                                                postTotalSaves = saves;
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
                                        RequestQueue modrequestQueue = Volley.newRequestQueue(lecturerMain.this);
                                        modrequestQueue.add(modStringRequest);

                                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(lecturerMain.this);
                                        alertDialog.setTitle("Total Interactions");
                                        alertDialog.setMessage("Total Views = "+ postTotalSaves + "\n" + "Total File Downloads = " );
                                        //add extra resources
                                        alertDialog.setPositiveButton("View in dashboard", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent i = new Intent(getApplicationContext(), dashboard.class);
                                                startActivity(i);
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
                                moduleNameV.setPadding(10, 100, 10, 0);
                                timeDate.setPadding(10, 150, 10, 10);
                                descriptionV.setPadding(10, 230, 10, 10);
                                lecturerName.setPadding(10, 400, 10, 0);

                                card.addView(titleView);
                                card.addView(timeDate);
                                card.addView(descriptionV);
                                card.addView(eye);
                                card.addView(lecturerName);
                                card.addView(moduleNameV);

                                postLayout.addView(card);
                                layout.addView(postLayout);

                                card.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        idClicked = id;
                                        moduleNameClicked = moduleNameV.getText().toString();
                                        Intent j = new Intent(getApplicationContext(), viewPost.class);
                                        startActivity(j);
                                        //textViewTitle.setText("get from button is  " + postNameClicked);
                                    }
                                });
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            TextView noposts = new TextView(lecturerMain.this);
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

}











