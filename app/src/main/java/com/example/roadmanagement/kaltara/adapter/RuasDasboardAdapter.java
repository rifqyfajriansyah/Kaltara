package com.example.roadmanagement.kaltara.adapter;

import android.content.Context;
import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.databaseHelper.DbTemporari;
import com.example.roadmanagement.kaltara.helper.Session;
import com.example.roadmanagement.kaltara.sinkronForm.SinkronUtama;

import java.util.List;

public class RuasDasboardAdapter extends RecyclerView.Adapter<RuasDasboardAdapter.ViewHolder> {
    private List<String> mArrayList;
    private Context mContex;
    DbTemporari dbTemporari;
    Session session;
    String status;


    public RuasDasboardAdapter(List<String> mArrayList, Context mContex, String status) {

        this.mArrayList = mArrayList;
        this.mContex = mContex;
        this.status = status;

        session  = new Session(mContex);
        dbTemporari = new DbTemporari(mContex);


    }

    @Override
    public RuasDasboardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RuasDasboardAdapter.ViewHolder(LayoutInflater.from(mContex).inflate
                (R.layout.ruasdashboard, parent, false));
    }

    @Override
    public void onBindViewHolder(RuasDasboardAdapter.ViewHolder holder, int position) {
        String current = mArrayList.get(position);
        holder.bindTO(current);
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView noruas;


        public ViewHolder(View itemView) {
            super(itemView);

            noruas = itemView.findViewById(R.id.ruasdashboards);

            itemView.setOnClickListener(this);

        }

        public void bindTO(String current) {


            noruas.setText(current);



        }


        @Override
        public void onClick(View view) {

            String warna = "putih";

            if(status.equals("1")){
                warna = "biru";
            }

            session.saveSPString(Session.SP_NORUAS, mArrayList.get(getAdapterPosition()));
            Intent i = new Intent(mContex, SinkronUtama.class);
            i.putExtra("warna", warna);
            mContex.startActivity(i);

        }
    }
}

