package com.weicong.textbookmanage.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weicong.frankutils124.base.BaseActivity;
import com.weicong.textbookmanage.model.User;

/**
 * @author: Frank
 * @time: 2018/4/14 10:42
 * @e-mail: 912220261@qq.com
 * Function:
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout courseLl;
    private LinearLayout bookLl;
    private LinearLayout orderLl;
    private TextView courseTv;
    private TextView bookTv;
    private TextView orderTv;

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
        courseTv = findViewById(R.id.main_course_tv);
        bookTv = findViewById(R.id.main_book_tv);
        orderTv = findViewById(R.id.main_order_tv);
        if (User.USER_STATUS.equals("学生")) {
            courseTv.setText("课程查询");
            bookTv.setText("教材查询");
            orderTv.setText("教材征订查询");
        }
    }

    @Override
    protected void initListener() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_course_ll:
                jumpToActivity(MainActivity.this, CourseActivity.class);
                break;
            case R.id.main_book_ll:
                jumpToActivity(MainActivity.this, BookActivity.class);
                break;
            case R.id.main_order_ll:
                jumpToActivity(MainActivity.this, OrderActivity.class);
                break;
        }
    }
}
