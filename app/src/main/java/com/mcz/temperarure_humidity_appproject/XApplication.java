package com.mcz.temperarure_humidity_appproject;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.mcz.temperarure_humidity_appproject.app.utils.ScreenUtil;


/**
 * Created by JackMar on 2017/3/1.
 * 邮箱：1261404794@qq.com
 */

public class XApplication extends Application {

    private static XApplication sInstance;
    //默认的首页位置
    public static int currentIndex = 1;
    //默认pageIndex
    public static int currentPageIndex = 1;

    public static boolean isLogin = false;

    //单例模式中获取唯一的MyApplication实例
    public static Context getInstance() {
        if (sInstance == null) {
            sInstance = new XApplication();
        }
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        init();
    }

    private void init() {
        ScreenUtil.defaultCenter().init(sInstance);
    }

    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return 进程号
     */
    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService( Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

}
