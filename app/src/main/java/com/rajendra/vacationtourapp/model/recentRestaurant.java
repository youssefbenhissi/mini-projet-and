package com.rajendra.vacationtourapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class recentRestaurant {
    @SerializedName("id")
    @Expose
    int id;

    @SerializedName("id_resto")
    @Expose
    String id_resto;
    @SerializedName("id_user")
    @Expose
    String id_user;

    public recentRestaurant() {
    }

    @Override
    public String toString() {
        return "recentRestaurant{" +
                "id=" + id +
                ", id_resto='" + id_resto + '\'' +
                ", id_user='" + id_user + '\'' +
                '}';
    }

    public recentRestaurant(int id, String id_resto, String id_user) {
        this.id = id;
        this.id_resto = id_resto;
        this.id_user = id_user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId_resto() {
        return id_resto;
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
