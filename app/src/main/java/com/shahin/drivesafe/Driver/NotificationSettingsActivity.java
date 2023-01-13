package com.shahin.drivesafe.Driver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.onesignal.OneSignal;
import com.shahin.drivesafe.R;

public class NotificationSettingsActivity extends AppCompatActivity {


    private static final String TAG = "NotificationActivity";


    Switch sound, vibrate, mute;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_settings);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Settings");


        sound = findViewById(R.id.soundId);
        vibrate = findViewById(R.id.vibrateId);
        mute = findViewById(R.id.muteId);


        sound.setChecked(true);
        vibrate.setChecked(true);
        mute.setChecked(false);



        sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sound.setChecked(true);
                    mute.setChecked(false);
                    OneSignal.setSubscription(true);
                    OneSignal.enableSound(true);
                    SharedPreferences sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
                    boolean swvibrate = sharedPreferences.getBoolean("vibrate", false);
                    storedata(true, swvibrate, false);

                } else {
                    sound.setChecked(false);
                    mute.setChecked(false);
                    OneSignal.enableSound(false);
                    SharedPreferences sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
                    boolean swvibrate = sharedPreferences.getBoolean("vibrate", false);
                    boolean swmute = sharedPreferences.getBoolean("mute", false);
                    storedata(false, swvibrate, false);
                    if (!swvibrate && !swmute) {
                        OneSignal.setSubscription(false);
                    } else {

                        OneSignal.setSubscription(true);
                        OneSignal.enableVibrate(true);
                    }
                }

            }
        });

        vibrate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    vibrate.setChecked(true);
                    mute.setChecked(false);
                    OneSignal.setSubscription(true);
                    OneSignal.enableVibrate(true);
                    SharedPreferences sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
                    boolean swSound = sharedPreferences.getBoolean("sound", false);
                    storedata(swSound, true, false);

                } else {
                    vibrate.setChecked(false);
                    mute.setChecked(false);
                    OneSignal.enableVibrate(false);
                    SharedPreferences sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
                    boolean swSound = sharedPreferences.getBoolean("sound", false);
                    boolean swmute = sharedPreferences.getBoolean("mute", false);
                    storedata(swSound, false, false);
                    if (!swSound && !swmute) {
                        OneSignal.setSubscription(false);
                    } else {
                        OneSignal.enableSound(true);
                        OneSignal.setSubscription(true);
                    }
                }
            }
        });

        mute.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    sound.setChecked(false);
                    vibrate.setChecked(false);
                    OneSignal.setSubscription(true);
                    OneSignal.enableSound(false);
                    OneSignal.enableVibrate(false);
                    storedata(false, false, true);

                } else {
                    sound.setChecked(false);
                    vibrate.setChecked(false);
                    OneSignal.setSubscription(false);
                    mute.setChecked(false);
                    storedata(false, false, false);

                }

            }
        });

        readData();


    }



    private void storedata(boolean s, boolean v, boolean m) {

        SharedPreferences sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("sound", s);
        editor.putBoolean("vibrate", v);
        editor.putBoolean("mute", m);
        editor.commit();

    }

    private void readData() {
        SharedPreferences sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        if (sharedPreferences.contains("sound") && sharedPreferences.contains("vibrate") && sharedPreferences.contains("mute")) {

            boolean swSound = sharedPreferences.getBoolean("sound", false);
            boolean swvibrate = sharedPreferences.getBoolean("vibrate", false);
            boolean swmute = sharedPreferences.getBoolean("mute", false);
            sound.setChecked(swSound);
            vibrate.setChecked(swvibrate);
            mute.setChecked(swmute);

        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==android.R.id.home)
            finish();
        Animatoo.animateSwipeRight(NotificationSettingsActivity.this);
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSwipeRight(NotificationSettingsActivity.this);
    }
}

