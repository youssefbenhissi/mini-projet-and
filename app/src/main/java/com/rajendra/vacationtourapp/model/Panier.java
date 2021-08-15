package com.rajendra.vacationtourapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Panier {
    @SerializedName("quantite")
    @Expose
    String quantite;
    @SerializedName("id_resto")
    @Expose
    String id_resto;
    @SerializedName("id_user")
    @Expose
    String id_user;
    @SerializedName("id_plat")
    @Expose
    String id_plat;

    plat_restaurant plat;

    public Panier(String quantite, String id_resto, String id_user, String id_plat, plat_restaurant plat) {
        this.quantite = quantite;
        this.id_resto = id_resto;
        this.id_user = id_user;
        this.id_plat = id_plat;
        this.plat = plat;
    }

    public plat_restaurant getPlat() {
        return plat;
    }

    public void setPlat(plat_restaurant plat) {
        this.plat = plat;
    }

    public String getQuantite() {
        return quantite;
    }

    public void setQuantite(String quantite) {
        this.quantite = quantite;
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

    public String getId_plat() {
        return id_plat;
    }

    public void setId_plat(String id_plat) {
        this.id_plat = id_plat;
    }

    @Override
    public String toString() {
        return "Panier{" +
                "quantite='" + quantite + '\'' +
                ", id_resto='" + id_resto + '\'' +
                ", id_user='" + id_user + '\'' +
                ", id_plat='" + id_plat + '\'' +
                ", plat=" + plat +
                '}';
    }
}
