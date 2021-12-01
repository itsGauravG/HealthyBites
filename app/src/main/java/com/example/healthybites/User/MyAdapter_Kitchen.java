package com.example.healthybites.User;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthybites.R;

import java.util.ArrayList;

public class MyAdapter_Kitchen extends RecyclerView.Adapter<MyAdapter_Kitchen.MyViewHolder> {

    Context context;
    ArrayList<Kitchens> list;

    public MyAdapter_Kitchen(Context contect , ArrayList<Kitchens> list){
        this.context = context;
        this.list = list ;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.kitchen_list,parent,false);



        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Kitchens kitchens = list.get(position);
        holder.kitchen_name.setText(kitchens.getKitchen_name());
        holder.kitchen_address.setText(kitchens.getKitchen_address());



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView kitchen_name , kitchen_address ;
        ImageView kitchen_image;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            kitchen_name = itemView.findViewById(R.id.kitchen_card_name);
            kitchen_address = itemView.findViewById(R.id.kitchen_card_address);
            kitchen_image = itemView.findViewById(R.id.kitchen_card_image);

        }

    }
}
