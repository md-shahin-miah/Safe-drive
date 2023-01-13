package com.shahin.drivesafe.User;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;
import com.shahin.drivesafe.R;
import com.shahin.drivesafe.Robi.APIInterface;
import com.shahin.drivesafe.Robi.RetrofitClientInstance;
import com.shahin.drivesafe.Robi.Status;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.shahin.drivesafe.R.drawable.bag_left;

public class RobiCodeActivity extends AppCompatActivity {

    private static final String TAG = "RobiCodeActivity";
    EditText editText ;
    Button button;
    TextView textView;

    boolean flg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robi_code);

        getSupportActionBar().hide();

        PopUpWindow();


        editText = findViewById(R.id.codeeditID);
        button = findViewById(R.id.codesubmitId);
        textView = findViewById(R.id.codewhyId);


        SharedPreferences sharedPreferences = getSharedPreferences("codesub", Context.MODE_PRIVATE);

        if (sharedPreferences.contains("key")) {
            flg = sharedPreferences.getBoolean("key", false);

        }


        Log.d(TAG, "onCreate: " + flg);


        if (flg){
            button.setEnabled(false);
            button.setBackgroundResource(bag_left);

        }



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editText.getText().toString().isEmpty()) {
                    Toast.makeText(RobiCodeActivity.this, "Write a vald code", Toast.LENGTH_SHORT).show();
                } else {
                    checkSubStatus(editText.getText().toString().trim());

                }
            }
        });


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new TTFancyGifDialog.Builder((Activity) RobiCodeActivity.this)
                        .setTitle("Subscription Notice")
                        .setMessage("For visiting all divers profile you need to subscribe our system." +
                                "\n\n"+
                                "To subscribe this write “start drivesafe” and send to 21213 from any airtel or Robi operator"
                                + "\n\n"+
                                "you will get a code and submit this code here"

                        )
                        .setPositiveBtnText("Send SMS")
                        .setPositiveBtnBackground("#22b573")
                        .setNegativeBtnBackground("#c1272d")
                        .setGifResource(R.drawable.gif1)
                        .isCancellable(true)
                        .OnPositiveClicked(new TTFancyGifDialogListener() {
                            @Override
                            public void OnClick() {

                                Uri uri = Uri.parse("smsto:21213");
                                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                                intent.putExtra("sms_body", "start drivesafe");
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
        });



    }



    public  void checkSubStatus(String code) {
        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();

        APIInterface apiInterface = retrofit.create(APIInterface.class);
        Call<Status> call = apiInterface.getStatus(code);


        call.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {

                Log.e("response", response.toString());
                if (response.body() != null) {


                    if (response.body().getStat()) {
                        setsubvalue(true);
                        Toast.makeText(RobiCodeActivity.this, "Successfully Subscribed", Toast.LENGTH_SHORT).show();
                        
                    } else {
                        setsubvalue(false);
                        Toast.makeText(RobiCodeActivity.this, "Not a Valid Code", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                setsubvalue(false);
                Toast.makeText(RobiCodeActivity.this, "Not a Valid Code", Toast.LENGTH_SHORT).show();
            }
        });

    }


    
    private  void setsubvalue(boolean val) {
        SharedPreferences sharedPreferences = getSharedPreferences("codesub", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("key", val);
        editor.commit();
        
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



    public  boolean setPreferenceBool( Boolean value) {
        SharedPreferences sharedPreferences = getSharedPreferences("codesub", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("substatus", value);
        return   editor.commit();
    }


}
