package com.example.roadmanagement.kaltara.Task;

import android.content.Context;

import com.example.roadmanagement.kaltara.Interface.CekSinkron;
import com.example.roadmanagement.kaltara.Interface.DataTemporari;
import com.example.roadmanagement.kaltara.Interface.SendId;
import com.example.roadmanagement.kaltara.api.FungsiAPI;
import com.example.roadmanagement.kaltara.databaseHelper.DbTemporari;

public class UpdateFunction {

    Context context;
    DbTemporari dbTemporari;
    FungsiAPI fungsiAPI;


    public UpdateFunction(Context context){

        this.context = context;

        fungsiAPI = new FungsiAPI(context);

    }

    public void mainUpdate(CekSinkron cekSinkron, final SendId sendId){

        int segmentAwal, subsegAwal, segmentAkhir, subsegAkhir;
        String posisiLane, lajurLane;
        final DataTemporari dataTemporari;

        if(cekSinkron.getJenis().equals("Opposite")){
            segmentAwal = cekSinkron.getNosegAkhir();
            subsegAwal = cekSinkron.getSubsegAkhir();
            segmentAkhir = cekSinkron.getNoseg();
            subsegAkhir = cekSinkron.getSubseg();
        }else{
            segmentAwal = cekSinkron.getNoseg();
            subsegAwal = cekSinkron.getSubseg();
            segmentAkhir = cekSinkron.getNosegAkhir();
            subsegAkhir = cekSinkron.getSubsegAkhir();
        }

        if(cekSinkron.getDetail().equals("Lane")){
            if(cekSinkron.getPosisi().substring(0,1).equals("L")){
                posisiLane = "kiri";
            }else{
                posisiLane = "kanan";
            }

            lajurLane = cekSinkron.getPosisi();
            dataTemporari = new DataTemporari(cekSinkron.getNoprov(), cekSinkron.getNoruas(), segmentAwal, subsegAwal, "Lane", posisiLane, lajurLane, cekSinkron.getJenis(), segmentAkhir, subsegAkhir, "1", "1");

        }else{

            dataTemporari = new DataTemporari(cekSinkron.getNoprov(), cekSinkron.getNoruas(), segmentAwal, subsegAwal, cekSinkron.getDetail(), cekSinkron.getPosisi(), cekSinkron.getLajur(), cekSinkron.getJenis(), segmentAkhir, subsegAkhir, "1", "1");

        }

        switch (dataTemporari.getTipe()){

            case "Lahan" : fungsiAPI.setLahan(dataTemporari, new SendId() {
                @Override
                public void hapusGambar(int id) {
                    sendId.hapusGambar(1);
                }
            });
            break;

            case "Bahu" : fungsiAPI.setBahu(dataTemporari, new SendId() {
                @Override
                public void hapusGambar(int id) {
                    sendId.hapusGambar(1);
                }
            });
                break;

            case "Saluran" : fungsiAPI.setSaluran(dataTemporari, new SendId() {
                @Override
                public void hapusGambar(int id) {
                    sendId.hapusGambar(1);
                }
            });
            break;

            case "Perkerasan" : fungsiAPI.setPerkerasan(dataTemporari, new SendId() {
                @Override
                public void hapusGambar(int id) {
                    sendId.hapusGambar(1);
                }
            });
                break;

            case "SPD" : fungsiAPI.setSPD(dataTemporari, new SendId() {
                @Override
                public void hapusGambar(int id) {
                    sendId.hapusGambar(1);
                }
            });
                break;

            case "SPL" : fungsiAPI.setSPL(dataTemporari, new SendId() {
                @Override
                public void hapusGambar(int id) {
                    sendId.hapusGambar(1);
                }
            });
                break;

            case "Median" : fungsiAPI.setMedian(dataTemporari, new SendId() {
                @Override
                public void hapusGambar(int id) {
                    sendId.hapusGambar(1);
                }
            });
            break;

            case "Lane" : fungsiAPI.setLane(dataTemporari, new SendId() {
                @Override
                public void hapusGambar(int id) {
                    sendId.hapusGambar(1);
                }
            });
                break;

            case "Segment" : fungsiAPI.setSegment(dataTemporari, new SendId() {
                @Override
                public void hapusGambar(int id) {
                    sendId.hapusGambar(1);
                }
            });
                break;


            case "Inlet ditrotoar" : fungsiAPI.setInlet(dataTemporari, new SendId() {
                @Override
                public void hapusGambar(int id) {
                    sendId.hapusGambar(1);
                }
            });
                break;

            case "Outlet" : fungsiAPI.setOutlet(dataTemporari, new SendId() {
                @Override
                public void hapusGambar(int id) {
                    sendId.hapusGambar(1);
                }
            });
                break;

            case "Gorong-gorong" : fungsiAPI.setGorong(dataTemporari, new SendId() {
                @Override
                public void hapusGambar(int id) {
                    sendId.hapusGambar(1);
                }
            });
                break;

            case "Saluran Lereng" : fungsiAPI.setLereng(dataTemporari, new SendId() {
                @Override
                public void hapusGambar(int id) {
                    sendId.hapusGambar(1);
                }
            });
                break;

            default: sendId.hapusGambar(1);

        }

    }
}
