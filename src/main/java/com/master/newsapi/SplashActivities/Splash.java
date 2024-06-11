package com.master.newsapi.SplashActivities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.master.newsapi.LoginActivities.Login;
import com.master.newsapi.MainActivity;
import com.master.newsapi.R;
import com.master.newsapi.LoginActivities.SessionManager;

public class Splash extends AppCompatActivity {

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sessionManager = new SessionManager(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sessionManager.isLoggedIn()) {
                    Intent intent = new Intent(Splash.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(Splash.this, Login.class);
                    startActivity(intent);
                }
                finish();
            }
        }, 4000); 
    }
}
