package com.master.newsapi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.master.newsapi.Api.NewsApiService;
import com.master.newsapi.LoginActivities.Login;
import com.master.newsapi.LoginActivities.SessionManager;
import com.master.newsapi.Retrofit.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final String API_KEY = "846a2356e7254f38a6cd07bc8af86226";
    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        toggle=new ActionBarDrawerToggle(this,
                drawerLayout,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        sessionManager=new SessionManager(this);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        if (!sessionManager.isLoggedIn()){
            navigateToLogin();
        }


        NewsApiService apiService = ApiClient.getService().create(NewsApiService.class);

        Call<NewsResponse> call = apiService.getTopHeadlines("us", API_KEY);
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call,
                                   Response<NewsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Article> articles = response.body().getArticles();
                    newsAdapter = new NewsAdapter(articles);
                    newsAdapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener(){
                        @Override
                        public void onItemClick(Article article){
                            openNewsDetails(article.getUrl());
                        }
                    });
                    recyclerView.setAdapter(newsAdapter);
                } else {
                    Log.e("MainActivity", "Response not successful");
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Log.e("MainActivity", "Error: " + t.getMessage());
            }
        });
    }



    public void openNewsDetails(String url){
        Intent intent=new Intent(MainActivity.this, NewsActivity.class);
        intent.putExtra("url",url);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.nav_profile){
            Intent intent=new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);

        }else if (id==R.id.nav_logout){
            sessionManager.setLogin(false,null);
            navigateToLogin();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    public void navigateToLogin(){
        Intent intent=new Intent(MainActivity.this, Login.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed(){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else
            super.onBackPressed();
    }
}
