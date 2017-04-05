package com.example.swjtu.recylerviewtest.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.swjtu.recylerviewtest.R;
import com.example.swjtu.recylerviewtest.allCourseDetail.CourseDetailActivity;
import com.example.swjtu.recylerviewtest.entity.Course;

import java.util.List;
import java.util.Random;

/**
 * Created by tangpeng on 2017/3/5.
 */

public class CategoryCourseListRecyclerAdapter extends RecyclerView.Adapter<CategoryCourseListRecyclerAdapter.ViewHolder> {

    private List<Course> courseList;
    private Context context;

    private Random random = new Random();

    public CategoryCourseListRecyclerAdapter(List<Course> courseList) {
        this.courseList = courseList;
    }

    @Override
    public CategoryCourseListRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null)
            context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_category_course, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    //每次item进入屏幕时 调用
    @Override
    public void onBindViewHolder(CategoryCourseListRecyclerAdapter.ViewHolder holder, final int position) {
        final Course course = courseList.get(position);
        //Glide很不错的图片加载库，自动完成图片压缩，可以从本地、网上、和资源id中加载图片，
        // 使用起来非常简单，只需要一句话,load里面可以是URI、资源ID，路径，into里面存放一个imageView实例
        Glide.with(context).load(course.getImageId()).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CourseDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("course", course);
                bundle.putSerializable("teacher", course.getTeacher());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        holder.courseName.setText(course.getName());
        holder.courseTeacher.setText(course.getTeacher().getName());
        holder.onTime.setText("进行至第" + random.nextInt(10) + "周");
        holder.studentNum.setText("" + random.nextInt(20000));
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView courseName;
        TextView courseTeacher;
        TextView studentNum;    //课程学生数量
        TextView onTime;    //课程进行时间

        public ViewHolder(View itemView) {//itemView是 每项数据的根布局
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.coursePic);
            courseName = (TextView) itemView.findViewById(R.id.courseName);
            courseTeacher = (TextView) itemView.findViewById(R.id.courseTeacher);
            studentNum = (TextView) itemView.findViewById(R.id.studentNum);
            onTime = (TextView) itemView.findViewById(R.id.onTime);
        }
    }
}
