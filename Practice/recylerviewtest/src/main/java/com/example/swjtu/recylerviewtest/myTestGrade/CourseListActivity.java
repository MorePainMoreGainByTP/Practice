package com.example.swjtu.recylerviewtest.myTestGrade;

import android.os.Bundle;
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
 * Created by tangpeng on 2017/3/24.
 */

public class CourseListActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private int[] imageId = {R.drawable.img22, R.drawable.img23, R.drawable.img24, R.drawable.img25, R.drawable.img26};
    private String[] courseName = {"博弈的思维看世界", "冷战史专题", "创业：道与术", "操作系统", "英语教学与互联网"};
    private String[] courseTeacher = {"尚补偲", "朱元甫", "秦才", "赵文轩", "刘圣凯"};
    private String[] positions = {"教授", "副教授"};
    private List<Course> courseList;

    Random random = new Random();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
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
            Course course = new Course(imageId[i], courseName[i], new Teacher(courseTeacher[i], positions[random.nextInt(5) % positions.length]), "", "");
            courseList.add(course);
        }
        MyCourseRecyclerAdapter myCourseRecyclerAdapter = new MyCourseRecyclerAdapter(courseList);
        myCourseRecyclerAdapter.setViewGrade(true);
        recyclerView.setAdapter(myCourseRecyclerAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    public void back(View v) {
        finish();
    }
}
