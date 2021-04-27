package com.example.lectly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.os.Bundle;
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

public class modules extends AppCompatActivity {

    ProgressDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modules);

        getAllModules();
    }

    public void getAllModules(){
        loadingDialog = new ProgressDialog(this); // this = YourActivity
        loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loadingDialog.setTitle("Loading");
        loadingDialog.setMessage("Loading all modules...");
        loadingDialog.setIndeterminate(true);
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.show();

        final FrameLayout layout = findViewById(R.id.modulesFrameLayout);
        StringRequest stringRequest = new StringRequest("http://192.168.1.87:8888/Lectly/getModules.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject allModules;
                        try {
                            allModules = new JSONObject(response);
                            JSONArray moduleResult = allModules.getJSONArray(ModuleDetails.JSON_ARRAY);

                            for (int i = 0; i < moduleResult.length(); i++) {
                                JSONObject jsonObject = moduleResult.getJSONObject(i);
                                String module_name= jsonObject.getString(ModuleDetails.MODULENAME);
                                String module_course= jsonObject.getString(ModuleDetails.COURSE);
                                String module_year = jsonObject.getString(ModuleDetails.YEAR);

                                TextView name = new TextView(modules.this);
                                name.setText(module_name);
                                name.setTextSize(26);
                                TextView course = new TextView(modules.this);
                                course.setText(module_course);
                                course.setTextSize(20);
                                TextView year = new TextView(modules.this);
                                year.setText("Year " + module_year);
                                year.setTextSize(20);

                                ConstraintLayout postLayout = new ConstraintLayout(modules.this);
                                CardView card = new CardView(modules.this);

                                card.setPadding(100, 10, 10, 10);
                                card.setCardElevation(10);
                                card.setRadius(15);
                                card.setClickable(true);

                                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams
                                        ((int) FrameLayout.LayoutParams.MATCH_PARENT, (int) FrameLayout.LayoutParams.WRAP_CONTENT);
                                CardView.LayoutParams cardParams = new CardView.LayoutParams
                                        ((int) CardView.LayoutParams.MATCH_PARENT, (int) CardView.LayoutParams.WRAP_CONTENT);

                                CardView.LayoutParams imageParams = new CardView.LayoutParams
                                        ((int) CardView.LayoutParams.MATCH_PARENT, (int) CardView.LayoutParams.WRAP_CONTENT);

                                ImageView edit = new ImageView(modules.this);
                                edit.setImageResource(R.drawable.editicon);

                                imageParams.width= 80;
                                imageParams.height= 80;
                                edit.setLayoutParams(imageParams);
                                imageParams.setMargins(900,100,10,0);

                                layoutParams.width = 1170;
                                layoutParams.leftMargin = -50;
                                // layoutParams.rightMargin = 100;
                                layoutParams.topMargin = i * 350;
                                postLayout.setLayoutParams(layoutParams);
                                card.setLayoutParams(cardParams);
                                card.setContentPadding(70, 20,50,40);
                                card.setBackgroundResource(R.drawable.card_boarder);


                                name.setPadding(10, 10, 10, 50);
                                course.setPadding(10, 100, 10, 0);
                                year.setPadding(10, 180, 10, 10);

                                card.addView(name);
                                card.addView(course);
                                card.addView(year);
                                card.addView(edit);

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
        RequestQueue requestQueue = Volley.newRequestQueue(modules.this);
        requestQueue.add(stringRequest);

    }
}