package com.example.roadmanagement.kaltara.GetSemuaImage;

public class DataListImage {
    int segment;
    String pathgambar;
    String pathgambaricon;
    String pathlokasi;

    public DataListImage(int segment, String pathgambar, String pathgambaricon, String pathlokasi) {
        this.segment = segment;
        this.pathgambar = pathgambar;
        this.pathgambaricon = pathgambaricon;
        this.pathlokasi = pathlokasi;
    }

    public String getPathgambaricon() {
        return pathgambaricon;
    }

    public void setPathgambaricon(String pathgambaricon) {
        this.pathgambaricon = pathgambaricon;
    }

    public String getPathlokasi() {
        return pathlokasi;
    }

    public void setPathlokasi(String pathlokasi) {
        this.pathlokasi = pathlokasi;
    }

    public int getSegment() {
        return segment;
    }

    public void setSegment(int segment) {
        this.segment = segment;
    }

    public String getPathgambar() {
        return pathgambar;
    }

    public void setPathgambar(String pathgambar) {
        this.pathgambar = pathgambar;
    }



}
