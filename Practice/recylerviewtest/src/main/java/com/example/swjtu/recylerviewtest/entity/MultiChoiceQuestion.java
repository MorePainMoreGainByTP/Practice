package com.example.swjtu.recylerviewtest.entity;

import java.util.ArrayList;

/**
 * Created by tangpeng on 2017/3/11.
 */

public class MultiChoiceQuestion extends BaseQuestion {

    private int choiceNum;  //选项个数
    private ArrayList<Integer> answers; //答案的索引
    private ArrayList<String> choices;  //选项


    public MultiChoiceQuestion(int type, String id, String question, int score) {
        super(type, id, question, score);
    }

    public MultiChoiceQuestion(int type, String id, String question, int score, int choiceNum, ArrayList<Integer> answers, ArrayList<String> choices) {
        super(type, id, question, score);
        this.choiceNum = choiceNum;
        this.answers = answers;
        this.choices = choices;
    }

    public int getChoiceNum() {
        return choiceNum;
    }

    public void setChoiceNum(int choiceNum) {
        this.choiceNum = choiceNum;
    }

    public ArrayList<Integer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Integer> answers) {
        this.answers = answers;
    }

    public ArrayList<String> getChoices() {
        return choices;
    }

    public void setChoices(ArrayList<String> choices) {
        this.choices = choices;
    }
}
