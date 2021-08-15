package com.rajendra.vacationtourapp.backrestaurant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rajendra.vacationtourapp.DashboardRestaurantActivity;
import com.rajendra.vacationtourapp.R;
import com.rajendra.vacationtourapp.adapter.CourseAdapter;
import com.rajendra.vacationtourapp.model.commande;
import com.rajendra.vacationtourapp.model.plat_restaurant;
import com.rajendra.vacationtourapp.retrofit.ApiInterface;
import com.rajendra.vacationtourapp.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class commandesActivity extends AppCompatActivity {
    RecyclerView courseRecyclerView;
    //ApiInterface apiInterface;
    //CourseAdapter courseAdapter;
    ApiInterface apiInterface;
    TextView  rating, name, price;
    ImageView imageView7;
    CourseAdapter courseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commandes);
        final List<commande> recentlyViewedList = new ArrayList<>();
        imageView7 = findViewById(R.id.imageView7);
        imageView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(commandesActivity.this, DashboardRestaurantActivity.class);
                startActivity(intent);
            }
        });
        rating = findViewById(R.id.rating);
        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        courseRecyclerView = findViewById(R.id.course_recycler);

        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        HashMap<String, String> map = new HashMap<>();
        map.put("idResto", "1");
        Call<List<commande>> callRandom = apiInterface.affichercommandes(map);
        callRandom.enqueue(new Callback<List<commande>>() {
            @Override
            public void onResponse(Call<List<commande>> callRandom, Response<List<commande>> response)
            {
                List<commande> RestaurantList = response.body();
                for(commande r :RestaurantList){
                    recentlyViewedList.add(r);
                    //Toast.makeText(commandesActivity.this, ""+r.toString(), Toast.LENGTH_SHORT).show();
                    // Toast.makeText(listPlat.this, ""+r.getNom(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<commande>> callRandom, Throwable t) {
                //Toast.makeText(getContext(), "Server is not responding.", Toast.LENGTH_SHORT).show();
            }
        });
        getCourseContent(recentlyViewedList);
    }
    private void getCourseContent(List<commande> playLists){

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        courseRecyclerView.setLayoutManager(layoutManager);
        courseAdapter = new CourseAdapter(this, playLists);
        courseRecyclerView.setAdapter(courseAdapter);
        courseAdapter.notifyDataSetChanged();



    }

}