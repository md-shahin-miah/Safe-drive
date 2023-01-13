package com.shahin.drivesafe.User.Fragments;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.shahin.drivesafe.Adapters.AllincidentUserAdapter;
import com.shahin.drivesafe.Models.ReportModel;
import com.shahin.drivesafe.R;
import com.shahin.drivesafe.Reporter.AllincidentlistRActivity;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class UHomeFragment extends Fragment {


    public UHomeFragment() { }


    RecyclerView recyclerView;

    private ProgressDialog progressDialog;
    List<ReportModel> reportModelList;

    EditText search ;

    AllincidentUserAdapter adapter ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_uhome, container, false);


        search = view.findViewById(R.id.editsearchID);
        recyclerView = view.findViewById(R.id.userrecyID);

        progressDialog = new ProgressDialog(getActivity());


        loadRecylerview();


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String newText = search.getText().toString().trim();
                if (newText.isEmpty()) {
                    loadRecylerview();
                    Log.d(TAG, "onTextChanged:  edittextt " + newText);
                } else {
                    List<ReportModel> newList = filter(reportModelList, newText);
                    Log.d(TAG, "onQueryTextChange: " + newList);
                    adapter.setfilter(newList);
                }

            }


            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        return view;
    }


    private List<ReportModel> filter(List<ReportModel> pd, String query) {


        query = query.toLowerCase().trim();

        List<ReportModel> filterProductData = new ArrayList<>();
        for (ReportModel prodata : pd) {
            if (prodata != null) {
                final String text = prodata.getDriver_name().toLowerCase();
                final String des = prodata.getDescription().toLowerCase();
                if (text.contains(query)) {
                    filterProductData.add(prodata);
                }
                if (des.contains(query)){
                    filterProductData.add(prodata);
                }

            } else {
                Log.d(TAG, "filter: ");
            }

        }
        return filterProductData;
    }



    private void loadRecylerview() {


        progressDialog.show();

        FirebaseFirestore.getInstance().collection("Report").orderBy("createat", Query.Direction.DESCENDING)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()){

                    reportModelList = new ArrayList<>();
                    for (DocumentSnapshot doc : task.getResult()){

                        ReportModel reportModel = doc.toObject(ReportModel.class);
                        reportModelList.add(reportModel);


                    }

                    progressDialog.dismiss();
                     adapter = new AllincidentUserAdapter(getActivity(),reportModelList);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(adapter);

                }

            }
        });


    }

}
