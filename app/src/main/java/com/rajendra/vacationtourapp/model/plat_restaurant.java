package com.rajendra.vacationtourapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class plat_restaurant {
    @SerializedName("id")
    @Expose
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @SerializedName("nom")
    @Expose
    String nom;
    @SerializedName("description")
    @Expose
    String description;
    @SerializedName("prix")
    @Expose
    String prix;
    @SerializedName("image")
    @Expose
    String image;
    @SerializedName("type_plat")
    @Expose
    String type_plat;
    @SerializedName("id_resto")
    @Expose
    String id_resto;
    @SerializedName("note_plat")
    @Expose
    String note_plat;
    @SerializedName("pourcentage_promotion")
    @Expose
    String 	pourcentage_promotion;

    public plat_restaurant() {
    }

    public plat_restaurant(String nom, String description, String prix, String image, String type_plat, String id_resto, String note_plat, String pourcentage_promotion) {
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.image = image;
        this.type_plat = type_plat;
        this.id_resto = id_resto;
        this.note_plat = note_plat;
        this.pourcentage_promotion = pourcentage_promotion;
    }

    public String getPourcentage_promotion() {
        return pourcentage_promotion;
    }

    public void setPourcentage_promotion(String pourcentage_promotion) {
        this.pourcentage_promotion = pourcentage_promotion;
    }

    public String getNote_plat() {
        return note_plat;
    }

    public void setNote_plat(String note_plat) {
        this.note_plat = note_plat;
    }

    public plat_restaurant(String nom, String prix, String image) {
        this.nom = nom;
        this.prix = prix;
        this.image = image;
    }

    @Override
    public String toString() {
        return "plat_restaurant{" +
                "nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", prix=" + prix +
                ", image='" + image + '\'' +
                ", type_plat='" + type_plat + '\'' +
                ", id_resto='" + id_resto + '\'' +
                '}';
    }

    public plat_restaurant(String nom, String description, String image, String type_plat, String id_resto) {
        this.nom = nom;
        this.description = description;
        this.image = image;
        this.type_plat = type_plat;
        this.id_resto = id_resto;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType_plat() {
        return type_plat;
    }

    public void setType_plat(String type_plat) {
        this.type_plat = type_plat;
    }

    public String getId_resto() {
        return id_resto;
    }

    public void setId_resto(String id_resto) {
        this.id_resto = id_resto;
    }
}
