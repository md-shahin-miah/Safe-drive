package com.shahin.drivesafe.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.squareup.picasso.Picasso;
import com.shahin.drivesafe.Models.UserModel;
import com.shahin.drivesafe.R;

import java.io.ByteArrayOutputStream;

public class ApprovepageActivity extends AppCompatActivity {

    private static final String TAG = "ApprovepageActivity";
    private int STORAGE_PERMISSION_CODE = 1;
    private TextView Ename, Enid, Elicense, Eaddress, Ephone, Eownername, Eowneradress, Eownerphone, Enumberplate, Eemail, Ebirthdate,Epoint;
    private ImageView photo,coverpic;

    private  TextView reject,approve;

    UserModel userModel ;


    Bitmap bitmap;
    public final static int QRcodeWidth = 500;
    Uri qruri;

    String QrcodeUri;

    String val;
    ProgressDialog progressDialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approvepage);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        Ename = findViewById(R.id.pendpronameID1);
        Enid = findViewById(R.id.pendnidId1);
        Elicense = findViewById(R.id.pendlicenseID1);
        Eaddress = findViewById(R.id.penproaddressId1);
        Ephone = findViewById(R.id.pendphoneId1);
        Eownername = findViewById(R.id.pendeownernameID1);
        Eowneradress = findViewById(R.id.pendowneraddressID1);
        Eownerphone = findViewById(R.id.pendownerphone1);
        Enumberplate = findViewById(R.id.pendnumberplateId1);
        Eemail = findViewById(R.id.pendemailID1);
        Epoint = findViewById(R.id.pendpropointId1);
        photo = findViewById(R.id.pendproPorpicID1);
        coverpic = findViewById(R.id.pendcoverpicId1);
        Ebirthdate = findViewById(R.id.pendbirthdayId1);
        approve = findViewById(R.id.pendaproveeId1);
        reject = findViewById(R.id.pendrejectid1);

        progressDialog2 = new ProgressDialog(this);

        Intent i = getIntent();
        userModel = (UserModel) i.getSerializableExtra("obj");

         val = i.getStringExtra("check");


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
        Epoint.setText(String.valueOf(userModel.getPoint()));
        Ebirthdate.setText(userModel.getBirthdate());

        Picasso.get().load(userModel.getImage()).into(photo);
        Picasso.get().load(userModel.getImage()).into(coverpic);


        if (val.equals("reject")){
            reject.setVisibility(View.GONE);
        }


        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!isConnected()){
                    Toast.makeText(ApprovepageActivity.this, "You are in offline", Toast.LENGTH_SHORT).show();
                    return;
                }


                new TTFancyGifDialog.Builder((Activity) ApprovepageActivity.this)
                        .setTitle("Warning !")
                        .setMessage("Are you Sure to Rejected?")
                        .setPositiveBtnText("Reject")
                        .setPositiveBtnBackground("#c1272d")
                        .setNegativeBtnText("Cancel")
                        .setNegativeBtnBackground("#22b573")
                        .setGifResource(R.drawable.gif1)      //pass your gif, png or jpg
                        .isCancellable(true)
                        .OnPositiveClicked(new TTFancyGifDialogListener() {
                            @Override
                            public void OnClick() {

                                final ProgressDialog progressDialog =  new ProgressDialog(ApprovepageActivity.this);
                                progressDialog.setMessage("please wait...");
                                progressDialog.show();

                                FirebaseFirestore.getInstance().collection("Rejected_Drivers").document(userModel.getUserId()).set(userModel)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                FirebaseFirestore.getInstance().collection("temp_userInfo").document(userModel.getUserId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                        progressDialog.dismiss();
                                                        Toast.makeText(ApprovepageActivity.this, "success", Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(ApprovepageActivity.this, AdminMainActivity.class));
                                                        Animatoo.animateSlideLeft(ApprovepageActivity.this);
                                                        finish();

                                                    }
                                                });

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        progressDialog.dismiss();

                                    }
                                });



                            }
                        })
                        .OnNegativeClicked(new TTFancyGifDialogListener() {
                            @Override
                            public void OnClick() {



                            }
                        })

                        .build();


            }
        });

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isConnected()){
                    Toast.makeText(ApprovepageActivity.this, "You are in offline", Toast.LENGTH_SHORT).show();
                    return;
                }



                new TTFancyGifDialog.Builder((Activity) ApprovepageActivity.this)
                        .setTitle("Warning !")
                        .setMessage("Are you Sure to Approved?")
                        .setPositiveBtnText("Approve")
                        .setPositiveBtnBackground("#22b573")
                        .setNegativeBtnText("Cancel")
                        .setNegativeBtnBackground("#c1272d")
                        .setGifResource(R.drawable.gif1)      //pass your gif, png or jpg
                        .isCancellable(true)
                        .OnPositiveClicked(new TTFancyGifDialogListener() {
                            @Override
                            public void OnClick() {


                                progressDialog2.setMessage("please wait...");
                                progressDialog2.show();

                                if (ContextCompat.checkSelfPermission(ApprovepageActivity.this,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                                    bitmap= null;
                                    try {
                                        bitmap = TextToImageEncode(userModel.getUserId());

                                        qruri = getImageUri(ApprovepageActivity.this,bitmap);

                                        Log.d(TAG, "OnClick: qrcode " + qruri);

                                        uploadqrcode ();

                                    } catch (WriterException e) {
                                        progressDialog2.dismiss();
                                        Toast.makeText(ApprovepageActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();  e.printStackTrace();
                                        e.printStackTrace();
                                    }

                                } else {
                                    progressDialog2.dismiss();
                                    requestStoragePermission();

                                }

                            }
                        })
                        .OnNegativeClicked(new TTFancyGifDialogListener() {
                            @Override
                            public void OnClick() {



                            }
                        })

                        .build();


            }
        });







    }




    private  void  uploadqrcode (){


        final StorageReference st =   FirebaseStorage.getInstance().getReference("userinfoQr").child(System.currentTimeMillis()+"_");

        st.putFile(qruri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                st.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {



                        UserModel usermodel2 = new UserModel(userModel.getName(), userModel.getNid(), userModel.getLicense(),
                                userModel.getAddress(),  userModel.getPhone(),
                                userModel.getOwner_name(),  userModel.getOwner_address(),  userModel.getOwner_phone(),
                                userModel.getNumber_plate(),  userModel.getEmail(),  userModel.getPassword(),
                                userModel.getImage(), uri.toString(),  userModel.getBirthdate(),
                                userModel.getGender() , userModel.getCreate_at(),  userModel.getLast_open(),  userModel.getPoint(), userModel.getUserId(), (float) 0.0);

                        FirebaseFirestore.getInstance().collection("approved_Drivers").document(userModel.getUserId()).set(usermodel2)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        FirebaseFirestore.getInstance().collection("temp_userInfo").document(userModel.getUserId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                progressDialog2.dismiss();
                                                Toast.makeText(ApprovepageActivity.this, "success", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(ApprovepageActivity.this, AdminMainActivity.class));
                                                finish();
                                                Animatoo.animateSlideLeft(ApprovepageActivity.this);
                                            }
                                        });

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                progressDialog2.dismiss();

                            }
                        });


                    }
                });
            }
        });

    }

    Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value, BarcodeFormat.DATA_MATRIX.QR_CODE, QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.black) : getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }


    private Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);

        return Uri.parse(path);
    }




    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of this and that")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(ApprovepageActivity.this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);


                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
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






}
