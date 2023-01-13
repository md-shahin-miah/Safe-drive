package com.shahin.drivesafe.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shahin.drivesafe.Models.ReviewModel;
import com.shahin.drivesafe.R;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder> {


    Context context;
    List<ReviewModel> reviewModelList;

    public ReviewAdapter(Context context, List<ReviewModel> reviewModelList) {
        this.context = context;
        this.reviewModelList = reviewModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ReviewModel reviewModel = reviewModelList.get(position);
        holder.username.setText(reviewModel.getUsername());
        holder.rating.setText(reviewModel.getRating()+"");
        holder.review.setText(reviewModel.getReview());
        holder.pro.setImageResource(R.drawable.proico);


    }

    @Override
    public int getItemCount() {
        return reviewModelList.size();
    }

    public  class  MyViewHolder  extends RecyclerView.ViewHolder {

        ImageView pro;
        TextView username,rating;
        TextView review;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            pro = itemView.findViewById(R.id.item_review_icon);

            review = itemView.findViewById(R.id.reviewdesId);

            username = itemView.findViewById(R.id.reviewnameID);
            rating = itemView.findViewById(R.id.reviewrating);



        }
    }






}
