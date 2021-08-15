package com.rajendra.vacationtourapp;

import android.app.Application;
import android.content.Context;

import com.rajendra.vacationtourapp.Helper.LocalHelper;

public class MainApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocalHelper.onAttach(base,"en"));
    }
}
