package com.example.swjtu.recylerviewtest.myCourse.coursePractice;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.swjtu.recylerviewtest.R;
import com.example.swjtu.recylerviewtest.entity.BaseQuestion;
import com.example.swjtu.recylerviewtest.myCourse.coursePractice.fragment.FailureAnalysisFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangpeng on 2017/3/26.
 */

public class FailureAnalysisActivity extends AppCompatActivity {

    private TextView txtCurrIndex, txtSumIndex;
    private ViewPager viewPager;
    private Button lastBtn, nextBtn;

    private Intent intent;

    private int currIndex = 0, sumIndex;
    private ArrayList<BaseQuestion> baseQuestions;
    private ArrayList<ArrayList<Object>> selectedAnswers;
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_failure_analysis);
        intent = getIntent();
        initViews();
        getData();
        initData();
    }

    private void initViews() {
        txtCurrIndex = (TextView) findViewById(R.id.text_currIndex);
        txtSumIndex = (TextView) findViewById(R.id.sum_item);
        viewPager = (ViewPager) findViewById(R.id.viewpager_question);
        lastBtn = (Button) findViewById(R.id.last);
        nextBtn = (Button) findViewById(R.id.next);
    }

    private void getData() {
        Bundle bundle = intent.getExtras();
        sumIndex = bundle.getInt("sumIndex");
        baseQuestions = (ArrayList<BaseQuestion>) bundle.getSerializable("baseQuestions");
        selectedAnswers = (ArrayList<ArrayList<Object>>) bundle.getSerializable("selectedAnswers");
    }

    private void initData() {
        txtCurrIndex.setText("" + (currIndex + 1));
        txtSumIndex.setText("" + sumIndex);
        for (int i = 0; i < sumIndex; i++) {
            FailureAnalysisFragment fragment = FailureAnalysisFragment.newInstance(baseQuestions.get(i), (i + 1) + "", selectedAnswers.get(i));
            fragments.add(fragment);
        }
        viewPager.setOffscreenPageLimit(sumIndex);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currIndex = position;
                txtCurrIndex.setText((currIndex + 1) + "");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(currIndex);
    }

    public void last(View v) {
        nextBtn.setText("下一题");
        nextBtn.setTextColor(getResources().getColor(R.color.black));
        if (currIndex == 0) {
            Toast.makeText(this, "当前已是第一道题", Toast.LENGTH_SHORT).show();
        } else {
            viewPager.setCurrentItem(--currIndex);
        }
        txtCurrIndex.setText((currIndex + 1) + "");
    }

    public void next(View v) {
        if (currIndex == sumIndex - 1) {
            new AlertDialog.Builder(this).setMessage("是否离开?").setPositiveButton("离开", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FailureAnalysisActivity.this.finish();
                }
            }).setNegativeButton("取消", null).create().show();
        } else {
            currIndex++;
            if (currIndex == sumIndex - 1) {
                nextBtn.setText("离开");
                nextBtn.setTextColor(getResources().getColor(R.color.limegreen));
            }
            viewPager.setCurrentItem(currIndex);
        }
        txtCurrIndex.setText((currIndex + 1) + "");
    }


    public void back(View v) {
        finish();
    }
}
