package com.example.roadmanagement.kaltara.Interface;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Koordinatku {
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("no_prov")
    @Expose
    private String noprov;
    @SerializedName("no_ruas")
    @Expose
    private String noruas;
    @SerializedName("nama_jalan")
    @Expose
    private String namajalan;
    @SerializedName("datakoordinat")
    @Expose
    private ArrayList<DataKoordinat> koordinats;


    public Koordinatku(String error, String noprov, String noruas, String namajalan, ArrayList<DataKoordinat> koordinats) {
        this.error = error;
        this.noprov = noprov;
        this.noruas = noruas;
        this.namajalan = namajalan;
        this.koordinats = koordinats;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getNoprov() {
        return noprov;
    }

    public void setNoprov(String noprov) {
        this.noprov = noprov;
    }

    public String getNoruas() {
        return noruas;
    }

    public void setNoruas(String noruas) {
        this.noruas = noruas;
    }

    public String getNamajalan() {
        return namajalan;
    }

    public void setNamajalan(String namajalan) {
        this.namajalan = namajalan;
    }

    public ArrayList<DataKoordinat> getKoordinats() {
        return koordinats;
    }

    public void setKoordinats(ArrayList<DataKoordinat> koordinats) {
        this.koordinats = koordinats;
    }
}
