
package com.developer.kb.kb.Pasien.response;

import com.google.gson.annotations.SerializedName;


public class Metode {

    @SerializedName("id_metode")
    private String mIdMetode;
    @SerializedName("nama_metode")
    private String mNamaMetode;

    public String getIdMetode() {
        return mIdMetode;
    }

    public void setIdMetode(String idMetode) {
        mIdMetode = idMetode;
    }

    public String getNamaMetode() {
        return mNamaMetode;
    }

    public void setNamaMetode(String namaMetode) {
        mNamaMetode = namaMetode;
    }

}
