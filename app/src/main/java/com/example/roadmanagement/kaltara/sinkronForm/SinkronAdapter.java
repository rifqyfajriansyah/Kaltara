package com.example.roadmanagement.kaltara.sinkronForm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.example.roadmanagement.kaltara.FormTerusan.MainFormTerusan;
import com.example.roadmanagement.kaltara.Formu.UnformEdit;
import com.example.roadmanagement.kaltara.Interface.DataTemporari;
import com.example.roadmanagement.kaltara.Interface.SendId;
import com.example.roadmanagement.kaltara.databaseHelper.DbBahu;
import com.example.roadmanagement.kaltara.databaseHelper.DbLahan;
import com.example.roadmanagement.kaltara.databaseHelper.DbLane;
import com.example.roadmanagement.kaltara.databaseHelper.DbMedian;
import com.example.roadmanagement.kaltara.databaseHelper.DbSaluran;
import com.example.roadmanagement.kaltara.databaseHelper.DbTemporari;
import com.example.roadmanagement.kaltara.helper.Session;

import java.util.ArrayList;

public class SinkronAdapter  extends RecyclerView.Adapter<SinkronAdapter.ViewHolder> {
    private ArrayList<DataTemporari> mArrayList;
    private Context mContex;
    private Session session;

    DbLahan dbLahan;
    DbBahu dbBahu;
    DbSaluran dbSaluran;
    DbLane dbLane;
    DbMedian dbMedian;
    DbTemporari dbTemporari;

    SendId sendId;


    public SinkronAdapter(ArrayList<DataTemporari> mArrayList, Context mContex, SendId sendId) {
        this.mArrayList = mArrayList;
        this.mContex = mContex;
        this.sendId = sendId;

        session = new Session(mContex);
        dbLahan =  new DbLahan(mContex);
        dbBahu = new DbBahu(mContex);
        dbSaluran = new DbSaluran(mContex);
        dbMedian = new DbMedian(mContex);
        dbLane = new DbLane(mContex);
        dbTemporari = new DbTemporari(mContex);


    }

    @Override
    public SinkronAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SinkronAdapter.ViewHolder(LayoutInflater.from(mContex).inflate
                (R.layout.detail_sinkron, parent, false));
    }

    @Override
    public void onBindViewHolder(SinkronAdapter.ViewHolder holder, int position) {
        DataTemporari current = mArrayList.get(position);


        holder.bindTO(current);
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView text;
        RelativeLayout box;
        CardView delete;

        public ViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.textsinkron);
            box = itemView.findViewById(R.id.sinkronrow);
            delete = itemView.findViewById(R.id.deleteSinkron);


            box.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    session.saveSPInt(Session.SP_NOSEGMENT, mArrayList.get(getAdapterPosition()).getNosegment());
                    session.saveSPInt(Session.SP_SUBSEGMENT, mArrayList.get(getAdapterPosition()).getSubsegment());

                    Intent i;

                    if(mArrayList.get(getAdapterPosition()).getJenis().equals("Unidentified")){

                        i = new Intent(mContex, UnformEdit.class);
                        i.putExtra("id", mArrayList.get(getAdapterPosition()).getNosegment());
                        i.putExtra("dari", "Sinkron");
                        i.putExtra("posisi", mArrayList.get(getAdapterPosition()).getPosisi());

                    }else{
                        i = new Intent(mContex, MainFormTerusan.class);
                        i.putExtra("from", "Sinkron");
                        if(mArrayList.get(getAdapterPosition()).getTipe().equals("Lane")) {
                            i.putExtra("posisi", mArrayList.get(getAdapterPosition()).getUrut());
                        }else{
                            i.putExtra("posisi", mArrayList.get(getAdapterPosition()).getPosisi());
                        }
                    }

                    i.putExtra("tipe", mArrayList.get(getAdapterPosition()).getTipe());

                    mContex.startActivity(i);
                }
            });


            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(mContex);
                    builder.setMessage("Apakah anda yakin ingin mereset ulang data ini ?");
                    builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String posisi;
                        if(mArrayList.get(getAdapterPosition()).getPosisi().equals("Left")){
                            posisi = "kiri";
                        }else{
                            posisi = "kanan";
                        }
                        resetData(mArrayList.get(getAdapterPosition()).getTipe(),
                                mArrayList.get(getAdapterPosition()).getNoprov(),
                                mArrayList.get(getAdapterPosition()).getNoruas(),
                                mArrayList.get(getAdapterPosition()).getNosegment(),
                                mArrayList.get(getAdapterPosition()).getSubsegment(),
                                posisi,
                                mArrayList.get(getAdapterPosition()).getUrut(),
                                mArrayList.get(getAdapterPosition()).getPosisi()
                                );

                        sendId.hapusGambar(1);

                        }
                    }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {



                        }
                    });
                    final AlertDialog dialog = builder.create();

                    dialog.setOnShowListener( new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface arg0) {
                            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
                            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);
                        }
                    });

                    dialog.show();

                }
            });

        }

        public void bindTO(DataTemporari current) {

            String segment;


            if(!current.getJenis().equals("Unidentified")) {

                if ((current.getNosegment()== current.getSegmentakhir()) && (current.getSubsegment() == current.getSubsegmentakhir())) {
                    segment = current.getNosegment()+"."+current.getSubsegment();
                } else {
                    segment = current.getNosegment()+"."+current.getSubsegment() + " - " + current.getSegmentakhir()+"."+current.getSubsegmentakhir();
                }

            }else{
                segment = String.valueOf(current.getNosegment());
            }

            text.setText(String.valueOf(current.getJenis()+" "+current.getTipe()+" "+current.getPosisi()+" "+current.getUrut()+" : "+ segment));

            if(current.getStatus().equals("1")){
                delete.setVisibility(View.GONE);
                box.setBackgroundColor(Color.parseColor("#b5cdf7"));
            }else {
                delete.setVisibility(View.VISIBLE);
                if (current.getFoto().equals("0")) {
                    box.setBackgroundColor(Color.parseColor("#febdb3"));
                } else {
                    box.setBackgroundColor(Color.parseColor("#d5f9d9"));
                }
            }

        }


        public void resetData(String tipe, String provinsi, String ruas, int segment, int subsegment, String posisi, String lajur, String letak){

            dbTemporari.hapusTemporari(provinsi, ruas, segment, subsegment, tipe, letak, lajur);

            /*if(!lajur.equals("Unidentified")) {
                switch (tipe) {
                    case "Lahan":
                        dbLahan.resetLahan(provinsi, ruas, segment, subsegment, posisi);
                        break;

                    case "Saluran":
                        dbSaluran.resetSaluran(provinsi, ruas, segment, subsegment, posisi);
                        break;

                    case "Lane":
                        dbLane.resetLane(provinsi, ruas, segment, subsegment, posisi, lajur);
                        break;

                    case "Bahu":
                        dbBahu.resetBahu(provinsi, ruas, segment, subsegment,posisi);
                        break;

                    case "Median":
                        dbMedian.resetMedian(provinsi, ruas, segment, subsegment);
                        break;


                    default:

                }
            }*/
        }

    }
}
