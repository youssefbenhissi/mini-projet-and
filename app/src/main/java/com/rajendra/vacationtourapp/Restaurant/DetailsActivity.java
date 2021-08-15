package com.rajendra.vacationtourapp.Restaurant;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.hsalf.smilerating.SmileRating;
import com.hsalf.smileyrating.SmileyRating;
import com.makeramen.roundedimageview.RoundedImageView;
import com.rajendra.vacationtourapp.AutoImageSliderActivity;
import com.rajendra.vacationtourapp.LoginActivity;
import com.rajendra.vacationtourapp.R;
import com.rajendra.vacationtourapp.model.TopPlacesData;
import com.rajendra.vacationtourapp.retrofit.ApiInterface;
import com.rajendra.vacationtourapp.retrofit.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {
    SmileyRating smile;
    ImageView imageviewOne, imageviewTwo, imageviewThree, imageviewTop, imgaeFavoris, imageView4;
    TextView nbrEtoiles, textView14, textView6,text,Region,Adresse;
    String urlImageOne,adresse, urlImageTwo, urlImagethree, nbr_etoiles, description, nomResto, idResto, etat, nbr_defois, somme_rating,link_restaurant;
    ApiInterface apiInterface;
    CallbackManager callbackManager;
    private LoginButton loginButton;

    ShareButton sbPhoto,sbLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        callbackManager = CallbackManager.Factory.create();
        loginButton = findViewById(R.id.login_button);
        loginButton.setPermissions(Arrays.asList("user_gender, user_friends"));
        sbLink = findViewById(R.id.sb_link);
        loginButton.registerCallback(callbackManager, new FacebookCallback() {
            @Override
            public void onSuccess(Object o) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        Intent intent = getIntent();
        imgaeFavoris = findViewById(R.id.imageView11);
        urlImageOne = intent.getStringExtra("imageOne");
        urlImageTwo = intent.getStringExtra("ImageTwo");
        urlImagethree = intent.getStringExtra("imageThree");
        nbr_etoiles = intent.getStringExtra("nbr_etoiles");
        description = intent.getStringExtra("description");
        adresse = intent.getStringExtra("adresse");
        nomResto = intent.getStringExtra("nomResto");
        idResto = intent.getStringExtra("idResto");
        etat = intent.getStringExtra("etat");
        somme_rating = intent.getStringExtra("somme_rating");
        nbr_defois = intent.getStringExtra("nbr_defois");
        link_restaurant = intent.getStringExtra("link_restaurant");

        imageviewOne = findViewById(R.id.imageView8);
        Adresse = findViewById(R.id.textView11);
        imageView4 = findViewById(R.id.imageView4);
        imageviewTwo = findViewById(R.id.imageView9);
        imageviewThree = findViewById(R.id.imageView10);
        imageviewThree = findViewById(R.id.imageView10);
        imageviewTop = findViewById(R.id.imageView3);
        nbrEtoiles = findViewById(R.id.textView7);
        textView14 = findViewById(R.id.textView14);
        textView6 = findViewById(R.id.textView6);
        text = findViewById(R.id.text);
        nbrEtoiles.setText(nbr_etoiles);
        textView14.setText(description);
        textView6.setText(nomResto);
        Adresse.setText(adresse);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailsActivity.this, AutoImageSliderActivity.class);
                startActivity(intent);
            }
        });
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailsActivity.this, RestaurantList.class);
                startActivity(intent);
            }
        });


        Glide.with(this).load(urlImageOne).into(imageviewOne);
        Glide.with(this).load(urlImageTwo).into(imageviewTwo);
        Glide.with(this).load(urlImagethree).into(imageviewThree);

        Glide.with(this).load(urlImageOne).into(imageviewTop);

        imageviewOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(view).load(urlImageOne).into(imageviewTop);
            }
        });
        imageviewTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(view).load(urlImageTwo).into(imageviewTop);
            }
        });
        imageviewThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(view).load(urlImagethree).into(imageviewTop);
            }
        });


        smile = findViewById(R.id.smile_rating);
        HashMap<String, String> map = new HashMap<>();
        String idUser= (String) getIntent().getSerializableExtra("idUser");

        map.put("idUser", idUser);
        map.put("idResto", idResto);
        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);

        Call<Void> call = apiInterface.verifyexistancereratingrestuarant(map);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if (response.code() == 202) {
                      smile.disallowSelection(true);
                } else if (response.code() == 200) {

                    smile.disallowSelection(false);
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("waaa", "" + t.getCause());
            }
        });


        smile.setSmileySelectedListener(new SmileyRating.OnSmileySelectedListener() {
            @Override
            public void onSmileySelected(SmileyRating.Type type) {
                // You can compare it with rating Type
                if (SmileyRating.Type.GREAT == type) {
                    Toast.makeText(DetailsActivity.this, "Wow, the user gave high rating", Toast.LENGTH_SHORT).show();
                }
                // You can get the user rating too
                // rating will between 1 to 5
                HashMap<String, String> map = new HashMap<>();
                int s = Integer.parseInt(somme_rating) + type.getRating();
                somme_rating = Integer.toString(s);

                int f = Integer.parseInt(nbr_defois) + 1;
                nbr_defois = Integer.toString(f);
                String idUser= (String) getIntent().getSerializableExtra("idUser");

                map.put("idUser", idUser);
                map.put("idResto", idResto);
                map.put("nbr_etoiles", Integer.toString(s / f));
                map.put("nbr_defois", nbr_defois);
                map.put("somme_rating", somme_rating);
                nbrEtoiles.setText(Integer.toString(s / f));
                Call<Void> call = apiInterface.ajouterRatingRestaurant(map);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(DetailsActivity.this, "On a enregistré votre rating", Toast.LENGTH_SHORT).show();

                        smile.disallowSelection(true);
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });

            }
        });


///////////////////////////////////////////////////////////////////////////////////:FACEBOOK SHARING//////////////////////////////////////////////::
        callbackManager = CallbackManager.Factory.create();


        imgaeFavoris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        DetailsActivity.this, R.style.BottomSheetDialogTheme
                );
                View bottomSheetView = LayoutInflater.from(getApplicationContext())
                        .inflate(
                                R.layout.layout_bottom_sheet,
                                (LinearLayout)findViewById(R.id.bottomSheetContainer)
                        );
                TextView nomRestaurant = (TextView)bottomSheetView.findViewById(R.id.nomRestaurant);
                nomRestaurant.setText(nomResto);
                ImageView imgRestaurant = (ImageView)bottomSheetView.findViewById(R.id.imageresto);
                Glide.with(DetailsActivity.this).load(urlImageOne).into(imgRestaurant);
                TextView adresseRestaurant = (TextView)bottomSheetView.findViewById(R.id.adresseresto);
                adresseRestaurant.setText(adresse);
                //imgRestaurant.setImageResource(R.drawable.ic_favorite);
                bottomSheetView.findViewById(R.id.viewRoute).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                            DisplayTrack(adresse);

                       // Toast.makeText(DetailsActivity.this,"Share...",Toast.LENGTH_SHORT).show();
                        //bottomSheetDialog.dismiss();
                    }
                });
               /* bottomSheetView.findViewById(R.id.buttonShare).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(DetailsActivity.this,"Share...",Toast.LENGTH_SHORT).show();
                        bottomSheetDialog.dismiss();
                    }
                });*/
                if (etat.equals("absent")) {

                    ImageView imgfavoris = (ImageView)bottomSheetView.findViewById(R.id.imageFavoris);
                    imgfavoris.setImageResource(R.drawable.ic_favorite);
                }
                else{
                    ImageView imgfavoris = (ImageView)bottomSheetView.findViewById(R.id.imageFavoris);
                    imgfavoris.setImageResource(R.drawable.ic_baseline_favorite_24);
                }
                bottomSheetView.findViewById(R.id.addFovoris).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (etat.equals("absent")) {
                            //AJOUETR AU FAVORIS

                            HashMap<String, String> map = new HashMap<>();
                            String idUser= (String) getIntent().getSerializableExtra("idUser");

                            map.put("idUser", idUser);
                            map.put("idResto", idResto);
                            etat = "present";
                            if (etat.equals("absent")) {

                                ImageView imgfavoris = (ImageView)bottomSheetView.findViewById(R.id.imageFavoris);
                                imgfavoris.setImageResource(R.drawable.ic_favorite);
                            }
                            else{
                                ImageView imgfavoris = (ImageView)bottomSheetView.findViewById(R.id.imageFavoris);
                                imgfavoris.setImageResource(R.drawable.ic_baseline_favorite_24);
                            }
                            Call<Void> call = apiInterface.ajouterFavorisRestaurant(map);
                            call.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    Toast.makeText(DetailsActivity.this, "On ajoute votre restaurant au favoris", Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {

                                }
                            });
                        } else if (etat.equals("present")) {
                            //SUPPRIMER  FAVORIS

                            HashMap<String, String> map = new HashMap<>();
                            String idUser= (String) getIntent().getSerializableExtra("idUser");
                            map.put("idUser", idUser);
                            map.put("idResto", idResto);
                            etat = "absent";
                            if (etat.equals("absent")) {

                                ImageView imgfavoris = (ImageView)bottomSheetView.findViewById(R.id.imageFavoris);
                                imgfavoris.setImageResource(R.drawable.ic_favorite);
                            }
                            else{
                                ImageView imgfavoris = (ImageView)bottomSheetView.findViewById(R.id.imageFavoris);
                                imgfavoris.setImageResource(R.drawable.ic_baseline_favorite_24);
                            }
                            Call<Void> call = apiInterface.supprimerFavorisRestaurant(map);
                            call.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {

                                    Toast.makeText(DetailsActivity.this, "On a supprimé votre restaurant du favoris", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {

                                }
                            });
                        }
                    }
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });


    }
    private void DisplayTrack(String sDestination) {

        try {
            Uri uri = Uri.parse("https://www.google.co.in/maps/dir/Current+Location/"+ sDestination);

            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }catch (ActivityNotFoundException e){
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");

            Intent intent = new Intent(Intent.ACTION_VIEW,uri);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        ShareLinkContent shareLinkContent = new ShareLinkContent.Builder().setContentUrl(Uri.parse(link_restaurant)).setShareHashtag(new ShareHashtag.Builder().setHashtag("#Restaurant").build()).build();
        sbLink.setShareContent(shareLinkContent);
        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String name = object.getString("name");
                    String id = object.getString("id");
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

}
