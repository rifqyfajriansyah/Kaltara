package com.example.roadmanagement.kaltara.helper;

import android.graphics.Color;
import android.widget.TextView;

import com.example.rifqy.kaltara.R;

public class HelperTextValue {

    public HelperTextValue(){}

    public String tipeLahan(String tipelahan){
        String value = "-";

        if(tipelahan!=null) {
            switch (tipelahan) {
                case "T1 (tebing less 1m)":
                    value = "T1";
                    break;

                case "T2 (tebing 1-5m)":
                    value = "T2";
                    break;

                case "T3 (tebing more 5m)":
                    value = "T3";
                    break;

                case "L1 (lembah less 1m)":
                    value = "L1";
                    break;

                case "L2 (lembah 1-5m)":
                    value = "L2";
                    break;

                case "L3 (lembah more 5m)":
                    value = "L3";
                    break;


            }
        }

        return value;
    }

    public String tataGuna(String tataguna){
        String value = "-";

        if(tataguna!=null) {
            switch (tataguna) {
                case "RURAL (sawah, kebun, hutan)":
                    value = "RURAL";
                    break;

                case "URBAN1 (Perumahan)":
                    value = "URBAN 1";
                    break;

                case "URBAN2 (Perindustrian)":
                    value = "URBAN 2";
                    break;

                case "URBAN3 (pertokoan, perkantoran, pasar)":
                    value = "URBAN 3";
                    break;

            }
        }

        return value;
    }


    public String tipeSaluran(String tipesaluran){

        String value = "Tidak ada";

        if(tipesaluran!=null) {

            switch (tipesaluran) {
                case "Tanah terbuka":
                    value = "Tanah Terbuka";
                    break;

                case "Beton/pas batu terbuka":
                    value = "Beton Terbuka";
                    break;

                case "Saluran irigasi":
                    value = "Saluran Irigasi";
                    break;

                case "Beton/pas batu tertutup":
                    value = "Beton Tertutup";
                    break;

            }
        }

        return value;
    }


    public String tipeBahu(String tipebahu){
        String value = "Tidak ada";

        if(tipebahu!=null) {
            switch (tipebahu) {
                case "Bahu lunak":
                    value = "Bahu Lunak";
                    break;

                case "Bahu diperkeras":
                    value = "Bahu Diperkeras";
                    break;

                default:
                    value = "Tidak Ada";
            }
        }

        return value;
    }


    public String tipeLane(String tipelane){

        String value = "-";

        if(tipelane!=null) {
            switch (tipelane) {
                case "Unpaved/Tanah":
                    value = "Unpaved";
                    break;

                case "Japat (Awcas) / Kerikil":
                    value = "Japat";
                    break;

                case "Telford/Macadam Terbuka":
                    value = "Telford";
                    break;

                case "Burtu":
                    value = "Burtu";
                    break;

                case "Burda":
                    value = "Burda";
                    break;

                case "Penetrasi Macadam 1 Lapis":
                    value = "Macadam 1";
                    break;

                case "Penetrasi Macadam 2 Lapis":
                    value = "Macadam 2";
                    break;

                case "Lasbutag (Butas)":
                    value = "Butas";
                    break;

                case "Aspal/Beton (AC)":
                    value = "Aspal";
                    break;

                case "latasbum (nacas)":
                    value = "Latasbum";
                    break;

                case "lataston (hrs)":
                    value = "Lataston";
                    break;

                case "HRSSA":
                    value = "HRSSA";
                    break;

                case "Slurry Seal":
                    value = "Slurry Seal";
                    break;

                case "Macro Seal":
                    value = "Macro Seal";
                    break;


                case "Micro Asbuton":
                    value = "Micro Asbuton";
                    break;

                case "DGEM":
                    value = "DGEM";
                    break;

                case "SMA":
                    value = "SMA";
                    break;

                case "BMA":
                    value = "BMA";
                    break;

                case "HSWC":
                    value = "HSWC";
                    break;

                case "SPAV":
                    value = "SPAV";
                    break;

                case "Concrete/Rigid":
                    value = "Rigid";
                    break;

                case "FLEXIBLE":
                    value = "Flexible";
                    break;

                default:
                    value = "-";
            }
        }

        return value;
    }


    public String tipeVertikal(String vertikal){

        String value = "-";

        if(vertikal!=null) {
            switch (vertikal) {
                case "DATAR (F)":
                    value = "Datar";
                    break;

                case "BUKIT (R)":
                    value = "Bukit";
                    break;

                case "GUNUNG (H)":
                    value = "Gunung";
                    break;

            }
        }

        return value;
    }


    public String tipeHorizontal(String horizontal){
        String value;

        switch (horizontal){
            case "LURUS" : value = "Lurus";
                break;

            case "SEDIKIT BELOKAN" : value = "Sedikit Belokan";
                break;

            case "BANYAK BELOKAN" : value = "Banyak Belokan";
                break;

            default: value = "-";
        }

        return value;
    }

    public String tipeHorizontaltable(String horizontal){
        String value = "-";

        if(horizontal!=null) {
            switch (horizontal) {
                case "LURUS":
                    value = "Lurus";
                    break;

                case "SEDIKIT BELOKAN":
                    value = "S.Belokan";
                    break;

                case "BANYAK BELOKAN":
                    value = "B.Belokan";
                    break;

            }
        }

        return value;
    }

    public void setAtributBaru(String tipe, String value1, String value2, TextView text1, TextView text2, TextView text3){

        if(tipe!=null){
            text1.setText(tipe);
            text2.setText(value1);
            text3.setText(value2);
        }else{
            text1.setText("");
            text2.setText("Tidak ada");
            text3.setText("");
        }

    }

    public void setAtributInlet(String value1, String value2, String value3, String value4, TextView text1, TextView text2, TextView text3){

        if(value1!=null){

            text1.setText(value1);
            text2.setText(value2);

            switch (value1){

                case "Combination" :

                    text2.setText(value3);
                    text3.setText(value4);

                    break;

                case "Curb" :

                    text3.setText(value3);

                    break;

                case "Gutter" :

                    text3.setText(value4);

                    break;
            }


        }else{
            text1.setText("");
            text2.setText("Tidak ada");
            text3.setText("");
        }

    }

    public void setAtributOutlet(String tipe, String value1, String value2, String value3, TextView text1, TextView text2, TextView text3){


        if(tipe!=null) {
            if (tipe.equals("Lingkaran")) {
                text1.setText(tipe);
                text2.setText(value1);
                text3.setText("");
            } else if (tipe.equals("Segiempat")) {
                text1.setText(tipe);
                text2.setText(value2);
                text3.setText(value3);
            }
        }else{
            text1.setText("");
            text2.setText("Tidak ada");
            text3.setText("");
        }

    }

    public void setAtributBaruTabel(String tipe, String value1, String value2, TextView text1, TextView text2, TextView text3){

        if(tipe!=null){
            text1.setText(tipe);
            text2.setText(value1);
            text3.setText(value2);
        }else{
            text1.setText("-");
            text2.setText("");
            text3.setText("");
        }

    }

    public void setAtributInletTabel(String value1, String value2, String value3, String value4, TextView text1, TextView text2, TextView text3){

        if(value1!=null){

            text1.setText(value1);
            text2.setText(value2);

            switch (value1){

                case "Combination" :

                    text2.setText(value3);
                    text3.setText(value4);

                    break;

                case "Curb" :

                    text3.setText(value3);

                    break;

                case "Gutter" :

                    text3.setText(value4);

                    break;
            }


        }else{
            text1.setText("-");
            text2.setText("");
            text3.setText("");
        }

    }

    public void setAtributOutletTabel(String tipe, String value1, String value2, String value3, TextView text1, TextView text2, TextView text3){


        if(tipe!=null) {
            if (tipe.equals("Lingkaran")) {
                text1.setText(tipe);
                text2.setText(value1);
                text3.setText("");
            } else if (tipe.equals("Segiempat")) {
                text1.setText(tipe);
                text2.setText(value2);
                text3.setText(value3);
            }
        }else{
            text1.setText("-");
            text2.setText("");
            text3.setText("");
        }

    }

    public void setTextString(String tipe, TextView text1){

        text1.setText(tipe);

        if(tipe == null || tipe.equals("") || tipe.equals("-")){
            text1.setText("-");
            text1.setTextColor(getWarna("merah"));
        }else if(tipe.contains("Tidak ada") ||tipe.equals("Opposite") || tipe.contains("Tidak Ada")){
            text1.setTextColor(getWarna("merah"));
        }else if(tipe.equals("Normal")){
            text1.setTextColor(getWarna("hijau"));
        }else {
            text1.setTextColor(getWarna("hitam"));
        }

    }

    public void setTextString(float tipe, TextView text1){


        if(tipe>0){
            text1.setText(String.valueOf(tipe)+" m");
            text1.setTextColor(getWarna("hitam"));

        }else{
            text1.setText(String.valueOf(tipe));
            text1.setTextColor(getWarna("merah"));

        }



    }

    public void setTextString(int tipe, TextView text1){


        if(tipe>0){
            text1.setText(String.valueOf(tipe)+" m");
            text1.setTextColor(getWarna("hitam"));

        }else{
            text1.setText(String.valueOf(tipe));
            text1.setTextColor(getWarna("merah"));

        }

    }

    private int getWarna(String warna){

        int warnaku;

        switch (warna){
            case "merah" : warnaku =  Color.parseColor("#AA2929");
            break;

            case "hijau" : warnaku = Color.parseColor("#58968B");
            break;

            default: warnaku = Color.parseColor("#ffffff");
        }

        return warnaku;

    }


}
