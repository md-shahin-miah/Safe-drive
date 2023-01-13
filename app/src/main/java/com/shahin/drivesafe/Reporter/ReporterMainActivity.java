package com.shahin.drivesafe.Reporter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;
import com.blikoon.qrcodescanner.QrCodeActivity;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.irfaan008.irbottomnavigation.SpaceItem;
import com.irfaan008.irbottomnavigation.SpaceNavigationView;
import com.irfaan008.irbottomnavigation.SpaceOnClickListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.shahin.drivesafe.Driver.DriverprofileDActivity;
import com.shahin.drivesafe.Models.UserModel;
import com.shahin.drivesafe.R;
import com.shahin.drivesafe.Reporter.Fragments.RHomeFragment;
import com.shahin.drivesafe.Reporter.Fragments.RNotificationFragment;

public class ReporterMainActivity extends AppCompatActivity  {
    private static final int REQUEST_CODE_QR_SCAN = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporter_main);


        getSupportActionBar().hide();
        SpaceNavigationView spaceNavigationView = findViewById(R.id.space);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.addSpaceItem(new SpaceItem("HOME", R.drawable.ic_home));
        spaceNavigationView.addSpaceItem(new SpaceItem("REPORT", R.drawable.ic_report_black_24dp));

      //  spaceNavigationView.showIconOnly();


        setFragment(new RHomeFragment());


        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                RxPermissions rxPermissions = new RxPermissions(ReporterMainActivity.this);
                rxPermissions
                        .request(Manifest.permission.CAMERA) // ask single or multiple permission once
                        .subscribe(granted -> {
                            if (granted) {
                                Intent i = new Intent(ReporterMainActivity.this, QrCodeActivity.class);
                                startActivityForResult(i, REQUEST_CODE_QR_SCAN);
                            } else {
                                // At least one permission is denied
                            }
                        });

            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                if (itemIndex == 0) {
                    setFragment(new RHomeFragment());
                } else if (itemIndex == 1) {
                    setFragment(new RNotificationFragment());
                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                //  Toast.makeText(ReporterMainActivity.this, itemIndex + "rrrrrrrrrrrrrrr " + itemName, Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment, fragment.getClass().getSimpleName());
        fragmentTransaction.commit();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {

            if (data == null)
                return;
            //Getting the passed result
            String result = data.getStringExtra("com.blikoon.qrcodescanner.error_decoding_image");
            if (result != null) {
                AlertDialog alertDialog = new AlertDialog.Builder(ReporterMainActivity.this).create();
                alertDialog.setTitle("Scan Error");
                alertDialog.setMessage("QR Code could not be scanned");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
            return;

        }
        if (requestCode == REQUEST_CODE_QR_SCAN) {
            if (data == null)
                return;
            //Getting the passed result
            String result = data.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult");


            ProgressDialog pd = new ProgressDialog(ReporterMainActivity.this);
            pd.show();


            if (result.length()!=28 || result.contains("//")){
                pd.dismiss();
                Toast.makeText(ReporterMainActivity.this, "Scan invalid QR code", Toast.LENGTH_SHORT).show();
                return;
            }

            FirebaseFirestore.getInstance().collection("approved_Drivers")
                    .document(result).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {


                    if (task.isSuccessful()){
                        DocumentSnapshot doc = task.getResult();
                        UserModel userModel = doc.toObject(UserModel.class);
                        pd.dismiss();

                        if (userModel==null){
                            Toast.makeText(ReporterMainActivity.this, "Scan invalid QR code", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        new TTFancyGifDialog.Builder((Activity) ReporterMainActivity.this)
                                .setTitle(userModel.getName())
                                .setMessage("Number plate: " +userModel.getNumber_plate())
                                .setPositiveBtnText("Report")
                                .setPositiveBtnBackground("#c1272d")
                                .setNegativeBtnText("View Profile")
                                .setNegativeBtnBackground("#22b573")
                                .setGifResource(R.drawable.gif1)
                                .isCancellable(true)
                                .OnPositiveClicked(new TTFancyGifDialogListener() {
                                    @Override
                                    public void OnClick() {
                                        Intent intent = new Intent(ReporterMainActivity.this,ReportActivity.class);
                                        intent.putExtra("obj",userModel);
                                        Animatoo.animateSlideLeft(ReporterMainActivity.this);
                                        startActivity(intent);
                                    }
                                })
                                .OnNegativeClicked(new TTFancyGifDialogListener() {
                                    @Override
                                    public void OnClick() {

                                        Intent intent = new Intent(ReporterMainActivity.this, DriverprofileDActivity.class);
                                        intent.putExtra("key",userModel.getUserId());
                                        Animatoo.animateSlideLeft(ReporterMainActivity.this);
                                        startActivity(intent);

                                    }
                                })

                                .build();
                    }
                    else
                    {
                        pd.dismiss();
                        Toast.makeText(ReporterMainActivity.this, "You are in offline", Toast.LENGTH_SHORT).show();
                    }


                }
            });


        }
    }


}

