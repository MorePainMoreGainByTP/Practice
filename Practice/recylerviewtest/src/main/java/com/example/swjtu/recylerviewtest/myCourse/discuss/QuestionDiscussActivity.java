package com.example.swjtu.recylerviewtest.myCourse.discuss;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.swjtu.recylerviewtest.R;
import com.example.swjtu.recylerviewtest.adapter.CourseDiscussRecyclerAdapter;
import com.example.swjtu.recylerviewtest.entity.DiscussTopic;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Created by tangpeng on 2017/3/8.
 */

public class QuestionDiscussActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private ArrayList<DiscussTopic> discussTopics;
    private Intent intent;

    private String[] content = {"C语言程序设计C语言程序设计C语言程序设计", "操作系统操作系统操作系统", "计算机网络计算机网络计算机网络", "H5+JS+CSS3H5+JS+CSS3H5+JS+CSS3",
            "PHP教学PHP教学PHP教学PHP教学PHP教学", "财务报表编制财务报表编制财务报表编制财务报表编制财务报表编制", "博弈的思维看世界博弈的思维看世界博弈的思维看世界博弈的思维看世界博弈的思维看世界"};
    private String[] titles = {"C语言程序设计", "操作系统", "计算机网络", "H5+JS+CSS3",
            "PHP教学", "财务报表编制", "博弈的思维看世界"};
    private String[] fromWho = {"小张", "小明", "小飞", "小葵", "小杨", "小赵", "小李"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_discuss);
        intent = getIntent();

        initViews();
        setViews();
        initData();
    }

    private void initViews() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewDiscuss);
    }

    private void setViews() {
        ((TextView) findViewById(R.id.discussTitle)).setText(intent.getStringExtra("title"));
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.colorIconBlue);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshCourses();
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(QuestionDiscussActivity.this, 1));
    }

    private void initData() {
        discussTopics = new ArrayList<>();
        Date date = new Date();
        Random random = new Random();
        for (int i = 0; i < titles.length; i++) {
            DiscussTopic discussTopic = new DiscussTopic(titles[i], content[i], (date.getYear() + 1900) + "-" + date.getMonth() + "-" + (date.getDate()
                    + random.nextInt(i+1)) + " " + date.getHours() + ":" + (date.getMinutes() + random.nextInt(20)) % 60 + ":" + date.getSeconds(), fromWho[i]);
            discussTopics.add(discussTopic);
        }
        recyclerView.setAdapter(new CourseDiscussRecyclerAdapter(discussTopics));
    }


    //发表帖子
    public void editComment(View v) {

    }

    private void refreshCourses() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                SystemClock.sleep(3000);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //停止刷新
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }.start();
    }

    public void back(View v) {
        finish();
    }
}
