package com.example.roadmanagement.kaltara.Interface;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CekSinkron {

    @SerializedName("no_prov")
    @Expose
    private String noprov;

    @SerializedName("no_ruas")
    @Expose
    private String noruas;

    @SerializedName("no_seg")
    @Expose
    private int noseg;

    @SerializedName("sub_seg")
    @Expose
    private int subseg;

    @SerializedName("no_seg_akhir")
    @Expose
    private int nosegAkhir;

    @SerializedName("sub_seg_akhir")
    @Expose
    private int subsegAkhir;

    @SerializedName("detail")
    @Expose
    private String detail;

    @SerializedName("posisi")
    @Expose
    private String posisi;

    @SerializedName("lajur")
    @Expose
    private String lajur;

    @SerializedName("jenis_survey")
    @Expose
    private String jenis;


    public CekSinkron(String noprov, String noruas, int noseg, int subseg, int nosegAkhir, int subsegAkhir, String detail, String posisi, String lajur, String jenis) {
        this.noprov = noprov;
        this.noruas = noruas;
        this.noseg = noseg;
        this.subseg = subseg;
        this.nosegAkhir = nosegAkhir;
        this.subsegAkhir = subsegAkhir;
        this.detail = detail;
        this.posisi = posisi;
        this.lajur = lajur;
        this.jenis = jenis;
    }

    public int getSubseg() {
        return subseg;
    }

    public void setSubseg(int subseg) {
        this.subseg = subseg;
    }

    public int getSubsegAkhir() {
        return subsegAkhir;
    }

    public void setSubsegAkhir(int subsegAkhir) {
        this.subsegAkhir = subsegAkhir;
    }

    public int getNoseg() {
        return noseg;
    }

    public void setNoseg(int noseg) {
        this.noseg = noseg;
    }

    public String getLajur() {
        return lajur;
    }

    public void setLajur(String lajur) {
        this.lajur = lajur;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public int getNosegAkhir() {
        return nosegAkhir;
    }

    public void setNosegAkhir(int nosegAkhir) {
        this.nosegAkhir = nosegAkhir;
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


    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPosisi() {
        return posisi;
    }

    public void setPosisi(String posisi) {
        this.posisi = posisi;
    }
}
