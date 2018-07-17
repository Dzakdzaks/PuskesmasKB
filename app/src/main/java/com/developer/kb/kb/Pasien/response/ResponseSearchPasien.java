
package com.developer.kb.kb.Pasien.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ResponseSearchPasien {

    @SerializedName("resultsproduk")
    private List<Resultsproduk> mResultsproduk;
    @SerializedName("value")
    private int mValue;

    public List<Resultsproduk> getResultsproduk() {
        return mResultsproduk;
    }

    public void setResultsproduk(List<Resultsproduk> resultsproduk) {
        mResultsproduk = resultsproduk;
    }

    public int getValue() {
        return mValue;
    }

    public void setValue(int value) {
        mValue = value;
    }

}
