package com.example.roadmanagement.kaltara.kolektif;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.roadmanagement.kaltara.Formu.FormUtama;
import com.example.roadmanagement.kaltara.Interface.DataSegmen;
import com.example.roadmanagement.kaltara.Interface.DataTemporari;
import com.example.roadmanagement.kaltara.Interface.SendId;
import com.example.roadmanagement.kaltara.Tabel.LihatTabel;
import com.example.roadmanagement.kaltara.databaseHelper.DbBahu;
import com.example.roadmanagement.kaltara.databaseHelper.DbLahan;
import com.example.roadmanagement.kaltara.databaseHelper.DbLane;
import com.example.roadmanagement.kaltara.databaseHelper.DbMedian;
import com.example.roadmanagement.kaltara.databaseHelper.DbSaluran;
import com.example.roadmanagement.kaltara.databaseHelper.DbSegmen;
import com.example.roadmanagement.kaltara.databaseHelper.DbTemporari;

public class KolektifSegmentTask extends AsyncTask<String, String, String> {
    ProgressDialog dialog;
    Context context;

    DbSegmen dbSegmen;
    DbLane dbLane;
    DbTemporari dbTemporari;
    SendId sendId ;


    int awal;
    int akhir;

    String status;


    String vertikal;
    String horizontal;
    String tipejalan;
    int l1;
    int l2;
    int l3;
    int l4;
    int l5;
    int l6;
    int l7;
    int l8;
    int l9;
    int l10;

    int r1;
    int r2;
    int r3;
    int r4;
    int r5;
    int r6;
    int r7;
    int r8;
    int r9;
    int r10;

    String lajur;
    String noprov;
    String ruas;
    String letak;

    Float lebarpvmt;

    float grade;

    int max;
    //int prog = 0 ;

    public KolektifSegmentTask(Context context, String noprov, String ruas,  int awal, int akhir, String vertikal, String horizontal, String tipejalan, Float lebarpvmt, float grade, int l1, int l2, int l3, int l4, int l5, int l6, int l7, int l8, int l9, int l10,
                               int r1, int r2, int r3, int r4, int r5, int r6, int r7, int r8, int r9, int r10, String status){
        this.context = context;
        this.sendId = sendId;

        this.vertikal = vertikal;
        this.horizontal = horizontal;
        this.tipejalan = tipejalan;
        this.lebarpvmt = lebarpvmt;
        this.grade = grade;
        this.l1 = l1;
        this.l2 = l2;
        this.l3 = l3;
        this.l4 = l4;
        this.l5 = l5;
        this.l6 = l6;
        this.l7 = l7;
        this.l8 = l8;
        this.l9 = l9;
        this.l10 = l10;

        this.r1 = r1;
        this.r2 = r2;
        this.r3 = r3;
        this.r4 = r4;
        this.r5 = r5;
        this.r6 = r6;
        this.r7 = r7;
        this.r8 = r8;
        this.r9 = r9;
        this.r10 = r10;

        this.status = status;
        this.noprov = noprov;
        this.ruas =ruas;
        this.awal = awal;
        this.akhir = akhir;






        dbTemporari = new DbTemporari(context);
        dbSegmen = new DbSegmen(context);
        dbLane = new DbLane(context);


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

        int prog=1;

        for (int i=awal; i<=akhir; i++){
            dialog.setProgress(prog);
           /* DataSegmen dataSegmen = dbSegmen.getSegmen(noprov, ruas, i);
            cekPerubahan(dataSegmen.getSegmentl1(), l1, "L1", i, "kiri");
            cekPerubahan(dataSegmen.getSegmentl2(), l2, "L2", i, "kiri");
            cekPerubahan(dataSegmen.getSegmentl3(), l3, "L3", i, "kiri");
            cekPerubahan(dataSegmen.getSegmentl4(), l4, "L4", i, "kiri");
            cekPerubahan(dataSegmen.getSegmentl5(), l5, "L5", i, "kiri");
            cekPerubahan(dataSegmen.getSegmentl6(), l6, "L6", i, "kiri");
            cekPerubahan(dataSegmen.getSegmentl7(), l7, "L7", i, "kiri");
            cekPerubahan(dataSegmen.getSegmentl8(), l8, "L8", i, "kiri");
            cekPerubahan(dataSegmen.getSegmentl9(), l9, "L9", i, "kiri");
            cekPerubahan(dataSegmen.getSegmentl10(), l10, "L10", i, "kiri");

            cekPerubahan(dataSegmen.getSegmentr1(), r1, "R1", i, "kanan");
            cekPerubahan(dataSegmen.getSegmentr2(), r2, "R2", i, "kanan");
            cekPerubahan(dataSegmen.getSegmentr3(), r3, "R3", i, "kanan");
            cekPerubahan(dataSegmen.getSegmentr4(), r4, "R4", i, "kanan");
            cekPerubahan(dataSegmen.getSegmentr1(), r5, "R5", i, "kanan");
            cekPerubahan(dataSegmen.getSegmentr2(), r6, "R6", i, "kanan");
            cekPerubahan(dataSegmen.getSegmentr3(), r7, "R7", i, "kanan");
            cekPerubahan(dataSegmen.getSegmentr4(), r8, "R8", i, "kanan");
            cekPerubahan(dataSegmen.getSegmentr1(), r9, "R9", i, "kanan");
            cekPerubahan(dataSegmen.getSegmentr2(), r10, "R10", i, "kanan");

            int jumlah = l1+l2+l3+l4+l5+l6+l7+l8+l9+l10+r1+r2+r3+r4+r5+r6+r7+r8+r9+r10;
            dbSegmen.postSegment(new DataSegmen(0, noprov, ruas, i, l1, l2, l3, l4, l5, l6, l7, l8, l9, l10,
                    r1, r2, r3, r4, r5, r6, r7, r8, r9, r10,jumlah,vertikal, horizontal, tipejalan, lebarpvmt,grade));
            dbTemporari.postTemporari(new DataTemporari(noprov, ruas, i, "Segment", "Update", "", null, 0,"1", "0"));
            prog=prog+1;*/
        }
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
            i.putExtra("posisi", "0");
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(i);
        }

        super.onPostExecute("Selesai");

    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    public void cekPerubahan(int a, int b, String lajur, int noseg, String posisi){

        //DataTemporari dataTemporari = new DataTemporari(noprov, ruas, noseg, "Segment", "", lajur, null, 0,"1","0");

        String letak;

        if(posisi.equals("kiri")){
            letak="Left";
        }else{
            letak="Right";
        }

        if(a!=b){

            /*if(a==0 && b==1){
                dataTemporari.setPosisi("Tambah");
                dbLane.tambahLane(noprov, ruas, noseg, posisi, lajur);
                dbTemporari.hapusTemporari(noprov, ruas, noseg, "Segment", "Hapus", lajur);
                dbTemporari.postTemporari(new DataTemporari( noprov, ruas, noseg, "Lane", letak, lajur, null, 0,"0", "0"));
            }else if(a==1 && b==0){

                dataTemporari.setPosisi("Hapus");
                dbLane.hapusLane(noprov, ruas, noseg, posisi, lajur);
                dbTemporari.hapusTemporari(noprov, ruas, noseg, "Segment", "Tambah", lajur);
                dbTemporari.hapusTemporari(noprov, ruas, noseg, "Lane", letak, lajur);
                //dbTemporari.hapusTemporari();
            }

            dbTemporari.postTemporari(dataTemporari);*/

        }


    }



}
