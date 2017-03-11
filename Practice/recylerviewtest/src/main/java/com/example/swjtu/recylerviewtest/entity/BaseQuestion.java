package com.example.swjtu.recylerviewtest.entity;

/**
 * Created by tangpeng on 2017/3/11.
 */

public class BaseQuestion {
    private int type;   //0是客观题，1是主观题
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
