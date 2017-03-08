package com.example.swjtu.recylerviewtest.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.swjtu.recylerviewtest.R;
import com.example.swjtu.recylerviewtest.entity.CoursePractice;
import com.example.swjtu.recylerviewtest.entity.CoursePracticeSection;

import java.util.List;

/**
 * Created by tangpeng on 2017/3/5.
 */

public class CoursePracticeSectionRecyclerAdapter extends RecyclerView.Adapter<CoursePracticeSectionRecyclerAdapter.ViewHolder> {

    private List<CoursePracticeSection> courseSections;
    private Context context;

    public CoursePracticeSectionRecyclerAdapter(List<CoursePracticeSection> courseSections) {
        this.courseSections = courseSections;
    }

    @Override
    public CoursePracticeSectionRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null)
            context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item_course_section, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    //每次item进入屏幕时 调用
    @Override
    public void onBindViewHolder(CoursePracticeSectionRecyclerAdapter.ViewHolder holder, int position) {
        CoursePracticeSection courseSection = courseSections.get(position);
        List<CoursePractice> courseResources = courseSection.getPracticeArrayList();
        //Glide很不错的图片加载库，自动完成图片压缩，可以从本地、网上、和资源id中加载图片，
        // 使用起来非常简单，只需要一句话,load里面可以是URI、资源ID，路径，into里面存放一个imageView实例
        //Glide.with(context).load(course.getImageId()).into(holder.imageView);
        holder.sectionName.setText(courseSection.getSectionName());
        holder.resList.setLayoutManager(new GridLayoutManager(context, 1));
        holder.resList.setAdapter(new CoursePracticeTypeRecyclerAdapter(courseResources));
    }

    @Override
    public int getItemCount() {
        return courseSections.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView sectionName;
        RecyclerView resList;

        public ViewHolder(View itemView) {//itemView是 每项数据的根布局
            super(itemView);
            sectionName = (TextView) itemView.findViewById(R.id.section_name);
            resList = (RecyclerView) itemView.findViewById(R.id.recyclerView_res_type);
        }
    }
}
