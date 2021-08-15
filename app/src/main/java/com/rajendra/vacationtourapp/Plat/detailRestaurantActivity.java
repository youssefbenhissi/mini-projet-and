package com.rajendra.vacationtourapp.Plat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.rajendra.vacationtourapp.R;
import com.rajendra.vacationtourapp.Restaurant.RestaurantList;
import com.rajendra.vacationtourapp.adapter.AsiaFoodAdapter;
import com.rajendra.vacationtourapp.adapter.PopularFoodAdapter;
import com.rajendra.vacationtourapp.model.TopPlacesData;
import com.rajendra.vacationtourapp.model.plat_restaurant;
import com.rajendra.vacationtourapp.retrofit.ApiInterface;
import com.rajendra.vacationtourapp.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class detailRestaurantActivity extends AppCompatActivity {

    RecyclerView popularRecycler, asiaRecycler;
    PopularFoodAdapter popularFoodAdapter;
    AsiaFoodAdapter asiaFoodAdapter;
    String idRestaurant;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_restaurant);
        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);

        // now here we will add some dummy data to out model class
        Intent intent = getIntent();
        final List<plat_restaurant> popularFoodList = new ArrayList<>();
        idRestaurant = intent.getStringExtra("idRestaurant");
        HashMap<String, String> map = new HashMap<>();

        map.put("idRestaurant", idRestaurant);
        Call<List<plat_restaurant>> call = apiInterface.afficherplatsparrestaurant(map);
        call.enqueue(new Callback<List<plat_restaurant>>() {
            @Override
            public void onResponse(Call<List<plat_restaurant>> call, Response<List<plat_restaurant>> response) {

                List<plat_restaurant> RestaurantList = response.body();
                for(plat_restaurant r :RestaurantList){
                    popularFoodList.add(r);
                }


            }

            @Override
            public void onFailure(Call<List<plat_restaurant>> call, Throwable t) {
                Toast.makeText(detailRestaurantActivity.this, "Server is not responding.", Toast.LENGTH_SHORT).show();
            }
        });

        setPopularRecycler(popularFoodList);


        final List<plat_restaurant> asiaFoodList = new ArrayList<>();
        Call<List<plat_restaurant>> callRandom = apiInterface.afficherplatsparrestaurant(map);
        callRandom.enqueue(new Callback<List<plat_restaurant>>() {
            @Override
            public void onResponse(Call<List<plat_restaurant>> callRandom, Response<List<plat_restaurant>> response) {

                List<plat_restaurant> RestaurantList = response.body();
                for(plat_restaurant r :RestaurantList){
                    asiaFoodList.add(r);
                }


            }

            @Override
            public void onFailure(Call<List<plat_restaurant>> callRandom, Throwable t) {
                Toast.makeText(detailRestaurantActivity.this, "Server is not responding.", Toast.LENGTH_SHORT).show();
            }
        });

        setAsiaRecycler(asiaFoodList);

    }


    private void setPopularRecycler(List<plat_restaurant> popularFoodList) {

        popularRecycler = findViewById(R.id.popular_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        popularRecycler.setLayoutManager(layoutManager);
        //popularFoodAdapter = new PopularFoodAdapter(this, popularFoodList);
        popularRecycler.setAdapter(popularFoodAdapter);

    }

    private void setAsiaRecycler(List<plat_restaurant> asiaFoodList) {

        asiaRecycler = findViewById(R.id.asia_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        asiaRecycler.setLayoutManager(layoutManager);
        asiaFoodAdapter = new AsiaFoodAdapter(this, asiaFoodList);
        asiaRecycler.setAdapter(asiaFoodAdapter);

    }


    // Hi all,
    // Today we are going to build a food app.
    // so the first things frist lets add font and image assets
    // so lets see the design
    // now we will setup recyclerview
    // first we make a model class then adapter class.
    // now we will create a recyclerview row item layout file
    // so our adapter class is ready
    // now we will bind data with recyclerview
    // so we have successfully setup popular recyclerview
    // now same setup we need to do for asia food
    // will make model class then adapter and setup recyclerview
    // so lets do it fast.
    // so asia food setup done.
    // Now we will setup Bottom app bar
    // bottom app bar setup done if you want you can increase menu item in menu file
    // now we will setup details activity and on click listener in recyclerview row item
    // so this tutorial has been completed if you have any
    // question and query please do comment
    // Like share and subscribe
    // Thankyou for watching
    // see you in the next tutorial
}
