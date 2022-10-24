package ru.romazanov.testapp1.api.model;

import com.google.gson.annotations.SerializedName;

public class UserResponse {

    @SerializedName("User")
    private String user ;
    @SerializedName("Uid")
    private String uid;
    @SerializedName("Language")
    private String language;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return user;
    }
}

