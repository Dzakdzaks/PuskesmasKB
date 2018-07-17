
package com.developer.kb.kb.Layanan.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ResponsePasienByPos {

    @SerializedName("msg")
    private String mMsg;
    @SerializedName("pasienbypos")
    private List<Pasienbypo> mPasienbypos;
    @SerializedName("result")
    private Long mResult;

    public String getMsg() {
        return mMsg;
    }

    public void setMsg(String msg) {
        mMsg = msg;
    }

    public List<Pasienbypo> getPasienbypos() {
        return mPasienbypos;
    }

    public void setPasienbypos(List<Pasienbypo> pasienbypos) {
        mPasienbypos = pasienbypos;
    }

    public Long getResult() {
        return mResult;
    }

    public void setResult(Long result) {
        mResult = result;
    }

}
