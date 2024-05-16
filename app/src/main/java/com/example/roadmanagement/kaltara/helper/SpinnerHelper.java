package com.example.roadmanagement.kaltara.helper;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.roadmanagement.kaltara.Interface.DataSpinner;
import com.example.rifqy.kaltara.R;

import java.util.ArrayList;

public class SpinnerHelper extends ArrayAdapter<DataSpinner> {

    LayoutInflater inflater;
    ArrayList<DataSpinner> dataGedungs;

    public SpinnerHelper(Activity context, int id, int idaja, ArrayList<DataSpinner> dataGedungs) {
        super(context, id, idaja, dataGedungs);
        inflater = context.getLayoutInflater();

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DataSpinner dataGedung = getItem(position);

        View rowview = inflater.inflate(R.layout.singlespinner, null, true);

        TextView txtTitle = rowview.findViewById(R.id.carigedung_nama);
        txtTitle.setText(dataGedung.getTextview());


        return rowview;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.singlespinner, parent, false);
        }

        DataSpinner dataGedung = getItem(position);
        TextView txtTitle = convertView.findViewById(R.id.carigedung_nama);
        txtTitle.setText(dataGedung.getTextview());
        return convertView;
    }
}
