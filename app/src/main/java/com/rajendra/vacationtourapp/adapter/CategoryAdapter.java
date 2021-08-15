package com.rajendra.vacationtourapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rajendra.vacationtourapp.Plat.ListerPlatCategorieActivity;
import com.rajendra.vacationtourapp.Plat.detailPlatActivity;
import com.rajendra.vacationtourapp.Plat.listPlat;
import com.rajendra.vacationtourapp.R;
import com.rajendra.vacationtourapp.model.Category;
import com.rajendra.vacationtourapp.model.plat_restaurant;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    Context context;
    List<Category> categoryList;
    String idRestaurant;

    public CategoryAdapter(Context context, List<Category> categoryList,String idRestaurant) {
        this.context = context;
        this.categoryList = categoryList;
        this.idRestaurant= idRestaurant;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.category_row_items, parent, false);

        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        holder.categoryImage.setImageResource(categoryList.get(position).getImageurl());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ListerPlatCategorieActivity.class);
                i.putExtra("idRestaurant",idRestaurant);
                i.putExtra("categorie",categoryList.get(position).getNom());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public  static class CategoryViewHolder extends RecyclerView.ViewHolder{

        ImageView categoryImage;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryImage = itemView.findViewById(R.id.categoryImage);

        }
    }

}

// lets import all the category images