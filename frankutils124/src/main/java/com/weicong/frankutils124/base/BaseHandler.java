package com.weicong.frankutils124.base;

import android.app.Activity;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * @author: Frank
 * @time: 2018/4/2 21:08
 * @e-mail: 912220261@qq.com
 * Function: 使用handler时，设置activity或fragment的弱引用
 */
public abstract class BaseHandler extends Handler {
    //activity弱引用
    private WeakReference<Activity> activityWeakReference;
    //fragment弱引用
    private WeakReference<Fragment> fragmentWeakReference;

    private BaseHandler(){}

    //设置activity弱引用
    public BaseHandler(Activity activity){
        activityWeakReference = new WeakReference<>(activity);
    }
    //设置fragment弱引用
    public BaseHandler(Fragment fragment){
        fragmentWeakReference = new WeakReference<>(fragment);
    }

    @Override
    public void handleMessage(Message msg) {
        if (activityWeakReference == null || activityWeakReference.get() == null || activityWeakReference.get().isFinishing()) {
            //当activity目前不可用时进行提醒
            Log.e("BaseHandler","This activity is not disable now.");
        } else if (fragmentWeakReference == null || fragmentWeakReference.get() == null || fragmentWeakReference.get().isRemoving()) {
            //当fragment目前不可用时进行提醒
            Log.e("BaseHandler","This fragment is not disable now.");
        } else {
            //正常状态接收消息
            handleMessage(msg, msg.what);
        }
    }

    public abstract void handleMessage(Message message, int what);
}
