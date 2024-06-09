package com.master.newsapi;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.master.newsapi.DataBase.User;
import com.master.newsapi.DataBase.UserDAO;

public class ProfileActivity extends AppCompatActivity {

    private TextView usernameTextView;
    private TextView emailTextView;

    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        usernameTextView = findViewById(R.id.usernameTextView);
        emailTextView = findViewById(R.id.emailTextView);

        userDAO = new UserDAO(this);

        // Get the logged-in user's email from session or intent extras
        String userEmail = getIntent().getStringExtra("userEmail"); // Replace with your logic to get the email

        // Fetch user details from database
        User user = userDAO.getUserByEmail(userEmail);

        // Display user details in UI
        if (user != null) {
            usernameTextView.setText(user.getUsername());
            emailTextView.setText(user.getEmail());
            // Set other fields as needed
        } else {
            // Handle case where user is not found
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        userDAO.close();
        super.onDestroy();
    }
}
