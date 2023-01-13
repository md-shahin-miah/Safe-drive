package com.shahin.drivesafe.Reporter.Fragments;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;
import com.blikoon.qrcodescanner.QrCodeActivity;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.shahin.drivesafe.Admin.DriverlistActivity;
import com.shahin.drivesafe.Admin.ReporterandAdminListActivity;
import com.shahin.drivesafe.Admin.SendSMSActivity;
import com.shahin.drivesafe.Driver.IncidentRulesActivity;
import com.shahin.drivesafe.FirstActivity;
import com.shahin.drivesafe.Models.UserModel;
import com.shahin.drivesafe.R;
import com.shahin.drivesafe.Reporter.AllincidentlistRActivity;
import com.shahin.drivesafe.Reporter.ReportActivity;
import com.shahin.drivesafe.User.UserProfileActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class RHomeFragment extends Fragment  implements View.OnClickListener {


    public RHomeFragment() {
        // Required empty public constructor
    }

    private static final int REQUEST_CODE_QR_SCAN = 101;

    LinearLayout layout1,layout2,layout3,layout4,layout5,layout6,layout7,layout8;
    LinearLayout layout9,layout10,layout11;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_rhome, container, false);



        layout1 = view.findViewById(R.id.scannerlinID);
        layout2 = view.findViewById(R.id.linnidverificationId);
        layout3 = view.findViewById(R.id.smsalertlinID);
        layout4 = view.findViewById(R.id.allincidentlinId);
        layout5 = view.findViewById(R.id.repotrterslinID);
        layout6 = view.findViewById(R.id.adminlistID);
        layout7 = view.findViewById(R.id.bestdriverlinID);
        layout8 = view.findViewById(R.id.alldriverlinID);
        layout9 = view.findViewById(R.id.signoutrepoID);
        layout10 = view.findViewById(R.id.traficruleID);
        layout11 = view.findViewById(R.id.profileid);




        layout1.setOnClickListener(this);
        layout2.setOnClickListener(this);
        layout3.setOnClickListener(this);
        layout4.setOnClickListener(this);
        layout5.setOnClickListener(this);
        layout6.setOnClickListener(this);
        layout7.setOnClickListener(this);
        layout8.setOnClickListener(this);
        layout9.setOnClickListener(this);
        layout10.setOnClickListener(this);
        layout11.setOnClickListener(this);



        return view;





    }

    @Override
    public void onClick(View v) {

        if (v==layout1){

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
        else if (v==layout2){

            Intent intent = new Intent(getActivity(), AllincidentlistRActivity.class);
            intent.putExtra("key","own");
            startActivity(intent);
            Animatoo.animateSwipeRight(getActivity());
        }
        else if (v==layout3){
            startActivity(new Intent(getActivity(), SendSMSActivity.class));
            Animatoo.animateZoom(getActivity());
        }
        else if (v==layout4){

            Intent intent = new Intent(getActivity(), AllincidentlistRActivity.class);
            intent.putExtra("key","all");
            startActivity(intent);
            Animatoo.animateSwipeRight(getActivity());
        }
        else if (v==layout5){
            Intent intent = new Intent(getActivity(), ReporterandAdminListActivity.class);
            intent.putExtra("key","repo");
            startActivity(intent);
            Animatoo.animateSwipeRight(getActivity());

        } else if (v==layout6){
            Intent intent = new Intent(getActivity(), DriverlistActivity.class);
            intent.putExtra("key","dng");
            startActivity(intent);
            Animatoo.animateZoom(getActivity());

        } else if (v==layout7){

            Intent intent = new Intent(getActivity(), DriverlistActivity.class);
            intent.putExtra("key","best");
            startActivity(intent);
            Animatoo.animateZoom(getActivity());
        }
        else if (v==layout8){

            Intent intent = new Intent(getActivity(), DriverlistActivity.class);
            intent.putExtra("key","all");
            startActivity(intent);
            Animatoo.animateZoom(getActivity());
        }

        else if (v==layout9){


            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("Do you want to Sign out?");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            storedata("no");
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(getActivity(), FirstActivity.class));

                            getActivity().finish();
                            Animatoo.animateZoom(getActivity());

                        }
                    });
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.show();


        }
        else if (v==layout10){

            Intent intent = new Intent(getActivity(), IncidentRulesActivity.class);
            startActivity(intent);

            Animatoo.animateSwipeRight(getActivity());

        }
        else if (v==layout11){

            Intent intent = new Intent(getActivity(), UserProfileActivity.class);
            intent.putExtra("key","reporter");
            startActivity(intent);


        }



    }


    private void storedata(String name) {
        SharedPreferences sharedPreferences =getActivity().getSharedPreferences("identy", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", name);
        editor.commit();
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


            if (result.length()!=28 || result.contains("//")){
                pd.dismiss();
                Toast.makeText(getActivity(), "Scan invalid QR code", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getActivity(), "Scan invalid QR code", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        new TTFancyGifDialog.Builder((Activity) getActivity())
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
                                        Intent intent = new Intent(getActivity(), ReportActivity.class);
                                        intent.putExtra("obj",userModel);
                                        Animatoo.animateSlideLeft(getActivity());
                                        startActivity(intent);
                                    }
                                })
                                .OnNegativeClicked(new TTFancyGifDialogListener() {
                                    @Override
                                    public void OnClick() {

                                    }
                                })

                                .build();
                    }
                    else
                    {
                        pd.dismiss();
                        Toast.makeText(getActivity(), "You are in offline", Toast.LENGTH_SHORT).show();
                    }


                }
            });


        }
    }












}

