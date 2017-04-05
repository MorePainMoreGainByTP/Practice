package com.example.swjtu.recylerviewtest.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.swjtu.recylerviewtest.R;
import com.example.swjtu.recylerviewtest.searchCourse.OneCategoryCourseActivity;

/**
 * Created by tangpeng on 2017/3/5.
 */

public class CourseCategoryRecyclerAdapter extends RecyclerView.Adapter<CourseCategoryRecyclerAdapter.ViewHolder> {

    private Context context;

    private int[] imageID;
    private String[] categoryNames;

    public CourseCategoryRecyclerAdapter(int[] imageID, String[] categoryNames) {
        this.imageID = imageID;
        this.categoryNames = categoryNames;
    }

    @Override
    public CourseCategoryRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null)
            context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item_course_category, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    //每次item进入屏幕时 调用
    @Override
    public void onBindViewHolder(CourseCategoryRecyclerAdapter.ViewHolder holder, final int position) {
        //Glide很不错的图片加载库，自动完成图片压缩，可以从本地、网上、和资源id中加载图片，
        // 使用起来非常简单，只需要一句话,load里面可以是URI、资源ID，路径，into里面存放一个imageView实例
        Glide.with(context).load(imageID[position]).into(holder.imageView);
        holder.categoryName.setText(categoryNames[position]);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OneCategoryCourseActivity.class);
                String category = "";
                switch (position) {
                    case 0:
                        category = "计算机";
                        break;
                    case 1:
                        category = "经济管理";
                        break;
                    case 2:
                        category = "外语学习";
                        break;
                    case 3:
                        category = "文学历史";
                        break;
                    case 4:
                        category = "工学";
                        break;
                    case 5:
                        category = "理学";
                        break;
                    case 6:
                        category = "生命科学";
                        break;
                    case 7:
                        category = "全部课程";
                        break;
                }
                intent.putExtra("category", category);
                context.startActivity(intent);
                ((Activity) context).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return imageID.length;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        ImageView imageView;
        LinearLayout linearLayout;

        public ViewHolder(View itemView) {//itemView是 每项数据的根布局
            super(itemView);
            categoryName = (TextView) itemView.findViewById(R.id.categoryName);
            imageView = (ImageView) itemView.findViewById(R.id.imageview);
            linearLayout = (LinearLayout) itemView;
        }
    }
}
