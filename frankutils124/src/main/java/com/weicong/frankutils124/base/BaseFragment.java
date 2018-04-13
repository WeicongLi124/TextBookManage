package com.weicong.frankutils124.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

/**
 * @author: Frank
 * @time: 2018/3/11 15:16
 * @e-mail: 912220261@qq.com
 * Function:
 */

public abstract class BaseFragment extends Fragment{
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(setLayout(),container,false);
        initView(view);
        initListener();
        return view;
    }

    /**
     * 获取布局
     * @return
     */
    protected abstract int setLayout();

    /**
     * 初始化view
     */
    protected abstract void initView(View view);

    /**
     * 初始化监听事件
     */
    protected abstract void initListener();

    /**
     * 收起软键盘
     */
    public void hideSoftInput(){
        // 收起键盘
        View view = getActivity().getWindow().peekDecorView();// 用于判断虚拟软键盘是否是显示的
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 不携带消息直接跳转
     * @param activity
     * @param nextActivity
     */
    public void jumpToActivity(Activity activity, Class<? extends Activity> nextActivity){
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
