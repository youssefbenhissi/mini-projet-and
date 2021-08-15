package com.rajendra.vacationtourapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

public class YesActivity extends AppCompatActivity {
    //Initialize variable
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yes);

        //Assign variable
        textView = (TextView) findViewById(R.id.text_view);

        //Initialize Notification Manager

        NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);

        //CLEAR notification
        manager.cancelAll();

        //check condition
        if(getIntent().hasExtra("yes")){
            //when yes button is clicked
            //set text
            textView.setText("You accepted request");

            //set text color
            textView.setTextColor(Color.GREEN);

        }else if(getIntent().hasExtra("no")){
            //when no button is clicked
            //set text
            textView.setText("You rejected request");

            //set text color
            textView.setTextColor(Color.RED);
        }


    }
}