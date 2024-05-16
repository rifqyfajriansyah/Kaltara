package com.example.roadmanagement.kaltara.FormTerusan;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.util.Log;

import com.example.roadmanagement.kaltara.Interface.CekSinkron;
import com.example.roadmanagement.kaltara.Interface.DataLane;
import com.example.roadmanagement.kaltara.Interface.SendDialog;
import com.example.roadmanagement.kaltara.Interface.SendId;
import com.example.roadmanagement.kaltara.Menuutama;
import com.example.roadmanagement.kaltara.Task.TaskUpdateAll;
import com.example.roadmanagement.kaltara.api.FungsiAPI;
import com.example.roadmanagement.kaltara.databaseHelper.DbLane;
import com.example.roadmanagement.kaltara.databaseHelper.DbTemporari;
import com.example.roadmanagement.kaltara.helper.Session;

import java.util.ArrayList;
import java.util.List;

public class TaskSaveLaneTerusan extends AsyncTask<String, String, String> {


    ProgressDialog dialog;
    Context context;
    SendId sendId;
    Session session;

    DataLane dataLane;
    DbLane dbLane;
    DbTemporari dbTemporari;
    int jumlahPerubahan, segmentIndex, subIndex;
    int segmentAkhir, subSegmentAkhir, indexAwalSegment, indexAwalSub, indexAkhirSegment, indexAkhirSub ;
    String tipeSurvey, foto;

    SendDialog sendDialog;

    protected PowerManager.WakeLock mWakeLock;
    final PowerManager pm;

    public TaskSaveLaneTerusan (Context context, DataLane dataLane, String tipeSurvey, int segmentAkhir, int subSegmentAkhir, String foto, SendDialog sendDialog){

        this.dataLane =dataLane;
        this.context = context;
        this.segmentAkhir = segmentAkhir;
        this.subSegmentAkhir = subSegmentAkhir;
        this.foto = foto;
        this.tipeSurvey = tipeSurvey;
        this.sendDialog = sendDialog;

        dialog = new ProgressDialog(context);
        dialog.setIndeterminate(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);


        session = new Session(context);
        dbLane = new DbLane(context);
        dbTemporari = new DbTemporari(context);

        jumlahPerubahan = getJumlahPerubahan(tipeSurvey, dataLane.getNosegment(), dataLane.getSubsegment(), segmentAkhir, subSegmentAkhir);

        indexAwalSegment  = dataLane.getNosegment();
        indexAwalSub = dataLane.getSubsegment();

        indexAkhirSegment = 0;
        indexAkhirSub = 0;

        pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");

    }

    @Override
    protected void onPreExecute() {
        dialog.show();
        dialog.setMax(jumlahPerubahan);
        dialog.setMessage("Harap tunggu, sedang update data");
        dialog.setIndeterminate(false);
        dialog.setCancelable(false);


    };

    @Override
    protected String doInBackground(String... params) {

        int progress = 0;

        segmentIndex = dataLane.getNosegment();
        subIndex = dataLane.getSubsegment();


            for(int i = 0; i<=jumlahPerubahan; i++ ){

                segmentIndex = tambahSegment(tipeSurvey, segmentIndex, subIndex);
                subIndex = tambahSubSegment(tipeSurvey, subIndex);

                Log.d("TESLANE", segmentIndex+"."+subIndex+"---"+indexAwalSegment+"."+indexAwalSub+"-"+indexAkhirSegment+"."+indexAkhirSub);

                if(i<jumlahPerubahan) {
                    dbLane.setLaneTerusan(dataLane, tipeSurvey, segmentIndex, subIndex, indexAwalSegment, indexAwalSub, indexAkhirSegment, indexAkhirSub, new InterfaceLaneTask() {
                        @Override
                        public void sendData(int segmentAwal, int subAwal, int segmentAkhir, int subAkhir) {

                            indexAwalSegment = segmentAwal;
                            indexAwalSub = subAwal;

                            indexAkhirSegment = segmentAkhir;
                            indexAkhirSub = subAkhir;

                            if (indexAwalSegment != 0 && indexAkhirSegment != 0) {
                                dbTemporari.saveTemporariTerusan("Lane", dataLane.getNoprov(), dataLane.getNoruas(), dataLane.getPosisi(), dataLane.getUrut(), indexAwalSegment, indexAwalSub, indexAkhirSegment, indexAkhirSub, tipeSurvey, foto);
                                indexAwalSegment = 0;
                                indexAkhirSegment = 0;
                                indexAwalSub = 0;
                                indexAkhirSub = 0;
                            }
                        }
                    });
                }

                if(i == jumlahPerubahan && indexAwalSegment !=0){

                    dbTemporari.saveTemporariTerusan("Lane", dataLane.getNoprov(), dataLane.getNoruas(), dataLane.getPosisi(), dataLane.getUrut(), indexAwalSegment, indexAwalSub, segmentAkhir,  subSegmentAkhir, tipeSurvey, foto);

                }

                dialog.setProgress(progress);
                progress = progress+1;

            }


        return "completed";
    }

    @Override
    protected void onPostExecute(String result) {

        // TODO Auto-generated method stub
        //dialog.dismiss();
        //dialog.dismiss();
        sendDialog.sendDialog(dialog);
        super.onPostExecute(result);

    }

    @Override
    protected void onProgressUpdate(String... values) {
        // getData(i);
        super.onProgressUpdate(values);
    }

    private int getJumlahPerubahan(String jenis, int segmentAwal, int subSegmentAwal, int segmentAkhir, int subSegmentAkhir){

        int indexAwal, indexAkhir, subAwal, subAkhir, jumlahPerubahan;

        if(jenis.equals("Normal")){

            indexAwal = segmentAwal;
            subAwal = subSegmentAwal;
            indexAkhir = segmentAkhir;
            subAkhir = subSegmentAkhir;

        }else{

            indexAwal = segmentAkhir;
            subAwal = subSegmentAkhir;
            indexAkhir = segmentAwal;
            subAkhir = subSegmentAwal;

        }

        if(indexAwal == indexAkhir){
            jumlahPerubahan = subAkhir - subAwal;
        }else if(subAwal>subAkhir){
            jumlahPerubahan = (10-subAwal+subAkhir) + ((indexAkhir-indexAwal-1)*10);
        }else{
            jumlahPerubahan = ((indexAkhir-indexAwal)*10) + (subAkhir-subAwal);
        }

        return jumlahPerubahan;


    }

    private int tambahSegment(String jenis, int segment, int subSegment){

        if(jenis.equals("Normal")){

            if(subSegment==9){
                return segment+1;
            }else{
                return segment;
            }

        }else{
            if(subSegment==0){
                return segment-1;
            }else{
                return segment;
            }
        }

    }

    private int tambahSubSegment(String jenis, int subsegment){

        if(jenis.equals("Normal")){

            if(subsegment==9){
                return 0;
            }else{
                return subsegment+1;
            }

        }else{

            if(subsegment==0){
                return 9;
            }else{
                return subsegment-1;
            }

        }

    }

}
