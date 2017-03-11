package com.example.swjtu.recylerviewtest.entity;

/**
 * Created by tangpeng on 2017/3/11.
 */

public class FillBlankQuestion extends BaseQuestion {

    private String answer;
    private String fillTip;


    public FillBlankQuestion(int type, String id, String question, int score, String answer, String fillTip) {
        super(type, id, question, score );
        this.answer = answer;
        this.fillTip = fillTip;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getFillTip() {
        return fillTip;
    }

    public void setFillTip(String fillTip) {
        this.fillTip = fillTip;
    }
}
