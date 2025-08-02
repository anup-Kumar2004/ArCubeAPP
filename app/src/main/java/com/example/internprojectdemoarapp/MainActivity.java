package com.example.internprojectdemoarapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Spinner spinnerDrills;
    Button startARBtn;
    ImageView imageDrill;
    TextView descText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerDrills = findViewById(R.id.spinnerDrills);
        imageDrill = findViewById(R.id.imageDrill);
        descText = findViewById(R.id.descText);
        startARBtn = findViewById(R.id.startARBtn);

        String[] drills = {"Drill 1", "Drill 2", "Drill 3"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, drills);
        spinnerDrills.setAdapter(adapter);

        spinnerDrills.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    imageDrill.setImageResource(R.drawable.drill1);
                    descText.setText("Drill 1: Practice cone placement");
                } else if (position == 1) {
                    imageDrill.setImageResource(R.drawable.drill2);
                    descText.setText("Drill 2: Accuracy training");
                } else {
                    imageDrill.setImageResource(R.drawable.drill3);
                    descText.setText("Drill 3: Coordination test");
                }
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) { }
        });

        startARBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ARActivity.class);
            startActivity(intent);
        });
    }
}
