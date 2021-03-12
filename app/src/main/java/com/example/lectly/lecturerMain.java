package com.example.lectly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.pusher.client.Pusher;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionStateChange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class lecturerMain extends AppCompatActivity {

    private static final String TAG = null;
    Button files;
    FloatingActionButton createButton;
    Button menu;
    EditText postTitleInput;
    EditText postDescriptionInput;
    EditText postDemoInput;
    EditText postStudentWorkInput;
    Switch postAllowCommentsDecision;

    //pusher
    private RecyclerView.LayoutManager lManager;
    private EventAdapter adapter;
    private Pusher pusher = new Pusher("1b0541bc439a8001f9a6");
    private static final String CHANNEL_NAME = "events_to_be_shown";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_main);
        setupUI();
        //setupListeners();

        //pusher
        // Get the RecyclerView
        RecyclerView recycler = (RecyclerView) findViewById(R.id.recycler_view);

        // Use LinearLayout as the layout manager
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        // Set the custom adapter
        List<Event> eventList = new ArrayList<>();
        adapter = new EventAdapter(eventList);
        recycler.setAdapter(adapter);

        Channel channel = pusher.subscribe(CHANNEL_NAME);

        SubscriptionEventListener eventListener = new SubscriptionEventListener() {
            @Override
            public void onEvent(String channel, final String event, final String data) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Received event with data: " + data);
                        Gson gson = new Gson();
                        Event evt = gson.fromJson(data, Event.class);
                        evt.setName(event + ":");
                        adapter.addEvent(evt);
                        ((LinearLayoutManager)lManager).scrollToPositionWithOffset(0, 0);
                    }
                });
            }
        };

        channel.bind("created", eventListener);
        channel.bind("updated", eventListener);
        channel.bind("deleted", eventListener);

        pusher.connect();

        pusher.connect(new ConnectionEventListener() {
            @Override
            public void onConnectionStateChange(ConnectionStateChange change) {
                System.out.println("State changed to " + change.getCurrentState() +
                        " from " + change.getPreviousState());
            }

            @Override
            public void onError(String message, String code, Exception e) {
                System.out.println("There was a problem connecting!");
                e.printStackTrace();
            }
        });

    }

    private void setupUI() {
        files = (Button) findViewById(R.id.files);
        menu = (Button) findViewById(R.id.menu);
        createButton = (FloatingActionButton) findViewById(R.id.createButton);
    }

/*
    private void setupListeners() {
        Data.DbHelper helper = new Data.DbHelper(lecturerMain.this);

        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();

        Data.Posts newPostTable = new Data.Posts();

        files.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // IPicker mPicker = Picker.createPicker(Picker.ONEDRIVE_APP_ID);
                // mPicker.startPicking(lecturerMain.this, LinkType.DownloadLink);
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder build = new AlertDialog.Builder(lecturerMain.this);

                Context context = build.getContext();
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.createpostdialog, null, false);

                postTitleInput = (EditText) view.findViewById(R.id.postTitleInput);
                postDescriptionInput = (EditText) view.findViewById(R.id.postDescriptionInput);
                postDemoInput = (EditText) view.findViewById(R.id.postDemoInput);
                postStudentWorkInput = (EditText) view.findViewById(R.id.postStudentWorkInput);
                postAllowCommentsDecision = (Switch) view.findViewById(R.id.allowComments);

                build.setView(view)
                        .setPositiveButton("Post Now", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //post and save to db
                                String titleUserInput = (String) postTitleInput.getText().toString();
                                values.put(newPostTable.COLUMN_NAME_TITLE, titleUserInput);
                                String descriptionUserInput = (String) postDescriptionInput.getText().toString();
                                values.put(newPostTable.COLUMN_NAME_DESCRIPTION, descriptionUserInput);
                                String demoUserInput = (String) postDemoInput.getText().toString();
                                values.put(newPostTable.COLUMN_NAME_DEMONSTRATION, demoUserInput);
                                String studentWorkUserInput = (String) postStudentWorkInput.getText().toString();
                                values.put(newPostTable.COLUMN_NAME_STUDENT, studentWorkUserInput);
                                Boolean decision;
                                //show comment section on post
                                //dont show comment section on post
                                decision = postAllowCommentsDecision.isChecked();

                                long newRowId = db.insert(Data.Posts.TABLE_NAME, null, values);
                            }
                        })
                        .setNegativeButton("Schedule Post", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //schedule post
                            }
                        });
                build.show();

            }

        });

    }*/

    public void onDestroy() {
        super.onDestroy();
        pusher.disconnect();
    }


}







