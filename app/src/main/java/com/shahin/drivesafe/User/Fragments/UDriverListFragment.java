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
import com.google.firebase.firestore.QuerySnapshot;
import com.shahin.drivesafe.Adapters.UserDriversAdapter;
import com.shahin.drivesafe.Models.UserModel;
import com.shahin.drivesafe.R;

import java.util.ArrayList;
import java.util.List;

import static com.google.firebase.remoteconfig.FirebaseRemoteConfig.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class UDriverListFragment extends Fragment {


    public UDriverListFragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;

    List<UserModel> userModelList = new ArrayList<>();

    EditText editText;

    UserDriversAdapter userDriversAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_udriver_list, container, false);

        recyclerView = view.findViewById(R.id.driversrecyId);
        editText = view.findViewById(R.id.editsearchID);

        view.findViewById(R.id.noitemID).setVisibility(View.GONE);



        loadRecylerview();



        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String newText = editText.getText().toString().trim();
                if (newText.isEmpty()) {
                    loadRecylerview();
                    Log.d(TAG, "onTextChanged:  edittextt " + newText);
                } else {
                    List<UserModel> newList = filter(userModelList, newText);
                    Log.d(TAG, "onQueryTextChange: " + newList);
                    userDriversAdapter.setfilter(newList);
                }

            }



            @Override
            public void afterTextChanged(Editable editable) {

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


    private void loadRecylerview() {

        ProgressDialog pd = new ProgressDialog(getActivity());

        pd.show();

        FirebaseFirestore.getInstance().collection("approved_Drivers").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()){

                            userModelList.clear();
                            for (DocumentSnapshot doc : task.getResult()){

                                UserModel userModel = doc.toObject(UserModel.class);
                                userModelList.add(userModel);


                            }
                            pd.dismiss();
                            if (userModelList.size()==0){

                                getView().findViewById(R.id.noitemID).setVisibility(View.VISIBLE);
                            }
                             userDriversAdapter = new UserDriversAdapter(getActivity(),userModelList);

                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            recyclerView.setAdapter(userDriversAdapter);

                        }
                        else
                        {
                            pd.dismiss();
                        }

                    }
                });




    }


}
