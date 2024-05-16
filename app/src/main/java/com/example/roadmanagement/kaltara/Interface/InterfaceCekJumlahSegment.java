package com.example.roadmanagement.kaltara.Interface;

import com.example.roadmanagement.kaltara.DownloadBaru.DetailDownload;

import java.util.ArrayList;

public interface InterfaceCekJumlahSegment {
    void cekSegment(String status, int segment, ArrayList<DetailDownload> detailDownloads);
}
