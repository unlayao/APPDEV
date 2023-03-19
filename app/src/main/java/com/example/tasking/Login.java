package com.example.tasking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    private EditText emailText, passText;
    private Button login, signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //assign
        emailText = (EditText) findViewById(R.id.inputEmail);
        passText = (EditText) findViewById(R.id.inputPass);
        login = findViewById(R.id.btnLogin);
        signup = findViewById(R.id.btnSign);

        //login button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailText.getText().toString();
                String pass = passText.getText().toString();
                if((email.equals("admin"))  && (pass.equals("admin123"))){
                    openAppMain();
                }else{
                    wrongAcc();
                }
            }
        });

        //signup button
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignUp();
            }
        });
    }
    public void wrongAcc(){
        Toast.makeText(Login.this, "Wrong Email or Pass", Toast.LENGTH_LONG).show();
    }
    public  void openSignUp(){
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
    public void openAppMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}