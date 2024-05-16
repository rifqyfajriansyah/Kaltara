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

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.Interface.DataInletTrotoar;
import com.example.roadmanagement.kaltara.Interface.DataSaluran;
import com.example.roadmanagement.kaltara.Interface.DataTemporari;
import com.example.roadmanagement.kaltara.Interface.SendId;
import com.example.roadmanagement.kaltara.adapter.ImageAdapter;
import com.example.roadmanagement.kaltara.databaseHelper.DbInlet;
import com.example.roadmanagement.kaltara.databaseHelper.DbSaluran;
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

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

import static android.app.Activity.RESULT_OK;

public class FragmentInlet extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, AdapterView.OnItemSelectedListener{

    //Widget
    ImageView imGaleri, imFoto;
    Spinner spKeberadaan, spKonstruksi, spKondisi;
    EditText edTinggi, edPanjang, edLebar, edSedimen;
    RecyclerView recInlet;
    CardView bSave, bAdd;
    RadioButton radioCurb, radioGutter, radioCombination;
    LinearLayout formInlet, boxH, boxW;


    //Variable
    String posisi, namaFile, sLokasi, asal, keberadaanInlet, jenisPenampang, jenisKonstruksi, kondisiSaluran, thumbnail, fileNama;
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

    public static FragmentInlet newInstance(String posisi) {
        FragmentInlet fragment = new FragmentInlet();
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

        View view = inflater.inflate(R.layout.form_inlet, container, false);

        context = getActivity();
        initWidget(view);
        initObject();
        initValue();

        session.saveSPInt(Session.POSISITABEL, helperFormTerusan.getPosisiTabel("Inlet ditrotoar"));

        if(session.getUserTipe()==99){

            spKeberadaan.setEnabled(true);
            spKonstruksi.setEnabled(true);
            spKondisi.setEnabled(true);

            radioCurb.setEnabled(true);
            radioGutter.setEnabled(true);
            radioCombination.setEnabled(true);

            edPanjang.setEnabled(true);
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

            spKeberadaan.setEnabled(false);
            spKonstruksi.setEnabled(false);
            spKondisi.setEnabled(false);

            radioCurb.setEnabled(false);
            radioGutter.setEnabled(false);
            radioCombination.setEnabled(false);

            edPanjang.setEnabled(false);
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

        spKeberadaan.setEnabled(false);
        spKonstruksi.setEnabled(false);
        spKondisi.setEnabled(false);

        radioCurb.setEnabled(false);
        radioGutter.setEnabled(false);
        radioCombination.setEnabled(false);

        edPanjang.setEnabled(false);
        edLebar.setEnabled(false);
        edTinggi.setEnabled(false);
        edSedimen.setEnabled(false);

        imFoto.setEnabled(false);
        imGaleri.setEnabled(false);
        bSave.setVisibility(View.GONE);

        locationKM = "0km0";
        getLastLocation();


        imFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                namaFile = permissionImage.getNamaFile("InletT", session.getKodeprov(), session.getNoruas(), String.valueOf(session.getNosegment()), String.valueOf(session.getSubsegment()),  posisi);

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

                namaFile = permissionImage.getNamaFile("InletT", session.getKodeprov(), session.getNoruas(), String.valueOf(session.getNosegment()), String.valueOf(session.getSubsegment()),  posisi);

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

                    helperFormTerusan.setOnsave(asal, "Inlet ditrotoar");


                }

            }

        });

        return view;
    }

    private void initWidget(View view){

        imFoto = view.findViewById(R.id.formInletFoto);
        imGaleri = view.findViewById(R.id.formInletGaleri);

        spKeberadaan = view.findViewById(R.id.formInletTipe);
        spKonstruksi = view.findViewById(R.id.formInletKonstruksi);
        spKondisi = view.findViewById(R.id.formInletKondisi);

        edTinggi = view.findViewById(R.id.formInletTinggi);
        edLebar = view.findViewById(R.id.formInletLebar);
        edPanjang = view.findViewById(R.id.formInletPanjang);
        edSedimen = view.findViewById(R.id.formInletTebal);

        bSave = view.findViewById(R.id.formInletSave);
        bAdd = view.findViewById(R.id.formInletAdd);

        boxH = view.findViewById(R.id.formInletBoxH);
        boxW = view.findViewById(R.id.formInletBoxW);

        formInlet = view.findViewById(R.id.formInletForm);
        recInlet = view.findViewById(R.id.formInletRecycle);

        groupHelper = new GRadioGroup(context);

        radioCurb = view.findViewById(R.id.formInletCurb);
        radioGutter = view.findViewById(R.id.formInletGutter);
        radioCombination = view.findViewById(R.id.formInletCombination);

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

        directory = permissionImage.getFolderFile("InletT", session.getKodeprov(), session.getNoruas(), posisi);

        dataInlet = dbInlet.getInlet(session.getKodeprov(), session.getNoruas(), session.getNosegment(), session.getSubsegment(), posisi);

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

        asal = getActivity().getIntent().getExtras().get("from").toString();

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

        if(parent.getId()==R.id.formInletTipe) {

            keberadaanInlet = arrKeberadaanValue[position];

            if (arrKeberadaanValue[position].equals("Tidak ada inlet")){

                formInlet.setVisibility(View.GONE);

            }else {

                formInlet.setVisibility(View.VISIBLE);

            }

        }else if(parent.getId()==R.id.formInletKonstruksi){

            jenisKonstruksi = arrKontruksi[position];

        }else if(parent.getId()==R.id.formInletKondisi){

            kondisiSaluran = arrKondisi[position];

        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}




