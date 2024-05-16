package com.example.roadmanagement.kaltara.DownloadBaru;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.PowerManager;

import com.example.roadmanagement.kaltara.Interface.SendId;
import com.example.roadmanagement.kaltara.api.FungsiAPI;
import com.example.roadmanagement.kaltara.databaseHelper.DbRuas;
import com.example.roadmanagement.kaltara.databaseHelper.DbSpinner;
import com.example.roadmanagement.kaltara.helper.Session;

import java.util.List;

//download all baru -- onclick menu utama

public class DownloadAllBaru extends AsyncTask<String, String, String> {
    ProgressDialog dialog;
    Context context;
    FungsiAPI fungsiAPI;
    Session session;

    DbRuas dbRuas;
    DbSpinner dbSpinner;

    //DbDownloadRuas dbDownloadRuas;

    String provinsi;
    protected PowerManager.WakeLock mWakeLock;
    List<String> listruas;
    final PowerManager pm;
    int pruas;

    ProgressDialog progressDialog;

    public DownloadAllBaru (Context context,  String provinsi){
        this.context = context;
        this.provinsi = provinsi;

        dialog = new ProgressDialog(context);
        dialog.setIndeterminate(false);
        //dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);


        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
       // progressDialog.setMessage("Loading ... ");

        dbRuas = new DbRuas(context);
        fungsiAPI = new FungsiAPI(context);
        session = new Session(context);

        //dbDownloadRuas = new DbDownloadRuas(context);

        dbRuas = new DbRuas(context);
        dbSpinner = new DbSpinner(context);


        pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);

        listruas = dbRuas.getRuasUndownload(String.valueOf(session.getKodeprov()));
        session = new Session(context);

        pruas = session.PosisiRuas();

    }

    @Override
    protected void onPreExecute() {
        dialog.show();
        dialog.setMessage("Harap tunggu, sedang mengunduh data");
        dialog.setIndeterminate(false);
        dialog.setCancelable(false);

        int progressbarstatus = 0;

    };

    @Override
    protected String doInBackground(String... params) {

        //while (i<=listsegment.size()-1){
        this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        this.mWakeLock.acquire();
        getData(0);
        //   i=i+1;
        // }

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

    private void getData(final int ruas) {

        if(ruas<listruas.size()) {

            String ruasnya = dbRuas.getSinkronDetail(String.valueOf(provinsi), listruas.get(ruas));
            dialog.setMessage("Downloading Ruas " + listruas.get(ruas));
            if (!ruasnya.equals("0")) {
                getData(ruas + 1);
            } else {
                fungsiAPI.DownloadNew(String.valueOf(session.getKodeprov()), listruas.get(ruas), ruas, progressDialog, new SendId() {
                    @Override
                    public void hapusGambar(int id) {

                        getData(id + 1);


                    }
                });
            }

        }else{
            session.saveSPInt(Session.DOWNLOADALL, 1);
        }
    }
}
