package com.example.tasking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class MainTask extends AppCompatActivity {
    // time will start in milliseconds
    private long START_TIME_IN_MS;
    private EditText tempTime;
    // variables for them time
    //private TextView mTextViewCountDown;
    private Button mButtonStartPause;
    private Button mButtonReset;

    private CountDownTimer mTimer;

    private boolean mTimerRunning;

    private long mTimeLeftInMS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_task);
        tempTime = (EditText) findViewById(R.id.editTextTime);
        //mTextViewCountDown = (TextView) findViewById(R.id.editTextTime);
        mButtonStartPause = (Button) findViewById(R.id.btnStartTime_PauseTime);
        mButtonReset = (Button) findViewById(R.id.resetButton);

        //Click button will start or pause
        mButtonStartPause.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                /*tempTime.setEnabled(false);
                long temporaryTime = 80000;
                START_TIME_IN_MS =  temporaryTime;
                mTimeLeftInMS = START_TIME_IN_MS;*/

                if(mTimerRunning){
                    pauseTimer();
                } else {
                    tempTime.setEnabled(false);
                    String input = tempTime.getText().toString();
                    long millisInput = Long.parseLong(input) * 60000;
                    setTime(millisInput);
                    startTimer();
                }
            }
        });

        // Click button will reset timer
        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        updateCountDownText();
    }


    private void setTime(long milliseconds){
        START_TIME_IN_MS = milliseconds;
        resetTimer();

    }

    // Method for when Timer is starting
    private void startTimer(){
        //tempTime = (EditText) findViewById(R.id.editTextTime);

        mTimer = new CountDownTimer(mTimeLeftInMS, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMS = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                mButtonStartPause.setText("Start");
                mButtonStartPause.setVisibility(View.INVISIBLE);
                mButtonReset.setVisibility(View.VISIBLE);
            }
        }.start();

        mTimerRunning = true;
        mButtonStartPause.setText("pause");
        mButtonReset.setVisibility(View.INVISIBLE);
    }

    // When when the timer is paused
    private void pauseTimer(){
       mTimer.cancel();
       mTimerRunning = false;
       mButtonStartPause.setText("Start");
       mButtonReset.setVisibility(View.VISIBLE);
    }

    // Method when Timer will reset
    private void resetTimer(){
        tempTime.setEnabled(true);
        tempTime.setText("");
        mTimeLeftInMS = START_TIME_IN_MS;
        updateCountDownText();
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);
    }

    //Updating the timer state
    private void updateCountDownText(){
        int minutes = (int) (mTimeLeftInMS / 1000) / 60;
        int seconds = (int) (mTimeLeftInMS / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d%02d", minutes, seconds);

        tempTime.setText(timeLeftFormatted);
    }
}