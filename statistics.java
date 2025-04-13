package com.appsnipp.AgriProbe;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class statistics extends Activity {

    private TextView temperatureTextView;
    private TextView humidityTextView;
    private TextView soilMoistureTextView;
    private ToggleButton pumpToggleButton;

    private ProgressBar progressBar1, progressBar2, progressBar3;

    private DatabaseReference mDatabase;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        firebaseDatabase = FirebaseDatabase.getInstance();

        // Initialize Firebase database reference
        mDatabase = FirebaseDatabase.getInstance().getReference("Data");

        // Read temperature data from Firebase
        temperatureTextView = findViewById(R.id.tempread);
        humidityTextView = findViewById(R.id.humidread);
        soilMoistureTextView = findViewById(R.id.soilread);
        pumpToggleButton = findViewById(R.id.togglebutton);

        progressBar1 = findViewById(R.id.progress1);
        progressBar2 = findViewById(R.id.progress2);
        progressBar3 = findViewById(R.id.progress3);

        // Call method to get and display data
        getAndDisplayData();

        // Set onCheckedChangeListener for pumpToggleButton
        pumpToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mDatabase.child("water_pump_status").setValue("ON");
                    Toast.makeText(statistics.this, "Water pump on", Toast.LENGTH_SHORT).show();
                } else {
                    mDatabase.child("water_pump_status").setValue("OFF");
                    Toast.makeText(statistics.this, "Water pump off", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getAndDisplayData() {
        // Add ValueEventListener to read data from Firebase
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // this method is call to get the realtime
                // updates in the data.
                // this method is called when the data is
                // changed in our Firebase console.
                // below line is for getting the data from
                // snapshot of our database.
                Long value1 = snapshot.child("temperature").getValue(Long.class);
                Long value2 = snapshot.child("humidity").getValue(Long.class);
                Long value3 = snapshot.child("soil moisture").getValue(Long.class);

                // check if values are not null
                if (value1 != null) {
                    // after getting the value we are setting
                    // our value to our text view in below line.
                    // Assuming you have three TextViews in your layout
                    temperatureTextView.setText(String.valueOf(value1));
                    progressBar1.setProgress(value1.intValue());
                }

                if (value2 != null) {
                    humidityTextView.setText(String.valueOf(value2));
                    progressBar2.setProgress(value2.intValue());
                }

                if (value3 != null) {
                    soilMoistureTextView.setText(String.valueOf(value3));
                    progressBar3.setProgress(value3.intValue());
                }

                // Set the state of the pumpToggleButton based on the water pump status
                String pumpStatus = snapshot.child("water_pump_status").getValue(String.class);
                if (pumpStatus != null) {
                    if (pumpStatus.equals("ON")) {
                        pumpToggleButton.setChecked(true);
                    } else {
                        pumpToggleButton.setChecked(false);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(statistics.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}