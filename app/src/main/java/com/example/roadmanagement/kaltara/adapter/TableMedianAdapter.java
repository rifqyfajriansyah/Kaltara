package com.example.roadmanagement.kaltara.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import androidx.constraintlayout.solver.widgets.Helper;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.FormTab.FormTabUtama;
import com.example.roadmanagement.kaltara.FormTab.Helper.AnimationTab;
import com.example.roadmanagement.kaltara.FormTerusan.MainFormTerusan;
import com.example.roadmanagement.kaltara.Interface.DataMedian;
import com.example.roadmanagement.kaltara.Interface.SingleSegment;
import com.example.roadmanagement.kaltara.databaseHelper.DbSpinner;
import com.example.roadmanagement.kaltara.helper.HelperTextValue;
import com.example.roadmanagement.kaltara.helper.Session;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class TableMedianAdapter extends RecyclerView.Adapter<TableMedianAdapter.ViewHolder> {
    private ArrayList<DataMedian> mArrayList;
    private ArrayList<SingleSegment> listkm;
    private Context mContex;
    private Session session;

    HelperTextValue helperTextValue;

    DbSpinner dbSpinner;


    public TableMedianAdapter(ArrayList<DataMedian> mArrayList, ArrayList<SingleSegment> listkm, Context mContex) {
        this.mArrayList = mArrayList;
        this.mContex = mContex;
        this.listkm = listkm;

        dbSpinner = new DbSpinner(mContex);

        session = new Session(mContex);

        helperTextValue = new HelperTextValue();

    }

    @Override
    public TableMedianAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TableMedianAdapter.ViewHolder(LayoutInflater.from(mContex).inflate
                (R.layout.tabelmedian, parent, false));
    }

    @Override
    public void onBindViewHolder(TableMedianAdapter.ViewHolder holder, int position) {
        DataMedian current = mArrayList.get(position);

        String kmku = listkm.get(position).getKmawal();
        String staku = listkm.get(position).getStaawal();

        String sub = listkm.get(position).getNoseg()+"."+listkm.get(position).getSubSegment();

        holder.bindTO(current, kmku, staku, sub);

        String warna = getWarna(String.valueOf(current.getLebarMedian()));
       holder.tlebar.setCardBackgroundColor(Color.parseColor(warna));



    }

    @Override
    public int getItemCount() {
        return listkm.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView lebar;
        TextView noseg;
        TextView tipe;
        TextView kmku;
        TextView staku;



        CardView tlebar;


        public ViewHolder(View itemView) {
            super(itemView);
            noseg = itemView.findViewById(R.id.tablemediansegment);
           // tipe = itemView.findViewById(R.id.tablemediantipe);
            lebar = itemView.findViewById(R.id.tablemediantinggi);
            tlebar = itemView.findViewById(R.id.ktmtinggi);
            kmku = itemView.findViewById(R.id.tablemediankm);
            staku = itemView.findViewById(R.id.tablemediansta);
            itemView.setOnClickListener(this);

        }

        public void bindTO(DataMedian current, String km, String sta, String sub) {
            noseg.setText(sub);
          //  tipe.setText(current.getTipeMedian());
            lebar.setText(String.valueOf(current.getLebarMedian()));
            //kmku.setText(km);
            staku.setText(sta);




        }


        @Override
        public void onClick(View view) {

            session.saveSPInt(Session.SP_SUBSEGMENT, mArrayList.get(getAdapterPosition()).getSubsegment());
            session.saveSPInt(Session.SP_NOSEGMENT, mArrayList.get(getAdapterPosition()).getNosegment());
            session.saveSPString(Session.SP_NORUAS, mArrayList.get(getAdapterPosition()).getNoruas());
            session.saveSPString(Session.SP_KODEPROV, mArrayList.get(getAdapterPosition()).getNoprov());
            session.saveSPInt(Session.FOKUS, getAdapterPosition());

            if(session.getSurvey()==1){

                session.saveSPBoolean(Session.FLAG_TAB, true);
                session.saveSPInt(Session.TAB_ATRIBUT, AnimationTab.getPosisiAtribut("Median "));

                Intent intent = new Intent(mContex, FormTabUtama.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContex.startActivity(intent);

            }else {
                Intent i = new Intent(mContex, MainFormTerusan.class);

                i.putExtra("tipe", "Median");
                i.putExtra("posisi", "");
                i.putExtra("from", "Tabel");

                mContex.startActivity(i);
            }


        }
    }

    private String getWarna(String warna){
        String warnanya = "#fff000";
        String warnaku = null;

        String[] pisah= warna.split(Pattern.quote("."));


        if(pisah[1].length()>2){
            pisah[1]=pisah[1].substring(0,2);
        }else{
            pisah[1] = pisah[1];
        }

        if(Integer.valueOf(pisah[0])>99){

            warnanya = pisah[0]+"fff";

        }else if(Integer.valueOf(pisah[0])>9){
            warnanya = pisah[1]+pisah[0]+"fff";
        }else if(Integer.valueOf(pisah[0])>0 && Integer.valueOf(pisah[1])>9){
            warnanya=pisah[0]+pisah[1]+"fff";
        }else if(Integer.valueOf(pisah[0])>0 && Integer.valueOf(pisah[1])>=0){
            warnanya=getkodewarna(pisah[0])+getkodewarna(pisah[1]);
        }else if(Integer.valueOf(pisah[1])>9){
            warnanya=pisah[1]+pisah[0]+"fff";
        }else if(Integer.valueOf(pisah[1])>0){
            int kode = Integer.valueOf(pisah[1])-1;
            warnanya=getkodewarna(pisah[1])+getkodewarna(String.valueOf(kode));
        }else{
            warnanya="ffffff";
        }
        warnanya = "#"+warnanya;

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
    
}


