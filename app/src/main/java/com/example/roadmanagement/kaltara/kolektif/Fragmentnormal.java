package com.example.roadmanagement.kaltara.kolektif;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rifqy.kaltara.R;

public class Fragmentnormal extends Fragment {

    public static Fragmentnormal newInstance() {
        Fragmentnormal fragment = new Fragmentnormal();
        return fragment;
    }

    public Fragmentnormal() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.normal, container, false);
        return rootView;
    }
}
