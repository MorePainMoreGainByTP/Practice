package com.example.swjtu.imagecroptest;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    public static final int FROM_CAMERA = 1;    //来自相机
    public static final int FROM_LIBRARY = 2;    //来自相册
    public static final int FROM_CROP = 3;  //裁剪
    private static final int CROP_PICTURE = 4;

    private int cropWidth = 500;    //截取框的宽度
    private int cropHeight = 500;   //截取框的高度

    String fileNameCamera = "image_header_camera.png";   // 拍照得到的临时文件名
    String fileNameCrop = "image_header_crop.png";   //裁剪后得到的临时文件名
    private Uri finalUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), fileNameCrop)); //最终截取到的图片的uri

    private CircleImageView circleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        circleImageView = (CircleImageView) findViewById(R.id.image_header);
    }

    //选择头像
    public void chooseImage(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(new String[]{"拍照", "相册"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        if (hasSdCard()) {//是否有存储卡
                            Uri imgUri = null;
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);    //调用相机
                            imgUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), fileNameCamera));
                            //指定照片保存路径，image_header_camera.png是一个临时文件,数据最终保存到imgUri中
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
                            startActivityForResult(intent, FROM_CROP);
                        } else {
                            Toast.makeText(MainActivity.this, "没有存储卡！", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 1://相册选取
                        Intent libraryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        libraryIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(libraryIntent, FROM_CROP);
                }
            }
        });
        builder.setNegativeButton("取消", null);
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case FROM_CROP:
                    Uri uri = null;
                    if (data != null) {//得到数据的uri(相册)
                        uri = data.getData();
                    } else {//得到临时保存的文件(照相)的uri
                        uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), fileNameCamera));
                    }
                    //开始截图
                    cropImg(uri, cropWidth, cropHeight, CROP_PICTURE);
                    break;
                case CROP_PICTURE://系统截图返回
                    Bitmap photo = null;
                    photo = decodeUriAsBitmap(finalUri);    //通过 之前生成的文件的uri来获得返回的照片，而不是data对象
                    Log.i(TAG, "onActivityResult: " + finalUri);
                    /*  以下方法通过系统截取图片时会崩溃
                    //得到返回的数据Uri
                    Uri photoUri = data.getData();
                    if (photoUri != null) {//相册
                        photo = BitmapFactory.decodeFile(photoUri.getPath());
                    }
                    if (photo == null) {
                        Bundle extra = data.getExtras();
                        if (extra != null) {
                            photo = (Bitmap) extra.get("data");
                            //进行图片的压缩截取
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        }
                    }*/
                    //显示截图结果
                    circleImageView.setImageBitmap(photo);
                    break;
            }
        }
    }


    //调用系统截图
    private void cropImg(Uri uri, int i, int i1, int cropPicture) {
        //系统自带截图
        Intent intent = new Intent("com.android.camera.action.CROP");
        //设置截取的图片信息
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        //裁剪框的比例 1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", cropWidth);
        intent.putExtra("outputY", cropHeight);
        //图片格式
        intent.putExtra("outputFormat", "png");
        //取消人脸识别
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);  //如果为true，图片会包含在data中返回，当数据过大时（一般是1M）就会崩溃，
        // 所以这里不通过data返回，而是通过下面的uri来获取该照片
        intent.putExtra(MediaStore.EXTRA_OUTPUT, finalUri);

        startActivityForResult(intent, CROP_PICTURE);
    }

    private Bitmap decodeUriAsBitmap(Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
            //进行图片的压缩截取
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG, "decodeUriAsBitmap: FileNotFoundException ", e);
            return null;
        }
        return bitmap;
    }


    private boolean hasSdCard() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }
}
