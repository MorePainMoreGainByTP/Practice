package com.example.swjtu.recylerviewtest.myCourse;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.swjtu.recylerviewtest.R;
import com.example.swjtu.recylerviewtest.adapter.MyCourseRecyclerAdapter;
import com.example.swjtu.recylerviewtest.entity.Course;
import com.example.swjtu.recylerviewtest.entity.Teacher;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by tangpeng on 2017/3/7.
 */

public class MyCourseListActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private int[] imageId = {R.drawable.img22, R.drawable.img23, R.drawable.img24, R.drawable.img25, R.drawable.img26};
    private String[] courseName = {"博弈的思维看世界", "冷战史专题", "创业：道与术", "操作系统", "英语教学与互联网"};
    private String[] courseTeacher = {"尚补偲", "朱元甫", "秦才", "赵文轩", "刘圣凯"};
    private List<Course> courseList;

    private String[] positions = {"教授", "副教授"};
    Random random = new Random();
    private ArrayList<String> courseProfile = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_course);
        initViews();
        initData();
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_my_course);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
    }

    private void initData() {
        courseList = new ArrayList<>();
        initCourseProfile();
        for (int i = 0; i < imageId.length; i++) {
            Course course = new Course(imageId[i], courseName[i], new Teacher(courseTeacher[i], positions[random.nextInt(5) % positions.length]), "", courseProfile.get(i));
            courseList.add(course);
        }
        MyCourseRecyclerAdapter myCourseRecyclerAdapter = new MyCourseRecyclerAdapter(courseList);
        recyclerView.setAdapter(myCourseRecyclerAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.aquamarine);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshCourses();
            }
        });
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

    private void initCourseProfile() {
        for (int i = 0; i < imageId.length / 5 + 1; i++) {
            courseProfile.add("《大学英语综合课程》是一门以培养学习者的语言基本技能、跨文化交际能力与批判性思维能力为总目标的综合性英语课程。旨在通过本课程学习，" +
                    "帮助学习者比较熟练地掌握听、说、读、写等语言基本技能，提升文化意识和跨文化交际能力，并增强独立思考、发现与解决问题等自主学习能力和思辨能力。");
            courseProfile.add("迅速吸收最新行业资讯，和来自全球的客户保持有效沟通和交流，是IT高端人才的职场护身绝技! IT行业职场英语课程聚焦你的沟通能力，" +
                    "阅读和写作能力，思维与决策力，助力你在人才济济的IT行业笑傲江湖！");
            courseProfile.add("大道至简，大象无形。南怀瑾先生曾有一个形象的比喻：儒家像粮店，佛家像百货店，而道家像药店。当我们面临着这么多的困扰、困惑的时候，" +
                    "老子和庄子的道家智慧，在很多方面能够为我们答疑解惑。本课程将引领各位探讨中国文化的根源之一，道家智慧。让我们一起来领略老子和庄子的人格魅力与精神境界！");
            courseProfile.add("本课程主要讲述计算机控制系统理论与工程设计的基础理论与方法，其中主要包括信号变换、系统建模与性能分析、数字控制器的模拟化设计方法、数字控制器的直接设计方法，基于状态空间模型的数字控制器极点配置设计方法，计算机控制系统仿真，以及计算机控制系统的工程化实现等技术。同时，课程设置了针对不同被控对象特性的多种实验，包括基础型实验和研究型实验，以加深对计算机控制系统基础理论和方法的理解。\n" +
                    "通过本课程的学习，将使学生掌握计算机控制系统设计的基本方法，培养学生应用所学过的控制理论基本知识分析和解决实际问题的能力，为进一步的学术研究和工程应用奠定基础。");
            courseProfile.add("本课程是理工科各专业的专业基础核心课程，是面向高校理工科专业的学生开设的一门计算机基础课程。通过本课程的学习，学生可以对计算机网络有一个基本的认识，了解当今计算机网络的现状和发展趋势，掌握计算机网络涉及的基本概念，掌握计算机网络应用基础知识，理解和掌握Internet的工作原理，熟练应用Internet提供的各种服务，从而掌握计算机网络的技术原理和综合应用。本课程培养学生的思维能力和实践动手能力，" +
                    "为学生学习后续课程以及解决生活、工作中遇到的相关问题提供技术和应用能力的支撑。");
        }
    }

    public void back(View v) {
        finish();
    }
}
