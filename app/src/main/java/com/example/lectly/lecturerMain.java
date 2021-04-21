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
    public static String idClicked;
    private int postID;
    boolean isPostClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_main);
        setupUI();
        getPosts();

        isPostClicked = false;
    }

    private void setupUI() {
        notes = (FloatingActionButton) findViewById(R.id.lNotesButton);
        //menu = (Button) findViewById(R.id.menu);
        create = (FloatingActionButton) findViewById(R.id.createButton);
        menu = (FloatingActionButton) findViewById(R.id.lMenuButton);
        filter = (FloatingActionButton) findViewById(R.id.lFilterButton);
        //dataView = findViewById(R.id.textViewTitle);

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

                                ConstraintLayout postLayout = new ConstraintLayout(lecturerMain.this);
                                CardView card = new CardView(lecturerMain.this);
                                JSONObject jsonObject = result.getJSONObject(i);

                                String id = jsonObject.getString("id");
                                String title = jsonObject.getString("title");
                                //String timeAndDate = jsonObject.getString("description");
                                String description = jsonObject.getString("description");
                                TextView titleView = new TextView(lecturerMain.this);
                                titleView.setTextSize(28);

                                TextView timeDate = new TextView(lecturerMain.this);
                                TextView descriptionV = new TextView(lecturerMain.this);
                                TextView lecturerName = new TextView(lecturerMain.this);
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

                                ImageView eye = new ImageView(lecturerMain.this);
                                eye.setImageResource(R.drawable.eyeoutline);
                                layoutParams.width = 1150;
                                layoutParams.leftMargin = -50;
                               // layoutParams.rightMargin = 100;
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
                                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(lecturerMain.this);
                                        alertDialog.setTitle("Total Interactions");
                                        alertDialog.setMessage("Total Views = " + "\n" + "Total File Downloads = " );
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
                                timeDate.setPadding(10, 100, 10, 10);
                                descriptionV.setPadding(10, 200, 10, 10);
                                lecturerName.setPadding(10, 400, 10, 0);;
                                //eye.setPadding(10,50,10,10);



                                card.addView(titleView);
                                card.addView(timeDate);
                                card.addView(descriptionV);
                                card.addView(eye);
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
/*

    private void creatingNewsfeed(JSONArray allPosts) {
        final ConstraintLayout layout = findViewById(R.id.constraintLayout);
        b_titles = new Button[postTitles.size()];
        final TextView[] tv_descriptions = new TextView[postDescriptions.size()];
        final TextView[] tv_demonstrations = new TextView[postDemonstrations.size()];
        final TextView[] tv_studentWork = new TextView[postStudentWorks.size()];

        for (int i = 0; i < postTitles.size(); i++) {

            ConstraintLayout postLayout = new ConstraintLayout(this);
            b_titles[i] = new Button(this);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                    ((int) LinearLayout.LayoutParams.WRAP_CONTENT, (int) LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 50;
            params.topMargin = i * 50;
            b_titles[i].setText(postTitles.get(i));
            b_titles[i].setTextSize((float) 20);
            if (i == 0) {
                b_titles[i].setPadding(100, 50, 20, 50);
            } else {
                b_titles[i].setPadding(100, 450 * i, 20, 50);
            }
            b_titles[i].setBackgroundColor(000000);
            b_titles[i].setLayoutParams(params);

            layout.addView(b_titles[i]);
        }

        for (Button b : b_titles){
           postID = findIndex(b_titles, b);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isPostClicked = true;
                    idClicked = postID;
                    Intent j = new Intent(getApplicationContext(), viewPost.class);
                    startActivity(j);
                    //textViewTitle.setText("get from button is  " + postNameClicked);
                }
            });
        }


        for (int i = 0; i < postDescriptions.size(); i++) {
            tv_descriptions[i] = new TextView(this);
            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams
                    ((int) ConstraintLayout.LayoutParams.WRAP_CONTENT, (int) ConstraintLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 50;
            params.topMargin = i * 50;
            tv_descriptions[i].setText(postDescriptions.get(i));
            tv_descriptions[i].setTextSize((float) 20);
            if (i == 0) {
                tv_descriptions[i].setPadding(100, 100, 20, 50);
            } else {
                tv_descriptions[i].setPadding(100, 500 * i, 20, 50);
            }
            tv_descriptions[i].setLayoutParams(params);
            layout.addView(tv_descriptions[i]);
        }

        for (int i = 0; i < postDemonstrations.size(); i++) {
            tv_demonstrations[i] = new TextView(this);
            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams
                    ((int) ConstraintLayout.LayoutParams.WRAP_CONTENT, (int) ConstraintLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 50;
            params.topMargin = i * 50;
            tv_demonstrations[i].setText(postDemonstrations.get(i));
            tv_demonstrations[i].setTextSize((float) 20);
            if (i == 0) {
                tv_demonstrations[i].setPadding(100, 150, 20, 50);
            } else {
                tv_demonstrations[i].setPadding(100, 550 * i, 20, 50);
            }
            tv_demonstrations[i].setLayoutParams(params);
            layout.addView(tv_demonstrations[i]);
        }

        for (int i = 0; i < postStudentWorks.size(); i++) {
            tv_studentWork[i] = new TextView(this);
            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams
                    ((int) ConstraintLayout.LayoutParams.WRAP_CONTENT, (int) ConstraintLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 50;
            params.topMargin = i * 50;

            tv_studentWork[i].setText(postStudentWorks.get(i));
            tv_studentWork[i].setTextSize((float) 20);
            if (i == 0) {
                tv_studentWork[i].setPadding(100, 200, 20, 50);
            } else {
                tv_studentWork[i].setPadding(100, 600 * i, 20, 50);
            }
            tv_studentWork[i].setLayoutParams(params);
            layout.addView(tv_studentWork[i]);
        }


    }

    public static int findIndex(Button arr[], Button t)
    {
        // if array is Null
        if (arr == null) {
            return -1;
        }
        // find length of array
        int len = arr.length;
        int i = 0;
        // traverse in the array
        while (i < len) {
            // if the i-th element is t
            // then return the index
            if (arr[i] == t) {
                return i;
            }
            else {
                i = i + 1;
            }
        }
        return -1;
    }
*/



}











