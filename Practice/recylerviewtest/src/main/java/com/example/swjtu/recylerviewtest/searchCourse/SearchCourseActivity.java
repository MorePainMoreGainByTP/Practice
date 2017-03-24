package com.example.swjtu.recylerviewtest.searchCourse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.swjtu.recylerviewtest.R;

/**
 * Created by tangpeng on 2017/3/8.
 */

public class SearchCourseActivity extends AppCompatActivity implements TextWatcher {

    private EditText editSearch;
    private TextView textSearch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_course);
        initViews();
    }


    private void initViews() {
        editSearch = (EditText) findViewById(R.id.editSearchName);
        editSearch.addTextChangedListener(this);
        textSearch = (TextView) findViewById(R.id.btnSearch);
    }

    public void back(View v) {
        finish();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s != null && !s.equals("")) {
            textSearch.setVisibility(View.VISIBLE);
        } else {
            textSearch.setVisibility(View.GONE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
