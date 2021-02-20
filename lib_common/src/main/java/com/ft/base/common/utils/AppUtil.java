package com.ft.base.common.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class AppUtil {
    private AppUtil() {
    }

    /**
     * 登录态失效，重新登录
     */
    /*public static void reLogin(Context context) {
        //  Constants.currentAccount = new Account();
        DrawCardUtil.needRefresh = true;
        MerchantUtil.needRefresh = true;
        try {
            // 清除SharedPreferences部分数据
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(SPConstants.UUID, "");
            editor.putString(SPConstants.CURRENT_ACCOUNT, "");
            editor.apply();
            new GestureManager(context).quitGesture();
            // 清除数据
            //LoginShareUtil.clearCurrentAccount(context);

        } catch (Exception e) {
            e.printStackTrace();
        }

        ExitAppUtils.getInstance().clearActivities();
        Intent intent = new Intent(context, LoginActivity.class);
        // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("sidInvalid", true);
        context.startActivity(intent);
    }*/

    /**
     * 退出登录
     */
    public static void loginOut(Activity activity) {

        /*HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("mchid", BaseApplication.getMch_id());
        dataMap.put("sid", BaseApplication.getSession_id());
        HttpSend.postStringForMoneyBox(activity)
                .to("kct_user")
                .action("logout")
                .addDataMap(dataMap)
                .buildRequestCall()
                .execute(new MoneyBoxCallback() {
                    @Override
                    public void onServerHandleSuccess(String strData, JSONObject jsonData) {

                    }
                });

//        Constants.currentAccount = new Account();
        DrawCardUtil.needRefresh = true;
        MerchantUtil.needRefresh = true;

        try {
            // 清除SharedPreferences部分数据
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(SPConstants.UUID, "");
            editor.putString(SPConstants.CURRENT_ACCOUNT, "");
            editor.apply();

            new GestureManager(activity).quitGesture();

            //    LoginShareUtil.clearCurrentAccount(activity);


        } catch (Exception e) {
            e.printStackTrace();
        }

//        // 停止推送消息
//        PushManager.stopWork(activity);

        ExitAppUtils.getInstance().clearActivities();
        Intent intent = new Intent(activity, LoginActivity.class);
        // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
        activity.finish();*/
    }

    /**
     * 某个服务是否在运行
     */
    public static boolean isServiceStarted(Context context, String serviceClassName) {
        boolean isStarted = false;
        try {
            ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningServiceInfo> runningServices = mActivityManager.getRunningServices(1000);
            for (ActivityManager.RunningServiceInfo mService : runningServices) {
                if (mService.service.getClassName().equals(serviceClassName)) {
                    isStarted = true;
                    break;
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return isStarted;
    }


    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取应用程序版本名称信息
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setFullScreen(Context context, boolean full) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (full) {
                if (Build.VERSION.SDK_INT >= 21) {
                    View decorView = activity.getWindow().getDecorView();
                    decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                    activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
                } else {
                    activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                }
            } else {
                //隐藏
                if (Build.VERSION.SDK_INT >= 21) {
                    View decorView = activity.getWindow().getDecorView();
                    decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE | View.SYSTEM_UI_FLAG_VISIBLE);
                    activity.getWindow().setStatusBarColor(ContextCompat.getColor(context, android.R.color.transparent));
                } else {
                    activity.getWindow().setFlags(View.SYSTEM_UI_FLAG_VISIBLE, View.SYSTEM_UI_FLAG_VISIBLE);
                }
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static boolean isNotificationEnabled(Context context) {

        String CHECK_OP_NO_THROW = "checkOpNoThrow";
        String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;

        Class appOpsClass = null;
        /* Context.APP_OPS_MANAGER */
        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE,
                    String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);

            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void goToSet(Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);//设置去向意图

        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE) {
            // 进入设置系统应用权限界面
            Intent intent = new Intent(Settings.ACTION_SETTINGS);
            context.startActivity(intent);
            return;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {// 运行系统在5.x环境使用
            // 进入设置系统应用权限界面
            Intent intent = new Intent(Settings.ACTION_SETTINGS);
            context.startActivity(intent);
            return;
        }*/
    }

}
