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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {
    private Button btnSUp;
    private EditText tempUser, tempEmail, tempPass, compPass;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //variable assign for signing in
        mAuth = FirebaseAuth.getInstance();
        tempUser = (EditText) findViewById(R.id.inputSUser);
        tempEmail = (EditText) findViewById(R.id.inputSEmail);
        tempPass = (EditText) findViewById(R.id.inputSPass);
        compPass = (EditText) findViewById(R.id.checkPass);
        btnSUp = findViewById(R.id.btnSignUp);

        //button for signing in
        btnSUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = tempEmail.getText().toString();
                String pass = tempPass.getText().toString();
                String cPass = compPass.getText().toString();
                newAccount(email, pass);
                //checks the email
                if(cPass.equals(pass)){
                    Toast.makeText(SignUp.this, "Account Creation SUCCESSFUL!", Toast.LENGTH_LONG).show();
                    newAccount(email, pass);
                }else{
                    tempPass.setText("");
                    compPass.setText("");
                    Toast.makeText(SignUp.this, "Pass Do NOT Match!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            currentUser.reload();
        }
    }
    //firebase createAccount method
    public void newAccount(String email, String pass){
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            openLogin();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUp.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }
    //firebase shi
    //firebase shi
    public void openLogin(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
    private void updateUI(FirebaseUser user) {
    }
}