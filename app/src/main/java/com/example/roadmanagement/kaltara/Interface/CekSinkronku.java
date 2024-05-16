package com.example.roadmanagement.kaltara.Interface;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CekSinkronku {

    @SerializedName("id")
    @Expose
    String id;

    @SerializedName("data")
    @Expose
    ArrayList<CekSinkron> cekSinkrons;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<CekSinkron> getCekSinkrons() {
        return cekSinkrons;
    }

    public void setCekSinkrons(ArrayList<CekSinkron> cekSinkrons) {
        this.cekSinkrons = cekSinkrons;
    }
}
