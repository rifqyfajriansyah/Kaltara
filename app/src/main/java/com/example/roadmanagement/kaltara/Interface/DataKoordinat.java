package com.example.roadmanagement.kaltara.Interface;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataKoordinat {
    @SerializedName("no_seg")
    @Expose
    private String noSeg;
    @SerializedName("lajur")
    @Expose
    private String lajur;
    @SerializedName("posisi")
    @Expose
    private String posisi;
    @SerializedName("koordinat")
    @Expose
    private String koordinat;

    public DataKoordinat(String noSeg, String lajur, String posisi, String koordinat) {
        this.noSeg = noSeg;
        this.lajur = lajur;
        this.posisi = posisi;
        this.koordinat = koordinat;
    }

    public String getNoSeg() {
        return noSeg;
    }

    public void setNoSeg(String noSeg) {
        this.noSeg = noSeg;
    }

    public String getLajur() {
        return lajur;
    }

    public void setLajur(String lajur) {
        this.lajur = lajur;
    }

    public String getPosisi() {
        return posisi;
    }

    public void setPosisi(String posisi) {
        this.posisi = posisi;
    }

    public String getKoordinat() {
        return koordinat;
    }

    public void setKoordinat(String koordinat) {
        this.koordinat = koordinat;
    }
}
