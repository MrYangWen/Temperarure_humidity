package com.mcz.temperarure_humidity_appproject.app.utils;

import android.content.Context;
import android.graphics.Paint;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Android操作字符串工具类
 * <p>
 * Created by luojiang on 2015/9/17.
 */
public class StrUtil {
    /**
     * The empty String {@code ""}.
     *
     * @since 2.0
     */
    public static final String EMPTY = "";

    /**
     * Represents a failed index search.
     *
     * @since 2.1
     */
    public static final int INDEX_NOT_FOUND = -1;

    /**
     * 首字母变小写
     */
    public static String firstCharToLowerCase(String str) {
        Character firstChar = str.charAt(0);
        String tail = str.substring(1);
        str = Character.toLowerCase(firstChar) + tail;
        return str;
    }

    /**
     * 首字母变大写
     */
    public static String firstCharToUpperCase(String str) {
        Character firstChar = str.charAt(0);
        String tail = str.substring(1);
        str = Character.toUpperCase(firstChar) + tail;
        return str;
    }

    /**
     * 字符串为 null 或者为 "" 时返回 true
     */
    public static boolean isBlank(String str) {
        return str == null || "".equals(str.trim()) ? true : false;
    }

    /**
     * 字符串不为 null 而且不为 "" 时返回 true
     */
    public static boolean notBlank(String str) {
        return str == null || "".equals(str.trim()) ? false : true;
    }

    public static boolean notBlank(String... strings) {
        if (strings == null)
            return false;
        for (String str : strings)
            if (str == null || "".equals(str.trim()))
                return false;
        return true;
    }

    public static boolean notNull(Object... paras) {
        if (paras == null)
            return false;
        for (Object obj : paras)
            if (obj == null)
                return false;
        return true;
    }

    /**
     * <p>
     * Checks if a CharSequence is empty ("") or null.
     * </p>
     * <p>
     * <pre>
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false
     * </pre>
     * <p>
     * <p>
     * NOTE: This method changed in Lang version 2.0. It no longer trims the CharSequence. That functionality is available in isBlank().
     * </p>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is empty or null
     * @since 3.0 Changed signature from isEmpty(String) to isEmpty(CharSequence)
     */
    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * <p>
     * Checks if a CharSequence is not empty ("") and not null.
     * </p>
     * <p>
     * <pre>
     * StringUtils.isNotEmpty(null)      = false
     * StringUtils.isNotEmpty("")        = false
     * StringUtils.isNotEmpty(" ")       = true
     * StringUtils.isNotEmpty("bob")     = true
     * StringUtils.isNotEmpty("  bob  ") = true
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is not empty and not null
     * @since 3.0 Changed signature from isNotEmpty(String) to isNotEmpty(CharSequence)
     */
    public static boolean isNotEmpty(CharSequence cs) {
        return !isEmpty(cs);
    }

    /**
     * <p>
     * Checks if a CharSequence is whitespace, empty ("") or null.
     * </p>
     * <p>
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("null")    = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is null, empty or whitespace
     * @since 3.0 Changed signature from isBlank(String) to isBlank(CharSequence)
     */
    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0 || cs.equals("null")) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>
     * Checks if a CharSequence is not empty (""), not null and not whitespace only.
     * </p>
     * <p>
     * <pre>
     * StringUtils.isNotBlank(null)      = false
     * StringUtils.isNotBlank("null")    = false
     * StringUtils.isNotBlank("")        = false
     * StringUtils.isNotBlank(" ")       = false
     * StringUtils.isNotBlank("bob")     = true
     * StringUtils.isNotBlank("  bob  ") = true
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is not empty and not null and not whitespace
     * @since 3.0 Changed signature from isNotBlank(String) to isNotBlank(CharSequence)
     */
    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }

    /**
     * @param str the String to be trimmed, may be null
     * @return the trimmed string, {@code null} if null String input
     */
    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    /**
     * @param str the String to be trimmed, may be null
     * @return the trimmed String, {@code null} if only chars &lt;= 32, empty or null String input
     * @since 2.0
     */
    public static String trimToNull(String str) {
        String ts = trim(str);
        return isEmpty(ts) ? null : ts;
    }

    /**
     * @param str the String to be trimmed, may be null
     * @return the trimmed String, or an empty String if {@code null} input
     * @since 2.0
     */
    public static String trimToEmpty(String str) {
        return str == null ? EMPTY : str.trim();
    }

    // Equals
    // -----------------------------------------------------------------------

    /**
     * <p>
     * Compares two CharSequences, returning {@code true} if they are equal.
     * </p>
     * <p>
     * <p>
     * {@code null}s are handled without exceptions. Two {@code null} references are considered to be equal. The comparison is case sensitive.
     * </p>
     * <p>
     * <pre>
     * StringUtils.equals(null, null)   = true
     * StringUtils.equals(null, "abc")  = false
     * StringUtils.equals("abc", null)  = false
     * StringUtils.equals("abc", "abc") = true
     * StringUtils.equals("abc", "ABC") = false
     * </pre>
     *
     * @param cs1 the first CharSequence, may be null
     * @param cs2 the second CharSequence, may be null
     * @return {@code true} if the CharSequences are equal, case sensitive, or both {@code null}
     * @see String#equals(Object)
     * @since 3.0 Changed signature from equals(String, String) to equals(CharSequence, CharSequence)
     */
    public static boolean equals(CharSequence cs1, CharSequence cs2) {
        return cs1 == null ? cs2 == null : cs1.equals(cs2);
    }

    /**
     * 数字正则表达式
     *
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher match = pattern.matcher(str);
        if (match.matches() == false) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 隐藏电话号码中间四位
     *
     * @param phoneNum
     * @return
     */
    public static String hidePhoneNum(String phoneNum) {
        return phoneNum.substring(0, 3) + "****" + phoneNum.substring(7, phoneNum.length());
    }

    /**
     * 生成唯一标示符
     *
     * @return
     * @author andrew
     */
    public static String getUUID() {
        UUID uuid = UUID.randomUUID();

        return uuid.toString();
    }

    public static float getContentWidth(String content, TextView tView) {
        if (null == tView && TextUtils.isEmpty(content)) {
            return 0f;
        }
        return getContentWidthWithSize(content, tView.getTextSize()) * tView.getTextScaleX();
    }

    private static float getContentWidthWithSize(String content, float textSize) {
        float width = 0f;

        Paint tPaint = new Paint();
        tPaint.setTextSize(textSize);
        width = tPaint.measureText(content);

        return width;
    }

    /**
     * 判断是否为手机号的正则
     *
     * @param mobiles 输入的电话号码
     * @return
     */
    public static boolean isMobile(String mobiles) {
        if (!StrUtil.isEmpty(mobiles)) {
            Pattern p = Pattern.compile("^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8]))\\d{8}$");

            Matcher m = p.matcher(mobiles);

            return m.matches();
        }
        return false;
    }

    /**
     * 验证密码规则
     *
     * @param passWord
     * @return
     */
    public static boolean isPasswordCorrect(String passWord) {
        if (!StrUtil.isEmpty(passWord)) {
            if (passWord.length() >= 6) {
                return true;
            }
        }
        return false;
    }


    /**
     * 身份证号码校验
     *
     * @param idCard 输入的电话号码
     * @return
     */
    public static boolean isIDCard(String idCard) {

        Pattern p = Pattern.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}(\\d|[x,X])$");

        Matcher m = p.matcher(idCard);

        return m.matches();

    }

    /**
     * 获取设备唯一的id
     *
     * @param context
     * @return
     */
    public static String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService( Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    /**
     * 根据返回的距离进行转换
     *
     * @param dis
     * @return
     */
    public static String m2Km(int dis) {
        String distance = "";
        if (dis < 1000) {
            distance = dis + "m";
        } else {
            int d = dis / 1000;
            distance = Math.floor(d * 10d) / 10 + "km";
        }
        return distance;
    }

    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


}
