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

public class students extends AppCompatActivity {

    ProgressDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);
        getAllStudents();
    }

    public void getAllStudents(){
        loadingDialog = new ProgressDialog(this); // this = YourActivity
        loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loadingDialog.setTitle("Loading");
        loadingDialog.setMessage("Loading all students...");
        loadingDialog.setIndeterminate(true);
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.show();
        final FrameLayout layout = findViewById(R.id.studentsFrameLayout);
        StringRequest stringRequest = new StringRequest("http://<ip-address>:3306/Lectly/getStudents.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject allModules;
                        try {
                            allModules = new JSONObject(response);
                            JSONArray moduleResult = allModules.getJSONArray(ModuleDetails.JSON_ARRAY);

                            for (int i = 0; i < moduleResult.length(); i++) {
                                JSONObject jsonObject = moduleResult.getJSONObject(i);
                                String module_name= jsonObject.getString("fullname");
                                String module_course= jsonObject.getString("username");
                                String module_year = jsonObject.getString("email");

                                TextView name = new TextView(students.this);
                                name.setText(module_name);
                                name.setTextSize(26);
                                TextView course = new TextView(students.this);
                                course.setText("Username: "+ module_course);
                                course.setTextSize(20);
                                TextView year = new TextView(students.this);
                                year.setText(module_year);
                                year.setTextSize(20);

                                ConstraintLayout postLayout = new ConstraintLayout(students.this);
                                CardView card = new CardView(students.this);

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

                                ImageView message = new ImageView(students.this);
                                message.setImageResource(R.drawable.messageicon);

                                imageParams.width= 80;
                                imageParams.height= 80;
                                message.setLayoutParams(imageParams);
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
                                card.addView(message);

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
        RequestQueue requestQueue = Volley.newRequestQueue(students.this);
        requestQueue.add(stringRequest);
    }
}
