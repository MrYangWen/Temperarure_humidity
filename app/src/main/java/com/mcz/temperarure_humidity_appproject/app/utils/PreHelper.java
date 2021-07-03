package com.mcz.temperarure_humidity_appproject.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.mcz.temperarure_humidity_appproject.XApplication ;


public class PreHelper {
    static PreHelper single = null;

    public static PreHelper defaultCenter() {
        synchronized (PreHelper.class) {
            if (single == null) {
                single = new PreHelper();
            }
        }
        return single;
    }
    public static PreHelper defaultCenter1(Context context) {
        synchronized (PreHelper.class) {
            if (single == null) {
                single = new PreHelper(context);
            }
        }
        return single;
    }

    SharedPreferences preference = null;

    public PreHelper() {
        XApplication xplt = new XApplication();
        preference = xplt.getInstance().getApplicationContext().getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE);
    }

    public PreHelper(Context context) {
        preference =context.getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE);
    }


    /**
     * 设置 类容
     *
     * @param key   建
     * @param value 值  如果value为空的话，则清除当前key 和对应的值
     */
    public void setData(String key, String value) {
        if (StrUtil.isEmpty(key)) {
            return;
        }
        Editor edit = preference.edit();
        if (StrUtil.isEmpty(value)) {
            edit.remove(key);
            edit.commit();
            return;
        }
        edit.putString(key, value);
        edit.commit();
    }


    /**
     * 设置 类容
     *
     * @param key   建
     * @param value 值  如果value为空的话，则清除当前key 和对应的值
     */
    public void setData(String key, int value) {
        Editor edit = preference.edit();
        edit.putInt(key, value);
        edit.commit();
    }

    /**
     * 设置 类容
     *
     * @param key   建
     * @param value 值  如果value为空的话，则清除当前key 和对应的值
     */
    public void setData(String key, boolean value) {
        if (StrUtil.isEmpty(key)) {
            return;
        }
        Editor edit = preference.edit();
        edit.putBoolean(key, value);
        edit.commit();
    }

    /**
     * 根据key  获取值
     *
     * @param key 建
     * @return String   value
     */
    public String getStringData(String key) {
        if (TextUtils.isEmpty(key)) {
            return "";
        }
        String value = preference.getString(key, "");
        return value;
    }


    /**
     * 根据key  获取值
     *
     * @param key 建
     * @return int   value
     */
    public int getIntData(String key) {
        if (TextUtils.isEmpty(key)) {
            return 0;
        }
        int value = preference.getInt(key, 0);
        return value;
    }

    /**
     * 获取布尔值
     *
     * @param key
     * @return
     */
    public boolean getBooleanData(String key) {
        if (StrUtil.isEmpty(key)) {
            return false;
        }
        return preference.getBoolean(key, false);
    }

    /**
     * 自定义默认返回类型的方法
     *
     * @param key
     * @param isDefault
     * @return
     */
    public boolean getBooleanData(String key, boolean isDefault) {
        if (StrUtil.isEmpty(key)) {
            return false;
        }
        return preference.getBoolean(key, isDefault);
    }

    /**
     * 存对象
     *
     * @param key
     * @param object
     */
    public void putObject(String key, Object object) {

        if (StrUtil.isEmpty(key)) {
            return;
        }
        Editor edit = preference.edit();
        if (object == null) {
            edit.remove(key);
            edit.commit();
        } else {
            String value = JSON.toJSONString(object);
            edit.putString(key, value);
            edit.commit();
        }
    }

    /**
     * 取对象
     *
     * @param key
     * @param c
     * @param <T>
     * @return
     */
    public <T> T getObject(String key, Class<T> c) {

        String value = preference.getString(key, "");
        if (TextUtils.isEmpty(value))
            return null;
        T t = JSON.parseObject(value, c);
        return t;
    }
}
