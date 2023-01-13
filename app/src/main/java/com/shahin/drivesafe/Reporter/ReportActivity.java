package com.shahin.drivesafe.Reporter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import com.shahin.drivesafe.Models.CaseModel;
import com.shahin.drivesafe.Models.NotificationModel;
import com.shahin.drivesafe.Models.ReportModel;
import com.shahin.drivesafe.Models.ReporterDataModel;
import com.shahin.drivesafe.Models.UserModel;
import com.shahin.drivesafe.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReportActivity extends AppCompatActivity implements View.OnClickListener {

    private final static int REQUEST_CODE_PERMISSION_SEND_SMS = 123;
    private static final String TAG = "ReportPageActivity";
    private CheckBox ck1, ck2, ck3, ck4, ck5, ck6, ck7, ck8, ck9, ck10, ck11, ck12, ck13, ck14, ck15, ck16, ck17;

    boolean bck1, bck2, bck3, bck4, bck5, bck6, bck7, bck8, bck9, bck10, bck11, bck12, bck13, bck14, bck15, bck16, bck17;

    Button submit;


    TextView Tname, Taddress, Tphone, Tpoint;

    CircleImageView propic;


    List<CaseModel> caseModelList = new ArrayList<>();

    int lostpoint = 0;

    private EditText editText;

    String des;

    ReporterDataModel reporterDataModel;

    private ProgressDialog progressDialog;


    UserModel userModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);


        if (checkPermission(Manifest.permission.SEND_SMS)) {

        } else {
            ActivityCompat.requestPermissions(ReportActivity.this, new String[]{
                    (Manifest.permission.SEND_SMS)}, REQUEST_CODE_PERMISSION_SEND_SMS);
        }




        bck1 = false;
        bck2 = false;
        bck3 = false;
        bck4 = false;
        bck5 = false;
        bck6 = false;
        bck7 = false;
        bck8 = false;
        bck9 = false;
        bck10 = false;
        bck11 = false;
        bck12 = false;
        bck13 = false;
        bck14 = false;
        bck15 = false;
        bck16 = false;
        bck17 = false;

        ck1 = findViewById(R.id.ck1);
        ck2 = findViewById(R.id.ck2);
        ck3 = findViewById(R.id.ck3);
        ck4 = findViewById(R.id.ck4);
        ck5 = findViewById(R.id.ck5);
        ck6 = findViewById(R.id.ck6);
        ck7 = findViewById(R.id.ck7);
        ck8 = findViewById(R.id.ck8);
        ck9 = findViewById(R.id.ck9);
        ck10 = findViewById(R.id.ck10);
        ck11 = findViewById(R.id.ck11);
        ck12 = findViewById(R.id.ck12);
        ck13 = findViewById(R.id.ck13);
        ck14 = findViewById(R.id.ck14);
        ck15 = findViewById(R.id.ck15);
        ck16 = findViewById(R.id.ck16);
        ck17 = findViewById(R.id.ck17);
        submit = findViewById(R.id.reportsubmitId);
        editText = findViewById(R.id.descriptionIDDD);
        progressDialog = new ProgressDialog(this);

        ck1.setOnClickListener(this);
        ck2.setOnClickListener(this);
        ck3.setOnClickListener(this);
        ck4.setOnClickListener(this);
        ck5.setOnClickListener(this);
        ck6.setOnClickListener(this);
        ck7.setOnClickListener(this);
        ck8.setOnClickListener(this);
        ck9.setOnClickListener(this);
        ck10.setOnClickListener(this);
        ck11.setOnClickListener(this);
        ck12.setOnClickListener(this);
        ck13.setOnClickListener(this);
        ck14.setOnClickListener(this);
        ck15.setOnClickListener(this);
        ck16.setOnClickListener(this);
        ck17.setOnClickListener(this);

        Tname = findViewById(R.id.pendpronameID);
        Taddress = findViewById(R.id.penproaddressId);
        Tphone = findViewById(R.id.pendingphoneId);
        Tpoint = findViewById(R.id.pendpropointId);


        propic = findViewById(R.id.pendproPorpicID);

        Intent i = getIntent();
         userModel = (UserModel) i.getSerializableExtra("obj");

        Tname.setText(userModel.getName());

        Taddress.setText(userModel.getAddress());
        Tphone.setText(userModel.getPhone());
        Tpoint.setText(userModel.getPoint() + "");

        Picasso.get().load(userModel.getImage()).into(propic);


        String ttt = FirebaseAuth.getInstance().getCurrentUser().getUid();


        FirebaseFirestore.getInstance().collection("Reporterinfo")
                .document(ttt).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    reporterDataModel = doc.toObject(ReporterDataModel.class);

                    Log.d(TAG, "onComplete: reportermodel " + reporterDataModel.getName());

                }

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                des = editText.getText().toString().trim();


                if (!isConnected()){
                    Toast.makeText(ReportActivity.this, "You are in offline", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (des.isEmpty()) {

                    editText.setError("Description Required.");
                    return;
                }

                progressDialog.setMessage("Please Wait...!");
                progressDialog.show();

                if (ck1.isChecked()) {
                    CaseModel caseModel = new CaseModel("General Fine", 20);
                    caseModelList.add(caseModel);
                    lostpoint += 20;

                }
                if (ck2.isChecked()) {
                    CaseModel caseModel = new CaseModel("Using Hydraulic Horn", 10);
                    caseModelList.add(caseModel);
                    lostpoint += 10;

                }
                if (ck3.isChecked()) {
                    CaseModel caseModel = new CaseModel("Disobey Police order", 50);
                    caseModelList.add(caseModel);
                    lostpoint += 50;

                }
                if (ck4.isChecked()) {

                    CaseModel caseModel = new CaseModel("Disobeying Red Signal", 50);
                    caseModelList.add(caseModel);
                    lostpoint += 50;
                }
                if (ck5.isChecked()) {
                    CaseModel caseModel = new CaseModel("Careless Driving", 30);
                    caseModelList.add(caseModel);
                    lostpoint += 30;

                }
                if (ck6.isChecked()) {
                    CaseModel caseModel = new CaseModel("Accident Related Fine", 50);
                    caseModelList.add(caseModel);
                    lostpoint += 50;

                }
                if (ck7.isChecked()) {
                    CaseModel caseModel = new CaseModel("Driving without Safety", 30);
                    caseModelList.add(caseModel);
                    lostpoint += 30;

                }
                if (ck8.isChecked()) {
                    CaseModel caseModel = new CaseModel("Black Smoke Emission", 20);
                    caseModelList.add(caseModel);
                    lostpoint += 20;

                }
                if (ck9.isChecked()) {
                    CaseModel caseModel = new CaseModel("Overloading The Car", 50);
                    caseModelList.add(caseModel);
                    lostpoint += 50;

                }
                if (ck10.isChecked()) {

                    CaseModel caseModel = new CaseModel("Driving Without Insurance", 50);
                    caseModelList.add(caseModel);
                    lostpoint += 50;
                }
                if (ck11.isChecked()) {
                    CaseModel caseModel = new CaseModel("Driving Without Permission", 50);
                    caseModelList.add(caseModel);
                    lostpoint += 50;
                }
                if (ck12.isChecked()) {

                    CaseModel caseModel = new CaseModel("Blocking Road Or Public Place", 25);
                    caseModelList.add(caseModel);
                    lostpoint += 25;
                }
                if (ck13.isChecked()) {
                    CaseModel caseModel = new CaseModel("Unauthorized Touch/Use Of Car", 25);
                    caseModelList.add(caseModel);
                    lostpoint += 25;

                }
                if (ck14.isChecked()) {
                    CaseModel caseModel = new CaseModel("Lane Violation", 100);
                    caseModelList.add(caseModel);
                    lostpoint += 100;

                }
                if (ck15.isChecked()) {
                    CaseModel caseModel = new CaseModel("Driving Without valid Registration", 70);
                    caseModelList.add(caseModel);

                    lostpoint += 70;

                }
                if (ck16.isChecked()) {

                    CaseModel caseModel = new CaseModel("Driving Without Fitness", 25);
                    caseModelList.add(caseModel);
                    lostpoint += 25;
                }
                if (ck17.isChecked()) {
                    CaseModel caseModel = new CaseModel("Driving Without Route Permit", 50);
                    caseModelList.add(caseModel);
                    lostpoint += 50;
                }

                ReportModel reportModel = new ReportModel(userModel.getName(), userModel.getPhone(), userModel.getUserId(), des, "null",

                        reporterDataModel.getName(), reporterDataModel.getUserid(), "null", System.currentTimeMillis(), lostpoint,userModel.getImage());

                FirebaseFirestore.getInstance().collection("Report").add(reportModel)
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {

                                if (task.isSuccessful()) {

                                    FirebaseFirestore.getInstance().collection("Report").document(task.getResult().getId())
                                            .update("repoid", task.getResult().getId());

                                    for (int i = 0; i < caseModelList.size(); i++) {
                                        CaseModel caseM = caseModelList.get(i);
                                        FirebaseFirestore.getInstance().collection("Report").document(task.getResult().getId())
                                                .collection("case").add(caseM);
                                    }
                                    FirebaseFirestore.getInstance().collection("approved_Drivers").document(userModel.getUserId())
                                            .update("point", userModel.getPoint()-lostpoint);


                                    NotificationModel notificationModel = new NotificationModel(reportModel.getReportername()+" is Reported against you",System.currentTimeMillis());

                                    FirebaseFirestore.getInstance().collection("Notifications").document(userModel.getUserId())
                                            .collection("msg").add(notificationModel);

                                    NotificationModel notificationModel2 = new NotificationModel(reportModel.getReportername()+" is Reported against " + userModel.getName(),System.currentTimeMillis());

                                    FirebaseFirestore.getInstance().collection("Notificationsadmin").add(notificationModel2);

                                    senddriver();
                                    senddriverowner();

                                    progressDialog.dismiss();
                                    startActivity(new Intent(ReportActivity.this, ReporterMainActivity.class));
                                    finish();

                                }

                            }
                        });
            }
        });

    }

    private boolean checkPermission(String permission) {
        int checkPermission = ContextCompat.checkSelfPermission(this, permission);
        return checkPermission == PackageManager.PERMISSION_GRANTED;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_SEND_SMS:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                }
                break;
        }


    }





    @Override
    public void onClick(View v) {


        if (v == ck1) {
            if (bck1) {
                ck1.setTextColor(this.getResources().getColor(R.color.black));
                bck1 = false;

            } else {
                ck1.setTextColor(this.getResources().getColor(R.color.colorPrimary));
                bck1 = true;

            }

        }
        /////////////////
        if (v == ck2) {
            if (bck2) {
                ck2.setTextColor(this.getResources().getColor(R.color.black));
                bck2 = false;

            } else {
                ck2.setTextColor(this.getResources().getColor(R.color.colorPrimary));
                bck2 = true;

            }
        }

        /////////////////
        if (v == ck3) {
            if (bck3) {
                ck3.setTextColor(this.getResources().getColor(R.color.black));
                bck3 = false;

            } else {
                ck3.setTextColor(this.getResources().getColor(R.color.colorPrimary));
                bck3 = true;

            }

        }

        /////////////////
        if (v == ck4) {
            if (bck4) {
                ck4.setTextColor(this.getResources().getColor(R.color.black));
                bck4 = false;

            } else {
                ck4.setTextColor(this.getResources().getColor(R.color.colorPrimary));
                bck4 = true;
            }

        }
        /////////////////
        if (v == ck5) {
            if (bck5) {
                ck5.setTextColor(this.getResources().getColor(R.color.black));
                bck5 = false;

            } else {
                ck5.setTextColor(this.getResources().getColor(R.color.colorPrimary));
                bck5 = true;

            }

        }
        /////////////////
        if (v == ck6) {
            if (bck6) {
                ck6.setTextColor(this.getResources().getColor(R.color.black));
                bck6 = false;

            } else {
                ck6.setTextColor(this.getResources().getColor(R.color.colorPrimary));
                bck6 = true;

            }

        }
        /////////////////
        if (v == ck7) {
            if (bck7) {
                ck7.setTextColor(this.getResources().getColor(R.color.black));
                bck7 = false;

            } else {
                ck7.setTextColor(this.getResources().getColor(R.color.colorPrimary));
                bck7 = true;

            }

        }
        /////////////////
        if (v == ck8) {
            if (bck8) {
                ck8.setTextColor(this.getResources().getColor(R.color.black));
                bck8 = false;

            } else {
                ck8.setTextColor(this.getResources().getColor(R.color.colorPrimary));
                bck8 = true;

            }

        }
        /////////////////
        if (v == ck9) {
            if (bck9) {
                ck9.setTextColor(this.getResources().getColor(R.color.black));
                bck9 = false;

            } else {
                ck9.setTextColor(this.getResources().getColor(R.color.colorPrimary));
                bck9 = true;

            }

        }
        /////////////////
        if (v == ck10) {
            if (bck10) {
                ck10.setTextColor(this.getResources().getColor(R.color.black));
                bck10 = false;

            } else {
                ck10.setTextColor(this.getResources().getColor(R.color.colorPrimary));
                bck10 = true;

            }

        }
        /////////////////
        if (v == ck11) {
            if (bck11) {
                ck11.setTextColor(this.getResources().getColor(R.color.black));
                bck11 = false;

            } else {
                ck11.setTextColor(this.getResources().getColor(R.color.colorPrimary));
                bck11 = true;

            }

        }
        /////////////////
        if (v == ck12) {
            if (bck12) {
                ck12.setTextColor(this.getResources().getColor(R.color.black));
                bck12 = false;

            } else {
                ck12.setTextColor(this.getResources().getColor(R.color.colorPrimary));
                bck12 = true;

            }

        }
        /////////////////
        if (v == ck13) {
            if (bck13) {
                ck13.setTextColor(this.getResources().getColor(R.color.black));
                bck13 = false;

            } else {
                ck13.setTextColor(this.getResources().getColor(R.color.colorPrimary));
                bck13 = true;

            }

        }
        /////////////////
        if (v == ck14) {
            if (bck14) {
                ck14.setTextColor(this.getResources().getColor(R.color.black));
                bck14 = false;

            } else {
                ck14.setTextColor(this.getResources().getColor(R.color.colorPrimary));
                bck14 = true;

            }

        }
        /////////////////
        if (v == ck15) {
            if (bck15) {
                ck15.setTextColor(this.getResources().getColor(R.color.black));
                bck15 = false;

            } else {
                ck15.setTextColor(this.getResources().getColor(R.color.colorPrimary));
                bck15 = true;

            }

        }
        /////////////////
        if (v == ck16) {
            if (bck16) {
                ck16.setTextColor(this.getResources().getColor(R.color.black));
                bck16 = false;

            } else {
                ck16.setTextColor(this.getResources().getColor(R.color.colorPrimary));
                bck16 = true;

            }

        }
        /////////////////
        if (v == ck17) {
            if (bck17) {
                ck17.setTextColor(this.getResources().getColor(R.color.black));
                bck17 = false;

            } else {
                ck17.setTextColor(this.getResources().getColor(R.color.colorPrimary));
                bck17 = true;

            }

        }


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



    private  void  senddriver(){
        String phone = userModel.getPhone();
        String msg = "it’s informed that a case has been filed against you today";

        SmsManager manager = SmsManager.getDefault();
        StringBuilder sb = new StringBuilder(msg);
        manager.sendTextMessage(phone, null, sb.toString(), null, null);


    }


    private  void  senddriverowner(){
        String phone = userModel.getOwner_phone();
        String msg = "Its informed that a case has been filed against your vehicle";

        SmsManager manager = SmsManager.getDefault();
        StringBuilder sb = new StringBuilder(msg);
        manager.sendTextMessage(phone, null, sb.toString(), null, null);




    }


}
