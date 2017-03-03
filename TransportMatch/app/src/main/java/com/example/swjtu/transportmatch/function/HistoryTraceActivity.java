package com.example.swjtu.transportmatch.function;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.example.swjtu.transportmatch.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by tangpeng on 2017/2/26.
 */

public class HistoryTraceActivity extends AppCompatActivity {

    private static final String TAG = "HistoryTraceActivity";

    private List<LatLng> latLngList = new ArrayList<>();

    private LatLng myLocation = new LatLng(30.7638540000, 103.9849410000);
    private Marker startMarker;
    private Marker endMarker;
    private TextView dateView;
    private MapView mapView;
    private AMap aMap;
    private Polyline polyline;

    private Calendar calendar;
    private int year, month, day;
    private long currTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_trace);

        initViews();
        mapView.onCreate(savedInstanceState);
        initData();
        initLatLngList_2();
        LatLngBounds.Builder builder = LatLngBounds.builder();
        for (int i = 0; i < latLngList.size(); i++) {
            builder.include(latLngList.get(i));
        }
        aMap.moveCamera(CameraUpdateFactory.newLatLngBounds((builder.build()), 21));    //将镜头大小和位置调整刚好包含bounds

        addPolyline();
    }

    private void initViews() {
        dateView = (TextView) findViewById(R.id.textView_date);
        mapView = (MapView) findViewById(R.id.map);
    }

    private void initData() {
        if (aMap == null) {
            aMap = mapView.getMap();
        }

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        Date dateT = new Date(year, month, day);
        currTime = dateT.getTime();
        Log.i(TAG, "initData:currTime " + currTime);
        String timeStr = year + "-" + month + "-" + day;
        dateView.setText(timeStr);
    }

    //选择日期
    public void selectDate(View v) {
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Log.i(TAG, "onDateSet: " + year + "-" + (month + 1) + "-" + dayOfMonth);
                Date date = new Date(year, month + 1, dayOfMonth);
                if (currTime < date.getTime()) {
                    Toast.makeText(HistoryTraceActivity.this, "日期不能大于今天", Toast.LENGTH_SHORT).show();
                } else {
                    dateView.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                }
                Log.i(TAG, "onDateSet: date " + date.getTime());
            }
        }, year, month - 1, day).show();
    }

    public void back(View v) {
        finish();
    }

    private void initLatLngList_1() {
        latLngList.add(new LatLng(30.7638540000, 103.9849410000));
        latLngList.add(new LatLng(30.7629470000, 103.9833790000));
        latLngList.add(new LatLng(30.7619880000, 103.9817700000));
        latLngList.add(new LatLng(30.7609550000, 103.9798600000));
        latLngList.add(new LatLng(30.7595360000, 103.9770700000));
        latLngList.add(new LatLng(30.7615550000, 103.9748170000));
        latLngList.add(new LatLng(30.7635640000, 103.9726820000));
        latLngList.add(new LatLng(30.7669660000, 103.9765560000));
        latLngList.add(new LatLng(30.7680550000, 103.9768130000));
    }

    private void initLatLngList_2() {
        latLngList.add(new LatLng(30.7616230000, 103.9811910000));
        latLngList.add(new LatLng(30.7574740000, 103.9733160000));
        latLngList.add(new LatLng(30.7558930000, 103.9747210000));
        latLngList.add(new LatLng(30.7553600000, 103.9753280000));
        latLngList.add(new LatLng(30.7564440000, 103.9768400000));
        latLngList.add(new LatLng(30.7551120000, 103.9784340000));
        latLngList.add(new LatLng(30.7535900000, 103.9770500000));
        latLngList.add(new LatLng(30.7502340000, 103.9797750000));
        latLngList.add(new LatLng(30.7456970000, 103.9759550000));
    }

    private void addPolyline() {
        //添加起点marker
        addStartEndMaker(0, true);
        //添加起点marker
        addStartEndMaker(latLngList.size() - 1, false);
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.addAll(latLngList);
        polylineOptions.width(10);
        polylineOptions.visible(true);  //线段可见性
        polylineOptions.setDottedLine(true);    //是否画虚线
        polylineOptions.useGradient(true);  //使用渐变色

        //添加线路
        polyline = aMap.addPolyline(polylineOptions);
    }

    private void addStartEndMaker(int index, boolean isStartMarker) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLngList.get(index));
        if (isStartMarker) {
            startMarker = aMap.addMarker(markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.vector_drawable_start_point)));
        } else {
            endMarker = aMap.addMarker(markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.vector_drawable_end_point_)));
        }
    }
}
