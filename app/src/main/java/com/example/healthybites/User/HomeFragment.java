package com.example.healthybites.User;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.healthybites.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    Activity refActivity;
    View parentholder;
/*
RecyclerView recyclerView;
DatabaseReference database;
MyAdapter_Kitchen adapter_kitchen;
ArrayList<Kitchens> list;*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        refActivity = getActivity();
        parentholder = inflater.inflate(R.layout.fragment_home, container,
                false);
/*


        recyclerView = parentholder.findViewById(R.id.kitchen_recycler);
        database = FirebaseDatabase.getInstance().getReference("Partner");
        recyclerView.setHasFixedSize(true);

        list = new ArrayList<>();
        adapter_kitchen = new MyAdapter_Kitchen(refActivity.getApplicationContext(),list);
        recyclerView.setAdapter(adapter_kitchen);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Kitchens kitchens = dataSnapshot.getValue(Kitchens.class);
                    list.add(kitchens);
                }
                adapter_kitchen.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        return void;*/
        return parentholder;

    }
}