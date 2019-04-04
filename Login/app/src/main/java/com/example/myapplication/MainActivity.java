package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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


public class    MainActivity extends AppCompatActivity {

    private EditText username;
    private EditText Password;
    private TextView info;
    private Button Login;
    private int counter = 5;
    private TextView userRegistration;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private TextView ForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        username = (EditText) findViewById(R.id.username);
        Password = (EditText) findViewById(R.id.password);
        info = (TextView) findViewById(R.id.info);
        Login = (Button) findViewById(R.id.btn_login);
        userRegistration = (TextView) findViewById(R.id.register);
        ForgotPassword = (TextView) findViewById(R.id.ForgotPassword);

        info.setText("No of attempts remaining : 5");

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null) {
            finish();
            startActivity(new Intent(MainActivity.this, SecondActivity.class));
        }


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(username.getText().toString(), Password.getText().toString());

            }
        });

        userRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Registration.class));
            }
        });

        ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PasswordActivity.class));

            }
        });
    }

    private void validate(String userUsername, String userPassword) {

        progressDialog.setMessage("Hello");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(userUsername, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    checkEmailVerification();
                } else {
                    Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    counter--;
                    info.setText("No of attempts remaining" + counter);
                    progressDialog.dismiss();
                    if (counter == 0) {
                        Login.setEnabled(false);
                    }
                }

            }


        });
    }
    private void checkEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();

        startActivity(new Intent(MainActivity.this, SecondActivity.class));

   //     if(emailflag){
     //       finish();
       //     startActivity(new Intent(MainActivity.this, SecondActivity.class));
       // }else{
         //   Toast.makeText(this, "Verify your email", Toast.LENGTH_SHORT).show();
           // firebaseAuth.signOut();
        }
    }



