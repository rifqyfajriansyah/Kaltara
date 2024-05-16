package com.example.roadmanagement.kaltara.FormTab;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.media.ExifInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.FormTab.Helper.AnimationTab;
import com.example.roadmanagement.kaltara.FormTab.Helper.DialogTab;
import com.example.roadmanagement.kaltara.FormTab.Helper.IntTab;
import com.example.roadmanagement.kaltara.FormTab.Helper.SaveLogic;
import com.example.roadmanagement.kaltara.FormTab.Helper.TabImage;
import com.example.roadmanagement.kaltara.FormTerusan.FragmentMedianTerusan;
import com.example.roadmanagement.kaltara.FormTerusan.HelperFormTerusan;
import com.example.roadmanagement.kaltara.Interface.DataMedian;
import com.example.roadmanagement.kaltara.Interface.SendId;
import com.example.roadmanagement.kaltara.adapter.ImageAdapter;
import com.example.roadmanagement.kaltara.databaseHelper.DbMedian;
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class TabMedian extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    //Widget
    ImageView imGaleri, imFoto, imTes;
    EditText edLebarMedian;
    RecyclerView recMedian;
    CardView bSave, bAdd;


    //Variable
    String posisi, namaFile, sLokasi, asal, thumbnail, fileNama;
    File fileFoto, iconFoto, tempFoto, directory;
    protected Location location;
    protected String locationKM;
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

    DataMedian dataMedian;
    DbMedian dbMedian;

    private FusedLocationProviderClient mFusedLocationClient;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private long UPDATE_INTERVAL = 2 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */

    private TextView txLebar;
    private SaveLogic saveLogic;
    ImageView iconEdit, iconDone, iconCancel, iconExpand;
    LinearLayoutCompat formEdit, formShow;
    private String valProv, valRuas;
    private int valSeg, valSubseg;

    private boolean flag_show = true;
    private ViewGroup parentView;

    public static TabMedian newInstance(String noprov, String noruas, int noseg, int subseg) {
        TabMedian fragment = new TabMedian();
        Bundle args = new Bundle();
        args.putString("provinsi", noprov);
        args.putString("ruas", noruas);
        args.putInt("segment", noseg);
        args.putInt("subseg", subseg);
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab_fragment_median, container, false);
        parentView = container;

        context = getActivity();
        initWidget(view);
        initObject();
        initValue();


        AnimationTab.setFormEdit(formEdit,  false, iconEdit, iconDone, iconCancel, iconExpand, parentView);

        if(session.getUserTipe()==99){
            iconEdit.setVisibility(View.GONE);
        }else{

            iconEdit.setVisibility(View.VISIBLE);

        }

        iconEdit.setOnClickListener(view1 -> {
            AnimationTab.setFormEdit(formEdit,  true, iconEdit, iconDone, iconCancel, iconExpand, parentView);
        });

        iconDone.setOnClickListener(view1 -> {

            DialogTab.setDialogSave(context, position -> {


                if(position==1){

                    dataMedian.setLebarMedian(Float.parseFloat(edLebarMedian.getText().toString()));
                    dataMedian.setGambarMedian(helperList.getNamaImage(listImage));
                    dataMedian.setGambarMedianicon(helperList.getNamaImage(listImage));
                    dataMedian.setLokasiMedian(helperList.getLokasi(listLokasi));

                    flag_show = true;
                    AnimationTab.setFormEdit(formEdit,  false, iconEdit, iconDone, iconCancel, iconExpand, parentView);
                    AnimationTab.setFormShow(formShow,  flag_show, iconExpand, parentView);
                    AnimationTab.hideKeyboardFrom(context, view);


                    saveLogic.saveMedian(dataMedian);
                    initValue();
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


        locationKM = "0km0";
        getLastLocation();

        imFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                namaFile = permissionImage.getNamaFile("Median", valProv, valRuas, String.valueOf(valSeg), String.valueOf(valSubseg),  "");

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

                namaFile = permissionImage.getNamaFile("Median", valProv, valRuas, String.valueOf(valSeg), String.valueOf(valSubseg),  "");

                thumbnail = permissionImage.getThumbnailName(namaFile, directory, listImage.size());
                fileNama = permissionImage.getFullNameImage(namaFile, directory, listImage.size());

                getLastLocation();

                if(permissionHelper.getManifest()){
                    Intent i = permissionImage.getFotoGaleri();
                    startActivityForResult(i, 2);
                }
            }
        });

        edLebarMedian.setOnFocusChangeListener((view1, b) -> {
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

        imTes = view.findViewById(R.id.tesImage);

        imFoto = view.findViewById(R.id.tabFotoCamera);
        imGaleri = view.findViewById(R.id.tabFotoGaleri);
        edLebarMedian = view.findViewById(R.id.tabMedianEdLebar);
        bAdd = view.findViewById(R.id.tabFotoAdd);
        recMedian = view.findViewById(R.id.tabFotoRec);

        formEdit = view.findViewById(R.id.tabMedianFormEdit);
        formShow = view.findViewById(R.id.tabMedianFormShow);

        txLebar = view.findViewById(R.id.tabMedianTxLebar);

        iconEdit = view.findViewById(R.id.tabToolEdit);
        iconCancel = view.findViewById(R.id.tabToolClose);
        iconDone= view.findViewById(R.id.tabToolSave);
        iconExpand = view.findViewById(R.id.tabToolExpand);

        saveLogic = new SaveLogic(context);

        permissionHelper = new PermissionHelper(context);
        permissionImage = new PermissionImage(context);
        session = new Session(context);
        imageHelper = new ImageHelper(context);

        dbMedian = new DbMedian(context);
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



    }



    private void initValue(){


        directory = permissionImage.getFolderFile("Median", valProv, valRuas, "");

        dataMedian = dbMedian.getMedian(valProv, valRuas, valSeg, valSubseg);

        HelperTextValue helperTextValue = new HelperTextValue();
        helperTextValue.setTextString(dataMedian.getLebarMedian(), txLebar);

        edLebarMedian.setText(String.valueOf(dataMedian.getLebarMedian()));
        listImage = helperList.getImagePath(dataMedian.getGambarMedian());
        listThumbnail = helperList.getImagePath(dataMedian.getGambarMedianicon());
        listLokasi = helperList.getStringpath(dataMedian.getLokasiMedian());

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

        recMedian.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.HORIZONTAL, false));
        recMedian.setHasFixedSize(true);
        recMedian.setAdapter(imageAdapter);


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

                String urlImage = permissionImage.getRealPathFromUri(data.getData());
                if(TabImage.checkPermission(urlImage)){

                    try {
                        tempFoto = new File(urlImage);
                        fileFoto = imageHelper.saveFromGaleri(tempFoto, fileNama);
                        iconFoto = imageHelper.saveIcon(tempFoto, thumbnail);

                        if(!TabImage.getImageLatlong(urlImage).equals("0.0km0.0") && !TabImage.getImageLatlong(urlImage).equals("0km0")){
                            sLokasi = TabImage.getImageLatlong(urlImage);
                        }

                        //Toast.makeText(context, "bisa", Toast.LENGTH_SHORT).show();


                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }


                }else{

                    String tempNama = TabImage.getTempoName(namaFile, directory, listImage.size());
                    tempFoto = TabImage.createImageFromBitmap(context, tempNama, data.getData());

                    //Toast.makeText(context, "tidak bisa", Toast.LENGTH_SHORT).show();

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


}
