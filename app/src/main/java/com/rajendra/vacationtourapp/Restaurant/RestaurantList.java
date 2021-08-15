package com.rajendra.vacationtourapp.Restaurant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.rajendra.vacationtourapp.R;
import com.rajendra.vacationtourapp.adapter.RecentlyRestaurantsViewedAdapter;
import com.rajendra.vacationtourapp.adapter.RestaurantListAdapter;
import com.rajendra.vacationtourapp.model.TopPlacesData;
import com.rajendra.vacationtourapp.model.recentRestaurant;
import com.rajendra.vacationtourapp.retrofit.ApiInterface;
import com.rajendra.vacationtourapp.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantList extends AppCompatActivity {
    AdView adView1,adView2;
    InterstitialAd interstitialAd;
    RecyclerView recentRecycler, topPlacesRecycler;
    RecentlyRestaurantsViewedAdapter recentlyRestaurantsViewedAdapter;
    RestaurantListAdapter restaurantListAdapter;
    ApiInterface apiInterface;
    List<String> idrestaurants =new ArrayList<>();
    EditText searchEditText;
    CharSequence search="";
    String idUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actiiy_show_restaurant);
        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);

        adView2 = findViewById(R.id.ad_view2);

        MobileAds.initialize(this,"ca-app-pub-2436093138329511~8707723900");
        AdRequest adRequest = new AdRequest.Builder().build();

        adView2.loadAd(adRequest);
        interstitialAd =  new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-2436093138329511/3462124325");
        interstitialAd.loadAd(new AdRequest.Builder().build());
        // Now here we will add some dummy data in our model class

        final List<TopPlacesData> recentsDataList = new ArrayList<>();



        Intent intent = getIntent();



        final List<TopPlacesData> topPlacesDataList = new ArrayList<>();

        Call<List<TopPlacesData>> call = apiInterface.getAllRestaurents();
        call.enqueue(new Callback<List<TopPlacesData>>() {
            @Override
            public void onResponse(Call<List<TopPlacesData>> call, Response<List<TopPlacesData>> response) {

                List<TopPlacesData> RestaurantList = response.body();
                for(TopPlacesData r :RestaurantList){
                    topPlacesDataList.add(r);
                }


            }

            @Override
            public void onFailure(Call<List<TopPlacesData>> call, Throwable t) {
                Toast.makeText(RestaurantList.this, "Server is not responding.", Toast.LENGTH_LONG).show();
            }
        });


        HashMap<String, String> map = new HashMap<>();
        String idUser= (String) getIntent().getSerializableExtra("idUser");
        map.put("idUser", idUser);
        Call<List<recentRestaurant>> callRecent = apiInterface.afficherRecentRestaurant(map);
        callRecent.enqueue(new Callback<List<recentRestaurant>>() {
            @Override
            public void onResponse(Call<List<recentRestaurant>> call, Response<List<recentRestaurant>> response) {
                List<recentRestaurant> kkk = response.body();
                for(recentRestaurant e :kkk){
                   // Toast.makeText(RestaurantList.this, ""+e.getId_resto(), Toast.LENGTH_SHORT).show();
                    for(TopPlacesData t:topPlacesDataList){
                        if(e.getId_resto().equals(t.getId_resto())){

                            recentsDataList.add(t);
                        }
                    }
                }
                
            }

            @Override
            public void onFailure(Call<List<recentRestaurant>> call, Throwable t) {

            }
        });
        String emailUser= (String) getIntent().getSerializableExtra("emailUser");
        setTopPlacesRecycler(topPlacesDataList,idUser,emailUser);
        setRecentRecycler(recentsDataList);
        searchEditText = findViewById(R.id.search);
        searchEditText.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    restaurantListAdapter.getFilter().filter(charSequence);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    });
    }

    private  void setRecentRecycler(List<TopPlacesData> recentsDataList){

        recentRecycler = findViewById(R.id.recent_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recentRecycler.setLayoutManager(layoutManager);
        recentlyRestaurantsViewedAdapter = new RecentlyRestaurantsViewedAdapter(this, recentsDataList);
        recentRecycler.setAdapter(recentlyRestaurantsViewedAdapter);

    }

    private  void setTopPlacesRecycler(List<TopPlacesData> topPlacesDataList,String idUser,String emailUser){

        topPlacesRecycler = findViewById(R.id.top_places_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        topPlacesRecycler.setLayoutManager(layoutManager);
        restaurantListAdapter = new RestaurantListAdapter(this, topPlacesDataList,idUser,emailUser);
        topPlacesRecycler.setAdapter(restaurantListAdapter);

    }

}
