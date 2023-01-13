package com.shahin.drivesafe.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.shahin.drivesafe.Adapters.PendingDriversAdapter;
import com.shahin.drivesafe.Driver.DriverprofileDActivity;
import com.shahin.drivesafe.Models.UserModel;
import com.shahin.drivesafe.R;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DriverlistActivity extends AppCompatActivity {


    ListView listView ;

    List<UserModel> list = new ArrayList<>();
    private ProgressDialog progressDialog;

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driverlist);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);





        imageView = findViewById(R.id.noitemID1);
        listView = findViewById(R.id.driverslistviewId1);
        progressDialog = new ProgressDialog(this);


        Intent intent = getIntent();

        String val = intent.getStringExtra("key");


        imageView.setVisibility(View.GONE);

        if (val.equals("best")){


            Bestddrivers();


        }
        else if (val.equals("all")){

            AllDrivers();
        }
        else if (val.equals("dng")){

            dangerdrivers();

        }

    }

    private void dangerdrivers() {

        progressDialog.setMessage("please wait...");
        progressDialog.show();
        FirebaseFirestore.getInstance().collection("approved_Drivers")
                .orderBy("point", Query.Direction.DESCENDING)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                list.clear();
                for (DocumentSnapshot doc : task.getResult()) {

                    UserModel userModel = doc.toObject(UserModel.class);

                    if (userModel.getPoint()<=20){
                        list.add(userModel);
                    }
                    Log.d(TAG, "onComplete: " + userModel.toString());

                }
                progressDialog.dismiss();

                if (list.size()==0){
                    imageView.setVisibility(View.VISIBLE);
                }

                PendingDriversAdapter adapter = new PendingDriversAdapter(DriverlistActivity.this,list);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        UserModel userModel = list.get(position);
                        Intent intent = new Intent(DriverlistActivity.this, DriverprofileDActivity.class);
                       intent.putExtra("key",userModel.getUserId());
                        startActivity(intent);
                        Animatoo.animateInAndOut(DriverlistActivity.this);

                    }
                });

                listView.setAdapter(adapter);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }


    private void Bestddrivers() {

        progressDialog.setMessage("please wait...");
        progressDialog.show();
        FirebaseFirestore.getInstance().collection("approved_Drivers")
                .orderBy("point", Query.Direction.DESCENDING).limit(10)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                list.clear();
                for (DocumentSnapshot doc : task.getResult()) {

                    UserModel userModel = doc.toObject(UserModel.class);
                    list.add(userModel);
                    Log.d(TAG, "onComplete: " + userModel.toString());

                }
                progressDialog.dismiss();

                if (list.size()==0){
                    imageView.setVisibility(View.VISIBLE);
                }

                PendingDriversAdapter adapter = new PendingDriversAdapter(DriverlistActivity.this,list);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        UserModel userModel = list.get(position);
                        Intent intent = new Intent(DriverlistActivity.this, DriverprofileDActivity.class);
                        intent.putExtra("key",userModel.getUserId());
                        startActivity(intent);
                        Animatoo.animateInAndOut(DriverlistActivity.this);

                    }
                });

                listView.setAdapter(adapter);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }

    private void AllDrivers() {


        progressDialog.setMessage("please wait...");
        progressDialog.show();

        FirebaseFirestore.getInstance().collection("approved_Drivers").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                list.clear();
                for (DocumentSnapshot doc : task.getResult()) {

                    UserModel userModel = doc.toObject(UserModel.class);
                    list.add(userModel);
                    Log.d(TAG, "onComplete: " + userModel.toString());

                }
                progressDialog.dismiss();

                PendingDriversAdapter adapter = new PendingDriversAdapter(DriverlistActivity.this,list);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        UserModel userModel = list.get(position);
                        Intent intent = new Intent(DriverlistActivity.this, DriverprofileDActivity.class);
                        intent.putExtra("key",userModel.getUserId());
                        startActivity(intent);
                        Animatoo.animateInAndOut(DriverlistActivity.this);

                    }
                });


                listView.setAdapter(adapter);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home)
            finish();
        Animatoo.animateZoom(DriverlistActivity.this);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateZoom(DriverlistActivity.this);
    }
}
