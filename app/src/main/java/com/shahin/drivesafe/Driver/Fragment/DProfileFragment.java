package com.shahin.drivesafe.Driver.Fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.shahin.drivesafe.Adapters.ReviewAdapter;
import com.shahin.drivesafe.Driver.IncidentRulesActivity;
import com.shahin.drivesafe.Driver.MyIncidentActivity;
import com.shahin.drivesafe.Driver.MyQRcodeActivity;
import com.shahin.drivesafe.Driver.NotificationSettingsActivity;
import com.shahin.drivesafe.Driver.PersonalinformationActivity;
import com.shahin.drivesafe.Driver.PointHistoryActivity;
import com.shahin.drivesafe.Driver.ReviewActivity;
import com.shahin.drivesafe.FirstActivity;
import com.shahin.drivesafe.Models.NormaluserModel;
import com.shahin.drivesafe.Models.ReportModel;
import com.shahin.drivesafe.Models.ReviewModel;
import com.shahin.drivesafe.Models.UserModel;
import com.shahin.drivesafe.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DProfileFragment extends Fragment {


    public DProfileFragment() {
        // Required empty public constructor
    }


    RecyclerView recyclerView;


    UserModel userModel;
    NormaluserModel normaluserModel;


    List<ReviewModel> reviewModelList;


    int ratingcnt;




    List<ReportModel> reportModelList;


    ImageView coverpic;
    CircleImageView propic;
    TextView name,address;
    TextView point ;
    TextView avgrating;

    TextView cnt1,cnt2,cnt3,cnt4,cnt5;

    ProgressBar progressBar1,progressBar2,progressBar3,progressBar4,progressBar5;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_dprofile, container, false);


        coverpic = view.findViewById(R.id.coverpicId);
        propic = view.findViewById(R.id.propicID);
        name = view.findViewById(R.id.pronameID);
        address = view.findViewById(R.id.proaddressId);
        point = view.findViewById(R.id.propointId);
        avgrating = view.findViewById(R.id.avgrating);

        recyclerView  = view.findViewById(R.id.reviewuserRecyID);


        cnt1 = view.findViewById(R.id.countpro);
        cnt2 =view. findViewById(R.id.countpro2);
        cnt3 = view.findViewById(R.id.countpro3);
        cnt4 = view.findViewById(R.id.countpro4);
        cnt5 = view.findViewById(R.id.countpro5);


        progressBar1 = view.findViewById(R.id.progress_bar1);
        progressBar2 = view.findViewById(R.id.progress_bar2);
        progressBar3 = view.findViewById(R.id.progress_bar3);
        progressBar4 = view.findViewById(R.id.progress_bar4);
        progressBar5 = view.findViewById(R.id.progress_bar5);


        setRecyview();




        FirebaseFirestore.getInstance().collection("approved_Drivers")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                DocumentSnapshot doc = task.getResult();
                userModel = doc.toObject(UserModel.class);


                FirebaseFirestore.getInstance().collection("approved_Drivers").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .collection("Reviews").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()){

                            ratingcnt = task.getResult().size();

                            Picasso.get().load(userModel.getImage()).into(coverpic);
                            Picasso.get().load(userModel.getImage()).into(propic);
                            name.setText(userModel.getName());
                            address.setText(userModel.getAddress());
                            point.setText(userModel.getPoint()+"");
                            avgrating.setText(userModel.getAvgrating()/ratingcnt+"");



                            int cn1 = 0,cn2 = 0,cn3 = 0,cn4 = 0,cn5 = 0;
                            for (DocumentSnapshot doc : task.getResult()){

                                ReviewModel reviewModel = doc.toObject(ReviewModel.class);

                                if (reviewModel.getRating()==5){
                                    cn5=cn5+1;
                                }else  if (reviewModel.getRating()==4){

                                    cn4 = cn4+1;
                                } else  if (reviewModel.getRating()==3){

                                    cn3 = cn3 +1;
                                }
                                else if (reviewModel.getRating()==2){
                                    cn2 = cn2 +1;
                                }
                                else  if (reviewModel.getRating()==1){

                                    cn1 = cn1 +1;

                                }
                            }
                            cnt5.setText(cn1+"");
                            cnt4.setText(cn2+"");
                            cnt3.setText(cn3+"");
                            cnt2.setText(cn4+"");
                            cnt1.setText(cn5+"");

                            progressBar1.setProgress(cn5);
                            progressBar2.setProgress(cn4);
                            progressBar3.setProgress(cn3);
                            progressBar4.setProgress(cn2);
                            progressBar5.setProgress(cn1);

                        }
                    }
                });

            }
        });


        view.findViewById(R.id.personalinfoID).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PersonalinformationActivity.class));
                Animatoo.animateZoom(getActivity());
            }
        });


        view.findViewById(R.id.incidentrules).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), IncidentRulesActivity.class));
                Animatoo.animateSwipeLeft(getActivity());
            }
        });

        view.findViewById(R.id.myincidentID).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), MyIncidentActivity.class));
                Animatoo.animateSplit(getActivity());

            }
        });

        view.findViewById(R.id.viewqrcode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), MyQRcodeActivity.class);

                intent.putExtra("key",userModel.getQrcode());
                startActivity(intent);
                Animatoo.animateInAndOut(getActivity());
            }
        });


        view.findViewById(R.id.pointhis).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PointHistoryActivity.class));

                Animatoo.animateZoom(getActivity());
            }
        });

        view.findViewById(R.id.morereview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), ReviewActivity.class);
                intent.putExtra("key",userModel.getUserId());
                startActivity(intent);
                Animatoo.animateSlideUp(getActivity());
            }
        });

        view.findViewById(R.id.notifisettings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), NotificationSettingsActivity.class));
                Animatoo.animateSwipeRight(getActivity());
            }
        });


        view.findViewById(R.id.signoutpro).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                storedata("no");
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), FirstActivity.class));

                getActivity().finish();
                Animatoo.animateZoom(getActivity());
            }
        });

        return view;
    }



    private void storedata(String name) {
        SharedPreferences sharedPreferences =getActivity().getSharedPreferences("identy", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", name);
        editor.commit();
    }

    private void setRecyview() {

        FirebaseFirestore.getInstance().collection("approved_Drivers").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("Reviews").orderBy("createat", Query.Direction.DESCENDING).limit(3).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){

                    reviewModelList = new ArrayList<>();

                    for (DocumentSnapshot doc : task.getResult()){

                        ReviewModel reviewModel = doc.toObject(ReviewModel.class);
                        reviewModelList.add(reviewModel);

                    }

                    ReviewAdapter adapter = new ReviewAdapter(getActivity(),reviewModelList);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setAdapter(adapter);


                }

            }
        });


    }

}

