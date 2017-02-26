package com.example.swjtu.transportmatch.systemSetting;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.swjtu.transportmatch.MainActivity;
import com.example.swjtu.transportmatch.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tangpeng on 2017/2/26.
 */

public class ChangeIdActivity extends AppCompatActivity {

    private EditText inputId;
    private ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences;
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
                Toast.makeText(ChangeIdActivity.this, "没有该ID号，请检查后重试", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_id);
        sharedPreferences = getSharedPreferences("tapedId", MODE_PRIVATE);
        requestQueue = Volley.newRequestQueue(this);

        initViews();
    }

    private void initViews() {
        inputId = (EditText) findViewById(R.id.input_id);
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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, MainActivity.URL, new Response.Listener<String>() {
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
        Toast.makeText(this, "更改成功", Toast.LENGTH_SHORT).show();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id", inputId.getText().toString().trim());
        editor.apply();
    }

    private void showDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在更改...");
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

    public void back(View v) {
        finish();
    }
}
