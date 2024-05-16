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
import android.widget.Spinner;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.Interface.DataCrossDrain;
import com.example.roadmanagement.kaltara.Interface.DataTemporari;
import com.example.roadmanagement.kaltara.Interface.SendId;
import com.example.roadmanagement.kaltara.adapter.ImageAdapter;
import com.example.roadmanagement.kaltara.databaseHelper.DbGorong;
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

public class FragmentGorong extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, AdapterView.OnItemSelectedListener{

    //Widget
    ImageView imGaleri, imFoto;
    Spinner spKeberadaan, spKonstruksi, spKondisi;
    EditText edDiameter, edLebar, edTinggi;
    RecyclerView recGorong;
    CardView bSave, bAdd;
    RadioButton radioLingkaran, radioSegi;
    LinearLayout formGorong, boxDiameter, boxLebar, boxTinggi;


    //Variable
    String posisi, namaFile, sLokasi, asal, keberadaanGorong, jenisPenampang, jenisKonstruksi, kondisiSaluran, thumbnail, fileNama;
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

    public static FragmentGorong newInstance(String posisi) {
        FragmentGorong fragment = new FragmentGorong();
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

        View view = inflater.inflate(R.layout.form_crossdrain, container, false);

        context = getActivity();
        initWidget(view);
        initObject();
        initValue();

        session.saveSPInt(Session.POSISITABEL, helperFormTerusan.getPosisiTabel("Gorong-gorong"));


        if(session.getUserTipe()==99){

            spKeberadaan.setEnabled(true);
            spKonstruksi.setEnabled(true);
            spKondisi.setEnabled(true);

            radioLingkaran.setEnabled(true);
            radioSegi.setEnabled(true);

            edDiameter.setEnabled(true);
            edLebar.setEnabled(true);
            edTinggi.setEnabled(true);

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

            radioLingkaran.setEnabled(false);
            radioSegi.setEnabled(false);

            edDiameter.setEnabled(false);
            edLebar.setEnabled(false);
            edTinggi.setEnabled(false);

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

        radioLingkaran.setEnabled(false);
        radioSegi.setEnabled(false);

        edDiameter.setEnabled(false);
        edLebar.setEnabled(false);
        edTinggi.setEnabled(false);

        imFoto.setEnabled(false);
        imGaleri.setEnabled(false);
        bSave.setVisibility(View.GONE);

        locationKM = "0km0";
        getLastLocation();


        imFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                namaFile = permissionImage.getNamaFile("Gorong", session.getKodeprov(), session.getNoruas(), String.valueOf(session.getNosegment()), String.valueOf(session.getSubsegment()),  posisi);

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

                namaFile = permissionImage.getNamaFile("Gorong", session.getKodeprov(), session.getNoruas(), String.valueOf(session.getNosegment()), String.valueOf(session.getSubsegment()),  posisi);

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

                    helperFormTerusan.setOnsave(asal, "Gorong-gorong");


                }else if(dataGorong.getJenisPenampang()==null){

                    Toast.makeText(context, "Silahkan pilih jenis penampang", Toast.LENGTH_SHORT).show();

                } else if(!dataGorong.getKeberadaan().equals("Tidak ada outlet") && (dataGorong.getJenisPenampang().equals("Lingkaran") && dataGorong.getDiameter()==0)||(dataGorong.getJenisPenampang().equals("Segiempat") && (dataGorong.getTinggi()==0 || dataGorong.getLebar()==0))){
                    Toast.makeText(context, "Silahkan isi data terlebih dahulu", Toast.LENGTH_SHORT).show();

                }else {

                    dbGorong.setSaluran(dataGorong);
                    dbTemporari.postTemporari(dataTemporari);

                    helperFormTerusan.setOnsave(asal, "Gorong-gorong");

                }

            }

        });

        return view;
    }

    private void initWidget(View view){

        boxDiameter = view.findViewById(R.id.formGorongBoxDiameter);
        boxLebar = view.findViewById(R.id.formGorongBoxLebar);
        boxTinggi = view.findViewById(R.id.formGorongBoxTinggi);

        imFoto = view.findViewById(R.id.formGorongFoto);
        imGaleri = view.findViewById(R.id.formGorongGaleri);

        spKeberadaan = view.findViewById(R.id.formGorongTipe);
        spKonstruksi = view.findViewById(R.id.formGorongKonstruksi);
        spKondisi = view.findViewById(R.id.formGorongKondisi);

        edTinggi = view.findViewById(R.id.formGorongTinggi);
        edLebar = view.findViewById(R.id.formGorongLebar);
        edDiameter = view.findViewById(R.id.formGorongDiameter);

        bSave = view.findViewById(R.id.formGorongSave);
        bAdd = view.findViewById(R.id.formGorongAdd);


        formGorong = view.findViewById(R.id.formGorongForm);
        recGorong = view.findViewById(R.id.formGorongRecycle);

        groupHelper = new GRadioGroup(context);

        radioLingkaran = view.findViewById(R.id.formGorongLingkaran);
        radioSegi = view.findViewById(R.id.formGorongKotak);

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

        directory = permissionImage.getFolderFile("Gorong", session.getKodeprov(), session.getNoruas(), posisi);

        dataGorong = dbGorong.getGorong(session.getKodeprov(), session.getNoruas(), session.getNosegment(), session.getSubsegment(), posisi);

        if(dataGorong.getJenisPenampang()!=null) {
            jenisPenampang = dataGorong.getJenisPenampang();
        }

        setTextVisibility(dataGorong.getJenisPenampang());

        edTinggi.setText(String.valueOf(dataGorong.getTinggi()));
        edLebar.setText(String.valueOf(dataGorong.getLebar()));
        edDiameter.setText(String.valueOf(dataGorong.getDiameter()));

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

        asal = getActivity().getIntent().getExtras().get("from").toString();

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

        if(parent.getId()==R.id.formGorongTipe) {

            keberadaanGorong = arrKeberadaanValue[position];

            if (arrKeberadaanValue[position].equals("Tidak ada gorong")){

                formGorong.setVisibility(View.GONE);

            }else {

                formGorong.setVisibility(View.VISIBLE);

            }

        }else if(parent.getId()==R.id.formGorongKonstruksi){

            jenisKonstruksi = arrKontruksi[position];

        }else if(parent.getId()==R.id.formGorongKondisi){

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
