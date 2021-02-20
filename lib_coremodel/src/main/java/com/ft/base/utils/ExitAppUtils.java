package com.ft.base.utils;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

public class ExitAppUtils {

    private List<Activity> mActivityList = new LinkedList<Activity>();

    private static ExitAppUtils instance;
    static {
        instance = new ExitAppUtils();
    }

    private ExitAppUtils() {

    }

    public static ExitAppUtils getInstance() {
        return instance;
    }

    public void addActivity(Activity activity) {
        mActivityList.add(activity);
    }

    public void delActivity(Activity activity) {
        mActivityList.remove(activity);
    }

    public void clearActivities() {
        for (Activity activity : mActivityList) {
            try {
                activity.finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(mActivityList!=null){
            mActivityList.clear();
        }
    }

    public void exitApp() {
        clearActivities();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

}
