package com.example.swjtu.secondcode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.swjtu.secondcode.immersiveMode.ImmersiveModeActivity;
import com.example.swjtu.secondcode.percentLayout.PercentFrameActivity;
import com.example.swjtu.secondcode.percentLayout.PercentRelativeActivity;
import com.example.swjtu.secondcode.phonePadCompatible.NewsActivity;
import com.example.swjtu.secondcode.singleInstance.AnotherTaskActivity;

/**
 *
 */
public class MainActivity extends BaseActivity {

    public static final String KEY_NAME = "key_name";
    private static final String TAG = "MainActivity";
    private NetWorkChangeReceiver netWorkChangeReceiver;

    private LocalBroadcastManager localBroadcastManager;    //本地广播管理器
    private BroadcastReceiver localBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "接收到本地广播", Toast.LENGTH_SHORT).show();
        }
    };   //本地广播接收器，只能接收本地广播。而且只能动态注册

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //如果activity已经创建且被销毁侯，再次创建时，savedInstanceState不为空
        if (savedInstanceState != null) {
            System.out.println(savedInstanceState.getString(KEY_NAME));
        }
        setContentView(R.layout.activity_main);
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                Log.e(TAG, "uncaughtException: ", e);
            }
        });
        sendBroadcast(new Intent("hello.world"));
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        Log.i(TAG, "onCreate: MainActivity Start");
    }

    //动态启动广播接收器，不用在Manifest中注册，当activity销毁时，需要解绑
    public void startDynamicBroadcast(View v) {
        //设置广播过滤条件
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        netWorkChangeReceiver = new NetWorkChangeReceiver();
        registerReceiver(netWorkChangeReceiver, intentFilter);
    }

    //发送本地广播，只有本地广播接收器才能接收到
    public void localBroadcast(View v) {
        //启动接收器
        IntentFilter intentFilter = new IntentFilter("com.example.swjtu.secondcode.LOCAL_BROADCAST");
        localBroadcastManager.registerReceiver(localBroadcastReceiver, intentFilter);  //只能动态注册
        Intent intent = new Intent("com.example.swjtu.secondcode.LOCAL_BROADCAST");
        localBroadcastManager.sendBroadcast(intent);
    }

    //有序广播
    public void orderBroadcast(View v) {
        Intent intent = new Intent("com.example.swjtu.secondcode.ORDER_BROADCAST");
        sendOrderedBroadcast(intent,null);  //发送全局广播，所有app都能收到
    }

    //一般的全局（整个手机）广播
    public void normalBraocast(View v) {
        Intent intent = new Intent("hello.world");
        sendBroadcast(intent);  //发送全局广播，所有app都能收到
    }


    //沉浸式体验
    public void immersive(View v) {
        startActivity(new Intent(this, ImmersiveModeActivity.class));
    }

    public void second(View v) {
        //显示启动需要参数的activity
        SecondActivity.actionStart(this, "lisi", 20);

        //隐式启动activity
//        Intent intent = new Intent("second");
//        intent.addCategory("hello");    //默认添加DEFAULT 的category，所有category必须匹配
//        startActivity(intent);
    }

    public void third(View v) {
        Intent intent = new Intent();
        intent.setAction("third");
        //所有category与data必须匹配
        intent.addCategory("zhnagsan");
        intent.setData(Uri.parse("http://www.tp.com"));   //只能接收Uri对象,表明Intent想要操作的数据，必须匹配标签data设置的多种属性（scheme、host、port、path、mimeType）
        startActivity(intent);
    }

    public void browser(View v) {
        //查看网址
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://www.baidu.com"));
        startActivity(intent);
    }

    public void dial(View v) {
        //拨打电话
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:10086"));
        startActivity(intent);
    }

    public void backPress(View v) {
        startActivityForResult(new Intent(this, BackPressActivity.class), 1);
    }

    //百分比布局，frame子类
    public void percentFrame(View v) {
        startActivity(new Intent(this, PercentFrameActivity.class));
    }

    //百分比布局relative子类
    public void percentRelative(View v) {
        startActivity(new Intent(this, PercentRelativeActivity.class));
    }

    public void goChat(View v) {
        startActivity(new Intent(this, ChatActivity.class));
    }

    public void phonePad(View v) {
        startActivity(new Intent(this, NewsActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "requestCode: " + requestCode);
        switch (requestCode) {
            case 1:
                Log.i(TAG, "resultCode: " + resultCode);
                if (resultCode == 0) {
                    Log.i(TAG, "data: " + data.getStringExtra("hello"));
                    Toast.makeText(this, data.getStringExtra("hello"), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void goAnother(View v) {
        startActivity(new Intent(this, AnotherTaskActivity.class));
    }

    //当activity不可见且因内存不足而被销毁时，调用此方法，可以将临时数据放到Bundle里面
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_NAME, "zhangsan");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: MainActivity");
        //动态注册，需要解绑
        if (netWorkChangeReceiver != null) {
            unregisterReceiver(netWorkChangeReceiver);
        }
        localBroadcastManager.unregisterReceiver(localBroadcastReceiver);
    }


    /**
     * 监听网络状态改变的接收器
     */
    class NetWorkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                Toast.makeText(context, "network is Available", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "network is unavailable", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
