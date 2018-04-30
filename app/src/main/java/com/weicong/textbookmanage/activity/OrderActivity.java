package com.weicong.textbookmanage.activity;

import android.support.design.widget.FloatingActionButton;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.weicong.frankutils124.base.BaseActivity;

/**
 * @author: Frank
 * @time: 2018/4/16 22:03
 * @e-mail: 912220261@qq.com
 * Function:
 */
public class OrderActivity extends BaseActivity {
    private EditText searchEdt;
    private Button searchBtn;
    private ListView orderLv;
    private FloatingActionButton addBtn;
    @Override
    protected int setLayout() {
        return R.layout.order_activity;
    }

    @Override
    protected void initView() {
        searchEdt = findViewById(R.id.order_search_edt);
        searchBtn = findViewById(R.id.order_search_btn);
        orderLv = findViewById(R.id.order_lv);
        addBtn = findViewById(R.id.order_add_btn);
    }

    @Override
    protected void initListener() {
        orderLv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                orderLv.requestFocus();
                hideSoftInput();
                return false;
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToActivity(OrderActivity.this,OrderAddActivity.class);
            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
