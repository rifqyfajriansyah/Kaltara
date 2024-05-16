package com.example.roadmanagement.kaltara.kolektif;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.roadmanagement.kaltara.Formu.FormUtama;
import com.example.roadmanagement.kaltara.Interface.DataBahu;
import com.example.roadmanagement.kaltara.Interface.DataLahan;
import com.example.roadmanagement.kaltara.Interface.DataLane;
import com.example.roadmanagement.kaltara.Interface.DataMedian;
import com.example.roadmanagement.kaltara.Interface.DataRuas;
import com.example.roadmanagement.kaltara.Interface.DataSaluran;
import com.example.roadmanagement.kaltara.Interface.DataSegmen;
import com.example.roadmanagement.kaltara.Interface.DataTemporari;
import com.example.roadmanagement.kaltara.Interface.SendId;
import com.example.roadmanagement.kaltara.Tabel.LihatTabel;
import com.example.roadmanagement.kaltara.api.FungsiAPI;
import com.example.roadmanagement.kaltara.databaseHelper.DbBahu;
import com.example.roadmanagement.kaltara.databaseHelper.DbLahan;
import com.example.roadmanagement.kaltara.databaseHelper.DbLane;
import com.example.roadmanagement.kaltara.databaseHelper.DbMedian;
import com.example.roadmanagement.kaltara.databaseHelper.DbSaluran;
import com.example.roadmanagement.kaltara.databaseHelper.DbSegmen;
import com.example.roadmanagement.kaltara.databaseHelper.DbTemporari;

import java.util.ArrayList;

public class KolektifTask extends AsyncTask<String, String, String> {
    ProgressDialog dialog;
    Context context;

    DbLahan dbLahan;
    DbMedian dbMedian;
    DbBahu dbBahu;
    DbLane dbLane;
    DbSaluran dbSaluran;
    DbTemporari dbTemporari;
    SendId sendId ;


    int awal;
    int akhir;

    String tipe;
    String posisi;
    String value1;
    String value2;
    String value3;
    String value4;
    String lajur;
    String noprov;
    String ruas;
    String letak;
    String status;

    String tempofoto;
    String foto;
    String icon;
    String lokasi;

    int max;
    String halaman;
    int params;
    //int prog = 0 ;

    public KolektifTask(Context context, String noprov, String ruas, String posisi, String lajur, int awal, int akhir, String tipe, String value1, String value2, String value3, String value4, String status, String foto, String icon, String lokasi, String halaman){
        this.context = context;
        this.sendId = sendId;

        this.noprov = noprov;
        this.ruas =ruas;
        this.awal = awal;
        this.akhir = akhir;
        this.tipe = tipe;
        this.status = status;

        this.halaman = halaman;

        this.posisi = posisi;

        this.lajur = lajur;

        this.value1 = value1;
        this.value2 = value2;
        this.value3 =value3;
        this.value4 = value4;

        this.foto = foto;
        this.icon = icon;
        this.lokasi = lokasi;

        dbLahan = new DbLahan(context);
        dbMedian = new DbMedian(context);
        dbBahu = new DbBahu(context);
        dbSaluran = new DbSaluran(context);
        dbLane = new DbLane(context);

        dbTemporari = new DbTemporari(context);

        if(posisi.equals("kiri")){
            letak = "Left";
        }else if(posisi.equals("kanan")){
            letak = "Right";
        }else{
            letak="";
        }



        if(foto==null){
            tempofoto="0";
        }else{
            tempofoto="1";
        }

        if(halaman!=null && foto==null){
            params=0;
        }else{
            params=1 ;
        }

        dialog = new ProgressDialog(context);
        dialog.setIndeterminate(false);
        dialog.setMessage("Harap tunggu ...");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        max = akhir - awal;

    }

    @Override
    protected void onPreExecute() {
        dialog.show();
        dialog.setProgress(0);
        dialog.setIndeterminate(false);
        dialog.setCancelable(false);
        dialog.setMax(max);
        int progressbarstatus = 0;

    };

    @Override
    protected String doInBackground(String... params) {

        ubahBanyak(tipe);
        return "Selesai";
    }

    @Override
    protected void onPostExecute(String result) {

        // TODO Auto-generated method stub
        dialog.dismiss();
        Toast.makeText(context, "Data berhasil diubah", Toast.LENGTH_SHORT).show();

        Intent i;

        if(status.equals("form")){
            i = new Intent(context, FormUtama.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(i);
        }else if(status.equals("tabel")){
            i = new Intent(context, LihatTabel.class);
            switch (tipe){
                case "Lahan": i.putExtra("posisi", "1");
                break;

                case "Bahu": i.putExtra("posisi", "3");
                break;

                case "Saluran" : i.putExtra("posisi", "2");
                break;

                case "Median" : i.putExtra("posisi", "4");
                break;

                case "Lane" : i.putExtra("posisi", "5");
                break;
            }

            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(i);
        }else if(status.equals("mainmenu")){

        }

        super.onPostExecute("Selesai");

    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    public void ubahBanyak(final String tipe){

        /*int prog = 0;

        switch (tipe) {

            case "Lahan": {
                for (int i = awal; i <= akhir; i++) {
                    dialog.setProgress(prog);
                    if(params==1) {
                        dbLahan.setLahanKolektif(noprov, ruas, i, posisi, value1, value2, Float.parseFloat("0"), Float.parseFloat(value3), foto, icon, lokasi);
                    }else{
                        DataLahan dataLahan = dbLahan.getLahan(noprov, ruas, i, posisi);
                        if(dataLahan.getGambarLahan() == null){
                            tempofoto = "0";
                        }else{
                            tempofoto = "1";
                        }
                        dbLahan.setLahanKolektifFoto(noprov, ruas, i, posisi, value1, value2, Float.parseFloat("0"), Float.parseFloat(value3));
                    }
                    DataTemporari dataTemporari = new DataTemporari(noprov, ruas, i, tipe, letak, lajur, null, 0,tempofoto, "0");
                    dbTemporari.postTemporari(dataTemporari);

                    prog = prog +1;


                }
            }

            break;

            case "Median":{
                for(int i = awal; i <=akhir; i++){

                    dialog.setProgress(prog);

                    if(params==1) {
                        dbMedian.setMedianKolektif(noprov, ruas, i, "", Float.parseFloat(value1), foto, icon, lokasi);
                    }else{
                        DataMedian dataMedian =  dbMedian.getMedian(noprov, ruas, i );
                        if(dataMedian.getGambarMedian() == null){
                            tempofoto = "0";
                        }else{
                            tempofoto = "1";
                        }
                        dbMedian.setMedianKolektifFoto(noprov, ruas, i, "", Float.parseFloat(value1));
                    }

                    DataTemporari dataTemporari = new DataTemporari(noprov, ruas, i, tipe, letak, lajur, null, 0,tempofoto,"0");
                    dbTemporari.postTemporari(dataTemporari);

                    prog = prog +1;

                }
            }

            break;

            case "Bahu": {
                for (int i = awal; i <= akhir; i++) {
                    dialog.setProgress(prog);

                    if(params==1) {
                       //Toast.makeText(context, "Foto", Toast.LENGTH_SHORT).show();
                        dbBahu.setBahuKolektif(noprov, ruas, i, posisi, value1, Float.parseFloat(value2), value3, Float.parseFloat(value4), foto, icon, lokasi);
                    }else{
                        //Toast.makeText(context, "Foto WO", Toast.LENGTH_SHORT).show();
                        DataBahu dataBahu = dbBahu.getBahu(noprov, ruas, i, posisi);
                        if(dataBahu.getGambarBahu() == null){
                            tempofoto = "0";
                        }else{
                            tempofoto = "1";
                        }
                        dbBahu.setBahuKolektifFoto(noprov, ruas, i, posisi, value1, Float.parseFloat(value2), value3, Float.parseFloat(value4));
                    }

                    DataTemporari dataTemporari = new DataTemporari(noprov, ruas, i, tipe, letak, lajur,null, 0, tempofoto, "0");
                    dbTemporari.postTemporari(dataTemporari);

                    prog = prog +1;


                }
            }
            break;


            case "Saluran": {
                for (int i = awal; i <= akhir; i++) {
                    dialog.setProgress(prog);

                    if(params==1) {
                        dbSaluran.setSaluranKolektif(noprov, ruas, i, posisi, value1, Float.parseFloat(value2), Float.parseFloat(value3), foto, icon, lokasi);
                    }else{
                        DataSaluran dataSaluran = dbSaluran.getSaluran(noprov, ruas, i, posisi);
                        if(dataSaluran.getGambarSaluran() == null){
                            tempofoto = "0";
                        }else{
                            tempofoto = "1";
                        }
                        dbSaluran.setSaluranKolektifFoto(noprov, ruas, i, posisi, value1, Float.parseFloat(value2), Float.parseFloat(value3));
                    }

                    DataTemporari dataTemporari = new DataTemporari( noprov, ruas, i, tipe, letak, lajur, null, 0,tempofoto, "0");
                    dbTemporari.postTemporari(dataTemporari);

                    prog = prog +1;


                }
            }
            break;


            case "Lane":{
                for (int i = awal; i <= akhir; i++) {
                    dialog.setProgress(prog);
                    final int finalI = i;

                    if(params==1) {
                        dbLane.setLaneKolektif(noprov, ruas, i, posisi, lajur, value1, Float.parseFloat(value2), foto, icon, lokasi, new SendId() {
                            @Override
                            public void hapusGambar(int id) {
                                if (id == 1) {
                                    DataTemporari dataTemporari = new DataTemporari( noprov, ruas, finalI, tipe, letak, lajur, null, 0,tempofoto, "0");
                                    dbTemporari.postTemporari(dataTemporari);
                                }
                            }
                        });
                    }else{

                        DataLane dataLane = dbLane.getLane(noprov, ruas, i, lajur, posisi);
                        if (dataLane != null){
                            if(dataLane.getGambarLane() == null){
                                tempofoto = "0";
                            }else{
                                tempofoto = "1";
                            }
                        }

                        dbLane.setLaneKolektifFoto(noprov, ruas, i, posisi, lajur, value1, Float.parseFloat(value2), new SendId() {
                            @Override
                            public void hapusGambar(int id) {
                                if (id == 1) {
                                    DataTemporari dataTemporari = new DataTemporari(noprov, ruas, finalI, tipe, letak, lajur, null, 0, tempofoto, "0");
                                    dbTemporari.postTemporari(dataTemporari);
                                }
                            }
                        });
                    }


                    prog = prog +1;


                }

                break;

            }
        }*/
    }





}
