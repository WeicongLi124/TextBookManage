package com.weicong.textbookmanage.activity;

import android.view.View;
import android.widget.LinearLayout;

import com.weicong.frankutils124.base.BaseActivity;

/**
 * @author: Frank
 * @time: 2018/4/14 10:42
 * @e-mail: 912220261@qq.com
 * Function:
 */
public class MainActivity extends BaseActivity implements View.OnClickListener{
    private LinearLayout courseLl;
    private LinearLayout bookLl;
    private LinearLayout orderLl;
    @Override
    protected int setLayout() {
        return R.layout.main_activity;
    }

    @Override
    protected void initView() {
        courseLl = findViewById(R.id.main_course_ll);
        courseLl.setOnClickListener(this);
        bookLl = findViewById(R.id.main_book_ll);
        bookLl.setOnClickListener(this);
        orderLl = findViewById(R.id.main_order_ll);
        orderLl.setOnClickListener(this);
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_course_ll:
                jumpToActivity(MainActivity.this,CourseActivity.class);
                break;
            case R.id.main_book_ll:
                jumpToActivity(MainActivity.this,BookActivity.class);
                break;
            case R.id.main_order_ll:
                jumpToActivity(MainActivity.this,OrderActivity.class);
                break;
        }
    }
}
