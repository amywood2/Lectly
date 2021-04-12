package com.example.lectly;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

public class viewPost extends AppCompatActivity {

    private String getidClicked;
    private JSONArray result;
    TextView moduleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);

        getidClicked = lecturerMain.idClicked;
        moduleName = findViewById(R.id.moduleName);
        getPost();
    }


    private void getPost() {
        StringRequest stringRequest = new StringRequest("http://192.168.1.87:8888/Lectly/getIndividualPost.php?id=" + getidClicked ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject allPosts = null;
                        try {
                            allPosts = new JSONObject(response);
                            result = allPosts.getJSONArray(PostDetails.JSON_ARRAY);
                            //moduleName.setText(result.toString());
                            getDetails(result);

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

    private void getDetails(JSONArray allPosts) {
        for (int i = 0; i < allPosts.length(); i++) {
            try {
                JSONObject json = allPosts.getJSONObject(i);
                moduleName.setText(result.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }



}