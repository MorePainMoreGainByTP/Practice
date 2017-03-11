package com.example.swjtu.recylerviewtest.entity;

import java.util.ArrayList;

/**
 * Created by tangpeng on 2017/3/11.
 */

public class ChoiceQuestion extends BaseQuestion {

    private int choiceNum;  //选项个数
    private int answer; //答案的索引
    private ArrayList<String> choices;  //选项

    public ChoiceQuestion(int type, String id, String question, int score, int choiceNum, int answer, ArrayList<String> choices) {
        super(type, id, question, score);
        this.choiceNum = choiceNum;
        this.answer = answer;
        this.choices = choices;
    }

    public ChoiceQuestion(int type, String id, String question, int score ) {
        super(type, id, question, score );
    }

    public int getChoiceNum() {
        return choiceNum;
    }

    public void setChoiceNum(int choiceNum) {
        this.choiceNum = choiceNum;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public ArrayList<String> getChoices() {
        return choices;
    }

    public void setChoices(ArrayList<String> choices) {
        this.choices = choices;
    }
}
