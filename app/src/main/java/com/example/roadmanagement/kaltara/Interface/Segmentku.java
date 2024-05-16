package com.example.roadmanagement.kaltara.Interface;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Segmentku {

    @SerializedName("error")
    @Expose
    private String error;

    @SerializedName("datasegment")
    @Expose
    private List<SingleSegment> list;

    public Segmentku(String error, ArrayList<SingleSegment> list) {
        this.error = error;
        this.list = list;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<SingleSegment> getList() {
        return list;
    }

    public void setList(List<SingleSegment> list) {
        this.list = list;
    }
}
