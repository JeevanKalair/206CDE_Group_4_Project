package com.example.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Registration extends AppCompatActivity {

    private EditText userName, userPassword, userEmail;
    private Button regButton;
    private TextView userLogin;
    private FirebaseAuth firebaseAuth;
    private ImageView userProfilePic;
    String email,name,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupUIViews();

        firebaseAuth = FirebaseAuth.getInstance();

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    String user_email = userEmail.getText().toString().trim();
                    String user_password = userPassword.getText().toString().trim();


                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                sendUserData();
                                firebaseAuth.signOut();
                                Toast.makeText(Registration.this, "Successfully Registered, Verification mail sent!", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(Registration.this, MainActivity.class));
                            } else {
                                Toast.makeText(Registration.this, "Registration Failed", Toast.LENGTH_SHORT).show();


                            }

                        }
                    });
                }
            }
        });
        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Registration.this, MainActivity.class));

            }
        });

    }

    private void setupUIViews() {
        userName = (EditText) findViewById(R.id.etUserName);
        userPassword = (EditText) findViewById(R.id.etUserPassword);
        userEmail = (EditText) findViewById(R.id.etUserEmail);
        regButton = (Button) findViewById(R.id.btnRegister);
        userLogin = (TextView) findViewById(R.id.Login);
        userProfilePic =(ImageView) findViewById(R.id.ivProfile);

    }

    private Boolean validate() {
        Boolean result = false;

        String name = userName.getText().toString();
        String password = userPassword.getText().toString();
        String email = userEmail.getText().toString();

        if (name.isEmpty() || password.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        } else {
            result = true;
        }

        return result;
    }

    private void sendEmailVerification() {
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        sendUserData();
                        Toast.makeText(Registration.this, "Successfully Registered, Verification mail sent!", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(Registration.this, MainActivity.class));
                    } else {
                        Toast.makeText(Registration.this, "Verification mail has'nt been sent!", Toast.LENGTH_SHORT).show();


                    }
                }
            });
        }
    }
    private void sendUserData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
        UserProfile userProfile = new UserProfile(email,name);
      myRef.setValue(userProfile);
    }
}


