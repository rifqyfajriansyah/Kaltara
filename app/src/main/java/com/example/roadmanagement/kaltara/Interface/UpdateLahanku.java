package com.example.roadmanagement.kaltara.Interface;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UpdateLahanku {
    @SerializedName("data")
    @Expose
    ArrayList<DataLahan> dataLahans;

    public ArrayList<DataLahan> getDataLahans() {
        return dataLahans;
    }

    public void setDataLahans(ArrayList<DataLahan> dataLahans) {
        this.dataLahans = dataLahans;
    }
}
