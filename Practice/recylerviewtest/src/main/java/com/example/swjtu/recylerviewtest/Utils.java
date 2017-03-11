package com.example.swjtu.recylerviewtest;

import java.util.Date;
import java.util.Random;

/**
 * Created by tangpeng on 2017/3/11.
 */

public class Utils {

    public static String getRandomDatetime() {
        Date date = new Date();
        Random random = new Random();
        return (date.getYear() + 1900) + "-" + date.getMonth() + "-" + (date.getDate()
                + random.nextInt(10)) % 30 + " " + date.getHours() + ":" + (date.getMinutes() + random.nextInt(20)) % 60 + ":" + date.getSeconds();
    }
}
