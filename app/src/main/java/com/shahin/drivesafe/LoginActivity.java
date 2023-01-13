package com.shahin.drivesafe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.FirebaseFirestoreException;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.shahin.drivesafe.Admin.AdminMainActivity;
import com.shahin.drivesafe.Driver.MainDriverActivity;
import com.shahin.drivesafe.Driver.RegistrationActivity;
import com.shahin.drivesafe.Reporter.ReporterMainActivity;
import com.shahin.drivesafe.User.UserMainActivity;
import com.shahin.drivesafe.User.UserRegActivity;

public class LoginActivity extends AppCompatActivity {


    private Button button;
    MaterialEditText email,pass;


    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        getSupportActionBar().hide();


        email = findViewById(R.id.loginemailId);
        pass = findViewById(R.id.loginPassId);
        button = findViewById(R.id.loginId);
        progressDialog = new ProgressDialog(this);


        findViewById(R.id.bcamedriverID).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
                Animatoo.animateSwipeLeft(LoginActivity.this);
            }
        });

        findViewById(R.id.texttttt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, UserRegActivity.class));
                Animatoo.animateSwipeRight(LoginActivity.this);

            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String Email = email.getText().toString().trim();
                final String Pass = pass.getText().toString().trim();

                if (Email.isEmpty()){
                    email.setError("Email Required");
                }
                else if (Pass.isEmpty()){
                    pass.setError("Password Required");
                }
                else {
                    progressDialog.setMessage("please wait...");
                    progressDialog.show();

                    firebaseAuth = FirebaseAuth.getInstance();

                    firebaseAuth.signInWithEmailAndPassword(Email,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){

                             String user = FirebaseAuth.getInstance().getCurrentUser().getUid();

                                FirebaseFirestore.getInstance().collection("idnty").document(user).get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                                String name = task.getResult().get("name").toString();

                                                if (name.equals("user")){
                                                    progressDialog.dismiss();
                                                    storedata(name);
                                                    startActivity(new Intent(LoginActivity.this, UserMainActivity.class));
                                                    Animatoo.animateZoom(LoginActivity.this);
                                                    finish();
                                                }
                                                if (name.equals("admin")){
                                                    progressDialog.dismiss();
                                                    storedata(name);
                                                    startActivity(new Intent(LoginActivity.this, AdminMainActivity.class));
                                                    Animatoo.animateZoom(LoginActivity.this);
                                                    finish();
                                                } if (name.equals("reporter")){
                                                    progressDialog.dismiss();
                                                    storedata(name);
                                                    startActivity(new Intent(LoginActivity.this, ReporterMainActivity.class));
                                                    Animatoo.animateZoom(LoginActivity.this);
                                                    finish();
                                                } if (name.equals("driver")){

                                                    FirebaseFirestore.getInstance().collection("approved_Drivers").document(user)
                                                            .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                                                @Override
                                                                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                                                                    if (documentSnapshot.exists()){

                                                                        progressDialog.dismiss();
                                                                        storedata(name);
                                                                        startActivity(new Intent(LoginActivity.this, MainDriverActivity.class));
                                                                        Animatoo.animateZoom(LoginActivity.this);
                                                                        finish();
                                                                    }
                                                                    else {
                                                                        FirebaseAuth.getInstance().signOut();
                                                                        progressDialog.dismiss();
                                                                        Toast.makeText(LoginActivity.this, "Wait for Admin approval", Toast.LENGTH_SHORT).show();

                                                                    }
                                                                }
                                                            });


                                                }
                                            }
                                        });

                            }
                            else
                            {
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });


                }
            }
        });



        findViewById(R.id.forgetpssId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this,ForgetPassActivity.class));

            }
        });



    }


    private void storedata(String name) {
        SharedPreferences sharedPreferences = getSharedPreferences("identy", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", name);
        editor.commit();
    }


}
