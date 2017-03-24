package com.example.swjtu.recylerviewtest.searchCourse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.swjtu.recylerviewtest.R;
import com.example.swjtu.recylerviewtest.adapter.CourseCategoryRecyclerAdapter;

/**
 * Created by tangpeng on 2017/3/9.
 */

public class CourseCategoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private String[] names = {"计算机", "经济管理", "外语学习", "文学历史", "工学", "理学", "生命科学", "全部课程"};
    private int[] imageId = {R.mipmap.diannao, R.mipmap.meiyuan, R.mipmap.yuyan, R.mipmap.shu,
            R.mipmap.gongju, R.mipmap.jisuanqi, R.mipmap.yao, R.mipmap.all};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_category);
        initViews();
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewCategory);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setAdapter(new CourseCategoryRecyclerAdapter(imageId, names));
    }

    public void back(View v) {
        finish();
    }
}
