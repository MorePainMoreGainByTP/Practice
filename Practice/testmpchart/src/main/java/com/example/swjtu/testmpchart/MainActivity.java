package com.example.swjtu.testmpchart;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LineChart lineChart;
    //x坐标的名字
    private String[] xAxisName = new String[]{"一月", "二月", "三月", "四月", "五月", "六月",
            "七月", "八月", "九月", "十月", "十一月", "十二月"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("异常"+e.getMessage());
            }
        });

        intiViews();
        initData();
    }

    private void intiViews() {
        lineChart = (LineChart) findViewById(R.id.line_chart);
        IAxisValueFormatter formatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xAxisName[(int) value];
            }

        };
//        XAxis xAxis = chart.getXAxis();
//        xAxis.setPosition(XAxisPosition.BOTTOM);
//        xAxis.setTextSize(10f);
//        xAxis.setTextColor(Color.RED);
//        xAxis.setDrawAxisLine(true);
//        xAxis.setDrawGridLines(false);
//// set a custom value formatter
//        xAxis.setValueFormatter(new MyCustomFormatter());

        XAxis xAxis = lineChart.getXAxis(); //x坐标轴
        xAxis.setGranularity(1f);    //设置最小的间隔单位
        xAxis.setValueFormatter(formatter);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);  //设置x坐标的位置
        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return value+"ml";
            }
        });
//
//        // data has AxisDependency.LEFT
//        YAxis left = mChart.getAxisLeft();
//        left.setDrawLabels(false); // no axis labels
//        left.setDrawAxisLine(false); // no axis line
//        left.setDrawGridLines(false); // no grid lines
//        left.setDrawZeroLine(true); // draw a zero line
//        mChart.getAxisRight().setEnabled(false); // no right axis
        lineChart.getAxisRight().setEnabled(false);
    }

    private void initData() {

        List<Entry> entryList = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            entryList.add(new Entry(i, (float) Math.random() * 100));
        }
        //Collections.sort(entryList, new EntryXComparator());  x的坐标值必须是升序

        LineDataSet lineDataSet1 = new LineDataSet(entryList, "油耗");    //对折线的文字说明
        lineDataSet1.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet1.setColor(Color.GREEN);    //线段颜色
        lineDataSet1.setValueTextColor(Color.RED);   //顶点的颜色

        List<Entry> entryList1 = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            entryList1.add(new Entry(i, (float) Math.random() * 10)); //i+1是x坐标值
        }

        LineDataSet lineDataSet2 = new LineDataSet(entryList1, "排放");    //对折线的文字说明
        lineDataSet2.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet2.setColors(new int[]{R.color.springgreen,R.color.yellow,R.color.red,R.color.deepskyblue},this);

        //添加两条折线
        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet1);
        dataSets.add(lineDataSet2);

        LineData lineData = new LineData(dataSets);
        lineChart.setData(lineData);
        lineChart.invalidate();
    }

    public void goBarChart(View v) {
        startActivity(new Intent(MainActivity.this, BarChartActivity.class));
    }
}
