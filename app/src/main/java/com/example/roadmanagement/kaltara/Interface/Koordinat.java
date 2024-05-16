package com.example.roadmanagement.kaltara.Interface;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Koordinat {
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("no_prov")
    @Expose
    private String noProv;
    @SerializedName("no_ruas")
    @Expose
    private String noRuas;
    @SerializedName("nama_jalan")
    @Expose
    private String namaJalan;
    @SerializedName("no_seg")
    @Expose
    private String noSeg;
    @SerializedName("posisi")
    @Expose
    private String posisi;
    @SerializedName("lajur")
    @Expose
    private String lajur;
    @SerializedName("koordinat")
    @Expose
    private String koordinat;

    public Koordinat(String noProv, String noRuas, String namaJalan, String noSeg, String posisi, String lajur, String koordinat) {
        this.noProv = noProv;
        this.noRuas = noRuas;
        this.namaJalan = namaJalan;
        this.noSeg = noSeg;
        this.posisi = posisi;
        this.lajur = lajur;
        this.koordinat = koordinat;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getNoProv() {
        return noProv;
    }

    public void setNoProv(String noProv) {
        this.noProv = noProv;
    }

    public String getNoRuas() {
        return noRuas;
    }

    public void setNoRuas(String noRuas) {
        this.noRuas = noRuas;
    }

    public String getNamaJalan() {
        return namaJalan;
    }

    public void setNamaJalan(String namaJalan) {
        this.namaJalan = namaJalan;
    }

    public String getNoSeg() {
        return noSeg;
    }

    public void setNoSeg(String noSeg) {
        this.noSeg = noSeg;
    }

    public String getPosisi() {
        return posisi;
    }

    public void setPosisi(String posisi) {
        this.posisi = posisi;
    }

    public String getLajur() {
        return lajur;
    }

    public void setLajur(String lajur) {
        this.lajur = lajur;
    }

    public String getKoordinat() {
        return koordinat;
    }

    public void setKoordinat(String koordinat) {
        this.koordinat = koordinat;
    }
}
