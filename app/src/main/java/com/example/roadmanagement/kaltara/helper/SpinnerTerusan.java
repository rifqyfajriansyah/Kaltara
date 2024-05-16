package com.example.roadmanagement.kaltara.helper;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.rifqy.kaltara.R;


public class SpinnerTerusan extends ArrayAdapter<String> {

    LayoutInflater inflater;
    String[] arrayData;

    public SpinnerTerusan(Activity context, int idLayout, String[] arrayData) {
        super(context, idLayout, arrayData);
        this.arrayData = arrayData;
        inflater = context.getLayoutInflater();

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String value = arrayData[position];
        View rowview = inflater.inflate(R.layout.singlespinner, null, true);

        TextView txtTitle = rowview.findViewById(R.id.carigedung_nama);
        txtTitle.setText(value);

        return rowview;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.singlespinnerterusan, parent, false);
        }

        String value = arrayData[position];
        TextView txtTitle = convertView.findViewById(R.id.textValue);
        txtTitle.setText(value);
        return convertView;
    }
}
