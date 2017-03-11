package com.example.swjtu.recylerviewtest.info;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.swjtu.recylerviewtest.R;
import com.example.swjtu.recylerviewtest.customView.ViewPagerIndicator;
import com.example.swjtu.recylerviewtest.entity.DiscussTopic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.example.swjtu.recylerviewtest.Utils.getRandomDatetime;

/**
 * Created by tangpeng on 2017/3/8.
 */

public class MyMessageActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private ViewPagerIndicator viewPagerIndicator;

    private List<String> tabTitle = Arrays.asList(new String[]{"我的话题", "回复我的"});
    private List<Fragment> fragments = new ArrayList<>();

    private ArrayList<DiscussTopic> myTopics;    //我的话题
    private ArrayList<DiscussTopic> replyTopics;    //回复我的
    private String[] topicTitle = {"C语言程序设计", "操作系统", "计算机网络", "H5+JS+CSS3", "PHP教学", "财务报表编制", "博弈的思维看世界"};
    private String[] topicContent = {"C语言程序设计C语言程序设计", "操作系统操作系统", "计算机网络计算机网络", "H5+JS+CSS3H5+JS+CSS3",
            "PHP教学PHP教学PHP教学", "财务报表编制财务报表编制财务报表编制", "博弈的思维看世界博弈的思维看世界博弈的思维看世界"};
    private String[] fromWho = {"张三超", "胡管", "刘芳", "李煜进", "蔡京", "张中丞", "陈意涵", "熊玉芳", "刘毅"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message);

        initViews();
        initData();
        setViews();
    }

    private void initViews() {
        viewPager = (ViewPager) findViewById(R.id.viewpager_my_topic);
        viewPagerIndicator = (ViewPagerIndicator) findViewById(R.id.viewpager_indicator_my_topic);
    }

    private void initData() {
        //首先填充数据
        myTopics = new ArrayList<>();
        replyTopics = new ArrayList<>();
        Random random = new Random();
        for (int i = 1; i < 15; i++) {
            DiscussTopic discussTopic = new DiscussTopic(topicTitle[i % topicTitle.length], topicContent[i % topicContent.length], getRandomDatetime(), null);
            myTopics.add(discussTopic);

            DiscussTopic replyTopic = new DiscussTopic(topicTitle[i % topicTitle.length], topicContent[i % topicContent.length], getRandomDatetime(), fromWho[random.nextInt(15) % fromWho.length] + " 回复:");
            replyTopics.add(replyTopic);
        }

        fragments.add(MyTopicFragment.newInstance(myTopics));
        fragments.add(ReplyMeFragment.newInstance(replyTopics));

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
    }

    private void setViews() {
        viewPagerIndicator.setVisibleTanCount(2);
        viewPagerIndicator.setItemTitles(tabTitle);
        viewPagerIndicator.setRadioTriangleWidth(1 / 10f);
        viewPagerIndicator.setViewPager(viewPager, 0);
    }


    public void back(View v) {
        finish();
    }
}
