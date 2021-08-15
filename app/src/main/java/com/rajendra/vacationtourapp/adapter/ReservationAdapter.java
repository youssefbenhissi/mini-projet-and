package com.rajendra.vacationtourapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.rajendra.vacationtourapp.R;
import com.rajendra.vacationtourapp.model.reservation;

import java.util.List;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.CourseViewHolder> {

    Context context;
    List<reservation> playLists;

    public ReservationAdapter(Context context, List<reservation> playLists) {
        this.context = context;
        this.playLists = playLists;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.course_list_row_items, parent, false);

        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {

        int i=position+1;
        holder.contentNumber.setText("0"+i);
        holder.contentTime.setText(playLists.get(position).getDate());
        holder.contentName.setText(playLists.get(position).getFirst_name());

    }

    @Override
    public int getItemCount() {
        return playLists.size();
    }


    public static class CourseViewHolder extends RecyclerView.ViewHolder{


        TextView contentNumber, contentTime, contentName;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);

            contentName = itemView.findViewById(R.id.content_title);
            contentTime = itemView.findViewById(R.id.content_time);
            contentNumber = itemView.findViewById(R.id.content_number);

        }
    }

}
