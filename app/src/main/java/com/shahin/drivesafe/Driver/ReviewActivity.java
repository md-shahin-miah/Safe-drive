package com.shahin.drivesafe.Driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.shahin.drivesafe.Adapters.ReviewAdapter;
import com.shahin.drivesafe.Models.ReviewModel;
import com.shahin.drivesafe.R;

import java.util.ArrayList;
import java.util.List;

public class ReviewActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    List<ReviewModel> reviewModelList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        recyclerView = findViewById(R.id.reviewuserRecyID);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Reviews");




        Intent intent = getIntent();


        String val = intent.getStringExtra("key");


        FirebaseFirestore.getInstance().collection("approved_Drivers").document(val)
                .collection("Reviews").orderBy("createat", Query.Direction.DESCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()){

                            reviewModelList = new ArrayList<>();

                            for (DocumentSnapshot doc : task.getResult()){

                                ReviewModel reviewModel = doc.toObject(ReviewModel.class);
                                reviewModelList.add(reviewModel);

                            }

                            ReviewAdapter adapter = new ReviewAdapter(ReviewActivity.this,reviewModelList);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(ReviewActivity.this));
                            recyclerView.setAdapter(adapter);

                        }

                    }
                });


    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home)
            finish();

        Animatoo.animateSlideDown(ReviewActivity.this);

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSlideDown(ReviewActivity.this);
    }
}


