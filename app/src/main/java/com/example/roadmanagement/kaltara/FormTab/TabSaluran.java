package com.example.roadmanagement.kaltara.FormTab;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.roadmanagement.kaltara.FormTerusan.FragmentSaluranTerusan;
import com.example.roadmanagement.kaltara.FormTerusan.HelperFormTerusan;
import com.example.roadmanagement.kaltara.Interface.DataSaluran;
import com.example.roadmanagement.kaltara.Interface.SendId;
import com.example.roadmanagement.kaltara.adapter.ImageAdapter;
import com.example.roadmanagement.kaltara.databaseHelper.DbSaluran;
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

public class TabSaluran extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, AdapterView.OnItemSelectedListener{

    //Widget
    ImageView imGaleri, imFoto;
    Spinner spTipeSaluran, spKonstruksi, spKondisi;
    EditText edDalam, edLebar, edTinggi, edSedimen;
    RecyclerView recSaluran;
    CardView bSave, bAdd, formKontruksiPilih;

    RadioButton radioYa, radioTidak,
            radioTrapesium, radioSegitiga, radioSegiempat, radioLingkaran;
    LinearLayout formSaluran, formSaluran5, formDrain1, formDrain2;
    TextView textJenis, texKonstruksi;
    RelativeLayout formKonstruksiset;


    //Variable
    String namaFile, sLokasi, asal, tipeSaluran, jenisPenampang, jenisKonstruksi, kondisiSaluran, permukaanSamping, thumbnail, fileNama;
    float maxLebar, maxDalam, maxTinggiAir, maxTinggiSedimen;
    File fileFoto, iconFoto, tempFoto, directory;
    protected Location location;
    protected String locationKM;
    String [] arrSaluranValue, arrKontruksi, arrKondisi;
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

    DataSaluran dataSaluran;
    DbSaluran dbSaluran;

    GRadioGroup groupHelper;

    private FusedLocationProviderClient mFusedLocationClient;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private long UPDATE_INTERVAL = 2 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */

    private TextView txTipe, txPenampang, txDalam, txLebar;
    private SaveLogic saveLogic;
    ImageView iconEdit, iconDone, iconCancel, iconExpand;
    LinearLayoutCompat formEdit, formShow;
    private String valProv, valRuas, valPosisi;
    private int valSeg, valSubseg;

    private boolean flag_show = true;
    private ViewGroup parentView;


    public static TabSaluran newInstance(String noprov, String noruas, int noseg, int subseg, String posisi) {
        TabSaluran fragment = new TabSaluran();
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

        View view = inflater.inflate(R.layout.tab_fragment_saluran, container, false);
        parentView = container;

        context = getActivity();
        initWidget(view);
        initObject();
        initValue();


        locationKM = "0km0";
        getLastLocation();

        AnimationTab.setFormEdit(formEdit,  false, iconEdit, iconDone, iconCancel, iconExpand, parentView);

        iconEdit.setOnClickListener(view1 -> {
            AnimationTab.setFormEdit(formEdit,  true, iconEdit, iconDone, iconCancel, iconExpand, parentView);
        });

        iconDone.setOnClickListener(view1 -> {

            DialogTab.setDialogSave(context, position -> {


                if(position==1){

                    dataSaluran.setTipeSaluran(tipeSaluran);
                    dataSaluran.setGambarSaluran(helperList.getNamaImage(listImage));
                    dataSaluran.setGambarSaluranicon(helperList.getNamaImage(listImage));
                    dataSaluran.setLokasiSaluran(helperList.getLokasi(listLokasi));

                    if(tipeSaluran.equals("Tidak ada saluran")){


                        dataSaluran.setDalamSaluran(0);
                        dataSaluran.setLebarSaluran(0);
                        dataSaluran.setTinggiAir(0);
                        dataSaluran.setTinggiSedimen(0);
                        dataSaluran.setJenisPenampang(null);
                        dataSaluran.setJenisKonstruksi(null);
                        dataSaluran.setKondisiSaluran(null);

                        if(session.getUserTipe()==99){
                            dataSaluran.setPermukaanSamping(permukaanSamping);
                        }


                    }else {

                        dataSaluran.setPermukaanSamping(null);

                        Float lebar, dalam, tinggi, sedimen;
                        lebar = Float.parseFloat(edLebar.getText().toString());
                        dalam = Float.parseFloat(edDalam.getText().toString());

                        if(session.getUserTipe()==99){

                            tinggi = Float.parseFloat(edTinggi.getText().toString());
                            sedimen = Float.parseFloat(edSedimen.getText().toString());
                            dataSaluran.setTinggiAir(tinggi);
                            dataSaluran.setTinggiSedimen(sedimen);
                            dataSaluran.setJenisPenampang(jenisPenampang);
                            dataSaluran.setJenisKonstruksi(jenisKonstruksi);
                            dataSaluran.setKondisiSaluran(kondisiSaluran);

                        }


                        dataSaluran.setDalamSaluran(dalam);
                        dataSaluran.setLebarSaluran(lebar);

                    }


                    if(session.getUserTipe()==99 && !dataSaluran.getTipeSaluran().equals("Tidak ada saluran") && (dataSaluran.getLebarSaluran()==0||dataSaluran.getDalamSaluran()==0 || dataSaluran.getTinggiAir()==0 || dataSaluran.getTinggiSedimen()==0)){
                        Toast.makeText(context, "Silahkan isi data terlebih dahulu", Toast.LENGTH_SHORT).show();
                    }else if(!dataSaluran.getTipeSaluran().equals("Tidak ada saluran") && (dataSaluran.getLebarSaluran()==0||dataSaluran.getDalamSaluran()==0)){
                        Toast.makeText(context, "Silahkan isi data terlebih dahulu", Toast.LENGTH_SHORT).show();
                    }else {

                        flag_show = true;
                        AnimationTab.setFormEdit(formEdit,  false, iconEdit, iconDone, iconCancel, iconExpand, parentView);
                        AnimationTab.setFormShow(formShow,  flag_show, iconExpand, parentView);
                        AnimationTab.hideKeyboardFrom(context, view);


                        saveLogic.saveSaluran(dataSaluran);
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

                namaFile = permissionImage.getNamaFile("Saluran", valProv, valRuas, String.valueOf(valSeg), String.valueOf(valSubseg),  valPosisi);

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

                namaFile = permissionImage.getNamaFile("Saluran", valProv, valRuas, String.valueOf(valSeg), String.valueOf(valSubseg),  valPosisi);

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

        return view;
    }

    private void initWidget(View view){

        imFoto = view.findViewById(R.id.tabFotoCamera);
        imGaleri = view.findViewById(R.id.tabFotoGaleri);
        spTipeSaluran = view.findViewById(R.id.tabSaluranSpinTipe);
        edDalam = view.findViewById(R.id.tabSaluranEdDalam);
        edLebar = view.findViewById(R.id.tabSaluranEdLebar);
        recSaluran = view.findViewById(R.id.tabFotoRec);
        //bSave = view.findViewById(R.id.formSaluranSave);
        bAdd = view.findViewById(R.id.tabFotoAdd);
        textJenis = view.findViewById(R.id.tabSaluranJenis);
        edTinggi = view.findViewById(R.id.tabSaluranEdAir);
        edSedimen = view.findViewById(R.id.tabSaluranEdSedimen);

        formSaluran = view.findViewById(R.id.tabSaluranFormDrainase);
        formSaluran5 = view.findViewById(R.id.tabSaluranFormDrainaseOption);

        formKonstruksiset = view.findViewById(R.id.tabSaluranCdKonstruksiTx);
        formKontruksiPilih = view.findViewById(R.id.tabSaluranCdKonstruksiSp);

        spKonstruksi = view.findViewById(R.id.tabSaluranSpKonstruksi);
        spKondisi = view.findViewById(R.id.tabSaluranSpKondisi);
        texKonstruksi = view.findViewById(R.id.tabSaluranTxKonstruksi);

        groupHelper = new GRadioGroup(context);

        radioYa = view.findViewById(R.id.tabSaluranRadioYes);
        radioTidak = view.findViewById(R.id.tabSaluranRadioNo);
        radioLingkaran = view.findViewById(R.id.tabSaluranLingkaran);
        radioSegiempat = view.findViewById(R.id.tabSaluranSegiEmpat);
        radioSegitiga = view.findViewById(R.id.tabSaluranSegitiga);
        radioTrapesium = view.findViewById(R.id.tabSaluranTrapesium);

        formDrain1 = view.findViewById(R.id.tabSaluranFormDrainasePenampang);
        formDrain2 = view.findViewById(R.id.tabSaluranFormDrainaseAtribut);

        formEdit = view.findViewById(R.id.tabSaluranFormEdit);
        formShow = view.findViewById(R.id.tabSaluranFormShow);

        txTipe = view.findViewById(R.id.tabSaluranTxTipe);
        txPenampang = view.findViewById(R.id.tabSaluranTxPenampang);
        txLebar = view.findViewById(R.id.tabSaluranTxLebar);
        txDalam = view.findViewById(R.id.tabSaluranTxDalam);

        iconEdit = view.findViewById(R.id.tabToolEdit);
        iconCancel = view.findViewById(R.id.tabToolClose);
        iconDone= view.findViewById(R.id.tabToolSave);
        iconExpand = view.findViewById(R.id.tabToolExpand);


        //pilihanTipe5.removeAllViews();

        permissionHelper = new PermissionHelper(context);
        permissionImage = new PermissionImage(context);
        session = new Session(context);
        imageHelper = new ImageHelper(context);

        dbSaluran = new DbSaluran(context);

        helperList = new HelperList();
        helperFormTerusan = new HelperFormTerusan(context);
        saveLogic = new SaveLogic(context);

        if(session.getUserTipe() == 99 ){

            spTipeSaluran.setEnabled(false);
            edLebar.setEnabled(false);
            edDalam.setEnabled(false);

            formDrain1.setVisibility(View.VISIBLE);
            formDrain2.setVisibility(View.VISIBLE);

            formSaluran5.setVisibility(View.VISIBLE);

        }else{

            spTipeSaluran.setEnabled(true);
            edLebar.setEnabled(true);
            edDalam.setEnabled(true);

            formDrain1.setVisibility(View.GONE);
            formDrain2.setVisibility(View.GONE);

            formSaluran5.setVisibility(View.GONE);

        }




    }

    private void initObject(){

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mGoogleApiClient.connect();

        spTipeSaluran.setAdapter(helperList.getSpinner(context, R.array.tipe_saluran));
        spTipeSaluran.setOnItemSelectedListener(this);

        spKonstruksi.setAdapter(helperList.getSpinner(context, R.array.konstruksi_saluran));
        spKonstruksi.setOnItemSelectedListener(this);

        spKondisi.setAdapter(helperList.getSpinner(context, R.array.kondisi_saluran));
        spKondisi.setOnItemSelectedListener(this);

    }



    private void initValue(){

        arrSaluranValue = helperList.getListSpinner(context, R.array.tipe_saluran_value);
        arrKondisi= helperList.getListSpinner(context, R.array.kondisi_saluran);
        arrKontruksi = helperList.getListSpinner(context, R.array.konstruksi_saluran);

        directory = permissionImage.getFolderFile("Saluran", valProv, valRuas, valPosisi);

        dataSaluran = dbSaluran.getSaluran(valProv, valRuas, valSeg, valSubseg, valPosisi);


       if(dataSaluran.getJenisPenampang()!=null){
            jenisPenampang = dataSaluran.getJenisPenampang();
        }

        HelperTextValue helperTextValue = new HelperTextValue();
        helperTextValue.setTextString(helperTextValue.tipeSaluran(dataSaluran.getTipeSaluran()), txTipe);
        helperTextValue.setTextString(dataSaluran.getLebarSaluran(), txLebar);
        helperTextValue.setTextString(dataSaluran.getDalamSaluran(), txDalam);
        helperTextValue.setTextString(dataSaluran.getJenisPenampang(), txPenampang);

        edDalam.setText(String.valueOf(dataSaluran.getDalamSaluran()));
        edLebar.setText(String.valueOf(dataSaluran.getLebarSaluran()));
        edTinggi.setText(String.valueOf(dataSaluran.getTinggiAir()));
        edSedimen.setText(String.valueOf(dataSaluran.getTinggiSedimen()));

        spTipeSaluran.setSelection(helperList.getIndex(arrSaluranValue, dataSaluran.getTipeSaluran()));
        spKondisi.setSelection(helperList.getIndex(arrKondisi, dataSaluran.getKondisiSaluran()));

        groupHelper.setClickRadio(radioTrapesium, radioSegitiga, radioSegiempat, radioLingkaran, groupHelper.getPosisiPenampangSaluran(dataSaluran.getJenisPenampang()));
        groupHelper.setClickRadio(radioYa, radioTidak, groupHelper.getPosisiSaluran5(dataSaluran.getPermukaanSamping()));

        listImage = helperList.getImagePath(dataSaluran.getGambarSaluran());
        listThumbnail = helperList.getImagePath(dataSaluran.getGambarSaluranicon());
        listLokasi = helperList.getStringpath(dataSaluran.getLokasiSaluran());

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

        recSaluran.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        recSaluran.setHasFixedSize(true);
        recSaluran.setAdapter(imageAdapter);

    }


    private void setOnclickWidget(){

        radioYa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permukaanSamping = "Ya";
                groupHelper.setClickRadio(radioYa, radioTidak, 1);

            }
        });

        radioTidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permukaanSamping = "Tidak";
                groupHelper.setClickRadio(radioYa, radioTidak, 2);
            }
        });

        radioTrapesium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jenisPenampang = "Trapesium";
                groupHelper.setClickRadio(radioTrapesium, radioSegitiga, radioSegiempat, radioLingkaran, 1);
            }
        });

        radioSegitiga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jenisPenampang = "Segitiga";
                groupHelper.setClickRadio(radioTrapesium, radioSegitiga, radioSegiempat, radioLingkaran, 2);
            }
        });


        radioSegiempat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jenisPenampang = "Segiempat";
                groupHelper.setClickRadio(radioTrapesium, radioSegitiga, radioSegiempat, radioLingkaran, 3);
            }
        });

        radioLingkaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jenisPenampang = "1/2 Lingkaran";
                groupHelper.setClickRadio(radioTrapesium, radioSegitiga, radioSegiempat, radioLingkaran, 4);
            }
        });

        edLebar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if(!b){

                    if(!edLebar.getText().toString().equals("")) {

                        float value = helperList.cekMaksimal(9, Float.parseFloat(edLebar.getText().toString()));
                        edLebar.setText(String.valueOf(value));

                    }else {
                        edLebar.setText("0");
                    }


                }

            }
        });

        edDalam.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if(!b){

                    if(!edDalam.getText().toString().equals("")) {


                        float value = helperList.cekMaksimal(5, Float.parseFloat(edDalam.getText().toString()));
                        edDalam.setText(String.valueOf(value));
                        maxDalam = value;

                    }else {
                        edDalam.setText("0");
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


                        float value = helperList.cekMaksimal(maxDalam, Float.parseFloat(edSedimen.getText().toString()));
                        edSedimen.setText(String.valueOf(value));


                    }else {
                        edSedimen.setText("0");
                    }


                }

            }
        });

        helperList.setEdit(edDalam);
        helperList.setEdit(edLebar);
        helperList.setEdit(edTinggi);
        helperList.setEdit(edSedimen);

        edDalam.setOnFocusChangeListener((view, b) -> {
            AnimationTab.setFormShow(formShow, getParamShow(b), iconExpand, parentView);
        });

        edTinggi.setOnFocusChangeListener((view, b) -> {
            AnimationTab.setFormShow(formShow, getParamShow(b), iconExpand, parentView);
        });

        edLebar.setOnFocusChangeListener((view, b) -> {
            AnimationTab.setFormShow(formShow, getParamShow(b), iconExpand, parentView);
        });

        edSedimen.setOnFocusChangeListener((view, b) -> {
            AnimationTab.setFormShow(formShow, getParamShow(b), iconExpand, parentView);
        });


    }

    private boolean getParamShow(boolean param){

        if(param){
            return false;
        }else{
            return flag_show;
        }

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

        if(parent.getId()==R.id.tabSaluranSpinTipe) {
            tipeSaluran = arrSaluranValue[position];
            setOnitem(arrSaluranValue[position]);
        }else if(parent.getId()==R.id.tabSaluranSpKonstruksi){
            jenisKonstruksi = arrKontruksi[position];
        }else if(parent.getId()==R.id.tabSaluranSpKondisi){
            kondisiSaluran = arrKondisi[position];
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void setOnitem(String value){

        if(value.equals("Tidak ada saluran")){

            if(session.getUserTipe()==99){
                formSaluran5.setVisibility(View.VISIBLE);
            }

            formSaluran.setVisibility(View.GONE);


        }else if(value.equals("Tanah terbuka")||value.equals("Saluran irigasi")){

            formSaluran5.setVisibility(View.GONE);
            formSaluran.setVisibility(View.VISIBLE);

            formKonstruksiset.setVisibility(View.VISIBLE);
            formKontruksiPilih.setVisibility(View.GONE);

            texKonstruksi.setText("Tanah");
            textJenis.setText("Terbuka");

            jenisKonstruksi = "Tanah";

        }else if(value.equals("Beton/pas batu terbuka") || value.equals("Beton/pas batu tertutup")){

            formSaluran5.setVisibility(View.GONE);
            formSaluran.setVisibility(View.VISIBLE);

            formKonstruksiset.setVisibility(View.GONE);
            formKontruksiPilih.setVisibility(View.VISIBLE);

            spKonstruksi.setSelection(helperList.getIndex(arrKontruksi, dataSaluran.getJenisKonstruksi()));


            if(value.equals("Beton/pas batu terbuka")){
                textJenis.setText("Terbuka");
            }else{
                textJenis.setText("Tertutup");
            }

        }

    }


}
