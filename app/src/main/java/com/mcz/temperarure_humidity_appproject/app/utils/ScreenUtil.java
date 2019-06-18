package com.mcz.temperarure_humidity_appproject.app.utils;

import android.content.Context;
import android.util.DisplayMetrics;


/**
 * 屏幕分辨率工具类
 */
public class ScreenUtil {
    private int width, height, statusBarHeight;

    static ScreenUtil single = null;
    private Context context;

    public static ScreenUtil defaultCenter() {
        synchronized (ScreenUtil.class) {
            if (single == null) {
                single = new ScreenUtil();
            }
        }
        return single;
    }


    public int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 需要在第一个activity里面初始化一次，后面一直存在内存里面
     *
     * @param context activity
     */
    public void init(Context context) {
        if (getWidth() != 0 && getHeight() != 0) return;
        DisplayMetrics mDisplayMetrics = context.getApplicationContext().getResources().getDisplayMetrics();
        setWidth(mDisplayMetrics.widthPixels);
        setHeight(mDisplayMetrics.heightPixels);
        PreHelper.defaultCenter().setData(PreferenceKey.SCREEN_WIDTH, width);
        PreHelper.defaultCenter().setData(PreferenceKey.SCREEN_WIDTH, width);
    }

    /**
     * 获取屏幕分辨率宽
     **/
    public int getWidth() {
        if (width == 0) {
            width = PreHelper.defaultCenter().getIntData(PreferenceKey.SCREEN_WIDTH);
        }
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * 获取屏幕分辨率高
     **/
    public int getHeight() {
        if (height == 0) {
            height = PreHelper.defaultCenter().getIntData(PreferenceKey.SCREEN_HEIGHT);
        }
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setStatusBarHeight(int statusBarHeight) {
        this.statusBarHeight = statusBarHeight;
    }


}
