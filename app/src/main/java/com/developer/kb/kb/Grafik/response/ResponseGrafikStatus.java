
package com.developer.kb.kb.Grafik.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseGrafikStatus {

    @SerializedName("kb_aktif")
    private List<String> mKbAktif;
    @SerializedName("kb_pasca_persalinan")
    private List<String> mKbPascaPersalinan;
    @SerializedName("msg")
    private String mMsg;
    @SerializedName("jml_pos")
    private int mJmlPos;
    @SerializedName("pus_4t_berkb")
    private List<String> mPus4TBerkb;
    @SerializedName("result")
    private Long mResult;
    @SerializedName("posyandu")
    private List<String> mPosyandu;

    public List<String> getKbAktif() {
        return mKbAktif;
    }

    public void setKbAktif(List<String> kbAktif) {
        mKbAktif = kbAktif;
    }

    public List<String> getKbPascaPersalinan() {
        return mKbPascaPersalinan;
    }

    public void setKbPascaPersalinan(List<String> kbPascaPersalinan) {
        mKbPascaPersalinan = kbPascaPersalinan;
    }

    public String getMsg() {
        return mMsg;
    }

    public int getmJmlPos() {
        return mJmlPos;
    }

    public void setmJmlPos(int mJmlPos) {
        this.mJmlPos = mJmlPos;
    }

    public void setMsg(String msg) {
        mMsg = msg;
    }

    public List<String> getPus4TBerkb() {
        return mPus4TBerkb;
    }

    public void setPus4TBerkb(List<String> pus4TBerkb) {
        mPus4TBerkb = pus4TBerkb;
    }

    public List<String> getPosyandu() {
        return mPosyandu;
    }

    public void setPosyandu(List<String> Posyandu) {
        mPosyandu = Posyandu;
    }

    public Long getResult() {
        return mResult;
    }

    public void setResult(Long result) {
        mResult = result;
    }

}
