package com.example.roadmanagement.kaltara.Task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.roadmanagement.kaltara.Interface.CekSinkron;
import com.example.roadmanagement.kaltara.Interface.DataBahu;
import com.example.roadmanagement.kaltara.Interface.DataDownload;
import com.example.roadmanagement.kaltara.Interface.DataLahan;
import com.example.roadmanagement.kaltara.Interface.DataMedian;
import com.example.roadmanagement.kaltara.Interface.DataRuas;
import com.example.roadmanagement.kaltara.Interface.DataSaluran;
import com.example.roadmanagement.kaltara.Interface.SendId;
import com.example.roadmanagement.kaltara.api.FungsiAPI;
import com.example.roadmanagement.kaltara.databaseHelper.DbBahu;
import com.example.roadmanagement.kaltara.databaseHelper.DbLahan;
import com.example.roadmanagement.kaltara.databaseHelper.DbMedian;
import com.example.roadmanagement.kaltara.databaseHelper.DbSaluran;

import java.util.ArrayList;

//task update lanjutan dari onclik spinner menu utama
public class TaskUpdateAll{

}

        /*extends AsyncTask<String, String, String> {
   // ProgressDialog dialog;
    Context context;
    FungsiAPI fungsiAPI;
    SendId sendId ;
    CekSinkron list;
    int ruas;
    String ruasku;

    public TaskUpdateAll (Context context, CekSinkron list, int ruas, SendId sendId){
        this.context = context;
        this.sendId = sendId;
        this.ruas =ruas;
        this.list = list;

       // dialog = new ProgressDialog(context);
       // dialog.setIndeterminate(false);
        //dialog.setMessage("Import Data "+ruasku);
       // dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        fungsiAPI = new FungsiAPI(context);

    }

    @Override
    protected void onPreExecute() {


    };

    @Override
    protected String doInBackground(String... params) {

        switch (list.getDetail()){
            case "Lahan" :{

                fungsiAPI.setLahan(list.getNoprov(), list.getNoruas(), list.getNoseg(), list.getPosisi());

            }
            break;
            case "Saluran" :{
              //  Toast.makeText(context, String.valueOf(ruas)+ " : "+list.getDetail(), Toast.LENGTH_SHORT).show();

                 fungsiAPI.setSaluran(list.getNoprov(), list.getNoruas(), list.getNoseg(), list.getPosisi());

            }
            break;
            case "Bahu":{
               // Toast.makeText(context, String.valueOf(ruas)+ " : "+list.getDetail(), Toast.LENGTH_SHORT).show();

                fungsiAPI.setBahu(list.getNoprov(), list.getNoruas(), list.getNoseg(), list.getPosisi());

            }
            break;

            case "Lane":{

                String urut = null;
                String letak = null;

                if(list.getPosisi().substring(0,1).equals("L")){
                    letak = "kiri";
                }else{
                    letak = "kanan";
                }

                urut = list.getPosisi().substring(1);

                fungsiAPI.setLane(list.getNoprov(), list.getNoruas(), list.getNoseg(),  letak, urut);

                /*switch (list.getPosisi()){
                    case "L1" : {
                        letak="kiri";
                        urut = "1";
                    }

                    break;

                    case "L2" : {
                        letak="kiri";
                        urut = "2";
                    }

                    break;

                    case "L3" : {
                        letak="kiri";
                        urut = "3";
                    }

                    break;

                    case "L4" : {
                        letak="kiri";
                        urut = "4";
                    }

                    break;

                    case "R1" : {
                        letak="kanan";
                        urut = "1";
                    }

                    break;

                    case "R2" : {
                        letak="kanan";
                        urut = "2";
                    }

                    break;

                    case "R3" : {
                        letak="kanan";
                        urut = "3";
                    }

                    break;

                    case "R4" : {
                        letak="kanan";
                        urut = "4";
                    }

                    break;
                }



            }
            break;

            case "Median":{

                fungsiAPI.setMedian(list.getNoprov(), list.getNoruas(), list.getNoseg());


            }
            break;

            case "Segment":{

                fungsiAPI.updateSegment(list.getNoprov(), list.getNoruas(), list.getNoseg(), list.getPosisi());

            }
        }


        return "Selesai";
    }

    @Override
    protected void onPostExecute(String result) {

        // TODO Auto-generated method stub
       // dialog.dismiss();
        sendId.hapusGambar(ruas);
        super.onPostExecute("Selesai");

    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

}
*/
