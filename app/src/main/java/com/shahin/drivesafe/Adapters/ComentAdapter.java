package com.shahin.drivesafe.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.shahin.drivesafe.Models.CommentModel;
import com.shahin.drivesafe.R;

import java.util.List;


public class ComentAdapter extends RecyclerView.Adapter<ComentAdapter.MyViewHolder> {

    private static final String TAG = "PostAdapter";

    Context context;
    List<CommentModel>commentModelList;


    public ComentAdapter(Context context, List<CommentModel> commentModelList) {
        this.context = context;
        this.commentModelList = commentModelList;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        final CommentModel commentModel = commentModelList.get(position);


        Log.d(TAG, "onBindViewHolder: " + commentModel.getUsername());

        holder.createat.setText(TimeAgo.from(commentModel.getCreateAt()));

        holder.username.setText(commentModel.getUsername());
        holder.msg.setText(commentModel.getComment());

    }

    @Override
    public int getItemCount() {
        return commentModelList.size();
    }



    public  class  MyViewHolder  extends RecyclerView.ViewHolder {



        ImageView pro;
        TextView createat;
        TextView username,msg;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            pro = itemView.findViewById(R.id.cpro);

            createat = itemView.findViewById(R.id.comcreateat);

            username = itemView.findViewById(R.id.commusername);
            msg = itemView.findViewById(R.id.maincomment);



        }
    }




}
