package com.ft.base.common.utils;


import android.util.Log;

import com.ft.base.BuildConfig;


/**
 * Created by vay on 2018/03/05.
 */
public class VayLog {

    private final static String DEFAULT_TAG = "VayLog";

    private static boolean LOGGABLE = BuildConfig.DEBUG;

    public static void d(String str) {
        d(DEFAULT_TAG, str);
    }

    public static void d(String tag, String str) {
        if (LOGGABLE) {
            Log.d(tag, str + "");
        }
    }

    public static void w(String str) {
        w(DEFAULT_TAG, str);
    }

    public static void w(String tag, String str) {
        if (LOGGABLE) {
            Log.w(tag, str + "");
        }
    }

    public static void e(String str) {
        e(DEFAULT_TAG, str);
    }

    public static void e(String tag, String str) {
        e(tag, str, null);
    }

    public static void e(String tag, String msg, Throwable e) {
        if (LOGGABLE) {
            Log.e(tag, msg + "", e);
        }
    }

    public static void i(String str) {
        i(DEFAULT_TAG, str + "");
    }

    public static void i(String tag, String str) {
        if (LOGGABLE) {
            Log.i(tag, str + "");
        }
    }

    public static void v(String str) {
        v(DEFAULT_TAG, str + "");
    }

    public static void v(String tag, String str) {
        if (LOGGABLE) {
            Log.v(tag, str + "");
        }
    }
}
