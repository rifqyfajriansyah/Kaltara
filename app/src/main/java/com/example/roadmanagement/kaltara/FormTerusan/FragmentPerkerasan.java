package com.example.roadmanagement.kaltara.FormTerusan;

import static android.app.Activity.RESULT_OK;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.Interface.DataInletMedian;
import com.example.roadmanagement.kaltara.Interface.DataMedian;
import com.example.roadmanagement.kaltara.Interface.DataSpDalam;
import com.example.roadmanagement.kaltara.Interface.DataSpLuar;
import com.example.roadmanagement.kaltara.Interface.SendId;
import com.example.roadmanagement.kaltara.adapter.ImageAdapter;
import com.example.roadmanagement.kaltara.databaseHelper.DbMedian;
import com.example.roadmanagement.kaltara.databaseHelper.DbMinlet;
import com.example.roadmanagement.kaltara.databaseHelper.DbSpd;
import com.example.roadmanagement.kaltara.databaseHelper.DbSpl;
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

public class FragmentPerkerasan extends Fragment {

    //Widget
    ImageView imGaleri, imFoto;
    EditText edLebarMedian;
    RecyclerView recMedian;
    CardView bSave, bAdd;


    //Variable
    String posisi, tipe;
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

    DataInletMedian dataMinlet;
    DbMinlet dbMinlet;

    private FusedLocationProviderClient mFusedLocationClient;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private long UPDATE_INTERVAL = 2 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */

    public static FragmentPerkerasan newInstance(String posisi, String tipe) {
        FragmentPerkerasan fragment = new FragmentPerkerasan();
        Bundle args = new Bundle();
        args.putString("posisi", posisi);
        args.putString("tipe", tipe);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        posisi = getArguments().getString("posisi");
        tipe = getArguments().getString("tipe");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.form_terusan_median, container, false);

        context = getActivity();
        initWidget(view);
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


        session.saveSPInt(Session.POSISITABEL, helperFormTerusan.getPosisiTabel("Perkerasan"));


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

        dbMinlet = new DbMinlet(context);
        helperList = new HelperList();
        helperFormTerusan = new HelperFormTerusan(context);




    }



    private void initValue(){
        DbSpl dbSpl = new DbSpl(context);
        DbSpd dbSpd = new DbSpd(context);

        Float lebar;
        String valueGambar, valueIcon, valueLokasi;


        if(tipe.equals("SPD")){
            DataSpDalam dataSpDalam = dbSpd.getData(session.getKodeprov(), session.getNoruas(), session.getNosegment(), session.getSubsegment(), posisi);
            lebar = dataSpDalam.getLebar();
            valueGambar = dataSpDalam.getGambar();
            valueIcon = dataSpDalam.getIcon();
            valueLokasi = dataSpDalam.getLokasi();
        }else if(tipe.equals("SPL")){

            DataSpLuar dataSpLuar = dbSpl.getData(session.getKodeprov(), session.getNoruas(), session.getNosegment(), session.getSubsegment(), posisi);
            lebar = dataSpLuar.getLebar();
            valueGambar = dataSpLuar.getGambar();
            valueIcon = dataSpLuar.getIcon();
            valueLokasi = dataSpLuar.getLokasi();

        }else{
            dataMinlet = dbMinlet.getMinlet(session.getKodeprov(), session.getNoruas(), session.getNosegment(), session.getSubsegment(), posisi);
            lebar = dataMinlet.getLebar();
            valueGambar = dataMinlet.getGambar();
            valueIcon = dataMinlet.getIcon();
            valueLokasi = dataMinlet.getLokasi();

        }

        //directory = permissionImage.getFolderFile("Perkerasan", session.getKodeprov(), session.getNoruas(), "");


       /* edLebarMedian.setText(String.valueOf(dataMinlet.getLebar()));
        listImage = helperList.getImagePath(dataMinlet.getGambar());
        listThumbnail = helperList.getImagePath(dataMinlet.getIcon());
        listLokasi = helperList.getStringpath(dataMinlet.getLokasi());*/

        edLebarMedian.setText(String.valueOf(lebar));
        listImage = helperList.getImagePath(valueGambar);
        listThumbnail = helperList.getImagePath(valueIcon);
        listLokasi = helperList.getStringpath(valueLokasi);

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

        //asal = getActivity().getIntent().getExtras().get("from").toString();

    }

}
