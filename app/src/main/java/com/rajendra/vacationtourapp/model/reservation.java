package com.rajendra.vacationtourapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class reservation {
    @SerializedName("id_table_resto")
    @Expose
    String id_table_resto;
    @SerializedName("first_name")
    @Expose
    String first_name;
    @SerializedName("date")
    @Expose
    String date;

    public reservation(String id_table_resto, String first_name, String date) {
        this.id_table_resto = id_table_resto;
        this.first_name = first_name;
        this.date = date;
    }

    public String getId_table_resto() {
        return id_table_resto;
    }

    public void setId_table_resto(String id_table_resto) {
        this.id_table_resto = id_table_resto;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
