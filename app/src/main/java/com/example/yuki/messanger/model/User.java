package com.example.yuki.messanger.model;

import com.stfalcon.chatkit.commons.models.IUser;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    public String id;
    public String username;
    public String email;
    public Boolean online;
    public ArrayList<String> room;

    //contructor
    public User() {}

    public User(String id, String username, String email, Boolean online, ArrayList<String> room) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.online = online;
        this.room = room;

    }
        public String getUsername() {
            return username;
        }

        public String getId() {
            return id;
        }

        public String getEmail() {
            return email;
        }

        public Boolean isOnline() {
            return online;
        }

        public ArrayList<String> getRoom() {
            return room;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setOnline(Boolean online) {
            this.online = online;
        }

        public void setRoom(ArrayList<String> room) {
            this.room = room;
        }
}
