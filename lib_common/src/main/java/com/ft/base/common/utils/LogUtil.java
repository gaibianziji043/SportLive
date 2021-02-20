package com.ft.base.common.utils;

import android.util.Log;
import com.ft.base.BuildConfig;

/**
 * Log管理类
 */
public class LogUtil {

    private LogUtil() {
    }

    private static final boolean SHOW_LOG = BuildConfig.DEBUG;
    private static final String TAG = "LOG";

    public static void d(String msg) {
        if (SHOW_LOG) Log.d(TAG, msg);
    }

    public static void i(String msg) {
        if (SHOW_LOG) Log.i(TAG, msg);
    }

    public static void v(String msg) {
        if (SHOW_LOG) Log.v(TAG, msg);
    }

    public static void e(String msg) {
        if (SHOW_LOG) Log.e(TAG, msg);
    }

    public static void d(String tag, String msg) {
        if (SHOW_LOG) Log.d(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (SHOW_LOG) Log.i(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (SHOW_LOG) Log.v(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (SHOW_LOG) Log.e(tag, msg);
    }


}