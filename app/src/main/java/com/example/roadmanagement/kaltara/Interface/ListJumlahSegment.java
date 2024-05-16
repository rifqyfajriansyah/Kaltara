package com.example.roadmanagement.kaltara.Interface;

import com.example.roadmanagement.kaltara.DownloadBaru.DetailDownload;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ListJumlahSegment {

    @SerializedName("hasil")
    @Expose
    private String hasil;
    @SerializedName("segmentDb")
    @Expose
    private int segmentDb;
    @SerializedName("data")
    @Expose
    private ArrayList<DetailDownload> detailDownloads;

    public String getHasil() {
        return hasil;
    }

    public void setHasil(String hasil) {
        this.hasil = hasil;
    }

    public int getSegmentDb() {
        return segmentDb;
    }

    public void setSegmentDb(int segmentDb) {
        this.segmentDb = segmentDb;
    }

    public ArrayList<DetailDownload> getDetailDownloads() {
        return detailDownloads;
    }

    public void setDetailDownloads(ArrayList<DetailDownload> detailDownloads) {
        this.detailDownloads = detailDownloads;
    }

}
