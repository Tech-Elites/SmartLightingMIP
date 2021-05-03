package com.example.smartlightingsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomAdapterBulbsList extends ArrayAdapter<BulbsInfo> {

    public CustomAdapterBulbsList(@NonNull Context context, ArrayList<BulbsInfo> arrayList) {
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;


        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.custom_layout_each_room, parent, false);
        }

        try{
            BulbsInfo b = getItem(position);
            TextView tname = currentItemView.findViewById(R.id.eachRoomLightName);
            TextView tstatus = currentItemView.findViewById(R.id.eachRoomLightStatus);
            tname.setText(b.getName());
            if(b.getStatus()==1){
                tstatus.setText("ON");
            }
            else{
                tstatus.setText("OFF");
            }
        }catch (Exception e){
            System.out.println(e);
        }

        return currentItemView;
    }
}

