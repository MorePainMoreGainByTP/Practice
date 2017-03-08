package com.example.swjtu.recylerviewtest.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.swjtu.recylerviewtest.R;
import com.example.swjtu.recylerviewtest.entity.DiscussTopic;

import java.util.List;

/**
 * Created by tangpeng on 2017/3/5.
 */

public class CourseDiscussRecyclerAdapter extends RecyclerView.Adapter<CourseDiscussRecyclerAdapter.ViewHolder> {

    private List<DiscussTopic> discussTopics;
    private Context context;

    public CourseDiscussRecyclerAdapter(List<DiscussTopic> discussTopics) {
        this.discussTopics = discussTopics;
    }

    @Override
    public CourseDiscussRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null)
            context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item_discuss, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    //每次item进入屏幕时 调用
    @Override
    public void onBindViewHolder(CourseDiscussRecyclerAdapter.ViewHolder holder, final int position) {
        DiscussTopic discussTopic = discussTopics.get(position);
        //Glide很不错的图片加载库，自动完成图片压缩，可以从本地、网上、和资源id中加载图片，
        // 使用起来非常简单，只需要一句话,load里面可以是URI、资源ID，路径，into里面存放一个imageView实例
        Glide.with(context).load(R.mipmap.hand_grey).into(holder.support);
        Glide.with(context).load(R.mipmap.edit_comment).into(holder.editComment);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "查看详情", Toast.LENGTH_SHORT).show();
            }
        });
        holder.discussTitle.setText(discussTopic.getTitle());
        holder.discussContent.setText(discussTopic.getContent());
        holder.discussDatetime.setText(discussTopic.getDatetime());
        holder.discussFromWho.setText("来自 -- " + discussTopic.getFromWho());
    }

    @Override
    public int getItemCount() {
        return discussTopics.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView discussTitle;
        TextView discussContent;
        TextView discussDatetime;
        TextView discussFromWho;
        ImageView support, editComment;

        public ViewHolder(View itemView) {//itemView是 每项数据的根布局
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            discussTitle = (TextView) itemView.findViewById(R.id.questionTitle);
            discussContent = (TextView) itemView.findViewById(R.id.questionContent);
            discussDatetime = (TextView) itemView.findViewById(R.id.questionDatetime);
            discussFromWho = (TextView) itemView.findViewById(R.id.questionFromWho);
            support = (ImageView) itemView.findViewById(R.id.supportHand);
            editComment = (ImageView) itemView.findViewById(R.id.editComment);
        }
    }
}
