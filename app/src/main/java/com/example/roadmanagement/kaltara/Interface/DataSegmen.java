package com.example.roadmanagement.kaltara.Interface;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataSegmen {

    public static final String TABLE_NAME = "datasegmen";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOPROV = "noprov";
    public static final String COLUMN_RUAS = "noruas";
    public static final String COLUMN_SEGMENT = "nosegment";
    public static final String COLUMN_SUB_SEGMENT = "subsegment";
    public static final String COLUMN_SEGMENL1 = "segmenl1";
    public static final String COLUMN_SEGMENL2 = "segmenl2";
    public static final String COLUMN_SEGMENL3 = "segmenl3";
    public static final String COLUMN_SEGMENL4 = "segmenl4";
    public static final String COLUMN_SEGMENL5 = "segmenl5";
    public static final String COLUMN_SEGMENL6 = "segmenl6";
    public static final String COLUMN_SEGMENL7 = "segmenl7";
    public static final String COLUMN_SEGMENL8 = "segmenl8";
    public static final String COLUMN_SEGMENL9 = "segmenl9";
    public static final String COLUMN_SEGMENL10 = "segmenl10";
    public static final String COLUMN_SEGMENR1 = "segmenr1";
    public static final String COLUMN_SEGMENR2 = "segmenr2";
    public static final String COLUMN_SEGMENR3 = "segmenr3";
    public static final String COLUMN_SEGMENR4 = "segmenr4";
    public static final String COLUMN_SEGMENR5 = "segmenr5";
    public static final String COLUMN_SEGMENR6 = "segmenr6";
    public static final String COLUMN_SEGMENR7 = "segmenr7";
    public static final String COLUMN_SEGMENR8 = "segmenr8";
    public static final String COLUMN_SEGMENR9 = "segmenr9";
    public static final String COLUMN_SEGMENR10 = "segmenr10";
    public static final String COLUMN_JUMLAHSEGMEN = "jsegmen";
    public static final String VERTIKAL = "vertikal";
    public static final String HORIZONTAL = "horizontal";
    public static final String TIPEJALAN = "tipejalan";
    public static final String LEBARPVMT = "lebarpvmt";
    public static final String GRADE = "grade";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NOPROV + " TEXT,"
                    + COLUMN_RUAS + " TEXT,"
                    + COLUMN_SEGMENT + " INTEGER,"
                    + COLUMN_SUB_SEGMENT + " INTEGER,"
                    + COLUMN_SEGMENL1 + " INTEGER,"
                    + COLUMN_SEGMENL2 + " INTEGER,"
                    + COLUMN_SEGMENL3 + " INTEGER,"
                    + COLUMN_SEGMENL4 + " INTEGER,"
                    + COLUMN_SEGMENL5 + " INTEGER,"
                    + COLUMN_SEGMENL6 + " INTEGER,"
                    + COLUMN_SEGMENL7 + " INTEGER,"
                    + COLUMN_SEGMENL8 + " INTEGER,"
                    + COLUMN_SEGMENL9 + " INTEGER,"
                    + COLUMN_SEGMENL10 + " INTEGER,"
                    + COLUMN_SEGMENR1 + " INTEGER,"
                    + COLUMN_SEGMENR2 + " INTEGER,"
                    + COLUMN_SEGMENR3 + " INTEGER,"
                    + COLUMN_SEGMENR4 + " INTEGER,"
                    + COLUMN_SEGMENR5 + " INTEGER,"
                    + COLUMN_SEGMENR6 + " INTEGER,"
                    + COLUMN_SEGMENR7 + " INTEGER,"
                    + COLUMN_SEGMENR8 + " INTEGER,"
                    + COLUMN_SEGMENR9 + " INTEGER,"
                    + COLUMN_SEGMENR10 + " INTEGER,"
                    + COLUMN_JUMLAHSEGMEN + " INTEGER,"
                    + VERTIKAL+" TEXT,"
                    + HORIZONTAL+" TEXT,"
                    + TIPEJALAN+" TEXT,"
                    + LEBARPVMT+" NUMERIC,"
                    + GRADE+" NUMERIC"
                    + ")";
    private int id;

    @SerializedName("no_prov")
    @Expose
    private String noprov;
    @SerializedName("no_ruas")
    @Expose
    private String noruas;
    @SerializedName("no_seg")
    @Expose
    private int nosegment;
    @SerializedName("sub_seg")
    @Expose
    private int subsegment;

    @SerializedName("l1")
    @Expose
    private int segmentl1;
    @SerializedName("l2")
    @Expose
    private int segmentl2;
    @SerializedName("l3")
    @Expose
    private int segmentl3;
    @SerializedName("l4")
    @Expose
    private int segmentl4;
    @SerializedName("l5")
    @Expose
    private int segmentl5;
    @SerializedName("l6")
    @Expose
    private int segmentl6;
    @SerializedName("l7")
    @Expose
    private int segmentl7;
    @SerializedName("l8")
    @Expose
    private int segmentl8;
    @SerializedName("l9")
    @Expose
    private int segmentl9;
    @SerializedName("l10")
    @Expose
    private int segmentl10;

    @SerializedName("r1")
    @Expose
    private int segmentr1;
    @SerializedName("r2")
    @Expose
    private int segmentr2;
    @SerializedName("r3")
    @Expose
    private int segmentr3;
    @SerializedName("r4")
    @Expose
    private int segmentr4;
    @SerializedName("r5")
    @Expose
    private int segmentr5;
    @SerializedName("r6")
    @Expose
    private int segmentr6;
    @SerializedName("r7")
    @Expose
    private int segmentr7;
    @SerializedName("r8")
    @Expose
    private int segmentr8;
    @SerializedName("r9")
    @Expose
    private int segmentr9;
    @SerializedName("r10")
    @Expose
    private int segmentr10;

    @SerializedName("jumlah")
    @Expose
    private int jumlahsegment;
    @SerializedName("vertikal")
    @Expose
    private String vertikal;
    @SerializedName("horizontal")
    @Expose
    private String horizontal;
    @SerializedName("tipejalan")
    @Expose
    private String tipejalan;
    @SerializedName("lebarpvmt")
    @Expose
    private float lebarpvmt;
    @SerializedName("grade")
    @Expose
    private float grade;

    public int getSubsegment() {
        return subsegment;
    }

    public void setSubsegment(int subsegment) {
        this.subsegment = subsegment;
    }

    public DataSegmen(String noprov, String noruas, int nosegment, int subsegment, int segmentl1, int segmentl2, int segmentl3, int segmentl4, int segmentl5, int segmentl6, int segmentl7, int segmentl8, int segmentl9, int segmentl10, int segmentr1, int segmentr2, int segmentr3, int segmentr4, int segmentr5, int segmentr6, int segmentr7, int segmentr8, int segmentr9, int segmentr10, int jumlahsegment, String vertikal, String horizontal, String tipejalan, float lebarpvmt, float grade) {
        this.noprov = noprov;
        this.noruas = noruas;
        this.nosegment = nosegment;
        this.subsegment = subsegment;
        this.segmentl1 = segmentl1;
        this.segmentl2 = segmentl2;
        this.segmentl3 = segmentl3;
        this.segmentl4 = segmentl4;
        this.segmentl5 = segmentl5;
        this.segmentl6 = segmentl6;
        this.segmentl7 = segmentl7;
        this.segmentl8 = segmentl8;
        this.segmentl9 = segmentl9;
        this.segmentl10 = segmentl10;
        this.segmentr1 = segmentr1;
        this.segmentr2 = segmentr2;
        this.segmentr3 = segmentr3;
        this.segmentr4 = segmentr4;
        this.segmentr5 = segmentr5;
        this.segmentr6 = segmentr6;
        this.segmentr7 = segmentr7;
        this.segmentr8 = segmentr8;
        this.segmentr9 = segmentr9;
        this.segmentr10 = segmentr10;
        this.jumlahsegment = jumlahsegment;
        this.vertikal = vertikal;
        this.horizontal = horizontal;
        this.tipejalan = tipejalan;
        this.lebarpvmt = lebarpvmt;
        this.grade = grade;
    }

    public int getSegmentl9() {
        return segmentl9;
    }

    public void setSegmentl9(int segmentl9) {
        this.segmentl9 = segmentl9;
    }

    public int getSegmentl10() {
        return segmentl10;
    }

    public void setSegmentl10(int segmentl10) {
        this.segmentl10 = segmentl10;
    }

    public int getSegmentr9() {
        return segmentr9;
    }

    public void setSegmentr9(int segmentr9) {
        this.segmentr9 = segmentr9;
    }

    public int getSegmentr10() {
        return segmentr10;
    }

    public void setSegmentr10(int segmentr10) {
        this.segmentr10 = segmentr10;
    }

    public int getSegmentl5() {
        return segmentl5;
    }

    public void setSegmentl5(int segmentl5) {
        this.segmentl5 = segmentl5;
    }

    public int getSegmentl6() {
        return segmentl6;
    }

    public void setSegmentl6(int segmentl6) {
        this.segmentl6 = segmentl6;
    }

    public int getSegmentl7() {
        return segmentl7;
    }

    public void setSegmentl7(int segmentl7) {
        this.segmentl7 = segmentl7;
    }

    public int getSegmentl8() {
        return segmentl8;
    }

    public void setSegmentl8(int segmentl8) {
        this.segmentl8 = segmentl8;
    }

    public int getSegmentr5() {
        return segmentr5;
    }

    public void setSegmentr5(int segmentr5) {
        this.segmentr5 = segmentr5;
    }

    public int getSegmentr6() {
        return segmentr6;
    }

    public void setSegmentr6(int segmentr6) {
        this.segmentr6 = segmentr6;
    }

    public int getSegmentr7() {
        return segmentr7;
    }

    public void setSegmentr7(int segmentr7) {
        this.segmentr7 = segmentr7;
    }

    public int getSegmentr8() {
        return segmentr8;
    }

    public void setSegmentr8(int segmentr8) {
        this.segmentr8 = segmentr8;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public String getVertikal() {
        return vertikal;
    }

    public void setVertikal(String vertikal) {
        this.vertikal = vertikal;
    }

    public String getHorizontal() {
        return horizontal;
    }

    public void setHorizontal(String horizontal) {
        this.horizontal = horizontal;
    }

    public String getTipejalan() {
        return tipejalan;
    }

    public void setTipejalan(String tipejalan) {
        this.tipejalan = tipejalan;
    }



    public float getLebarpvmt() {
        return lebarpvmt;
    }

    public void setLebarpvmt(float lebarpvmt) {
        this.lebarpvmt = lebarpvmt;
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

    public int getSegmentl1() {
        return segmentl1;
    }

    public void setSegmentl1(int segmentl1) {
        this.segmentl1 = segmentl1;
    }

    public int getSegmentl2() {
        return segmentl2;
    }

    public void setSegmentl2(int segmentl2) {
        this.segmentl2 = segmentl2;
    }

    public int getSegmentl3() {
        return segmentl3;
    }

    public void setSegmentl3(int segmentl3) {
        this.segmentl3 = segmentl3;
    }

    public int getSegmentl4() {
        return segmentl4;
    }

    public void setSegmentl4(int segmentl4) {
        this.segmentl4 = segmentl4;
    }

    public int getSegmentr1() {
        return segmentr1;
    }

    public void setSegmentr1(int segmentr1) {
        this.segmentr1 = segmentr1;
    }

    public int getSegmentr2() {
        return segmentr2;
    }

    public void setSegmentr2(int segmentr2) {
        this.segmentr2 = segmentr2;
    }

    public int getSegmentr3() {
        return segmentr3;
    }

    public void setSegmentr3(int segmentr3) {
        this.segmentr3 = segmentr3;
    }

    public int getSegmentr4() {
        return segmentr4;
    }

    public void setSegmentr4(int segmentr4) {
        this.segmentr4 = segmentr4;
    }

    public int getJumlahsegment() {
        return jumlahsegment;
    }

    public void setJumlahsegment(int jumlahsegment) {
        this.jumlahsegment = jumlahsegment;
    }
}
