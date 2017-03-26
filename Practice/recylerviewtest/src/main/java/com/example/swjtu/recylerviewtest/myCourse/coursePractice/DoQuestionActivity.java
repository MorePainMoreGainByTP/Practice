package com.example.swjtu.recylerviewtest.myCourse.coursePractice;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.swjtu.recylerviewtest.R;
import com.example.swjtu.recylerviewtest.entity.BaseQuestion;
import com.example.swjtu.recylerviewtest.myCourse.coursePractice.fragment.SingleChoiceFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by tangpeng on 2017/3/11.
 */

public class DoQuestionActivity extends AppCompatActivity {
    private static final String TAG = "DoQuestionActivity";
    private Intent intent;

    private ViewPager viewPager;
    private TextView currTextIndex, remainTime;
    private Button nextButton;
    private int currIndex = 0;  //当前的题目index
    private int sumIndex = 1;   //总的题目
    private int timeLimitSecond;  //倒计时

    private FragmentPagerAdapter fragmentPagerAdapter;
    private List<Fragment> fragments = new ArrayList<>();

    private InputMethodManager inputMethodManager;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    remainTime.setText(second2Time(timeLimitSecond));
                    break;
                case 2://时间到
                    new AlertDialog.Builder(DoQuestionActivity.this).setMessage("时间已到,请交卷！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            showResult(errorItems());
                        }
                    }).setCancelable(false).create().show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_question);
        intent = getIntent();
        timeLimitSecond = 60 * intent.getIntExtra("practiceTimeLimit", 0);    //将分钟转换成秒
        sumIndex = intent.getIntExtra("practiceNum", 1);
        Log.i(TAG, "onCreate: timeLimitSecond:" + timeLimitSecond + ",sumIndex:" + sumIndex);
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        initViews();
        initData();
        new Thread() {
            @Override
            public void run() {
                super.run();
                while (timeLimitSecond-- > 0) {
                    SystemClock.sleep(1000);
                    handler.sendEmptyMessage(1);
                }
                if (timeLimitSecond <= 0)
                    handler.sendEmptyMessage(2);
            }
        }.start();

    }

    private void initViews() {
        viewPager = (ViewPager) findViewById(R.id.viewpager_question);
        currTextIndex = (TextView) findViewById(R.id.text_currIndex);
        remainTime = (TextView) findViewById(R.id.text_remain_time);
        nextButton = (Button) findViewById(R.id.next);
        TextView sumItem = (TextView) findViewById(R.id.sum_item);

        sumItem.setText("" + intent.getIntExtra("practiceNum", 1));
        viewPager.setOffscreenPageLimit(sumIndex);
        remainTime.setText(second2Time(timeLimitSecond));
        currTextIndex.setText((currIndex + 1) + "");
        if (sumIndex == 1) {
            nextButton.setText("提交");
            nextButton.setTextColor(getResources().getColor(R.color.limegreen));
        }
    }

    private void initData() {
        int[] types = {0, 1, 2, 3};
        Random random = new Random();
        for (int i = 0; i < sumIndex; i++) {
            fragments.add(SingleChoiceFragment.newInstance(types[random.nextInt(types.length) % types.length], (i + 1) + ""));
        }
        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };
        viewPager.setAdapter(fragmentPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currIndex = position;
                currTextIndex.setText((currIndex + 1) + "");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //上一题
    public void last(View v) {
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
        nextButton.setText("下一题");
        nextButton.setTextColor(getResources().getColor(R.color.black));
        if (currIndex == 0) {
            Toast.makeText(this, "当前已是第一道题", Toast.LENGTH_SHORT).show();
        } else {
            viewPager.setCurrentItem(--currIndex);
        }
    }

    //下一题
    public void next(View v) {
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
        if (currIndex == sumIndex - 1) {
            ArrayList<Integer> unfinished = unfinishedItem();
            if (unfinished.size() > 0) {
                StringBuilder tip = new StringBuilder("第");
                for (int i = 0; i < unfinished.size(); i++) {
                    tip.append(unfinished.get(i));
                    if (i != unfinished.size() - 1) {
                        tip.append("，");
                    } else {
                        tip.append("题未完成。");
                    }
                }
                new AlertDialog.Builder(this).setMessage(tip.toString()).setNegativeButton("取消", null).setPositiveButton("提交", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showResult(errorItems());
                        dialog.dismiss();
                    }
                }).setCancelable(false).create().show();
            } else {
                new AlertDialog.Builder(this).setMessage("请仔细检查，确认提交").setNegativeButton("取消", null).setPositiveButton("提交", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showResult(errorItems());
                        dialog.dismiss();
                    }
                }).setCancelable(false).create().show();
            }
        } else {
            currIndex++;
            if (currIndex == sumIndex - 1) {
                nextButton.setText("提交");
                nextButton.setTextColor(getResources().getColor(R.color.limegreen));
            }
            viewPager.setCurrentItem(currIndex);
        }
    }

    private String second2Time(int second) {
        int hour = second / 3600;
        second -= hour * 3600;
        int min = second / 60;
        second -= min * 60;
        return hour + ":" + min + ":" + second;
    }

    private ArrayList<Integer> unfinishedItem() {
        ArrayList<Integer> integers = new ArrayList<>();    //未完成的题目号
        for (int i = 0; i < fragments.size(); i++) {
            SingleChoiceFragment fragment = (SingleChoiceFragment) fragments.get(i);
            fragment.checkAnswer();
            if (!fragment.isHasFinished()) {
                integers.add(i + 1);
            }
        }
        return integers;
    }

    private ArrayList<Integer> errorItems() {
        ArrayList<Integer> rightItem = new ArrayList<>();
        for (int i = 0; i < fragments.size(); i++) {
            SingleChoiceFragment fragment = (SingleChoiceFragment) fragments.get(i);
            if (!fragment.isResultIsTrue()) {
                rightItem.add(i + 1);
            }
        }
        return rightItem;
    }

    private void showResult(ArrayList<Integer> result) {
        StringBuilder builder = new StringBuilder();
        int errorItem = result.size();
        if (errorItem == 0) {
            builder.append("不错哟，全部正确！");
        } else {
            builder.append("第");
            for (int i = 0; i < result.size(); i++) {
                builder.append(result.get(i));
                if (i != result.size() - 1) {
                    builder.append("，");
                } else {
                    builder.append("题错误。\n");
                }
            }
            builder.append("正确率：" + (int) (((double) (sumIndex - errorItem) / sumIndex) * 100) + "%" + "\n继续加油！");
        }
        AlertDialog.Builder builder1 = new AlertDialog.Builder(DoQuestionActivity.this).setMessage(builder.toString()).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DoQuestionActivity.this.finish();
            }
        });
        if (errorItem != 0)
            builder1.setNegativeButton("错题解析", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ArrayList<Integer> integers = errorItems();
                    Intent intent = new Intent(DoQuestionActivity.this,FailureAnalysisActivity.class);
                    ArrayList<BaseQuestion> baseQuestions = new ArrayList<BaseQuestion>();
                    ArrayList<ArrayList<Object>> selectedAnswers = new ArrayList<>();
                    for (int i = 0; i < integers.size(); i++) {
                        SingleChoiceFragment singleChoiceFragment = (SingleChoiceFragment) fragments.get(integers.get(i)-1);
                        baseQuestions.add(singleChoiceFragment.getQuestion());
                        selectedAnswers.add(singleChoiceFragment.getSelectedAnswer());
                    }
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("baseQuestions",baseQuestions);
                    bundle.putSerializable("selectedAnswers",selectedAnswers);
                    bundle.putInt("sumIndex",integers.size());
                    intent.putExtras(bundle);
                    startActivity(intent);
                    DoQuestionActivity.this.finish();
                }
            });
        builder1.create().show();
    }

    public void back(View v) {
        finish();
    }
}
