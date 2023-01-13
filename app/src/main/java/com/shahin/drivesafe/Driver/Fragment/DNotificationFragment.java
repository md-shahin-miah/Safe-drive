package com.shahin.drivesafe.Driver.Fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.shahin.drivesafe.Adapters.NotificationAdapter;
import com.shahin.drivesafe.Models.NotificationModel;
import com.shahin.drivesafe.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DNotificationFragment extends Fragment {


    public DNotificationFragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;


    List<NotificationModel> notificationModelList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_dnotification, container, false);



        recyclerView   = view.findViewById(R.id.notificationrecy3);


        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseFirestore.getInstance().collection("Notifications")
                .document(id).collection("msg")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()){

                            notificationModelList = new ArrayList<>();

                            for (DocumentSnapshot doc : task.getResult()){

                                NotificationModel notificationModel = doc.toObject(NotificationModel.class);
                                notificationModelList.add(notificationModel);


                            }

                            if (notificationModelList.size()==0){

                                view.findViewById(R.id.nonotificD).setVisibility(View.VISIBLE);
                            }

                            NotificationAdapter adapter = new NotificationAdapter(getActivity(),notificationModelList);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            recyclerView.setAdapter(adapter);

                        }

                    }
                });



        return view;
    }

}
