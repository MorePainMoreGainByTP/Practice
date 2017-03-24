package com.example.swjtu.bluetoothclient;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private static final int REQUEST_BLUETOOTH_PERMISSION = 10;

    private BluetoothAdapter bluetoothAdapter;

    private TextView bondedDevice;
    private RecyclerView recyclerView;
    private List<String> devices = new ArrayList<>();
    private BlueToothDeviceAdapter blueToothDeviceAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBlueToothAdapter();
        initViews();
        registerBroadCastReceiver();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                Log.e(TAG, "uncaughtException: " + e.getMessage(), e);
            }
        });
    }

    private void initViews() {
        bondedDevice = (TextView) findViewById(R.id.bondedDevice);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        blueToothDeviceAdapter = new BlueToothDeviceAdapter(devices);
        blueToothDeviceAdapter.setBluetoothAdapter(bluetoothAdapter);
        recyclerView.setAdapter(blueToothDeviceAdapter);
    }

    private void initBlueToothAdapter() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    private void openBlueTooth() {
        if (bluetoothAdapter != null) {
            bluetoothAdapter.enable();
        }
    }

    private void closeBlueTooth() {
        if (bluetoothAdapter != null) {
            bluetoothAdapter.disable();
        }
    }

    public void buttonOpenBlueTooth(View v) {
        Button btn = (Button) v;
        if (btn.getText().equals("打开蓝牙")) {
            openBlueTooth();
            btn.setText("关闭蓝牙");
        } else if (btn.getText().equals("关闭蓝牙")) {
            btn.setText("打开蓝牙");
            closeBlueTooth();
        }
    }

    //搜索附近设备并显示
    public void buttonShowBlueTooth(View v) {
        if (bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
        }
        bluetoothAdapter.startDiscovery();

        Set<BluetoothDevice> bluetoothDevices = bluetoothAdapter.getBondedDevices();
        if (bluetoothDevices.size() > 0) {
            for (BluetoothDevice device : bluetoothDevices) {
                bondedDevice.append(device.getName() + "=" + device.getAddress() + "\n");
            }
        }

    }

    // 注册广播接收器
    private void registerBroadCastReceiver() {
        //设置广播过滤
        IntentFilter intentFilter = new IntentFilter();
        //搜索到一个设备发送一次广播
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        //搜索完毕后发送广播
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        intentFilter.setPriority(Integer.MAX_VALUE);
        this.registerReceiver(bluetoothReceiver, intentFilter);
    }

    private BroadcastReceiver bluetoothReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                devices.add(device.getName() + "=" + device.getAddress());
                blueToothDeviceAdapter.notifyDataSetChanged();
                Toast.makeText(context, "发现设备" + device.getName(), Toast.LENGTH_SHORT).show();
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                Toast.makeText(context, "搜索完毕！", Toast.LENGTH_SHORT).show();
            } else if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {//连接
                Toast.makeText(context, "ACTION_ACL_CONNECTED", Toast.LENGTH_SHORT).show();
            } else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {//断开连接
                Toast.makeText(context, "ACTION_ACL_DISCONNECTED", Toast.LENGTH_SHORT).show();
                blueToothDeviceAdapter.setBluetoothDevice(null);
                blueToothDeviceAdapter.setClientSocket(null);
                blueToothDeviceAdapter.setIs(null);
                blueToothDeviceAdapter.setOutputStream(null);
            }
        }
    };

    //由于蓝牙所需要的权限包含Dangerous Permissions，所以需要在Java代码中进行动态授权处理：
    private void requestBlueToothPermission() {
        //判断系统版本
        if (Build.VERSION.SDK_INT >= 23) {
            //检测当前app是否拥有某个权限
            int checkPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
            //该权限是否已经授权
            if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                //是否需要向用户解释，为什么要申请权限
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    Toast.makeText(this, "需要蓝牙管理权限", Toast.LENGTH_SHORT).show();
                }
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_BLUETOOTH_PERMISSION);
                return;
            }
        }
    }
}
