package com.example.roadmanagement.kaltara.Interface;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UpdateLaneku {

    @SerializedName("data")
    @Expose
    ArrayList<DataLane> dataLanes;

    public ArrayList<DataLane> getDataLanes() {
        return dataLanes;
    }

    public void setDataLanes(ArrayList<DataLane> dataLanes) {
        this.dataLanes = dataLanes;
    }
}
