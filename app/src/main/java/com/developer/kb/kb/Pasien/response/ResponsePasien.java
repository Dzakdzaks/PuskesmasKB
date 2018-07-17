
package com.developer.kb.kb.Pasien.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponsePasien {

    @SerializedName("msg")
    private String mMsg;
    @SerializedName("pasien")
    private List<Pasien> mPasien;
    @SerializedName("result")
    private Long mResult;

    public String getMsg() {
        return mMsg;
    }

    public void setMsg(String msg) {
        mMsg = msg;
    }

    public List<Pasien> getPasien() {
        return mPasien;
    }

    public void setPasien(List<Pasien> pasien) {
        mPasien = pasien;
    }

    public Long getResult() {
        return mResult;
    }

    public void setResult(Long result) {
        mResult = result;
    }

}
