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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class regestration extends AppCompatActivity {

    private EditText editTextText, editTextText13, editTextText12, editTextText1;
    private TextView textView2, already;
    private Button button2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regestration);

        // Find views by their IDs
        editTextText = findViewById(R.id.editTextText);
        editTextText13 = findViewById(R.id.editTextText13);
        editTextText12 = findViewById(R.id.editTextText12);
        editTextText1 = findViewById(R.id.editTextText1);
        textView2 = findViewById(R.id.textView2);
        button2 = findViewById(R.id.button2);
        already = findViewById(R.id.textView3);

//add color to action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Set the background color
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.gradientLightGreen)));
        }




        already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(regestration.this, agriprobe.class));
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username, email, password, confirm_password;
                username = String.valueOf(editTextText.getText());
                email = String.valueOf(editTextText13.getText());
                password = String.valueOf(editTextText12.getText());
                confirm_password = String.valueOf(editTextText1.getText());

                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(regestration.this, "Enter username", Toast.LENGTH_SHORT).show();
                    return;

                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(regestration.this, "Enter correct mail", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(regestration.this, "enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(confirm_password)) {
                    Toast.makeText(regestration.this, "confirm password", Toast.LENGTH_SHORT).show();
                }

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    //registration successfull
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    //check if user is not null
                                    if (user != null) {
                                        UserProfileChangeRequest profileupdates = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(username) //set the username
                                                .build();

                                        user.updateProfile(profileupdates) //update user profile with user name
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(regestration.this, "account successfully created", Toast.LENGTH_SHORT).show();
                                                            startActivity(new Intent(regestration.this,agriprobe.class));
                                                        }
                                                    }
                                                });
                                    }

                                } else {
                                    // if sign in fails, display a message to the user
                                    Toast.makeText(regestration.this, "regestration failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }


                        });


                // Perform any additional setup or logic if needed
            }
        });
    }
}

