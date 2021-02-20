package com.ft.base.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ft.base.base.BaseApplication;

import java.util.Set;

/**
 * SharedPreferences工具类
 */
public final class SPUtils {

    private static SharedPreferences sp;

    private static void init(Context context) {
        if (sp == null) {
            sp = PreferenceManager.getDefaultSharedPreferences(BaseApplication.getInstance());
        }
    }

    public static void setSharedIntData(Context context, String key, int value) {
        if (sp == null) {
            init(context);
        }
        sp.edit().putInt(key, value).commit();
    }

    public static int getSharedIntData(Context context, String key) {
        if (sp == null) {
            init(context);
        }
        return sp.getInt(key, 0);
    }

    public static void setSharedlongData(Context context, String key, long value) {
        if (sp == null) {
            init(context);
        }
        sp.edit().putLong(key, value).commit();
    }

    public static long getSharedlongData(Context context, String key) {
        if (sp == null) {
            init(context);
        }
        return sp.getLong(key, 0l);
    }

    public static void setSharedFloatData(Context context, String key,
                                          float value) {
        if (sp == null) {
            init(context);
        }
        sp.edit().putFloat(key, value).commit();
    }

    public static Float getSharedFloatData(Context context, String key) {
        if (sp == null) {
            init(context);
        }
        return sp.getFloat(key, 0f);
    }

    public static void setSharedBooleanData(Context context, String key,
                                            boolean value) {
        if (sp == null) {
            init(context);
        }
        sp.edit().putBoolean(key, value).commit();
    }

    public static Boolean getSharedBooleanData(Context context, String key) {
        if (sp == null) {
            init(context);
        }
        return sp.getBoolean(key, false);
    }
    public static Boolean getSharedBooleanData(Context context, String key, boolean defValue) {
        if (sp == null) {
            init(context);
        }
        return sp.getBoolean(key, defValue);
    }

    public static void setSharedStringData(Context context, String key, String value) {
        if (sp == null) {
            init(context);
        }
        sp.edit().putString(key, value).commit();
    }

    public static String getSharedStringData(Context context, String key) {
        if (sp == null) {
            init(context);
        }
        return sp.getString(key, "");
    }

    public static String getSharedStringData(Context context, String key, String defValue) {
        if (sp == null) {
            init(context);
        }
        return sp.getString(key, defValue);
    }

    public static SharedPreferences getSharedPreferences(Context context) {
        if (sp == null) {
            init(context);
        }
        return sp;
    }

    public static void remove(Context context, String key) {
        if (sp == null) {
            init(context);
        }
        sp.edit().remove(key).commit();
    }

    public static void setSharedSetData(Context context, String key, Set<String> value) {
        if (sp == null) {
            init(context);
        }
        sp.edit().putStringSet(key, value).commit();
    }

    public static Set<String> getSharedSetData(Context context, String key) {
        if (sp == null) {
            init(context);
        }
        return sp.getStringSet(key, null);
    }
}
