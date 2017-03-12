package com.example.swjtu.recylerviewtest.entity;

import java.util.ArrayList;

/**
 * Created by tangpeng on 2017/3/11.
 */

public class JudgeQuestion extends BaseQuestion {

    private int choiceNum;  //判断选项数
    private ArrayList<String> choices;  //选项描述
    private int answer;

    public JudgeQuestion(int type, String id, String question, int score, int choiceNum, ArrayList<String> choices, int answer) {
        super(type, id, question, score);
        this.choiceNum = choiceNum;
        this.choices = choices;
        this.answer = answer;
    }

    public int getChoiceNum() {
        return choiceNum;
    }

    public void setChoiceNum(int choiceNum) {
        this.choiceNum = choiceNum;
    }

    public ArrayList<String> getChoices() {
        return choices;
    }

    public void setChoices(ArrayList<String> choices) {
        this.choices = choices;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
}
