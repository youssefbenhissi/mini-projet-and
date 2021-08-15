package com.rajendra.vacationtourapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rajendra.vacationtourapp.R;
import com.rajendra.vacationtourapp.model.PlatCategory;

import java.util.List;

public class PlatCategorieAdapter extends RecyclerView.Adapter<PlatCategorieAdapter.ProductViewHolder> {

    Context context;
    List<PlatCategory> productCategoryList;

    public PlatCategorieAdapter(Context context, List<PlatCategory> productCategoryList) {
        this.context = context;
        this.productCategoryList = productCategoryList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.category_plat_row_item, parent, false);
        // lets create a recyclerview row item layout file
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        holder.catagoryName.setText(productCategoryList.get(position).getProductName());

    }

    @Override
    public int getItemCount() {
        return productCategoryList.size();
    }


    public static final class ProductViewHolder extends RecyclerView.ViewHolder{


        TextView catagoryName;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            catagoryName = itemView.findViewById(R.id.cat_name);

        }
    }

}
