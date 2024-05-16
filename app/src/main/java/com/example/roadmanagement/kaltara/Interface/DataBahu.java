package com.example.roadmanagement.kaltara.Interface;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataBahu {

    public static final String TABLE_NAME = "databahu";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOPROV = "noprov";
    public static final String COLUMN_RUAS = "noruas";
    public static final String COLUMN_SEGMENT = "nosegment";
    public static final String COLUMN_SUB_SEGMENT = "subsegment";
    public static final String COLUMN_POSISI = "posisi";
    public static final String COLUMN_TIPE = "tipebahu";
    public static final String COLUMN_LEBAR = "lebarbahu";
    public static final String COLUMN_MATERIAL_BAHU = "materialbahu";
    public static final String COLUMN_KEMIRINGAN_DERAJAT = "kemiringanbahuderajat";
    public static final String COLUMN_KEMIRINGAN_PERSEN = "kemiringanbahupersen";
    public static final String COLUMN_KEMIRINGAN_ARAH = "kemiringanbahuarah";
    public static final String COLUMN_KEMIRINGAN_KONDISI = "kemiringanbahukondisi";
    public static final String COLUMN_TIPE_INNER = "tipebahuinner";
    public static final String COLUMN_LEBAR_INNER = "lebarbahuinner";
    public static final String COLUMN_MATERIAL_INNER = "materialbahuinner";
    public static final String COLUMN_GAMBAR = "gambarbahu";
    public static final String COLUMN_GAMBARICON = "gambarbahuicon";
    public static final String COLUMN_LOKASI = "lokasibahu";
    public static final String COLUMN_KERUSAKAN = "kerusakanbahu";
    public static final String COLUMN_PANJANGKR = "panjangkrbahu";
    public static final String COLUMN_DALAMKR = "dalamskrbahu";
    public static final String COLUMN_GAMBARKR = "gambarkrbahu";
    public static final String COLUMN_GAMBARKRICON = "gambarkrbahuicon";
    public static final String COLUMN_LOKASIKR = "lokasikerusakan";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NOPROV + " TEXT,"
                    + COLUMN_RUAS + " TEXT,"
                    + COLUMN_SEGMENT + " INTEGER,"
                    + COLUMN_SUB_SEGMENT + " INTEGER,"
                    + COLUMN_POSISI + " TEXT,"
                    + COLUMN_TIPE + " TEXT,"
                    + COLUMN_LEBAR+ " NUMERIC,"
                    + COLUMN_MATERIAL_BAHU + " TEXT,"
                    + COLUMN_KEMIRINGAN_DERAJAT+ " NUMERIC,"
                    + COLUMN_KEMIRINGAN_PERSEN + " NUMERIC,"
                    + COLUMN_KEMIRINGAN_ARAH+ " TEXT,"
                    + COLUMN_KEMIRINGAN_KONDISI+ " TEXT,"
                    + COLUMN_TIPE_INNER + " TEXT,"
                    + COLUMN_LEBAR_INNER+ " NUMERIC,"
                    + COLUMN_MATERIAL_INNER + " TEXT,"
                    + COLUMN_GAMBAR + " TEXT,"
                    + COLUMN_GAMBARICON + " TEXT,"
                    + COLUMN_LOKASI + " TEXT,"
                    + COLUMN_KERUSAKAN + " TEXT,"
                    + COLUMN_PANJANGKR + " INTEGER,"
                    + COLUMN_DALAMKR + " INTEGER,"
                    + COLUMN_GAMBARKR + " TEXT,"
                    + COLUMN_GAMBARKRICON + " TEXT,"
                    + COLUMN_LOKASIKR + " TEXT"
                    + ")";


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
    int subSegment;

    @SerializedName("posisi")
    @Expose
    String posisi;

    @SerializedName("tipe")
    @Expose
    String tipeBahu;

    @SerializedName("lebar")
    @Expose
    float lebarBahu;

    @SerializedName("material")
    @Expose
    String materialBahu;

    @SerializedName("derajat")
    @Expose
    float kemiringanDerajat;

    @SerializedName("kemiringanPersen")
    @Expose
    float kemiringanPersen;

    @SerializedName("arah")
    @Expose
    String kemiringanArah;

    @SerializedName("kondisi")
    @Expose
    String kemiringanKondisi;

    @SerializedName("tipeinner")
    @Expose
    String tipeBahuInner;

    @SerializedName("lebarinner")
    @Expose
    float lebarBahuInner;

    @SerializedName("materialinner")
    @Expose
    String materialInner;

    String gambarBahu;
    String gambarBahuicon;
    String lokasiBahu;

    public DataBahu(String noprov, String noruas, int nosegment, int subSegment, String posisi, String tipeBahu, float lebarBahu, String materialBahu, float kemiringanDerajat, float kemiringanPersen, String kemiringanArah, String kemiringanKondisi, String tipeBahuInner, float lebarBahuInner, String materialInner, String gambarBahu, String gambarBahuicon, String lokasiBahu) {
        this.noprov = noprov;
        this.noruas = noruas;
        this.nosegment = nosegment;
        this.subSegment = subSegment;
        this.posisi = posisi;
        this.tipeBahu = tipeBahu;
        this.lebarBahu = lebarBahu;
        this.materialBahu = materialBahu;
        this.kemiringanDerajat = kemiringanDerajat;
        this.kemiringanPersen = kemiringanPersen;
        this.kemiringanArah = kemiringanArah;
        this.kemiringanKondisi = kemiringanKondisi;
        this.tipeBahuInner = tipeBahuInner;
        this.lebarBahuInner = lebarBahuInner;
        this.materialInner = materialInner;
        this.gambarBahu = gambarBahu;
        this.gambarBahuicon = gambarBahuicon;
        this.lokasiBahu = lokasiBahu;
    }

    public int getSubSegment() {
        return subSegment;
    }

    public void setSubSegment(int subSegment) {
        this.subSegment = subSegment;
    }

    public String getTipeBahuInner() {
        return tipeBahuInner;
    }

    public void setTipeBahuInner(String tipeBahuInner) {
        this.tipeBahuInner = tipeBahuInner;
    }

    public float getLebarBahuInner() {
        return lebarBahuInner;
    }

    public void setLebarBahuInner(float lebarBahuInner) {
        this.lebarBahuInner = lebarBahuInner;
    }

    public String getLokasiBahu() {
        return lokasiBahu;
    }

    public void setLokasiBahu(String lokasiBahu) {
        this.lokasiBahu = lokasiBahu;
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

    public String getTipeBahu() {
        return tipeBahu;
    }

    public void setTipeBahu(String tipeBahu) {
        this.tipeBahu = tipeBahu;
    }

    public float getLebarBahu() {
        return lebarBahu;
    }

    public void setLebarBahu(float lebarBahu) {
        this.lebarBahu = lebarBahu;
    }

    public String getGambarBahu() {
        return gambarBahu;
    }

    public void setGambarBahu(String gambarBahu) {
        this.gambarBahu = gambarBahu;
    }

    public String getGambarBahuicon() {
        return gambarBahuicon;
    }

    public void setGambarBahuicon(String gambarBahuicon) {
        this.gambarBahuicon = gambarBahuicon;
    }




    public String getMaterialBahu() {
        return materialBahu;
    }

    public void setMaterialBahu(String materialBahu) {
        this.materialBahu = materialBahu;
    }

    public float getKemiringanDerajat() {
        return kemiringanDerajat;
    }

    public void setKemiringanDerajat(float kemiringanDerajat) {
        this.kemiringanDerajat = kemiringanDerajat;
    }

    public float getKemiringanPersen() {
        return kemiringanPersen;
    }

    public void setKemiringanPersen(float kemiringanPersen) {
        this.kemiringanPersen = kemiringanPersen;
    }

    public String getKemiringanArah() {
        return kemiringanArah;
    }

    public void setKemiringanArah(String kemiringanArah) {
        this.kemiringanArah = kemiringanArah;
    }

    public String getKemiringanKondisi() {
        return kemiringanKondisi;
    }

    public void setKemiringanKondisi(String kemiringanKondisi) {
        this.kemiringanKondisi = kemiringanKondisi;
    }

    public String getMaterialInner() {
        return materialInner;
    }

    public void setMaterialInner(String materialInner) {
        this.materialInner = materialInner;
    }

}
