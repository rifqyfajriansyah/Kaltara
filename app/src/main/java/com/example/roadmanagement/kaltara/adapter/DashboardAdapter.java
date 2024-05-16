package com.example.roadmanagement.kaltara.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.roadmanagement.kaltara.Interface.DataTemporari;
import com.example.rifqy.kaltara.R;

import java.util.ArrayList;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {
    private ArrayList<DataTemporari> mArrayList;
    private Context mContex;


    public DashboardAdapter(ArrayList<DataTemporari> mArrayList, Context mContex) {
        this.mArrayList = mArrayList;
        this.mContex = mContex;

    }

    @Override
    public DashboardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DashboardAdapter.ViewHolder(LayoutInflater.from(mContex).inflate
                (R.layout.detaildashboard, parent, false));
    }

    @Override
    public void onBindViewHolder(DashboardAdapter.ViewHolder holder, int position) {
        DataTemporari current = mArrayList.get(position);
        holder.bindTO(current);
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView noprov;
        TextView noruas;
        TextView nosegment;
        TextView tipe;
        TextView letak;
        TextView urut;

        public ViewHolder(View itemView) {
            super(itemView);
            noprov = itemView.findViewById(R.id.noprovdashboard);
            noruas = itemView.findViewById(R.id.noruasdashboard);
            nosegment = itemView.findViewById(R.id.nosegmentdashboard);
            tipe = itemView.findViewById(R.id.tipedashboard);
            letak = itemView.findViewById(R.id.posisidashboard);
            urut = itemView.findViewById(R.id.nourutdashboard);
            itemView.setOnClickListener(this);

        }

        public void bindTO(DataTemporari current) {

            noprov.setText(String.valueOf(current.getNoprov()));
            noruas.setText(String.valueOf(current.getNoruas()));
            nosegment.setText(String.valueOf(current.getNosegment()));
            tipe.setText(current.getTipe());
            letak.setText(current.getPosisi());
            urut.setText(current.getUrut());


        }


        @Override
        public void onClick(View view) {

        }
    }
}

