
package com.developer.kb.kb.Laporan.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseLaporanLayanan {

    @SerializedName("jml_pos")
    private int mJmlPos;
    @SerializedName("KB_Aktif")
    private List<String> mKBAktif;
    @SerializedName("KB_Pasca_Persalinan")
    private List<String> mKBPascaPersalinan;
    @SerializedName("Kondom")
    private List<String> mKondom;
    @SerializedName("Kondom1")
    private List<String> mKondom1;
    @SerializedName("msg")
    private String mMsg;
    @SerializedName("PUS_4T_BerKB")
    private List<String> mPUS4TBerKB;
    @SerializedName("Pil")
    private List<String> mPil;
    @SerializedName("Pil1")
    private List<String> mPil1;
    @SerializedName("posyandu")
    private List<String> mPosyandu;
    @SerializedName("result")
    private Long mResult;
    @SerializedName("Suntik")
    private List<String> mSuntik;
    @SerializedName("Suntik1")
    private List<String> mSuntik1;

    public int getJmlPos() {
        return mJmlPos;
    }

    public void setJmlPos(int jmlPos) {
        mJmlPos = jmlPos;
    }

    public List<String> getKBAktif() {
        return mKBAktif;
    }

    public void setKBAktif(List<String> kBAktif) {
        mKBAktif = kBAktif;
    }

    public List<String> getKBPascaPersalinan() {
        return mKBPascaPersalinan;
    }

    public void setKBPascaPersalinan(List<String> kBPascaPersalinan) {
        mKBPascaPersalinan = kBPascaPersalinan;
    }

    public List<String> getKondom() {
        return mKondom;
    }

    public void setKondom(List<String> kondom) {
        mKondom = kondom;
    }

    public List<String> getKondom1() {
        return mKondom1;
    }

    public void setKondom1(List<String> kondom1) {
        mKondom1 = kondom1;
    }

    public String getMsg() {
        return mMsg;
    }

    public void setMsg(String msg) {
        mMsg = msg;
    }

    public List<String> getPUS4TBerKB() {
        return mPUS4TBerKB;
    }

    public void setPUS4TBerKB(List<String> pUS4TBerKB) {
        mPUS4TBerKB = pUS4TBerKB;
    }

    public List<String> getPil() {
        return mPil;
    }

    public void setPil(List<String> pil) {
        mPil = pil;
    }

    public List<String> getPil1() {
        return mPil1;
    }

    public void setPil1(List<String> pil1) {
        mPil1 = pil1;
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

    public List<String> getSuntik1() {
        return mSuntik1;
    }

    public void setSuntik1(List<String> suntik1) {
        mSuntik1 = suntik1;
    }

}
