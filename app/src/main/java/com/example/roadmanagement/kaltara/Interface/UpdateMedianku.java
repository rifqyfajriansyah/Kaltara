package com.example.roadmanagement.kaltara.Interface;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UpdateMedianku {

    @SerializedName("data")
    @Expose
    ArrayList<DataMedian> dataMedians;

    public ArrayList<DataMedian> getDataMedians() {
        return dataMedians;
    }

    public void setDataMedians(ArrayList<DataMedian> dataMedians) {
        this.dataMedians = dataMedians;
    }
}
