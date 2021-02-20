package com.ft.base.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.view.Display;

import androidx.annotation.NonNull;

import java.lang.reflect.Method;

/**
 * 常用工具类
 */
public final class Utils {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(@NonNull final Context context) {
        Utils.context = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) {
            return context;
        }
        throw new NullPointerException("should be initialized in application");
    }



    public static Resources disabledDisplayDpiChange(Resources res) {
        Configuration newConfig = res.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //字体非默认值
            if (res.getConfiguration().fontScale != 1) {
                newConfig.fontScale = 1;
            }
            newConfig.densityDpi = getDefaultDisplayDensity();
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        } else {
            //字体非默认值
            if (res.getConfiguration().fontScale != 1) {
                newConfig.fontScale = 1;//设置默认
                res.updateConfiguration(newConfig, res.getDisplayMetrics());
            }
        }
        return res;
    }

    public static int getDefaultDisplayDensity() {
        try {
            Class aClass = Class.forName("android.view.WindowManagerGlobal");
            Method method = aClass.getMethod("getWindowManagerService");
            method.setAccessible(true);
            Object iwm = method.invoke(aClass);
            Method getInitialDisplayDensity = iwm.getClass().getMethod("getInitialDisplayDensity", int.class);
            getInitialDisplayDensity.setAccessible(true);
            Object densityDpi = getInitialDisplayDensity.invoke(iwm, Display.DEFAULT_DISPLAY);
            return (int) densityDpi;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}