package com.example.roadmanagement.kaltara.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import androidx.cardview.widget.CardView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.FormTab.FormTabUtama;
import com.example.roadmanagement.kaltara.FormTab.Helper.DialogTab;
import com.example.roadmanagement.kaltara.FormTab.Helper.IntTab;
import com.example.roadmanagement.kaltara.FormTerusan.HelperFormTerusan;
import com.example.roadmanagement.kaltara.FormTerusan.TaskEndSurvey;
import com.example.roadmanagement.kaltara.Formu.FormUtama;
import com.example.roadmanagement.kaltara.Interface.SendDialog;
import com.example.roadmanagement.kaltara.Interface.SendId;
import com.example.roadmanagement.kaltara.Menuutama;
import com.example.roadmanagement.kaltara.databaseHelper.DbRuas;
import com.example.roadmanagement.kaltara.databaseHelper.DbSpinner;
import com.example.roadmanagement.kaltara.databaseHelper.DbTemporari;

public class DialogHelper {

    Context context;
    Session session;
    Intent intent;
    HelperFormTerusan helperFormTerusan;
    DbTemporari dbTemporari;
    DbSpinner dbSpinner;

    int maksSegment;

    public DialogHelper(Context context){

        this.context = context;
        session = new Session(context);
        dbTemporari = new DbTemporari(context);
        dbSpinner = new DbSpinner(context);

        maksSegment = dbSpinner.getMaksSegment(session.getKodeprov(), session.getNoruas());
    }

    public void dialogSurvey(){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View dialogview = inflater.inflate(R.layout.dialog_survey, null);
        CardView cardNormal = dialogview.findViewById(R.id.dialogSurveyNormal);
        CardView cardOpp = dialogview.findViewById(R.id.dialogSurveyOpp);


        builder.setView(dialogview);
        cardNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setDialogSurvey("Normal");

            }
        });

        cardOpp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setDialogSurvey("Opposite");

            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();

    }


    public void dialogEnd(){

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View dialogview = inflater.inflate(R.layout.dialog_end_survey, null);
        TextView textView = dialogview.findViewById(R.id.textEndsurvey);
        CardView endYa = dialogview.findViewById(R.id.endYa);
        CardView endMenuUtama = dialogview.findViewById(R.id.endMenuUTama);
        final CardView endTidak = dialogview.findViewById(R.id.endTidak);


        builder.setView(dialogview);

        endMenuUtama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setCancelable(false);
                progressDialog.setProgress(ProgressDialog.STYLE_HORIZONTAL);
                TaskEndSurvey taskEndSurvey = new TaskEndSurvey(context, session.getTipesurvey(), progressDialog, new SendDialog() {
                    @Override
                    public void sendDialog(ProgressDialog dialog) {

                        dialog.dismiss();

                        session.saveSPInt(Session.SURVEY, 0 );
                        session.saveSPString(Session.TIPESURVEY, null);

                        session.saveSPInt(Session.SP_NOSEGMENT, 1);
                        session.saveSPInt(Session.SP_SUBSEGMENT, 0 );

                        session.saveSPInt(Session.FOKUS, 0);

                        Intent intent = new Intent(context, Menuutama.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(intent);
                        ((Activity) context).finish();

                    }
                });

                taskEndSurvey.execute("Bisa");

                /*helperFormTerusan = new HelperFormTerusan(context);

                helperFormTerusan.saveLahanEnd(session.getTipesurvey(), session.getKodeprov(), session.getNoruas(), "kiri");
                helperFormTerusan.saveLahanEnd(session.getTipesurvey(), session.getKodeprov(), session.getNoruas(), "kanan");
                helperFormTerusan.saveBahuEnd(session.getTipesurvey(), session.getKodeprov(), session.getNoruas(), "kiri");
                helperFormTerusan.saveBahuEnd(session.getTipesurvey(), session.getKodeprov(), session.getNoruas(), "kanan");
                helperFormTerusan.saveSaluranEnd(session.getTipesurvey(), session.getKodeprov(), session.getNoruas(), "kiri");
                helperFormTerusan.saveSaluranEnd(session.getTipesurvey(), session.getKodeprov(), session.getNoruas(), "kanan");
                helperFormTerusan.saveMedianEnd(session.getTipesurvey(), session.getKodeprov(), session.getNoruas());

                session.saveSPInt(Session.SURVEY, 0 );
                session.saveSPString(Session.TIPESURVEY, null);

                session.saveSPInt(Session.SP_NOSEGMENT, 1);
                session.saveSPInt(Session.SP_SUBSEGMENT, 0 );

                session.saveSPInt(Session.FOKUS, 0);

                Intent intent = new Intent(context, Menuutama.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
                ((Activity) context).finish();*/

            }
        });


        endYa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                helperFormTerusan = new HelperFormTerusan(context);

                helperFormTerusan.saveSegmentEnd(session.getTipesurvey(), session.getKodeprov(), session.getNoruas(), new SendId() {
                    @Override
                    public void hapusGambar(int id) {

                        ProgressDialog progressDialog = new ProgressDialog(context);
                        progressDialog.setCancelable(false);
                        progressDialog.setProgress(ProgressDialog.STYLE_HORIZONTAL);
                        TaskEndSurvey taskEndSurvey = new TaskEndSurvey(context, session.getTipesurvey(), progressDialog, new SendDialog() {
                            @Override
                            public void sendDialog(ProgressDialog dialog) {

                                dialog.dismiss();

                                session.saveSPInt(Session.SURVEY, 0 );
                                session.saveSPString(Session.TIPESURVEY, null);

                                session.saveSPInt(Session.SP_NOSEGMENT, 1);
                                session.saveSPInt(Session.SP_SUBSEGMENT, 0 );

                                session.saveSPInt(Session.FOKUS, 0);

                                Intent intent = new Intent(context, Menuutama.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(intent);
                                ((Activity) context).finish();

                            }
                        });

                        taskEndSurvey.execute("Bisa");


                        /*helperFormTerusan.saveLaneEnd(session.getTipesurvey(), session.getKodeprov(), session.getNoruas(), new SendId() {
                            @Override
                            public void hapusGambar(int id) {

                                helperFormTerusan.saveLahanEnd(session.getTipesurvey(), session.getKodeprov(), session.getNoruas(), "kiri");
                                helperFormTerusan.saveLahanEnd(session.getTipesurvey(), session.getKodeprov(), session.getNoruas(), "kanan");
                                helperFormTerusan.saveBahuEnd(session.getTipesurvey(), session.getKodeprov(), session.getNoruas(), "kiri");
                                helperFormTerusan.saveBahuEnd(session.getTipesurvey(), session.getKodeprov(), session.getNoruas(), "kanan");
                                helperFormTerusan.saveSaluranEnd(session.getTipesurvey(), session.getKodeprov(), session.getNoruas(), "kiri");
                                helperFormTerusan.saveSaluranEnd(session.getTipesurvey(), session.getKodeprov(), session.getNoruas(), "kanan");
                                helperFormTerusan.saveMedianEnd(session.getTipesurvey(), session.getKodeprov(), session.getNoruas());

                                session.saveSPInt(Session.SURVEY, 0 );
                                session.saveSPString(Session.TIPESURVEY, null);

                                session.saveSPInt(Session.SP_NOSEGMENT, 1);
                                session.saveSPInt(Session.SP_SUBSEGMENT, 0 );

                                session.saveSPInt(Session.FOKUS, 0);

                                Intent intent = new Intent(context, Menuutama.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(intent);
                                ((Activity) context).finish();

                            }
                        });*/

                    }
                });

                /*helperFormTerusan.saveLaneEnd(session.getTipesurvey(), session.getKodeprov(), session.getNoruas(),"kiri","L1");
                helperFormTerusan.saveLaneEnd(session.getTipesurvey(), session.getKodeprov(), session.getNoruas(),"kiri","L2");
                helperFormTerusan.saveLaneEnd(session.getTipesurvey(), session.getKodeprov(), session.getNoruas(),"kiri","L3");
                helperFormTerusan.saveLaneEnd(session.getTipesurvey(), session.getKodeprov(), session.getNoruas(),"kiri","L4");
                helperFormTerusan.saveLaneEnd(session.getTipesurvey(), session.getKodeprov(), session.getNoruas(),"kiri","L5");
                helperFormTerusan.saveLaneEnd(session.getTipesurvey(), session.getKodeprov(), session.getNoruas(),"kiri","L6");
                helperFormTerusan.saveLaneEnd(session.getTipesurvey(), session.getKodeprov(), session.getNoruas(),"kiri","L7");
                helperFormTerusan.saveLaneEnd(session.getTipesurvey(), session.getKodeprov(), session.getNoruas(),"kiri","L8");
                helperFormTerusan.saveLaneEnd(session.getTipesurvey(), session.getKodeprov(), session.getNoruas(),"kiri","L9");
                helperFormTerusan.saveLaneEnd(session.getTipesurvey(), session.getKodeprov(), session.getNoruas(),"kiri","L10");

                helperFormTerusan.saveLaneEnd(session.getTipesurvey(), session.getKodeprov(), session.getNoruas(),"kanan","R1");
                helperFormTerusan.saveLaneEnd(session.getTipesurvey(), session.getKodeprov(), session.getNoruas(),"kanan","R2");
                helperFormTerusan.saveLaneEnd(session.getTipesurvey(), session.getKodeprov(), session.getNoruas(),"kanan","R3");
                helperFormTerusan.saveLaneEnd(session.getTipesurvey(), session.getKodeprov(), session.getNoruas(),"kanan","R4");
                helperFormTerusan.saveLaneEnd(session.getTipesurvey(), session.getKodeprov(), session.getNoruas(),"kanan","R5");
                helperFormTerusan.saveLaneEnd(session.getTipesurvey(), session.getKodeprov(), session.getNoruas(),"kanan","R6");
                helperFormTerusan.saveLaneEnd(session.getTipesurvey(), session.getKodeprov(), session.getNoruas(),"kanan","R7");
                helperFormTerusan.saveLaneEnd(session.getTipesurvey(), session.getKodeprov(), session.getNoruas(),"kanan","R8");
                helperFormTerusan.saveLaneEnd(session.getTipesurvey(), session.getKodeprov(), session.getNoruas(),"kanan","R9");
                helperFormTerusan.saveLaneEnd(session.getTipesurvey(), session.getKodeprov(), session.getNoruas(),"kanan","R10");*/





                /*helperFormTerusan.saveLahanEnd("kiri");
                helperFormTerusan.saveLahanEnd("kanan");
                helperFormTerusan.saveSaluranEnd("kiri");
                helperFormTerusan.saveSaluranEnd("kanan");
                helperFormTerusan.saveBahuEnd("kiri");
                helperFormTerusan.saveBahuEnd("kanan");
                helperFormTerusan.saveMedianEnd();
                helperFormTerusan.saveSegmentEnd(session.getTipesurvey());

                helperFormTerusan.saveLaneEnd("L1", session.getTipesurvey());
                helperFormTerusan.saveLaneEnd("L2", session.getTipesurvey());
                helperFormTerusan.saveLaneEnd("L3", session.getTipesurvey());
                helperFormTerusan.saveLaneEnd("L4", session.getTipesurvey());
                helperFormTerusan.saveLaneEnd("L5", session.getTipesurvey());
                helperFormTerusan.saveLaneEnd("L6", session.getTipesurvey());
                helperFormTerusan.saveLaneEnd("L7", session.getTipesurvey());
                helperFormTerusan.saveLaneEnd("L8", session.getTipesurvey());
                helperFormTerusan.saveLaneEnd("L9", session.getTipesurvey());
                helperFormTerusan.saveLaneEnd("L10", session.getTipesurvey());

                helperFormTerusan.saveLaneEnd("R1", session.getTipesurvey());
                helperFormTerusan.saveLaneEnd("R2", session.getTipesurvey());
                helperFormTerusan.saveLaneEnd("R3", session.getTipesurvey());
                helperFormTerusan.saveLaneEnd("R4", session.getTipesurvey());
                helperFormTerusan.saveLaneEnd("R5", session.getTipesurvey());
                helperFormTerusan.saveLaneEnd("R6", session.getTipesurvey());
                helperFormTerusan.saveLaneEnd("R7", session.getTipesurvey());
                helperFormTerusan.saveLaneEnd("R8", session.getTipesurvey());
                helperFormTerusan.saveLaneEnd("R9", session.getTipesurvey());
                helperFormTerusan.saveLaneEnd("R10", session.getTipesurvey());

                session.saveSPListIndex(Session.SESSION_SEGMENT, null);
                session.saveSPListIndex(Session.SESSION_LAHANKIRI, null);
                session.saveSPListIndex(Session.SESSION_LAHANKANAN, null);
                session.saveSPListIndex(Session.SESSION_SALURANKIRI, null);
                session.saveSPListIndex(Session.SESSION_SALURANKANAN, null);
                session.saveSPListIndex(Session.SESSION_BAHUKIRI, null);
                session.saveSPListIndex(Session.SESSION_BAHUKANAN, null);
                session.saveSPListIndex(Session.SESSION_MEDIAN, null);


                session.saveSPListIndex(Session.SESSION_L1, null);
                session.saveSPListIndex(Session.SESSION_L2, null);
                session.saveSPListIndex(Session.SESSION_L3, null);
                session.saveSPListIndex(Session.SESSION_L4, null);
                session.saveSPListIndex(Session.SESSION_L5, null);
                session.saveSPListIndex(Session.SESSION_L6, null);
                session.saveSPListIndex(Session.SESSION_L7, null);
                session.saveSPListIndex(Session.SESSION_L8, null);
                session.saveSPListIndex(Session.SESSION_L9, null);
                session.saveSPListIndex(Session.SESSION_L10, null);

                session.saveSPListIndex(Session.SESSION_R1, null);
                session.saveSPListIndex(Session.SESSION_R2, null);
                session.saveSPListIndex(Session.SESSION_R3, null);
                session.saveSPListIndex(Session.SESSION_R4, null);
                session.saveSPListIndex(Session.SESSION_R5, null);
                session.saveSPListIndex(Session.SESSION_R6, null);
                session.saveSPListIndex(Session.SESSION_R7, null);
                session.saveSPListIndex(Session.SESSION_R8, null);
                session.saveSPListIndex(Session.SESSION_R9, null);
                session.saveSPListIndex(Session.SESSION_R10, null);*/


            }
        });




        final AlertDialog dialog = builder.create();

        dialog.setOnShowListener( new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {

                endTidak.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });

        dialog.show();

    }

    private void setDialogSurvey(String tipeSurvey){


        String dbSurvey = dbTemporari.cekSurveyTemporari(session.getKodeprov(), session.getNoruas());
        HelperFormTerusan helperFormTerusan = new HelperFormTerusan(context);


        if(dbSurvey.equals("Detail")){

            dbTemporari.updateTemporariDetail(tipeSurvey, session.getKodeprov(), session.getNoruas());

            if(dbTemporari.cekSurveyTemporari(session.getKodeprov(), session.getNoruas()).equals(tipeSurvey)){
                //helperFormTerusan.resumeSurvey(tipeSurvey);
            }

            Intent intent = getIntentSurvey(tipeSurvey);
            context.startActivity(intent);
            ((Activity) context).finish();

        }else if(dbSurvey.equals(tipeSurvey)){
            lanjutDialog(tipeSurvey, dbSurvey);

        }else{

            resumeDialog(tipeSurvey, dbSurvey);

        }

    }

    public void lanjutDialog(final String tipeSurvey, String dbSurvey){

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View dialogview = inflater.inflate(R.layout.dialog_lanjut_survey, null);

        TextView textView = dialogview.findViewById(R.id.dialogLanjutHasil);
        CardView endYa = dialogview.findViewById(R.id.dialogLanjutLanjut);
        final CardView endTidak = dialogview.findViewById(R.id.dialogLanjutUlang);

        textView.setText(dbSurvey);
        if(dbSurvey.equals("Normal")){
            textView.setTextColor(Color.parseColor("#4159BE"));

        }else{
            textView.setTextColor(Color.parseColor("#AA2929"));

        }


        builder.setView(dialogview);


        endYa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dbTemporari.deleteTemporariJenis(session.getKodeprov(), session.getNoruas(), "Detail");

                helperFormTerusan = new HelperFormTerusan(context);
               // helperFormTerusan.resumeSurvey(tipeSurvey);

                Intent intent = getIntentSurvey(tipeSurvey);
                context.startActivity(intent);
                ((Activity) context).finish();

            }
        });

        endTidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogTab.setDialogAlert(context, "Semua perubahan akan dihapus, apakah anda yakin ? ", new IntTab() {
                    @Override
                    public void sendPosition(int position) {

                        if(position==1){

                            DbRuas dbRuas = new DbRuas(context);
                            dbRuas.updateFlag(session.getKodeprov(), session.getNoruas(), 1);
                            dbTemporari.deleteAllTemporari(session.getKodeprov(), session.getNoruas());
                            Intent intent = getIntentSurvey(tipeSurvey);
                            context.startActivity(intent);
                            ((Activity) context).finish();

                        }

                    }
                });


            }
        });


        final AlertDialog dialog = builder.create();
        dialog.show();

    }


    public void resumeDialog(final String tipeSurvey, String dbSurvey){

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View dialogview = inflater.inflate(R.layout.dialog_resume_survey, null);

        TextView textView = dialogview.findViewById(R.id.dialogResumeHasil);
        CardView endYa = dialogview.findViewById(R.id.dialogResumeYa);
        final CardView endTidak = dialogview.findViewById(R.id.dialogResumeTidak);

        textView.setText(dbSurvey);
        if(dbSurvey.equals("Normal")){
            textView.setTextColor(Color.parseColor("#4159BE"));

        }else{
            textView.setTextColor(Color.parseColor("#AA2929"));

        }


        builder.setView(dialogview);


        endYa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                dbTemporari.deleteAllTemporari(session.getKodeprov(), session.getNoruas());

                Intent intent = getIntentSurvey(tipeSurvey);
                context.startActivity(intent);
                ((Activity) context).finish();

            }
        });




        final AlertDialog dialog = builder.create();

        dialog.setOnShowListener( new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {

                endTidak.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });

        dialog.show();

    }

    private Intent getIntentSurvey(String tipeSurvey){

        int sessionSegment, sessionSub;

        if(tipeSurvey.equals("Normal")){
            sessionSegment = 1;
            sessionSub = 0;
        }else{
            sessionSegment = dbSpinner.getMaksSegment(session.getKodeprov(), session.getNoruas());
            sessionSub = dbSpinner.getMaksSegmentSub(session.getKodeprov(), session.getNoruas(), sessionSegment);
        }

        session.saveSPString(Session.TIPESURVEY, tipeSurvey);
        session.saveSPInt(Session.SURVEY,1);

        session.saveSPInt(Session.SP_NOSEGMENT, sessionSegment);
        session.saveSPInt(Session.SP_SUBSEGMENT, sessionSub);

        Intent intent = new Intent(context, FormTabUtama.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        return intent;
    }


}
