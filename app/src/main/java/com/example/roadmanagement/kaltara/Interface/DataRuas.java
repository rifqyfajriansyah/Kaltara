package com.example.roadmanagement.kaltara.Interface;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataRuas {

    @SerializedName("no_prov")
    @Expose
    String noprov;

    @SerializedName("no_ruas")
    @Expose
    String noruas;

    String sinkronid;
    String sinkronDetail;

    @SerializedName("nama_jalan")
    @Expose
    String namaruas;

    int flagreset;


    public static final String TABLE_NAME = "dataruas";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOPROV = "noprov";
    public static final String COLUMN_RUAS = "noruas";
    public static final String SINKRON_ID = "sinkron";
    public static final String SINKRON_DETAIL = "sinkronDetail";
    public static final String NAMA_RUAS = "namaruas";
    public static final String FLAG_RESET = "flagreset";


    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NOPROV + " TEXT,"
                    + COLUMN_RUAS + " TEXT,"
                    + SINKRON_ID + " TEXT, "
                    + SINKRON_DETAIL + " TEXT, "
                    + NAMA_RUAS + " TEXT, "
                    + FLAG_RESET + " INTEGER )";

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

    public String getSinkronid() {
        return sinkronid;
    }

    public void setSinkronid(String sinkronid) {
        this.sinkronid = sinkronid;
    }

    public String getSinkronDetail() {
        return sinkronDetail;
    }

    public void setSinkronDetail(String sinkronDetail) {
        this.sinkronDetail = sinkronDetail;
    }

    public String getNamaruas() {
        return namaruas;
    }

    public void setNamaruas(String namaruas) {
        this.namaruas = namaruas;
    }

    public int getFlagreset() {
        return flagreset;
    }

    public void setFlagreset(int flagreset) {
        this.flagreset = flagreset;
    }


    public DataRuas(String noprov, String noruas, String sinkronid, String sinkronDetail, String namaruas, int flagreset) {
        this.noprov = noprov;
        this.noruas = noruas;
        this.sinkronid = sinkronid;
        this.sinkronDetail = sinkronDetail;
        this.namaruas = namaruas;
        this.flagreset = flagreset;
    }
}
