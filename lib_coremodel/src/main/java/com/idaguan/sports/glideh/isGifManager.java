package com.idaguan.sports.glideh;

import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author by sunfusheng on 2017/6/14.
 */
public class isGifManager {

    private static Map<String, isGifListener> listenersMap = Collections.synchronizedMap(new HashMap<>());
    public static Set<String> isGifSet = new HashSet<>();

    private isGifManager() {
    }


    public static final isGifListener LISTENER = (url, isGifType) -> {
        isGifListener onProgressListener = getProgressListener(url);
        if (onProgressListener != null) {
            onProgressListener.isGifListener(url,isGifType);
            removeListener(url);
        }
    };

    public static void addListener(String url, isGifListener listener) {
        if (!TextUtils.isEmpty(url) && listener != null) {
            listenersMap.put(url, listener);
//            listener.isGifListener(url,null);
        }
    }

    public static void removeListener(String url) {
        if (!TextUtils.isEmpty(url)) {
            listenersMap.remove(url);
        }
    }

    public static isGifListener getProgressListener(String url) {
        if (TextUtils.isEmpty(url) || listenersMap == null || listenersMap.size() == 0) {
            return null;
        }

        isGifListener listenerWeakReference = listenersMap.get(url);
        if (listenerWeakReference != null) {
            return listenerWeakReference;
        }
        return null;
    }

   public interface isGifListener {
        void isGifListener(String url, String type);
    }
}
