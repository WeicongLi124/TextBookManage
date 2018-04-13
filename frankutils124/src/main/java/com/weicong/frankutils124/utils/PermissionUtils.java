package com.weicong.frankutils124.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * @author: Frank
 * @time: 2018/3/11 21:38
 * @e-mail: 912220261@qq.com
 * Function: 用于获取手机相关权限的工具类
 */

public class PermissionUtils {

    /**
     * 相机的权限类型
     */
    public static final int CAMERA_PERMISSION = 0;
    /**
     * 内存的权限类型
     */
    public static final int STORAGE_PERMISSION = 1;
    public static final int MOUNT_UNMOUNT_FILESYSTEMS = 2;
    /**
     * 读取手机状态数据
     */
    public static final int READ_PHONE_STATE = 3;
    /**
     * 电话权限
     */
    public static final int CALL_PHONE = 4;
    /**
     * 获取权限方法
     *
     * @param context        上下文资源
     * @param permissionType 权限的类型
     */
    public static void getPermission(Context context, int permissionType) {
        boolean flag = false;
        //如果时Android 6.0 以上
        if (Build.VERSION.SDK_INT >= 23) {
            switch (permissionType) {
                //获取相机权限
                case CAMERA_PERMISSION:
                    if (!checkPermission(context, Manifest.permission.CAMERA)) {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION);
                    }
                    break;
                //获取内存权限
                case STORAGE_PERMISSION:
                    //获取读内存权限
                    if (!checkPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION);
                    }
                    //获取写内存权限
                    if (!checkPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION);
                    }
                    break;
                case MOUNT_UNMOUNT_FILESYSTEMS:
                    if (!checkPermission(context, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS)) {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS}, MOUNT_UNMOUNT_FILESYSTEMS);
                    }
                    break;
                case READ_PHONE_STATE:
                    //读取手机状态数据
                    if (!checkPermission(context, Manifest.permission.READ_PHONE_STATE)) {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_PHONE_STATE}, READ_PHONE_STATE);
                    }
                    break;
                case CALL_PHONE:
                    //获取电话权限
                    if (!checkPermission(context, Manifest.permission.CALL_PHONE)) {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, CALL_PHONE);
                    }
                    break;
                default:
                    break;
            }
        }
    }


    /**
     * 检测权限是否已获得
     *
     * @param context
     * @param type
     * @return
     */
    public static boolean checkPermission(Context context, String type) {
        boolean flag = false;
        int checkReadStoragePermission = ContextCompat.checkSelfPermission(context, type);
        if (checkReadStoragePermission == PackageManager.PERMISSION_GRANTED) {
            flag = true;
        }
        return flag;
    }

}
