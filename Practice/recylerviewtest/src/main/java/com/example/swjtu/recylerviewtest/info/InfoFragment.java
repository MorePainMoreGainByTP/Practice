package com.example.swjtu.recylerviewtest.info;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swjtu.recylerviewtest.R;
import com.example.swjtu.recylerviewtest.adapter.InfoRecyclerAdapter;
import com.example.swjtu.recylerviewtest.entity.MessageInfo;

import java.util.ArrayList;

/**
 * Created by tangpeng on 2017/3/7.
 */

public class InfoFragment extends android.support.v4.app.Fragment {

    private ArrayList<MessageInfo> messageList;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private InfoRecyclerAdapter infoRecyclerAdapter;

    public static String KEY_MESSAGE_INFO = "message_info";

    public static InfoFragment newInstance(ArrayList<MessageInfo> infos) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_MESSAGE_INFO, infos);
        InfoFragment infoFragment = new InfoFragment();
        infoFragment.setArguments(bundle);
        return infoFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_info, null);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        Bundle bundle = getArguments();
        messageList = (ArrayList<MessageInfo>) bundle.getSerializable(KEY_MESSAGE_INFO);
        initData();
        setViews();

        return rootView;
    }

    private void initData() {
        infoRecyclerAdapter = new InfoRecyclerAdapter(messageList);
        recyclerView.setAdapter(infoRecyclerAdapter);
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
                SystemClock.sleep(3000);
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
