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
import android.view.KeyEvent;
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
import com.example.swjtu.recylerviewtest.entity.Teacher;
import com.example.swjtu.recylerviewtest.info.InfoActivity;
import com.example.swjtu.recylerviewtest.info.MyMessageActivity;
import com.example.swjtu.recylerviewtest.loginRegister.LoginActivity;
import com.example.swjtu.recylerviewtest.myCourse.MyCourseListActivity;
import com.example.swjtu.recylerviewtest.myDownload.MyDownloadActivity;
import com.example.swjtu.recylerviewtest.myTestGrade.CourseListActivity;
import com.example.swjtu.recylerviewtest.searchCourse.CourseCategoryActivity;
import com.example.swjtu.recylerviewtest.searchCourse.SearchCourseActivity;
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
import java.util.Random;

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
    private String[] courseName = {"大学英语综合课程", "英语教学与互联网", "老子的人生智慧", "H5+JS+CSS3", "PHP应用与开发", "概率论与数理统计",
            "大学物理", "教学研究", "机械原理", "物理化学", "C语言程序设计", "中国茶道", "计算机网络", "工程信号与系统", "结构力学", "药物分析", "药物化学",
            "针灸学", "中国饮食文化", "管理沟通", "财务报表编制", "博弈的思维看世界", "冷战史专题", "创业：道与术", "操作系统", "太极拳医学"};
    private String[] courseTeacher = {"徐帆", "晨阳", "赵丹", "李庆", "冯凯", "李莹迷", "张三宸", "刘一飞", "催帐秋", "王凤奎", "李丹崖", "钱钟伟", "邵佳一", "李志", "王强才"
            , "万秋波", "张嘉译", "王光兴", "李学鹏", "郜昊", "马雪东", "尚补偲", "朱元甫", "秦才", "赵文轩", "刘圣凯"};
    private String[] positions = {"教授", "副教授"};
    Random random = new Random();
    private ArrayList<String> courseProfile = new ArrayList<>();

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
                        startActivity(new Intent(MainActivity.this, MyDownloadActivity.class));
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
                    case R.id.myGrade:
                        startActivity(new Intent(MainActivity.this, CourseListActivity.class));
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
        collapsingToolbarLayout.setTitle("精品课程");
        courseList = new ArrayList<>();
        initCourseProfile();
        for (int i = 0; i < imageId.length; i++) {
            Course course = new Course(imageId[i], courseName[i], new Teacher(courseTeacher[i], positions[random.nextInt(2)]), "", courseProfile.get(i));
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

    private void initCourseProfile() {
        for (int i = 0; i < imageId.length / 5; i++) {
            courseProfile.add("《大学英语综合课程》是一门以培养学习者的语言基本技能、跨文化交际能力与批判性思维能力为总目标的综合性英语课程。旨在通过本课程学习，" +
                    "帮助学习者比较熟练地掌握听、说、读、写等语言基本技能，提升文化意识和跨文化交际能力，并增强独立思考、发现与解决问题等自主学习能力和思辨能力。");
            courseProfile.add("迅速吸收最新行业资讯，和来自全球的客户保持有效沟通和交流，是IT高端人才的职场护身绝技! IT行业职场英语课程聚焦你的沟通能力，" +
                    "阅读和写作能力，思维与决策力，助力你在人才济济的IT行业笑傲江湖！");
            courseProfile.add("大道至简，大象无形。南怀瑾先生曾有一个形象的比喻：儒家像粮店，佛家像百货店，而道家像药店。当我们面临着这么多的困扰、困惑的时候，" +
                    "老子和庄子的道家智慧，在很多方面能够为我们答疑解惑。本课程将引领各位探讨中国文化的根源之一，道家智慧。让我们一起来领略老子和庄子的人格魅力与精神境界！");
            courseProfile.add("本课程主要讲述计算机控制系统理论与工程设计的基础理论与方法，其中主要包括信号变换、系统建模与性能分析、数字控制器的模拟化设计方法、数字控制器的直接设计方法，基于状态空间模型的数字控制器极点配置设计方法，计算机控制系统仿真，以及计算机控制系统的工程化实现等技术。同时，课程设置了针对不同被控对象特性的多种实验，包括基础型实验和研究型实验，以加深对计算机控制系统基础理论和方法的理解。\n" +
                    "通过本课程的学习，将使学生掌握计算机控制系统设计的基本方法，培养学生应用所学过的控制理论基本知识分析和解决实际问题的能力，为进一步的学术研究和工程应用奠定基础。");
            courseProfile.add("本课程是理工科各专业的专业基础核心课程，是面向高校理工科专业的学生开设的一门计算机基础课程。通过本课程的学习，学生可以对计算机网络有一个基本的认识，了解当今计算机网络的现状和发展趋势，掌握计算机网络涉及的基本概念，掌握计算机网络应用基础知识，理解和掌握Internet的工作原理，熟练应用Internet提供的各种服务，从而掌握计算机网络的技术原理和综合应用。本课程培养学生的思维能力和实践动手能力，" +
                    "为学生学习后续课程以及解决生活、工作中遇到的相关问题提供技术和应用能力的支撑。");
        }
        courseProfile.add("本课程是理工科各专业的专业基础核心课程，是面向高校理工科专业的学生开设的一门计算机基础课程。通过本课程的学习，学生可以对计算机网络有一个基本的认识，了解当今计算机网络的现状和发展趋势，掌握计算机网络涉及的基本概念，掌握计算机网络应用基础知识，理解和掌握Internet的工作原理，熟练应用Internet提供的各种服务，从而掌握计算机网络的技术原理和综合应用。本课程培养学生的思维能力和实践动手能力，" +
                "为学生学习后续课程以及解决生活、工作中遇到的相关问题提供技术和应用能力的支撑。");
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
                startActivity(new Intent(MainActivity.this, CourseCategoryActivity.class));
                break;
            case R.id.courseSearch:
                startActivity(new Intent(MainActivity.this, SearchCourseActivity.class));
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

    private long lastTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            if (Math.abs(System.currentTimeMillis() - lastTime) < 2000) {
                finish();
            } else {
                lastTime = System.currentTimeMillis();
            }
        }
        return true;
    }
}
