package com.example.lectly;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
                alertDialog.setMessage("Information about the pomodoro study techinque");
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
                if (timerRunning){
                    pauseTimer();
                } else{
                    startTimer();
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