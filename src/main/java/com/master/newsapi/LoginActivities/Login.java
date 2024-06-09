package com.master.newsapi.LoginActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.master.newsapi.DataBase.User;
import com.master.newsapi.DataBase.UserDAO;
import com.master.newsapi.MainActivity;
import com.master.newsapi.R;


import org.mindrot.jbcrypt.BCrypt;

public class Login extends AppCompatActivity {
    private UserDAO userDAO;
    private EditText username, password;
    private Button login;
    private TextView signUp;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);
        if (sessionManager.isLoggedIn()) {
            navigateToMainActivity();
        }

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.button);
        signUp = findViewById(R.id.signUp);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, SignUp.class);
                startActivity(i);
            }
        });

        userDAO = new UserDAO(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    public void loginUser() {
        String email = username.getText().toString().trim();
        String Password = password.getText().toString().trim();
        if (email.isEmpty() || Password.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            return;
        }
        User user = userDAO.getUser(email);
        if (user != null && BCrypt.checkpw(Password, user.getPassword())) {
            Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();
            sessionManager.setLogin(true, email);
            navigateToMainActivity();
        } else {
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        userDAO.close();
        super.onDestroy();
    }
}

