package com.example.roadmanagement.kaltara.Interface;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataMedian {
    public static final String TABLE_NAME = "datamedian";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOPROV = "noprov";
    public static final String COLUMN_RUAS = "noruas";
    public static final String COLUMN_SEGMENT = "nosegment";
    public static final String COLUMN_SUB_SEGMENT = "subsegment";
    public static final String COLUMN_TIPE = "tipemedian";
    public static final String COLUMN_LEBAR = "lebarmedian";
    public static final String COLUMN_GAMBAR = "gambarmedian";
    public static final String COLUMN_GAMBARICON = "gambarmedianicon";
    public static final String COLUMN_LOKASI = "lokasimedian";
    public static final String COLUMN_KERUSAKAN = "kerusakanmedian";
    public static final String COLUMN_PANJANGKR = "panjangkrmedian";
    public static final String COLUMN_GAMBARKR = "gambarkrmedian";
    public static final String COLUMN_GAMBARKRICON = "gambarkrmedianicon";
    public static final String COLUMN_LOKASIKR = "lokasikr";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NOPROV + " TEXT,"
                    + COLUMN_RUAS + " TEXT,"
                    + COLUMN_SEGMENT + " INTEGER,"
                    + COLUMN_SUB_SEGMENT + " INTEGER,"
                    + COLUMN_TIPE + " TEXT,"
                    + COLUMN_LEBAR+ " NUMERIC,"
                    + COLUMN_GAMBAR + " TEXT,"
                    + COLUMN_GAMBARICON + " TEXT,"
                    + COLUMN_LOKASI + " TEXT,"
                    + COLUMN_KERUSAKAN + " TEXT,"
                    + COLUMN_PANJANGKR + " INTEGER,"
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
    int subsegment;
    @SerializedName("tipe")
    @Expose
    String tipeMedian;
    @SerializedName("lebarmedian")
    @Expose
    float lebarMedian;
    String gambarMedian;
    String gambarMedianicon;
    String lokasiMedian;
    String kerusakanMedian;
    int panjangKr;
    String gambarKr;
    String gambarKricon;
    String lokasikr;


    public DataMedian(String noprov, String noruas, int nosegment, int subsegment, String tipeMedian, float lebarMedian, String gambarMedian, String gambarMedianicon, String lokasiMedian) {
        this.noprov = noprov;
        this.noruas = noruas;
        this.nosegment = nosegment;
        this.subsegment = subsegment;
        this.tipeMedian = tipeMedian;
        this.lebarMedian = lebarMedian;
        this.gambarMedian = gambarMedian;
        this.gambarMedianicon = gambarMedianicon;
        this.lokasiMedian = lokasiMedian;
    }

    public int getSubsegment() {
        return subsegment;
    }

    public void setSubsegment(int subsegment) {
        this.subsegment = subsegment;
    }

    public String getLokasiMedian() {
        return lokasiMedian;
    }

    public void setLokasiMedian(String lokasiMedian) {
        this.lokasiMedian = lokasiMedian;
    }

    public String getLokasikr() {
        return lokasikr;
    }

    public void setLokasikr(String lokasikr) {
        this.lokasikr = lokasikr;
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

    public String getTipeMedian() {
        return tipeMedian;
    }

    public void setTipeMedian(String tipeMedian) {
        this.tipeMedian = tipeMedian;
    }

    public float getLebarMedian() {
        return lebarMedian;
    }

    public void setLebarMedian(float lebarMedian) {
        this.lebarMedian = lebarMedian;
    }

    public String getGambarMedian() {
        return gambarMedian;
    }

    public void setGambarMedian(String gambarMedian) {
        this.gambarMedian = gambarMedian;
    }

    public String getGambarMedianicon() {
        return gambarMedianicon;
    }

    public void setGambarMedianicon(String gambarMedianicon) {
        this.gambarMedianicon = gambarMedianicon;
    }

    public String getKerusakanMedian() {
        return kerusakanMedian;
    }

    public void setKerusakanMedian(String kerusakanMedian) {
        this.kerusakanMedian = kerusakanMedian;
    }

    public int getPanjangKr() {
        return panjangKr;
    }

    public void setPanjangKr(int panjangKr) {
        this.panjangKr = panjangKr;
    }

    public String getGambarKr() {
        return gambarKr;
    }

    public void setGambarKr(String gambarKr) {
        this.gambarKr = gambarKr;
    }

    public String getGambarKricon() {
        return gambarKricon;
    }

    public void setGambarKricon(String gambarKricon) {
        this.gambarKricon = gambarKricon;
    }
}
