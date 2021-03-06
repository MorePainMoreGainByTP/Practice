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

public class ReplyMeRecyclerAdapter extends RecyclerView.Adapter<ReplyMeRecyclerAdapter.ViewHolder> {

    private List<DiscussTopic> replyTopicsList;
    private Context context;

    public ReplyMeRecyclerAdapter(List<DiscussTopic> replyTopics) {
        this.replyTopicsList = replyTopics;
    }

    @Override
    public ReplyMeRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null)
            context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item_reply_me, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    //每次item进入屏幕时 调用
    @Override
    public void onBindViewHolder(ReplyMeRecyclerAdapter.ViewHolder holder, final int position) {
        DiscussTopic replyTopics = replyTopicsList.get(position);
        //Glide很不错的图片加载库，自动完成图片压缩，可以从本地、网上、和资源id中加载图片，
        // 使用起来非常简单，只需要一句话,load里面可以是URI、资源ID，路径，into里面存放一个imageView实例
        //Glide.with(context).load(replyTopics.getImageId()).into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "查看详情", Toast.LENGTH_SHORT).show();
            }
        });
        holder.replyTopicsReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "回复", Toast.LENGTH_SHORT).show();
            }
        });
        holder.replyTopicsTime.setText(replyTopics.getDatetime());
        holder.replyTopicsTitle.setText(replyTopics.getTitle());
        holder.replyTopicsContent.setText(replyTopics.getContent());
        holder.replyFromWho.setText(replyTopics.getFromWho());
    }

    @Override
    public int getItemCount() {
        return replyTopicsList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView replyTopicsTime;
        TextView replyTopicsTitle;
        TextView replyTopicsContent;
        TextView replyTopicsReply;
        TextView replyFromWho;

        public ViewHolder(View itemView) {//itemView是 每项数据的根布局
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            replyTopicsTime = (TextView) itemView.findViewById(R.id.text_reply_time);
            replyTopicsTitle = (TextView) itemView.findViewById(R.id.text_reply_topic_title);
            replyTopicsContent = (TextView) itemView.findViewById(R.id.text_reply_content);
            replyTopicsReply = (TextView) itemView.findViewById(R.id.text_reply);
            replyFromWho = (TextView) itemView.findViewById(R.id.text_who_reply);
        }
    }
}
