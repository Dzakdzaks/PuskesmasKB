
package com.developer.kb.kb.Pasien.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseMetode {

    @SerializedName("metode")
    private List<Metode> mMetode;
    @SerializedName("msg")
    private String mMsg;
    @SerializedName("result")
    private Long mResult;

    public List<Metode> getMetode() {
        return mMetode;
    }

    public void setMetode(List<Metode> metode) {
        mMetode = metode;
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
