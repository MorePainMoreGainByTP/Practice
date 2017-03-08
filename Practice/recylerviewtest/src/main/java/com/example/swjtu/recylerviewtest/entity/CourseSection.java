package com.example.swjtu.recylerviewtest.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by tangpeng on 2017/3/8.
 */

public class CourseSection implements Serializable {
    private String sectionName;
    private ArrayList<CourseResource> courseResources;

    public CourseSection(String sectionName, ArrayList<CourseResource> courseResources) {
        this.sectionName = sectionName;
        this.courseResources = courseResources;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public ArrayList<CourseResource> getCourseResources() {
        return courseResources;
    }

    public void setCourseResources(ArrayList<CourseResource> courseResources) {
        this.courseResources = courseResources;
    }
}
