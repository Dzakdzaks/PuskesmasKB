
package com.developer.kb.kb.Login.response;

import com.google.gson.annotations.SerializedName;

public class Users {

    @SerializedName("alamat")
    private String mAlamat;
    @SerializedName("id_pos")
    private String mIdPos;
    @SerializedName("id_role")
    private String mIdRole;
    @SerializedName("id_users")
    private String mIdUsers;
    @SerializedName("nama")
    private String mNama;
    @SerializedName("nip")
    private String mNip;
    @SerializedName("nomor_telpon")
    private String mNomorTelpon;
    @SerializedName("password")
    private String mPassword;
    @SerializedName("username")
    private String mUsername;

    public String getmNamaRole() {
        return mNamaRole;
    }

    public void setmNamaRole(String mNamaRole) {
        this.mNamaRole = mNamaRole;
    }

    @SerializedName("nama_role")
    private String mNamaRole;

    public String getAlamat() {
        return mAlamat;
    }

    public void setAlamat(String alamat) {
        mAlamat = alamat;
    }

    public String getIdPos() {
        return mIdPos;
    }

    public void setIdPos(String idPos) {
        mIdPos = idPos;
    }

    public String getIdRole() {
        return mIdRole;
    }

    public void setIdRole(String idRole) {
        mIdRole = idRole;
    }

    public String getIdUsers() {
        return mIdUsers;
    }

    public void setIdUsers(String idUsers) {
        mIdUsers = idUsers;
    }

    public String getNama() {
        return mNama;
    }

    public void setNama(String nama) {
        mNama = nama;
    }

    public String getNip() {
        return mNip;
    }

    public void setNip(String nip) {
        mNip = nip;
    }

    public String getNomorTelpon() {
        return mNomorTelpon;
    }

    public void setNomorTelpon(String nomorTelpon) {
        mNomorTelpon = nomorTelpon;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

}
