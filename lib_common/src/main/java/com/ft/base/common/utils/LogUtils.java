package com.ft.base.common.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * Debug日志
 */

public class LogUtils {

    public static boolean flag = true;
    public static String tag = "TAGG";


    public static void logi(String msg) {// 调试信息
        if (flag && !TextUtils.isEmpty(msg)) {
            Log.i(tag, msg);
        }
    }

    public static void loge(String msg) {// 调试信息
        if (flag && !TextUtils.isEmpty(msg)) {
            Log.e(tag, msg);
        }
    }


}
