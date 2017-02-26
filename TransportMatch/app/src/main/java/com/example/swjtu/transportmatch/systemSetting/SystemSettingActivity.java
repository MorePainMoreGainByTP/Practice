package com.example.swjtu.transportmatch.systemSetting;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.swjtu.transportmatch.R;

/**
 * Created by tangpeng on 2017/2/26.
 */

public class SystemSettingActivity extends AppCompatActivity {

    private String[] tipFrequency = new String[]{"15s", "30s", "1min", "5min", "10min", "30min", "1h", "3h", "10h"};
    private TextView frequencyTextView;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stystem_setting);

        sharedPreferences = getSharedPreferences("systemSetting", MODE_PRIVATE);

        initViews();
        initData();
    }

    private void initViews() {
        frequencyTextView = (TextView) findViewById(R.id.textview_safe_tip_frequency);
    }

    private void initData() {
        String frequency = sharedPreferences.getString("frequency", "1min");
        frequencyTextView.setText(frequency);
    }

    public void changeId(View v) {//修改绑定的ID
        startActivity(new Intent(SystemSettingActivity.this, ChangeIdActivity.class));
    }

    public void safeTipFrequency(View v) {//设置安全围栏提示频率
        new AlertDialog.Builder(this).setItems(tipFrequency, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                frequencyTextView.setText(tipFrequency[which]);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("frequency", tipFrequency[which]);
                editor.apply();
            }
        }).create().show();
    }

    public void back(View v) {
        finish();
    }
}
