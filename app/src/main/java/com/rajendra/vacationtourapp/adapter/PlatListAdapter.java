package com.rajendra.vacationtourapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.rajendra.vacationtourapp.Plat.detailPlatActivity;
import com.rajendra.vacationtourapp.Plat.listPlat;
import com.rajendra.vacationtourapp.R;
import com.rajendra.vacationtourapp.model.plat_restaurant;
import com.rajendra.vacationtourapp.retrofit.ApiInterface;
import com.rajendra.vacationtourapp.retrofit.RetrofitClient;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlatListAdapter extends RecyclerView.Adapter<PlatListAdapter.ProductViewHolder> {

    Context context;
    List<plat_restaurant> productsList;
    ApiInterface apiInterface;
    String idUser;

    public PlatListAdapter(Context context, List<plat_restaurant> productsList,String idUser) {
        this.context = context;
        this.productsList = productsList;
        this.idUser = idUser;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.plat_row_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductViewHolder holder, int position) {
        Glide.with(context).load(productsList.get(position).getImage()).into(holder.prodImage);

        holder.prodName.setText(productsList.get(position).getNom());
        holder.prodQty.setText(productsList.get(position).getNote_plat());
        holder.prodPrice.setText(productsList.get(position).getPrix());
        holder.panier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
                HashMap<String, String> map = new HashMap<>();
                map.put("id_resto", productsList.get(position).getId_resto());
                map.put("id_user",idUser);
                map.put("id_plat", productsList.get(position).getId());
                Call<Void> callRandom = apiInterface.ajouterplatpanier(map);
                callRandom.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> callRandom, Response<Void> response)
                    {

                    }

                    @Override
                    public void onFailure(Call<Void> callRandom, Throwable t) {
                        //Toast.makeText(getContext(), "Server is not responding.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, detailPlatActivity.class);
                context.startActivity(i);
               // Intent i = new Intent(context, Productdetails.class);
/*
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(holder.prodImage, "image");
                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity) context, pairs);
             */
//context.startActivity(i/*, activityOptions.toBundle()*/);
            }
        });


    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public static final class ProductViewHolder extends RecyclerView.ViewHolder{

        ImageView prodImage,panier;
        TextView prodName, prodQty, prodPrice;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            panier = itemView.findViewById(R.id.imageView6);
            prodImage = itemView.findViewById(R.id.prod_image);
            prodName = itemView.findViewById(R.id.prod_name);
            prodPrice = itemView.findViewById(R.id.prod_price);
            prodQty = itemView.findViewById(R.id.prod_qty);


        }
    }

}
