package com.example.roadmanagement.kaltara.downloading;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.roadmanagement.kaltara.Interface.DataDownloadRuas;
import com.example.roadmanagement.kaltara.Interface.DataRuas;
import com.example.roadmanagement.kaltara.Interface.SendId;
import com.example.roadmanagement.kaltara.Menuutama;
import com.example.roadmanagement.kaltara.api.FungsiAPI;
import com.example.roadmanagement.kaltara.databaseHelper.DbDownloadRuas;
import com.example.roadmanagement.kaltara.databaseHelper.DbRuas;
import com.example.roadmanagement.kaltara.helper.Session;

import java.util.ArrayList;

//download all ruas - diawal login

public class DownloadRuas extends AsyncTask<String, String, String> {
    ProgressDialog dialog;
    Context context;
    FungsiAPI fungsiAPI;
    DbRuas dbRuas;
    //DbDownloadRuas dbDownloadRuas;
    Session session;
    ArrayList<DataRuas> listruas;

    public DownloadRuas (Context context, ArrayList<DataRuas> listruas){
        this.context = context;
        this.listruas = listruas;
        dialog = new ProgressDialog(context);
        dialog.setIndeterminate(false);
        dialog.setMessage("Download Ruas");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dbRuas = new DbRuas(context);
       // dbDownloadRuas = new DbDownloadRuas(context);
        fungsiAPI = new FungsiAPI(context);

        session = new Session(context);

    }

    @Override
    protected void onPreExecute() {
        dialog.show();
        dialog.setProgress(0);
        dialog.setIndeterminate(false);
        dialog.setCancelable(false);
        dialog.setMax(listruas.size());
    }

    @Override
    protected String doInBackground(String... params) {
        for (int i = 0; i < listruas.size(); i++) {
            DataRuas dataRuas = new DataRuas(String.valueOf(listruas.get(i).getNoprov()), listruas.get(i).getNoruas(), "0", "0", listruas.get(i).getNamaruas(), 0);
            dbRuas.setRuasku(dataRuas);
            dialog.setProgress(i);
        }
        return "completed";
    }

    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub

        fungsiAPI.saveProvinsi(session.getKodeprov(), new SendId() {
            @Override
            public void hapusGambar(int id) {
                dialog.dismiss();

                session.saveSPInt(Session.DOWNLOADATARUAS, 1);

                ((Activity)context).finish();
                Intent i = new Intent(context, Menuutama.class);
                context.startActivity(i);
            }
        });



        super.onPostExecute(result);

    }

    @Override
    protected void onProgressUpdate(String... values) {

        super.onProgressUpdate(values);
    }

}

