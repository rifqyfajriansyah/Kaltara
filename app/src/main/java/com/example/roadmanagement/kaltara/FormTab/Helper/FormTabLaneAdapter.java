package com.example.roadmanagement.kaltara.FormTab.Helper;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.helper.Session;

import java.util.ArrayList;

public class FormTabLaneAdapter extends RecyclerView.Adapter<FormTabLaneAdapter.ViewHolder> {
    private ArrayList<String> mArrayList;
    private Context mContex;
    Session session;
    int selectedPos;

    StringTab stringTab;


    public FormTabLaneAdapter(ArrayList<String> mArrayList, Context mContex, StringTab stringTab) {

        this.mArrayList = mArrayList;
        this.mContex = mContex;
        session = new Session(mContex);
        this.stringTab = stringTab;

        if(session.getTabAtributLane()>mArrayList.size()-1){
            selectedPos = session.getTabAtributLane()-1;
            session.saveSPInt(Session.TAB_ATRIBUT_LANE, selectedPos);
        }else{
            selectedPos = session.getTabAtributLane();
        }


    }

    @Override
    public FormTabLaneAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FormTabLaneAdapter.ViewHolder(LayoutInflater.from(mContex).inflate
                (R.layout.detail_atribut, parent, false));
    }

    private static int dpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    @Override
    public void onBindViewHolder(FormTabLaneAdapter.ViewHolder holder, int position) {
        String current = mArrayList.get(position);

        int widthku = selectedPos == position ? 120 : 80;

        holder.cardParam.width = dpToPx(widthku, mContex);
        holder.cardView.setLayoutParams(holder.cardParam);

        holder.textView.setTextSize(selectedPos == position ? 16 : 14);
        holder.textView.setTextColor(selectedPos == position ? Color.parseColor("#FFFFFF") : Color.parseColor("#58968B"));
        holder.cardView.setCardBackgroundColor(selectedPos == position ? Color.parseColor("#58968B") : Color.parseColor("#FFFFFF"));
        holder.bindTO(current);
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView textView;
        CardView cardView;

        RecyclerView.LayoutParams cardParam;

        public ViewHolder(View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.detAtrCd);
            textView = itemView.findViewById(R.id.detAtrText);

            cardParam = (RecyclerView.LayoutParams) cardView.getLayoutParams();

            itemView.setOnClickListener(this);

        }

        public void bindTO(String current) {
            textView.setText(current);
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

            session.saveSPInt(Session.TAB_ATRIBUT_LANE, getAdapterPosition());

            stringTab.sendString(mArrayList.get(getAdapterPosition()));

        }
    }
}
