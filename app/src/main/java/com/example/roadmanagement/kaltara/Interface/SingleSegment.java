package com.example.roadmanagement.kaltara.Interface;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SingleSegment {

    @SerializedName("no_prov")
    @Expose
    private String noprov;

    @SerializedName("no_ruas")
    @Expose
    private String noruas;

    @SerializedName("no_segment")
    @Expose
    private int noseg;

    @SerializedName("sub_segment")
    @Expose
    private int subSegment;

    @SerializedName("km_awal")
    @Expose
    private String kmawal;

    @SerializedName("km_akhir")
    @Expose
    private String kmakhir;

    @SerializedName("sta_awal")
    @Expose
    private String staawal;

    @SerializedName("sta_akhir")
    @Expose
    private String staakhir;



    public static final String TABLE_NAME = "segmentku";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOPROV = "noprov";
    public static final String COLUMN_RUAS = "noruas";
    public static final String COLUMN_SEGMENT = "spinnersegment";
    public static final String COLUMN_SUB_SEGMENT = "subsegment";
    public static final String COLUMN_KMAWAL = "kmawal";
    public static final String COLUMN_KMAKHIR = "kmakhir";
    public static final String COLUMN_STAAWAL = "staawal";
    public static final String COLUMN_STAAKHIR = "staakhir";


    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NOPROV + " TEXT,"
                    + COLUMN_RUAS + " TEXT,"
                    + COLUMN_SEGMENT + " INTEGER,"
                    + COLUMN_SUB_SEGMENT + " INTEGER,"
                    + COLUMN_KMAWAL + " TEXT,"
                    + COLUMN_KMAKHIR + " TEXT,"
                    + COLUMN_STAAWAL+ " TEXT,"
                    + COLUMN_STAAKHIR+ " TEXT"
                    + ")";

    public SingleSegment(String noprov, String noruas, int noseg, int subSegment, String kmawal, String kmakhir, String staawal, String staakhir) {
        this.noprov = noprov;
        this.noruas = noruas;
        this.noseg = noseg;
        this.subSegment = subSegment;
        this.kmawal = kmawal;
        this.kmakhir = kmakhir;
        this.staawal = staawal;
        this.staakhir = staakhir;
    }

    public int getSubSegment() {
        return subSegment;
    }

    public void setSubSegment(int subSegment) {
        this.subSegment = subSegment;
    }

    public String getNoprov() {
        return noprov;
    }

    public void setNoprov(String noprov) {
        this.noprov = noprov;
    }

    public int getNoseg() {
        return noseg;
    }

    public void setNoseg(int noseg) {
        this.noseg = noseg;
    }

    public String getKmawal() {
        return kmawal;
    }

    public void setKmawal(String kmawal) {
        this.kmawal = kmawal;
    }

    public String getKmakhir() {
        return kmakhir;
    }

    public void setKmakhir(String kmakhir) {
        this.kmakhir = kmakhir;
    }

    public String getStaawal() {
        return staawal;
    }

    public void setStaawal(String staawal) {
        this.staawal = staawal;
    }

    public String getStaakhir() {
        return staakhir;
    }

    public void setStaakhir(String staakhir) {
        this.staakhir = staakhir;
    }

    public String getNoruas() {
        return noruas;
    }

    public void setNoruas(String noruas) {
        this.noruas = noruas;
    }
}
