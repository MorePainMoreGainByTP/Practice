package com.example.swjtu.transportmatch.function;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.swjtu.transportmatch.R;

/**
 * Created by tangpeng on 2017/2/26.
 */

public class HistoryTraceActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_trace);
    }

    public void back(View v) {
        finish();
    }
}
