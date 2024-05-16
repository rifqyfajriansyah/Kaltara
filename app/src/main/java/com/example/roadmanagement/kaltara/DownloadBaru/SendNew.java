package com.example.roadmanagement.kaltara.DownloadBaru;

import com.example.roadmanagement.kaltara.Interface.DataDownload;

import java.util.ArrayList;

public interface SendNew {
    void DownloadData(ArrayList<DetailDownload> detailDownloads) throws InterruptedException;
}
