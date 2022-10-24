package ru.romazanov.testapp1.api.model;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class UsersResponse {

    @SerializedName("ListUsers")
    private ArrayList<UserResponse> userList;
    @SerializedName("CurrentUid")
    private String currentUid;

    public ArrayList<UserResponse> getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<UserResponse> userList) {
        this.userList = userList;
    }

    public String getCurrentUid() {
        return currentUid;
    }

    public void setCurrentUid(String currentUid) {
        this.currentUid = currentUid;
    }
}
