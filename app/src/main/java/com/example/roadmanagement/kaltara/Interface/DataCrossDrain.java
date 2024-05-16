package com.example.roadmanagement.kaltara.Interface;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataCrossDrain {

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

    @SerializedName("keberadaan")
    @Expose
    String keberadaan;

    @SerializedName("penampang")
    @Expose
    String jenisPenampang;

    @SerializedName("diameter")
    @Expose
    float diameter;

    @SerializedName("lebar")
    @Expose
    float lebar;

    @SerializedName("tinggi")
    @Expose
    float tinggi;

    @SerializedName("konstruksi")
    @Expose
    String jenisKonstruksi;

    @SerializedName("kondisi")
    @Expose
    String kondisiSaluran;
    String gambar;
    String icon;
    String lokasi;

    public static final String TABLE_NAME = "dataCrossdrain";
    public static final String ID = "id";
    public static final String NOPROV = "noprov";
    public static final String NORUAS = "noruas";
    public static final String NOSEG = "nosegment";
    public static final String SUBSEG = "subsegment";
    public static final String POSISI = "posisi";
    public static final String KEBERADAAN = "keberadaan";
    public static final String JENISPENAMPANG = "jenisPenampang";
    public static final String DIAMETER = "diameter";
    public static final String LEBAR = "lebar";
    public static final String TINGGI = "tinggi";
    public static final String JENISKONSTRUKSI = "jenisKonstruksi";
    public static final String KONDISISALURAN = "kondisiSaluran";
    public static final String COLUMN_GAMBAR = "gambarlahan";
    public static final String COLUMN_GAMBAR_ICON = "gambarlahanicon";
    public static final String COLUMN_LOKASI = "lokasilahan";




    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + NOPROV + " TEXT,"
                    + NORUAS + " TEXT,"
                    + NOSEG + " INTEGER,"
                    + SUBSEG + " INTEGER,"
                    + POSISI + " TEXT,"
                    + KEBERADAAN + " TEXT,"
                    + JENISPENAMPANG+ " TEXT,"
                    + DIAMETER + " NUMERIC,"
                    + LEBAR + " NUMERIC,"
                    + TINGGI + " NUMERIC,"
                    + JENISKONSTRUKSI + " TEXT,"
                    + KONDISISALURAN + " TEXT,"
                    + COLUMN_GAMBAR + " TEXT,"
                    + COLUMN_GAMBAR_ICON + " TEXT,"
                    + COLUMN_LOKASI + " TEXT"
                    + ")";

    public DataCrossDrain(String noprov, String noruas, int nosegment, int subsegment, String posisi, String keberadaan, String jenisPenampang, float diameter, float lebar, float tinggi, String jenisKonstruksi, String kondisiSaluran, String gambar, String icon, String lokasi) {
        this.noprov = noprov;
        this.noruas = noruas;
        this.nosegment = nosegment;
        this.subsegment = subsegment;
        this.posisi = posisi;
        this.keberadaan = keberadaan;
        this.jenisPenampang = jenisPenampang;
        this.diameter = diameter;
        this.lebar = lebar;
        this.tinggi = tinggi;
        this.jenisKonstruksi = jenisKonstruksi;
        this.kondisiSaluran = kondisiSaluran;
        this.gambar = gambar;
        this.icon = icon;
        this.lokasi = lokasi;
    }

    public String getPosisi() {
        return posisi;
    }

    public void setPosisi(String posisi) {
        this.posisi = posisi;
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

    public String getKeberadaan() {
        return keberadaan;
    }

    public void setKeberadaan(String keberadaan) {
        this.keberadaan = keberadaan;
    }

    public String getJenisPenampang() {
        return jenisPenampang;
    }

    public void setJenisPenampang(String jenisPenampang) {
        this.jenisPenampang = jenisPenampang;
    }

    public float getDiameter() {
        return diameter;
    }

    public void setDiameter(float diameter) {
        this.diameter = diameter;
    }

    public float getLebar() {
        return lebar;
    }

    public void setLebar(float lebar) {
        this.lebar = lebar;
    }

    public float getTinggi() {
        return tinggi;
    }

    public void setTinggi(float tinggi) {
        this.tinggi = tinggi;
    }

    public String getJenisKonstruksi() {
        return jenisKonstruksi;
    }

    public void setJenisKonstruksi(String jenisKonstruksi) {
        this.jenisKonstruksi = jenisKonstruksi;
    }

    public String getKondisiSaluran() {
        return kondisiSaluran;
    }

    public void setKondisiSaluran(String kondisiSaluran) {
        this.kondisiSaluran = kondisiSaluran;
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

}
