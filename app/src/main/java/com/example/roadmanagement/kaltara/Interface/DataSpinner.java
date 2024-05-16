package com.example.roadmanagement.kaltara.Interface;

public class DataSpinner {
    String value;
    String textview;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTextview() {
        return textview;
    }

    public void setTextview(String textview) {
        this.textview = textview;
    }

    public DataSpinner(String value, String textview) {
        this.value = value;
        this.textview = textview;
    }
}
