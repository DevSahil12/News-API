package com.master.newsapi.LoginActivities;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.master.newsapi.DataBase.User;
import com.master.newsapi.DataBase.UserDAO;
import com.master.newsapi.R;
import com.master.newsapi.SplashActivities.SplashLogin;

import org.mindrot.jbcrypt.BCrypt;


public class SignUp extends AppCompatActivity {

    public EditText username,email,password;
    Button signup;
    UserDAO userDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username=findViewById(R.id.FirstName);
        email=findViewById(R.id.Email);
        password=findViewById(R.id.Password);
        signup=findViewById(R.id.buttonLogin);

        userDAO=new UserDAO(this);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });


    }
    public void registerUser(){
        String username1=username.getText().toString().trim();
        String email1=email.getText().toString().trim();
        String password1=password.getText().toString().trim();
        if (username1.isEmpty() || email1.isEmpty() || password1.isEmpty())
        {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String hashedPassword= BCrypt.hashpw(password1,BCrypt.gensalt());
        User newUser=new User(username1,email1,password1);
        long result=userDAO.addUser(newUser);

        if (result!=-1){
            Toast.makeText(this, "User registered successfully", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(SignUp.this, SplashLogin.class);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();

        }

    }
    @Override
    protected void onDestroy(){
        userDAO.close();
        super.onDestroy();
    }
}