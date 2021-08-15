package com.rajendra.vacationtourapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class favoris_restaurant {
    @SerializedName("id")
    @Expose
    int id_favoris;

    @SerializedName("id_resto")
    @Expose
    String id_resto;
    @SerializedName("id_user")
    @Expose
    String id_user;

    public int getId_favoris() {
        return id_favoris;
    }

    public void setId_favoris(int id_favoris) {
        this.id_favoris = id_favoris;
    }

    public String getId_resto() {
        return id_resto;
    }

    public favoris_restaurant(int id_favoris, String id_resto, String id_user) {
        this.id_favoris = id_favoris;
        this.id_resto = id_resto;
        this.id_user = id_user;
    }

    public void setId_resto(String id_resto) {
        this.id_resto = id_resto;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }
}
