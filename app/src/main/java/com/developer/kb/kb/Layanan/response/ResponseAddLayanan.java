
package com.developer.kb.kb.Layanan.response;

import com.google.gson.annotations.SerializedName;


public class ResponseAddLayanan {

    @SerializedName("msg")
    private String mMsg;
    @SerializedName("result")
    private Long mResult;

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
