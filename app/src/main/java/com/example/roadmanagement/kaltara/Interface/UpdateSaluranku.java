package com.example.roadmanagement.kaltara.Interface;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UpdateSaluranku {

    public ArrayList<DataSaluran> getDataSalurans() {
        return dataSalurans;
    }

    public void setDataSalurans(ArrayList<DataSaluran> dataSalurans) {
        this.dataSalurans = dataSalurans;
    }

    @SerializedName("data")
    @Expose
    ArrayList<DataSaluran> dataSalurans;
}
