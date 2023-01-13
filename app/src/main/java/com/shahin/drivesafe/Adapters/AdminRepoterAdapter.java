package com.shahin.drivesafe.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shahin.drivesafe.Models.ReporterDataModel;
import com.shahin.drivesafe.R;

import java.util.List;

public class AdminRepoterAdapter extends BaseAdapter {


    Context context;

    List<ReporterDataModel> listModelList;

    public AdminRepoterAdapter(Context context, List<ReporterDataModel> listModelList) {
        this.context = context;
        this.listModelList = listModelList;
    }

    @Override
    public int getCount() {
        return listModelList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.view_tem_layout,null,false);


        ImageView imageView;
        TextView title, subtitle,point;


        title = view.findViewById(R.id.itemmain_title);
        subtitle = view.findViewById(R.id.itemmain_subtitle);
        imageView = view.findViewById(R.id.itemmain_img);


        title.setText(listModelList.get(i).getName());
        subtitle.setText(listModelList.get(i).getEmail());
   
        imageView.setImageResource(R.drawable.proico);




        return view;
    }
}

