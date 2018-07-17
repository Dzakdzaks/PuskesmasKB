
package com.developer.kb.kb.Layanan.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ResponseLayanan {

    @SerializedName("layanan")
    private List<Layanan> mLayanan;
    @SerializedName("msg")
    private String mMsg;
    @SerializedName("result")
    private Long mResult;

    public List<Layanan> getLayanan() {
        return mLayanan;
    }

    public void setLayanan(List<Layanan> layanan) {
        mLayanan = layanan;
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
