package com.example.roadmanagement.kaltara.Interface;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Ruasku {

    @SerializedName("error")
    @Expose
    String error;
    @SerializedName("dataruas")
    @Expose
    ArrayList<DataRuas> dataRuas;

    public ArrayList<DataRuas> getDataRuas() {
        return dataRuas;
    }

    public void setDataRuas(ArrayList<DataRuas> dataRuas) {
        this.dataRuas = dataRuas;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
