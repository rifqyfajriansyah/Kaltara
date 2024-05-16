package com.example.roadmanagement.kaltara.Task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.roadmanagement.kaltara.DownloadBaru.DetailDownload;
import com.example.roadmanagement.kaltara.Interface.DataSegmen;
import com.example.roadmanagement.kaltara.Interface.SendId;
import com.example.roadmanagement.kaltara.Interface.SingleSegment;
import com.example.roadmanagement.kaltara.Menuutama;
import com.example.roadmanagement.kaltara.databaseHelper.DbBahu;
import com.example.roadmanagement.kaltara.databaseHelper.DbLahan;
import com.example.roadmanagement.kaltara.databaseHelper.DbLane;
import com.example.roadmanagement.kaltara.databaseHelper.DbMedian;
import com.example.roadmanagement.kaltara.databaseHelper.DbRuas;
import com.example.roadmanagement.kaltara.databaseHelper.DbSaluran;
import com.example.roadmanagement.kaltara.databaseHelper.DbSegmen;
import com.example.roadmanagement.kaltara.databaseHelper.DbSpinner;
import com.example.roadmanagement.kaltara.helper.Session;

import java.util.ArrayList;

public class JumlahSegmentTask extends AsyncTask<String, String, String> {
    ProgressDialog dialog;
    Context context;

    DbLahan dbLahan;
    DbSaluran dbSaluran;
    DbBahu dbBahu;
    DbMedian dbMedian;
    DbLane dbLane;
    DbSegmen dbSegmen;
    DbSpinner dbSpinner;
    SendId sendId;

    Session session;


    String ruas;



    ArrayList<DetailDownload> listsegment;


    public JumlahSegmentTask (final Context context, ArrayList<DetailDownload> listsegment, String ruas){
        this.context = context;
        this.listsegment = listsegment;
        this.ruas = ruas;


        dialog = new ProgressDialog(context);
        dialog.setIndeterminate(false);
        dialog.setMessage("Downloading Data Ruas "+ruas);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);


        dbLahan = new DbLahan(context);
        dbSaluran = new DbSaluran(context);
        dbBahu = new DbBahu(context);
        dbLane = new DbLane(context);
        dbMedian = new DbMedian(context);
        dbSegmen = new DbSegmen(context);
        dbSpinner = new DbSpinner(context);

        session = new Session(context);

    }

    @Override
    protected void onPreExecute() {
        dialog.show();
        dialog.setProgress(0);
        dialog.setIndeterminate(false);
        dialog.setCancelable(false);
        dialog.setMax(listsegment.size());
        int progressbarstatus = 0;

    };

    @Override
    protected String doInBackground(String... params) {

        for (int i = 0; i < listsegment.size(); i++) {
            dialog.setProgress(i+1);
            DetailDownload detailDownload = listsegment.get(i);
            String provinsi = detailDownload.getNoprov();
            String ruas = detailDownload.getNoruas();
            int jumlah = detailDownload.getL1()+detailDownload.getL2()+detailDownload.getL3()+detailDownload.getL4()+detailDownload.getR1()+detailDownload.getR2()+detailDownload.getR3()+detailDownload.getR4();
            int segment = Integer.valueOf(detailDownload.getNoseg());

            /*dbSpinner.setSpinner(new SingleSegment(provinsi, ruas, detailDownload.getNoseg(), detailDownload.getKmaawal(), detailDownload.getKm_akhir(), detailDownload.getStaawal(), detailDownload.getSta_akhir()));

            dbSegmen.postSegment(new DataSegmen(0, provinsi, ruas, segment, detailDownload.getL1(), detailDownload.getL2(), detailDownload.getL3(), detailDownload.getL4(), detailDownload.getL5(),detailDownload.getL6(),detailDownload.getL7(),detailDownload.getL8(), detailDownload.getL9(), detailDownload.getL10(),
                    detailDownload.getR1(), detailDownload.getR2(), detailDownload.getR3(), detailDownload.getR4(), detailDownload.getR5(),detailDownload.getR6(),detailDownload.getR7(),detailDownload.getR8(), detailDownload.getR9(),detailDownload.getR10(),
                    jumlah, detailDownload.getVertikal(), detailDownload.getHorizontal(), detailDownload.getTipejalan(), detailDownload.getLebarpvmt(),detailDownload.getGrade()));

            dbLahan.setLahan(detailDownload.getNoprov(), ruas, segment, "kiri", detailDownload.getLtipelahan(), detailDownload.getLtagunlahan(), 0, detailDownload.getLtinggilahan());
            dbLahan.setLahan(detailDownload.getNoprov(), ruas, segment, "kanan", detailDownload.getRtipelahan(), detailDownload.getRtagunlahan(), 0, detailDownload.getRtinggilahan());

            dbBahu.setBahu(provinsi, ruas, segment, "kiri", getBahu(detailDownload.getLtipebahu()), detailDownload.getLlebarbahu(), getBahu(detailDownload.getLtipebahuinner()), detailDownload.getLlebarbahuinner());
            dbBahu.setBahu(provinsi, ruas, segment, "kanan", getBahu(detailDownload.getRtipebahu()), detailDownload.getRlebarbahu(), getBahu(detailDownload.getRtipebahuinner()), detailDownload.getRlebarbahuinner());

            dbSaluran.setSaluran(provinsi, ruas, segment, "kiri", detailDownload.getLtipesaluran(), detailDownload.getLlebarsaluran(), detailDownload.getLdalamsaluran());
            dbSaluran.setSaluran(provinsi, ruas, segment, "kanan", detailDownload.getRtipesaluran(), detailDownload.getRlebarsaluran(), detailDownload.getRdalamsaluran());

            dbMedian.setMedian(provinsi, ruas, segment, detailDownload.getTipemedian(), detailDownload.getLebarmedian());

            if(detailDownload.getL1()==1){
                dbLane.setLane(provinsi, ruas, segment, "kiri", "L1", getpvmt(detailDownload.getL1pvmt()), detailDownload.getL1lebarlane());
            }
            if(detailDownload.getL2()==1){
                dbLane.setLane(provinsi, ruas, segment, "kiri", "L2", getpvmt(detailDownload.getL2pvmt()), detailDownload.getL2lebarlane());
            }
            if(detailDownload.getL3()==1){
                dbLane.setLane(provinsi, ruas, segment, "kiri", "L3", getpvmt(detailDownload.getL3pvmt()), detailDownload.getL3lebarlane());
            }
            if(detailDownload.getL4()==1){
                dbLane.setLane(provinsi, ruas, segment, "kiri", "L4", getpvmt(detailDownload.getL4pvmt()), detailDownload.getL4lebarlane());
            }
            if(detailDownload.getL5()==1){
                dbLane.setLane(provinsi, ruas, segment, "kiri", "L5", getpvmt(detailDownload.getL5pvmt()), detailDownload.getL5lebarlane());
            }
            if(detailDownload.getL6()==1){
                dbLane.setLane(provinsi, ruas, segment, "kiri", "L6", getpvmt(detailDownload.getL6pvmt()), detailDownload.getL6lebarlane());
            }
            if(detailDownload.getL7()==1){
                dbLane.setLane(provinsi, ruas, segment, "kiri", "L7", getpvmt(detailDownload.getL7pvmt()), detailDownload.getL7lebarlane());
            }
            if(detailDownload.getL8()==1){
                dbLane.setLane(provinsi, ruas, segment, "kiri", "L8", getpvmt(detailDownload.getL8pvmt()), detailDownload.getL8lebarlane());
            }
            if(detailDownload.getL9()==1){
                dbLane.setLane(provinsi, ruas, segment, "kiri", "L9", getpvmt(detailDownload.getL9pvmt()), detailDownload.getL9lebarlane());
            }
            if(detailDownload.getL10()==1){
                dbLane.setLane(provinsi, ruas, segment, "kiri", "L10", getpvmt(detailDownload.getL10pvmt()), detailDownload.getL10lebarlane());
            }

            if(detailDownload.getR1()==1){
                dbLane.setLane(provinsi, ruas, segment, "kanan", "R1", getpvmt(detailDownload.getR1pvmt()), detailDownload.getR1lebarlane());
            }
            if(detailDownload.getR2()==1){
                dbLane.setLane(provinsi, ruas, segment, "kanan", "R2", getpvmt(detailDownload.getR2pvmt()), detailDownload.getR2lebarlane());
            }
            if(detailDownload.getR3()==1){
                dbLane.setLane(provinsi, ruas, segment, "kanan", "R3", getpvmt(detailDownload.getR3pvmt()), detailDownload.getR3lebarlane());
            }
            if(detailDownload.getR4()==1){
                dbLane.setLane(provinsi, ruas, segment, "kanan", "R4", getpvmt(detailDownload.getR4pvmt()), detailDownload.getR4lebarlane());
            }
            if(detailDownload.getR5()==1){
                dbLane.setLane(provinsi, ruas, segment, "kanan", "R5", getpvmt(detailDownload.getR5pvmt()), detailDownload.getR5lebarlane());
            }
            if(detailDownload.getR6()==1){
                dbLane.setLane(provinsi, ruas, segment, "kanan", "R6", getpvmt(detailDownload.getR6pvmt()), detailDownload.getR6lebarlane());
            }
            if(detailDownload.getR7()==1){
                dbLane.setLane(provinsi, ruas, segment, "kanan", "R7", getpvmt(detailDownload.getR7pvmt()), detailDownload.getR7lebarlane());
            }
            if(detailDownload.getR8()==1){
                dbLane.setLane(provinsi, ruas, segment, "kanan", "R8", getpvmt(detailDownload.getR8pvmt()), detailDownload.getR8lebarlane());
            }
            if(detailDownload.getR9()==1){
                dbLane.setLane(provinsi, ruas, segment, "kanan", "R9", getpvmt(detailDownload.getR9pvmt()), detailDownload.getR9lebarlane());
            }
            if(detailDownload.getR10()==1){
                dbLane.setLane(provinsi, ruas, segment, "kanan", "R10", getpvmt(detailDownload.getR10pvmt()), detailDownload.getR10lebarlane());
            }*/

        }
        return "Selesai";
    }

    @Override
    protected void onPostExecute(String result) {
        ((Activity)context).finish();
        Intent i = new Intent(context, Menuutama.class);
        context.startActivity(i);
        super.onPostExecute("Selesai");

    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    public String getpvmt(String scv){

        String sc;
        switch (scv){
            case "Aspal Beton" : sc="Aspal/Beton (AC)";
                break;

            case "Aspal/Beton" : sc="Aspal/Beton (AC)";
                break;

            case "ASPAL BETON (AC)" : sc="Aspal/Beton (AC)";
                break;

            case "ASPAL BETON (AC) dan CONCRETE" : sc="Aspal/Beton (AC)";
                break;

            case "Aspal Beton (AC)" : sc="Aspal/Beton (AC)";
                break;

            case "BETON (AC)" : sc="Aspal/Beton (AC)";
                break;

            case "aspal" : sc="Aspal/Beton (AC)";
                break;

            case "TANAH" : sc="Unpaved/Tanah";
                break;

            case "UNPAVED" : sc="Unpaved/Tanah";
                break;

            case "RIGID" : sc="Concrete/Rigid";
                break;

            case "CONCRETE" : sc="Concrete/Rigid";
                break;

            case "JAPAT (AWCAS) / KERIKIL" : sc="Japat (Awcas) / Kerikil";
                break;

            case "flexible" : sc="FLEXIBLE";
                break;

            case "PENETRASI MACADAM 1 LAPIS" : sc="Penetrasi Macadam 1 Lapis";
                break;


            default: sc = scv;

        }
        return  sc;
    }

    public String getBahu(String tipes){
        String tipebahu;
        if(tipes.equals("diperkeras") || tipes.equals("bahu yang diperkeras")){
            tipebahu = "BAHU YANG DIPERKERAS";
        }else if(tipes.equals("bahu lunak")){
            tipebahu="BAHU LUNAK";
        }else{
            tipebahu = tipes;
        }

        return tipebahu;
    }

}
