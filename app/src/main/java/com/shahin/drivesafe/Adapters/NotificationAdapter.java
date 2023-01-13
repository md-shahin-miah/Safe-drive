package com.shahin.drivesafe.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.shahin.drivesafe.Models.NotificationModel;
import com.shahin.drivesafe.R;

import java.util.List;

public class NotificationAdapter  extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {


    Context context;
    List<NotificationModel> notificationModelList;

    public NotificationAdapter(Context context, List<NotificationModel> notificationModelList) {
        this.context = context;
        this.notificationModelList = notificationModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        NotificationModel notificationModel = notificationModelList.get(position);

        holder.msg.setText(notificationModel.getMsg());
        holder.createat.setText(TimeAgo.from(notificationModel.getCreateat()));

    }

    @Override
    public int getItemCount() {
        return notificationModelList.size();
    }

    public  class  MyViewHolder  extends RecyclerView.ViewHolder {

        TextView createat,msg;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            createat = itemView.findViewById(R.id.notcreateat);
            msg = itemView.findViewById(R.id.notmsg);


        }
    }



}
