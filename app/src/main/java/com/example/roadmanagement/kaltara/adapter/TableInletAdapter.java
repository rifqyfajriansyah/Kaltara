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
import com.example.roadmanagement.kaltara.Interface.SingleSegment;
import com.example.roadmanagement.kaltara.databaseHelper.DbSpinner;
import com.example.roadmanagement.kaltara.helper.HelperTextValue;
import com.example.roadmanagement.kaltara.helper.Session;

import java.util.ArrayList;

public class TableInletAdapter extends RecyclerView.Adapter<TableInletAdapter.ViewHolder> {
    private ArrayList<DataInletTrotoar> mArrayList;
    private ArrayList<DataInletTrotoar> mArrayListo;
    private ArrayList<SingleSegment> kmku;
    HelperTextValue helperTextValue;
    private Context mContex;
    private Session session;
    DbSpinner dbSpinner;



    public TableInletAdapter(ArrayList<DataInletTrotoar> mArrayList, ArrayList<DataInletTrotoar> mArrayListo, ArrayList<SingleSegment> kmku, Context mContex) {
        this.mArrayList = mArrayList;
        this.mArrayListo = mArrayListo;
        this.kmku = kmku;
        this.mContex = mContex;

        helperTextValue = new HelperTextValue();

        session = new Session(mContex);
        dbSpinner = new DbSpinner(mContex);

    }

    @Override
    public TableInletAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TableInletAdapter.ViewHolder(LayoutInflater.from(mContex).inflate
                (R.layout.tabeltambahan, parent, false));
    }

    @Override
    public void onBindViewHolder(TableInletAdapter.ViewHolder holder, int position) {

        DataInletTrotoar current = mArrayList.get(position);
        DataInletTrotoar currento = mArrayListo.get(position);
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

                    i.putExtra("tipe", "Inlet ditrotoar");
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

                    i.putExtra("tipe", "Inlet ditrotoar");
                    i.putExtra("posisi", "kanan");
                    i.putExtra("from", "Tabel");

                    mContex.startActivity(i);*/
                    onClickKu(mArrayListo);

                }
            });

        }

        private void onClickKu(ArrayList<DataInletTrotoar> arrayList) {

            session.saveSPInt(Session.SP_NOSEGMENT, arrayList.get(getAdapterPosition()).getNosegment());
            session.saveSPInt(Session.SP_SUBSEGMENT, arrayList.get(getAdapterPosition()).getSubsegment());
            session.saveSPString(Session.SP_NORUAS, arrayList.get(getAdapterPosition()).getNoruas());
            session.saveSPString(Session.SP_KODEPROV, arrayList.get(getAdapterPosition()).getNoprov());

            session.saveSPInt(Session.FOKUS, getAdapterPosition());
            String posisi = arrayList.get(getAdapterPosition()).getPosisi();

            if (session.getSurvey() == 1) {

                session.saveSPBoolean(Session.FLAG_TAB, true);
                session.saveSPString(Session.POSISI, posisi);
                session.saveSPInt(Session.TAB_ATRIBUT, AnimationTab.getPosisiAtribut("Inlet"));

                Intent intent = new Intent(mContex, FormTabUtama.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContex.startActivity(intent);

            } else {

                Intent i = new Intent(mContex, MainFormTerusan.class);
                i.putExtra("tipe", "Inlet ditrotoar");
                i.putExtra("posisi", posisi);
                i.putExtra("from", "Tabel");

                mContex.startActivity(i);

            }
        }

        public void bindTO(DataInletTrotoar current, DataInletTrotoar currento, String stal, String tSubseg) {

            noseg.setText(tSubseg);
            sta.setText(stal);

            helperTextValue.setAtributInletTabel(current.getJenisPenampang(), "L : "+current.getPanjang()+" m", " h : "+current.getTinggi()+" m"," W : "+current.getLebar()+" m",
                    textValueKiri, textKiri1, textKiri2);

            helperTextValue.setAtributInletTabel(currento.getJenisPenampang(), "L : "+currento.getPanjang()+" m", " h : "+currento.getTinggi()+" m"," W : "+currento.getLebar()+" m",
                    textValueKanan, textKanan1, textKanan2);



        }

    }


    private String getWarna(String tipe) {

        String warna = "#FFFFFF";

        if(tipe!=null) {

            switch (tipe) {

                case "Curb":
                    warna = "#E06496";
                    break;

                case "Gutter":
                    warna = "#738DF0";
                    break;

                case "Combination":
                    warna = "#FF00BCD4";
                    break;

            }
        }

        return warna;
    }

}
