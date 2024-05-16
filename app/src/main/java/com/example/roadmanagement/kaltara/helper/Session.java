package com.example.roadmanagement.kaltara.helper;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Session {
    public static final String SP_AIS_APP = "spKaltara";

    public static final String SP_KODEPROV = "kodeprov";
    public static final String SP_NORUAS = "noruas";
    public static final String SP_NOSEGMENT = "nosegment";
    public static final String SP_SUBSEGMENT = "subsegment";
    public static final String SP_KM = "km";
    public static final String SP_STA = "sta";
    public static final String SP_JMLSEGMENT = "jmlsegment";
    public static final String LOGIN = "login";
    public static final String USER = "user";
    public static final String FOKUS = "fokusku";

    public static final String NAMAPROV = "namaprov";



    public static final String DOWNLOADALL = "downloadall";
    public static final String DOWNLOADSPINNER = "spinnerku";
    public static final String SEGMENTPOSISI = "dsegment";
    public static final String RUASPOSISI = "druas";
    public static final String DOWNLOADATARUAS = "dataruas";
    public static final String DOWNLOADDATASEGMENT = "datasegment";
    public static final String DOWNLOADDATALANE = "datalane";
    public static final String DETAILDATALANE = "detailane";

    public static final String DOWNLOADSINGLE = "segmentsingle";


    public static final String DOWNLOAD_TAMBAHAN = "downloadtambahan";

    public static final String POSISI = "posisi";
    public static final String POSISILAJUR = "posisilajur";

    public static final String SINKRON_HELPER = "sinkronhelper";

    public static final String FORM_INFO = "forminfo";
    //public static final String VALUE1 = "value1";
    //public static final String VALUE2 = "value2";
   // public static final String VALUE3 = "value3";

    public static final String SURVEY = "survey";
    public static final String TIPESURVEY = "tipesurvey";

    public static final String POSISITABEL = "posisitable";

    public static final String TIPEUSER = "tipeuser";

    public static final String SESSION_LAHANKIRI = "lahankiriIndex";
    public static final String SESSION_LAHANKANAN = "lahankananIndex";
    public static final String SESSION_BAHUKIRI = "bahukiriIndex";
    public static final String SESSION_BAHUKANAN = "bahukananIndex";
    public static final String SESSION_SALURANKIRI = "salurankiriIndex";
    public static final String SESSION_SALURANKANAN = "salurankananIndex";
    public static final String SESSION_MEDIAN = "medianIndex";
    public static final String SESSION_SEGMENT = "segmentIndex";

    public static final String SESSION_L1 = "l1Index";
    public static final String SESSION_L2 = "l2Index";
    public static final String SESSION_L3 = "l3Index";
    public static final String SESSION_L4 = "l4Index";
    public static final String SESSION_L5 = "l5Index";
    public static final String SESSION_L6 = "l6Index";
    public static final String SESSION_L7 = "l7Index";
    public static final String SESSION_L8 = "l8Index";
    public static final String SESSION_L9 = "l9Index";
    public static final String SESSION_L10 = "l10Index";

    public static final String SESSION_R1 = "r1Index";
    public static final String SESSION_R2 = "r2Index";
    public static final String SESSION_R3 = "r3Index";
    public static final String SESSION_R4 = "r4Index";
    public static final String SESSION_R5 = "r5Index";
    public static final String SESSION_R6 = "r6Index";
    public static final String SESSION_R7 = "r7Index";
    public static final String SESSION_R8 = "r8Index";
    public static final String SESSION_R9 = "r9Index";
    public static final String SESSION_R10 = "r10Index";

    public static final String TAB_ATRIBUT = "atributTab";
    public static final String TAB_ATRIBUT_LANE = "atributTabLane";
    public static final String FLAG_FORM = "flagform";
    public static final String FLAG_TAB = "flagtab";



    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public Session(Context context) {
        sp = context.getSharedPreferences(SP_AIS_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String keySP, String value) {
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSPInt(String keySP, int value) {
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value) {
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public void saveSPListIndex(String keySP, Set<String> value) {
        spEditor.putStringSet(keySP, value);
        spEditor.commit();
    }

    public String getKodeprov() {
        return sp.getString(SP_KODEPROV, null);
    }
    public String getNoruas() {
        return sp.getString(SP_NORUAS, null);
    }
    public int getNosegment() {
        return sp.getInt(SP_NOSEGMENT, 0);
    }
    public String getKM() {
        return sp.getString(SP_KM, null);
    }
    public String getSTA() {
        return sp.getString(SP_STA, null);
    }

    public String getNamaprov() {
        return sp.getString(NAMAPROV, null);
    }

    public String getUserku() {
        return sp.getString(USER, null);
    }

    public int getJmlSegment() {
        return sp.getInt(SP_JMLSEGMENT, 0);
    }

    public int getFokus() {
        return sp.getInt(FOKUS, 0);
    }

    public int getTabAtribut() {
        return sp.getInt(TAB_ATRIBUT, 0);
    }

    public int getTabAtributLane() {
        return sp.getInt(TAB_ATRIBUT_LANE, 0);
    }


    public  boolean getValueLogin(){
        return sp.getBoolean(LOGIN, false);
    }

    public int DataRuasDownload(){
        return sp.getInt(DOWNLOADATARUAS, 0);
    }

    public int DataSegmentDownload(){
        return sp.getInt(DOWNLOADDATASEGMENT, 0);
    }

    public int DataLaneDownload(){
        return sp.getInt(DOWNLOADDATALANE, 0);
    }

    public int PosisiSegment(){
        return sp.getInt(SEGMENTPOSISI, 0);
    }

    public int PosisiRuas(){
        return sp.getInt(RUASPOSISI, 1);
    }

    public int LanePosisi(){
        return sp.getInt(DETAILDATALANE, 0);
    }

    public int DownloadAll(){
        return sp.getInt(DOWNLOADALL, 0);
    }

    public int DataSpinnerDownload(){
        return sp.getInt(DOWNLOADSPINNER, 0);
    }

    public int getDownloadTambah(){
        return sp.getInt(DOWNLOAD_TAMBAHAN, 0);
    }


    public String getPosisi() {
        return sp.getString(POSISI, null);
    }
    public String getPosisiLajur() {
        return sp.getString(POSISILAJUR, null);
    }

    public String getSinkronHelper() {
        return sp.getString(SINKRON_HELPER, null);
    }

    public void setIndexData(String key, ArrayList<String> list){

        Set<String> mySet = new HashSet<String>(list);
        saveSPListIndex(key, mySet);
    }

    public Set<String> getIndexData(String key){

        return sp.getStringSet(key, null);

    }

    public int getSurvey(){
        return sp.getInt(SURVEY, 0);
    }
    public String getTipesurvey(){
        return sp.getString(TIPESURVEY, "Normal");
    }


    public String getFormInfo() {
        return sp.getString(FORM_INFO, null);
    }

    public int getPosisiTable() {
        return sp.getInt(POSISITABEL, 0);
    }

    public int getUserTipe() {
        return sp.getInt(TIPEUSER, 0);
    }

    public int getSubsegment() {
        return sp.getInt(SP_SUBSEGMENT, 0);
    }

    public boolean getFlagForm(){ return sp.getBoolean(FLAG_FORM, false);}
    public boolean getFlagTab(){ return sp.getBoolean(FLAG_TAB, false);}


}
