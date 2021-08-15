package com.rajendra.vacationtourapp.retrofit;

import com.rajendra.vacationtourapp.model.BraintreeToken;
import com.rajendra.vacationtourapp.model.BraintreeTransaction;
import com.rajendra.vacationtourapp.model.recentRestaurant;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IBraintreeApi {
    @GET("/checkouts/new")
    Call<List<BraintreeToken>> getToken();

    @POST("/checkouts")
    Call<List<BraintreeTransaction>> submitPayment(@Body HashMap<String, String> map);
}
