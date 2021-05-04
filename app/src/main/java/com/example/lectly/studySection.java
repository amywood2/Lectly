package com.example.lectly;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class studySection extends AppCompatActivity {

    public long START_TIME_IN_MILLIS;

    private TextView countdownText;
    private Button startButton;
    private Button resetButton;
    private Button startStudyButton;
    private Button startBreakButton;
    private FloatingActionButton studyInfo;

    private CountDownTimer countDownTimer;

    private boolean timerRunning;

    private long timeLeftInMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_section);

        countdownText = findViewById(R.id.timer);
        startButton = findViewById(R.id.startTimerButton);
        resetButton = findViewById(R.id.resetTimerButton);
        startStudyButton = findViewById(R.id.startStudyButton);
        startBreakButton = findViewById(R.id.startBreakButton);
        studyInfo = findViewById(R.id.studyInfoButton);

        studyInfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(studySection.this);
                alertDialog.setTitle("Pomodoro Technique");
                alertDialog.setMessage("The Pomodoro Technique is a very effective way to study. The technique works by studying for 25 minutes" +
                        " and then taking a 5 minute break and doing something you enjoy during that time." + "\n" + "\n" + "Use the timers in this section to help you!" +
                        "\n" + "\n" + "This technique allow you to enter your zone of proximal development and enhancing what you can do independently.");
                //add extra resources

                alertDialog.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.show();


            }
        });

        startStudyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                START_TIME_IN_MILLIS = 1500000;
                timeLeftInMillis = START_TIME_IN_MILLIS;
                startButton.setText("Start Timer");
                updateCountDownText();

            }
        });

        startBreakButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                START_TIME_IN_MILLIS = 300000;
                timeLeftInMillis = START_TIME_IN_MILLIS;
                startButton.setText("Start Timer");
                updateCountDownText();

            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (timerRunning) {
                    pauseTimer();
                } else {
                    startTimer();

                    SimpleDateFormat style = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Timestamp getTimestamp = new Timestamp(System.currentTimeMillis());
                    String timestamp = style.format(getTimestamp);
                    Handler handler = new Handler();
                    if (START_TIME_IN_MILLIS == 300000) {

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                //Starting Write and Read data with URL
                                //Creating array for parameters
                                String[] field = new String[3];
                                field[0] = "student_id";
                                field[1] = "type";
                                field[2] = "timestamp";

                                //Creating array for data
                                String[] data = new String[3];
                                data[0] = LoginActivity.user_id;
                                data[1] = "25 minute study timer";
                                data[2] = timestamp;

                                PutData putData = new PutData("http://<ip-address>:3306/Lectly/addStudyTimesToDashboard.php", "POST", field, data);
                                if (putData.startPut()) {
                                    if (putData.onComplete()) {
                                        String result = putData.getResult();
                                        Toast.makeText(studySection.this, result, Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        });
                    } else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                //Starting Write and Read data with URL
                                //Creating array for parameters
                                String[] field = new String[3];
                                field[0] = "student_id";
                                field[1] = "type";
                                field[2] = "timestamp";

                                //Creating array for data
                                String[] data = new String[3];
                                data[0] = LoginActivity.user_id;
                                data[1] = "5 minute break timer";
                                data[2] = timestamp;

                                PutData putData = new PutData("http://<ip-address>:3306/Lectly/addStudyTimesToDashboard.php", "POST", field, data);
                                if (putData.startPut()) {
                                    if (putData.onComplete()) {
                                        String result = putData.getResult();
                                        Toast.makeText(studySection.this, result, Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        });
                    }
                }
            }
        });








        resetButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                resetTimer();
            }
        });
      // updateCountDownText();
    }

    private void startTimer(){
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                startButton.setText("Start Timer");
                startButton.setVisibility(View.INVISIBLE);
                resetButton.setVisibility(View.VISIBLE);
            }
        }.start();
        timerRunning = true;
        startButton.setText("Pause Timer");
        resetButton.setVisibility(View.INVISIBLE);
    }

    private void updateCountDownText(){
        //timeLeftInMillis = START_TIME_IN_MILLIS;
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeLeft = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);

        countdownText.setText(timeLeft);
    }

    private void pauseTimer(){
        countDownTimer.cancel();
        timerRunning = false;
        startButton.setText("Restart Timer");
        resetButton.setVisibility(View.VISIBLE);
    }

    private void resetTimer(){
        timeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        resetButton.setVisibility(View.INVISIBLE);
        startButton.setVisibility(View.VISIBLE);
    }


}
