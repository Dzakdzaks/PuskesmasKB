
package com.developer.kb.kb.Layanan.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ResponseTumor {

    @SerializedName("msg")
    private String mMsg;
    @SerializedName("result")
    private Long mResult;
    @SerializedName("tumor")
    private List<Tumor> mTumor;

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

    public List<Tumor> getTumor() {
        return mTumor;
    }

    public void setTumor(List<Tumor> tumor) {
        mTumor = tumor;
    }

}
