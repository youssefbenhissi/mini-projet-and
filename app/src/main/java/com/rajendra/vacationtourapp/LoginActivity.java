package com.rajendra.vacationtourapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.rajendra.vacationtourapp.Helper.LocalHelper;
import com.rajendra.vacationtourapp.Restaurant.RestaurantList;

import org.json.JSONException;
import org.json.JSONObject;
import com.rajendra.vacationtourapp.R;
import java.util.Arrays;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {
    TextView textView15;
    AlertDialog.Builder mBuilder;
    private Button changeLanguage;
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    ShareButton sbPhoto,sbLink;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocalHelper.onAttach(newBase,"en"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.rajendra.vacationtourapp.R.layout.activity_login);



        textView15 = findViewById(com.rajendra.vacationtourapp.R.id.textView15);
        //multi language
        Paper.init(this);
        String language = Paper.book().read("language");
        if(language ==null){
            Paper.book().write("language","en");

        }
        updateView((String)Paper.book().read("language"));


        callbackManager = CallbackManager.Factory.create();
        loginButton = findViewById(R.id.login_button);
        loginButton.setPermissions(Arrays.asList("user_gender, user_friends"));

        sbLink = findViewById(R.id.sb_link);
       // sbPhoto = findViewById(R.id.share);
        loginButton.registerCallback(callbackManager, new FacebookCallback() {
            @Override
            public void onSuccess(Object o) {
                Intent intent=new Intent(LoginActivity.this, RestaurantList.class);
                startActivity(intent);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        ShareLinkContent shareLinkContent = new ShareLinkContent.Builder().setContentUrl(Uri.parse("https://www.youtube.com/")).setShareHashtag(new ShareHashtag.Builder().setHashtag("#Youssef").build()).build();
        sbLink.setShareContent(shareLinkContent);
     //   ShareLinkContent content = new ShareLinkContent.Builder()
      //          .setContentUrl(Uri.parse("https://developers.facebook.com"))
       //         .build();
       // sbPhoto.setShareContent(content);
        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
//                Log.d("Demo", object.toString());
                //Toast.makeText(MainActivity.this, object.toString(), Toast.LENGTH_SHORT).show();
                try {
                    String name = object.getString("name");
                    String id = object.getString("id");

                    //Picasso.get().load("https://graph.facebook.com/"+id+"/picture?type=large").into(imageView);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        Bundle bundle = new Bundle();
        bundle.putString("fields", "gender,name,id,first_name,last_name, picture.width(150).height(150)");
        graphRequest.setParameters(bundle);
        graphRequest.executeAsync();
    }
    AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if (currentAccessToken == null) {
                LoginManager.getInstance().logOut();

            }
        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
    }
    private void updateView(String language) {
        Context context = LocalHelper.setLocale(this,language);
        Resources resources= context.getResources();
        textView15.setText(resources.getString(R.string.welcome_to_slizzing_bites));
       // textView.setText(resources.getString(R.string.hello));

    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.rajendra.vacationtourapp.R.menu.main_menu,menu);
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
    }*/
}