package com.rajendra.vacationtourapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.airbnb.lottie.LottieAnimationView;
import com.rajendra.vacationtourapp.User.LoginActivity;

public class    IntroductoryActivity extends AppCompatActivity {
    ImageView logo,appName,splashImg;
    LottieAnimationView lottieAnimationView;
    private static final int NUM_PAGES=3;
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor data_editor;
    private ViewPager viewPager;
    Animation btnAnim ;
    private ScreenSlidePlagerAdapter plagerAdapter;
    Button skip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductory);
        sharedPreferences = getSharedPreferences("Exam", Context.MODE_PRIVATE);
        data_editor = sharedPreferences.edit();
        data_editor.apply();
        boolean firstTime = sharedPreferences.getBoolean("pref_first_time",false);

        if (firstTime){
           // String login=sharedPreferences.getString("login","");
            Intent intent =new Intent(IntroductoryActivity.this,LandingActivity.class);
           // intent.putExtra("login",login);
            startActivity(intent);
            finish();
        }

        logo=findViewById(R.id.logo);
        appName=findViewById(R.id.app_name);
        splashImg=findViewById(R.id.img);
        lottieAnimationView=findViewById(R.id.lottie);
        viewPager=findViewById(R.id.pager);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_animation);
        plagerAdapter=new ScreenSlidePlagerAdapter(getSupportFragmentManager(),0);
        //viewPager.setAdapter(plagerAdapter);
        splashImg.animate().translationY(-10000).setDuration(1000).setStartDelay(4000);
        logo.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        appName.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        viewPager.setAdapter(plagerAdapter);

    }

    public void skipAction(View view) {
        Intent intent=new Intent(IntroductoryActivity.this, LoginActivity.class);
        startActivity(intent);

    }

    private class ScreenSlidePlagerAdapter extends FragmentStatePagerAdapter{

        public ScreenSlidePlagerAdapter(@NonNull FragmentManager fm,int behavior)
        {
            super(fm,behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    OnBoardingFragment1 tab1=new OnBoardingFragment1();
                    return tab1;
                case 1:
                    OnBoardingFragment2 tab2=new OnBoardingFragment2();
                    return tab2;
                case 2:
                    OnBoardingFragment3 tab3=new OnBoardingFragment3();
                    return tab3;

            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}