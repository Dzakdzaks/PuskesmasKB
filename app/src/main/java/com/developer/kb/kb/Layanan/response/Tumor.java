
package com.developer.kb.kb.Layanan.response;

import com.google.gson.annotations.SerializedName;


public class Tumor {

    @SerializedName("id_tumor")
    private String mIdTumor;
    @SerializedName("nama_tumor")
    private String mNamaTumor;

    public String getIdTumor() {
        return mIdTumor;
    }

    public void setIdTumor(String idTumor) {
        mIdTumor = idTumor;
    }

    public String getNamaTumor() {
        return mNamaTumor;
    }

    public void setNamaTumor(String namaTumor) {
        mNamaTumor = namaTumor;
    }

}
