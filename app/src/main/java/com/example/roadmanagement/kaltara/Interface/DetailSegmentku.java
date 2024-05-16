package com.example.roadmanagement.kaltara.Interface;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DetailSegmentku {

    @SerializedName("data")
    @Expose
    ArrayList<DataSegmen> dataSegmen;

    public ArrayList<DataSegmen> getDataSegmen() {
        return dataSegmen;
    }

    public void setDataSegmen(ArrayList<DataSegmen> dataSegmen) {
        this.dataSegmen = dataSegmen;
    }
}
