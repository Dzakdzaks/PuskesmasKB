
package com.developer.kb.kb.Posyandu.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ResponsePosyandu {

    @SerializedName("msg")
    private String mMsg;
    @SerializedName("posyandu")
    private List<Posyandu> mPosyandu;
    @SerializedName("result")
    private Long mResult;

    public String getMsg() {
        return mMsg;
    }

    public void setMsg(String msg) {
        mMsg = msg;
    }

    public List<Posyandu> getPosyandu() {
        return mPosyandu;
    }

    public void setPosyandu(List<Posyandu> posyandu) {
        mPosyandu = posyandu;
    }

    public Long getResult() {
        return mResult;
    }

    public void setResult(Long result) {
        mResult = result;
    }

}
