package com.master.newsapi;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class NewsActivity extends AppCompatActivity {

    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
       webView=findViewById(R.id.webView);

        Intent intent=getIntent();
        if (intent!=null && intent.hasExtra("url")){
            String url=intent.getStringExtra("url");
            webView.loadUrl(url);
        }else {
            Toast.makeText(this, "Invalid news url", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}