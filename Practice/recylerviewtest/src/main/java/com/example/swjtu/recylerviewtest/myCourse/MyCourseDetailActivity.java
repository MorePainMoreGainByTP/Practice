package com.example.swjtu.recylerviewtest.myCourse;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.swjtu.recylerviewtest.R;
import com.example.swjtu.recylerviewtest.customView.ViewPagerIndicator;
import com.example.swjtu.recylerviewtest.entity.CoursePractice;
import com.example.swjtu.recylerviewtest.entity.CoursePracticeSection;
import com.example.swjtu.recylerviewtest.entity.CourseResource;
import com.example.swjtu.recylerviewtest.entity.CourseSection;
import com.example.swjtu.recylerviewtest.entity.Teacher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by tangpeng on 2017/3/8.
 */

public class MyCourseDetailActivity extends AppCompatActivity {

    private String courseProfile = "迅速吸收最新行业资讯，和来自全球的客户保持有效沟通和交流，" +
            "是IT高端人才的职场护身绝技! IT行业职场英语课程聚焦你的沟通能力，阅读和写作能力，思维与决策力，助力你在人才济济的IT行业笑傲江湖！";
    private ArrayList<CourseSection> courseSections;
    private ArrayList<CoursePracticeSection> coursePracticeSections;

    private ViewPagerIndicator viewPagerIndicator;
    private ViewPager viewPager;
    private List<String> tabTitles = Arrays.asList(new String[]{"简介", "课件", "练习", "讨论"});
    private List<Fragment> fragmentList;

    private FloatingActionButton handActionButton;
    private SwipeRefreshLayout swipeRefreshLayout;

    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_course_detail);

        intent = getIntent();
        setActionBar();
        initViews();
        initData();
        setViews();
    }

    private void setActionBar() {
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolBar);
        collapsingToolbarLayout.setTitle(intent.getStringExtra("courseName"));
        Toolbar toolbar = (Toolbar) findViewById(R.id.tooBar_detail);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        ((ImageView) findViewById(R.id.course_image_view_detail)).setBackgroundResource(intent.getIntExtra("imageId", 0));
    }

    private void initViews() {
        handActionButton = (FloatingActionButton) findViewById(R.id.handFloatingButton);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        viewPager = (ViewPager) findViewById(R.id.id_viewpager);
        viewPagerIndicator = (ViewPagerIndicator) findViewById(R.id.id_viewpager_indicator);
    }

    private void setViews() {
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.colorIconBlue);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshCourses();
            }
        });

        viewPager.setOffscreenPageLimit(3);
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

        viewPagerIndicator.setVisibleTanCount(4);
        viewPagerIndicator.setItemTitles(tabTitles);
        viewPagerIndicator.setRadioTriangleWidth(1 / 6f);
        viewPagerIndicator.setViewPager(viewPager, 0);
    }

    private void initData() {
        fragmentList = new ArrayList<>();
        fragmentList.add(CourseIntroduceFragment.newInstance(courseProfile, new Teacher(intent.getStringExtra("courseTeacher"), "教授")));
        initCourseSectionList();
        fragmentList.add(CourseResourceFragment.newInstance(courseSections));
        fragmentList.add(CoursePracticeFragment.newInstance(coursePracticeSections));
        fragmentList.add(new CourseDiscussFragment());
    }

    private void initCourseSectionList() {
        courseSections = new ArrayList<>();
        Random random = new Random();
        String[] type = {"视频", "文件"};
        for (int i = 0; i < 10; i++) {
            ArrayList<CourseResource> courseResources = new ArrayList<>();
            for (int j = 0; j < (random.nextInt(4) + 1); j++) {
                CourseResource courseResource = new CourseResource(type[random.nextInt(5) % type.length], "资源item:" + random.nextInt(10));
                courseResources.add(courseResource);
            }
            CourseSection courseSection = new CourseSection("第" + random.nextInt(10) + "章", courseResources);
            courseSections.add(courseSection);
        }

        coursePracticeSections = new ArrayList<>();
        String[] practiceType = {"作业", "练习", "考试"};
        for (int i = 0; i < 10; i++) {
            ArrayList<CoursePractice> coursePractices = new ArrayList<>();
            for (int j = 0; j < (random.nextInt(4) + 1); j++) {
                CoursePractice coursePractice = new CoursePractice(practiceType[random.nextInt(5) % practiceType.length], "测试item:" + random.nextInt(10));
                coursePractices.add(coursePractice);
            }
            CoursePracticeSection coursePracticeSection = new CoursePracticeSection("第" + random.nextInt(10) + "章", coursePractices);
            coursePracticeSections.add(coursePracticeSection);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
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
