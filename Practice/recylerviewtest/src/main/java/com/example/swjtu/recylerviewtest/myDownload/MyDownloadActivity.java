package com.example.swjtu.recylerviewtest.myDownload;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.swjtu.recylerviewtest.R;
import com.example.swjtu.recylerviewtest.adapter.DownloadCourseSectionRecyclerAdapter;
import com.example.swjtu.recylerviewtest.entity.CourseResource;
import com.example.swjtu.recylerviewtest.entity.CourseSection;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by tangpeng on 2017/3/9.
 */

public class MyDownloadActivity extends AppCompatActivity {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private ArrayList<CourseSection> courseSections;

    private String[] courseName = {"IT行业职场英语", "中国茶道", "中国饮食文化", "海洋与人类文明的产生", "航空燃气涡轮发动机", "材料科学基础"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_download);
        initViews();
        setViews();
        initData();
    }

    private void initViews() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }

    private void setViews() {
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorIconBlue);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshCourses();
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(MyDownloadActivity.this, 1));
    }

    private void initData() {
        courseSections = new ArrayList<>();
        Random random = new Random();
        String[] type = {"视频", "文件"};
        for (int i = 0; i < 10; i++) {
            ArrayList<CourseResource> courseResources = new ArrayList<>();
            for (int j = 0; j < (random.nextInt(4) + 1); j++) {
                CourseResource courseResource = new CourseResource(type[random.nextInt(5) % type.length], "资源item:" + random.nextInt(10));
                courseResources.add(courseResource);
            }
            CourseSection courseSection = new CourseSection(courseName[random.nextInt(7) % courseName.length], courseResources);
            courseSections.add(courseSection);
        }
        recyclerView.setAdapter(new DownloadCourseSectionRecyclerAdapter(courseSections));
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
