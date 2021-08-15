package com.rajendra.vacationtourapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    private String first_name;
    private String last_name;
     private String email;
     private int etat;
     private int nbrdefois;
    @SerializedName("id")
    @Expose
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNbrdefois() {
        return nbrdefois;
    }

    public void setNbrdefois(int nbrdefois) {
        this.nbrdefois = nbrdefois;
    }

    public String getFirst_name() {
        return first_name;
    }

    public User(String first_name, String last_name, String email, int etat, int nbrdefois) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.etat = etat;
        this.nbrdefois = nbrdefois;
    }

    @Override
    public String toString() {
        return "User{" +
                "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", etat=" + etat +
                ", nbrdefois=" + nbrdefois +
                '}';
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }


    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User() {
    }




}
