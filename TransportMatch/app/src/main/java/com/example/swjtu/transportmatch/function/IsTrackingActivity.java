package com.example.swjtu.transportmatch.function;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.swjtu.transportmatch.R;

/**
 * Created by tangpeng on 2017/2/26.
 */

public class IsTrackingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_is_tracking);
    }

    public void back(View v) {
        finish();
    }
}
