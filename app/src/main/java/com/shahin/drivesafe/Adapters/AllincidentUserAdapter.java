package com.shahin.drivesafe.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.shahin.drivesafe.Models.CaseModel;
import com.shahin.drivesafe.Models.ReportModel;
import com.shahin.drivesafe.MyApp;
import com.shahin.drivesafe.R;
import com.shahin.drivesafe.User.CommentActivity;
import com.shahin.drivesafe.User.DriverProfileUserActivity;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllincidentUserAdapter extends  RecyclerView.Adapter<AllincidentUserAdapter.MyViewHolder> {

    boolean flg;
    private Context context;
    List<ReportModel> reportModelList;

    List<CaseModel>caseModelList ;

    public AllincidentUserAdapter(Context context, List<ReportModel> reportModelList) {
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




        SharedPreferences sharedPreferences = context.getSharedPreferences("codesub", Context.MODE_PRIVATE);

        if (sharedPreferences.contains("key")) {
            flg = sharedPreferences.getBoolean("key", false);

        }



        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (MyApp.ck==22 && !flg){

                    new TTFancyGifDialog.Builder((Activity) context)
                            .setTitle("Subscription Notice")
                            .setMessage("For visiting all divers profile you need to subscribe our system." +
                                    "\n\n"+
                                    "To subscribe this write “start drivesafe” and send to 21213 from any airtel or Robi operator"
                                    + "\n\n"+
                                    "you will get a code and submit this code here"

                            )
                            .setPositiveBtnText("Send SMS")
                            .setPositiveBtnBackground("#22b573")
                            .setNegativeBtnBackground("#22b573")
                            .setGifResource(R.drawable.gif1)
                            .isCancellable(true)
                            .OnPositiveClicked(new TTFancyGifDialogListener() {
                                @Override
                                public void OnClick() {

                                    Uri uri = Uri.parse("smsto:21213");
                                    Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                                    intent.putExtra("sms_body", "start drivesafe");
                                    context.startActivity(intent);
                                }
                            })

                            .build();
                }
                else {
                    MyApp.ck+=1;
                    Intent intent = new Intent(context, DriverProfileUserActivity.class);
                    intent.putExtra("key",reportModel.getUserid());
                    context.startActivity(intent);
                    Animatoo.animateSlideUp(context);

                }


            }
        });


        holder.comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, CommentActivity.class);
                intent.putExtra("id", reportModel.getRepoid());
                intent.putExtra("show", "yes");
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
