package ru.romazanov.testapp1.api.model;

import com.google.gson.annotations.SerializedName;

public class AuthResponse {
    @SerializedName("Code")
    int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
