package com.shahin.drivesafe.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shahin.drivesafe.Models.NormaluserModel;
import com.shahin.drivesafe.Models.ReporterDataModel;
import com.shahin.drivesafe.R;

public class UserProfileActivity extends AppCompatActivity {


    TextView name ,email;

    TextView signout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        getSupportActionBar().hide();

        name = findViewById(R.id.user_profile_name);
        email = findViewById(R.id.user_profile_short_bio);
        signout = findViewById(R.id.signoutTextID);



        Intent intent = getIntent();

        String val = intent.getStringExtra("key");


        if (val.equals("user")){

            showuser();

        }
        else  if (val.equals("reporter")){

            showreporter();
        }



        PopUpWindow();







    }

    private void showreporter() {


        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore.getInstance().collection("Reporterinfo").document(id)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()){

                    DocumentSnapshot doc  = task.getResult();
                    ReporterDataModel normaluserModel = doc.toObject(ReporterDataModel.class);

                    name.setText(normaluserModel.getName());
                    email.setText(normaluserModel.getEmail());

                }

            }
        });

    }

    private void showuser() {


        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore.getInstance().collection("NormalUserinfo").document(id)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()){

                    DocumentSnapshot doc  = task.getResult();
                    NormaluserModel normaluserModel = doc.toObject(NormaluserModel.class);

                    name.setText(normaluserModel.getUsername());
                    email.setText(normaluserModel.getEmail());



                }

            }
        });

    }


    private void PopUpWindow(){
        DisplayMetrics displayMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width=displayMetrics.widthPixels;
        int height=displayMetrics.heightPixels;
        getWindow().setLayout((int)(width*.9),(int)(height*.6));
        WindowManager.LayoutParams params=getWindow().getAttributes();
        params.gravity= Gravity.CENTER;
        params.x=0;
        params.y=-20;
        getWindow().setAttributes(params);
    }


}
