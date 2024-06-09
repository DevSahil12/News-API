package com.master.newsapi.SplashActivities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.master.newsapi.MainActivity;
import com.master.newsapi.R;

public class SplashLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_login);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashLogin.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },5000);

    }
}