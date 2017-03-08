package com.example.swjtu.recylerviewtest.entity;

import java.io.Serializable;

/**
 * Created by tangpeng on 2017/3/8.
 */

public class DiscussTopic implements Serializable{
    private String title;
    private String content;
    private String datetime;
    private String fromWho;

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

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getFromWho() {
        return fromWho;
    }

    public void setFromWho(String fromWho) {
        this.fromWho = fromWho;
    }

    public DiscussTopic(String title, String content, String datetime, String fromWho) {
        this.title = title;
        this.content = content;
        this.datetime = datetime;
        this.fromWho = fromWho;
    }
}
