package com.example.swjtu.recylerviewtest.entity;

import java.io.Serializable;

/**
 * Created by tangpeng on 2017/3/5.
 */

public class Course implements Serializable{
    private int ImageId;
    private String name;
    private Teacher teacher;
    private String imageURL;
    private String courseProfile;

    public Course(int imageId, String name, Teacher teacher, String imageURL, String courseProfile) {
        ImageId = imageId;
        this.name = name;
        this.teacher = teacher;
        this.imageURL = imageURL;
        this.courseProfile = courseProfile;
    }

    public int getImageId() {
        return ImageId;
    }

    public void setImageId(int imageId) {
        ImageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getCourseProfile() {
        return courseProfile;
    }

    public void setCourseProfile(String courseProfile) {
        this.courseProfile = courseProfile;
    }
}
