package com.example.roadmanagement.kaltara.Interface;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Downloadku {
    @SerializedName("error")
    @Expose
    private String error;

    @SerializedName("data")
    @Expose
    private ArrayList<DataDownload> list;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ArrayList<DataDownload> getList() {
        return list;
    }

    public void setList(ArrayList<DataDownload> list) {
        this.list = list;
    }
}
