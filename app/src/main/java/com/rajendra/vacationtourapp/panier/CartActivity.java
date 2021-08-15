package com.rajendra.vacationtourapp.panier;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.braintreepayments.api.PaymentMethod;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.interfaces.HttpResponseCallback;
import com.braintreepayments.api.internal.HttpClient;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.rajendra.vacationtourapp.PlaceOrderActivity;
import com.rajendra.vacationtourapp.R;
import com.rajendra.vacationtourapp.Restaurant.RestaurantList;
import com.rajendra.vacationtourapp.adapter.CartAdapter;
import com.rajendra.vacationtourapp.adapter.RecentlyRestaurantsViewedAdapter;
import com.rajendra.vacationtourapp.model.Panier;
import com.rajendra.vacationtourapp.model.TopPlacesData;
import com.rajendra.vacationtourapp.model.plat_restaurant;
import com.rajendra.vacationtourapp.retrofit.ApiInterface;
import com.rajendra.vacationtourapp.retrofit.RetrofitClient;
import com.scwang.wave.MultiWaveHeader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CartActivity extends AppCompatActivity implements RewardedVideoAdListener {
    RewardedVideoAd rewardedVideoAd;
    Button buttonAd;
    int value = 0;
    private static final int REQUEST_CODE = 1234;
    RecyclerView recycler_cart;
    Button btn_place_order, btn_order;
    // Button youssef;
    CartAdapter recentlyRestaurantsViewedAdapter;
    ApiInterface apiInterface;
    final String API_GET_TOKEN = "http://10.0.2.2/braintree/main.php";
    final String API_CHECK_OUT = "http://10.0.2.2/braintree/checkout.php";
    String token, amount;
    HashMap<String, String> paramHash;
    public TextView txt_final_price;


    MultiWaveHeader waveHeader,waveFooter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        waveHeader = findViewById(R.id.wave_header);
        waveFooter = findViewById(R.id.wave_footer);


        waveHeader.setVelocity(2);
        waveHeader.setProgress(1);
        waveHeader.isRunning();
        waveHeader.setGradientAngle(45);
        waveHeader.setWaveHeight(40);
        waveHeader.setStartColor(Color.RED);
        waveHeader.setCloseColor(Color.CYAN);


        waveFooter.setVelocity(2);
        waveFooter.setProgress(1);
        waveFooter.isRunning();
        waveFooter.setGradientAngle(45);
        waveFooter.setWaveHeight(40);
        waveFooter.setStartColor(Color.MAGENTA);
        waveFooter.setCloseColor(Color.YELLOW);



        /*youssef =findViewById(R.id.youssef);
        youssef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   Toast.makeText(CartActivity.this, "waa", Toast.LENGTH_SHORT).show();
                submitPayment();
            }
        });*/
        recycler_cart = (RecyclerView) findViewById(R.id.recycler_cart);
        recycler_cart.setLayoutManager(new LinearLayoutManager(this));
        recycler_cart.setHasFixedSize(true);
        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        btn_order = (Button) findViewById(R.id.btn_order);

       /* btn_place_order = (Button)findViewById(R.id.btn_place_order);
        btn_place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   Toast.makeText(CartActivity.this, "waa", Toast.LENGTH_SHORT).show();
                submitPayment();
            }
        });*/
        HashMap<String, String> map = new HashMap<>();
        String idUser= (String) getIntent().getSerializableExtra("idUser");
        String idRestaurant= (String) getIntent().getSerializableExtra("idRestaurant");
        map.put("idUser", idUser);
        map.put("idResto", idRestaurant);
        txt_final_price = findViewById(R.id.txt_final_price);
        txt_final_price.setText("0");
        Call<List<Panier>> call = apiInterface.executeafficherpanier(map);
        final List<Panier> topPlacesDataList = new ArrayList<>();
        call.enqueue(new Callback<List<Panier>>() {
            @Override
            public void onResponse(Call<List<Panier>> call, Response<List<Panier>> response) {

                List<Panier> RestaurantList = response.body();
                for (final Panier pa : RestaurantList) {
                    HashMap<String, String> mapPlat = new HashMap<>();
                    topPlacesDataList.add(pa);
                }

                for (final Panier pa : topPlacesDataList) {
                    HashMap<String, String> mapPlat = new HashMap<>();
                    mapPlat.put("idPlat", pa.getId_plat());
                    Call<List<plat_restaurant>> callPlat = apiInterface.executeafficherplatparid(mapPlat);
                    callPlat.enqueue(new Callback<List<plat_restaurant>>() {
                        @Override
                        public void onResponse(Call<List<plat_restaurant>> callPlat, Response<List<plat_restaurant>> response) {

                            List<plat_restaurant> RestaurantList = response.body();
                            for (plat_restaurant r : RestaurantList) {
                                pa.setPlat(r);

                            }
                            setRecentRecycler(topPlacesDataList);

                            //   calculSomme(topPlacesDataList);
                            //txt_final_price.setText(calculSomme(topPlacesDataList));
                        }

                        @Override
                        public void onFailure(Call<List<plat_restaurant>> callPlat, Throwable t) {
                            Toast.makeText(CartActivity.this, "Server is not responding.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Panier>> call, Throwable t) {
                Toast.makeText(CartActivity.this, "Server is not responding.", Toast.LENGTH_SHORT).show();
            }
        });
        buttonAd = findViewById(R.id.button);
        /*if(txt_final_price.getText().toString().equals("0")){
            btn_order.setEnabled(false);
            buttonAd.setEnabled(false);

        }*/

        buttonAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rewardedVideoAd.isLoaded()) {
                    rewardedVideoAd.show();
                }
            }
        });
        MobileAds.initialize(this, "ca-app-pub-2436093138329511~8707723900");
        rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        rewardedVideoAd.setRewardedVideoAdListener(this);
        loadsAd();
        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CartActivity.this, PlaceOrderActivity.class);
                String idUser= (String) getIntent().getSerializableExtra("idUser");
                String emailUser= (String) getIntent().getSerializableExtra("emailUser");
                String idRestaurant= (String) getIntent().getSerializableExtra("idRestaurant");
                i.putExtra("idRestaurant",idRestaurant);
                i.putExtra("idUser",idUser);
                i.putExtra("emailUser",emailUser);
                i.putExtra("montant", txt_final_price.getText().toString());
                startActivity(i);
            }
        });

        new getToken().execute();
    }

    public void loadsAd() {
        rewardedVideoAd.loadAd("ca-app-pub-2436093138329511/3342457407", new AdRequest.Builder().build());
    }

    private void submitPayment() {
        DropInRequest dropInRequest = new DropInRequest().clientToken(token);

        startActivityForResult(dropInRequest.getIntent(this), REQUEST_CODE);

    }

    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
        loadsAd();
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        value = Integer.parseInt(txt_final_price.getText().toString());

        txt_final_price.setText(String.valueOf(value-1));
        buttonAd.setEnabled(false);
        buttonAd.setBackgroundColor(buttonAd.getContext().getResources().getColor(R.color.bt_very_light_gray));

    }

    @Override
    protected void onResume() {
        rewardedVideoAd.resume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        rewardedVideoAd.pause(this);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        rewardedVideoAd.destroy(this);
        super.onDestroy();
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onRewardedVideoCompleted() {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private class getToken extends AsyncTask {
        ProgressDialog mDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDialog = new ProgressDialog(CartActivity.this, android.R.style.Theme_DeviceDefault_Dialog);
            mDialog.setCancelable(false);
            mDialog.setMessage("Please Wait");
            mDialog.show();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            mDialog.dismiss();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            HttpClient client = new HttpClient();
            client.get(API_GET_TOKEN, new HttpResponseCallback() {
                @Override
                public void success(final String responseBody) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //     group_waiting.setVisibility(View.GONE);
                            //   group_payment.setVisibility(View.VISIBLE);
                            token = responseBody;
                            //  Toast.makeText(CartActivity.this, "waaaaa"+responseBody.toLowerCase(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void failure(Exception exception) {

                }
            });
            return null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //Toast.makeText(this, "kkkkkkkk"+, Toast.LENGTH_SHORT).show();
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {

            if (resultCode == CartActivity.RESULT_OK) {

                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                PaymentMethodNonce nonce = result.getPaymentMethodNonce();
                String strNonce = nonce.getNonce();
                Log.d("mylog", "Result: " + strNonce);
                // Send payment price with the nonce
                // use the result to update your UI and send the payment method nonce to your server
                if (!txt_final_price.getText().toString().isEmpty()) {
                    amount = txt_final_price.getText().toString();
                    paramHash = new HashMap<>();
                    paramHash.put("amount", amount);
                    paramHash.put("nonce", strNonce);
                    sendPaymentDetails();
                } else
                    Toast.makeText(this, "Please enter a valid amount.", Toast.LENGTH_SHORT).show();

            } else if (resultCode == RESULT_CANCELED) {
                // the user canceled
                Log.d("mylog", "user canceled");
            } else {
                // handle errors here, an exception may be available in
                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                Log.d("mylog", "Error : " + error.toString());
            }
        }

    }

    private void sendPaymentDetails() {
        RequestQueue queue = Volley.newRequestQueue(CartActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, API_CHECK_OUT,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("iheb", response);
                        if (response.toString().contains("Successful")) {
                            Toast.makeText(CartActivity.this, "Transaction successful!", Toast.LENGTH_SHORT).show();
                            HashMap<String, String> mapPlat = new HashMap<>();
                            mapPlat.put("idResto", "1");
                            String idUser= (String) getIntent().getSerializableExtra("idUser");
                            mapPlat.put("idUser", idUser);
                            Call<Void> callPlat = apiInterface.supprimerpaniercommande(mapPlat);
                            callPlat.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> callPlat, Response<Void> response) {
                                }

                                @Override
                                public void onFailure(Call<Void> callPlat, Throwable t) {
                                }
                            });

                        } else {
                            Toast.makeText(CartActivity.this, "Transaction Failed", Toast.LENGTH_SHORT).show();
                        }
                        Log.d("EDMT_ERROR", response.toString());

                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("EDMT_ERROR", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (paramHash == null) {
                    return null;
                }
                Map<String, String> params = new HashMap<>();
                for (String key : paramHash.keySet()) {
                    params.put(key, paramHash.get(key));
                }
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(stringRequest);

    }


    private void setRecentRecycler(List<Panier> recentsDataList) {

        recycler_cart = findViewById(R.id.recycler_cart);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recycler_cart.setLayoutManager(layoutManager);
        String idUser= (String) getIntent().getSerializableExtra("idUser");
        recentlyRestaurantsViewedAdapter = new CartAdapter(this, recentsDataList, idUser);
        recycler_cart.setAdapter(recentlyRestaurantsViewedAdapter);

    }

}