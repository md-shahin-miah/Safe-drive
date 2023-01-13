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
import com.google.firebase.firestore.QuerySnapshot;
import com.shahin.drivesafe.Adapters.PendingDriversAdapter;
import com.shahin.drivesafe.Models.UserModel;
import com.shahin.drivesafe.R;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RequestDriverActivity extends AppCompatActivity {


    ListView listView ;

    List<UserModel> list = new ArrayList<>();
    private ProgressDialog progressDialog;


    ImageView imageView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_driver);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        listView = findViewById(R.id.pendingdriverslistviewId1);
        progressDialog = new ProgressDialog(this);

        imageView = findViewById(R.id.noitemID111);

        imageView.setVisibility(View.GONE);

        Intent intent = getIntent();

        String val = intent.getStringExtra("key");


        if (val.equals("reject")){


            Rejecteddrivers();


        }
        else if (val.equals("app")){

            PendingDrivers();
        }


    }

    private void Rejecteddrivers() {

        progressDialog.setMessage("please wait...");
        progressDialog.show();

        FirebaseFirestore.getInstance().collection("Rejected_Drivers").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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


                PendingDriversAdapter adapter = new PendingDriversAdapter(RequestDriverActivity.this,list);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        UserModel userModel = list.get(position);
                        Intent intent = new Intent(RequestDriverActivity.this,ApprovepageActivity.class);
                        intent.putExtra("obj",userModel);
                        intent.putExtra("check","reject");
                        startActivity(intent);
                        Animatoo.animateInAndOut(RequestDriverActivity.this);

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


    private void PendingDrivers() {


        progressDialog.setMessage("please wait...");
        progressDialog.show();

        FirebaseFirestore.getInstance().collection("temp_userInfo").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

                PendingDriversAdapter adapter = new PendingDriversAdapter(RequestDriverActivity.this,list);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        UserModel userModel = list.get(position);
                        Intent intent = new Intent(RequestDriverActivity.this,ApprovepageActivity.class);
                        intent.putExtra("obj",userModel);
                        intent.putExtra("check","app");
                        startActivity(intent);
                        Animatoo.animateInAndOut(RequestDriverActivity.this);

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
        Animatoo.animateSwipeLeft(RequestDriverActivity.this);
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSwipeLeft(RequestDriverActivity.this);
    }
}
