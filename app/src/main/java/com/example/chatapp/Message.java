package com.example.chatapp;

public class Message {

    private String content,username,time;

    public Message()
    {

    }

    public Message (String content,String username,String time)
    {
        this.content = content;
        this.username = username;
        this.time = time;
    }

    public String getContent()
    {
        return content;
    }

    public String getUsername() {
        return username;
    }

    public String getTime() {
        return time;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
