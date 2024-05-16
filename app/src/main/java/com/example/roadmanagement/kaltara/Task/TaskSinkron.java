package com.example.roadmanagement.kaltara.Task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.roadmanagement.kaltara.Interface.CekSinkron;
import com.example.roadmanagement.kaltara.Interface.DataTemporari;
import com.example.roadmanagement.kaltara.Interface.SendId;
import com.example.roadmanagement.kaltara.Menuutama;
import com.example.roadmanagement.kaltara.api.FungsiAPI;
import com.example.roadmanagement.kaltara.databaseHelper.DbRuas;
import com.example.roadmanagement.kaltara.databaseHelper.DbTemporari;
import com.example.roadmanagement.kaltara.helper.Session;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TaskSinkron extends AsyncTask<String, String, String> {

    Context context;
    Session session;
    ProgressDialog dialog;
    ArrayList<DataTemporari> list;
    SinkronFunction sinkronFunction;
    FungsiAPI fungsiAPI;

    DbTemporari dbTemporari;
    DbRuas dbRuas;
    int index;

    int idSinkron;

    String waktu;

    public TaskSinkron (Context context, ArrayList<DataTemporari> list, int idSinkron){

        this.context = context;
        this.list = list;
        this.idSinkron = idSinkron;

        waktu = getWaktu();

        session = new Session(context);
        sinkronFunction = new SinkronFunction(context, waktu);
        dbTemporari  = new DbTemporari(context);
        fungsiAPI = new FungsiAPI(context);
        dbRuas = new DbRuas(context);



        dialog = new ProgressDialog(context);
        dialog.setIndeterminate(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setMessage("Sedang proses sinron data, Silahkan tunggu beberapa saat...");
        dialog.show();

    }

    @Override
    protected void onPreExecute() {

        dialog.setMax(list.size());


    };

    @Override
    protected String doInBackground(String... params) {

        index = 0;

        sinkronData(index, list.get(index));


        return "Selesai";
    }

    @Override
    protected void onPostExecute(String result) {

        // TODO Auto-generated method stub
        //dialog.dismiss();
        super.onPostExecute("Selesai");

    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    private void sinkronData(final int index, final DataTemporari dataTemporari){


        dialog.setProgress(index+1);

        sinkronFunction.sinkronUtama(dataTemporari, idSinkron, new SendId() {
            @Override
            public void hapusGambar(int id) {

                dataTemporari.setStatus("1");
                dbTemporari.postTemporari(dataTemporari);

                if(index+1 < list.size()){

                    //dbRuas.updateSinkronId(session.getKodeprov(), session.getNoruas(), String.valueOf(idSinkron));
                    sinkronData(index+1, list.get(index+1));

                }else{

                    if(dbTemporari.cekSurveyTemporari(session.getKodeprov(), session.getNoruas()).equals("Normal")||dbTemporari.cekSurveyTemporari(session.getKodeprov(), session.getNoruas()).equals("Opposite")){
                        fungsiAPI.saveSinkronid(session.getKodeprov(), session.getNoruas(), String.valueOf(idSinkron), waktu);
                    }

                    dbRuas.updateSinkronId(session.getKodeprov(), session.getNoruas(), String.valueOf(idSinkron));

                    Intent i = new Intent(context, Menuutama.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(i);
                    ((Activity)context).finish();

                }


            }
        });

    }

    private String getWaktu(){
        return new SimpleDateFormat("MM/DD/YYYY - HH:mm:ss").format(new Date());
    }


}