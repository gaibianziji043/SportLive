package com.ft.base.common.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.View;

/**
 * Created by Administrator on 2017/4/18.
 */

public class ScreenShotTools {

    public static Bitmap takeScreenShot(Activity activity) {
        Bitmap bitmap = null;
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        bitmap = view.getDrawingCache();

        // 获取状态栏的高度
        Rect frame = new Rect();
        // 测量屏幕宽高
        view.getWindowVisibleDisplayFrame(frame);
        int stautsHeight = frame.top;
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay().getHeight();
        // 根据坐标点和需要的款和高创建bitmap
        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);


//        View view = activity.getWindow().getDecorView();
//        view.setDrawingCacheEnabled(true);
//        view.buildDrawingCache();
//        bitmap = view.getDrawingCache();
//        Rect frame = new Rect();
//        view.getWindowVisibleDisplayFrame(frame);
//        int statusBarHeight = frame.top;
//
//        bitmap = Bitmap.createBitmap(bitmap,
//                0, DensityUtil.dp2px(this, 48),
//                Constants.SCREEN_WIDTH, DensityUtil.dp2px(this, 65 + 240 + 50 + 50));
//        view.destroyDrawingCache();


        return bitmap;
    }


    public static Bitmap takeScreenShot_NoTitle(Activity activity) {
        // 截图的View
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int stautsHeight = frame.top;
        LogUtils.loge("状态栏的高度为=======" + stautsHeight);

        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay().getHeight();

        Bitmap bm = Bitmap.createBitmap(bmp,
                0, DensityUtil.dp2px(activity, 75),
                width, DensityUtil.dp2px(activity, 65 + 240 + 50 + 50));
        view.destroyDrawingCache();

        return bm;
    }


}
