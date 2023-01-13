package com.shahin.drivesafe.Driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.shahin.drivesafe.Adapters.PointHistoryAdapter;
import com.shahin.drivesafe.Models.ReportModel;
import com.shahin.drivesafe.R;
import com.shahin.drivesafe.Reporter.AllincidentlistRActivity;

import java.util.ArrayList;
import java.util.List;

public class PointHistoryActivity extends AppCompatActivity {


    RecyclerView recyclerView;

    List<ReportModel> reportModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_history);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Point History");


        recyclerView = findViewById(R.id.pointhistoryID);

       String userid =  FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseFirestore.getInstance().collection("Report").whereEqualTo("userid",userid)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()){


                    reportModelList = new ArrayList<>();

                    for (DocumentSnapshot doc : task.getResult()){

                        ReportModel reportModel = doc.toObject(ReportModel.class);
                        reportModelList.add(reportModel);

                    }


                    PointHistoryAdapter adapter = new PointHistoryAdapter(PointHistoryActivity.this,reportModelList);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PointHistoryActivity.this);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setHasFixedSize(true);

                    recyclerView.setAdapter(adapter);


                }

            }
        });




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home)
            finish();
        Animatoo.animateZoom(PointHistoryActivity.this);
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateZoom(PointHistoryActivity.this);
    }
}
