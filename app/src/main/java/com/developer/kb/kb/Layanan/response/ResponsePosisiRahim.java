
package com.developer.kb.kb.Layanan.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ResponsePosisiRahim {

    @SerializedName("msg")
    private String mMsg;
    @SerializedName("posisirahim")
    private List<Posisirahim> mPosisirahim;
    @SerializedName("result")
    private Long mResult;

    public String getMsg() {
        return mMsg;
    }

    public void setMsg(String msg) {
        mMsg = msg;
    }

    public List<Posisirahim> getPosisirahim() {
        return mPosisirahim;
    }

    public void setPosisirahim(List<Posisirahim> posisirahim) {
        mPosisirahim = posisirahim;
    }

    public Long getResult() {
        return mResult;
    }

    public void setResult(Long result) {
        mResult = result;
    }

}
