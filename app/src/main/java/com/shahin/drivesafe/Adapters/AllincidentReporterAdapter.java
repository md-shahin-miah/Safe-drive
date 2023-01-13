package com.shahin.drivesafe.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.shahin.drivesafe.Driver.DriverprofileDActivity;
import com.shahin.drivesafe.Models.CaseModel;
import com.shahin.drivesafe.Models.ReportModel;
import com.shahin.drivesafe.R;
import com.shahin.drivesafe.User.CommentActivity;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static java.util.Objects.requireNonNull;

public class AllincidentReporterAdapter extends  RecyclerView.Adapter<AllincidentReporterAdapter.MyViewHolder> {



    private Context context;
    List<ReportModel> reportModelList;

    List<CaseModel>caseModelList ;

    public AllincidentReporterAdapter(Context context, List<ReportModel> reportModelList) {
        this.context = context;
        this.reportModelList = reportModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.allincident_item_admin, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final ReportModel reportModel = reportModelList.get(position);

        Picasso.get().load(reportModel.getDriverimage()).into(holder.propic);



        holder.drivername.setText(reportModel.getDriver_name());
        holder.Title.setText("Case Summary");
        holder.des.setText(reportModel.getDescription());
        holder.reportername.setText(reportModel.getReportername());
        holder.createat.setText(TimeAgo.from(reportModel.getCreateat()));

        FirebaseFirestore.getInstance().collection("Report")
                .document(reportModel.getRepoid()).collection("case").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()){

                            caseModelList = new ArrayList<>();

                            String text = "";

                            for (DocumentSnapshot doc : task.getResult()){

                                CaseModel caseModel = doc.toObject(CaseModel.class);

                                caseModelList.add(caseModel);

                                text =  text+ " \u25A0 " +caseModel.getName()+"  [ point - "+caseModel.getPoint()+ " ] \n";
                            }

                            holder.casetext.setText(text);

                        }

                    }
                });

        
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, DriverprofileDActivity.class);
                intent.putExtra("key",reportModel.getUserid());
                context.startActivity(intent);

                Animatoo.animateInAndOut(context);
            }
        });


        holder.comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, CommentActivity.class);
                intent.putExtra("id", reportModel.getRepoid());
                intent.putExtra("show", "no");
                context.startActivity(intent);
                Animatoo.animateSlideUp(context);
            }
        });




        FirebaseFirestore.getInstance().collection("Report").document(reportModel.getRepoid())
                .collection("comments").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()){


                    holder.commentText.setText("Comments " +task.getResult().size());

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


        CircleImageView propic;
        TextView drivername,Title,casetext,des,reportername,createat,commentText;
        LinearLayout comments;
        RelativeLayout relativeLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            propic  = itemView.findViewById(R.id.itemdriverpropic);
            drivername = itemView.findViewById(R.id.itemdrivernameID);
            Title = itemView.findViewById(R.id.titleitemdriver);
            casetext = itemView.findViewById(R.id.caseitemdriverId);
            des = itemView.findViewById(R.id.itemcasedescription);
            reportername = itemView.findViewById(R.id.itemreporterId);
            createat = itemView.findViewById(R.id.itemcreateatID);
            commentText = itemView.findViewById(R.id.commentsTextID);
            comments  = itemView.findViewById(R.id.commentsitemID);
            relativeLayout = itemView.findViewById(R.id.lin_item);







        }
    }




}
