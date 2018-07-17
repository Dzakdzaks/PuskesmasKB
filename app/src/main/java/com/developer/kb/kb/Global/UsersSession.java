package com.developer.kb.kb.Global;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.developer.kb.kb.Login.LoginActivity;

public class UsersSession {
    public static final String SP_MAHASISWA_APP = "spMahasiswaApp";

    public static final String SP_ID_USERS = "spIdUsers";
    public static final String SP_ID_POS = "spIdPos";
    public static final String SP_ID_ROLE = "spIdRole";
    public static final String SP_NAME = "spName";
    public static final String SP_NIP = "spNip";
    public static final String SP_USERNAME = "spUname";
    public static final String SP_PASSWORD = "spPass";
    public static final String SP_ADDRESS = "spAddress";
    public static final String SP_NO = "spNo";

    public static final String SP_SUDAH_LOGIN = "spSudahLogin";

    int PRIVATE_MODE = 0;
    SharedPreferences sp;
    SharedPreferences.Editor spEditor;
    Context context;

    public UsersSession(Context context) {
        this.context = context;
        sp = context.getSharedPreferences(SP_MAHASISWA_APP, PRIVATE_MODE);
        spEditor = sp.edit();
    }

    public void saveSPString(String keySP, String value) {
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSPInt(String keySP, int value) {
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

//    public void checkLogin() {
//        // Check login status
//        if (!this.getSPSudahLogin()) {
//            Intent i = new Intent(context, HomeActivity.class);
//            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(i);
//        }
//    }

    public void logout() {
        spEditor.clear();
        spEditor.commit();
        Intent i = new Intent(context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);

    }

    public void saveSPBoolean(String keySP, boolean value) {
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public String getSpIdUsers() {
        return sp.getString(SP_ID_USERS, "");
    }

    public String getSpIdPos() {
        return sp.getString(SP_ID_POS, "");
    }

    public String getSpIdRole() {
        return sp.getString(SP_ID_ROLE, "");
    }

    public String getSpName() {
        return sp.getString(SP_NAME, "");
    }

    public String getSpNip() {
        return sp.getString(SP_NIP, "");
    }

    public String getSpUsername() {
        return sp.getString(SP_USERNAME, "");
    }

    public String getSpPassword() {
        return sp.getString(SP_PASSWORD, "");
    }

    public String getSpNo() {
        return sp.getString(SP_NO, "");
    }

    public String getSpAddress() {
        return sp.getString(SP_ADDRESS, "");
    }

    public Boolean getSPSudahLogin() {
        return sp.getBoolean(SP_SUDAH_LOGIN, false);
    }
}
