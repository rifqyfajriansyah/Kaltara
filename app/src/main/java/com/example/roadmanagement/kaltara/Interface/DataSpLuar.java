package com.example.roadmanagement.kaltara.Interface;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataSpLuar {

    public static final String TABLE_NAME = "dataspluar";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOPROV = "noprov";
    public static final String COLUMN_RUAS = "noruas";
    public static final String COLUMN_SEGMENT = "nosegment";
    public static final String COLUMN_SUB_SEGMENT = "subsegment";
    public static final String COLUMN_POSISI = "posisi";
    public static final String COLUMN_LEBAR = "lebar";

    public static final String COLUMN_GAMBAR = "gambar";
    public static final String COLUMN_GAMBAR_ICON = "icon";
    public static final String COLUMN_LOKASI = "lokasi";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NOPROV + " TEXT,"
                    + COLUMN_RUAS + " TEXT,"
                    + COLUMN_SEGMENT + " INTEGER,"
                    + COLUMN_SUB_SEGMENT + " INTEGER,"
                    + COLUMN_POSISI + " TEXT,"
                    + COLUMN_LEBAR+ " NUMERIC,"
                    + COLUMN_GAMBAR + " TEXT,"
                    + COLUMN_GAMBAR_ICON + " TEXT,"
                    + COLUMN_LOKASI + " TEXT"
                    + ")";


    @SerializedName("no_prov")
    @Expose
    String noprov;
    @SerializedName("no_ruas")
    @Expose
    String noruas;
    @SerializedName("no_seg")
    @Expose
    int nosegment;
    @SerializedName("sub_seg")
    @Expose
    int subsegment;
    @SerializedName("posisi")
    @Expose
    String posisi;
    @SerializedName("lebar_luar")
    @Expose
    float lebar;

    String gambar;
    String icon;
    String lokasi;

    public DataSpLuar(String noprov, String noruas, int nosegment, int subsegment, String posisi, float lebar, String gambar, String icon, String lokasi) {
        this.noprov = noprov;
        this.noruas = noruas;
        this.nosegment = nosegment;
        this.subsegment = subsegment;
        this.posisi = posisi;
        this.lebar = lebar;
        this.gambar = gambar;
        this.icon = icon;
        this.lokasi = lokasi;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
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

    public int getNosegment() {
        return nosegment;
    }

    public void setNosegment(int nosegment) {
        this.nosegment = nosegment;
    }

    public int getSubsegment() {
        return subsegment;
    }

    public void setSubsegment(int subsegment) {
        this.subsegment = subsegment;
    }

    public String getPosisi() {
        return posisi;
    }

    public void setPosisi(String posisi) {
        this.posisi = posisi;
    }

    public float getLebar() {
        return lebar;
    }

    public void setLebar(float lebar) {
        this.lebar = lebar;
    }

}
