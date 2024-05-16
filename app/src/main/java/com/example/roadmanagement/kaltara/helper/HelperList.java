package com.example.roadmanagement.kaltara.helper;

import android.app.Activity;
import android.content.Context;
import androidx.cardview.widget.CardView;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.Interface.SingleSegment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HelperList {

    public  HelperList(){}

    public int getIndex(String[] array, String value){

        int index = 0;

        if(value!=null) {

            for (int i = 0; i < array.length; i++) {
                if (array[i].equals(value)) {
                    index = i;
                }
            }
        }

        return index;
    }


    public String getNamaImage(ArrayList<File> listFile){
        String namaFile = null;
        for (int i=0; i<listFile.size(); i++){

            if(i == 0 ){
                namaFile = listFile.get(i)+",";
            }else if (i==listFile.size()-1){
                namaFile = namaFile + listFile.get(i);
            }else{
                namaFile = namaFile + listFile.get(i)+",";
            }

        }

        return  namaFile;
    }

    public String getLokasi(List<String> listString){

        String namaList = null;

        for (int i=0; i<listString.size(); i++){

            if(i == 0 ){
                namaList = listString.get(i)+",";
            }else if (i==listString.size()-1){
                namaList = namaList + listString.get(i);
            }else{
                namaList = namaList + listString.get(i)+",";
            }

        }

        return namaList;
    }

    public SpinnerTerusan getSpinner(Context context, int idArray){

        SpinnerTerusan adapter = new SpinnerTerusan((Activity) context, R.layout.singlespinner, context.getResources().getStringArray(idArray));
        return adapter;

    }


    public SpinnerWarna getSpinnerWarna(Context context, int idArray){

        SpinnerWarna adapter = new SpinnerWarna((Activity) context, R.layout.singlespinnerwarna, context.getResources().getStringArray(idArray));
        return adapter;

    }

    public String[] getListSpinner(Context context, int idArray){

        String[] array = context.getResources().getStringArray(idArray);
        return array;
    }



    public ArrayList<File> getImagePath(String fullPath){

        ArrayList<File> listFile = new ArrayList<>();

        if(fullPath!=null) {
            if(!fullPath.equals("")) {
                String[] pathArray = fullPath.split(",");
                for (int i = 0; i < pathArray.length; i++) {
                    File file = new File(pathArray[i]);
                    listFile.add(file);
                }
            }
        }

        return listFile;
    }

    public List<String> getStringpath (String fullPath){

        List<String> listString = new ArrayList<>();

        if(fullPath!=null){

            if(!fullPath.equals("")) {
                String[] pathArray = fullPath.split(",");
                for (int i = 0; i < pathArray.length; i++) {
                    listString.add(pathArray[i]);
                }

            }
        }

        return listString;
    }

    public void setWidgetAdd(ArrayList<File> listImage, CardView cardView){

        if(listImage.size()<7){
            cardView.setVisibility(View.VISIBLE);
        }else{
            cardView.setVisibility(View.GONE);
        }

    }

    public int getStatFoto(ArrayList<File> listImage){
        if(listImage.size()>0){
            return 1;
        }else{
            return 0;
        }
    }

    public float cekMaksimal(float maksimal, float value){

        if(maksimal==0){
            return value;
        }else if(value>maksimal){
            return maksimal;
        }else{
            return value;
        }

    }

    public void setEdit(final EditText ed){

        ed.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    ed.clearFocus();
                }
                return false;
            }
        });

    }


    /*public String[] getArrayValue(Context context, int idArray){

        String[] array = context.getResources().getStringArray(idArray);
        List<String> list = arraytolistPilih(array);
        return listtoArray(list);

    }


    private String[] getarrayPilih(Context context, int idArray){

        String[] array = context.getResources().getStringArray(idArray);
        List<String> list = arraytolistPilih(array);
        return listtoArray(list);


    }

    private List<String> arraytolistPilih(String[] array){

        List<String> list = new ArrayList<>();
        list.add("--Pilih--");

        for (int i=0; i<array.length; i++){
            list.add(array[i]);
        }

        return list;

    }

    private String[] listtoArray(List<String> list){

        String[] array = new String[list.size()];
        list.toArray(array); // fill the array

        return array;

    }*/




}
