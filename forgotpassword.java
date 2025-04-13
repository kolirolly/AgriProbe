package com.appsnipp.AgriProbe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotpassword extends AppCompatActivity {

    private Button forgetbut;
    private EditText txtemail;
    private String Email;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        auth = FirebaseAuth.getInstance();

        txtemail = findViewById(R.id.forgotemail);
        forgetbut = findViewById(R.id.continuee);

        //add color to action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Set the background color
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.gradientLightGreen)));
        }

        forgetbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validatedata();
            }
        });
    }

    private void validatedata() {
        Email = txtemail.getText().toString();
        if (TextUtils.isEmpty(Email)) {
            txtemail.setError("Email is required");
        } else {
            forgetpass();
        }
    }

    private void forgetpass() {
        auth.sendPasswordResetEmail(Email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(forgotpassword.this, "Check your Email", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(forgotpassword.this, agriprobe.class));
                            finish();
                        } else {
                            Toast.makeText(forgotpassword.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
