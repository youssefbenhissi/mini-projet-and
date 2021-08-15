package com.rajendra.vacationtourapp.User;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.rajendra.vacationtourapp.R;
import com.rajendra.vacationtourapp.Restaurant.RestaurantList;
import com.rajendra.vacationtourapp.model.User;
import com.rajendra.vacationtourapp.retrofit.ApiInterface;
import com.rajendra.vacationtourapp.retrofit.RetrofitClient;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignupTabFragment extends Fragment {

    Dialog epicDialog;
    EditText verifCade;
    Button PositivePopupBtn,NegativePopupBtn,btnAccept,btnRetry;
    TextView titleTv,messageTv;
    ImageView closePopupPositiveImg,closePopupNegativeImg;

    EditText nom,prenom,email,pass;
    private Retrofit retrofit;
    private ApiInterface retrofitInterface;
    Button bouttonSignUp;
    float v=0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup root=(ViewGroup)inflater.inflate(R.layout.signup_tab_fragment,container,false);
        epicDialog=new Dialog(getContext());
        bouttonSignUp=root.findViewById(R.id.bouttonSignUp);
        retrofit = RetrofitClient.getRetrofitInstance();

        retrofitInterface = retrofit.create(ApiInterface.class);

        nom=root.findViewById(R.id.nom);
        prenom=root.findViewById(R.id.prenom);
        email=root.findViewById(R.id.email);
        pass=root.findViewById(R.id.pass1);
        nom.setTranslationY(800);
        prenom.setTranslationY(800);
        email.setTranslationY(800);
        pass.setTranslationY(800);
        bouttonSignUp.setTranslationY(800);
        nom.setAlpha(v);
        prenom.setAlpha(v);
        email.setAlpha(v);
        pass.setAlpha(v);
        bouttonSignUp.setAlpha(v);
        nom.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();
        prenom.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();
        email.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();
        pass.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(700).start();
        bouttonSignUp.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(900).start();
        bouttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().isEmpty()){
                    ShowNegativePopup("Le champ email est obligatoire");
                }
                else if (!isValidEmail(email.getText().toString())) {
                    ShowNegativePopup("email invalide");
                }
                else if(prenom.getText().toString().isEmpty()){
                    ShowNegativePopup("Le champ prénom est obligatoire");
                }
                else if(nom.getText().toString().isEmpty()){
                    ShowNegativePopup("Le champ nom est obligatoire");
                }
                else if(pass.getText().toString().isEmpty()){
                    ShowNegativePopup("Le champ mot de passe est obligatoire");
                }
                else if (!checkString(pass.getText().toString())){
                    ShowNegativePopup("Le mot de passe doit contenir au moin une lettre en majuscule, une lettre en miniscule et un chiffre et sa longueur minimale est 5");
                }
                else{
                HashMap<String, String> map = new HashMap<>();

                map.put("firstName", prenom.getText().toString());
                map.put("lastName", nom.getText().toString());
                map.put("email", email.getText().toString());
                map.put("password", pass.getText().toString());

                Call<Void> call = retrofitInterface.executeSignup(map);

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                        if (response.code() == 200) {
                            ShowPositivePopup();
                          //  Intent intent=new Intent(getContext(),LandingActivityClient.class);
                            //startActivity(intent);
                        } else if (response.code() == 400) {
                            ShowNegativePopup("Email déja utilisé");
                        }

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });

            }}
        });
        return root;
    }
    public void ShowNegativePopup(String message) {
        epicDialog.setContentView(R.layout.epic_popup_negative);
        closePopupNegativeImg = (ImageView) epicDialog.findViewById(R.id.closePopupNegativeImg);
        btnRetry= (Button) epicDialog.findViewById(R.id.btnRetry);
        titleTv= (TextView) epicDialog.findViewById(R.id.titleTv);
        titleTv.setText("Inscription a echoué");
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

    private static boolean checkString(String str) {
        char ch;
        boolean capitalFlag = false;
        boolean lowerCaseFlag = false;
        boolean numberFlag = false;
        int longueur= str.length();

        for(int i=0;i < str.length();i++) {
            ch = str.charAt(i);
            if( Character.isDigit(ch)) {
                numberFlag = true;
            }
            else if (Character.isUpperCase(ch)) {
                capitalFlag = true;
            } else if (Character.isLowerCase(ch)) {
                lowerCaseFlag = true;
            }
            if(numberFlag && capitalFlag && lowerCaseFlag)
                return true;
        }
        return false;
    }
}
