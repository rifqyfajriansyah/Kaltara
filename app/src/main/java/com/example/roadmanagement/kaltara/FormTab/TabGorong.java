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
import com.example.roadmanagement.kaltara.FormTab.Helper.TabImage;
import com.example.roadmanagement.kaltara.FormTerusan.FragmentGorong;
import com.example.roadmanagement.kaltara.FormTerusan.HelperFormTerusan;
import com.example.roadmanagement.kaltara.Interface.DataCrossDrain;
import com.example.roadmanagement.kaltara.Interface.DataTemporari;
import com.example.roadmanagement.kaltara.Interface.SendId;
import com.example.roadmanagement.kaltara.adapter.ImageAdapter;
import com.example.roadmanagement.kaltara.databaseHelper.DbGorong;
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

public class TabGorong extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, AdapterView.OnItemSelectedListener{

    //Widget
    ImageView imGaleri, imFoto;
    Spinner spKeberadaan, spKonstruksi, spKondisi;
    EditText edDiameter, edLebar, edTinggi;
    RecyclerView recGorong;
    CardView bSave, bAdd;
    RadioButton radioLingkaran, radioSegi;
    LinearLayout formGorong, boxDiameter, boxLebar, boxTinggi;


    //Variable
    String  namaFile, sLokasi,  keberadaanGorong, jenisPenampang, jenisKonstruksi, kondisiSaluran, thumbnail, fileNama;
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

    DataCrossDrain dataGorong;
    DbGorong dbGorong;

    GRadioGroup groupHelper;

    private FusedLocationProviderClient mFusedLocationClient;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private long UPDATE_INTERVAL = 2 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */

    private TextView txKonstruksi, txPenampang, txLebar, txDiameter, txTinggi, txKondisi;
    ImageView iconEdit, iconDone, iconCancel, iconExpand;
    LinearLayoutCompat formEdit, formShow;
    private String valProv, valRuas, valPosisi;
    private int valSeg, valSubseg;

    private boolean flag_show = true;
    private ViewGroup parentView;

    public static TabGorong newInstance(String noprov, String noruas, int noseg, int subseg, String posisi) {
        TabGorong fragment = new TabGorong();
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

        View view = inflater.inflate(R.layout.tab_fragment_gorong, container, false);
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

                    dataGorong.setKeberadaan(keberadaanGorong);
                    dataGorong.setGambar(helperList.getNamaImage(listImage));
                    dataGorong.setIcon(helperList.getNamaImage(listThumbnail));
                    dataGorong.setLokasi(helperList.getLokasi(listLokasi));

                    if(keberadaanGorong.equals("Tidak ada gorong")){

                        dataGorong.setJenisPenampang(null);
                        dataGorong.setTinggi(0);
                        dataGorong.setLebar(0);
                        dataGorong.setDiameter(0);
                        dataGorong.setJenisKonstruksi(null);
                        dataGorong.setKondisiSaluran(null);


                    }else {

                        Float lebar, diameter, tinggi;
                        lebar = Float.parseFloat(edLebar.getText().toString());
                        diameter = Float.parseFloat(edDiameter.getText().toString());
                        tinggi = Float.parseFloat(edTinggi.getText().toString());

                        dataGorong.setTinggi(tinggi);
                        dataGorong.setLebar(lebar);
                        dataGorong.setDiameter(diameter);
                        dataGorong.setJenisPenampang(jenisPenampang);
                        dataGorong.setJenisKonstruksi(jenisKonstruksi);
                        dataGorong.setKondisiSaluran(kondisiSaluran);
                    }


                    DbTemporari dbTemporari = new DbTemporari(context);

                    String foto;
                    String status;

                    if(session.getTipesurvey()!=null){
                        status = session.getTipesurvey();
                    }else{
                        status = dbTemporari.cekSurveyTemporariTipe(dataGorong.getNoprov(), dataGorong.getNoruas(), "Gorong-gorong", dataGorong.getPosisi(), "");
                    }

                    if(listImage.size()>0){
                        foto = "1";
                    }else{
                        foto = "0";
                    }

                    DataTemporari dataTemporari = new DataTemporari(dataGorong.getNoprov(), dataGorong.getNoruas(), dataGorong.getNosegment(), dataGorong.getSubsegment(), "Gorong-gorong", dataGorong.getPosisi(), "",  status, dataGorong.getNosegment(), dataGorong.getSubsegment(), foto, "0");


                    if(dataGorong.getKeberadaan().equals("Tidak ada gorong")){

                        dbGorong.setSaluran(dataGorong);
                        dbTemporari.postTemporari(dataTemporari);




                    }else if(dataGorong.getJenisPenampang()==null){

                        Toast.makeText(context, "Silahkan pilih jenis penampang", Toast.LENGTH_SHORT).show();

                    } else if(!dataGorong.getKeberadaan().equals("Tidak ada outlet") && (dataGorong.getJenisPenampang().equals("Lingkaran") && dataGorong.getDiameter()==0)||(dataGorong.getJenisPenampang().equals("Segiempat") && (dataGorong.getTinggi()==0 || dataGorong.getLebar()==0))){
                        Toast.makeText(context, "Silahkan isi data terlebih dahulu", Toast.LENGTH_SHORT).show();

                    }else {

                        flag_show = true;
                        AnimationTab.setFormEdit(formEdit, false, iconEdit, iconDone, iconCancel, iconExpand, parentView);
                        AnimationTab.setFormShow(formShow, flag_show, iconExpand, parentView);
                        AnimationTab.hideKeyboardFrom(context, view);

                        dbGorong.setSaluran(dataGorong);
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

                namaFile = permissionImage.getNamaFile("Gorong", valProv, valRuas, String.valueOf(valSeg), String.valueOf(valSubseg),  valPosisi);

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

                namaFile = permissionImage.getNamaFile("Gorong", valProv, valRuas, String.valueOf(valSeg), String.valueOf(valSubseg),  valPosisi);

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

        edDiameter.setOnFocusChangeListener((view1, b) -> {
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

        boxDiameter = view.findViewById(R.id.tabGorongBoxDiameter);
        boxLebar = view.findViewById(R.id.tabGorongBoxLebar);
        boxTinggi = view.findViewById(R.id.tabGorongBoxTinggi);

        imFoto = view.findViewById(R.id.tabFotoCamera);
        imGaleri = view.findViewById(R.id.tabFotoGaleri);

        spKeberadaan = view.findViewById(R.id.tabGorongSpinKeberadaan);
        spKonstruksi = view.findViewById(R.id.tabGorongSpinKonstruksi);
        spKondisi = view.findViewById(R.id.tabGorongSpinKondisi);

        edTinggi = view.findViewById(R.id.tabGorongEdTinggi);
        edLebar = view.findViewById(R.id.tabGorongEdLebar);
        edDiameter = view.findViewById(R.id.tabGorongEdDiameter);

        bAdd = view.findViewById(R.id.tabFotoAdd);


        formGorong = view.findViewById(R.id.tabGorongForm);
        recGorong = view.findViewById(R.id.tabFotoRec);

        groupHelper = new GRadioGroup(context);

        radioLingkaran = view.findViewById(R.id.tabGorongRadioLingkaran);
        radioSegi = view.findViewById(R.id.tabGorongRadioKotak);

        formEdit = view.findViewById(R.id.tabGorongFormEdit);
        formShow = view.findViewById(R.id.tabGorongFormShow);

        txKonstruksi = view.findViewById(R.id.tabGorongTxKonstruksi);
        txPenampang = view.findViewById(R.id.tabGorongTxPenampang);
        txKondisi = view.findViewById(R.id.tabGorongTxKondisi);
        txDiameter = view.findViewById(R.id.tabGorongTxDiameter);
        txTinggi = view.findViewById(R.id.tabGorongTxTinggi);
        txLebar = view.findViewById(R.id.tabGorongTxLebar);

        iconEdit = view.findViewById(R.id.tabToolEdit);
        iconCancel = view.findViewById(R.id.tabToolClose);
        iconDone= view.findViewById(R.id.tabToolSave);
        iconExpand = view.findViewById(R.id.tabToolExpand);

        //pilihanTipe5.removeAllViews();

        permissionHelper = new PermissionHelper(context);
        permissionImage = new PermissionImage(context);
        session = new Session(context);
        imageHelper = new ImageHelper(context);

        dbGorong = new DbGorong(context);

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

        spKeberadaan.setAdapter(helperList.getSpinner(context, R.array.keberadaan_gorong));
        spKeberadaan.setOnItemSelectedListener(this);

        spKonstruksi.setAdapter(helperList.getSpinner(context, R.array.konstruksi_gorong));
        spKonstruksi.setOnItemSelectedListener(this);

        spKondisi.setAdapter(helperList.getSpinner(context, R.array.kondisi_saluran));
        spKondisi.setOnItemSelectedListener(this);

    }



    private void initValue(){

        arrKeberadaan = helperList.getListSpinner(context, R.array.keberadaan_gorong);
        arrKeberadaanValue = helperList.getListSpinner(context, R.array.keberadaan_gorong_value);
        arrKondisi= helperList.getListSpinner(context, R.array.kondisi_saluran);
        arrKontruksi = helperList.getListSpinner(context, R.array.konstruksi_gorong);

        directory = permissionImage.getFolderFile("Gorong", valProv, valRuas, valPosisi);

        dataGorong = dbGorong.getGorong(valProv, valRuas, valSeg, valSubseg,  valPosisi);

        if(dataGorong.getJenisPenampang()!=null) {
            jenisPenampang = dataGorong.getJenisPenampang();
        }

        setTextVisibility(dataGorong.getJenisPenampang());

        edTinggi.setText(String.valueOf(dataGorong.getTinggi()));
        edLebar.setText(String.valueOf(dataGorong.getLebar()));
        edDiameter.setText(String.valueOf(dataGorong.getDiameter()));

        HelperTextValue helperTextValue = new HelperTextValue();
        helperTextValue.setTextString(dataGorong.getLebar(), txLebar);
        helperTextValue.setTextString(dataGorong.getDiameter(), txDiameter);
        helperTextValue.setTextString(dataGorong.getTinggi(), txTinggi);
        txPenampang.setText(dataGorong.getJenisPenampang());
        txKondisi.setText(dataGorong.getKondisiSaluran());
        txKonstruksi.setText(dataGorong.getJenisKonstruksi());

        spKeberadaan.setSelection(helperList.getIndex(arrKeberadaanValue, dataGorong.getKeberadaan()));
        spKondisi.setSelection(helperList.getIndex(arrKondisi, dataGorong.getKondisiSaluran()));
        spKonstruksi.setSelection(helperList.getIndex(arrKontruksi, dataGorong.getJenisKonstruksi()));

        groupHelper.setClickRadio(radioLingkaran, radioSegi, groupHelper.getPosisiOutlet(dataGorong.getJenisPenampang()));

        listImage = helperList.getImagePath(dataGorong.getGambar());
        listThumbnail = helperList.getImagePath(dataGorong.getIcon());
        listLokasi = helperList.getStringpath(dataGorong.getLokasi());

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

        recGorong.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        recGorong.setHasFixedSize(true);
        recGorong.setAdapter(imageAdapter);



    }


    private void setOnclickWidget(){


        radioLingkaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jenisPenampang = "Lingkaran";
                groupHelper.setClickRadio(radioLingkaran, radioSegi, 1);
                setTextVisibility("Lingkaran");
            }
        });

        radioSegi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jenisPenampang = "Segiempat";
                groupHelper.setClickRadio(radioLingkaran, radioSegi, 2);
                setTextVisibility("Segiempat");
            }
        });




        edDiameter.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if(!b){

                    if(!edDiameter.getText().toString().equals("")) {

                        float value = helperList.cekMaksimal(2, Float.parseFloat(edDiameter.getText().toString()));
                        edDiameter.setText(String.valueOf(value));

                    }else {
                        edDiameter.setText("0");
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

        edTinggi.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if(!b){


                    if(!edTinggi.getText().toString().equals("")) {

                        float value = helperList.cekMaksimal(2, Float.parseFloat(edTinggi.getText().toString()));
                        edTinggi.setText(String.valueOf(value));

                    }else {
                        edTinggi.setText("0");
                    }


                }

            }
        });



        helperList.setEdit(edTinggi);
        helperList.setEdit(edLebar);
        helperList.setEdit(edDiameter);




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

            keberadaanGorong = arrKeberadaanValue[position];

            if (arrKeberadaanValue[position].equals("Tidak ada gorong")){

                formGorong.setVisibility(View.GONE);

            }else {

                formGorong.setVisibility(View.VISIBLE);

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

    private void setTextVisibility(String jenisPenampang){

        boxDiameter.setVisibility(View.GONE);
        boxTinggi.setVisibility(View.GONE);
        boxLebar.setVisibility(View.GONE);

        edDiameter.setText("0");
        edLebar.setText("0");
        edTinggi.setText("0");

        if(jenisPenampang!=null){

            if(jenisPenampang.equals("Lingkaran")){

                boxDiameter.setVisibility(View.VISIBLE);
                boxTinggi.setVisibility(View.GONE);
                boxLebar.setVisibility(View.GONE);

                edLebar.setText("0");
                edTinggi.setText("0");

            }else{

                edDiameter.setText("0");

                boxDiameter.setVisibility(View.GONE);
                boxTinggi.setVisibility(View.VISIBLE);
                boxLebar.setVisibility(View.VISIBLE);

            }

        }

    }

}

