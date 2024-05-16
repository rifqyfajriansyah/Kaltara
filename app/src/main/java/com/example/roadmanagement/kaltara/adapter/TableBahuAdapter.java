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
import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.Interface.SingleSegment;
import com.example.roadmanagement.kaltara.databaseHelper.DbSpinner;
import com.example.roadmanagement.kaltara.helper.HelperTextValue;
import com.example.roadmanagement.kaltara.helper.Session;

import java.util.ArrayList;

public class TableBahuAdapter  extends RecyclerView.Adapter<TableBahuAdapter.ViewHolder> {
    private ArrayList<DataBahu> mArrayList;
    private ArrayList<DataBahu> mArrayListo;
    private ArrayList<SingleSegment> kmku;
    HelperTextValue helperTextValue;
    private Context mContex;
    private Session session;
    DbSpinner dbSpinner;


    public TableBahuAdapter(ArrayList<DataBahu> mArrayList, ArrayList<DataBahu> mArrayListo, ArrayList<SingleSegment> kmku, Context mContex) {
        this.mArrayList = mArrayList;
        this.mArrayListo = mArrayListo;
        this.kmku = kmku;
        this.mContex = mContex;

        helperTextValue = new HelperTextValue();

        session = new Session(mContex);
        dbSpinner = new DbSpinner(mContex);

    }

    @Override
    public TableBahuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TableBahuAdapter.ViewHolder(LayoutInflater.from(mContex).inflate
                (R.layout.tabelbahu, parent, false));
    }

    @Override
    public void onBindViewHolder(TableBahuAdapter.ViewHolder holder, int position) {
        DataBahu current = mArrayList.get(position);
        DataBahu currento = mArrayListo.get(position);
        String kma = kmku.get(position).getKmawal();
        String sta = kmku.get(position).getStaawal();
        String sub = kmku.get(position).getNoseg()+"."+kmku.get(position).getSubSegment();
        holder.bindTO(current, currento, kma, sta, sub);

       String warnakiri = getWarna(current.getTipeBahu());
       String warnakanan = getWarna(currento.getTipeBahu());
       holder.bahukiri.setCardBackgroundColor(Color.parseColor(warnakiri));
       holder.bahukanan.setCardBackgroundColor(Color.parseColor(warnakanan));

    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView noseg;
        TextView km;
        TextView sta;

        TextView lebar;
        TextView tipe;


        TextView lebaro;
        TextView tipeo;

        CardView bahukiri;
        CardView bahukanan;


        //TextView kondisi;

       // TextView posisi;

       // LinearLayout tposisi;
        //LinearLayout tlebar;


        public ViewHolder(View itemView) {
            super(itemView);
            noseg = itemView.findViewById(R.id.tablebahusegment);
            tipe = itemView.findViewById(R.id.tablebahutipekiri);
            lebar = itemView.findViewById(R.id.tablebahulebarkiri);

            tipeo = itemView.findViewById(R.id.tablebahutipekanan);
            lebaro = itemView.findViewById(R.id.tablebahulebarkanan);

            bahukiri = itemView.findViewById(R.id.tablekiribahu);
            bahukanan = itemView.findViewById(R.id.tablekananbahu);

            km = itemView.findViewById(R.id.tablebahukm);
            sta = itemView.findViewById(R.id.tablebahusta);

            //itemView.setOnClickListener(this);

            bahukiri.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickKu(mArrayList);
                }
            });

            bahukanan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   onClickKu(mArrayListo);

                }
            });

        }

        public void bindTO(DataBahu current, DataBahu currento, String kml, String stal, String tSubseg) {

            noseg.setText(tSubseg);
            //km.setText(kml);
            sta.setText(stal);

            tipe.setText(helperTextValue.tipeBahu(current.getTipeBahu()));
            lebar.setText(String.valueOf(current.getLebarBahu()));
            tipeo.setText(helperTextValue.tipeBahu(currento.getTipeBahu()));
            lebaro.setText(String.valueOf(currento.getLebarBahu()));


        }


        private void onClickKu(ArrayList<DataBahu> arrayList) {

            session.saveSPInt(Session.SP_NOSEGMENT, arrayList.get(getAdapterPosition()).getNosegment());
            session.saveSPInt(Session.SP_SUBSEGMENT, arrayList.get(getAdapterPosition()).getSubSegment());
            session.saveSPString(Session.SP_NORUAS, arrayList.get(getAdapterPosition()).getNoruas());
            session.saveSPString(Session.SP_KODEPROV, arrayList.get(getAdapterPosition()).getNoprov());

            session.saveSPInt(Session.FOKUS, getAdapterPosition());
            String posisi = arrayList.get(getAdapterPosition()).getPosisi();

            if (session.getSurvey() == 1) {

                session.saveSPBoolean(Session.FLAG_TAB, true);
                session.saveSPString(Session.POSISI, posisi);
                session.saveSPInt(Session.TAB_ATRIBUT, AnimationTab.getPosisiAtribut("Bahu"));

                Intent intent = new Intent(mContex, FormTabUtama.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContex.startActivity(intent);

            } else {

                Intent i = new Intent(mContex, MainFormTerusan.class);
                i.putExtra("tipe", "Bahu");
                i.putExtra("posisi", posisi);
                i.putExtra("from", "Tabel");

                mContex.startActivity(i);

            }
        }


        /*@Override
        public void onClick(View view) {

            session.saveSPInt(Session.SP_NOSEGMENT, mArrayList.get(getAdapterPosition()).getNosegment());
            session.saveSPString(Session.SP_NORUAS, mArrayList.get(getAdapterPosition()).getNoruas());
            session.saveSPString(Session.SP_KODEPROV, mArrayList.get(getAdapterPosition()).getNoprov());

            session.saveSPInt(Session.FOKUS, getAdapterPosition());


            Intent i = new Intent(mContex, MainFormTerusan.class);

            String posisi = mArrayList.get(getAdapterPosition()).getPosisi();
            i.putExtra("tipe", "Bahu");
            i.putExtra("posisi", posisi);
            i.putExtra("from", "Tabel");

            mContex.startActivity(i);


        }*/
    }


    private String getWarna(String tipe) {

        String warna = "#FFFFFF";

        if(tipe!=null) {

            if (tipe.equals("Bahu lunak")) {
                warna = "#FF00BCD4";
            }else if(tipe.equals("Bahu diperkeras")){
                warna = "#FFECE288";
            }
        }

        return warna;
    }


    private String getValuetipe(String value){
        String hasil = null;

        if(value!=null) {
            switch (value) {
                case "bahu lunak":
                    hasil = "1";
                    break;

                case "bahu yang diperkeras":
                    hasil = "2";
                    break;

                case "tidak ada bahu":
                    hasil = "3";
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

            default : hasil = "fff";
        }

        return hasil;
    }
}

