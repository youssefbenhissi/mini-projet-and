package com.rajendra.vacationtourapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NotificationActionActivity extends AppCompatActivity {
    Button btShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_action);

        //Assign Variables
        btShow = findViewById(R.id.bt_show);

        btShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Initialize notification manager
                NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

                //Initialize intent for yes button
                Intent intent1 = new Intent(NotificationActionActivity.this, YesActivity.class);

                //Put extra
                intent1.putExtra("yes", true);

                // add Flags
                intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                //initialize pending intent
                PendingIntent pendingIntent1 = PendingIntent.getActivity(NotificationActionActivity.this, 0, intent1, PendingIntent.FLAG_ONE_SHOT);

                //initialize intent for no button

                Intent intent2 = new Intent(NotificationActionActivity.this, YesActivity.class);

                //Put extra
                intent2.putExtra("no", false);

                //Add flags
                intent2.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                //Initialize pending intent

                PendingIntent pendingIntent2 = PendingIntent.getActivity(
                        NotificationActionActivity.this, 1, intent2, PendingIntent.FLAG_ONE_SHOT
                );

                //Get default ringtone uri
                Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                //initialize notification builder
                NotificationCompat.Builder builder = new NotificationCompat.Builder(
                        NotificationActionActivity.this,getString(R.string.app_name)
                );

                //Set notification title
                builder.setContentTitle("Request");

                //set Content text
                builder.setContentText("Are you sure you want to accept reuest ? ");

                //set icon
                builder.setSmallIcon(R.drawable.ic_notification);

                //set sound
                builder.setSound(uri);

                //set auto cancel
                builder.setAutoCancel(true);

                //set priority
                builder.setPriority(NotificationCompat.PRIORITY_HIGH);

                //perform action  on yes button
                builder.addAction(R.drawable.ic_launcher_foreground,"Yes",pendingIntent1);

                //perform action  on no button
                builder.addAction(R.drawable.ic_launcher_foreground,"No",pendingIntent2);

                //Notify Manager
                manager.notify(1,builder.build());





            }
        });
    }
}