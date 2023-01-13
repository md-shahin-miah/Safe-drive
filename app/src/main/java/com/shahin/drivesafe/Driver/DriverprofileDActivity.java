package com.shahin.drivesafe.Driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.shahin.drivesafe.Adapters.AllincidentReporterAdapter;
import com.shahin.drivesafe.Adapters.ReviewAdapter;
import com.shahin.drivesafe.Models.NormaluserModel;
import com.shahin.drivesafe.Models.ReportModel;
import com.shahin.drivesafe.Models.ReviewModel;
import com.shahin.drivesafe.Models.UserModel;
import com.shahin.drivesafe.R;
import com.shahin.drivesafe.Reporter.AllincidentlistRActivity;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DriverprofileDActivity extends AppCompatActivity {


    RecyclerView recyclerView;


    UserModel userModel;
    NormaluserModel normaluserModel;


    List<ReviewModel> reviewModelList;
    String val;

    int ratingcnt;


    RecyclerView recyclerView2;

    List<ReportModel> reportModelList;


    ImageView coverpic;
    CircleImageView propic;
    TextView name,address,license,phone;
    TextView point ;
    TextView avgrating;

    TextView cnt1,cnt2,cnt3,cnt4,cnt5;

    ProgressBar progressBar1,progressBar2,progressBar3,progressBar4,progressBar5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driverprofile_d);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Driver Profile");

        Intent intent = getIntent();
        val = intent.getStringExtra("key");


        coverpic = findViewById(R.id.coverpicId);
        propic = findViewById(R.id.propicID);
        name = findViewById(R.id.pronameID);
        address = findViewById(R.id.proaddressId);
        point = findViewById(R.id.propointId);
        avgrating = findViewById(R.id.avgrating);
        license = findViewById(R.id.licensenumberID);
        phone = findViewById(R.id.phonenumber);

        recyclerView  = findViewById(R.id.reviewuserRecyID);
        recyclerView2  = findViewById(R.id.OwnRecyID);


        cnt1 = findViewById(R.id.countpro);
        cnt2 = findViewById(R.id.countpro2);
        cnt3 = findViewById(R.id.countpro3);
        cnt4 = findViewById(R.id.countpro4);
        cnt5 = findViewById(R.id.countpro5);


        progressBar1 = findViewById(R.id.progress_bar1);
        progressBar2 = findViewById(R.id.progress_bar2);
        progressBar3 = findViewById(R.id.progress_bar3);
        progressBar4 = findViewById(R.id.progress_bar4);
        progressBar5 = findViewById(R.id.progress_bar5);


        setRecyview();

        setRecyview2();


        if (!isConnected()){

            Toast.makeText(this, "You are in Offline", Toast.LENGTH_SHORT).show();

            return;
        }




        FirebaseFirestore.getInstance().collection("NormalUserinfo")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                DocumentSnapshot doc = task.getResult();

                normaluserModel = doc.toObject(NormaluserModel.class);


            }
        });


        FirebaseFirestore.getInstance().collection("approved_Drivers")
                .document(val)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                DocumentSnapshot doc = task.getResult();
                userModel = doc.toObject(UserModel.class);


                FirebaseFirestore.getInstance().collection("approved_Drivers").document(val)
                        .collection("Reviews").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()){

                            ratingcnt = task.getResult().size();

                            Picasso.get().load(userModel.getImage()).into(coverpic);
                            Picasso.get().load(userModel.getImage()).into(propic);
                            name.setText(userModel.getName());
                            address.setText(userModel.getAddress());
                            point.setText(userModel.getPoint()+"");

                            float rat = userModel.getAvgrating()/ratingcnt;
                            String rats = String.format("%.2f", rat);
                            avgrating.setText(rats);

                            license.setText("License Number : " + userModel.getLicense());
                            phone.setText("Phone Number : " + userModel.getPhone());

                            int cn1 = 0,cn2 = 0,cn3 = 0,cn4 = 0,cn5 = 0;
                            for (DocumentSnapshot doc : task.getResult()){

                                ReviewModel reviewModel = doc.toObject(ReviewModel.class);

                                if (reviewModel.getRating()==5){
                                    cn5=cn5+1;
                                }else  if (reviewModel.getRating()==4){

                                    cn4 = cn4+1;
                                } else  if (reviewModel.getRating()==3){

                                    cn3 = cn3 +1;
                                }
                                else if (reviewModel.getRating()==2){
                                    cn2 = cn2 +1;
                                }
                                else  if (reviewModel.getRating()==1){

                                    cn1 = cn1 +1;

                                }
                            }
                            cnt5.setText(cn1+"");
                            cnt4.setText(cn2+"");
                            cnt3.setText(cn3+"");
                            cnt2.setText(cn4+"");
                            cnt1.setText(cn5+"");

                            progressBar1.setProgress(cn5);
                            progressBar2.setProgress(cn4);
                            progressBar3.setProgress(cn3);
                            progressBar4.setProgress(cn2);
                            progressBar5.setProgress(cn1);

                        }
                    }
                });

            }
        });


        findViewById(R.id.morereview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DriverprofileDActivity.this, ReviewActivity.class);
                intent.putExtra("key",userModel.getUserId());
                startActivity(intent);
            }
        });



    }


    private void setRecyview2() {

        FirebaseFirestore.getInstance().collection("Report").whereEqualTo("userid",val)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()){

                    reportModelList = new ArrayList<>();

                    for (DocumentSnapshot doc : task.getResult()){

                        ReportModel reportModel = doc.toObject(ReportModel.class);
                        reportModelList.add(reportModel);

                    }

                    AllincidentReporterAdapter adapter = new AllincidentReporterAdapter(DriverprofileDActivity.this,reportModelList);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DriverprofileDActivity.this);
                    recyclerView2.setLayoutManager(linearLayoutManager);
                    recyclerView2.setHasFixedSize(true);
                    recyclerView2.setAdapter(adapter);

                }

            }
        });



    }

    private void setRecyview() {
        FirebaseFirestore.getInstance().collection("approved_Drivers").document(val)
                .collection("Reviews").orderBy("createat", Query.Direction.DESCENDING).limit(3).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){

                    reviewModelList = new ArrayList<>();

                    for (DocumentSnapshot doc : task.getResult()){

                        ReviewModel reviewModel = doc.toObject(ReviewModel.class);
                        reviewModelList.add(reviewModel);
                    }
                    ReviewAdapter adapter = new ReviewAdapter(DriverprofileDActivity.this,reviewModelList);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(DriverprofileDActivity.this));
                    recyclerView.setAdapter(adapter);

                }

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }


    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
        }
        return connected;
    }




}
