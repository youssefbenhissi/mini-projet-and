package com.rajendra.vacationtourapp.retrofit;



import com.rajendra.vacationtourapp.model.Panier;
import com.rajendra.vacationtourapp.model.TopPlacesData;
import com.rajendra.vacationtourapp.model.User;
import com.rajendra.vacationtourapp.model.commande;
import com.rajendra.vacationtourapp.model.plat_restaurant;
import com.rajendra.vacationtourapp.model.recentRestaurant;
import com.rajendra.vacationtourapp.model.reservation;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET("/listRestaurants/")
    Call<List<TopPlacesData>> getAllRestaurents();

    //ajouter favoris restaurant
    @POST("/ajouter_favoris_restaurant")
    Call<Void> ajouterFavorisRestaurant(@Body HashMap<String, String> map);

    //verifier existance favoris restaurant
    @POST("/verify_existance_favoris")
    Call<Void> verifyexistancefavorisrestaurant(@Body HashMap<String, String> map);

    //supprimer favoris restaurant
    @POST("/supprimer_favoris_restaurant")
    Call<Void> supprimerFavorisRestaurant(@Body HashMap<String, String> map);

    //ajouter un restaurat au recent
    @POST("/ajouter_recent_restaurant")
    Call<Void> ajouterRecentRestaurant(@Body HashMap<String, String> map);

    //ajouter un restaurat au recent
    @POST("/listRecents")
    Call<List<recentRestaurant>> afficherRecentRestaurant(@Body HashMap<String, String> map);

    //ajouter un rating au restaurant
    @POST("/ajouter_rating_restaurant")
    Call<Void> ajouterRatingRestaurant(@Body HashMap<String, String> map);

    //verifier existance favoris restaurant
    @POST("/verify_existance_rating")
    Call<Void> verifyexistancereratingrestuarant(@Body HashMap<String, String> map);

    //afficher plats par restaurant Pupolar
    @POST("/listPlats")
    Call<List<plat_restaurant>> afficherplatsparrestaurant(@Body HashMap<String, String> map);

    //afficher plats par restaurant Pupolar
    @POST("/listPlatsRandom")
    Call<List<plat_restaurant>> afficherplatsrandom(@Body HashMap<String, String> map);

    //afficher plats par restaurant Pupolar
    @POST("/listPlatsDiscount")
    Call<List<plat_restaurant>> afficherplatsdiscount(@Body HashMap<String, String> map);

    //afficher plats par restaurant complet
    @POST("/listPlatsComplet")
    Call<List<plat_restaurant>> afficherplatsparrestaurantcomplet(@Body HashMap<String, String> map);
    //afficher plats par restaurant complet par categorie
    @POST("/listPlatsCompletCategorie")
    Call<List<plat_restaurant>> afficherplatsparrestaurantcompletparcategorie(@Body HashMap<String, String> map);

    @POST("/register/")
    Call<Void> executeSignup (@Body HashMap<String, String> map);
    @POST("/login")
    Call<User> executeLogin(@Body HashMap<String, String> map);

    @POST("/loginrestaurant")
    Call<TopPlacesData> executeLoginRestaurant(@Body HashMap<String, String> map);

    @POST("/verify_user")
    Call<User> executeVerification(@Body HashMap<String, String> map);
    @POST("/block_user")
    Call<Void> executeBlock(@Body HashMap<String, String> map);
    @POST("/forget_password")
    Call<Void> executeForget(@Body HashMap<String, String> map);
    @POST("/login_facebook")
    Call<User> executeLoginFacebook(@Body HashMap<String, String> map);

    @POST("/getIdUser")
    Call<User> executegetiduser(@Body HashMap<String, String> map);

    //afficher plat par id
    @POST("/getplatparid")
    Call<List<plat_restaurant>> executeafficherplatparid(@Body HashMap<String, String> map);

    //afficher panier par id user et id restaurant
    @POST("/getpanier")
    Call<List<Panier>> executeafficherpanier(@Body HashMap<String, String> map);

    //update panier
    @POST("/updatepanier")
    Call<Void> updatepanier(@Body HashMap<String, String> map);


    //supprimer panier
    @POST("/deletepatpanier")
    Call<Void> supprimerpanier(@Body HashMap<String, String> map);

    //supprimer panier commande
    @POST("/deletepatpaniercommande")
    Call<Void> supprimerpaniercommande(@Body HashMap<String, String> map);


    //supprimer panier commande
    @POST("/verifier_cash_on_delivery")
    Call<Void> ajoutercommandecashondelivery(@Body HashMap<String, String> map);

    //ajouter plat au panier
    @POST("/ajouterpanierplat")
    Call<Void> ajouterplatpanier(@Body HashMap<String, String> map);


    @POST("/listcommandesswift")
    Call<List<commande>> affichercommandes(@Body HashMap<String, String> map);
    @POST("/listtablesswift")
    Call<List<reservation>> afficherreservations(@Body HashMap<String, String> map);


    @POST("/statistiquesplats")
    Call<Integer> statistiquesplats(@Body HashMap<String, String> map);
}
