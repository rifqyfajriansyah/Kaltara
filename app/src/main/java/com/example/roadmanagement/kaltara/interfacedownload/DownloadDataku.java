package com.example.roadmanagement.kaltara.interfacedownload;

import com.example.roadmanagement.kaltara.Interface.DataDownload;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DownloadDataku {

    @SerializedName("error")
    @Expose
    private String error;

    @SerializedName("data")
    @Expose
    private ArrayList<DownloadDataIsi> list;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ArrayList<DownloadDataIsi> getList() {
        return list;
    }

    public void setList(ArrayList<DownloadDataIsi> list) {
        this.list = list;
    }

}
