package com.example.tasking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class MainTask extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    // time will start in milliseconds
    private long START_TIME_IN_MS;
    private TextView tempTime;
    private EditText inputTime;
    // variables for them time
    //private TextView mTextViewCountDown;
    private Button mButtonStartPause;
    private Button mButtonReset;

    private CountDownTimer mTimer;

    private boolean mTimerRunning;
    private boolean isPaused;

    private long mTimeLeftInMS;

    //progress bar
    private ProgressBar pb;
    private int pbMax;
    private int current;
    //input
    private String input;
    //current time
    private int currentTime;
    //spinner initiation
    private Spinner spinner;
    private String spinnerdata;


    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //built in
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_task);
        //NavBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //main timer
        tempTime = findViewById(R.id.editTextTime);
        //takes input from user
        inputTime = findViewById(R.id.timeInput);
        //start / pause button
        mButtonStartPause = findViewById(R.id.btnStartTime_PauseTime);
        //reset button
        mButtonReset = findViewById(R.id.resetButton);
        //progress bar
        pb = findViewById(R.id.timerBar);
        //spinner for list of activities
        spinner = findViewById(R.id.spinner);

        showAll();
        //Click button will start or pause
        tempTime.setVisibility(View.INVISIBLE);



        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty(inputTime)){
                    Toast.makeText(MainTask.this, "Invalid Number of Minutes", Toast.LENGTH_LONG).show();
                    return;
                }else{
                    input = inputTime.getText().toString();
                }
                long millisInput = Long.parseLong(input) * 60000;
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    //checks if timer is paused
                    if (isPaused) {
                        Toast.makeText(MainTask.this, "Timer Resumed!", Toast.LENGTH_LONG).show();
                        setTime(currentTime);
                        startTimer();
                        pb.setMax(pbMax);
                        pb.setProgress(current);
                        isPaused = false;
                    } else {
                        inputTime.setVisibility(View.INVISIBLE);
                        tempTime.setVisibility(View.VISIBLE);
                        spinner.setEnabled(false);
                        Toast.makeText(MainTask.this, "Timer Started with Task " + spinnerdata, Toast.LENGTH_LONG).show();
                        setTime(millisInput);
                        startTimer();
                        pbMax = Integer.parseInt(input) * 60000;
                        pb.setMax(pbMax);
                    }
                }
                inputTime.setEnabled(false);
            }
        });

        // Click button will reset timer
        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputTime.setText("");
                resetTimer();
                tempTime.setVisibility(View.INVISIBLE);
                inputTime.setVisibility(View.VISIBLE);
                spinner.setEnabled(true);
                Toast.makeText(MainTask.this, "Timer Reset!", Toast.LENGTH_SHORT).show();
            }
        });

        //spinner declared values (FOR TESTING)
        String[] activities = {"Html Coding", "Appdev Coding", "Social Issues Essay"};
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(activities));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, arrayList);
        spinner.setAdapter(arrayAdapter);

        //selecting from spinner (activities)
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerdata = adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;

        return true;
    }

    //sets time
    private void setTime(long milliseconds) {
        START_TIME_IN_MS = milliseconds;
        resetTimer();
    }

    // Method for when Timer is starting
    private void startTimer() {


        mTimer = new CountDownTimer(mTimeLeftInMS, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                current = pb.getProgress() + 1000;
                mTimeLeftInMS = millisUntilFinished;

                pb.setProgress(current);
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                spinner.setEnabled(true);
                Toast.makeText(MainTask.this, "Done!", Toast.LENGTH_SHORT).show();
                mButtonStartPause.setText("Start");
                mButtonStartPause.setVisibility(View.INVISIBLE);
                mButtonReset.setVisibility(View.VISIBLE);
                inputTime.setEnabled(true);
            }
        }.start();

        mTimerRunning = true;
        mButtonStartPause.setText("pause");
        mButtonReset.setVisibility(View.INVISIBLE);
    }

    // When when the timer is paused
    private void pauseTimer() {
        isPaused = true;
        Toast.makeText(MainTask.this, "Timer Paused!", Toast.LENGTH_SHORT).show();
        mTimer.cancel();
        mTimerRunning = false;
        mButtonStartPause.setText("Start");
        mButtonReset.setVisibility(View.VISIBLE);
        currentTime = (int) mTimeLeftInMS - 250;
    }

    // Method when Timer will reset
    private void resetTimer() {
        isPaused = false;
        inputTime.setEnabled(true);
        tempTime.setText("");
        pb.setProgress(0);
        mTimeLeftInMS = START_TIME_IN_MS;
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);
    }

    //Updating the timer state
    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMS / 1000) / 60;
        int seconds = (int) (mTimeLeftInMS / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        tempTime.setText(timeLeftFormatted);

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void hideAll(){
        inputTime.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(View.INVISIBLE);
        pb.setVisibility(View.INVISIBLE);
        spinner.setVisibility(View.INVISIBLE);
    }

    private void showAll(){
        inputTime.setVisibility(View.VISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);
        pb.setVisibility(View.VISIBLE);
        spinner.setVisibility(View.VISIBLE);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        switch(item.getItemId()){
            case R.id.removedrawer:
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_home:
                Intent intent = new Intent(this, MainTask.class);
                startActivity(intent);
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AccountFragment()).commit();
                hideAll();
                break;
            case R.id.nav_tasks:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new TasksFragment()).commit();
                hideAll();
                break;
            case R.id.nav_achievements:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AchievementsFragment()).commit();
                hideAll();
                break;
            case R.id.nav_alarm:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AlarmFragment()).commit();
                hideAll();
                break;
            case R.id.nav_theme:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ThemesFragment()).commit();
                hideAll();
                break;
            case R.id.nav_usage:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new UsageFragment()).commit();
                hideAll();
                break;
            case R.id.nav_about:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AboutFragment()).commit();
                hideAll();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}