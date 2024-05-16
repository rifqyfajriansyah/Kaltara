package com.example.roadmanagement.kaltara.helper;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewParent;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

public class GRadioGroup {

    Context context;

    public GRadioGroup(Context context) {

        this.context = context;
    }

    public void setClickRadio(RadioButton radio1, RadioButton radio2, int posisi){

        if(posisi==1){
            radio1.setChecked(true);
            radio2.setChecked(false);
        }else if(posisi==2){
            radio1.setChecked(false);
            radio2.setChecked(true);
        }else{
            radio1.setChecked(false);
            radio2.setChecked(false);
        }

    }

    public void setClickRadio(RadioButton radio1, RadioButton radio2, RadioButton radio3, int posisi){

        radio1.setChecked(false);
        radio2.setChecked(false);
        radio3.setChecked(false);

        switch (posisi){
            case 1 : radio1.setChecked(true);
            break;

            case 2 : radio2.setChecked(true);
            break;

            case 3 : radio3.setChecked(true);
            break;
        }
    }


    public void setClickRadio(RadioButton radio1, RadioButton radio2, RadioButton radio3, RadioButton radio4, int posisi){

        radio1.setChecked(false);
        radio2.setChecked(false);
        radio3.setChecked(false);
        radio4.setChecked(false);

        switch (posisi){
            case 1 : radio1.setChecked(true);
                break;

            case 2 : radio2.setChecked(true);
                break;

            case 3 : radio3.setChecked(true);
                break;

            case 4 : radio4.setChecked(true);
                break;

        }
    }

    public int getPosisiPenampangSaluran(String penampang) {

        int index = 0;

        if(penampang!=null) {
            switch (penampang) {
                case "Trapesium":
                    index = 1;
                    break;

                case "Segitiga":
                    index = 2;
                    break;

                case "Segiempat":
                    index = 3;
                    break;

                case "1/2 Lingkaran":
                    index = 4;
                    break;
            }
        }

        return index;

    }

    public int getPosisiSaluran5(String value) {

        int index = 0;

        if(value!=null) {
            switch (value) {
                case "Ya":
                    index = 1;
                    break;

                case "Tidak":
                    index = 2;
                    break;

            }
        }

        return index;

    }

    public int getPosisiKemiringan(String value) {

        int index = 0;

        if(value!=null) {
            switch (value) {
                case "Luar":
                    index = 1;
                    break;

                case "Dalam":
                    index = 2;
                    break;

            }
        }

        return index;

    }


    public int getPosisiInlet(String penampang) {

        int index = 0;

        if(penampang!=null) {
            switch (penampang) {
                case "Curb":
                    index = 1;
                    break;

                case "Gutter":
                    index = 2;
                    break;

                case "Combination":
                    index = 3;
                    break;

            }
        }

        return index;

    }

    public int getPosisiOutlet(String penampang) {

        int index = 0;

        if(penampang!=null) {
            switch (penampang) {
                case "Lingkaran":
                    index = 1;
                    break;

                case "Segiempat":
                    index = 2;
                    break;


            }
        }

        return index;

    }

}

