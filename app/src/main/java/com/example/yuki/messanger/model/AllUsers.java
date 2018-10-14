package com.example.yuki.messanger.model;

public class AllUsers {

    public String user_name;
    public String avatar;
    public String online;

    public AllUsers(){}

    public AllUsers(String user_name, String avatar, String online) {
        this.user_name = user_name;
        this.avatar = avatar;
        this.online = online;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }
}
