package com.example.roadmanagement.kaltara.adapter;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rifqy.kaltara.BuildConfig;
import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.GetSemuaImage.DataListImage;
import com.example.roadmanagement.kaltara.GetSemuaImage.HapusImage;
import com.example.roadmanagement.kaltara.helper.ImageHelper;
import com.example.roadmanagement.kaltara.helper.Session;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VerticalAdapter extends RecyclerView.Adapter<VerticalAdapter.ViewHolder> {

    private ArrayList<DataListImage> mArrayList;
    private Context mContex;
    ArrayList<File> listgambar;

    List<Integer> listurutan = new ArrayList<>();
    List<String> listnamagambar = new ArrayList<>();

    List<String> listicon = new ArrayList<>();
    List<String> listlokasi = new ArrayList<>();

    List<Integer> segments = new ArrayList<>();

    String singleGambar;

    ImageHelper imageHelper;

    Session session;

    File mPhotoFile;
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_GALLERY_PHOTO = 2;


    String tipe;
    String posisi;

    String fullname;
    int index = 0;



    private CallbackInterface mCallback;

    public interface CallbackInterface{

        /**
         * Callback invoked when clicked
         * @param  - the position
         * @param  - the text to pass back
         */
        void onPictureTaken(Integer requestCode //kode request
                , Intent action //tipe get foto
                , File file //file foto get kamera
                , String fullimage //full nama satu segment
                , String fullicon
                , String fulllokasi
                , String namafile //nama file get foto
                , Integer urutan //urutan get foto
        );


        void onHapus(String fullimage,  String iconImage, String lokasi, Integer segment);
    }





    public VerticalAdapter(ArrayList<DataListImage> mArrayList, Context mContex, String tipe, String posisi) {
        this.mArrayList = mArrayList;
        this.mContex = mContex;
        this.posisi = posisi;
        this.tipe = tipe;

        fullname = tipe+"_"+posisi;

        session = new Session(mContex);

        try{
            mCallback = (CallbackInterface) mContex;
        }catch(ClassCastException ex){
            //.. should log the error or throw and exception
            Log.e("MyAdapter","Must implement the CallbackInterface in the Activity", ex);
        }

        listnamagambar.clear();
        segments.clear();
        listurutan.clear();
        listicon.clear();
        listlokasi.clear();

    }

    @Override
    public VerticalAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VerticalAdapter.ViewHolder(LayoutInflater.from(mContex).inflate
                (R.layout.list_image_detail, parent, false));
    }

    @Override
    public void onBindViewHolder(VerticalAdapter.ViewHolder holder, int position) {
        DataListImage current = mArrayList.get(position);
        holder.bindTO(current);
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        TextView textView;
        Button imageView;
        RecyclerView recyclerView;
        HorizontalAdapter horizontalAdapter;


        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.gambar_segment);
            imageView = itemView.findViewById(R.id.tambahgambarsemua);
            recyclerView = itemView.findViewById(R.id.recyclehorizontal);


            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    index = getAdapterPosition();
                    session.saveSPInt(Session.SP_NOSEGMENT, getAdapterPosition()+1);
                    final CharSequence[] items = {"Take Photo", "Choose from Library",
                            "Cancel"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContex);
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int item) {
                            if (items[item].equals("Take Photo")) {
                               /* if (!checkPermissions()) {
                                    requestPermissions();
                                } else if(checkLocation()){
                                    requestStoragePermission(true);
                                }*/

                                requestStoragePermission(true);
                            } else if (items[item].equals("Choose from Library")) {
                                requestStoragePermission(false);
                            } else if (items[item].equals("Cancel")) {
                                dialog.dismiss();
                            }
                        }
                    });
                    builder.show();
                }
            });

            recyclerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    index = getAdapterPosition();
                    session.saveSPInt(Session.SP_NOSEGMENT, getAdapterPosition()+1);
                }
            });

        }

        public void bindTO(DataListImage current) {


            listgambar = new ArrayList();

            listgambar.clear();


            textView.setText(String.valueOf(current.getSegment()));

            singleGambar = current.getPathgambar();

            listgambar = getImage(singleGambar);

            if(listgambar.size()==5){
                imageView.setVisibility(View.GONE);
            }else{
                imageView.setVisibility(View.VISIBLE);
            }
            listnamagambar.add(current.getPathgambar());
            listicon.add(current.getPathgambaricon());
            listlokasi.add(current.getPathlokasi());
            segments.add(current.getSegment());


            horizontalAdapter =  new HorizontalAdapter(listgambar, current.getPathgambaricon(), current.getPathlokasi(), mContex, current.getSegment(), new HapusImage() {
                @Override
                public void hapusImage(String path, String icon, String lokasi, Integer segment) {

                    mCallback.onHapus(path, icon, lokasi, segment);

                }
            });


            recyclerView.setLayoutManager(new LinearLayoutManager(mContex, LinearLayout.HORIZONTAL, false));
            recyclerView.setAdapter(horizontalAdapter);

            horizontalAdapter.notifyDataSetChanged();

            //Glide.with(mContex).load(current).apply(new RequestOptions()).into(imageView);

        }

    }


    private ArrayList<File> getImage (String string){

        ArrayList<File> files = new ArrayList<>();

        if(string!=null) {
            String[] gambarku = string.split(",");
            listurutan.add(gambarku.length);
            for (int i = 0; i < gambarku.length; i++) {
                File fileku = new File(gambarku[i]);
                files.add(fileku);

            }
        }else{
            listurutan.add(0);
        }

        return files;
    }



    private void dispatchTakePictureIntent() {

      //  Toast.makeText(mContex, String.valueOf(index), Toast.LENGTH_SHORT).show();

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(mContex.getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();

            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(mContex,
                        BuildConfig.APPLICATION_ID + ".provider",
                        photoFile);

                mPhotoFile = photoFile;

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);


                mCallback.onPictureTaken(REQUEST_TAKE_PHOTO, takePictureIntent, mPhotoFile, listnamagambar.get(index), listicon.get(index), listlokasi.get(index), fullname, listurutan.get(index));



            }
        }
    }

    private void dispatchGalleryIntent() {

     //   Toast.makeText(mContex, String.valueOf(index), Toast.LENGTH_SHORT).show();

        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);


        mCallback.onPictureTaken(REQUEST_GALLERY_PHOTO, pickPhoto, mPhotoFile, listnamagambar.get(index), listicon.get(index), listlokasi.get(index), fullname , listurutan.get(index));


    }

    private void requestStoragePermission(final boolean isCamera) {
        Dexter.withActivity((Activity) mContex).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            if (isCamera) {
                                dispatchTakePictureIntent();
                            } else {
                                dispatchGalleryIntent();
                            }
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
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContex);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
       /* builder.setPositiveButton("GOTO SETTINGS", (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());*/
        builder.show();





    }

    private File createImageFile() throws IOException {



        File directory;
        String detail;


        if(tipe.equals("Median")){
            detail = String.valueOf(session.getKodeprov())+"."+String.valueOf(session.getNoruas())+"."+String.valueOf(session.getNosegment());
            directory = ((Activity)mContex).getExternalFilesDir(Environment.DIRECTORY_PICTURES+File.separator+session.getKodeprov()+File.separator+session.getNoruas()+File.separator+tipe);
        }else{

            String posisiku;
            if(posisi.equals("Left")){
                posisiku="L";
            }else if(posisi.equals("Right")){
                posisiku ="R";
            }else{
                posisiku=posisi;
            }
            detail =  posisi+"_"+String.valueOf(session.getKodeprov())+"."+String.valueOf(session.getNoruas())+"."+String.valueOf(session.getNosegment());
            directory =  ((Activity)mContex).getExternalFilesDir(Environment.DIRECTORY_PICTURES+File.separator+session.getKodeprov()+File.separator+session.getNoruas()+File.separator+tipe+File.separator+posisiku);
        }


        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        String mFileName = tipe +"_"+detail+"_"+timeStamp;
        File mFile = File.createTempFile(mFileName, ".jpg", directory);

        return mFile;
    }


}
