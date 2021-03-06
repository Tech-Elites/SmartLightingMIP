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

public class CustomAdapterListOfRoom extends ArrayAdapter<String> {

    public CustomAdapterListOfRoom(@NonNull Context context, ArrayList<String> arrayList) {
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;


        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.custom_adapter_layout_home, parent, false);
        }

        try{
            String s = getItem(position);
            TextView tname = currentItemView.findViewById(R.id.customRoomName);
            tname.setText(s);
        }catch (Exception e){
            System.out.println(e);
        }

        return currentItemView;
    }
}

