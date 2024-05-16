package com.example.roadmanagement.kaltara.FormTab.Helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.rifqy.kaltara.R;

public class DialogTab {

    public static void setDialogAlert(Context context, String text, IntTab sendData) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View dialogview = inflater.inflate(R.layout.tab_dialog_cancel, null);
        TextView textView = dialogview.findViewById(R.id.tabDCtext);
        CardView cardYa = dialogview.findViewById(R.id.tabDCya);
        CardView cardNo = dialogview.findViewById(R.id.tabDCno);

        textView.setText(text);

        builder.setView(dialogview);

        final AlertDialog dialog = builder.create();


        dialog.setOnShowListener(arg0 -> {

            cardNo.setOnClickListener(v -> dialog.dismiss());

            cardYa.setOnClickListener(v -> {
                sendData.sendPosition(1);
                dialog.dismiss();
            });

        });

        dialog.show();
    }

    public static void setDialogSave(Context context, IntTab sendData) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View dialogview = inflater.inflate(R.layout.tab_dialog_save, null);
        CardView cardYa = dialogview.findViewById(R.id.tabDSya);
        CardView cardNo = dialogview.findViewById(R.id.tabDSno);


        builder.setView(dialogview);

        final AlertDialog dialog = builder.create();


        dialog.setOnShowListener(arg0 -> {

            cardNo.setOnClickListener(v -> dialog.dismiss());

            cardYa.setOnClickListener(v -> {
                sendData.sendPosition(1);
                dialog.dismiss();
            });

        });

        dialog.show();
    }
}
