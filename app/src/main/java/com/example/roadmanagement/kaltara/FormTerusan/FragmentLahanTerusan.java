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
import androidx.constraintlayout.solver.widgets.Helper;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.FileProvider;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.CameraLoc.Cameraku;
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

import static android.app.Activity.RESULT_OK;


public class FragmentLahanTerusan extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, AdapterView.OnItemSelectedListener{

    //Widget
    ImageView imGaleri, imFoto;
    Spinner spTipelahan, spTagun;
    EditText edTinggi;
    RecyclerView recLahan;
    CardView bSave, bAdd;


    //Variable
    String posisi, namaFile, sLokasi, asal, tipeSurvey, thumbnail, fileNama;
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
    HelperFormTerusan helperFormTerusan;

    DataLahan dataLahan;
    DataTemporari dataTemporari;
    DbLahan dbLahan;
    DbTemporari dbTemporari;

    private FusedLocationProviderClient mFusedLocationClient;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private long UPDATE_INTERVAL = 2 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */

    public static FragmentLahanTerusan newInstance(String posisi) {
        FragmentLahanTerusan fragment = new FragmentLahanTerusan();
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
                             final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.form_terusan_lahan, container, false);
        context = getActivity();
        initWidget(view);
        initObject();
        initValue();

        session.saveSPInt(Session.POSISITABEL, helperFormTerusan.getPosisiTabel("Lahan"));

        if(session.getUserTipe()==99){

            spTipelahan.setEnabled(false);
            spTagun.setEnabled(false);
            edTinggi.setEnabled(false);
            bSave.setEnabled(false);

            imFoto.setEnabled(false);
            imGaleri.setEnabled(false);
            imFoto.setVisibility(View.INVISIBLE);
            imGaleri.setVisibility(View.INVISIBLE);

            bSave.setCardBackgroundColor(Color.parseColor("#d9d9d9"));

        }else{

            spTipelahan.setEnabled(true);
            spTagun.setEnabled(true);
            edTinggi.setEnabled(true);
            bSave.setEnabled(true);

            imFoto.setEnabled(true);
            imGaleri.setEnabled(true);
            imFoto.setVisibility(View.VISIBLE);
            imGaleri.setVisibility(View.VISIBLE);

            bSave.setCardBackgroundColor(Color.parseColor("#4159BE"));

        }

        spTipelahan.setEnabled(false);
        spTagun.setEnabled(false);
        edTinggi.setEnabled(false);
        bSave.setEnabled(false);

        imFoto.setEnabled(false);
        imGaleri.setEnabled(false);
        bSave.setVisibility(View.GONE);

        locationKM = "0km0";
        getLastLocation();


        imFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                namaFile = permissionImage.getNamaFile("Lahan", session.getKodeprov(), session.getNoruas(), String.valueOf(session.getNosegment()), String.valueOf(session.getSubsegment()), posisi);

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



                namaFile = permissionImage.getNamaFile("Lahan", session.getKodeprov(), session.getNoruas(), String.valueOf(session.getNosegment()), String.valueOf(session.getSubsegment()),  posisi);

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
                dataLahan.setTinggiLahan(Float.parseFloat(edTinggi.getText().toString()));
                dataLahan.setGambarLahan(helperList.getNamaImage(listImage));
                dataLahan.setIcongambar(helperList.getNamaImage(listImage));
                dataLahan.setLokasilahan(helperList.getLokasi(listLokasi));


                if(session.getSurvey()==1) {
                    helperFormTerusan.saveLahan(dataLahan, asal);
                }else{
                    helperFormTerusan.saveLahanDetail(dataLahan, asal);
                }


            }
        });

        //Toast.makeText(context, "", Toast.LENGTH_SHORT).show();

        return view;
    }

    private void initWidget(View view){

        imFoto = view.findViewById(R.id.formLahanFoto);
        imGaleri = view.findViewById(R.id.formLahanGaleri);
        spTagun = view.findViewById(R.id.formLahanTataguna);
        spTipelahan = view.findViewById(R.id.formLahanTipe);
        edTinggi = view.findViewById(R.id.formLahanTinggi);
        recLahan = view.findViewById(R.id.formLahanRecycle);
        bSave = view.findViewById(R.id.formLahanSave);
        bAdd = view.findViewById(R.id.formLahanAdd);

        permissionHelper = new PermissionHelper(context);
        permissionImage = new PermissionImage(context);
        session = new Session(context);
        imageHelper = new ImageHelper(context);

        dbLahan = new DbLahan(context);
        dbTemporari = new DbTemporari(context);

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


        spTagun.setAdapter(helperList.getSpinner(context, R.array.tatagunaLahan));
        spTipelahan.setAdapter(helperList.getSpinner(context, R.array.tipe_lahan));

        spTagun.setOnItemSelectedListener(this);
        spTipelahan.setOnItemSelectedListener(this);


    }



    private void initValue(){

        arrTagun = helperList.getListSpinner(context, R.array.tatagunaLahan);
        arrTipelahan = helperList.getListSpinner(context, R.array.tipe_lahan);

        directory = permissionImage.getFolderFile("Lahan", session.getKodeprov(), session.getNoruas(), posisi);

        dataLahan = dbLahan.getLahan(session.getKodeprov(), session.getNoruas(), session.getNosegment(), session.getSubsegment(), posisi);
        //dataTemporari = dbTemporari.getTemporariTerusan(session.getKodeprov(), session.getNoruas(), session.getNosegment(),"Lahan", posisi, "");

        edTinggi.setText(String.valueOf(dataLahan.getTinggiLahan()));
        spTagun.setSelection(helperList.getIndex(arrTagun, dataLahan.getTatagunaLahan()));
        spTipelahan.setSelection(helperList.getIndex(arrTipelahan, dataLahan.getTipeLahan()));
        listImage = helperList.getImagePath(dataLahan.getGambarLahan());
        listThumbnail = helperList.getImagePath(dataLahan.getIcongambar());
        listLokasi = helperList.getStringpath(dataLahan.getLokasilahan());

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

        recLahan.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.HORIZONTAL, false));
        recLahan.setHasFixedSize(true);
        recLahan.setAdapter(imageAdapter);


        asal = getActivity().getIntent().getExtras().get("from").toString();

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

        if (parent.getId() == R.id.formLahanTataguna) {
            if(arrTagun[position].equals("--Pilih--")){
                dataLahan.setTatagunaLahan(null);
            }else {
                dataLahan.setTatagunaLahan(arrTagun[position]);
            }
        }else if (parent.getId() == R.id.formLahanTipe){
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
