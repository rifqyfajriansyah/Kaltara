package com.example.roadmanagement.kaltara.FormTerusan;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.Interface.DataMedian;
import com.example.roadmanagement.kaltara.Interface.SendId;
import com.example.roadmanagement.kaltara.adapter.ImageAdapter;
import com.example.roadmanagement.kaltara.databaseHelper.DbMedian;
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

public class FragmentMedianTerusan extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener{

    //Widget
    ImageView imGaleri, imFoto;
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

    public static FragmentMedianTerusan newInstance(String posisi) {
        FragmentMedianTerusan fragment = new FragmentMedianTerusan();
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

        View view = inflater.inflate(R.layout.form_terusan_median, container, false);

        context = getActivity();
        initWidget(view);
        initObject();
        initValue();


        if(session.getUserTipe()==99){

            edLebarMedian.setEnabled(false);
            bSave.setEnabled(false);

            imFoto.setEnabled(false);
            imGaleri.setEnabled(false);
            imFoto.setVisibility(View.INVISIBLE);
            imGaleri.setVisibility(View.INVISIBLE);

            bSave.setCardBackgroundColor(Color.parseColor("#d9d9d9"));

        }else{

            edLebarMedian.setEnabled(true);
            bSave.setEnabled(true);

            imFoto.setEnabled(true);
            imGaleri.setEnabled(true);
            imFoto.setVisibility(View.VISIBLE);
            imGaleri.setVisibility(View.VISIBLE);

            bSave.setCardBackgroundColor(Color.parseColor("#4159BE"));

        }

        edLebarMedian.setEnabled(false);
        bSave.setVisibility(View.GONE);

        imFoto.setEnabled(false);
        imGaleri.setEnabled(false);


        session.saveSPInt(Session.POSISITABEL, helperFormTerusan.getPosisiTabel("Median"));
        locationKM = "0km0";
        getLastLocation();

        imFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                namaFile = permissionImage.getNamaFile("Median", session.getKodeprov(), session.getNoruas(), String.valueOf(session.getNosegment()), String.valueOf(session.getSubsegment()),  "");

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

                namaFile = permissionImage.getNamaFile("Median", session.getKodeprov(), session.getNoruas(), String.valueOf(session.getNosegment()), String.valueOf(session.getSubsegment()),  "");

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
                dataMedian.setLebarMedian(Float.parseFloat(edLebarMedian.getText().toString()));
                dataMedian.setGambarMedian(helperList.getNamaImage(listImage));
                dataMedian.setGambarMedianicon(helperList.getNamaImage(listImage));
                dataMedian.setLokasiMedian(helperList.getLokasi(listLokasi));


                if(session.getSurvey()==1) {
                    helperFormTerusan.saveMedian(dataMedian, asal);
                }else{
                    helperFormTerusan.saveMedianDetail(dataMedian, asal);
                }

               /* if(session.getSurvey()==1) {
                    helperFormTerusan.saveMedian(dataMedian, asal);
                }else if(asal.equals("Tabel")){
                    helperFormTerusan.saveMedianDetail(dataMedian, asal);
                }else {
                    helperFormTerusan.saveMedianSinkron(dataMedian);
                }*/

            }
        });

        return view;
    }

    private void initWidget(View view){

        imFoto = view.findViewById(R.id.formMedianFoto);
        imGaleri = view.findViewById(R.id.formMedianGaleri);
        edLebarMedian = view.findViewById(R.id.formMedianLebar);
        bSave = view.findViewById(R.id.formMedianSave);
        bAdd = view.findViewById(R.id.formMedianAdd);
        recMedian = view.findViewById(R.id.formMedianRecycle);

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


        directory = permissionImage.getFolderFile("Median", session.getKodeprov(), session.getNoruas(), "");

        dataMedian = dbMedian.getMedian(session.getKodeprov(), session.getNoruas(), session.getNosegment(), session.getSubsegment());

        edLebarMedian.setText(String.valueOf(dataMedian.getLebarMedian()));
        listImage = helperList.getImagePath(dataMedian.getGambarMedian());
        listThumbnail = helperList.getImagePath(dataMedian.getGambarMedianicon());
        listLokasi = helperList.getStringpath(dataMedian.getLokasiMedian());

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

        recMedian.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.HORIZONTAL, false));
        recMedian.setHasFixedSize(true);
        recMedian.setAdapter(imageAdapter);

        asal = getActivity().getIntent().getExtras().get("from").toString();

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


}
