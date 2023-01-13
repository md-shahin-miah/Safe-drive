package com.shahin.drivesafe.Admin.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shahin.drivesafe.FirstActivity;
import com.shahin.drivesafe.Models.ReporterDataModel;
import com.shahin.drivesafe.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AProfileFragment extends Fragment {


    public AProfileFragment() {
        // Required empty public constructor
    }


    TextView name ,email;

    TextView signout;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_aprofile, container, false);


        name =view. findViewById(R.id.user_profile_name1);
        email = view.findViewById(R.id.user_profile_short_bio1);
        signout = view.findViewById(R.id.signoutTextID1);

        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore.getInstance().collection("admininfo").document(id)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()){
                    DocumentSnapshot doc  = task.getResult();
                    ReporterDataModel normaluserModel = doc.toObject(ReporterDataModel.class);

                    name.setText(normaluserModel.getName());
                    email.setText(normaluserModel.getEmail());

                }

            }
        });


        signout.setOnClickListener(new View.OnClickListener() {
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
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("identy", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", name);
        editor.commit();
    }


}

