package com.rajendra.vacationtourapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rajendra.vacationtourapp.Plat.detailRestaurantNouveauActivity;
import com.rajendra.vacationtourapp.R;
import com.rajendra.vacationtourapp.Restaurant.DetailsActivity;
import com.rajendra.vacationtourapp.model.TopPlacesData;
import com.rajendra.vacationtourapp.retrofit.ApiInterface;
import com.rajendra.vacationtourapp.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.TopPlacesViewHolder> {

    Context context;
    List<TopPlacesData> topPlacesDataList;
    List<TopPlacesData> filterDataList;
    String idUser;
    String emailUser;
    String etatFavoris;
    ApiInterface apiInterface;
    public RestaurantListAdapter(Context context, List<TopPlacesData> topPlacesDataList,String idUser,String emailUser) {
        this.context = context;
        this.topPlacesDataList = topPlacesDataList;
        this.filterDataList = topPlacesDataList;
        this.idUser = idUser;
        this.emailUser = emailUser;
    }

    @NonNull
    @Override
    public TopPlacesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.restaurant_row_item, parent, false);

        // here we create a recyclerview row item layout file
        return new TopPlacesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopPlacesViewHolder holder, final int position) {

        holder.countryName.setText(filterDataList.get(position).getAdresse());
        holder.placeName.setText(filterDataList.get(position).getNom());
        holder.bouttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);


                HashMap<String, String> map = new HashMap<>();

                map.put("idUser", idUser);
                map.put("idResto", filterDataList.get(position).getId_resto());


                Call<Void> callRecent = apiInterface.ajouterRecentRestaurant(map);
                callRecent.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });

                Call<Void> call = apiInterface.verifyexistancefavorisrestaurant(map);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                        if (response.code() == 202) {
                            final Intent i = new Intent(context, DetailsActivity.class);
                            i.putExtra("nomResto", filterDataList.get(position).getNom());
                            i.putExtra("imageOne", filterDataList.get(position).getImage());
                            i.putExtra("ImageTwo", filterDataList.get(position).getImageTwo());
                            i.putExtra("imageThree", filterDataList.get(position).getImageThree());
                            i.putExtra("nbr_etoiles", filterDataList.get(position).getNbr_etoiles());
                            i.putExtra("description", filterDataList.get(position).getDescription());
                            i.putExtra("idResto", filterDataList.get(position).getId_resto());
                            i.putExtra("nbr_defois", filterDataList.get(position).getNbrFois());
                            i.putExtra("somme_rating", filterDataList.get(position).getSommeRating());
                            i.putExtra("link_restaurant", filterDataList.get(position).getLink_restaurant());
                            i.putExtra("adresse", filterDataList.get(position).getAdresse());
                            i.putExtra("idUser", idUser);
                            i.putExtra("emailUser", emailUser);
                            i.putExtra("etat", "absent");
                            context.startActivity(i);
                        } else if (response.code() == 200) {
                            final Intent i = new Intent(context, DetailsActivity.class);
                            i.putExtra("nomResto", filterDataList.get(position).getNom());
                            i.putExtra("imageOne", filterDataList.get(position).getImage());
                            i.putExtra("ImageTwo", filterDataList.get(position).getImageTwo());
                            i.putExtra("imageThree", filterDataList.get(position).getImageThree());
                            i.putExtra("nbr_etoiles", filterDataList.get(position).getNbr_etoiles());
                            i.putExtra("description", filterDataList.get(position).getDescription());
                            i.putExtra("idResto", filterDataList.get(position).getId_resto());
                            i.putExtra("nbr_defois", filterDataList.get(position).getNbrFois());
                            i.putExtra("somme_rating", filterDataList.get(position).getSommeRating());
                            i.putExtra("link_restaurant", filterDataList.get(position).getLink_restaurant());
                            i.putExtra("adresse", filterDataList.get(position).getAdresse());
                            i.putExtra("idUser", idUser);
                            i.putExtra("emailUser", emailUser);
                            i.putExtra("etat", "present");
                            context.startActivity(i);
                        }

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("waaa",""+t.getCause());
                    }
                });



            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, detailRestaurantNouveauActivity.class);
                i.putExtra("idRestaurant", filterDataList.get(position).getId_resto());
                i.putExtra("idUser", idUser);
                i.putExtra("emailUser", emailUser);
                context.startActivity(i);
            }
        });

        Glide.with(context).load(filterDataList.get(position).getImage()).into(holder.placeImage);
       

        //holder.placeImage.setImageResource(topPlacesDataList.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return filterDataList.size();
    }

    public static final class TopPlacesViewHolder extends RecyclerView.ViewHolder{

        ImageView placeImage,bouttonNext;
        TextView placeName, countryName;
        public TopPlacesViewHolder(@NonNull View itemView) {
            super(itemView);

            placeImage = itemView.findViewById(R.id.place_image);
            placeName = itemView.findViewById(R.id.place_name);
            countryName = itemView.findViewById(R.id.country_name);
            bouttonNext = itemView.findViewById(R.id.imageView2);
        }
    }
    public Filter getFilter(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String key =charSequence.toString();
                if(key.isEmpty()){
                    filterDataList = topPlacesDataList;
                }
                else{
                    List<TopPlacesData> listFirltered = new ArrayList<>();
                    for(TopPlacesData t: topPlacesDataList){
                        if(t.getNom().toLowerCase().contains(key.toLowerCase())){
                            listFirltered.add(t);
                        }
                    }
                    filterDataList = listFirltered;
                }
                FilterResults filterResults =new FilterResults();
                filterResults.values = filterDataList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filterDataList = (List<TopPlacesData>)filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
