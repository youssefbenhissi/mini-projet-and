package com.rajendra.vacationtourapp.Plat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.rajendra.vacationtourapp.R;
import com.rajendra.vacationtourapp.adapter.AsiaFoodAdapter;
import com.rajendra.vacationtourapp.adapter.AsiaFoodcategorieAdapter;
import com.rajendra.vacationtourapp.adapter.PopularFoodAdapter;
import com.rajendra.vacationtourapp.adapter.PopularFoodcategorieAdapter;
import com.rajendra.vacationtourapp.model.AsiaFood;
import com.rajendra.vacationtourapp.model.PopularFood;
import com.rajendra.vacationtourapp.model.plat_restaurant;
import com.rajendra.vacationtourapp.retrofit.ApiInterface;
import com.rajendra.vacationtourapp.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListerPlatCategorieActivity extends AppCompatActivity {

    RecyclerView popularRecycler, asiaRecycler;
    PopularFoodcategorieAdapter popularFoodAdapter;
    AsiaFoodcategorieAdapter asiaFoodAdapter;
    ApiInterface apiInterface;
    String idRestaurant,categorie;
    String idUser;
    List<plat_restaurant> asiaFoodList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lister_plat_categorie);
        // now here we will add some dummy data to out model class

        List<PopularFood> popularFoodList = new ArrayList<>();

        popularFoodList.add(new PopularFood("Float Cake Vietnam", "$7.05", R.drawable.popularfood1));
        popularFoodList.add(new PopularFood("Chiken Drumstick", "$17.05", R.drawable.popularfood2));
        popularFoodList.add(new PopularFood("Fish Tikka Stick", "$25.05", R.drawable.popularfood3));
        popularFoodList.add(new PopularFood("Float Cake Vietnam", "$7.05", R.drawable.popularfood1));
        popularFoodList.add(new PopularFood("Chiken Drumstick", "$17.05", R.drawable.popularfood2));
        popularFoodList.add(new PopularFood("Fish Tikka Stick", "$25.05", R.drawable.popularfood3));

       // setPopularRecycler(popularFoodList);


       /* List<AsiaFood> asiaFoodList = new ArrayList<>();
        asiaFoodList.add(new AsiaFood("Chicago Pizza", "$20", R.drawable.asiafood1, "4.5", "Briand Restaurant"));
        asiaFoodList.add(new AsiaFood("Straberry Cake", "$25", R.drawable.asiafood2, "4.2", "Friends Restaurant"));
        asiaFoodList.add(new AsiaFood("Chicago Pizza", "$20", R.drawable.asiafood1, "4.5", "Briand Restaurant"));
        asiaFoodList.add(new AsiaFood("Straberry Cake", "$25", R.drawable.asiafood2, "4.2", "Friends Restaurant"));
        asiaFoodList.add(new AsiaFood("Chicago Pizza", "$20", R.drawable.asiafood1, "4.5", "Briand Restaurant"));
        asiaFoodList.add(new AsiaFood("Straberry Cake", "$25", R.drawable.asiafood2, "4.2", "Friends Restaurant"));
*/
        Intent intent = getIntent();
        idRestaurant = intent.getStringExtra("idRestaurant");
        idUser = intent.getStringExtra("idUser");
        categorie = intent.getStringExtra("categorie");

        asiaFoodList = new ArrayList<>();
        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        if (categorie.equals("")){
            HashMap<String, String> map = new HashMap<>();
            map.put("idRestaurant", idRestaurant);
            Call<List<plat_restaurant>> callRandom = apiInterface.afficherplatsparrestaurantcomplet(map);
            callRandom.enqueue(new Callback<List<plat_restaurant>>() {
                @Override
                public void onResponse(Call<List<plat_restaurant>> callRandom, Response<List<plat_restaurant>> response) {

                    List<plat_restaurant> RestaurantList = response.body();
                    for(plat_restaurant r :RestaurantList){
                        asiaFoodList.add(r);
                        // Toast.makeText(listPlat.this, ""+r.getNom(), Toast.LENGTH_SHORT).show();
                    }


                }

                @Override
                public void onFailure(Call<List<plat_restaurant>> callRandom, Throwable t) {
                  //  Toast.makeText(ListerPlatCategorieActivity.this, "Server is not responding.", Toast.LENGTH_LONG).show();
                }
            });


            setAsiaRecycler(asiaFoodList,idUser);
        }else {


            HashMap<String, String> map = new HashMap<>();
            map.put("idRestaurant", idRestaurant);
            map.put("type", categorie);
            Toast.makeText(this, idRestaurant+"   "+categorie, Toast.LENGTH_SHORT).show();
            Call<List<plat_restaurant>> callRandom = apiInterface.afficherplatsparrestaurantcompletparcategorie(map);
            callRandom.enqueue(new Callback<List<plat_restaurant>>() {
                @Override
                public void onResponse(Call<List<plat_restaurant>> callRandom, Response<List<plat_restaurant>> response) {

                    List<plat_restaurant> RestaurantList = response.body();
                    for(plat_restaurant r :RestaurantList){
                        asiaFoodList.add(r);
                        // Toast.makeText(listPlat.this, ""+r.getNom(), Toast.LENGTH_SHORT).show();
                    }


                }

                @Override
                public void onFailure(Call<List<plat_restaurant>> callRandom, Throwable t) {
                    Toast.makeText(ListerPlatCategorieActivity.this, "Server is not responding.", Toast.LENGTH_LONG).show();
                }
            });


            setAsiaRecycler(asiaFoodList,idUser);
        }



    }


    private void setPopularRecycler(List<PopularFood> popularFoodList) {

        popularRecycler = findViewById(R.id.popular_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        popularRecycler.setLayoutManager(layoutManager);
        popularFoodAdapter = new PopularFoodcategorieAdapter(this, popularFoodList);
        popularRecycler.setAdapter(popularFoodAdapter);

    }

    private void setAsiaRecycler(List<plat_restaurant> asiaFoodList,String idUser) {

        asiaRecycler = findViewById(R.id.asia_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        asiaRecycler.setLayoutManager(layoutManager);
        asiaFoodAdapter = new AsiaFoodcategorieAdapter(this, asiaFoodList, idUser);
        asiaRecycler.setAdapter(asiaFoodAdapter);

    }


}