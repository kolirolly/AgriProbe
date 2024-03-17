package com.appsnipp.AgriProbe;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class dashboard extends AppCompatActivity {


    FirebaseAuth auth;
    FirebaseUser user;
    private Button startButton;
    private Button logoutButton, manual;
    private TextView userman, user2, user3, user4, user5;
    private boolean isManualVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize start button
        startButton = findViewById(R.id.start);
        manual = findViewById(R.id.manual);
        userman = findViewById(R.id.manual1);
        user2 = findViewById(R.id.manual2);
        user3 = findViewById(R.id.manual3);
        user4 = findViewById(R.id.manual4);
        user5 = findViewById(R.id.manual5);

        //add color to action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Set the background color
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.gradientLightGreen)));
        }

        //manual button
        manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isManualVisible) {
                    // Show the manual instructions
                    userman.setVisibility(View.VISIBLE);
                    user2.setVisibility(View.VISIBLE);
                    user3.setVisibility(View.VISIBLE);
                    user4.setVisibility(View.VISIBLE);
                    user5.setVisibility(View.VISIBLE);
                    isManualVisible = true;
                } else {
                    // Hide the manual instructions
                    userman.setVisibility(View.GONE);
                    user2.setVisibility(View.GONE);
                    user3.setVisibility(View.GONE);
                    user4.setVisibility(View.GONE);
                    user5.setVisibility(View.GONE);
                    isManualVisible = false;
                }
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open StatisticsActivity when start button is clicked
                startActivity(new Intent(dashboard.this, statistics.class));
            }
        });

        // Initialize logout button
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        logoutButton = findViewById(R.id.logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to AgriProbeActivity when logout button is clicked
                FirebaseAuth.getInstance().signOut();
               Intent intent = new Intent(new Intent(dashboard.this, agriprobe.class));
               startActivity(intent);
                // Finish the current activity to prevent going back to DashboardActivity
                finish();
                Toast.makeText(dashboard.this, "Logout successful", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
