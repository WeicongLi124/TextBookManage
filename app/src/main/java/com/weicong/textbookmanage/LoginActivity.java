package com.weicong.textbookmanage;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.weicong.frankutils124.base.BaseActivity;

/**
 * @author: Frank
 * @time: 2018/4/8 13:27
 * @e-mail: 912220261@qq.com
 * Function:
 */
public class LoginActivity extends BaseActivity {
    private RelativeLayout loginLayout;
    private TextView registerTextView;

    @Override
    protected int setLayout() {
        return R.layout.login_activity;
    }

    @Override
    protected void initView() {
        setSteepStatusBar();
        loginLayout = findViewById(R.id.login_rl);
        registerTextView = findViewById(R.id.register_tv);
    }

    @Override
    protected void initListener() {
        loginLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                loginLayout.requestFocus();
                hideSoftInput();
                return false;
            }
        });
        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToActivity(LoginActivity.this,StatusActivity.class);
                /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(new Intent(LoginActivity.this,StatusActivity.class), ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this).toBundle());
                }else jumpToActivity(LoginActivity.this,StatusActivity.class);*/
            }
        });
    }

}
