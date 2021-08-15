package com.rajendra.vacationtourapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.interfaces.HttpResponseCallback;
import com.braintreepayments.api.internal.HttpClient;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.rajendra.vacationtourapp.Restaurant.RestaurantList;
import com.rajendra.vacationtourapp.model.plat_restaurant;
import com.rajendra.vacationtourapp.panier.CartActivity;
import com.rajendra.vacationtourapp.retrofit.ApiInterface;
import com.rajendra.vacationtourapp.retrofit.RetrofitClient;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceOrderActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,GoogleApiClient.ConnectionCallbacks {
    CheckBox checkbox;
    GoogleApiClient googleApiClient;
    String SiteKey = "6LelgwwaAAAAAKq19ax8Kk-BDjxgI_lW2R4VQR8t";
    AdView adView1,adView2;
    EditText edt_date;
    InterstitialAd interstitialAd;
    TextView txt_total_cash,txt_user_phone;

    TextView txt_user_adress, txt_new_adress,textViewError;
    Button btn_add_new_adress, btn_proceed, btn_add_current_position;
    CheckBox ckb_defaut_adress;
    RadioButton rdi_online_payment;
    RadioButton rdi_cod;
    ApiInterface apiInterface;
    HashMap<String,String> paramHash;
    AlertDialog dialog;
    String token,amount;
    private static final int REQUEST_CODE = 1234;
    FusedLocationProviderClient fusedLocationProviderClient;
    boolean isSelectedDate = false;
    boolean isAddNewAddress = false;
    final String API_GET_TOKEN = "http://10.0.2.2/braintree/main.php";
    final String API_CHECK_OUT = "http://10.0.2.2/braintree/checkout.php";
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {


        btn_add_new_adress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAddNewAddress = true;
                ckb_defaut_adress.setChecked(false);
                View layout_add_newadress = LayoutInflater.from(PlaceOrderActivity.this).inflate(R.layout.layout_add_new_adress, null);
                EditText edt_new_adress = (EditText) layout_add_newadress.findViewById(R.id.edt_add_new_adress);
                edt_new_adress.setText(txt_new_adress.getText().toString());
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(PlaceOrderActivity.this)
                        .setTitle("Add New Adress")
                        .setView(layout_add_newadress)
                        .setNegativeButton("CANCEL", ((dialogInterface, i) -> dialogInterface.dismiss()))
                        .setPositiveButton("Add", ((dialogInterface, i) -> txt_new_adress.setText(edt_new_adress.getText().toString())));
                androidx.appcompat.app.AlertDialog addNewAdressDialog = builder.create();
                addNewAdressDialog.show();


            }
        });

        //CURRENT LOCATION
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        btn_add_current_position.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(PlaceOrderActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    ActivityCompat.requestPermissions(PlaceOrderActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });
        edt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(PlaceOrderActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)

                );
                dpd.show(getSupportFragmentManager(), "DatepickerDialog");
            }
        });
        btn_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isSelectedDate) {
                    Toast.makeText(PlaceOrderActivity.this, "Please select Date", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isAddNewAddress) {
                    if (!ckb_defaut_adress.isChecked()) {
                        Toast.makeText(PlaceOrderActivity.this, "Please choose default Adress or set new adress", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (rdi_cod.isChecked()) {


                    // process COD
                    HashMap<String, String> mapPlat = new HashMap<>();
                    String emailUser= (String) getIntent().getSerializableExtra("emailUser");
                    String idRestaurant= (String) getIntent().getSerializableExtra("idRestaurant");
                    mapPlat.put("email", emailUser);

                    mapPlat.put("idResto", idRestaurant);
                    mapPlat.put("adresse", txt_new_adress.getText().toString());
                    mapPlat.put("total", txt_total_cash.getText().toString());
                    String idUser= (String) getIntent().getSerializableExtra("idUser");
                    mapPlat.put("id_user", idUser);
                    Call<Void> callPlat = apiInterface.ajoutercommandecashondelivery(mapPlat);
                    callPlat.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> callPlat, Response<Void> response) {

                        }
                        @Override
                        public void onFailure(Call<Void> callPlat, Throwable t) {
                        }
                    });

                } else if (rdi_online_payment.isChecked()) {
                    //initialize connecvtivity Manger
                    ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

                    //Get active newtork info
                    NetworkInfo networkInfo  = connectivityManager.getActiveNetworkInfo();
                    if(networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()){
                        //when internet is inactive
                        //initalize dialog
                        Dialog dialog = new Dialog(PlaceOrderActivity.this);
                        //Set Content View
                        dialog.setContentView(R.layout.alert_dialog);
                        //set outside touch
                        dialog.setCanceledOnTouchOutside(false);
                        //set dialog width and height
                        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
                        //set transparant background
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        //set Animation
                        dialog.getWindow().getAttributes().windowAnimations  = android.R.style.Animation_Dialog;
                        //initialize dialog variable

                        Button btTryAgain = dialog.findViewById(R.id.bt_try_again);
                        //Perform  on Click listener
                        btTryAgain.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //call recreate
                                recreate();
                            }
                        });
                        //show dialog
                        dialog.show();
                    }
                    else{
                        //when internet is active

                        submitPayment();
                    }

                }
            }
        });

    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task task) {
                Location location = (Location) task.getResult();
                if(location != null){
                    try {
                        Geocoder geocoder = new Geocoder(PlaceOrderActivity.this, Locale.getDefault());

                        List<Address> adressess = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                        View layout_add_newadress = LayoutInflater.from(PlaceOrderActivity.this).inflate(R.layout.layout_add_new_adress, null);

                        EditText edt_new_adress = (EditText) layout_add_newadress.findViewById(R.id.edt_add_new_adress);
                      //  edt_new_adress.setText(txt_new_adress.getText().toString());
                          isAddNewAddress = true;
                        ckb_defaut_adress.setChecked(false);
                        txt_new_adress.setText(adressess.get(0).getAddressLine(0));
                        //edt_new_adress.setText(adressess.get(0).getAddressLine(0));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        txt_user_phone = findViewById(R.id.txt_user_phone);
        textViewError = findViewById(R.id.textViewError);
        String emailUser= (String) getIntent().getSerializableExtra("emailUser");
        txt_user_phone.setText(emailUser);
        adView2 = findViewById(R.id.ad_view2);
       MobileAds.initialize(this,"ca-app-pub-2436093138329511~8707723900");
        AdRequest adRequest = new AdRequest.Builder().build();

       /* adView2.loadAd(adRequest);
        interstitialAd =  new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-2436093138329511/3462124325");
        interstitialAd.loadAd(new AdRequest.Builder().build());*/
        checkbox = findViewById(R.id.check_box);
        googleApiClient = new GoogleApiClient.Builder(this).addApi(SafetyNet.API).addConnectionCallbacks(PlaceOrderActivity.this)
                .build();
        googleApiClient.connect();

        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkbox.isChecked()) {
                    SafetyNet.SafetyNetApi.verifyWithRecaptcha(googleApiClient, SiteKey).setResultCallback(new ResultCallbacks<SafetyNetApi.RecaptchaTokenResult>() {


                        @Override
                        public void onSuccess(@NonNull SafetyNetApi.RecaptchaTokenResult recaptchaTokenResult) {
                            Status status = recaptchaTokenResult.getStatus();
                            if((status != null)&& status.isSuccess()){
                                Toast.makeText(PlaceOrderActivity.this, "Successfully verified", Toast.LENGTH_SHORT).show();
                                checkbox.setTextColor(Color.GREEN);
                                btn_proceed.setEnabled(true);
                                btn_proceed.setBackgroundColor(btn_proceed.getContext().getResources().getColor(R.color.bluecolor));
                            }else {
                                checkbox.setTextColor(Color.BLACK);
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Status status) {
                            Toast.makeText(PlaceOrderActivity.this, ""+status, Toast.LENGTH_LONG).show();

                        }
                    });

                }
            }
        });
        edt_date=findViewById(R.id.edt_date);
        txt_total_cash=findViewById(R.id.txt_total_cash);
        txt_user_adress=findViewById(R.id.txt_user_adress);
        txt_new_adress=findViewById(R.id.txt_new_adress);
        txt_user_phone=findViewById(R.id.txt_user_phone);
        btn_add_new_adress=findViewById(R.id.btn_add_new_adress);
        ckb_defaut_adress=findViewById(R.id.ckb_default_address);
        rdi_cod=findViewById(R.id.rdi_cod);
        rdi_online_payment=findViewById(R.id.rdi_online_payment);
        btn_proceed=findViewById(R.id.btn_proceed);
        btn_add_current_position =findViewById(R.id.btn_add_current_position);
        Intent intent = getIntent();
        txt_total_cash.setText(intent.getStringExtra("montant"));
        initView();
        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        btn_proceed.setEnabled(false);
        btn_proceed.setBackgroundColor(btn_proceed.getContext().getResources().getColor(R.color.bt_very_light_gray));

        new getToken().execute();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
            isSelectedDate = true;

            edt_date.setText(new StringBuilder("")
                .append(monthOfYear+1)
                    .append("/")
                    .append(dayOfMonth)
                    .append("/")
                    .append(year)
            );
        Calendar now = Calendar.getInstance();
                int yearNow = now.get(Calendar.YEAR);
                int monthNow = now.get(Calendar.MONTH);
                int dayNow = now.get(Calendar.DAY_OF_MONTH);
                Log.d("kkkkkk","a now"+yearNow+" m now "+monthNow+" d now "+dayNow+" d "+ dayOfMonth +" m "+ monthOfYear+" y "+year);
        //Toast.makeText(this, "a now"+yearNow+" m now "+monthNow+" y now "+yearNow+" d "+ dayOfMonth +" m "+ monthOfYear+" y "+year, Toast.LENGTH_LONG).show();

        if(yearNow > year){
            isSelectedDate = false;
        }
        if((year >= yearNow) && (monthNow > monthOfYear))
        {
            isSelectedDate = false;
        }
        if((year >= yearNow) && (monthOfYear >= monthNow) && (dayNow > dayOfMonth)){
            isSelectedDate = false;
        }
    if(isSelectedDate == false){
        textViewError.setText("The date is Invalid");
    }
    else{
        textViewError.setText("");
    }

    }
    private void submitPayment() {
        DropInRequest dropInRequest = new DropInRequest().clientToken(token);

        startActivityForResult(dropInRequest.getIntent(this),REQUEST_CODE);

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private class getToken extends AsyncTask {
        ProgressDialog mDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDialog = new ProgressDialog(PlaceOrderActivity.this,android.R.style.Theme_DeviceDefault_Dialog);
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
            HttpClient client =new HttpClient();
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
                if (!txt_total_cash.getText().toString().isEmpty()) {
                    amount = txt_total_cash.getText().toString();
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

    private void sendPaymentDetails(){
        RequestQueue queue = Volley.newRequestQueue(PlaceOrderActivity.this);
        StringRequest stringRequest =new StringRequest(Request.Method.POST, API_CHECK_OUT,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("iheb",response);
                        if(response.toString().contains("Successful")){
                            Toast.makeText(PlaceOrderActivity.this, "Transaction successful!", Toast.LENGTH_SHORT).show();
                            Intent i =new Intent(PlaceOrderActivity.this, RestaurantList.class);
                            String emailUser= (String) getIntent().getSerializableExtra("emailUser");
                            String idUser= (String) getIntent().getSerializableExtra("idUser");
                            i.putExtra("emailUser", emailUser);
                            i.putExtra("idUser", idUser);
                            startActivity(i);
                            HashMap<String, String> mapPlat = new HashMap<>();
                            mapPlat.put("idUser", idUser);
                            mapPlat.put("idResto", "1");
                            Call<Void> callPlat = apiInterface.supprimerpaniercommande(mapPlat);
                            callPlat.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> callPlat, Response<Void> response) {

                                }

                                @Override
                                public void onFailure(Call<Void> callPlat, Throwable t) {
                                    Toast.makeText(PlaceOrderActivity.this, "Server is not responding.", Toast.LENGTH_SHORT).show();
                                }
                            });


                          /*  HashMap<String, String> mapPlat = new HashMap<>();
                            mapPlat.put("idResto", "1");
                            mapPlat.put("idUser", "8");
                            Call<Void> callPlat = apiInterface.supprimerpaniercommande(mapPlat);
                            callPlat.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> callPlat, Response<Void> response) {}
                                @Override
                                public void onFailure(Call<Void> callPlat, Throwable t) {
                                }
                            });*/

                        }
                        else{
                            Toast.makeText(PlaceOrderActivity.this, "Transaction Failed", Toast.LENGTH_SHORT).show();
                        }
                        Log.d("EDMT_ERROR",response.toString());

                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("EDMT_ERROR",error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if(paramHash == null){
                    return null;
                }
                Map<String,String> params = new HashMap<>();
                for(String key: paramHash.keySet()){
                    params.put(key,paramHash.get(key));
                }
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params =new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(stringRequest);

    }
}