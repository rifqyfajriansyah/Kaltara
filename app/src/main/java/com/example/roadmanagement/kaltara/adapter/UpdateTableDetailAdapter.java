package com.example.roadmanagement.kaltara.adapter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.Interface.DataTemporari;
import com.example.roadmanagement.kaltara.Interface.SendId;

import com.example.roadmanagement.kaltara.Task.TaskSinkron;
import com.example.roadmanagement.kaltara.api.FungsiAPI;
import com.example.roadmanagement.kaltara.databaseHelper.DbTemporari;
import com.example.roadmanagement.kaltara.helper.Session;

import java.util.ArrayList;
import java.util.List;

public class UpdateTableDetailAdapter extends RecyclerView.Adapter<UpdateTableDetailAdapter.ViewHolder> {
    List<String> detail = new ArrayList<>();
    private Context mContex;
    DbTemporari dbTemporari;
    DashboardAdapter dashboardAdapter;

    String noruas;
    String status;

    FungsiAPI fungsiAPI;
    Session session;

    ArrayList<DataTemporari> dataTemporaris = new ArrayList<>();


    public UpdateTableDetailAdapter(String noruas, String status, Context mContex) {
        this.mContex = mContex;
        dbTemporari = new DbTemporari(mContex);

        this.noruas = noruas;
        this.status = status;

        fungsiAPI = new FungsiAPI(mContex);
        session = new Session(mContex);

        ArrayList<DataTemporari> Lahan = dbTemporari.getListTemporari(session.getKodeprov(), noruas, "Lahan", null, null, "1", status, "1");
        ArrayList<DataTemporari> Lane = dbTemporari.getListTemporari(session.getKodeprov(), noruas, "Lane", null, null, "1", status, "1");
        ArrayList<DataTemporari> Bahu = dbTemporari.getListTemporari(session.getKodeprov(), noruas, "Bahu", null, null, "1", status, "1");
        ArrayList<DataTemporari> Median = dbTemporari.getListTemporari(session.getKodeprov(), noruas, "Median", null, null, "1", status, "1");
        ArrayList<DataTemporari> Saluran = dbTemporari.getListTemporari(session.getKodeprov(), noruas, "Saluran", null, null, "1", status, "1");
        ArrayList<DataTemporari> Segment = dbTemporari.getListTemporari(session.getKodeprov(), noruas, "Segment", null, null, "1", status, "1");
        ArrayList<DataTemporari> inlet = dbTemporari.getListTemporari(session.getKodeprov(), noruas, "Inlet ditrotoar", null, null, "1", status, "1");
        ArrayList<DataTemporari> minlet = dbTemporari.getListTemporari(session.getKodeprov(), noruas, "Perkerasan", null, null, "1", status, "1");
        ArrayList<DataTemporari> spd = dbTemporari.getListTemporari(session.getKodeprov(), noruas, "SPD", null, null, "1", status, "1");
        ArrayList<DataTemporari> spl = dbTemporari.getListTemporari(session.getKodeprov(), noruas, "SPL", null, null, "1", status, "1");
        ArrayList<DataTemporari> outlet = dbTemporari.getListTemporari(session.getKodeprov(), noruas, "Outlet", null, null, "1", status, "1");
        ArrayList<DataTemporari> gorong = dbTemporari.getListTemporari(session.getKodeprov(), noruas, "Gorong-gorong", null, null, "1", status, "1");
        ArrayList<DataTemporari> lereng = dbTemporari.getListTemporari(session.getKodeprov(), noruas, "Saluran Lereng", null, null, "1", status, "1");

        if(Lahan.size()!=0){
            detail.add("Lahan");
        }

        if(Lane.size()!=0){
            detail.add("Lane");
        }

        if(Bahu.size()!=0){
            detail.add("Bahu");
        }

        if(Median.size()!=0){
            detail.add("Median");
        }

        if(Saluran.size()!=0){
            detail.add("Saluran");
        }

        if(Segment.size()!=0){
            detail.add("Segment");
        }

        if(inlet.size()!=0){
            detail.add("Inlet ditrotoar");
        }

        if(minlet.size()!=0){
            detail.add("Perkerasan");
        }

        if(spd.size()!=0){
            detail.add("SPD");
        }

        if(spl.size()!=0){
            detail.add("SPL");
        }

        if(outlet.size()!=0){
            detail.add("Outlet");
        }

        if(gorong.size()!=0){
            detail.add("Gorong-gorong");
        }

        if(lereng.size()!=0){
            detail.add("Saluran Lereng");
        }

        if(detail.size()>1){
            detail.add("Sinkron Semua");
        }



    }

    @Override
    public UpdateTableDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UpdateTableDetailAdapter.ViewHolder(LayoutInflater.from(mContex).inflate
                (R.layout.detail_updatedetail, parent, false));
    }

    @Override
    public void onBindViewHolder(UpdateTableDetailAdapter.ViewHolder holder, int position) {
        String current = detail.get(position);
        holder.bindTO(current);
    }

    @Override
    public int getItemCount() {
        return detail.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView tdetail;


        public ViewHolder(View itemView) {
            super(itemView);

            tdetail = itemView.findViewById(R.id.text_updated);

            itemView.setOnClickListener(this);

        }

        public void bindTO(String current) {
            tdetail.setText(current);

        }


        @Override
        public void onClick(View view) {


            if(detail.get(getAdapterPosition()).equals("Sinkron Semua")) {
                //dataTemporaris = dbTemporari.getSinkrotempo(noruas,null, null, null, status, "1");
                dataTemporaris = dbTemporari.getListTemporari(session.getKodeprov(), noruas, null, null, null, "1", status, "1");
            }else if(!detail.get(getAdapterPosition()).equals("Lane")){

                dataTemporaris = dbTemporari.getListTemporari(session.getKodeprov(), noruas, detail.get(getAdapterPosition()), null, null, "1", status, "1");
                //dataTemporaris = dbTemporari.getSinkrotempo(noruas, detail.get(getAdapterPosition()), null, null, status, "1");
            }else{

                dataTemporaris = dbTemporari.getListTemporari(session.getKodeprov(), noruas, "Segment", null, null, "1", status, "1");
                if(dataTemporaris.size()>0){
                    dataTemporaris.clear();
                }else{
                    dataTemporaris = dbTemporari.getListTemporari(session.getKodeprov(), noruas, "Lane", null, null, "1", status, "1");
                }

            }


            if(dataTemporaris.size()>0) {

                fungsiAPI.cekSinkronUrut(session.getKodeprov(), noruas, new SendId() {
                    @Override
                    public void hapusGambar(int id) {

                        TaskSinkron taskSinkron = new TaskSinkron(mContex, dataTemporaris, id);
                        taskSinkron.execute("bisa", "bisa", "bisa");

                    }


                });
            }else{
                Toast.makeText(mContex, "Tidak ada data yang bisa diupdate"+"\n"
                        +"Pastikan setiap perubahan disertai dengan foto", Toast.LENGTH_LONG).show();
            }

        }
    }
}

