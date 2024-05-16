package com.example.roadmanagement.kaltara.CameraLoc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.TaskStackBuilder;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.Formu.DetailMedian;
import com.example.roadmanagement.kaltara.databaseHelper.DbRuas;
import com.example.roadmanagement.kaltara.helper.Session;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.ConnectionCallbacks;
import com.google.android.gms.common.api.internal.OnConnectionFailedListener;
import com.google.android.gms.common.internal.StringResourceValueReader;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.security.auth.callback.Callback;

public class Cameraku extends AppCompatActivity{

    private static final String TAG = Cameraku.class.getSimpleName();
    private Camera mCamera;
    private CameraPreview mPreview;
    private Camera.PictureCallback mPicture;
    private Button capture;
    private ImageView switchCamera;
    private Context myContext;
    private FrameLayout cameraPreview;
    private boolean cameraFront = false;
    public static Bitmap bitmap;
    private SensorManager sensorManager;
    private Sensor sensorKu;
    private SensorEventListener senLi;


    private String provider, latlong, ruas, provinsi, kmLatlong;
    TextView tTanggal, tLatlong, tProv, tRuas;
    TextClock tWaktu;
    Calendar calendar;

    Session session;
    DbRuas dbruas;


    private LinearLayout linText;

    private int rotasiku;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cameraku);

        myContext = this;

        initWidget();
        initObject();
        initValue();


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);



        // camera surface view created
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);



        setTextku(linText);


        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(myContext, "Cetek", Toast.LENGTH_SHORT).show();
                mCamera.takePicture(null, null, mPicture);
            }
        });


        switchCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the number of cameras
                int camerasNumber = Camera.getNumberOfCameras();

                if (camerasNumber > 1) {
                    //release the old camera instance
                    //switch camera, from the front and the back and vice versa

                    releaseCamera();
                    chooseCamera();
                } else {

                }
            }
        });

    }

    private void initWidget(){

        linText = findViewById(R.id.camFullText);
        tWaktu = findViewById(R.id.camTextWaktu);
        tTanggal = findViewById(R.id.camTextTanggal);
        tLatlong = findViewById(R.id.camTextLatlong);
        tProv = findViewById(R.id.camTextProvinsi);
        tRuas = findViewById(R.id.camTextRuas);
        capture = findViewById(R.id.btnCam);
        switchCamera = findViewById(R.id.btnSwitch);
        cameraPreview = findViewById(R.id.cPreview);


    }

    private void initObject(){

        dbruas = new DbRuas(myContext);
        session = new Session(myContext);

        mCamera = Camera.open();
        mCamera.setDisplayOrientation(90);

        mPreview = new CameraPreview(myContext, mCamera);
        cameraPreview.addView(mPreview);
        mPicture = getPictureCallback();
        mCamera.startPreview();

    }

    private void initValue(){

        rotasiku = 90;


        kmLatlong  = getIntent().getStringExtra("kmlokasi");

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());


        calendar = Calendar.getInstance();
        tTanggal.setText(String.valueOf(df.format(c)));

        provinsi = session.getNamaprov();
        ruas = session.getNoruas()+" - "+dbruas.getNamaRuas(session.getKodeprov(), session.getNoruas());

        tProv.setText(provinsi);
        tRuas.setText(ruas);



        if(kmLatlong.equals("0km0")){
            tLatlong.setVisibility(View.GONE);
            latlong = "0 0";
        }else{
            tLatlong.setVisibility(View.VISIBLE);

            String[] lokasi = kmLatlong.split("km");
            latlong = lokasi[0]+" "+lokasi[1];

        }

        tLatlong.setText(latlong);

    }

    private void setTextku(LinearLayout linearLayout) {

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) linearLayout.getLayoutParams();
        final int[] rotation = new int[1];

        sensorManager =
                (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorKu =
                sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        senLi = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {


                float[] rotationMatrix = new float[16];
                SensorManager.getRotationMatrixFromVector(
                        rotationMatrix, sensorEvent.values);

                float[] remappedRotationMatrix = new float[16];
                SensorManager.remapCoordinateSystem(rotationMatrix,
                        SensorManager.AXIS_X,
                        SensorManager.AXIS_Z,
                        remappedRotationMatrix);

// Convert to orientations
                float[] orientations = new float[3];
                SensorManager.getOrientation(remappedRotationMatrix, orientations);

                for (int i = 0; i < 3; i++) {
                    orientations[i] = (float) (Math.toDegrees(orientations[i]));
                }

                if (orientations[2] > 45) {

                    rotasiku = 180;

                    rotation[0] = 270;
                    linearLayout.setOrientation(LinearLayout.VERTICAL);

                    params.removeRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    params.removeRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    params.addRule(RelativeLayout.ALIGN_PARENT_TOP);

                    //   textCoba.setGravity(80);


                } else if (orientations[2] < -45) {

                    rotasiku = 0;
                    rotation[0] = 90;

                    params.removeRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    params.removeRule(RelativeLayout.ALIGN_PARENT_TOP);
                    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);


                } else if (Math.abs(orientations[2]) < 10) {

                    rotasiku = 90;
                    rotation[0] = 0;
                    linearLayout.setOrientation(LinearLayout.VERTICAL);

                    params.removeRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    params.removeRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    params.addRule(RelativeLayout.ALIGN_PARENT_TOP);

                }

                linearLayout.setLayoutParams(params);
                linearLayout.setRotation(rotation[0]);

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        };

        sensorManager.registerListener(senLi,
                sensorKu, SensorManager.SENSOR_DELAY_NORMAL);

    }


    private int findFrontFacingCamera() {

        int cameraId = -1;

        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                cameraId = i;
                cameraFront = true;
                break;
            }
        }
        return cameraId;

    }

    private int findBackFacingCamera() {
        int cameraId = -1;
        //Search for the back facing camera
        //get the number of cameras
        int numberOfCameras = Camera.getNumberOfCameras();
        //for every camera check
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                cameraId = i;
                cameraFront = false;
                break;

            }

        }
        return cameraId;
    }

    public void onResume() {

        super.onResume();

        if (mCamera == null) {
            mCamera = Camera.open();
            mCamera.setDisplayOrientation(90);
            mPicture = getPictureCallback();
            mPreview.refreshCamera(mCamera);
            Log.d("nu", "null");
        } else {
            Log.d("nu", "no null");
        }

    }

    public void chooseCamera() {
        //if the camera preview is the front
        if (cameraFront) {
            int cameraId = findBackFacingCamera();
            if (cameraId >= 0) {
                //open the backFacingCamera
                //set a picture callback
                //refresh the preview

                mCamera = Camera.open(cameraId);
                mCamera.setDisplayOrientation(90);
                mPicture = getPictureCallback();
                mPreview.refreshCamera(mCamera);
            }
        } else {
            int cameraId = findFrontFacingCamera();
            if (cameraId >= 0) {
                //open the backFacingCamera
                //set a picture callback
                //refresh the preview
                mCamera = Camera.open(cameraId);
                mCamera.setDisplayOrientation(90);
                mPicture = getPictureCallback();
                mPreview.refreshCamera(mCamera);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        releaseCamera();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){

                Intent returnIntent = new Intent();
                returnIntent.putExtra("image",data.getStringExtra("image"));
                returnIntent.putExtra("latlong", data.getStringExtra("latlong"));
                setResult(Activity.RESULT_OK,returnIntent);
                finish();

            }
        }
    }

    private void releaseCamera() {
        // stop and release camera
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }

    private Camera.PictureCallback getPictureCallback() {
        Camera.PictureCallback picture = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                Intent intent = new Intent(Cameraku.this, PicturePreview.class);
                intent.putExtra("rotasi", String.valueOf(rotasiku));
                intent.putExtra("latlongtext", tLatlong.getText().toString());
                intent.putExtra("latlongval", kmLatlong);
                intent.putExtra("waktu", tTanggal.getText()+" "+tWaktu.getText());
                intent.putExtra("provinsi", provinsi);
                intent.putExtra("ruas", ruas);
                intent.putExtra("namafile", getIntent().getStringExtra("namafile"));

                startActivityForResult(intent, 1);
            }
        };
        return picture;
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


}
