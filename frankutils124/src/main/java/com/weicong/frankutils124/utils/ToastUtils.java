package com.weicong.frankutils124.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * @author: Frank
 * @time: 2018/3/11 15:22
 * @e-mail: 912220261@qq.com
 * Function:
 */

public class ToastUtils {

    private static Toast toast;

    /**
     * 传入文字
     * */
    public static void show( Context context , String text, int code){

        if (toast == null){
            toast = Toast.makeText( context, text , code);
        }else {
            //如果当前Toast没有消失， 直接显示内容，不需要重新设置
            toast.setText(text);
        }
        toast.show();
    }
    /**
     * 传入资源文件
     * */
    public static void show(Context context, int resId, int code){
        if (toast == null){
            toast = Toast.makeText( context, resId , code);
        }else {
            //如果当前Toast没有消失， 直接显示内容，不需要重新设置
            toast.setText(resId);
        }
        toast.show();
    }
    /**
     * 传入文字,在中间显示
     * */
    public static void showCenter( Context context , String text, int code){

        if (toast == null){
            toast = Toast.makeText( context, text , code);
        }else {
            //如果当前Toast没有消失， 直接显示内容，不需要重新设置
            toast.setText(text);
        }
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
    /**
     * 传入图片
     * */
    public static void showImg( Context context ,  int resImg, int code){
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(resImg);
        if (toast == null){
            toast = new Toast(context);
            toast.setView(imageView);
        }else {
            //如果当前Toast没有消失， 直接显示内容，不需要重新设置
            toast.setView(imageView);
        }
        //添加图片的操作,这里没有设置图片和文字显示在一行的操作呢...
        toast.show();
    }
}
