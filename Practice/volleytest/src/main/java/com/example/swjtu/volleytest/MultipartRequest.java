package com.example.swjtu.volleytest;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by tangpeng on 2017/3/2.
 */

public class MultipartRequest extends StringRequest {

    private MultipartEntity entity = new MultipartEntity();
    private Response.Listener<String> mListener;
    private List<File> fileList;    //要上传的文件可以是多个
    private String fileName;    //一个文件名key 可 对应多个文件value
    private Map<String,String> params;

    //上传单个文件
    public MultipartRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener,File file,String fileName,Map<String,String> params) {
        super(method, url, listener, errorListener);
        fileList = new ArrayList<>();
        if(file != null){
            fileList.add(file);
        }
        this.fileName = fileName;
        mListener = listener;
        this.params = params;
        buildMultipartEntity();
    }

    //上传多个文件
    public MultipartRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener,List<File> files,String fileName,Map<String,String> params) {
        super(url, listener, errorListener);
        fileList = files;
        this.fileName = fileName;
        mListener = listener;
        this.params = params;
        buildMultipartEntity();
    }

    private void buildMultipartEntity() {
        if(fileList != null && fileList.size() > 0){
            for(File file:fileList){
                entity.addPart(fileName,new FileBody(file));
            }
            long length = entity.getContentLength();
            System.out.println("文件："+fileList.size()+"个，大小："+length);
        }
        if(params != null && params.size() > 0){
            for(Map.Entry<String,String> entry:params.entrySet()){
                try {
                    entity.addPart(entry.getKey(),new StringBody(entry.getValue(), Charset.forName("utf-8")));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    System.out.println("StringBody异常"+e.getMessage());
                }
            }
        }
    }

    @Override
    public String getBodyContentType() {
        return entity.getContentType().getValue();
    }
}
