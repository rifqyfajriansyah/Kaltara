package com.example.roadmanagement.kaltara.Formu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.Interface.DataTemporari;
import com.example.roadmanagement.kaltara.helper.Session;

import java.util.ArrayList;

public class UnFormAdapter extends RecyclerView.Adapter<UnFormAdapter.ViewHolder> {
    private ArrayList<DataTemporari> mArrayList;
    private Context mContex;
    private Session session;


    public UnFormAdapter(ArrayList<DataTemporari> mArrayList, Context mContex) {
        this.mArrayList = mArrayList;
        this.mContex = mContex;

        session = new Session(mContex);

    }

    @Override
    public UnFormAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UnFormAdapter.ViewHolder(LayoutInflater.from(mContex).inflate
                (R.layout.detail_sinkron, parent, false));
    }

    @Override
    public void onBindViewHolder(UnFormAdapter.ViewHolder holder, int position) {
        DataTemporari current = mArrayList.get(position);


        holder.bindTO(current);
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView text;
        RelativeLayout box;
        CardView delete;

        public ViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.textsinkron);
            box = itemView.findViewById(R.id.sinkronrow);
            delete = itemView.findViewById(R.id.deleteSinkron);

            delete.setVisibility(View.GONE);


            itemView.setOnClickListener(this);

        }

        public void bindTO(DataTemporari current) {

            text.setText(String.valueOf(current.getNosegment()+"-"+current.getTipe()+" "+current.getPosisi()+" "+current.getUrut()));

            if(current.getStatus().equals("1")){
                box.setBackgroundColor(Color.parseColor("#b5cdf7"));
            }else {
                if (current.getFoto().equals("0")) {
                    box.setBackgroundColor(Color.parseColor("#febdb3"));
                } else {
                    box.setBackgroundColor(Color.parseColor("#d5f9d9"));
                }
            }

        }


        @Override
        public void onClick(View view) {



            Intent i = new Intent(mContex, UnformEdit.class);;

            switch(mArrayList.get(getAdapterPosition()).getTipe()){
                case "Lahan" :
                        i.putExtra("id", mArrayList.get(getAdapterPosition()).getNosegment());
                        i.putExtra("tipe", "Lahan");
                        i.putExtra("dari", "Recycle");
                        i.putExtra("posisi", mArrayList.get(getAdapterPosition()).getPosisi());
                    break;

                case "Saluran" :
                    i.putExtra("id", mArrayList.get(getAdapterPosition()).getNosegment());
                    i.putExtra("tipe", "Saluran");
                    i.putExtra("dari", "Recycle");
                    i.putExtra("posisi", mArrayList.get(getAdapterPosition()).getPosisi());
                    break;

                case "Lane" :
                    i.putExtra("id", mArrayList.get(getAdapterPosition()).getNosegment());
                    i.putExtra("tipe", "Lane");
                    i.putExtra("dari", "Recycle");
                    i.putExtra("posisi", mArrayList.get(getAdapterPosition()).getPosisi());

                    break;

                case "Bahu" :
                    i.putExtra("id", mArrayList.get(getAdapterPosition()).getNosegment());
                    i.putExtra("tipe", "Bahu");
                    i.putExtra("dari", "Recycle");
                    i.putExtra("posisi", mArrayList.get(getAdapterPosition()).getPosisi());
                    break;

                case "Median" :
                    i.putExtra("id", mArrayList.get(getAdapterPosition()).getNosegment());
                    i.putExtra("tipe", "Median");
                    i.putExtra("dari", "Recycle");
                    i.putExtra("posisi", mArrayList.get(getAdapterPosition()).getPosisi());
                    break;

                default:

            }


            mContex.startActivity(i);

        }
    }
}