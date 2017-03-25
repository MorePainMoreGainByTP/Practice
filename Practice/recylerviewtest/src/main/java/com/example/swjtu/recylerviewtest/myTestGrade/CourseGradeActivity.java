package com.example.swjtu.recylerviewtest.myTestGrade;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.swjtu.recylerviewtest.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangpeng on 2017/3/24.
 */

public class CourseGradeActivity extends AppCompatActivity {

    private BarChart barChart;
    private String[] xAxisName = new String[]{"阶段考试一","阶段考试二", "期中考试", "阶段考试三", "阶段考试四", "期末考试"};
    private PieChart pieChart;
    private Intent intent;

    private int highScore, lowScore, averageScore = 0;
    private int rightRate, errorRate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_grade);
        intent = getIntent();

        barChart = (BarChart) findViewById(R.id.gradeBaeChart);
        pieChart = (PieChart) findViewById(R.id.rightRatePie);
        setXAxis();
        initBarChart();
        initViews();
        initPieChart();
    }

    private void initViews() {
        String courseName = intent.getStringExtra("courseName");
        ((TextView) findViewById(R.id.courseName)).setText(courseName);
        ((TextView) findViewById(R.id.highestScore)).setText(highScore + "分");
        ((TextView) findViewById(R.id.lowestScore)).setText(lowScore + "分");
        ((TextView) findViewById(R.id.averageScore)).setText(averageScore + "分");
        rightRate = (int) ((Math.random() * 50) + 70);
        if (rightRate > 100) {
            rightRate = 100;
        }
        errorRate = 100 - rightRate;
        ((TextView) findViewById(R.id.rightRate)).setText(rightRate + "%");
    }

    private void setXAxis() {
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);  //设置x坐标的位置
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xAxisName[(int) value];
            }
        });
        barChart.getAxisLeft().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return (int) (value) + "";
            }
        });
        barChart.getAxisLeft().setAxisMinimum(0f); // start at zero
        barChart.getAxisRight().setEnabled(false);
    }

    private void initBarChart() {
        List<BarEntry> barEntries = new ArrayList<>();
        int score = 0;
        int sum = 0;
        for (int i = 0; i < xAxisName.length; i++) {
            score = (int) (Math.random() * 50 + 70);
            if (score > 100) {
                score = 100;
            }
            barEntries.add(new BarEntry(i, score));
            if (i == 0) {
                highScore = lowScore = score;
            } else {
                if (highScore < score)
                    highScore = score;
                if (lowScore > score)
                    lowScore = score;
            }
            sum += score;
        }
        averageScore = sum / xAxisName.length;
        BarDataSet dataSet = new BarDataSet(barEntries, "成绩");
        dataSet.setColors(new int[]{R.color.springgreen, R.color.yellow, R.color.red, R.color.deepskyblue}, this);
        BarData data = new BarData(dataSet);
        data.setBarWidth(0.9f); //由x坐标值已经决定了两个bar之间的间隙为1，所以这里设置bar的宽度为0.9，则bar之间的宽度就为0.1了
        barChart.clear();
        barChart.animateY(2000);    //设置动画效果
        barChart.setData(data);
        barChart.setFitBars(true);  //使所有的bar都能完整显示出来
        Description description = new Description();
        description.setText("");
        barChart.setDescription(description);
        barChart.invalidate();
    }

    private void initPieChart() {
        List<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(rightRate, "正确率"));
        pieEntries.add(new PieEntry(errorRate, "错误率"));
        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
        PieData pieData = new PieData(pieDataSet);
        Description description = new Description();
        description.setText("");
        pieChart.setDescription(description);
        pieChart.setUsePercentValues(true);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);
        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setRotationEnabled(true);
        pieChart.setRotationAngle(0);
        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        pieChart.setEntryLabelColor(Color.WHITE);
        pieChart.setEntryLabelTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf"));
        pieChart.setEntryLabelTextSize(12f);

        pieChart.setHighlightPerTapEnabled(true);
        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        pieDataSet.setColors(colors);
        pieDataSet.setSliceSpace(3f);
        pieDataSet.setSelectionShift(5f);

        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(11f);
        pieData.setValueTextColor(Color.WHITE);
        pieData.setValueTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf"));
        pieChart.setDrawCenterText(false);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    public void back(View v) {
        finish();
    }

}
