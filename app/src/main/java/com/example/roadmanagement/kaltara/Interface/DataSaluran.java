package com.example.roadmanagement.kaltara.Interface;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataSaluran {
    public static final String TABLE_NAME = "datasaluran";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOPROV = "noprov";
    public static final String COLUMN_RUAS = "noruas";
    public static final String COLUMN_SEGMENT = "nosegment";
    public static final String COLUMN_SUB_SEGMENT = "subsegment";
    public static final String COLUMN_POSISI = "posisi";
    public static final String COLUMN_TIPE = "tipesaluran";
    public static final String COLUMN_PERMUKAAN_SAMPING = "permukaanSamping";
    public static final String COLUMN_JENIS_PENAMPANG = "jenisPenampang";
    public static final String COLUMN_LEBAR = "lebarsaluran";
    public static final String COLUMN_DALAM = "dalamsaluran";
    public static final String COLUMN_TINGGI_AIR = "tinggiAir";
    public static final String COLUMN_TINGGI_SEDIMEN = "tinggiSedimen";
    public static final String COLUMN_JENIS_KONSTRUKSI = "jenisKonstruksi";
    public static final String COLUMN_KONDISI_SALURAN = "kondisiSaluran";
    public static final String COLUMN_GAMBAR = "gambarsaluran";
    public static final String COLUMN_GAMBAR_ICON = "gambarsaluranicon";
    public static final String COLUMN_LOKASI = "lokasisaluran";
    public static final String COLUMN_KERUSAKAN = "kerusakansaluran";
    public static final String COLUMN_PANJANGKR = "panjangkrsaluran";
    public static final String COLUMN_GAMBARKR = "gambarkrsaluran";
    public static final String COLUMN_GAMBARKRICON = "gambarkrsaluranicon";
    public static final String COLUMN_LOKASIKR = "lokasikr";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NOPROV + " TEXT,"
                    + COLUMN_RUAS + " TEXT,"
                    + COLUMN_SEGMENT + " INTEGER,"
                    + COLUMN_SUB_SEGMENT + " INTEGER,"
                    + COLUMN_POSISI + " TEXT,"
                    + COLUMN_TIPE + " TEXT,"
                    + COLUMN_PERMUKAAN_SAMPING + " TEXT,"
                    + COLUMN_JENIS_PENAMPANG + " TEXT,"
                    + COLUMN_LEBAR+ " NUMERIC,"
                    + COLUMN_DALAM + " NUMERIC,"
                    + COLUMN_TINGGI_AIR + " NUMERIC,"
                    + COLUMN_TINGGI_SEDIMEN + " NUMERIC,"
                    + COLUMN_JENIS_KONSTRUKSI + " TEXT,"
                    + COLUMN_KONDISI_SALURAN + " TEXT,"
                    + COLUMN_GAMBAR + " TEXT,"
                    + COLUMN_GAMBAR_ICON+ " TEXT,"
                    + COLUMN_LOKASI+ " TEXT,"
                    + COLUMN_KERUSAKAN + " TEXT,"
                    + COLUMN_PANJANGKR + " INTEGER,"
                    + COLUMN_GAMBARKR + " TEXT,"
                    + COLUMN_GAMBARKRICON + " TEXT,"
                    + COLUMN_LOKASIKR+ " TEXT"
                    + ")";

    int id;
    @SerializedName("no_prov")
    @Expose
    String noprov;
    @SerializedName("no_ruas")
    @Expose
    String noruas;
    @SerializedName("no_seg")
    @Expose
    int nosegment;
    @SerializedName("sub_seg")
    @Expose
    int subsegment;
    @SerializedName("posisi")
    @Expose
    String posisi;
    @SerializedName("tipe")
    @Expose
    String tipeSaluran;

    @SerializedName("samping")
    @Expose
    String permukaanSamping;
    @SerializedName("penampang")
    @Expose
    String jenisPenampang;
    @SerializedName("lebar")
    @Expose
    float lebarSaluran;
    @SerializedName("dalam")
    @Expose
    float dalamSaluran;
    @SerializedName("tinggi")
    @Expose
    float tinggiAir;
    @SerializedName("sedimen")
    @Expose
    float tinggiSedimen;
    @SerializedName("konstruksi")
    @Expose
    String jenisKonstruksi;
    @SerializedName("kondisi")
    @Expose
    String kondisiSaluran;

    String gambarSaluran;
    String gambarSaluranicon;
    String lokasiSaluran;
    String kerusakanSaluran;
    int panjangKr;
    String gambarKr;
    String gambarKricon;
    String lokasikr;

    public DataSaluran(String noprov, String noruas, int nosegment, int subsegment, String posisi, String tipeSaluran, String permukaanSamping, String jenisPenampang, float lebarSaluran, float dalamSaluran, float tinggiAir, float tinggiSedimen, String jenisKonstruksi, String kondisiSaluran, String gambarSaluran, String gambarSaluranicon, String lokasiSaluran) {
        this.noprov = noprov;
        this.noruas = noruas;
        this.nosegment = nosegment;
        this.subsegment = subsegment;
        this.posisi = posisi;
        this.tipeSaluran = tipeSaluran;
        this.permukaanSamping = permukaanSamping;
        this.jenisPenampang = jenisPenampang;
        this.lebarSaluran = lebarSaluran;
        this.dalamSaluran = dalamSaluran;
        this.tinggiAir = tinggiAir;
        this.tinggiSedimen = tinggiSedimen;
        this.jenisKonstruksi = jenisKonstruksi;
        this.kondisiSaluran = kondisiSaluran;
        this.gambarSaluran = gambarSaluran;
        this.gambarSaluranicon = gambarSaluranicon;
        this.lokasiSaluran = lokasiSaluran;
    }

    public String getLokasiSaluran() {
        return lokasiSaluran;
    }

    public void setLokasiSaluran(String lokasiSaluran) {
        this.lokasiSaluran = lokasiSaluran;
    }

    public String getLokasikr() {
        return lokasikr;
    }

    public void setLokasikr(String lokasikr) {
        this.lokasikr = lokasikr;
    }

    public float getLebarSaluran() {
        return lebarSaluran;
    }

    public void setLebarSaluran(float lebarSaluran) {
        this.lebarSaluran = lebarSaluran;
    }

    public float getDalamSaluran() {
        return dalamSaluran;
    }

    public void setDalamSaluran(float dalamSaluran) {
        this.dalamSaluran = dalamSaluran;
    }

    public String getPosisi() {
        return posisi;
    }

    public void setPosisi(String posisi) {
        this.posisi = posisi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoprov() {
        return noprov;
    }

    public void setNoprov(String noprov) {
        this.noprov = noprov;
    }

    public String getNoruas() {
        return noruas;
    }

    public void setNoruas(String noruas) {
        this.noruas = noruas;
    }

    public int getNosegment() {
        return nosegment;
    }

    public void setNosegment(int nosegment) {
        this.nosegment = nosegment;
    }

    public String getTipeSaluran() {
        return tipeSaluran;
    }

    public void setTipeSaluran(String tipeSaluran) {
        this.tipeSaluran = tipeSaluran;
    }





    public String getGambarSaluran() {
        return gambarSaluran;
    }

    public void setGambarSaluran(String gambarSaluran) {
        this.gambarSaluran = gambarSaluran;
    }

    public String getGambarSaluranicon() {
        return gambarSaluranicon;
    }

    public void setGambarSaluranicon(String gambarSaluranicon) {
        this.gambarSaluranicon = gambarSaluranicon;
    }

    public String getKerusakanSaluran() {
        return kerusakanSaluran;
    }

    public void setKerusakanSaluran(String kerusakanSaluran) {
        this.kerusakanSaluran = kerusakanSaluran;
    }



    public int getPanjangKr() {
        return panjangKr;
    }

    public void setPanjangKr(int panjangKr) {
        this.panjangKr = panjangKr;
    }

    public String getGambarKr() {
        return gambarKr;
    }

    public void setGambarKr(String gambarKr) {
        this.gambarKr = gambarKr;
    }

    public String getGambarKricon() {
        return gambarKricon;
    }

    public void setGambarKricon(String gambarKricon) {
        this.gambarKricon = gambarKricon;
    }

    public int getSubsegment() {
        return subsegment;
    }

    public void setSubsegment(int subsegment) {
        this.subsegment = subsegment;
    }

    public String getPermukaanSamping() {
        return permukaanSamping;
    }

    public void setPermukaanSamping(String permukaanSamping) {
        this.permukaanSamping = permukaanSamping;
    }

    public String getJenisPenampang() {
        return jenisPenampang;
    }

    public void setJenisPenampang(String jenisPenampang) {
        this.jenisPenampang = jenisPenampang;
    }

    public float getTinggiAir() {
        return tinggiAir;
    }

    public void setTinggiAir(float tinggiAir) {
        this.tinggiAir = tinggiAir;
    }

    public float getTinggiSedimen() {
        return tinggiSedimen;
    }

    public void setTinggiSedimen(float tinggiSedimen) {
        this.tinggiSedimen = tinggiSedimen;
    }

    public String getJenisKonstruksi() {
        return jenisKonstruksi;
    }

    public void setJenisKonstruksi(String jenisKonstruksi) {
        this.jenisKonstruksi = jenisKonstruksi;
    }

    public String getKondisiSaluran() {
        return kondisiSaluran;
    }

    public void setKondisiSaluran(String kondisiSaluran) {
        this.kondisiSaluran = kondisiSaluran;
    }
}
