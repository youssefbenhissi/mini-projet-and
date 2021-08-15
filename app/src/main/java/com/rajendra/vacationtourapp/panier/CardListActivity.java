package com.rajendra.vacationtourapp.panier;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.rajendra.vacationtourapp.R;


public class CardListActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recycler_cart;
    TextView txt_final_price;
    Button btn_order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);
        toolbar = findViewById(R.id.app_bar);
        recycler_cart = findViewById(R.id.recycler_cart);
        txt_final_price = findViewById(R.id.txt_final_price);
        btn_order= findViewById(R.id.btn_order);

    }
}