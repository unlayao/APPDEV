package com.example.tasking;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText emailText, passText;
    private Button login, signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);


        //firebase
        mAuth = FirebaseAuth.getInstance();


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
                signIn(email, pass);
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
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }
    public void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            openAppMain();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                            updateUI(null);
                        }
                    }
                });
    }
    private void updateUI(FirebaseUser user) {
    }
    public void wrongAcc(){
        Toast.makeText(Login.this, "Wrong Email or Pass", Toast.LENGTH_LONG).show();
    }
    public  void openSignUp(){

        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
    public void openAppMain(){
        Intent intent = new Intent(this, MainTask.class);
        startActivity(intent);
    }

}