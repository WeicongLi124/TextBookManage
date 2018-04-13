package com.weicong.frankutils124.utils;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

/**
 * @author: Frank
 * @time: 2018/3/12 12:00
 * @e-mail: 912220261@qq.com
 * Function: 用于获取手机信息的工具类
 * Attention: 需要检测获取 READ_PHONE_STATE 权限
 */

public class PhoneUtils {

    /**
     * 获取设备ID
     * @param context
     * @return
     */
    public static String getDeviceId(Context context) {
        TelephonyManager tm =
                (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getDeviceId() : null;
    }

    /**
     * 获取设备的IMEI码
     * @param context
     * @return
     */
    public static String getIMEI(Context context) {
        TelephonyManager tm =
                (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return tm != null ? tm.getImei() : null;
        }
        return tm != null ? tm.getDeviceId() : null;
    }

    /**
     * 获取设备的MEID码
     * @param context
     * @return
     */
    public static String getMEID(Context context) {
        TelephonyManager tm =
                (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return tm != null ? tm.getMeid() : null;
        } else {
            return tm != null ? tm.getDeviceId() : null;
        }
    }

    /**
     * 获取设备的IMSI码
     * @param context
     * @return
     */
    public static String getIMSI(Context context) {
        TelephonyManager tm =
                (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getSubscriberId() : null;
    }
}
