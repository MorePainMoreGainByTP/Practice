package com.example.swjtu.recylerviewtest.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.swjtu.recylerviewtest.R;
import com.example.swjtu.recylerviewtest.entity.Course;
import com.example.swjtu.recylerviewtest.myErrorQuestion.ErrorQuesActivity;

import java.util.List;

/**
 * Created by tangpeng on 2017/3/5.
 */

public class ErrorQuesCourseRecyclerAdapter extends RecyclerView.Adapter<ErrorQuesCourseRecyclerAdapter.ViewHolder> {

    private List<Course> courseList;
    private Context context;

    public ErrorQuesCourseRecyclerAdapter(List<Course> courseList) {
        this.courseList = courseList;
    }

    @Override
    public ErrorQuesCourseRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null)
            context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item_my_course, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    //每次item进入屏幕时 调用
    @Override
    public void onBindViewHolder(ErrorQuesCourseRecyclerAdapter.ViewHolder holder, int position) {
        final Course course = courseList.get(position);
        //Glide很不错的图片加载库，自动完成图片压缩，可以从本地、网上、和资源id中加载图片，
        // 使用起来非常简单，只需要一句话,load里面可以是URI、资源ID，路径，into里面存放一个imageView实例
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ErrorQuesActivity.class));
            }
        });
        Glide.with(context).load(course.getImageId()).into(holder.imageView);
        holder.courseName.setText(course.getName());
        holder.courseTeacher.setText(course.getTeacher().getName());
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imageView;
        TextView courseName;
        TextView courseTeacher;

        public ViewHolder(View itemView) {//itemView是 每项数据的根布局
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.course_image);
            courseName = (TextView) itemView.findViewById(R.id.course_name);
            courseTeacher = (TextView) itemView.findViewById(R.id.course_teacher);
            cardView = (CardView) itemView;
        }
    }
}
