package com.rajendra.vacationtourapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.rajendra.vacationtourapp.R;
import com.rajendra.vacationtourapp.model.Panier;
import com.rajendra.vacationtourapp.model.plat_restaurant;
import com.rajendra.vacationtourapp.panier.CartActivity;
import com.rajendra.vacationtourapp.retrofit.ApiInterface;
import com.rajendra.vacationtourapp.retrofit.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder>{
    Context context;
    List<Panier> carList;
    String idUser;
    ApiInterface apiInterface;

    public CartAdapter(Context context, List<Panier> carList,String idUser) {
        this.context = context;
        this.carList = carList;
        this.idUser = idUser;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.cart_item_layout,parent,false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartViewHolder holder, final int position) {

        Glide.with(context).load(carList.get(position).getPlat().getImage()).into(holder.img_product);

        holder.txt_amount.setNumber(carList.get(position).getQuantite());
        holder.txt_price.setText(new StringBuilder("$").append(carList.get(position).getPlat().getPrix()));
        holder.txt_product_name.setText(carList.get(position).getPlat().getNom());
        TextView txtViewfinal = (TextView) ((Activity)context).findViewById(R.id.txt_final_price);
        String finalResult=txtViewfinal.getText().toString();
        finalResult = String.valueOf(Integer.parseInt(finalResult)+(Integer.parseInt(carList.get(position).getQuantite()) *  Integer.parseInt(carList.get(position).getPlat().getPrix()))) ;
        txtViewfinal.setText(finalResult);
        holder.detelte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);

                HashMap<String, String> mapPlat = new HashMap<>();
                mapPlat.put("idPlat", carList.get(position).getId_plat());
                mapPlat.put("idResto", carList.get(position).getId_resto());
                mapPlat.put("idUser", idUser);
                TextView txtViewfinal = (TextView) ((Activity)context).findViewById(R.id.txt_final_price);
                String finalResult=txtViewfinal.getText().toString();
                finalResult = String.valueOf(Integer.parseInt(finalResult)-(Integer.parseInt(carList.get(position).getQuantite()) *  Integer.parseInt(carList.get(position).getPlat().getPrix()))) ;
                txtViewfinal.setText(finalResult);
                Call<Void> callPlat = apiInterface.supprimerpanier(mapPlat);
                callPlat.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> callPlat, Response<Void> response) {
                    }
                    @Override
                    public void onFailure(Call<Void> callPlat, Throwable t) {
                    }
                });
                carList.remove(position);
                notifyDataSetChanged();

            }
        });
        holder.txt_amount.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener()  {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);

                String num = holder.txt_amount.getNumber();
                HashMap<String, String> mapPlat = new HashMap<>();
                mapPlat.put("idPlat", carList.get(position).getId_plat());
                mapPlat.put("idResto", carList.get(position).getId_resto());
                mapPlat.put("quantite", num);
                mapPlat.put("idUser", idUser);
                TextView txtView = (TextView) ((Activity)context).findViewById(R.id.txt_final_price);
               // txtView.setText("waaa");
                if(newValue>oldValue){
                    int somme = Integer.parseInt(txtView.getText().toString());
                    somme += Integer.parseInt(carList.get(position).getPlat().getPrix()) ;
                    txtView.setText(String.valueOf(somme));
                }

                else{
                    int somme = Integer.parseInt(txtView.getText().toString());
                    somme -= Integer.parseInt(carList.get(position).getPlat().getPrix());
                    txtView.setText(String.valueOf(somme));
                }
//                int somme = Integer.parseInt(txtView.getText().toString());

                Call<Void> callPlat = apiInterface.updatepanier(mapPlat);
                callPlat.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> callPlat, Response<Void> response) {}
                    @Override
                    public void onFailure(Call<Void> callPlat, Throwable t) {
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder{
        ImageView img_product;
        TextView txt_product_name,txt_sugar_ice,txt_price;
        ImageView detelte;
        ElegantNumberButton txt_amount;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            img_product = (ImageView) itemView.findViewById(R.id.img_product);
            txt_amount = (ElegantNumberButton) itemView.findViewById(R.id.txt_amount);
            txt_product_name = (TextView) itemView.findViewById(R.id.txt_product_name);
            txt_price = (TextView) itemView.findViewById(R.id.txt_price);
           detelte = (ImageView) itemView.findViewById(R.id.delete);

        }
    }
}
