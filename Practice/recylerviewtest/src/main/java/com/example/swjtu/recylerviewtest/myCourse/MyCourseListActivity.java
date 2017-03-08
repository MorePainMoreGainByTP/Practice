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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangpeng on 2017/3/7.
 */

public class MyCourseListActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private int[] imageId = {R.drawable.img22, R.drawable.img23, R.drawable.img24, R.drawable.img25, R.drawable.img26};
    private String[] courseName = {"大学物理", "宇宙探索与发现", "药物分析", "药物化学", "太极拳医学"};
    private String[] courseTeacher = {"尚补偲", "朱元甫", "秦才", "赵文轩", "刘圣凯"};
    private List<Course> courseList;


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
        for (int i = 0; i < imageId.length; i++) {
            Course course = new Course(imageId[i], courseName[i], courseTeacher[i]);
            courseList.add(course);
        }
        MyCourseRecyclerAdapter myCourseRecyclerAdapter = new MyCourseRecyclerAdapter(courseList);
        recyclerView.setAdapter(myCourseRecyclerAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent,R.color.aquamarine);
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

    public void back(View v){
        finish();
    }
}
