package com.example.swjtu.recylerviewtest.info;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.swjtu.recylerviewtest.R;
import com.example.swjtu.recylerviewtest.customView.ViewPagerIndicator;
import com.example.swjtu.recylerviewtest.entity.MessageInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static com.example.swjtu.recylerviewtest.Utils.getRandomDatetime;

/**
 * Created by tangpeng on 2017/3/7.
 */

public class InfoActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ViewPagerIndicator viewPagerIndicator;

    private List<String> tabTitle = Arrays.asList(new String[]{"课程通知", "系统通知"});
    private List<Fragment> fragments = new ArrayList<>();

    private ArrayList<MessageInfo> courseMessage, systemMessage; //分别是课程与系统通知里面的消息

    private String[] courseName = {"C语言程序设计", "操作系统", "计算机网络", "H5+JS+CSS3", "PHP教学", "财务报表编制", "博弈的思维看世界"};
    private String[] content = {"C语言程序设计C语言程序设计", "操作系统操作系统", "计算机网络计算机网络", "H5+JS+CSS3H5+JS+CSS3",
            "PHP教学PHP教学PHP教学", "财务报表编制财务报表编制财务报表编制", "博弈的思维看世界博弈的思维看世界博弈的思维看世界"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        initViews();
        initData();
        setViews();
    }

    private void initViews() {
        viewPager = (ViewPager) findViewById(R.id.id_viewpager);
        viewPagerIndicator = (ViewPagerIndicator) findViewById(R.id.id_viewpager_indicator);
    }

    private void initData() {
        //首先填充数据
        Date date = new Date();
        courseMessage = new ArrayList<>();
        systemMessage = new ArrayList<>();
        Random random = new Random();
        for (int i = 1; i < 15; i++) {
            MessageInfo messageInfo = new MessageInfo(getRandomDatetime(), courseName[i % courseName.length], content[i % content.length]);
            courseMessage.add(messageInfo);
            MessageInfo messageInfo2 = new MessageInfo(getRandomDatetime(), courseName[(i % courseName.length + random.nextInt(3)) % courseName.length], content[(i % content.length + random.nextInt(3)) % content.length]);
            systemMessage.add(messageInfo2);
        }
        fragments.add(InfoFragment.newInstance(courseMessage));
        fragments.add(InfoFragment.newInstance(systemMessage));

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
    }

    private void setViews() {
        viewPagerIndicator.setVisibleTanCount(2);
        viewPagerIndicator.setItemTitles(tabTitle);
        viewPagerIndicator.setRadioTriangleWidth(1 / 10f);
        viewPagerIndicator.setViewPager(viewPager, 0);
    }

    public void back(View v) {
        finish();
    }
}
