package com.example.roadmanagement.kaltara.FormTerusan;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import android.widget.Toast;

import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.Interface.DataBahu;
import com.example.roadmanagement.kaltara.Interface.SendId;
import com.example.roadmanagement.kaltara.adapter.ImageAdapter;
import com.example.roadmanagement.kaltara.databaseHelper.DbBahu;
import com.example.roadmanagement.kaltara.databaseHelper.DbSpinner;
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

public class FragmentBahuTerusan extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, AdapterView.OnItemSelectedListener{

    //Widget
    ImageView imGaleri, imFoto;
    Spinner spTipeBahu, spTipeInner, spMaterial, spMaterialInner, spKondisiBahu;
    EditText edLebarBahu, edLebarInner, edKemiringan;
    RecyclerView recBahu;
    CardView bSave, bAdd;
    RadioButton radioDalam, radioLuar;
    LinearLayout formBahu, formBahuInner;


    //Variable
    String posisi, namaFile, sLokasi, asal, sTipe, sTipeInner, sMaterial, sMaterialInner, sKondisi, sArah, thumbnail, fileNama;
    File fileFoto, iconFoto, tempFoto, directory;
    protected Location location;
    protected String locationKM;
    String [] arrTipe, arrTipeInner, arrMaterial, arrMaterialInner, arrKondisi;
    ArrayList<File> listImage;
    ArrayList<File> listThumbnail;
    List<String> listLokasi;
    int foto;

    //Object
    Context context;
    GRadioGroup gRadioGroup;
    ImageAdapter imageAdapter;
    PermissionHelper permissionHelper;
    PermissionImage permissionImage;
    HelperList helperList;
    ImageHelper imageHelper;
    Session session;
    HelperFormTerusan helperFormTerusan;

    DataBahu dataBahu;
    DbBahu dbBahu;

    float lebarBahu, kemiringanderajat, lebarBahuInner;
    DbSpinner dbSpinner;

    LinearLayout formDrain, formMal, formMad;

    private FusedLocationProviderClient mFusedLocationClient;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private long UPDATE_INTERVAL = 2 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */

    public static FragmentBahuTerusan newInstance(String posisi) {
        FragmentBahuTerusan fragment = new FragmentBahuTerusan();
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

        View view = inflater.inflate(R.layout.form_terusan_bahu, container, false);

        context = getActivity();
        initWidget(view);
        initObject();
        initValue();


        session.saveSPInt(Session.POSISITABEL, helperFormTerusan.getPosisiTabel("Bahu"));

        locationKM = "0km0";
        getLastLocation();


        imFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                namaFile = permissionImage.getNamaFile("Bahu", session.getKodeprov(), session.getNoruas(), String.valueOf(session.getNosegment()), String.valueOf(session.getSubsegment()), posisi);

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

                namaFile = permissionImage.getNamaFile("Bahu", session.getKodeprov(), session.getNoruas(), String.valueOf(session.getNosegment()), String.valueOf(session.getSubsegment()), posisi);

                thumbnail = permissionImage.getThumbnailName(namaFile, directory, listImage.size());
                fileNama = permissionImage.getFullNameImage(namaFile, directory, listImage.size());

                getLastLocation();

                if(permissionHelper.getManifest()){
                    Intent i = permissionImage.getFotoGaleri();
                    startActivityForResult(i, 2);
                }
            }
        });

        setOnclick();

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dataBahu.setTipeBahu(sTipe);
                dataBahu.setTipeBahuInner(sTipeInner);
                dataBahu.setGambarBahu(helperList.getNamaImage(listImage));
                dataBahu.setGambarBahuicon(helperList.getNamaImage(listImage));
                dataBahu.setLokasiBahu(helperList.getLokasi(listLokasi));

                lebarBahu = Float.parseFloat(edLebarBahu.getText().toString());
                lebarBahuInner = Float.parseFloat(edLebarInner.getText().toString());


                if(sTipe.equals("Tidak ada bahu")){
                    dataBahu.setMaterialBahu(null);
                    dataBahu.setLebarBahu(0);
                    dataBahu.setKemiringanDerajat(0);
                    dataBahu.setKemiringanArah(null);
                    dataBahu.setKemiringanKondisi(null);
                }else{

                    dataBahu.setLebarBahu(lebarBahu);

                    if(session.getUserTipe()==99) {

                        kemiringanderajat = Float.parseFloat(edKemiringan.getText().toString());
                        dataBahu.setMaterialBahu(sMaterial);
                        dataBahu.setKemiringanDerajat(kemiringanderajat);
                        dataBahu.setKemiringanArah(sArah);
                        dataBahu.setKemiringanKondisi(sKondisi);

                    }
                }



                if(sTipeInner.equals("Tidak ada bahu")){
                    dataBahu.setLebarBahuInner(0);
                    dataBahu.setMaterialInner(null);
                }else{
                    dataBahu.setLebarBahuInner(lebarBahuInner);

                    if(session.getUserTipe()==9) {
                        dataBahu.setMaterialInner(sMaterialInner);
                    }
                }


                if((!dataBahu.getTipeBahu().equals("Tidak ada bahu") && lebarBahu == 0) || (!dataBahu.getTipeBahuInner().equals("Tidak ada bahu") && lebarBahuInner==0)){

                    Toast.makeText(context, "Silahkan ubah lebar bahu terlebih dahulu", Toast.LENGTH_SHORT).show();

                }else{


                    if(session.getSurvey()==1) {
                        helperFormTerusan.saveBahu(dataBahu, asal);
                    }else{
                        helperFormTerusan.saveBahuDetail(dataBahu, asal);
                    }

                }

            }
        });

        return view;
    }

    private void initWidget(View view){

        imFoto = view.findViewById(R.id.formBahuFoto);
        imGaleri = view.findViewById(R.id.formBahuGaleri);
        spTipeBahu = view.findViewById(R.id.formBahuLuarTipe);
        spMaterial = view.findViewById(R.id.formBahuLuarMaterial);
        edLebarBahu = view.findViewById(R.id.formBahuLuarLebar);
        edKemiringan = view.findViewById(R.id.formBahuKemiringanDerajat);

        radioLuar = view.findViewById(R.id.formBahuLuarJalan);
        radioDalam = view.findViewById(R.id.formBahuDalamJalan);

        spKondisiBahu = view.findViewById(R.id.formBahuLuarKondisi);

        spTipeInner = view.findViewById(R.id.formBahuDalamTipe);
        spMaterialInner = view.findViewById(R.id.formBahuDalamMaterial);
        edLebarInner = view.findViewById(R.id.formBahuDalamLebar);

        formBahu = view.findViewById(R.id.formBahuLuarForm);
        formBahuInner = view.findViewById(R.id.formBahuDalamForm);

        formDrain = view.findViewById(R.id.formBahuDrain);
        formMad = view.findViewById(R.id.formBahuMad);
        formMal = view.findViewById(R.id.formBahuMal);



        bSave = view.findViewById(R.id.formBahuSave);
        bAdd = view.findViewById(R.id.formBahuAdd);
        recBahu = view.findViewById(R.id.formBahuRecycle);

        permissionHelper = new PermissionHelper(context);
        permissionImage = new PermissionImage(context);
        session = new Session(context);
        imageHelper = new ImageHelper(context);

        dbBahu = new DbBahu(context);
        dbSpinner = new DbSpinner(context);

        gRadioGroup = new GRadioGroup(context);


        helperList = new HelperList();
        helperFormTerusan = new HelperFormTerusan(context);

        if(session.getUserTipe() == 99){
            formDrain.setVisibility(View.VISIBLE);
            formMad.setVisibility(View.VISIBLE);
            formMal.setVisibility(View.VISIBLE);

            spTipeBahu.setEnabled(false);
            spTipeInner.setEnabled(false);
            edLebarBahu.setEnabled(false);
            edLebarInner.setEnabled(false);
        }else{
            formDrain.setVisibility(View.GONE);
            formMad.setVisibility(View.GONE);
            formMal.setVisibility(View.GONE);

            spTipeBahu.setEnabled(true);
            spTipeInner.setEnabled(true);
            edLebarBahu.setEnabled(true);
            edLebarInner.setEnabled(true);
        }

        spTipeBahu.setEnabled(false);
        spTipeInner.setEnabled(false);
        edLebarBahu.setEnabled(false);
        edLebarInner.setEnabled(false);

        spMaterial.setEnabled(false);
        edKemiringan.setEnabled(false);
        radioDalam.setEnabled(false);
        radioLuar.setEnabled(false);
        spKondisiBahu.setEnabled(false);
        spMaterialInner.setEnabled(false);
        imFoto.setEnabled(false);
        imGaleri.setEnabled(false);
        bSave.setVisibility(View.GONE);




    }

    private void initObject(){

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mGoogleApiClient.connect();


        spTipeBahu.setAdapter(helperList.getSpinner(context, R.array.tipe_bahu));
        spTipeInner.setAdapter(helperList.getSpinner(context, R.array.tipe_bahu));

        spTipeBahu.setOnItemSelectedListener(this);
        spTipeInner.setOnItemSelectedListener(this);


    }



    private void initValue(){

        arrTipe = helperList.getListSpinner(context, R.array.tipe_bahu);
        arrTipeInner = helperList.getListSpinner(context, R.array.tipe_bahu);
        arrKondisi = helperList.getListSpinner(context, R.array.kondisi_saluran);

        directory = permissionImage.getFolderFile("Bahu", session.getKodeprov(), session.getNoruas(), posisi);

        dataBahu = dbBahu.getBahu(session.getKodeprov(), session.getNoruas(), session.getNosegment(), session.getSubsegment(), posisi);

        if(dataBahu.getKemiringanArah()!=null){
            sArah = dataBahu.getKemiringanArah();
        }



        edLebarBahu.setText(String.valueOf(dataBahu.getLebarBahu()));
        edKemiringan.setText(String.valueOf(dataBahu.getKemiringanDerajat()));

        edLebarInner.setText(String.valueOf(dataBahu.getLebarBahuInner()));

        spTipeBahu.setSelection(helperList.getIndex(arrTipe, dataBahu.getTipeBahu()));
        spTipeInner.setSelection(helperList.getIndex(arrTipeInner, dataBahu.getTipeBahuInner()));

        listImage = helperList.getImagePath(dataBahu.getGambarBahu());
        listThumbnail = helperList.getImagePath(dataBahu.getGambarBahuicon());
        listLokasi = helperList.getStringpath(dataBahu.getLokasiBahu());

        helperList.setWidgetAdd(listImage, bAdd);

        gRadioGroup.setClickRadio(radioLuar, radioDalam, gRadioGroup.getPosisiKemiringan(dataBahu.getKemiringanArah()));

        imageAdapter = new ImageAdapter(listImage, context, new SendId() {
            @Override
            public void hapusGambar(int id) {

                /*listImage.remove(id);
                listThumbnail.remove(id);
                listLokasi.remove(id);

                helperList.setWidgetAdd(listImage, bAdd);

                imageAdapter.notifyDataSetChanged();*/

            }
        });

        recBahu.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        recBahu.setHasFixedSize(true);
        recBahu.setAdapter(imageAdapter);

        asal = getActivity().getIntent().getExtras().get("from").toString();

    }

    private void setOnclick(){

        radioLuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gRadioGroup.setClickRadio(radioLuar, radioDalam, 1);
                sArah = "Luar";
            }
        });

        radioDalam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gRadioGroup.setClickRadio(radioLuar, radioDalam, 2);
                sArah = "Dalam";
            }
        });


        helperList.setEdit(edLebarBahu);
        helperList.setEdit(edLebarInner);
        helperList.setEdit(edKemiringan);

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

        if (parent.getId() == R.id.formBahuLuarTipe) {
            if(arrTipe[position].equals("--Pilih--") || arrTipe[position].equals("Tidak ada bahu")){

                formBahu.setVisibility(View.GONE);
                sTipe = "Tidak ada bahu";

            }else {

                formBahu.setVisibility(View.VISIBLE);

                if(arrTipe[position].equals("Bahu lunak")) {
                    arrMaterial = helperList.getListSpinner(context, R.array.material_bahu_lunak);
                    spMaterial.setAdapter(helperList.getSpinner(context, R.array.material_bahu_lunak));

                }else if(arrTipe[position].equals("Bahu diperkeras")){
                    arrMaterial = helperList.getListSpinner(context, R.array.material_bahu_keras);
                    spMaterial.setAdapter(helperList.getSpinner(context, R.array.material_bahu_keras));

                }

                spMaterial.setSelection(helperList.getIndex(arrMaterial, dataBahu.getMaterialBahu()));

                spKondisiBahu.setAdapter(helperList.getSpinner(context, R.array.kondisi_saluran));

                spKondisiBahu.setSelection(helperList.getIndex(arrKondisi, dataBahu.getKemiringanKondisi()));

                spMaterial.setOnItemSelectedListener(this);
                spKondisiBahu.setOnItemSelectedListener(this);

                sTipe = arrTipe[position];

            }

        }else if (parent.getId() == R.id.formBahuDalamTipe){
            if(arrTipeInner[position].equals("--Pilih--") || arrTipeInner[position].equals("Tidak ada bahu")) {

                formBahuInner.setVisibility(View.GONE);
                sTipeInner = "Tidak ada bahu";

            }else {

                formBahuInner.setVisibility(View.VISIBLE);

                if(arrTipeInner[position].equals("Bahu lunak")) {

                    arrMaterialInner = helperList.getListSpinner(context, R.array.material_bahu_lunak);
                    spMaterialInner.setAdapter(helperList.getSpinner(context, R.array.material_bahu_lunak));

                }else if(arrTipe[position].equals("Bahu diperkeras")){
                    arrMaterialInner = helperList.getListSpinner(context, R.array.material_bahu_keras);
                    spMaterialInner.setAdapter(helperList.getSpinner(context, R.array.material_bahu_keras));

                }

                spMaterialInner.setSelection(helperList.getIndex(arrMaterialInner, dataBahu.getMaterialInner()));
                spMaterialInner.setOnItemSelectedListener(this);

                sTipeInner = arrTipeInner[position];
            }

        } else if(parent.getId() == R.id.formBahuLuarMaterial){

                sMaterial =arrMaterial[position];

        }else if(parent.getId() == R.id.formBahuDalamMaterial){

                sMaterialInner =arrMaterialInner[position];


        }else if(parent.getId() == R.id.formBahuLuarKondisi){

                sKondisi =arrKondisi[position];

        }



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
