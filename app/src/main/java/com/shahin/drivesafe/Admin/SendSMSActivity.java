package com.shahin.drivesafe.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.shahin.drivesafe.R;

import java.util.ArrayList;

public class SendSMSActivity extends AppCompatActivity {


    private Button btnSendSMS;
    private EditText etPhoneNum, etMessage;
    private final static int REQUEST_CODE_PERMISSION_SEND_SMS = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("SMS Alert");


        etPhoneNum = findViewById(R.id.etPhoneNum);
        etMessage =findViewById(R.id.etMsg);
        btnSendSMS =  findViewById(R.id.btnSendSMS);

        btnSendSMS.setEnabled(false);

        // setting List View for Messages

        if (checkPermission(Manifest.permission.SEND_SMS)) {
            btnSendSMS.setEnabled(true);
        } else {
            ActivityCompat.requestPermissions(SendSMSActivity.this, new String[]{
                    (Manifest.permission.SEND_SMS)}, REQUEST_CODE_PERMISSION_SEND_SMS);
        }

        btnSendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = etMessage.getText().toString();
                String phone = etPhoneNum.getText().toString();
                if (phone.length()!=11){

                    etPhoneNum.setError("Enter Valid Phone Number");
                    return;
                }
                else {

                    if ( phone.startsWith("017")){
                    }else if (phone.startsWith("018")){

                    }else if (phone.startsWith("016")){

                    }else if (phone.startsWith("019")){

                    }else if (phone.startsWith("013")){

                    }else if (phone.startsWith("015")){

                    } else  if (phone.startsWith("014")){

                    }
                    else {
                        etPhoneNum.setError("Enter Valid Phone Number");
                        return;
                    }

                }

                if (msg.isEmpty()){

                    etMessage.setError("Message field must not empty");
                    return;
                }

                if (msg.length()>60){
                    etMessage.setError("Message  must content only 60 character ");
                    return;
                }


              //  android.telephony.SmsManager sms=android.telephony.SmsManager.getDefault();
              //  SmsManager manager = SmsManager.getDefault();
                StringBuilder sb = new StringBuilder(msg);

            //    sms.sendTextMessage(phone, null, sb.toString(), null, null);
//
//
//                SmsManager smsMan = SmsManager.getDefault();
//                smsMan.sendTextMessage(phone, null, msg, null, null);


                SmsManager sms = SmsManager.getDefault();
                ArrayList<String> parts = sms.divideMessage(msg);
                sms.sendMultipartTextMessage(phone, null, parts, null, null);




                Toast.makeText(SendSMSActivity.this,
                        "SMS send to " + phone, Toast.LENGTH_LONG).show();


                etPhoneNum.setText("");
                etMessage.setText("");

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
                    btnSendSMS.setEnabled(true);
                }
                break;
        }


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home)
            finish();

        Animatoo.animateZoom(SendSMSActivity.this);
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateZoom(SendSMSActivity.this);
    }
}


