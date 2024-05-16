package com.example.roadmanagement.kaltara.CameraLoc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rifqy.kaltara.R;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class PicturePreview extends AppCompatActivity {

    private PhotoView imageView;
    private Intent intent;
    private int rotasi;

    private Button save;

   // private static final String IMAGE_DIRECTORY = "/"+ Environment.DIRECTORY_ALARMS;

    private Context context;
    private String tWaktu, tLatlong, valLatlong, provinsi, ruas, namafile;

    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_preview);
        imageView = findViewById(R.id.img);

        context = PicturePreview.this;
        intent = getIntent();
        rotasi = Integer.valueOf(intent.getStringExtra("rotasi"));
        tWaktu = intent.getStringExtra("waktu");
        tLatlong = intent.getStringExtra("latlongtext");
        valLatlong = intent.getStringExtra("latlongval");
        namafile = intent.getStringExtra("namafile");

        provinsi = intent.getStringExtra("provinsi");
        ruas = intent.getStringExtra("ruas");


        save = findViewById(R.id.pictureSave);

        bitmap = getImage(Cameraku.bitmap);

        imageView.setImageBitmap(bitmap);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pathImage = saveGambar(bitmap);

                Intent returnIntent = new Intent();
                returnIntent.putExtra("latlong", valLatlong);
                returnIntent.putExtra("image", pathImage);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();

            }
        });

        //saveImage(Cameraku.bitmap);
    }


    private Bitmap getImage(Bitmap mybitmap){

            Matrix matrix = new Matrix();

            matrix.postRotate(rotasi);


            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            Bitmap bm = mybitmap.copy(Bitmap.Config.ARGB_8888, true);
            Bitmap rotatedBitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);

            writeTextOnDrawable(rotatedBitmap, tWaktu, tLatlong);
            //bm.compress(Bitmap.CompressFormat.JPEG, 50, bytes);

            return rotatedBitmap;
    }

    private String saveGambar(Bitmap bitmap){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
        //myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + File.separator + Environment.DIRECTORY_DOCUMENTS);
        // have the object build the directory structure, if needed.

        if (!wallpaperDirectory.exists()) {
            Log.d("dirrrrrr", "" + wallpaperDirectory.mkdirs());
            wallpaperDirectory.mkdirs();
        }

        try {
            /*File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");*/

            File f = new File(namafile);
            f.createNewFile();   //give read write permission
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);


            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            return "";
        }
    }


    /*public String saveImage(Bitmap myBitmap) {

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        Bitmap bm = myBitmap.copy(Bitmap.Config.ARGB_8888, true);
        writeTextOnDrawable(bm, "Bisalah", tWaktu);
        bm.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
        //myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + File.separator + Environment.DIRECTORY_DOCUMENTS);
        // have the object build the directory structure, if needed.

        if (!wallpaperDirectory.exists()) {
            Log.d("dirrrrrr", "" + wallpaperDirectory.mkdirs());
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();   //give read write permission
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);

           
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";

    }*/

    private void writeTextOnDrawable(Bitmap bm, String textWaktu, String textLatlong) {

        int i = 1;

        Typeface tf = Typeface.create("Helvetica", Typeface.BOLD);

        float paramku;

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        paint.setTypeface(tf);
        paint.setTextAlign(Paint.Align.RIGHT);


        //Rect textRect = new Rect();
        //paint.getTextBounds(text, 0, text.length(), textRect);

        Canvas canvas = new Canvas(bm);
        if(canvas.getHeight()<canvas.getWidth()){
            paramku = canvas.getHeight()/1080;
        }else{
            paramku = canvas.getWidth()/1080;
        }
        paint.setTextSize(convertToPixels(context, 14*paramku));

        //If the text is bigger than the canvas , reduce the font size
        //if(textRect.width() >= (canvas.getWidth() - 4))     //the padding on either sides is considered as 4, so as to appropriately fit in the text
        //    paint.setTextSize(convertToPixels(context, 14));        //Scaling needs to be used for different dpi's

        //Calculate the positions
        //int xPos = (canvas.getWidth() / 2) - 2;     //-2 is for regulating the x position offset
        float xPos = canvas.getWidth()-convertToPixels(context, 10*paramku);
        float yPos = convertToPixels(context, 20*paramku);
        //"- ((paint.descent() + paint.ascent()) / 2)" is the distance from the baseline to the center.
        //int yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2)) ;

        canvas.drawText(textWaktu, xPos, yPos, paint);

        if(!textLatlong.equals("0 0")){
            canvas.drawText(textLatlong, xPos, yPos+convertToPixels(context,20*paramku), paint);
            i = i+1;
        }

        canvas.drawText(provinsi, xPos, yPos+convertToPixels(context,i*20*paramku), paint);
        canvas.drawText(ruas, xPos, yPos+convertToPixels(context,(i+1)*20*paramku), paint);


        //return new BitmapDrawable(getResources(), bm);
    }

    public static float convertToPixels(Context context, float nDP)
    {
        final float conversionScale = context.getResources().getDisplayMetrics().density;

        return (float) ((nDP * conversionScale) + 0.5f) ;

    }


    public static int convertDpToPixel(Context context, int dp){
        return dp * ((int) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static float convertPixelsToDp(Context context, float px){
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    private int dp(int param, int urut){

        return param*urut;
    }

}