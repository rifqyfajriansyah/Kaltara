package com.example.roadmanagement.kaltara.adapter;

import android.content.Context;
import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.Interface.UpdateTable;
import com.example.roadmanagement.kaltara.databaseHelper.DbTemporari;
import com.example.roadmanagement.kaltara.helper.Session;
import com.example.roadmanagement.kaltara.sinkronForm.SinkronUtama;

import java.util.ArrayList;

// no use

public class UpdateTableAdapter extends RecyclerView.Adapter<UpdateTableAdapter.ViewHolder> {
    private ArrayList<UpdateTable> mArrayList;
    private Context mContex;
    DbTemporari dbTemporari;
    DashboardAdapter dashboardAdapter;
    Session session;


    public UpdateTableAdapter(ArrayList<UpdateTable> mArrayList, Context mContex) {
        this.mArrayList = mArrayList;
        this.mContex = mContex;

        dbTemporari = new DbTemporari(mContex);
        session = new Session(mContex);


    }

    @Override
    public UpdateTableAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UpdateTableAdapter.ViewHolder(LayoutInflater.from(mContex).inflate
                (R.layout.ruasdashboard, parent, false));
    }

    @Override
    public void onBindViewHolder(UpdateTableAdapter.ViewHolder holder, int position) {
        UpdateTable current = mArrayList.get(position);
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

        public void bindTO(UpdateTable current) {


            noruas.setText(current.getNoruas());



        }


        @Override
        public void onClick(View view) {
            // UpdateTaskRuas uploadingTask = new UpdateTaskRuas(mContex, mArrayList.get(getAdapterPosition()));
            // uploadingTask.execute("bisa", "bisa", "bisa");



            /*AlertDialog.Builder builder = new AlertDialog.Builder(mContex);

            String title = "Ruas "+mArrayList.get(getAdapterPosition()).getNoruas();
            SpannableString ss1 = new SpannableString(title);
            ss1.setSpan(new RelativeSizeSpan(1f), 0 , ss1.length(),0);
            ss1.setSpan(new ForegroundColorSpan(Color.parseColor("#AF0C0C")), 0 , ss1.length(), 0);
            builder.setTitle(ss1);

            LayoutInflater inflater =LayoutInflater.from(mContex);
            View dialogview = inflater.inflate(R.layout.dialoglist, null);
            RecyclerView recyclerViews = dialogview.findViewById(R.id.dialogrecycle);
            ArrayList<DataTemporari> noruass = dbTemporari.getRuasTemporari(mArrayList.get(getAdapterPosition()).getNoruas(),"1");
            dashboardAdapter = new DashboardAdapter(noruass, mContex);

            recyclerViews.setLayoutManager(new LinearLayoutManager(mContex, LinearLayout.VERTICAL, false));
            recyclerViews.setHasFixedSize(true);
            recyclerViews.setAdapter(dashboardAdapter);

            builder.setView(dialogview);


            builder.show();*/
            session.saveSPString(Session.SP_NORUAS, mArrayList.get(getAdapterPosition()).getNoruas());
            Intent i = new Intent(mContex, SinkronUtama.class);
            i.putExtra("value", 1);
            mContex.startActivity(i);

        }
    }
}
