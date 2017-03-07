package com.example.swjtu.recylerviewtest.entity;

import java.io.Serializable;

/**
 * Created by tangpeng on 2017/3/7.
 */

public class MessageInfo implements Serializable{
    private String time;
    private String title;
    private String content;

    public MessageInfo(String time, String title, String content) {
        this.time = time;
        this.title = title;
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
