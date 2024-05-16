package com.example.roadmanagement.kaltara.Task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.PowerManager;

import com.example.roadmanagement.kaltara.DownloadBaru.DetailDownload;
import com.example.roadmanagement.kaltara.Interface.DataBahu;
import com.example.roadmanagement.kaltara.Interface.DataCrossDrain;
import com.example.roadmanagement.kaltara.Interface.DataDrainase;
import com.example.roadmanagement.kaltara.Interface.DataInletMedian;
import com.example.roadmanagement.kaltara.Interface.DataInletTrotoar;
import com.example.roadmanagement.kaltara.Interface.DataLahan;
import com.example.roadmanagement.kaltara.Interface.DataLane;
import com.example.roadmanagement.kaltara.Interface.DataMedian;
import com.example.roadmanagement.kaltara.Interface.DataOutlet;
import com.example.roadmanagement.kaltara.Interface.DataSaluran;
import com.example.roadmanagement.kaltara.Interface.DataSegmen;
import com.example.roadmanagement.kaltara.Interface.SendId;
import com.example.roadmanagement.kaltara.Interface.SingleSegment;
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
import com.example.roadmanagement.kaltara.databaseHelper.DbSpinner;
import com.example.roadmanagement.kaltara.helper.Session;

import java.util.ArrayList;

public class TaskAssetDel extends AsyncTask<String, String, String> {
    ProgressDialog dialog;
    Context context;

    DbLahan dbLahan;
    DbSaluran dbSaluran;
    DbBahu dbBahu;
    DbMedian dbMedian;
    DbLane dbLane;
    DbSegmen dbSegmen;
    DbSpinner dbSpinner;

    DbInlet dbInlet;
    DbMinlet dbMinlet;
    DbOutlet dbOutletl;
    DbGorong dbGorong;
    DbLereng dbLereng;

    SendId sendId;

    Session session;


    int noseg, subseg;
    String provinsi, ruas;
    DbRuas dbRuas;



    FungsiAPI fungsiAPI;

    protected PowerManager.WakeLock mWakeLock;
    final PowerManager pm;

    //download single ruas - onclick pada spinner

    public TaskAssetDel (final Context context, String ruas, int noseg, int subseg, ProgressDialog progressDialog, SendId sendId){
        this.context = context;
        this.sendId = sendId;
        this.ruas = ruas;
        this.noseg = noseg;
        this.subseg = subseg;
        this.dialog = progressDialog;

        fungsiAPI = new FungsiAPI(context);

        dialog = new ProgressDialog(context);
        dialog.setIndeterminate(false);
        dialog.setMessage("Penyegaran Ruas  "+ruas);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        if (dialog.isShowing()) {
            dialog.dismiss();
        }


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

        session = new Session(context);

        provinsi = session.getKodeprov();

        pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        this.mWakeLock.acquire();

    }

    @Override
    protected void onPreExecute() {


        dialog.show();
        dialog.setIndeterminate(false);
        dialog.setCancelable(false);

        int progressbarstatus = 0;

    };

    @Override
    protected String doInBackground(String... params) {

        dbSegmen.deleteMaks(provinsi, ruas, noseg, subseg);
        dbSpinner.deleteMaks(provinsi, ruas, noseg, subseg);

        dbBahu.deleteMaks(provinsi, ruas, noseg, subseg);
        dbLahan.deleteMaks(provinsi, ruas, noseg, subseg);
        dbLane.deleteMaks(provinsi, ruas, noseg, subseg);
        dbMedian.deleteMaks(provinsi, ruas, noseg, subseg);
        dbSaluran.deleteMaks(provinsi, ruas, noseg, subseg);

        dbGorong.deleteMaks(provinsi, ruas, noseg, subseg);
        dbInlet.deleteMaks(provinsi, ruas, noseg, subseg);
        dbLereng.deleteMaks(provinsi, ruas, noseg, subseg);
        dbMinlet.deleteMaks(provinsi, ruas, noseg, subseg);
        dbOutletl.deleteMaks(provinsi, ruas, noseg, subseg);

        return "Selesai";
    }

    @Override
    protected void onPostExecute(String result) {
        dialog.dismiss();
        sendId.hapusGambar(1);

        super.onPostExecute("Selesai");

    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }


}
