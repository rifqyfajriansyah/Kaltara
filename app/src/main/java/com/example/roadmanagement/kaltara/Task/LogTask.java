package com.example.roadmanagement.kaltara.Task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.roadmanagement.kaltara.DownloadBaru.DetailDownload;
import com.example.roadmanagement.kaltara.Interface.SendId;
import com.example.roadmanagement.kaltara.Menuutama;
import com.example.roadmanagement.kaltara.databaseHelper.DbBahu;
import com.example.roadmanagement.kaltara.databaseHelper.DbLahan;
import com.example.roadmanagement.kaltara.databaseHelper.DbLane;
import com.example.roadmanagement.kaltara.databaseHelper.DbMedian;
import com.example.roadmanagement.kaltara.databaseHelper.DbSaluran;
import com.example.roadmanagement.kaltara.databaseHelper.DbSegmen;
import com.example.roadmanagement.kaltara.databaseHelper.DbSpinner;
import com.example.roadmanagement.kaltara.helper.Session;

import java.util.ArrayList;

public class LogTask extends AsyncTask<String, String, String> {
    ProgressDialog dialog;
    Context context;

    Session session;


    public LogTask (final Context context){
        this.context = context;


        dialog = new ProgressDialog(context);
        dialog.setIndeterminate(false);
        dialog.setMessage("Silahkan tunggu ");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);


        session = new Session(context);

    }

    @Override
    protected void onPreExecute() {
        dialog.show();
        dialog.setProgress(0);
        dialog.setIndeterminate(false);
        dialog.setCancelable(false);

        int progressbarstatus = 0;

    };

    @Override
    protected String doInBackground(String... params) {


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



}
