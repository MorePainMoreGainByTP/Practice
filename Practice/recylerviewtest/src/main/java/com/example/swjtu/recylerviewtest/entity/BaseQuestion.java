package com.example.swjtu.recylerviewtest.entity;

import java.io.Serializable;

/**
 * Created by tangpeng on 2017/3/11.
 */

public class BaseQuestion implements Serializable{
    private int type;   //0是单选，1是多选，2是判断，3是填空
    private String id;     //题目编号
    private String question;    //问题
    private int score;  //分数

    public BaseQuestion(int type, String id, String question, int score ) {
        this.type = type;
        this.id = id;
        this.question = question;
        this.score = score;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
