package com.example.roadmanagement.kaltara.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rifqy.kaltara.R;

import java.util.List;

public class UpdateAdapter extends RecyclerView.Adapter<UpdateAdapter.ViewHolder> {
    private List<String> mArrayList;
    private Context mContex;
    String status;


    public UpdateAdapter(List<String> mArrayList, String status, Context mContex) {
        this.mArrayList = mArrayList;
        this.mContex = mContex;
        this.status = status;

    }

    @Override
    public UpdateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UpdateAdapter.ViewHolder(LayoutInflater.from(mContex).inflate
                (R.layout.dialogdetail, parent, false));
    }

    @Override
    public void onBindViewHolder(UpdateAdapter.ViewHolder holder, int position) {
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

            noruas = itemView.findViewById(R.id.noruasnya);

            itemView.setOnClickListener(this);

        }

        public void bindTO(String current) {


            noruas.setText(current);



        }


        @Override
        public void onClick(View view) {
            //UpdateTaskRuas uploadingTask = new UpdateTaskRuas(mContex, status, mArrayList.get(getAdapterPosition()));
            //uploadingTask.execute("bisa", "bisa", "bisa");

            AlertDialog.Builder builder = new AlertDialog.Builder(mContex);

            TextView textView = new TextView(mContex);
            textView.setText(mArrayList.get(getAdapterPosition())+" :");
            textView.setPadding(30, 30, 20, 30);
            textView.setTextSize(16F);
            textView.setBackgroundColor(R.color.colorPrimaryDark);
            textView.setTextColor(Color.YELLOW);
            //builder.setMessage("Silahkan pilih bagian untuk disinkron : ");


            LayoutInflater inflater = ((Activity)mContex).getLayoutInflater();
            View dialogview = inflater.inflate(R.layout.dialoglist, null);
            RecyclerView recyclerViews = dialogview.findViewById(R.id.dialogrecycle);
            UpdateTableDetailAdapter updateAdapter = new UpdateTableDetailAdapter(mArrayList.get(getAdapterPosition()), status, mContex);

            recyclerViews.setLayoutManager(new LinearLayoutManager(mContex, LinearLayout.VERTICAL, false));
            recyclerViews.setHasFixedSize(true);
            recyclerViews.setAdapter(updateAdapter);

            builder.setView(dialogview);
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            final AlertDialog dialog = builder.create();
            dialog.setCustomTitle(textView);
            dialog.getWindow().setBackgroundDrawableResource(R.color.colorPrimaryDark);

            dialog.setOnShowListener( new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface arg0) {
                    dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);
                  //  dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setBackgroundColor(Color.RED);
                }
            });

            dialog.show();
        }
    }
}
