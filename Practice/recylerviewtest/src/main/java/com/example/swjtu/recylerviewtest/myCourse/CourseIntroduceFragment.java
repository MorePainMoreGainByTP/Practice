package com.example.swjtu.recylerviewtest.myCourse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.swjtu.recylerviewtest.R;
import com.example.swjtu.recylerviewtest.entity.Teacher;

/**
 * Created by tangpeng on 2017/3/8.
 */

public class CourseIntroduceFragment extends Fragment {

    private String courseProfile;
    private Teacher teacher;

    public static final String COURSE_PROFILE = "courseProfile";
    public static final String TEACHER = "teacher";


    public static CourseIntroduceFragment newInstance(String courseProfile, Teacher teacher) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(TEACHER, teacher);
        bundle.putString(COURSE_PROFILE, courseProfile);
        CourseIntroduceFragment fragment = new CourseIntroduceFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_course_introduce, null);
        Bundle bundle = getArguments();
        courseProfile = bundle.getString(COURSE_PROFILE);
        teacher = (Teacher) bundle.getSerializable(TEACHER);
        ((TextView) (rootView.findViewById(R.id.course_content_text))).setText(courseProfile);
        ((TextView) (rootView.findViewById(R.id.teacherName))).setText(teacher.getName());
        ((TextView) (rootView.findViewById(R.id.teacherPosition))).setText(teacher.getPosition());
        Glide.with(getActivity()).load(R.mipmap.teacher).into((ImageView) rootView.findViewById(R.id.teacherHeaderImage));

        return rootView;
    }
}
