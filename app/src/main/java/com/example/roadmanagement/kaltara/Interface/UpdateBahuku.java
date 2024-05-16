package com.example.roadmanagement.kaltara.Interface;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UpdateBahuku {
    public ArrayList<DataBahu> getDataBahus() {
        return dataBahus;
    }

    public void setDataBahus(ArrayList<DataBahu> dataBahus) {
        this.dataBahus = dataBahus;
    }

    @SerializedName("data")
    @Expose
    ArrayList<DataBahu> dataBahus;
}
