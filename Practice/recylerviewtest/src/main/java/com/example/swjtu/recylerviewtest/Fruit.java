package com.example.swjtu.recylerviewtest;

/**
 * Created by tangpeng on 2017/3/5.
 */

public class Fruit {
    private int ImageId;
    private String name;

    public Fruit(int imageId, String name) {
        ImageId = imageId;
        this.name = name;
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
