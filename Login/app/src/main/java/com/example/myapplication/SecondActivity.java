package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;


public class SecondActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Button log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        firebaseAuth = FirebaseAuth.getInstance();

        log = (Button) findViewById(R.id.btnLog);

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logout();

            }
        });
    }

    private void Logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(SecondActivity.this, MainActivity.class));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.logMenu: {
                Logout();
            }
            case R.id.settingsMenu:
                startActivity(new Intent(SecondActivity.this,Profile.class));
            }
            return super.onOptionsItemSelected(item);
        }

    }
