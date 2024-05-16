package com.example.roadmanagement.kaltara.Tabel;

public class ClassModel {

    public interface Passdata {
        void stateChanged();
    }

    private static ClassModel mInstance;
    private Passdata mListener;
    private String angka;

    private ClassModel() {}

    public static ClassModel getInstance() {
        if(mInstance == null) {
            mInstance = new ClassModel();
        }
        return mInstance;
    }

    public void setListener(Passdata listener) {
        mListener = listener;
    }

    public void changeState(String angka) {
        if(mListener != null) {
            this.angka = angka;
            notifyStateChange();
        }
    }

    public String getAngka() {
        return angka;
    }

    private void notifyStateChange() {
        mListener.stateChanged();
    }

}
