package com.example.tasking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button getStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getStarted button assigning
        getStarted = (Button) findViewById(R.id.btnStart);
        getStarted.setOnClickListener(v -> openLogin());
    }
    public void openLogin(){
        Intent intent = new Intent(this, MainTask.class);
        startActivity(intent);
    }
}