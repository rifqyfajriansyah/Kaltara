package com.example.roadmanagement.kaltara.FormTab;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.FormTab.Helper.AnimationTab;
import com.example.roadmanagement.kaltara.FormTab.Helper.DialogTab;
import com.example.roadmanagement.kaltara.FormTab.Helper.IntTab;
import com.example.roadmanagement.kaltara.FormTab.Helper.SaveLogic;
import com.example.roadmanagement.kaltara.FormTab.Helper.TabImage;
import com.example.roadmanagement.kaltara.FormTerusan.FragmentInlet;
import com.example.roadmanagement.kaltara.FormTerusan.HelperFormTerusan;
import com.example.roadmanagement.kaltara.Interface.DataInletTrotoar;
import com.example.roadmanagement.kaltara.Interface.DataTemporari;
import com.example.roadmanagement.kaltara.Interface.SendId;
import com.example.roadmanagement.kaltara.adapter.ImageAdapter;
import com.example.roadmanagement.kaltara.databaseHelper.DbInlet;
import com.example.roadmanagement.kaltara.databaseHelper.DbTemporari;
import com.example.roadmanagement.kaltara.helper.GRadioGroup;
import com.example.roadmanagement.kaltara.helper.HelperList;
import com.example.roadmanagement.kaltara.helper.HelperTextValue;
import com.example.roadmanagement.kaltara.helper.ImageHelper;
import com.example.roadmanagement.kaltara.helper.PermissionHelper;
import com.example.roadmanagement.kaltara.helper.PermissionImage;
import com.example.roadmanagement.kaltara.helper.Session;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

public class TabInlet extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, AdapterView.OnItemSelectedListener{

    //Widget
    ImageView imGaleri, imFoto;
    Spinner spKeberadaan, spKonstruksi, spKondisi;
    EditText edTinggi, edPanjang, edLebar, edSedimen;
    RecyclerView recInlet;
    CardView bSave, bAdd;
    RadioButton radioCurb, radioGutter, radioCombination;
    LinearLayout formInlet, boxH, boxW;


    //Variable
    String  namaFile, sLokasi, asal, keberadaanInlet, jenisPenampang, jenisKonstruksi, kondisiSaluran, thumbnail, fileNama;
    float maxLebar, maxDalam, maxTinggiAir, maxTinggiSedimen;
    File fileFoto, iconFoto, tempFoto, directory;
    protected Location location;
    protected String locationKM;
    String [] arrKeberadaan, arrKontruksi, arrKondisi, arrKeberadaanValue;
    ArrayList<File> listImage;
    ArrayList<File> listThumbnail;
    List<String> listLokasi;
    int foto;

    //Object
    Context context;
    ImageAdapter imageAdapter;
    PermissionHelper permissionHelper;
    PermissionImage permissionImage;
    HelperList helperList;
    ImageHelper imageHelper;
    Session session;
    HelperFormTerusan helperFormTerusan;

    DataInletTrotoar dataInlet;
    DbInlet dbInlet;

    GRadioGroup groupHelper;

    private FusedLocationProviderClient mFusedLocationClient;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private long UPDATE_INTERVAL = 2 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */

    private TextView txKonstruksi, txPenampang, txLebar, txPanjang, txTinggi, txKondisi;
    ImageView iconEdit, iconDone, iconCancel, iconExpand;
    LinearLayoutCompat formEdit, formShow;
    private String valProv, valRuas, valPosisi;
    private int valSeg, valSubseg;

    private boolean flag_show = true;
    private ViewGroup parentView;

    public static TabInlet newInstance(String noprov, String noruas, int noseg, int subseg, String posisi) {
        TabInlet fragment = new TabInlet();
        Bundle args = new Bundle();
        args.putString("provinsi", noprov);
        args.putString("ruas", noruas);
        args.putInt("segment", noseg);
        args.putInt("subseg", subseg);
        args.putString("posisi", posisi);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        valProv = getArguments().getString("provinsi");
        valRuas = getArguments().getString("ruas");
        valSeg = getArguments().getInt("segment");
        valSubseg = getArguments().getInt("subseg");
        valPosisi = getArguments().getString("posisi");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab_fragment_inlet, container, false);
        parentView = container;

        context = getActivity();
        initWidget(view);
        initObject();
        initValue();

        AnimationTab.setFormEdit(formEdit,  false, iconEdit, iconDone, iconCancel, iconExpand, parentView);
        iconEdit.setVisibility(session.getUserTipe()==99?View.VISIBLE:View.GONE);

        locationKM = "0km0";
        getLastLocation();

        iconEdit.setOnClickListener(view1 -> {
            AnimationTab.setFormEdit(formEdit,  true, iconEdit, iconDone, iconCancel, iconExpand, parentView);
        });

        iconDone.setOnClickListener(view1 -> {

            DialogTab.setDialogSave(context, position -> {


                if(position==1){


                    dataInlet.setKeberadaan(keberadaanInlet);
                    dataInlet.setGambar(helperList.getNamaImage(listImage));
                    dataInlet.setIcon(helperList.getNamaImage(listThumbnail));
                    dataInlet.setLokasi(helperList.getLokasi(listLokasi));

                    if(keberadaanInlet.equals("Tidak ada inlet")){

                        dataInlet.setJenisPenampang(null);
                        dataInlet.setTinggi(0);
                        dataInlet.setPanjang(0);
                        dataInlet.setLebar(0);
                        dataInlet.setTinggiSedimen(0);
                        dataInlet.setJenisKonstruksi(null);
                        dataInlet.setKondisiSaluran(null);


                    }else {

                        Float lebar, panjang, tinggi, sedimen;
                        lebar = Float.parseFloat(edLebar.getText().toString());
                        panjang = Float.parseFloat(edPanjang.getText().toString());
                        tinggi = Float.parseFloat(edTinggi.getText().toString());
                        sedimen = Float.parseFloat(edSedimen.getText().toString());

                        dataInlet.setTinggi(tinggi);
                        dataInlet.setLebar(lebar);
                        dataInlet.setPanjang(panjang);
                        dataInlet.setTinggiSedimen(sedimen);
                        dataInlet.setJenisPenampang(jenisPenampang);
                        dataInlet.setJenisKonstruksi(jenisKonstruksi);
                        dataInlet.setKondisiSaluran(kondisiSaluran);
                    }

                    if(!dataInlet.getKeberadaan().equals("Tidak ada inlet") && (dataInlet.getPanjang()==0 ||  dataInlet.getTinggiSedimen()==0)){
                        Toast.makeText(context, "Silahkan isi data terlebih dahulu", Toast.LENGTH_SHORT).show();
                    }else {

                        flag_show = true;
                        AnimationTab.setFormEdit(formEdit,  false, iconEdit, iconDone, iconCancel, iconExpand, parentView);
                        AnimationTab.setFormShow(formShow,  flag_show, iconExpand, parentView);
                        AnimationTab.hideKeyboardFrom(context, view);


                        dbInlet.setInlet(dataInlet);
                        DbTemporari dbTemporari = new DbTemporari(context);

                        String foto;
                        String status;

                        if(session.getTipesurvey()!=null){
                            status = session.getTipesurvey();
                        }else{
                            status = dbTemporari.cekSurveyTemporariTipe(dataInlet.getNoprov(), dataInlet.getNoruas(), "Inlet ditrotoar", dataInlet.getPosisi(), "");
                        }

                        if(listImage.size()>0){
                            foto = "1";
                        }else{
                            foto = "0";
                        }

                        DataTemporari dataTemporari = new DataTemporari(dataInlet.getNoprov(), dataInlet.getNoruas(), dataInlet.getNosegment(), dataInlet.getSubsegment(), "Inlet ditrotoar", dataInlet.getPosisi(), "",  status, dataInlet.getNosegment(), dataInlet.getSubsegment(), foto, "0");
                        dbTemporari.postTemporari(dataTemporari);

                        initValue();
                        Toast.makeText(context, "Perubahan berhasil disimpan", Toast.LENGTH_SHORT).show();


                    }






                }

            });


        });

        iconCancel.setOnClickListener(view1 -> {

            DialogTab.setDialogAlert(context, "Apakah anda ingin membatalkan perubahan ?", new IntTab() {
                @Override
                public void sendPosition(int position) {
                    if(position==1){
                        flag_show = true;
                        AnimationTab.setFormEdit(formEdit,  false, iconEdit, iconDone, iconCancel, iconExpand, parentView);
                        AnimationTab.setFormShow(formShow,  flag_show, iconExpand, parentView);
                        AnimationTab.hideKeyboardFrom(context, view);
                    }
                }
            });

        });

        iconExpand.setOnClickListener(view1 -> {
            flag_show = !flag_show;
            AnimationTab.setFormShow(formShow,  flag_show, iconExpand, parentView);

        });


        imFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                namaFile = permissionImage.getNamaFile("InletT", valProv, valRuas, String.valueOf(valSeg), String.valueOf(valSubseg),  valPosisi);

                thumbnail = permissionImage.getThumbnailName(namaFile, directory, listImage.size());
                fileNama = permissionImage.getFullNameImage(namaFile, directory, listImage.size());

                getLastLocation();
                if(permissionHelper.getManifest()){
                    Intent i = permissionImage.getFotoLokasi(context, fileNama, sLokasi);
                    startActivityForResult(i, 1);
                }
            }
        });

        imGaleri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                namaFile = permissionImage.getNamaFile("InletT", valProv, valRuas, String.valueOf(valSeg), String.valueOf(valSubseg),  valPosisi);

                thumbnail = permissionImage.getThumbnailName(namaFile, directory, listImage.size());
                fileNama = permissionImage.getFullNameImage(namaFile, directory, listImage.size());

                getLastLocation();

                if(permissionHelper.getManifest()){
                    Intent i = permissionImage.getFotoGaleri();
                    startActivityForResult(i, 2);
                }
            }
        });

        setOnclickWidget();


        edLebar.setOnFocusChangeListener((view1, b) -> {
            AnimationTab.setFormShow(formShow, getParamShow(b), iconExpand, parentView);
        });

        edTinggi.setOnFocusChangeListener((view1, b) -> {
            AnimationTab.setFormShow(formShow, getParamShow(b), iconExpand, parentView);
        });

        edPanjang.setOnFocusChangeListener((view1, b) -> {
            AnimationTab.setFormShow(formShow, getParamShow(b), iconExpand, parentView);
        });

        edSedimen.setOnFocusChangeListener((view1, b) -> {
            AnimationTab.setFormShow(formShow, getParamShow(b), iconExpand, parentView);
        });

        return view;
    }

    private boolean getParamShow(boolean param){

        if(param){
            return false;
        }else{
            return flag_show;
        }

    }

    private void initWidget(View view){

        imFoto = view.findViewById(R.id.tabFotoCamera);
        imGaleri = view.findViewById(R.id.tabFotoGaleri);

        spKeberadaan = view.findViewById(R.id.tabInletSpinKeberadaan);
        spKonstruksi = view.findViewById(R.id.tabInletSpinKonstruksi);
        spKondisi = view.findViewById(R.id.tabInletSpinKondisi);

        edTinggi = view.findViewById(R.id.tabInletEdTinggi);
        edLebar = view.findViewById(R.id.tabInletEdLebar);
        edPanjang = view.findViewById(R.id.tabInletEdPanjang);
        edSedimen = view.findViewById(R.id.tabInletEdSedimen);

        bAdd = view.findViewById(R.id.tabFotoAdd);

        boxH = view.findViewById(R.id.tabInletBoxH);
        boxW = view.findViewById(R.id.tabInletBoxW);

        formInlet = view.findViewById(R.id.tabInletForm);
        recInlet = view.findViewById(R.id.tabFotoRec);

        formEdit = view.findViewById(R.id.tabInletFormEdit);
        formShow = view.findViewById(R.id.tabInletFormShow);

        txKonstruksi = view.findViewById(R.id.tabInletTxKonstruksi);
        txPenampang = view.findViewById(R.id.tabInletTXPenampang);
        txKondisi = view.findViewById(R.id.tabInletTxKondisi);
        txPanjang = view.findViewById(R.id.tabInletTxPanjang);
        txTinggi = view.findViewById(R.id.tabInletTxTinggi);
        txLebar = view.findViewById(R.id.tabInletTxLebar);

        iconEdit = view.findViewById(R.id.tabToolEdit);
        iconCancel = view.findViewById(R.id.tabToolClose);
        iconDone= view.findViewById(R.id.tabToolSave);
        iconExpand = view.findViewById(R.id.tabToolExpand);


        groupHelper = new GRadioGroup(context);

        radioCurb = view.findViewById(R.id.tabInletRadioCurb);
        radioGutter = view.findViewById(R.id.tabInletRadioGutton);
        radioCombination = view.findViewById(R.id.tabInletRadioCombination);

        //pilihanTipe5.removeAllViews();

        permissionHelper = new PermissionHelper(context);
        permissionImage = new PermissionImage(context);
        session = new Session(context);
        imageHelper = new ImageHelper(context);

        dbInlet = new DbInlet(context);

        helperList = new HelperList();
        helperFormTerusan = new HelperFormTerusan(context);




    }

    private void initObject(){

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mGoogleApiClient.connect();

        spKeberadaan.setAdapter(helperList.getSpinner(context, R.array.keberaaan_inlet));
        spKeberadaan.setOnItemSelectedListener(this);

        spKonstruksi.setAdapter(helperList.getSpinner(context, R.array.konstruksi_inlet));
        spKonstruksi.setOnItemSelectedListener(this);

        spKondisi.setAdapter(helperList.getSpinner(context, R.array.kondisi_saluran));
        spKondisi.setOnItemSelectedListener(this);

    }



    private void initValue(){

        arrKeberadaan = helperList.getListSpinner(context, R.array.keberaaan_inlet);
        arrKeberadaanValue = helperList.getListSpinner(context, R.array.keberaaan_inlet_value);
        arrKondisi= helperList.getListSpinner(context, R.array.kondisi_saluran);
        arrKontruksi = helperList.getListSpinner(context, R.array.konstruksi_inlet);

        directory = permissionImage.getFolderFile("InletT", valProv, valRuas, valPosisi);

        dataInlet = dbInlet.getInlet(valProv, valRuas, valSeg, valSubseg,  valPosisi);

        if(dataInlet.getJenisPenampang()!=null){
            jenisPenampang = dataInlet.getJenisPenampang();

            if(jenisPenampang.equals("Curb")){
                boxW.setVisibility(View.GONE);
                edLebar.setText("0");

                boxH.setVisibility(View.VISIBLE);
            }else if(jenisPenampang.equals("Gutter")){
                boxH.setVisibility(View.GONE);
                edTinggi.setText("0");

                boxW.setVisibility(View.VISIBLE);
            }else{
                boxH.setVisibility(View.VISIBLE);
                boxW.setVisibility(View.VISIBLE);
            }
        }

        edTinggi.setText(String.valueOf(dataInlet.getTinggi()));
        edLebar.setText(String.valueOf(dataInlet.getLebar()));
        edPanjang.setText(String.valueOf(dataInlet.getPanjang()));
        edSedimen.setText(String.valueOf(dataInlet.getTinggiSedimen()));

        // Toast.makeText(context, dataInlet.getJenisKonstruksi(), Toast.LENGTH_SHORT).show();

        spKeberadaan.setSelection(helperList.getIndex(arrKeberadaanValue, dataInlet.getKeberadaan()));
        spKondisi.setSelection(helperList.getIndex(arrKondisi, dataInlet.getKondisiSaluran()));
        spKonstruksi.setSelection(helperList.getIndex(arrKontruksi, dataInlet.getJenisKonstruksi()));

        HelperTextValue helperTextValue = new HelperTextValue();
        helperTextValue.setTextString(dataInlet.getLebar(), txLebar);
        helperTextValue.setTextString(dataInlet.getPanjang(), txPanjang);
        helperTextValue.setTextString(dataInlet.getTinggi(), txTinggi);
        txPenampang.setText(dataInlet.getJenisPenampang());
        txKondisi.setText(dataInlet.getKondisiSaluran());
        txKonstruksi.setText(dataInlet.getJenisKonstruksi());

        groupHelper.setClickRadio(radioCurb, radioGutter, radioCombination, groupHelper.getPosisiInlet(dataInlet.getJenisPenampang()));

        listImage = helperList.getImagePath(dataInlet.getGambar());
        listThumbnail = helperList.getImagePath(dataInlet.getIcon());
        listLokasi = helperList.getStringpath(dataInlet.getLokasi());

        helperList.setWidgetAdd(listImage, bAdd);

        imageAdapter = new ImageAdapter(listImage, context, new SendId() {
            @Override
            public void hapusGambar(int id) {

                listImage.remove(id);
                listThumbnail.remove(id);
                listLokasi.remove(id);

                helperList.setWidgetAdd(listImage, bAdd);

                imageAdapter.notifyDataSetChanged();

            }
        });

        recInlet.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        recInlet.setHasFixedSize(true);
        recInlet.setAdapter(imageAdapter);


    }


    private void setOnclickWidget(){


        radioCurb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jenisPenampang = "Curb";
                groupHelper.setClickRadio(radioCurb, radioGutter, radioCombination, 1);

                boxH.setVisibility(View.VISIBLE);
                boxW.setVisibility(View.GONE);

                edLebar.setText("0");
            }
        });

        radioGutter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jenisPenampang = "Gutter";
                groupHelper.setClickRadio(radioCurb, radioGutter, radioCombination, 2);

                boxH.setVisibility(View.GONE);
                boxW.setVisibility(View.VISIBLE);

                edTinggi.setText("0");
            }
        });


        radioCombination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jenisPenampang = "Combination";
                groupHelper.setClickRadio(radioCurb, radioGutter, radioCombination, 3);

                boxH.setVisibility(View.VISIBLE);
                boxW.setVisibility(View.VISIBLE);
            }
        });


        edTinggi.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if(!b){

                    if(!edTinggi.getText().toString().equals("")) {

                        float value = helperList.cekMaksimal(0.3f, Float.parseFloat(edTinggi.getText().toString()));
                        edTinggi.setText(String.valueOf(value));
                        maxTinggiSedimen = value;

                    }else {
                        edTinggi.setText("0");
                    }



                }

            }
        });


        edLebar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if(!b){

                    if(!edLebar.getText().toString().equals("")) {

                        float value = helperList.cekMaksimal(2, Float.parseFloat(edLebar.getText().toString()));
                        edLebar.setText(String.valueOf(value));

                    }else {
                        edLebar.setText("0");
                    }



                }

            }
        });

        edPanjang.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if(!b){

                    if(!edPanjang.getText().toString().equals("")) {

                        float value = helperList.cekMaksimal(2, Float.parseFloat(edPanjang.getText().toString()));
                        edPanjang.setText(String.valueOf(value));

                    }else {
                        edPanjang.setText("0");
                    }




                }

            }
        });

        edTinggi.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if(!b){


                    if(!edTinggi.getText().toString().equals("")) {

                        float value = helperList.cekMaksimal(maxDalam, Float.parseFloat(edTinggi.getText().toString()));
                        edTinggi.setText(String.valueOf(value));

                    }else {
                        edTinggi.setText("0");
                    }



                }

            }
        });

        edSedimen.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if(!b){

                    if(!edSedimen.getText().toString().equals("")) {

                        float value = helperList.cekMaksimal(maxTinggiSedimen, Float.parseFloat(edSedimen.getText().toString()));
                        edSedimen.setText(String.valueOf(value));

                    }else {
                        edSedimen.setText("0");
                    }



                }

            }
        });

        helperList.setEdit(edTinggi);
        helperList.setEdit(edLebar);
        helperList.setEdit(edPanjang);
        helperList.setEdit(edSedimen);




    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {

            if (requestCode == 1) {
                try {

                    fileFoto = new File(data.getStringExtra("image"));
                    iconFoto = imageHelper.saveIcon(fileFoto, thumbnail);

                    sLokasi = data.getStringExtra("latlong");

                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else if (requestCode == 2) {

                /*try {

                    String urlImage = permissionImage.getRealPathFromUri(data.getData());
                    tempFoto = new File(urlImage);
                    fileFoto = imageHelper.saveFromGaleri(tempFoto, fileNama);
                    iconFoto = imageHelper.saveIcon(tempFoto, thumbnail);

                } catch (IOException e) {
                    e.printStackTrace();
                }*/

                String urlImage = permissionImage.getRealPathFromUri(data.getData());
                if(TabImage.checkPermission(urlImage)){

                    try {
                        tempFoto = new File(urlImage);
                        fileFoto = imageHelper.saveFromGaleri(tempFoto, fileNama);
                        iconFoto = imageHelper.saveIcon(tempFoto, thumbnail);

                        if(!TabImage.getImageLatlong(urlImage).equals("0.0km0.0") && !TabImage.getImageLatlong(urlImage).equals("0km0")){
                            sLokasi = TabImage.getImageLatlong(urlImage);
                        }

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }


                }else{

                    String tempNama = TabImage.getTempoName(namaFile, directory, listImage.size());
                    tempFoto = TabImage.createImageFromBitmap(context, tempNama, data.getData());

                    try {
                        fileFoto = imageHelper.saveFromGaleri(tempFoto, fileNama);
                        iconFoto = imageHelper.saveIcon(tempFoto, thumbnail);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    tempFoto.delete();

                }

            }

            listImage.add(fileFoto);
            listThumbnail.add(iconFoto);
            listLokasi.add(sLokasi);
            helperList.setWidgetAdd(listImage, bAdd);
            imageAdapter.notifyDataSetChanged();



        }
    }

    private void getLastLocation() {

        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener((Activity) context, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            location = task.getResult();
                            String latitude = String.valueOf(location.getLatitude());
                            String longtitude = String.valueOf( location.getLongitude());
                            locationKM = latitude+"km"+longtitude;

                        } else {
                            locationKM = "0km0";
                            Log.w(String.valueOf(context), "getLastLocation:exception", task.getException());
                        }

                        sLokasi = locationKM;
                    }
                });


    }

    protected void startLocationUpdates() {

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);

        if (ActivityCompat.checkSelfPermission( context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
        Log.d("reque", "--->>>>");

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {


        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Location mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLocation == null) {
            startLocationUpdates();
        }
        if (mLocation != null) {

        } else {
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(String.valueOf(context), "Connection Suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(String.valueOf(context), "Connection failed. Error: " + connectionResult.getErrorCode());
    }


    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mGoogleApiClient.disconnect();
        mGoogleApiClient.connect();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(parent.getId()==spKeberadaan.getId()) {

            keberadaanInlet = arrKeberadaanValue[position];

            if (arrKeberadaanValue[position].equals("Tidak ada inlet")){

                formInlet.setVisibility(View.GONE);

            }else {

                formInlet.setVisibility(View.VISIBLE);

            }

        }else if(parent.getId()==spKonstruksi.getId()){

            jenisKonstruksi = arrKontruksi[position];

        }else if(parent.getId()==spKondisi.getId()){

            kondisiSaluran = arrKondisi[position];

        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
