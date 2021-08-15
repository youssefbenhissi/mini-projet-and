package com.rajendra.vacationtourapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rajendra.vacationtourapp.Restaurant.RestaurantList;
import com.rajendra.vacationtourapp.User.LoginActivity;
import com.rajendra.vacationtourapp.model.TopPlacesData;
import com.rajendra.vacationtourapp.model.User;
import com.rajendra.vacationtourapp.retrofit.ApiInterface;
import com.rajendra.vacationtourapp.retrofit.RetrofitClient;

import java.io.IOException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginRestaurantActivity extends AppCompatActivity {
    Dialog epicDialog;
    ImageView closePopupNegativeImg;
    EditText email, pass;

    FloatingActionButton floatButton;
    private Retrofit retrofit;
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor data_editor;
    private ApiInterface retrofitInterface;
    Button PositivePopupBtn, NegativePopupBtn, btnAccept, btnRetry, btnLogin;
    TextView titleTv, messageTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_restaurant);
        retrofit = RetrofitClient.getRetrofitInstance();
        retrofitInterface = retrofit.create(ApiInterface.class);
        btnLogin = findViewById(R.id.btnLogin);
        email = findViewById(R.id.inputEmail);
        pass = findViewById(R.id.inputPassword);
        floatButton = findViewById(R.id.floatButton);
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginRestaurantActivity.this, BotActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().isEmpty()) {
                    ShowNegativePopup("Le champ email est obligatoire");
                } else if (!isValidEmail(email.getText().toString())) {
                    ShowNegativePopup("email invalide");
                } else if (pass.getText().toString().isEmpty()) {
                    ShowNegativePopup("Le champ mot de passe est obligatoire");
                } else {
                    HashMap<String, String> map = new HashMap<>();

                    map.put("email", email.getText().toString());
                    map.put("password", pass.getText().toString());

                    Call<TopPlacesData> call = retrofitInterface.executeLoginRestaurant(map);
                    call.enqueue(new Callback<TopPlacesData>() {

                        @Override
                        public void onResponse(Call<TopPlacesData> call, Response<TopPlacesData> response) {
                            TopPlacesData result = response.body();
                            if (response.code() == 200) {


                                sharedPreferences = getSharedPreferences("Exam", Context.MODE_PRIVATE);
                                data_editor = sharedPreferences.edit();
                                data_editor.putBoolean("pref_first_time", Boolean.TRUE);
                                data_editor.apply();

                                Intent intent = new Intent(LoginRestaurantActivity.this, DashboardRestaurantActivity.class);
                                startActivity(intent);
                            }
                            if (response.code() == 202) {
                                ShowNegativePopup("Votre mot de passe est erroné");


                            } else if (response.code() == 400) {
                                ShowNegativePopup("Aucun Utilisateur trouvé avec ces informations d'identification ");
                            }

                        }

                        @Override
                        public void onFailure(Call<TopPlacesData> call, Throwable t) {

                        }
                    });

                }
            }
        });

        epicDialog = new Dialog(LoginRestaurantActivity.this);
    }

    public static boolean isValidEmail(CharSequence target) {

        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();

    }

    public void ShowNegativePopup(String message) {
        epicDialog.setContentView(R.layout.epic_popup_negative);
        closePopupNegativeImg = (ImageView) epicDialog.findViewById(R.id.closePopupNegativeImg);
        btnRetry = (Button) epicDialog.findViewById(R.id.btnRetry);
        titleTv = (TextView) epicDialog.findViewById(R.id.titleTv);
        titleTv.setText("Connexion a echoué");
        messageTv = (TextView) epicDialog.findViewById(R.id.messageTv);
        messageTv.setText(message);
        closePopupNegativeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog.dismiss();
            }
        });
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog.dismiss();
            }
        });
        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }
}