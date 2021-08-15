package com.rajendra.vacationtourapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rajendra.vacationtourapp.Plat.listPlat;
import com.rajendra.vacationtourapp.model.plat_restaurant;
import com.rajendra.vacationtourapp.retrofit.ApiInterface;
import com.rajendra.vacationtourapp.retrofit.RetrofitClient;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatisticsRestaurantActivity extends AppCompatActivity {
    ImageView back;
    TextView textView12;
    ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_restaurant);


        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        back = findViewById(R.id.imageView);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i =new Intent(StatisticsRestaurantActivity.this, DashboardRestaurantActivity.class);
                startActivity(i);
                finish();

            }
        });
        textView12 = findViewById(R.id.textView12);
        HashMap<String, String> map = new HashMap<>();
        map.put("idResto", "1");
        Call<Integer> callRandom = apiInterface.statistiquesplats(map);
        callRandom.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> callRandom, Response<Integer> response) {

                int RestaurantList = response.body();
                textView12.setText(String.valueOf(RestaurantList));

            }

            @Override
            public void onFailure(Call<Integer> callRandom, Throwable t) {
                Toast.makeText(StatisticsRestaurantActivity.this, "Server is not responding.", Toast.LENGTH_LONG).show();
            }
        });
    }
}