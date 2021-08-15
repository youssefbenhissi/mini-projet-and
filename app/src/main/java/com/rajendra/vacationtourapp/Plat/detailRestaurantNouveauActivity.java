package com.rajendra.vacationtourapp.Plat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.rajendra.vacationtourapp.LandingActivity;
import com.rajendra.vacationtourapp.R;
import com.rajendra.vacationtourapp.adapter.AsiaFoodAdapter;
import com.rajendra.vacationtourapp.adapter.CategoryAdapter;
import com.rajendra.vacationtourapp.adapter.PopularFoodAdapter;
import com.rajendra.vacationtourapp.model.Category;
import com.rajendra.vacationtourapp.model.plat_restaurant;
import com.rajendra.vacationtourapp.panier.CartActivity;
import com.rajendra.vacationtourapp.retrofit.ApiInterface;
import com.rajendra.vacationtourapp.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class detailRestaurantNouveauActivity extends AppCompatActivity {

    RecyclerView discountRecyclerView, categoryRecyclerView, recentlyViewedRecycler;
    PopularFoodAdapter discountedProductAdapter;
String idRestaurant;
    List<plat_restaurant> discountedProductsList;
    CategoryAdapter categoryAdapter;
    List<Category> categoryList;
    ImageView cart,imageView2;
    AsiaFoodAdapter recentlyViewedAdapter;
    List<plat_restaurant> recentlyViewedList;

    TextView allCategory,allCategory2;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lister_plat);
        setContentView(R.layout.activity_lister_plat);
        imageView2 = findViewById(R.id.imageView2);
        imageView2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
// Launching News Feed Screen

                SharedPreferences preferences = getSharedPreferences("Exam", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                //editor.remove("pref_first_time");
                editor.remove("login");
                editor.commit();

                finish();

                Intent intent = new Intent(detailRestaurantNouveauActivity.this, LandingActivity.class);
                startActivity(intent);
            }
        });
        String idUser= (String) getIntent().getSerializableExtra("idUser");
        String emailUser= (String) getIntent().getSerializableExtra("emailUser");
        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        cart = findViewById(R.id.imageView);
        Intent intent = getIntent();
        idRestaurant = intent.getStringExtra("idRestaurant");
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(detailRestaurantNouveauActivity.this, CartActivity.class);
                intent.putExtra("idUser",idUser);
                intent.putExtra("emailUser",emailUser);
                intent.putExtra("idRestaurant",idRestaurant);
                startActivity(intent);
                //startActivity(new Intent(detailRestaurantNouveauActivity.this, CartActivity.class));
            }
        });
        discountRecyclerView = findViewById(R.id.discountedRecycler);
        categoryRecyclerView = findViewById(R.id.categoryRecycler);
        allCategory = findViewById(R.id.allCategoryImage);
        allCategory2 = findViewById(R.id.allCategoryImage2);
        recentlyViewedRecycler = findViewById(R.id.recently_item);




        // adding data to model
        discountedProductsList = new ArrayList<>();
        HashMap<String, String> map = new HashMap<>();
        //Intent intent = getIntent();
        idRestaurant = intent.getStringExtra("idRestaurant");
        map.put("idRestaurant", idRestaurant);
        allCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(detailRestaurantNouveauActivity.this, ListerPlatCategorieActivity.class);
              //  Toast.makeText(detailRestaurantNouveauActivity.this, "waaaaaaaaa"+idRestaurant, Toast.LENGTH_SHORT).show();
                i.putExtra("idRestaurant", idRestaurant);
                intent.putExtra("emailUser",emailUser);
                i.putExtra("idUser", idUser);
                i.putExtra("categorie", "");
                startActivity(i);
            }
        });
        allCategory2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(detailRestaurantNouveauActivity.this, AllCategory.class);
                i.putExtra("idRestaurant", idRestaurant);
                startActivity(i);
            }
        });

        Call<List<plat_restaurant>> call = apiInterface.afficherplatsdiscount(map);
        call.enqueue(new Callback<List<plat_restaurant>>() {
            @Override
            public void onResponse(Call<List<plat_restaurant>> call, Response<List<plat_restaurant>> response) {

                List<plat_restaurant> RestaurantList = response.body();
                for(plat_restaurant r :RestaurantList){
                    discountedProductsList.add(r);
                }


            }

            @Override
            public void onFailure(Call<List<plat_restaurant>> call, Throwable t) {
                Toast.makeText(detailRestaurantNouveauActivity.this, "Server is not responding.", Toast.LENGTH_SHORT).show();
            }
        });

        // adding data to model
        categoryList = new ArrayList<>();
        categoryList.add(new Category(1, R.drawable.ic_home_fruits,"Sandwichs"));
        categoryList.add(new Category(2, R.drawable.ic_home_fish,"Pizzas"));
        categoryList.add(new Category(3, R.drawable.ic_home_meats,"Plats"));
        //categoryList.add(new Category(4, R.drawable.ic_home_veggies));
        /*categoryList.add(new Category(5, R.drawable.ic_home_fruits));
        categoryList.add(new Category(6, R.drawable.ic_home_fish));
        categoryList.add(new Category(7, R.drawable.ic_home_meats));
        categoryList.add(new Category(8, R.drawable.ic_home_veggies));*/
        // adding data to model
       recentlyViewedList = new ArrayList<>();
        setCategoryRecycler(categoryList,idRestaurant);
        Call<List<plat_restaurant>> callrecently = apiInterface.afficherplatsparrestaurant(map);
        callrecently.enqueue(new Callback<List<plat_restaurant>>() {
            @Override
            public void onResponse(Call<List<plat_restaurant>> callrecently, Response<List<plat_restaurant>> response) {

                List<plat_restaurant> RestaurantList = response.body();
                for(plat_restaurant r :RestaurantList){
                    recentlyViewedList.add(r);
                }


            }

            @Override
            public void onFailure(Call<List<plat_restaurant>> call, Throwable t) {
                Toast.makeText(detailRestaurantNouveauActivity.this, "Server is not responding.", Toast.LENGTH_SHORT).show();
            }
        });
        setDiscountedRecycler(discountedProductsList);
        setCategoryRecycler(categoryList,idRestaurant);
       setRecentlyViewedRecycler(recentlyViewedList);

    }

    private void setDiscountedRecycler(List<plat_restaurant> dataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        discountRecyclerView.setLayoutManager(layoutManager);
        discountedProductAdapter = new PopularFoodAdapter(this,dataList);
        discountRecyclerView.setAdapter(discountedProductAdapter);
    }


    private void setCategoryRecycler(List<Category> categoryDataList,String idRestaurant) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        categoryRecyclerView.setLayoutManager(layoutManager);
        categoryAdapter = new CategoryAdapter(this,categoryDataList,idRestaurant);
        categoryRecyclerView.setAdapter(categoryAdapter);
    }

    private void setRecentlyViewedRecycler(List<plat_restaurant> recentlyViewedDataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recentlyViewedRecycler.setLayoutManager(layoutManager);
        recentlyViewedAdapter = new AsiaFoodAdapter(this,recentlyViewedDataList);
        recentlyViewedRecycler.setAdapter(recentlyViewedAdapter);
    }
    //Now again we need to create a adapter and model class for recently viewed items.
    // lets do it fast.
}
