package com.example.roadmanagement.kaltara.Interface;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataLahan {
    int id;
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
    @SerializedName("tipe")
    @Expose
    String tipeLahan;

    @SerializedName("tagun")
    @Expose
    String tatagunaLahan;

    @SerializedName("kemiringan")
    @Expose
    float kemiringan;

    @SerializedName("tinggi")
    @Expose
    float tinggiLahan;

    String gambarLahan;
    String icongambar;
    String lokasilahan;

    public static final String TABLE_NAME = "datalahan";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOPROV = "noprov";
    public static final String COLUMN_RUAS = "noruas";
    public static final String COLUMN_SEGMENT = "nosegment";
    public static final String COLUMN_SUB_SEGMENT = "subsegment";
    public static final String COLUMN_POSISI = "posisi";
    public static final String COLUMN_TIPE = "tipelahan";
    public static final String COLUMN_TAGUN = "tagunlahan";
    public static final String COLUMN_TINGGILAHAN = "tinggilahan";
    public static final String COLUMN_KEMIRINGANLAHAN = "kemiringanlahan";
    public static final String COLUMN_GAMBAR = "gambarlahan";
    public static final String COLUMN_GAMBAR_ICON = "gambarlahanicon";
    public static final String COLUMN_LOKASI = "lokasilahan";




    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NOPROV + " TEXT,"
                    + COLUMN_RUAS + " TEXT,"
                    + COLUMN_SEGMENT + " INTEGER,"
                    + COLUMN_SUB_SEGMENT + " INTEGER,"
                    + COLUMN_POSISI + " TEXT,"
                    + COLUMN_TIPE + " TEXT,"
                    + COLUMN_TAGUN+ " TEXT,"
                    + COLUMN_KEMIRINGANLAHAN + " NUMERIC,"
                    + COLUMN_TINGGILAHAN + " NUMERIC,"
                    + COLUMN_GAMBAR + " TEXT,"
                    + COLUMN_GAMBAR_ICON + " TEXT,"
                    + COLUMN_LOKASI + " TEXT"
                    + ")";


    public DataLahan(String noprov, String noruas, int nosegment, int subsegment, String posisi, String tipeLahan, String tatagunaLahan, float kemiringan, float tinggiLahan, String gambarLahan, String icongambar, String lokasilahan) {
        this.noprov = noprov;
        this.noruas = noruas;
        this.nosegment = nosegment;
        this.subsegment = subsegment;
        this.posisi = posisi;
        this.tipeLahan = tipeLahan;
        this.tatagunaLahan = tatagunaLahan;
        this.kemiringan = kemiringan;
        this.tinggiLahan = tinggiLahan;
        this.gambarLahan = gambarLahan;
        this.icongambar = icongambar;
        this.lokasilahan = lokasilahan;
    }

    public int getSubsegment() {
        return subsegment;
    }

    public void setSubsegment(int subsegment) {
        this.subsegment = subsegment;
    }

    public float getKemiringan() {
        return kemiringan;
    }

    public void setKemiringan(float kemiringan) {
        this.kemiringan = kemiringan;
    }

    public String getLokasilahan() {
        return lokasilahan;
    }

    public void setLokasilahan(String lokasilahan) {
        this.lokasilahan = lokasilahan;
    }

    public String getPosisi() {
        return posisi;
    }

    public void setPosisi(String posisi) {
        this.posisi = posisi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getTipeLahan() {
        return tipeLahan;
    }

    public void setTipeLahan(String tipeLahan) {
        this.tipeLahan = tipeLahan;
    }

    public String getTatagunaLahan() {
        return tatagunaLahan;
    }

    public void setTatagunaLahan(String tatagunaLahan) {
        this.tatagunaLahan = tatagunaLahan;
    }

    public float getTinggiLahan() {
        return tinggiLahan;
    }

    public void setTinggiLahan(float tinggiLahan) {
        this.tinggiLahan = tinggiLahan;
    }

    public String getGambarLahan() {
        return gambarLahan;
    }

    public void setGambarLahan(String gambarLahan) {
        this.gambarLahan = gambarLahan;
    }

    public String getIcongambar() {
        return icongambar;
    }

    public void setIcongambar(String icongambar) {
        this.icongambar = icongambar;
    }
}
