package com.example.swjtu.recylerviewtest.entity;

/**
 * Created by tangpeng on 2017/3/5.
 */

public class Course {
    private int ImageId;
    private String name;
    private String teacherName;

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Course(int imageId, String name, String teacherName) {

        ImageId = imageId;
        this.name = name;
        this.teacherName = teacherName;
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
}
