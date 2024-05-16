package com.example.roadmanagement.kaltara.api;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;

import android.widget.Toast;

import com.example.roadmanagement.kaltara.DownloadBaru.DetailDownload;
import com.example.roadmanagement.kaltara.DownloadBaru.DetailDrainase;
import com.example.roadmanagement.kaltara.DownloadBaru.ListDownload;
import com.example.roadmanagement.kaltara.DownloadBaru.ListDrainase;
import com.example.roadmanagement.kaltara.DownloadBaru.TaskDownloadBaru;
import com.example.roadmanagement.kaltara.DownloadBaru.TaskDownloadDrainase;
import com.example.roadmanagement.kaltara.Interface.CekSinkron;
import com.example.roadmanagement.kaltara.Interface.CekSinkronku;
import com.example.roadmanagement.kaltara.Interface.DataBahu;
import com.example.roadmanagement.kaltara.Interface.DataCrossDrain;
import com.example.roadmanagement.kaltara.Interface.DataDrainase;
import com.example.roadmanagement.kaltara.Interface.DataInletMedian;
import com.example.roadmanagement.kaltara.Interface.DataInletTrotoar;
import com.example.roadmanagement.kaltara.Interface.DataLahan;
import com.example.roadmanagement.kaltara.Interface.DataLane;
import com.example.roadmanagement.kaltara.Interface.DataMedian;
import com.example.roadmanagement.kaltara.Interface.DataOutlet;
import com.example.roadmanagement.kaltara.Interface.DataRuas;
import com.example.roadmanagement.kaltara.Interface.DataSaluran;
import com.example.roadmanagement.kaltara.Interface.DataSegmen;
import com.example.roadmanagement.kaltara.Interface.DataSpDalam;
import com.example.roadmanagement.kaltara.Interface.DataSpLuar;
import com.example.roadmanagement.kaltara.Interface.DataTemporari;
import com.example.roadmanagement.kaltara.Interface.Downloadruas;
import com.example.roadmanagement.kaltara.Interface.InterfaceCekJumlahSegment;
import com.example.roadmanagement.kaltara.Interface.Koordinat;
import com.example.roadmanagement.kaltara.Interface.Koordinatku;
import com.example.roadmanagement.kaltara.Interface.ListJumlahSegment;
import com.example.roadmanagement.kaltara.Interface.PolygonFunc;
import com.example.roadmanagement.kaltara.Interface.Ruasku;
import com.example.roadmanagement.kaltara.Interface.SendId;
import com.example.roadmanagement.kaltara.Interface.SendString;
import com.example.roadmanagement.kaltara.Interface.Sinkrondata;
import com.example.roadmanagement.kaltara.Task.TaskAssetExt;
import com.example.roadmanagement.kaltara.Task.TaskDrainExt;
import com.example.roadmanagement.kaltara.databaseHelper.DbBahu;
import com.example.roadmanagement.kaltara.databaseHelper.DbGorong;
import com.example.roadmanagement.kaltara.databaseHelper.DbInlet;
import com.example.roadmanagement.kaltara.databaseHelper.DbLahan;
import com.example.roadmanagement.kaltara.databaseHelper.DbLane;
import com.example.roadmanagement.kaltara.databaseHelper.DbLereng;
import com.example.roadmanagement.kaltara.databaseHelper.DbMedian;
import com.example.roadmanagement.kaltara.databaseHelper.DbMinlet;
import com.example.roadmanagement.kaltara.databaseHelper.DbOutlet;
import com.example.roadmanagement.kaltara.databaseHelper.DbRuas;
import com.example.roadmanagement.kaltara.databaseHelper.DbSaluran;
import com.example.roadmanagement.kaltara.databaseHelper.DbSegmen;
import com.example.roadmanagement.kaltara.databaseHelper.DbSpd;
import com.example.roadmanagement.kaltara.databaseHelper.DbSpinner;
import com.example.roadmanagement.kaltara.databaseHelper.DbSpl;
import com.example.roadmanagement.kaltara.databaseHelper.DbTemporari;
import com.example.roadmanagement.kaltara.helper.Session;
import com.example.roadmanagement.kaltara.interfacedownload.SendDownload;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FungsiAPI {

    Context context;
    Retrofit retrofit;
    Apiku apiku;
    Session session;
    ProgressDialog dialog;
    DbTemporari dbTemporari;
    DbSpinner dbSpinner;
    DbSegmen dbSegmen;

    int tipeLogin;

    public FungsiAPI(Context context) {
        this.context = context;
        apiku = new Apiku(context);
        retrofit = apiku.initRetrofit();
        session = new Session(context);

        dbTemporari = new DbTemporari(context);
        dbSpinner = new DbSpinner(context);

        tipeLogin = session.getUserTipe();
//        dialog = new ProgressDialog(context);
    }


    public void setFlagLog(final String username, final int value, final SendId sendId) {


        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);
        Call<String> result = apiService.setLogUp(username, value);

        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    if (response.body() != null) {
                        //Toast.makeText(context, response.body(), Toast.LENGTH_SHORT).show();
                        sendId.hapusGambar(1);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }


    public void getFlagExt(final String username, final ProgressDialog dialog,  final SendId sendId) {


        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);
        Call<String> result = apiService.getLogExt(username);

        //dialog.dismiss();

        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    if (response.body() != null) {

                        dialog.show();

                        sendId.hapusGambar(Integer.valueOf(response.body()));

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                //Toast.makeText(context, "ada", Toast.LENGTH_SHORT).show();
                //dialog.dismiss();
                t.printStackTrace();
            }
        });

    }

    public void getAssetExt(final String provinsi, String ruas,  final ProgressDialog dialog,  final SendString sendId) {

        dbSegmen = new DbSegmen(context);
        DataSegmen dataSegmen = dbSegmen.getMaksSegment(provinsi, ruas);

        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);
        Call<String> result = apiService.getAssetExt(provinsi, ruas, dataSegmen.getNosegment(), dataSegmen.getSubsegment());

        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    if (response.body() != null) {
                        dialog.dismiss();
                        sendId.sendString(response.body());

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                dialog.dismiss();
                sendId.sendString("Sama,-");
                t.printStackTrace();
            }
        });

    }


    public void downAssetExt(final String provinsi, String ruas, int noseg, int subseg, ProgressDialog progressDialog, SendId oper) {

        final ArrayList<DetailDownload> downloads = new ArrayList<>();
        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);
        //Call<ListDownload> result;
        //result = apiService.downAssetExt(provinsi, ruas, noseg, subseg);

        Call<ListDownload> result;
        if(tipeLogin==99){
            result = apiService.downAssetExtDrainase(provinsi, ruas, noseg, subseg);
        }else{
            result = apiService.downAssetExt(provinsi, ruas, noseg, subseg);
        }

        result.enqueue(new Callback<ListDownload>() {
            @Override
            public void onResponse(Call<ListDownload> call, Response<ListDownload> response) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                try {
                    if (response.body() != null) {

                        for (int i = 0; i < response.body().getList().size(); i++) {
                            DetailDownload detailDownload = response.body().getList().get(i);
                            downloads.add(detailDownload);
                        }

                        TaskAssetExt taskAssetExt = new TaskAssetExt(context, downloads, ruas, noseg, subseg, id -> oper.hapusGambar(id));
                        taskAssetExt.execute("bisa", "bisa", "bisa");


                    }
                } catch (Exception e) {
                    Toast.makeText(context, "kosong", Toast.LENGTH_SHORT).show();

                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ListDownload> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                t.printStackTrace();
                Toast.makeText(context, "Belum terdapat segment pada ruas ini", Toast.LENGTH_LONG).show();


            }
        });

    }

    public void downDrainExt(String provinsi, final String ruas, final int noseg, final int subseg, final ProgressDialog progressDialog, final SendId oper) {

        final ArrayList<DetailDrainase> listDrainase = new ArrayList<>();
        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);
        apiService.downDrainExt(provinsi, ruas, noseg, subseg).enqueue(new Callback<ListDrainase>() {
            @Override
            public void onResponse(Call<ListDrainase> call, Response<ListDrainase> response) {

                if(progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                try {
                    if (response.body() != null) {

                        for(int i=0;i<response.body().getList().size();i++){
                            DetailDrainase detailDrainase= response.body().getList().get(i);
                            listDrainase.add(detailDrainase);
                        }

                        TaskDrainExt taskDrainExt = new TaskDrainExt(context, listDrainase, ruas,  new SendId() {
                            @Override
                            public void hapusGambar(int id) {
                                oper.hapusGambar(id);
                            }
                        });

                        taskDrainExt.execute("a", "a", "a");

                    }
                } catch (Exception e) {
                    Toast.makeText(context, "kosong", Toast.LENGTH_SHORT).show();

                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ListDrainase> call, Throwable t) {

                if(progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                t.printStackTrace();
                Toast.makeText(context, "Belum terdapat segment pada ruas ini", Toast.LENGTH_LONG).show();


            }
        });

    }

    public void uploadgambar(final File file, final String posisi) {

        RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("gambarku", file.getName(), mFile);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), posisi);

        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);
        Call<String> result = apiService.uploadFile(fileToUpload, filename);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    if (response.body() != null) {
                        Toast.makeText(context, "masuk", Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    Toast.makeText(context, file.getName(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                //Toast.makeText(context, posisi+"/"+file.getName(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();

            }
        });
    }


    public void saveRuas(String kodeprov, final Downloadruas downloadruas) {
        final ArrayList<DataRuas> listruas = new ArrayList<>();
        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);

        Call<Ruasku> result = apiService.getRuas(kodeprov);
        result.enqueue(new Callback<Ruasku>() {
            @Override
            public void onResponse(Call<Ruasku> call, Response<Ruasku> response) {
                try {
                    if (response.body() != null) {
                        if (response.body().getError().equals("FALSE")) {

                            for (int i = 0; i < response.body().getDataRuas().size(); i++) {
                                DataRuas dataRuas = new DataRuas(response.body().getDataRuas().get(i).getNoprov(), response.body().getDataRuas().get(i).getNoruas(), "0", "0", response.body().getDataRuas().get(i).getNamaruas(), 0);
                                listruas.add(dataRuas);
                            }
                            downloadruas.DownloadAll(listruas);
                        } else {


                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, "ruas gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Ruasku> call, Throwable t) {
                t.printStackTrace();

            }
        });

    }


    public void saveProvinsi(String kodeprov, SendId sendId) {
        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);

        Call<String> result = apiService.getProv(kodeprov);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    if (response.body() != null) {
                            session.saveSPString(Session.NAMAPROV, response.body());
                            sendId.hapusGambar(1);
                        }

                    }
               catch (Exception e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();

            }
        });

    }




    public void saveLahan(DataLahan dataLahan, int segmentAkhir, int subsegAkhir, final SendId sendId) {
        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);
        apiService.setLahan(dataLahan.getNoprov(), dataLahan.getNoruas(), dataLahan.getNosegment(), dataLahan.getSubsegment(), segmentAkhir, subsegAkhir, dataLahan.getPosisi(), dataLahan.getTipeLahan(), dataLahan.getTatagunaLahan(), dataLahan.getTinggiLahan()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {

                    sendId.hapusGambar(1);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();

                sendId.hapusGambar(1);

            }
        });

    }


    public void saveSaluran(DataSaluran dataSaluran, int segmentAkhir, int subsegAkhir, final SendId sendId) {
        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);

        Call<String> result;
        if(tipeLogin==99){
            result = apiService.setSaluranDrainase(dataSaluran.getNoprov(), dataSaluran.getNoruas(), dataSaluran.getNosegment(), dataSaluran.getSubsegment(), segmentAkhir, subsegAkhir, dataSaluran.getPosisi(),
                    dataSaluran.getTipeSaluran(), dataSaluran.getPermukaanSamping(), dataSaluran.getJenisPenampang(), dataSaluran.getDalamSaluran(), dataSaluran.getLebarSaluran(), dataSaluran.getTinggiAir(), dataSaluran.getTinggiSedimen(), dataSaluran.getJenisKonstruksi(), dataSaluran.getKondisiSaluran());
        }else{
            result = apiService.setSaluran(dataSaluran.getNoprov(), dataSaluran.getNoruas(), dataSaluran.getNosegment(), dataSaluran.getSubsegment(), segmentAkhir, subsegAkhir, dataSaluran.getPosisi(),
                    dataSaluran.getTipeSaluran(), dataSaluran.getPermukaanSamping(), dataSaluran.getJenisPenampang(), dataSaluran.getDalamSaluran(), dataSaluran.getLebarSaluran(), dataSaluran.getTinggiAir(), dataSaluran.getTinggiSedimen(), dataSaluran.getJenisKonstruksi(), dataSaluran.getKondisiSaluran());
        }

        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {

                    sendId.hapusGambar(1);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();

                sendId.hapusGambar(1);

            }
        });

    }


    public void savePerkerasan(DataInletMedian dataMinlet, int segmentAkhir, int subsegAkhir, final SendId sendId) {
        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);

        Call<String> result = apiService.setPerkerasan(dataMinlet.getNoprov(), dataMinlet.getNoruas(), dataMinlet.getNosegment(), dataMinlet.getSubsegment(), segmentAkhir, subsegAkhir, dataMinlet.getPosisi(), dataMinlet.getLebar());

        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {

                    sendId.hapusGambar(1);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();

                sendId.hapusGambar(1);

            }
        });

    }

    public void saveSPD(DataSpDalam dataSpDalam, int segmentAkhir, int subsegAkhir, final SendId sendId) {
        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);

        Call<String> result = apiService.setSPD(dataSpDalam.getNoprov(), dataSpDalam.getNoruas(), dataSpDalam.getNosegment(), dataSpDalam.getSubsegment(), segmentAkhir, subsegAkhir, dataSpDalam.getPosisi(), dataSpDalam.getLebar());

        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {

                    sendId.hapusGambar(1);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();

                sendId.hapusGambar(1);

            }
        });

    }

    public void saveSPL(DataSpLuar dataSpLuar, int segmentAkhir, int subsegAkhir, final SendId sendId) {
        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);

        Call<String> result = apiService.setSPL(dataSpLuar.getNoprov(), dataSpLuar.getNoruas(), dataSpLuar.getNosegment(), dataSpLuar.getSubsegment(), segmentAkhir, subsegAkhir, dataSpLuar.getPosisi(), dataSpLuar.getLebar());

        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {

                    sendId.hapusGambar(1);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();

                sendId.hapusGambar(1);

            }
        });

    }


    public void saveBahu(DataBahu dataBahu, int segmentAkhir, int subsegAkhir, final SendId sendId) {
        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);

        Call<String> result;
        if(tipeLogin==99){
            result = apiService.setBahuDrainase(dataBahu.getNoprov(), dataBahu.getNoruas(), dataBahu.getNosegment(), dataBahu.getSubSegment(), segmentAkhir, subsegAkhir, dataBahu.getPosisi(),
                    dataBahu.getTipeBahu(), dataBahu.getMaterialBahu(), dataBahu.getLebarBahu(), dataBahu.getKemiringanDerajat(), dataBahu.getKemiringanArah(), dataBahu.getKemiringanKondisi(),
                    dataBahu.getTipeBahuInner(), dataBahu.getMaterialInner(), dataBahu.getLebarBahuInner());
        }else{
            result = apiService.setBahu(dataBahu.getNoprov(), dataBahu.getNoruas(), dataBahu.getNosegment(), dataBahu.getSubSegment(), segmentAkhir, subsegAkhir, dataBahu.getPosisi(),
                    dataBahu.getTipeBahu(), dataBahu.getMaterialBahu(), dataBahu.getLebarBahu(), dataBahu.getKemiringanDerajat(), dataBahu.getKemiringanArah(), dataBahu.getKemiringanKondisi(),
                    dataBahu.getTipeBahuInner(), dataBahu.getMaterialInner(), dataBahu.getLebarBahuInner());
        }

        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {

                    sendId.hapusGambar(1);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();

                sendId.hapusGambar(1);

                Toast.makeText(context, "error bahu", Toast.LENGTH_SHORT).show();


            }
        });

    }


    public void saveLane(DataLane dataLane, int segmentAkhir, int subsegAkhir, final SendId sendId) {

        int lajur = Integer.valueOf(dataLane.getUrut().substring(1));

        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);

        Call<String> result;
        if(tipeLogin==99){
            result = apiService.setLaneDrainase(dataLane.getNoprov(), dataLane.getNoruas(), dataLane.getNosegment(), dataLane.getSubsegment(), segmentAkhir, subsegAkhir, dataLane.getPosisi(), lajur,
                    dataLane.getSc1(), dataLane.getLebarLane(), dataLane.getKemiringanDerajat(), dataLane.getKemiringanArah(), dataLane.getKemiringanKondisi());
        }else{
            result = apiService.setLane(dataLane.getNoprov(), dataLane.getNoruas(), dataLane.getNosegment(), dataLane.getSubsegment(), segmentAkhir, subsegAkhir, dataLane.getPosisi(), lajur,
                    dataLane.getSc1(), dataLane.getLebarLane(), dataLane.getKemiringanDerajat(), dataLane.getKemiringanArah(), dataLane.getKemiringanKondisi());
        }

        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {

                    sendId.hapusGambar(1);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();

                sendId.hapusGambar(1);

                Toast.makeText(context, "error Lane", Toast.LENGTH_SHORT).show();


            }
        });

    }

    public void saveSegmentku(DataSegmen dataSegmen, int segmentAkhir, int subsegAkhir, final SendId sendId) {

        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);
        apiService.setSegment(dataSegmen.getNoprov(), dataSegmen.getNoruas(), dataSegmen.getNosegment(), dataSegmen.getSubsegment(), segmentAkhir, subsegAkhir,
                dataSegmen.getVertikal(), dataSegmen.getHorizontal(), dataSegmen.getTipejalan(),dataSegmen.getLebarpvmt(), dataSegmen.getGrade()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {

                    sendId.hapusGambar(1);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();

                sendId.hapusGambar(1);

                Toast.makeText(context, "error Segment", Toast.LENGTH_SHORT).show();


            }
        });

    }

    public void saveMedian(DataMedian dataMedian, int segmentAkhir, int subsegAkhir, final SendId sendId) {

        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);
        apiService.setMedian(dataMedian.getNoprov(), dataMedian.getNoruas(), dataMedian.getNosegment(), dataMedian.getSubsegment(), segmentAkhir, subsegAkhir,dataMedian.getLebarMedian()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {

                    sendId.hapusGambar(1);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();

                sendId.hapusGambar(1);


            }
        });

    }

    public void saveImage(String tipe, String provinsi, String ruas, int segment, int subseg, String posisi, String path, String namafile, String latlong, int urut) {
        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);
        String[] lokasi = latlong.split("km");

        Call<String> result;
        if(tipeLogin==99){
            result = apiService.setImageDrainase(provinsi, ruas, segment, subseg, tipe, posisi, namafile, path, lokasi[0],lokasi[1], urut);
        }else{
            result = apiService.setImage(provinsi, ruas, segment, subseg, tipe, posisi, namafile, path, lokasi[0],lokasi[1], urut);
        }

        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    if (response.body() != null) {


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();

            }
        });

    }

    public void saveSinkrondetail(String provinsi, String ruas, int segment, int subseg, int segmentAkhir, int subsegAkhir, String detail, String posisi, String lajur, String jenisSurvey, String tanggal, int sinkronid, final SendId sendId) {

        String user = session.getUserku();

        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);

        Call<String> result;
        if(tipeLogin==99){
            result = apiService.setSinkrondetailDrainase(provinsi, ruas, segment, subseg, segmentAkhir, subsegAkhir, detail, posisi, lajur, jenisSurvey, user, tanggal, sinkronid);
        }else{
            result = apiService.setSinkrondetail(provinsi, ruas, segment, subseg, segmentAkhir, subsegAkhir, detail, posisi, lajur, jenisSurvey, user, tanggal, sinkronid);
        }

        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {

                    sendId.hapusGambar(1);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                sendId.hapusGambar(1);

                t.printStackTrace();

            }
        });

    }

    public void saveInlet(DataInletTrotoar dataInletTrotoar, final SendId sendId) {
        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);
        apiService.setInlet(dataInletTrotoar.getNoprov(), dataInletTrotoar.getNoruas(), dataInletTrotoar.getNosegment(), dataInletTrotoar.getSubsegment(),  dataInletTrotoar.getPosisi(),
                dataInletTrotoar.getKeberadaan(), dataInletTrotoar.getJenisPenampang(), dataInletTrotoar.getPanjang(), dataInletTrotoar.getLebar(), dataInletTrotoar.getTinggi(), dataInletTrotoar.getTinggiSedimen(), dataInletTrotoar.getJenisKonstruksi(), dataInletTrotoar.getKondisiSaluran()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {

                    sendId.hapusGambar(1);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();

                sendId.hapusGambar(1);

            }
        });

    }

    public void saveMinlet(DataInletMedian dataMinlet, final SendId sendId) {
        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);
        apiService.setMinlet(dataMinlet.getNoprov(), dataMinlet.getNoruas(), dataMinlet.getNosegment(), dataMinlet.getSubsegment(),  dataMinlet.getPosisi(),
                dataMinlet.getKeberadaan(), dataMinlet.getJenisPenampang(), dataMinlet.getPanjang(), dataMinlet.getLebar(), dataMinlet.getTinggi(), dataMinlet.getTinggiSedimen(), dataMinlet.getJenisKonstruksi(), dataMinlet.getKondisiSaluran()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {

                    sendId.hapusGambar(1);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();

                sendId.hapusGambar(1);
            }
        });

    }

    public void saveOutlet(DataOutlet dataOutlet, final SendId sendId) {
        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);
        apiService.setOutlet(dataOutlet.getNoprov(), dataOutlet.getNoruas(), dataOutlet.getNosegment(), dataOutlet.getSubsegment(),  dataOutlet.getPosisi(),
                dataOutlet.getKeberadaan(), dataOutlet.getJenisPenampang(), dataOutlet.getDiameter(), dataOutlet.getLebar(), dataOutlet.getTinggi(), dataOutlet.getJenisKonstruksi(), dataOutlet.getKondisiSaluran()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {

                    sendId.hapusGambar(1);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();

                sendId.hapusGambar(1);

            }
        });

    }

    public void saveGorong(DataCrossDrain dataGorong, final SendId sendId) {
        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);
        apiService.setGorong(dataGorong.getNoprov(), dataGorong.getNoruas(), dataGorong.getNosegment(), dataGorong.getSubsegment(),  dataGorong.getPosisi(),
                dataGorong.getKeberadaan(), dataGorong.getJenisPenampang(), dataGorong.getDiameter(), dataGorong.getLebar(), dataGorong.getTinggi(), dataGorong.getJenisKonstruksi(), dataGorong.getKondisiSaluran()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {

                    sendId.hapusGambar(1);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();

                sendId.hapusGambar(1);

            }
        });

    }

    public void saveLereng(DataDrainase dataLereng, final SendId sendId) {
        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);
        apiService.setLereng(dataLereng.getNoprov(), dataLereng.getNoruas(), dataLereng.getNosegment(), dataLereng.getSubsegment(),  dataLereng.getPosisi(),
                dataLereng.getKeberadaan(), dataLereng.getJenisPenampang(), dataLereng.getLebar(), dataLereng.getTinggi(), dataLereng.getTinggiSedimen(), dataLereng.getKondisiSaluran()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {

                    sendId.hapusGambar(1);

                } catch (Exception e) {
                    e.printStackTrace();


                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();

                sendId.hapusGambar(1);

            }
        });

    }

    public void saveSinkronid(String kodeprov, String noruas, String id, String tanggal) {
        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);

        Call<String> result;
        if(tipeLogin==99){
            result = apiService.setSinkronidDrainase(kodeprov, noruas, id, session.getUserku(), tanggal);
        }else{
            result = apiService.setSinkronid(kodeprov, noruas, id, session.getUserku(), tanggal);
        }

        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    //Toast.makeText(context, response.body(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();

            }
        });

    }

    public void cekSinkronUrut(final String kodeprov, final String noruas, final SendId sendId) {
        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);

        DbRuas dbRuas = new DbRuas(context);
        int flag = dbRuas.getFlag(kodeprov, noruas);

        Call<Integer> result;
        if(tipeLogin==99){
            result = apiService.cekSinkronUrutDrainase(kodeprov, noruas, flag);
        }else{
            result = apiService.cekSinkronUrut(kodeprov, noruas, flag);
        }

        result.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                try {
                    dbRuas.updateFLagReset(kodeprov, noruas, 0);
                    sendId.hapusGambar(response.body());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                t.printStackTrace();

            }
        });

    }


    public void cekSinkronid(final String kodeprov, final String ruas, final String id, final ProgressDialog dialog, final Sinkrondata sinkrondata) {
        final ArrayList<CekSinkron> cekSinkrons = new ArrayList<>();
        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);


        Call<CekSinkronku> result;

        if(tipeLogin==99){
            result = apiService.cekSinkronidDrainase(kodeprov, ruas, id);
        }else{
            result = apiService.cekSinkronid(kodeprov, ruas, id);
        }

        result.enqueue(new Callback<CekSinkronku>() {
            @Override
            public void onResponse(Call<CekSinkronku> call, Response<CekSinkronku> response) {
                dialog.dismiss();
                try {
                   // Toast.makeText(context, id+","+ response.body().getId(), Toast.LENGTH_SHORT).show();
                    DbRuas dbRuas = new DbRuas(context);
                    String provinsi = kodeprov;
                    dbRuas.updateSinkronId(String.valueOf(provinsi), ruas, response.body().getId());
                    for(int i =0 ; i<response.body().getCekSinkrons().size(); i++){
                        cekSinkrons.add(response.body().getCekSinkrons().get(i));
                    }

                    sinkrondata.data(cekSinkrons, response.body().getId());

                    //String a = dbRuas.getSinkronid(kodeprov, ruas);
                    //Toast.makeText(context, a, Toast.LENGTH_SHORT).show();




/*
                    DbRuas dbRuas = new DbRuas(context);
                    dbRuas.setRuas(kodeprov, ruas, response.body().getId());


                    Toast.makeText(context, response.body().getId(), Toast.LENGTH_SHORT).show();

                    for(int i =0 ; i<response.body().getCekSinkrons().size(); i++){
                        cekSinkrons.add(response.body().getCekSinkrons().get(i));
                    }*/

                   // sinkrondata.data(cekSinkrons);


                } catch (Exception e) {


                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CekSinkronku> call, Throwable t) {

                sinkrondata.data(cekSinkrons, id);
                dialog.dismiss();
                t.printStackTrace();

            }
        });
    }


    public void setLahan(final DataTemporari dataTemporari, final SendId sendId) {
        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);
        apiService.updateLahan(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment(), dataTemporari.getSubsegment(), dataTemporari.getPosisi()).enqueue(new Callback<DataLahan>() {
            @Override
            public void onResponse(Call<DataLahan> call, Response<DataLahan> response) {
                try {
                    if (response.body() != null) {

                        DbLahan dbLahan = new DbLahan(context);

                        DataLahan dataLahan = response.body();
                        dbLahan.saveLahanNormal(dataLahan, dataTemporari.getNosegment(), dataTemporari.getSubsegment(),  dataTemporari.getSegmentakhir(), dataTemporari.getSubsegmentakhir(), dataTemporari.getJenis());

                        dbTemporari.postTemporari(dataTemporari);

                        sendId.hapusGambar(1);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DataLahan> call, Throwable t) {
                t.printStackTrace();

            }
        });

    }


    public void setSaluran(final DataTemporari dataTemporari, final SendId sendId) {
        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);

        Call<DataSaluran> result;

        if(tipeLogin==99){
            result = apiService.updateSaluranDrainase(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment(), dataTemporari.getSubsegment(), dataTemporari.getPosisi());
        }else{
            result = apiService.updateSaluran(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment(), dataTemporari.getSubsegment(), dataTemporari.getPosisi());
        }

        result.enqueue(new Callback<DataSaluran>() {
            @Override
            public void onResponse(Call<DataSaluran> call, Response<DataSaluran> response) {
                try {
                    if (response.body() != null) {

                        DbSaluran dbSaluran = new DbSaluran(context);

                        DataSaluran dataSaluran = response.body();
                        dbSaluran.saveSaluranNormal(dataSaluran, dataTemporari.getNosegment(), dataTemporari.getSubsegment(),  dataTemporari.getSegmentakhir(), dataTemporari.getSubsegmentakhir(), dataTemporari.getJenis());

                        dbTemporari.postTemporari(dataTemporari);

                        sendId.hapusGambar(1);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DataSaluran> call, Throwable t) {
                t.printStackTrace();

            }
        });

    }

    public void setPerkerasan(final DataTemporari dataTemporari, final SendId sendId) {
        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);

        Call<DataInletMedian> result = apiService.updatePerkerasan(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment(), dataTemporari.getSubsegment(), dataTemporari.getPosisi());

        result.enqueue(new Callback<DataInletMedian>() {
            @Override
            public void onResponse(Call<DataInletMedian> call, Response<DataInletMedian> response) {
                try {
                    if (response.body() != null) {

                        DbMinlet dbMinlet = new DbMinlet(context);

                        DataInletMedian dataMinlet = response.body();
                        dbMinlet.saveMinletNormal(dataMinlet, dataTemporari.getNosegment(), dataTemporari.getSubsegment(),  dataTemporari.getSegmentakhir(), dataTemporari.getSubsegmentakhir(), dataTemporari.getJenis());

                        dbTemporari.postTemporari(dataTemporari);

                        sendId.hapusGambar(1);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DataInletMedian> call, Throwable t) {
                t.printStackTrace();

            }
        });

    }

    public void setSPD(final DataTemporari dataTemporari, final SendId sendId) {
        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);

        Call<DataSpDalam> result = apiService.updateSPD(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment(), dataTemporari.getSubsegment(), dataTemporari.getPosisi());

        result.enqueue(new Callback<DataSpDalam>() {
            @Override
            public void onResponse(Call<DataSpDalam> call, Response<DataSpDalam> response) {
                try {
                    if (response.body() != null) {

                        DbSpd dbSpd = new DbSpd(context);

                        DataSpDalam dataSpDalam = response.body();
                        dbSpd.saveNormal(dataSpDalam, dataTemporari.getNosegment(), dataTemporari.getSubsegment(),  dataTemporari.getSegmentakhir(), dataTemporari.getSubsegmentakhir(), dataTemporari.getJenis());

                        dbTemporari.postTemporari(dataTemporari);

                        sendId.hapusGambar(1);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DataSpDalam> call, Throwable t) {
                t.printStackTrace();

            }
        });

    }

    public void setSPL(final DataTemporari dataTemporari, final SendId sendId) {
        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);

        Call<DataSpLuar> result = apiService.updateSPL(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment(), dataTemporari.getSubsegment(), dataTemporari.getPosisi());

        result.enqueue(new Callback<DataSpLuar>() {
            @Override
            public void onResponse(Call<DataSpLuar> call, Response<DataSpLuar> response) {
                try {
                    if (response.body() != null) {

                        DbSpl dbSpl = new DbSpl(context);

                        DataSpLuar dataSpLuar = response.body();
                        dbSpl.saveNormal(dataSpLuar, dataTemporari.getNosegment(), dataTemporari.getSubsegment(),  dataTemporari.getSegmentakhir(), dataTemporari.getSubsegmentakhir(), dataTemporari.getJenis());

                        dbTemporari.postTemporari(dataTemporari);

                        sendId.hapusGambar(1);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DataSpLuar> call, Throwable t) {
                t.printStackTrace();

            }
        });

    }




    public void setBahu(final DataTemporari dataTemporari, final SendId sendId) {
        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);

        Call<DataBahu> result;

        if(tipeLogin==99){
            result = apiService.updateBahuDrainase(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment(), dataTemporari.getSubsegment(), dataTemporari.getPosisi());
        }else{
            result = apiService.updateBahu(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment(), dataTemporari.getSubsegment(), dataTemporari.getPosisi());
        }

        result.enqueue(new Callback<DataBahu>() {
            @Override
            public void onResponse(Call<DataBahu> call, Response<DataBahu> response) {
                try {
                    if (response.body() != null) {

                        DbBahu dbBahu = new DbBahu(context);

                        DataBahu dataBahu = response.body();
                        dbBahu.saveBahuNormal(dataBahu, dataTemporari.getNosegment(), dataTemporari.getSubsegment(),  dataTemporari.getSegmentakhir(), dataTemporari.getSubsegmentakhir(), dataTemporari.getJenis());

                        dbTemporari.postTemporari(dataTemporari);

                        sendId.hapusGambar(1);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DataBahu> call, Throwable t) {
                t.printStackTrace();

            }
        });

    }

    public void setLane(final DataTemporari dataTemporari, final SendId sendId) {

        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);

        Call<DataLane> result;

        if(tipeLogin==99){
            result = apiService.updateLaneDrainase(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment(), dataTemporari.getSubsegment(), dataTemporari.getPosisi(), dataTemporari.getUrut().substring(1));
        }else{
            result = apiService.updateLane(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment(), dataTemporari.getSubsegment(), dataTemporari.getPosisi(), dataTemporari.getUrut().substring(1));
        }


        result.enqueue(new Callback<DataLane>() {
            @Override
            public void onResponse(Call<DataLane> call, Response<DataLane> response) {
                try {
                    if (response.body() != null) {

                        DbLane dbLane = new DbLane(context);
                        DataLane dataLane = response.body();

                        String lajur;
                        if(dataLane.getPosisi().equals("kiri")){
                            lajur = "L"+dataLane.getUrut();
                        }else{
                            lajur = "R"+dataLane.getUrut();
                        }

                        dataLane.setUrut(lajur);
                        dbLane.saveLaneNormal(dataLane, dataTemporari.getNosegment(), dataTemporari.getSubsegment(),  dataTemporari.getSegmentakhir(), dataTemporari.getSubsegmentakhir(), dataTemporari.getJenis());
                        dbTemporari.postTemporari(dataTemporari);

                        sendId.hapusGambar(1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DataLane> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    public void setMedian(final DataTemporari dataTemporari, final SendId sendId) {
        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);
        apiService.updateMedian(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment(), dataTemporari.getSubsegment()).enqueue(new Callback<DataMedian>() {
            @Override
            public void onResponse(Call<DataMedian> call, Response<DataMedian> response) {
                try {
                    if (response.body() != null) {

                        DbMedian dbMedian = new DbMedian(context);

                        DataMedian dataMedian = response.body();
                        dbMedian.saveMedianNormal(dataMedian, dataTemporari.getNosegment(), dataTemporari.getSubsegment(),  dataTemporari.getSegmentakhir(), dataTemporari.getSubsegmentakhir(), dataTemporari.getJenis());

                        dbTemporari.postTemporari(dataTemporari);

                        sendId.hapusGambar(1);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DataMedian> call, Throwable t) {
                t.printStackTrace();

            }
        });

    }

    public void setSegment(final DataTemporari dataTemporari, final SendId sendId) {

        if(dataTemporari.getPosisi().equals("Update")) {

            InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);
            apiService.updateSegment(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment(), dataTemporari.getSubsegment()).enqueue(new Callback<DataSegmen>() {
                @Override
                public void onResponse(Call<DataSegmen> call, Response<DataSegmen> response) {
                    try {
                        if (response.body() != null) {

                            DbSegmen dbSegmen = new DbSegmen(context);

                            DataSegmen dataSegmen = response.body();

                            int jumlahsegment = response.body().getSegmentl1() + response.body().getSegmentl2() + response.body().getSegmentl3() + response.body().getSegmentl4() +
                                    response.body().getSegmentl5() + response.body().getSegmentl6() + response.body().getSegmentl7() + response.body().getSegmentl8() +
                                    response.body().getSegmentl9() + response.body().getSegmentl10() +
                                    response.body().getSegmentr1() + response.body().getSegmentr2() + response.body().getSegmentr3() + response.body().getSegmentr4() +
                                    response.body().getSegmentr5() + response.body().getSegmentr6() + response.body().getSegmentr7() + response.body().getSegmentr8() +
                                    response.body().getSegmentr9() + response.body().getSegmentr10();

                            dataSegmen.setJumlahsegment(jumlahsegment);
                            dbSegmen.saveSegmentNormal(dataSegmen, dataTemporari.getNosegment(), dataTemporari.getSubsegment(),  dataTemporari.getSegmentakhir(), dataTemporari.getSubsegmentakhir(), dataTemporari.getJenis());


                            dbTemporari.postTemporari(dataTemporari);
                            sendId.hapusGambar(1);
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<DataSegmen> call, Throwable t) {
                    t.printStackTrace();

                }
            });

        }else{

            String posisiLane;
            DbLane dbLane = new DbLane(context);

            if(dataTemporari.getUrut().substring(0,1).equals("L")){
                posisiLane = "kiri";
            }else{
                posisiLane = "kanan";
            }

            int segment = dbSpinner.getSegment(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getSubsegment());

            if(dataTemporari.getPosisi().equals("Hapus")){
                dbLane.hapusLane(dataTemporari.getNoprov(), dataTemporari.getNoruas(), segment, dataTemporari.getSubsegment(), posisiLane, dataTemporari.getUrut());
                dbTemporari.hapusTemporari(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment(), dataTemporari.getSubsegment(), "Lane", posisiLane, dataTemporari.getUrut());
                dbTemporari.hapusTemporari(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment(), dataTemporari.getSubsegment(), "Segment", "Tambah", dataTemporari.getUrut());

            }else{

                dbTemporari.hapusTemporari(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment(), dataTemporari.getSubsegment(), "Segment", "Hapus", dataTemporari.getUrut());
                dbLane.tambahLane(dataTemporari.getNoprov(), dataTemporari.getNoruas(), segment, dataTemporari.getSubsegment(),  posisiLane, dataTemporari.getUrut());
            }

            dbTemporari.postTemporari(dataTemporari);
            sendId.hapusGambar(1);

        }

    }

    public void setInlet(final DataTemporari dataTemporari, final SendId sendId) {
        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);
        apiService.updateInlet(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment(), dataTemporari.getSubsegment(), dataTemporari.getPosisi()).enqueue(new Callback<DataInletTrotoar>() {
            @Override
            public void onResponse(Call<DataInletTrotoar> call, Response<DataInletTrotoar> response) {
                try {
                    if (response.body() != null) {

                        DbInlet dbInlet = new DbInlet(context);

                        DataInletTrotoar dataInletTrotoar = response.body();
                        dbInlet.setInlet(dataInletTrotoar);
                        dbTemporari.postTemporari(dataTemporari);

                        sendId.hapusGambar(1);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DataInletTrotoar> call, Throwable t) {
                t.printStackTrace();

            }
        });

    }

    public void setMinlet(final DataTemporari dataTemporari, final SendId sendId) {
        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);
        apiService.updateMinlet(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment(), dataTemporari.getSubsegment(), dataTemporari.getPosisi()).enqueue(new Callback<DataInletMedian>() {
            @Override
            public void onResponse(Call<DataInletMedian> call, Response<DataInletMedian> response) {
                try {
                    if (response.body() != null) {

                        DbMinlet dbMinlet = new DbMinlet(context);

                        DataInletMedian dataInletMedian = response.body();
                        dbMinlet.setMinlet(dataInletMedian);
                        dbTemporari.postTemporari(dataTemporari);

                        sendId.hapusGambar(1);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DataInletMedian> call, Throwable t) {
                t.printStackTrace();

            }
        });

    }


    public void setOutlet(final DataTemporari dataTemporari, final SendId sendId) {
        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);
        apiService.updateOutlet(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment(), dataTemporari.getSubsegment(), dataTemporari.getPosisi()).enqueue(new Callback<DataOutlet>() {
            @Override
            public void onResponse(Call<DataOutlet> call, Response<DataOutlet> response) {
                try {
                    if (response.body() != null) {

                        DbOutlet dbOutlet = new DbOutlet(context);

                        DataOutlet dataOutlet = response.body();
                        dbOutlet.setSaluran(dataOutlet);
                        dbTemporari.postTemporari(dataTemporari);

                        sendId.hapusGambar(1);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DataOutlet> call, Throwable t) {
                t.printStackTrace();

            }
        });

    }

    public void setGorong(final DataTemporari dataTemporari, final SendId sendId) {
        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);
        apiService.updateGorong(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment(), dataTemporari.getSubsegment(), dataTemporari.getPosisi()).enqueue(new Callback<DataCrossDrain>() {
            @Override
            public void onResponse(Call<DataCrossDrain> call, Response<DataCrossDrain> response) {
                try {
                    if (response.body() != null) {

                        DbGorong dbGorong= new DbGorong(context);

                        DataCrossDrain dataCrossDrain = response.body();
                        dbGorong.setSaluran(dataCrossDrain);
                        dbTemporari.postTemporari(dataTemporari);

                        sendId.hapusGambar(1);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DataCrossDrain> call, Throwable t) {
                t.printStackTrace();

            }
        });

    }


    public void setLereng(final DataTemporari dataTemporari, final SendId sendId) {
        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);
        apiService.updateLereng(dataTemporari.getNoprov(), dataTemporari.getNoruas(), dataTemporari.getNosegment(), dataTemporari.getSubsegment(), dataTemporari.getPosisi()).enqueue(new Callback<DataDrainase>() {
            @Override
            public void onResponse(Call<DataDrainase> call, Response<DataDrainase> response) {
                try {
                    if (response.body() != null) {

                        DbLereng dbLereng= new DbLereng(context);

                        DataDrainase dataDrainase = response.body();
                        dbLereng.setLereng(dataDrainase);
                        dbTemporari.postTemporari(dataTemporari);

                        sendId.hapusGambar(1);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DataDrainase> call, Throwable t) {
                t.printStackTrace();

            }
        });

    }


    /*
    public void getDownload(String provinsi, String ruas, String segment, final SendDownload downloadData) {
        final ArrayList<DownloadDataIsi> downloads = new ArrayList<>();
        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);
        apiService.downloadata(provinsi, ruas, segment).enqueue(new Callback<DownloadDataku>() {
            @Override
            public void onResponse(Call<DownloadDataku> call, Response<DownloadDataku> response) {
                try {
                    if (response.body() != null) {

                        if (response.body() != null) {
                            //Toast.makeText(context, "berhasil", Toast.LENGTH_SHORT).show();

                            for (int i = 0; i < response.body().getList().size(); i++) {
                                DownloadDataIsi dataDownload = response.body().getList().get(i);
                                downloads.add(dataDownload);
                            }
                            downloadData.send(downloads);

                        }

                    }
                } catch (Exception e) {
                    Toast.makeText(context, "kosong", Toast.LENGTH_SHORT).show();

                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DownloadDataku> call, Throwable t) {
                t.printStackTrace();
              Toast.makeText(context, String.valueOf(t), Toast.LENGTH_SHORT).show();


            }
        });

    }*/


    /*public void getLokasiMedian(String ruas, final PolygonFunc polygonFunc) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("provinsi", getkodeprov(session.getKodeprov()));
        hashMap.put("ruas", ruas);
        final ArrayList<PolygonOptions> polygons = new ArrayList<>();
        final ArrayList<Koordinat> koordinats = new ArrayList<>();
        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);
        Call<Koordinatku> result = apiService.getLokasiMedian(hashMap);
        result.enqueue(new Callback<Koordinatku>() {
            @Override
            public void onResponse(Call<Koordinatku> call, Response<Koordinatku> response) {
                try {
                    if (response.body() != null) {

                        for (int i = 0; i < response.body().getKoordinats().size(); i++) {
                            PolygonOptions poly = new PolygonOptions();
                            //poly.fillColor(Color.GRAY);
                            poly.strokeColor(Color.parseColor("#9932CC"));
                            poly.fillColor(Color.parseColor("#809932CC"));
                            //String segment = response.body().getKoordinats().get(i).getNoSeg();
                            String[] latlong = response.body().getKoordinats().get(i).getKoordinat().split(",");
                            for (int j = 0; j < latlong.length; j++) {
                                String[] fosisi = latlong[j].split(" ");
                                double latitude = Double.parseDouble(fosisi[0]);
                                double longitude = Double.parseDouble(fosisi[1]);
                                poly.add(new LatLng(latitude, longitude));
                            }

                            Koordinat koordinat = new Koordinat(response.body().getNoprov(), response.body().getNoruas(), response.body().getNamajalan(), response.body().getKoordinats().get(i).getNoSeg(), response.body().getKoordinats().get(i).getPosisi(), response.body().getKoordinats().get(i).getLajur(), response.body().getKoordinats().get(i).getKoordinat());
                            koordinats.add(koordinat);
                            polygons.add(poly);
                        }

                        // Koordinat koordinat = new Koordinat(response.body().getNoProv(), response.body().getNoRuas(), response.body().getNamaJalan(), response.body().getNoSeg(), response.body().getPosisi(), response.body().getLajur());
                        polygonFunc.lihatPolygon(polygons, koordinats);
                        String[] latlong = response.body().getKoordinats().get(0).getKoordinat().split(",");
                        String[] fosisi = latlong[0].split(" ");
                        double latitude = Double.parseDouble(fosisi[0]);
                        double longitude = Double.parseDouble(fosisi[1]);
                       // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 18.0f));

                    } else {
                       // Snackbar.make(getWindow().getDecorView().getRootView(), "Data Polygon kosong", Snackbar.LENGTH_LONG)
                      //          .setAction("Action", null).show();
                       // dialog.dismiss();
                    }
                } catch (Exception e) {
                   // Snackbar.make(getWindow().getDecorView().getRootView(), "Data Polygon kosong", Snackbar.LENGTH_LONG)
                  //          .setAction("Action", null).show();
                  //  dialog.dismiss();
                    //  Toast.makeText(PilihLokasi.this, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();

                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Koordinatku> call, Throwable t) {
                t.printStackTrace();
                //dialog.setMessage(t.toString());
                // dialog.show();
              //  Snackbar.make(getWindow().getDecorView().getRootView(), "Tidak ada koneksi internet", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();

            }
        });
    }

    public void getLokasiSaluran(String ruas, final PolygonFunc polygonFunc) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("provinsi", getkodeprov(session.getKodeprov()));
        hashMap.put("ruas", ruas);
        final ArrayList<PolygonOptions> polygons = new ArrayList<>();
        final ArrayList<Koordinat> koordinats = new ArrayList<>();
        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);
        Call<Koordinatku> result = apiService.getLokasiSaluran(hashMap);
        result.enqueue(new Callback<Koordinatku>() {
            @Override
            public void onResponse(Call<Koordinatku> call, Response<Koordinatku> response) {
                try {
                    if (response.body() != null) {

                        for (int i = 0; i < response.body().getKoordinats().size(); i++) {
                            PolygonOptions poly = new PolygonOptions();
                            //poly.fillColor(Color.GRAY);
                            poly.strokeColor(Color.parseColor("#00FFFF"));
                            poly.fillColor(Color.parseColor("#5000FFFF"));
                            //String segment = response.body().getKoordinats().get(i).getNoSeg();
                            String[] latlong = response.body().getKoordinats().get(i).getKoordinat().split(",");
                            for (int j = 0; j < latlong.length; j++) {
                                String[] fosisi = latlong[j].split(" ");
                                double latitude = Double.parseDouble(fosisi[0]);
                                double longitude = Double.parseDouble(fosisi[1]);
                                poly.add(new LatLng(latitude, longitude));
                            }

                            Koordinat koordinat = new Koordinat(response.body().getNoprov(), response.body().getNoruas(), response.body().getNamajalan(), response.body().getKoordinats().get(i).getNoSeg(), response.body().getKoordinats().get(i).getPosisi(), response.body().getKoordinats().get(i).getLajur(), response.body().getKoordinats().get(i).getKoordinat());
                            koordinats.add(koordinat);
                            polygons.add(poly);
                        }

                        // Koordinat koordinat = new Koordinat(response.body().getNoProv(), response.body().getNoRuas(), response.body().getNamaJalan(), response.body().getNoSeg(), response.body().getPosisi(), response.body().getLajur());
                        polygonFunc.lihatPolygon(polygons, koordinats);
                        String[] latlong = response.body().getKoordinats().get(0).getKoordinat().split(",");
                        String[] fosisi = latlong[0].split(" ");
                        double latitude = Double.parseDouble(fosisi[0]);
                        double longitude = Double.parseDouble(fosisi[1]);
                        // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 18.0f));

                    } else {
                        // Snackbar.make(getWindow().getDecorView().getRootView(), "Data Polygon kosong", Snackbar.LENGTH_LONG)
                        //          .setAction("Action", null).show();
                        // dialog.dismiss();
                    }
                } catch (Exception e) {
                    // Snackbar.make(getWindow().getDecorView().getRootView(), "Data Polygon kosong", Snackbar.LENGTH_LONG)
                    //          .setAction("Action", null).show();
                    //  dialog.dismiss();
                    //  Toast.makeText(PilihLokasi.this, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();

                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Koordinatku> call, Throwable t) {
                t.printStackTrace();
                //dialog.setMessage(t.toString());
                // dialog.show();
                //  Snackbar.make(getWindow().getDecorView().getRootView(), "Tidak ada koneksi internet", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();

            }
        });
    }


    public void getLokasiBahu(String ruas, final PolygonFunc polygonFunc) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("provinsi", getkodeprov(session.getKodeprov()));
        hashMap.put("ruas", ruas);
        final ArrayList<PolygonOptions> polygons = new ArrayList<>();
        final ArrayList<Koordinat> koordinats = new ArrayList<>();
        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);
        Call<Koordinatku> result = apiService.getLokasiBahu(hashMap);
        result.enqueue(new Callback<Koordinatku>() {
            @Override
            public void onResponse(Call<Koordinatku> call, Response<Koordinatku> response) {
                try {
                    if (response.body() != null) {

                        for (int i = 0; i < response.body().getKoordinats().size(); i++) {
                            PolygonOptions poly = new PolygonOptions();
                            //poly.fillColor(Color.GRAY);
                            poly.strokeColor(Color.parseColor("#A9A9A9"));
                            poly.fillColor(Color.parseColor("#50A9A9A9"));
                            //String segment = response.body().getKoordinats().get(i).getNoSeg();
                            String[] latlong = response.body().getKoordinats().get(i).getKoordinat().split(",");
                            for (int j = 0; j < latlong.length; j++) {
                                String[] fosisi = latlong[j].split(" ");
                                double latitude = Double.parseDouble(fosisi[0]);
                                double longitude = Double.parseDouble(fosisi[1]);
                                poly.add(new LatLng(latitude, longitude));
                            }

                            Koordinat koordinat = new Koordinat(response.body().getNoprov(), response.body().getNoruas(), response.body().getNamajalan(), response.body().getKoordinats().get(i).getNoSeg(), response.body().getKoordinats().get(i).getPosisi(), response.body().getKoordinats().get(i).getLajur(), response.body().getKoordinats().get(i).getKoordinat());
                            koordinats.add(koordinat);
                            polygons.add(poly);
                        }

                        // Koordinat koordinat = new Koordinat(response.body().getNoProv(), response.body().getNoRuas(), response.body().getNamaJalan(), response.body().getNoSeg(), response.body().getPosisi(), response.body().getLajur());
                        polygonFunc.lihatPolygon(polygons, koordinats);
                        String[] latlong = response.body().getKoordinats().get(0).getKoordinat().split(",");
                        String[] fosisi = latlong[0].split(" ");
                        double latitude = Double.parseDouble(fosisi[0]);
                        double longitude = Double.parseDouble(fosisi[1]);
                        // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 18.0f));

                    } else {
                        // Snackbar.make(getWindow().getDecorView().getRootView(), "Data Polygon kosong", Snackbar.LENGTH_LONG)
                        //          .setAction("Action", null).show();
                        // dialog.dismiss();
                    }
                } catch (Exception e) {
                    // Snackbar.make(getWindow().getDecorView().getRootView(), "Data Polygon kosong", Snackbar.LENGTH_LONG)
                    //          .setAction("Action", null).show();
                    //  dialog.dismiss();
                    //  Toast.makeText(PilihLokasi.this, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();

                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Koordinatku> call, Throwable t) {
                t.printStackTrace();
                //dialog.setMessage(t.toString());
                // dialog.show();
                //  Snackbar.make(getWindow().getDecorView().getRootView(), "Tidak ada koneksi internet", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();

            }
        });
    }*/


    public void getLokasiLane(String ruas, final PolygonFunc polygonFunc) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("provinsi", session.getKodeprov());
        hashMap.put("ruas", ruas);
        final ArrayList<PolygonOptions> polygons = new ArrayList<>();
        final ArrayList<Koordinat> koordinats = new ArrayList<>();
        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);
        Call<Koordinatku> result = apiService.getLokasiRuas(session.getKodeprov(), ruas);
        result.enqueue(new Callback<Koordinatku>() {
            @Override
            public void onResponse(Call<Koordinatku> call, Response<Koordinatku> response) {
                try {
                    if (response.body() != null) {

                        for (int i = 0; i < response.body().getKoordinats().size(); i++) {
                            PolygonOptions poly = new PolygonOptions();
                            //poly.fillColor(Color.GRAY);
                            poly.strokeColor(Color.parseColor("#50000000"));
                            poly.fillColor(Color.parseColor("#00000000"));
                            //String segment = response.body().getKoordinats().get(i).getNoSeg();
                            String[] latlong = response.body().getKoordinats().get(i).getKoordinat().split(",");
                            for (int j = 0; j < latlong.length; j++) {
                                String[] fosisi = latlong[j].split(" ");
                                double latitude = Double.parseDouble(fosisi[0]);
                                double longitude = Double.parseDouble(fosisi[1]);
                                poly.add(new LatLng(latitude, longitude));
                            }

                            Koordinat koordinat = new Koordinat(response.body().getNoprov(), response.body().getNoruas(), response.body().getNamajalan(), response.body().getKoordinats().get(i).getNoSeg(), response.body().getKoordinats().get(i).getPosisi(), response.body().getKoordinats().get(i).getLajur(), response.body().getKoordinats().get(i).getKoordinat());
                            koordinats.add(koordinat);
                            polygons.add(poly);
                        }

                        // Koordinat koordinat = new Koordinat(response.body().getNoProv(), response.body().getNoRuas(), response.body().getNamaJalan(), response.body().getNoSeg(), response.body().getPosisi(), response.body().getLajur());
                        polygonFunc.lihatPolygon(polygons, koordinats);
                        String[] latlong = response.body().getKoordinats().get(0).getKoordinat().split(",");
                        String[] fosisi = latlong[0].split(" ");
                        double latitude = Double.parseDouble(fosisi[0]);
                        double longitude = Double.parseDouble(fosisi[1]);
                        // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 18.0f));

                    } else {
                        // Snackbar.make(getWindow().getDecorView().getRootView(), "Data Polygon kosong", Snackbar.LENGTH_LONG)
                        //          .setAction("Action", null).show();
                        // dialog.dismiss();
                    }
                } catch (Exception e) {
                    // Snackbar.make(getWindow().getDecorView().getRootView(), "Data Polygon kosong", Snackbar.LENGTH_LONG)
                    //          .setAction("Action", null).show();
                    //  dialog.dismiss();
                    //  Toast.makeText(PilihLokasi.this, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();

                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Koordinatku> call, Throwable t) {
                t.printStackTrace();
                //dialog.setMessage(t.toString());
                // dialog.show();
                //  Snackbar.make(getWindow().getDecorView().getRootView(), "Tidak ada koneksi internet", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();

            }
        });
    }

/*
    public void saveFulldata(final String kodeprov, final String ruas, String segment,  final DownloadData downloadData) {
        final ArrayList<DataDownload> downloads = new ArrayList<>();
        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);
        Call<Downloadku> result = apiService.downloadfull(kodeprov, ruas, segment);
        result.enqueue(new Callback<Downloadku>() {
            @Override
            public void onResponse(Call<Downloadku> call, Response<Downloadku> response) {
                try {
                    if (response.body() != null) {
                        // Toast.makeText(context, "bisakok", Toast.LENGTH_SHORT).show();
                        for (int i = 0; i < response.body().getList().size(); i++) {
                            DataDownload dataDownload = response.body().getList().get(i);
                            downloads.add(dataDownload);
                        }
                        downloadData.DownloadAll(downloads);

                    }
                } catch (Exception e) {
                    Toast.makeText(context, "salah"+ruas, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Downloadku> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(context, "failure " + ruas, Toast.LENGTH_SHORT).show();
            }
        });
    }*/

/*
    public void saveFulldata(final String kodeprov, final String ruas, final String segment) {
        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);
        Call<Downloadku> result = apiService.downloadfull("10", "003", "1");
        result.enqueue(new Callback<Downloadku>() {
            @Override
            public void onResponse(Call<Downloadku> call, Response<Downloadku> response) {
                try {
                    if (response.body() != null) {

                        Toast.makeText(context, "berhasil", Toast.LENGTH_SHORT).show();
                       for (int i = 0; i < response.body().getList().size(); i++) {
                            DataDownload dataDownload = response.body().getList().get(i);
                            downloads.add(dataDownload);
                        }
                        downloadData.DownloadAll(downloads);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, "gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Downloadku> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(context, "salah", Toast.LENGTH_SHORT).show();
               // Toast.makeText(context, kodeprov+", "+ruas+", "+segment, Toast.LENGTH_SHORT).show();

            }
        });

    }*/


    //download baru 2021
    public void DownloadNew(String provinsi, final String ruas, final int pruas, final ProgressDialog progressDialog, final SendId oper) {
        final ArrayList<DetailDownload> downloads = new ArrayList<>();
        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);

        Call<ListDownload> result;
        if(tipeLogin==99){
            result = apiService.downloadfirstDrainase(provinsi, ruas);
        }else{
            result = apiService.downloadfirst(provinsi, ruas);
        }

        result.enqueue(new Callback<ListDownload>() {
                @Override
                public void onResponse(Call<ListDownload> call, Response<ListDownload> response) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    try {
                        if (response.body() != null) {

                            for (int i = 0; i < response.body().getList().size(); i++) {
                                DetailDownload detailDownload = response.body().getList().get(i);
                                downloads.add(detailDownload);
                            }

                            TaskDownloadBaru taskDownloadBaru = new TaskDownloadBaru(context, downloads, ruas, pruas, response.body().getSinkronid(), new SendId() {
                                @Override
                                public void hapusGambar(int id) {
                                    oper.hapusGambar(id);
                                }
                            });
                            taskDownloadBaru.execute("bisa", "bisa", "bisa");


                        }
                    } catch (Exception e) {
                        Toast.makeText(context, "kosong", Toast.LENGTH_SHORT).show();

                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ListDownload> call, Throwable t) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    t.printStackTrace();
                    Toast.makeText(context, "Belum terdapat segment pada ruas ini", Toast.LENGTH_LONG).show();


                }
            });

    }



    public void DownloadDrainase(String provinsi, final String ruas, final int pruas, final ProgressDialog progressDialog, final SendId oper) {

        final ArrayList<DetailDrainase> listDrainase = new ArrayList<>();
        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);
        apiService.downloadDrainase(provinsi, ruas).enqueue(new Callback<ListDrainase>() {
            @Override
            public void onResponse(Call<ListDrainase> call, Response<ListDrainase> response) {

                if(progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                try {
                    if (response.body() != null) {

                        for(int i=0;i<response.body().getList().size();i++){
                            DetailDrainase detailDrainase= response.body().getList().get(i);
                            listDrainase.add(detailDrainase);
                        }

                        TaskDownloadDrainase taskDownloadDrainase = new TaskDownloadDrainase(context, listDrainase, ruas, pruas, new SendId() {
                            @Override
                            public void hapusGambar(int id) {
                                oper.hapusGambar(id);
                            }
                        });

                        taskDownloadDrainase.execute("a", "a", "a");

                    }
                } catch (Exception e) {
                    Toast.makeText(context, "kosong", Toast.LENGTH_SHORT).show();

                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ListDrainase> call, Throwable t) {

                if(progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                t.printStackTrace();
                Toast.makeText(context, "Belum terdapat segment pada ruas ini", Toast.LENGTH_LONG).show();


            }
        });

    }


    //Lihat Jumlah Segment
    public void cekJumlahSegment(String provinsi, final String ruas, int segment, final ProgressDialog progressDialog, final InterfaceCekJumlahSegment oper) {
        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);
        apiService.cekJumlahSegment(provinsi, ruas, segment).enqueue(new Callback<ListJumlahSegment>() {
            @Override
            public void onResponse(Call<ListJumlahSegment> call, Response<ListJumlahSegment> response) {
                if(progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                try {
                    if (response.body() != null) {

                        oper.cekSegment(response.body().getHasil(), response.body().getSegmentDb(), response.body().getDetailDownloads());

                    }
                } catch (Exception e) {
                    //Toast.makeText(context, "kosong", Toast.LENGTH_SHORT).show();

                    oper.cekSegment(response.body().getHasil(), response.body().getSegmentDb(), response.body().getDetailDownloads());

                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ListJumlahSegment> call, Throwable t) {
                if(progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                t.printStackTrace();
                Toast.makeText(context, "Belum terdapat segment pada ruas ini", Toast.LENGTH_LONG).show();

                /*TaskDownloadBaru taskDownloadBaru = new TaskDownloadBaru(context, downloads, ruas, pruas, new SendId() {
                    @Override
                    public void hapusGambar(int id) {
                        oper.hapusGambar(id);
                    }
                });
                taskDownloadBaru.execute("bisa", "bisa","bisa");*/


            }
        });

    }




    public void saveLahanUnd(DataLahan dataLahan) {
        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);
        apiService.setLahanUnd(dataLahan.getNoprov(), dataLahan.getNoruas(), String.valueOf(dataLahan.getNosegment()), dataLahan.getPosisi(), dataLahan.getTipeLahan(), dataLahan.getTatagunaLahan(), dataLahan.getTinggiLahan()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    if (response.body() != null) {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();

            }
        });

    }

    public void saveSaluranUnd(DataSaluran dataSaluran) {
        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);
        apiService.setSaluranUnd(dataSaluran.getNoprov(), dataSaluran.getNoruas(), String.valueOf(dataSaluran.getNosegment()), dataSaluran.getPosisi(), dataSaluran.getTipeSaluran(), dataSaluran.getLebarSaluran(), dataSaluran.getDalamSaluran()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    if (response.body() != null) {
                        //  Toast.makeText(context, response.body(), Toast.LENGTH_SHORT).show();
                        // Toast.makeText(DetailLahan.this, response.body(), Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();

            }
        });

    }

    public void saveBahuUnd(DataBahu dataBahu) {
        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);
        apiService.setBahuUnd(dataBahu.getNoprov(), dataBahu.getNoruas(), String.valueOf(dataBahu.getNosegment()), dataBahu.getPosisi(), dataBahu.getTipeBahu(), dataBahu.getLebarBahu(), dataBahu.getTipeBahuInner(), dataBahu.getLebarBahuInner()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    if (response.body() != null) {
                        //Toast.makeText(context, response.body(), Toast.LENGTH_SHORT).show();
                        // Toast.makeText(DetailLahan.this, response.body(), Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();

                Toast.makeText(context, "error bahu", Toast.LENGTH_SHORT).show();


            }
        });

    }


    public void saveLaneUnd(DataLane dataLane) {

        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);
        apiService.setLaneUnd(dataLane.getNoprov(), dataLane.getNoruas(), String.valueOf(dataLane.getNosegment()), dataLane.getPosisi() , dataLane.getSc1(), dataLane.getSc1(), dataLane.getLebarLane()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    if (response.body() != null) {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();

                Toast.makeText(context, "error Lane", Toast.LENGTH_SHORT).show();


            }
        });

    }


    public void saveMedianUnd(DataMedian dataMedian) {

        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);
        apiService.setMedianUnd(dataMedian.getNoprov(), dataMedian.getNoruas(), String.valueOf(dataMedian.getNosegment()),   "" ,dataMedian.getLebarMedian()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    if (response.body() != null) {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();

                // Toast.makeText(context, "error Median", Toast.LENGTH_SHORT).show();


            }
        });

    }

    public void saveImageUnd(String tipe, String provinsi, String ruas, int segment, String posisi, String path, String namafile, String latlong, int urut) {
        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);
        String[] lokasi = latlong.split("km");
        apiService.setImageUnd(provinsi, ruas, segment, tipe, posisi, namafile, path, lokasi[0],lokasi[1], urut).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    if (response.body() != null) {

                        // Toast.makeText(context, response.body(), Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();

            }
        });

    }

}
