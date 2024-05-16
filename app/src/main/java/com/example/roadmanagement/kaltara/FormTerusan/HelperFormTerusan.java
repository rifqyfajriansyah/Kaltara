package com.example.roadmanagement.kaltara.FormTerusan;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.Formu.DetailSaluran;
import com.example.roadmanagement.kaltara.Formu.FormUtama;
import com.example.roadmanagement.kaltara.Interface.DataBahu;
import com.example.roadmanagement.kaltara.Interface.DataInletMedian;
import com.example.roadmanagement.kaltara.Interface.DataLahan;
import com.example.roadmanagement.kaltara.Interface.DataLane;
import com.example.roadmanagement.kaltara.Interface.DataMedian;
import com.example.roadmanagement.kaltara.Interface.DataSaluran;
import com.example.roadmanagement.kaltara.Interface.DataSegmen;
import com.example.roadmanagement.kaltara.Interface.DataSpDalam;
import com.example.roadmanagement.kaltara.Interface.DataSpLuar;
import com.example.roadmanagement.kaltara.Interface.DataTemporari;
import com.example.roadmanagement.kaltara.Interface.SendDialog;
import com.example.roadmanagement.kaltara.Interface.SendId;
import com.example.roadmanagement.kaltara.Interface.Senddata;
import com.example.roadmanagement.kaltara.Interface.SingleSegment;
import com.example.roadmanagement.kaltara.Tabel.LihatTabel;
import com.example.roadmanagement.kaltara.databaseHelper.DbBahu;
import com.example.roadmanagement.kaltara.databaseHelper.DbLahan;
import com.example.roadmanagement.kaltara.databaseHelper.DbLane;
import com.example.roadmanagement.kaltara.databaseHelper.DbMedian;
import com.example.roadmanagement.kaltara.databaseHelper.DbMinlet;
import com.example.roadmanagement.kaltara.databaseHelper.DbSaluran;
import com.example.roadmanagement.kaltara.databaseHelper.DbSegmen;
import com.example.roadmanagement.kaltara.databaseHelper.DbSpd;
import com.example.roadmanagement.kaltara.databaseHelper.DbSpinner;
import com.example.roadmanagement.kaltara.databaseHelper.DbSpl;
import com.example.roadmanagement.kaltara.databaseHelper.DbTemporari;
import com.example.roadmanagement.kaltara.helper.HelperList;
import com.example.roadmanagement.kaltara.helper.Session;
import com.example.roadmanagement.kaltara.sinkronForm.SinkronUtama;

import java.lang.reflect.Array;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class HelperFormTerusan {

    Context context;
    Session session;
    DbTemporari dbTemporari;
    DbLahan dbLahan;
    DbSaluran dbSaluran;
    DbBahu dbBahu;
    DbMedian dbMedian;
    DbLane dbLane;
    DbSegmen dbSegmen;

    int maksimalSub, maksimalSegment;

    DbSpinner dbSpinner;
    HelperList helperList;


    public HelperFormTerusan(Context context){

        this.context = context;

        session = new Session(context);
        dbLahan = new DbLahan(context);
        dbSaluran = new DbSaluran(context);
        dbBahu = new DbBahu(context);
        dbMedian = new DbMedian(context);
        dbLane = new DbLane(context);
        dbSegmen = new DbSegmen(context);
        dbTemporari = new DbTemporari(context);
        dbSpinner = new DbSpinner(context);
        helperList = new HelperList();


        if(session.getTipesurvey()!=null){
            if(session.getTipesurvey().equals("Normal")){
                maksimalSegment = dbSpinner.getMaksSegment(session.getKodeprov(), session.getNoruas());
                maksimalSub = dbSpinner.getMaksSegmentSub(session.getKodeprov(), session.getNoruas(), maksimalSegment);
            }else{
                maksimalSegment = 1;
                maksimalSub = 0;
            }
        }

    }



    private ArrayList<DataTemporari> getTerusan(String tipe, String jenis, String provinsi, String ruas, String posisi, String lajur){

        ArrayList<DataTemporari> list = dbTemporari.getListTemporari(provinsi, ruas, tipe, posisi, lajur, null, null, jenis);
        return list;

    }



    public void saveLahan(DataLahan dataLahan, String asal){

        dbLahan.setLahan(dataLahan);
        saveTemporari("Lahan", session.getTipesurvey(), dataLahan.getNoprov(), dataLahan.getNoruas(), dataLahan.getNosegment(), dataLahan.getSubsegment(), dataLahan.getPosisi(), "", dataLahan.getNosegment(), dataLahan.getSubsegment());

        ArrayList<DataTemporari> listTemporari = getTerusan("Lahan", session.getTipesurvey(), dataLahan.getNoprov(), dataLahan.getNoruas(), dataLahan.getPosisi(), null);

        if(listTemporari.size()>1){

            int index = getIndex(listTemporari, dataLahan.getNosegment(), dataLahan.getSubsegment());


            if(index!=0){

                int segAkhir = getMinSegment(session.getTipesurvey(), listTemporari.get(index).getNosegment(), listTemporari.get(index).getSubsegment());
                int subAkhir = getMinSub(session.getTipesurvey(), listTemporari.get(index).getSubsegment());
                addSebelum(session.getTipesurvey(), listTemporari, index);
                DataLahan dataLahanTemporari = dbLahan.getLahan(dataLahan.getNoprov(), dataLahan.getNoruas(), listTemporari.get(index-1).getNosegment(),  listTemporari.get(index-1).getSubsegment(), listTemporari.get(index-1).getPosisi());
                dbLahan.saveLahanNormal(dataLahanTemporari, dataLahanTemporari.getNosegment(), dataLahanTemporari.getSubsegment(), segAkhir, subAkhir, session.getTipesurvey());
                Log.d("LAHANSUB", dataLahanTemporari.getNosegment()+"."+dataLahanTemporari.getSubsegment()+"--"+segAkhir+"."+subAkhir);
            }

            if(index!=listTemporari.size()-1){

                int segAkhir = getMinSegment(session.getTipesurvey(), listTemporari.get(index+1).getNosegment(), listTemporari.get(index+1).getSubsegment());
                int subAkhir = getMinSub(session.getTipesurvey(), listTemporari.get(index+1).getSubsegment());
                addSesudah(session.getTipesurvey(), listTemporari, index);
                dbLahan.saveLahanNormal(dataLahan, dataLahan.getNosegment(), dataLahan.getSubsegment(), segAkhir, subAkhir, session.getTipesurvey());
            }

        }

        setOnsave(asal, "Lahan");

    }

    public void saveBahu(DataBahu dataBahu, String asal){

        dbBahu.setBahu(dataBahu);
        saveTemporari("Bahu", session.getTipesurvey(), dataBahu.getNoprov(), dataBahu.getNoruas(), dataBahu.getNosegment(), dataBahu.getSubSegment(), dataBahu.getPosisi(), "", dataBahu.getNosegment(), dataBahu.getSubSegment());
        ArrayList<DataTemporari> listTemporari = getTerusan("Bahu", session.getTipesurvey(), dataBahu.getNoprov(), dataBahu.getNoruas(), dataBahu.getPosisi(), null);

        if(listTemporari.size()>1){

            int index = getIndex(listTemporari, dataBahu.getNosegment(), dataBahu.getSubSegment());


            if(index!=0){

                int segAkhir = getMinSegment(session.getTipesurvey(), listTemporari.get(index).getNosegment(), listTemporari.get(index).getSubsegment());
                int subAkhir = getMinSub(session.getTipesurvey(), listTemporari.get(index).getSubsegment());
                addSebelum(session.getTipesurvey(), listTemporari, index);
                DataBahu dataBahuTemporari = dbBahu.getBahu(dataBahu.getNoprov(), dataBahu.getNoruas(), listTemporari.get(index-1).getNosegment(),  listTemporari.get(index-1).getSubsegment(), listTemporari.get(index-1).getPosisi());
                dbBahu.saveBahuNormal(dataBahuTemporari, dataBahuTemporari.getNosegment(), dataBahuTemporari.getSubSegment(), segAkhir, subAkhir, session.getTipesurvey());
            }

            if(index!=listTemporari.size()-1){

                int segAkhir = getMinSegment(session.getTipesurvey(), listTemporari.get(index+1).getNosegment(), listTemporari.get(index+1).getSubsegment());
                int subAkhir = getMinSub(session.getTipesurvey(), listTemporari.get(index+1).getSubsegment());
                addSesudah(session.getTipesurvey(), listTemporari, index);
                dbBahu.saveBahuNormal(dataBahu, dataBahu.getNosegment(), dataBahu.getSubSegment(), segAkhir, subAkhir, session.getTipesurvey());
            }

        }

        setOnsave(asal, "Bahu");

    }

    public void saveSaluran(DataSaluran dataSaluran, String asal){

        dbSaluran.setSaluran(dataSaluran);
        saveTemporari("Saluran", session.getTipesurvey(), dataSaluran.getNoprov(), dataSaluran.getNoruas(), dataSaluran.getNosegment(), dataSaluran.getSubsegment(), dataSaluran.getPosisi(), "", dataSaluran.getNosegment(), dataSaluran.getSubsegment());
        ArrayList<DataTemporari> listTemporari = getTerusan("Saluran", session.getTipesurvey(), dataSaluran.getNoprov(), dataSaluran.getNoruas(), dataSaluran.getPosisi(), null);

        if(listTemporari.size()>1){

            int index = getIndex(listTemporari, dataSaluran.getNosegment(), dataSaluran.getSubsegment());


            if(index!=0){

                int segAkhir = getMinSegment(session.getTipesurvey(), listTemporari.get(index).getNosegment(), listTemporari.get(index).getSubsegment());
                int subAkhir = getMinSub(session.getTipesurvey(), listTemporari.get(index).getSubsegment());
                Log.d("SUBSEGKU", segAkhir+"."+subAkhir);
                addSebelum(session.getTipesurvey(), listTemporari, index);
                DataSaluran dataSaluranNormal = dbSaluran.getSaluran(dataSaluran.getNoprov(), dataSaluran.getNoruas(), listTemporari.get(index-1).getNosegment(),  listTemporari.get(index-1).getSubsegment(), listTemporari.get(index-1).getPosisi());
                dbSaluran.saveSaluranNormal(dataSaluranNormal, dataSaluranNormal.getNosegment(), dataSaluranNormal.getSubsegment(), segAkhir, subAkhir, session.getTipesurvey());
            }

            if(index!=listTemporari.size()-1){

                int segAkhir = getMinSegment(session.getTipesurvey(), listTemporari.get(index+1).getNosegment(), listTemporari.get(index+1).getSubsegment());
                int subAkhir = getMinSub(session.getTipesurvey(), listTemporari.get(index+1).getSubsegment());
                addSesudah(session.getTipesurvey(), listTemporari, index);
                dbSaluran.saveSaluranNormal(dataSaluran, dataSaluran.getNosegment(), dataSaluran.getSubsegment(), segAkhir, subAkhir, session.getTipesurvey());
            }

        }

        setOnsave(asal, "Saluran");

    }

    public void saveMedian(DataMedian dataMedian, String asal){

        dbMedian.setMedian(dataMedian);
        saveTemporari("Median", session.getTipesurvey(), dataMedian.getNoprov(), dataMedian.getNoruas(), dataMedian.getNosegment(), dataMedian.getSubsegment(), "", "", dataMedian.getNosegment(), dataMedian.getSubsegment());
        ArrayList<DataTemporari> listTemporari = getTerusan("Median", session.getTipesurvey(), dataMedian.getNoprov(), dataMedian.getNoruas(), null, null);

        if(listTemporari.size()>1){

            int index = getIndex(listTemporari, dataMedian.getNosegment(), dataMedian.getSubsegment());


            if(index!=0){

                int segAkhir = getMinSegment(session.getTipesurvey(), listTemporari.get(index).getNosegment(), listTemporari.get(index).getSubsegment());
                int subAkhir = getMinSub(session.getTipesurvey(), listTemporari.get(index).getSubsegment());
                Log.d("SUBSEGKU", segAkhir+"."+subAkhir);
                addSebelum(session.getTipesurvey(), listTemporari, index);
                DataMedian dataMedianNormal = dbMedian.getMedian(dataMedian.getNoprov(), dataMedian.getNoruas(), listTemporari.get(index-1).getNosegment(),  listTemporari.get(index-1).getSubsegment());
                dbMedian.saveMedianNormal(dataMedianNormal, dataMedianNormal.getNosegment(), dataMedianNormal.getSubsegment(), segAkhir, subAkhir, session.getTipesurvey());
            }

            if(index!=listTemporari.size()-1){

                int segAkhir = getMinSegment(session.getTipesurvey(), listTemporari.get(index+1).getNosegment(), listTemporari.get(index+1).getSubsegment());
                int subAkhir = getMinSub(session.getTipesurvey(), listTemporari.get(index+1).getSubsegment());
                addSesudah(session.getTipesurvey(), listTemporari, index);
                dbMedian.saveMedianNormal(dataMedian, dataMedian.getNosegment(), dataMedian.getSubsegment(), segAkhir, subAkhir, session.getTipesurvey());
            }

        }

        setOnsave(asal, "Median");

    }

    public void saveLaneCepat(DataLane dataLane, final String asal){


        dbLane.setLane(dataLane);
        saveTemporari("Lane", session.getTipesurvey(), dataLane.getNoprov(), dataLane.getNoruas(), dataLane.getNosegment(), dataLane.getSubsegment(), dataLane.getPosisi(), dataLane.getUrut(), dataLane.getNosegment(), dataLane.getSubsegment());
        ArrayList<DataTemporari> listTemporari = getTerusan("Lane", session.getTipesurvey(), dataLane.getNoprov(), dataLane.getNoruas(), dataLane.getPosisi(), null);

        if(listTemporari.size()>1){

            int index = getIndex(listTemporari, dataLane.getNosegment(), dataLane.getSubsegment());

            if(index!=0){

                int segAkhir = getMinSegment(session.getTipesurvey(), listTemporari.get(index).getNosegment(), listTemporari.get(index).getSubsegment());
                int subAkhir = getMinSub(session.getTipesurvey(), listTemporari.get(index).getSubsegment());
                addSebelum(session.getTipesurvey(), listTemporari, index);
                DataLane dataLaneNormal = dbLane.getLane(dataLane.getNoprov(), dataLane.getNoruas(), listTemporari.get(index-1).getNosegment(),  listTemporari.get(index-1).getSubsegment(), dataLane.getPosisi(), dataLane.getUrut());
                dbLane.saveLaneNormal(dataLaneNormal, dataLaneNormal.getNosegment(), dataLaneNormal.getSubsegment(), segAkhir, subAkhir, session.getTipesurvey());

            }

            if(index!=listTemporari.size()-1){

                int segAkhir = getMinSegment(session.getTipesurvey(), listTemporari.get(index+1).getNosegment(), listTemporari.get(index+1).getSubsegment());
                int subAkhir = getMinSub(session.getTipesurvey(), listTemporari.get(index+1).getSubsegment());
                addSesudah(session.getTipesurvey(), listTemporari, index);
                dbLane.saveLaneNormal(dataLane, dataLane.getNosegment(), dataLane.getSubsegment(), segAkhir, subAkhir, session.getTipesurvey());

            }

        }

        setOnsave(asal, "Lane");


    }

    public void saveLane(DataLane dataLane, final String asal){

        dbLane.setLane(dataLane);
        saveTemporari("Lane", session.getTipesurvey(), dataLane.getNoprov(), dataLane.getNoruas(), dataLane.getNosegment(), dataLane.getSubsegment(), dataLane.getPosisi(), dataLane.getUrut(), dataLane.getNosegment(), dataLane.getSubsegment());
        ArrayList<DataTemporari> listTemporari = getTerusan("Lane", session.getTipesurvey(), dataLane.getNoprov(), dataLane.getNoruas(), dataLane.getPosisi(), null);
        List<String> listUpdate = new ArrayList<>();

        if(listTemporari.size()>1){

            int index = getIndex(listTemporari, dataLane.getNosegment(), dataLane.getSubsegment());

            if(index!=0){
                listUpdate.add("Sebelum");
            }

            if(index!=listTemporari.size()-1){
                listUpdate.add("Sesudah");
            }

            loopingLane(listUpdate, listTemporari, session.getTipesurvey(), index, 0, new SendId() {
                @Override

                 public void hapusGambar(int id) {

                    setOnsave(asal, "Lane");

                }
            });


        }else{

            setOnsave(asal, "Lane");

        }

    }

    private void loopingLane(final List<String> listUpdate, final ArrayList<DataTemporari> listTemporari, final String tipeSurvey, final int indexTemporari, final int indexLooping, final SendId sendId){

        TaskSaveLaneTerusan taskSaveLaneTerusan;
        int segAkhir, subAkhir, foto;
        DataLane dataLane;

        Log.d("LOOPINGLANE", listUpdate.get(indexLooping)+" - "+listUpdate.size());

        if(listUpdate.get(indexLooping).equals("Sebelum")){

            segAkhir = getMinSegment(tipeSurvey, listTemporari.get(indexTemporari).getNosegment(), listTemporari.get(indexTemporari).getSubsegment());
            subAkhir = getMinSub(tipeSurvey, listTemporari.get(indexTemporari).getSubsegment());
            dataLane = dbLane.getLane(listTemporari.get(indexTemporari).getNoprov(), listTemporari.get(indexTemporari).getNoruas(), listTemporari.get(indexTemporari-1).getNosegment(),  listTemporari.get(indexTemporari-1).getSubsegment(), listTemporari.get(indexTemporari).getPosisi(), listTemporari.get(indexTemporari).getUrut());


        }else if(listUpdate.get(indexLooping).equals("Sesudah")){

            segAkhir = getMinSegment(tipeSurvey, listTemporari.get(indexTemporari+1).getNosegment(), listTemporari.get(indexTemporari+1).getSubsegment());
            subAkhir = getMinSub(tipeSurvey, listTemporari.get(indexTemporari+1).getSubsegment());
            dataLane = dbLane.getLane(listTemporari.get(indexTemporari).getNoprov(), listTemporari.get(indexTemporari).getNoruas(), listTemporari.get(indexTemporari).getNosegment(),  listTemporari.get(indexTemporari).getSubsegment(), listTemporari.get(indexTemporari).getPosisi(), listTemporari.get(indexTemporari).getUrut());

        }else{

            int segmentAwal = tambahSegment(tipeSurvey, listTemporari.get(indexTemporari).getNosegment(), listTemporari.get(indexTemporari).getSubsegment());
            int subAwal = tambahSubSegment(tipeSurvey, listTemporari.get(indexTemporari).getSubsegment());

            if(indexTemporari == listTemporari.size()-1){

                segAkhir = indexSegmentMaks(tipeSurvey);
                subAkhir = indexSubMaks(tipeSurvey, segAkhir);

            }else {
                segAkhir = getMinSegment(tipeSurvey, listTemporari.get(indexTemporari + 1).getNosegment(), listTemporari.get(indexTemporari + 1).getSubsegment());
                subAkhir = getMinSub(tipeSurvey, listTemporari.get(indexTemporari + 1).getSubsegment());
            }

            dataLane = dbLane.getLane(listTemporari.get(indexTemporari).getNoprov(), listTemporari.get(indexTemporari).getNoruas(), segmentAwal,  subAwal, listTemporari.get(indexTemporari).getPosisi(), listTemporari.get(indexTemporari).getUrut());

        }

        foto = getFotoTipe("Lane",dataLane.getNoprov(), dataLane.getNoruas(), dataLane.getNosegment(), dataLane.getSubsegment(), dataLane.getPosisi(), dataLane.getUrut());
        taskSaveLaneTerusan = new TaskSaveLaneTerusan(context, dataLane, tipeSurvey, segAkhir, subAkhir, String.valueOf(foto), new SendDialog() {
            @Override
            public void sendDialog(ProgressDialog dialog) {
                dialog.dismiss();

                if(indexLooping<listUpdate.size()-1){
                    loopingLane(listUpdate, listTemporari, tipeSurvey, indexTemporari, indexLooping+1, sendId);
                }else{
                    sendId.hapusGambar(1);
                }
            }
        });
        taskSaveLaneTerusan.execute("a","a","a");

    }

    public void saveSegment(final DataSegmen dataSegmen, final String asal){

        List<String> list = new ArrayList<>();

        DataSegmen dataSegmenTempo = dbSegmen.getSegmen(dataSegmen.getNoprov(), dataSegmen.getNoruas(), dataSegmen.getNosegment(), dataSegmen.getSubsegment());

        cekPerubahan(dataSegmenTempo.getSegmentl1(), dataSegmen.getSegmentl1(), dataSegmen.getNosegment(), dataSegmen.getSubsegment(), "kiri", "L1", session.getTipesurvey());
        cekPerubahan(dataSegmenTempo.getSegmentl2(), dataSegmen.getSegmentl2(), dataSegmen.getNosegment(), dataSegmen.getSubsegment(), "kiri", "L2", session.getTipesurvey());
        cekPerubahan(dataSegmenTempo.getSegmentl3(), dataSegmen.getSegmentl3(), dataSegmen.getNosegment(), dataSegmen.getSubsegment(), "kiri", "L3", session.getTipesurvey());
        cekPerubahan(dataSegmenTempo.getSegmentl4(), dataSegmen.getSegmentl4(), dataSegmen.getNosegment(), dataSegmen.getSubsegment(), "kiri", "L4", session.getTipesurvey());
        cekPerubahan(dataSegmenTempo.getSegmentl5(), dataSegmen.getSegmentl5(), dataSegmen.getNosegment(), dataSegmen.getSubsegment(), "kiri", "L5", session.getTipesurvey());
        cekPerubahan(dataSegmenTempo.getSegmentl6(), dataSegmen.getSegmentl6(), dataSegmen.getNosegment(), dataSegmen.getSubsegment(), "kiri", "L6", session.getTipesurvey());
        cekPerubahan(dataSegmenTempo.getSegmentl7(), dataSegmen.getSegmentl7(), dataSegmen.getNosegment(), dataSegmen.getSubsegment(), "kiri", "L7", session.getTipesurvey());
        cekPerubahan(dataSegmenTempo.getSegmentl8(), dataSegmen.getSegmentl8(), dataSegmen.getNosegment(), dataSegmen.getSubsegment(), "kiri", "L8", session.getTipesurvey());
        cekPerubahan(dataSegmenTempo.getSegmentl9(), dataSegmen.getSegmentl9(), dataSegmen.getNosegment(), dataSegmen.getSubsegment(), "kiri", "L9", session.getTipesurvey());
        cekPerubahan(dataSegmenTempo.getSegmentl10(), dataSegmen.getSegmentl10(), dataSegmen.getNosegment(), dataSegmen.getSubsegment(), "kiri", "L10", session.getTipesurvey());

        cekPerubahan(dataSegmenTempo.getSegmentr1(), dataSegmen.getSegmentr1(), dataSegmen.getNosegment(), dataSegmen.getSubsegment(), "kanan", "R1", session.getTipesurvey());
        cekPerubahan(dataSegmenTempo.getSegmentr2(), dataSegmen.getSegmentr2(), dataSegmen.getNosegment(), dataSegmen.getSubsegment(), "kanan", "R2", session.getTipesurvey());
        cekPerubahan(dataSegmenTempo.getSegmentr3(), dataSegmen.getSegmentr3(), dataSegmen.getNosegment(), dataSegmen.getSubsegment(), "kanan", "R3", session.getTipesurvey());
        cekPerubahan(dataSegmenTempo.getSegmentr4(), dataSegmen.getSegmentr4(), dataSegmen.getNosegment(), dataSegmen.getSubsegment(), "kanan", "R4", session.getTipesurvey());
        cekPerubahan(dataSegmenTempo.getSegmentr5(), dataSegmen.getSegmentr5(), dataSegmen.getNosegment(), dataSegmen.getSubsegment(), "kanan", "R5", session.getTipesurvey());
        cekPerubahan(dataSegmenTempo.getSegmentr6(), dataSegmen.getSegmentr6(), dataSegmen.getNosegment(), dataSegmen.getSubsegment(), "kanan", "R6", session.getTipesurvey());
        cekPerubahan(dataSegmenTempo.getSegmentr7(), dataSegmen.getSegmentr7(), dataSegmen.getNosegment(), dataSegmen.getSubsegment(), "kanan", "R7", session.getTipesurvey());
        cekPerubahan(dataSegmenTempo.getSegmentr8(), dataSegmen.getSegmentr8(), dataSegmen.getNosegment(), dataSegmen.getSubsegment(), "kanan", "R8", session.getTipesurvey());
        cekPerubahan(dataSegmenTempo.getSegmentr9(), dataSegmen.getSegmentr9(), dataSegmen.getNosegment(), dataSegmen.getSubsegment(), "kanan", "R9", session.getTipesurvey());
        cekPerubahan(dataSegmenTempo.getSegmentr10(), dataSegmen.getSegmentr10(), dataSegmen.getNosegment(), dataSegmen.getSubsegment(), "kanan", "R10", session.getTipesurvey());

        dbSegmen.postSegment(dataSegmen);
        saveTemporari("Segment", session.getTipesurvey(), dataSegmen.getNoprov(), dataSegmen.getNoruas(), dataSegmen.getNosegment(), dataSegmen.getSubsegment(), "Update", "", dataSegmen.getNosegment(), dataSegmen.getSubsegment());
        ArrayList<DataTemporari> listTemporari = getTerusan("Segment", session.getTipesurvey(), dataSegmen.getNoprov(), dataSegmen.getNoruas(), "Update", null);

        if(listTemporari.size()>1){

            int index = getIndex(listTemporari, dataSegmen.getNosegment(), dataSegmen.getSubsegment());

            if(index!=0) {
                addSebelum(session.getTipesurvey(), listTemporari, index);
                list.add("Sebelum");
            }

            if(index!=listTemporari.size()-1) {
                addSesudah(session.getTipesurvey(), listTemporari, index);
                list.add("Sesudah");
            }

            loopingSegment(list, listTemporari, session.getTipesurvey(), index, 0, new SendId() {
                @Override
                public void hapusGambar(int id) {
                    setOnsave(asal, "Segment");
                }
            });


            /*if(index!=0){

                final int segAkhir = getMinSegment(session.getTipesurvey(), listTemporari.get(index).getNosegment(), listTemporari.get(index).getSubsegment());
                final int subAkhir = getMinSub(session.getTipesurvey(), listTemporari.get(index).getSubsegment());
                //Log.d("SUBSEGKU", segAkhir+"."+subAkhir);
                addSebelum(session.getTipesurvey(), listTemporari, index);
                final DataSegmen dataSegmenNormal = dbSegmen.getSegmen(dataSegmen.getNoprov(), dataSegmen.getNoruas(), listTemporari.get(index-1).getNosegment(),  listTemporari.get(index-1).getSubsegment());

                TaskSaveSegmentTerusan taskSaveSegmentTerusan = new TaskSaveSegmentTerusan(context, dataSegmenNormal, session.getTipesurvey(), segAkhir, subAkhir, new SendDialog() {
                    @Override
                    public void sendDialog(ProgressDialog dialog) {
                        dialog.dismiss();
                        dbSegmen.saveSegmentNormal(dataSegmenNormal, dataSegmenNormal.getNosegment(), dataSegmenNormal.getSubsegment(), segAkhir, subAkhir, session.getTipesurvey());
                    }
                });
                taskSaveSegmentTerusan.execute("a", "a", "a");
            }

            if(index!=listTemporari.size()-1){

                final int segAkhir = getMinSegment(session.getTipesurvey(), listTemporari.get(index+1).getNosegment(), listTemporari.get(index+1).getSubsegment());
                final int subAkhir = getMinSub(session.getTipesurvey(), listTemporari.get(index+1).getSubsegment());
                addSesudah(session.getTipesurvey(), listTemporari, index);
                TaskSaveSegmentTerusan taskSaveSegmentTerusan = new TaskSaveSegmentTerusan(context, dataSegmen, session.getTipesurvey(), segAkhir, subAkhir, new SendDialog() {
                    @Override
                    public void sendDialog(ProgressDialog dialog) {

                        dialog.dismiss();
                        dbSegmen.saveSegmentNormal(dataSegmen, dataSegmen.getNosegment(), dataSegmen.getSubsegment(), segAkhir, subAkhir, session.getTipesurvey());

                    }
                });


                taskSaveSegmentTerusan.execute("a", "a", "a");
            }*/

        }else{
            setOnsave(asal, "Segment");
        }

    }

    private void loopingSegment(final List<String> listUpdate, final ArrayList<DataTemporari> listTemporari, final String tipeSurvey, final int indexTemporari, final int indexLooping, final SendId sendId){

        TaskSaveSegmentTerusan taskSaveSegmentTerusan;
        final int segAkhir, subAkhir;
        final DataSegmen dataSegmen;

        //Log.d("LOOPINGLANE", listUpdate.get(indexLooping)+" - "+listUpdate.size());

        if(listUpdate.get(indexLooping).equals("Sebelum")){

            segAkhir = getMinSegment(tipeSurvey, listTemporari.get(indexTemporari).getNosegment(), listTemporari.get(indexTemporari).getSubsegment());
            subAkhir = getMinSub(tipeSurvey, listTemporari.get(indexTemporari).getSubsegment());
            dataSegmen = dbSegmen.getSegmen(listTemporari.get(indexTemporari).getNoprov(), listTemporari.get(indexTemporari).getNoruas(), listTemporari.get(indexTemporari-1).getNosegment(),  listTemporari.get(indexTemporari-1).getSubsegment());

        }else{

            segAkhir = getMinSegment(tipeSurvey, listTemporari.get(indexTemporari+1).getNosegment(), listTemporari.get(indexTemporari+1).getSubsegment());
            subAkhir = getMinSub(tipeSurvey, listTemporari.get(indexTemporari+1).getSubsegment());
            dataSegmen = dbSegmen.getSegmen(listTemporari.get(indexTemporari).getNoprov(), listTemporari.get(indexTemporari).getNoruas(), listTemporari.get(indexTemporari).getNosegment(),  listTemporari.get(indexTemporari).getSubsegment());

        }

        taskSaveSegmentTerusan = new TaskSaveSegmentTerusan(context, dataSegmen, tipeSurvey, segAkhir, subAkhir, new SendDialog() {
            @Override
            public void sendDialog(ProgressDialog dialog) {

                dialog.dismiss();
                dbSegmen.saveSegmentNormal(dataSegmen, dataSegmen.getNosegment(), dataSegmen.getSubsegment(), segAkhir, subAkhir, session.getTipesurvey());

                if(indexLooping<listUpdate.size()-1){
                    loopingSegment(listUpdate, listTemporari, tipeSurvey, indexTemporari, indexLooping+1, sendId);
                }else{
                    sendId.hapusGambar(1);
                }
            }
        });
        taskSaveSegmentTerusan.execute("a","a","a");

    }

    public void saveLahanEnd(String tipeSurvey, String provinsi, String ruas, String posisi){

        ArrayList<DataTemporari> listTemporari = getTerusan("Lahan", tipeSurvey, provinsi, ruas, posisi, null);

        if(listTemporari.size()>0){

            int index = listTemporari.size()-1;
            DataTemporari dataTemporari = listTemporari.get(index);

            if(cekMaksimalIndex(tipeSurvey, dataTemporari.getNosegment(), dataTemporari.getSubsegment())){

                DataLahan dataLahanNormal = dbLahan.getLahan(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment(), dataTemporari.getSubsegment(), dataTemporari.getPosisi());
                dbLahan.saveLahanNormal(dataLahanNormal, dataTemporari.getNosegment(), dataTemporari.getSubsegment(), maksimalSegment, maksimalSub, tipeSurvey);
                saveTemporari("Lahan", tipeSurvey, provinsi, ruas, dataTemporari.getNosegment(), dataTemporari.getSubsegment(), dataTemporari.getPosisi(), "", maksimalSegment, maksimalSub);

            }

        }

    }

    public void saveBahuEnd(String tipeSurvey, String provinsi, String ruas, String posisi){

        ArrayList<DataTemporari> listTemporari = getTerusan("Bahu", tipeSurvey, provinsi, ruas, posisi, null);

        if(listTemporari.size()>0){

            int index = listTemporari.size()-1;
            DataTemporari dataTemporari = listTemporari.get(index);

            if(cekMaksimalIndex(tipeSurvey, dataTemporari.getNosegment(), dataTemporari.getSubsegment())){

                DataBahu dataBahuNormal = dbBahu.getBahu(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment(), dataTemporari.getSubsegment(), dataTemporari.getPosisi());
                dbBahu.saveBahuNormal(dataBahuNormal, dataTemporari.getNosegment(), dataTemporari.getSubsegment(), maksimalSegment, maksimalSub, tipeSurvey);
                saveTemporari("Bahu", tipeSurvey, provinsi, ruas, dataTemporari.getNosegment(), dataTemporari.getSubsegment(), dataTemporari.getPosisi(), "", maksimalSegment, maksimalSub);

            }

        }

    }

    public void saveSaluranEnd(String tipeSurvey, String provinsi, String ruas, String posisi){

        ArrayList<DataTemporari> listTemporari = getTerusan("Saluran", tipeSurvey, provinsi, ruas, posisi, null);

        if(listTemporari.size()>0){

            int index = listTemporari.size()-1;
            DataTemporari dataTemporari = listTemporari.get(index);

            if(cekMaksimalIndex(tipeSurvey, dataTemporari.getNosegment(), dataTemporari.getSubsegment())){

                DataSaluran dataSaluranNormal = dbSaluran.getSaluran(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment(), dataTemporari.getSubsegment(), dataTemporari.getPosisi());
                dbSaluran.saveSaluranNormal(dataSaluranNormal, dataTemporari.getNosegment(), dataTemporari.getSubsegment(), maksimalSegment, maksimalSub, tipeSurvey);
                saveTemporari("Saluran", tipeSurvey, provinsi, ruas, dataTemporari.getNosegment(), dataTemporari.getSubsegment(), dataTemporari.getPosisi(), "", maksimalSegment, maksimalSub);

            }

        }

    }

    public void saveMedianEnd(String tipeSurvey, String provinsi, String ruas){

        ArrayList<DataTemporari> listTemporari = getTerusan("Median", tipeSurvey, provinsi, ruas, null, null);

        if(listTemporari.size()>0){

            int index = listTemporari.size()-1;
            DataTemporari dataTemporari = listTemporari.get(index);

            if(cekMaksimalIndex(tipeSurvey, dataTemporari.getNosegment(), dataTemporari.getSubsegment())){

                DataMedian dataMedianNormal = dbMedian.getMedian(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment(), dataTemporari.getSubsegment());
                dbMedian.saveMedianNormal(dataMedianNormal, dataTemporari.getNosegment(), dataTemporari.getSubsegment(), maksimalSegment, maksimalSub, tipeSurvey);
                saveTemporari("Median", tipeSurvey, provinsi, ruas, dataTemporari.getNosegment(), dataTemporari.getSubsegment(), "", "", maksimalSegment, maksimalSub);

            }

        }

    }

    public void savePerkerasanEnd(String tipeSurvey, String provinsi, String ruas, String posisi){

        ArrayList<DataTemporari> listTemporari = getTerusan("Perkerasan", tipeSurvey, provinsi, ruas, posisi, null);

        if(listTemporari.size()>0){

            int index = listTemporari.size()-1;
            DataTemporari dataTemporari = listTemporari.get(index);

            if(cekMaksimalIndex(tipeSurvey, dataTemporari.getNosegment(), dataTemporari.getSubsegment())){
                DbMinlet dbMinlet = new DbMinlet(context);
                DataInletMedian dataMinletNormal = dbMinlet.getMinlet(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment(), dataTemporari.getSubsegment(), dataTemporari.getPosisi());
                dbMinlet.saveMinletNormal(dataMinletNormal, dataTemporari.getNosegment(), dataTemporari.getSubsegment(), maksimalSegment, maksimalSub, tipeSurvey);
                saveTemporari("Perkerasan", tipeSurvey, provinsi, ruas, dataTemporari.getNosegment(), dataTemporari.getSubsegment(), dataTemporari.getPosisi(), "", maksimalSegment, maksimalSub);

            }

        }

    }

    public void saveSPDEnd(String tipeSurvey, String provinsi, String ruas, String posisi){

        ArrayList<DataTemporari> listTemporari = getTerusan("SPD", tipeSurvey, provinsi, ruas, posisi, null);

        if(listTemporari.size()>0){

            int index = listTemporari.size()-1;
            DataTemporari dataTemporari = listTemporari.get(index);

            if(cekMaksimalIndex(tipeSurvey, dataTemporari.getNosegment(), dataTemporari.getSubsegment())){
                DbSpd dbSpd = new DbSpd(context);
                DataSpDalam dataSpDalamNormal = dbSpd.getData(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment(), dataTemporari.getSubsegment(), dataTemporari.getPosisi());
                dbSpd.saveNormal(dataSpDalamNormal, dataTemporari.getNosegment(), dataTemporari.getSubsegment(), maksimalSegment, maksimalSub, tipeSurvey);
                saveTemporari("SPD", tipeSurvey, provinsi, ruas, dataTemporari.getNosegment(), dataTemporari.getSubsegment(), dataTemporari.getPosisi(), "", maksimalSegment, maksimalSub);

            }

        }

    }

    public void saveSPLEnd(String tipeSurvey, String provinsi, String ruas, String posisi){

        ArrayList<DataTemporari> listTemporari = getTerusan("SPL", tipeSurvey, provinsi, ruas, posisi, null);

        if(listTemporari.size()>0){

            int index = listTemporari.size()-1;
            DataTemporari dataTemporari = listTemporari.get(index);

            if(cekMaksimalIndex(tipeSurvey, dataTemporari.getNosegment(), dataTemporari.getSubsegment())){
                DbSpl dbSpl = new DbSpl(context);
                DataSpLuar dataSpLuarNormal = dbSpl.getData(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment(), dataTemporari.getSubsegment(), dataTemporari.getPosisi());
                dbSpl.saveNormal(dataSpLuarNormal, dataTemporari.getNosegment(), dataTemporari.getSubsegment(), maksimalSegment, maksimalSub, tipeSurvey);
                saveTemporari("SPL", tipeSurvey, provinsi, ruas, dataTemporari.getNosegment(), dataTemporari.getSubsegment(), dataTemporari.getPosisi(), "", maksimalSegment, maksimalSub);

            }

        }

    }

    /*public void saveLaneEnd(String tipeSurvey, String provinsi, String ruas, String posisi, String lajur){

        ArrayList<DataTemporari> listTemporari = getTerusan("Lane", tipeSurvey, provinsi, ruas, posisi, lajur);

        if(listTemporari.size()>0){

            int index = listTemporari.size()-1;
            DataTemporari dataTemporari = listTemporari.get(index);

            if(cekMaksimalIndex(tipeSurvey, dataTemporari.getNosegment(), dataTemporari.getSubsegment())){

                DataLane dataLaneNormal = dbLane.getLane(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment(), dataTemporari.getSubsegment(), dataTemporari.getPosisi(), dataTemporari.getUrut());
                //saveTemporari("Lane", tipeSurvey, provinsi, ruas, dataTemporari.getNosegment(), dataTemporari.getSubsegment(), dataTemporari.getPosisi(), dataTemporari.getUrut(), maksimalSegment, maksimalSub);
                //TaskSaveLaneTerusan taskSaveLaneTerusan = new TaskSaveLaneTerusan(context, dataLaneNormal, session.getTipesurvey(), maksimalSegment, maksimalSub, dataTemporari.getFoto());
                //taskSaveLaneTerusan.execute("a","a","a");

            }

        }

    }*/

    public void saveLaneEnd(String tipeSurvey, String provinsi, String ruas, String posisi) {

        /*String[] arrayLane = context.getResources().getStringArray(R.array.arrayLane);
        loopingTaskLane(arrayLane, 0, tipeSurvey, provinsi, ruas, new SendId() {
            @Override
            public void hapusGambar(int id) {
                sendId.hapusGambar(1);
            }
        });*/

        String posisiLane;

        if(posisi.substring(0,1).equals("L")){
            posisiLane = "kiri";
        }else{
            posisiLane = "kanan";
        }

        ArrayList<DataTemporari> listTemporari = getTerusan("Lane", tipeSurvey, provinsi, ruas, posisiLane, posisi);

        if(listTemporari.size()>0){

            int index = listTemporari.size()-1;
            DataTemporari dataTemporari = listTemporari.get(index);
            int segmentLane = dbLane.getMaksSegmentLajur(tipeSurvey, dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getPosisi(), dataTemporari.getUrut());
            int subsegmentLane = dbLane.getMaksSubsegLajur(tipeSurvey, dataTemporari.getNoprov(), dataTemporari.getNoruas(), segmentLane, dataTemporari.getPosisi(), dataTemporari.getUrut());

            if(cekMaksimalIndexLane(tipeSurvey, dataTemporari.getNosegment(), dataTemporari.getSubsegment(), segmentLane, subsegmentLane)){

                DataLane dataLaneNormal = dbLane.getLane(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment(), dataTemporari.getSubsegment(), dataTemporari.getPosisi(), dataTemporari.getUrut());
                dbLane.saveLaneNormal(dataLaneNormal, dataTemporari.getNosegment(), dataTemporari.getSubsegment(), segmentLane, subsegmentLane, tipeSurvey);
                saveTemporari("Lane", tipeSurvey, provinsi, ruas, dataTemporari.getNosegment(), dataTemporari.getSubsegment(), dataTemporari.getPosisi(), dataTemporari.getUrut(), segmentLane, subsegmentLane);

            }

        }
    }

    private void loopingTaskLane(final String[] arrayLane, final int indexLajur, final String tipeSurvey, final String provinsi, final String ruas, final SendId sendId){

        String lajur = arrayLane[indexLajur];
        String posisi;
        if(lajur.substring(0,1).equals("L")){
            posisi = "kiri";
        }else{
            posisi = "kanan";
        }

        ArrayList<DataTemporari> listTemporari = getTerusan("Lane", tipeSurvey, provinsi, ruas, posisi, lajur);

        if(listTemporari.size()>0){

            int index = listTemporari.size()-1;
            DataTemporari dataTemporari = listTemporari.get(index);

            if(cekMaksimalIndex(tipeSurvey, dataTemporari.getNosegment(), dataTemporari.getSubsegment())){

                DataLane dataLaneNormal = dbLane.getLane(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment(), dataTemporari.getSubsegment(), dataTemporari.getPosisi(), dataTemporari.getUrut());
                TaskSaveLaneTerusan taskSaveLaneTerusan = new TaskSaveLaneTerusan(context, dataLaneNormal, session.getTipesurvey(), maksimalSegment, maksimalSub, dataTemporari.getFoto(), new SendDialog() {
                    @Override
                    public void sendDialog(ProgressDialog dialog) {
                        dialog.dismiss();
                        if(indexLajur<arrayLane.length-1) {
                            loopingTaskLane(arrayLane, indexLajur + 1, tipeSurvey, provinsi, ruas, sendId);
                        }else{
                            sendId.hapusGambar(1);
                        }
                    }
                });
                taskSaveLaneTerusan.execute("a","a","a");

            }

        }else{
            if(indexLajur<arrayLane.length-1) {
                loopingTaskLane(arrayLane, indexLajur + 1, tipeSurvey, provinsi, ruas, sendId);
            }else{
                sendId.hapusGambar(1);
            }
        }

    }

    public void saveSegmentEnd(final String tipeSurvey, String provinsi, String ruas, final SendId sendId){

        ArrayList<DataTemporari> listTemporari = getTerusan("Segment", tipeSurvey, provinsi, ruas, "Update", null);

        if(listTemporari.size()>0){

            int index = listTemporari.size()-1;
            final DataTemporari dataTemporari = listTemporari.get(index);

            if(cekMaksimalIndex(tipeSurvey, dataTemporari.getNosegment(), dataTemporari.getSubsegment())){

                final DataSegmen dataSegmenNormal = dbSegmen.getSegmen(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment(), dataTemporari.getSubsegment());

                saveTemporari("Segment", tipeSurvey, provinsi, ruas, dataTemporari.getNosegment(), dataTemporari.getSubsegment(), "Update", "", maksimalSegment, maksimalSub);
                TaskSaveSegmentTerusan taskSaveSegmentTerusan = new TaskSaveSegmentTerusan(context, dataSegmenNormal, session.getTipesurvey(), maksimalSegment, maksimalSub, new SendDialog() {
                    @Override
                    public void sendDialog(ProgressDialog dialog) {
                        dialog.dismiss();
                        dbSegmen.saveSegmentNormal(dataSegmenNormal, dataTemporari.getNosegment(), dataTemporari.getSubsegment(), maksimalSegment, maksimalSub, tipeSurvey);
                        sendId.hapusGambar(1);

                    }
                });


                taskSaveSegmentTerusan.execute("a", "a", "a");

            }else{
                sendId.hapusGambar(1);
            }

        }else{
            sendId.hapusGambar(1);
        }


    }


    public void saveLahanDetail(DataLahan dataLahan, String asal){

        String tipeSurvey = dbTemporari.cekSurveyTemporariTipe(dataLahan.getNoprov(), dataLahan.getNoruas(), "Lahan", dataLahan.getPosisi(), "");

        if(tipeSurvey.equals("Detail") || asal.equals("Tabel")){

            dbLahan.setLahan(dataLahan);
            saveTemporari("Lahan", tipeSurvey, dataLahan.getNoprov(), dataLahan.getNoruas(), dataLahan.getNosegment(), dataLahan.getSubsegment(), dataLahan.getPosisi(), "", dataLahan.getNosegment(), dataLahan.getSubsegment());


        }

        ArrayList<DataTemporari> listTemporari = getTerusan("Lahan", tipeSurvey, dataLahan.getNoprov(), dataLahan.getNoruas(), dataLahan.getPosisi(), null);
        int index = getIndex(listTemporari, dataLahan.getNosegment(), dataLahan.getSubsegment());

        if(asal.equals("Tabel")){

            if (index > 0) {
                addSebelum(tipeSurvey, listTemporari, index);
            }

            if (cekMaksimalDetail(tipeSurvey, dataLahan.getNosegment(), dataLahan.getSubsegment())) {

                addSesudahDetail("Lahan", tipeSurvey, listTemporari, index);

            }

        }else{

            dbLahan.saveLahanNormal(dataLahan, listTemporari.get(index).getNosegment(), listTemporari.get(index).getSubsegment(), listTemporari.get(index).getSegmentakhir(), listTemporari.get(index).getSubsegmentakhir(), tipeSurvey);
            saveTemporari("Lahan", tipeSurvey, dataLahan.getNoprov(), dataLahan.getNoruas(), dataLahan.getNosegment(), dataLahan.getSubsegment(), dataLahan.getPosisi(), "", listTemporari.get(index).getSegmentakhir(), listTemporari.get(index).getSubsegmentakhir());
        }


        setOnsave(asal, "Lahan");

    }

    public void saveBahuDetail(DataBahu dataBahu, String asal){

        String tipeSurvey = dbTemporari.cekSurveyTemporariTipe(dataBahu.getNoprov(), dataBahu.getNoruas(), "Bahu", dataBahu.getPosisi(), "");

        if(tipeSurvey.equals("Detail") || asal.equals("Tabel")){

            dbBahu.setBahu(dataBahu);
            saveTemporari("Bahu", tipeSurvey, dataBahu.getNoprov(), dataBahu.getNoruas(), dataBahu.getNosegment(), dataBahu.getSubSegment(), dataBahu.getPosisi(), "", dataBahu.getNosegment(), dataBahu.getSubSegment());


        }

        ArrayList<DataTemporari> listTemporari = getTerusan("Bahu", tipeSurvey, dataBahu.getNoprov(), dataBahu.getNoruas(), dataBahu.getPosisi(), null);
        int index = getIndex(listTemporari, dataBahu.getNosegment(), dataBahu.getSubSegment());

        if(asal.equals("Tabel")){

            if (index > 0) {
                addSebelum(tipeSurvey, listTemporari, index);
            }

            if (cekMaksimalDetail(tipeSurvey, dataBahu.getNosegment(), dataBahu.getSubSegment())) {

                addSesudahDetail("Bahu", tipeSurvey, listTemporari, index);

            }

        }else{

            dbBahu.saveBahuNormal(dataBahu, listTemporari.get(index).getNosegment(), listTemporari.get(index).getSubsegment(), listTemporari.get(index).getSegmentakhir(), listTemporari.get(index).getSubsegmentakhir(), tipeSurvey);
            saveTemporari("Bahu", tipeSurvey, dataBahu.getNoprov(), dataBahu.getNoruas(), dataBahu.getNosegment(), dataBahu.getSubSegment(), dataBahu.getPosisi(), "", listTemporari.get(index).getSegmentakhir(), listTemporari.get(index).getSubsegmentakhir());
        }


        setOnsave(asal, "Bahu");

    }

    public void saveSaluranDetail(DataSaluran dataSaluran, String asal){

        String tipeSurvey = dbTemporari.cekSurveyTemporariTipe(dataSaluran.getNoprov(), dataSaluran.getNoruas(), "Saluran", dataSaluran.getPosisi(), "");

        if(tipeSurvey.equals("Detail") || asal.equals("Tabel")){

            dbSaluran.setSaluran(dataSaluran);
            saveTemporari("Saluran", tipeSurvey, dataSaluran.getNoprov(), dataSaluran.getNoruas(), dataSaluran.getNosegment(), dataSaluran.getSubsegment(), dataSaluran.getPosisi(), "", dataSaluran.getNosegment(), dataSaluran.getSubsegment());


        }

        ArrayList<DataTemporari> listTemporari = getTerusan("Saluran", tipeSurvey, dataSaluran.getNoprov(), dataSaluran.getNoruas(), dataSaluran.getPosisi(), null);
        int index = getIndex(listTemporari, dataSaluran.getNosegment(), dataSaluran.getSubsegment());

        if(asal.equals("Tabel")){

            if (index > 0) {
                addSebelum(tipeSurvey, listTemporari, index);
            }

            if (cekMaksimalDetail(tipeSurvey, dataSaluran.getNosegment(), dataSaluran.getSubsegment())) {

                addSesudahDetail("Saluran", tipeSurvey, listTemporari, index);

            }

        }else{

            dbSaluran.saveSaluranNormal(dataSaluran, listTemporari.get(index).getNosegment(), listTemporari.get(index).getSubsegment(), listTemporari.get(index).getSegmentakhir(), listTemporari.get(index).getSubsegmentakhir(), tipeSurvey);
            saveTemporari("Saluran", tipeSurvey, dataSaluran.getNoprov(), dataSaluran.getNoruas(), dataSaluran.getNosegment(), dataSaluran.getSubsegment(), dataSaluran.getPosisi(), "", listTemporari.get(index).getSegmentakhir(), listTemporari.get(index).getSubsegmentakhir());
        }


        setOnsave(asal, "Saluran");

    }


    public void saveMedianDetail(DataMedian dataMedian, String asal){

        String tipeSurvey = dbTemporari.cekSurveyTemporariTipe(dataMedian.getNoprov(), dataMedian.getNoruas(), "Median", "", "");

        if(tipeSurvey.equals("Detail") || asal.equals("Tabel")){

            dbMedian.setMedian(dataMedian);
            saveTemporari("Median", tipeSurvey, dataMedian.getNoprov(), dataMedian.getNoruas(), dataMedian.getNosegment(), dataMedian.getSubsegment(), "", "", dataMedian.getNosegment(), dataMedian.getSubsegment());


        }

        ArrayList<DataTemporari> listTemporari = getTerusan("Median", tipeSurvey, dataMedian.getNoprov(), dataMedian.getNoruas(), null, null);
        int index = getIndex(listTemporari, dataMedian.getNosegment(), dataMedian.getSubsegment());

        if(asal.equals("Tabel")){

            if (index > 0) {
                addSebelum(tipeSurvey, listTemporari, index);
            }

            if (cekMaksimalDetail(tipeSurvey, dataMedian.getNosegment(), dataMedian.getSubsegment())) {

                addSesudahDetail("Median", tipeSurvey, listTemporari, index);

            }

        }else{

            dbMedian.saveMedianNormal(dataMedian, listTemporari.get(index).getNosegment(), listTemporari.get(index).getSubsegment(), listTemporari.get(index).getSegmentakhir(), listTemporari.get(index).getSubsegmentakhir(), tipeSurvey);
            saveTemporari("Median", tipeSurvey, dataMedian.getNoprov(), dataMedian.getNoruas(), dataMedian.getNosegment(), dataMedian.getSubsegment(), "", "", listTemporari.get(index).getSegmentakhir(), listTemporari.get(index).getSubsegmentakhir());
        }


        setOnsave(asal, "Median");

    }


    public void saveLaneDetail(DataLane dataLane, final String asal){

        String tipeSurvey = dbTemporari.cekSurveyTemporariTipe(dataLane.getNoprov(), dataLane.getNoruas(), "Lane", dataLane.getPosisi(), dataLane.getUrut());

        if(tipeSurvey.equals("Detail") || asal.equals("Tabel")){

            dbLane.setLane(dataLane);
            saveTemporari("Lane", tipeSurvey, dataLane.getNoprov(), dataLane.getNoruas(), dataLane.getNosegment(), dataLane.getSubsegment(), dataLane.getPosisi(), dataLane.getUrut(), dataLane.getNosegment(), dataLane.getSubsegment());


        }

        ArrayList<DataTemporari> listTemporari = getTerusan("Lane", tipeSurvey, dataLane.getNoprov(), dataLane.getNoruas(), dataLane.getPosisi(), dataLane.getUrut());
        int index = getIndex(listTemporari, dataLane.getNosegment(), dataLane.getSubsegment());

        if(asal.equals("Tabel")){

            if (index > 0) {

                addSebelum(tipeSurvey, listTemporari, index);
            }

            if (cekMaksimalDetail(tipeSurvey, dataLane.getNosegment(), dataLane.getSubsegment())) {

                addSesudahDetail("Lane", tipeSurvey, listTemporari, index);

            }


        }else{

            dbLane.saveLaneNormal(dataLane, listTemporari.get(index).getNosegment(), listTemporari.get(index).getSubsegment(), listTemporari.get(index).getSegmentakhir(), listTemporari.get(index).getSubsegmentakhir(), tipeSurvey);
            saveTemporari("Lane", tipeSurvey, dataLane.getNoprov(), dataLane.getNoruas(), dataLane.getNosegment(), dataLane.getSubsegment(), dataLane.getPosisi(), dataLane.getUrut(), listTemporari.get(index).getSegmentakhir(), listTemporari.get(index).getSubsegmentakhir());

        }


        setOnsave(asal, "Lane");


    }

    public void saveSegmenDetail(final DataSegmen dataSegmen, final String asal){

        final String tipeSurvey = dbTemporari.cekSurveyTemporariTipe(dataSegmen.getNoprov(), dataSegmen.getNoruas(), "Segment", "Update", "");

        DataSegmen dataSegmenTempo = dbSegmen.getSegmen(dataSegmen.getNoprov(), dataSegmen.getNoruas(), dataSegmen.getNosegment(), dataSegmen.getSubsegment());

        cekPerubahan(dataSegmenTempo.getSegmentl1(), dataSegmen.getSegmentl1(), dataSegmen.getNosegment(), dataSegmen.getSubsegment(), "kiri", "L1", tipeSurvey);
        cekPerubahan(dataSegmenTempo.getSegmentl2(), dataSegmen.getSegmentl2(), dataSegmen.getNosegment(), dataSegmen.getSubsegment(), "kiri", "L2", tipeSurvey);
        cekPerubahan(dataSegmenTempo.getSegmentl3(), dataSegmen.getSegmentl3(), dataSegmen.getNosegment(), dataSegmen.getSubsegment(), "kiri", "L3", tipeSurvey);
        cekPerubahan(dataSegmenTempo.getSegmentl4(), dataSegmen.getSegmentl4(), dataSegmen.getNosegment(), dataSegmen.getSubsegment(), "kiri", "L4", tipeSurvey);
        cekPerubahan(dataSegmenTempo.getSegmentl5(), dataSegmen.getSegmentl5(), dataSegmen.getNosegment(), dataSegmen.getSubsegment(), "kiri", "L5", tipeSurvey);
        cekPerubahan(dataSegmenTempo.getSegmentl6(), dataSegmen.getSegmentl6(), dataSegmen.getNosegment(), dataSegmen.getSubsegment(), "kiri", "L6", tipeSurvey);
        cekPerubahan(dataSegmenTempo.getSegmentl7(), dataSegmen.getSegmentl7(), dataSegmen.getNosegment(), dataSegmen.getSubsegment(), "kiri", "L7", tipeSurvey);
        cekPerubahan(dataSegmenTempo.getSegmentl8(), dataSegmen.getSegmentl8(), dataSegmen.getNosegment(), dataSegmen.getSubsegment(), "kiri", "L8", tipeSurvey);
        cekPerubahan(dataSegmenTempo.getSegmentl9(), dataSegmen.getSegmentl9(), dataSegmen.getNosegment(), dataSegmen.getSubsegment(), "kiri", "L9", tipeSurvey);
        cekPerubahan(dataSegmenTempo.getSegmentl10(), dataSegmen.getSegmentl10(), dataSegmen.getNosegment(), dataSegmen.getSubsegment(), "kiri", "L10", tipeSurvey);

        cekPerubahan(dataSegmenTempo.getSegmentr1(), dataSegmen.getSegmentr1(), dataSegmen.getNosegment(), dataSegmen.getSubsegment(), "kanan", "R1", tipeSurvey);
        cekPerubahan(dataSegmenTempo.getSegmentr2(), dataSegmen.getSegmentr2(), dataSegmen.getNosegment(), dataSegmen.getSubsegment(), "kanan", "R2", tipeSurvey);
        cekPerubahan(dataSegmenTempo.getSegmentr3(), dataSegmen.getSegmentr3(), dataSegmen.getNosegment(), dataSegmen.getSubsegment(), "kanan", "R3", tipeSurvey);
        cekPerubahan(dataSegmenTempo.getSegmentr4(), dataSegmen.getSegmentr4(), dataSegmen.getNosegment(), dataSegmen.getSubsegment(), "kanan", "R4", tipeSurvey);
        cekPerubahan(dataSegmenTempo.getSegmentr5(), dataSegmen.getSegmentr5(), dataSegmen.getNosegment(), dataSegmen.getSubsegment(), "kanan", "R5", tipeSurvey);
        cekPerubahan(dataSegmenTempo.getSegmentr6(), dataSegmen.getSegmentr6(), dataSegmen.getNosegment(), dataSegmen.getSubsegment(), "kanan", "R6", tipeSurvey);
        cekPerubahan(dataSegmenTempo.getSegmentr7(), dataSegmen.getSegmentr7(), dataSegmen.getNosegment(), dataSegmen.getSubsegment(), "kanan", "R7", tipeSurvey);
        cekPerubahan(dataSegmenTempo.getSegmentr8(), dataSegmen.getSegmentr8(), dataSegmen.getNosegment(), dataSegmen.getSubsegment(), "kanan", "R8", tipeSurvey);
        cekPerubahan(dataSegmenTempo.getSegmentr9(), dataSegmen.getSegmentr9(), dataSegmen.getNosegment(), dataSegmen.getSubsegment(), "kanan", "R9", tipeSurvey);
        cekPerubahan(dataSegmenTempo.getSegmentr10(), dataSegmen.getSegmentr10(), dataSegmen.getNosegment(), dataSegmen.getSubsegment(), "kanan", "R10", tipeSurvey);

        dbSegmen.postSegment(dataSegmen);

        if(tipeSurvey.equals("Detail") || asal.equals("Tabel")){

            saveTemporari("Segment", tipeSurvey, dataSegmen.getNoprov(), dataSegmen.getNoruas(), dataSegmen.getNosegment(), dataSegmen.getSubsegment(), "Update", "", dataSegmen.getNosegment(), dataSegmen.getSubsegment());


        }

        final ArrayList<DataTemporari> listTemporari = getTerusan("Segment", tipeSurvey, dataSegmen.getNoprov(), dataSegmen.getNoruas(), "Update", null);
        final int index = getIndex(listTemporari, dataSegmen.getNosegment(), dataSegmen.getSubsegment());

        if(asal.equals("Tabel")){

            if (index > 0) {
                addSebelum(tipeSurvey, listTemporari, index);
            }

            if (cekMaksimalDetail(tipeSurvey, dataSegmen.getNosegment(), dataSegmen.getSubsegment())) {

                addSesudahDetail("Segment", tipeSurvey, listTemporari, index);

            }

        }else{


            TaskSaveSegmentTerusan taskSaveSegmentTerusan = new TaskSaveSegmentTerusan(context, dataSegmen, tipeSurvey, listTemporari.get(index).getSegmentakhir(), listTemporari.get(index).getSubsegmentakhir(), new SendDialog() {
                @Override
                public void sendDialog(ProgressDialog dialog) {

                    dialog.dismiss();
                    dbSegmen.saveSegmentNormal(dataSegmen, listTemporari.get(index).getNosegment(), listTemporari.get(index).getSubsegment(), listTemporari.get(index).getSegmentakhir(), listTemporari.get(index).getSubsegmentakhir(), tipeSurvey);
                    saveTemporari("Segment", tipeSurvey, dataSegmen.getNoprov(), dataSegmen.getNoruas(), dataSegmen.getNosegment(), dataSegmen.getSubsegment(), "Update", "", listTemporari.get(index).getSegmentakhir(), listTemporari.get(index).getSubsegmentakhir());

                    setOnsave(asal, "Segment");
                }
            });


            taskSaveSegmentTerusan.execute("a", "a", "a");

        }

        if(tipeSurvey.equals("Detail") || asal.equals("Tabel")){

            setOnsave(asal, "Segment");

        }




    }





    private void cekPerubahan(int a, int b, int segment, int subSegment, String posisi, String lajur, String jenisSurvey){

        if(a!=b){

            if(a==0 && b==1){

                dbLane.tambahLane(session.getKodeprov(), session.getNoruas(), segment, subSegment, posisi, lajur);
                saveTemporari("Segment", jenisSurvey, session.getKodeprov(), session.getNoruas(), segment, subSegment, "Tambah", lajur, segment, subSegment);
                dbTemporari.hapusTemporari(session.getKodeprov(), session.getNoruas(), segment, subSegment, "Segment", "Hapus", lajur);

            }else if(a==1 && b==0){

                dbLane.hapusLane(session.getKodeprov(), session.getNoruas(), segment, subSegment, posisi, lajur);
                saveTemporari("Segment", jenisSurvey, session.getKodeprov(), session.getNoruas(), segment, subSegment, "Hapus", lajur, segment, subSegment);
                dbTemporari.hapusTemporari(session.getKodeprov(), session.getNoruas(), segment, subSegment, "Lane", posisi, lajur);
                dbTemporari.hapusTemporari(session.getKodeprov(), session.getNoruas(), segment, subSegment, "Segment", "Tambah", lajur);
            }


        }


    }



    private boolean cekMaksimalIndex(String tipeSurvey, int segment, int subsegment){

        if(tipeSurvey.equals("Normal")) {

            if (segment < maksimalSegment || (segment == maksimalSegment && subsegment < maksimalSub)) {
                return true;
            } else {
                return false;
            }
        }else{
            if(segment>maksimalSegment || (segment==maksimalSegment && subsegment>maksimalSub)){
                return true;
            }else{
                return false;
            }
        }

    }

    private boolean cekMaksimalIndexLane(String tipeSurvey, int segment, int subsegment, int segmentLane, int subsegmentLane){

        if(tipeSurvey.equals("Normal")) {

            if (segment < segmentLane || (segment == segmentLane && subsegment < subsegmentLane)) {
                return true;
            } else {
                return false;
            }
        }else{

            if(segment>segmentLane || (segment==segmentLane && subsegment>subsegmentLane)){
                return true;
            }else{
                return false;
            }
        }

    }

    private boolean cekMaksimalDetail(String tipeSurvey, int segment, int subsegment){

        int detailSegment, detailSub;

        if(tipeSurvey.equals("Normal")){
            detailSegment = dbSpinner.getMaksSegment(session.getKodeprov(), session.getNoruas());
            detailSub = dbSpinner.getMaksSegmentSub(session.getKodeprov(), session.getNoruas(), detailSegment);
        }else{
            detailSegment = 1;
            detailSub = 0;
        }

        if(tipeSurvey.equals("Normal")) {

            if (segment < detailSegment || (segment == detailSegment && subsegment < detailSub)) {
                return true;
            } else {
                return false;
            }
        }else{
            if(segment>detailSegment || (segment==detailSegment && subsegment>detailSub)){
                return true;
            }else{
                return false;
            }
        }

    }

    private void addSebelum(String jenisSurvey, ArrayList<DataTemporari> list, int index){

        int segAkhir = getMinSegment(jenisSurvey, list.get(index).getNosegment(), list.get(index).getSubsegment());
        int subAkhir = getMinSub(jenisSurvey, list.get(index).getSubsegment());

        DataTemporari dataTemporari = list.get(index-1);
        dataTemporari.setSegmentakhir(segAkhir);
        dataTemporari.setSubsegmentakhir(subAkhir);
        dataTemporari.setStatus("0");

        dbTemporari.postTemporari(dataTemporari);

    }


    private void addSesudah(String jenisSurvey, ArrayList<DataTemporari> list, int index){

        int segAkhir = getMinSegment(jenisSurvey, list.get(index+1).getNosegment(), list.get(index+1).getSubsegment());
        int subAkhir = getMinSub(jenisSurvey, list.get(index+1).getSubsegment());

        DataTemporari dataTemporari = list.get(index);
        dataTemporari.setSegmentakhir(segAkhir);
        dataTemporari.setSubsegmentakhir(subAkhir);
        dataTemporari.setStatus("0");

        dbTemporari.postTemporari(dataTemporari);

    }

    private void addSesudahDetail(String tipe, String tipeSurvey, ArrayList<DataTemporari> list, int index){

        int segAwal = tambahSegment(tipeSurvey, list.get(index).getNosegment(), list.get(index).getSubsegment());
        int subAwal = tambahSubSegment(tipeSurvey, list.get(index).getSubsegment());

        int maksSeg = indexSegmentMaks(tipeSurvey);
        int maksSub = indexSubMaks(tipeSurvey, maksSeg);

        //Log.d("DETAIL", segAwal+"."+subAwal);

        if(index<list.size()-1){

            if(index != list.get(index+1).getNosegment() && index != list.get(index+1).getSubsegment()){

                int segAkhir = getMinSegment(tipeSurvey, list.get(index+1).getNosegment(), list.get(index+1).getSubsegment());
                int subAkhir = getMinSub(tipeSurvey, list.get(index+1).getSubsegment());

                saveTemporari(tipe, tipeSurvey, list.get(index).getNoprov(), list.get(index).getNoruas(), segAwal, subAwal, list.get(index).getPosisi(), list.get(index).getUrut(), segAkhir, subAkhir);

            }


        }else{

            saveTemporari(tipe, tipeSurvey, list.get(index).getNoprov(), list.get(index).getNoruas(), segAwal, subAwal, list.get(index).getPosisi(), list.get(index).getUrut(), maksSeg, maksSub);


        }

    }

    private int getIndex(ArrayList<DataTemporari> list, int segment, int subsegment){

        for(int i=0; i<list.size(); i++){
            if(list.get(i).getNosegment()==segment && list.get(i).getSubsegment()==subsegment){
                return i;
            }
        }

        return 0;

    }

    private int getMinSegment(String jenis, int segment, int subsegment){

        if(jenis.equals("Normal")){

            if(subsegment>0){
                return segment;
            }else{
                return segment-1;
            }

        }else{

            if(subsegment == 9){
                return segment+1;
            }else{
                return segment;
            }

        }

    }

    private int getMinSub(String jenis, int subsegment){

        if(jenis.equals("Normal")){

            if(subsegment>0){
                return subsegment-1;
            }else{
                return 9;
            }

        }else{

            if(subsegment<9){
                return subsegment+1;
            }else{
                return 0;
            }

        }

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


    private void saveTemporari(String tipe, String jenisSurvey, String provinsi, String ruas, int segment, int subsegment, String posisi, String lajur, int segmentAkhir, int subSegmentAkhir){

        int foto;

        if(tipe.equals("Segment")){
            foto = 1;
        }else{
            foto  = getFotoTipe(tipe, provinsi, ruas, segment, subsegment, posisi, lajur);
        }

        DataTemporari dataTemporari = new DataTemporari(provinsi, ruas, segment, subsegment, tipe, posisi,lajur, jenisSurvey, segmentAkhir, subSegmentAkhir, String.valueOf(foto), "0");
        dbTemporari.postTemporari(dataTemporari);
    }

    private int getFotoTipe(String tipe, String provinsi, String ruas, int segment, int subsegment, String posisi, String lajur ) {

        int foto;

        switch (tipe) {
            case "Bahu":

                DataBahu dataBahu = dbBahu.getBahu(provinsi, ruas, segment, subsegment, posisi);
                foto = helperList.getStatFoto(helperList.getImagePath(dataBahu.getGambarBahu()));

                break;

            case "Lahan":

                DataLahan dataLahan = dbLahan.getLahan(provinsi, ruas, segment, subsegment, posisi);
                foto = helperList.getStatFoto(helperList.getImagePath(dataLahan.getGambarLahan()));

                break;

            case "Saluran":

                DataSaluran dataSaluran = dbSaluran.getSaluran(provinsi, ruas, segment, subsegment, posisi);
                foto = helperList.getStatFoto(helperList.getImagePath(dataSaluran.getGambarSaluran()));

                break;


            case "Perkerasan":
                DbMinlet dbMinlet = new DbMinlet(context);
                DataInletMedian dataMinlet = dbMinlet.getMinlet(provinsi, ruas, segment, subsegment, posisi);
                foto = helperList.getStatFoto(helperList.getImagePath(dataMinlet.getGambar()));

                break;

            case "SPD":
                DbSpd dbSpd = new DbSpd(context);
                DataSpDalam dataSpDalam = dbSpd.getData(provinsi, ruas, segment, subsegment, posisi);
                foto = helperList.getStatFoto(helperList.getImagePath(dataSpDalam.getGambar()));

                break;

            case "SPL":
                DbSpl dbSpl = new DbSpl(context);
                DataSpLuar dataSpLuar = dbSpl.getData(provinsi, ruas, segment, subsegment, posisi);
                foto = helperList.getStatFoto(helperList.getImagePath(dataSpLuar.getGambar()));

                break;




            case "Median":

                DataMedian dataMedian = dbMedian.getMedian(provinsi, ruas, segment, subsegment);
                foto = helperList.getStatFoto(helperList.getImagePath(dataMedian.getGambarMedian()));

                break;

            case "Lane":

                DataLane dataLane = dbLane.getLane(provinsi, ruas, segment, subsegment, posisi, lajur);
                foto = helperList.getStatFoto(helperList.getImagePath(dataLane.getGambarLane()));

                break;

            case "Segment" :
                foto = 1;
                break;

            default: foto = 0;

        }

        return foto;

    }

    public int getPosisiTabel(String tipe){

        int value = 0;

        switch (tipe){
            case "Segment" : value = 0;
                break;

            case "Median" : value = 1;
                break;

            case "Lahan" : value = 2;
                break;

            case "Saluran" : value = 3;
                break;

            case "Bahu" : value = 4;
                break;

            case "Lane" : value = 5;
                break;

            case "Perkerasan" : value = 6;
                break;

            case "Inlet ditrotoar" : value = 7;
                break;

            case "Outlet" : value = 8;
                break;

            case "Gorong-gorong" : value = 9;
                break;

        }


        return value;


    }

    /*public int getPosisiTabel(String tipe){

        int value = 0;

        switch (tipe){
            case "Segment" : value = 0;
                break;

            case "Lahan" : value = 1;
                break;

            case "Saluran" : value = 2;
                break;

            case "Bahu" : value = 3;
                break;

            case "Median" : value = 4;
                break;

            case "Lane" : value = 5;
                break;

            case "Inlet ditrotoar" : value = 6;
                break;

            case "Inlet dimedian" : value = 7;
                break;

            case "Outlet" : value = 8;
                break;

            case "Gorong-gorong" : value = 9;
                break;

            case "Saluran Lereng" : value = 10;
                break;
        }


        return value;


    }*/

    public void setOnsave(String asal, String tipe){

        Intent i;

        int index = getPosisiTabel(tipe);

        if(asal.equals("Survey")){
            i = new Intent(context, FormUtama.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }else if(asal.equals("Tabel")){
            i = new Intent(context, LihatTabel.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.putExtra("posisi", String.valueOf(index));
        }else{
            i = new Intent(context, SinkronUtama.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.putExtra("tipe", tipe);
        }

        context.startActivity(i);
        ((Activity)context).finish();

    }


    private int indexSegmentMaks(String tipeSurvey){
        if(tipeSurvey.equals("Normal")){
            return dbSpinner.getMaksSegment(session.getKodeprov(), session.getNoruas());
        }else{
            return 1;
        }
    }

    private int indexSubMaks(String tipeSurvey, int segment){
        if(tipeSurvey.equals("Normal")){
            return dbSpinner.getMaksSegmentSub(session.getKodeprov(), session.getNoruas(), segment);
        }else{
            return 0;
        }
    }

    /*


    public HelperFormTerusan(Context context){

        this.context = context;

        session = new Session(context);
        dbLahan = new DbLahan(context);
        dbSaluran = new DbSaluran(context);
        dbBahu = new DbBahu(context);
        dbMedian = new DbMedian(context);
        dbLane = new DbLane(context);
        dbSegmen = new DbSegmen(context);
        dbTemporari = new DbTemporari(context);
        dbSpinner = new DbSpinner(context);
        helperList = new HelperList();

        if(session.getSurvey()==1) {

            if(session.getTipesurvey().equals("Normal")){
                maksSubSegment = dbSpinner.getMaksSegmentSub(session.getKodeprov(), session.getNoruas());
                //maksSegment = dbSpinner.getMaksSegment(session.getKodeprov(), session.getNoruas());
                jenisSurvey = 0;
            }else{
                maksSubSegment = 1;
                //maksSegment = 1;
                jenisSurvey = 1;
            }

        }

    }

    public void setOnsave(String asal, String tipe){

        Intent i;

        int index = getPosisiTabel(tipe);

        if(asal.equals("Survey")){
            i = new Intent(context, FormUtama.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }else if(asal.equals("Tabel")){
            i = new Intent(context, LihatTabel.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.putExtra("posisi", String.valueOf(index));
        }else{
            i = new Intent(context, SinkronUtama.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.putExtra("tipe", tipe);
        }

        context.startActivity(i);
        ((Activity)context).finish();

    }


    //LAHAN

    public void saveLahan(DataLahan dataLahan, String asal){

        String keySession;
        int foto;

        if(dataLahan.getPosisi().equals("kiri")){
            keySession = Session.SESSION_LAHANKIRI;
        }else{
            keySession = Session.SESSION_LAHANKANAN;
        }

        ArrayList<Float>arrayIndex  = addIndex(keySession, dataLahan.getSubsegment());

        foto = helperList.getStatFoto(helperList.getImagePath(dataLahan.getGambarLahan()));

        dbLahan.updateLahan(dataLahan);
        dbTemporari.saveTemporariTerusan("Lahan", dataLahan.getNoprov(), dataLahan.getNoruas(), dataLahan.getPosisi(), "", dataLahan.getSubsegment(), dataLahan.getSubsegment(), session.getTipesurvey(), String.valueOf(foto));

        if(arrayIndex.size()>1){

            for(int i=0; i<arrayIndex.size()-1; i++){

                int segmentIndex = dbSpinner.getSegment(dataLahan.getNoprov(), dataLahan.getNoruas(), arrayIndex.get(i));
                DataLahan dataLahanNormal  = dbLahan.getLahan(dataLahan.getNoprov(), dataLahan.getNoruas(), segmentIndex, arrayIndex.get(i), dataLahan.getPosisi());
                float subSegmentAkhir  = getSubSegmentAkhir(session.getTipesurvey(), arrayIndex.get(i+1));

                foto = helperList.getStatFoto(helperList.getImagePath(dataLahanNormal.getGambarLahan()));
                dbLahan.saveLahanNormal(dataLahanNormal, arrayIndex.get(i), subSegmentAkhir, session.getTipesurvey());
                dbTemporari.saveTemporariTerusan("Lahan", dataLahanNormal.getNoprov(), dataLahanNormal.getNoruas(), dataLahanNormal.getPosisi(), "", dataLahanNormal.getSubsegment(), subSegmentAkhir, session.getTipesurvey(), String.valueOf(foto));
            }

        }

        session.setIndexData(keySession, changeArray(arrayIndex));
        setOnsave(asal, "Lahan");
    }


    public void saveLahanEnd(String posisiku) {

        String keySession;

        if (posisiku.equals("kiri")) {
            keySession = Session.SESSION_LAHANKIRI;
        } else {
            keySession = Session.SESSION_LAHANKANAN;
        }

        ArrayList<Float> arrayIndex = getIndexData(keySession);

        if (arrayIndex.size() > 0) {

            float maksIndex = arrayIndex.get(arrayIndex.size() - 1);
            int maksIndexSegment = dbSpinner.getSegment(session.getKodeprov(), session.getNoruas(), maksIndex);

            DataLahan dataLahanNormal = dbLahan.getLahan(session.getKodeprov(), session.getNoruas(), maksIndexSegment, maksIndex, posisiku);
            int statfoto = helperList.getStatFoto(helperList.getImagePath(dataLahanNormal.getGambarLahan()));
            dbLahan.saveLahanNormal(dataLahanNormal, maksIndex, maksSubSegment, session.getTipesurvey());
            dbTemporari.saveTemporariTerusan("Lahan", dataLahanNormal.getNoprov(), dataLahanNormal.getNoruas(), dataLahanNormal.getPosisi(), "", dataLahanNormal.getSubsegment(), maksSubSegment, session.getTipesurvey(), String.valueOf(statfoto));
        }

        session.setIndexData(keySession, changeArray(arrayIndex));
    }


    //SALURAN
    public void saveSaluran(DataSaluran dataSaluran, String asal){

        String keySession;
        int foto;

        if(dataSaluran.getPosisi().equals("kiri")){
            keySession = Session.SESSION_SALURANKIRI;
        }else{
            keySession = Session.SESSION_SALURANKANAN;
        }

        foto = helperList.getStatFoto(helperList.getImagePath(dataSaluran.getGambarSaluran()));

        ArrayList<Float>arrayIndex  = addIndex(keySession, dataSaluran.getSubsegment());

        dbSaluran.updateSaluran(dataSaluran);
        dbTemporari.saveTemporariTerusan("Saluran", dataSaluran.getNoprov(), dataSaluran.getNoruas(), dataSaluran.getPosisi(), "", dataSaluran.getSubsegment(), dataSaluran.getSubsegment(), session.getTipesurvey(), String.valueOf(foto));

        if(arrayIndex.size()>1){

            for(int i=0; i<arrayIndex.size()-1; i++){
                int segmentIndex = dbSpinner.getSegment(dataSaluran.getNoprov(), dataSaluran.getNoruas(), arrayIndex.get(i));
                DataSaluran dataSaluranNormal = dbSaluran.getSaluran(dataSaluran.getNoprov(), dataSaluran.getNoruas(), segmentIndex, arrayIndex.get(i), dataSaluran.getPosisi());
                float subSegmentAkhir = getSubSegmentAkhir(session.getTipesurvey(), arrayIndex.get(i+1));

                foto = helperList.getStatFoto(helperList.getImagePath(dataSaluranNormal.getGambarSaluran()));
                dbSaluran.saveSaluranNormal(dataSaluranNormal, arrayIndex.get(i), subSegmentAkhir, session.getTipesurvey());
                dbTemporari.saveTemporariTerusan("Saluran", dataSaluranNormal.getNoprov(), dataSaluranNormal.getNoruas(), dataSaluranNormal.getPosisi(), "", dataSaluranNormal.getSubsegment(), subSegmentAkhir, session.getTipesurvey(), String.valueOf(foto));
            }

        }

        session.setIndexData(keySession, changeArray(arrayIndex));
        setOnsave(asal, "Saluran");
    }


    public void saveSaluranEnd(String posisiku){

        String keySession;

        if(posisiku.equals("kiri")){
            keySession = Session.SESSION_SALURANKIRI;
        }else{
            keySession = Session.SESSION_SALURANKANAN;
        }

        ArrayList<Float>arrayIndex  = getIndexData(keySession);

        if(arrayIndex.size()>0){

            float maksIndex = arrayIndex.get(arrayIndex.size()-1);
            int maksIndexSegment = dbSpinner.getSegment(session.getKodeprov(), session.getNoruas(), maksIndex);

            DataSaluran dataSaluranNormal  = dbSaluran.getSaluran(session.getKodeprov(), session.getNoruas(), maksIndexSegment, maksIndex, posisiku);
            int statfoto = helperList.getStatFoto(helperList.getImagePath(dataSaluranNormal.getGambarSaluran()));
            dbSaluran.saveSaluranNormal(dataSaluranNormal, maksIndex, maksSubSegment, session.getTipesurvey());
            dbTemporari.saveTemporariTerusan("Saluran", dataSaluranNormal.getNoprov(), dataSaluranNormal.getNoruas(), dataSaluranNormal.getPosisi(), "", dataSaluranNormal.getSubsegment(), maksSubSegment, session.getTipesurvey(), String.valueOf(statfoto));
        }

        session.setIndexData(keySession, changeArray(arrayIndex));
    }



    //BAHU
    public void saveBahu(DataBahu dataBahu, String asal){

        String keySession;
        int foto;

        if(dataBahu.getPosisi().equals("kiri")){
            keySession = Session.SESSION_BAHUKIRI;
        }else{
            keySession = Session.SESSION_BAHUKANAN;
        }

        foto = helperList.getStatFoto(helperList.getImagePath(dataBahu.getGambarBahu()));

        ArrayList<Float>arrayIndex  = addIndex(keySession, dataBahu.getSubSegment());

        dbBahu.updateBahu(dataBahu);
        dbTemporari.saveTemporariTerusan("Bahu", dataBahu.getNoprov(), dataBahu.getNoruas(), dataBahu.getPosisi(), "", dataBahu.getSubSegment(), dataBahu.getSubSegment(), session.getTipesurvey(), String.valueOf(foto));

        if(arrayIndex.size()>1){

            for(int i=0; i<arrayIndex.size()-1; i++){

                int segmentIndex = dbSpinner.getSegment(dataBahu.getNoprov(), dataBahu.getNoruas(), arrayIndex.get(i));
                DataBahu dataBahuNormal = dbBahu.getBahu(dataBahu.getNoprov(), dataBahu.getNoruas(), segmentIndex,  arrayIndex.get(i), dataBahu.getPosisi());
                float subsegmentAkhir = getSubSegmentAkhir(session.getTipesurvey(), arrayIndex.get(i+1));


                foto = helperList.getStatFoto(helperList.getImagePath(dataBahuNormal.getGambarBahu()));

                dbBahu.saveBahuNormal(dataBahuNormal, arrayIndex.get(i), subsegmentAkhir, session.getTipesurvey());
                dbTemporari.saveTemporariTerusan("Bahu", dataBahuNormal.getNoprov(), dataBahuNormal.getNoruas(), dataBahuNormal.getPosisi(), "", dataBahuNormal.getSubSegment(), subsegmentAkhir, session.getTipesurvey(), String.valueOf(foto));
            }

        }

        session.setIndexData(keySession, changeArray(arrayIndex));
        setOnsave(asal, "Bahu");
    }


    public void saveBahuEnd(String posisiku){

        String keySession;

        if(posisiku.equals("kiri")){
            keySession = Session.SESSION_BAHUKIRI;
        }else{
            keySession = Session.SESSION_BAHUKANAN;
        }

        ArrayList<Float>arrayIndex  = getIndexData(keySession);

        if(arrayIndex.size()>0){

            float maksIndex = arrayIndex.get(arrayIndex.size()-1);
            int maksIndexSegment = dbSpinner.getSegment(session.getKodeprov(), session.getNoruas(), maksIndex);

            DataBahu dataBahuNormal  = dbBahu.getBahu(session.getKodeprov(), session.getNoruas(),maksIndexSegment, maksIndex, posisiku);
            int statfoto = helperList.getStatFoto(helperList.getImagePath(dataBahuNormal.getGambarBahu()));
            dbBahu.saveBahuNormal(dataBahuNormal, maksIndex, maksSubSegment, session.getTipesurvey());
            dbTemporari.saveTemporariTerusan("Bahu", dataBahuNormal.getNoprov(), dataBahuNormal.getNoruas(), dataBahuNormal.getPosisi(), "", dataBahuNormal.getSubSegment(), maksSubSegment, session.getTipesurvey(), String.valueOf(statfoto));
        }

        session.setIndexData(keySession, changeArray(arrayIndex));
    }


    //MEDIAN

    public void saveMedian(DataMedian dataMedian, String asal){

        int foto;
        String keySession = Session.SESSION_MEDIAN;
        ArrayList<Float>arrayIndex  = addIndex(keySession, dataMedian.getSubsegment());
        foto = helperList.getStatFoto(helperList.getImagePath(dataMedian.getGambarMedian()));
        dbMedian.updateMedian(dataMedian);
        dbTemporari.saveTemporariTerusan("Median", dataMedian.getNoprov(), dataMedian.getNoruas(), "", "", dataMedian.getSubsegment(), dataMedian.getSubsegment(), session.getTipesurvey(), String.valueOf(foto));

        if(arrayIndex.size()>1){

            for(int i=0; i<arrayIndex.size()-1; i++){
                int segmentIndex = dbSpinner.getSegment(dataMedian.getNoprov(), dataMedian.getNoruas(), arrayIndex.get(i));
                DataMedian dataMedianNormal = dbMedian.getMedian(dataMedian.getNoprov(), dataMedian.getNoruas(), segmentIndex, arrayIndex.get(i));
                foto = helperList.getStatFoto(helperList.getImagePath(dataMedianNormal.getGambarMedian()));
                float subSegmentAkhir = getSubSegmentAkhir(session.getTipesurvey(), arrayIndex.get(i+1));
                dbMedian.saveMedianNormal(dataMedianNormal, arrayIndex.get(i), subSegmentAkhir, session.getTipesurvey());
                dbTemporari.saveTemporariTerusan("Median", dataMedianNormal.getNoprov(), dataMedianNormal.getNoruas(), "", "", dataMedianNormal.getSubsegment(), subSegmentAkhir, session.getTipesurvey(), String.valueOf(foto));
            }

        }

        session.setIndexData(keySession, changeArray(arrayIndex));
        setOnsave(asal, "Median");
    }


    public void saveMedianEnd(){

        String keySession = Session.SESSION_MEDIAN;

        ArrayList<Float>arrayIndex  = getIndexData(keySession);

        if(arrayIndex.size()>0){

            float maksIndex = arrayIndex.get(arrayIndex.size()-1);
            int maksIndexSegment = dbSpinner.getSegment(session.getKodeprov(), session.getNoruas(), maksIndex);

            DataMedian dataMedianNormal  = dbMedian.getMedian(session.getKodeprov(), session.getNoruas(), maksIndexSegment, maksIndex);
            int statfoto = helperList.getStatFoto(helperList.getImagePath(dataMedianNormal.getGambarMedian()));
            dbMedian.saveMedianNormal(dataMedianNormal, maksIndex, maksSubSegment, session.getTipesurvey());
            dbTemporari.saveTemporariTerusan("Median", dataMedianNormal.getNoprov(), dataMedianNormal.getNoruas(), "", "", dataMedianNormal.getSubsegment(), maksSubSegment, session.getTipesurvey(), String.valueOf(statfoto));
        }

    }


    //LANE

    public void saveLane(DataLane dataLane, String asal){

        int foto = helperList.getStatFoto(helperList.getImagePath(dataLane.getGambarLane()));

        String keySession = getStringLane(dataLane.getUrut());

        ArrayList<Float>arrayIndex  = addIndex(keySession, dataLane.getSubsegment());

        dbLane.updateLane(dataLane);
        dbTemporari.saveTemporariTerusan("Lane", dataLane.getNoprov(), dataLane.getNoruas(), dataLane.getPosisi(), dataLane.getUrut(), dataLane.getSubsegment(), dataLane.getSubsegment(), session.getTipesurvey(), String.valueOf(foto));

        if(arrayIndex.size()>1){

            for(int i=0; i<arrayIndex.size()-1; i++){
                int segmentIndex = dbSpinner.getSegment(dataLane.getNoprov(), dataLane.getNoruas(), arrayIndex.get(i));
                DataLane dataLaneNormal = dbLane.getLane(dataLane.getNoprov(), dataLane.getNoruas(), segmentIndex, arrayIndex.get(i), dataLane.getPosisi(), dataLane.getUrut());
                foto = helperList.getStatFoto(helperList.getImagePath(dataLaneNormal.getGambarLane()));
                float subSegmentAkhir = getSubSegmentAkhir(session.getTipesurvey(), arrayIndex.get(i+1));
                TaskSaveLaneTerusan taskSaveLaneTerusan = new TaskSaveLaneTerusan(context, dataLaneNormal, session.getTipesurvey(), subSegmentAkhir, String.valueOf(foto));
                taskSaveLaneTerusan.execute("bisa", "bisa", "bisa");

            }

        }

        session.setIndexData(keySession, changeArray(arrayIndex));
        setOnsave(asal, "Lane");
    }


    public void saveLaneEnd(String lajur, String tipeSurvey){

        float maksSubsegLane;
        int foto;

        String posisi;

        if(lajur.substring(0,1).equals("L")){
            posisi = "kiri";
        }else{
            posisi = "kanan";
        }

        if(tipeSurvey.equals("Normal")){
            maksSubsegLane = dbLane.getMaksSubsegLajur(session.getKodeprov(), session.getNoruas(), posisi, lajur);
        }else{
            maksSubsegLane = 1;
        }

        String keySession = getStringLane(lajur);

        ArrayList<Float>arrayIndex  = getIndexData(keySession);

        if(arrayIndex.size()>0){

            for(int i=0; i<arrayIndex.size(); i++){

                if(i == arrayIndex.size()-1){

                    float maksIndex = arrayIndex.get(i);
                    int maksIndexSegment = dbSpinner.getSegment(session.getKodeprov(), session.getNoruas(), maksIndex);
                    DataLane dataLaneNormal = dbLane.getLane(session.getKodeprov(), session.getNoruas(), maksIndexSegment, maksIndex, posisi , lajur);

                    if(dataLaneNormal!=null){

                        foto = helperList.getStatFoto(helperList.getImagePath(dataLaneNormal.getGambarLane()));
                        TaskSaveLaneTerusan taskSaveLaneTerusan = new TaskSaveLaneTerusan(context, dataLaneNormal, tipeSurvey, maksSubsegLane, String.valueOf(foto));
                        taskSaveLaneTerusan.execute("bisa", "bisa", "bisa");

                    }



                }else {

                    float subsegmentIndex = arrayIndex.get(i);
                    int segmentIndex = dbSpinner.getSegment(session.getKodeprov(), session.getNoruas(), subsegmentIndex);

                    DataLane dataLaneNormal = dbLane.getLane(session.getKodeprov(), session.getNoruas(), segmentIndex, subsegmentIndex, posisi, lajur);

                    if(dataLaneNormal!=null) {
                        foto = helperList.getStatFoto(helperList.getImagePath(dataLaneNormal.getGambarLane()));
                        float subSegmentAkhir = getSubSegmentAkhir(session.getTipesurvey(), arrayIndex.get(i+1));
                        TaskSaveLaneTerusan taskSaveLaneTerusan = new TaskSaveLaneTerusan(context, dataLaneNormal, session.getTipesurvey(), subSegmentAkhir, String.valueOf(foto));
                        taskSaveLaneTerusan.execute("bisa", "bisa", "bisa");
                    }
                }

            }

        }


    }


    //SEGMENT

    public void saveSegment(DataSegmen dataSegmen, String asal){

        DataSegmen dataSegmenTempo = dbSegmen.getSegmen(dataSegmen.getNoprov(), dataSegmen.getNoruas(), dataSegmen.getNosegment(), dataSegmen.getSubsegment());

        String keySession = Session.SESSION_SEGMENT;

        ArrayList<Float>arrayIndex  = addIndex(keySession, dataSegmen.getSubsegment());

        cekPerubahan(dataSegmenTempo.getSegmentl1(), dataSegmen.getSegmentl1(), dataSegmenTempo.getSubsegment(), "kiri", "L1", session.getTipesurvey());
        cekPerubahan(dataSegmenTempo.getSegmentl2(), dataSegmen.getSegmentl2(), dataSegmenTempo.getSubsegment(),"kiri", "L2", session.getTipesurvey());
        cekPerubahan(dataSegmenTempo.getSegmentl3(), dataSegmen.getSegmentl3(), dataSegmenTempo.getSubsegment(),"kiri", "L3", session.getTipesurvey());
        cekPerubahan(dataSegmenTempo.getSegmentl4(), dataSegmen.getSegmentl4(), dataSegmenTempo.getSubsegment(),"kiri", "L4", session.getTipesurvey());
        cekPerubahan(dataSegmenTempo.getSegmentl5(), dataSegmen.getSegmentl5(), dataSegmenTempo.getSubsegment(),"kiri", "L5", session.getTipesurvey());
        cekPerubahan(dataSegmenTempo.getSegmentl6(), dataSegmen.getSegmentl6(), dataSegmenTempo.getSubsegment(),"kiri", "L6", session.getTipesurvey());
        cekPerubahan(dataSegmenTempo.getSegmentl7(), dataSegmen.getSegmentl7(), dataSegmenTempo.getSubsegment(),"kiri", "L7", session.getTipesurvey());
        cekPerubahan(dataSegmenTempo.getSegmentl8(), dataSegmen.getSegmentl8(), dataSegmenTempo.getSubsegment(),"kiri", "L8", session.getTipesurvey());
        cekPerubahan(dataSegmenTempo.getSegmentl9(), dataSegmen.getSegmentl9(), dataSegmenTempo.getSubsegment(),"kiri", "L9", session.getTipesurvey());
        cekPerubahan(dataSegmenTempo.getSegmentl10(), dataSegmen.getSegmentl10(), dataSegmenTempo.getSubsegment(),"kiri", "L10", session.getTipesurvey());

        cekPerubahan(dataSegmenTempo.getSegmentr1(), dataSegmen.getSegmentr1(), dataSegmenTempo.getSubsegment(),"kanan", "R1", session.getTipesurvey());
        cekPerubahan(dataSegmenTempo.getSegmentr2(), dataSegmen.getSegmentr2(), dataSegmenTempo.getSubsegment(),"kanan", "R2", session.getTipesurvey());
        cekPerubahan(dataSegmenTempo.getSegmentr3(), dataSegmen.getSegmentr3(), dataSegmenTempo.getSubsegment(),"kanan", "R3", session.getTipesurvey());
        cekPerubahan(dataSegmenTempo.getSegmentr4(), dataSegmen.getSegmentr4(), dataSegmenTempo.getSubsegment(),"kanan", "R4", session.getTipesurvey());
        cekPerubahan(dataSegmenTempo.getSegmentr5(), dataSegmen.getSegmentr5(), dataSegmenTempo.getSubsegment(),"kanan", "R5", session.getTipesurvey());
        cekPerubahan(dataSegmenTempo.getSegmentr6(), dataSegmen.getSegmentr6(), dataSegmenTempo.getSubsegment(),"kanan", "R6", session.getTipesurvey());
        cekPerubahan(dataSegmenTempo.getSegmentr7(), dataSegmen.getSegmentr7(), dataSegmenTempo.getSubsegment(),"kanan", "R7", session.getTipesurvey());
        cekPerubahan(dataSegmenTempo.getSegmentr8(), dataSegmen.getSegmentr8(), dataSegmenTempo.getSubsegment(),"kanan", "R8", session.getTipesurvey());
        cekPerubahan(dataSegmenTempo.getSegmentr9(), dataSegmen.getSegmentr9(), dataSegmenTempo.getSubsegment(),"kanan", "R9", session.getTipesurvey());
        cekPerubahan(dataSegmenTempo.getSegmentr10(), dataSegmen.getSegmentr10(), dataSegmenTempo.getSubsegment(),"kanan", "R10", session.getTipesurvey());

        dbSegmen.updateSegmen(dataSegmen);
        dbTemporari.saveTemporariTerusan("Segment", dataSegmen.getNoprov(), dataSegmen.getNoruas(), "Update", "", dataSegmen.getSubsegment(), dataSegmen.getSubsegment(), session.getTipesurvey(), "1");


        if(arrayIndex.size()>1){

            loopingSegment(0, arrayIndex);

        }

        session.setIndexData(keySession, changeArray(arrayIndex));
        setOnsave(asal, "Segment");
    }

    private void loopingSegment(int index, final ArrayList<Float> arrayList){


        final float subSegment = arrayList.get(index);
        final float subSegmentAkhir = getSubSegmentAkhir(session.getTipesurvey(), arrayList.get(index+1));

        int segmentIndex = dbSpinner.getSegment(session.getKodeprov(), session.getNoruas(), subSegment);


        final DataSegmen dataSegmentNormal = dbSegmen.getSegmen(session.getKodeprov(), session.getNoruas(), segmentIndex, subSegment);
        dbTemporari.saveTemporariTerusan("Segment", dataSegmentNormal.getNoprov(), dataSegmentNormal.getNoruas(), "Update", "", subSegment, subSegmentAkhir, session.getTipesurvey(), "1");


        TaskSaveSegmentTerusan taskSaveSegmentTerusan = new TaskSaveSegmentTerusan(context, dataSegmentNormal, session.getTipesurvey(), subSegmentAkhir, index, new SendId() {
            @Override
            public void hapusGambar(int id) {

                if(id+1<arrayList.size()) {
                    loopingSegment(id, arrayList);
                }else{
                    dbSegmen.saveSegmentNormal(dataSegmentNormal, subSegment, subSegmentAkhir, session.getTipesurvey());
                }

            }
        });

        taskSaveSegmentTerusan.execute("", "","");


    }

    public void saveSegmentEnd(final String tipe){

        String keySession = Session.SESSION_SEGMENT;

        ArrayList<Float>arrayIndex  = getIndexData(keySession);

        if(arrayIndex.size()>0){



            final float maksIndex = arrayIndex.get(arrayIndex.size()-1);
            int maksIndexSegment = dbSpinner.getSegment(session.getKodeprov(), session.getNoruas(), maksIndex);

            final DataSegmen dataSegmentNormal = dbSegmen.getSegmen(session.getKodeprov(), session.getNoruas(), maksIndexSegment, maksIndex);

            dbTemporari.saveTemporariTerusan("Segment", dataSegmentNormal.getNoprov(), dataSegmentNormal.getNoruas(), "Update", "", dataSegmentNormal.getSubsegment(), maksSubSegment, tipe, "1");


            TaskSaveSegmentTerusan taskSaveSegmentTerusan = new TaskSaveSegmentTerusan(context, dataSegmentNormal, tipe, maksSubSegment, 0, new SendId() {
                @Override
                public void hapusGambar(int id) {

                    if(id==1) {

                        dbSegmen.saveSegmentNormal(dataSegmentNormal, dataSegmentNormal.getSubsegment(), maksSubSegment, tipe);

                    }


                }
            });

            taskSaveSegmentTerusan.execute("", "","");

        }


    }








    //Resume Survey


    public void resumeSurvey(String jenisSurvey){

        String provinsi = session.getKodeprov();
        String ruas = session.getNoruas();

        session.setIndexData(Session.SESSION_SEGMENT, changeArray(dbTemporari.getSegmentTerusan(provinsi, ruas, "Segment", "Update", "", jenisSurvey)));
        session.setIndexData(Session.SESSION_LAHANKIRI, changeArray(dbTemporari.getSegmentTerusan(provinsi, ruas, "Lahan", "kiri", "", jenisSurvey)));
        session.setIndexData(Session.SESSION_LAHANKANAN, changeArray(dbTemporari.getSegmentTerusan(provinsi, ruas, "Lahan", "kanan", "", jenisSurvey)));
        session.setIndexData(Session.SESSION_SALURANKIRI, changeArray(dbTemporari.getSegmentTerusan(provinsi, ruas, "Saluran", "kiri", "", jenisSurvey)));
        session.setIndexData(Session.SESSION_SALURANKANAN, changeArray(dbTemporari.getSegmentTerusan(provinsi, ruas, "Saluran", "kanan", "", jenisSurvey)));
        session.setIndexData(Session.SESSION_BAHUKIRI, changeArray(dbTemporari.getSegmentTerusan(provinsi, ruas, "Bahu", "kiri", "", jenisSurvey)));
        session.setIndexData(Session.SESSION_BAHUKANAN, changeArray(dbTemporari.getSegmentTerusan(provinsi, ruas, "Bahu", "kanan", "", jenisSurvey)));
        session.setIndexData(Session.SESSION_MEDIAN, changeArray(dbTemporari.getSegmentTerusan(provinsi, ruas, "Median", "", "", jenisSurvey)));


        session.setIndexData(Session.SESSION_L1, changeArray(dbTemporari.getSegmentTerusan(provinsi, ruas, "Lane", "kiri", "L1", jenisSurvey)));
        session.setIndexData(Session.SESSION_L2, changeArray(dbTemporari.getSegmentTerusan(provinsi, ruas, "Lane", "kiri", "L2", jenisSurvey)));
        session.setIndexData(Session.SESSION_L3, changeArray(dbTemporari.getSegmentTerusan(provinsi, ruas, "Lane", "kiri", "L3", jenisSurvey)));
        session.setIndexData(Session.SESSION_L4, changeArray(dbTemporari.getSegmentTerusan(provinsi, ruas, "Lane", "kiri", "L4", jenisSurvey)));
        session.setIndexData(Session.SESSION_L5, changeArray(dbTemporari.getSegmentTerusan(provinsi, ruas, "Lane", "kiri", "L5", jenisSurvey)));
        session.setIndexData(Session.SESSION_L6, changeArray(dbTemporari.getSegmentTerusan(provinsi, ruas, "Lane", "kiri", "L6", jenisSurvey)));
        session.setIndexData(Session.SESSION_L7, changeArray(dbTemporari.getSegmentTerusan(provinsi, ruas, "Lane", "kiri", "L7", jenisSurvey)));
        session.setIndexData(Session.SESSION_L8, changeArray(dbTemporari.getSegmentTerusan(provinsi, ruas, "Lane", "kiri", "L8", jenisSurvey)));
        session.setIndexData(Session.SESSION_L9, changeArray(dbTemporari.getSegmentTerusan(provinsi, ruas, "Lane", "kiri", "L9", jenisSurvey)));
        session.setIndexData(Session.SESSION_L10, changeArray(dbTemporari.getSegmentTerusan(provinsi, ruas, "Lane", "kiri", "L10", jenisSurvey)));

        session.setIndexData(Session.SESSION_R1, changeArray(dbTemporari.getSegmentTerusan(provinsi, ruas, "Lane", "kanan", "R1", jenisSurvey)));
        session.setIndexData(Session.SESSION_R2, changeArray(dbTemporari.getSegmentTerusan(provinsi, ruas, "Lane", "kanan", "R2", jenisSurvey)));
        session.setIndexData(Session.SESSION_R3, changeArray(dbTemporari.getSegmentTerusan(provinsi, ruas, "Lane", "kanan", "R3", jenisSurvey)));
        session.setIndexData(Session.SESSION_R4, changeArray(dbTemporari.getSegmentTerusan(provinsi, ruas, "Lane", "kanan", "R4", jenisSurvey)));
        session.setIndexData(Session.SESSION_R5, changeArray(dbTemporari.getSegmentTerusan(provinsi, ruas, "Lane", "kanan", "R5", jenisSurvey)));
        session.setIndexData(Session.SESSION_R6, changeArray(dbTemporari.getSegmentTerusan(provinsi, ruas, "Lane", "kanan", "R6", jenisSurvey)));
        session.setIndexData(Session.SESSION_R7, changeArray(dbTemporari.getSegmentTerusan(provinsi, ruas, "Lane", "kanan", "R7", jenisSurvey)));
        session.setIndexData(Session.SESSION_R8, changeArray(dbTemporari.getSegmentTerusan(provinsi, ruas, "Lane", "kanan", "R8", jenisSurvey)));
        session.setIndexData(Session.SESSION_R9, changeArray(dbTemporari.getSegmentTerusan(provinsi, ruas, "Lane", "kanan", "R9", jenisSurvey)));
        session.setIndexData(Session.SESSION_R10, changeArray(dbTemporari.getSegmentTerusan(provinsi, ruas, "Lane", "kanan", "R10", jenisSurvey)));


    }

    //Detail


    public void saveLahanDetail(DataLahan dataLahan, String asal){

        dbLahan.updateLahan(dataLahan);

        String tipeSurvey = dbTemporari.cekSurveyTemporariTipe(dataLahan.getNoprov(), dataLahan.getNoruas(), "Lahan", dataLahan.getPosisi(), "");
        String inputSurvey = dbTemporari.cekSurveyTemporari(dataLahan.getNoprov(), dataLahan.getNoruas());

        addDetailTempo(inputSurvey, "Lahan", dataLahan.getNoprov(), dataLahan.getNoruas(), dataLahan.getSubsegment(), dataLahan.getPosisi(), "", helperList.getStatFoto(helperList.getImagePath(dataLahan.getGambarLahan())));

        if(!tipeSurvey.equals("Detail")){
            addDetail("Lahan", dataLahan.getNoprov(), dataLahan.getNoruas(), dataLahan.getSubsegment(), dataLahan.getPosisi(), "", tipeSurvey);
        }

        setOnsave(asal, "Lahan");

    }

    public void saveSaluranDetail(DataSaluran dataSaluran, String asal){

        dbSaluran.updateSaluran(dataSaluran);

        String tipeSurvey = dbTemporari.cekSurveyTemporariTipe(dataSaluran.getNoprov(), dataSaluran.getNoruas(), "Saluran", dataSaluran.getPosisi(), "");
        String inputSurvey = dbTemporari.cekSurveyTemporari(dataSaluran.getNoprov(), dataSaluran.getNoruas());

        addDetailTempo(inputSurvey,"Saluran", dataSaluran.getNoprov(), dataSaluran.getNoruas(), dataSaluran.getSubsegment(), dataSaluran.getPosisi(), "", helperList.getStatFoto(helperList.getImagePath(dataSaluran.getGambarSaluran())));

        if(!tipeSurvey.equals("Detail")){

            addDetail("Saluran", dataSaluran.getNoprov(), dataSaluran.getNoruas(), dataSaluran.getSubsegment(), dataSaluran.getPosisi(), "", tipeSurvey);

        }

        setOnsave(asal, "Saluran");

    }

    public void saveBahuDetail(DataBahu dataBahu, String asal){

        dbBahu.updateBahu(dataBahu);

        String tipeSurvey = dbTemporari.cekSurveyTemporariTipe(dataBahu.getNoprov(), dataBahu.getNoruas(), "Bahu", dataBahu.getPosisi(),"");
        String inputSurvey = dbTemporari.cekSurveyTemporari(dataBahu.getNoprov(), dataBahu.getNoruas());

        addDetailTempo(inputSurvey,"Bahu", dataBahu.getNoprov(), dataBahu.getNoruas(), dataBahu.getSubSegment(), dataBahu.getPosisi(), "", helperList.getStatFoto(helperList.getImagePath(dataBahu.getGambarBahu())));

        if(!tipeSurvey.equals("Detail")){

            addDetail("Bahu", dataBahu.getNoprov(), dataBahu.getNoruas(), dataBahu.getSubSegment(), dataBahu.getPosisi(), "", tipeSurvey);

        }

        setOnsave(asal, "Bahu");

    }

    public void saveMedianDetail(DataMedian dataMedian, String asal){

        dbMedian.updateMedian(dataMedian);

        String tipeSurvey = dbTemporari.cekSurveyTemporariTipe(dataMedian.getNoprov(), dataMedian.getNoruas(), "Median", "", "");
        String inputSurvey = dbTemporari.cekSurveyTemporari(dataMedian.getNoprov(), dataMedian.getNoruas());

        addDetailTempo(inputSurvey, "Median", dataMedian.getNoprov(), dataMedian.getNoruas(), dataMedian.getSubsegment(), "", "", helperList.getStatFoto(helperList.getImagePath(dataMedian.getGambarMedian())));

        if(!tipeSurvey.equals("Detail")){

            addDetail("Median", dataMedian.getNoprov(), dataMedian.getNoruas(), dataMedian.getSubsegment(), "", "", tipeSurvey);

        }

        setOnsave(asal, "Median");

    }

    public void saveLaneDetail(DataLane dataLane, String asal){

        dbLane.updateLane(dataLane);

        String tipeSurvey = dbTemporari.cekSurveyTemporariTipe(dataLane.getNoprov(), dataLane.getNoruas(), "Lane", dataLane.getPosisi(), dataLane.getUrut());
        String inputSurvey = dbTemporari.cekSurveyTemporari(dataLane.getNoprov(), dataLane.getNoruas());

        addDetailTempo(inputSurvey, "Lane", dataLane.getNoprov(), dataLane.getNoruas(), dataLane.getSubsegment(), dataLane.getPosisi(), dataLane.getUrut(), helperList.getStatFoto(helperList.getImagePath(dataLane.getGambarLane())));

        if(!tipeSurvey.equals("Detail")){

            addDetail("Lane", dataLane.getNoprov(), dataLane.getNoruas(), dataLane.getSubsegment(), dataLane.getPosisi(), dataLane.getUrut(), tipeSurvey);

        }

        setOnsave(asal, "Lane");

    }

    public void saveSegmentDetail(DataSegmen dataSegmen, String asal){

        String tipeSurvey = dbTemporari.cekSurveyTemporariTipe(dataSegmen.getNoprov(), dataSegmen.getNoruas(), "Segment", "Update", "");
        String inputSurvey = dbTemporari.cekSurveyTemporari(dataSegmen.getNoprov(), dataSegmen.getNoruas());

        DataSegmen dataSegmenTempo = dbSegmen.getSegmen(dataSegmen.getNoprov(), dataSegmen.getNoruas(), dataSegmen.getNosegment(), dataSegmen.getSubsegment());


        cekPerubahan(dataSegmenTempo.getSegmentl1(), dataSegmen.getSegmentl1(), dataSegmenTempo.getSubsegment(),"kiri", "L1", inputSurvey);
        cekPerubahan(dataSegmenTempo.getSegmentl2(), dataSegmen.getSegmentl2(), dataSegmenTempo.getSubsegment(),"kiri", "L2", inputSurvey);
        cekPerubahan(dataSegmenTempo.getSegmentl3(), dataSegmen.getSegmentl3(), dataSegmenTempo.getSubsegment(),"kiri", "L3", inputSurvey);
        cekPerubahan(dataSegmenTempo.getSegmentl4(), dataSegmen.getSegmentl4(), dataSegmenTempo.getSubsegment(),"kiri", "L4", inputSurvey);
        cekPerubahan(dataSegmenTempo.getSegmentl5(), dataSegmen.getSegmentl5(), dataSegmenTempo.getSubsegment(),"kiri", "L5", inputSurvey);
        cekPerubahan(dataSegmenTempo.getSegmentl6(), dataSegmen.getSegmentl6(), dataSegmenTempo.getSubsegment(),"kiri", "L6", inputSurvey);
        cekPerubahan(dataSegmenTempo.getSegmentl7(), dataSegmen.getSegmentl7(), dataSegmenTempo.getSubsegment(),"kiri", "L7", inputSurvey);
        cekPerubahan(dataSegmenTempo.getSegmentl8(), dataSegmen.getSegmentl8(), dataSegmenTempo.getSubsegment(),"kiri", "L8", inputSurvey);
        cekPerubahan(dataSegmenTempo.getSegmentl9(), dataSegmen.getSegmentl9(), dataSegmenTempo.getSubsegment(),"kiri", "L9", inputSurvey);
        cekPerubahan(dataSegmenTempo.getSegmentl10(), dataSegmen.getSegmentl10(), dataSegmenTempo.getSubsegment(),"kiri", "L10", inputSurvey);

        cekPerubahan(dataSegmenTempo.getSegmentr1(), dataSegmen.getSegmentr1(), dataSegmenTempo.getSubsegment(),"kanan", "R1", inputSurvey);
        cekPerubahan(dataSegmenTempo.getSegmentr2(), dataSegmen.getSegmentr2(), dataSegmenTempo.getSubsegment(),"kanan", "R2", inputSurvey);
        cekPerubahan(dataSegmenTempo.getSegmentr3(), dataSegmen.getSegmentr3(), dataSegmenTempo.getSubsegment(),"kanan", "R3", inputSurvey);
        cekPerubahan(dataSegmenTempo.getSegmentr4(), dataSegmen.getSegmentr4(), dataSegmenTempo.getSubsegment(),"kanan", "R4", inputSurvey);
        cekPerubahan(dataSegmenTempo.getSegmentr5(), dataSegmen.getSegmentr5(), dataSegmenTempo.getSubsegment(),"kanan", "R5", inputSurvey);
        cekPerubahan(dataSegmenTempo.getSegmentr6(), dataSegmen.getSegmentr6(), dataSegmenTempo.getSubsegment(),"kanan", "R6", inputSurvey);
        cekPerubahan(dataSegmenTempo.getSegmentr7(), dataSegmen.getSegmentr7(), dataSegmenTempo.getSubsegment(),"kanan", "R7", inputSurvey);
        cekPerubahan(dataSegmenTempo.getSegmentr8(), dataSegmen.getSegmentr8(), dataSegmenTempo.getSubsegment(),"kanan", "R8", inputSurvey);
        cekPerubahan(dataSegmenTempo.getSegmentr9(), dataSegmen.getSegmentr9(), dataSegmenTempo.getSubsegment(),"kanan", "R9", inputSurvey);
        cekPerubahan(dataSegmenTempo.getSegmentr10(), dataSegmen.getSegmentr10(), dataSegmenTempo.getSubsegment(),"kanan", "R10", inputSurvey);



        dbSegmen.updateSegmen(dataSegmen);

        addDetailTempo(inputSurvey,"Segment", dataSegmen.getNoprov(), dataSegmen.getNoruas(), dataSegmen.getSubsegment(), "Update", "", 1);
        if(!tipeSurvey.equals("Detail")){

            addDetail("Segment", dataSegmen.getNoprov(), dataSegmen.getNoruas(), dataSegmen.getSubsegment(), "Update", "", tipeSurvey);

        }

        setOnsave(asal, "Segment");

    }


    //SINKRON

    public void saveLahanSinkron(DataLahan dataLahan){

        int foto;

        DataTemporari dataTemporari = dbTemporari.getTemporari(dataLahan.getNoprov(), dataLahan.getNoruas(), dataLahan.getSubsegment(), "Lahan", dataLahan.getPosisi(), "");
        foto = helperList.getStatFoto(helperList.getImagePath(dataLahan.getGambarLahan()));
        dbLahan.saveLahanNormal(dataLahan, dataTemporari.getSubsegment(), dataTemporari.getSubsegmentakhir(), dataTemporari.getJenis());
        dbTemporari.saveTemporariTerusan("Lahan", dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getPosisi(), "", dataTemporari.getSubsegment(), dataTemporari.getSubsegmentakhir(), dataTemporari.getJenis(), String.valueOf(foto));

        setOnsave("Sinkron", "Lahan");
    }

    public void saveSaluranSinkron(DataSaluran dataSaluran){

        int foto;

        DataTemporari dataTemporari = dbTemporari.getTemporari(dataSaluran.getNoprov(), dataSaluran.getNoruas(), dataSaluran.getSubsegment(), "Saluran", dataSaluran.getPosisi(), "");
        foto = helperList.getStatFoto(helperList.getImagePath(dataSaluran.getGambarSaluran()));
        dbSaluran.saveSaluranNormal(dataSaluran, dataTemporari.getSubsegment(), dataTemporari.getSubsegmentakhir(), dataTemporari.getJenis());
        dbTemporari.saveTemporariTerusan("Saluran", dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getPosisi(), "", dataTemporari.getSubsegment(), dataTemporari.getSubsegmentakhir(), dataTemporari.getJenis(), String.valueOf(foto));

        setOnsave("Sinkron", "Saluran");
    }

    public void saveBahuSinkron(DataBahu dataBahu){

        int foto;

        DataTemporari dataTemporari = dbTemporari.getTemporari(dataBahu.getNoprov(), dataBahu.getNoruas(), dataBahu.getSubSegment(), "Bahu", dataBahu.getPosisi(), "");
        foto = helperList.getStatFoto(helperList.getImagePath(dataBahu.getGambarBahu()));
        dbBahu.saveBahuNormal(dataBahu, dataTemporari.getNosegment(), dataTemporari.getSegmentakhir(), dataTemporari.getJenis());
        dbTemporari.saveTemporariTerusan("Bahu", dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getPosisi(), "", dataTemporari.getSubsegment(), dataTemporari.getSubsegmentakhir(), dataTemporari.getJenis(), String.valueOf(foto));

        setOnsave("Sinkron", "Bahu");
    }

    public void saveMedianSinkron(DataMedian dataMedian){

        int foto;

        DataTemporari dataTemporari = dbTemporari.getTemporari(dataMedian.getNoprov(), dataMedian.getNoruas(), dataMedian.getSubsegment(), "Median", "", "");
        foto = helperList.getStatFoto(helperList.getImagePath(dataMedian.getGambarMedian()));
        dbMedian.saveMedianNormal(dataMedian, dataTemporari.getNosegment(), dataTemporari.getSegmentakhir(), dataTemporari.getJenis());
        dbTemporari.saveTemporariTerusan("Median", dataTemporari.getNoprov(), dataTemporari.getNoruas(), "", "", dataTemporari.getSubsegment(), dataTemporari.getSubsegmentakhir(), dataTemporari.getJenis(), String.valueOf(foto));

        setOnsave("Sinkron", "Median");
    }

    public void saveLaneSinkron(DataLane dataLane){

        int foto;

        DataTemporari dataTemporari = dbTemporari.getTemporari(dataLane.getNoprov(), dataLane.getNoruas(), dataLane.getSubsegment(), "Lane", dataLane.getPosisi(), dataLane.getUrut());
        foto = helperList.getStatFoto(helperList.getImagePath(dataLane.getGambarLane()));
        TaskSaveLaneTerusan taskSaveLaneTerusan = new TaskSaveLaneTerusan(context, dataLane, dataTemporari.getJenis(), dataTemporari.getSubsegmentakhir(), String.valueOf(foto));
        taskSaveLaneTerusan.execute("bisa", "bisa", "bisa");
        setOnsave("Sinkron", "Lane");

    }

    public void saveSegmentSinkron(final DataSegmen dataSegmen){

        final DataTemporari dataTemporari = dbTemporari.getTemporari(dataSegmen.getNoprov(), dataSegmen.getNoruas(), dataSegmen.getSubsegment(), "Segment", "Update", "");

        DataSegmen dataSegmenTempo = dbSegmen.getSegmen(dataSegmen.getNoprov(), dataSegmen.getNoruas(), dataSegmen.getNosegment(), dataSegmen.getSubsegment());
        cekPerubahan(dataSegmenTempo.getSegmentl1(), dataSegmen.getSegmentl1(), dataSegmenTempo.getSubsegment(), "kiri", "L1", dataTemporari.getJenis());
        cekPerubahan(dataSegmenTempo.getSegmentl2(), dataSegmen.getSegmentl2(), dataSegmenTempo.getSubsegment(),"kiri", "L2", dataTemporari.getJenis());
        cekPerubahan(dataSegmenTempo.getSegmentl3(), dataSegmen.getSegmentl3(), dataSegmenTempo.getSubsegment(),"kiri", "L3", dataTemporari.getJenis());
        cekPerubahan(dataSegmenTempo.getSegmentl4(), dataSegmen.getSegmentl4(), dataSegmenTempo.getSubsegment(),"kiri", "L4", dataTemporari.getJenis());
        cekPerubahan(dataSegmenTempo.getSegmentl5(), dataSegmen.getSegmentl5(), dataSegmenTempo.getSubsegment(),"kiri", "L5", dataTemporari.getJenis());
        cekPerubahan(dataSegmenTempo.getSegmentl6(), dataSegmen.getSegmentl6(), dataSegmenTempo.getSubsegment(),"kiri", "L6", dataTemporari.getJenis());
        cekPerubahan(dataSegmenTempo.getSegmentl7(), dataSegmen.getSegmentl7(), dataSegmenTempo.getSubsegment(),"kiri", "L7", dataTemporari.getJenis());
        cekPerubahan(dataSegmenTempo.getSegmentl8(), dataSegmen.getSegmentl8(), dataSegmenTempo.getSubsegment(),"kiri", "L8", dataTemporari.getJenis());
        cekPerubahan(dataSegmenTempo.getSegmentl9(), dataSegmen.getSegmentl9(), dataSegmenTempo.getSubsegment(),"kiri", "L9", dataTemporari.getJenis());
        cekPerubahan(dataSegmenTempo.getSegmentl10(), dataSegmen.getSegmentl10(), dataSegmenTempo.getSubsegment(),"kiri", "L10", dataTemporari.getJenis());

        cekPerubahan(dataSegmenTempo.getSegmentr1(), dataSegmen.getSegmentr1(), dataSegmenTempo.getSubsegment(),"kanan", "R1", dataTemporari.getJenis());
        cekPerubahan(dataSegmenTempo.getSegmentr2(), dataSegmen.getSegmentr2(), dataSegmenTempo.getSubsegment(),"kanan", "R2", dataTemporari.getJenis());
        cekPerubahan(dataSegmenTempo.getSegmentr3(), dataSegmen.getSegmentr3(), dataSegmenTempo.getSubsegment(),"kanan", "R3", dataTemporari.getJenis());
        cekPerubahan(dataSegmenTempo.getSegmentr4(), dataSegmen.getSegmentr4(), dataSegmenTempo.getSubsegment(),"kanan", "R4", dataTemporari.getJenis());
        cekPerubahan(dataSegmenTempo.getSegmentr5(), dataSegmen.getSegmentr5(), dataSegmenTempo.getSubsegment(),"kanan", "R5", dataTemporari.getJenis());
        cekPerubahan(dataSegmenTempo.getSegmentr6(), dataSegmen.getSegmentr6(), dataSegmenTempo.getSubsegment(),"kanan", "R6", dataTemporari.getJenis());
        cekPerubahan(dataSegmenTempo.getSegmentr7(), dataSegmen.getSegmentr7(), dataSegmenTempo.getSubsegment(),"kanan", "R7", dataTemporari.getJenis());
        cekPerubahan(dataSegmenTempo.getSegmentr8(), dataSegmen.getSegmentr8(), dataSegmenTempo.getSubsegment(),"kanan", "R8", dataTemporari.getJenis());
        cekPerubahan(dataSegmenTempo.getSegmentr9(), dataSegmen.getSegmentr9(), dataSegmenTempo.getSubsegment(),"kanan", "R9", dataTemporari.getJenis());
        cekPerubahan(dataSegmenTempo.getSegmentr10(), dataSegmen.getSegmentr10(), dataSegmenTempo.getSubsegment(),"kanan", "R10", dataTemporari.getJenis());

        dbSegmen.updateSegmen(dataSegmen);


        if(dataTemporari.getSubsegment()!=dataTemporari.getSubsegmentakhir()) {

            TaskSaveSegmentTerusan taskSaveSegmentTerusan = new TaskSaveSegmentTerusan(context, dataSegmen, dataTemporari.getJenis(), dataTemporari.getSubsegmentakhir(), 0, new SendId() {
                @Override
                public void hapusGambar(int id) {

                    dbSegmen.saveSegmentNormal(dataSegmen, dataTemporari.getSubsegment(), dataTemporari.getSubsegmentakhir(), dataTemporari.getJenis());

                }
            });

            taskSaveSegmentTerusan.execute("", "", "");
        }

        dbTemporari.saveTemporariTerusan("Segment", dataTemporari.getNoprov(), dataTemporari.getNoruas(), "Update", "", dataTemporari.getSubsegment(), dataTemporari.getSubsegmentakhir(), dataTemporari.getJenis(), "1");

        setOnsave("Sinkron", "Segment");

    }




    private void addDetailTempo(String tipeSurvey, String tipe, String provinsi, String ruas, float subSegment, String posisi, String lajur, int foto){

        int segment = dbSpinner.getSegment(provinsi, ruas, subSegment);

        DataTemporari dataTemporariAsli = new DataTemporari(provinsi, ruas, segment, subSegment, tipe, posisi, lajur, tipeSurvey, segment, subSegment, String.valueOf(foto), "0");
        dbTemporari.postTemporari(dataTemporariAsli);

    }



    private void addDetail(String tipe, String provinsi, String ruas, float subSegment, String posisi, String lajur, String tipeSurvey){

        ArrayList<Float> listInteger = dbTemporari.getSegmentTerusan(provinsi, ruas, tipe, posisi, lajur, tipeSurvey);
        listInteger = addIndexManual(tipeSurvey, listInteger, subSegment);


        addSebelum(tipe, provinsi, ruas, subSegment, posisi, lajur, tipeSurvey, listInteger);
        addSesudah(tipe, provinsi, ruas, subSegment, posisi, lajur, tipeSurvey, listInteger);



    }

    private void addSebelum(String tipe, String provinsi, String ruas, float subSegment, String posisi, String lajur, String tipeSurvey, ArrayList<Float> list){

        float subSebelum, subSebelumMaks;
        int segmentSebelum, segmentSebelumMaks;
        int indexSubsegment = list.indexOf(subSegment);
        int foto;



        if(indexSubsegment!=0){

            subSebelum = list.get(indexSubsegment-1);

            if(tipeSurvey.equals("Normal")){
                subSebelumMaks = (float) (subSegment-0.1);
            }else{
                subSebelumMaks = (float) (subSegment+ 0.1);
            }

            segmentSebelum = dbSpinner.getSegment(provinsi, ruas, subSebelum);
            segmentSebelumMaks = dbSpinner.getSegment(provinsi, ruas, subSebelumMaks);

            foto = getFotoTipe(tipe, provinsi, ruas, segmentSebelum, subSebelum, posisi, lajur);

            if(tipe.equals("Lane")){

                DataLane dataLane = dbLane.getLane(provinsi, ruas, segmentSebelum, subSebelum,posisi, lajur);
                TaskSaveLaneTerusan taskSaveLaneTerusan = new TaskSaveLaneTerusan(context, dataLane, tipeSurvey, subSebelumMaks, String.valueOf(foto));
                taskSaveLaneTerusan.execute("bisa", "bisa", "bisa");

            }else {

                DataTemporari dataTemporari = new DataTemporari(provinsi, ruas, segmentSebelum, subSebelum, tipe, posisi, lajur, tipeSurvey, segmentSebelumMaks, subSebelumMaks, String.valueOf(foto), "0");
                dbTemporari.postTemporari(dataTemporari);

            }

        }
    }

    private void addSesudah(String tipe, String provinsi, String ruas, float subsegment, String posisi, String lajur, String tipeSurvey, ArrayList<Float> list){

        int segmentSudah, segmentSesudahMaks;
        float subSegmentSesudah, subSegmentSesudahMaks, subMaksimal;
        int indexSegment = list.indexOf(subsegment);
        int foto;



        if(tipeSurvey.equals("Normal")){
            subMaksimal = dbSpinner.getMaksSegment(provinsi, ruas);
            subSegmentSesudah = (float) (subsegment+0.1);
        }else{
            subSegmentSesudah = (float) (subsegment-0.1);
            subMaksimal = 1;
        }


        if(subsegment != subMaksimal){

            if(indexSegment==list.size()-1){
                subSegmentSesudahMaks = subMaksimal;
            }else if(tipeSurvey.equals("Normal")){
                subSegmentSesudahMaks = (float) (list.get(indexSegment+1)-0.1);
            }else {
                subSegmentSesudahMaks = (float) (list.get(indexSegment+1)+0.1);
            }

            segmentSudah = dbSpinner.getSegment(provinsi, ruas, subSegmentSesudah);
            segmentSesudahMaks = dbSpinner.getSegment(provinsi, ruas, subSegmentSesudahMaks);

            foto = getFotoTipe(tipe, provinsi, ruas, segmentSudah, subSegmentSesudah, posisi, lajur);


            if(tipe.equals("Lane")){
                DataLane dataLane = dbLane.getLane(provinsi, ruas, segmentSudah, subSegmentSesudah,posisi, lajur);
                TaskSaveLaneTerusan taskSaveLaneTerusan = new TaskSaveLaneTerusan(context, dataLane, tipeSurvey, subSegmentSesudahMaks, String.valueOf(foto));
                taskSaveLaneTerusan.execute("bisa", "bisa", "bisa");
            }else {
                DataTemporari dataTemporari = new DataTemporari(provinsi, ruas, segmentSudah, subSegmentSesudah, tipe, posisi, lajur, tipeSurvey, segmentSesudahMaks, subSegmentSesudahMaks, String.valueOf(foto), "0");
                dbTemporari.postTemporari(dataTemporari);
            }

        }
    }


    private ArrayList<Float> addIndexManual(String tipe, ArrayList<Float> list, float indexadd){

        if(!list.contains(indexadd)){
            list.add(indexadd);
        }

        if(tipe.equals("Normal")){
            Collections.sort(list);
        }else{
            Collections.sort(list, Collections.<Float>reverseOrder());
        }

        return list;
    }






    //add data ke session
    private ArrayList<Float> addIndex(String session, float subSegment){

        ArrayList<Float>arrayIndex  = getIndexData(session);

        if(arrayIndex != null){
            if(!arrayIndex.contains(subSegment)){
                arrayIndex.add(subSegment);
            }
        }else {
            arrayIndex.add(subSegment);
        }

        Sorting(arrayIndex, jenisSurvey);

        return  arrayIndex;


    }

    //get data dari session
    private ArrayList<Float> getIndexData(String key){

        ArrayList<Float> list = new ArrayList<>();

        Set<String> mySet = session.getIndexData(key);

        if(mySet!=null) {

            for (String myString : mySet) {
                list.add(Float.valueOf(myString));
            }

        }

        Sorting(list, jenisSurvey);

        return list;
    }

    //ubah data dari string to integer
    private ArrayList<String> changeArray(ArrayList<Float> list){

        ArrayList<String> listReturn = new ArrayList<>();

        for(Float value : list){
            listReturn.add(String.valueOf(value));
        }

        return listReturn;
    }

    //sorting data by tipe survey
    private void Sorting(ArrayList<Float> list, int params){

        if(params==0){
            Collections.sort(list);
        }else{
            Collections.sort(list, Collections.<Float>reverseOrder());
        }

    }


    //get position lihat tabel
    public int getPosisiTabel(String tipe){

        int value = 0;

        switch (tipe){
            case "Segment" : value = 0;
                break;

            case "Lahan" : value = 1;
                break;

            case "Saluran" : value = 2;
                break;

            case "Bahu" : value = 3;
                break;

            case "Median" : value = 4;
                break;

            case "Lane" : value = 5;
                break;

        }


        return value;


    }

    private String getStringLane(String lajur){

        String value = "";

        switch (lajur){

            case "L1" : value =  Session.SESSION_L1;
            break;

            case "L2" : value =  Session.SESSION_L2;
                break;

            case "L3" : value =  Session.SESSION_L3;
                break;

            case "L4" : value =  Session.SESSION_L4;
                break;

            case "L5" : value =  Session.SESSION_L5;
                break;

            case "L6" : value =  Session.SESSION_L6;
                break;

            case "L7" : value =  Session.SESSION_L7;
                break;

            case "L8" : value =  Session.SESSION_L8;
                break;

            case "L9" : value =  Session.SESSION_L9;
                break;

            case "L10" : value =  Session.SESSION_L10;
                break;

            case "R1" : value =  Session.SESSION_R1;
                break;

            case "R2" : value =  Session.SESSION_R2;
                break;

            case "R3" : value =  Session.SESSION_R3;
                break;

            case "R4" : value =  Session.SESSION_R4;
                break;

            case "R5" : value =  Session.SESSION_R5;
                break;

            case "R6" : value =  Session.SESSION_R6;
                break;

            case "R7" : value =  Session.SESSION_R7;
                break;

            case "R8" : value =  Session.SESSION_R8;
                break;

            case "R9" : value =  Session.SESSION_R9;
                break;

            case "R10" : value =  Session.SESSION_R10;
                break;

        }

        return value;

    }


    private int getFotoTipe(String tipe, String provinsi, String ruas, int segment, float subsegment, String posisi, String lajur ) {

        int foto;

        switch (tipe) {
            case "Bahu":

                DataBahu dataBahu = dbBahu.getBahu(provinsi, ruas, segment, subsegment, posisi);
                foto = helperList.getStatFoto(helperList.getImagePath(dataBahu.getGambarBahu()));

                break;

            case "Lahan":

                DataLahan dataLahan = dbLahan.getLahan(provinsi, ruas, segment, subsegment, posisi);
                foto = helperList.getStatFoto(helperList.getImagePath(dataLahan.getGambarLahan()));

                break;

            case "Saluran":

                DataSaluran dataSaluran = dbSaluran.getSaluran(provinsi, ruas, segment, subsegment, posisi);
                foto = helperList.getStatFoto(helperList.getImagePath(dataSaluran.getGambarSaluran()));

                break;

            case "Median":

                DataMedian dataMedian = dbMedian.getMedian(provinsi, ruas, segment, subsegment);
                foto = helperList.getStatFoto(helperList.getImagePath(dataMedian.getGambarMedian()));

                break;

            case "Lane":

                DataLane dataLane = dbLane.getLane(provinsi, ruas, segment, subsegment, posisi, lajur);
                foto = helperList.getStatFoto(helperList.getImagePath(dataLane.getGambarLane()));

                break;

            case "Segment" :
                foto = 1;
                break;

                default: foto = 0;

        }

        return foto;

    }

    private float getSubSegmentAkhir(String tipeSurvey, float subSegment){

        float returnData;

        if(tipeSurvey.equals("Normal")){
            returnData = (float) (subSegment-0.1);
        }else{
            returnData  = (float) (subSegment+0.1);
        }

        return returnData;
    }

    /*private void addSebelumLane(String tipe, String provinsi, String ruas, int segment, String posisi, String lajur, String tipeSurvey, ArrayList<Integer> list, ArrayList<Integer> listDetail){

        int segmentSebelum, segmentSebelumMaks;
        int indexSegment = list.indexOf(segment);
        int foto;



        if(indexSegment!=0 && !listDetail.contains(list.get(indexSegment-1))){

            segmentSebelum = list.get(indexSegment-1);

            foto = getFotoTipe(tipe, provinsi, ruas, segmentSebelum, posisi, lajur);

            if(tipeSurvey.equals("Normal")){
                segmentSebelumMaks = segment-1;
            }else{
                segmentSebelumMaks = segment+1;
            }

            DataLane dataLane = dbLane.getLaneTerusan(provinsi, ruas, segmentSebelum,posisi, lajur);

            TaskSaveLaneTerusan taskSaveLaneTerusan = new TaskSaveLaneTerusan(context, dataLane, session.getTipesurvey(), segmentSebelumMaks, String.valueOf(foto));
            taskSaveLaneTerusan.execute("bisa", "bisa", "bisa");

            DataTemporari dataTemporari = new DataTemporari(provinsi, ruas, segmentSebelum, tipe, posisi, lajur, tipeSurvey, segmentSebelumMaks, String.valueOf(foto), "0");
            dbTemporari.postTemporari(dataTemporari);

        }
    }

    private void addSesudahLane(String tipe, String provinsi, String ruas, int segment, String posisi, String lajur, String tipeSurvey, ArrayList<Integer> list, ArrayList<Integer> listDetail){

        int segmentSesudah, segmentSesudahMaks;
        int indexSegment = list.indexOf(segment);
        int foto, maks;

        if(tipeSurvey.equals("Normal")){
            maks = dbSpinner.getMaksSegment(provinsi, ruas);
            segmentSesudah = segment+1;
        }else{
            segmentSesudah = segment-1;
            maks = 1;
        }

        if(segment != maks){

            if(indexSegment==list.size()-1){
                segmentSesudahMaks = maks;
            }else if(tipeSurvey.equals("Normal")){
                segmentSesudahMaks = list.get(indexSegment+1)-1;
            }else {
                segmentSesudahMaks = list.get(indexSegment+1)+1;
            }


            foto = getFotoTipe(tipe, provinsi, ruas, segmentSesudah, posisi, lajur);


            DataTemporari dataTemporari = new DataTemporari(provinsi, ruas, segmentSesudah, tipe, posisi, lajur, tipeSurvey, segmentSesudahMaks, String.valueOf(foto), "0");
            dbTemporari.postTemporari(dataTemporari);

        }

    }

    private void loopingSegmentEnd(int index, final ArrayList<Integer> arrayList){

        final int segmentAkhir;
        final int segment = arrayList.get(index);

        if(session.getTipesurvey().equals("Normal")){
            segmentAkhir = arrayList.get(index+1)-1;
        }else{
            segmentAkhir = arrayList.get(index+1)+1;
        }

        final DataSegmen dataSegmentNormal = dbSegmen.getSegmen(session.getKodeprov(), session.getNoruas(), segment);
        dbTemporari.saveTemporariTerusan("Segment", dataSegmentNormal.getNoprov(), dataSegmentNormal.getNoruas(), "Update", "", dataSegmentNormal.getNosegment(), segmentAkhir, session.getTipesurvey(), "1");


        TaskSaveSegmentTerusan taskSaveSegmentTerusan = new TaskSaveSegmentTerusan(context, dataSegmentNormal, session.getTipesurvey(), segmentAkhir, index, new SendId() {
            @Override
            public void hapusGambar(int id) {

                if(id<arrayList.size()-1) {

                    loopingSegment(id, arrayList);
                    dbSegmen.saveSegmentNormal(dataSegmentNormal, segment, segmentAkhir, session.getTipesurvey());

                }

            }
        });

        taskSaveSegmentTerusan.execute("", "","");


    }

     private ArrayList<Integer> groupList(ArrayList<Integer> listTipe, ArrayList<Integer> listDetail, String tipeSurvey){

        for(int i=0; i<listDetail.size(); i++){
            if(!listTipe.contains(listDetail.get(i))){
                listTipe.add(listDetail.get(i));
            }
        }

        if(tipeSurvey.equals("Normal")){
            Collections.sort(listTipe);
        }else{
            Collections.sort(listTipe, Collections.<Integer>reverseOrder());
        }

        return listTipe;
    }*/



}
