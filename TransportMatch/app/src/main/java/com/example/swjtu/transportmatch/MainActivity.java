package com.example.swjtu.transportmatch;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.swjtu.transportmatch.function.MoreFunctionActivity;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String URL = "";

    private CheckBox blueTeeth, WiFi;
    private EditText inputId;
    private SharedPreferences sharedPreferences;
    private Button btnTape;

    private ProgressDialog progressDialog;
    private WifiManager wifiManager;
    private BluetoothAdapter bluetoothAdapter;

    private RequestQueue requestQueue;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            dismissDialog();
            if (msg.what == 1) {//
                tapeSuccessfully();
            }
            if (msg.what == 2) {
                Toast.makeText(MainActivity.this, "没有该ID号，请检查后重试", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("tapedId", MODE_PRIVATE);
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        requestQueue = Volley.newRequestQueue(this);

        initViews();
        initData();

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("异常：" + e.getMessage());
            }
        });
    }

    private void initViews() {
        blueTeeth = (CheckBox) findViewById(R.id.switch_blue_teeth);
        WiFi = (CheckBox) findViewById(R.id.switch_wi_fi);
        inputId = (EditText) findViewById(R.id.input_id);
        btnTape = (Button) findViewById(R.id.btn_tape);

        if (!bluetoothAdapter.isEnabled()) {//未打开蓝牙
            blueTeeth.setChecked(false);
        } else {
            blueTeeth.setChecked(true);
        }
        if (wifiManager.isWifiEnabled()) {//如果wifi是开启的
            WiFi.setChecked(true);
        } else {
            WiFi.setChecked(false);
        }

        blueTeeth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {//打开蓝牙
                    if (bluetoothAdapter.enable()) {
                        Toast.makeText(MainActivity.this, "正在打开蓝牙", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "打开蓝牙失败", Toast.LENGTH_SHORT).show();
                    }
                } else {//关闭蓝牙
                    if (bluetoothAdapter.disable()) {
                        Toast.makeText(MainActivity.this, "正在关闭蓝牙", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "关闭蓝牙失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        WiFi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {//打开wi-fi
                    if (!wifiManager.isWifiEnabled()) {//没有打开
                        wifiManager.setWifiEnabled(true);
                        Toast.makeText(MainActivity.this, "正在打开Wi-Fi", Toast.LENGTH_SHORT).show();
                    }
                } else {//关闭wi-fi
                    if (wifiManager.isWifiEnabled()) {//没有打开
                        wifiManager.setWifiEnabled(false);
                        Toast.makeText(MainActivity.this, "正在关闭Wi-Fi", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void initData() {
        String savedId = sharedPreferences.getString("id", "");
        if (!savedId.equals("")) {//注册的id有记录
            inputId.setText(savedId);
            inputId.setFocusable(false);
            btnTape.setClickable(false);
            btnTape.setText("已绑定");
            findViewById(R.id.layout_more_function).setVisibility(View.VISIBLE);
        }
    }

    //绑定ID
    public void tape(View v) {
        String id = inputId.getText().toString().trim();
        if (id != null && id.length() == 6) {
            //验证ID是否存在
            idExists(id);
        } else {
            Toast.makeText(this, "请输入完整的ID", Toast.LENGTH_SHORT).show();
        }
    }

    //在服务器上验证是否有该ID
    private void idExists(final String id) {
        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                handler.sendEmptyMessage(1);
                System.out.println(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                handler.sendEmptyMessage(2);
                System.out.println("请求失败" + volleyError.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("id", id);
                return map;
            }
        };

        requestQueue.add(stringRequest);
    }

    //绑定成功
    private void tapeSuccessfully() {
        Toast.makeText(this, "绑定成功", Toast.LENGTH_SHORT).show();
        inputId.setFocusable(false);
        btnTape.setClickable(false);
        btnTape.setText("已绑定");
        findViewById(R.id.layout_more_function).setVisibility(View.VISIBLE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id", inputId.getText().toString().trim());
        editor.apply();
    }

    public void moreFunction(View v) {//更多功能
        startActivity(new Intent(MainActivity.this, MoreFunctionActivity.class));
    }

    private void showDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在绑定...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }

    private void dismissDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
}
