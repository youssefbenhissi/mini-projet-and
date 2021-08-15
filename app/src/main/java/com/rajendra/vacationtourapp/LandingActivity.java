package com.rajendra.vacationtourapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.FacebookSdk;
import com.rajendra.vacationtourapp.User.LoginActivity;
import com.rajendra.vacationtourapp.adapter.PlateAdapter;
import com.rajendra.vacationtourapp.model.PlateModel;

import java.util.ArrayList;
import java.util.List;

public class LandingActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<PlateModel> plateModelList;
    private PlateAdapter plateAdapter;
    private TextView bouttonClient,bouttonRestaurant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_activity);
        FacebookSdk.sdkInitialize(getApplicationContext());

        //////////////////HIDE STATUS BAR START////////////////////////////////////////////::
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            Window w=getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        bouttonClient=findViewById(R.id.bouttonClient);
        bouttonRestaurant=findViewById(R.id.bouttonRestaurant);
        bouttonRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LandingActivity.this, LoginRestaurantActivity.class);
                startActivity(intent);
            }
        });
        bouttonClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LandingActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager =new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setKeepScreenOn(true);
        recyclerView.setHasFixedSize(true);

        plateModelList=new ArrayList<>();
        plateModelList.add(new PlateModel(R.drawable.plate1));
        plateModelList.add(new PlateModel(R.drawable.plate2));
        plateModelList.add(new PlateModel(R.drawable.plate3));
        plateModelList.add(new PlateModel(R.drawable.plate4));
        plateAdapter=new PlateAdapter(plateModelList,this);
        recyclerView.setAdapter(plateAdapter);
        plateAdapter.notifyDataSetChanged();

        ///////////////////////////: call autoscroll start ///////////
        autoScroll();

        ///////////////////////////: call autoscroll end ///////////
    }



    public void autoScroll(){
        final int speedScrool=0;
        final Handler handler=new Handler();
        final Runnable runnable= new Runnable() {
            int count=0;
            @Override
            public void run() {
                if(count==plateAdapter.getItemCount()){
                    count=0;
                }
                if(count<plateAdapter.getItemCount()){
                    recyclerView.smoothScrollToPosition(++count);
                    handler.postDelayed(this,speedScrool);
                }
            }
        };
        handler.postDelayed(runnable, speedScrool);
    }
}