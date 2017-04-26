package com.example.swjtu.databaseexpriment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by tangpeng on 2017/4/9.
 */

public class AllUsersActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);
        ActionBar actionBar =  getSupportActionBar();
        actionBar.setTitle("用户列表");
        
    }
}
