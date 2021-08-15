package com.rajendra.vacationtourapp.User;

import android.content.Context;
import android.content.Intent;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rajendra.vacationtourapp.BotActivity;
import com.rajendra.vacationtourapp.Helper.LocalHelper;
import com.rajendra.vacationtourapp.LoginRestaurantActivity;
import com.rajendra.vacationtourapp.R;
import com.rajendra.vacationtourapp.adapter.LoginAdapter;
import com.rajendra.vacationtourapp.retrofit.ApiInterface;
import com.rajendra.vacationtourapp.retrofit.RetrofitClient;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {
    ViewPager viewpager;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    private Retrofit retrofit;
    TextView textView15;
    private static final int SIGN_IN = 1;

    private ApiInterface retrofitInterface;
    private String BASE_URL = "http://192.168.1.6:1337";
    Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            SharePhoto photo = new SharePhoto.Builder().setBitmap(bitmap).build();
            if (ShareDialog.canShow(SharePhotoContent.class)) {
                SharePhotoContent content = new SharePhotoContent.Builder().addPhoto(photo).build();
                shareDialog.show(content);
            }
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            Toast.makeText(LoginActivity.this, "kkkkk" + errorDrawable, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
            Toast.makeText(LoginActivity.this, "aaaa", Toast.LENGTH_SHORT).show();
        }
    };
    private static final int NUM_PAGES = 2;
    private LoginActivity.ScreenSlidePlagerAdapter plagerAdapter;
    float v = 0;
    Button SignIn, SignUp;
    private CallbackManager facebookcallbackManager;
    private LoginButton loginButton;

    FloatingActionButton floatButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        floatButton = findViewById(R.id.floatButton);
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, BotActivity.class);
                startActivity(intent);
            }
        });
        textView15 = findViewById(R.id.textView15);
        retrofit = RetrofitClient.getRetrofitInstance();

        retrofitInterface = retrofit.create(ApiInterface.class);
        //tabLayout=(TabLayout) findViewById(R.id.tab_layout);
        facebookcallbackManager = CallbackManager.Factory.create();



        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        viewpager = findViewById(R.id.view_pager);
        //tabLayout.addTab(tabLayout.newTab().setText("Login"));
        //tabLayout.addTab(tabLayout.newTab().setText("Sign Up"));
        //tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final LoginAdapter adapter = new LoginAdapter(getSupportFragmentManager(), this, 2);
        viewpager.setAdapter(adapter);
        //viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        plagerAdapter = new ScreenSlidePlagerAdapter(getSupportFragmentManager(), 0);
        viewpager.setAdapter(plagerAdapter);
        Paper.init(this);
        String language = Paper.book().read("language");
        if(language ==null){
            Paper.book().write("language","en");

        }
        updateView((String)Paper.book().read("language"));

    }

    public void fonction(View view) {
        viewpager.setCurrentItem(0);
        SignIn = findViewById(R.id.SignIn);
        SignIn.setBackgroundResource(R.drawable.button_bg);
        SignUp = findViewById(R.id.SignUp);
        SignUp.setBackgroundResource(R.drawable.button_bg1);
    }

    public void fonctionIheb(View view) {
        viewpager.setCurrentItem(1);
        SignIn = findViewById(R.id.SignIn);
        SignIn.setBackgroundResource(R.drawable.button_bg1);
        SignUp = findViewById(R.id.SignUp);
        SignUp.setBackgroundResource(R.drawable.button_bg);
    }


    private static class ScreenSlidePlagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePlagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    LoginTabFragment tab1 = new LoginTabFragment();

                    return tab1;
                case 1:
                    SignupTabFragment tab2 = new SignupTabFragment();
                    return tab2;


            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
    private void updateView(String language) {
        Context context = LocalHelper.setLocale(this,language);
        Resources resources= context.getResources();
        textView15.setText(resources.getString(R.string.welcome_to_slizzing_bites));
        SignIn = findViewById(R.id.SignIn);
        SignUp = findViewById(R.id.SignUp);
        SignIn.setText(R.string.sign_in);
        SignUp.setText(R.string.sign_up);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.language_en){
            Paper.book().write("language","en");
            updateView((String)Paper.book().read("language"));
        }
        else  if(item.getItemId() == R.id.language_fr){
            Paper.book().write("language","fr");
            updateView((String)Paper.book().read("language"));
        }
        return true;
    }
}