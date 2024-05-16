package com.example.roadmanagement.kaltara.DownloadBaru;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ListDownload {

    @SerializedName("data")
    @Expose
    private ArrayList<DetailDownload> list;


    @SerializedName("sinkronid")
    @Expose
    private String sinkronid;

    public ArrayList<DetailDownload> getList() {
        return list;
    }

    public void setList(ArrayList<DetailDownload> list) {
        this.list = list;
    }


    public String getSinkronid() {
        return sinkronid;
    }

    public void setSinkronid(String sinkronid) {
        this.sinkronid = sinkronid;
    }
}
