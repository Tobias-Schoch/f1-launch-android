package com.schoch.f1racereaction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class SettingsActivity extends AppCompatActivity {

    private static final String SHARED_PREF_NAME = "settings";
    private static final String KEY_NAME = "darkmode";
    private static final String SHARED_PREF_NAME2 = "high_f1";
    private static final String KEY_NAME2 = "f1";
    private static int darkmode_enabled = 2;

    MaterialButton backButton;
    MaterialButton darkModeButton;
    MaterialButton resetButton;
    TextView settings_heading;
    RelativeLayout settings_act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settings_heading = findViewById(R.id.settings_heading);
        settings_act = findViewById(R.id.settings_act);
        resetButton = findViewById(R.id.reset);

        SharedPreferences sp = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String name = sp.getString(KEY_NAME, null);

        if (name != null) {
            darkmode_enabled = Integer.parseInt(name);
        }

        if (darkmode_enabled == 1) {
            settings_heading.setTextColor(Color.parseColor("#ffffff"));
            settings_act.setBackgroundColor(Color.parseColor("#15151f"));
        }

        initViews();
    }

    protected void initViews() {
        backButton = findViewById(R.id.back);
        darkModeButton = findViewById(R.id.darkmode);
        resetButton = findViewById(R.id.reset);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackButtonClick();
            }
        });
        darkModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ondarkModeButtonClick();
            }
        });
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResetButtonClick();
            }
        });
    }

    protected void onResetButtonClick() {
        SharedPreferences sp = getSharedPreferences(SHARED_PREF_NAME2, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        SharedPreferences sp2 = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String name = sp2.getString(KEY_NAME, null);
        settings_heading.setTextColor(Color.parseColor("#15151f"));
        settings_act.setBackgroundColor(Color.parseColor("#ffffff"));
        darkmode_enabled = 2;
        SharedPreferences sp3 = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sp3.edit();
        editor2.putString(KEY_NAME, String.valueOf(darkmode_enabled));
        editor2.apply();


        editor.putString(KEY_NAME2, "- ");
        editor.apply();
        Toast.makeText(SettingsActivity.this, "Reset Highscore", Toast.LENGTH_LONG).show();
    }

    protected void onBackButtonClick() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    protected void ondarkModeButtonClick() {

        SharedPreferences sp = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String name = sp.getString(KEY_NAME, null);

        if (name != null) {
            darkmode_enabled = Integer.parseInt(name);
        }

        if (darkmode_enabled == 1) {
            settings_heading.setTextColor(Color.parseColor("#15151f"));
            settings_act.setBackgroundColor(Color.parseColor("#ffffff"));
            darkmode_enabled = 2;
        } else if (darkmode_enabled == 2) {
            settings_heading.setTextColor(Color.parseColor("#ffffff"));
            settings_act.setBackgroundColor(Color.parseColor("#15151f"));
            darkmode_enabled = 1;
        }

        SharedPreferences sp2 = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp2.edit();
        editor.putString(KEY_NAME, String.valueOf(darkmode_enabled));
        editor.apply();

    }
}