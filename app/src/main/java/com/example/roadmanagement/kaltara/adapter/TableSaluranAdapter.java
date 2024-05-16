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
import com.example.roadmanagement.kaltara.Interface.DataSaluran;
import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.Interface.SingleSegment;
import com.example.roadmanagement.kaltara.databaseHelper.DbSpinner;
import com.example.roadmanagement.kaltara.helper.HelperTextValue;
import com.example.roadmanagement.kaltara.helper.Session;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class TableSaluranAdapter extends RecyclerView.Adapter<TableSaluranAdapter.ViewHolder> {
    private ArrayList<DataSaluran> mArrayList;
    private ArrayList<DataSaluran> mArrayListo;
    private ArrayList<SingleSegment> kmku;
    private Context mContex;
    Session session;

    DbSpinner dbSpinner;

    HelperTextValue helperTextValue;


    public TableSaluranAdapter(ArrayList<DataSaluran> mArrayList, ArrayList<DataSaluran> mArrayListo, ArrayList<SingleSegment> kmku, Context mContex) {
        this.mArrayList = mArrayList;
        this.mArrayListo = mArrayListo;
        this.kmku = kmku;
        this.mContex = mContex;

        dbSpinner = new DbSpinner(mContex);

        session = new Session(mContex);

        helperTextValue = new HelperTextValue();

    }

    @Override
    public TableSaluranAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TableSaluranAdapter.ViewHolder(LayoutInflater.from(mContex).inflate
                (R.layout.tabelsaluran, parent, false));
    }

    @Override
    public void onBindViewHolder(TableSaluranAdapter.ViewHolder holder, int position) {
        DataSaluran current = mArrayList.get(position);
       DataSaluran currento = mArrayListo.get(position);
        String kma = kmku.get(position).getKmawal();
        String sta = kmku.get(position).getStaawal();
        String sub = kmku.get(position).getNoseg()+"."+kmku.get(position).getSubSegment();

        holder.bindTO(current,currento, kma, sta, sub);

        String warnakiri = getWarna(String.valueOf(current.getLebarSaluran()), getValuetipe(current.getTipeSaluran()));
        String warnakanan = getWarna(String.valueOf(currento.getLebarSaluran()), getValuetipe(currento.getTipeSaluran()));
        holder.salurankiri.setCardBackgroundColor(Color.parseColor(warnakiri));
        holder.salurankanan.setCardBackgroundColor(Color.parseColor(warnakanan));


    }

    @Override
    public int getItemCount() {
        return kmku.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {



        TextView kms;
        TextView stas;

        TextView noseg;

        TextView tipe;
        TextView lebar;
        TextView dalam;
        TextView tipeo;
        TextView lebaro;
        TextView dalamo;

        CardView salurankiri;
        CardView salurankanan;


        public ViewHolder(View itemView) {
            super(itemView);
            kms = itemView.findViewById(R.id.tablesalurankm);
            stas = itemView.findViewById(R.id.tablesaluransta);

            noseg = itemView.findViewById(R.id.tablesaluransegment);
            tipeo = itemView.findViewById(R.id.tablesalurantipekanan);
            lebaro = itemView.findViewById(R.id.tablesaluranlebarkanan);
            tipe = itemView.findViewById(R.id.tablesalurantipekiri);
            lebar = itemView.findViewById(R.id.tablesaluranlebarkiri);

            salurankanan = itemView.findViewById(R.id.tablekanansaluran);
            salurankiri = itemView.findViewById(R.id.tablekirisaluran);

            dalam = itemView.findViewById(R.id.tablesalurandalamkiri);
            dalamo = itemView.findViewById(R.id.tablesalurandalamkanan);


            salurankiri.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*session.saveSPInt(Session.SP_SUBSEGMENT, mArrayList.get(getAdapterPosition()).getSubsegment());
                    session.saveSPInt(Session.SP_NOSEGMENT, mArrayList.get(getAdapterPosition()).getNosegment());
                    session.saveSPString(Session.SP_NORUAS, mArrayList.get(getAdapterPosition()).getNoruas());
                    session.saveSPString(Session.SP_KODEPROV, mArrayList.get(getAdapterPosition()).getNoprov());
                    session.saveSPInt(Session.FOKUS, getAdapterPosition());


                    Intent i = new Intent(mContex, MainFormTerusan.class);

                    i.putExtra("tipe", "Saluran");
                    i.putExtra("posisi", "kiri");
                    i.putExtra("from", "Tabel");
                    mContex.startActivity(i);*/

                    onClickKu(mArrayList);


                }
            });

            salurankanan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*session.saveSPInt(Session.SP_SUBSEGMENT, mArrayList.get(getAdapterPosition()).getSubsegment());
                    session.saveSPInt(Session.SP_NOSEGMENT, mArrayList.get(getAdapterPosition()).getNosegment());
                    session.saveSPString(Session.SP_NORUAS, mArrayList.get(getAdapterPosition()).getNoruas());
                    session.saveSPString(Session.SP_KODEPROV, mArrayList.get(getAdapterPosition()).getNoprov());
                    session.saveSPInt(Session.FOKUS, getAdapterPosition());


                    Intent i = new Intent(mContex, MainFormTerusan.class);

                    i.putExtra("tipe", "Saluran");
                    i.putExtra("posisi", "kanan");
                    i.putExtra("from", "Tabel");
                    mContex.startActivity(i);*/

                    onClickKu(mArrayListo);
                }
            });

        }

        public void bindTO(DataSaluran current, DataSaluran currento, String kml, String stal, String sub) {



            noseg.setText(sub);
            tipe.setText(helperTextValue.tipeSaluran(current.getTipeSaluran()));
            lebar.setText(String.valueOf(current.getLebarSaluran()));
            dalam.setText(String.valueOf(current.getDalamSaluran()));
            tipeo.setText(helperTextValue.tipeSaluran(currento.getTipeSaluran()));
            lebaro.setText(String.valueOf(currento.getLebarSaluran()));
            dalamo.setText(String.valueOf(currento.getDalamSaluran()));
            //kms.setText(kml);
            stas.setText(stal);



           // dalam.setText(String.valueOf(current.getDalamSaluran()));
          //  posisi.setText(current.getPosisi());




        }

        private void onClickKu(ArrayList<DataSaluran> arrayList) {

            session.saveSPInt(Session.SP_NOSEGMENT, arrayList.get(getAdapterPosition()).getNosegment());
            session.saveSPInt(Session.SP_SUBSEGMENT, arrayList.get(getAdapterPosition()).getSubsegment());
            session.saveSPString(Session.SP_NORUAS, arrayList.get(getAdapterPosition()).getNoruas());
            session.saveSPString(Session.SP_KODEPROV, arrayList.get(getAdapterPosition()).getNoprov());

            session.saveSPInt(Session.FOKUS, getAdapterPosition());
            String posisi = arrayList.get(getAdapterPosition()).getPosisi();

            if (session.getSurvey() == 1) {

                session.saveSPBoolean(Session.FLAG_TAB, true);
                session.saveSPString(Session.POSISI, posisi);
                session.saveSPInt(Session.TAB_ATRIBUT, AnimationTab.getPosisiAtribut("Saluran"));

                Intent intent = new Intent(mContex, FormTabUtama.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContex.startActivity(intent);

            } else {

                Intent i = new Intent(mContex, MainFormTerusan.class);
                i.putExtra("tipe", "Saluran");
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
            Intent i = new Intent(mContex, DetailSaluran.class);
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

    private String getWarna(String length, String value) {
        String warnanya = "#ffffff";

        String[] pisah = length.split(Pattern.quote("."));

        if(pisah[1].length()>2){
            pisah[1]=pisah[1].substring(0,2);
        }else{
            pisah[1] = pisah[1];
        }

        if (Integer.valueOf(pisah[0]) > 99) {
            warnanya = "#"+getkodewarna(value)+pisah[0];
        } else if(Integer.valueOf(pisah[0])>9){

            warnanya = "#"+pisah[1]+pisah[0]+getkodewarna(value);

        }else if(Integer.valueOf(pisah[0])>0 && Integer.valueOf(pisah[1])>9){

            warnanya="#"+pisah[0]+pisah[1]+getkodewarna(value);

        }else if(Integer.valueOf(pisah[0])>0 && Integer.valueOf(pisah[1])>=0){

            warnanya="#"+getkodewarna(value)+getkodewarna(pisah[1]);

        }else if(Integer.valueOf(pisah[1])>9){

            warnanya="#"+pisah[1]+value+getkodewarna(value);

        }else if(Integer.valueOf(pisah[1])>0){

            warnanya="#"+getkodewarna(pisah[1])+getkodewarna(value);

        }

        //warnanya = "#"+warnanya;

        return warnanya;
    }


    private String getValuetipe(String value){
        String hasil = null;

        if(value!=null) {
            switch (value) {
                case "Tanah terbuka":
                    hasil = "1";
                    break;

                case "Beton/pas batu terbuka":
                    hasil = "2";
                    break;

                case "Saluran irigasi":
                    hasil = "3";
                    break;

                case "Beton/pas batu tertutup":
                    hasil = "4";
                    break;

                default:
                    hasil = "0";

            }
        }else{
            hasil = "0";
        }

        return hasil;
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

            case "10" : hasil = "76a";
                break;

            case "11" : hasil = "cfa";
                break;

            case "12" : hasil = "bcd";
                break;

            case "13" : hasil = "87a";
                break;

            default : hasil = "fff";
        }

        return hasil;
    }
}
