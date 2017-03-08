package com.example.swjtu.recylerviewtest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.swjtu.recylerviewtest.R;
import com.example.swjtu.recylerviewtest.entity.CourseResource;

import java.util.List;

/**
 * Created by tangpeng on 2017/3/5.
 */

public class CourseResTypeRecyclerAdapter extends RecyclerView.Adapter<CourseResTypeRecyclerAdapter.ViewHolder> {

    private List<CourseResource> courseResources;
    private Context context;

    public CourseResTypeRecyclerAdapter(List<CourseResource> courseResources) {
        this.courseResources = courseResources;
    }

    @Override
    public CourseResTypeRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null)
            context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item_res_type, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    //每次item进入屏幕时 调用
    @Override
    public void onBindViewHolder(CourseResTypeRecyclerAdapter.ViewHolder holder, int position) {
        CourseResource courseSection = courseResources.get(position);
        //Glide很不错的图片加载库，自动完成图片压缩，可以从本地、网上、和资源id中加载图片，
        // 使用起来非常简单，只需要一句话,load里面可以是URI、资源ID，路径，into里面存放一个imageView实例
        Glide.with(context).load(R.mipmap.download_blue).into(holder.download);
        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "下载资源", Toast.LENGTH_SHORT).show();
            }
        });
        holder.resType.setText(courseSection.getType());
        holder.resName.setText(courseSection.getName());
    }

    @Override
    public int getItemCount() {
        return courseResources.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView resType;
        TextView resName;
        ImageView download;

        public ViewHolder(View itemView) {//itemView是 每项数据的根布局
            super(itemView);
            resType = (TextView) itemView.findViewById(R.id.res_type);
            resName = (TextView) itemView.findViewById(R.id.res_name);
            download = (ImageView) itemView.findViewById(R.id.imageview_download_res);
        }
    }
}
