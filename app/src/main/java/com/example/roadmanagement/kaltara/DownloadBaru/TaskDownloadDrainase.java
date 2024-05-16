package com.example.roadmanagement.kaltara.DownloadBaru;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.roadmanagement.kaltara.Interface.DataCrossDrain;
import com.example.roadmanagement.kaltara.Interface.DataDrainase;
import com.example.roadmanagement.kaltara.Interface.DataInletMedian;
import com.example.roadmanagement.kaltara.Interface.DataInletTrotoar;
import com.example.roadmanagement.kaltara.Interface.DataOutlet;
import com.example.roadmanagement.kaltara.Interface.SendId;
import com.example.roadmanagement.kaltara.databaseHelper.DbGorong;
import com.example.roadmanagement.kaltara.databaseHelper.DbInlet;
import com.example.roadmanagement.kaltara.databaseHelper.DbLereng;
import com.example.roadmanagement.kaltara.databaseHelper.DbMinlet;
import com.example.roadmanagement.kaltara.databaseHelper.DbOutlet;
import com.example.roadmanagement.kaltara.databaseHelper.DbRuas;
import com.example.roadmanagement.kaltara.databaseHelper.DbSpinner;
import com.example.roadmanagement.kaltara.helper.Session;

import java.util.ArrayList;

public class TaskDownloadDrainase extends AsyncTask<String, String, String> {

    ProgressDialog dialog;
    Context context;

    DbSpinner dbSpinner;

    DbInlet dbInlet;
    DbMinlet dbMinlet;
    DbOutlet dbOutletl;
    DbGorong dbGorong;
    DbLereng dbLereng;

    SendId sendId;

    Session session;


    String ruas;
    int pruas;
    DbRuas dbRuas;



    ArrayList<DetailDrainase> listDrainase;

    //download single ruas - onclick pada spinner

    public TaskDownloadDrainase (final Context context, ArrayList<DetailDrainase> listDrainase, String ruas, int pruas, SendId sendId){
        this.context = context;
        this.listDrainase = listDrainase;
        this.sendId = sendId;
        this.pruas = pruas;
        this.ruas = ruas;

        dialog = new ProgressDialog(context);
        dialog.setIndeterminate(false);
        dialog.setMessage("Downloading Data Drainase "+ruas);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);


        dbSpinner = new DbSpinner(context);
        dbRuas = new DbRuas(context);

        dbInlet = new DbInlet(context);
        dbMinlet = new DbMinlet(context);
        dbOutletl = new DbOutlet(context);
        dbGorong = new DbGorong(context);
        dbLereng = new DbLereng(context);

        session = new Session(context);

    }

    @Override
    protected void onPreExecute() {
        dialog.show();
        dialog.setProgress(0);
        dialog.setIndeterminate(false);
        dialog.setCancelable(false);
        dialog.setMax(listDrainase.size());
        int progressbarstatus = 0;

    };

    @Override
    protected String doInBackground(String... params) {

        for (int i = 0; i < listDrainase.size(); i++) {
            dialog.setProgress(i+1);
            DetailDrainase detailDrainase = listDrainase.get(i);
            String provinsi = detailDrainase.getNoprov();
            String ruas = detailDrainase.getNoruas();
            int segment = detailDrainase.getNoseg();
            int subsegment = detailDrainase.getSubseg();
            //ganti nanti


            DataInletTrotoar dataInletKiri = new DataInletTrotoar(provinsi, ruas, segment, subsegment, "kiri", detailDrainase.getlKeberadaanInlet(), detailDrainase.getlPenampangInlet(), detailDrainase.getlTinggiInlet(), detailDrainase.getlPanjangInlet(), detailDrainase.getlLebarInlet(), detailDrainase.getlSedimenInlet(), detailDrainase.getlKonstruksiInlet(), detailDrainase.getlKondisiInlet(), null, null, null);
            DataInletTrotoar dataInletKanan = new DataInletTrotoar(provinsi, ruas, segment, subsegment, "kanan", detailDrainase.getrKeberadaanInlet(), detailDrainase.getrPenampangInlet(), detailDrainase.getrTinggiInlet(), detailDrainase.getrPanjangInlet(), detailDrainase.getrLebarInlet(), detailDrainase.getrSedimenInlet(), detailDrainase.getrKonstruksiInlet(), detailDrainase.getrKondisiInlet(), null, null, null);

            //DataInletMedian dataMinletKiri = new DataInletMedian(provinsi, ruas, segment, subsegment, "kiri", detailDrainase.getlKeberadaanMinlet(), detailDrainase.getlPenampangMinlet(), detailDrainase.getlTinggiMinlet(), detailDrainase.getlPanjangMinlet(), detailDrainase.getlLebarMinlet(), detailDrainase.getlSedimenMinlet(), detailDrainase.getlKonstruksiMinlet(), detailDrainase.getlKondisiMinlet(), null, null, null);
            //DataInletMedian dataMinletKanan = new DataInletMedian(provinsi, ruas, segment, subsegment, "kanan", detailDrainase.getrKeberadaanMinlet(), detailDrainase.getrPenampangMinlet(), detailDrainase.getrTinggiMinlet(), detailDrainase.getrPanjangMinlet(), detailDrainase.getrLebarMinlet(), detailDrainase.getrSedimenMinlet(), detailDrainase.getrKonstruksiMinlet(), detailDrainase.getrKondisiMinlet(), null, null, null);

            DataOutlet dataOutletKiri = new DataOutlet(provinsi, ruas, segment, subsegment, "kiri", detailDrainase.getlKeberadaanOutlet(), detailDrainase.getlPenampangOutlet(), detailDrainase.getlDiameterOutlet(), detailDrainase.getlLebarOutlet(), detailDrainase.getlTinggiOutlet(), detailDrainase.getlKonstruksiOutlet(), detailDrainase.getlKondisiOutlet(), null, null, null);
            DataOutlet dataOutletKanan = new DataOutlet(provinsi, ruas, segment, subsegment, "kanan", detailDrainase.getrKeberadaanOutlet(), detailDrainase.getrPenampangOutlet(), detailDrainase.getrDiameterOutlet(), detailDrainase.getrLebarOutlet(), detailDrainase.getrTinggiOutlet(), detailDrainase.getrKonstruksiOutlet(), detailDrainase.getrKondisiOutlet(), null, null, null);

            DataCrossDrain dataGorongKiri = new DataCrossDrain(provinsi, ruas, segment, subsegment, "kiri", detailDrainase.getlKeberadaanGorong(), detailDrainase.getlPenampangGorong(), detailDrainase.getlDiameterGorong(), detailDrainase.getlLebarGorong(), detailDrainase.getlTinggiGorong(), detailDrainase.getlKonstruksiGorong(), detailDrainase.getlKondisiGorong(), null, null, null);
            DataCrossDrain dataGorongKanan = new DataCrossDrain(provinsi, ruas, segment, subsegment, "kanan", detailDrainase.getrKeberadaanGorong(), detailDrainase.getrPenampangGorong(), detailDrainase.getrDiameterGorong(), detailDrainase.getrLebarGorong(), detailDrainase.getrTinggiGorong(), detailDrainase.getrKonstruksiGorong(), detailDrainase.getrKondisiGorong(), null, null, null);

            DataDrainase dataLerengKiri = new DataDrainase(provinsi, ruas, segment, subsegment, "kiri", detailDrainase.getlKeberadaanLereng(), detailDrainase.getlPenampangLereng(), detailDrainase.getlLebarLereng(), detailDrainase.getlTinggiLereng(), detailDrainase.getlSedimenLereng(), detailDrainase.getlKondisiLereng(), null, null, null);
            DataDrainase dataLerengKanan = new DataDrainase(provinsi, ruas, segment, subsegment, "kanan", detailDrainase.getrKeberadaanLereng(), detailDrainase.getrPenampangLereng(), detailDrainase.getrLebarLereng(), detailDrainase.getrTinggiLereng(), detailDrainase.getrSedimenLereng(), detailDrainase.getrKondisiLereng(), null, null, null);


            dbInlet.setInlet(dataInletKiri);
            dbInlet.setInlet(dataInletKanan);

           // dbMinlet.setMinlet(dataMinletKiri);
           // dbMinlet.setMinlet(dataMinletKanan);

            dbOutletl.setSaluran(dataOutletKiri);
            dbOutletl.setSaluran(dataOutletKanan);

            dbGorong.setSaluran(dataGorongKiri);
            dbGorong.setSaluran(dataGorongKanan);

            dbLereng.setLereng(dataLerengKiri);
            dbLereng.setLereng(dataLerengKanan);

        }
        return "Selesai";
    }

    @Override
    protected void onPostExecute(String result) {

        dialog.dismiss();
        sendId.hapusGambar(pruas);
        super.onPostExecute("Selesai");

    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }


}
