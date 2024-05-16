package com.example.roadmanagement.kaltara.helper;

import android.app.Activity;
import android.graphics.Color;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.rifqy.kaltara.R;

public class SpinnerWarna extends ArrayAdapter<String> {

    LayoutInflater inflater;
    String[] arrayData;

    public SpinnerWarna(Activity context, int idLayout, String[] arrayData) {
        super(context, idLayout, arrayData);
        this.arrayData = arrayData;
        inflater = context.getLayoutInflater();

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String value = arrayData[position];
        View rowview = inflater.inflate(R.layout.singlespinnerwarna, null, true);

        CardView cardView = rowview.findViewById(R.id.warnaCard);
        cardView.setCardBackgroundColor(Color.parseColor(getWarna(value)));

        return rowview;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.singlespinnerwarna, parent, false);
        }

        String value = arrayData[position];
        CardView cardView = convertView.findViewById(R.id.warnaCard);
        cardView.setCardBackgroundColor(Color.parseColor(getWarna(value)));
        return convertView;
    }

    private String getWarna(String value){

        String warna = "#FFFFFF";

        switch (value){
            case "merah" : warna = "#febdb3";
            break;

            case "hijau" : warna = "#d5f9d9";
                break;

            case "biru" : warna = "#b5cdf7";
                break;
        }

        return warna;
    }
}
