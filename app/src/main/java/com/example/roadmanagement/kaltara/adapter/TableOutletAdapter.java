package com.example.roadmanagement.kaltara.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.FormTab.FormTabUtama;
import com.example.roadmanagement.kaltara.FormTab.Helper.AnimationTab;
import com.example.roadmanagement.kaltara.FormTerusan.MainFormTerusan;
import com.example.roadmanagement.kaltara.Interface.DataBahu;
import com.example.roadmanagement.kaltara.Interface.DataInletTrotoar;
import com.example.roadmanagement.kaltara.Interface.DataOutlet;
import com.example.roadmanagement.kaltara.Interface.SingleSegment;
import com.example.roadmanagement.kaltara.databaseHelper.DbSpinner;
import com.example.roadmanagement.kaltara.helper.HelperTextValue;
import com.example.roadmanagement.kaltara.helper.Session;

import java.util.ArrayList;

public class TableOutletAdapter extends RecyclerView.Adapter<TableOutletAdapter.ViewHolder> {
    private ArrayList<DataOutlet> mArrayList;
    private ArrayList<DataOutlet> mArrayListo;
    private ArrayList<SingleSegment> kmku;
    HelperTextValue helperTextValue;
    private Context mContex;
    private Session session;
    DbSpinner dbSpinner;



    public TableOutletAdapter(ArrayList<DataOutlet> mArrayList, ArrayList<DataOutlet> mArrayListo, ArrayList<SingleSegment> kmku, Context mContex) {
        this.mArrayList = mArrayList;
        this.mArrayListo = mArrayListo;
        this.kmku = kmku;
        this.mContex = mContex;

        helperTextValue = new HelperTextValue();

        session = new Session(mContex);
        dbSpinner = new DbSpinner(mContex);

    }

    @Override
    public TableOutletAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TableOutletAdapter.ViewHolder(LayoutInflater.from(mContex).inflate
                (R.layout.tabeltambahan, parent, false));
    }

    @Override
    public void onBindViewHolder(TableOutletAdapter.ViewHolder holder, int position) {

        DataOutlet current = mArrayList.get(position);
        DataOutlet currento = mArrayListo.get(position);
        String sta = kmku.get(position).getStaawal();
        String sub = kmku.get(position).getNoseg()+"."+kmku.get(position).getSubSegment();
        holder.bindTO(current, currento, sta, sub);
        holder.boxKiri.setCardBackgroundColor(Color.parseColor(getWarna(current.getJenisPenampang())));
        holder.boxKanan.setCardBackgroundColor(Color.parseColor(getWarna(currento.getJenisPenampang())));


    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView noseg, sta, textValueKiri, textKiri1, textKiri2,
                textValueKanan, textKanan1, textKanan2;

        CardView boxKiri;
        CardView boxKanan;



        public ViewHolder(View itemView) {
            super(itemView);

            noseg = itemView.findViewById(R.id.tabelTambahanSegment);
            sta = itemView.findViewById(R.id.tabelTambahanSta);

            boxKiri = itemView.findViewById(R.id.tabelTambahanKiri);
            textValueKiri = itemView.findViewById(R.id.tabelTambahanKiri2);
            textKiri1 = itemView.findViewById(R.id.tabelTambahanKiri1);
            textKiri2 = itemView.findViewById(R.id.tabelTambahanKiri3);

            boxKanan = itemView.findViewById(R.id.tabelTambahanKanan);
            textValueKanan = itemView.findViewById(R.id.tabelTambahanKanan2);
            textKanan1 = itemView.findViewById(R.id.tabelTambahanKanan1);
            textKanan2 = itemView.findViewById(R.id.tabelTambahanKanan3);



            boxKiri.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*session.saveSPInt(Session.SP_SUBSEGMENT, mArrayList.get(getAdapterPosition()).getSubsegment());
                    session.saveSPInt(Session.SP_NOSEGMENT, mArrayList.get(getAdapterPosition()).getNosegment());
                    session.saveSPString(Session.SP_NORUAS, mArrayList.get(getAdapterPosition()).getNoruas());
                    session.saveSPString(Session.SP_KODEPROV, mArrayList.get(getAdapterPosition()).getNoprov());

                    session.saveSPInt(Session.FOKUS, getAdapterPosition());


                    Intent i = new Intent(mContex, MainFormTerusan.class);

                    i.putExtra("tipe", "Outlet");
                    i.putExtra("posisi", "kiri");
                    i.putExtra("from", "Tabel");

                    mContex.startActivity(i);*/

                    onClickKu(mArrayList);
                }
            });

            boxKanan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*session.saveSPInt(Session.SP_SUBSEGMENT, mArrayList.get(getAdapterPosition()).getSubsegment());
                    session.saveSPInt(Session.SP_NOSEGMENT, mArrayList.get(getAdapterPosition()).getNosegment());
                    session.saveSPString(Session.SP_NORUAS, mArrayList.get(getAdapterPosition()).getNoruas());
                    session.saveSPString(Session.SP_KODEPROV, mArrayList.get(getAdapterPosition()).getNoprov());

                    session.saveSPInt(Session.FOKUS, getAdapterPosition());


                    Intent i = new Intent(mContex, MainFormTerusan.class);

                    i.putExtra("tipe", "Outlet");
                    i.putExtra("posisi", "kanan");
                    i.putExtra("from", "Tabel");

                    mContex.startActivity(i);*/

                    onClickKu(mArrayListo);

                }
            });

        }

        private void onClickKu(ArrayList<DataOutlet> arrayList) {

            session.saveSPInt(Session.SP_NOSEGMENT, arrayList.get(getAdapterPosition()).getNosegment());
            session.saveSPInt(Session.SP_SUBSEGMENT, arrayList.get(getAdapterPosition()).getSubsegment());
            session.saveSPString(Session.SP_NORUAS, arrayList.get(getAdapterPosition()).getNoruas());
            session.saveSPString(Session.SP_KODEPROV, arrayList.get(getAdapterPosition()).getNoprov());

            session.saveSPInt(Session.FOKUS, getAdapterPosition());
            String posisi = arrayList.get(getAdapterPosition()).getPosisi();

            if (session.getSurvey() == 1) {

                session.saveSPBoolean(Session.FLAG_TAB, true);
                session.saveSPString(Session.POSISI, posisi);
                session.saveSPInt(Session.TAB_ATRIBUT, AnimationTab.getPosisiAtribut("Outlet"));

                Intent intent = new Intent(mContex, FormTabUtama.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContex.startActivity(intent);

            } else {

                Intent i = new Intent(mContex, MainFormTerusan.class);
                i.putExtra("tipe", "Outlet");
                i.putExtra("posisi", posisi);
                i.putExtra("from", "Tabel");

                mContex.startActivity(i);

            }
        }

        public void bindTO(DataOutlet current, DataOutlet currento, String stal, String tSubseg) {

            noseg.setText(tSubseg);
            sta.setText(stal);

            helperTextValue.setAtributOutletTabel(current.getJenisPenampang(), "d : "+current.getDiameter()+" m", "b : "+current.getLebar()+" m", "h : "+current.getTinggi()+" m",
                    textValueKiri, textKiri1, textKiri2);

            helperTextValue.setAtributOutletTabel(currento.getJenisPenampang(), "d : "+currento.getDiameter()+" m", "b : "+currento.getLebar()+" m", "h : "+currento.getTinggi()+" m",
                    textValueKanan, textKanan1, textKanan2);


        }

    }


    private String getWarna(String tipe) {

        String warna = "#FFFFFF";

        if(tipe!=null) {

            switch (tipe) {

                case "Lingkaran":
                    warna = "#E06496";
                    break;

                case "Segiempat":
                    warna = "#738DF0";
                    break;


            }
        }

        return warna;
    }

}
