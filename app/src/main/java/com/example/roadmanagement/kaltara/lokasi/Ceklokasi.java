package com.example.roadmanagement.kaltara.lokasi;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.rifqy.kaltara.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

public class Ceklokasi extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

private static final String TAG = "MainActivity";

private GoogleApiClient mGoogleApiClient;
private Location mLocation;
private LocationManager mLocationManager;

Button button;

private LocationRequest mLocationRequest;
private com.google.android.gms.location.LocationListener listener;
private long UPDATE_INTERVAL = 2 * 1000;  /* 10 secs */
private long FASTEST_INTERVAL = 2000; /* 2 sec */

private LocationManager locationManager;
@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ceklokasi);


        mGoogleApiClient = new GoogleApiClient.Builder(this)
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .addApi(LocationServices.API)
        .build();

        mLocationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);

        button = (Button)findViewById(R.id.kliked);

        checkLocation(); //check whether location service is enable or not in your  phone
        }

@Override
public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
        return;
        }

        startLocationUpdates();

        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if(mLocation == null){
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
protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
        mGoogleApiClient.connect();
        }
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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

        String msg = "Updated Location: " +
        Double.toString(location.getLatitude()) + "," +
        Double.toString(location.getLongitude());
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        // You can now create a LatLng Object for use with maps
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
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
}