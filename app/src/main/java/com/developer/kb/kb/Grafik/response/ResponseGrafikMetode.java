
package com.developer.kb.kb.Grafik.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseGrafikMetode {

    @SerializedName("jml_pos")
    private int mJmlPos;
    @SerializedName("kondom")
    private List<String> mKondom;
    @SerializedName("msg")
    private String mMsg;
    @SerializedName("pil")
    private List<String> mPil;
    @SerializedName("posyandu")
    private List<String> mPosyandu;
    @SerializedName("result")
    private Long mResult;
    @SerializedName("suntik")
    private List<String> mSuntik;

    public int getJmlPos() {
        return mJmlPos;
    }

    public void setJmlPos(int jmlPos) {
        mJmlPos = jmlPos;
    }

    public List<String> getKondom() {
        return mKondom;
    }

    public void setKondom(List<String> kondom) {
        mKondom = kondom;
    }

    public String getMsg() {
        return mMsg;
    }

    public void setMsg(String msg) {
        mMsg = msg;
    }

    public List<String> getPil() {
        return mPil;
    }

    public void setPil(List<String> pil) {
        mPil = pil;
    }

    public List<String> getPosyandu() {
        return mPosyandu;
    }

    public void setPosyandu(List<String> posyandu) {
        mPosyandu = posyandu;
    }

    public Long getResult() {
        return mResult;
    }

    public void setResult(Long result) {
        mResult = result;
    }

    public List<String> getSuntik() {
        return mSuntik;
    }

    public void setSuntik(List<String> suntik) {
        mSuntik = suntik;
    }

}
