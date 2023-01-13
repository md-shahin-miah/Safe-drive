package com.shahin.drivesafe.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.shahin.drivesafe.LoginActivity;
import com.shahin.drivesafe.Models.Identity;
import com.shahin.drivesafe.Models.NormaluserModel;
import com.shahin.drivesafe.R;

public class UserRegActivity extends AppCompatActivity {

    private MaterialEditText Eusername,Eemail,Epass;

    private Button button;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reg);


        getSupportActionBar().hide();
        Eusername = findViewById(R.id.createusernamerepoID);
        Eemail = findViewById(R.id.createemailrepoID);
        Epass = findViewById(R.id.createpassrepoID);

        progressDialog = new ProgressDialog(this);

        button = findViewById(R.id.createbuttonrepoID);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = Eusername.getText().toString().trim();
                String email = Eemail.getText().toString().trim();
                String pass = Epass.getText().toString().trim();

                if (!isConnected()){
                    Toast.makeText(UserRegActivity.this, "You are in offline", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (username.isEmpty()){
                    Eusername.setError("Username Required");
                    return;
                }
                if (email.isEmpty()){
                    Eemail.setError("Email Required");
                    return;
                }

                if (pass.isEmpty()){

                    Epass.setError("Password required");
                    return;
                }


                progressDialog.setMessage("Please Wait...!");
                progressDialog.show();

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                String userid = task.getResult().getUser().getUid();

                                NormaluserModel normaluserModel = new NormaluserModel(username,email,userid);

                                FirebaseFirestore.getInstance().collection("NormalUserinfo")
                                        .document(userid).set(normaluserModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        progressDialog.dismiss();
                                        Identity identity = new Identity("user");

                                        FirebaseFirestore.getInstance().collection("idnty").document(userid).set(identity);

                                        startActivity(new Intent(UserRegActivity.this, LoginActivity.class));
                                        finish();

                                    }
                                });

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(UserRegActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });






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




    @Override
    public void onBackPressed() {

        super.onBackPressed();
        Animatoo.animateSwipeLeft(UserRegActivity.this);
    }
}


