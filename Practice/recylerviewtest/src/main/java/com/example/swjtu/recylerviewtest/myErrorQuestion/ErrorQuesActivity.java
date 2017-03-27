package com.example.swjtu.recylerviewtest.myErrorQuestion;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.swjtu.recylerviewtest.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by tangpeng on 2017/3/27.
 */

public class ErrorQuesActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private List<Fragment> fragmentList;
    private TextView txtCurrIndex, txtSumIndex;
    private Button last, next;

    private int currIndex = 0, sumIndex;
    Random random = new Random();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_ques);
        sumIndex = random.nextInt(15) + 1;
        initViews();
        initData();
    }

    private void initViews() {
        viewPager = (ViewPager) findViewById(R.id.viewpager_error_que);
        last = (Button) findViewById(R.id.last);
        next = (Button) findViewById(R.id.next);
        txtCurrIndex = (TextView) findViewById(R.id.txtCurrIndex);
        txtSumIndex = (TextView) findViewById(R.id.txtSumIndex);
    }

    private void initData() {
        txtCurrIndex.setText("1");
        txtSumIndex.setText("" + sumIndex);
        fragmentList = new ArrayList<>();
        for (int i = 0; i < sumIndex; i++) {
            ErrorQueFragment fragment = ErrorQueFragment.newInstance(random.nextInt(4), "" + (i + 1));
            fragmentList.add(fragment);
        }

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currIndex = position;
                txtCurrIndex.setText("" + (currIndex + 1));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setOffscreenPageLimit(sumIndex);
    }

    public void last(View v) {
        if (currIndex == 0) {
            Toast.makeText(this, "已是第一题", Toast.LENGTH_SHORT).show();
        } else {
            viewPager.setCurrentItem(--currIndex);
        }
        next.setEnabled(true);
        txtCurrIndex.setText("" + (currIndex + 1));
    }

    public void next(View v) {
        if (currIndex != fragmentList.size() - 1) {
            currIndex++;
        }
        viewPager.setCurrentItem(currIndex);
        if (currIndex == fragmentList.size() - 1) {
            next.setEnabled(false);
        }
        txtCurrIndex.setText("" + (currIndex + 1));
    }

    public void back(View v) {
        finish();
    }
}
