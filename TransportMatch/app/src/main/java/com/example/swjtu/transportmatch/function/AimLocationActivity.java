package com.example.swjtu.transportmatch.function;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.example.swjtu.transportmatch.R;

/**
 * Created by tangpeng on 2017/2/26.
 */

public class AimLocationActivity extends AppCompatActivity {

    private MapView mapView;
    private AMap aMap;

    private String[] naviWay = new String[]{"步行", "驾车", "取消"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aim_location);

        initViews();
        mapView.onCreate(savedInstanceState);
        initData();
    }

    private void initViews() {
        mapView = (MapView) findViewById(R.id.map_aim_location);
    }

    private void initData() {
        if (aMap == null)
            aMap = mapView.getMap();
    }

    //立即追踪
    public void trackNow(View v) {
        new AlertDialog.Builder(this).setItems(naviWay, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        break;   //步行导航进行追踪
                    case 1:
                        break;   //驾车导航追踪
                    default:
                        break;
                }
            }
        }).create().show();
    }

    public void back(View v) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mapView.onDestroy()，销毁地图
        mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mapView.onResume ()，重新绘制加载地图
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mapView.onPause ()，暂停地图的绘制
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapView.onSaveInstanceState(outState);
    }


}
