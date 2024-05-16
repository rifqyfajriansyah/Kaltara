package com.example.roadmanagement.kaltara.FormTerusan;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import com.example.roadmanagement.kaltara.Interface.DataLane;
import com.example.roadmanagement.kaltara.Interface.DataSegmen;
import com.example.roadmanagement.kaltara.Interface.SendDialog;
import com.example.roadmanagement.kaltara.Interface.SendId;
import com.example.roadmanagement.kaltara.Interface.Senddata;
import com.example.roadmanagement.kaltara.databaseHelper.DbLane;
import com.example.roadmanagement.kaltara.databaseHelper.DbSegmen;
import com.example.roadmanagement.kaltara.databaseHelper.DbSpinner;
import com.example.roadmanagement.kaltara.databaseHelper.DbTemporari;
import com.example.roadmanagement.kaltara.helper.Session;

public class TaskSaveSegmentTerusan extends AsyncTask<String, String, String> {


    ProgressDialog dialog;
    Context context;
    Session session;

    DataSegmen dataSegmen;
    DbSegmen dbSegmen;
    DbSpinner dbSpinner;
    DbLane dbLane;
    DbTemporari dbTemporari;
    int jumlahPerubahan;
    String tipeSurvey, provinsi, ruas;
    int segmentAkhir, subSegmentAkhir, indexSegment, indexSub;

    SendDialog sendDialog;

    protected PowerManager.WakeLock mWakeLock;
    final PowerManager pm;


    public TaskSaveSegmentTerusan (Context context, DataSegmen dataSegmen, String tipeSurvey, int segmentAkhir, int subSegmentAkhir, SendDialog sendDialog){

        this.dataSegmen = dataSegmen;
        this.context = context;
        this.segmentAkhir = segmentAkhir;
        this.subSegmentAkhir = subSegmentAkhir;
        this.tipeSurvey = tipeSurvey;
        this.sendDialog = sendDialog;


        dialog = new ProgressDialog(context);
        dialog.setIndeterminate(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);


        session = new Session(context);
        dbLane = new DbLane(context);
        dbSegmen = new DbSegmen(context);
        dbSpinner = new DbSpinner(context);
        dbTemporari = new DbTemporari(context);

        provinsi = session.getKodeprov();
        ruas = session.getNoruas();

        jumlahPerubahan = getJumlahPerubahan(tipeSurvey, dataSegmen.getNosegment(), dataSegmen.getSubsegment(), segmentAkhir, subSegmentAkhir);


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
        indexSegment = dataSegmen.getNosegment();
        indexSub = dataSegmen.getSubsegment();

            for(int i=0; i<jumlahPerubahan; i++ ){

                indexSegment = tambahSegment(tipeSurvey, indexSegment, indexSub);
                indexSub = tambahSubSegment(tipeSurvey, indexSub);

                DataSegmen dataSegmenTempo  = dbSegmen.getSegmen(provinsi, ruas, indexSegment, indexSub);

                    cekPerubahan(dataSegmenTempo.getSegmentl1(), dataSegmen.getSegmentl1(), indexSegment, indexSub, "kiri", "L1");
                    cekPerubahan(dataSegmenTempo.getSegmentl2(), dataSegmen.getSegmentl2(), indexSegment, indexSub, "kiri", "L2");
                    cekPerubahan(dataSegmenTempo.getSegmentl3(), dataSegmen.getSegmentl3(), indexSegment, indexSub, "kiri", "L3");
                    cekPerubahan(dataSegmenTempo.getSegmentl4(), dataSegmen.getSegmentl4(), indexSegment, indexSub, "kiri", "L4");
                    cekPerubahan(dataSegmenTempo.getSegmentl5(), dataSegmen.getSegmentl5(), indexSegment, indexSub, "kiri", "L5");
                    cekPerubahan(dataSegmenTempo.getSegmentl6(), dataSegmen.getSegmentl6(), indexSegment, indexSub, "kiri", "L6");
                    cekPerubahan(dataSegmenTempo.getSegmentl7(), dataSegmen.getSegmentl7(), indexSegment, indexSub, "kiri", "L7");
                    cekPerubahan(dataSegmenTempo.getSegmentl8(), dataSegmen.getSegmentl8(), indexSegment, indexSub, "kiri", "L8");
                    cekPerubahan(dataSegmenTempo.getSegmentl9(), dataSegmen.getSegmentl9(), indexSegment, indexSub, "kiri", "L9");
                    cekPerubahan(dataSegmenTempo.getSegmentl10(), dataSegmen.getSegmentl10(), indexSegment, indexSub, "kiri", "L10");

                    cekPerubahan(dataSegmenTempo.getSegmentr1(), dataSegmen.getSegmentr1(), indexSegment, indexSub, "kanan", "R1");
                    cekPerubahan(dataSegmenTempo.getSegmentr2(), dataSegmen.getSegmentr2(), indexSegment, indexSub, "kanan", "R2");
                    cekPerubahan(dataSegmenTempo.getSegmentr3(), dataSegmen.getSegmentr3(), indexSegment, indexSub, "kanan", "R3");
                    cekPerubahan(dataSegmenTempo.getSegmentr4(), dataSegmen.getSegmentr4(), indexSegment, indexSub, "kanan", "R4");
                    cekPerubahan(dataSegmenTempo.getSegmentr5(), dataSegmen.getSegmentr5(), indexSegment, indexSub, "kanan", "R5");
                    cekPerubahan(dataSegmenTempo.getSegmentr6(), dataSegmen.getSegmentr6(), indexSegment, indexSub, "kanan", "R6");
                    cekPerubahan(dataSegmenTempo.getSegmentr7(), dataSegmen.getSegmentr7(), indexSegment, indexSub, "kanan", "R7");
                    cekPerubahan(dataSegmenTempo.getSegmentr8(), dataSegmen.getSegmentr8(), indexSegment, indexSub, "kanan", "R8");
                    cekPerubahan(dataSegmenTempo.getSegmentr9(), dataSegmen.getSegmentr9(), indexSegment, indexSub, "kanan", "R9");
                    cekPerubahan(dataSegmenTempo.getSegmentr10(), dataSegmen.getSegmentr10(), indexSegment, indexSub, "kanan", "R10");


                dialog.setProgress(progress);
                progress = progress+1;

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
        super.onProgressUpdate(values);
    }


    private void cekPerubahan(int a, int b, int segment, int subSegment, String posisi, String lajur){

        if(a!=b){

            if(a==0 && b==1){

                //Log.d("TESTING", a+"--"+b+"--"+lajur+"."+segment+"."+subSegment+"- Tambah");

                dbLane.tambahLane(provinsi, ruas, segment, subSegment, posisi, lajur);
                dbTemporari.saveTemporariTerusan("Segment", provinsi, ruas, "Tambah", lajur, segment, subSegment, segment, subSegment, tipeSurvey, "1");
                dbTemporari.hapusTemporari(provinsi, ruas, segment, subSegment, "Segment", "Hapus", lajur);

            }else if(a==1 && b==0){

                //Log.d("TESTING", a+"--"+b+"--"+lajur+"."+segment+"."+subSegment+"- Hapus");

                dbLane.hapusLane(provinsi, ruas, segment, subSegment, posisi, lajur);
                dbTemporari.saveTemporariTerusan("Segment", provinsi, ruas, "Hapus", lajur, segment, subSegment, segment, subSegment, tipeSurvey, "1");
                dbTemporari.hapusTemporari(provinsi, ruas, segment, subSegment, "Lane", posisi, lajur);
                dbTemporari.hapusTemporari(provinsi, ruas, segment, subSegment, "Segment", "Tambah", lajur);
            }


        }

        //else{
            //Log.d("TESTING", a+"--"+b+"--"+lajur+"."+segment+"."+subSegment+"- Update");
        //}


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


