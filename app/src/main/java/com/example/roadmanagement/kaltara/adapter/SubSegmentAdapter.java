package com.example.roadmanagement.kaltara.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.FormTab.Helper.DialogTab;
import com.example.roadmanagement.kaltara.FormTab.Helper.IntTab;
import com.example.roadmanagement.kaltara.Interface.SegmentClick;
import com.example.roadmanagement.kaltara.Interface.SingleSegment;
import com.example.roadmanagement.kaltara.databaseHelper.DbSpinner;
import com.example.roadmanagement.kaltara.helper.Session;

import java.util.ArrayList;

public class SubSegmentAdapter extends RecyclerView.Adapter<SubSegmentAdapter.ViewHolder> {

    private ArrayList<SingleSegment> mArrayList;
    private Context mContex;
    Session session;
    int selectedPos;
    SegmentClick segmentClick;

    DbSpinner dbSpinner;
    int maxsub;

    public SubSegmentAdapter(ArrayList<SingleSegment> mArrayList, Context mContex, SegmentClick segmentClick) {

        this.mArrayList = mArrayList;
        this.mContex = mContex;
        session = new Session(mContex);
        this.segmentClick = segmentClick;

        dbSpinner = new DbSpinner(mContex);

        maxsub = dbSpinner.getMaksSegmentSub(session.getKodeprov(), session.getNoruas(), session.getNosegment());

        /*if(session.getTipesurvey().equals("Opposite")){

            selectedPos = maxsub-session.getSubsegment();

        }else{
            selectedPos = session.getSubsegment();
        }*/

        selectedPos = session.getSubsegment();


    }

    @Override
    public SubSegmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SubSegmentAdapter.ViewHolder(LayoutInflater.from(mContex).inflate
                (R.layout.detailsubsegment, parent, false));
    }

    @Override
    public void onBindViewHolder(SubSegmentAdapter.ViewHolder holder, int position) {
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


        TextView textView, textStaAkhir;
        RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            relativeLayout = itemView.findViewById(R.id.subsegmentKotak);
            textView = itemView.findViewById(R.id.subsegmentNomor);
            textStaAkhir = itemView.findViewById(R.id.subsegmentAkhir);
            itemView.setOnClickListener(this);

        }

        public void bindTO(SingleSegment current) {
            textView.setText(String.valueOf(current.getNoseg())+"."+String.valueOf(current.getSubSegment()));
            textStaAkhir.setText(current.getStaakhir());
        }


        @Override
        public void onClick(View view) {

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


        }

        private void onPositive(){

            notifyItemChanged(selectedPos);
            selectedPos = getAdapterPosition();
            notifyItemChanged(selectedPos);

            int segment = mArrayList.get(getAdapterPosition()).getNoseg();
            int subsegment = mArrayList.get(getAdapterPosition()).getSubSegment();

            String sta = mArrayList.get(getAdapterPosition()).getStaawal()+" - "+mArrayList.get(getAdapterPosition()).getStaakhir();

            session.saveSPInt(Session.SP_SUBSEGMENT, subsegment);

            segmentClick.Klik(session.getKodeprov(), session.getNoruas(), segment, subsegment, "", sta);

        }

    }
}