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
import com.rajendra.vacationtourapp.model.PopularFood;
import com.rajendra.vacationtourapp.model.plat_restaurant;

import java.util.List;


public class PopularFoodAdapter extends RecyclerView.Adapter<PopularFoodAdapter.PopularFoodViewHolder> {

    Context context;
    List<plat_restaurant> popularFoodList;



    public PopularFoodAdapter(Context context, List<plat_restaurant> popularFoodList) {
        this.context = context;
        this.popularFoodList = popularFoodList;
    }

    @NonNull
    @Override
    public PopularFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.promotion_food_row_item, parent, false);
        return new PopularFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularFoodViewHolder holder, int position) {
        Glide.with(context).load(popularFoodList.get(position).getImage()).into(holder.foodImage);

        //holder.foodImage.setImageResource(popularFoodList.get(position).getImageUrl());
        holder.name.setText(popularFoodList.get(position).getNom());
        holder.price.setText(popularFoodList.get(position).getPourcentage_promotion()+"%");

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
        return popularFoodList.size();
    }


    public static final class PopularFoodViewHolder extends RecyclerView.ViewHolder{


        ImageView foodImage;
        TextView price, name;

        public PopularFoodViewHolder(@NonNull View itemView) {
            super(itemView);

            foodImage = itemView.findViewById(R.id.food_image);
            price = itemView.findViewById(R.id.price);
            name = itemView.findViewById(R.id.name);



        }
    }

}
