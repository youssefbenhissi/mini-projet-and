package com.rajendra.vacationtourapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

public class AutoImageSliderActivity extends AppCompatActivity {
    SliderView sliderView;
    int  [] images = {R.drawable.asiafood1,R.drawable.asiafood2,R.drawable.ic_home_fish};
    SliderAdp sliderAdp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_image_slider);
        sliderView=findViewById(R.id.slider_view);
        sliderAdp = new SliderAdp(images);
        sliderView.setSliderAdapter(sliderAdp);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.setAutoCycle(true);
    }
}