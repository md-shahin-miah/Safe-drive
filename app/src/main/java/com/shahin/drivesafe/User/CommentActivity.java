package com.shahin.drivesafe.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.shahin.drivesafe.Adapters.ComentAdapter;
import com.shahin.drivesafe.Models.CommentModel;
import com.shahin.drivesafe.R;

import java.util.ArrayList;

public class CommentActivity extends AppCompatActivity {



    RelativeLayout relativeLayout;

    ImageView sendbutton;
    MaterialEditText sendtext;

    String id;
    private RecyclerView recyclerView;
    ArrayList<CommentModel> list=new ArrayList<>();

    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Comments");


        relativeLayout = findViewById(R.id.ttr);


        sendbutton = findViewById(R.id.sendbutton);
        sendtext = findViewById(R.id.sendtext);


        Intent intent = getIntent();


        id = intent.getStringExtra("id");
       String  show = intent.getStringExtra("show");

        showcomment();

        if (show.equals("no")){
            relativeLayout.setVisibility(View.GONE);
        }




        final String user = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseFirestore.getInstance().collection("NormalUserinfo").document(user)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (!isConnected()){
                    Toast.makeText(CommentActivity.this, "You are in offline", Toast.LENGTH_SHORT).show();
                    return;
                }

                DocumentSnapshot document = task.getResult();
                String value = document.getString("username");
                username=value;


            }
        });


        sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = sendtext.getText().toString();

                if (!isConnected()){
                    Toast.makeText(CommentActivity.this, "You are in offline", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (!msg.isEmpty()){

                    CommentModel commentModel = new CommentModel(username,msg,System.currentTimeMillis());

                    FirebaseFirestore.getInstance().collection("Report").document(id)
                            .collection("comments").add(commentModel);

                    showcomment();

                }
                else
                {
                    Toast.makeText(CommentActivity.this, "You can't send empty message", Toast.LENGTH_SHORT).show();
                }
                sendtext.setText("");

            }
        });




    }






    private void showcomment() {


        FirebaseFirestore.getInstance().collection("Report").document(id)
                .collection("comments").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()){

                    list.clear();

                    for (DocumentSnapshot doc : task.getResult()){
                        CommentModel commentModel = doc.toObject(CommentModel.class);
                        list.add(commentModel);
                    }

                    if (list.size()>0){
                        findViewById(R.id.noitemID).setVisibility(View.GONE);
                    }
                    ComentAdapter comentAdapter = new ComentAdapter(CommentActivity.this,list);
                    recyclerView = findViewById(R.id.recychat);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(CommentActivity.this));
                    recyclerView.setAdapter(comentAdapter);


                }

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home)
            finish();
        Animatoo.animateSlideDown(CommentActivity.this);
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSlideDown(CommentActivity.this);
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

