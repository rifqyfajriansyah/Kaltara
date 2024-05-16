package com.example.roadmanagement.kaltara.GetSemuaImage;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rifqy.kaltara.BuildConfig;
import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.Formu.DetailMedian;
import com.example.roadmanagement.kaltara.Interface.DataTemporari;
import com.example.roadmanagement.kaltara.adapter.VerticalAdapter;
import com.example.roadmanagement.kaltara.databaseHelper.DbBahu;
import com.example.roadmanagement.kaltara.databaseHelper.DbLahan;
import com.example.roadmanagement.kaltara.databaseHelper.DbLane;
import com.example.roadmanagement.kaltara.databaseHelper.DbMedian;
import com.example.roadmanagement.kaltara.databaseHelper.DbSaluran;
import com.example.roadmanagement.kaltara.databaseHelper.DbTemporari;
import com.example.roadmanagement.kaltara.helper.ImageHelper;
import com.example.roadmanagement.kaltara.helper.Session;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListImage extends AppCompatActivity implements VerticalAdapter.CallbackInterface, AdapterView.OnItemSelectedListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener  {

    Session session;
    DbLahan dbLahan;
    DbBahu dbBahu;
    DbSaluran dbSaluran;
    DbMedian dbMedian;
    DbLane dbLane;

    VerticalAdapter verticalAdapter;
    RecyclerView verticalRecycle;

    DbTemporari dbTemporari;

    ArrayList<DataListImage> listImages = new ArrayList<>();


    ImageHelper imageHelper;

    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_GALLERY_PHOTO = 2;

    File mPhotoFile;
    File iconFoto;

    Spinner stipegambar;
    Spinner sposisigambar;
    EditText segmentgambar;

    String lajur = "";

    List<String> listbagian = new ArrayList<>();

    String tipegambarku[];
    String[] posisigamarku = {"--Posisi--"};



    String Tipe;
    String posisi ="--Posisi--";
    int segment;

    String fullname; //nama tipe-posisi

    String fullGambarku; //nama fullimage untuk tambah
    String fullIconku; //untuk icon
    String fullLokasiku; //untuk lokasi




    Integer urutanku; //urutan foto
    String namaku; //nama foto single
    String iconku; //icon single
    String lokasiku; //lokasi single
    String posisiku; //posisi L or R


    TextView noprov;
    TextView noruas;


    private static final String TAG = DetailMedian.class.getSimpleName();
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private FusedLocationProviderClient mFusedLocationClient;
    protected Location mLastLocation;
    String lokasi;
    List<String> listlokasi = new ArrayList<>();


    LocationManager locationManager;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private com.google.android.gms.location.LocationListener listener;
    private long UPDATE_INTERVAL = 2 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_image);

         verticalRecycle = findViewById(R.id.recyclevertical);
        noprov = findViewById(R.id.toolbakprovinsi);
        noruas = findViewById(R.id.toolbakruas);

         session = new Session(this);
         dbLahan = new DbLahan(this);
         dbBahu = new DbBahu(this);
         dbSaluran = new DbSaluran(this);
         dbMedian = new DbMedian(this);
         dbLane = new DbLane(this);

         dbTemporari = new DbTemporari(this);

         segmentgambar = findViewById(R.id.segmentgambar);

         imageHelper = new ImageHelper(this);

         stipegambar = findViewById(R.id.tipegambar);
         sposisigambar = findViewById(R.id.posisigambar);








        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();


        mGoogleApiClient.connect();




        initSegment(posisi);



        noprov.setText(String.valueOf(session.getKodeprov()));
        session.saveSPInt(Session.SP_NOSEGMENT, 0);
        noruas.setText(session.getNoruas());





        initTipe();

        segmentgambar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    verticalRecycle.scrollToPosition(Integer.valueOf(segmentgambar.getText().toString())-1);
                    //Toast.makeText(ListImage.this, "bsia", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });


    }


    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String a = imageHelper.setNamaThumbnailList(fullname,urutanku);
            iconku = a;


            if (requestCode == REQUEST_TAKE_PHOTO) {
                try {

                    mPhotoFile = imageHelper.saveFromCamera(mPhotoFile);
                    iconFoto = imageHelper.saveIcon(mPhotoFile, a);


                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (requestCode == REQUEST_GALLERY_PHOTO) {
                Uri selectedImage = data.getData();

                try {
                    namaku = imageHelper.setNamaFileListImage(fullname,urutanku);
                    //mPhotoFile = mCompressor.compressToFile(new File(getRealPathFromUri(selectedImage)));
                    mPhotoFile = new File(getRealPathFromUri(selectedImage));
                    mPhotoFile = imageHelper.saveFromGaleri(mPhotoFile, namaku);
                    iconFoto = imageHelper.saveIcon(mPhotoFile, a);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            getLastLocation();

            lokasi = null;

            String inputImage;
            String inputIcon;
            String inputLocation;

            if(urutanku==0){
                inputImage = namaku;
                inputIcon = iconku;
                inputLocation = lokasiku;
            }else{
                inputImage = fullGambarku+","+namaku;
                inputIcon = fullIconku+","+iconku;
                inputLocation = fullLokasiku+","+lokasiku;
            }

            saveImage(Tipe, posisiku, inputImage, inputIcon, inputLocation, session.getNosegment());
            DataTemporari dataTemporari;

            String tempofoto;
            if(inputImage==null){
                tempofoto="0";
            }else{
                tempofoto="1";
            }

            if(Tipe.equals("Lane")) {
                dataTemporari = new DataTemporari(session.getKodeprov(), session.getNoruas(), session.getNosegment(), Tipe, lajur, posisi, null, 0, tempofoto,"0");
            }else{
                dataTemporari = new DataTemporari(session.getKodeprov(), session.getNoruas(), session.getNosegment(), Tipe, posisi, lajur, null, 0, tempofoto,"0");
            }
            dbTemporari.postTemporari(dataTemporari);

            initData();
        }
    }*/



    public String getRealPathFromUri(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = this.getContentResolver().query(contentUri, proj, null, null, null);
            assert cursor != null;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    @Override
    public void onPictureTaken(Integer requestCode, Intent action, File file, String fullGambar, String fullIcon, String fullLokasi, String namafile, Integer urutan) {


        fullname = namafile;
        //urutanku = urutan;
        //fullGambarku = fullGambar;
        //fullIconku = fullIcon;
        //fullLokasiku = fullLokasi;

        DataListImage dataListImage = getSingleImage(Tipe, posisiku);
        fullGambarku = dataListImage.getPathgambar();
        fullIconku = dataListImage.getPathgambaricon();
        fullLokasiku =dataListImage.getPathlokasi();
        urutanku = dapatUrut(dataListImage.getPathgambar());


        if(file!=null) {
            namaku = file.getAbsolutePath();
        }


        if(requestCode==REQUEST_TAKE_PHOTO){
            mPhotoFile = file;
        }else {
            mPhotoFile = null;
        }


        startActivityForResult(action, requestCode);

    }

    @Override
    public void onHapus(String fullimage, String icon, String lokasi, Integer segment) {


        saveImage(Tipe, posisiku, fullimage, icon, lokasi, segment);

        String tempofoto;
        if(fullimage==null){
            tempofoto="0";
        }else{
            tempofoto="1";
        }

        DataTemporari dataTemporari;
        /*if(Tipe.equals("Lane")) {
            dataTemporari = new DataTemporari(session.getKodeprov(), session.getNoruas(), session.getNosegment(), Tipe, lajur, posisi,null, 0, tempofoto,"0");
        }else{
            dataTemporari = new DataTemporari(session.getKodeprov(), session.getNoruas(), session.getNosegment(), Tipe, posisi, lajur,null, 0, tempofoto,"0");
        }*/
        //dbTemporari.postTemporari(dataTemporari);

        initData();

    }

    public void initTipe(){
        tipegambarku = getResources().getStringArray(R.array.tipe_bagian);

        listbagian.clear();


        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        tipegambarku); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        stipegambar.setAdapter(spinnerArrayAdapter);
        stipegambar.setOnItemSelectedListener(ListImage.this);
    }


    public void initPosisi(String a){

        posisigamarku = null;

        if(a.equals("Lane")){
            posisigamarku = getResources().getStringArray(R.array.posisiLane);
        }else if(a.equals("Median") || a.equals("--Tipe--")){
            posisigamarku = getResources().getStringArray(R.array.posisiMedian);
        }else{
            posisigamarku = getResources().getStringArray(R.array.posisiNormal);
        }


        Tipe = a;


        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        posisigamarku); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        sposisigambar.setAdapter(spinnerArrayAdapter);
        sposisigambar.setOnItemSelectedListener(ListImage.this);
    }

    public void initSegment(String posisi){
        if(posisi.equals("--Posisi--")){
            segmentgambar.setEnabled(false);
        }else{
            segmentgambar.setEnabled(true);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(parent.getId()==R.id.tipegambar){
            initPosisi(tipegambarku[position]);
            /*Tipe = tipegambarku[position];*/
            if(tipegambarku[position].equals("Median")){
                posisi ="";
                initSegment(posisi);
                initData();
            }
        }else if(parent.getId()==R.id.posisigambar){
            if(!Tipe.equals("Median")) {
                posisi = posisigamarku[position];
                initSegment(posisi);

                if(posisi.equals("Left")){
                    posisiku = "kiri";
                }else if (posisi.equals("Right")){
                    posisiku = "kanan";
                }else{
                    posisiku = posisi;

                    if(posisi.substring(0,1).equals("L")){
                        lajur ="Left";
                    }else {
                        lajur="Right";
                    }

                }
            }else{
                posisi = "";
            }


            if(!Tipe.equals("--Tipe--")&&!posisi.equals("--Posisi--")){
                initData();
            }


        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void initData(){

        listImages.clear();

        listImages = getImage(Tipe, posisiku);

        verticalAdapter = new VerticalAdapter(listImages, this, Tipe, posisi);

        verticalRecycle.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        verticalRecycle.setHasFixedSize(true);
        verticalRecycle.setAdapter(verticalAdapter);


        verticalAdapter.notifyDataSetChanged();


        if(session.getNosegment()!=0) {

            verticalRecycle.scrollToPosition(session.getNosegment() - 1);
        }


    }


    private Integer dapatUrut(String string){
        Integer urut = 0;
        if(string!=null) {
            String[] urutanku = string.split(",");
            urut = urutanku.length;
        }

        return urut;
    }


    @Override
    public void onStart() {
        super.onStart();

        if (!checkPermissions()) {
            requestPermissions();
        } else {
            getLastLocation();
            checkLocation();
        }
    }

    @SuppressWarnings("MissingPermission")
    private void getLastLocation() {
        final String[] latlong = {null};

        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            mLastLocation = task.getResult();

                            // Toast.makeText(PilihLokasi.this, String.valueOf(mLastLocation.getLatitude()),Toast.LENGTH_SHORT).show();
                            String latitude = String.valueOf(mLastLocation.getLatitude());
                            String longtitude = String.valueOf( mLastLocation.getLongitude());
                            latlong[0] = latitude+"km"+longtitude;

                            //Toast.makeText(ListImage.this, latlong[0], Toast.LENGTH_SHORT).show();
                            listlokasi.add(latlong[0]);

                            lokasiku = latlong[0];

                        } else {
                            lokasiku = "0km0";
                            Log.w(TAG, "getLastLocation:exception", task.getException());
                            showSnackbar(getString(R.string.no_location_detected));

                        }
                    }
                });

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

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }


    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(ListImage.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }


    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);

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
    public void onConnected(Bundle bundle) {


        // startLocationUpdates();

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


        Location mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

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
        mGoogleApiClient.disconnect();
    }


    private void saveImage(String tipe, String posisi, String pathImage, String pathIcon, String pathLocation, Integer segment){




       /* switch (tipe){
            case "Lahan" : dbLahan.updateImageLahan(String.valueOf(session.getKodeprov()), session.getNoruas(), segment, posisiku, pathImage, pathIcon, pathLocation);
                break;

            case "Bahu" : dbBahu.updateImageBahu(String.valueOf(session.getKodeprov()), session.getNoruas(), segment, posisiku, pathImage, pathIcon, pathLocation);
                break;

            case "Saluran" : dbSaluran.updateImageSaluran(String.valueOf(session.getKodeprov()), session.getNoruas(), segment, posisiku, pathImage, pathIcon, pathLocation);
                break;

            case "Median" : dbMedian.updateImageMedian(String.valueOf(session.getKodeprov()), session.getNoruas(), segment, posisiku, pathImage, pathIcon, pathLocation);
                break;

            case "Lane" : dbLane.updateImageLahan(String.valueOf(session.getKodeprov()), session.getNoruas(), segment, posisiku, pathImage, pathIcon, pathLocation);
                break;

        }*/


    }


    private ArrayList<DataListImage> getImage(String tipe, String posisi){

        ArrayList<DataListImage> listku = new ArrayList<>();


        /*switch (tipe){
            case "Lahan" : listku = dbLahan.getImageLahan(String.valueOf(session.getKodeprov()), session.getNoruas(), posisi);
            break;

            case "Bahu" : listku = dbBahu.getImageBahu(String.valueOf(session.getKodeprov()), session.getNoruas(), posisi);
            break;

            case "Saluran" : listku = dbSaluran.getImageSaluran(String.valueOf(session.getKodeprov()), session.getNoruas(), posisi);
            break;

            case "Median" : listku = dbMedian.getImageMedian(String.valueOf(session.getKodeprov()), session.getNoruas(), posisi);
            break;

            case "Lane" : listku = dbLane.getImageLane(String.valueOf(session.getKodeprov()), session.getNoruas(), posisi);
            break;

        }*/



        return listku;
    }


    private DataListImage getSingleImage(String tipe, String posisi){
        DataListImage dataku = null;

       /* switch (tipe){
            case "Lahan" : dataku=dbLahan.getImageSingle(String.valueOf(session.getKodeprov()), session.getNoruas(), String.valueOf(session.getNosegment()), posisi);
            break;

            case "Saluran" : dataku=dbSaluran.getImageSingle(String.valueOf(session.getKodeprov()), session.getNoruas(), String.valueOf(session.getNosegment()), posisi);
                break;

            case "Bahu" : dataku=dbBahu.getImageSingle(String.valueOf(session.getKodeprov()), session.getNoruas(), String.valueOf(session.getNosegment()), posisi);
                break;

            case "Median" : dataku=dbMedian.getImageSingle(String.valueOf(session.getKodeprov()), session.getNoruas(), String.valueOf(session.getNosegment()), posisi);
                break;

            case "Lane" : dataku=dbLane.getImageSingle(String.valueOf(session.getKodeprov()), session.getNoruas(), String.valueOf(session.getNosegment()), posisi);
                break;
        }*/

        return dataku;
    }



}
