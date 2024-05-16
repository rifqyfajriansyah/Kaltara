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

import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.FormTab.FormTabUtama;
import com.example.roadmanagement.kaltara.FormTab.Helper.AnimationTab;
import com.example.roadmanagement.kaltara.FormTerusan.MainFormTerusan;
import com.example.roadmanagement.kaltara.Interface.DataSegmen;
import com.example.roadmanagement.kaltara.Interface.SingleSegment;
import com.example.roadmanagement.kaltara.databaseHelper.DbSpinner;
import com.example.roadmanagement.kaltara.helper.HelperTextValue;
import com.example.roadmanagement.kaltara.helper.Session;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class TableSegmenAdapter extends RecyclerView.Adapter<TableSegmenAdapter.ViewHolder> {
    private ArrayList<DataSegmen> mArrayList;
    private ArrayList<SingleSegment> kmku;
    private Context mContex;
    Session session;

    DbSpinner dbSpinner;
    String val1;
    String val2;

    HelperTextValue helperTextValue;


    public TableSegmenAdapter(ArrayList<DataSegmen> mArrayList, ArrayList<SingleSegment> kmku, Context mContex) {
        this.mArrayList = mArrayList;
        this.kmku = kmku;
        this.mContex = mContex;

        dbSpinner = new DbSpinner(mContex);

        session = new Session(mContex);

        helperTextValue = new HelperTextValue();

    }

    @Override
    public TableSegmenAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TableSegmenAdapter.ViewHolder(LayoutInflater.from(mContex).inflate
                (R.layout.tablesegment, parent, false));
    }

    @Override
    public void onBindViewHolder(TableSegmenAdapter.ViewHolder holder, int position) {
        DataSegmen current = mArrayList.get(position);

        String kml = kmku.get(position).getKmawal();
        String stal = kmku.get(position).getStaawal();
        String sub = kmku.get(position).getNoseg()+"."+kmku.get(position).getSubSegment();

        if(session.getTipesurvey().equals("Opposite")){
            val1 = String.valueOf(current.getSegmentl1()+current.getSegmentl2()+current.getSegmentl3()+current.getSegmentl4()+current.getSegmentl5());
            val2 = String.valueOf(current.getSegmentr1()+current.getSegmentr2()+current.getSegmentr3()+current.getSegmentr4()+current.getSegmentr5());
        }else{
            val2 = String.valueOf(current.getSegmentl1()+current.getSegmentl2()+current.getSegmentl3()+current.getSegmentl4()+current.getSegmentl5());
            val1 = String.valueOf(current.getSegmentr1()+current.getSegmentr2()+current.getSegmentr3()+current.getSegmentr4()+current.getSegmentr5());
        }


        String warna1 = getkodewarna(String.valueOf(val1));
        String warna2 = getkodewarna(String.valueOf(val2));

        holder.bindTO(current, val1, val2, kml, stal, sub);
        holder.segmentkiri.setCardBackgroundColor(Color.parseColor(warna1));
        holder.segmentkanan.setCardBackgroundColor(Color.parseColor(warna2));


    }

    @Override
    public int getItemCount() {
        return kmku.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {



        TextView kms;
        TextView stas;

        TextView noseg;
        TextView jumlahkiri;
        TextView jumlahkanan;
        TextView horizontalkiri;
        TextView horizontalkanan;
        TextView vertikalkiri;
        TextView vertikalkanan;

        CardView segmentkiri;
        CardView segmentkanan;


        public ViewHolder(View itemView) {
            super(itemView);
            kms = itemView.findViewById(R.id.tablesegmentkm);
            stas = itemView.findViewById(R.id.tablesegmentsta);
            noseg = itemView.findViewById(R.id.tablesegmentsegment);

            segmentkiri = itemView.findViewById(R.id.tablekirisegment);
            segmentkanan = itemView.findViewById(R.id.tablekanansegment);

            jumlahkiri = itemView.findViewById(R.id.tablesegmentjumlahs);
            jumlahkanan = itemView.findViewById(R.id.tablesegmentjumlah);

            horizontalkiri = itemView.findViewById(R.id.tablesegmenthorizontals);
            horizontalkanan = itemView.findViewById(R.id.tablesegmenthorizontal);

            vertikalkiri = itemView.findViewById(R.id.tablesegmentvertikals);
            vertikalkanan = itemView.findViewById(R.id.tablesegmentvertikal);




            itemView.setOnClickListener(this);


        }

        public void bindTO(DataSegmen current, String kiri, String kanan, String kml, String stal, String sub) {



            noseg.setText(sub);
            jumlahkiri.setText(kiri);
            jumlahkanan.setText(kanan);
            vertikalkiri.setText(helperTextValue.tipeVertikal(current.getVertikal()));
            vertikalkanan.setText(helperTextValue.tipeVertikal(current.getVertikal()));
            horizontalkiri.setText(helperTextValue.tipeHorizontaltable(current.getHorizontal()));
            horizontalkanan.setText(helperTextValue.tipeHorizontaltable(current.getHorizontal()));

            //kms.setText(kml);
            stas.setText(stal);



        }


        @Override
        public void onClick(View view) {

            session.saveSPInt(Session.SP_NOSEGMENT, mArrayList.get(getAdapterPosition()).getNosegment());
            session.saveSPInt(Session.SP_SUBSEGMENT, mArrayList.get(getAdapterPosition()).getSubsegment());
            session.saveSPString(Session.SP_NORUAS, mArrayList.get(getAdapterPosition()).getNoruas());
            session.saveSPString(Session.SP_KODEPROV, mArrayList.get(getAdapterPosition()).getNoprov());
            session.saveSPInt(Session.FOKUS, getAdapterPosition());

            if(session.getSurvey()==1){

                session.saveSPBoolean(Session.FLAG_TAB, true);
                session.saveSPInt(Session.TAB_ATRIBUT, AnimationTab.getPosisiAtribut("Segment"));

                Intent intent = new Intent(mContex, FormTabUtama.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContex.startActivity(intent);

            }else {

                Intent i = new Intent(mContex, MainFormTerusan.class);

                i.putExtra("tipe", "Segment");
                i.putExtra("posisi", "");
                i.putExtra("from", "Tabel");

                mContex.startActivity(i);
            }

        }
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
                case "tanah terbuka":
                    hasil = "1";
                    break;

                case "beton/pas batu terbuka":
                    hasil = "2";
                    break;

                case "saluran irigasi":
                    hasil = "3";
                    break;

                case "beton/pas batu tertutup":
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
            case "0" : hasil = "#FFFFFF";
                break;

            case "1" : hasil = "#FFC0CB";
                break;

            case "2" : hasil = "#FFFACD";
                break;

            case "3" : hasil = "#98FB98";
                break;

            case "4" : hasil = "#B0C4DE";
                break;

            case "5" : hasil = "#B344DE";
                break;

            case "6" : hasil = "#B387DE";
                break;

            case "7" : hasil = "#B349D8";
                break;

            case "8" : hasil = "#B3434E";
                break;

            case "9" : hasil = "#B344EB";
                break;

            case "10" : hasil = "#B544E";
                break;

            case "11" : hasil = "cfa";
                break;

            case "12" : hasil = "bcd";
                break;

            case "13" : hasil = "87a";
                break;

            default : hasil = "#FFFFFF";
        }

        return hasil;
    }
}
