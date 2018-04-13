package com.weicong.frankutils124.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * @author: Frank
 * @time: 2018/3/11 15:00
 * @e-mail: 912220261@qq.com
 * Function:
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayout());
        //steepStatusBar();
        initView();
        initListener();
    }

    /**
     * 获取布局
     * @return
     */
    protected abstract int setLayout();

    /**
     * 初始化view
     */
    protected abstract void initView();

    /**
     * 初始化监听事件
     */
    protected abstract void initListener();

    /**
     * 沉浸状态栏
     */
    public void setSteepStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 收起软键盘
     */
    public void hideSoftInput(){
        // 收起键盘
        View view = getWindow().peekDecorView();// 用于判断虚拟软键盘是否是显示的
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 不携带消息直接跳转
     * @param activity
     * @param nextActivity
     */
    public void jumpToActivity(Activity activity,Class<? extends Activity> nextActivity){
        activity.startActivity(new Intent(activity,nextActivity));
    }

    /**
     * 携带消息进行跳转
     * @param activity
     * @param nextActivity
     * @param requestCode
     */
    public void jumpToActivity(Activity activity,Class<? extends Activity> nextActivity,int requestCode){
        activity.startActivityForResult(new Intent(activity,nextActivity),requestCode);
    }

}
