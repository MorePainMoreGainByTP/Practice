package com.example.swjtu.recylerviewtest.myCourse;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swjtu.recylerviewtest.R;
import com.example.swjtu.recylerviewtest.myCourse.discuss.QuestionDiscussActivity;

/**
 * Created by tangpeng on 2017/3/8.
 */

public class CourseDiscussFragment extends Fragment implements View.OnClickListener {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_discuss, null);
        rootView.findViewById(R.id.questionTeacher).setOnClickListener(this);
        rootView.findViewById(R.id.discussArea).setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.discussArea:
                startActivity(new Intent(getActivity(), QuestionDiscussActivity.class).putExtra("title", "课堂交流区"));
                break;
            case R.id.questionTeacher:
                startActivity(new Intent(getActivity(), QuestionDiscussActivity.class).putExtra("title", "老师答疑区"));
                break;
        }
    }
}
