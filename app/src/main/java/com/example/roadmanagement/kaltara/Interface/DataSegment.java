package com.example.roadmanagement.kaltara.Interface;

public class DataSegment {

    String noseg;
    String kmawal;
    String kmakhir;
    String stawal;
    String stakhir;

    public DataSegment(String noseg, String kmawal, String kmakhir, String stawal, String stakhir) {
        this.noseg = noseg;
        this.kmawal = kmawal;
        this.kmakhir = kmakhir;
        this.stawal = stawal;
        this.stakhir = stakhir;
    }

    public String getStawal() {
        return stawal;
    }

    public void setStawal(String stawal) {
        this.stawal = stawal;
    }

    public String getStakhir() {
        return stakhir;
    }

    public void setStakhir(String stakhir) {
        this.stakhir = stakhir;
    }

    public String getNoseg() {
        return noseg;
    }

    public void setNoseg(String noseg) {
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
}
