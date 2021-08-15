package com.rajendra.vacationtourapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.rajendra.vacationtourapp.R;
import com.rajendra.vacationtourapp.model.AllCategoryModel;

import java.util.List;

public class AllCategoryAdapter extends RecyclerView.Adapter<AllCategoryAdapter.AllCategoryViewHolder> {

    Context context;
    List<AllCategoryModel> categoryList;
    String idRestaurant;

    public AllCategoryAdapter(Context context, List<AllCategoryModel> categoryList,String idRestaurant) {
        this.context = context;
        this.categoryList = categoryList;
        this.idRestaurant=idRestaurant;
    }

    @NonNull
    @Override
    public AllCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.all_category_row_items, parent, false);

        return new AllCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllCategoryViewHolder holder, int position) {

        holder.categoryImage.setImageResource(categoryList.get(position).getImageurl());

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public  static class AllCategoryViewHolder extends RecyclerView.ViewHolder{

        ImageView categoryImage;

        public AllCategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryImage = itemView.findViewById(R.id.categoryImage);

        }
    }

}

// lets import all the category images