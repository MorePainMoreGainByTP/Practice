package com.example.swjtu.recylerviewtest.info;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swjtu.recylerviewtest.R;
import com.example.swjtu.recylerviewtest.adapter.MyTopicRecyclerAdapter;
import com.example.swjtu.recylerviewtest.entity.DiscussTopic;

import java.util.ArrayList;

/**
 * Created by tangpeng on 2017/3/9.
 */

public class MyTopicFragment extends Fragment {

    private ArrayList<DiscussTopic> topicList;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private MyTopicRecyclerAdapter myTopicRecyclerAdapter;

    private static String KEY_MY_TOPIC = "my_topic";

    public static MyTopicFragment newInstance(ArrayList<DiscussTopic> infos) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_MY_TOPIC, infos);
        MyTopicFragment myTopicFragment = new MyTopicFragment();
        myTopicFragment.setArguments(bundle);
        return myTopicFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_topic, null);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        Bundle bundle = getArguments();
        topicList = (ArrayList<DiscussTopic>) bundle.getSerializable(KEY_MY_TOPIC);
        initData();
        setViews();

        return rootView;
    }

    private void initData() {
        myTopicRecyclerAdapter = new MyTopicRecyclerAdapter(topicList);
        recyclerView.setAdapter(myTopicRecyclerAdapter);
    }

    private void setViews() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.red);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
    }

    private void refreshData() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                SystemClock.sleep(2000);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //停止刷新
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }.start();
    }
}
