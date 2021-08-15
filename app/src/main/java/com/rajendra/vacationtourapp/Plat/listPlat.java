package com.rajendra.vacationtourapp.Plat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rajendra.vacationtourapp.R;
import com.rajendra.vacationtourapp.adapter.PlatCategorieAdapter;
import com.rajendra.vacationtourapp.adapter.PlatListAdapter;
import com.rajendra.vacationtourapp.model.PlatCategory;
import com.rajendra.vacationtourapp.model.plat_restaurant;
import com.rajendra.vacationtourapp.retrofit.ApiInterface;
import com.rajendra.vacationtourapp.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class listPlat extends AppCompatActivity {

    PlatCategorieAdapter productCategoryAdapter;
    RecyclerView productCatRecycler, prodItemRecycler;
    PlatListAdapter productAdapter;

    ApiInterface apiInterface;
String idRestaurant;
String idUser;
ImageView returnButton;
    List<plat_restaurant> recentlyViewedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plat_list_activity);

        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        List<PlatCategory> productCategoryList = new ArrayList<>();
        productCategoryList.add(new PlatCategory( "Trending"));
        productCategoryList.add(new PlatCategory( "Trending"));
        productCategoryList.add(new PlatCategory( "Trending"));
        productCategoryList.add(new PlatCategory( "Trending"));
        productCategoryList.add(new PlatCategory( "Trending"));
        setProductRecycler(productCategoryList);
        Intent intent = getIntent();
        idRestaurant = intent.getStringExtra("idRestaurant");
        idUser = intent.getStringExtra("idUser");
        recentlyViewedList = new ArrayList<>();
        HashMap<String, String> map = new HashMap<>();
        map.put("idRestaurant", idRestaurant);
        Call<List<plat_restaurant>> callRandom = apiInterface.afficherplatsparrestaurantcomplet(map);
        callRandom.enqueue(new Callback<List<plat_restaurant>>() {
            @Override
            public void onResponse(Call<List<plat_restaurant>> callRandom, Response<List<plat_restaurant>> response) {

                List<plat_restaurant> RestaurantList = response.body();
                for(plat_restaurant r :RestaurantList){
                    recentlyViewedList.add(r);
                  // Toast.makeText(listPlat.this, ""+r.getNom(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<List<plat_restaurant>> callRandom, Throwable t) {
                Toast.makeText(listPlat.this, "Server is not responding.", Toast.LENGTH_LONG).show();
            }
        });


        setProdItemRecycler(recentlyViewedList);
        for(plat_restaurant r :recentlyViewedList){
         //   recentlyViewedList.add(r);
            Toast.makeText(listPlat.this, ""+r.getNom(), Toast.LENGTH_SHORT).show();
        }
        returnButton = findViewById(R.id.back);
        String emailUser= (String) getIntent().getSerializableExtra("emailUser");
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(listPlat.this, detailRestaurantNouveauActivity.class);
             //   Toast.makeText(listPlat.this, "waaaaaaaaa"+idRestaurant, Toast.LENGTH_SHORT).show();
                i.putExtra("idRestaurant", idRestaurant);
                i.putExtra("idUser",idUser);
                i.putExtra("emailUser",emailUser);
                startActivity(i);
                finish();
            }
        });


    }

    private void setProductRecycler(List<PlatCategory> productCategoryList){

        productCatRecycler = findViewById(R.id.cat_recycler);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        productCatRecycler.setLayoutManager(layoutManager);
        productCategoryAdapter = new PlatCategorieAdapter(this, productCategoryList);
        productCatRecycler.setAdapter(productCategoryAdapter);

    }

    private void setProdItemRecycler(List<plat_restaurant> productsList){
        //Toast.makeText(this, ""+productsList, Toast.LENGTH_SHORT).show();
        prodItemRecycler = findViewById(R.id.product_recycler);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        prodItemRecycler.setLayoutManager(layoutManager);
        productAdapter = new PlatListAdapter(this, productsList,idUser);
        prodItemRecycler.setAdapter(productAdapter);

    }





}
