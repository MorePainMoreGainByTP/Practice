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
import com.example.swjtu.recylerviewtest.entity.MessageInfo;

import java.util.List;

/**
 * Created by tangpeng on 2017/3/5.
 */

public class InfoRecyclerAdapter extends RecyclerView.Adapter<InfoRecyclerAdapter.ViewHolder> {

    private List<MessageInfo> messageList;
    private Context context;

    public InfoRecyclerAdapter(List<MessageInfo> messageList) {
        this.messageList = messageList;
    }

    @Override
    public InfoRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null)
            context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item_info, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    //每次item进入屏幕时 调用
    @Override
    public void onBindViewHolder(InfoRecyclerAdapter.ViewHolder holder, final int position) {
        MessageInfo message = messageList.get(position);
        //Glide很不错的图片加载库，自动完成图片压缩，可以从本地、网上、和资源id中加载图片，
        // 使用起来非常简单，只需要一句话,load里面可以是URI、资源ID，路径，into里面存放一个imageView实例
        //Glide.with(context).load(message.getImageId()).into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "查看详情", Toast.LENGTH_SHORT).show();
            }
        });
        holder.messageTime.setText(message.getTime());
        holder.messageTitle.setText(message.getTitle());
        holder.messageContent.setText(message.getContent());
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView messageTime;
        TextView messageTitle;
        TextView messageContent;

        public ViewHolder(View itemView) {//itemView是 每项数据的根布局
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            messageTime = (TextView) itemView.findViewById(R.id.text_info_time);
            messageTitle = (TextView) itemView.findViewById(R.id.text_info_title);
            messageContent = (TextView) itemView.findViewById(R.id.text_info_content);
        }
    }
}
