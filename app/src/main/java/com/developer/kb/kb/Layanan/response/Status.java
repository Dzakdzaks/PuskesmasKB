
package com.developer.kb.kb.Layanan.response;

import com.google.gson.annotations.SerializedName;


public class Status {

    @SerializedName("id_status")
    private String mIdStatus;
    @SerializedName("nama_status")
    private String mNamaStatus;

    public String getIdStatus() {
        return mIdStatus;
    }

    public void setIdStatus(String idStatus) {
        mIdStatus = idStatus;
    }

    public String getNamaStatus() {
        return mNamaStatus;
    }

    public void setNamaStatus(String namaStatus) {
        mNamaStatus = namaStatus;
    }

}
