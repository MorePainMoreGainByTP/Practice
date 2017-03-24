package com.example.swjtu.recylerviewtest.setting;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import com.example.swjtu.recylerviewtest.R;


/**
 * Created by tangpeng on 2017/3/9.
 */


public class SettingActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    dismissDialog();
                    Toast.makeText(SettingActivity.this, "已是最新版", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }


    public void updateApp(View v) {
        showDialog();
        new Thread() {
            @Override
            public void run() {
                super.run();
                SystemClock.sleep(2000);
                handler.sendEmptyMessage(1);
            }
        }.start();
    }

    public void aboutApp(View v) {

    }

    public void functionFeedback(View v) {

    }

    public void exitLogin(View v) {
        new AlertDialog.Builder(this).setMessage("退出登录？").setNegativeButton("取消", null).setPositiveButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).create().show();
    }


    private void showDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(SettingActivity.this);
            progressDialog.setMessage("检查中...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        progressDialog.show();
    }

    private void dismissDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public void back(View v) {
        finish();
    }
}
