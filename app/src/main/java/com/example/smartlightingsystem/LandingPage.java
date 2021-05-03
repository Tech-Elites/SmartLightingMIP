package com.example.smartlightingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LandingPage extends AppCompatActivity {

    CustomAdapterListOfRoom customAdapterListOfRoom;
    ListView listViewOfRooms;
    ArrayList<String> arrayListOfRooms;
    TextView homeName;
    ProgressBar p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        arrayListOfRooms=new ArrayList<>();
        homeName=findViewById(R.id.houseName);
        listViewOfRooms=findViewById(R.id.itemsList);
        listViewOfRooms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onClickItem(position);
            }
        });
        p=findViewById(R.id.landingProgress);
        p.setVisibility(View.VISIBLE);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("MyHome");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                homeName.setText(snapshot.getKey());
                arrayListOfRooms.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String temp = dataSnapshot.getKey();
                    arrayListOfRooms.add(temp);
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
            customAdapterListOfRoom = new CustomAdapterListOfRoom(this, arrayListOfRooms);
            listViewOfRooms.setAdapter(customAdapterListOfRoom);
        } catch (Exception e) {
            e.printStackTrace();
        }
        p.setVisibility(View.INVISIBLE);
    }

    void onClickItem(int position){
        Intent i = new Intent(LandingPage.this, BulbInfoPage.class);
        i.putExtra("roomname",arrayListOfRooms.get(position));
        startActivity(i);
    }
}