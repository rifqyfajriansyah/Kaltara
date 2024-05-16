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
import com.example.roadmanagement.kaltara.Interface.DataLane;
import com.example.roadmanagement.kaltara.Interface.SendId;
import com.example.roadmanagement.kaltara.adapter.ImageAdapter;
import com.example.roadmanagement.kaltara.databaseHelper.DbLane;
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

public class FragmentLaneTerusan extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, AdapterView.OnItemSelectedListener{

    //Widget
    ImageView imGaleri, imFoto;
    Spinner spTipeLane, spKondisi;
    EditText edLebar, edKemiringan;
    RecyclerView recLane;
    CardView bSave, bAdd;
    RadioButton radioLuar, radioDalam;


    //Variable
    String lajur, namaFile, sLokasi, posisi, asal, sArah, sTipe, sKondisi, thumbnail, fileNama;
    float fLebar, fKemiringan;
    File fileFoto, iconFoto, tempFoto, directory;
    protected Location location;
    protected String locationKM;
    String [] arrTipe, arrKondisi;
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
    GRadioGroup gRadioGroup;

    DataLane dataLane;
    DbLane dbLane;

    LinearLayout formDrain;

    private FusedLocationProviderClient mFusedLocationClient;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private long UPDATE_INTERVAL = 2 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */

    public static FragmentLaneTerusan newInstance(String lajur) {
        FragmentLaneTerusan fragment = new FragmentLaneTerusan();
        Bundle args = new Bundle();
        args.putString("posisi", lajur);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lajur = getArguments().getString("posisi");

        if(lajur.substring(0,1).equals("L")){
            posisi = "kiri";
        }else{
            posisi = "kanan";
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.form_terusan_lane, container, false);

        context = getActivity();
        initWidget(view);
        initObject();
        initValue();

        setOnclick();


        session.saveSPInt(Session.POSISITABEL, helperFormTerusan.getPosisiTabel("Lane"));

        locationKM = "0km0";
        getLastLocation();


        imFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                namaFile = permissionImage.getNamaFile("Lane", session.getKodeprov(), session.getNoruas(), String.valueOf(session.getNosegment()), String.valueOf(session.getSubsegment()),  lajur);

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

                namaFile = permissionImage.getNamaFile("Lane", session.getKodeprov(), session.getNoruas(), String.valueOf(session.getNosegment()), String.valueOf(session.getSubsegment()),  lajur);

                thumbnail = permissionImage.getThumbnailName(namaFile, directory, listImage.size());
                fileNama = permissionImage.getFullNameImage(namaFile, directory, listImage.size());

                getLastLocation();

                if(permissionHelper.getManifest()){
                    Intent i = permissionImage.getFotoGaleri();
                    startActivityForResult(i, 2);
                }
            }
        });

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                float lebarLane = Float.parseFloat(edLebar.getText().toString());


                if(lebarLane == 0 || sTipe==null){

                    Toast.makeText(context, "Silahkan isi terlebih dahulu", Toast.LENGTH_SHORT).show();

                }else {

                    dataLane.setGambarLane(helperList.getNamaImage(listImage));
                    dataLane.setGambarLaneicon(helperList.getNamaImage(listImage));
                    dataLane.setLokasiLane(helperList.getLokasi(listLokasi));

                    dataLane.setLebarLane(lebarLane);
                    dataLane.setSc1(sTipe);

                    if(session.getUserTipe()==99) {

                        float kemiringan = Float.parseFloat(edKemiringan.getText().toString());

                        dataLane.setKemiringanArah(sArah);
                        dataLane.setKemiringanDerajat(kemiringan);
                        dataLane.setKemiringanKondisi(sKondisi);
                    }



                    if(session.getSurvey()==1) {
                        helperFormTerusan.saveLaneCepat(dataLane, asal);
                    }else{
                        helperFormTerusan.saveLaneDetail(dataLane, asal);
                    }
                }

            }
        });

        return view;
    }

    private void initWidget(View view){

        imFoto = view.findViewById(R.id.formLaneFoto);
        imGaleri = view.findViewById(R.id.formLaneGaleri);
        spTipeLane = view.findViewById(R.id.formLaneTipe);
        spKondisi = view.findViewById(R.id.formLaneKondisi);
        edLebar = view.findViewById(R.id.formLaneLebar);
        edKemiringan = view.findViewById(R.id.formLaneKemiringanDerajat);
        bSave = view.findViewById(R.id.formLaneSave);
        bAdd = view.findViewById(R.id.formLaneAdd);
        recLane = view.findViewById(R.id.formLaneRecycle);

        radioLuar = view.findViewById(R.id.formLaneLuarJalan);
        radioDalam = view.findViewById(R.id.formLaneDalamJalan);

        formDrain = view.findViewById(R.id.formLaneDrain);


        permissionHelper = new PermissionHelper(context);
        permissionImage = new PermissionImage(context);
        session = new Session(context);
        imageHelper = new ImageHelper(context);

        dbLane = new DbLane(context);
        gRadioGroup = new GRadioGroup(context);


        helperList = new HelperList();
        helperFormTerusan = new HelperFormTerusan(context);


        if(session.getUserTipe()==99){
            formDrain.setVisibility(View.VISIBLE);
            spTipeLane.setEnabled(false);
            edLebar.setEnabled(false);
        }else{
            formDrain.setVisibility(View.GONE);
            spTipeLane.setEnabled(true);
            edLebar.setEnabled(true);
        }

        spTipeLane.setEnabled(false);
        edLebar.setEnabled(false);
        edKemiringan.setEnabled(false);
        radioDalam.setEnabled(false);
        radioLuar.setEnabled(false);
        spKondisi.setEnabled(false);
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


        spTipeLane.setAdapter(helperList.getSpinner(context, R.array.tipe_permukaan));
        spKondisi.setAdapter(helperList.getSpinner(context, R.array.kondisi_saluran));

        spTipeLane.setOnItemSelectedListener(this);
        spKondisi.setOnItemSelectedListener(this);


    }



    private void initValue(){

        arrTipe = helperList.getListSpinner(context, R.array.tipe_permukaan);
        arrKondisi = helperList.getListSpinner(context, R.array.kondisi_saluran);

        directory = permissionImage.getFolderFile("Lane", session.getKodeprov(), session.getNoruas(), lajur);

        dataLane = dbLane.getLane(session.getKodeprov(), session.getNoruas(), session.getNosegment(), session.getSubsegment(), posisi, lajur);

        if(dataLane.getKemiringanArah()!=null){
            sArah = dataLane.getKemiringanArah();
        }

        edLebar.setText(String.valueOf(dataLane.getLebarLane()));
        edKemiringan.setText(String.valueOf(dataLane.getKemiringanDerajat()));
        spTipeLane.setSelection(helperList.getIndex(arrTipe, dataLane.getSc1()));
        spKondisi.setSelection(helperList.getIndex(arrKondisi, dataLane.getKemiringanKondisi()));

        gRadioGroup.setClickRadio(radioLuar, radioDalam, gRadioGroup.getPosisiKemiringan(dataLane.getKemiringanArah()));

        listImage = helperList.getImagePath(dataLane.getGambarLane());
        listThumbnail = helperList.getImagePath(dataLane.getGambarLaneicon());
        listLokasi = helperList.getStringpath(dataLane.getLokasiLane());

        helperList.setWidgetAdd(listImage, bAdd);

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

        recLane.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        recLane.setHasFixedSize(true);
        recLane.setAdapter(imageAdapter);

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


        helperList.setEdit(edLebar);
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

        if(parent.getId()==R.id.formLaneTipe) {

            if (arrTipe[position].equals("--Pilih--")) {
                sTipe = null;
                edLebar.setText("0.0");
            } else {
                sTipe = arrTipe[position];
            }

        }else if(parent.getId()==R.id.formLaneKondisi){

            sKondisi = arrKondisi[position];

        }




    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
