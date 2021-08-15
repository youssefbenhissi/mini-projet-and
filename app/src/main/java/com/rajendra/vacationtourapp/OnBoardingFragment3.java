package com.rajendra.vacationtourapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class    OnBoardingFragment3 extends Fragment {
    FloatingActionButton fab;
    Animation btnAnim ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root=(ViewGroup)inflater.inflate(R.layout.fragment_on_boarding3,container,false);
        fab=root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(root.getContext(),LandingActivity.class);
                //Intent intent=new Intent(root.getContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }
}
