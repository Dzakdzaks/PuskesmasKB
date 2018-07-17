
package com.developer.kb.kb.Login.users;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ResponseUsersKaderGet {

    @SerializedName("msg")
    private String mMsg;
    @SerializedName("result")
    private Long mResult;
    @SerializedName("users")
    private List<User> mUsers;

    public String getMsg() {
        return mMsg;
    }

    public void setMsg(String msg) {
        mMsg = msg;
    }

    public Long getResult() {
        return mResult;
    }

    public void setResult(Long result) {
        mResult = result;
    }

    public List<User> getUsers() {
        return mUsers;
    }

    public void setUsers(List<User> users) {
        mUsers = users;
    }

}
