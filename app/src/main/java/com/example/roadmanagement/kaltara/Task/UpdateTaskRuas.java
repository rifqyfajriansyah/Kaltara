package com.example.roadmanagement.kaltara.Task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;

import com.example.roadmanagement.kaltara.Interface.DataBahu;
import com.example.roadmanagement.kaltara.Interface.DataLahan;
import com.example.roadmanagement.kaltara.Interface.DataLane;
import com.example.roadmanagement.kaltara.Interface.DataMedian;
import com.example.roadmanagement.kaltara.Interface.DataSaluran;
import com.example.roadmanagement.kaltara.Interface.DataSegmen;
import com.example.roadmanagement.kaltara.Interface.DataTemporari;
import com.example.roadmanagement.kaltara.Interface.SendId;
import com.example.roadmanagement.kaltara.api.FungsiAPI;
import com.example.roadmanagement.kaltara.databaseHelper.DbBahu;
import com.example.roadmanagement.kaltara.databaseHelper.DbLahan;
import com.example.roadmanagement.kaltara.databaseHelper.DbLane;
import com.example.roadmanagement.kaltara.databaseHelper.DbMedian;
import com.example.roadmanagement.kaltara.databaseHelper.DbRuas;
import com.example.roadmanagement.kaltara.databaseHelper.DbSaluran;
import com.example.roadmanagement.kaltara.databaseHelper.DbSegmen;
import com.example.roadmanagement.kaltara.databaseHelper.DbTable;
import com.example.roadmanagement.kaltara.databaseHelper.DbTemporari;
import com.example.roadmanagement.kaltara.helper.Session;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//update task data dari update on menu utama
public class UpdateTaskRuas {

        //extends AsyncTask<String, String, String> {
    ProgressDialog dialog;
    Context context;
    FungsiAPI fungsiAPI;
    DbTable dbTable;
    DbTemporari dbTemporari;
    DbLahan dbLahan;
    DbSaluran dbSaluran;
    DbBahu dbBahu;
    DbLane dbLane;
    DbMedian dbMedian;
    DbSegmen dbSegmen;
    DbRuas dbRuas;
    SendId sendId ;
    //String ruas;
    //String detail;

    String status;

    String ruasku;
    ArrayList<DataTemporari> dataTemporaris = new ArrayList<>();

    ArrayList<DataTemporari> berubah = new ArrayList<>();

    Session session;
    String tanggal;
    String id;

    int sinkronid;




    /*public UpdateTaskRuas (Context context, ArrayList<DataTemporari> dataTemporaris, String ruas, String id){
        this.context = context;
        this.sendId = sendId;
        //this.ruas =ruas;
        //this.detail =detail;
        this.ruasku = ruas;
        this.id = id;

        this.dataTemporaris = dataTemporaris;

        sinkronid = Integer.valueOf(id);
        dialog = new ProgressDialog(context);
        //dialog.setIndeterminate(false);
        dialog.setMessage("Harap Tunggu");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        dbTemporari = new DbTemporari(context);
        dbBahu = new DbBahu(context);
        dbLahan = new DbLahan(context);
        dbSaluran = new DbSaluran(context);
        dbLane = new DbLane(context);
        dbMedian = new DbMedian(context);
        dbRuas = new DbRuas(context);
        dbSegmen = new DbSegmen(context);
        fungsiAPI = new FungsiAPI(context);

        session = new Session(context);

        tanggal = getTanggal()+gettime();

        dbTable = new DbTable(context);


    }

    @Override
    protected void onPreExecute() {
        dialog.show();
        //dialog.setIndeterminate(false);
        dialog.setCancelable(false);
        dialog.setMax(dataTemporaris.size());
        dialog.setProgress(0);

    };

    @Override
    protected String doInBackground(String... params) {
        for(int i=0; i<dataTemporaris.size();i++) {


            try {
                if(dataTemporaris.get(i).getUrut().equals("Unidentified")){
                    detailUnd(dataTemporaris.get(i));
                }else {
                    detailnya(dataTemporaris.get(i));
                }
                dialog.setMessage(dataTemporaris.get(i).getTipe());
                dialog.setProgress(i);
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
        return "Selesai";
    }

    @Override
    protected void onPostExecute(String result) {

        // TODO Auto-generated method stub

        dbTable.setTable(ruasku, getTanggal());
        dbTemporari.updateRuastemporari(berubah);
        //dialog.dismiss();
        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //dialog.dismiss();
                Intent j = new Intent(context, context.getClass())
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                ((Activity)context).finish();
                context.startActivity(j);
            }
        }, 7000);

        super.onPostExecute("Selesai");

    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }


    public String getkodeprov(int a){
        String prov = null;

        if(a<10){
            prov="0"+String.valueOf(a);
        }else{
            prov = String.valueOf(a);
        }

        return prov;
    }

    public String gettime(){
        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        return timeStamp;
    }

    public String getTanggal(){
        String timeStamp = new SimpleDateFormat("d/M/yyyy").format(new Date());
        return timeStamp;
    }


    public void detailnya(final DataTemporari dataTemporari){


        String tipe = dataTemporari.getTipe();
        String posisiku = dataTemporari.getPosisi();
        String posisik = null;
        String lokasi = null;

        if(posisiku.equals("Left")){
            posisik = "kiri";
            lokasi ="L";
        }else{
            posisik = "kanan";
            lokasi = "R";
        }

        switch (tipe) {
            case "Lahan": {
                String posisi = "asset2020/"+dataTemporari.getNoprov()+"/"+dataTemporari.getNoruas()+"/"+"lahan"+"/"+lokasi;


                dataTemporari.setStatus("1");
               // dbTemporari.updateTemporari(dataTemporari);
                DataLahan dataLahan = dbLahan.getLahan(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment(), posisik);
                fungsiAPI.saveLahan(dataLahan, posisik);


                if (dataLahan.getGambarLahan()==null) {
                }else{
                    String[] pathimage = dataLahan.getGambarLahan().split(",");
                    String[] lokasiku = dataLahan.getLokasilahan().split(",");
                    for (int a = 0; a < pathimage.length; a++) {


                        try {
                            File file = new File(pathimage[a]);
                            fungsiAPI.uploadgambar(file, posisi);
                            String path = file.getAbsolutePath();
                            String filename=path.substring(path.lastIndexOf("/")+1);
                            fungsiAPI.saveImage(dataTemporari, posisik, posisi, filename, lokasiku[a],a+1);
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                    }
                }

                fungsiAPI.saveSinkrondetail(dataTemporari.getNoprov(), dataTemporari.getNoruas(), String.valueOf(dataTemporari.getNosegment()), tipe, posisik, tanggal, sinkronid);

            }
            break;

            case "Saluran": {
                String posisi = "asset2020/"+dataTemporari.getNoprov()+"/"+dataTemporari.getNoruas()+"/"+"saluran"+"/"+lokasi;


                dataTemporari.setStatus("1");
               // dbTemporari.updateTemporari(dataTemporari);
                DataSaluran dataSaluran = dbSaluran.getSaluran(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment(), posisik);
                fungsiAPI.saveSaluran(dataSaluran, posisik);


                if (dataSaluran.getGambarSaluran()==null) {
                }else{
                    String[] pathimage = dataSaluran.getGambarSaluran().split(",");
                    String[] lokasiku = dataSaluran.getLokasiSaluran().split(",");
                    for (int a = 0; a < pathimage.length; a++) {

                        try {
                            File file = new File(pathimage[a]);
                            String path = file.getAbsolutePath();
                            String filename=path.substring(path.lastIndexOf("/")+1);
                            fungsiAPI.uploadgambar(file, posisi);
                            fungsiAPI.saveImage(dataTemporari, posisik, posisi, filename, lokasiku[a],a+1);
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                    }
                }

                fungsiAPI.saveSinkrondetail(dataTemporari.getNoprov(), dataTemporari.getNoruas(), String.valueOf(dataTemporari.getNosegment()), tipe, posisik, tanggal, sinkronid);

            }
            break;

            case "Bahu": {
                String posisi = "asset2020/"+dataTemporari.getNoprov()+"/"+dataTemporari.getNoruas()+"/"+"bahu"+"/"+lokasi;

                dataTemporari.setStatus("1");
              //  dbTemporari.updateTemporari(dataTemporari);
                DataBahu dataBahu = dbBahu.getBahu(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment(), posisik);
                fungsiAPI.saveBahu(dataBahu, posisik);

                if (dataBahu.getGambarBahu()==null) {
                }else{
                    String[] pathimage = dataBahu.getGambarBahu().split(",");
                    String[] lokasiku = dataBahu.getLokasiBahu().split(",");
                    for (int a = 0; a < pathimage.length; a++) {

                        try {
                            File file = new File(pathimage[a]);
                            String path = file.getAbsolutePath();
                            String filename=path.substring(path.lastIndexOf("/")+1);
                            fungsiAPI.uploadgambar(file, posisi);
                            fungsiAPI.saveImage(dataTemporari, posisik, posisi, filename, lokasiku[a],a+1);
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }



                    }
                }

                fungsiAPI.saveSinkrondetail(dataTemporari.getNoprov(), dataTemporari.getNoruas(), String.valueOf(dataTemporari.getNosegment()), tipe, posisik, tanggal, sinkronid);

            }
            break;

            case "Lane":{


                dataTemporari.setStatus("1");
                //dbTemporari.updateTemporari(dataTemporari);
                DataLane dataLane = dbLane.getLane(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment(), dataTemporari.getUrut(), posisik);


                String urut = dataLane.getUrut().substring(1);

                fungsiAPI.saveSinkrondetail(dataTemporari.getNoprov(), dataTemporari.getNoruas(), String.valueOf(dataTemporari.getNosegment()), tipe, dataLane.getUrut(), tanggal, sinkronid);
                fungsiAPI.saveLane(dataLane,urut);

                String posisi = "asset2020/"+dataTemporari.getNoprov()+"/"+dataTemporari.getNoruas()+"/"+"Lane"+"/"+dataLane.getUrut();


                if (dataLane.getGambarLane()==null) {
                }else{
                    String[] pathimage = dataLane.getGambarLane().split(",");
                    String[] lokasiku = dataLane.getLokasiLane().split(",");
                    for (int a = 0; a < pathimage.length; a++) {

                        try {
                            File file = new File(pathimage[a]);
                            fungsiAPI.uploadgambar(file, posisi);
                            String path = file.getAbsolutePath();
                            String filename=path.substring(path.lastIndexOf("/")+1);
                            fungsiAPI.saveImage(dataTemporari, dataLane.getUrut(), posisi, filename, lokasiku[a],a+1);
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }



                    }
                }

                break;


            }

            case "Median":{
                String posisi = "asset2020/"+dataTemporari.getNoprov()+"/"+dataTemporari.getNoruas()+"/"+"median";

                dataTemporari.setStatus("1");
               // dbTemporari.updateTemporari(dataTemporari);
                DataMedian dataMedian = dbMedian.getMedian(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment());
                fungsiAPI.saveMedian(dataMedian);

                if (dataMedian.getGambarMedian()==null) {
                }else{
                    String[] pathimage = dataMedian.getGambarMedian().split(",");
                    String[] lokasiku = dataMedian.getLokasiMedian().split(",");
                    for (int a = 0; a < pathimage.length; a++) {

                        try {
                            File file = new File(pathimage[a]);
                            String path = file.getAbsolutePath();
                            String filename=path.substring(path.lastIndexOf("/")+1);
                            fungsiAPI.uploadgambar(file, posisi);
                            fungsiAPI.saveImage(dataTemporari, "", posisi, filename, lokasiku[a],a+1);
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }



                    }

                }

                fungsiAPI.saveSinkrondetail(dataTemporari.getNoprov(), dataTemporari.getNoruas(), String.valueOf(dataTemporari.getNosegment()), tipe, null, tanggal, sinkronid);
            }

            break;

            case "Segment" :{


                dataTemporari.setStatus("1");
                DataSegmen dataSegmen = dbSegmen.getSegmen(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment());

                String posisi = "";
                int urut = 0;

                if(dataTemporari.getUrut().length()>0) {
                    if (dataTemporari.getUrut().substring(0, 1).equals("L")) {
                        posisi = "kiri";
                    } else {
                        posisi = "kanan";
                    }
                    urut = Integer.valueOf(dataTemporari.getUrut().substring(1));
                }




                 if(dataTemporari.getPosisi().equals("Update")) {
                     fungsiAPI.saveSinkrondetail(dataTemporari.getNoprov(), dataTemporari.getNoruas(), String.valueOf(dataTemporari.getNosegment()), tipe, dataTemporari.getPosisi(), tanggal, sinkronid);
                 }else{
                     fungsiAPI.saveSinkrondetail(dataTemporari.getNoprov(), dataTemporari.getNoruas(), String.valueOf(dataTemporari.getNosegment()), tipe, dataTemporari.getPosisi()+","+dataTemporari.getUrut(), tanggal, sinkronid);
                     fungsiAPI.saveSinkrondetail(dataTemporari.getNoprov(), dataTemporari.getNoruas(), String.valueOf(dataTemporari.getNosegment()), "Lane", dataTemporari.getUrut(), tanggal, sinkronid);
                 }
                fungsiAPI.saveSegmentku(dataSegmen.getNoprov(), dataSegmen.getNoruas(), dataSegmen.getNosegment(), posisi, urut, dataTemporari.getPosisi(), dataSegmen.getVertikal(), dataSegmen.getHorizontal(), dataSegmen.getTipejalan(), dataSegmen.getLebarpvmt(), dataSegmen.getGrade());

            }
            break;

            default:

        }

        fungsiAPI.saveSinkronid(dataTemporari.getNoprov(), dataTemporari.getNoruas(), id, tanggal);
        dbRuas.setRuasku(String.valueOf(dataTemporari.getNoprov()), dataTemporari.getNoruas(), id);
        berubah.add(dataTemporari);

    }


    public void detailUnd(final DataTemporari dataTemporari){


        String tipe = dataTemporari.getTipe();
        String posisi = dataTemporari.getPosisi();
        String posisiData = null;
        String posisiImage = null;
        if(posisi.equals("Left")){
            posisiData = "kiri";
            posisiImage ="L";
        }else if(posisi.equals("Right")){
            posisiData = "kanan";
            posisiImage = "R";
        }else if(posisi.length()>0){
            posisiData = posisi;
            posisiImage = posisi;
        }

        switch (tipe) {
            case "Lahan": {
                String folder = "asset2020/"+dataTemporari.getNoprov()+"/"+dataTemporari.getNoruas()+"/"+"lahan"+"/"+posisiImage;


                dataTemporari.setStatus("1");
                DataLahan dataLahan = dbLahan.getLahanUn(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment());
                fungsiAPI.saveLahanUnd(dataLahan);


                if (dataLahan.getGambarLahan()==null) {
                }else{
                    String[] pathimage = dataLahan.getGambarLahan().split(",");
                    String[] lokasiku = dataLahan.getLokasilahan().split(",");
                    for (int a = 0; a < pathimage.length; a++) {


                        try {
                            File file = new File(pathimage[a]);
                            fungsiAPI.uploadgambar(file, folder);
                            String path = file.getAbsolutePath();
                            String filename=path.substring(path.lastIndexOf("/")+1);
                            fungsiAPI.saveImageUnd(dataTemporari, posisiData, folder, filename, lokasiku[a],a+1);
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                    }
                }
            }
            break;

            case "Saluran": {
                String folder = "asset2020/"+dataTemporari.getNoprov()+"/"+dataTemporari.getNoruas()+"/"+"saluran"+"/"+posisiImage;


                dataTemporari.setStatus("1");
                DataSaluran dataSaluran = dbSaluran.getSaluranUn(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment());
                fungsiAPI.saveSaluranUnd(dataSaluran);


                if (dataSaluran.getGambarSaluran()==null) {
                }else{
                    String[] pathimage = dataSaluran.getGambarSaluran().split(",");
                    String[] lokasiku = dataSaluran.getLokasiSaluran().split(",");
                    for (int a = 0; a < pathimage.length; a++) {



                        try {
                            File file = new File(pathimage[a]);
                            String path = file.getAbsolutePath();
                            String filename=path.substring(path.lastIndexOf("/")+1);
                            fungsiAPI.uploadgambar(file, folder);
                            fungsiAPI.saveImageUnd(dataTemporari, posisiData, folder, filename, lokasiku[a],a+1);
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }




                    }
                }

            }
            break;

            case "Bahu": {
                String folder = "asset2020/"+dataTemporari.getNoprov()+"/"+dataTemporari.getNoruas()+"/"+"bahu"+"/"+posisiImage;

                dataTemporari.setStatus("1");
                //  dbTemporari.updateTemporari(dataTemporari);
                DataBahu dataBahu = dbBahu.getBahuUn(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment());
                fungsiAPI.saveBahuUnd(dataBahu);

                if (dataBahu.getGambarBahu()==null) {
                }else{
                    String[] pathimage = dataBahu.getGambarBahu().split(",");
                    String[] lokasiku = dataBahu.getLokasiBahu().split(",");
                    for (int a = 0; a < pathimage.length; a++) {


                        try {
                            File file = new File(pathimage[a]);
                            String path = file.getAbsolutePath();
                            String filename=path.substring(path.lastIndexOf("/")+1);
                            fungsiAPI.uploadgambar(file, folder);
                            fungsiAPI.saveImageUnd(dataTemporari, posisiData, folder, filename, lokasiku[a],a+1);

                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }

            }
            break;

            case "Lane":{


                dataTemporari.setStatus("1");
                String folder = "asset2020/"+dataTemporari.getNoprov()+"/"+dataTemporari.getNoruas()+"/"+"Lane"+"/"+posisiImage;
                DataLane dataLane = dbLane.getLaneUn(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment(), dataTemporari.getPosisi());
                fungsiAPI.saveLaneUnd(dataLane);
                if (dataLane.getGambarLane()==null) {
                }else{
                    String[] pathimage = dataLane.getGambarLane().split(",");
                    String[] lokasiku = dataLane.getLokasiLane().split(",");
                    for (int a = 0; a < pathimage.length; a++) {

                        try {
                            File file = new File(pathimage[a]);
                            fungsiAPI.uploadgambar(file, folder);
                            String path = file.getAbsolutePath();
                            String filename=path.substring(path.lastIndexOf("/")+1);
                            fungsiAPI.saveImageUnd(dataTemporari, posisiData, folder, filename, lokasiku[a],a+1);

                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                    }
                }

                break;


            }

            case "Median":{
                String folder = "asset2020/"+dataTemporari.getNoprov()+"/"+dataTemporari.getNoruas()+"/"+"median";

                dataTemporari.setStatus("1");
                // dbTemporari.updateTemporari(dataTemporari);
                DataMedian dataMedian = dbMedian.getMedianUn(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment());
                fungsiAPI.saveMedianUnd(dataMedian);

                if (dataMedian.getGambarMedian()==null) {
                }else{
                    String[] pathimage = dataMedian.getGambarMedian().split(",");
                    String[] lokasiku = dataMedian.getLokasiMedian().split(",");
                    for (int a = 0; a < pathimage.length; a++) {

                        try {
                            File file = new File(pathimage[a]);
                            String path = file.getAbsolutePath();
                            String filename=path.substring(path.lastIndexOf("/")+1);
                            fungsiAPI.uploadgambar(file, folder);
                            fungsiAPI.saveImageUnd(dataTemporari, "", folder, filename, lokasiku[a],a+1);

                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }



                    }

                }

            }

            break;


            default:

        }

        berubah.add(dataTemporari);

    }*/


}
