package com.example.swjtu.recylerviewtest.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.swjtu.recylerviewtest.R;
import com.example.swjtu.recylerviewtest.entity.CourseResource;

import java.util.List;

/**
 * Created by tangpeng on 2017/3/5.
 */

public class DownloadCourseResRecyclerAdapter extends RecyclerView.Adapter<DownloadCourseResRecyclerAdapter.ViewHolder> {

    private List<CourseResource> courseResources;
    private Context context;

    public DownloadCourseResRecyclerAdapter(List<CourseResource> courseResources) {
        this.courseResources = courseResources;
    }

    @Override
    public DownloadCourseResRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null)
            context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item_download_res, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    //每次item进入屏幕时 调用
    @Override
    public void onBindViewHolder(final DownloadCourseResRecyclerAdapter.ViewHolder holder, int position) {
        CourseResource courseSection = courseResources.get(position);
        //Glide很不错的图片加载库，自动完成图片压缩，可以从本地、网上、和资源id中加载图片，
        // 使用起来非常简单，只需要一句话,load里面可以是URI、资源ID，路径，into里面存放一个imageView实例
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.resType.getText().toString().equals("视频")) {
                    Toast.makeText(context, "观看视频", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "下载文件？", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.resType.setText(courseSection.getType());
        holder.resName.setText(courseSection.getName());
        holder.deleteRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context).setMessage("删除该文件？").setNegativeButton("取消", null).setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return courseResources.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        TextView resType;
        TextView resName;
        TextView deleteRes;

        public ViewHolder(View itemView) {//itemView是 每项数据的根布局
            super(itemView);
            linearLayout = (LinearLayout) itemView;
            resType = (TextView) itemView.findViewById(R.id.res_type);
            resName = (TextView) itemView.findViewById(R.id.res_name);
            deleteRes = (TextView) itemView.findViewById(R.id.deleteRes);
        }
    }
}
