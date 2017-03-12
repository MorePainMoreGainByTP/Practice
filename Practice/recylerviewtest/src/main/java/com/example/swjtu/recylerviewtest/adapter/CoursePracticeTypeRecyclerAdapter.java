package com.example.swjtu.recylerviewtest.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.swjtu.recylerviewtest.R;
import com.example.swjtu.recylerviewtest.entity.CoursePractice;
import com.example.swjtu.recylerviewtest.myCourse.coursePractice.PracticeProfileActivity;

import java.util.List;

/**
 * Created by tangpeng on 2017/3/5.
 */

public class CoursePracticeTypeRecyclerAdapter extends RecyclerView.Adapter<CoursePracticeTypeRecyclerAdapter.ViewHolder> {

    private List<CoursePractice> coursePractice;
    private Context context;

    public CoursePracticeTypeRecyclerAdapter(List<CoursePractice> coursePractice) {
        this.coursePractice = coursePractice;
    }

    @Override
    public CoursePracticeTypeRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null)
            context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item_practice_type, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    //每次item进入屏幕时 调用
    @Override
    public void onBindViewHolder(CoursePracticeTypeRecyclerAdapter.ViewHolder holder, int position) {
        final CoursePractice courseSection = coursePractice.get(position);
        //Glide很不错的图片加载库，自动完成图片压缩，可以从本地、网上、和资源id中加载图片，
        // 使用起来非常简单，只需要一句话,load里面可以是URI、资源ID，路径，into里面存放一个imageView实例
        Glide.with(context).load(R.mipmap.pen_green).into(holder.practiceImageView);
        holder.practiceImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "进入练习", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, PracticeProfileActivity.class);
                intent.putExtra("practiceTitle", courseSection.getType());
                intent.putExtra("practiceUnitName", courseSection.getName());
                intent.putExtra("practiceUnitProfile", courseSection.getPracticeProfile());
                intent.putExtra("practiceNum", courseSection.getQuestionNum());
                intent.putExtra("practiceTimeLimit", courseSection.getTimeLimit());
                intent.putExtra("practiceDeadLine", courseSection.getDeadLine());
                intent.putExtra("groupId", courseSection.getGroupId());
                context.startActivity(intent);
            }
        });
        holder.practiceType.setText(courseSection.getType());
        holder.practiceName.setText(courseSection.getName());
    }

    @Override
    public int getItemCount() {
        return coursePractice.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView practiceType;
        TextView practiceName;
        ImageView practiceImageView;

        public ViewHolder(View itemView) {//itemView是 每项数据的根布局
            super(itemView);
            practiceType = (TextView) itemView.findViewById(R.id.practice_type);
            practiceName = (TextView) itemView.findViewById(R.id.practice_name);
            practiceImageView = (ImageView) itemView.findViewById(R.id.join_practice);
        }
    }
}
