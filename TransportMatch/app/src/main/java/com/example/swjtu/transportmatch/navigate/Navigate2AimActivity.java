package com.example.swjtu.transportmatch.navigate;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewListener;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviStaticInfo;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.autonavi.tbt.NaviStaticInfo;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.example.swjtu.transportmatch.R;
import com.example.swjtu.transportmatch.utils.ErrorInfo;
import com.example.swjtu.transportmatch.utils.TTSController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangpeng on 2017/3/1.
 */

public class Navigate2AimActivity extends AppCompatActivity implements AMapNaviViewListener, AMapNaviListener {
    //AMapNaviListener 导航事件监听，提供导航过程中的事件（如：路径规划成功/失败、拥堵重新算路、到达目的地等）回调接口。

    private static final String MSCAPPID = "58b2ad9a";

    private CheckBox switchCommand;
    private String[] commandItems = new String[]{"声音(默认)", "振动", "声音+振动", "取消"};
    private AMapNaviView aMapNaviView;  //AMapNavi 是导航对外控制类，提供计算规划路线、偏航以及拥堵重新算路等方法。注意：AMapNavi 不支持多实例。
    private AMapNavi aMapNavi;

    private List<NaviLatLng> starts;    //导航起始点坐标，可以多个
    private List<NaviLatLng> ends;    //导航起始点坐标，可以多个

    private Intent intent;
    private double startLat, startLng;   //起点经纬度
    private double endLat, endLng;   //终点经纬度

    private TTSController ttsController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigate2aim);

        //实例化语音引擎
        ttsController = TTSController.getInstance(this);
        ttsController.init(MSCAPPID);


        initViews();
        intent = getIntent();
        startLat = intent.getDoubleExtra("startLat", 0);
        startLng = intent.getDoubleExtra("startLng", 0);
        endLat = intent.getDoubleExtra("endLat", 0);
        endLng = intent.getDoubleExtra("endLng", 0);
        initNaviLatLngList();

        aMapNaviView.onCreate(savedInstanceState);
        aMapNaviView.setAMapNaviViewListener(this);

        aMapNavi = AMapNavi.getInstance(this);
        aMapNavi.addAMapNaviListener(this);
        aMapNavi.addAMapNaviListener(ttsController);    //因为ttsController实现了AMapNaviListener接口
    }

    //初始化 起终点坐标
    private void initNaviLatLngList() {
        if (starts == null) {
            starts = new ArrayList<>();
        } else {
            starts.clear();
        }
        if (ends == null) {
            ends = new ArrayList<>();
        } else {
            ends.clear();
        }

        starts.add(new NaviLatLng(startLat, startLng));
        ends.add(new NaviLatLng(endLat, endLng));
    }

    private void initViews() {
        aMapNaviView = (AMapNaviView) findViewById(R.id.navigate);

        switchCommand = (CheckBox) findViewById(R.id.switch_command);
        switchCommand.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {//启动指令报警，调用百度云推送
                    new AlertDialog.Builder(Navigate2AimActivity.this).setItems(commandItems, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    break;//启动声音报警
                                case 1:
                                    break;//启动振动报警
                                case 2:
                                    break;//启动声音 + 振动报警
                                default:
                                    switchCommand.setChecked(false);
                                    break;
                            }
                        }
                    }).create().show();
                } else {//关闭指令报警

                }
            }
        });
    }

    public void back(View v) {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        aMapNaviView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        aMapNaviView.onPause();
        //仅仅是停止你当前在说的这句话，一会到新的路口还是会再说的
        ttsController.stopSpeaking();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        aMapNaviView.onDestroy();
        //since 1.6.0 不再在naviview destroy的时候自动执行AMapNavi.stopNavi();请自行执行
        aMapNavi.stopNavi();
        aMapNavi.destroy();
    }


    //以下是AMapNaviViewListener接口的方法
    @Override
    public void onNaviSetting() {
        //底部导航设置点击回调
    }

    @Override
    public void onNaviCancel() {
        finish();
    }

    @Override
    public boolean onNaviBackClick() {
        return false;
    }

    @Override
    public void onNaviMapMode(int i) {
    }

    @Override
    public void onNaviTurnClick() {
        //转弯view的点击回调
    }

    @Override
    public void onNextRoadClick() {
        //下一个道路View点击回调
    }

    @Override
    public void onScanViewButtonClick() {
        //全览按钮点击回调
    }

    @Override
    public void onLockMap(boolean b) {
        //锁地图状态发生变化时回调
    }

    @Override
    public void onNaviViewLoaded() {
        Log.d("wlx", "导航页面加载成功");
        Log.d("wlx", "请不要使用AMapNaviView.getMap().setOnMapLoadedListener();会overwrite导航SDK内部画线逻辑");
    }

    //以下是AMapNaviListener接口的方法
    @Override
    public void onInitNaviFailure() {
    }

    //导航对象AMapNavi对象初始化成功时，进行路径规划。
    @Override
    public void onInitNaviSuccess() {
        /**
         * 方法:
         *   int strategy=mAMapNavi.strategyConvert(congestion, avoidhightspeed, cost, hightspeed, multipleroute);
         * 参数:
         * @congestion 躲避拥堵
         * @avoidhightspeed 不走高速
         * @cost 避免收费
         * @hightspeed 高速优先
         * @multipleroute 多路径
         *
         * 说明:
         *      以上参数都是boolean类型，其中multipleroute参数表示是否多条路线，如果为true则此策略会算出多条路线。
         * 注意:
         *      不走高速与高速优先不能同时为true
         *      高速优先与避免收费不能同时为true
         */
        int strategy = 0;
        try {
            strategy = aMapNavi.strategyConvert(true, false, false, false, false);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("onInitNaviSuccess：计算策略失败");
        }
        int type = intent.getIntExtra("type", 1);    //默认为步行导航
        if (type == 2) {//驾车
            //驾车路径计算：当坐标以列表形式存放时，列表的尾点为实际导航点（起点或终点），其他坐标点为辅助信息，带有方向性，可有效避免算路到马路的另一侧。
            //路径的计算策略包含单一策略和多策略，通过多策略，可计算出多条规划路径（最多3条），多路径通过 onCalculateMultipleRoutesSuccess(int[] routeIds) 回调。
            aMapNavi.calculateDriveRoute(starts, ends, null, strategy);
        } else if (type == 1) {//步行,只能有一个 起点和终点
            if (!aMapNavi.calculateWalkRoute(starts.get(0), ends.get(0))) {//没有规划成功
                Toast.makeText(this, "路径太长，规划失败！请换其他导航方式", Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public void onStartNavi(int i) {
        //开始导航回调
    }

    @Override
    public void onTrafficStatusUpdate() {

    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {
        //当前位置回调
    }

    //导航 SDK 中透传文字的回调
    @Override
    public void onGetNavigationText(int i, String s) {
        //播报类型和播报文字回调
    }

    @Override
    public void onEndEmulatorNavi() {
        //结束模拟导航
    }

    @Override
    public void onArriveDestination() {
        //到达目的地
    }

    @Override
    public void onArriveDestination(NaviStaticInfo naviStaticInfo) {
        //到达目的地，有统计信息回调
    }

    @Override
    public void onArriveDestination(AMapNaviStaticInfo aMapNaviStaticInfo) {

    }

    //当路径规划成功时，开启导航
    @Override
    public void onCalculateRouteSuccess() {
        int emulator = intent.getIntExtra("emulator", 0);    //默认实时导航
        if (emulator == 1) {
            //设置模拟导航的行车速度
            aMapNavi.setEmulatorNaviSpeed(150);
            //开始模拟导航
            aMapNavi.startNavi(NaviType.EMULATOR);
        } else if (emulator == 0) {
            //开始实时导航
            aMapNavi.startNavi(NaviType.GPS);
        }
    }

    @Override
    public void onCalculateRouteFailure(int errorInfo) {
        //路线计算失败
        Log.e("dm", "--------------------------------------------");
        Log.i("dm", "路线计算失败：错误码=" + errorInfo + ",Error Message= " + ErrorInfo.getError(errorInfo));
        Log.i("dm", "错误码详细链接见：http://lbs.amap.com/api/android-navi-sdk/guide/tools/errorcode/");
        Log.e("dm", "--------------------------------------------");
        Toast.makeText(this, "errorInfo：" + errorInfo + ",Message：" + ErrorInfo.getError(errorInfo), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onReCalculateRouteForYaw() {
        //偏航后重新计算路线回调
    }

    @Override
    public void onReCalculateRouteForTrafficJam() {
        //拥堵后重新计算路线回调
    }

    @Override
    public void onArrivedWayPoint(int i) {
        //到达途径点
    }

    @Override
    public void onGpsOpenStatus(boolean b) {
        //GPS开关状态回调
    }

    @Override
    public void onNaviInfoUpdated(AMapNaviInfo aMapNaviInfo) {

    }

    @Override
    public void onNaviInfoUpdate(NaviInfo naviInfo) {
        //导航过程中的信息更新，请看NaviInfo的具体说明
    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {

    }

    @Override
    public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {

    }

    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {
        //显示转弯回调
    }

    @Override
    public void hideCross() {
        //隐藏转弯回调
    }

    @Override
    public void showLaneInfo(AMapLaneInfo[] aMapLaneInfos, byte[] bytes, byte[] bytes1) {
        //显示车道信息
    }

    @Override
    public void hideLaneInfo() {
        //隐藏车道信息
    }

    @Override
    public void onCalculateMultipleRoutesSuccess(int[] ints) {
        //多路径算路成功回调
    }

    @Override
    public void notifyParallelRoad(int i) {
        if (i == 0) {
            Toast.makeText(this, "当前在主辅路过渡", Toast.LENGTH_SHORT).show();
            Log.d("wlx", "当前在主辅路过渡");
            return;
        }
        if (i == 1) {
            Toast.makeText(this, "当前在主路", Toast.LENGTH_SHORT).show();

            Log.d("wlx", "当前在主路");
            return;
        }
        if (i == 2) {
            Toast.makeText(this, "当前在辅路", Toast.LENGTH_SHORT).show();

            Log.d("wlx", "当前在辅路");
        }
    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {
        //更新交通设施信息
    }

    @Override
    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {
        //更新巡航模式的统计信息
    }

    @Override
    public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {
        //更新巡航模式的拥堵信息
    }
}
