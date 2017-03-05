package com.example.swjtu.recylerviewtest;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Fruit> fruitList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private DrawerLayout drawerLayout;
    private int[] imageId = {R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4, R.drawable.img5, R.drawable.img6, R.drawable.img7,
            R.drawable.img8, R.drawable.img9, R.drawable.img10, R.drawable.img11, R.drawable.img12, R.drawable.img13, R.drawable.img14, R.drawable.img15,
            R.drawable.img16, R.drawable.img17, R.drawable.img18, R.drawable.img19, R.drawable.img20, R.drawable.img21, R.drawable.img22, R.drawable.img23,
            R.drawable.img24, R.drawable.img25, R.drawable.img26};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);   //将toolbar代替actionbar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.menu_navi);
        }
        initViews();
        initData();

        //LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //水平方向滑动
        //layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        //以下实现瀑布布局,列数和方向
        //StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);

        //实现网格布局,默认是垂直方向，最后一个参数 决定是否反方向滑动
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        ImagesRecyclerAdapter recyclerAdapter = new ImagesRecyclerAdapter(fruitList);
        recyclerView.setAdapter(recyclerAdapter);
    }


    private void initData() {
        fruitList = new ArrayList<>();
        for (int i = 0; i < imageId.length; i++) {
            Fruit fruit = new Fruit(imageId[i], getRandomLengthName("img" + (i + 1)));
            fruitList.add(fruit);
        }
        //设置下拉旋转箭头的颜色
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruits();
            }
        });
    }

    private void refreshFruits() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                SystemClock.sleep(2000);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "完成刷新", Toast.LENGTH_SHORT).show();
                        //停止刷新
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }.start();
    }

    private String getRandomLengthName(String baseName) {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < random.nextInt(20); i++) {
            builder.append(baseName);
        }
        return builder.toString();
    }

    private void initViews() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navi_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Toast.makeText(MainActivity.this, "点击" + item.getTitle(), Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawers();
                return true;
            }
        });
        FloatingActionButton actionButton = (FloatingActionButton)findViewById(R.id.floatingBtn);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"更多内容",Snackbar.LENGTH_SHORT).setAction("恢复", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "更多功能正在开发", Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
