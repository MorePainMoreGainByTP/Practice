package com.example.swjtu.recylerviewtest.entity;

import java.io.Serializable;

/**
 * Created by tangpeng on 2017/3/8.
 */

public class Teacher implements Serializable{
    private String name;
    private String position;
    private String headerURL;

    public Teacher(String name, String position) {
        this.name = name;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getHeaderURL() {
        return headerURL;
    }

    public void setHeaderURL(String headerURL) {
        this.headerURL = headerURL;
    }
}
