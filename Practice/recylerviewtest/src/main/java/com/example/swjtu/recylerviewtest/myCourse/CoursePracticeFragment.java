package com.example.swjtu.recylerviewtest.myCourse;

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
import com.example.swjtu.recylerviewtest.adapter.CoursePracticeSectionRecyclerAdapter;
import com.example.swjtu.recylerviewtest.entity.CoursePracticeSection;

import java.util.ArrayList;

/**
 * Created by tangpeng on 2017/3/8.
 */

public class CoursePracticeFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    private ArrayList<CoursePracticeSection> courseSectionArrayList;
    public static final String COURSE_SECTION = "courseSection";

    public static CoursePracticeFragment newInstance(ArrayList<CoursePracticeSection> list) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(COURSE_SECTION, list);
        CoursePracticeFragment fragment = new CoursePracticeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_course_resource, null);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewResource);
        setViews();
        initData();
        return rootView;
    }

    private void setViews() {
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorIconBlue);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshCourses();
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
    }

    private void initData() {
        courseSectionArrayList = (ArrayList<CoursePracticeSection>) getArguments().getSerializable(COURSE_SECTION);
        recyclerView.setAdapter(new CoursePracticeSectionRecyclerAdapter(courseSectionArrayList));
    }

    private void refreshCourses() {
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
