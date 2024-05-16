package com.example.roadmanagement.kaltara.interfacedownload;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DownloadDataIsi {
    @SerializedName("no_prov")
    @Expose
    private String noprov;

    @SerializedName("no_ruas")
    @Expose
    private String noruas;

    @SerializedName("no_seg")
    @Expose
    private String noseg;

    @SerializedName("posisi")
    @Expose
    private String posisi;

    @SerializedName("lajur")
    @Expose
    private String lajur;

    @SerializedName("tipelane")
    @Expose
    private String tipelane;

    @SerializedName("surface")
    @Expose
    private String sc;

    @SerializedName("panjanglane")
    @Expose
    private float panjanglane;

    @SerializedName("lebarpvmt")
    @Expose
    private float lebarpvmt;

    @SerializedName("jenilahan")
    @Expose
    private String jlahan;

    @SerializedName("tagunlahan")
    @Expose
    private String tagunlahan;

    @SerializedName("panjanglahan")
    @Expose
    private float panjanglahan;

    @SerializedName("jenissaluran")
    @Expose
    private String jsaluran;

    @SerializedName("lebarsaluran")
    @Expose
    private float lebarsaluran;

    @SerializedName("dalamsaluran")
    @Expose
    private float dalamsaluran;

    @SerializedName("jenisbahu")
    @Expose
    private String jenisbahu;

    @SerializedName("lebarbahu")
    @Expose
    private float lebarbahu;

    @SerializedName("jenismedian")
    @Expose
    private String jenismedian;

    @SerializedName("lebarmedian")
    @Expose
    private float lebarmedian;

    public float getLebarpvmt() {
        return lebarpvmt;
    }

    public void setLebarpvmt(float lebarpvmt) {
        this.lebarpvmt = lebarpvmt;
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

    public String getNoseg() {
        return noseg;
    }

    public void setNoseg(String noseg) {
        this.noseg = noseg;
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

    public String getTipelane() {
        return tipelane;
    }

    public void setTipelane(String tipelane) {
        this.tipelane = tipelane;
    }

    public String getSc() {
        return sc;
    }

    public void setSc(String sc) {
        this.sc = sc;
    }

    public float getPanjanglane() {
        return panjanglane;
    }

    public void setPanjanglane(float panjanglane) {
        this.panjanglane = panjanglane;
    }

    public String getJlahan() {
        return jlahan;
    }

    public void setJlahan(String jlahan) {
        this.jlahan = jlahan;
    }

    public String getTagunlahan() {
        return tagunlahan;
    }

    public void setTagunlahan(String tagunlahan) {
        this.tagunlahan = tagunlahan;
    }

    public float getPanjanglahan() {
        return panjanglahan;
    }

    public void setPanjanglahan(float panjanglahan) {
        this.panjanglahan = panjanglahan;
    }

    public String getJsaluran() {
        return jsaluran;
    }

    public void setJsaluran(String jsaluran) {
        this.jsaluran = jsaluran;
    }

    public float getLebarsaluran() {
        return lebarsaluran;
    }

    public void setLebarsaluran(float lebarsaluran) {
        this.lebarsaluran = lebarsaluran;
    }

    public float getDalamsaluran() {
        return dalamsaluran;
    }

    public void setDalamsaluran(float dalamsaluran) {
        this.dalamsaluran = dalamsaluran;
    }

    public String getJenisbahu() {
        return jenisbahu;
    }

    public void setJenisbahu(String jenisbahu) {
        this.jenisbahu = jenisbahu;
    }

    public float getLebarbahu() {
        return lebarbahu;
    }

    public void setLebarbahu(float lebarbahu) {
        this.lebarbahu = lebarbahu;
    }

    public String getJenismedian() {
        return jenismedian;
    }

    public void setJenismedian(String jenismedian) {
        this.jenismedian = jenismedian;
    }

    public float getLebarmedian() {
        return lebarmedian;
    }

    public void setLebarmedian(float lebarmedian) {
        this.lebarmedian = lebarmedian;
    }
}
