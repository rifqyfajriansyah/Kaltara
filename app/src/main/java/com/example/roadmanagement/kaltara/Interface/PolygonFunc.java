package com.example.roadmanagement.kaltara.Interface;

import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;

public interface PolygonFunc {
    void tambahPoygon(PolygonOptions polygon, Koordinat position);

    void lihatPolygon(ArrayList<PolygonOptions> polygonList, ArrayList<Koordinat> position);
}
