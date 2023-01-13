package com.shahin.drivesafe.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.shahin.drivesafe.Models.CaseModel;
import com.shahin.drivesafe.Models.ReportModel;
import com.shahin.drivesafe.R;

import java.util.ArrayList;
import java.util.List;

public class PointHistoryAdapter extends  RecyclerView.Adapter<PointHistoryAdapter.MyViewHolder> {



    private Context context;
    List<ReportModel> reportModelList;

    List<CaseModel>caseModelList ;

    public PointHistoryAdapter(Context context, List<ReportModel> reportModelList) {
        this.context = context;
        this.reportModelList = reportModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.point_history_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final ReportModel reportModel = reportModelList.get(position);



        holder.Title.setText("Case Summary");
        holder.crateat.setText(TimeAgo.from(reportModel.getCreateat()));

        FirebaseFirestore.getInstance().collection("Report")
                .document(reportModel.getRepoid()).collection("case").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()){

                            caseModelList = new ArrayList<>();

                            String text = "";

                            int total = 0;

                            for (DocumentSnapshot doc : task.getResult()){

                                CaseModel caseModel = doc.toObject(CaseModel.class);

                                caseModelList.add(caseModel);

                                text =  text+ " \u25A0 " +caseModel.getName()+"  [ point - "+caseModel.getPoint()+ " ] \n";

                                total = total + caseModel.getPoint();
                            }

                            holder.casetext.setText(text);
                            holder.drivername.setText("Total Lost Point - " + total);
                        }

                    }
                });

    }


    @Override
    public int getItemCount() {
        return reportModelList.size();
    }


    public void setfilter(List<ReportModel> itemData) {
        reportModelList = new ArrayList<>();
        reportModelList.addAll(itemData);
        notifyDataSetChanged();

    }




    //region ViewHolder class
    class MyViewHolder extends RecyclerView.ViewHolder {



        TextView drivername,Title,casetext,crateat;


        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            drivername = itemView.findViewById(R.id.itemdrivernameID);
            Title = itemView.findViewById(R.id.titleitemdriver);
            casetext = itemView.findViewById(R.id.caseitemdriverId);
            crateat = itemView.findViewById(R.id.itemcreateatID);




        }
    }




}
