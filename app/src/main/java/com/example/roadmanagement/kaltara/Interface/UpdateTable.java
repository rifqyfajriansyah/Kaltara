package com.example.roadmanagement.kaltara.Interface;

public class UpdateTable {

    public static final String TABLE_NAME= "tableupdate";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NORUAS = "waktu";
    public static final String COLUMN_WAKTU = "noruas";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NORUAS + " TEXT,"
                    + COLUMN_WAKTU + " TEXT"
                    + ")";


    String noruas;
    String waktu;

    public UpdateTable(String noruas, String waktu) {
        this.noruas = noruas;
        this.waktu = waktu;
    }

    public String getNoruas() {
        return noruas;
    }

    public void setNoruas(String noruas) {
        this.noruas = noruas;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }
}
