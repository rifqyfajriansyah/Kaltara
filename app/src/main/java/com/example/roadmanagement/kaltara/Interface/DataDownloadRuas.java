package com.example.roadmanagement.kaltara.Interface;

public class DataDownloadRuas {

    public static final String TABLE_NAME = "downloadruas";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOPROV = "noprov";
    public static final String COLUMN_RUAS = "noruas";
    public static final String CEKRUAS = "cekruas";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NOPROV + " TEXT,"
                    + COLUMN_RUAS + " TEXT,"
                    + CEKRUAS + " TEXT )";

    String noprov;
    String noruas;
    String cekdownload;

    public DataDownloadRuas(String noprov, String noruas, String cekdownload) {
        this.noprov = noprov;
        this.noruas = noruas;
        this.cekdownload = cekdownload;
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

    public String getCekdownload() {
        return cekdownload;
    }

    public void setCekdownload(String cekdownload) {
        this.cekdownload = cekdownload;
    }
}
