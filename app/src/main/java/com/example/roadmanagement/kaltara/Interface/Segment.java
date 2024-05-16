package com.example.roadmanagement.kaltara.Interface;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Segment {
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("nosegment")
    @Expose
    private List<String> noSegment;
    @SerializedName("km_awal")
    @Expose
    private List<String> kmAwalseg;
    @SerializedName("km_akhir")
    @Expose
    private List<String> kmAkhirseg;

    public Segment(List<String> noSegment, List<String> kmAwalseg, List<String> kmAkhirseg) {
        this.noSegment = noSegment;
        this.kmAwalseg = kmAwalseg;
        this.kmAkhirseg = kmAkhirseg;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<String> getNoSegment() {
        return noSegment;
    }

    public void setNoSegment(List<String> noSegment) {
        this.noSegment = noSegment;
    }

    public List<String> getKmAwalseg() {
        return kmAwalseg;
    }

    public void setKmAwalseg(List<String> kmAwalseg) {
        this.kmAwalseg = kmAwalseg;
    }

    public List<String> getKmAkhirseg() {
        return kmAkhirseg;
    }

    public void setKmAkhirseg(List<String> kmAkhirseg) {
        this.kmAkhirseg = kmAkhirseg;
    }
}
