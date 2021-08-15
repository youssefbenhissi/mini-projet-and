package com.rajendra.vacationtourapp.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class commande {
    @SerializedName("id_commande")
    @Expose
    String id;
    @SerializedName("first_name")
    @Expose
    String first_name;
    @SerializedName("total")
    @Expose
    String total;
    @SerializedName("adresse")
    @Expose
    String adresse;

    @Override
    public String toString() {
        return "commande{" +
                "id='" + id + '\'' +
                ", first_name='" + first_name + '\'' +
                ", total='" + total + '\'' +
                ", adresse='" + adresse + '\'' +
                '}';
    }

    public commande(String id, String first_name, String total, String adresse) {
        this.id = id;
        this.first_name = first_name;
        this.total = total;
        this.adresse = adresse;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
}
