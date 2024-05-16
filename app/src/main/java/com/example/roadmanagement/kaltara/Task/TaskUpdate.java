package com.example.roadmanagement.kaltara.Task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.PowerManager;

import com.example.roadmanagement.kaltara.Interface.CekSinkron;
import com.example.roadmanagement.kaltara.Interface.DataDownload;
import com.example.roadmanagement.kaltara.Interface.DataRuas;
import com.example.roadmanagement.kaltara.Interface.DownloadData;
import com.example.roadmanagement.kaltara.Interface.SendId;
import com.example.roadmanagement.kaltara.Menuutama;
import com.example.roadmanagement.kaltara.api.FungsiAPI;
import com.example.roadmanagement.kaltara.databaseHelper.DbBahu;
import com.example.roadmanagement.kaltara.databaseHelper.DbLahan;
import com.example.roadmanagement.kaltara.databaseHelper.DbMedian;
import com.example.roadmanagement.kaltara.databaseHelper.DbRuas;
import com.example.roadmanagement.kaltara.databaseHelper.DbSaluran;
import com.example.roadmanagement.kaltara.helper.Session;

import java.util.ArrayList;


//Task Update onclick spinner menu utama

public class TaskUpdate extends AsyncTask<String, String, String> {
    ProgressDialog dialog;
    Context context;
    FungsiAPI fungsiAPI;
    SendId sendId;
    Session session;
    String sinkronId;

    DbRuas dbRuas;

    UpdateFunction updateFunction;

    protected PowerManager.WakeLock mWakeLock;
    ArrayList<CekSinkron> list;
    final PowerManager pm;
    int i = 0;

    public TaskUpdate (Context context, ArrayList<CekSinkron> list, String sinkronId){

        this.context = context;
        this.list = list;
        this.sinkronId = sinkronId;

        dialog = new ProgressDialog(context);
        dialog.setIndeterminate(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        dbRuas = new DbRuas(context);


        fungsiAPI = new FungsiAPI(context);
        updateFunction = new UpdateFunction(context);

        session = new Session(context);
        pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);

    }

    @Override
    protected void onPreExecute() {
        dialog.show();
        dialog.setMax(list.size());
        dialog.setMessage("Harap tunggu, sedang update data");
        dialog.setIndeterminate(false);
        dialog.setCancelable(false);

        int progressbarstatus = 0;

    };

    @Override
    protected String doInBackground(String... params) {


        //this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        //this.mWakeLock.acquire();
        setUpdate(0);

        return "completed";
    }

    @Override
    protected void onPostExecute(String result) {

        // TODO Auto-generated method stub
        //dialog.dismiss();
        super.onPostExecute(result);

    }

    @Override
    protected void onProgressUpdate(String... values) {
        // getData(i);
        this.mWakeLock.release();
        super.onProgressUpdate(values);
    }

    private void setUpdate(final int a){

        updateFunction.mainUpdate(list.get(a), new SendId() {
            @Override
            public void hapusGambar(int id) {

                dialog.setProgress(a);

                if(a+1<list.size()){

                    setUpdate(a+1);

                }else{

                    dbRuas.updateSinkronId(session.getKodeprov(), session.getNoruas(), sinkronId);

                    ((Activity)context).finish();
                    Intent i = new Intent(context, Menuutama.class);
                    context.startActivity(i);

                }
            }
        });



    }

}

