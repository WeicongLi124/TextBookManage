package com.weicong.textbookmanage.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.weicong.frankutils124.base.BaseActivity;
import com.weicong.textbookmanage.R;

public class StatusActivity extends BaseActivity {
    private CardView studentCard;
    private CardView teacherCard;

    @Override
    protected int setLayout() {
        return R.layout.status_activity;
    }

    @Override
    protected void initView() {
        studentCard = findViewById(R.id.status_student_card);
        teacherCard = findViewById(R.id.status_teacher_card);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.slide_bottom));
        }*/
    }

    @Override
    protected void initListener() {
        studentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatusActivity.this,RegisterActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("status","学生");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        teacherCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatusActivity.this,RegisterActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("status","教师");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
