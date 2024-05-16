package com.example.roadmanagement.kaltara.Interface;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataLane {

    public static final String TABLE_NAME= "datalane";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOPROV = "noprov";
    public static final String COLUMN_RUAS = "noruas";
    public static final String COLUMN_SEGMENT = "nosegment";
    public static final String COLUMN_SUB_SEGMENT = "subsegment";
    public static final String COLUMN_POSISI = "posisi";
    public static final String COLUMN_URUT = "laneurut";
    public static final String COLUMN_LEBAR = "lebarlane";
    public static final String COLUMN_SC1 = "sclane1";
    public static final String COLUMN_SC2= "sclane2";
    public static final String COLUMN_SC3 = "sclane3";
    public static final String COLUMN_THICKNESS1 = "thickness1";
    public static final String COLUMN_THICKNESS2 = "thickness2";
    public static final String COLUMN_THICKNESS3 = "thickness3";
    public static final String COLUMN_SURFYEAR1 = "surfyear1";
    public static final String COLUMN_SURFYEAR2 = "surfyear2";
    public static final String COLUMN_SURFYEAR3 = "surfyear3";
    public static final String COLUMN_KEMIRINGAN_DERAJAT = "kemiringanDerajat";
    public static final String COLUMN_KEMIRINGAN_PERSEN = "kemiringanPersen";
    public static final String COLUMN_KEMIRINGAN_ARAH = "kemiringanArah";
    public static final String COLUMN_KEMIRINGAN_KONDISI = "kemiringanKondisi";
    public static final String COLUMN_GAMBAR = "gambarlane";
    public static final String COLUMN_GAMBARICON = "gambarlaneicon";
    public static final String COLUMN_LOKASI = "lokasilane";


    public static final String CREATE_TABLE_LEFT =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NOPROV + " TEXT,"
                    + COLUMN_RUAS + " TEXT,"
                    + COLUMN_SEGMENT + " INTEGER,"
                    + COLUMN_SUB_SEGMENT + " INTEGER,"
                    + COLUMN_POSISI + " TEXT,"
                    + COLUMN_URUT+ " TEXT,"
                    + COLUMN_LEBAR+ " NUMERIC,"
                    + COLUMN_SC1+ " TEXT,"
                    + COLUMN_SC2+ " TEXT,"
                    + COLUMN_SC3+ " TEXT,"
                    + COLUMN_THICKNESS1 + " NUMERIC,"
                    + COLUMN_THICKNESS2 + " NUMERIC,"
                    + COLUMN_THICKNESS3 + " NUMERIC,"
                    + COLUMN_SURFYEAR1+ " TEXT,"
                    + COLUMN_SURFYEAR2+ " TEXT,"
                    + COLUMN_SURFYEAR3+ " TEXT,"
                    + COLUMN_KEMIRINGAN_DERAJAT + " NUMERIC,"
                    + COLUMN_KEMIRINGAN_PERSEN + " NUMERIC,"
                    + COLUMN_KEMIRINGAN_ARAH + " TEXT,"
                    + COLUMN_KEMIRINGAN_KONDISI + " TEXT,"
                    + COLUMN_GAMBAR + " TEXT,"
                    + COLUMN_GAMBARICON + " TEXT,"
                    + COLUMN_LOKASI + " TEXT"
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
    int subsegment;

    @SerializedName("posisi")
    @Expose
    String posisi;

    @SerializedName("lajur")
    @Expose
    String urut;

    @SerializedName("lebar")
    @Expose
    float lebarLane;

    @SerializedName("surface")
    @Expose
    String sc1;
    String sc2;
    String sc3;


    float thickness1;
    float thickness2;
    float thickness3;

    String surfyear1;
    String surfyear2;
    String surfyear3;

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

    String gambarLane;
    String gambarLaneicon;
    String lokasiLane;


    public DataLane(String noprov, String noruas, int nosegment, int subsegment, String posisi, String urut, float lebarLane, String sc1, float kemiringanDerajat, float kemiringanPersen, String kemiringanArah, String kemiringanKondisi, String gambarLane, String gambarLaneicon, String lokasiLane) {
        this.noprov = noprov;
        this.noruas = noruas;
        this.nosegment = nosegment;
        this.subsegment = subsegment;
        this.posisi = posisi;
        this.urut = urut;
        this.lebarLane = lebarLane;
        this.sc1 = sc1;
        this.kemiringanDerajat = kemiringanDerajat;
        this.kemiringanPersen = kemiringanPersen;
        this.kemiringanArah = kemiringanArah;
        this.kemiringanKondisi = kemiringanKondisi;
        this.gambarLane = gambarLane;
        this.gambarLaneicon = gambarLaneicon;
        this.lokasiLane = lokasiLane;
    }

    public String getSc1() {
        return sc1;
    }

    public void setSc1(String sc1) {
        this.sc1 = sc1;
    }

    public String getSc2() {
        return sc2;
    }

    public void setSc2(String sc2) {
        this.sc2 = sc2;
    }

    public String getSc3() {
        return sc3;
    }

    public void setSc3(String sc3) {
        this.sc3 = sc3;
    }

    public float getThickness1() {
        return thickness1;
    }

    public void setThickness1(float thickness1) {
        this.thickness1 = thickness1;
    }

    public float getThickness2() {
        return thickness2;
    }

    public void setThickness2(float thickness2) {
        this.thickness2 = thickness2;
    }

    public float getThickness3() {
        return thickness3;
    }

    public void setThickness3(float thickness3) {
        this.thickness3 = thickness3;
    }

    public String getSurfyear1() {
        return surfyear1;
    }

    public void setSurfyear1(String surfyear1) {
        this.surfyear1 = surfyear1;
    }

    public String getSurfyear2() {
        return surfyear2;
    }

    public void setSurfyear2(String surfyear2) {
        this.surfyear2 = surfyear2;
    }

    public String getSurfyear3() {
        return surfyear3;
    }

    public void setSurfyear3(String surfyear3) {
        this.surfyear3 = surfyear3;
    }

    public String getLokasiLane() {
        return lokasiLane;
    }

    public void setLokasiLane(String lokasiLane) {
        this.lokasiLane = lokasiLane;
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

    public String getUrut() {
        return urut;
    }

    public void setUrut(String urut) {
        this.urut = urut;
    }


    public float getLebarLane() {
        return lebarLane;
    }

    public void setLebarLane(float lebarLane) {
        this.lebarLane = lebarLane;
    }

    public String getGambarLane() {
        return gambarLane;
    }

    public void setGambarLane(String gambarLane) {
        this.gambarLane = gambarLane;
    }

    public String getGambarLaneicon() {
        return gambarLaneicon;
    }

    public void setGambarLaneicon(String gambarLaneicon) {
        this.gambarLaneicon = gambarLaneicon;
    }

    public int getSubsegment() {
        return subsegment;
    }

    public void setSubsegment(int subsegment) {
        this.subsegment = subsegment;
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
}
