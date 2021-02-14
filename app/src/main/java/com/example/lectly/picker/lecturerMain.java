package com.example.lectly.picker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lectly.R;


public class lecturerMain extends AppCompatActivity {

    Button files;
    Button postButton;
    Button menu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_main);
        setupUI();
        setupListeners();
    }

    private void setupUI() {
        files = findViewById(R.id.files);
        postButton = findViewById(R.id.postButton);
        menu = findViewById(R.id.menu);
    }

    private void setupListeners() {
        files.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IPicker mPicker = Picker.createPicker(Picker.ONEDRIVE_APP_ID);
                mPicker.startPicking(lecturerMain.this, LinkType.DownloadLink);
            }
        });
    }
}


