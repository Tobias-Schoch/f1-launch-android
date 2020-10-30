package com.schoch.f1racereaction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.Random;

public class RaceActivity extends AppCompatActivity {

    MaterialButton button;
    ImageView lights;
    TextView textView;
    TextView record;
    TextView title;
    RelativeLayout race_act;
    long startTime;
    int mode = 1;
    String high = "";

    private static final String SHARED_PREF_NAME = "high_f1";
    private static final String KEY_NAME = "f1";
    private static final String SHARED_PREF_NAME2 = "settings";
    private static final String KEY_NAME2 = "darkmode";
    private static int darkmode_enabled = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_race);

        record = findViewById(R.id.record);
        race_act = findViewById(R.id.race_act);
        title = findViewById(R.id.title);
        textView = findViewById(R.id.textView);

        SharedPreferences sp = getSharedPreferences(SHARED_PREF_NAME2, MODE_PRIVATE);
        String name = sp.getString(KEY_NAME2, null);

        if (name != null) {
            darkmode_enabled = Integer.parseInt(name);
        }

        if (darkmode_enabled == 1) {
            record.setTextColor(Color.parseColor("#ffffff"));
            title.setTextColor(Color.parseColor("#ffffff"));
            textView.setTextColor(Color.parseColor("#ffffff"));
            race_act.setBackgroundColor(Color.parseColor("#15151f"));
        }
        initViews();
    }

    protected void initViews() {
        button = findViewById(R.id.button);
        lights = findViewById(R.id.lights);
        textView = findViewById(R.id.textView);
        record = findViewById(R.id.record);
        final Handler handler = new Handler();

        SharedPreferences sp = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String highest = sp.getString(KEY_NAME, null);

        if (highest == null) {
            highest = "- ";
        }

        record.setText("Highscore: " + highest + "ms");

        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

                    if (mode == 1) {
                        button.setText("Race");
                        button.setTextSize(40);
                        mode = 2;

                        Random r = new Random();
                        int time = r.nextInt(4000 - 1000) + 1000;
                        time += 4000;


                        lights.setImageResource(R.mipmap.phase_two);

                        handler.postDelayed(new Runnable() {
                            public void run() {
                                lights.setImageResource(R.mipmap.phase_three);
                            }
                        }, 1000);

                        handler.postDelayed(new Runnable() {
                            public void run() {
                                lights.setImageResource(R.mipmap.phase_four);
                            }
                        }, 2000);

                        handler.postDelayed(new Runnable() {
                            public void run() {
                                lights.setImageResource(R.mipmap.phase_five);
                            }
                        }, 3000);

                        handler.postDelayed(new Runnable() {
                            public void run() {
                                lights.setImageResource(R.mipmap.phase_six);
                            }
                        }, 4000);

                        handler.postDelayed(new Runnable() {
                            public void run() {
                                lights.setImageResource(R.mipmap.phase_one);
                                mode = 2;
                                startTime = System.currentTimeMillis();
                            }
                        }, time);
                    } else if (mode == 2) {
                        if (startTime != 0) {
                            long difference = System.currentTimeMillis() - startTime;
                            textView.setText(String.valueOf(difference) + "ms");

                            SharedPreferences sp = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                            String highest = sp.getString(KEY_NAME, null);

                            if (highest == null || highest.equals("- ")) {
                                high = "- ";
                                SharedPreferences sp2 = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp2.edit();
                                editor.putString(KEY_NAME, String.valueOf(difference));
                                editor.apply();
                                record.setText("Highscore: " + String.valueOf(difference) + "ms");
                            } else {
                                high = highest;
                                if (Integer.parseInt(high) > Integer.parseInt(String.valueOf(difference))) {
                                    SharedPreferences sp2 = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sp2.edit();
                                    editor.putString(KEY_NAME, String.valueOf(difference));
                                    editor.apply();
                                    record.setText("Highscore: " + String.valueOf(difference) + "ms");
                                }
                            }

                            button.setText("Restart");
                            button.setTextSize(23);
                            startTime = 0;
                            mode = 1;
                        } else {
                            handler.removeCallbacksAndMessages(null);
                            lights.setImageResource(R.mipmap.phase_one);
                            textView.setText("Too early");
                            button.setText("Restart");
                            button.setTextSize(23);
                            startTime = 0;
                            mode = 1;
                        }
                    }
                }
                return false;
            }
        });
    }
}