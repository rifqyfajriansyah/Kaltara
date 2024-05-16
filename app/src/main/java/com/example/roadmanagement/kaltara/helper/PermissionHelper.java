package com.example.roadmanagement.kaltara.helper;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.Interface.SendId;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.DexterBuilder;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class PermissionHelper {

    Context context;
    private String TAG;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    DialogHelper dialogHelper;

    public  PermissionHelper(Context context){

        this.context = context;
        TAG = context.getPackageName();
        dialogHelper = new DialogHelper(context);
    }


    public void checkAllPermission() {

        DexterBuilder.MultiPermissionListener dexter;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            dexter = Dexter.withActivity((Activity)context).withPermissions(Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);
        }else{
            dexter = Dexter.withActivity((Activity)context).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);
        }

                dexter.withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {

                        if (report.areAllPermissionsGranted()) {
                            dialogHelper.dialogSurvey();
                        }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            checkPermissionSingle(Manifest.permission.ACCESS_FINE_LOCATION);
                            checkPermissionSingle(Manifest.permission.CAMERA);
                            if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                                checkPermissionSingle(Manifest.permission.READ_EXTERNAL_STORAGE);
                                checkPermissionSingle(Manifest.permission.WRITE_EXTERNAL_STORAGE);

                            }
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();


    }


    public void checkPermissionSingle(String manifest){
        if(!checkPermission(manifest)){
            requestPermission(manifest);
        }
    }

    /*public void checkPermissionSingle(String manifest, SendId sendInteger){
        if(!checkPermission(manifest)){
            requestLocation(manifest);
        }else{
            sendInteger.hapusGambar(1);
        }
    }*/


    /*public void checkPermissionDouble(String manifest, String manifest2, SendId sendInteger){
        if(!checkPermission(manifest)){
            requestLocation(manifest);
        }else if(!checkPermission(manifest2)){
            requestLocation(manifest2);
        }else{
            sendInteger.hapusGambar(1);
        }
    }*/



    private boolean checkPermission(String manifest) {
        int permissionState = ActivityCompat.checkSelfPermission((Activity)context,
                manifest);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission(final String permission) {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale((Activity)context,
                        permission);

        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");

            showSnackbar(R.string.permission, android.R.string.ok,
                    view -> startPermissionRequest(permission));

        } else {
            Log.i(TAG, "Requesting permission");
            startPermissionRequest(permission);
        }
    }

    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(((Activity)context).findViewById(android.R.id.content),
                context.getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(context.getString(actionStringId), listener).show();
    }

    private void startPermissionRequest(String manifest) {
        ActivityCompat.requestPermissions((Activity)context,
                new String[]{manifest},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public boolean viewLocation() {


        if(!isLocationEnabled()) {
            showAlert();
            return false;
        }else{
            return true;
        }

    }

    public void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        context.startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                    }
                });
        dialog.show();
    }

    private boolean isLocationHasAcces(){
        int permissionState = ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    /*private boolean checkLocation() {

        boolean i;

        if(!isLocationEnabled()) {
            showAlert();
            i=false;
        }else if(!isLocationHasAcces()){
            requestLocation(Manifest.permission.ACCESS_FINE_LOCATION);
            i=false;
        }else{
            i = true;
        }

        return i;
    }*/

    /*public boolean cekPermission(String manifest){

        if(!isLocationHasAcces()) {
            requestLocation(manifest);
            return false;
        }else{
            return true;
        }
    }*/


    public boolean requestLocation() {

        final boolean[] param = {false};

        DexterBuilder.MultiPermissionListener dexter;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            dexter = Dexter.withActivity((Activity)context).withPermissions(Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);
        }else{
            dexter = Dexter.withActivity((Activity)context).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);
        }

                dexter.withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                           param[0] = true;
                        }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                //.withErrorListener(error -> Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show())
                .onSameThread()
                .check();

            return param[0];
    }

    public boolean getManifest() {

        boolean i = false;

        if(!isLocationEnabled()) {
            showAlert();
        }else if(requestLocation()){
            i=true;
        }

        return i;
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder((Activity)context);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
       /* builder.setPositiveButton("GOTO SETTINGS", (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());*/
        builder.show();

    }


}

