package com.example.smartlightingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BulbInfoPage extends AppCompatActivity {

    String roomname;
    TextView roomNameText;
    CustomAdapterBulbsList customAdapterBulbsList;
    ArrayList<BulbsInfo> arrayList;
    ListView listViewBulbs;
    ProgressBar p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulb_info);
        arrayList=new ArrayList<BulbsInfo>();
        Intent i = getIntent();
        roomname=i.getStringExtra("roomname");
        roomNameText=findViewById(R.id.roomName);
        roomNameText.setText(roomname);
        p=findViewById(R.id.bulbsProgress);
        p.setVisibility(View.VISIBLE);
        listViewBulbs=findViewById(R.id.bulbsList);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("MyHome").child(roomname);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    BulbsInfo temp = dataSnapshot.getValue(BulbsInfo.class);
                    arrayList.add(temp);
                }
                generatePage();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    void generatePage(){
        try {
            customAdapterBulbsList = new CustomAdapterBulbsList(this, arrayList);
            listViewBulbs.setAdapter(customAdapterBulbsList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        p.setVisibility(View.INVISIBLE);
    }
}