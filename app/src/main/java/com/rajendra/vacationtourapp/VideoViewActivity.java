package com.rajendra.vacationtourapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoViewActivity extends AppCompatActivity {
    VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);

        /*videoView = findViewById(R.id.video_view);

        //creating media controller
        MediaController mediaController =new MediaController(this);
        mediaController.setAnchorView(videoView);


        //Parse Video Link
        Uri uri= Uri.parse("http://www.youtube.com/watch?v=3X6ixJ5BnuA&ab_channel=DABLDE");

        //set media controller and video uri
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(uri);


        //start the videoView
        videoView.start();

*/
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=ofLFXySmCQk&ab_channel=BiGGUEB"));
        startActivity(intent);


    }
}