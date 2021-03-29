package com.example.lectly;

import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.google.android.material.navigation.NavigationView;


import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class studentMain extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private static final String TAG = null;
    Button files;
    Button menu;
    EditText postTitleInput;
    EditText postDescriptionInput;
    EditText postDemoInput;
    EditText postStudentWorkInput;
    Switch postAllowCommentsDecision;

    //pusher
/*    private RecyclerView.LayoutManager lManager;
    private EventAdapter adapter;
    private Pusher pusher = new Pusher("1b0541bc439a8001f9a6");
    private static final String CHANNEL_NAME = "events_to_be_shown";*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_main);


/*
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

    }*/

    }



    private void setupUI() {
        //files = (Button) findViewById(R.id.files);
        //menu = (Button) findViewById(R.id.menu);

    }
}