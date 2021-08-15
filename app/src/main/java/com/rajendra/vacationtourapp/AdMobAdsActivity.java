package com.rajendra.vacationtourapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class AdMobAdsActivity extends AppCompatActivity {
    Button btShow;
    AdView adView1,adView2;
    InterstitialAd interstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_mob_ads);
        btShow = findViewById(R.id.bt_show);
        adView1 = findViewById(R.id.ad_view1);
        adView2 = findViewById(R.id.ad_view2);

        MobileAds.initialize(this,"ca-app-pub-2436093138329511~8707723900");
        AdRequest adRequest = new AdRequest.Builder().build();
        adView1.loadAd(adRequest);
        adView2.loadAd(adRequest);
        interstitialAd =  new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-2436093138329511/3462124325");
        interstitialAd.loadAd(new AdRequest.Builder().build());

        btShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interstitialAd.show();
            }
        });

    }
}