package com.appsnipp.AgriProbe;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class agriprobe extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText editTextemail, editTextpassword;
    CheckBox showPasswordCheckBox;
    Button signupButton, signInButton;
    TextView forgot;


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(agriprobe.this, dashboard.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agriprobe);

        mAuth = FirebaseAuth.getInstance();
        editTextemail = findViewById(R.id.mailid);
        editTextpassword = findViewById(R.id.passid);
        showPasswordCheckBox = findViewById(R.id.showPasswordCheckBox);
        signupButton = findViewById(R.id.button1);
        signInButton = findViewById(R.id.button2);
        forgot = findViewById(R.id.forgotlink);

        //add color to action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Set the background color
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.gradientLightGreen)));
        }

        //forgot
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(agriprobe.this, forgotpassword.class) );
            }
        });
        //show password
        showPasswordCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Show Password
                    editTextpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    // Hide Password
                    editTextpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(agriprobe.this, regestration.class);
                startActivity(intent);
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextemail.getText().toString().trim();
                String password = editTextpassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(agriprobe.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(agriprobe.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(agriprobe.this, "Authentication successful", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(agriprobe.this, dashboard.class));
                                } else {
                                    Toast.makeText(agriprobe.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
    }
}
