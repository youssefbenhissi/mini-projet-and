package com.rajendra.vacationtourapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rajendra.vacationtourapp.R;
import com.rajendra.vacationtourapp.model.TopPlacesData;

import java.util.List;

public class RecentlyRestaurantsViewedAdapter extends RecyclerView.Adapter<RecentlyRestaurantsViewedAdapter.RecentsViewHolder> {

    Context context;
    List<TopPlacesData> recentsDataList;

    public RecentlyRestaurantsViewedAdapter(Context context, List<TopPlacesData> recentsDataList) {
        this.context = context;
        this.recentsDataList = recentsDataList;
    }

    @NonNull
    @Override
    public RecentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recent_restaurant_row_item, parent, false);

        // here we create a recyclerview row item layout file
        return new RecentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentsViewHolder holder, int position) {

        holder.countryName.setText(recentsDataList.get(position).getAdresse());
        holder.placeName.setText(recentsDataList.get(position).getNom());
        Glide.with(context).load(recentsDataList.get(position).getImage()).into(holder.placeImage);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Intent i=new Intent(context, DetailsActivity.class);
                //context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return recentsDataList.size();
    }

    public static final class RecentsViewHolder extends RecyclerView.ViewHolder{

        ImageView placeImage;
        TextView placeName, countryName, price;

        public RecentsViewHolder(@NonNull View itemView) {
            super(itemView);

            placeImage = itemView.findViewById(R.id.place_image);
            placeName = itemView.findViewById(R.id.place_name);
            countryName = itemView.findViewById(R.id.country_name);

        }
    }
}
