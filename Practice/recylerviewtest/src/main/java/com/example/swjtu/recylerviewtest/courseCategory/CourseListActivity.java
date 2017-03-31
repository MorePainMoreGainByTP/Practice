package com.example.swjtu.recylerviewtest.courseCategory;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.swjtu.recylerviewtest.R;

/**
 * Created by tangpeng on 2017/3/28.
 */

public class CourseListActivity extends AppCompatActivity implements TextWatcher {
    private EditText editSearch;
    private TextView textSearch, courseCategory;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_category_list);
        initViews();
    }

    private void initViews() {
        editSearch = (EditText) findViewById(R.id.editSearchName);
        editSearch.addTextChangedListener(this);
        textSearch = (TextView) findViewById(R.id.btnSearch);
        courseCategory = (TextView) findViewById(R.id.courseCategory);
        courseCategory.setText(getIntent().getStringExtra("category"));
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerCategoryList);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.colorIconBlue);
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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s != null && !s.equals("")) {
            textSearch.setVisibility(View.VISIBLE);
        } else {
            textSearch.setVisibility(View.GONE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

}
