package com.example.roadmanagement.kaltara.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.roadmanagement.kaltara.FormTab.FormTabUtama;
import com.example.roadmanagement.kaltara.FormTab.Helper.AnimationTab;
import com.example.roadmanagement.kaltara.FormTerusan.MainFormTerusan;
import com.example.roadmanagement.kaltara.Interface.DataBahu;
import com.example.roadmanagement.kaltara.Interface.DataLahan;
import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.Interface.SingleSegment;
import com.example.roadmanagement.kaltara.databaseHelper.DbSpinner;
import com.example.roadmanagement.kaltara.helper.HelperTextValue;
import com.example.roadmanagement.kaltara.helper.Session;

import java.util.ArrayList;

public class TableLahanAdapter extends RecyclerView.Adapter<TableLahanAdapter.ViewHolder> {
    private ArrayList<DataLahan> mArrayList;
    private ArrayList<DataLahan> mArrayListo;
    private Context mContex;
    Session session;
    DbSpinner dbSpinner;
    ArrayList<SingleSegment> km;

    HelperTextValue helperTextValue;


    public TableLahanAdapter(ArrayList<DataLahan> mArrayList, ArrayList<DataLahan> mArrayListo, ArrayList<SingleSegment> km, Context mContex) {
        this.mArrayList = mArrayList;
        this.mArrayListo = mArrayListo;
        this.mContex = mContex;
        this.km = km;

        dbSpinner = new DbSpinner(mContex);
        session = new Session(mContex);

        helperTextValue = new HelperTextValue();

    }

    @Override
    public TableLahanAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TableLahanAdapter.ViewHolder(LayoutInflater.from(mContex).inflate
                (R.layout.tabelahan, parent, false));
    }

    @Override
    public void onBindViewHolder(TableLahanAdapter.ViewHolder holder, int position) {
        DataLahan current = mArrayList.get(position);
        DataLahan currento = mArrayListo.get(position);
        String kmku = km.get(position).getKmawal();
        String staku = km.get(position).getStaawal();
        String sub = km.get(position).getNoseg()+"."+km.get(position).getSubSegment();
        holder.bindTO(current, currento, kmku, staku, sub);

        String warnakiri = getWarna(current.getTipeLahan(), current.getTatagunaLahan());
        String warnakanan = getWarna(currento.getTipeLahan(), currento.getTatagunaLahan());

        holder.tablekiri.setCardBackgroundColor(Color.parseColor(warnakiri));
        holder.tablekanan.setCardBackgroundColor(Color.parseColor(warnakanan));

      //  String warna = getWarna(String.valueOf(current.getTinggiLahan()));
      //  holder.ttinggi.setBackgroundColor(Color.parseColor(warna));
     /*   if(current.getPosisi().equals("kiri")){
            holder.tposisi.setBackgroundColor(Color.parseColor("#66ff66"));
        }else{
            holder.tposisi.setBackgroundColor(Color.parseColor("#ff5050"));
        }*/


    }

    @Override
    public int getItemCount() {
        return km.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView tinggi;
        TextView noseg;
        TextView tagun;
        TextView tipe;

        TextView kma;
        TextView sta;

        TextView tinggio;
        TextView taguno;
        TextView tipeo;

        CardView tablekanan;
        CardView tablekiri;


        public ViewHolder(View itemView) {
            super(itemView);
            noseg = itemView.findViewById(R.id.tablelahansegment);
            tipe = itemView.findViewById(R.id.tablelahantipekiri);
            tagun = itemView.findViewById(R.id.tablelahantagunkiri);
            tinggi = itemView.findViewById(R.id.tablelahantinggikiri);

            tipeo = itemView.findViewById(R.id.tablelahantipekanan);
            taguno = itemView.findViewById(R.id.tablelahantagunkanan);
            tinggio = itemView.findViewById(R.id.tablelahantinggikanan);

            tablekanan = itemView.findViewById(R.id.tablekananlahan);
            tablekiri = itemView.findViewById(R.id.tablekirilahan);

            kma = itemView.findViewById(R.id.tablelahankm);
            sta = itemView.findViewById(R.id.tablelahansta);

            tablekanan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    /*session.saveSPInt(Session.SP_NOSEGMENT, mArrayListo.get(getAdapterPosition()).getNosegment());
                    session.saveSPString(Session.SP_NORUAS, mArrayListo.get(getAdapterPosition()).getNoruas());
                    session.saveSPString(Session.SP_KODEPROV, mArrayListo.get(getAdapterPosition()).getNoprov());
                    session.saveSPInt(Session.SP_SUBSEGMENT, mArrayList.get(getAdapterPosition()).getSubsegment());
                    session.saveSPInt(Session.FOKUS, getAdapterPosition());

                    Intent i = new Intent(mContex, MainFormTerusan.class);

                    i.putExtra("tipe", "Lahan");
                    i.putExtra("posisi", "kanan");
                    i.putExtra("from", "Tabel");

                    mContex.startActivity(i);*/

                    onClickKu(mArrayListo);
                }
            });


            tablekiri.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*session.saveSPInt(Session.SP_NOSEGMENT, mArrayList.get(getAdapterPosition()).getNosegment());
                    session.saveSPString(Session.SP_NORUAS, mArrayList.get(getAdapterPosition()).getNoruas());
                    session.saveSPString(Session.SP_KODEPROV, mArrayList.get(getAdapterPosition()).getNoprov());
                    session.saveSPInt(Session.SP_SUBSEGMENT, mArrayList.get(getAdapterPosition()).getSubsegment());
                    session.saveSPInt(Session.FOKUS, getAdapterPosition());

                    Intent i = new Intent(mContex, MainFormTerusan.class);

                    i.putExtra("tipe", "Lahan");
                    i.putExtra("posisi", "kiri");
                    i.putExtra("from", "Tabel");

                    mContex.startActivity(i);*/

                    onClickKu(mArrayList);
                }
            });

        }

        public void bindTO(DataLahan current, DataLahan currento, String kmku, String staku, String tSub) {

            noseg.setText(tSub);

            tipe.setText(helperTextValue.tipeLahan(current.getTipeLahan()));
            tagun.setText(helperTextValue.tataGuna(current.getTatagunaLahan()));
            tinggi.setText(String.valueOf(current.getTinggiLahan()));

            tipeo.setText(helperTextValue.tipeLahan(currento.getTipeLahan()));
            taguno.setText(helperTextValue.tataGuna(currento.getTatagunaLahan()));
            tinggio.setText(String.valueOf(currento.getTinggiLahan()));

            //kma.setText(kmku);
            sta.setText(staku);
//           posisi.setText(current.getPosisi());


        }

        private void onClickKu(ArrayList<DataLahan> arrayList) {

            session.saveSPInt(Session.SP_NOSEGMENT, arrayList.get(getAdapterPosition()).getNosegment());
            session.saveSPInt(Session.SP_SUBSEGMENT, arrayList.get(getAdapterPosition()).getSubsegment());
            session.saveSPString(Session.SP_NORUAS, arrayList.get(getAdapterPosition()).getNoruas());
            session.saveSPString(Session.SP_KODEPROV, arrayList.get(getAdapterPosition()).getNoprov());

            session.saveSPInt(Session.FOKUS, getAdapterPosition());
            String posisi = arrayList.get(getAdapterPosition()).getPosisi();

            if (session.getSurvey() == 1) {

                session.saveSPBoolean(Session.FLAG_TAB, true);
                session.saveSPString(Session.POSISI, posisi);
                session.saveSPInt(Session.TAB_ATRIBUT, AnimationTab.getPosisiAtribut("Lahan"));

                Intent intent = new Intent(mContex, FormTabUtama.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContex.startActivity(intent);

            } else {

                Intent i = new Intent(mContex, MainFormTerusan.class);
                i.putExtra("tipe", "Lahan");
                i.putExtra("posisi", posisi);
                i.putExtra("from", "Tabel");

                mContex.startActivity(i);

            }
        }

/*
        @Override
        public void onClick(View view) {

            session.saveSPInt(Session.SP_NOSEGMENT, mArrayList.get(getAdapterPosition()).getNosegment());
            session.saveSPString(Session.SP_NORUAS, mArrayList.get(getAdapterPosition()).getNoruas());
            session.saveSPInt(Session.SP_KODEPROV, mArrayList.get(getAdapterPosition()).getNoprov());
            session.saveSPInt(Session.FOKUS, getAdapterPosition());

            String km = dbSpinner.getKm(String.valueOf(mArrayList.get(getAdapterPosition()).getNoprov()),mArrayList.get(getAdapterPosition()).getNoruas(), String.valueOf(mArrayList.get(getAdapterPosition()).getNosegment()) );
            String sta = dbSpinner.getSta(String.valueOf(mArrayList.get(getAdapterPosition()).getNoprov()),mArrayList.get(getAdapterPosition()).getNoruas(), String.valueOf(mArrayList.get(getAdapterPosition()).getNosegment()) );

            session.saveSPString(Session.SP_KM, km);
            session.saveSPString(Session.SP_STA, sta);

            String judul;
            Intent i = new Intent(mContex, DetailLahan.class);
            if(mArrayList.get(getAdapterPosition()).getPosisi().equals("kiri")) {
                judul = "Left";
            }else{
                judul = "Right";
            }
            i.putExtra("dari","tabel");
            i.putExtra("judul", judul);
            mContex.startActivity(i);

        }*/
    }

    private String getWarna(String value1, String value2){
        String warnanya;

        warnanya = "#"+getkodewarna(getValuetipe(value1))+getkodewarna(getvalueTagun(value2));

        return warnanya;
    }

    public String getkodewarna(String kode){
        String hasil = null;
        switch (kode){
            case "0" : hasil = "fff";
                break;

            case "1" : hasil = "c92";
                break;

            case "2" : hasil = "1f2";
                break;

            case "3" : hasil = "9e1";
                break;

            case "4" : hasil = "3ca";
                break;

            case "5" : hasil = "8a4";
                break;

            case "6" : hasil = "0ff";
                break;

            case "7" : hasil = "3b4";
                break;

            case "8" : hasil = "491";
                break;

            case "9" : hasil = "5ab";
                break;

            default : hasil = "fff";
        }

        return hasil;
    }

    private String getTipe(String value){
        String hasil = null;

        if(value!=null) {
            switch (value) {
                case "T1 (tebing less 1m)":
                    hasil = "T1";
                    break;

                case "T2 (tebing 1-5m)":
                    hasil = "T2";
                    break;

                case "T3 (tebing more 5m)":
                    hasil = "T3";
                    break;

                case "L1 (lembah less 1m)":
                    hasil = "L1";
                    break;

                case "L2 (lembah 1-5m)":
                    hasil = "L2";
                    break;

                case "L3 (lembah more 5m)":
                    hasil = "L3";
                    break;

                default:
                    hasil = "-";

            }
        }else{
            hasil = "-";
        }

        return hasil;
    }


    private String getTagun(String value){
        String hasil = null;

        if(value!=null) {
            switch (value) {
                case "URBAN1 (Perumahan)":
                    hasil = "URBAN 1";
                    break;

                case "URBAN2 (Perindustrian)":
                    hasil = "URBAN 2";
                    break;

                case "URBAN3 (pertokoan, perkantoran, pasar)":
                    hasil = "URBAN 3";
                    break;

                case "RURAL (sawah, kebun, hutan)":
                    hasil = "RURAL";
                    break;


                default:
                    hasil = "-";

            }
        }else{
            hasil = "-";
        }

        return hasil;
    }

    private String getValuetipe(String value){
        String hasil = null;

        if(value!=null) {
            switch (value) {
                case "T1 (tebing less 1m)":
                    hasil = "1";
                    break;

                case "T2 (tebing 1-5m)":
                    hasil = "2";
                    break;

                case "T3 (tebing more 5m)":
                    hasil = "3";
                    break;

                case "L1 (lembah less 1m)":
                    hasil = "4";
                    break;

                case "L2 (lembah 1-5m)":
                    hasil = "5";
                    break;

                case "L3 (lembah more 5m)":
                    hasil = "6";
                    break;

                default:
                    hasil = "0";

            }
        }else{
            hasil = "0";
        }

        return hasil;
    }

    private String getvalueTagun(String value){
        String hasil = null;
        if(value!=null) {
            switch (value) {
                case "URBAN1 (Perumahan)":
                    hasil = "7";
                    break;

                case "URBAN2 (Perindustrian)":
                    hasil = "8";
                    break;

                case "URBAN3 (pertokoan, perkantoran, pasar)":
                    hasil = "9";
                    break;

                case "RURAL (sawah, kebun, hutan)":
                    hasil = "0";
                    break;


                default:
                    hasil = "0";

            }
        }else{
            hasil="0";
        }

        return hasil;
    }

}
