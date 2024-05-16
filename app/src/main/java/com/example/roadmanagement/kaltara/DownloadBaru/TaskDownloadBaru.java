package com.example.roadmanagement.kaltara.DownloadBaru;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.widget.Toast;

import com.example.roadmanagement.kaltara.Interface.DataBahu;
import com.example.roadmanagement.kaltara.Interface.DataCrossDrain;
import com.example.roadmanagement.kaltara.Interface.DataDrainase;
import com.example.roadmanagement.kaltara.Interface.DataInletMedian;
import com.example.roadmanagement.kaltara.Interface.DataInletTrotoar;
import com.example.roadmanagement.kaltara.Interface.DataLahan;
import com.example.roadmanagement.kaltara.Interface.DataLane;
import com.example.roadmanagement.kaltara.Interface.DataMedian;
import com.example.roadmanagement.kaltara.Interface.DataOutlet;
import com.example.roadmanagement.kaltara.Interface.DataRuas;
import com.example.roadmanagement.kaltara.Interface.DataSaluran;
import com.example.roadmanagement.kaltara.Interface.DataSegmen;
import com.example.roadmanagement.kaltara.Interface.DataSpDalam;
import com.example.roadmanagement.kaltara.Interface.DataSpLuar;
import com.example.roadmanagement.kaltara.Interface.DataSpinner;
import com.example.roadmanagement.kaltara.Interface.SendId;
import com.example.roadmanagement.kaltara.Interface.Senddata;
import com.example.roadmanagement.kaltara.Interface.SingleSegment;
import com.example.roadmanagement.kaltara.Menuutama;
import com.example.roadmanagement.kaltara.api.FungsiAPI;
import com.example.roadmanagement.kaltara.databaseHelper.DbBahu;

import com.example.roadmanagement.kaltara.databaseHelper.DbGorong;
import com.example.roadmanagement.kaltara.databaseHelper.DbInlet;
import com.example.roadmanagement.kaltara.databaseHelper.DbLahan;
import com.example.roadmanagement.kaltara.databaseHelper.DbLane;
import com.example.roadmanagement.kaltara.databaseHelper.DbLereng;
import com.example.roadmanagement.kaltara.databaseHelper.DbMedian;
import com.example.roadmanagement.kaltara.databaseHelper.DbMinlet;
import com.example.roadmanagement.kaltara.databaseHelper.DbOutlet;
import com.example.roadmanagement.kaltara.databaseHelper.DbRuas;
import com.example.roadmanagement.kaltara.databaseHelper.DbSaluran;
import com.example.roadmanagement.kaltara.databaseHelper.DbSegmen;
import com.example.roadmanagement.kaltara.databaseHelper.DbSpd;
import com.example.roadmanagement.kaltara.databaseHelper.DbSpinner;
import com.example.roadmanagement.kaltara.databaseHelper.DbSpl;
import com.example.roadmanagement.kaltara.helper.Session;
import com.example.roadmanagement.kaltara.interfacedownload.DownloadDataIsi;

import java.util.ArrayList;

public class TaskDownloadBaru extends AsyncTask<String, String, String> {
    ProgressDialog dialog;
    Context context;

    DbLahan dbLahan;
    DbSaluran dbSaluran;
    DbBahu dbBahu;
    DbMedian dbMedian;
    DbLane dbLane;
    DbSegmen dbSegmen;
    DbSpinner dbSpinner;

    DbSpd dbSpd;
    DbSpl dbSpl;

    DbInlet dbInlet;
    DbMinlet dbMinlet;
    DbOutlet dbOutletl;
    DbGorong dbGorong;
    DbLereng dbLereng;

    SendId sendId;

    Session session;


    String ruas;
    int pruas;
    String sinkronid;
    DbRuas dbRuas;



    ArrayList<DetailDownload> listsegment;
    FungsiAPI fungsiAPI;

    protected PowerManager.WakeLock mWakeLock;
    final PowerManager pm;

    //download single ruas - onclick pada spinner

    public TaskDownloadBaru (final Context context, ArrayList<DetailDownload> listsegment, String ruas, int pruas, String sinkronid, SendId sendId){
        this.context = context;
        this.listsegment = listsegment;
        this.sendId = sendId;
        this.pruas = pruas;
        this.ruas = ruas;
        this.sinkronid = sinkronid;

        fungsiAPI = new FungsiAPI(context);

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
        dbRuas = new DbRuas(context);

        dbInlet = new DbInlet(context);
        dbMinlet = new DbMinlet(context);
        dbOutletl = new DbOutlet(context);
        dbGorong = new DbGorong(context);
        dbLereng = new DbLereng(context);

        dbSpd = new DbSpd(context);
        dbSpl = new DbSpl(context);


        session = new Session(context);

        pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        this.mWakeLock.acquire();

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

            int subsegment = detailDownload.getSubseg();
            //ganti nanti

            SingleSegment dataSpinner = new SingleSegment(provinsi, ruas, segment, subsegment, detailDownload.getKmaawal(), detailDownload.getKm_akhir(), detailDownload.getStaawal(), detailDownload.getSta_akhir());
            DataSegmen dataSegmen = new DataSegmen(provinsi, ruas, segment, subsegment, detailDownload.getL1(), detailDownload.getL2(), detailDownload.getL3(), detailDownload.getL4(), detailDownload.getL5(),detailDownload.getL6(),detailDownload.getL7(),detailDownload.getL8(), detailDownload.getL9(), detailDownload.getL10(),
                    detailDownload.getR1(), detailDownload.getR2(), detailDownload.getR3(), detailDownload.getR4(), detailDownload.getR5(),detailDownload.getR6(),detailDownload.getR7(),detailDownload.getR8(), detailDownload.getR9(),detailDownload.getR10(),
                    jumlah, detailDownload.getVertikal(), detailDownload.getHorizontal(), detailDownload.getTipejalan(), detailDownload.getLebarpvmt(),detailDownload.getGrade());
            DataLahan dataLahanKiri = new DataLahan(provinsi, ruas, segment, subsegment, "kiri", detailDownload.getLtipelahan(), detailDownload.getLtagunlahan(), 0, detailDownload.getLtinggilahan(), null, null, null);
            DataLahan dataLahanKanan = new DataLahan(provinsi, ruas, segment, subsegment, "kanan", detailDownload.getRtipelahan(), detailDownload.getRtagunlahan(), 0, detailDownload.getRtinggilahan(), null, null, null);

            DataBahu dataBahuKiri = new DataBahu(provinsi, ruas, segment, subsegment, "kiri", detailDownload.getLtipebahu(), detailDownload.getLlebarbahu(),detailDownload.getLmaterialbahu(), detailDownload.getLderajatbahu(),0, detailDownload.getLarahbahu(),detailDownload.getLkondisibahu(), detailDownload.getLtipebahuinner(), detailDownload.getLlebarbahuinner(), detailDownload.getLmaterialbahuinner(), null, null, null );
            DataBahu dataBahuKanan = new DataBahu(provinsi, ruas, segment, subsegment, "kanan", detailDownload.getRtipebahu(), detailDownload.getRlebarbahu(),detailDownload.getRmaterialbahu(), detailDownload.getRderajatbahu(),0, detailDownload.getRarahbahu(),detailDownload.getRkondisibahu(), detailDownload.getRtipebahuinner(), detailDownload.getRlebarbahuinner(), detailDownload.getRmaterialbahuinner(), null, null, null );

            DataSaluran dataSaluranKiri = new DataSaluran(provinsi, ruas, segment, subsegment, "kiri", detailDownload.getLtipesaluran(), detailDownload.getLsampingsaluran(), detailDownload.getLpenampangsaluran(), detailDownload.getLlebarsaluran(), detailDownload.getLdalamsaluran(), detailDownload.getLtinggisaluran(), detailDownload.getLsedimensaluran(), detailDownload.getLkonstruksisaluran(), detailDownload.getLkondisisaluran(), null, null, null);
            DataSaluran dataSaluranKanan = new DataSaluran(provinsi, ruas, segment, subsegment, "kanan", detailDownload.getRtipesaluran(), detailDownload.getRsampingsaluran(), detailDownload.getRpenampangsaluran(), detailDownload.getRlebarsaluran(), detailDownload.getRdalamsaluran(), detailDownload.getRtinggisaluran(), detailDownload.getRsedimensaluran(), detailDownload.getRkonstruksisaluran(), detailDownload.getRkondisisaluran(), null, null, null);

            DataMedian dataMedian = new DataMedian(provinsi, ruas, segment, subsegment, detailDownload.getTipemedian(), detailDownload.getLebarmedian(), null, null, null);

            DataInletTrotoar dataInletKiri = new DataInletTrotoar(provinsi, ruas, segment, subsegment, "kiri", null, null, 0, 0, 0, 0, null, null, null, null, null);
            DataInletTrotoar dataInletKanan = new DataInletTrotoar(provinsi, ruas, segment, subsegment, "kanan", null, null, 0, 0, 0, 0, null, null, null, null, null);

            DataInletMedian dataMinletKiri = new DataInletMedian(provinsi, ruas, segment, subsegment, "kiri", null, null, 0, 0, 0, 0, null, null, null, null, null);
            DataInletMedian dataMinletKanan = new DataInletMedian(provinsi, ruas, segment, subsegment, "kanan", null, null, 0, 0, 0, 0, null, null, null, null, null);

            DataOutlet dataOutletKiri = new DataOutlet(provinsi, ruas, segment, subsegment, "kiri", null, null, 0,0,0, null, null, null, null, null);
            DataOutlet dataOutletKanan = new DataOutlet(provinsi, ruas, segment, subsegment, "kanan", null, null, 0,0,0, null, null, null, null, null);

            DataDrainase dataLerengKiri = new DataDrainase(provinsi, ruas, segment, subsegment, "kiri", null, null, 0,0,0,null, null, null, null);
            DataDrainase dataLerengKanan = new DataDrainase(provinsi, ruas, segment, subsegment, "kanan", null, null, 0,0,0,null, null, null, null);

            DataCrossDrain dataGorongKiri = new DataCrossDrain(provinsi, ruas, segment, subsegment, "kiri",  null, null, 0, 0, 0, null, null, null, null, null);
            DataCrossDrain dataGorongKanan = new DataCrossDrain(provinsi, ruas, segment, subsegment, "kanan",  null, null, 0, 0, 0, null, null, null, null, null);

            dbInlet.setInlet(dataInletKiri);
            dbInlet.setInlet(dataInletKanan);


            dataMinletKiri.setLebar(detailDownload.getL_keras());
            dataMinletKanan.setLebar(detailDownload.getR_keras());

            dbMinlet.setMinlet(dataMinletKiri);
            dbMinlet.setMinlet(dataMinletKanan);

            dbOutletl.setSaluran(dataOutletKiri);
            dbOutletl.setSaluran(dataOutletKanan);

            dbGorong.setSaluran(dataGorongKiri);
            dbGorong.setSaluran(dataGorongKanan);

            dbLereng.setLereng(dataLerengKiri);
            dbLereng.setLereng(dataLerengKanan);


            dbSpinner.setSpinner(dataSpinner);
            dbSegmen.postSegment(dataSegmen);
            dbLahan.setLahan(dataLahanKiri);
            dbLahan.setLahan(dataLahanKanan);
            dbBahu.setBahu(dataBahuKiri);
            dbBahu.setBahu(dataBahuKanan);
            dbSaluran.setSaluran(dataSaluranKiri);
            dbSaluran.setSaluran(dataSaluranKanan);
            dbMedian.setMedian(dataMedian);

            DataSpDalam dataSpdKiri = new DataSpDalam(provinsi, ruas, segment, subsegment, "kiri", detailDownload.getL_spd(), null, null, null);
            DataSpDalam dataSpdKanan = new DataSpDalam(provinsi, ruas, segment, subsegment, "kanan", detailDownload.getR_spd(), null, null, null);

            DataSpLuar dataSplKiri = new DataSpLuar(provinsi, ruas, segment, subsegment, "kiri", detailDownload.getL_spl(), null, null, null);
            DataSpLuar dataSplKanan = new DataSpLuar(provinsi, ruas, segment, subsegment, "kanan", detailDownload.getR_spl(), null, null, null);

            dbSpd.postData(dataSpdKiri);
            dbSpd.postData(dataSpdKanan);
            dbSpl.postData(dataSplKiri);
            dbSpl.postData(dataSplKanan);

            if(detailDownload.getL1()==1){
                DataLane dataLane = new DataLane(provinsi, ruas, segment, subsegment, "kiri", "L1", detailDownload.getL1lebar(), detailDownload.getL1pvmt(), detailDownload.getL1derajat(), 0, detailDownload.getL1arah(), detailDownload.getL1kondisi(), null, null, null);
                dbLane.setLane(dataLane);
            }
            if(detailDownload.getL2()==1){
                DataLane dataLane = new DataLane(provinsi, ruas, segment, subsegment, "kiri", "L2", detailDownload.getL2lebar(), detailDownload.getL2pvmt(), detailDownload.getL2derajat(), 0, detailDownload.getL2arah(), detailDownload.getL2kondisi(), null, null, null);
                dbLane.setLane(dataLane);
            }
            if(detailDownload.getL3()==1){
                DataLane dataLane = new DataLane(provinsi, ruas, segment, subsegment, "kiri", "L3", detailDownload.getL3lebar(), detailDownload.getL3pvmt(), detailDownload.getL3derajat(), 0, detailDownload.getL3arah(), detailDownload.getL3kondisi(), null, null, null);
                dbLane.setLane(dataLane);
            }
            if(detailDownload.getL4()==1){
                DataLane dataLane = new DataLane(provinsi, ruas, segment, subsegment, "kiri", "L4", detailDownload.getL4lebar(), detailDownload.getL4pvmt(), detailDownload.getL4derajat(), 0, detailDownload.getL4arah(), detailDownload.getL4kondisi(), null, null, null);
                dbLane.setLane(dataLane);
            }
            if(detailDownload.getL5()==1){
                DataLane dataLane = new DataLane(provinsi, ruas, segment, subsegment, "kiri", "L5", detailDownload.getL5lebar(), detailDownload.getL5pvmt(), detailDownload.getL5derajat(), 0, detailDownload.getL5arah(), detailDownload.getL5kondisi(), null, null, null);
                dbLane.setLane(dataLane);
            }
            if(detailDownload.getL6()==1){
                DataLane dataLane = new DataLane(provinsi, ruas, segment, subsegment, "kiri", "L6", detailDownload.getL6lebar(), detailDownload.getL6pvmt(), detailDownload.getL6derajat(), 0, detailDownload.getL6arah(), detailDownload.getL6kondisi(), null, null, null);
                dbLane.setLane(dataLane);
            }
            if(detailDownload.getL7()==1){
                DataLane dataLane = new DataLane(provinsi, ruas, segment, subsegment, "kiri", "L7", detailDownload.getL7lebar(), detailDownload.getL7pvmt(), detailDownload.getL7derajat(), 0, detailDownload.getL7arah(), detailDownload.getL7kondisi(), null, null, null);
                dbLane.setLane(dataLane);
            }
            if(detailDownload.getL8()==1){
                DataLane dataLane = new DataLane(provinsi, ruas, segment, subsegment, "kiri", "L8", detailDownload.getL8lebar(), detailDownload.getL8pvmt(), detailDownload.getL8derajat(), 0, detailDownload.getL8arah(), detailDownload.getL8kondisi(), null, null, null);
                dbLane.setLane(dataLane);
            }
            if(detailDownload.getL9()==1){
                DataLane dataLane = new DataLane(provinsi, ruas, segment, subsegment, "kiri", "L9", detailDownload.getL9lebar(), detailDownload.getL9pvmt(), detailDownload.getL9derajat(), 0, detailDownload.getL9arah(), detailDownload.getL9kondisi(), null, null, null);
                dbLane.setLane(dataLane);
            }
            if(detailDownload.getL10()==1){
                DataLane dataLane = new DataLane(provinsi, ruas, segment, subsegment, "kiri", "L10", detailDownload.getL10lebar(), detailDownload.getL10pvmt(), detailDownload.getL10derajat(), 0, detailDownload.getL10arah(), detailDownload.getL10kondisi(), null, null, null);
                dbLane.setLane(dataLane);
            }

            if(detailDownload.getR1()==1){
                DataLane dataLane = new DataLane(provinsi, ruas, segment, subsegment, "kanan", "R1", detailDownload.getR1lebar(), detailDownload.getR1pvmt(), detailDownload.getR1derajat(), 0, detailDownload.getR1arah(), detailDownload.getR1kondisi(), null, null, null);
                dbLane.setLane(dataLane);
            }
            if(detailDownload.getR2()==1){
                DataLane dataLane = new DataLane(provinsi, ruas, segment, subsegment, "kanan", "R2", detailDownload.getR2lebar(), detailDownload.getR2pvmt(), detailDownload.getR2derajat(), 0, detailDownload.getR2arah(), detailDownload.getR2kondisi(), null, null, null);
                dbLane.setLane(dataLane);
            }
            if(detailDownload.getR3()==1){
                DataLane dataLane = new DataLane(provinsi, ruas, segment, subsegment, "kanan", "R3", detailDownload.getR3lebar(), detailDownload.getR3pvmt(), detailDownload.getR3derajat(), 0, detailDownload.getR3arah(), detailDownload.getR3kondisi(), null, null, null);
                dbLane.setLane(dataLane);
            }
            if(detailDownload.getR4()==1){
                DataLane dataLane = new DataLane(provinsi, ruas, segment, subsegment, "kanan", "R4", detailDownload.getR4lebar(), detailDownload.getR4pvmt(), detailDownload.getR4derajat(), 0, detailDownload.getR4arah(), detailDownload.getR4kondisi(), null, null, null);
                dbLane.setLane(dataLane);
            }
            if(detailDownload.getR5()==1){
                DataLane dataLane = new DataLane(provinsi, ruas, segment, subsegment, "kanan", "R5", detailDownload.getR5lebar(), detailDownload.getR5pvmt(), detailDownload.getR5derajat(), 0, detailDownload.getR5arah(), detailDownload.getR5kondisi(), null, null, null);
                dbLane.setLane(dataLane);
            }
            if(detailDownload.getR6()==1){
                DataLane dataLane = new DataLane(provinsi, ruas, segment, subsegment, "kanan", "R6", detailDownload.getR6lebar(), detailDownload.getR6pvmt(), detailDownload.getR6derajat(), 0, detailDownload.getR6arah(), detailDownload.getR6kondisi(), null, null, null);
                dbLane.setLane(dataLane);
            }
            if(detailDownload.getR7()==1){
                DataLane dataLane = new DataLane(provinsi, ruas, segment, subsegment, "kanan", "R7", detailDownload.getR7lebar(), detailDownload.getR7pvmt(), detailDownload.getR7derajat(), 0, detailDownload.getR7arah(), detailDownload.getR7kondisi(), null, null, null);
                dbLane.setLane(dataLane);
            }
            if(detailDownload.getR8()==1){
                DataLane dataLane = new DataLane(provinsi, ruas, segment, subsegment, "kanan", "R8", detailDownload.getR8lebar(), detailDownload.getR8pvmt(), detailDownload.getR8derajat(), 0, detailDownload.getR8arah(), detailDownload.getR8kondisi(), null, null, null);
                dbLane.setLane(dataLane);
            }
            if(detailDownload.getR9()==1){
                DataLane dataLane = new DataLane(provinsi, ruas, segment, subsegment, "kanan", "R9", detailDownload.getR9lebar(), detailDownload.getR9pvmt(), detailDownload.getR9derajat(), 0, detailDownload.getR9arah(), detailDownload.getR9kondisi(), null, null, null);
                dbLane.setLane(dataLane);
            }
            if(detailDownload.getR10()==1){
                DataLane dataLane = new DataLane(provinsi, ruas, segment, subsegment, "kanan", "R10", detailDownload.getR10lebar(), detailDownload.getR10pvmt(), detailDownload.getR10derajat(), 0, detailDownload.getR10arah(), detailDownload.getR10kondisi(), null, null, null);
                dbLane.setLane(dataLane);
            }

        }
        return "Selesai";
    }

    @Override
    protected void onPostExecute(String result) {


        fungsiAPI.DownloadDrainase(session.getKodeprov(), ruas, pruas, dialog, new SendId() {
            @Override
            public void hapusGambar(int id) {
                dbRuas.updateSinkronDetail(session.getKodeprov(), ruas, "1");
                sendId.hapusGambar(pruas);
            }
        });

        //dbRuas.setRuasku(session.getKodeprov(), ruas, sinkronid);

        super.onPostExecute("Selesai");

    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }


}

