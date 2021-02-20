package com.ft.base.common.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Android单位转换工具类
 */
public class DensityUtil {

    private DensityUtil() {
    }

    /**
     * dp->px
     */
    public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }


    /**
     * sp->px
     */
    public static int sp2px(Context context, int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }


    /**
     * px转dp
     */
    public static float px2dp(Context context, float pxVal) {
        return pxVal / context.getResources().getDisplayMetrics().density;
    }

    /**
     * px转sp
     */
    public static float px2sp(Context context, float pxVal) {
        return pxVal / context.getResources().getDisplayMetrics().scaledDensity;
    }

}
