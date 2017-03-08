package com.example.swjtu.recylerviewtest.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by tangpeng on 2017/3/8.
 */

public class CoursePracticeSection implements Serializable {
    private String sectionName;
    private ArrayList<CoursePractice> practiceArrayList;

    public CoursePracticeSection(String sectionName, ArrayList<CoursePractice> practices) {
        this.sectionName = sectionName;
        this.practiceArrayList = practices;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public ArrayList<CoursePractice> getPracticeArrayList() {
        return practiceArrayList;
    }

    public void setPracticeArrayList(ArrayList<CoursePractice> practiceArrayList) {
        this.practiceArrayList = practiceArrayList;
    }
}
