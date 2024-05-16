package com.example.roadmanagement.kaltara.Task;

import android.content.Context;
import android.content.Intent;

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
import com.example.roadmanagement.kaltara.Interface.DataSpDalam;
import com.example.roadmanagement.kaltara.Interface.DataSpLuar;
import com.example.roadmanagement.kaltara.Interface.DataTemporari;
import com.example.roadmanagement.kaltara.Interface.SendId;
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
import com.example.roadmanagement.kaltara.databaseHelper.DbSaluran;
import com.example.roadmanagement.kaltara.databaseHelper.DbSegmen;
import com.example.roadmanagement.kaltara.databaseHelper.DbSpd;
import com.example.roadmanagement.kaltara.databaseHelper.DbSpinner;
import com.example.roadmanagement.kaltara.databaseHelper.DbSpl;
import com.example.roadmanagement.kaltara.databaseHelper.DbTemporari;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SinkronFunction {

    FungsiAPI fungsiAPI;
    Context context;
    DbLahan dbLahan;
    DbSaluran dbSaluran;
    DbBahu dbBahu;
    DbLane dbLane;
    DbMedian dbMedian;
    DbSegmen dbSegmen;
    DbSpinner dbSpinner;
    DbInlet dbInlet;
    DbMinlet dbMinlet;
    DbOutlet dbOutlet;
    DbGorong dbGorong;
    DbLereng dblereng;

    DbSpd dbSpd;

    DbSpl dbSpl;

    String waktu;


    public SinkronFunction(Context context, String waktu){
        this.context = context;
        this.waktu = waktu;

        fungsiAPI = new FungsiAPI(context);

        dbLahan = new DbLahan(context);
        dbSaluran = new DbSaluran(context);
        dbBahu = new DbBahu(context);
        dbMedian = new DbMedian(context);
        dbLane = new DbLane(context);
        dbSegmen = new DbSegmen(context);
        dbSpinner = new DbSpinner(context);

        dbInlet = new DbInlet(context);
        dbMinlet = new DbMinlet(context);
        dbOutlet = new DbOutlet(context);
        dbGorong = new DbGorong(context);
        dblereng = new DbLereng(context);

        dbSpl = new DbSpl(context);
        dbSpd = new DbSpd(context);

    }

    public void sinkronUtama(DataTemporari dataTemporari,  int idSinkron, final SendId sendId){

        int segmentAwal, subSegmentAwal, segmentAkhir, subSegmentAkhir;

        if(dataTemporari.getJenis().equals("Opposite")){
            segmentAwal = dataTemporari.getSegmentakhir();
            segmentAkhir = dataTemporari.getNosegment();
            subSegmentAwal = dataTemporari.getSubsegmentakhir();
            subSegmentAkhir = dataTemporari.getSubsegment();
        }else{

            segmentAwal = dataTemporari.getNosegment();
            segmentAkhir = dataTemporari.getSegmentakhir();
            subSegmentAwal = dataTemporari.getSubsegment();
            subSegmentAkhir = dataTemporari.getSubsegmentakhir();
        }

        switch (dataTemporari.getTipe()){

            case "Lahan" :

                sinkronLahan(dataTemporari, segmentAwal, subSegmentAwal, segmentAkhir, subSegmentAkhir, idSinkron, new SendId() {
                    @Override
                    public void hapusGambar(int id) {
                        sendId.hapusGambar(id);
                    }
                });

                break;

            case "Bahu" :

                sinkronBahu(dataTemporari, segmentAwal, subSegmentAwal, segmentAkhir, subSegmentAkhir, idSinkron, new SendId() {
                    @Override
                    public void hapusGambar(int id) {
                        sendId.hapusGambar(id);
                    }
                });

                break;

            case "Saluran" :

                sinkronSaluran(dataTemporari, segmentAwal, subSegmentAwal, segmentAkhir, subSegmentAkhir, idSinkron, new SendId() {
                    @Override
                    public void hapusGambar(int id) {
                        sendId.hapusGambar(id);
                    }
                });

                break;

            case "Perkerasan" :

                sinkronPerkerasan(dataTemporari, segmentAwal, subSegmentAwal, segmentAkhir, subSegmentAkhir, idSinkron, new SendId() {
                    @Override
                    public void hapusGambar(int id) {
                        sendId.hapusGambar(id);
                    }
                });

                break;

            case "SPD" :

                sinkronSPD(dataTemporari, segmentAwal, subSegmentAwal, segmentAkhir, subSegmentAkhir, idSinkron, new SendId() {
                    @Override
                    public void hapusGambar(int id) {
                        sendId.hapusGambar(id);
                    }
                });

                break;

            case "SPL" :

                sinkronSPL(dataTemporari, segmentAwal, subSegmentAwal, segmentAkhir, subSegmentAkhir, idSinkron, new SendId() {
                    @Override
                    public void hapusGambar(int id) {
                        sendId.hapusGambar(id);
                    }
                });

                break;

            case "Lane" :

                sinkronLane(dataTemporari, segmentAwal, subSegmentAwal, segmentAkhir, subSegmentAkhir, idSinkron, new SendId() {
                    @Override
                    public void hapusGambar(int id) {
                        sendId.hapusGambar(id);
                    }
                });

                break;

            case "Median" :

                sinkronMedian(dataTemporari, segmentAwal, subSegmentAwal, segmentAkhir, subSegmentAkhir, idSinkron, new SendId() {
                    @Override
                    public void hapusGambar(int id) {
                        sendId.hapusGambar(id);
                    }
                });

                break;

            case "Segment" :

                sinkronSegment(dataTemporari, segmentAwal, subSegmentAwal, segmentAkhir, subSegmentAkhir, idSinkron, new SendId() {
                    @Override
                    public void hapusGambar(int id) {
                        sendId.hapusGambar(id);
                    }
                });

                break;


            case "Inlet ditrotoar" :

                sinkronInlet(dataTemporari, idSinkron, new SendId() {
                    @Override
                    public void hapusGambar(int id) {
                        sendId.hapusGambar(id);
                    }
                });

                break;


            case "Inlet dimedian" :

                sinkronMinlet(dataTemporari, idSinkron, new SendId() {
                    @Override
                    public void hapusGambar(int id) {
                        sendId.hapusGambar(id);
                    }
                });

                break;


            case "Outlet" :

                sinkronOutlet(dataTemporari, idSinkron, new SendId() {
                    @Override
                    public void hapusGambar(int id) {
                        sendId.hapusGambar(id);
                    }
                });

                break;


            case "Gorong-gorong" :

                sinkronGorong(dataTemporari, idSinkron, new SendId() {
                    @Override
                    public void hapusGambar(int id) {
                        sendId.hapusGambar(id);
                    }
                });

                break;

            case "Saluran Lereng" :

                sinkronLereng(dataTemporari, idSinkron, new SendId() {
                    @Override
                    public void hapusGambar(int id) {
                        sendId.hapusGambar(id);
                    }
                });

                break;

        }


    }

    private void sinkronLahan(final DataTemporari dataTemporari, final int segmentAwal, final int subsegAwal, final int segmentAkhir, final int subsegAkhir, final int idSinkron, final SendId sendId){

        final DataLahan dataLahan;

        if(dataTemporari.getJenis().equals("Unidentified")){

            dataLahan = dbLahan.getLahanUn(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment());
            sinkronImageUnd("Lahan", dataLahan.getNoprov(), dataLahan.getNoruas(), dataLahan.getNosegment(), dataLahan.getPosisi(), dataLahan.getGambarLahan(), dataLahan.getLokasilahan(), new SendId() {
                @Override
                public void hapusGambar(int id) {

                    sendId.hapusGambar(id);

                }
            });

        }else {

            dataLahan = dbLahan.getLahan(dataTemporari.getNoprov(), dataTemporari.getNoruas(), segmentAwal, subsegAwal, dataTemporari.getPosisi());
            fungsiAPI.saveLahan(dataLahan, segmentAkhir, subsegAkhir, new SendId() {
                @Override
                public void hapusGambar(int id) {

                    sinkronImage("Lahan", dataLahan.getNoprov(), dataLahan.getNoruas(), dataLahan.getNosegment(), dataLahan.getSubsegment(), dataLahan.getPosisi(), dataLahan.getGambarLahan(), dataLahan.getLokasilahan(), new SendId() {
                        @Override
                        public void hapusGambar(int id) {

                            fungsiAPI.saveSinkrondetail(dataTemporari.getNoprov(), dataTemporari.getNoruas(), segmentAwal, subsegAwal, segmentAkhir, subsegAkhir, dataTemporari.getTipe(), dataTemporari.getPosisi(), dataTemporari.getUrut(), dataTemporari.getJenis(), waktu, idSinkron, new SendId() {
                                @Override
                                public void hapusGambar(int id) {
                                    sendId.hapusGambar(id);
                                }
                            });


                        }
                    });

                }
            });

        }

    }


    private void sinkronSaluran(final DataTemporari dataTemporari, final int segmentAwal, final int subsegAwal, final int segmentAkhir, final int subsegAkhir, final int idSinkron, final SendId sendId){

        final DataSaluran dataSaluran;

        if(dataTemporari.getJenis().equals("Unidentified")){

            dataSaluran = dbSaluran.getSaluranUn(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment());
            fungsiAPI.saveSaluranUnd(dataSaluran);
            sinkronImageUnd("Saluran", dataSaluran.getNoprov(), dataSaluran.getNoruas(), dataSaluran.getNosegment(), dataSaluran.getPosisi(), dataSaluran.getGambarSaluran(), dataSaluran.getLokasiSaluran(), new SendId() {
                @Override
                public void hapusGambar(int id) {

                    sendId.hapusGambar(id);

                }
            });

        }else {

            dataSaluran = dbSaluran.getSaluran(dataTemporari.getNoprov(), dataTemporari.getNoruas(), segmentAwal, subsegAwal, dataTemporari.getPosisi());
            fungsiAPI.saveSaluran(dataSaluran, segmentAkhir, subsegAkhir, new SendId() {
                @Override
                public void hapusGambar(int id) {

                    sinkronImage("Saluran", dataSaluran.getNoprov(), dataSaluran.getNoruas(), dataSaluran.getNosegment(), dataSaluran.getSubsegment(), dataSaluran.getPosisi(), dataSaluran.getGambarSaluran(), dataSaluran.getLokasiSaluran(),
                            new SendId() {
                                @Override
                                public void hapusGambar(int id) {

                                    fungsiAPI.saveSinkrondetail(dataTemporari.getNoprov(), dataTemporari.getNoruas(), segmentAwal, subsegAwal, segmentAkhir, subsegAkhir, dataTemporari.getTipe(), dataTemporari.getPosisi(), dataTemporari.getUrut(), dataTemporari.getJenis(), waktu, idSinkron, new SendId() {
                                        @Override
                                        public void hapusGambar(int id) {
                                            sendId.hapusGambar(id);
                                        }
                                    });


                                }
                            });

                }
            });

        }

    }

    private void sinkronPerkerasan(final DataTemporari dataTemporari, final int segmentAwal, final int subsegAwal, final int segmentAkhir, final int subsegAkhir, final int idSinkron, final SendId sendId){

        final DataInletMedian dataMinlet;

        dataMinlet = dbMinlet.getMinlet(dataTemporari.getNoprov(), dataTemporari.getNoruas(), segmentAwal, subsegAwal, dataTemporari.getPosisi());
        fungsiAPI.savePerkerasan(dataMinlet, segmentAkhir, subsegAkhir, new SendId() {
            @Override
            public void hapusGambar(int id) {

                sinkronImage("Perkerasan", dataMinlet.getNoprov(), dataMinlet.getNoruas(), dataMinlet.getNosegment(), dataMinlet.getSubsegment(), dataMinlet.getPosisi(), dataMinlet.getGambar(), dataMinlet.getLokasi(),
                        new SendId() {
                            @Override
                            public void hapusGambar(int id) {

                                fungsiAPI.saveSinkrondetail(dataTemporari.getNoprov(), dataTemporari.getNoruas(), segmentAwal, subsegAwal, segmentAkhir, subsegAkhir, dataTemporari.getTipe(), dataTemporari.getPosisi(), dataTemporari.getUrut(), dataTemporari.getJenis(), waktu, idSinkron, new SendId() {
                                    @Override
                                    public void hapusGambar(int id) {
                                        sendId.hapusGambar(id);
                                    }
                                });


                            }
                        });

            }
        });



    }

    private void sinkronSPD(final DataTemporari dataTemporari, final int segmentAwal, final int subsegAwal, final int segmentAkhir, final int subsegAkhir, final int idSinkron, final SendId sendId){

        final DataSpDalam dataSpDalam;

        dataSpDalam = dbSpd.getData(dataTemporari.getNoprov(), dataTemporari.getNoruas(), segmentAwal, subsegAwal, dataTemporari.getPosisi());
        fungsiAPI.saveSPD(dataSpDalam, segmentAkhir, subsegAkhir, new SendId() {
            @Override
            public void hapusGambar(int id) {

                sinkronImage("SPD", dataSpDalam.getNoprov(), dataSpDalam.getNoruas(), dataSpDalam.getNosegment(), dataSpDalam.getSubsegment(), dataSpDalam.getPosisi(), dataSpDalam.getGambar(), dataSpDalam.getLokasi(),
                        new SendId() {
                            @Override
                            public void hapusGambar(int id) {

                                fungsiAPI.saveSinkrondetail(dataTemporari.getNoprov(), dataTemporari.getNoruas(), segmentAwal, subsegAwal, segmentAkhir, subsegAkhir, dataTemporari.getTipe(), dataTemporari.getPosisi(), dataTemporari.getUrut(), dataTemporari.getJenis(), waktu, idSinkron, new SendId() {
                                    @Override
                                    public void hapusGambar(int id) {
                                        sendId.hapusGambar(id);
                                    }
                                });


                            }
                        });

            }
        });



    }

    private void sinkronSPL(final DataTemporari dataTemporari, final int segmentAwal, final int subsegAwal, final int segmentAkhir, final int subsegAkhir, final int idSinkron, final SendId sendId){

        final DataSpLuar dataSpLuar;

        dataSpLuar = dbSpl.getData(dataTemporari.getNoprov(), dataTemporari.getNoruas(), segmentAwal, subsegAwal, dataTemporari.getPosisi());
        fungsiAPI.saveSPL(dataSpLuar, segmentAkhir, subsegAkhir, new SendId() {
            @Override
            public void hapusGambar(int id) {

                sinkronImage("SPL", dataSpLuar.getNoprov(), dataSpLuar.getNoruas(), dataSpLuar.getNosegment(), dataSpLuar.getSubsegment(), dataSpLuar.getPosisi(), dataSpLuar.getGambar(), dataSpLuar.getLokasi(),
                        new SendId() {
                            @Override
                            public void hapusGambar(int id) {

                                fungsiAPI.saveSinkrondetail(dataTemporari.getNoprov(), dataTemporari.getNoruas(), segmentAwal, subsegAwal, segmentAkhir, subsegAkhir, dataTemporari.getTipe(), dataTemporari.getPosisi(), dataTemporari.getUrut(), dataTemporari.getJenis(), waktu, idSinkron, new SendId() {
                                    @Override
                                    public void hapusGambar(int id) {
                                        sendId.hapusGambar(id);
                                    }
                                });


                            }
                        });

            }
        });



    }

    private void sinkronBahu(final DataTemporari dataTemporari, final int segmentAwal, final int subsegAwal, final int segmentAkhir, final int subsegAkhir, final int idSinkron, final SendId sendId){

        final DataBahu dataBahu;

        if(dataTemporari.getJenis().equals("Unidentified")){

            dataBahu = dbBahu.getBahuUn(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment());
            fungsiAPI.saveBahuUnd(dataBahu);
            sinkronImageUnd("Bahu", dataBahu.getNoprov(), dataBahu.getNoruas(), dataBahu.getNosegment(), dataBahu.getPosisi(), dataBahu.getGambarBahu(), dataBahu.getLokasiBahu(), new SendId() {
                @Override
                public void hapusGambar(int id) {

                    sendId.hapusGambar(id);

                }
            });

        }else {

            dataBahu = dbBahu.getBahu(dataTemporari.getNoprov(), dataTemporari.getNoruas(), segmentAwal, subsegAwal, dataTemporari.getPosisi());
            fungsiAPI.saveBahu(dataBahu, segmentAkhir, subsegAkhir, new SendId() {
                @Override
                public void hapusGambar(int id) {

                    sinkronImage("Bahu", dataBahu.getNoprov(), dataBahu.getNoruas(), dataBahu.getNosegment(), dataBahu.getSubSegment(), dataBahu.getPosisi(), dataBahu.getGambarBahu(), dataBahu.getLokasiBahu(), new SendId() {
                        @Override
                        public void hapusGambar(int id) {

                            fungsiAPI.saveSinkrondetail(dataTemporari.getNoprov(), dataTemporari.getNoruas(), segmentAwal, subsegAwal, segmentAkhir, subsegAkhir, dataTemporari.getTipe(), dataTemporari.getPosisi(), dataTemporari.getUrut(), dataTemporari.getJenis(), waktu, idSinkron, new SendId() {
                                @Override
                                public void hapusGambar(int id) {


                                    sendId.hapusGambar(id);


                                }
                            });
                        }
                    });

                }
            });

        }

    }

    private void sinkronLane(final DataTemporari dataTemporari, final int segmentAwal, final int subsegAwal, final int segmentAkhir, final int subsegAkhir, final int idSinkron, final SendId sendId){

        final DataLane dataLane;

        if(dataTemporari.getJenis().equals("Unidentified")){

            dataLane = dbLane.getLaneUn(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment(), dataTemporari.getPosisi());
            fungsiAPI.saveLaneUnd(dataLane);
            sinkronImageUnd("Lane", dataLane.getNoprov(), dataLane.getNoruas(), dataLane.getNosegment(), dataLane.getPosisi(), dataLane.getGambarLane(), dataLane.getLokasiLane(), new SendId() {
                @Override
                public void hapusGambar(int id) {

                    sendId.hapusGambar(id);

                }
            });

        }else {

            dataLane = dbLane.getLane(dataTemporari.getNoprov(), dataTemporari.getNoruas(), segmentAwal, subsegAwal, dataTemporari.getPosisi(), dataTemporari.getUrut());
            fungsiAPI.saveLane(dataLane, segmentAkhir, subsegAkhir, new SendId() {
                @Override
                public void hapusGambar(int id) {

                    sinkronImage("Lane", dataLane.getNoprov(), dataLane.getNoruas(), dataLane.getNosegment(), dataLane.getSubsegment(), dataLane.getUrut(), dataLane.getGambarLane(), dataLane.getLokasiLane(), new SendId() {
                        @Override
                        public void hapusGambar(int id) {

                            fungsiAPI.saveSinkrondetail(dataTemporari.getNoprov(), dataTemporari.getNoruas(), segmentAwal, subsegAwal, segmentAkhir, subsegAkhir, dataTemporari.getTipe(), dataTemporari.getUrut(), "", dataTemporari.getJenis(), waktu, idSinkron, new SendId() {
                                @Override
                                public void hapusGambar(int id) {
                                    sendId.hapusGambar(id);
                                }
                            });


                        }
                    });

                }
            });

        }

    }


    private void sinkronMedian(final DataTemporari dataTemporari, final int segmentAwal, final int subsegAwal, final int segmentAkhir, final int subsegAkhir, final int idSinkron, final SendId sendId){

        final DataMedian dataMedian;

        if(dataTemporari.getJenis().equals("Unidentified")){

            dataMedian = dbMedian.getMedianUn(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment());
            fungsiAPI.saveMedianUnd(dataMedian);
            sinkronImageUnd("Median", dataMedian.getNoprov(), dataMedian.getNoruas(), dataMedian.getNosegment(), "", dataMedian.getGambarMedian(), dataMedian.getLokasiMedian(), new SendId() {
                @Override
                public void hapusGambar(int id) {

                    sendId.hapusGambar(id);

                }
            });

        }else {

            dataMedian = dbMedian.getMedian(dataTemporari.getNoprov(), dataTemporari.getNoruas(), segmentAwal, subsegAwal);
            fungsiAPI.saveMedian(dataMedian, segmentAkhir, subsegAkhir, new SendId() {
                @Override
                public void hapusGambar(int id) {
                    sinkronImage("Median", dataMedian.getNoprov(), dataMedian.getNoruas(), dataMedian.getNosegment(), dataMedian.getSubsegment(), "", dataMedian.getGambarMedian(), dataMedian.getLokasiMedian(), new SendId() {
                        @Override
                        public void hapusGambar(int id) {

                            fungsiAPI.saveSinkrondetail(dataTemporari.getNoprov(), dataTemporari.getNoruas(), segmentAwal, subsegAwal, segmentAkhir, subsegAkhir, dataTemporari.getTipe(), "", "", dataTemporari.getJenis(), waktu, idSinkron, new SendId() {
                                @Override
                                public void hapusGambar(int id) {
                                    sendId.hapusGambar(id);
                                }
                            });

                        }
                    });

                }
            });

        }

    }

    private void sinkronSegment(final DataTemporari dataTemporari, final int segmentAwal, final int subsegAwal, final int segmentAkhir, final int subsegAkhir, final int idSinkron, final SendId sendId){


        if(dataTemporari.getPosisi().equals("Update")){

            final DataSegmen dataSegmen;
            dataSegmen = dbSegmen.getSegmen(dataTemporari.getNoprov(), dataTemporari.getNoruas(), segmentAwal, subsegAwal);

            fungsiAPI.saveSegmentku(dataSegmen, segmentAkhir, subsegAkhir, new SendId() {
                @Override
                public void hapusGambar(int id) {

                    fungsiAPI.saveSinkrondetail(dataTemporari.getNoprov(), dataTemporari.getNoruas(), segmentAwal, subsegAwal, segmentAkhir, subsegAkhir, dataTemporari.getTipe(), dataTemporari.getPosisi(), "", dataTemporari.getJenis(), waktu, idSinkron, new SendId() {
                        @Override
                        public void hapusGambar(int id) {
                            sendId.hapusGambar(1);
                        }
                    });

                }
            });



        }else{

            fungsiAPI.saveSinkrondetail(dataTemporari.getNoprov(), dataTemporari.getNoruas(), segmentAwal, subsegAwal, segmentAkhir, subsegAkhir, dataTemporari.getTipe(), dataTemporari.getPosisi(), dataTemporari.getUrut(), dataTemporari.getJenis(), waktu, idSinkron, new SendId() {
                @Override
                public void hapusGambar(int id) {
                    sendId.hapusGambar(1);
                }
            });


        }


    }


    private void sinkronInlet(final DataTemporari dataTemporari, final int idSinkron, final SendId sendId){

        final DataInletTrotoar dataInlet =  dbInlet.getInlet(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment(), dataTemporari.getSubsegment(), dataTemporari.getPosisi());
        fungsiAPI.saveInlet(dataInlet, new SendId() {
            @Override
            public void hapusGambar(int id) {
                sinkronImage("Inlet", dataInlet.getNoprov(), dataInlet.getNoruas(), dataInlet.getNosegment(), dataInlet.getSubsegment(), dataInlet.getPosisi(), dataInlet.getGambar(), dataInlet.getLokasi(), new SendId() {
                    @Override
                    public void hapusGambar(int id) {

                        fungsiAPI.saveSinkrondetail(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment(), dataTemporari.getSubsegment(), dataTemporari.getNosegment(), dataTemporari.getSubsegment(), dataTemporari.getTipe(), dataTemporari.getPosisi(), "", dataTemporari.getJenis(), waktu, idSinkron, new SendId() {
                            @Override
                            public void hapusGambar(int id) {
                                sendId.hapusGambar(id);
                            }
                        });


                    }
                });

            }
        });



    }

    private void sinkronMinlet(final DataTemporari dataTemporari, final int idSinkron, final SendId sendId){

        final DataInletMedian dataMinlet =  dbMinlet.getMinlet(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment(), dataTemporari.getSubsegment(), dataTemporari.getPosisi());
        fungsiAPI.saveMinlet(dataMinlet, new SendId() {
            @Override
            public void hapusGambar(int id) {

                sinkronImage("Minlet", dataMinlet.getNoprov(), dataMinlet.getNoruas(), dataMinlet.getNosegment(), dataMinlet.getSubsegment(), dataMinlet.getPosisi(), dataMinlet.getGambar(), dataMinlet.getLokasi(), new SendId() {
                    @Override
                    public void hapusGambar(int id) {

                        fungsiAPI.saveSinkrondetail(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment(), dataTemporari.getSubsegment(), dataTemporari.getNosegment(), dataTemporari.getSubsegment(), dataTemporari.getTipe(), dataTemporari.getPosisi(), "", dataTemporari.getJenis(), waktu, idSinkron, new SendId() {
                            @Override
                            public void hapusGambar(int id) {
                                sendId.hapusGambar(id);
                            }
                        });


                    }
                });

            }
        });



    }

    private void sinkronOutlet(final DataTemporari dataTemporari, final int idSinkron, final SendId sendId){

        final DataOutlet dataOutlet =  dbOutlet.getOutlet(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment(), dataTemporari.getSubsegment(), dataTemporari.getPosisi());
        fungsiAPI.saveOutlet(dataOutlet, new SendId() {
            @Override
            public void hapusGambar(int id) {

                sinkronImage("Outlet", dataOutlet.getNoprov(), dataOutlet.getNoruas(), dataOutlet.getNosegment(), dataOutlet.getSubsegment(), dataOutlet.getPosisi(), dataOutlet.getGambar(), dataOutlet.getLokasi(), new SendId() {
                    @Override
                    public void hapusGambar(int id) {

                        fungsiAPI.saveSinkrondetail(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment(), dataTemporari.getSubsegment(), dataTemporari.getNosegment(), dataTemporari.getSubsegment(), dataTemporari.getTipe(), dataTemporari.getPosisi(), "", dataTemporari.getJenis(), waktu, idSinkron, new SendId() {
                            @Override
                            public void hapusGambar(int id) {
                                sendId.hapusGambar(id);
                            }
                        });


                    }
                });

            }
        });



    }

    private void sinkronGorong(final DataTemporari dataTemporari, final int idSinkron, final SendId sendId){

        final DataCrossDrain dataGorong =  dbGorong.getGorong(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment(), dataTemporari.getSubsegment(), dataTemporari.getPosisi());
        fungsiAPI.saveGorong(dataGorong, new SendId() {
            @Override
            public void hapusGambar(int id) {
                sinkronImage("Gorong", dataGorong.getNoprov(), dataGorong.getNoruas(), dataGorong.getNosegment(), dataGorong.getSubsegment(), dataGorong.getPosisi(), dataGorong.getGambar(), dataGorong.getLokasi(), new SendId() {
                    @Override
                    public void hapusGambar(int id) {

                        fungsiAPI.saveSinkrondetail(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment(), dataTemporari.getSubsegment(), dataTemporari.getNosegment(), dataTemporari.getSubsegment(), dataTemporari.getTipe(), dataTemporari.getPosisi(), "", dataTemporari.getJenis(), waktu, idSinkron, new SendId() {
                            @Override
                            public void hapusGambar(int id) {
                                sendId.hapusGambar(id);
                            }
                        });

                    }
                });
            }
        });



    }


    private void sinkronLereng(final DataTemporari dataTemporari, final int idSinkron, final SendId sendId){

        final DataDrainase dataLereng =  dblereng.getLereng(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment(), dataTemporari.getSubsegment(), dataTemporari.getPosisi());
        fungsiAPI.saveLereng(dataLereng, new SendId() {
            @Override
            public void hapusGambar(int id) {

                sinkronImage("Lereng", dataLereng.getNoprov(), dataLereng.getNoruas(), dataLereng.getNosegment(), dataLereng.getSubsegment(), dataLereng.getPosisi(), dataLereng.getGambar(), dataLereng.getLokasi(), new SendId() {
                    @Override
                    public void hapusGambar(int id) {

                        fungsiAPI.saveSinkrondetail(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment(), dataTemporari.getSubsegment(), dataTemporari.getNosegment(), dataTemporari.getSubsegment(), dataTemporari.getTipe(), dataTemporari.getPosisi(), "", dataTemporari.getJenis(), waktu, idSinkron, new SendId() {
                            @Override
                            public void hapusGambar(int id) {
                                sendId.hapusGambar(id);
                            }
                        });

                    }
                });


            }
        });


    }



    private void sinkronImage(String tipe, String provinsi, String ruas, int segment, int subseg, String posisi, String image, String location, SendId sendId){

        if(image!=null) {

            String folder = getFolder(tipe, provinsi, ruas, posisi);
            String[] pathimage = image.split(",");
            String[] lokasiku = location.split(",");

            for (int a = 0; a <= pathimage.length; a++) {


                if(a == pathimage.length){

                    sendId.hapusGambar(1);

                }else{

                    try {
                        File file = new File(pathimage[a]);
                        String path = file.getAbsolutePath();
                        String filename = path.substring(path.lastIndexOf("/") + 1);
                        fungsiAPI.uploadgambar(file, folder);
                        fungsiAPI.saveImage(tipe, provinsi, ruas, segment, subseg, posisi, folder, filename, lokasiku[a],a+1);
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }
        }else{

            sendId.hapusGambar(1);
        }

    }

    private void sinkronImageUnd(String tipe, String provinsi, String ruas, int segment, String posisi, String image, String location, SendId sendId){

        if(image!=null) {

            String folder = getFolder(tipe, provinsi, ruas, posisi);
            String[] pathimage = image.split(",");
            String[] lokasiku = location.split(",");
            for (int a = 0; a <=pathimage.length; a++) {


                if(a == pathimage.length){
                    sendId.hapusGambar(1);

                }else{

                    try {
                        File file = new File(pathimage[a]);
                        String path = file.getAbsolutePath();
                        String filename = path.substring(path.lastIndexOf("/") + 1);
                        fungsiAPI.uploadgambar(file, folder);
                        fungsiAPI.saveImageUnd(tipe, provinsi, ruas, segment, posisi, folder, filename, lokasiku[a],a+1);
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }
        }


    }


    private String getFolder(String tipe, String provinsi, String ruas, String posisi){


        if(tipe.equals("Median")){
            return  "asset2022/"+provinsi+"/"+ruas+"/"+"Median";
        }else{
            return  "asset2022/"+provinsi+"/"+ruas+"/"+tipe+"/"+getPosisi(posisi);
        }



    }

    private String getPosisi(String posisi){

        if(posisi.equals("kiri")){
            return "L";
        }else if(posisi.equals("kanan")){
            return "R";
        }else{
            return posisi;
        }

    }



}
