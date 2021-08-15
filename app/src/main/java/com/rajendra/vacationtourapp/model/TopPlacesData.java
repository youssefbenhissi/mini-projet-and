package com.rajendra.vacationtourapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopPlacesData {
    @SerializedName("id_resto")
    @Expose
    String id_resto;

    @SerializedName("nom")
    @Expose
    String nom;
    @SerializedName("adresse")
    @Expose
    String adresse;
    @SerializedName("image_pr")
    @Expose
    String image;
    @SerializedName("image_de")
    @Expose
    String imageTwo;
    @SerializedName("image_tr")
    @Expose
    String imageThree;

    @SerializedName("nbr_etoiles")
    @Expose
    String nbr_etoiles;

    @SerializedName("description")
    @Expose
    String description;
    @SerializedName("nbr_defois")
    @Expose
    String nbrFois;
    @SerializedName("somme_rating")
    @Expose
    String sommeRating;
    @SerializedName("link_restaurant")
    @Expose
    String link_restaurant;

    public String getLink_restaurant() {
        return link_restaurant;
    }

    public void setLink_restaurant(String link_restaurant) {
        this.link_restaurant = link_restaurant;
    }

    public String getNbrFois() {
        return nbrFois;
    }

    public void setNbrFois(String nbrFois) {
        this.nbrFois = nbrFois;
    }

    public String getSommeRating() {
        return sommeRating;
    }

    public void setSommeRating(String sommeRating) {
        this.sommeRating = sommeRating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNbr_etoiles() {
        return nbr_etoiles;
    }

    public void setNbr_etoiles(String nbr_etoiles) {
        this.nbr_etoiles = nbr_etoiles;
    }

    public String getImageTwo() {
        return imageTwo;
    }

    public void setImageTwo(String imageTwo) {
        this.imageTwo = imageTwo;
    }

    public String getImageThree() {
        return imageThree;
    }

    public void setImageThree(String imageThree) {
        this.imageThree = imageThree;
    }

    @Override
    public String toString() {
        return "TopPlacesData{" +
                "id_resto=" + id_resto +
                ", nom='" + nom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    public TopPlacesData() {
    }

    public TopPlacesData(String id_resto, String nom, String adresse, String image) {
        this.id_resto = id_resto;
        this.nom = nom;
        this.adresse = adresse;
        this.image = image;
    }

    public String getId_resto() {
        return id_resto;
    }

    public void setId_resto(String id_resto) {
        this.id_resto = id_resto;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
