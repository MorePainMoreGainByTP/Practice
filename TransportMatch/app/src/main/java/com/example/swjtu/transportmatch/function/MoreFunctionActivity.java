package com.example.swjtu.transportmatch.function;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.swjtu.transportmatch.R;
import com.example.swjtu.transportmatch.systemSetting.SystemSettingActivity;

/**
 * Created by tangpeng on 2017/2/26.
 */

public class MoreFunctionActivity extends AppCompatActivity {

    private TextView safeDistance;
    private CheckBox switchCommand;

    private String[] safeDistanceItems = new String[]{"10米", "20米", "40米", "100米", "500米", "1km", "5km", "10km"};
    private String[] commandItems = new String[]{"声音(默认)", "振动", "声音+振动","取消"};
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_function);

        sharedPreferences = getSharedPreferences("systemSetting", MODE_PRIVATE);
        initViews();
        initData();
    }

    private void initViews() {
        safeDistance = (TextView) findViewById(R.id.textview_safe_distance);
        switchCommand = (CheckBox) findViewById(R.id.switch_command);

        switchCommand.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {//启动指令报警，调用百度云推送
                    new AlertDialog.Builder(MoreFunctionActivity.this).setItems(commandItems, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    break;//启动声音报警
                                case 1:
                                    break;//启动振动报警
                                case 2:
                                    break;//启动声音 + 振动报警
                                default:switchCommand.setChecked(false);break;
                            }
                        }
                    }).create().show();
                } else {//关闭指令报警

                }
            }
        });
    }

    private void initData() {
        String distance = sharedPreferences.getString("distance", "100米");
        safeDistance.setText(distance);
    }

    public void aimLocation(View v) {//查看目标当前位置
        startActivity(new Intent(MoreFunctionActivity.this, AimLocationActivity.class));
    }

    public void historyTrace(View v) {//查看目标的历史轨迹
        startActivity(new Intent(MoreFunctionActivity.this, HistoryTraceActivity.class));
    }

    public void safeDistance(View v) {//设置安全围栏半径
        new AlertDialog.Builder(this).setItems(safeDistanceItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                safeDistance.setText(safeDistanceItems[which]);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("distance", safeDistanceItems[which]);
                editor.apply();
            }
        }).create().show();
    }

    public void setting(View v) {//系统设置
        startActivity(new Intent(MoreFunctionActivity.this, SystemSettingActivity.class));
    }

    public void back(View v) {
        finish();
    }
}
