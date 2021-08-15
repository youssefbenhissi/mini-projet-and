package com.rajendra.vacationtourapp.model;

public class Category {


    Integer id;
    Integer imageurl;
    String nom;
    public Category(Integer id, Integer imageurl,String nom ) {
        this.id = id;
        this.imageurl = imageurl;
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getImageurl() {
        return imageurl;
    }

    public void setImageurl(Integer imageurl) {
        this.imageurl = imageurl;
    }

}
