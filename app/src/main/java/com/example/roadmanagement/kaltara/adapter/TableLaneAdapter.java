package com.example.roadmanagement.kaltara.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.FormTab.FormTabUtama;
import com.example.roadmanagement.kaltara.FormTab.Helper.AnimationTab;
import com.example.roadmanagement.kaltara.FormTerusan.MainFormTerusan;
import com.example.roadmanagement.kaltara.Interface.DataCrossDrain;
import com.example.roadmanagement.kaltara.Interface.DataLane;
import com.example.roadmanagement.kaltara.Interface.SingleSegment;
import com.example.roadmanagement.kaltara.databaseHelper.DbLane;
import com.example.roadmanagement.kaltara.databaseHelper.DbSpinner;
import com.example.roadmanagement.kaltara.helper.HelperTextValue;
import com.example.roadmanagement.kaltara.helper.Session;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class TableLaneAdapter extends RecyclerView.Adapter<TableLaneAdapter.ViewHolder> {
    private ArrayList<DataLane> mArrayListl1;
    private ArrayList<DataLane> mArrayListl2;
    private ArrayList<DataLane> mArrayListl3;
    private ArrayList<DataLane> mArrayListl4;
    private ArrayList<DataLane> mArrayListl5;
    private ArrayList<DataLane> mArrayListl6;
    private ArrayList<DataLane> mArrayListl7;
    private ArrayList<DataLane> mArrayListl8;
    private ArrayList<DataLane> mArrayListl9;
    private ArrayList<DataLane> mArrayListl10;

    private ArrayList<DataLane> mArrayListr1;
    private ArrayList<DataLane> mArrayListr2;
    private ArrayList<DataLane> mArrayListr3;
    private ArrayList<DataLane> mArrayListr4;
    private ArrayList<DataLane> mArrayListr5;
    private ArrayList<DataLane> mArrayListr6;
    private ArrayList<DataLane> mArrayListr7;
    private ArrayList<DataLane> mArrayListr8;
    private ArrayList<DataLane> mArrayListr9;
    private ArrayList<DataLane> mArrayListr10;

    private ArrayList<SingleSegment> kmku;
    private Context mContex;
    private Session session;

    DbSpinner dbSpinner;
    DbLane dbLane;

    int maxkiri;
    int maxkanan;

    HelperTextValue helperTextValue;

    int i=1;


    public TableLaneAdapter(ArrayList<DataLane> mArrayListl1, ArrayList<DataLane> mArrayListl2, ArrayList<DataLane> mArrayListl3, ArrayList<DataLane> mArrayListl4, ArrayList<DataLane> mArrayListl5, ArrayList<DataLane> mArrayListl6, ArrayList<DataLane> mArrayListl7, ArrayList<DataLane> mArrayListl8, ArrayList<DataLane> mArrayListl9, ArrayList<DataLane> mArrayListl10,
                            ArrayList<DataLane> mArrayListr1, ArrayList<DataLane> mArrayListr2, ArrayList<DataLane> mArrayListr3, ArrayList<DataLane> mArrayListr4, ArrayList<DataLane> mArrayListr5, ArrayList<DataLane> mArrayListr6, ArrayList<DataLane> mArrayListr7, ArrayList<DataLane> mArrayListr8, ArrayList<DataLane> mArrayListr9, ArrayList<DataLane> mArrayListr10,
                            ArrayList<SingleSegment> kmku, Context mContex, int maxkiri, int maxkanan) {

        this.mArrayListl1 = mArrayListl1;
        this.mArrayListl2 = mArrayListl2;
        this.mArrayListl3 = mArrayListl3;
        this.mArrayListl4 = mArrayListl4;
        this.mArrayListl5 = mArrayListl5;
        this.mArrayListl6 = mArrayListl6;
        this.mArrayListl7 = mArrayListl7;
        this.mArrayListl8 = mArrayListl8;
        this.mArrayListl9 = mArrayListl9;
        this.mArrayListl10 = mArrayListl10;

        this.mArrayListr1 = mArrayListr1;
        this.mArrayListr2 = mArrayListr2;
        this.mArrayListr3 = mArrayListr3;
        this.mArrayListr4 = mArrayListr4;
        this.mArrayListr5 = mArrayListr5;
        this.mArrayListr6 = mArrayListr6;
        this.mArrayListr7 = mArrayListr7;
        this.mArrayListr8 = mArrayListr8;
        this.mArrayListr9 = mArrayListr9;
        this.mArrayListr10 = mArrayListr10;

        helperTextValue = new HelperTextValue();


        this.kmku = kmku;
        this.mContex = mContex;

        this.maxkiri = maxkiri;
        this.maxkanan = maxkanan;

        dbSpinner = new DbSpinner(mContex);


        session = new Session(mContex);

        dbLane = new DbLane(mContex);

    }

    @Override
    public TableLaneAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TableLaneAdapter.ViewHolder(LayoutInflater.from(mContex).inflate
                (R.layout.tabelane, parent, false));
    }

    @Override
    public void onBindViewHolder(TableLaneAdapter.ViewHolder holder, int position) {

        DataLane currentl1 = Kondisi(mArrayListl1, position);
        DataLane currentl2 = Kondisi(mArrayListl2, position);
        DataLane currentl3 = Kondisi(mArrayListl3, position);
        DataLane currentl4 = Kondisi(mArrayListl4, position);
        DataLane currentl5 = Kondisi(mArrayListl5, position);
        DataLane currentl6 = Kondisi(mArrayListl6, position);
        DataLane currentl7 = Kondisi(mArrayListl7, position);
        DataLane currentl8 = Kondisi(mArrayListl8, position);
        DataLane currentl9 = Kondisi(mArrayListl9, position);
        DataLane currentl10 = Kondisi(mArrayListl10, position);

        DataLane currentr1 = Kondisi(mArrayListr1, position);
        DataLane currentr2 = Kondisi(mArrayListr2, position);
        DataLane currentr3 = Kondisi(mArrayListr3, position);
        DataLane currentr4 = Kondisi(mArrayListr4, position);
        DataLane currentr5 = Kondisi(mArrayListr5, position);
        DataLane currentr6 = Kondisi(mArrayListr6, position);
        DataLane currentr7 = Kondisi(mArrayListr7, position);
        DataLane currentr8 = Kondisi(mArrayListr8, position);
        DataLane currentr9 = Kondisi(mArrayListr9, position);
        DataLane currentr10 = Kondisi(mArrayListr10, position);

        String kmaw = kmku.get(position).getKmawal();
        String staw = kmku.get(position).getStaawal();

        String sub = kmku.get(position).getNoseg()+"."+kmku.get(position).getSubSegment();

        holder.bindTO(currentl1, currentl2, currentl3, currentl4, currentl5, currentl6, currentl7, currentl8, currentl9, currentl10,
                currentr1, currentr2, currentr3, currentr4, currentr5, currentr6, currentr7, currentr8, currentr9, currentr10,
                kmaw, staw,position+1, sub);

        setValueLane(currentl1, holder.l1);
        setValueLane(currentl2, holder.l2);
        setValueLane(currentl3, holder.l3);
        setValueLane(currentl4, holder.l4);
        setValueLane(currentl5, holder.l5);
        setValueLane(currentl6, holder.l6);
        setValueLane(currentl7, holder.l7);
        setValueLane(currentl8, holder.l8);
        setValueLane(currentl9, holder.l9);
        setValueLane(currentl10, holder.l10);

        setValueLane(currentr1, holder.r1);
        setValueLane(currentr2, holder.r2);
        setValueLane(currentr3, holder.r3);
        setValueLane(currentr4, holder.r4);
        setValueLane(currentr5, holder.r5);
        setValueLane(currentr6, holder.r6);
        setValueLane(currentr7, holder.r7);
        setValueLane(currentr8, holder.r8);
        setValueLane(currentr9, holder.r9);
        setValueLane(currentr10, holder.r10);

        setRowLane(maxkanan, holder.r1, holder.r2, holder.r3, holder.r4, holder.r5, holder.r6, holder.r7, holder.r8, holder.r9, holder.r10);
        setRowLane(maxkiri, holder.l1, holder.l2, holder.l3, holder.l4, holder.l5, holder.l6, holder.l7, holder.l8, holder.l9, holder.l10);
    }

    @Override
    public int getItemCount() {
        return kmku.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView noseg;
        TextView kml;
        TextView stal;

        TextView lebarr1;
        TextView scr1;
        TextView lebarr2;
        TextView scr2;
        TextView lebarr3;
        TextView scr3;
        TextView lebarr4;
        TextView scr4;
        TextView lebarr5;
        TextView scr5;
        TextView lebarr6;
        TextView scr6;
        TextView lebarr7;
        TextView scr7;
        TextView lebarr8;
        TextView scr8;
        TextView lebarr9;
        TextView scr9;
        TextView lebarr10;
        TextView scr10;


        TextView lebarl1;
        TextView scl1;
        TextView lebarl2;
        TextView scl2;
        TextView lebarl3;
        TextView scl3;
        TextView lebarl4;
        TextView scl4;
        TextView lebarl5;
        TextView scl5;
        TextView lebarl6;
        TextView scl6;
        TextView lebarl7;
        TextView scl7;
        TextView lebarl8;
        TextView scl8;
        TextView lebarl9;
        TextView scl9;
        TextView lebarl10;
        TextView scl10;

        CardView l1;
        CardView l2;
        CardView l3;
        CardView l4;
        CardView l5;
        CardView l6;
        CardView l7;
        CardView l8;
        CardView l9;
        CardView l10;

        CardView r1;
        CardView r2;
        CardView r3;
        CardView r4;
        CardView r5;
        CardView r6;
        CardView r7;
        CardView r8;
        CardView r9;
        CardView r10;


        public ViewHolder(View itemView) {
            super(itemView);
            noseg = itemView.findViewById(R.id.tablelanesegment);
            kml = itemView.findViewById(R.id.tablelanekm);
            stal = itemView.findViewById(R.id.tablelanesta);
            lebarr1 = itemView.findViewById(R.id.tabler1tinggi);
            scr1 = itemView.findViewById(R.id.tabler1sc);

            lebarr2 = itemView.findViewById(R.id.tabler2tinggi);
            scr2 = itemView.findViewById(R.id.tabler2sc);

            lebarr3 = itemView.findViewById(R.id.tabler3tinggi);
            scr3 = itemView.findViewById(R.id.tabler3sc);

            lebarr4 = itemView.findViewById(R.id.tabler4tinggi);
            scr4 = itemView.findViewById(R.id.tabler4sc);

            lebarr5 = itemView.findViewById(R.id.tabler5tinggi);
            scr5 = itemView.findViewById(R.id.tabler5sc);

            lebarr6 = itemView.findViewById(R.id.tabler6tinggi);
            scr6 = itemView.findViewById(R.id.tabler6sc);

            lebarr7 = itemView.findViewById(R.id.tabler7tinggi);
            scr7 = itemView.findViewById(R.id.tabler7sc);

            lebarr8 = itemView.findViewById(R.id.tabler8tinggi);
            scr8 = itemView.findViewById(R.id.tabler8sc);

            lebarr9 = itemView.findViewById(R.id.tabler9tinggi);
            scr9 = itemView.findViewById(R.id.tabler9sc);

            lebarr10 = itemView.findViewById(R.id.tabler10tinggi);
            scr10 = itemView.findViewById(R.id.tabler10sc);



            lebarl1 = itemView.findViewById(R.id.tablel1tinggi);
            scl1 = itemView.findViewById(R.id.tablel1sc);

            lebarl2 = itemView.findViewById(R.id.tablel2tinggi);
            scl2 = itemView.findViewById(R.id.tablel2sc);

            lebarl3 = itemView.findViewById(R.id.tablel3tinggi);
            scl3 = itemView.findViewById(R.id.tablel3sc);

            lebarl4 = itemView.findViewById(R.id.tablel4tinggi);
            scl4 = itemView.findViewById(R.id.tablel4sc);

            lebarl5 = itemView.findViewById(R.id.tablel5tinggi);
            scl5 = itemView.findViewById(R.id.tablel5sc);

            lebarl6 = itemView.findViewById(R.id.tablel6tinggi);
            scl6 = itemView.findViewById(R.id.tablel6sc);

            lebarl7 = itemView.findViewById(R.id.tablel7tinggi);
            scl7 = itemView.findViewById(R.id.tablel7sc);

            lebarl8 = itemView.findViewById(R.id.tablel8tinggi);
            scl8 = itemView.findViewById(R.id.tablel8sc);

            lebarl9 = itemView.findViewById(R.id.tablel9tinggi);
            scl9 = itemView.findViewById(R.id.tablel9sc);

            lebarl10 = itemView.findViewById(R.id.tablel10tinggi);
            scl10 = itemView.findViewById(R.id.tablel10sc);


            l1=itemView.findViewById(R.id.tablel1);
            l2=itemView.findViewById(R.id.tablel2);
            l3=itemView.findViewById(R.id.tablel3);
            l4=itemView.findViewById(R.id.tablel4);
            l5=itemView.findViewById(R.id.tablel5);
            l6=itemView.findViewById(R.id.tablel6);
            l7=itemView.findViewById(R.id.tablel7);
            l8=itemView.findViewById(R.id.tablel8);
            l9=itemView.findViewById(R.id.tablel9);
            l10=itemView.findViewById(R.id.tablel10);

            r1=itemView.findViewById(R.id.tabler1);
            r2=itemView.findViewById(R.id.tabler2);
            r3=itemView.findViewById(R.id.tabler3);
            r4=itemView.findViewById(R.id.tabler4);
            r5=itemView.findViewById(R.id.tabler5);
            r6=itemView.findViewById(R.id.tabler6);
            r7=itemView.findViewById(R.id.tabler7);
            r8=itemView.findViewById(R.id.tabler8);
            r9=itemView.findViewById(R.id.tabler9);
            r10=itemView.findViewById(R.id.tabler10);


            l1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*int segment = Integer.valueOf(kmku.get(getAdapterPosition()).getNoseg());
                    int subsegment = kmku.get(getAdapterPosition()).getSubSegment();
                    Intent i = setClick(getDataLane(segment, subsegment,"kiri", "L1"), getAdapterPosition());
                    mContex.startActivity(i);*/
                    onClickKu(mArrayListl1);
                }
            });

            l2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*int segment = Integer.valueOf(kmku.get(getAdapterPosition()).getNoseg());
                    int subsegment = kmku.get(getAdapterPosition()).getSubSegment();
                    Intent i = setClick(getDataLane(segment, subsegment,"kiri", "L2"), getAdapterPosition());
                    mContex.startActivity(i);*/
                    onClickKu(mArrayListl2);
                }
            });

            l3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*int segment = Integer.valueOf(kmku.get(getAdapterPosition()).getNoseg());
                    int subsegment = kmku.get(getAdapterPosition()).getSubSegment();
                    Intent i = setClick(getDataLane(segment, subsegment,"kiri", "L3"), getAdapterPosition());
                    mContex.startActivity(i);*/

                    onClickKu(mArrayListl3);
                }
            });

            l4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*int subsegment = kmku.get(getAdapterPosition()).getSubSegment();
                    int segment = Integer.valueOf(kmku.get(getAdapterPosition()).getNoseg());
                    Intent i = setClick(getDataLane(segment, subsegment,"kiri", "L4"), getAdapterPosition());
                    mContex.startActivity(i);*/
                    onClickKu(mArrayListl4);
                }
            });

            l5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*int subsegment = kmku.get(getAdapterPosition()).getSubSegment();
                    int segment = Integer.valueOf(kmku.get(getAdapterPosition()).getNoseg());
                    Intent i = setClick(getDataLane(segment, subsegment,"kiri", "L5"), getAdapterPosition());
                    mContex.startActivity(i);*/
                    onClickKu(mArrayListl5);
                }
            });

            l6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*int subsegment = kmku.get(getAdapterPosition()).getSubSegment();
                    int segment = Integer.valueOf(kmku.get(getAdapterPosition()).getNoseg());
                    Intent i = setClick(getDataLane(segment, subsegment,"kiri", "L6"), getAdapterPosition());
                    mContex.startActivity(i);*/

                    onClickKu(mArrayListl6);
                }
            });

            l7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*int subsegment = kmku.get(getAdapterPosition()).getSubSegment();
                    int segment = Integer.valueOf(kmku.get(getAdapterPosition()).getNoseg());
                    Intent i = setClick(getDataLane(segment, subsegment,"kiri", "L7"), getAdapterPosition());
                    mContex.startActivity(i);*/

                    onClickKu(mArrayListl7);
                }
            });

            l8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*int subsegment = kmku.get(getAdapterPosition()).getSubSegment();
                    int segment = Integer.valueOf(kmku.get(getAdapterPosition()).getNoseg());
                    Intent i = setClick(getDataLane(segment, subsegment,"kiri", "L8"), getAdapterPosition());
                    mContex.startActivity(i);*/

                    onClickKu(mArrayListl8);
                }
            });

            l9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*int subsegment = kmku.get(getAdapterPosition()).getSubSegment();
                    int segment = Integer.valueOf(kmku.get(getAdapterPosition()).getNoseg());
                    Intent i = setClick(getDataLane(segment, subsegment,"kiri", "L9"), getAdapterPosition());
                    mContex.startActivity(i);*/

                    onClickKu(mArrayListl9);
                }
            });

            l10.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*int subsegment = kmku.get(getAdapterPosition()).getSubSegment();
                    int segment = Integer.valueOf(kmku.get(getAdapterPosition()).getNoseg());
                    Intent i = setClick(getDataLane(segment, subsegment,"kiri", "L10"), getAdapterPosition());
                    mContex.startActivity(i);*/

                    onClickKu(mArrayListl10);
                }
            });




            r1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickKu(mArrayListr1);
                }
            });

            r2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickKu(mArrayListr2);
                }
            });

            r3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickKu(mArrayListr3);
                }
            });

            r4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickKu(mArrayListr4);
                }
            });

            r5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickKu(mArrayListr5);
                }
            });

            r6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickKu(mArrayListr6);
                }
            });

            r7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickKu(mArrayListr7);
                }
            });

            r8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickKu(mArrayListr8);
                }
            });

            r9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickKu(mArrayListr9);
                }
            });

            r10.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickKu(mArrayListr10);
                }
            });




        }

        public void bindTO(DataLane currentl1, DataLane currentl2, DataLane currentl3, DataLane currentl4, DataLane currentl5, DataLane currentl6, DataLane currentl7, DataLane currentl8, DataLane currentl9, DataLane currentl10,
                           DataLane currentr1, DataLane currentr2, DataLane currentr3, DataLane currentr4, DataLane currentr5, DataLane currentr6, DataLane currentr7, DataLane currentr8, DataLane currentr9, DataLane currentr10,
                           String kma, String staa, int segment, String tSub) {
            noseg.setText(tSub);
            //kml.setText(kma);
            stal.setText(staa);

            if(currentl1!=null) {
                scl1.setText(helperTextValue.tipeLane(currentl1.getSc1()));
                lebarl1.setText(String.valueOf(currentl1.getLebarLane()));
            }

            if(currentl2!=null){
                scl2.setText(helperTextValue.tipeLane(currentl2.getSc1()));
                lebarl2.setText(String.valueOf(currentl2.getLebarLane()));
            }

            if(currentl3!=null){
                scl3.setText(helperTextValue.tipeLane(currentl3.getSc1()));
                lebarl3.setText(String.valueOf(currentl3.getLebarLane()));
            }

            if(currentl4!=null){
                scl4.setText(helperTextValue.tipeLane(currentl4.getSc1()));
                lebarl4.setText(String.valueOf(currentl4.getLebarLane()));
            }

            if(currentl5!=null) {
                scl5.setText(helperTextValue.tipeLane(currentl5.getSc1()));
                lebarl5.setText(String.valueOf(currentl5.getLebarLane()));
            }

            if(currentl6!=null){
                scl6.setText(helperTextValue.tipeLane(currentl6.getSc1()));
                lebarl6.setText(String.valueOf(currentl6.getLebarLane()));
            }

            if(currentl7!=null){
                scl7.setText(helperTextValue.tipeLane(currentl7.getSc1()));
                lebarl7.setText(String.valueOf(currentl7.getLebarLane()));
            }

            if(currentl8!=null){
                scl8.setText(helperTextValue.tipeLane(currentl8.getSc1()));
                lebarl8.setText(String.valueOf(currentl8.getLebarLane()));
            }

            if(currentl9!=null) {
                scl9.setText(helperTextValue.tipeLane(currentl9.getSc1()));
                lebarl9.setText(String.valueOf(currentl9.getLebarLane()));
            }

            if(currentl10!=null){
                scl10.setText(helperTextValue.tipeLane(currentl10.getSc1()));
                lebarl10.setText(String.valueOf(currentl10.getLebarLane()));
            }



            if(currentr1!=null) {

                scr1.setText(helperTextValue.tipeLane(currentr1.getSc1()));
                lebarr1.setText(String.valueOf(currentr1.getLebarLane()));
            }

            if(currentr2!=null){
                scr2.setText(helperTextValue.tipeLane(currentr2.getSc1()));
                lebarr2.setText(String.valueOf(currentr2.getLebarLane()));
            }

            if(currentr3!=null){
                scr3.setText(helperTextValue.tipeLane(currentr3.getSc1()));
                lebarr3.setText(String.valueOf(currentr3.getLebarLane()));
            }

            if(currentr4!=null){
                scr4.setText(helperTextValue.tipeLane(currentr4.getSc1()));
                lebarr4.setText(String.valueOf(currentr4.getLebarLane()));
            }

            if(currentr5!=null) {

                scr5.setText(helperTextValue.tipeLane(currentr5.getSc1()));
                lebarr5.setText(String.valueOf(currentr5.getLebarLane()));
            }

            if(currentr6!=null){
                scr6.setText(helperTextValue.tipeLane(currentr6.getSc1()));
                lebarr6.setText(String.valueOf(currentr6.getLebarLane()));
            }

            if(currentr7!=null){
                scr7.setText(helperTextValue.tipeLane(currentr7.getSc1()));
                lebarr7.setText(String.valueOf(currentr7.getLebarLane()));
            }

            if(currentr8!=null){
                scr8.setText(helperTextValue.tipeLane(currentr8.getSc1()));
                lebarr8.setText(String.valueOf(currentr8.getLebarLane()));
            }

            if(currentr9!=null) {

                scr9.setText(helperTextValue.tipeLane(currentr9.getSc1()));
                lebarr9.setText(String.valueOf(currentr9.getLebarLane()));
            }

            if(currentr10!=null){
                scr10.setText(helperTextValue.tipeLane(currentr10.getSc1()));
                lebarr10.setText(String.valueOf(currentr10.getLebarLane()));
            }



            //scr4.setText(current.getSc());
            //lebarr4.setText(String.valueOf(current.getLebarLane()));


        }

        private void onClickKu(ArrayList<DataLane> arrayList) {

            DataLane dataLaneClick = getPositionLane(arrayList, getAdapterPosition());

            //session.saveSPInt(Session.SP_NOSEGMENT, arrayList.get(getAdapterPosition()).getNosegment());
            //session.saveSPInt(Session.SP_SUBSEGMENT, arrayList.get(getAdapterPosition()).getSubsegment());
            //session.saveSPString(Session.SP_NORUAS, arrayList.get(getAdapterPosition()).getNoruas());
            //session.saveSPString(Session.SP_KODEPROV, arrayList.get(getAdapterPosition()).getNoprov());

            session.saveSPInt(Session.SP_NOSEGMENT, dataLaneClick.getNosegment());
            session.saveSPInt(Session.SP_SUBSEGMENT, dataLaneClick.getSubsegment());
            session.saveSPString(Session.SP_NORUAS, dataLaneClick.getNoruas());
            session.saveSPString(Session.SP_KODEPROV, dataLaneClick.getNoprov());

            session.saveSPInt(Session.FOKUS, getAdapterPosition());
            String posisi = dataLaneClick.getPosisi();
            String lajur = dataLaneClick.getUrut();

            int indexLajur = Integer.valueOf(lajur.substring(1));

            if (session.getSurvey() == 1) {

                session.saveSPBoolean(Session.FLAG_TAB, true);
                session.saveSPString(Session.POSISI, posisi);
                session.saveSPInt(Session.TAB_ATRIBUT, AnimationTab.getPosisiAtribut("Lane"));
                session.saveSPInt(Session.TAB_ATRIBUT_LANE, indexLajur);

                Intent intent = new Intent(mContex, FormTabUtama.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContex.startActivity(intent);

            } else {

                Intent i = new Intent(mContex, MainFormTerusan.class);
                i.putExtra("tipe", "Lane");
                i.putExtra("posisi", lajur);
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
            Intent i = new Intent(mContex, DetailLane.class);
            if(mArrayList.get(getAdapterPosition()).getPosisi().equals("kiri")) {
                judul = "Left";
            }else{
                judul = "Right";
            }

            i.putExtra("judul", mArrayList.get(getAdapterPosition()).getUrut());
            i.putExtra("dari","tabel");
            i.putExtra("letak", judul);
            mContex.startActivity(i);


        }*/
    }

   /* private String getWarna(String warna){
        String warnanya = "#fff000";
        String warnaku = null;

        String[] pisah= warna.split(Pattern.quote("."));

        if(Integer.valueOf(pisah[0])>99){

            warnaku = pisah[0];

        }else if(Integer.valueOf(pisah[0])>9){
            warnaku = pisah[1]+pisah[0];
        }else if(Integer.valueOf(pisah[0])>0){
            warnaku=pisah[0]+"f"+pisah[1];
        }else if(Integer.valueOf(pisah[1])>0){
            warnaku=pisah[1]+"f"+pisah[1];
        }else{
            warnaku="fff";
        }
        warnanya = "#"+warnaku+"fff";

        return warnanya;

        private DataLane Kondisi(ArrayList<DataLane> list, int index){

        DataLane dataku = null;

        if(list.size()>0) {

            int segment = list.get(index).getNosegment();
            int subsegment = list.get(index).getSubsegment();

            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getNosegment() == segment && list.get(i).getSubsegment()==subsegment) {

                    dataku = list.get(i);

                }
            }
        }



        return dataku;
    }
    }*/


    private DataLane Kondisi(ArrayList<DataLane> list, int index){

        DataLane dataku = null;

        if(list.size()>0) {

            int segment = Integer.valueOf(kmku.get(index).getNoseg());
            int subsegment = kmku.get(index).getSubSegment();

            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getNosegment() == segment && list.get(i).getSubsegment()==subsegment) {

                    dataku = list.get(i);

                }
            }
        }



        return dataku;
    }
/*
    private DataLane Kondisi(SingleSegment singleSegment, String posisi, String lajur){

        DataLane dataku = dbLane.getLane(singleSegment.getNoprov(), singleSegment.getNoruas(), Integer.valueOf(singleSegment.getNoseg()), singleSegment.getSubSegment(), posisi, lajur);
        return dataku;
    }*/

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

            warnanya="#"+pisah[1]+pisah[0]+getkodewarna(value);

        }else if(Integer.valueOf(pisah[1])>0){

            warnanya="#"+getkodewarna(pisah[1])+getkodewarna(value);

        }



        return warnanya;
    }

    private String getValuetipe(String value){
        String hasil = null;

        if(value!=null) {
            switch (value) {
                case "Unpaved/Tanah":
                    hasil = "1";
                    break;

                case "Japat(Awcas)/Kerikil":
                    hasil = "2";
                    break;

                case "Telford/Macadam Terbuka":
                    hasil = "3";
                    break;

                case "Burtu":
                    hasil = "4";
                    break;

                case "Burda":
                    hasil = "5";
                    break;

                case "Penetrasi Macadam 1 Lapis":
                    hasil = "6";
                    break;

                case "Penetrasi Macadam 2 Lapis":
                    hasil = "7";
                    break;

                case "Lasbutag (Butas)":
                    hasil = "8";
                    break;

                case "Aspal/Beton (AC)":
                    hasil = "9";
                    break;

                case "latasbum (nacas)":
                    hasil = "10";
                    break;

                case "lataston (hrs)":
                    hasil = "11";
                    break;

                case "HRSSA":
                    hasil = "12";
                    break;

                case "Slurry Seal":
                    hasil = "13";
                    break;

                case "Macro Seal":
                    hasil = "14";
                    break;

                case "Micro Asbuton":
                    hasil = "15";
                    break;

                case "DGEM":
                    hasil = "16";
                    break;

                case "SMA":
                    hasil = "17";
                    break;

                case "BMA":
                    hasil = "18";
                    break;

                case "HSWC":
                    hasil = "19";
                    break;

                case "SPAV":
                    hasil = "20";
                    break;

                case "Concrete/Rigid":
                    hasil = "21";
                    break;

                case "FLEXIBLE":
                    hasil = "22";
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

            case "13" : hasil = "abc";
                break;

            case "14" : hasil = "217";
                break;

            case "15" : hasil = "aa1";
                break;

            case "16" : hasil = "c92";
                break;

            case "17" : hasil = "532";
                break;

            case "18" : hasil = "240";
                break;

            case "19" : hasil = "145";
                break;

            case "20" : hasil = "b87";
                break;

            case "21" : hasil = "b09";
                break;

            case "22" : hasil = "a29";
                break;

            default : hasil = "fff";
        }

        return hasil;
    }

    private void setValueLane(DataLane datalane, CardView cardView){

        if(datalane==null){
           cardView.setVisibility(View.INVISIBLE);
        }else{
            cardView.setVisibility(View.VISIBLE);
            String warnal1 = getWarna(String.valueOf(datalane.getLebarLane()), getValuetipe(datalane.getSc1()));
            cardView.setCardBackgroundColor(Color.parseColor(warnal1));
        }

    }

    private void setRowLane(int jumlah, CardView c1, CardView c2, CardView c3, CardView c4, CardView c5, CardView c6, CardView c7, CardView c8, CardView c9, CardView c10){


       switch (jumlah){
            case 0 :
                c1.setVisibility(View.GONE);
                c2.setVisibility(View.GONE);
                c3.setVisibility(View.GONE);
                c4.setVisibility(View.GONE);
                c5.setVisibility(View.GONE);
                c6.setVisibility(View.GONE);
                c7.setVisibility(View.GONE);
                c8.setVisibility(View.GONE);
                c9.setVisibility(View.GONE);
                c10.setVisibility(View.GONE);

                break;


            case 1 :

                c2.setVisibility(View.GONE);
                c3.setVisibility(View.GONE);
                c4.setVisibility(View.GONE);
                c5.setVisibility(View.GONE);
                c6.setVisibility(View.GONE);
                c7.setVisibility(View.GONE);
                c8.setVisibility(View.GONE);
                c9.setVisibility(View.GONE);
                c10.setVisibility(View.GONE);

                break;


            case 2 :

                c3.setVisibility(View.GONE);
                c4.setVisibility(View.GONE);
                c5.setVisibility(View.GONE);
                c6.setVisibility(View.GONE);
                c7.setVisibility(View.GONE);
                c8.setVisibility(View.GONE);
                c9.setVisibility(View.GONE);
                c10.setVisibility(View.GONE);

                break;


            case 3 :

                c4.setVisibility(View.GONE);
                c5.setVisibility(View.GONE);
                c6.setVisibility(View.GONE);
                c7.setVisibility(View.GONE);
                c8.setVisibility(View.GONE);
                c9.setVisibility(View.GONE);
                c10.setVisibility(View.GONE);

                break;


            case 4 :

                c5.setVisibility(View.GONE);
                c6.setVisibility(View.GONE);
                c7.setVisibility(View.GONE);
                c8.setVisibility(View.GONE);
                c9.setVisibility(View.GONE);
                c10.setVisibility(View.GONE);


                break;

           case 5 :


               c6.setVisibility(View.GONE);
               c7.setVisibility(View.GONE);
               c8.setVisibility(View.GONE);
               c9.setVisibility(View.GONE);
               c10.setVisibility(View.GONE);


               break;

           case 6 :

               c7.setVisibility(View.GONE);
               c8.setVisibility(View.GONE);
               c9.setVisibility(View.GONE);
               c10.setVisibility(View.GONE);


               break;

           case 7 :


               c8.setVisibility(View.GONE);
               c9.setVisibility(View.GONE);
               c10.setVisibility(View.GONE);


               break;

           case 8 :


               c9.setVisibility(View.GONE);
               c10.setVisibility(View.GONE);


               break;

           case 9 :

               c10.setVisibility(View.GONE);


               break;

           case 10 :




               break;


            default:

        }

    }



    /*private Intent setClick(DataLane dataLane, int position){

                session.saveSPInt(Session.SP_SUBSEGMENT, dataLane.getSubsegment());
                session.saveSPInt(Session.SP_NOSEGMENT, dataLane.getNosegment());
                session.saveSPString(Session.SP_NORUAS, dataLane.getNoruas());
                session.saveSPString(Session.SP_KODEPROV, dataLane.getNoprov());
                session.saveSPInt(Session.FOKUS, position);

                Intent i = new Intent(mContex, MainFormTerusan.class);

                i.putExtra("tipe", "Lane");
                i.putExtra("posisi", dataLane.getUrut());
                i.putExtra("from", "Tabel");

        return i;
    }*/

    private DataLane getDataLane(int segment, int subsegment, String posisi, String lajur){

        DataLane dataLane = dbLane.getLane(session.getKodeprov(), session.getNoruas(), segment, subsegment,  posisi, lajur);
        return dataLane;

    }

    private DataLane getPositionLane(ArrayList<DataLane> listku, int index){

        DataLane dataReturn = null;

        int indexSegment = 1;
        int indexSubseg = 0;

        for(int i=0; i<index; i++){
            if(indexSubseg==9){
                indexSegment = indexSegment+1;
                indexSubseg = 0;
            }else {
                indexSubseg = indexSubseg + 1;
            }
        }

        for(DataLane dataLane : listku){
            if(dataLane.getNosegment()==indexSegment && dataLane.getSubsegment()==indexSubseg){
                dataReturn = dataLane;
                return dataReturn;
            }
        }

        return dataReturn;

    }

}


