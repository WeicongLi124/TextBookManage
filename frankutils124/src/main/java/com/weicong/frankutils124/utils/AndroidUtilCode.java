package com.weicong.frankutils124.utils;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;

/**
 * @author: Frank
 * @time: 2018/3/11 20:31
 * @e-mail: 912220261@qq.com
 * Function: 用于获取设备信息的工具类
 */

public class AndroidUtilCode {

    /**
     * 获取设备厂商
     * @return
     */
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取设备型号
     * @return
     */
    public static String getModel(){
        String model = Build.MODEL;
        if (model != null) model = model.trim().replace("\\*","");
        else model = "";
        return model;
    }

    /**
     * 获取设备的安卓ID
     * @param context
     * @return
     */
    public static String getAndroidID(Context context){
        return Settings.Secure.getString(
                context.getContentResolver(),
                Settings.Secure.ANDROID_ID
        );
    }

    /**
     * 获取安卓系统版本号
     * @return
     */
    public static String getSDKVersionName(){
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取安卓系统版本码
     * @return
     */
    public static int getSDKVersionCode(){
        return Build.VERSION.SDK_INT;
    }
}
