package com.shahin.drivesafe.Adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.shahin.drivesafe.Models.UserModel;
import com.shahin.drivesafe.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PendingDriversAdapter extends BaseAdapter {


    Context context;
    List<UserModel> list;

    public PendingDriversAdapter(Context context, List<UserModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {


        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        convertView = LayoutInflater.from(context).inflate(R.layout.item_pending_driver_list,parent,false);

        TextView name = convertView.findViewById(R.id.item_Driver_Namae_Id);
        TextView license = convertView.findViewById(R.id.item_Driver_license_Id);
        TextView createat = convertView.findViewById(R.id.item_createAt_ID);
        CircleImageView img = convertView.findViewById(R.id.itemmain_imgm);


        UserModel userModel = list.get(position);

        name.setText(userModel.getName()+" ");
        license.setText(userModel.getLicense());
        createat.setText(TimeAgo.from(userModel.getCreate_at()));


        return convertView;
    }
}
