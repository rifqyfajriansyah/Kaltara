package com.example.roadmanagement.kaltara.FormTab.Helper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.roadmanagement.kaltara.FormTerusan.TaskSaveLaneTerusan;
import com.example.roadmanagement.kaltara.FormTerusan.TaskSaveSegmentTerusan;
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

import java.util.ArrayList;
import java.util.List;

public class SaveLogic {

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


    public SaveLogic(Context context){

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

    public void saveLahan(DataLahan dataLahan){

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

    }

    public void saveBahu(DataBahu dataBahu){

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

    }

    public void saveSaluran(DataSaluran dataSaluran){

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

    }

    public void saveMedian(DataMedian dataMedian){

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

    }

    public void savePerkerasan(DataInletMedian dataMinlet){

        DbMinlet dbMinlet = new DbMinlet(context);

        dbMinlet.setMinlet(dataMinlet);
        saveTemporari("Perkerasan", session.getTipesurvey(), dataMinlet.getNoprov(), dataMinlet.getNoruas(), dataMinlet.getNosegment(), dataMinlet.getSubsegment(), dataMinlet.getPosisi(), "", dataMinlet.getNosegment(), dataMinlet.getSubsegment());
        ArrayList<DataTemporari> listTemporari = getTerusan("Perkerasan", session.getTipesurvey(), dataMinlet.getNoprov(), dataMinlet.getNoruas(), dataMinlet.getPosisi(), null);

        if(listTemporari.size()>1){

            int index = getIndex(listTemporari, dataMinlet.getNosegment(), dataMinlet.getSubsegment());


            if(index!=0){

                int segAkhir = getMinSegment(session.getTipesurvey(), listTemporari.get(index).getNosegment(), listTemporari.get(index).getSubsegment());
                int subAkhir = getMinSub(session.getTipesurvey(), listTemporari.get(index).getSubsegment());
                Log.d("SUBSEGKU", segAkhir+"."+subAkhir);
                addSebelum(session.getTipesurvey(), listTemporari, index);
                DataInletMedian dataMinletNormal = dbMinlet.getMinlet(dataMinlet.getNoprov(), dataMinlet.getNoruas(), listTemporari.get(index-1).getNosegment(),  listTemporari.get(index-1).getSubsegment(), dataMinlet.getPosisi());
                dbMinlet.saveMinletNormal(dataMinletNormal, dataMinletNormal.getNosegment(), dataMinletNormal.getSubsegment(), segAkhir, subAkhir, session.getTipesurvey());
            }

            if(index!=listTemporari.size()-1){

                int segAkhir = getMinSegment(session.getTipesurvey(), listTemporari.get(index+1).getNosegment(), listTemporari.get(index+1).getSubsegment());
                int subAkhir = getMinSub(session.getTipesurvey(), listTemporari.get(index+1).getSubsegment());
                addSesudah(session.getTipesurvey(), listTemporari, index);
                dbMinlet.saveMinletNormal(dataMinlet, dataMinlet.getNosegment(), dataMinlet.getSubsegment(), segAkhir, subAkhir, session.getTipesurvey());
            }

        }

    }

    public void saveSpDalam(DataSpDalam dataSpDalam){

        DbSpd dbSpd = new DbSpd(context);

        dbSpd.postData(dataSpDalam);
        saveTemporari("SPD", session.getTipesurvey(), dataSpDalam.getNoprov(), dataSpDalam.getNoruas(), dataSpDalam.getNosegment(), dataSpDalam.getSubsegment(), dataSpDalam.getPosisi(), "", dataSpDalam.getNosegment(), dataSpDalam.getSubsegment());
        ArrayList<DataTemporari> listTemporari = getTerusan("SPD", session.getTipesurvey(), dataSpDalam.getNoprov(), dataSpDalam.getNoruas(), dataSpDalam.getPosisi(), null);

        if(listTemporari.size()>1){

            int index = getIndex(listTemporari, dataSpDalam.getNosegment(), dataSpDalam.getSubsegment());


            if(index!=0){

                int segAkhir = getMinSegment(session.getTipesurvey(), listTemporari.get(index).getNosegment(), listTemporari.get(index).getSubsegment());
                int subAkhir = getMinSub(session.getTipesurvey(), listTemporari.get(index).getSubsegment());
                addSebelum(session.getTipesurvey(), listTemporari, index);
                DataSpDalam dataSpDalamNormal = dbSpd.getData(dataSpDalam.getNoprov(), dataSpDalam.getNoruas(), listTemporari.get(index-1).getNosegment(),  listTemporari.get(index-1).getSubsegment(), dataSpDalam.getPosisi());
                dbSpd.saveNormal(dataSpDalamNormal, dataSpDalamNormal.getNosegment(), dataSpDalamNormal.getSubsegment(), segAkhir, subAkhir, session.getTipesurvey());
            }

            if(index!=listTemporari.size()-1){

                int segAkhir = getMinSegment(session.getTipesurvey(), listTemporari.get(index+1).getNosegment(), listTemporari.get(index+1).getSubsegment());
                int subAkhir = getMinSub(session.getTipesurvey(), listTemporari.get(index+1).getSubsegment());
                addSesudah(session.getTipesurvey(), listTemporari, index);
                dbSpd.saveNormal(dataSpDalam, dataSpDalam.getNosegment(), dataSpDalam.getSubsegment(), segAkhir, subAkhir, session.getTipesurvey());
            }

        }

    }

    public void saveSpLuar(DataSpLuar dataSpLuar){

        DbSpl dbSpl = new DbSpl(context);

        dbSpl.postData(dataSpLuar);
        saveTemporari("SPL", session.getTipesurvey(), dataSpLuar.getNoprov(), dataSpLuar.getNoruas(), dataSpLuar.getNosegment(), dataSpLuar.getSubsegment(), dataSpLuar.getPosisi(), "", dataSpLuar.getNosegment(), dataSpLuar.getSubsegment());
        ArrayList<DataTemporari> listTemporari = getTerusan("SPL", session.getTipesurvey(), dataSpLuar.getNoprov(), dataSpLuar.getNoruas(), dataSpLuar.getPosisi(), null);

        if(listTemporari.size()>1){

            int index = getIndex(listTemporari, dataSpLuar.getNosegment(), dataSpLuar.getSubsegment());


            if(index!=0){

                int segAkhir = getMinSegment(session.getTipesurvey(), listTemporari.get(index).getNosegment(), listTemporari.get(index).getSubsegment());
                int subAkhir = getMinSub(session.getTipesurvey(), listTemporari.get(index).getSubsegment());
                addSebelum(session.getTipesurvey(), listTemporari, index);
                DataSpLuar dataSpLuarNormal = dbSpl.getData(dataSpLuar.getNoprov(), dataSpLuar.getNoruas(), listTemporari.get(index-1).getNosegment(),  listTemporari.get(index-1).getSubsegment(), dataSpLuar.getPosisi());
                dbSpl.saveNormal(dataSpLuarNormal, dataSpLuarNormal.getNosegment(), dataSpLuarNormal.getSubsegment(), segAkhir, subAkhir, session.getTipesurvey());
            }

            if(index!=listTemporari.size()-1){

                int segAkhir = getMinSegment(session.getTipesurvey(), listTemporari.get(index+1).getNosegment(), listTemporari.get(index+1).getSubsegment());
                int subAkhir = getMinSub(session.getTipesurvey(), listTemporari.get(index+1).getSubsegment());
                addSesudah(session.getTipesurvey(), listTemporari, index);
                dbSpl.saveNormal(dataSpLuar, dataSpLuar.getNosegment(), dataSpLuar.getSubsegment(), segAkhir, subAkhir, session.getTipesurvey());
            }

        }

    }

    public void saveLane(DataLane dataLane){

        dbLane.setLane(dataLane);
        saveTemporari("Lane", session.getTipesurvey(), dataLane.getNoprov(), dataLane.getNoruas(), dataLane.getNosegment(), dataLane.getSubsegment(), dataLane.getPosisi(), dataLane.getUrut(), dataLane.getNosegment(), dataLane.getSubsegment());
        ArrayList<DataTemporari> listTemporari = getTerusan("Lane", session.getTipesurvey(), dataLane.getNoprov(), dataLane.getNoruas(), dataLane.getPosisi(), dataLane.getUrut());

        //Toast.makeText(context, String.valueOf(listTemporari.size()), Toast.LENGTH_SHORT).show();



        if(listTemporari.size()>1){

            //Log.d("TESTINGLANE", listTemporari.get())

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

        /*for(int i = 0; i<listTemporari.size(); i++){
            DataTemporari dataTemporari = listTemporari.get(i);
            Log.d("TESTINGLANE", dataTemporari.getJenis()+"--"+dataTemporari.getNoprov()+"---"+dataTemporari.getNoruas()+"---"+dataTemporari.getPosisi()+"---"+dataTemporari.getUrut()+"---"+
                    dataTemporari.getNosegment()+"."+dataTemporari.getSubsegment()+"---"+dataTemporari.getSegmentakhir()+"."+dataTemporari.getSubsegmentakhir());
        }*/


    }
    public void saveSegment(final DataSegmen dataSegmen){

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

                }
            });


        }else{

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

}
