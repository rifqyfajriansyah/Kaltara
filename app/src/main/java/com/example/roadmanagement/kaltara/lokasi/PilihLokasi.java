package com.example.roadmanagement.kaltara.lokasi;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import androidx.annotation.NonNull;

import com.example.roadmanagement.kaltara.FormTab.FormTabUtama;
import com.example.roadmanagement.kaltara.Interface.DataRuas;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rifqy.kaltara.BuildConfig;
import com.example.roadmanagement.kaltara.Formu.FormUtama;
import com.example.roadmanagement.kaltara.Interface.Koordinat;
import com.example.roadmanagement.kaltara.Interface.Koordinatku;
import com.example.roadmanagement.kaltara.Interface.PolygonFunc;
import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.Interface.SingleSegment;
import com.example.roadmanagement.kaltara.api.Apiku;
import com.example.roadmanagement.kaltara.api.FungsiAPI;
import com.example.roadmanagement.kaltara.api.InterfaceKaltara;

import com.example.roadmanagement.kaltara.databaseHelper.DbRuas;
import com.example.roadmanagement.kaltara.databaseHelper.DbSpinner;
import com.example.roadmanagement.kaltara.helper.Session;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PilihLokasi extends FragmentActivity implements OnMapReadyCallback, AdapterView.OnItemSelectedListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    private static final String TAG = PilihLokasi.class.getSimpleName();
    DbRuas dbRuas;
    public GoogleMap mMap;
    public Marker marker;
    Session session;
    CardView ambillokasi;
    TextView namajalan;
    TextView posisi;
    TextView lajur;
    String ruasaja;
    //int segmentaja;
    double latitude;
    double longtitude;
    Retrofit retrofit;
    //String segment;
    ArrayList<Koordinat> liskordinat = new ArrayList<>();
    Apiku apiku;
    // String km;
    //  String sta;
    DbSpinner dbSpinner;
    ImageView setLokasi;

    ImageView iconBack;
    TextView ruasJudul;

    Marker markeran;

    //SingleSegment singleSegment;



    ArrayList<MarkerOptions> markerOptions = new ArrayList<>();


    List<String> tipemap = new ArrayList<>();

    HashMap<String, Koordinat> polig = new HashMap<>();
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private FusedLocationProviderClient mFusedLocationClient;
    ProgressDialog dialog;

    //HashMap<Integer, Integer> posisik = new HashMap<>();
    List<String> dataruas = new ArrayList<>();
    List<String> datasegmentku = new ArrayList<>();

    Spinner noruas;
    Spinner nosegment;
    Spinner tipe;

    protected Location mLastLocation;





    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationManager mLocationManager;

    private LocationRequest mLocationRequest;
    private com.google.android.gms.location.LocationListener listener;
    private long UPDATE_INTERVAL = 2 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */

    private LocationManager locationManager;

    boolean onreadymap = false;

    int valSegment = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_lokasi);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        noruas = findViewById(R.id.getProvinsi);
        nosegment = findViewById(R.id.getRuas);

        ruasJudul = findViewById(R.id.mapRuas);


        session = new Session(this);

        valSegment = session.getNosegment();

        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setMessage("Loading");


        dialog.show();

        ambillokasi = findViewById(R.id.getPosisi);
        ambillokasi.setVisibility(View.INVISIBLE);
        apiku = new Apiku(this);
        retrofit = apiku.initRetrofit();
        namajalan = findViewById(R.id.namajalan);
        posisi = findViewById(R.id.posisipeta);
        lajur = findViewById(R.id.lajurpeta);
        setLokasi = findViewById(R.id.getLokasi);
        tipe = findViewById(R.id.tipemap);
        dbSpinner = new DbSpinner(this);
        dbRuas = new DbRuas(this);

        iconBack = findViewById(R.id.formMapBack);

        if(session.getSurvey()==1){
            noruas.setVisibility(View.GONE);
            ruasJudul.setVisibility(View.VISIBLE);
            initSegment(session.getNoruas());

            ruasJudul.setText(session.getNoruas());


        }else{

            noruas.setVisibility(View.VISIBLE);
            ruasJudul.setVisibility(View.GONE);
            initRuas();

        }


        initTipe();



        setLokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onreadymap = true;
                mGoogleApiClient.connect();
            }
        });

        iconBack.setOnClickListener(view -> {
            Intent i = new Intent(PilihLokasi.this, FormTabUtama.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        });


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mapFragment.getMapAsync(this);

        ambillokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                session.saveSPInt(Session.SP_NOSEGMENT, valSegment);
                if(session.getTipesurvey().equals("Opposite")){
                    int subseg = dbSpinner.getMaksSegmentSub(session.getKodeprov(), session.getNoruas(), valSegment);
                    session.saveSPInt(Session.SP_SUBSEGMENT, subseg);
                }else {
                    session.saveSPInt(Session.SP_SUBSEGMENT, 0);
                }
                Intent i = new Intent(PilihLokasi.this, FormTabUtama.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                if(session.getSurvey()==1){
                    startActivity(i);
                }


            }
        });


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        /*offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(segmentaja!=0) {
                    session.saveSPInt(Session.SP_NOSEGMENT, segmentaja);
                    Intent i = new Intent(PilihLokasi.this, FormUtama.class);
                    startActivity(i);
                }
            }
        });*/



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(mMap.MAP_TYPE_SATELLITE);
        setRuas(session.getNoruas());


        mMap.setOnPolygonClickListener(new GoogleMap.OnPolygonClickListener() {
            @Override
            public void onPolygonClick(Polygon polygon) {


                Koordinat koordinat = polig.get(polygon.getId());
                //singleSegment = dbSpinner.getSpinner(koordinat.getNoProv(), koordinat.getNoRuas(), koordinat.getNoSeg(), Float.parseFloat(koordinat.getNoSeg()));
                String judul = koordinat.getNoProv() + "." + koordinat.getNoRuas()+"."+koordinat.getNoSeg();
                //String sta = "STA "+cekString(singleSegment.getStaawal())+" - "+cekString(singleSegment.getStaakhir());
                //String km = "KM "+cekString(singleSegment.getKmawal())+" - "+cekString(singleSegment.getKmakhir());
                DataRuas ruasPoly = dbRuas.getRuasdata(koordinat.getNoProv(), koordinat.getNoRuas());

                String sta = dbSpinner.getSta(koordinat.getNoProv(), koordinat.getNoRuas(), Integer.valueOf(koordinat.getNoSeg()), "awal")+" - "+dbSpinner.getSta(koordinat.getNoProv(), koordinat.getNoRuas(), Integer.valueOf(koordinat.getNoSeg()), "akhir");
                //String km = dbSpinner.getKm(koordinat.getNoProv(), koordinat.getNoRuas(), Integer.valueOf(koordinat.getNoSeg()), "awal")+" - "+dbSpinner.getKm(koordinat.getNoProv(), koordinat.getNoRuas(), Integer.valueOf(koordinat.getNoSeg()), "akhir");

                namajalan.setText(judul);
                posisi.setText(ruasPoly.getNamaruas());
                lajur.setText(sta);
                ambillokasi.setVisibility(View.VISIBLE);
                valSegment = Integer.valueOf(koordinat.getNoSeg());

            }
        });


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (ambillokasi.getVisibility() == View.VISIBLE) {
                    ambillokasi.setVisibility(View.INVISIBLE);
                }
            }
        });

    }


    private void getPoligonRuas(String ruas, final PolygonFunc polygonFunc) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("provinsi", String.valueOf(session.getKodeprov()));
        hashMap.put("ruas", ruas);
        final ArrayList<PolygonOptions> polygons = new ArrayList<>();
        final ArrayList<Koordinat> koordinats = new ArrayList<>();
        InterfaceKaltara apiService = retrofit.create(InterfaceKaltara.class);
        Call<Koordinatku> result = apiService.getLokasiRuas(session.getKodeprov(), ruas);
        result.enqueue(new Callback<Koordinatku>() {
            @Override
            public void onResponse(Call<Koordinatku> call, Response<Koordinatku> response) {
                try {
                    if (response.body() != null) {

                        for (int i = 0; i < response.body().getKoordinats().size(); i++) {
                            PolygonOptions poly = new PolygonOptions();
                            //poly.fillColor(Color.parseColor("#50000000"));
                            //poly.strokeColor(Color.parseColor("#000000"));
                            poly.fillColor(Color.BLUE);
                            poly.strokeColor(Color.BLUE);
                            //String segment = response.body().getKoordinats().get(i).getNoSeg();
                            String[] latlong = response.body().getKoordinats().get(i).getKoordinat().split(",");
                            for (int j = 0; j < latlong.length; j++) {
                                String[] fosisi = latlong[j].split(" ");
                                double latitude = Double.parseDouble(fosisi[0]);
                                double longitude = Double.parseDouble(fosisi[1]);
                                poly.add(new LatLng(latitude, longitude));
                            }

                            Koordinat koordinat = new Koordinat(response.body().getNoprov(), response.body().getNoruas(), response.body().getNamajalan(), response.body().getKoordinats().get(i).getNoSeg(), response.body().getKoordinats().get(i).getPosisi(), response.body().getKoordinats().get(i).getLajur(), response.body().getKoordinats().get(i).getKoordinat());
                            koordinats.add(koordinat);
                            polygons.add(poly);
                        }

                        // Koordinat koordinat = new Koordinat(response.body().getNoProv(), response.body().getNoRuas(), response.body().getNamaJalan(), response.body().getNoSeg(), response.body().getPosisi(), response.body().getLajur());
                        polygonFunc.lihatPolygon(polygons, koordinats);
                        String[] latlong = response.body().getKoordinats().get(0).getKoordinat().split(",");
                        String[] fosisi = latlong[0].split(" ");
                        double latitude = Double.parseDouble(fosisi[0]);
                        double longitude = Double.parseDouble(fosisi[1]);
                        if(onreadymap) {
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 18.0f));
                        }

                    } else {
                        Snackbar.make(getWindow().getDecorView().getRootView(), "Data Polygon kosong", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        dialog.dismiss();
                    }
                } catch (Exception e) {
                    Snackbar.make(getWindow().getDecorView().getRootView(), "Data Polygon kosong", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    dialog.dismiss();
                    //  Toast.makeText(PilihLokasi.this, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();

                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Koordinatku> call, Throwable t) {
                t.printStackTrace();
                //dialog.setMessage(t.toString());
                // dialog.show();
                Snackbar.make(getWindow().getDecorView().getRootView(), "Tidak ada koneksi internet", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
    }

/*
    @Override
    public void onStart() {
        super.onStart();

        if (!checkPermissionFine()) {
            requestPermissionsfine();
        } else {

            isLocationEnabled();

            if (mGoogleApiClient != null) {
                mGoogleApiClient.connect();
            }
        }

       // cekLokasi();

    }


*/

    @SuppressWarnings("MissingPermission")
    private void getLastLocation() {
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            mLastLocation = task.getResult();

                            // Toast.makeText(PilihLokasi.this, String.valueOf(mLastLocation.getLatitude()),Toast.LENGTH_SHORT).show();
                            latitude = mLastLocation.getLatitude();
                            longtitude = mLastLocation.getLongitude();
                            if(onreadymap) {
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longtitude), 18.0f));
                            }
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(new LatLng(latitude, longtitude));
                            markerOptions.title("Posisi Anda");


                            mMap.addMarker(markerOptions);


                            mGoogleApiClient.disconnect();

                            //Toast.makeText(PilihLokasi.this, String.valueOf(latitude) + "," + String.valueOf(longtitude), Toast.LENGTH_SHORT).show();
                        } else {
                            Log.w(TAG, "getLastLocation:exception", task.getException());
                            showSnackbar(getString(R.string.no_location_detected));

                        }
                    }
                });
    }

    private String cekString(String text){

        if(text==null){
            return " ";
        }else{
            return text;
        }

    }


    private void showSnackbar(final String text) {
        View container = findViewById(R.id.map);
        if (container != null) {
            Snackbar.make(container, text, Snackbar.LENGTH_LONG).show();
        }
    }

    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }


    private boolean checkPermissionFine() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(PilihLokasi.this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    private void startLocationFine() {
        ActivityCompat.requestPermissions(PilihLokasi.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION);

        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");

            showSnackbar(R.string.permission_rationale, android.R.string.ok,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startLocationPermissionRequest();
                        }
                    });

        } else {
            Log.i(TAG, "Requesting permission");
            startLocationPermissionRequest();
        }
    }

    private void requestPermissionsfine() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);

        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");

            showSnackbar(R.string.permission_rationale, android.R.string.ok,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startLocationFine();
                        }
                    });

        } else {
            Log.i(TAG, "Requesting permission");
            startLocationFine();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                showSnackbar(R.string.permission_denied_explanation, R.string.settings,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
            }
        }
    }



    public void initRuas(){
        dataruas = dbRuas.getRuasDownload(String.valueOf(session.getKodeprov()));
        String[] spinnerArray = new String[dataruas.size()];
        dataruas.toArray(spinnerArray);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        spinnerArray); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        noruas.setAdapter(spinnerArrayAdapter);
        noruas.setOnItemSelectedListener(PilihLokasi.this);

        if(session.getNoruas()!=null){
            noruas.setSelection(setValue());
        }

    }

    public void initTipe(){

        String[] tipespinner = {"Road", "Satelite"};

        tipemap.clear();

        tipemap.add("Road");
        tipemap.add("Satelite");



        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        tipespinner); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        tipe.setAdapter(spinnerArrayAdapter);
        tipe.setOnItemSelectedListener(PilihLokasi.this);

    }

    public void initSegment(String ruas){
        datasegmentku = dbSpinner.getSegment(String.valueOf(session.getKodeprov()), ruas);
        if(datasegmentku!=null) {
            String[] spinnerArray = new String[datasegmentku.size()];
            //Toast.makeText(PilihLokasi.this, datasegment.size(), Toast.LENGTH_SHORT).show();
            datasegmentku.toArray(spinnerArray);
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                    (this, android.R.layout.simple_spinner_item,
                            spinnerArray); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                    .simple_spinner_dropdown_item);
            nosegment.setAdapter(spinnerArrayAdapter);
            nosegment.setOnItemSelectedListener(PilihLokasi.this);
        }

        nosegment.setSelection(valSegment-1);


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(parent.getId() == R.id.getProvinsi){
            initSegment(dataruas.get(position));
            setRuas(dataruas.get(position));
            session.saveSPString(Session.SP_NORUAS, dataruas.get(position));


        }else{
            if (parent.getId() == R.id.getRuas){
                valSegment = Integer.valueOf(datasegmentku.get(position));
                pindahPosisi(valSegment);

            }else if(parent.getId() == R.id.tipemap){
                switch (tipemap.get(position)){
                    case "Satelite" : mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    break;
                    case "Road" : mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    break;
                    case "Hybrid" : mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                    break;
                    case "Terrain" : mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                }
            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {



    }

    public void pindahPosisi(int id) {
        for (int i = 0; i<liskordinat.size();i++){
            if(liskordinat.get(i).getNoSeg().equals(String.valueOf(id))){
                //koordinat = liskordinat.get(i);
                String[] latlong =  liskordinat.get(i).getKoordinat().split(",");
                int tengah;
                if (latlong.length/2 %2 == 1){
                    tengah = latlong.length/2;
                }else {
                    tengah = (latlong.length+1)/2;
                }

               // Toast.makeText(PilihLokasi.this, String.valueOf(tengah), Toast.LENGTH_SHORT).show();
                String[] fosisi = latlong[tengah].split(" ");
                double latitude = Double.parseDouble(fosisi[0]);
                double longitude = Double.parseDouble(fosisi[1]);

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 24.0f));

            }
        }


    }

    public int setValue(){
        int index = 0;

            for(int i = 0 ; i<dataruas.size();i++){
                if(dataruas.get(i).equals(session.getNoruas())){
                    index = i;
                }
            }


        return index;
    }

    public void setRuas(String ruas){
        dialog.show();
        dialog.setIndeterminate(false);
        dialog.setCancelable(false);
        /*
        getPoligonRuas(ruas, new PolygonFunc() {
            @Override
            public void tambahPoygon(PolygonOptions polygon, Koordinat position) {

            }

            @Override
            public void lihatPolygon(ArrayList<PolygonOptions> polygonList, ArrayList<Koordinat> position) {
                for(int i=0; i<polygonList.size();i++){
                    liskordinat = position;
                    Polygon polygon = mMap.addPolygon(polygonList.get(i));
                    polygon.setClickable(true);
                    polig.put(polygon.getId(), position.get(i));
                    if(i==polygonList.size()-1){
                        dialog.dismiss();
                    }
                }
            }
        });*/

        FungsiAPI fungsiAPI = new FungsiAPI(PilihLokasi.this);

        fungsiAPI.getLokasiLane(ruas, new PolygonFunc() {
            @Override
            public void tambahPoygon(PolygonOptions polygon, Koordinat position) {

            }

            @Override
            public void lihatPolygon(ArrayList<PolygonOptions> polygonList, ArrayList<Koordinat> position) {
                for(int i=0; i<polygonList.size();i++){
                    liskordinat = position;
                    Polygon polygon = mMap.addPolygon(polygonList.get(i));
                    polygon.setClickable(true);
                    polig.put(polygon.getId(), position.get(i));
                    if(i==polygonList.size()-1){
                        dialog.dismiss();
                        valSegment = session.getNosegment();
                        pindahPosisi(valSegment);
                    }
                }
            }
        });

        /*fungsiAPI.getLokasiMedian(ruas, new PolygonFunc() {
            @Override
            public void tambahPoygon(PolygonOptions polygon, Koordinat position) {

            }

            @Override
            public void lihatPolygon(ArrayList<PolygonOptions> polygonList, ArrayList<Koordinat> position) {
                for(int i=0; i<polygonList.size();i++){
                    liskordinat = position;
                    Polygon polygon = mMap.addPolygon(polygonList.get(i));
                    polygon.setClickable(false);
                    polig.put(polygon.getId(), position.get(i));
                    if(i==polygonList.size()-1){
                        dialog.dismiss();
                    }
                }
            }
        });

        fungsiAPI.getLokasiSaluran(ruas, new PolygonFunc() {
            @Override
            public void tambahPoygon(PolygonOptions polygon, Koordinat position) {

            }

            @Override
            public void lihatPolygon(ArrayList<PolygonOptions> polygonList, ArrayList<Koordinat> position) {
                for(int i=0; i<polygonList.size();i++){
                    liskordinat = position;
                    Polygon polygon = mMap.addPolygon(polygonList.get(i));
                    polygon.setClickable(false);
                    polig.put(polygon.getId(), position.get(i));
                    if(i==polygonList.size()-1){
                        dialog.dismiss();
                    }
                }
            }
        });


        fungsiAPI.getLokasiBahu(ruas, new PolygonFunc() {
            @Override
            public void tambahPoygon(PolygonOptions polygon, Koordinat position) {

            }

            @Override
            public void lihatPolygon(ArrayList<PolygonOptions> polygonList, ArrayList<Koordinat> position) {
                for(int i=0; i<polygonList.size();i++){
                    liskordinat = position;
                    Polygon polygon = mMap.addPolygon(polygonList.get(i));
                    polygon.setClickable(false);
                    polig.put(polygon.getId(), position.get(i));
                    if(i==polygonList.size()-1){
                        dialog.dismiss();
                    }
                }
            }
        });*/
    }

    @Override
    public void onConnected(Bundle bundle) {


        startLocationUpdates();

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLocation == null) {
            startLocationUpdates();
        }
        if (mLocation != null) {

            // mLatitudeTextView.setText(String.valueOf(mLocation.getLatitude()));
            //mLongitudeTextView.setText(String.valueOf(mLocation.getLongitude()));
        } else {
            Toast.makeText(this, "Location not Detected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection Suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed. Error: " + connectionResult.getErrorCode());
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    protected void startLocationUpdates() {
        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);
        // Request location updates

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
        Log.d("reque", "--->>>>");
    }

    @Override
    public void onLocationChanged(Location location) {


        mLastLocation = location;
        latitude = mLastLocation.getLatitude();
        longtitude = mLastLocation.getLongitude();

        if(onreadymap) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longtitude), 18.0f));
        }
        MarkerOptions markerku = new MarkerOptions();
        markerku.position(new LatLng(latitude, longtitude));
        markerku.title(String.valueOf(latitude)+","+String.valueOf(longtitude));

        if(markerOptions.size()==0){
            markerOptions.add(markerku);
        }else{
            markeran.remove();
            markerOptions.remove(0);
            markerOptions.add(markerku);
        }


        markeran = mMap.addMarker(markerOptions.get(0));
        mGoogleApiClient.disconnect();

        dialog.dismiss();
    }

    private boolean checkLocation() {
        if(!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                    }
                });
        dialog.show();
    }

    private boolean isLocationEnabled() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }



    @Override
    public void onStart() {
        super.onStart();

        if (!checkPermissionFine()) {
            requestPermissionsfine();
        } else {

            checkLocation();

            if (mGoogleApiClient != null) {
                mGoogleApiClient.connect();
            }
        }

        // cekLokasi();

    }


}



