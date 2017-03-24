package com.example.swjtu.bluetoothclient;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

/**
 * Created by tangpeng on 2017/3/21.
 */

public class BlueToothDeviceAdapter extends RecyclerView.Adapter<BlueToothDeviceAdapter.ViewHolder> {

    private static final String TAG = "BlueToothDeviceAdapter";

    private Context mContext;
    private List<String> deviceName;
    private BluetoothAdapter bluetoothAdapter;

    private final UUID MY_UUID = UUID
            .fromString("abcd1234-ab12-ab12-ab12-abcdef123456");//随便定义一个
    private BluetoothSocket clientSocket;
    private BluetoothDevice bluetoothDevice;
    private OutputStream outputStream;
    private InputStream is;


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Toast.makeText(mContext, "连接断开", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(mContext, "连接正常", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(mContext, "读取数据异常", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public BlueToothDeviceAdapter(List<String> deviceName) {
        this.deviceName = deviceName;
    }

    public void setBluetoothAdapter(BluetoothAdapter bluetoothAdapter) {
        this.bluetoothAdapter = bluetoothAdapter;
    }

    @Override
    public BlueToothDeviceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_item_bluetooth_device, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String str = deviceName.get(position);
        final String address = str.substring(str.indexOf("=") + 1).trim();  //解析蓝牙地址
        holder.textView.setText(str);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //主动连接蓝牙服务端
                if (bluetoothAdapter.isDiscovering()) {
                    bluetoothAdapter.cancelDiscovery();
                }
                if (bluetoothDevice == null) {
                    //获得远程设备
                    bluetoothDevice = bluetoothAdapter.getRemoteDevice(address);
                }
                if (clientSocket == null) {//创建客户端Socket
                    try {
                        clientSocket = bluetoothDevice.createRfcommSocketToServiceRecord(MY_UUID);
                        //开始连接蓝牙，如果没有配对则弹出对话框提示我们进行配对
                        clientSocket.connect();
                        //获得输出流（客户端指向服务端输出文本）
                        outputStream = clientSocket.getOutputStream();
                        is = clientSocket.getInputStream();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e(TAG, "onClick: ", e);
                    }
                }
                if (outputStream != null) {
                    try {//往服务端写信息
                        outputStream.write("Hello BlueTooth!".getBytes("utf-8"));
                        if (is != null) {
                            int result = is.read();
                            Toast.makeText(mContext, "" + result, Toast.LENGTH_LONG).show();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    while (true) {
                                        SystemClock.sleep(2000);
                                        try {
                                            int result = is.read();
                                            if (result != 1) {
                                                handler.sendEmptyMessage(1);
                                            } else {
                                                handler.sendEmptyMessage(2);
                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                            Log.e(TAG, "run: is.read() ", e);
                                            handler.sendEmptyMessage(3);
                                        }
                                    }
                                }
                            }).start();
                        }
                        Log.i(TAG, "onClick: Hello BlueTooth");
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e(TAG, "onClick: ", e);
                    }
                }
                Log.i(TAG, "onClick: 4");
            }
        });
    }

    @Override
    public int getItemCount() {
        return deviceName.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.bluetoothName);
        }
    }

    public BluetoothSocket getClientSocket() {
        return clientSocket;
    }

    public BluetoothDevice getBluetoothDevice() {
        return bluetoothDevice;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public InputStream getIs() {
        return is;
    }

    public void setClientSocket(BluetoothSocket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void setBluetoothDevice(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void setIs(InputStream is) {
        this.is = is;
    }
}
