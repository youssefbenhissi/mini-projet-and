package com.rajendra.vacationtourapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.rajendra.vacationtourapp.Plat.detailPlatActivity;
import com.rajendra.vacationtourapp.R;
import com.rajendra.vacationtourapp.model.AsiaFood;
import com.rajendra.vacationtourapp.model.plat_restaurant;

import java.util.List;


public class AsiaFoodAdapter extends RecyclerView.Adapter<AsiaFoodAdapter.AsiaFoodViewHolder> {

    Context context;
    List<plat_restaurant> asiaFoodList;



    public AsiaFoodAdapter(Context context, List<plat_restaurant> asiaFoodList) {
        this.context = context;
        this.asiaFoodList = asiaFoodList;
    }

    @NonNull
    @Override
    public AsiaFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.asia_food_row_item, parent, false);
        return new AsiaFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder( AsiaFoodViewHolder holder, int position) {
        Glide.with(context).load(asiaFoodList.get(position).getImage()).into(holder.foodImage);

//        holder.foodImage.setImageResource(asiaFoodList.get(position).getImage());
        holder.name.setText(asiaFoodList.get(position).getNom());
        holder.price.setText(asiaFoodList.get(position).getPrix());
        holder.rating.setText(asiaFoodList.get(position).getNote_plat());
        holder.restorantName.setText(asiaFoodList.get(position).getNom());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, detailPlatActivity.class);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return asiaFoodList.size();
    }


    public static final class AsiaFoodViewHolder extends RecyclerView.ViewHolder{


        ImageView foodImage;
        TextView price, name, rating, restorantName;

        public AsiaFoodViewHolder(@NonNull View itemView) {
            super(itemView);

            foodImage = itemView.findViewById(R.id.food_image);
            price = itemView.findViewById(R.id.price);
            name = itemView.findViewById(R.id.name);
            rating = itemView.findViewById(R.id.rating);
            restorantName = itemView.findViewById(R.id.restorant_name);



        }
    }

}
