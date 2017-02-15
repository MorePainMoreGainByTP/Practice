package com.example.swjtu.volleytest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextView responseText;
    private ImageView responseImage;

    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //创建 网络请求队列
        requestQueue = Volley.newRequestQueue(this);

        responseText =(TextView)findViewById(R.id.requestText);
        responseImage =(ImageView) findViewById(R.id.requestImage);
    }


    //测试Volley的stringRequest,请求返回结果是String格式
    public void stringRequest(View v){
        StringRequest request = new StringRequest(Request.Method.POST,"http://www.baidu.com",new Response.Listener<String>(){
            //请求成功
            @Override
            public void onResponse(String s) {
                responseText.setText(s);
            }
        },new Response.ErrorListener(){
            //请求失败
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                responseText.setText(volleyError.getMessage());
            }
        });

        //启动请求
        requestQueue.add(request);
    }

    //测试Volley的jsonObjectRequest请求返回结果是JSON格式
    public void jsonObjectRequest(View v){
        String jsonStr = "{\"dri_phone\":\"1234567890\",\"carBrand\":\"大众\"}";
        String url = "http://192.168.1.108:8080/lcx/servlet/DriverRegister";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("字符串转json异常");
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Toast.makeText(MainActivity.this, "请求成功："+jsonObject.toString(), Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, "请求失败："+volleyError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    //测试Volley的jsonArrayRequest
    public void jsonArrayRequest(View v){

    }
}
