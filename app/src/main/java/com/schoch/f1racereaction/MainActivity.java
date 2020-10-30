package com.schoch.f1racereaction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    MaterialButton singlePlayerButton;
    MaterialButton settingsButton;
    TextView start_heading;
    TextView description;
    LinearLayout main_act;
    private static final String SHARED_PREF_NAME = "settings";
    private static final String KEY_NAME = "darkmode";
    private static int darkmode_enabled = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start_heading = findViewById(R.id.start_heading);
        description = findViewById(R.id.description);
        singlePlayerButton = findViewById(R.id.single_player);
        settingsButton = findViewById(R.id.settings);
        main_act = findViewById(R.id.main_act);

        SharedPreferences sp = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String name = sp.getString(KEY_NAME, null);

        if (name != null) {
            darkmode_enabled = Integer.parseInt(name);
        }

        if (darkmode_enabled == 1) {
            start_heading.setTextColor(Color.parseColor("#ffffff"));
            description.setTextColor(Color.parseColor("#ffffff"));
            main_act.setBackgroundColor(Color.parseColor("#15151F"));
        }

        initViews();
    }

    protected void initViews() {
        singlePlayerButton = findViewById(R.id.single_player);
        settingsButton = findViewById(R.id.settings);
        singlePlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSinglePlayerButtonClick();
            }
        });
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSettingsButtonClick();
            }
        });
    }

    protected void onSinglePlayerButtonClick() {
        Intent intent = new Intent(this, RaceActivity.class);
        startActivity(intent);
    }

    protected void onSettingsButtonClick() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}