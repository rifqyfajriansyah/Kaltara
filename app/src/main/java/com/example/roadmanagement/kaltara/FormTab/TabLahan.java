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
import com.example.roadmanagement.kaltara.FormTerusan.HelperFormTerusan;
import com.example.roadmanagement.kaltara.Interface.DataLahan;
import com.example.roadmanagement.kaltara.Interface.DataTemporari;
import com.example.roadmanagement.kaltara.Interface.SendId;
import com.example.roadmanagement.kaltara.adapter.ImageAdapter;
import com.example.roadmanagement.kaltara.databaseHelper.DbLahan;
import com.example.roadmanagement.kaltara.databaseHelper.DbTemporari;
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

public class TabLahan extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, AdapterView.OnItemSelectedListener{

    //Widget
    ImageView imGaleri, imFoto;
    Spinner spTipelahan, spTagun;
    EditText edTinggi;
    RecyclerView recLahan;
    CardView bSave, bAdd;


    //Variable
    String valNoprov, valNoruas, valPosisi, namaFile, sLokasi, asal, tipeSurvey, thumbnail, fileNama;
    private int valNoseg, valSubseg;
    File fileFoto, iconFoto, tempFoto, directory;
    protected Location location;
    protected String locationKM;
    String [] arrTagun, arrTipelahan;
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

    DataLahan dataLahan;
    DataTemporari dataTemporari;
    DbLahan dbLahan;
    DbTemporari dbTemporari;

    private TextView txTipe, txTagun, txTinggi;
    private SaveLogic saveLogic;
    ImageView iconEdit, iconDone, iconCancel, iconExpand;
    LinearLayoutCompat formEdit, formShow;

    private FusedLocationProviderClient mFusedLocationClient;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private long UPDATE_INTERVAL = 2 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */

    private Boolean flag_show = true;

    private ViewGroup parentView;

    public static TabLahan newInstance(String noprov, String noruas, int segment, int subseg, String posisi) {
        TabLahan fragment = new TabLahan();
        Bundle args = new Bundle();
        args.putString("noprov", noprov);
        args.putString("noruas", noruas);
        args.putInt("noseg", segment);
        args.putInt("subseg", subseg);
        args.putString("posisi", posisi);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        valNoprov = getArguments().getString("noprov");
        valNoruas = getArguments().getString("noruas");
        valNoseg = getArguments().getInt("noseg");
        valSubseg = getArguments().getInt("subseg");
        valPosisi = getArguments().getString("posisi");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab_fragment_lahan, container, false);

        parentView = container;

        context = getActivity();
        initWidget(view);
        initObject();
        initValue();



        locationKM = "0km0";
        getLastLocation();
        AnimationTab.setFormEdit(formEdit,  false, iconEdit, iconDone, iconCancel, iconExpand, parentView);
        iconEdit.setVisibility(session.getUserTipe()==99 ? View.GONE : View.VISIBLE);


        iconEdit.setOnClickListener(view1 -> {
            AnimationTab.setFormEdit(formEdit,  true, iconEdit, iconDone, iconCancel, iconExpand, parentView);
        });

        iconDone.setOnClickListener(view1 -> {

            DialogTab.setDialogSave(context, position -> {

                if(position==1){

                    flag_show = true;
                    AnimationTab.setFormEdit(formEdit,  false, iconEdit, iconDone, iconCancel, iconExpand, parentView);
                    AnimationTab.setFormShow(formShow,  flag_show, iconExpand, parentView);
                    AnimationTab.hideKeyboardFrom(context, view);
                    dataLahan.setTinggiLahan(Float.parseFloat(edTinggi.getText().toString()));
                    dataLahan.setGambarLahan(helperList.getNamaImage(listImage));
                    dataLahan.setIcongambar(helperList.getNamaImage(listImage));
                    dataLahan.setLokasilahan(helperList.getLokasi(listLokasi));

                    saveLogic.saveLahan(dataLahan);

                    Toast.makeText(context, "Perubahan berhasil disimpan", Toast.LENGTH_SHORT).show();


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


                namaFile = permissionImage.getNamaFile("Lahan", valNoprov, valNoruas, String.valueOf(valNoseg), String.valueOf(valSubseg), valPosisi);
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

                namaFile = permissionImage.getNamaFile("Lahan", valNoprov, valNoruas, String.valueOf(valNoseg), String.valueOf(valSubseg), valPosisi);
                thumbnail = permissionImage.getThumbnailName(namaFile, directory, listImage.size());
                fileNama = permissionImage.getFullNameImage(namaFile, directory, listImage.size());

                getLastLocation();

                if(permissionHelper.getManifest()){
                    Intent i = permissionImage.getFotoGaleri();
                    startActivityForResult(i, 2);
                }

            }
        });

        edTinggi.setOnFocusChangeListener((view1, b) -> {
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
        spTagun = view.findViewById(R.id.tabLahanSpinTagun);
        spTipelahan = view.findViewById(R.id.tabLahanSpinTipe);
        edTinggi = view.findViewById(R.id.tabLahanEdTinggi);
        recLahan = view.findViewById(R.id.tabFotoRec);
//        bSave = view.findViewById(R.id.tabToolSave);
        bAdd = view.findViewById(R.id.tabFotoAdd);

        formEdit = view.findViewById(R.id.tabLahanFormEdit);
        formShow = view.findViewById(R.id.tabLahanFormShow);

        iconEdit = view.findViewById(R.id.tabToolEdit);
        iconCancel = view.findViewById(R.id.tabToolClose);
        iconDone= view.findViewById(R.id.tabToolSave);
        iconExpand = view.findViewById(R.id.tabToolExpand);

        txTipe = view.findViewById(R.id.tabLahanTxTipe);
        txTagun = view.findViewById(R.id.tabLahanTxTagun);
        txTinggi = view.findViewById(R.id.tabLahanTxTinggi);

        permissionHelper = new PermissionHelper(context);
        permissionImage = new PermissionImage(context);
        session = new Session(context);
        imageHelper = new ImageHelper(context);

        dbLahan = new DbLahan(context);
        dbTemporari = new DbTemporari(context);

        helperList = new HelperList();

        saveLogic = new SaveLogic(context);




    }

    private void initObject(){

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mGoogleApiClient.connect();


        spTagun.setAdapter(helperList.getSpinner(context, R.array.tatagunaLahan));
        spTipelahan.setAdapter(helperList.getSpinner(context, R.array.tipe_lahan));

        spTagun.setOnItemSelectedListener(this);
        spTipelahan.setOnItemSelectedListener(this);


    }



    private void initValue(){

        arrTagun = helperList.getListSpinner(context, R.array.tatagunaLahan);
        arrTipelahan = helperList.getListSpinner(context, R.array.tipe_lahan);

        directory = permissionImage.getFolderFile("Lahan", valNoprov, valNoruas, valPosisi);

        dataLahan = dbLahan.getLahan(valNoprov, valNoruas, valNoseg, valSubseg, valPosisi);

        edTinggi.setText(String.valueOf(dataLahan.getTinggiLahan()));
        spTagun.setSelection(helperList.getIndex(arrTagun, dataLahan.getTatagunaLahan()));
        spTipelahan.setSelection(helperList.getIndex(arrTipelahan, dataLahan.getTipeLahan()));

        txTipe.setText(dataLahan.getTipeLahan());
        txTagun.setText(dataLahan.getTatagunaLahan());
        txTinggi.setText(String.valueOf(dataLahan.getTinggiLahan()));

        listImage = helperList.getImagePath(dataLahan.getGambarLahan());
        listThumbnail = helperList.getImagePath(dataLahan.getIcongambar());
        listLokasi = helperList.getStringpath(dataLahan.getLokasilahan());

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

        recLahan.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        recLahan.setHasFixedSize(true);
        recLahan.setAdapter(imageAdapter);

    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {

            if (requestCode == 1) {
                try {

                    //fileFoto = imageHelper.saveFromCamera(tempFoto);
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

        if (parent.getId() == R.id.tabLahanSpinTagun) {
            if(arrTagun[position].equals("--Pilih--")){
                dataLahan.setTatagunaLahan(null);
            }else {
                dataLahan.setTatagunaLahan(arrTagun[position]);
            }
        }else if (parent.getId() == R.id.tabLahanSpinTipe){
            if(arrTipelahan[position].equals("--Pilih--")) {
                dataLahan.setTipeLahan(null);
            }else {
                dataLahan.setTipeLahan(arrTipelahan[position]);
            }
        }



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
