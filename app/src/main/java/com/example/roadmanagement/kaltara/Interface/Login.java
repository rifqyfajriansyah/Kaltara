package com.example.roadmanagement.kaltara.Interface;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login {
    @SerializedName("username")
    @Expose
    String username;
    @SerializedName("password")
    @Expose
    String password;
    @SerializedName("kodelogin")
    @Expose
    int kodelogin;
    @SerializedName("kodepv")
    @Expose
    String kodeprov;
    @SerializedName("namapv")
    @Expose
    String namaprov;
    @SerializedName("kodebl")
    @Expose
    String kodebalai;
    @SerializedName("namabl")
    @Expose
    String namabalai;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getKodelogin() {
        return kodelogin;
    }

    public void setKodelogin(int kodelogin) {
        this.kodelogin = kodelogin;
    }

    public String getKodeprov() {
        return kodeprov;
    }

    public void setKodeprov(String kodeprov) {
        this.kodeprov = kodeprov;
    }

    public String getNamaprov() {
        return namaprov;
    }

    public void setNamaprov(String namaprov) {
        this.namaprov = namaprov;
    }

    public String getKodebalai() {
        return kodebalai;
    }

    public void setKodebalai(String kodebalai) {
        this.kodebalai = kodebalai;
    }

    public String getNamabalai() {
        return namabalai;
    }

    public void setNamabalai(String namabalai) {
        this.namabalai = namabalai;
    }
}
