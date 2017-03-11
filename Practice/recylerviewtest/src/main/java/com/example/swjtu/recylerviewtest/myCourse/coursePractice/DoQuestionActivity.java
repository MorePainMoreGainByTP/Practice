package com.example.swjtu.recylerviewtest.myCourse.coursePractice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.swjtu.recylerviewtest.R;

/**
 * Created by tangpeng on 2017/3/11.
 */

public class DoQuestionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_question);
    }

    public void back(View v){
        finish();
    }
}
