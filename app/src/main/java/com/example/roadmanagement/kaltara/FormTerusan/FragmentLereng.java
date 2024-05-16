package com.example.roadmanagement.kaltara.FormTerusan;

import android.Manifest;
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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.Interface.DataCrossDrain;
import com.example.roadmanagement.kaltara.Interface.DataDrainase;
import com.example.roadmanagement.kaltara.Interface.DataTemporari;
import com.example.roadmanagement.kaltara.Interface.SendId;
import com.example.roadmanagement.kaltara.adapter.ImageAdapter;
import com.example.roadmanagement.kaltara.databaseHelper.DbLereng;
import com.example.roadmanagement.kaltara.databaseHelper.DbTemporari;
import com.example.roadmanagement.kaltara.helper.GRadioGroup;
import com.example.roadmanagement.kaltara.helper.HelperList;
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

import static android.app.Activity.RESULT_OK;

public class FragmentLereng extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, AdapterView.OnItemSelectedListener{

    //Widget
    ImageView imGaleri, imFoto;
    Spinner spTipeLereng, spKondisi;
    EditText edLebar, edTinggi, edSedimen;
    RecyclerView recLereng;
    CardView bSave, bAdd, formKontruksiPilih;

    RadioButton radioTrapesium, radioSegitiga, radioSegiempat, radioLingkaran;
    LinearLayout formLereng;
    TextView textJenis;
    RelativeLayout formKonstruksiset;


    //Variable
    String posisi, namaFile, sLokasi, asal, tipeLereng, jenisPenampang,kondisiLereng, thumbnail, fileNama;
    float maxLebar, maxDalam, maxTinggiAir, maxTinggiSedimen;
    File fileFoto, iconFoto, tempFoto, directory;
    protected Location location;
    protected String locationKM;
    String [] arrLerengValue, arrKontruksi, arrKondisi;
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

    DataDrainase dataLereng;
    DbLereng dbLereng;

    GRadioGroup groupHelper;

    private FusedLocationProviderClient mFusedLocationClient;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private long UPDATE_INTERVAL = 2 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */

    public static FragmentLereng newInstance(String posisi) {
        FragmentLereng fragment = new FragmentLereng();
        Bundle args = new Bundle();
        args.putString("posisi", posisi);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        posisi = getArguments().getString("posisi");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.form_lereng, container, false);

        context = getActivity();
        initWidget(view);
        initObject();
        initValue();

        session.saveSPInt(Session.POSISITABEL, helperFormTerusan.getPosisiTabel("Saluran Lereng"));

        if(session.getUserTipe()==99){

            spTipeLereng.setEnabled(true);
            spKondisi.setEnabled(true);

            radioLingkaran.setEnabled(true);
            radioSegiempat.setEnabled(true);
            radioSegitiga.setEnabled(true);
            radioTrapesium.setEnabled(true);

            edLebar.setEnabled(true);
            edTinggi.setEnabled(true);
            edSedimen.setEnabled(true);

            imFoto.setEnabled(true);
            imGaleri.setEnabled(true);
            imFoto.setVisibility(View.VISIBLE);
            imGaleri.setVisibility(View.VISIBLE);

            bSave.setEnabled(true);
            bSave.setCardBackgroundColor(Color.parseColor("#4159BE"));

        }else{

            spTipeLereng.setEnabled(false);
            spKondisi.setEnabled(false);

            radioLingkaran.setEnabled(false);
            radioSegiempat.setEnabled(false);
            radioSegitiga.setEnabled(false);
            radioTrapesium.setEnabled(false);

            edLebar.setEnabled(false);
            edTinggi.setEnabled(false);
            edSedimen.setEnabled(false);

            imFoto.setEnabled(false);
            imGaleri.setEnabled(false);
            imFoto.setVisibility(View.INVISIBLE);
            imGaleri.setVisibility(View.INVISIBLE);

            bSave.setEnabled(false);
            bSave.setCardBackgroundColor(Color.parseColor("#d9d9d9"));

        }

        locationKM = "0km0";
        getLastLocation();


        imFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                namaFile = permissionImage.getNamaFile("Lereng", session.getKodeprov(), session.getNoruas(),String.valueOf(session.getNosegment()), String.valueOf(session.getSubsegment()),  posisi);

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

                namaFile = permissionImage.getNamaFile("Lereng", session.getKodeprov(), session.getNoruas(),String.valueOf(session.getNosegment()), String.valueOf(session.getSubsegment()),  posisi);

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

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dataLereng.setKeberadaan(tipeLereng);
                dataLereng.setGambar(helperList.getNamaImage(listImage));
                dataLereng.setIcon(helperList.getNamaImage(listThumbnail));
                dataLereng.setLokasi(helperList.getLokasi(listLokasi));

                if(tipeLereng.equals("Tidak ada saluran lereng")){

                    dataLereng.setLebar(0);
                    dataLereng.setTinggi(0);
                    dataLereng.setTinggiSedimen(0);
                    dataLereng.setJenisPenampang(null);
                    dataLereng.setKondisiSaluran(null);


                }else {


                    Float lebar, tinggi, sedimen;
                    lebar = Float.parseFloat(edLebar.getText().toString());
                    tinggi = Float.parseFloat(edTinggi.getText().toString());
                    sedimen = Float.parseFloat(edSedimen.getText().toString());

                    dataLereng.setLebar(lebar);
                    dataLereng.setTinggi(tinggi);
                    dataLereng.setTinggiSedimen(sedimen);
                    dataLereng.setJenisPenampang(jenisPenampang);
                    dataLereng.setKondisiSaluran(kondisiLereng);
                }

                if(!dataLereng.getKeberadaan().equals("Tidak ada saluran lereng") && (dataLereng.getLebar()==0||dataLereng.getTinggi()==0 || dataLereng.getTinggiSedimen()==0)){
                    Toast.makeText(context, "Silahkan isi data terlebih dahulu", Toast.LENGTH_SHORT).show();
                }else {

                    dbLereng.setLereng(dataLereng);
                    DbTemporari dbTemporari = new DbTemporari(context);

                    String foto;
                    String status;

                    if(session.getTipesurvey()!=null){
                        status = session.getTipesurvey();
                    }else{
                        status = dbTemporari.cekSurveyTemporariTipe(dataLereng.getNoprov(), dataLereng.getNoruas(), "Saluran Lereng", dataLereng.getPosisi(), "");
                    }

                    if(listImage.size()>0){
                        foto = "1";
                    }else{
                        foto = "0";
                    }

                    DataTemporari dataTemporari = new DataTemporari(dataLereng.getNoprov(), dataLereng.getNoruas(), dataLereng.getNosegment(), dataLereng.getSubsegment(), "Saluran Lereng", dataLereng.getPosisi(), "",  status, dataLereng.getNosegment(), dataLereng.getSubsegment(), foto, "0");
                    dbTemporari.postTemporari(dataTemporari);

                    helperFormTerusan.setOnsave(asal, "Saluran Lereng");

                }

            }

        });

        return view;
    }

    private void initWidget(View view){

        imFoto = view.findViewById(R.id.formLerengFoto);
        imGaleri = view.findViewById(R.id.formLerengGaleri);
        spTipeLereng = view.findViewById(R.id.formLerengTipe);
        edLebar = view.findViewById(R.id.formLerengLebar);
        recLereng = view.findViewById(R.id.formLerengRecycle);
        bSave = view.findViewById(R.id.formLerengSave);
        bAdd = view.findViewById(R.id.formLerengAdd);
        edTinggi = view.findViewById(R.id.formLerengDalam);
        edSedimen = view.findViewById(R.id.formLerengSedimen);

        formLereng = view.findViewById(R.id.formLerengForm);

        spKondisi = view.findViewById(R.id.formLerengKondisi);

        groupHelper = new GRadioGroup(context);

        radioLingkaran = view.findViewById(R.id.formLerengLingkaran);
        radioSegiempat = view.findViewById(R.id.formLerengSegiEmpat);
        radioSegitiga = view.findViewById(R.id.formLerengSegitiga);
        radioTrapesium = view.findViewById(R.id.formLerengTrapesium);

        //pilihanTipe5.removeAllViews();

        permissionHelper = new PermissionHelper(context);
        permissionImage = new PermissionImage(context);
        session = new Session(context);
        imageHelper = new ImageHelper(context);

        dbLereng = new DbLereng(context);

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

        spTipeLereng.setAdapter(helperList.getSpinner(context, R.array.tipe_lereng));
        spTipeLereng.setOnItemSelectedListener(this);

        spKondisi.setAdapter(helperList.getSpinner(context, R.array.kondisi_saluran));
        spKondisi.setOnItemSelectedListener(this);

    }



    private void initValue(){

        arrLerengValue = helperList.getListSpinner(context, R.array.tipe_lereng_value);
        arrKondisi= helperList.getListSpinner(context, R.array.kondisi_saluran);

        directory = permissionImage.getFolderFile("Lereng", session.getKodeprov(), session.getNoruas(), posisi);

        dataLereng = dbLereng.getLereng(session.getKodeprov(), session.getNoruas(), session.getNosegment(), session.getSubsegment(), posisi);

        if(dataLereng.getJenisPenampang()!=null){
            jenisPenampang = dataLereng.getJenisPenampang();
        }

        edLebar.setText(String.valueOf(dataLereng.getLebar()));
        edTinggi.setText(String.valueOf(dataLereng.getTinggi()));
        edSedimen.setText(String.valueOf(dataLereng.getTinggiSedimen()));

        spTipeLereng.setSelection(helperList.getIndex(arrLerengValue, dataLereng.getKeberadaan()));
        spKondisi.setSelection(helperList.getIndex(arrKondisi, dataLereng.getKondisiSaluran()));

        groupHelper.setClickRadio(radioTrapesium, radioSegitiga, radioSegiempat, radioLingkaran, groupHelper.getPosisiPenampangSaluran(dataLereng.getJenisPenampang()));
        listImage = helperList.getImagePath(dataLereng.getGambar());
        listThumbnail = helperList.getImagePath(dataLereng.getIcon());
        listLokasi = helperList.getStringpath(dataLereng.getLokasi());

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

        recLereng.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        recLereng.setHasFixedSize(true);
        recLereng.setAdapter(imageAdapter);

        asal = getActivity().getIntent().getExtras().get("from").toString();

    }


    private void setOnclickWidget(){


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


        edTinggi.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if(!b){

                    if(!edTinggi.getText().toString().equals("")) {

                        float value = helperList.cekMaksimal(5, Float.parseFloat(edTinggi.getText().toString()));
                        edTinggi.setText(String.valueOf(value));
                        maxDalam = value;

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

        helperList.setEdit(edLebar);
        helperList.setEdit(edTinggi);
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

                try {

                    String urlImage = permissionImage.getRealPathFromUri(data.getData());
                    tempFoto = new File(urlImage);
                    fileFoto = imageHelper.saveFromGaleri(tempFoto, fileNama);
                    iconFoto = imageHelper.saveIcon(tempFoto, thumbnail);

                } catch (IOException e) {
                    e.printStackTrace();
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

        if(parent.getId()==R.id.formLerengTipe) {

            if(arrLerengValue[position].equals("Tidak ada saluran lereng")){
                formLereng.setVisibility(View.GONE);
            }else{
                formLereng.setVisibility(View.VISIBLE);
            }

            tipeLereng = arrLerengValue[position];

        }else if(parent.getId()==R.id.formLerengKondisi){

            kondisiLereng = arrKondisi[position];

        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
