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
import com.example.roadmanagement.kaltara.FormTerusan.MainFormTerusan;
import com.example.roadmanagement.kaltara.Interface.DataDrainase;
import com.example.roadmanagement.kaltara.Interface.SingleSegment;
import com.example.roadmanagement.kaltara.databaseHelper.DbSpinner;
import com.example.roadmanagement.kaltara.helper.HelperTextValue;
import com.example.roadmanagement.kaltara.helper.Session;

import java.util.ArrayList;

public class TableLerengAdapter extends RecyclerView.Adapter<TableLerengAdapter.ViewHolder> {
private ArrayList<DataDrainase> mArrayList;
private ArrayList<DataDrainase> mArrayListo;
private ArrayList<SingleSegment> kmku;
        HelperTextValue helperTextValue;
private Context mContex;
private Session session;
        DbSpinner dbSpinner;



public TableLerengAdapter(ArrayList<DataDrainase> mArrayList, ArrayList<DataDrainase> mArrayListo, ArrayList<SingleSegment> kmku, Context mContex) {
        this.mArrayList = mArrayList;
        this.mArrayListo = mArrayListo;
        this.kmku = kmku;
        this.mContex = mContex;

        helperTextValue = new HelperTextValue();

        session = new Session(mContex);
        dbSpinner = new DbSpinner(mContex);

        }

@Override
public TableLerengAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TableLerengAdapter.ViewHolder(LayoutInflater.from(mContex).inflate
        (R.layout.tabeltambahan, parent, false));
        }

@Override
public void onBindViewHolder(TableLerengAdapter.ViewHolder holder, int position) {

        DataDrainase current = mArrayList.get(position);
        DataDrainase currento = mArrayListo.get(position);
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
                session.saveSPInt(Session.SP_SUBSEGMENT, mArrayList.get(getAdapterPosition()).getSubsegment());
                session.saveSPInt(Session.SP_NOSEGMENT, mArrayList.get(getAdapterPosition()).getNosegment());
                session.saveSPString(Session.SP_NORUAS, mArrayList.get(getAdapterPosition()).getNoruas());
                session.saveSPString(Session.SP_KODEPROV, mArrayList.get(getAdapterPosition()).getNoprov());

                session.saveSPInt(Session.FOKUS, getAdapterPosition());


                Intent i = new Intent(mContex, MainFormTerusan.class);

                i.putExtra("tipe", "Saluran Lereng");
                i.putExtra("posisi", "kiri");
                i.putExtra("from", "Tabel");

                mContex.startActivity(i);
            }
        });

        boxKanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.saveSPInt(Session.SP_SUBSEGMENT, mArrayList.get(getAdapterPosition()).getSubsegment());
                session.saveSPInt(Session.SP_NOSEGMENT, mArrayList.get(getAdapterPosition()).getNosegment());
                session.saveSPString(Session.SP_NORUAS, mArrayList.get(getAdapterPosition()).getNoruas());
                session.saveSPString(Session.SP_KODEPROV, mArrayList.get(getAdapterPosition()).getNoprov());

                session.saveSPInt(Session.FOKUS, getAdapterPosition());


                Intent i = new Intent(mContex, MainFormTerusan.class);

                i.putExtra("tipe", "Saluran Lereng");
                i.putExtra("posisi", "kanan");
                i.putExtra("from", "Tabel");

                mContex.startActivity(i);

            }
        });

    }

    public void bindTO(DataDrainase current, DataDrainase currento, String stal, String tSubseg) {

        noseg.setText(tSubseg);
        sta.setText(stal);

        helperTextValue.setAtributBaruTabel(current.getJenisPenampang(), "b : "+current.getLebar()+" m", " h : "+current.getTinggi()+" m",
                textValueKiri, textKiri1, textKiri2);

        helperTextValue.setAtributBaruTabel(currento.getJenisPenampang(), "b : "+currento.getLebar()+" m", " h : "+currento.getTinggi()+" m",
                textValueKanan, textKanan1, textKanan2);


    }

}


    private String getWarna(String tipe) {

        String warna = "#FFFFFF";

        if(tipe!=null) {

            switch (tipe) {

                case "Trapesium":
                    warna = "#E06496";
                    break;

                case "Segitiga":
                    warna = "#738DF0";
                    break;

                case "Segiempat":
                    warna = "#FF00BCD4";
                    break;

                case "1/2 Lingkaran":
                    warna = "#FFECE288";
                    break;
            }
        }

        return warna;
    }

}
