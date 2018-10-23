package com.weicong.textbookmanage.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;

import com.weicong.frankutils124.base.BaseActivity;

public class StatusActivity extends BaseActivity {
    private LinearLayout studentLl;
    private LinearLayout teacherLl;

    @Override
    protected int setLayout() {
        return R.layout.status_activity;
    }

    @Override
    protected void initView() {
        studentLl = findViewById(R.id.status_student_ll);
        teacherLl = findViewById(R.id.status_teacher_ll);
    }

    @Override
    protected void initListener() {
        studentLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatusActivity.this, RegisterActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("status", "学生");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        teacherLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatusActivity.this, RegisterActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("status", "教师");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
