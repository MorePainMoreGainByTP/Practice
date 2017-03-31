package com.example.swjtu.recylerviewtest.WatchVideoOnline;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.swjtu.recylerviewtest.R;
import com.example.swjtu.recylerviewtest.entity.ChoiceQuestion;
import com.example.swjtu.recylerviewtest.entity.FillBlankQuestion;
import com.example.swjtu.recylerviewtest.entity.JudgeQuestion;
import com.example.swjtu.recylerviewtest.entity.MultiChoiceQuestion;
import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static android.view.View.GONE;

/**
 * Created by tangpeng on 2017/3/27.
 */

public class VideoOnlineActivity extends AppCompatActivity implements UniversalVideoView.VideoViewCallback, CompoundButton.OnCheckedChangeListener {
    private static final String TAG = "VideoOnlineActivity";
    private static final String SEEK_POSITION_KEY = "SEEK_POSITION_KEY";
    private static final String VIDEO_URL = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";

    private String[] videoURL = {"http://all.51voa.com:88/201702/supreme-court-justices-process.mp4", "http://all.51voa.com:88/v/fisc-warrant.mp4",
            "http://all.51voa.com:88/201611/louisiana-purchase.mp4", "http://all.51voa.com:88/201701/could-have-would-have.mp4",
            "http://all.51voa.com:88/201702/linking-verbs.mp4", "http://all.51voa.com:88/201701/time-clause-beforeafter.mp4"};
    private String strVideoInfo = "“IT行业职场英语”，是面向计算机科学与技术、软件工程等相关专业学生开设的一门通识与公共基础类课程。" +
            "本课程通过对IT行业历史、文化的梳理，帮助学习者对行业文化有充分认识和理解，通过IT行业企业文化以及职场生存技能的训练，" +
            "帮助学习者规划自己的职业发展，进而在国际化IT公司的商务活动中更加自信自如的进行沟通。";

    private UniversalMediaController mediaController;
    private UniversalVideoView mVideoView;
    private FrameLayout mVideoLayout;
    private LinearLayout introduceLayout;
    private TextView videoInfo;
    private AlertDialog examDialog;

    private int mSeekPosition;
    private int cachedHeight;
    private boolean isFullscreen;

    private Thread timerThread; //计秒线程

    private int currSeconds = 0;   //当前视频播放的进度
    private double[] pointPercents = {0.1, 0.2, 0.3, 0.72, 0.9};    //弹出测试的视频进度位置
    private int duration;   //视频的总时间
    private int currPointIndex = 0;  //下一个弹出测试百分比下标

    private ChoiceQuestion choiceQuestion;  //单选
    private MultiChoiceQuestion multiChoiceQuestion;    //多选
    private JudgeQuestion judgeQuestion;    //判断
    private FillBlankQuestion fillBlankQuestion;    //填空

    private LinearLayout layoutABCD;
    private LinearLayout layoutFillBlank;
    private TextView questionDesc;
    private TextView answerA, answerB, answerC, answerD;
    private EditText editFillBlank;
    private CheckBox a, b, c, d;
    private int type;

    Random random = new Random();

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            showExamDialog(msg.what);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_online);
        initViews();
        setVideoAreaSize();
    }

    private void initViews() {
        mVideoLayout = (FrameLayout) findViewById(R.id.video_layout);
        introduceLayout = (LinearLayout) findViewById(R.id.introduce_layout);
        mediaController = (UniversalMediaController) findViewById(R.id.media_controller);
        mVideoView = (UniversalVideoView) findViewById(R.id.videoView);
        mVideoView.setMediaController(mediaController);
        mVideoView.setVideoViewCallback(this);
        videoInfo = (TextView) findViewById(R.id.videoInfo);
        videoInfo.setText(strVideoInfo);
    }

    /**
     * 置视频区域大小
     */
    private void setVideoAreaSize() {
        mVideoLayout.post(new Runnable() {
            @Override
            public void run() {
                int width = mVideoLayout.getWidth();
                cachedHeight = (int) (width * 405f / 720f);
//                cachedHeight = (int) (width * 3f / 4f);
//                cachedHeight = (int) (width * 9f / 16f);
                ViewGroup.LayoutParams videoLayoutParams = mVideoLayout.getLayoutParams();
                videoLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                videoLayoutParams.height = cachedHeight;
                mVideoLayout.setLayoutParams(videoLayoutParams);
                Random random = new Random();
                mVideoView.setVideoPath(videoURL[random.nextInt(videoURL.length)]);
                mVideoView.requestFocus();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause ");
        if (mVideoView != null && mVideoView.isPlaying()) {
            mSeekPosition = mVideoView.getCurrentPosition();
            Log.d(TAG, "onPause mSeekPosition=" + mSeekPosition);
            mVideoView.pause();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState Position=" + mVideoView.getCurrentPosition());
        outState.putInt(SEEK_POSITION_KEY, mSeekPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle outState) {
        super.onRestoreInstanceState(outState);
        mSeekPosition = outState.getInt(SEEK_POSITION_KEY);
        Log.d(TAG, "onRestoreInstanceState Position=" + mSeekPosition);
    }

    @Override
    public void onScaleChange(boolean isFullscreen) {
        this.isFullscreen = isFullscreen;
        if (isFullscreen) {
            ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            mVideoLayout.setLayoutParams(layoutParams);
            introduceLayout.setVisibility(GONE);
        } else {
            ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = this.cachedHeight;
            mVideoLayout.setLayoutParams(layoutParams);
            introduceLayout.setVisibility(View.VISIBLE);
        }
        switchTitleBar(!isFullscreen);
    }

    private void switchTitleBar(boolean show) {
        android.support.v7.app.ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            if (show) {
                supportActionBar.show();
            } else {
                supportActionBar.hide();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (this.isFullscreen) {
            mVideoView.setFullscreen(false);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onPause(MediaPlayer mediaPlayer) {
    }

    @Override
    public void onStart(MediaPlayer mediaPlayer) {
        duration = mVideoView.getDuration() / 1000;
        if (timerThread == null) {
            timerThread = new Thread() {
                @Override
                public void run() {
                    super.run();
                    while (true) {
                        SystemClock.sleep(1000);
                        if (mVideoView.isPlaying())
                            currSeconds++;
                        if (currPointIndex < pointPercents.length) {
                            if (currSeconds == (int) (duration * pointPercents[currPointIndex])) {
                                handler.sendEmptyMessage(currPointIndex);
                                currSeconds++;
                            }
                        }
                        if (currSeconds >= duration) {
                            Log.i(TAG, "run: currSeconds >= duration timerThread end");
                            break;
                        }
                    }
                }
            };
            timerThread.start();
        }
    }

    @Override
    public void onBufferingStart(MediaPlayer mediaPlayer) {

    }

    @Override
    public void onBufferingEnd(MediaPlayer mediaPlayer) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mVideoView != null) {
            mVideoView.closePlayer();
            mVideoView = null;
        }
    }

    private void showExamDialog(int which) {
        mVideoView.pause();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(initExamViews());
        builder.setCancelable(false);
        builder.setPositiveButton("提交", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!checkAnswer()) {//回答错误
                    backToLastPoint();
                    Log.i(TAG, "onClick: 回答错误");
                } else {
                    currPointIndex++;
                    new AlertDialog.Builder(VideoOnlineActivity.this).setMessage("回答正确,请继续播放！").setPositiveButton("确定", null).create().show();
                    Log.i(TAG, "onClick: 回答正确");
                }
                dialog.dismiss();
            }
        }).setNegativeButton("退出视频", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                VideoOnlineActivity.this.finish();
            }
        });
        examDialog = builder.create();
        examDialog.show();
    }

    private void backToLastPoint() {
        new AlertDialog.Builder(VideoOnlineActivity.this).setMessage("对不起，回答错误，后退到上个知识点").setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (currPointIndex <= 0) {
                    mVideoView.seekTo(duration / 2);
                    currSeconds = 0;
                    currPointIndex = 0;
                    Log.i(TAG, "onClick: 回退到0");
                } else {
                    currPointIndex--;
                    mVideoView.seekTo((int) (duration * pointPercents[currPointIndex]) + 1);
                    Log.i(TAG, "onClick: 回退到：" + ((int) (duration * pointPercents[currPointIndex]) + 1));
                    currSeconds = ((int) (duration * pointPercents[currPointIndex]) + 1);
                }
            }
        }).setCancelable(false).create().show();
    }

    //初始化测试对话框里面的控件
    private View initExamViews() {
        View root = LayoutInflater.from(this).inflate(R.layout.dialog_exam, null);
        View topLayout = root.findViewById(R.id.topLayout);
        topLayout.setVisibility(GONE);
        questionDesc = (TextView) root.findViewById(R.id.questionDesc);
        layoutABCD = (LinearLayout) root.findViewById(R.id.layoutABCD);
        layoutFillBlank = (LinearLayout) root.findViewById(R.id.layoutFillBlank);
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

        initData();
        return root;
    }

    public void back(View v) {
        finish();
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

    private void initData() {
        type = random.nextInt(4);
        switch (type) {
            case 0://单选
                layoutFillBlank.setVisibility(GONE);
                fillSingleChoice();
                questionDesc.setText(choiceQuestion.getQuestion());
                break;
            case 1://多选
                layoutFillBlank.setVisibility(GONE);
                fillMultiChoice();
                questionDesc.setText(multiChoiceQuestion.getQuestion());
                break;
            case 2://判断
                layoutFillBlank.setVisibility(GONE);
                c.setVisibility(GONE);
                d.setVisibility(GONE);
                answerC.setVisibility(GONE);
                answerD.setVisibility(GONE);
                fillJudgeChoice();
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
                questionDesc.setText(fillBlankQuestion.getQuestion());
                break;
        }
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
        String quesStr = "The following steps help choose a suitable topic for the presentation except ___.(多选)";
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

    private int getScore() {
        return (1 + random.nextInt(3) % 3);
    }

    //检查结果是否正确
    public boolean checkAnswer() {
        boolean hasFinished = false;
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
                    hasFinished = true;
                } else {
                    hasFinished = false;
                }
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
                            hasFinished = false;
                            break;
                        }
                    }
                } else {
                    hasFinished = false;
                }
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
                    hasFinished = true;
                } else {
                    hasFinished = false;
                }
                break;
            case 3:
                String answerStr = editFillBlank.getText().toString();
                hasFinished = true;
                if (answerStr.trim().equals("")) {
                    hasFinished = false;
                }
                if (answerStr.equals(fillBlankQuestion.getAnswer())) {
                    hasFinished = true;
                } else {
                    hasFinished = false;
                }
                break;
        }
        return hasFinished;
    }


}
