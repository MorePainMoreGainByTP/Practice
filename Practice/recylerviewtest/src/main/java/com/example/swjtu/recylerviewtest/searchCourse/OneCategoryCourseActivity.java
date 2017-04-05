package com.example.swjtu.recylerviewtest.searchCourse;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.swjtu.recylerviewtest.R;
import com.example.swjtu.recylerviewtest.adapter.CategoryCourseListRecyclerAdapter;
import com.example.swjtu.recylerviewtest.entity.Course;
import com.example.swjtu.recylerviewtest.entity.Teacher;

import java.util.ArrayList;

/**
 * Created by tangpeng on 2017/3/15.
 */

public class OneCategoryCourseActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private Intent intent;

    private int[] imageId = {R.drawable.img4, R.drawable.img5, R.drawable.img11, R.drawable.img29, R.drawable.img25, R.drawable.img30, R.drawable.img27,
            R.drawable.img28, R.drawable.img32, R.drawable.img31};
    private String[] courseName = {"H5+JS+CSS3", "PHP应用与开发", "C语言程序设计", "计算机网络技术", "操作系统", "软件工程专业导论",
            "Python数据分析与展示", "数据结构", "大学计算机基础", "网络技术与应用"};
    private String[] courseTeacher = {"李庆", "冯凯", "李丹崖", "邵佳一", "赵文轩", "刘文", "杨锡城", "李晨威", "阎浩瀚", "郑伟"};

    private ArrayList<Course> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_category);
        intent = getIntent();
        initViews();
        toolbar.setTitle(intent.getStringExtra("category"));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        initArrayList();
        setRecyclerView();
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.aquamarine);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshCourses();
            }
        });
    }

    private void initArrayList() {
        for (int i = 0; i < imageId.length; i++) {
            Teacher teacher = new Teacher(courseTeacher[i], "教授");
            Course course = new Course(imageId[i], courseName[i], teacher, "", "");
            arrayList.add(course);
        }
    }

    private void setRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setAdapter(new CategoryCourseListRecyclerAdapter(arrayList));
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
