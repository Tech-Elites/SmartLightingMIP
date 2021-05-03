package com.example.smartlightingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new CountDownTimer(3000,1000)
        {
            public void onTick(long milliseconds)
            {

            }
            public void onFinish()
            {
                Intent i = new Intent(MainActivity.this, LandingPage.class);
                finish();
                startActivity(i);
            }

        }.start();

    }
}