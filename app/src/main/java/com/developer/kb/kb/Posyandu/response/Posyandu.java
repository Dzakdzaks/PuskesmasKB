
package com.developer.kb.kb.Posyandu.response;

import com.google.gson.annotations.SerializedName;


public class Posyandu {

    @SerializedName("alamat_pos")
    private String mAlamatPos;
    @SerializedName("id_petugas")
    private String mIdPetugas;
    @SerializedName("nama")
    private String mNama;
    @SerializedName("id_pos")
    private String mIdPos;
    @SerializedName("nama_pos")
    private String mNamaPos;
    @SerializedName("no_telp_pos")
    private String mNoTelpPos;

    public String getAlamatPos() {
        return mAlamatPos;
    }

    public void setAlamatPos(String alamatPos) {
        mAlamatPos = alamatPos;
    }

    public String getIdPetugas() {
        return mIdPetugas;
    }

    public void setIdPetugas(String idPetugas) {
        mIdPetugas = idPetugas;
    }

    public String getIdPos() {
        return mIdPos;
    }

    public void setIdPos(String idPos) {
        mIdPos = idPos;
    }

    public String getNamaPos() {
        return mNamaPos;
    }

    public void setNamaPos(String namaPos) {
        mNamaPos = namaPos;
    }

    public String getNoTelpPos() {
        return mNoTelpPos;
    }

    public void setNoTelpPos(String noTelpPos) {
        mNoTelpPos = noTelpPos;
    }

    public String getNama() {
        return mNama;
    }

    public void setNama(String nama) {
        mNama = nama;
    }

}
