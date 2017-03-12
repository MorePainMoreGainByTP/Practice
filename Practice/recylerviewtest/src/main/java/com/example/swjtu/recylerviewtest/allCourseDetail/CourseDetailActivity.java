package com.example.swjtu.recylerviewtest.allCourseDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.swjtu.recylerviewtest.R;
import com.example.swjtu.recylerviewtest.entity.Course;
import com.example.swjtu.recylerviewtest.entity.Teacher;

/**
 * Created by tangpeng on 2017/3/6.
 */

public class CourseDetailActivity extends AppCompatActivity {

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;
    private ImageView courseImage;
    private TextView courseProfile;

    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        intent = getIntent();
        initViews();
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        initData();
    }

    private void initViews() {
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolBar);
        toolbar = (Toolbar) findViewById(R.id.tooBar_detail);
        courseImage = (ImageView) findViewById(R.id.course_image_view_detail);
        courseProfile = (TextView) findViewById(R.id.course_content_text);

    }

    private void initData() {
        Bundle bundle = intent.getExtras();
        Course course = (Course) bundle.getSerializable("course");
        Teacher teacher = (Teacher) bundle.getSerializable("teacher");
        collapsingToolbarLayout.setTitle(course.getName());
        courseProfile.setText(course.getCourseProfile());
        Glide.with(this).load(course.getImageId()).into(courseImage);
        Glide.with(this).load(R.mipmap.teacher).into((ImageView) findViewById(R.id.teacherHeaderImage));
        ((TextView) findViewById(R.id.teacherName)).setText(teacher.getName());
        ((TextView) findViewById(R.id.teacherPosition)).setText(teacher.getPosition());
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
