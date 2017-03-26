package com.example.swjtu.recylerviewtest.myCourse.coursePractice.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.swjtu.recylerviewtest.R;
import com.example.swjtu.recylerviewtest.entity.BaseQuestion;
import com.example.swjtu.recylerviewtest.entity.ChoiceQuestion;
import com.example.swjtu.recylerviewtest.entity.FillBlankQuestion;
import com.example.swjtu.recylerviewtest.entity.JudgeQuestion;
import com.example.swjtu.recylerviewtest.entity.MultiChoiceQuestion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static android.view.View.GONE;

/**
 * Created by tangpeng on 2017/3/11.
 */

public class SingleChoiceFragment extends Fragment implements CompoundButton.OnCheckedChangeListener,Serializable {
    private static final String TAG = "SingleChoiceFragment";
    private boolean resultIsTrue = false;   //结果是否正确
    private boolean hasFinished = false;    //是否选了答案

    private LinearLayout layoutRoot;
    private LinearLayout layoutABCD;
    private LinearLayout layoutFillBlank;
    private TextView orderView, typeScoreView;
    private TextView questionDesc;
    private TextView answerA, answerB, answerC, answerD;
    private EditText editFillBlank;
    private CheckBox a, b, c, d;

    private int type;   //题型
    private String order;

    private ChoiceQuestion choiceQuestion;  //单选
    private MultiChoiceQuestion multiChoiceQuestion;    //多选
    private JudgeQuestion judgeQuestion;    //判断
    private FillBlankQuestion fillBlankQuestion;    //填空

    private static String KEY_ORDER = "key_order";
    private static String KEY_TYPE = "key_type";

    private ArrayList<Object> selectedAnswer = new ArrayList<>();  //选择的答案

    Random random = new Random();

    public static SingleChoiceFragment newInstance(int type, String order) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_TYPE, type);
        bundle.putString(KEY_ORDER, order);
        SingleChoiceFragment singleChoiceFragment = new SingleChoiceFragment();
        singleChoiceFragment.setArguments(bundle);
        return singleChoiceFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_single_choice, null);
        Bundle bundle = getArguments();
        type = bundle.getInt(KEY_TYPE);
        order = bundle.getString(KEY_ORDER);
        Log.i(TAG, "onCreateView: type:" + type + ",order:" + order);
        initViews(root);
        initData();
        return root;
    }

    private void initViews(View root) {
        layoutRoot = (LinearLayout) root;
        layoutRoot.setVisibility(View.INVISIBLE);
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
        a.setOnCheckedChangeListener(this);
        b.setOnCheckedChangeListener(this);
        c.setOnCheckedChangeListener(this);
        d.setOnCheckedChangeListener(this);
        editFillBlank = (EditText) root.findViewById(R.id.editFillBlank);
    }

    private void initData() {
        orderView.setText(order + "、");
        StringBuilder builder = new StringBuilder("（");
        int score = 0;
        switch (type) {
            case 0://单选
                layoutFillBlank.setVisibility(GONE);
                fillSingleChoice();
                builder.append("单选");
                score = choiceQuestion.getScore();
                questionDesc.setText(choiceQuestion.getQuestion());
                break;
            case 1://多选
                layoutFillBlank.setVisibility(GONE);
                fillMultiChoice();
                builder.append("多选");
                score = multiChoiceQuestion.getScore();
                questionDesc.setText(multiChoiceQuestion.getQuestion());
                break;
            case 2://判断
                layoutFillBlank.setVisibility(GONE);
                c.setVisibility(GONE);
                d.setVisibility(GONE);
                answerC.setVisibility(GONE);
                answerD.setVisibility(GONE);
                fillJudgeChoice();
                builder.append("判断");
                score = judgeQuestion.getScore();
                questionDesc.setText(judgeQuestion.getQuestion());
                break;
            case 3://填空
                layoutABCD.setVisibility(GONE);
                a.setVisibility(GONE);
                b.setVisibility(GONE);
                c.setVisibility(GONE);
                d.setVisibility(GONE);
                answerA.setVisibility(GONE);
                answerB.setVisibility(GONE);
                answerC.setVisibility(GONE);
                answerD.setVisibility(GONE);
                fillBlank();
                builder.append("填空");
                score = fillBlankQuestion.getScore();
                questionDesc.setText(fillBlankQuestion.getQuestion());
                break;
        }

        builder.append("，" + score + "分）");
        typeScoreView.setText(builder.toString());
        layoutRoot.setVisibility(View.VISIBLE);
    }

    private void fillSingleChoice() {
        String quesStr = "Which of the following suggestions are helpful to a persuasive inspiring presentation";
        ArrayList<String> choices = new ArrayList<>();
        choices.add("Try to explain the meanings of the unfamiliar words to the audience.");
        choices.add("Avoid generalizing and exaggerating.");
        choices.add("Capture the audience's attention.");
        choices.add("Find out what the audience's needs and interests are,and show how you can satisfy those needs");
        choiceQuestion = new ChoiceQuestion(type, "0", quesStr, getScore(), 4, 1, choices);
        choiceQuestion.setAnalysis("在原文第三段末尾与第四段开头有相应的解释");
        answerA.setText(choiceQuestion.getChoices().get(0));
        answerB.setText(choiceQuestion.getChoices().get(1));
        answerC.setText(choiceQuestion.getChoices().get(2));
        answerD.setText(choiceQuestion.getChoices().get(3));
    }

    private void fillMultiChoice() {
        String quesStr = "The following steps help choose a suitable topic for the presentation except ___.";
        ArrayList<String> choices = new ArrayList<>();
        choices.add("Listing key words to refine your topic.");
        choices.add("Writing down every word you come up with to guarantee a suitable topic.");
        choices.add("arranging your ideas from the most important to the least important,or vice versa.");
        choices.add("brainstorming possible titles");
        ArrayList<Integer> answers = new ArrayList<>();
        answers.add(1);
        answers.add(3);
        multiChoiceQuestion = new MultiChoiceQuestion(type, "0", quesStr, getScore(), 4, answers, choices);
        multiChoiceQuestion.setAnalysis("可以使用排除法来完成这道题，首先可以在第一段、第三段与第四段找到其中的三个选项然后再分别排除");
        answerA.setText(multiChoiceQuestion.getChoices().get(0));
        answerB.setText(multiChoiceQuestion.getChoices().get(1));
        answerC.setText(multiChoiceQuestion.getChoices().get(2));
        answerD.setText(multiChoiceQuestion.getChoices().get(3));
    }

    private void fillJudgeChoice() {
        String quesStr = "Only those inexperienced speakers need to plan the presentation ,for the planning precess is time-consuming.";
        ArrayList<String> choices = new ArrayList<>();
        choices.add("正确");
        choices.add("错误");
        judgeQuestion = new JudgeQuestion(type, "0", quesStr, getScore(), 2, choices, 0);
        judgeQuestion.setAnalysis("在文中第二段的第三句话“There are so ....”可以做出判断");
        answerA.setText(judgeQuestion.getChoices().get(0));
        answerB.setText(judgeQuestion.getChoices().get(1));
    }

    private void fillBlank() {
        String quesStr = "According to the lecture,a suitable topic should be ___,appropriate and limited scope.";
        fillBlankQuestion = new FillBlankQuestion(type, "0", quesStr, getScore(), "hello", "请输入答案");
        fillBlankQuestion.setAnalysis("这是检查考生对全文的理解能力。从全文来看，可以推断这是一篇关于...的文章，所以比较合适的答案是...");
        editFillBlank.setHint(fillBlankQuestion.getFillTip());
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (type == 1) {//多选
            switch (buttonView.getId()) {
                case R.id.choiceA:
                    if (isChecked) {
                        a.setBackgroundResource(R.mipmap.a_press);
                    } else {
                        a.setBackgroundResource(R.mipmap.a_gray);
                    }

                    break;
                case R.id.choiceB:
                    if (isChecked) {
                        b.setBackgroundResource(R.mipmap.b_press);
                    } else {
                        b.setBackgroundResource(R.mipmap.b_gray);
                    }
                    break;
                case R.id.choiceC:
                    if (isChecked) {
                        c.setBackgroundResource(R.mipmap.c_press);
                    } else {
                        c.setBackgroundResource(R.mipmap.c_gray1);
                    }
                    break;
                case R.id.choiceD:
                    if (isChecked) {
                        d.setBackgroundResource(R.mipmap.d_press);
                    } else {
                        d.setBackgroundResource(R.mipmap.d_gray);
                    }
                    break;
            }
        } else {
            switch (buttonView.getId()) {
                case R.id.choiceA:
                    clearState();
                    a.setBackgroundResource(R.mipmap.a_press);
                    break;
                case R.id.choiceB:
                    clearState();
                    b.setBackgroundResource(R.mipmap.b_press);
                    break;
                case R.id.choiceC:
                    clearState();
                    c.setBackgroundResource(R.mipmap.c_press);
                    break;
                case R.id.choiceD:
                    clearState();
                    d.setBackgroundResource(R.mipmap.d_press);
                    break;
            }
        }
    }

    private void clearState() {
        a.setBackgroundResource(R.mipmap.a_gray);
        b.setBackgroundResource(R.mipmap.b_gray);
        c.setBackgroundResource(R.mipmap.c_gray1);
        d.setBackgroundResource(R.mipmap.d_gray);
    }

    private int getScore() {
        return (1 + random.nextInt(3) % 3);
    }

    //检查是否完成与正确
    public void checkAnswer() {
        switch (type) {
            case 0:
                int answerSelected = -1;
                hasFinished = true;
                if (a.isChecked()) {
                    answerSelected = 0;
                } else if (b.isChecked()) {
                    answerSelected = 1;
                } else if (c.isChecked()) {
                    answerSelected = 2;
                } else if (d.isChecked()) {
                    answerSelected = 3;
                } else {
                    hasFinished = false;
                }
                if (choiceQuestion.getAnswer() == answerSelected) {
                    resultIsTrue = true;
                } else {
                    resultIsTrue = false;
                }
                selectedAnswer.add(answerSelected);
                break;
            case 1:
                ArrayList<Object> integers = new ArrayList<>();
                hasFinished = true;
                if (a.isChecked()) {
                    integers.add(0);
                } else if (b.isChecked()) {
                    integers.add(1);
                } else if (c.isChecked()) {
                    integers.add(2);
                } else if (d.isChecked()) {
                    integers.add(3);
                } else {
                    hasFinished = false;
                }
                Collections.sort(multiChoiceQuestion.getAnswers());
                if (integers.size() == multiChoiceQuestion.getAnswers().size()) {
                    for (int i = 0; i < integers.size(); i++) {
                        if ((int) integers.get(i) == multiChoiceQuestion.getAnswers().get(i)) {
                            continue;
                        } else {
                            resultIsTrue = false;
                            break;
                        }
                    }
                } else {
                    resultIsTrue = false;
                }
                selectedAnswer = integers;
                break;
            case 2:
                int answerSelected2 = -1;
                hasFinished = true;
                if (a.isChecked()) {
                    answerSelected2 = 0;
                } else if (b.isChecked()) {
                    answerSelected2 = 1;
                } else {
                    hasFinished = false;
                }
                if (judgeQuestion.getAnswer() == answerSelected2) {
                    resultIsTrue = true;
                } else {
                    resultIsTrue = false;
                }
                selectedAnswer.add(answerSelected2);
                break;
            case 3:
                String answerStr = editFillBlank.getText().toString();
                selectedAnswer.add(answerStr);
                hasFinished = true;
                if (answerStr.trim().equals("")) {
                    hasFinished = false;
                }
                if (answerStr.equals(fillBlankQuestion.getAnswer())) {
                    resultIsTrue = true;
                } else {
                    resultIsTrue = false;
                }
                break;
        }
        Log.i(TAG, "checkAnswer: hasFinished:" + hasFinished);
    }

    public BaseQuestion getQuestion() {
        switch (type) {
            case 0:
                return choiceQuestion;
            case 1:
                return multiChoiceQuestion;
            case 2:
                return judgeQuestion;
            case 3:
                return fillBlankQuestion;
        }
        return null;
    }

    public int getType() {
        return type;
    }

    public ArrayList<Object> getSelectedAnswer() {
        return selectedAnswer;
    }

    public boolean isResultIsTrue() {
        return resultIsTrue;
    }

    public boolean isHasFinished() {
        return hasFinished;
    }
}
