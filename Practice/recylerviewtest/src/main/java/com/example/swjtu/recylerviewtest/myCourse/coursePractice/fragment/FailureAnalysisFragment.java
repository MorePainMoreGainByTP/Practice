package com.example.swjtu.recylerviewtest.myCourse.coursePractice.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.swjtu.recylerviewtest.R;
import com.example.swjtu.recylerviewtest.entity.BaseQuestion;
import com.example.swjtu.recylerviewtest.entity.ChoiceQuestion;
import com.example.swjtu.recylerviewtest.entity.FillBlankQuestion;
import com.example.swjtu.recylerviewtest.entity.JudgeQuestion;
import com.example.swjtu.recylerviewtest.entity.MultiChoiceQuestion;

import java.util.ArrayList;

/**
 * Created by tangpeng on 2017/3/26.
 */

public class FailureAnalysisFragment extends Fragment {

    private LinearLayout layoutABCD;
    private LinearLayout layoutFillBlank;
    private TextView orderView, typeScoreView;
    private TextView questionDesc;
    private TextView answerA, answerB, answerC, answerD;
    private EditText editFillBlank;
    private CheckBox a, b, c, d;
    private TextView txtRightAnswer, questionAnalysis;

    private int type;
    private String order;
    private ArrayList<Object> selectedAnswer;
    private BaseQuestion baseQuestion;
    private ChoiceQuestion choiceQuestion;  //单选
    private MultiChoiceQuestion multiChoiceQuestion;    //多选
    private JudgeQuestion judgeQuestion;    //判断
    private FillBlankQuestion fillBlankQuestion;    //填空

    private static String KEY_ORDER = "key_order";
    private static String KEY_OBJECT = "key_object";
    private static String KEY_SELECTED_OBJECT = "key_selected_object";

    public static FailureAnalysisFragment newInstance(BaseQuestion baseQuestion, String order, ArrayList<?> selectedAnswer) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_OBJECT, baseQuestion);
        bundle.putString(KEY_ORDER, order);
        bundle.putSerializable(KEY_SELECTED_OBJECT, selectedAnswer);
        FailureAnalysisFragment failureAnalysisFragment = new FailureAnalysisFragment();
        failureAnalysisFragment.setArguments(bundle);
        return failureAnalysisFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_failure_analysis, null);
        Bundle bundle = getArguments();
        baseQuestion = (BaseQuestion) bundle.getSerializable(KEY_OBJECT);
        type = baseQuestion.getType();
        order = bundle.getString(KEY_ORDER);
        selectedAnswer = (ArrayList<Object>) bundle.getSerializable(KEY_SELECTED_OBJECT);
        initViews(root);
        initData();

        return root;
    }

    private void initViews(View root) {
        layoutABCD = (LinearLayout) root.findViewById(R.id.layoutABCD);
        layoutFillBlank = (LinearLayout) root.findViewById(R.id.layoutFillBlank);
        orderView = (TextView) root.findViewById(R.id.order);
        typeScoreView = (TextView) root.findViewById(R.id.typeScore);
        questionDesc = (TextView) root.findViewById(R.id.questionDesc);
        answerA = (TextView) root.findViewById(R.id.textA);
        answerB = (TextView) root.findViewById(R.id.textB);
        answerC = (TextView) root.findViewById(R.id.textC);
        answerD = (TextView) root.findViewById(R.id.textD);
        a = (CheckBox) root.findViewById(R.id.choiceA);
        b = (CheckBox) root.findViewById(R.id.choiceB);
        c = (CheckBox) root.findViewById(R.id.choiceC);
        d = (CheckBox) root.findViewById(R.id.choiceD);
        a.setClickable(false);
        b.setClickable(false);
        c.setClickable(false);
        d.setClickable(false);
        editFillBlank = (EditText) root.findViewById(R.id.editFillBlank);
        txtRightAnswer = (TextView) root.findViewById(R.id.txtRightAnswer);
        questionAnalysis = (TextView) root.findViewById(R.id.questionAnalysis);
    }

    private void initData() {
        orderView.setText(order + "、");
        StringBuilder builder = new StringBuilder("（");
        int score = 0;
        switch (type) {
            case 0://单选
                layoutFillBlank.setVisibility(View.GONE);
                fillSingleChoice();
                builder.append("单选");
                score = choiceQuestion.getScore();
                questionDesc.setText(choiceQuestion.getQuestion());
                break;
            case 1://多选
                layoutFillBlank.setVisibility(View.GONE);
                fillMultiChoice();
                builder.append("多选");
                score = multiChoiceQuestion.getScore();
                questionDesc.setText(multiChoiceQuestion.getQuestion());
                break;
            case 2://判断
                layoutFillBlank.setVisibility(View.GONE);
                c.setVisibility(View.GONE);
                d.setVisibility(View.GONE);
                answerC.setVisibility(View.GONE);
                answerD.setVisibility(View.GONE);
                fillJudgeChoice();
                builder.append("判断");
                score = judgeQuestion.getScore();
                questionDesc.setText(judgeQuestion.getQuestion());
                break;
            case 3://填空
                layoutABCD.setVisibility(View.GONE);
                a.setVisibility(View.GONE);
                b.setVisibility(View.GONE);
                c.setVisibility(View.GONE);
                d.setVisibility(View.GONE);
                answerA.setVisibility(View.GONE);
                answerB.setVisibility(View.GONE);
                answerC.setVisibility(View.GONE);
                answerD.setVisibility(View.GONE);
                fillBlank();
                builder.append("填空");
                score = fillBlankQuestion.getScore();
                questionDesc.setText(fillBlankQuestion.getQuestion());
                break;
        }

        builder.append("，" + score + "分）");
        typeScoreView.setText(builder.toString());
    }

    private void fillSingleChoice() {
        choiceQuestion = (ChoiceQuestion) baseQuestion;
        answerA.setText(choiceQuestion.getChoices().get(0));
        answerB.setText(choiceQuestion.getChoices().get(1));
        answerC.setText(choiceQuestion.getChoices().get(2));
        answerD.setText(choiceQuestion.getChoices().get(3));
        setCheckBox();
        String rightAnswer = "";
        switch (choiceQuestion.getAnswer()) {
            case 0:
                rightAnswer = "A";
                break;
            case 1:
                rightAnswer = "B";
                break;
            case 2:
                rightAnswer = "C";
                break;
            case 3:
                rightAnswer = "D";
                break;
        }
        setAnswerAnalysis(rightAnswer, choiceQuestion.getAnalysis());
    }

    private void fillMultiChoice() {
        multiChoiceQuestion = (MultiChoiceQuestion) baseQuestion;
        answerA.setText(multiChoiceQuestion.getChoices().get(0));
        answerB.setText(multiChoiceQuestion.getChoices().get(1));
        answerC.setText(multiChoiceQuestion.getChoices().get(2));
        answerD.setText(multiChoiceQuestion.getChoices().get(3));
        setCheckBox();
        ArrayList<Integer> integers = multiChoiceQuestion.getAnswers();
        String answer = "";
        for (Integer integer : integers) {
            String abcd = "";
            switch (integer) {
                case 0:
                    abcd = "A";
                    break;
                case 1:
                    abcd = "B";
                    break;
                case 2:
                    abcd = "C";
                    break;
                case 3:
                    abcd = "D";
                    break;
            }
            answer += abcd;
        }

        setAnswerAnalysis(answer, multiChoiceQuestion.getAnalysis());
    }

    private void fillJudgeChoice() {
        judgeQuestion = (JudgeQuestion) baseQuestion;
        answerA.setText(judgeQuestion.getChoices().get(0));
        answerB.setText(judgeQuestion.getChoices().get(1));
        setCheckBox();
        String ab = "";
        if (judgeQuestion.getAnswer() == 0) {
            ab = "A";
        } else ab = "B";
        setAnswerAnalysis(ab, judgeQuestion.getAnalysis());
    }

    private void fillBlank() {
        fillBlankQuestion = (FillBlankQuestion) baseQuestion;
        Object obj = selectedAnswer.get(0);
        editFillBlank.setHint((String) obj);
        editFillBlank.setFocusable(false);
        setAnswerAnalysis(fillBlankQuestion.getAnswer(), fillBlankQuestion.getAnalysis());
    }

    private void setCheckBox() {
        for (Object obj : selectedAnswer) {
            Integer integer = (Integer) obj;
            switch (integer.intValue()) {
                case 0:
                    a.setBackgroundResource(R.mipmap.a_press);
                    break;
                case 1:
                    b.setBackgroundResource(R.mipmap.b_press);
                    break;
                case 2:
                    c.setBackgroundResource(R.mipmap.c_press);
                    break;
                case 3:
                    d.setBackgroundResource(R.mipmap.d_press);
                    break;
            }
        }
    }

    private void setAnswerAnalysis(String answer, String analysis) {
        txtRightAnswer.setText(answer);
        questionAnalysis.setText(analysis);
    }

}
