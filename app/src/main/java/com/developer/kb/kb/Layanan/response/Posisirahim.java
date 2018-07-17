
package com.developer.kb.kb.Layanan.response;

import com.google.gson.annotations.SerializedName;

public class Posisirahim {

    @SerializedName("id_posisi")
    private String mIdPosisi;
    @SerializedName("nama_posisi")
    private String mNamaPosisi;

    public String getIdPosisi() {
        return mIdPosisi;
    }

    public void setIdPosisi(String idPosisi) {
        mIdPosisi = idPosisi;
    }

    public String getNamaPosisi() {
        return mNamaPosisi;
    }

    public void setNamaPosisi(String namaPosisi) {
        mNamaPosisi = namaPosisi;
    }

}
