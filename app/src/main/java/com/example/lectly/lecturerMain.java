package com.example.lectly;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;


import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class lecturerMain extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private static final String TAG = null;
    Button files;
    ImageButton menu;
    FloatingActionButton create;
    TextView dataView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_main);
        setupUI();


        getPosts();

    }

    private void setupUI() {
        //files = (Button) findViewById(R.id.files);
        //menu = (Button) findViewById(R.id.menu);
        create = findViewById(R.id.createButton);
        menu = findViewById(R.id.menuButton);
        dataView = findViewById(R.id.dataView);


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
    }

        private void getPosts() {
            String title = null;
            String description = null;
            String demonstration = null;
            String studentWork = null;

            String[] field = new String[4];
            field[0] = "title";
            field[1] = "description";
            field[2] = "demonstration";
            field[3] = "studentWork";

            String method = "GET";
            String[] data = new String[4];
            data[0] = title;
            data[1] = description;
            data[2] = demonstration;
            data[3] = studentWork;

            Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    FetchData fetchData = new FetchData("http://192.168.1.87:8888/Lectly/getPosts.php");
                    if (fetchData.startFetch()) {
                        if (fetchData.onComplete()) {
                            String result = fetchData.getResult();
                            if(result.equals("success")){
                                dataView.setText(result);
                            }

                            Log.i("FetchData", result);
                        }
                    }
                }
            });
        }
}




