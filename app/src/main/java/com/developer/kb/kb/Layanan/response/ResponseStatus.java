
package com.developer.kb.kb.Layanan.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ResponseStatus {

    @SerializedName("msg")
    private String mMsg;
    @SerializedName("result")
    private Long mResult;
    @SerializedName("status")
    private List<Status> mStatus;

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

    public List<Status> getStatus() {
        return mStatus;
    }

    public void setStatus(List<Status> status) {
        mStatus = status;
    }

}
