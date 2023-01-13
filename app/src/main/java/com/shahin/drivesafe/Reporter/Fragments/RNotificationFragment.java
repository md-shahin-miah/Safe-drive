package com.shahin.drivesafe.Reporter.Fragments;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;
import com.blikoon.qrcodescanner.QrCodeActivity;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.shahin.drivesafe.Adapters.UserDriversAdapter;
import com.shahin.drivesafe.Driver.DriverprofileDActivity;
import com.shahin.drivesafe.Models.UserModel;
import com.shahin.drivesafe.R;
import com.shahin.drivesafe.Reporter.ReportActivity;

import java.util.ArrayList;
import java.util.List;

import static com.google.firebase.remoteconfig.FirebaseRemoteConfig.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class RNotificationFragment extends Fragment {


    public RNotificationFragment() {
        // Required empty public constructor
    }


    RelativeLayout scannerRely;

    private static final int REQUEST_CODE_QR_SCAN = 101;
    private final String LOGTAG = "QRCScanner-MainActivity";

    CardView searchcard;


    List<UserModel> userModelList = new ArrayList<>();

    EditText editText;


    UserDriversAdapter userDriversAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_rnotification, container, false);

        searchcard = view.findViewById(R.id.cardsearch1);
        scannerRely = view.findViewById(R.id.scannerrelyID);
        editText = view.findViewById(R.id.searchesittextID);


        scannerRely.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        });

        FirebaseFirestore.getInstance().collection("approved_Drivers")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {


                if (task.isSuccessful()) {

                    userModelList.clear();
                    for (DocumentSnapshot doc : task.getResult()) {

                        UserModel userModel = doc.toObject(UserModel.class);

                        userModelList.add(userModel);
                    }

                    userDriversAdapter = new UserDriversAdapter(getActivity(), userModelList);


                }


            }
        });

        searchcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String qu = editText.getText().toString().trim();

                List<UserModel> newList = filter(userModelList, qu);
                userDriversAdapter.setfilter(newList);

                if (qu.isEmpty()){
                    Log.d(TAG, "onClick: ");
                    return;
                }

                if (newList.size() != 0) {
//                    Log.d(TAG, "onQueryTextChange: " + newList.get(0).getPhone());
//                    Intent intent = new Intent(getActivity(), DriverprofileDActivity.class);
//                    intent.putExtra("key", newList.get(0).getUserId());
//                    Animatoo.animateSlideLeft(getActivity());
//                    startActivity(intent);
//                    Animatoo.animateInAndOut(getActivity());

                    UserModel userModel = newList.get(0);

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

                                    Intent intent = new Intent(getActivity(), DriverprofileDActivity.class);
                                    intent.putExtra("key",userModel.getUserId());
                                    Animatoo.animateSlideLeft(getActivity());
                                    startActivity(intent);

                                }
                            })

                            .build();









                } else {
                    Toast.makeText(getActivity(), "No result found.", Toast.LENGTH_SHORT).show();
                }


            }
        });



        return view;
    }



    private List<UserModel> filter(List<UserModel> pd, String query) {


        query = query.toLowerCase().trim();

        List<UserModel> filterProductData = new ArrayList<>();
        for (UserModel prodata : pd) {
            if (prodata != null) {
                final String text = prodata.getName().toLowerCase();
                final String text2 = prodata.getAddress().toLowerCase();
                final String text3 = prodata.getLicense().toLowerCase();
                final String text4 = prodata.getPhone().toLowerCase();

                if (text.contains(query)) {
                    filterProductData.add(prodata);
                }
                if (text2.contains(query)) {
                    filterProductData.add(prodata);
                }
                if (text3.contains(query)) {
                    filterProductData.add(prodata);
                }
                if (text4.contains(query)) {
                    filterProductData.add(prodata);
                }


            } else {
                Log.d(TAG, "filter: ");
            }

        }
        return filterProductData;
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

                                        Intent intent = new Intent(getActivity(), DriverprofileDActivity.class);
                                        intent.putExtra("key",userModel.getUserId());
                                        Animatoo.animateSlideLeft(getActivity());
                                        startActivity(intent);

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
