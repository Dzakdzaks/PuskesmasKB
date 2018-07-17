
package com.developer.kb.kb.Laporan.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseKartu {

    @SerializedName("laporankartu")
    private List<Laporankartu> mLaporankartu;
    @SerializedName("msg")
    private String mMsg;
    @SerializedName("result")
    private Long mResult;

    public List<Laporankartu> getLaporankartu() {
        return mLaporankartu;
    }

    public void setLaporankartu(List<Laporankartu> laporankartu) {
        mLaporankartu = laporankartu;
    }

    public String getMsg() {
        return mMsg;
    }

    public void setMsg(String msg) {
        mMsg = msg;
    }

    public Long getResult() {
        return mResult;
    }

    public void setResult(Long result) {
        mResult = result;
    }

}
