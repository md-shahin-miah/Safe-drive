//package com.xd.drivesafe.Reporter;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.MenuItem;
//import android.widget.Toast;
//
//import com.blogspot.atifsoftwares.animatoolib.Animatoo;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.Query;
//import com.google.firebase.firestore.QuerySnapshot;
//import com.xd.drivesafe.Adapters.AllincidentReporterAdapter;
//import com.xd.drivesafe.Models.ReportModel;
//import com.xd.drivesafe.Models.ReporterDataModel;
//import com.xd.drivesafe.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class AllincidentlistRActivity extends AppCompatActivity {
//
//    RecyclerView recyclerView;
//
//    private ProgressDialog progressDialog;
//    List <ReportModel> reportModelList;
//    ReporterDataModel reporterDataModel;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_allincidentlist_r);
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setTitle("All incidents");
//
//        recyclerView = findViewById(R.id.allincidentID);
//        progressDialog = new ProgressDialog(this);
//
//
//        Intent intent = getIntent();
//
//        String val = intent.getStringExtra("key");
//
//        if (val.equals("all")){
//
//            allincident();
//        }
//        else  if (val.equals("own")){
//
//            String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
//
//            FirebaseFirestore.getInstance().collection("Reporterinfo").document(id)
//                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//
//                    if (task.isSuccessful()){
//
//                        DocumentSnapshot doc = task.getResult();
//                        reporterDataModel = doc.toObject(ReporterDataModel.class);
//                        ownincident();
//                    }
//                    else
//                    {
//                        Toast.makeText(AllincidentlistRActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//
//                    }
//
//                }
//
//            });
//
//
//
//        }
//
//
//
//
//
//
//
//
//
//    }
//
//    private void ownincident() {
//
//        progressDialog.show();
//
//
//
//        FirebaseFirestore.getInstance().collection("Report")
//                .whereEqualTo("repoterid",reporterDataModel.getUserid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//
//                if (task.isSuccessful()){
//
//
//                    reportModelList = new ArrayList<>();
//
//                    for (DocumentSnapshot doc : task.getResult()){
//
//                        ReportModel reportModel = doc.toObject(ReportModel.class);
//                        reportModelList.add(reportModel);
//
//
//                    }
//
//                    progressDialog.dismiss();
//                    AllincidentReporterAdapter adapter = new AllincidentReporterAdapter(AllincidentlistRActivity.this,reportModelList);
//                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AllincidentlistRActivity.this);
//                    recyclerView.setLayoutManager(linearLayoutManager);
//                    recyclerView.setHasFixedSize(true);
//
//                    recyclerView.setAdapter(adapter);
//
//                }
//                else {
//
//                    progressDialog.dismiss();
//                    Toast.makeText(AllincidentlistRActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//
//                }
//
//            }
//        });
//
//
//
//
//    }
//
//    private void allincident() {
//
//        progressDialog.show();
//
//        FirebaseFirestore.getInstance().collection("Report").orderBy("createat", Query.Direction.DESCENDING)
//                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//
//                if (task.isSuccessful()){
//
//
//                    reportModelList = new ArrayList<>();
//
//                    for (DocumentSnapshot doc : task.getResult()){
//
//                        ReportModel reportModel = doc.toObject(ReportModel.class);
//                        reportModelList.add(reportModel);
//
//
//                    }
//
//                    progressDialog.dismiss();
//
//                    AllincidentReporterAdapter adapter = new AllincidentReporterAdapter(AllincidentlistRActivity.this,reportModelList);
//                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AllincidentlistRActivity.this);
//                    recyclerView.setLayoutManager(linearLayoutManager);
//                    recyclerView.setHasFixedSize(true);
//
//                    recyclerView.setAdapter(adapter);
//
//                }
//
//            }
//        });
//
//
//
//    }
//
//
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        if (item.getItemId() == android.R.id.home)
//            finish();
//        Animatoo.animateSwipeLeft(AllincidentlistRActivity.this);
//        return super.onOptionsItemSelected(item);
//    }
//
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        Animatoo.animateSwipeLeft(AllincidentlistRActivity.this);
//    }
//}
