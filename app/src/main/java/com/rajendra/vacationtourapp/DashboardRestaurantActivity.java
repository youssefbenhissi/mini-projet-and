package com.rajendra.vacationtourapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.rajendra.vacationtourapp.backrestaurant.ReservationActivity;
import com.rajendra.vacationtourapp.backrestaurant.commandesActivity;

public class DashboardRestaurantActivity extends AppCompatActivity {
    ImageView statistiques,commandes,logout,reservations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_restaurant);
        statistiques = findViewById(R.id.statistiques);
        commandes = findViewById(R.id.commandes);
        reservations = findViewById(R.id.reservations);
        logout = findViewById(R.id.logout);
        statistiques.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intent = new Intent(DashboardRestaurantActivity.this,StatisticsRestaurantActivity.class);
                startActivity(Intent);

            }
        });
        commandes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intent = new Intent(DashboardRestaurantActivity.this, commandesActivity.class);
                startActivity(Intent);

            }
        });
        reservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intent = new Intent(DashboardRestaurantActivity.this, ReservationActivity.class);
                startActivity(Intent);

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getSharedPreferences("Exam", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("login");
                editor.commit();
                finish();
                Intent intent = new Intent(DashboardRestaurantActivity.this, LandingActivity.class);
                startActivity(intent);

            }
        });

    }
}