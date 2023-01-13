package com.shahin.drivesafe.Driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shahin.drivesafe.Models.UserModel;
import com.shahin.drivesafe.R;

public class PersonalinformationActivity extends AppCompatActivity {



    private static final String TAG = "ApprovepageActivity";
    private TextView Ename, Enid, Elicense, Eaddress, Ephone, Eownername, Eowneradress, Eownerphone, Enumberplate, Eemail, Ebirthdate,Epoint;

    UserModel userModel ;


    ProgressDialog progressDialog2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalinformation);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Personal Information");


        Ename = findViewById(R.id.pendnamelID);
        Enid = findViewById(R.id.pendnidId);
        Elicense = findViewById(R.id.pendlicenseID);
        Eaddress = findViewById(R.id.pendaddresslID);
        Ephone = findViewById(R.id.pendphoneId);
        Eownername = findViewById(R.id.pendeownernameID);
        Eowneradress = findViewById(R.id.pendowneraddressID);
        Eownerphone = findViewById(R.id.pendownerphone);
        Enumberplate = findViewById(R.id.pendnumberplateId);
        Eemail = findViewById(R.id.pendemailID);
        Epoint = findViewById(R.id.pendpropointId);
        Ebirthdate = findViewById(R.id.pendbirthdayId);


        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseFirestore.getInstance().collection("approved_Drivers").document(id)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()){

                    DocumentSnapshot doc = task.getResult();

                    userModel = doc.toObject(UserModel.class);
                    Ename.setText(userModel.getName());
                    Enid.setText(userModel.getNid());
                    Elicense.setText(userModel.getLicense());
                    Eaddress.setText(userModel.getAddress());
                    Ephone.setText(userModel.getPhone());
                    Eownername.setText(userModel.getOwner_name());
                    Eowneradress.setText(userModel.getOwner_address());
                    Eownerphone.setText(userModel.getOwner_phone());
                    Enumberplate.setText(userModel.getNumber_plate());
                    Eemail.setText(userModel.getEmail());

                    Ebirthdate.setText(userModel.getBirthdate());

                }

            }
        });

    }


    private void PopUpWindow(){
        DisplayMetrics displayMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width=displayMetrics.widthPixels;
        int height=displayMetrics.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.6));
        WindowManager.LayoutParams params=getWindow().getAttributes();
        params.gravity= Gravity.CENTER;
        params.x=0;
        params.y=-20;
        getWindow().setAttributes(params);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home)
            finish();
        Animatoo.animateZoom(PersonalinformationActivity.this);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateZoom(PersonalinformationActivity.this);
    }
}
