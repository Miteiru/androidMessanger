package com.example.yuki.messanger;

import com.stfalcon.chatkit.commons.models.IMessage;

import java.util.Date;

public class Message implements IMessage {
    private String id;
    private String text;
    private User user;
    private Date createdAt;

    public Message(String id, User user, String text){
        this(id, user, text, new Date());
    }

    public Message(String id, User user, String text, Date createdAt){
        this.id = id;
        this.user = user;
        this.text = text;
        this.createdAt = createdAt;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }

    public String getStatus(){
        return "Sent";
    }

    public void setText(String text){
        this.text = text;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setUser(User user){
        this.user = user;
    }
}

