package com.example.roadmanagement.kaltara.DownloadBaru;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ListDrainase {

    @SerializedName("data")
    @Expose
    private ArrayList<DetailDrainase> list;



    public ArrayList<DetailDrainase> getList() {
        return list;
    }

    public void setList(ArrayList<DetailDrainase> list) {
        this.list = list;
    }


}
