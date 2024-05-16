package com.example.roadmanagement.kaltara.Interface;

public class DataTemporari {
    public static final String TABLE_NAME= "datasementara";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOPROV = "noprov";
    public static final String COLUMN_RUAS = "noruas";
    public static final String COLUMN_SEGMENT = "nosegment";
    public static final String COLUMN_SUB_SEGMENT = "subsegment";
    public static final String COLUMN_TIPE = "tipe";
    public static final String COLUMN_POSISI = "posisi";
    public static final String COLUMN_URUT = "urut";
    public static final String COLUMN_JENIS = "jenis";
    public static final String COLUMN_SEGMENTAKHIR = "segmentakhir";
    public static final String COLUMN_SUB_SEGMENTAKHIR = "subsegmentakhir";
    public static final String COLUMN_FOTO = "foto";
    public static final String COLUMN_STATUS = "status";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NOPROV + " TEXT,"
                    + COLUMN_RUAS + " TEXT,"
                    + COLUMN_SEGMENT + " INTEGER,"
                    + COLUMN_SUB_SEGMENT + " INTEGER,"
                    + COLUMN_TIPE + " TEXT,"
                    + COLUMN_POSISI + " TEXT,"
                    + COLUMN_URUT+ " TEXT,"
                    + COLUMN_JENIS+ " TEXT,"
                    + COLUMN_SEGMENTAKHIR+ " INTEGER,"
                    + COLUMN_SUB_SEGMENTAKHIR+ " INTEGER,"
                    + COLUMN_FOTO+ " TEXT,"
                    + COLUMN_STATUS + " TEXT"
                    + ")";
    private int id;
    private String noprov;
    private String noruas;
    private int nosegment;
    private int subsegment;
    private String tipe;
    private String posisi;
    private String urut;
    private String jenis;
    private int segmentakhir;
    private int subsegmentakhir;
    private String foto;
    private String status;

    public DataTemporari(String noprov, String noruas, int nosegment, int subsegment, String tipe, String posisi, String urut, String jenis, int segmentakhir, int subsegmentakhir, String foto, String status) {
        this.noprov = noprov;
        this.noruas = noruas;
        this.nosegment = nosegment;
        this.subsegment = subsegment;
        this.tipe = tipe;
        this.posisi = posisi;
        this.urut = urut;
        this.jenis = jenis;
        this.segmentakhir = segmentakhir;
        this.subsegmentakhir = subsegmentakhir;
        this.foto = foto;
        this.status = status;
    }

    public int getSubsegment() {
        return subsegment;
    }

    public void setSubsegment(int subsegment) {
        this.subsegment = subsegment;
    }

    public int getSubsegmentakhir() {
        return subsegmentakhir;
    }

    public void setSubsegmentakhir(int subsegmentakhir) {
        this.subsegmentakhir = subsegmentakhir;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public int getSegmentakhir() {
        return segmentakhir;
    }

    public void setSegmentakhir(int segmentakhir) {
        this.segmentakhir = segmentakhir;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
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

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getPosisi() {
        return posisi;
    }

    public void setPosisi(String posisi) {
        this.posisi = posisi;
    }

    public String getUrut() {
        return urut;
    }

    public void setUrut(String urut) {
        this.urut = urut;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
