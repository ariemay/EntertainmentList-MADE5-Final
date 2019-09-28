package com.ariemay.entertainmentlistmade3.views;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toolbar;

import com.ariemay.entertainmentlistmade3.R;
import com.ariemay.entertainmentlistmade3.alarms.DailyCheckerAlarm;
import com.ariemay.entertainmentlistmade3.alarms.NewMoviesAlarm;

public class SettingActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

    private static final String DAILY = "daily";
    private static final String RELEASE = "release";

    Switch dailyCheck, upcomingCheck;
    NewMoviesAlarm newMoviesAlarm;
    DailyCheckerAlarm dailyCheckerAlarm;
    SharedPreferences dailyAlarmPrefer, newMoviePref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        dailyCheck = findViewById(R.id.daily_setting);
        upcomingCheck = findViewById(R.id.upcoming_setting);

        dailyCheck.setOnCheckedChangeListener(this);
        upcomingCheck.setOnCheckedChangeListener(this);

        dailyCheckerAlarm  = new DailyCheckerAlarm();
        newMoviesAlarm = new NewMoviesAlarm();

        dailyAlarmPrefer = getSharedPreferences(DAILY, MODE_PRIVATE);
        dailyCheck.setChecked(dailyAlarmPrefer.getBoolean(DAILY, false));

        newMoviePref = getSharedPreferences(RELEASE, MODE_PRIVATE);
        upcomingCheck.setChecked(newMoviePref.getBoolean(RELEASE, false));

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case (R.id.daily_setting):
                if (b) {
                    String time = "07:00";
                    dailyCheckerAlarm.setDailyAlarm(getApplicationContext(), time);
                    SharedPreferences.Editor editor = getSharedPreferences(DAILY, MODE_PRIVATE).edit();
                    editor.putBoolean(DAILY, true);
                    editor.apply();
                } else {
                    dailyCheckerAlarm.cancelAlarm(getApplicationContext());
                    SharedPreferences.Editor editor = getSharedPreferences(DAILY, MODE_PRIVATE).edit();
                    editor.putBoolean(DAILY, false);
                    editor.apply();
                }
                break;

            case (R.id.upcoming_setting):
                if (b) {
                    String time = "08:00";
                    newMoviesAlarm.setReleaseAlarm(getApplicationContext(), time);
                    SharedPreferences.Editor editor = getSharedPreferences(RELEASE, MODE_PRIVATE).edit();
                    editor.putBoolean(RELEASE, true);
                    editor.apply();
                } else {
                    newMoviesAlarm.cancelAlarm(getApplicationContext());
                    SharedPreferences.Editor editor = getSharedPreferences(RELEASE, MODE_PRIVATE).edit();
                    editor.putBoolean(RELEASE, false);
                    editor.apply();
                }
                break;
        }
    }

}
