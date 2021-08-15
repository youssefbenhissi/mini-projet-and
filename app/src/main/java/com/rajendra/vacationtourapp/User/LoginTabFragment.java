package com.rajendra.vacationtourapp.User;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.rajendra.vacationtourapp.R;
import com.rajendra.vacationtourapp.Restaurant.RestaurantList;
import com.rajendra.vacationtourapp.model.User;
import com.rajendra.vacationtourapp.retrofit.ApiInterface;
import com.rajendra.vacationtourapp.retrofit.RetrofitClient;

import java.io.IOException;
import java.util.HashMap;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginTabFragment extends Fragment {
    EditText email,pass;
    TextView forgetPass;
    EditText verifCade;
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor data_editor;
    Button login;
    private Retrofit retrofit;
    private ApiInterface retrofitInterface;
    private String BASE_URL = "http://192.168.1.6:1337";

    Dialog epicDialog;
    Button PositivePopupBtn,NegativePopupBtn,btnAccept,btnRetry;
    TextView titleTv,messageTv;
    ImageView closePopupPositiveImg,closePopupNegativeImg;

    Button positivePopupBtn,negativePopupBtn;
    float v=0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup root=(ViewGroup)inflater.inflate(R.layout.login_tab_fragment,container,false);
        epicDialog=new Dialog(getContext());

        //data_editor.apply();

        email=root.findViewById(R.id.email);
        retrofit =   RetrofitClient.getRetrofitInstance();

        retrofitInterface = retrofit.create(ApiInterface.class);
        pass=root.findViewById(R.id.pass);
        forgetPass=root.findViewById(R.id.forget_pass);
        login=root.findViewById(R.id.login);
        email.setTranslationY(800);
        pass.setTranslationY(800);
        forgetPass.setTranslationY(800);
        login.setTranslationY(800);
        email.setAlpha(v);
        pass.setAlpha(v);
        forgetPass.setAlpha(v);
        login.setAlpha(v);
        email.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();
        pass.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();
        forgetPass.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();
        login.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(700).start();
        forgetPass.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                HashMap<String, String> mapVerif = new HashMap<>();
                mapVerif.put("email", email.getText().toString());
                Call<Void> callBlock = retrofitInterface.executeForget(mapVerif);
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

                StrictMode.setThreadPolicy(policy);
                try {

                    callBlock.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ShowPositivePopup();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().isEmpty()){
                    ShowNegativePopup("Le champ email est obligatoire");
                }
                else if (!isValidEmail(email.getText().toString())) {
                    ShowNegativePopup("email invalide");
                }
                else if(pass.getText().toString().isEmpty()){
                    ShowNegativePopup("Le champ mot de passe est obligatoire");
                }
                else {

                    HashMap<String, String> map = new HashMap<>();

                    map.put("email", email.getText().toString());
                    map.put("password", pass.getText().toString());

                    Call<User> call = retrofitInterface.executeLogin(map);

                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {

                            User result = response.body();

                            if (response.code() == 200) {

                                if (result.getEtat() == 0) {
                                    //Toast.makeText(getContext(), ""+result.getNbrdefois(), Toast.LENGTH_SHORT).show();
                                    ShowPositivePopup();
                                } else {
                                    sharedPreferences = getActivity().getSharedPreferences("Exam", Context.MODE_PRIVATE);
                                    data_editor = sharedPreferences.edit();
                                    data_editor.putBoolean("pref_first_time", Boolean.TRUE);
                                    data_editor.apply();

                                    Intent intent =new Intent(getContext(), RestaurantList.class);

                                    intent.putExtra("idUser",result.getId());
                                    intent.putExtra("emailUser",result.getEmail());
                                    startActivity(intent);
                                }

                            }
                            if (response.code() == 202) {
                                if (result.getNbrdefois() > 2) {
                                    HashMap<String, String> mapBlock = new HashMap<>();
                                    mapBlock.put("email", email.getText().toString());
                                    Call<Void> callBlock = retrofitInterface.executeBlock(mapBlock);
                                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

                                    StrictMode.setThreadPolicy(policy);
                                    try {

                                        callBlock.execute();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    ShowNegativePopup("vous etes bloqué.");

                                } else {
                                    ShowNegativePopup("Mauvaises informations d'identification. Il vous reste " + (3 - (result.getNbrdefois())) + " tentatives");

                                }

                            } else if (response.code() == 400) {
                                ShowNegativePopup("Aucun Utilisateur trouvé avec ces informations d'identification ");

                            }

                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Log.d("waaa", "" + t.getCause());
                        }
                    });
                }
            }
        });

        return root;
    }
    public void ShowNegativePopup(String message) {
        epicDialog.setContentView(R.layout.epic_popup_negative);
        closePopupNegativeImg = (ImageView) epicDialog.findViewById(R.id.closePopupNegativeImg);
        btnRetry= (Button) epicDialog.findViewById(R.id.btnRetry);
        titleTv= (TextView) epicDialog.findViewById(R.id.titleTv);
        titleTv.setText("Connexion a echoué");
        messageTv=(TextView) epicDialog.findViewById(R.id.messageTv);
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
    public static boolean isValidEmail(CharSequence target) {

        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();

    }
    public void ShowPositivePopup() {
        epicDialog.setContentView(R.layout.epic_popup_positive);
        closePopupPositiveImg = (ImageView) epicDialog.findViewById(R.id.closePopupPositiveImg);
        btnAccept= (Button) epicDialog.findViewById(R.id.btnAccept);
        titleTv= (TextView) epicDialog.findViewById(R.id.titleTv);
        messageTv=(TextView) epicDialog.findViewById(R.id.messageTv);
        verifCade = (EditText) epicDialog.findViewById(R.id.code);

        closePopupPositiveImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog.dismiss();
            }
        });
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> map = new HashMap<>();

                map.put("email", email.getText().toString());
                map.put("code", verifCade.getText().toString());

                Call<User> call  = retrofitInterface.executeVerification(map);

                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {

                        if (response.code() == 202) {
                            ShowNegativePopup("Le code est erroné");
                        } else if (response.code() == 200) {
                            User result = response.body();
                            Intent intent =new Intent(getContext(), RestaurantList.class);
                            intent.putExtra("idUser",result.getId());
                            intent.putExtra("emailUser",result.getEmail());
                            startActivity(intent);
                        }

                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.d("waaa",""+t.getCause());
                    }
                });

            }
        });
        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }

}
