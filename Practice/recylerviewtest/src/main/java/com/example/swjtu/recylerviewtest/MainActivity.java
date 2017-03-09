package com.example.swjtu.recylerviewtest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.example.swjtu.recylerviewtest.adapter.CourseRecyclerAdapter;
import com.example.swjtu.recylerviewtest.entity.Course;
import com.example.swjtu.recylerviewtest.info.InfoActivity;
import com.example.swjtu.recylerviewtest.info.MyMessageActivity;
import com.example.swjtu.recylerviewtest.loginRegister.LoginActivity;
import com.example.swjtu.recylerviewtest.myCourse.MyCourseListActivity;
import com.example.swjtu.recylerviewtest.setting.SettingActivity;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import toan.android.floatingactionmenu.FloatingActionButton;
import toan.android.floatingactionmenu.FloatingActionsMenu;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, OnItemClickListener, View.OnClickListener {
    private static final String TAG = "MainActivity";
    public static boolean hasLogin = false;

    private ConvenientBanner convenientBanner;

    private ArrayList<Integer> localImages = new ArrayList<>(); //存放本地图片的ID
    private List<String> networkImages; //存放网络上的图片URL
    private String[] imagesFromNetwork = {"http://nos.netease.com/edu-image/29b1be07-19a4-48b4-8adf-8bd5f801c648.jpg?imageView&quality=100",
            "http://nos.netease.com/edu-image/bc4f3bac-f559-4484-b29a-c007a4a3c188.jpg?imageView&quality=100",
            "http://nos.netease.com/edu-image/d6c4a5e0-c4f7-49df-96a0-083263e6ccb6.jpg?imageView&quality=100",
            "http://nos.netease.com/edu-image/d74ce609-c9b9-4ed0-868a-8762736f2d45.jpg?imageView&quality=100",
            "http://nos.netease.com/edu-image/6be9e0ca-3fb5-4d23-aa44-f93bb1d43bd4.jpg?imageView&quality=100",
            "http://edu-image.nosdn.127.net/4853D2270567FD7F7ED74F51A40DD11D.png",
            "http://nos.netease.com/edu-image/3fadad76-e56a-4f09-8757-ab923d504a19.jpg?imageView&quality=100"
    };

    private DrawerLayout drawerLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private FloatingActionButton categoryCourse, searchCourse;
    private FloatingActionsMenu actionsMenu;
    private List<Course> courseList;
    private int[] imageId = {R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4, R.drawable.img5, R.drawable.img6, R.drawable.img7,
            R.drawable.img8, R.drawable.img9, R.drawable.img10, R.drawable.img11, R.drawable.img12, R.drawable.img13, R.drawable.img14, R.drawable.img15,
            R.drawable.img16, R.drawable.img17, R.drawable.img18, R.drawable.img19, R.drawable.img20, R.drawable.img21, R.drawable.img22, R.drawable.img23,
            R.drawable.img24, R.drawable.img25, R.drawable.img26};
    private String[] courseName = {"C语言程序设计", "操作系统", "计算机网络", "H5+JS+CSS3", "PHP教学", "财务报表编制", "博弈的思维看世界", "创业：道与术", "管理沟通"
            , "大学英语综合课程", "英语教学与互联网", "IT行业职场英语", "中国茶道", "中国饮食文化", "海洋与人类文明的产生", "航空燃气涡轮发动机", "材料科学基础"
            , "浅论电子学", "结构力学", "物理化学", "概率论与数理统计", "大学物理", "宇宙探索与发现", "药物分析", "药物化学", "太极拳医学"};
    private String[] courseTeacher = {"徐帆", "晨阳", "赵丹", "李庆", "冯凯", "李莹迷", "张三宸", "刘一飞", "催帐秋", "王凤奎", "李丹崖", "钱钟伟", "邵佳一", "李志", "王强才"
            , "万秋波", "张嘉译", "王光兴", "李学鹏", "郜昊", "马雪东", "尚补偲", "朱元甫", "秦才", "赵文轩", "刘圣凯"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                Log.e(TAG, "uncaughtException: ", e);
            }
        });
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
        CourseRecyclerAdapter recyclerAdapter = new CourseRecyclerAdapter(courseList);
        recyclerView.setAdapter(recyclerAdapter);
    }

    private void initViews() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navi_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.my_study:
                        startActivity(new Intent(MainActivity.this, MyCourseListActivity.class));
                        break;
                    case R.id.my_download:
                        break;
                    case R.id.message_center:
                        startActivity(new Intent(MainActivity.this, InfoActivity.class));
                        break;
                    case R.id.notepad:
                        break;
                    case R.id.setting:
                        startActivity(new Intent(MainActivity.this, SettingActivity.class));
                        break;
                    case R.id.my_message:
                        startActivity(new Intent(MainActivity.this, MyMessageActivity.class));
                        break;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
        convenientBanner = (ConvenientBanner) findViewById(R.id.convenientBanner);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolBar);
        categoryCourse = (FloatingActionButton) findViewById(R.id.courseCategory);
        searchCourse = (FloatingActionButton) findViewById(R.id.courseSearch);
        actionsMenu = (FloatingActionsMenu) findViewById(R.id.addFloatingMenu);

        searchCourse.setOnClickListener(this);
        categoryCourse.setOnClickListener(this);

    }

    //点击头像
    public void clickHeader(View v) {
        if (hasLogin) {//更换头像

        } else {//登录
            login(v);
        }
    }

    //登录
    public void login(View v) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

    private void initData() {
        courseList = new ArrayList<>();
        for (int i = 0; i < imageId.length; i++) {
            Course course = new Course(imageId[i], courseName[i], courseTeacher[i]);
            courseList.add(course);
        }
        //设置下拉旋转箭头的颜色
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshCourses();
            }
        });

        initImageLoader();
        loadTestDatas();

        /*
        convenientBanner.setPages(new CBViewHolderCreator<LocalImageHolderView>() {
            @Override
            public LocalImageHolderView createHolder() {
                return new LocalImageHolderView();
            }
        }, localImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused})
                //设置指示器的方向
//                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
//                .setOnPageChangeListener(this)//监听翻页事件
                .setOnItemClickListener(this);
    }*/

        //convenientBanner.setManualPageable(false);//设置不能手动影响
        // 网络加载例子
        networkImages = Arrays.asList(imagesFromNetwork);
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, networkImages).setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused}).setOnItemClickListener(this);
    }

    /*
    手动New并且添加到ListView Header的例子
    ConvenientBanner mConvenientBanner = new ConvenientBanner(this,false);
    mConvenientBanner.setMinimumHeight(500);
    mConvenientBanner.setPages(
            new CBViewHolderCreator<LocalImageHolderView>() {
                @Override
                public LocalImageHolderView createHolder() {
                    return new LocalImageHolderView();
                }
            }, localImages)
            //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
            .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                    //设置指示器的方向
            .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
            .setOnItemClickListener(this);
    listView.addHeaderView(mConvenientBanner);
*/
    //初始化网络图片缓存库
    private void initImageLoader() {
        //网络图片例子,结合常用的图片缓存库UIL,你可以根据自己需求自己换其他网络图片库
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().
                showImageForEmptyUri(R.drawable.ic_default_adimage)
                .cacheInMemory(true).cacheOnDisk(true).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext()).defaultDisplayImageOptions(defaultOptions)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }

    /*
    加入测试Views
    * */
    private void loadTestDatas() {
        //本地图片集合
        for (int position = 0; position < 7; position++)
            localImages.add(getResId("ic_test_" + position, R.drawable.class));
    }

    /**
     * 通过文件名获取资源id 例子：getResId("icon", R.drawable.class);
     *
     * @param variableName
     * @param c
     * @return
     */
    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    private void refreshCourses() {
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Toast.makeText(this, "监听到翻到第" + position + "了", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    //广告栏被点击
    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, "点击了第" + position + "个", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //开始自动翻页
        convenientBanner.startTurning(5000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        actionsMenu.collapse();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //停止翻页
        convenientBanner.stopTurning();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.courseCategory:
                actionsMenu.collapse();
                break;
            case R.id.courseSearch:
                actionsMenu.collapse();
                break;
        }
    }


    class LocalImageHolderView implements Holder<Integer> {

        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, Integer data) {
            imageView.setImageResource(data);
        }
    }

    class NetworkImageHolderView implements Holder<String> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            imageView.setImageResource(R.drawable.ic_default_adimage);
            ImageLoader.getInstance().displayImage(data, imageView);
        }
    }
}
