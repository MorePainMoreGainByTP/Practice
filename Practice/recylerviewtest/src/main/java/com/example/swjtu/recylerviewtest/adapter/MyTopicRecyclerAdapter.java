package com.example.swjtu.recylerviewtest.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.swjtu.recylerviewtest.R;
import com.example.swjtu.recylerviewtest.entity.DiscussTopic;

import java.util.List;

/**
 * Created by tangpeng on 2017/3/5.
 */

public class MyTopicRecyclerAdapter extends RecyclerView.Adapter<MyTopicRecyclerAdapter.ViewHolder> {

    private List<DiscussTopic> discussTopicsList;
    private Context context;

    public MyTopicRecyclerAdapter(List<DiscussTopic> discussTopics) {
        this.discussTopicsList = discussTopics;
    }

    @Override
    public MyTopicRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null)
            context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item_my_topic, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    //每次item进入屏幕时 调用
    @Override
    public void onBindViewHolder(MyTopicRecyclerAdapter.ViewHolder holder, final int position) {
        DiscussTopic discussTopics = discussTopicsList.get(position);
        //Glide很不错的图片加载库，自动完成图片压缩，可以从本地、网上、和资源id中加载图片，
        // 使用起来非常简单，只需要一句话,load里面可以是URI、资源ID，路径，into里面存放一个imageView实例
        //Glide.with(context).load(discussTopics.getImageId()).into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "查看详情", Toast.LENGTH_SHORT).show();
            }
        });
        holder.discussTopicsDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "删除话题", Toast.LENGTH_SHORT).show();
            }
        });
        holder.discussTopicsTime.setText(discussTopics.getDatetime());
        holder.discussTopicsTitle.setText(discussTopics.getTitle());
        holder.discussTopicsContent.setText(discussTopics.getContent());
    }

    @Override
    public int getItemCount() {
        return discussTopicsList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView discussTopicsTime;
        TextView discussTopicsTitle;
        TextView discussTopicsContent;
        TextView discussTopicsDelete;

        public ViewHolder(View itemView) {//itemView是 每项数据的根布局
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            discussTopicsTime = (TextView) itemView.findViewById(R.id.text_my_topic_time);
            discussTopicsTitle = (TextView) itemView.findViewById(R.id.text_my_topic_title);
            discussTopicsContent = (TextView) itemView.findViewById(R.id.text_my_topic_content);
            discussTopicsDelete = (TextView) itemView.findViewById(R.id.text_my_topic_delete);
        }
    }
}
