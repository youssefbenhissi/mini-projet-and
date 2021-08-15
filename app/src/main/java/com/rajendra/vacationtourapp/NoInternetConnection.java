package com.rajendra.vacationtourapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

public class NoInternetConnection extends AppCompatActivity {
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet_connection);
        webView = findViewById(R.id.web_view);

        //initialize settings
        WebSettings websettings = webView.getSettings();
        websettings.setJavaScriptEnabled(true);

        //initialize connecvtivity Manger
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        //Get active newtork info
        NetworkInfo networkInfo  = connectivityManager.getActiveNetworkInfo();

        //check network status
        if(networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()){
            //when internet is inactive
            //initalize dialog
            Dialog dialog = new Dialog(this);
            //Set Content View
            dialog.setContentView(R.layout.alert_dialog);
            //set outside touch
            dialog.setCanceledOnTouchOutside(false);
            //set dialog width and height
            dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
            //set transparant background
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //set Animation
            dialog.getWindow().getAttributes().windowAnimations  = android.R.style.Animation_Dialog;
            //initialize dialog variable

            Button btTryAgain = dialog.findViewById(R.id.bt_try_again);
            //Perform  on Click listener
            btTryAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //call recreate
                    recreate();
                }
            });
            //show dialog
            dialog.show();
        }
        else{
            //when internet is active

            //load url in webview
            webView.loadUrl("https://m.youtube.com");
        }
    }
}