package com.example.swjtu.transportmatch.function;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.example.swjtu.transportmatch.R;
import com.example.swjtu.transportmatch.navigate.Navigate2AimActivity;

/**
 * Created by tangpeng on 2017/2/26.
 */

public class AimLocationActivity extends AppCompatActivity implements LocationSource,
        AMapLocationListener, AMap.OnMarkerClickListener, GeocodeSearch.OnGeocodeSearchListener, AMap.OnMapClickListener {

    private static final int DEBUG_EMULATOR = 0;    //0实时导航,1模拟导航

    private MapView mapView;
    private AMap aMap;

    private OnLocationChangedListener mListener;
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;
    private AMapLocation aMapLocation;  //返回的定位信息

    private boolean mFirstFix = false;
    private boolean aimFirstFix = false;

    private LatLng aimLatLng = new LatLng(30.70253, 104.08395);    //模拟目标位置

    private Marker mLocMarker;  //我的位置marker
    private Marker aimMaker;    //目标位置对应的marker
    private Marker aimInfoWindow;   //点击目标解析并显示地址的marker
    private Circle circle;

    private TextView distanceTextView;
    private String distanceStr;

    private String[] naviWay = new String[]{"步行", "驾车", "取消"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aim_location);

        distanceStr = getSharedPreferences("systemSetting", MODE_PRIVATE).getString("distance", "1km");
        initViews();
        mapView.onCreate(savedInstanceState);
        initData();
    }

    private void initViews() {
        mapView = (MapView) findViewById(R.id.map_aim_location);
        distanceTextView = (TextView) findViewById(R.id.textview_distance);
    }

    private void initData() {
        distanceTextView.setText(distanceStr);  //显示安全围栏半径
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
        addAimMaker();
    }

    //设置aMap的属性
    private void setUpMap() {
        aMap.setLocationSource(this);     //设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(false);  //显示定位按钮
        aMap.setMyLocationEnabled(true); //显示定位层并可触发定位
        aMap.setMyLocationType(AMap.MAP_TYPE_NORMAL);  //设置定位方式：定位，跟随，旋转
        aMap.showIndoorMap(true);   //开启室内地图
        aMap.setOnMarkerClickListener(this);    //监听点击marker
        aMap.setOnMapClickListener(this);
    }

    //是否开启卫星地图
    public void switchSatellite(View v) {
        CheckedTextView checkedTextView = (CheckedTextView) v;
        if (aMap != null) {
            if (checkedTextView.isChecked())
                aMap.setMapType(AMap.MAP_TYPE_SATELLITE);   //设置为卫星地图
            else aMap.setMapType(AMap.MAP_TYPE_NORMAL);   //设置为矢量地图
        }
        if (checkedTextView.isChecked()) {
            checkedTextView.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.satellite_press), null, null);
            //System.out.println("checked");
            checkedTextView.setChecked(false);
        } else {
            checkedTextView.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.satellite), null, null);
            //System.out.println("unchecked");
            checkedTextView.setChecked(true);
        }
    }

    //定位到自己的位置
    public void backMyLocation(View v) {
        if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {//将地图定位到当前位置
            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude()), 11));
        } else {
            Toast.makeText(this, "定位失败！", Toast.LENGTH_SHORT).show();
        }
    }

    //立即追踪
    public void trackNow(View v) {
        new AlertDialog.Builder(this).setItems(naviWay, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        openNavigate(1);//步行导航
                        break;
                    case 1:
                        openNavigate(2);//驾车导航追踪
                        break;
                    default:
                        break;
                }
            }
        }).create().show();
    }

    private void openNavigate(int type) {
        if (aMapLocation != null) {
            if (aimLatLng != null) {
                Intent intent2 = new Intent(AimLocationActivity.this, Navigate2AimActivity.class);
                intent2.putExtra("startLat", aMapLocation.getLatitude());
                intent2.putExtra("startLng", aMapLocation.getLongitude());
                intent2.putExtra("endLat", aimLatLng.latitude);
                intent2.putExtra("endLng", aimLatLng.longitude);
                intent2.putExtra("type", type); //设置导航方式
                intent2.putExtra("emulator", DEBUG_EMULATOR); //是否开启模拟
                startActivity(intent2);
            } else {
                Toast.makeText(AimLocationActivity.this, "没有获得目标位置！", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(AimLocationActivity.this, "定位失败！", Toast.LENGTH_SHORT).show();
        }
    }

    //添加目标定位
    private void addAimMaker() {
        if (aimMaker != null) {
            return;
        }
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.aim_location1);
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.icon(bitmapDescriptor);
        markerOptions.anchor(0.5f, 0.5f);
        markerOptions.position(aimLatLng);
        aimMaker = aMap.addMarker(markerOptions);
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(aimLatLng, 13));
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                this.aMapLocation = aMapLocation;
                LatLng latLng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                if (!mFirstFix) {
                    mFirstFix = true;
                    addMarker(latLng);  //在地图上添加定位图标
                    addCircle(latLng);
                    //aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
                } else {
                    mLocMarker.setPosition(latLng);
                    circle.setCenter(latLng);
                }
            }
        } else {
            String errStr = "定位失败," + aMapLocation.getErrorCode() + ":" + aMapLocation.getErrorInfo();
            Toast.makeText(this, errStr, Toast.LENGTH_SHORT).show();
        }
    }

    //添加定位marker
    private void addMarker(LatLng latLng) {
        if (mLocMarker != null) {
            return;
        }
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.my_location1);
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.icon(bitmapDescriptor);
        markerOptions.anchor(0.5f, 0.5f);
        markerOptions.position(latLng);
        mLocMarker = aMap.addMarker(markerOptions);
    }

    //在定位图标外添加围栏
    private void addCircle(LatLng latLng) {
        if (circle != null) {
            return;
        }
        int radius = 1000;
        switch (distanceStr) {
            case "10米":
                radius = 10;
                break;
            case "20米":
                radius = 20;
                break;
            case "40米":
                radius = 40;
                break;
            case "100米":
                radius = 100;
                break;
            case "500米":
                radius = 500;
                break;
            case "1km":
                radius = 1000;
                break;
            case "5km":
                radius = 5000;
                break;
            case "10km":
                radius = 10000;
                break;
            case "50km":
                radius = 50000;
                break;
        }
        circle = aMap.addCircle(new CircleOptions().center(latLng).radius(radius).strokeColor(getResources().getColor(R.color.colorIconBlue)).fillColor(Color.argb(50, 1, 1, 1)).strokeWidth(2));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.equals(aimMaker)) {
            GeocodeSearch geocodeSearch = new GeocodeSearch(this);
            geocodeSearch.setOnGeocodeSearchListener(this);
            RegeocodeQuery regeocodeQuery = new RegeocodeQuery(new LatLonPoint(marker.getPosition().latitude, marker.getPosition().longitude), 200f, GeocodeSearch.GPS);
            geocodeSearch.getFromLocationAsyn(regeocodeQuery);  //开始解析
            View rootView = getLayoutInflater().inflate(R.layout.layout_aim_top_window, null);
            rootView.setBackgroundResource(R.drawable.custom_info_bubble);
            //TextView address = (TextView) rootView.findViewById(R.id.aim_address);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(aimMaker.getPosition());
            markerOptions.icon(BitmapDescriptorFactory.fromView(rootView));
            aimInfoWindow = aMap.addMarker(markerOptions);
        } else {
            if (aimInfoWindow != null) {
                aimInfoWindow.destroy();
            }
        }
        return false;
    }

    //激活定位，打开定位服务
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (null == mLocationClient) {
            mLocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            mLocationClient.setLocationListener(this);  //设置定位监听
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy); //设置高精度定位模式
            mLocationClient.setLocationOption(mLocationOption); //设置定位参数
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mLocationClient.startLocation();
        }
    }

    //停止定位
    @Override
    public void deactivate() {
        mListener = null;
        if (null != mLocationClient) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
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
        deactivate();
        mFirstFix = false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapView.onSaveInstanceState(outState);
    }

    //逆地理编码
    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        View rootView = getLayoutInflater().inflate(R.layout.layout_aim_top_window, null);
        rootView.setBackgroundResource(R.drawable.custom_info_bubble);
        TextView address = (TextView) rootView.findViewById(R.id.aim_address);
        if (aimInfoWindow != null) {
            aimInfoWindow.destroy();
        }
        if (i == AMapException.CODE_AMAP_SUCCESS) {
            if (regeocodeResult.getRegeocodeAddress() != null
                    && regeocodeResult.getRegeocodeAddress().getFormatAddress() != null) {
                address.setText(regeocodeResult.getRegeocodeAddress().getFormatAddress() + "附近");

            } else {
                address.setText("没有相关数据");
            }
        } else {
            address.setText("解析地址失败");
        }
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(aimMaker.getPosition());
        markerOptions.icon(BitmapDescriptorFactory.fromView(rootView));
        aimInfoWindow = aMap.addMarker(markerOptions);
    }

    //地理编码
    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    @Override
    public void onMapClick(LatLng latLng) {
        if (aimInfoWindow != null) {
            aimInfoWindow.destroy();
        }
    }
}
