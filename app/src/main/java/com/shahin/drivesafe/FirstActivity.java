package com.shahin.drivesafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shahin.drivesafe.Admin.AdminMainActivity;
import com.shahin.drivesafe.Driver.MainDriverActivity;
import com.shahin.drivesafe.Reporter.ReporterMainActivity;
import com.shahin.drivesafe.User.UserMainActivity;

public class FirstActivity extends AppCompatActivity {

    private static final String TAG = "FirstActivity";
    String idnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);


        SharedPreferences sharedPreferences = getSharedPreferences("identy", Context.MODE_PRIVATE);

        if (sharedPreferences.contains("name")) {
            idnt = sharedPreferences.getString("name", null);

        }

        if (idnt==null){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(FirstActivity.this, LoginActivity.class));
            finish();

        }
       else if (idnt.equals("no")) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(FirstActivity.this, LoginActivity.class));
            finish();
        }

        Log.d(TAG, "onCreate:rrrrrrrrrrrrrrrrr " + idnt);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {

            Log.d(TAG, "onCreate:rrrrrrrrrrrrrrrrr " + idnt);
            if (idnt == null) {
                startActivity(new Intent(FirstActivity.this, LoginActivity.class));
                finish();
            } else if (idnt.equals("user")) {
                startActivity(new Intent(FirstActivity.this, UserMainActivity.class));
                finish();
            } else if (idnt.equals("admin")) {
                startActivity(new Intent(FirstActivity.this, AdminMainActivity.class));
                finish();
            } else if (idnt.equals("reporter")) {
                startActivity(new Intent(FirstActivity.this, ReporterMainActivity.class));
                finish();
            } else if (idnt.equals("driver")) {
                startActivity(new Intent(FirstActivity.this, MainDriverActivity.class));
                finish();
            } else if (idnt.equals("no")) {
                startActivity(new Intent(FirstActivity.this, LoginActivity.class));
                finish();

            }

        }

    }
}

