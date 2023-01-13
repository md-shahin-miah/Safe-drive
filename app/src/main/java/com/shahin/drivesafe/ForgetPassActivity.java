package com.shahin.drivesafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassActivity extends AppCompatActivity {



    private EditText editText;
    private Button button;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);


        editText = findViewById(R.id.resetemailId);
        button = findViewById(R.id.resetbuttonId);
        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String em = editText.getText().toString().trim();
                if (em.isEmpty()){
                    editText.setError("Email Required");
                    return;
                }
                else
                {
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                    firebaseAuth.sendPasswordResetEmail(em).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()){
                                Toast.makeText(ForgetPassActivity.this, "Please check your Email. if you want to reset your password..", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(ForgetPassActivity.this,LoginActivity.class));
                                finish();
                            }
                            else
                            {
                                Toast.makeText(ForgetPassActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

            }
        });






    }
}
