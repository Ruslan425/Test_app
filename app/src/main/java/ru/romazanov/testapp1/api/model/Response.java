package ru.romazanov.testapp1.api.model;

import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("Users")
    UsersResponse users;

    public UsersResponse getUsers() {
        return users;
    }

    public void setUsers(UsersResponse users) {
        this.users = users;
    }
}
