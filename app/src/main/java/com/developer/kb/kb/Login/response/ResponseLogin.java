
package com.developer.kb.kb.Login.response;

import com.google.gson.annotations.SerializedName;


public class ResponseLogin {

    @SerializedName("error")
    private Boolean mError;
    @SerializedName("users")
    private Users mUsers;

    public Boolean getError() {
        return mError;
    }

    public void setError(Boolean error) {
        mError = error;
    }

    public Users getUsers() {
        return mUsers;
    }

    public void setUsers(Users users) {
        mUsers = users;
    }

}
