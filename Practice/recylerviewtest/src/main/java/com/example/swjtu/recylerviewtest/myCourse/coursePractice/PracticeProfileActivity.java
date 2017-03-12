package com.example.swjtu.recylerviewtest.myCourse.coursePractice;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.swjtu.recylerviewtest.R;

/**
 * Created by tangpeng on 2017/3/11.
 */

public class PracticeProfileActivity extends AppCompatActivity {
    private static final String TAG = "PracticeProfileActivity";

    private Intent intent;
    private String groupId; //习题组的ID
    private int quesNum;
    private int timeLim;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_profile);
        intent = getIntent();

        groupId = intent.getStringExtra("groupId");
        quesNum = intent.getIntExtra("practiceNum", 0);
        timeLim = intent.getIntExtra("practiceTimeLimit", 0);

        setViews();
    }

    private void setViews() {
        ((TextView) findViewById(R.id.practiceTitle)).setText(intent.getStringExtra("practiceTitle"));
        ((TextView) findViewById(R.id.practiceUnitName)).setText(intent.getStringExtra("practiceUnitName"));
        ((TextView) findViewById(R.id.practiceUnitProfile)).setText(intent.getStringExtra("practiceUnitProfile"));
        ((TextView) findViewById(R.id.practiceNum)).setText(quesNum + "道题");
        ((TextView) findViewById(R.id.practiceTimeLimit)).setText(timeLim + "分钟");
        ((TextView) findViewById(R.id.practiceDeadLine)).setText(intent.getStringExtra("practiceDeadLine"));
    }

    //答题
    public void doPractice(View v) {
        startActivity(new Intent(PracticeProfileActivity.this, DoQuestionActivity.class).putExtra("groupId", groupId).putExtra("practiceNum", quesNum)
                .putExtra("practiceTimeLimit", timeLim));
        Log.i(TAG, "doPractice: groupId" + groupId + ", practiceNum" + quesNum + ",practiceTimeLimit " + timeLim);
    }

    public void back(View v) {
        finish();
    }
}
