package com.shahin.drivesafe.Admin.Fragments;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.blikoon.qrcodescanner.QrCodeActivity;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.shahin.drivesafe.Admin.CreateAdminActivity;
import com.shahin.drivesafe.Admin.CreateReporterActivity;
import com.shahin.drivesafe.Admin.DriverlistActivity;
import com.shahin.drivesafe.Admin.NidVerifiationActivity;
import com.shahin.drivesafe.Admin.ReporterandAdminListActivity;
import com.shahin.drivesafe.Admin.RequestDriverActivity;
import com.shahin.drivesafe.Admin.SendSMSActivity;
import com.shahin.drivesafe.Driver.DriverprofileDActivity;
import com.shahin.drivesafe.Models.UserModel;
import com.shahin.drivesafe.R;
//import com.xd.drivesafe.Reporter.AllincidentlistRActivity;



public class AHomeFragment extends Fragment implements View.OnClickListener {
    public AHomeFragment() {
    }

    private static final int REQUEST_CODE_QR_SCAN = 101;
    private final String LOGTAG = "QRCScanner-MainActivity";

    LinearLayout layout1, layout2, layout3, layout4, layout5, layout6, layout7, layout8, layout9, layout10, layout11, layout12;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ahome, container, false);


        layout1 = view.findViewById(R.id.scannerlinID1);
         layout2 = view.findViewById(R.id.linnidverificationId1);
        layout3 = view.findViewById(R.id.smsalertlinID1);
        layout4 = view.findViewById(R.id.allincidentlinId1);
        layout5 = view.findViewById(R.id.repotrterslinID1);
        layout6 = view.findViewById(R.id.adminlistID1);
        layout7 = view.findViewById(R.id.bestdriverlinID1);
        layout8 = view.findViewById(R.id.createadminlinID1);
        layout9 = view.findViewById(R.id.alldriverlinID1);
        layout10 = view.findViewById(R.id.createreporterlinID1);
        layout11 = view.findViewById(R.id.pendingdriversID1);
        layout12 = view.findViewById(R.id.rejectdriverliID1);



        layout1.setOnClickListener(AHomeFragment.this);
        layout2.setOnClickListener(AHomeFragment.this);
        layout3.setOnClickListener(AHomeFragment.this);
        layout4.setOnClickListener(AHomeFragment.this);
        layout5.setOnClickListener(AHomeFragment.this);
        layout6.setOnClickListener(AHomeFragment.this);
        layout7.setOnClickListener(AHomeFragment.this);
        layout8.setOnClickListener(AHomeFragment.this);
        layout9.setOnClickListener(AHomeFragment.this);
        layout10.setOnClickListener(AHomeFragment.this);
        layout11.setOnClickListener(AHomeFragment.this);
        layout12.setOnClickListener(AHomeFragment.this);


        view.findViewById(R.id.cardddddddddddddddd3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Snackbar.make(getView(),"Coming Soon" , Snackbar.LENGTH_LONG).show();

            }
        });


        return view;

    }

    @Override
    public void onClick(View v) {

        if (v == layout1) {
            RxPermissions rxPermissions = new RxPermissions(getActivity());
            rxPermissions
                    .request(Manifest.permission.CAMERA) // ask single or multiple permission once
                    .subscribe(granted -> {
                        if (granted) {
                            Intent i = new Intent(getActivity(), QrCodeActivity.class);
                            startActivityForResult(i, REQUEST_CODE_QR_SCAN);
                        } else {
                            // At least one permission is denied
                        }
                    });

      }
        else if (v == layout2) {
            startActivity(new Intent(getActivity(), NidVerifiationActivity.class));
            Animatoo.animateZoom(getActivity());

        }

        else if (v == layout3) {

            startActivity(new Intent(getActivity(), SendSMSActivity.class));
            Animatoo.animateZoom(getActivity());

        } else if (v == layout4) {

//            Intent intent = new Intent(getActivity(), AllincidentlistRActivity.class);
//            intent.putExtra("key","all");
//            startActivity(intent);
//            Animatoo.animateSwipeLeft(getActivity());

        } else if (v == layout5) {
            Intent intent = new Intent(getActivity(), ReporterandAdminListActivity.class);
            intent.putExtra("key","repo");
            startActivity(intent);
            Animatoo.animateSplit(getActivity());
        } else if (v == layout6) {

            Intent intent = new Intent(getActivity(), ReporterandAdminListActivity.class);
            intent.putExtra("key","admin");
            startActivity(intent);
            Animatoo.animateInAndOut(getActivity());

        } else if (v == layout7) {
            Intent intent = new Intent(getActivity(), DriverlistActivity.class);
            intent.putExtra("key","best");
            startActivity(intent);

            Animatoo.animateZoom(getActivity());
        } else if (v == layout8) {
            startActivity(new Intent(getActivity(), CreateAdminActivity.class));
            Animatoo.animateSlideUp(getActivity());

        } else if (v == layout9) {

            Intent intent = new Intent(getActivity(), DriverlistActivity.class);
            intent.putExtra("key","all");
            startActivity(intent);

            Animatoo.animateSwipeRight(getActivity());

        } else if (v == layout10) {

            startActivity(new Intent(getActivity(), CreateReporterActivity.class));
            Animatoo.animateZoom(getActivity());
        } else if (v == layout11) {

            Intent intent = new Intent(getActivity(), RequestDriverActivity.class);
            intent.putExtra("key","app");
            startActivity(intent);

            Animatoo.animateSlideUp(getActivity());

        } else if (v == layout12) {
            Intent intent = new Intent(getActivity(), RequestDriverActivity.class);
            intent.putExtra("key","reject");
            startActivity(intent);
            Animatoo.animateSwipeLeft(getActivity());

        }

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
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
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


            ProgressDialog pd = new ProgressDialog(getActivity());
            pd.show();


            if (result.length() != 28 || result.contains("//")) {
                pd.dismiss();
                Toast.makeText(getActivity(), "Scan invalid QR code", Toast.LENGTH_SHORT).show();
                return;
            }


            FirebaseFirestore.getInstance().collection("approved_Drivers")
                    .document(result).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {


                    if (task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();
                        UserModel userModel = doc.toObject(UserModel.class);
                        pd.dismiss();

                        if (userModel == null) {
                            Toast.makeText(getActivity(), "Scan invalid QR code", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Intent intent = new Intent(getActivity(), DriverprofileDActivity.class);
                        intent.putExtra("key", userModel.getUserId());
                        Animatoo.animateSlideLeft(getActivity());
                        startActivity(intent);

                    } else {
                        pd.dismiss();
                        Toast.makeText(getActivity(), "You are in offline", Toast.LENGTH_SHORT).show();
                    }


                }
            });


        }
    }

}