package com.example.roadmanagement.kaltara.FormTerusan;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.util.Log;

import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.Interface.DataLane;
import com.example.roadmanagement.kaltara.Interface.DataTemporari;
import com.example.roadmanagement.kaltara.Interface.SendDialog;
import com.example.roadmanagement.kaltara.Interface.SendId;
import com.example.roadmanagement.kaltara.databaseHelper.DbLane;
import com.example.roadmanagement.kaltara.databaseHelper.DbTemporari;
import com.example.roadmanagement.kaltara.helper.Session;

import java.util.ArrayList;

public class TaskEndSurvey extends AsyncTask<String, String, String> {


    ProgressDialog dialog;
    Context context;
    Session session;

    DbTemporari dbTemporari;
    String provinsi, ruas;
    String tipeSurvey, foto;

    SendDialog sendDialog;


    HelperFormTerusan helperFormTerusan;

    protected PowerManager.WakeLock mWakeLock;
    final PowerManager pm;

    String[] arrayTipe;

    public TaskEndSurvey (Context context, String tipeSurvey, ProgressDialog dialog, SendDialog sendDialog){

        this.context = context;
        this.tipeSurvey = tipeSurvey;
        this.dialog = dialog;
        this.sendDialog = sendDialog;

        helperFormTerusan = new HelperFormTerusan(context);


        session = new Session(context);
        dbTemporari = new DbTemporari(context);


        provinsi = session.getKodeprov();
        ruas = session.getNoruas();

        arrayTipe = context.getResources().getStringArray(R.array.arrayEndSurvey);

        pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");

    }

    @Override
    protected void onPreExecute() {
        dialog.show();
        dialog.setMax(arrayTipe.length);
        dialog.setMessage("Harap tunggu, sedang update data");
        dialog.setIndeterminate(false);
        dialog.setCancelable(false);


    };

    @Override
    protected String doInBackground(String... params) {



        for(int i = 0; i<arrayTipe.length; i++ ){

            dialog.setProgress(i);


            String[] parameter = arrayTipe[i].split("-");
            dialog.setMessage(parameter[0] +" "+parameter[1]);
            ArrayList<DataTemporari> list = getListPerubahan(parameter[0], provinsi, ruas, parameter[1]);
            if(list.size()>0){

                try {

                    setPerubahan(parameter[0], provinsi, ruas, parameter[1], tipeSurvey);
                    Thread.sleep(500);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }

        }


        return "completed";
    }

    @Override
    protected void onPostExecute(String result) {

        sendDialog.sendDialog(dialog);
        super.onPostExecute(result);

    }

    @Override
    protected void onProgressUpdate(String... values) {
        // getData(i);
        super.onProgressUpdate(values);
    }


    private void setPerubahan(String jenisPerubahan, String provinsi, String ruas, String posisi, String tipeSurvey){

        switch (jenisPerubahan){
            case "Bahu" : helperFormTerusan.saveBahuEnd(tipeSurvey, provinsi, ruas, posisi);
            break;

            case "Lahan" : helperFormTerusan.saveLahanEnd(tipeSurvey, provinsi, ruas, posisi);
            break;

            case "Saluran" : helperFormTerusan.saveSaluranEnd(tipeSurvey, provinsi, ruas, posisi);
            break;

            case "Perkerasan" : helperFormTerusan.savePerkerasanEnd(tipeSurvey, provinsi, ruas, posisi);
            break;

            case "SPD" : helperFormTerusan.saveSPDEnd(tipeSurvey, provinsi, ruas, posisi);
            break;

            case "SPL" : helperFormTerusan.saveSPLEnd(tipeSurvey, provinsi, ruas, posisi);
            break;

            case "Median" : helperFormTerusan.saveMedianEnd(tipeSurvey, provinsi, ruas);
            break;

            case "Lane" : helperFormTerusan.saveLaneEnd(tipeSurvey, provinsi, ruas, posisi);
            break;
        }


    }


    private ArrayList<DataTemporari> getListPerubahan(String jenisPerubahan, String provinsi, String ruas, String posisi){

        ArrayList<DataTemporari> list = new ArrayList<>();

        if(jenisPerubahan.equals("Lane")){
            String posisiLane;
            if(posisi.substring(0,1).equals("L")){
                posisiLane = "kiri";
            }else{
                posisiLane = "kanan";
            }
            list = dbTemporari.getListTemporari(provinsi, ruas, jenisPerubahan, posisiLane, posisi, null, null, tipeSurvey);
        }else if(jenisPerubahan.equals("Median")){
            list = dbTemporari.getListTemporari(provinsi, ruas, jenisPerubahan, null, null, null, null, tipeSurvey);
        }else{
            list = dbTemporari.getListTemporari(provinsi, ruas, jenisPerubahan, posisi, null, null, null, tipeSurvey);
        }

        return list;
    }

}

