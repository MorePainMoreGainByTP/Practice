package com.example.swjtu.recylerviewtest.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.swjtu.recylerviewtest.R;


/**
 * Created by tangpeng on 2017/3/9.
 */

public class SettingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    public void back(View v) {
        finish();
    }
}
