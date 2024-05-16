package com.example.roadmanagement.kaltara.adapter;

import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.roadmanagement.kaltara.FormTab.Helper.DialogTab;
import com.example.roadmanagement.kaltara.FormTab.Helper.IntTab;
import com.example.roadmanagement.kaltara.Interface.SegmentClick;
import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.Interface.SingleSegment;
import com.example.roadmanagement.kaltara.databaseHelper.DbSpinner;
import com.example.roadmanagement.kaltara.helper.Session;

import java.util.ArrayList;

public class SegmentAdapter extends RecyclerView.Adapter<SegmentAdapter.ViewHolder> {
    private ArrayList<SingleSegment> mArrayList;
    private Context mContex;
    Session session;
    int selectedPos;
    SegmentClick segmentClick;

    DbSpinner dbSpinner;

    int maxSegment;



    public SegmentAdapter(ArrayList<SingleSegment> mArrayList, Context mContex, SegmentClick segmentClick) {

        this.mArrayList = mArrayList;
        this.mContex = mContex;
        session = new Session(mContex);
        this.segmentClick = segmentClick;

        dbSpinner = new DbSpinner(mContex);
        maxSegment = dbSpinner.getMaksSegment(session.getKodeprov(), session.getNoruas());

        /*if(session.getNosegment()==0){
            selectedPos = RecyclerView.NO_POSITION;
        }else if(session.getTipesurvey().equals("Opposite")){
            selectedPos = maxSegment - session.getNosegment();
        }else{
            selectedPos = session.getNosegment()-1;
        }*/

        if(session.getNosegment()==0){
            selectedPos = RecyclerView.NO_POSITION;
        }else{
            selectedPos = session.getNosegment()-1;
        }
    }

    @Override
    public SegmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SegmentAdapter.ViewHolder(LayoutInflater.from(mContex).inflate
                (R.layout.detailsegment, parent, false));
    }

    @Override
    public void onBindViewHolder(SegmentAdapter.ViewHolder holder, int position) {
        SingleSegment current = mArrayList.get(position);
        holder.textView.setTextColor(selectedPos == position ? ContextCompat.getColor(mContex, R.color.colorAccent) : ContextCompat.getColor(mContex, R.color.colorPrimaryDark));
        holder.relativeLayout.setBackground(selectedPos == position ? ContextCompat.getDrawable(mContex, R.drawable.listbirutua) : ContextCompat.getDrawable(mContex, R.drawable.listputih));
        holder.bindTO(current);
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView textView;
        RelativeLayout relativeLayout;
        TextView textSta;

        public ViewHolder(View itemView) {
            super(itemView);

            relativeLayout = itemView.findViewById(R.id.kotaksegment);
            textView = itemView.findViewById(R.id.nomorsegment);
            textSta = itemView.findViewById(R.id.stasegment);
            itemView.setOnClickListener(this);

        }

        public void bindTO(SingleSegment current) {
            textView.setText(String.valueOf(current.getNoseg()));
            textSta.setText(current.getStaawal());
        }


        @Override
        public void onClick(View view) {

            //Toast.makeText(mContex, String.valueOf(session.getFlagForm()), Toast.LENGTH_SHORT).show();

            if(session.getFlagForm()) {

                DialogTab.setDialogAlert(mContex, "Data perubahan belum tersimpan, tetap lanjut ?", new IntTab() {
                    @Override
                    public void sendPosition(int position) {
                        if (position == 1) {
                            onPositive();
                        }
                    }
                });

            }else{
                onPositive();
            }


            //Toast.makeText(mContex, String.valueOf(session.getNosegment()), Toast.LENGTH_LONG).show();
        }

        public void onPositive(){

            notifyItemChanged(selectedPos);
            selectedPos = getAdapterPosition();
            notifyItemChanged(selectedPos);

            int segment = mArrayList.get(getAdapterPosition()).getNoseg();
            int subsegment = 0 ;

            if(session.getTipesurvey().equals("Opposite")){
                //DbSpinner dbSpinner = new DbSpinner(mContex);
                subsegment = dbSpinner.getMaksSegmentSub(session.getKodeprov(), session.getNoruas(), segment);
            }


            session.saveSPInt(Session.SP_NOSEGMENT, segment);
            session.saveSPInt(Session.SP_SUBSEGMENT, subsegment);
            session.saveSPInt(Session.FOKUS, getAdapterPosition()-1);

            SingleSegment singleSegment = dbSpinner.getSpinner(session.getKodeprov(), session.getNoruas(), segment, subsegment);
            String sta = singleSegment.getStaawal()+" - "+singleSegment.getStaakhir();

            segmentClick.Klik(session.getKodeprov(), session.getNoruas(), segment, subsegment, "", sta);

        }
    }

}


